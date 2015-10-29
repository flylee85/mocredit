package com.mocredit.sendcode.service.impl;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.model.*;
import com.mocredit.activity.persitence.ActivityStoreMapper;
import com.mocredit.activity.persitence.BatchCodeMapper;
import com.mocredit.activity.persitence.BatchMapper;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.util.*;
import cn.m.mt.common.MMSBO;
import com.mocredit.sendcode.constant.BatchCodeStatus;
import com.mocredit.sendcode.constant.BatchStatus;
import com.mocredit.sendcode.constant.DownloadType;
import com.mocredit.sendcode.service.SendCodeService;
import com.mocredit.sys.service.OptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.InputStream;
import java.util.*;

/**
 * Created by ytq on 2015/10/23.
 * 发码组建接口实现类
 */
@Service
public class SendCodeServiceImpl implements SendCodeService {
    @Autowired
    private BatchMapper batchMapper;
    @Autowired
    private BatchCodeMapper batchCodeMapper;
    @Autowired
    private OptLogService optLogService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityStoreMapper activityStoreMapper; // 活动关联门店dao对象

    @Override
    @Transactional
    public List<BatchCode> downloadList(String type, String name, String id, Integer codeCount) {
        //    CODE("01", "验码"), BATCH("02", "批次"), ACTIVITY("03", "活动");
        Map<String, Object> batchMap = new HashMap<>();
        Activity activity = null;
        if (DownloadType.ACTIVITY.getValue().equals(type)) {
            activity = activityService.getActivityById(id);
            String batchId = activityService.extractedCode(id, name, codeCount);
            batchMap.put("batchId", batchId);
        }
        if (DownloadType.BATCH.getValue().equals(type)) {
            String actId = batchMapper.getBatchById(id).getActivityId();
            activity = activityService.getActivityById(actId);
            batchMap.put("batchId", id);
        }
        List<BatchCode> batchCodeAllList = new ArrayList<>();
        int pageNum = 1;
        boolean isFinish = false;
        while (!isFinish) {
            PageHelper.startPage(pageNum, pageSize);
            List<BatchCode> batchCodeList = batchCodeMapper.queryBatchCodeList(batchMap);
            if (batchCodeList.isEmpty()) {
                isFinish = true;
            } else {
                batchCodeAllList.addAll(batchCodeList);
                pageNum += 1;
            }
        }
        for (BatchCode batchCode : batchCodeAllList) {
            batchCode.setStatus(BatchCodeStatus.ALREADY_SEND.getValue());
            batchCode.setStartTime(new Date());
            batchCodeMapper.updateBatchCode(batchCode);
        }
        carryVerifyCode(activity, batchMap.get("batchId") + "", batchCodeAllList);
        return batchCodeAllList;
    }

