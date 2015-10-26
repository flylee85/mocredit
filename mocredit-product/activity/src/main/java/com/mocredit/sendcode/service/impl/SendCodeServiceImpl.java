package com.mocredit.sendcode.service.impl;

import com.mocredit.activity.model.Activity;
import com.mocredit.activity.model.Batch;
import com.mocredit.activity.model.BatchBvo;
import com.mocredit.activity.model.BatchCode;
import com.mocredit.activity.persitence.BatchCodeMapper;
import com.mocredit.activity.persitence.BatchMapper;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.util.*;
import com.mocredit.sendcode.service.SendCodeService;
import com.mocredit.sys.service.OptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<String> downloadList(String type, String id, Integer codeCount) {
        List<String> a = new ArrayList<>();
        a.add("aa");
        a.add("bb");
        return a;
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
    public Map<String, Object> importCustomor(String activityId, String batchId, String name, String type, InputStream in) {
        //定义一个返回Map
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //解析excel流,并生成list
        List<List<Object>> excelList = ExcelUtil.excel2List(in);

        //获取标题和字段在excel中的对应关系，并存放在一个Map中
        List<Object> titleList = excelList.get(0);
        Map<String, Integer> titleColumnMap = new HashMap<String, Integer>();
        for (int i = 0; i < titleList.size(); i++) {
            String titleStr = String.valueOf(titleList.get(i));
            if ("手机号".equals(titleStr)) {
                titleColumnMap.put("mobile", i);
            } else if ("姓名".equals(titleStr)) {
                titleColumnMap.put("name", i);
            }
        }
        //遍历每一条数据，根据上一段代码中获取到的对应关系，获取手机号，并验证是否为正确的手机格式，如果是非法格式，则返回错误
        for (int i = 1; i < excelList.size(); i++) {
            List<Object> oneRowList = excelList.get(i);
            String mobile = String.valueOf(oneRowList.get(titleColumnMap.get("mobile")));
            //如果不是正确的手机格式，则返回错误信息
            if (!ValidatorUtil.isMobile(mobile)) {
                resultMap.put("success", false);
                resultMap.put("msg", "第" + (i + 1) + "行发生错误，错误原因：不是正确的手机号格式");
                return resultMap;
            }
        }

	    /*
         * 遍历每一条数据，执行批量添加方法
	     */
        //获取一次添加数量
        Integer batchInsertCount = Integer.valueOf(PropertiesUtil.getValue("activity.batchInsertCount"));
        Integer successNumber = 0;
        //定义一个空的列表，用于临时存放联系人数据，当列表数量达到一定数量时，就批量添加数据。
        List<BatchCode> batchOrderCodeList = new ArrayList<BatchCode>();
        //遍历列表，将列表中的数据拼装后放入到临时列表中
        for (int i = 1; i < excelList.size(); i++) {
            List<Object> oneRowList = excelList.get(i);
            String mobile = String.valueOf(oneRowList.get(titleColumnMap.get("mobile")));
            String n = String.valueOf(oneRowList.get(titleColumnMap.get("name")));

            BatchCode oc = new BatchCode();
            oc.setCustomerName(n);
            oc.setCustomerMobile(mobile);
            oc.setId(IDUtil.getID());
            oc.setOrderId(batchId);
            oc.setStatus("01");//01：已导入，未提码

            batchOrderCodeList.add(oc);

            //当该数量达到一定的数量时，批量添加数据，批量添加完成后，将临时存储数据的列表置空
            if (i != 0 && i % batchInsertCount == 0) {
                //批量添加批次码数据
                successNumber += batchCodeMapper.batchAddBatchCode(batchOrderCodeList);
                batchOrderCodeList = new ArrayList<BatchCode>();
            }
        }
        //添加剩余的批次码数据
        successNumber += batchCodeMapper.batchAddBatchCode(batchOrderCodeList);

        //保存导入联系人批次数据
        Batch od = new Batch();
        od.setId(batchId);
        od.setActivityId(activityId);
        od.setBatch("");
        od.setImportNumber(successNumber);//导入联系人数量
        od.setImportSuccessNumber(successNumber);//导入成功数量
//        od.setRemark(remark);//备注
        od.setStatus("01");//导入状态
        od.setCreatetime(new Date());
        batchMapper.addBatch(od);

        //保存日志
        StringBuffer optInfo = new StringBuffer();
        optInfo.append("批次：" + od.getBatch() + ";导入数量:" + od.getImportNumber() + ";导入成功数量:" + od.getImportSuccessNumber() + ";备注：" + od.getRemark());
        optLogService.addOptLog("活动Id:" + activityId + ",批次Id:" + batchId, "", "导入联系人-----" + optInfo.toString());

        //返回数据
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功" + successNumber + "条");
        resultMap.put("successNumber", successNumber);//成功数
        resultMap.put("importNumber", successNumber);//导入数量
        return resultMap;
    }
}
