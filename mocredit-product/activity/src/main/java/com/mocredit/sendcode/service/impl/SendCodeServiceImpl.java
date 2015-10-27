package com.mocredit.sendcode.service.impl;

import com.mocredit.activity.model.*;
import com.mocredit.activity.persitence.BatchCodeMapper;
import com.mocredit.activity.persitence.BatchMapper;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.util.*;
import com.mocredit.common.MMSBO;
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
import javax.servlet.http.HttpSession;
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

    @Override
    public List<BatchCode> downloadList(String type, String name, String id, Integer codeCount) {
        //    CODE("01", "验码"), BATCH("02", "批次"), ACTIVITY("03", "活动");
        Map<String, Object> batchMap = new HashMap<>();
        if (DownloadType.ACTIVITY.getValue().equals(type)) {
            String batchId = activityService.extractedCode(id, name, codeCount);
            batchMap.put("batchId", batchId);
        }
        if (DownloadType.BATCH.getValue().equals(type)) {
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
        batchMap.put("status", "00");
        return batchMapper.delBatchById(batchMap) > 0;
    }

    @Override
    public boolean sendCodeById(String actId, String id) {
        try {
            List<BatchCode> batchCodeAllList = new ArrayList<>();
            batchCodeAllList.add(batchCodeMapper.getBatchCodeById(id));
            sendCode(actId, batchCodeAllList);
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
        try {
            Map<String, Object> batchMap = new HashMap<>();
            batchMap.put("batchId", batchId);
            batchMap.put("status", "02");
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
            sendCode(actId, batchCodeAllList);
            //更新批次导入数量和成功数量
            Batch batch = new Batch();
            batch.setId(batchId);
            batch.setImportFailNumber(batchCodeAllList.size());
            batch.setImportSuccessNumber(batchCodeAllList.size());
            batch.setStatus("04");
            batchMapper.updateBatch(batch);
            //记录发送短信和保存发码两个步骤的日志
            StringBuffer optInfo1 = new StringBuffer();
            optInfo1.append("发送数量：" + batchCodeAllList.size() + ";");
            optLogService.addOptLog("活动Id:" + actId + ",批次Id:" + batchId, "", "发送短信并保存发码记录-----" + optInfo1.toString());
            return true;

        } catch (Exception e) {
            return false;
        }
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
        //获取标题和字段在excel中的对应关系，并存放在一个Map中
        List<Object> titleList = excelList.get(0);
        String actBatchId = null;
        Integer importNumber = null;
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
            importNumber = resultMap.size();
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
            importNumber = excelList.size() - 1;
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
        Map<String, Object> batchMap = new HashMap<>();
        batchMap.put("batchId", actBatchId);
        batchMap.put("status", "02");
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
        sendCode(activityId, batchCodeAllList);
        //更新批次导入数量和成功数量
        Batch batch = new Batch();
        batch.setId(actBatchId);
        batch.setImportFailNumber(importNumber);
        batch.setImportSuccessNumber(importNumber);
        batch.setStatus("04");
        batchMapper.updateBatch(batch);
        //记录发送短信和保存发码两个步骤的日志
        StringBuffer optInfo1 = new StringBuffer();
        optInfo1.append("发送数量：" + batchCodeAllList.size() + ";");
        optLogService.addOptLog("活动Id:" + activityId + ",批次Id:" + actBatchId, "", "发送短信并保存发码记录-----" + optInfo1.toString());

        //返回数据
        msgMap.put("success", true);
        msgMap.put("msg", "上传成功" + batchCodeAllList.size() + "条");
        msgMap.put("successNumber", batchCodeAllList.size());//成功数
        msgMap.put("importNumber", batchCodeAllList.size());//导入数量
        return msgMap;
    }

    @Transactional()
    public void sendCode(String actId, List<BatchCode> batchCodeList) {
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
        for (BatchCode batchCode : batchCodeList) {
            duanxin.setMobile(batchCode.getCustomerMobile());
            String content = noticeSmsMsg.replace("$name", batchCode.getCustomerName()).replace("$pwd", batchCode.getCode());//批量替换
            duanxin.setContent(content);
            final MMSBO sendMsg = duanxin;
            logger.info("短信内容==电话：" + sendMsg.getMobile() + "名称:" + sendMsg.getCustomer() + "内容：" + sendMsg.getContent());
//            jmsTemplate.send("subject", new MessageCreator() {
//                public Message createMessage(Session session) throws JMSException {
//                    ObjectMessage msg = session.createObjectMessage(sendMsg);
//                    return msg;
//                }
//            });

            //batch_code 状态，状态暂定为01：已提码，02：已导入，03：已送码，未发码，04：已发码
            // batch 00：已删除 01：已提码，未导入联系人  02：已导入联系人，待送码  03：已送码，待发码 04：已发码
            Map<String, Object> batchCodeMap = new HashMap<>();
            batchCodeMap.put("id", batchCode.getId());
            batchCodeMap.put("status", "04");
            batchCodeMapper.updateBatchCodeById(batchCodeMap);
        }
    }
}