    @Override
    public List<BatchBvo> getActBatchList(Map<String, Object> activityMap, Integer draw, Integer pageNum, Integer pageSize) {
        if (draw != null && draw != 1) {
            String searchContent = String.valueOf(activityMap.get("search[value]"));
            if (searchContent != null && !"".equals(searchContent)) {
                activityMap.put("searchInfoContent", searchContent);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return batchMapper.getActBatchList(activityMap);
    }

    @Override
    public List<Object> getBatchDetailList(String batchId, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Object uploadAndSend(String type, List<Object> customerMobileList) {
        return null;
    }

    @Override
    public Object sendCode(String type, String id) {
        return null;
    }

    @Override
    public boolean delBatchById(String batchId) {
        Map<String, Object> batchMap = new HashMap<>();
        batchMap.put("batchId", batchId);
        batchMap.put("status", BatchStatus.DEL.getValue());
        return batchMapper.delBatchById(batchMap) > 0;
    }

    @Override
    public boolean sendCodeById(String actId, String id) {
        try {
            List<BatchCode> batchCodeAllList = new ArrayList<>();
            BatchCode batchCode = batchCodeMapper.getBatchCodeById(id);
            batchCodeAllList.add(batchCode);
            sendCode(actId, batchCode.getBatchId(), batchCodeAllList);
            //todo 单次发送短信需要更新批次发送成功数

            //记录发送短信和保存发码两个步骤的日志
            StringBuffer optInfo1 = new StringBuffer();
            optInfo1.append("发送数量：" + batchCodeAllList.size() + ";");
            optLogService.addOptLog("活动Id:" + actId + ",码Id:" + id, "", "发送短信并保存发码记录-----" + optInfo1.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean sendCodeByBatchId(String actId, String batchId) {
        boolean sendSuccessFlag = true;
        try {
            Map<String, Object> batchMap = new HashMap<>();
            batchMap.put("batchId", batchId);
            List<BatchCode> batchCodeAllList = new ArrayList<>();
            int pageNum = 1;
            boolean isFinish = false;
            while (!isFinish) {
                PageHelper.startPage(pageNum, pageSize);
                List<BatchCode> batchCodeList = batchCodeMapper.queryBatchCodeList(batchMap);
                if (batchCodeList.isEmpty()) {
                    isFinish = true;
                } else {
                    batchCodeAllList.addAll(batchCodeList);
                    pageNum += 1;
                }
            }
            //更新批次导入数量和成功数量
            Batch batchOri = batchMapper.getBatchById(batchId);
            //更新批次导入数量和成功数量
            Batch batch = new Batch();
            batch.setId(batchId);
            //记录发送短信和保存发码两个步骤的日志
            StringBuffer optInfo1 = new StringBuffer();
            optInfo1.append("发送数量：" + batchCodeAllList.size() + ";");
            try {
                sendCode(actId, batchId, batchCodeAllList);
                if (batchOri.getSendSuccessNumber() != null) {
                    batch.setSendSuccessNumber(batchOri.getSendSuccessNumber() + batchCodeAllList.size());
                } else {
                    batch.setSendSuccessNumber(batchCodeAllList.size());
                }
                batch.setImportSuccessNumber(batchCodeAllList.size() + batchOri.getImportSuccessNumber());
                batch.setStatus(BatchStatus.ALREADY_SEND.getValue());
                optInfo1.append("成功数量：" + batchCodeAllList.size() + ";");
            } catch (Exception e) {
                if (batchOri.getSendFailNumber() != null) {
                    batch.setSendFailNumber(batchOri.getSendFailNumber() + batchCodeAllList.size());
                } else {
                    batch.setSendFailNumber(batchCodeAllList.size());

                }
                batch.setStatus(BatchStatus.IMPORT_NOT_CARRY.getValue());
                optInfo1.append("失败数量：" + batchCodeAllList.size() + ";");
                sendSuccessFlag = false;
            }
            batchMapper.updateBatch(batch);
            optLogService.addOptLog("活动Id:" + actId + ",批次Id:" + batchId, "", "发送短信并保存发码记录-----" + optInfo1.toString());
        } catch (Exception e) {
            sendSuccessFlag = false;
        }
        return sendSuccessFlag;
    }

    @Override
    public List<BatchCodeBvo> getActBatchCodeList(Map<String, Object> batchMap, Integer draw, Integer pageNum, Integer pageSize) {
        if (draw != null && draw != 1) {
            String searchContent = String.valueOf(batchMap.get("search[value]"));
            if (searchContent != null && !"".equals(searchContent)) {
                batchMap.put("searchInfoContent", searchContent);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return batchMapper.getActBatchCodeList(batchMap);
    }

    @Override
    @Transactional
    public Map<String, Object> importCustomor(String activityId, String name, String type, InputStream in) {
        //定义一个返回Map
        Map<String, Object> msgMap = new HashMap<String, Object>();
        Map<String, String> resultMap = new HashMap<String, String>();
        //解析excel流,并生成list
        List<List<Object>> excelList = ExcelUtil.excel2List(in);
        String actBatchId = validatorMobile(activityId, name, type, msgMap, resultMap, excelList);
        Map<String, Object> batchMap = new HashMap<>();
        batchMap.put("batchId", actBatchId);
        batchMap.put("status", BatchStatus.IMPORT_NOT_CARRY.getValue());
        List<BatchCode> batchCodeAllList = new ArrayList<>();
        int pageNum = 1;
        boolean isFinish = false;
        while (!isFinish) {
            PageHelper.startPage(pageNum, pageSize);
            List<BatchCode> batchCodeList = batchCodeMapper.queryBatchCodeList(batchMap);
            if (batchCodeList.isEmpty()) {
                isFinish = true;
            } else {
                batchCodeAllList.addAll(batchCodeList);
                pageNum += 1;
            }
        }
        //更新批次导入数量和成功数量
        Batch batch = new Batch();
        batch.setId(actBatchId);
        batch.setImportNumber(batchCodeAllList.size());
        batch.setImportSuccessNumber(batchCodeAllList.size());
        batch.setImportFailNumber(0);
        batch.setSendNumber(batchCodeAllList.size());
        //记录发送短信和保存发码两个步骤的日志
        StringBuffer optInfo1 = new StringBuffer();
        optInfo1.append("发送数量：" + batchCodeAllList.size() + ";");
        try {
            sendCode(activityId, actBatchId, batchCodeAllList);
            batch.setSendSuccessNumber(batchCodeAllList.size());
            batch.setStatus(BatchStatus.ALREADY_SEND.getValue());
            optInfo1.append("成功数量：" + batchCodeAllList.size() + ";");
            msgMap.put("success", true);
            msgMap.put("msg", "上传并发送成功" + batchCodeAllList.size() + "条");

        } catch (Exception e) {
            batch.setSendSuccessNumber(0);
            batch.setSendFailNumber(batchCodeAllList.size());
            batch.setStatus(BatchStatus.IMPORT_NOT_CARRY.getValue());
            optInfo1.append("失败数量：" + batchCodeAllList.size() + ";");
            msgMap.put("success", false);
            msgMap.put("msg", "发送失败" + batchCodeAllList.size() + "条");
        }
        batchMapper.updateBatch(batch);
        optLogService.addOptLog("活动Id:" + activityId + ",批次Id:" + actBatchId, "", "发送短信并保存发码记录-----" + optInfo1.toString());
        //返回数据
        return msgMap;
    }

    @Transactional()
    public void sendCode(String actId, String batchId, List<BatchCode> batchCodeList) {
        //定义短信内容对象
        MMSBO duanxin = new MMSBO();
        Activity activity = activityService.getActivityById(actId);
        String noticeSmsMsg = activity.getNoticeSmsMsg();
        ;//短信模版内容
        //获取是否推送短信开关
        boolean isPushSms = Boolean.parseBoolean(PropertiesUtil.getValue("isPushSms"));
        if (isPushSms) {
            //如果推送短信开关开启，为短信内容对象设置一些固定信息
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.setDeliveryPersistent(true);
            duanxin.setBarcodeno(3);
            duanxin.setEitemid(1995L);
            duanxin.setEntid(46L);
            duanxin.setIsresend(1);
            duanxin.setMttype(1);
            duanxin.setPackageid(25898810);
            duanxin.setStatus(0);
            duanxin.setStatuscode("NYYH");
            duanxin.setType(0);

            duanxin.setEorderid(5115538L);
            duanxin.setBarcodeid(73349609L);
            duanxin.setCharcode("E1073SCP70");
            duanxin.setNumberpwd("010073787632");
            duanxin.setTid("20150428111444117862");
            //duanxin.setCreatetime(activity.getEndTime());

        }
        if (isPushSms) {
            for (BatchCode batchCode : batchCodeList) {
                duanxin.setMobile(batchCode.getCustomerMobile());
                duanxin.setCustomer(batchCode.getCustomerName());
                if (noticeSmsMsg != null) {
                    String content = noticeSmsMsg.replace("$name", batchCode.getCustomerName()).replace("$pwd", batchCode.getCode());//批量替换
                    duanxin.setContent(content);
                }
                final MMSBO sendMsg = duanxin;
                logger.info("短信内容==电话：" + sendMsg.getMobile() + "名称:" + sendMsg.getCustomer() + "内容：" + sendMsg.getContent());
//                jmsTemplate.send("subject", new MessageCreator() {
//                    public Message createMessage(Session session) throws JMSException {
//                        ObjectMessage msg = session.createObjectMessage(sendMsg);
//                        return msg;
//                    }
//                });

                //batch_code 状态，状态暂定为01：已提码，02：已导入，03：已送码，未发码，04：已发码
                // batch 00：已删除 01：已提码，未导入联系人  02：已导入联系人，待送码  03：已送码，待发码 04：已发码
                Map<String, Object> batchCodeMap = new HashMap<>();
                batchCodeMap.put("id", batchCode.getId());
                batchCodeMap.put("status", BatchCodeStatus.ALREADY_SEND.getValue());
                batchCodeMap.put("startTime", DateUtil.getLongCurDate());
                batchCode.setStartTime(new Date());
                batchCodeMapper.updateBatchCodeById(batchCodeMap);
            }
        }
        //送码
        carryVerifyCode(activity, batchId, batchCodeList);
    }

    /**
     * 验码接口-送码
     *
     * @param act,活动对象
     * @param batchId,批次id
     * @param batchCodeList,活动发码批次下所有的码对象列表
     * @return
     */
    private Map<String, Object> carryVerifyCode(Activity act, String batchId, List<BatchCode> batchCodeList) {
        // 定义发码批次对象，设置上一些基本信息
        BatchVO batchVO = new BatchVO();
        batchVO.setActivityId(act.getId());// 活动Id
        batchVO.setActivityName(act.getName());// 活动名称
        batchVO.setOperType("IMPORT");// 操作编码
        batchVO.setTicketTitle(act.getReceiptTitle());// 活动小票标题
        batchVO.setTicketContent(act.getReceiptPrint());// 活动小票内容
        batchVO.setPosSuccessMsg(act.getPosSuccessMsg());// pos验证成功短信
        batchVO.setSuccessSmsMsg(act.getSuccessSmsMsg());// 验证成功提示
        // 获取并设置关联门店数据
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("activityId", act.getId());
        List<ActivityStore> storeList = activityStoreMapper.queryActivityStoreList(queryMap);
        batchVO.setActActivityStores(storeList);
        // 解析并设置码数据
        List<BatchCodeVO> carryList = new ArrayList<BatchCodeVO>();// 定义送码的列表
        // 遍历活动发码批次下所有的码对象列表
        for (int i = 0; i < batchCodeList.size(); i++) {
            BatchCode oc = batchCodeList.get(i);
            // 组件新的活动批次码对象
            BatchCodeVO codeVO = new BatchCodeVO();
            codeVO.setCodeSerialNumber(String.valueOf(oc.getCodeId()));// 码库提码的id
            codeVO.setCode(String.valueOf(oc.getCode()));// 码库提码的码值
            codeVO.setActivityId(act.getId());// 活动Id
            codeVO.setActivityName(act.getName());// 活动名称
            codeVO.setAmount(String.valueOf(act.getAmount()));// 活动价格
            codeVO.setCreateTime(DateUtil.getLongCurDate());// 创建时间，也就是当前时间
            codeVO.setCustomMobile(oc.getCustomerMobile());// 联系人手机号
            codeVO.setCustomName(oc.getCustomerName());// 联系人名称
            codeVO.setIssueEnterpriseId(act.getEnterpriseId());// 所属活动发行方Id
            codeVO.setIssueEnterpriseName(act.getEnterpriseName());// 所属活动发行方名称
            codeVO.setContractId(act.getContractId());// 合同
            codeVO.setMaxNum(String.valueOf(act.getMaxNumber()));// 最大次数
            codeVO.setOrderId(batchId);// 发码批次Id
            codeVO.setReleaseTime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));// 发布时间，当然时间
            codeVO.setStartTime(DateUtil.dateToStr(oc.getStartTime(), "yyyy-MM-dd HH:mm:ss"));// 活动开始时间
            codeVO.setEndTime(DateUtil.dateToStr(oc.getEndTime(), "yyyy-MM-dd HH:mm:ss"));// 活动结束时间
            codeVO.setSelectDate(act.getSelectDate());// 活动指定日期
            // 将新组建的码对象添加到列表中
            carryList.add(codeVO);
        }
        // 将列表添加到码批次对象中
        batchVO.setActivityCodeList(carryList);
        // carryJson.put("activityCodeList", carryJsonArray);
        // 获取验码系统--送码ＵＲＬ
        String carryCodeUrl = PropertiesUtil.getValue("verifyCode.carryCode");
        // 将要送码的数据JSON化
        String carryMapJson = JSON.toJSONString(batchVO);
        // String carryMapJson = JacksonJsonMapper.objectToJson(carryMap);
        // String carryMapJson= JSON.toJSONString(batchVO);
        // String carryMapJson = carryJson.toJSONString();
        logger.debug("送码，请求参数：" + carryMapJson);
        // 调用Http工具，执行送码操作，并解析返回值
        String carryCodeJson = HttpUtil.doRestfulByHttpConnection(carryCodeUrl, carryMapJson);// 送码
        Map<String, Object> carryCodeMap = JSON.parseObject(carryCodeJson, Map.class);
        logger.debug("送码，返回结果：" + carryCodeJson);
        // 保存验码模块送码的日志
        StringBuffer optInfo = new StringBuffer();
        optInfo.append("请求参数码数量：" + carryList.size() + ";");
        boolean isSuccess = Boolean.parseBoolean(String.valueOf(carryCodeMap.get("success")));
        optInfo.append("请求回应success：" + isSuccess + ";");
        optLogService.addOptLog("活动Id:" + act.getId() + ",批次Id:" + batchId, "",
                "验码接口送码-----" + optInfo.toString());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 将返回对象的success设置为true,并返回数据对象
        resultMap.put("success", true);
        return resultMap;
    }

    public String validatorMobile(String activityId, String name, String type, Map<String, Object> msgMap, Map<String, String> resultMap, List<List<Object>> excelList) {
        String actBatchId = null;
        try {
            if ("01".equals(type)) {
                for (int i = 1; i < excelList.size(); i++) {
                    String customerMobile = excelList.get(i).get(0) + "";
                    String customerName = excelList.get(i).get(1) + "";
                    resultMap.put(customerMobile, customerName);
                    //如果不是正确的手机格式，则返回错误信息
                    if (!ValidatorUtil.isMobile(customerMobile)) {
                        msgMap.put("success", false);
                        msgMap.put("msg", "第" + (i + 1) + "行发生错误，错误原因：不是正确的手机号格式");
                        throw new BusinessException(msgMap.get("msg") + "");
                    }
                }
                actBatchId = activityService.extractedCode(activityId, name, resultMap.size());
                for (String key : resultMap.keySet()) {
                    String customerMobile = key;
                    String customerName = resultMap.get(key);
                    Map<String, Object> batchMap = new HashMap<>();
                    batchMap.put("batchId", actBatchId);
                    batchMap.put("customerMobile", customerMobile);
                    batchMap.put("customerName", customerName);
                    batchCodeMapper.updateBatchCodeByBatchId(batchMap);
                }
            }
            if ("02".equals(type)) {
                actBatchId = activityService.extractedCode(activityId, name, excelList.size() - 1);
                for (int i = 1; i < excelList.size(); i++) {
                    String customerMobile = excelList.get(i).get(0) + "";
                    String customerName = excelList.get(i).get(1) + "";
                    //如果不是正确的手机格式，则返回错误信息
                    if (!ValidatorUtil.isMobile(customerMobile)) {
                        msgMap.put("success", false);
                        msgMap.put("msg", "第" + (i + 1) + "行发生错误，错误原因：不是正确的手机号格式");
                        throw new BusinessException(msgMap.get("msg") + "");
                    }
                    Map<String, Object> batchMap = new HashMap<>();
                    batchMap.put("batchId", actBatchId);
                    batchMap.put("customerMobile", customerMobile);
                    batchMap.put("customerName", customerName);
                    batchCodeMapper.updateBatchCodeByBatchId(batchMap);
                }
            }
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException("请求码库接口失败");
        }
        return actBatchId;
    }
}
