package com.mocredit.gateway.service.impl;

import com.mocredit.base.util.DateUtil;
import com.mocredit.gateway.constant.ActivityStatus;
import com.mocredit.gateway.constant.CardRule;
import com.mocredit.gateway.constant.ErrorCodeType;
import com.mocredit.gateway.constant.TranRecordType;
import com.mocredit.gateway.entity.Activity;
import com.mocredit.gateway.entity.Record;
import com.mocredit.gateway.entity.Response;
import com.mocredit.gateway.entity.TranRecord;
import com.mocredit.gateway.persistence.RecordMapper;
import com.mocredit.gateway.service.ActivityService;
import com.mocredit.gateway.service.RecordService;
import com.mocredit.gateway.service.TranRecordService;
import com.mocredit.gateway.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by ytq on 2015/12/22.
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TranRecordService tranRecordService;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public boolean save(Record record) {
        return false;
    }

    @Override
    @Transactional
    public boolean save(Record record, Response resp) {
        try {
            //获取活动的卡规则
            Activity activity = activityService.getByActivityCode(record.getCode());
            //判读活动是否存在
            if (null == activity) {
                resp.setSuccess(false);
                resp.setErrorCode(ErrorCodeType.ACTIVITY_NOT_EXIST.getValue());
                resp.setErrorMsg(ErrorCodeType.ACTIVITY_NOT_EXIST.getText());
                return false;
            }
//            if (ActivityStatus.STOPPING.equals(activity.getStatus())) {
//                resp.setSuccess(false);
//                resp.setErrorCode(ErrorCodeType.ACTIVITY_ALREADY_STOPPING.getValue());
//                resp.setErrorMsg(ErrorCodeType.ACTIVITY_ALREADY_STOPPING.getText());
//                return false;
//            }
//            if (null == activity.getRule()) {
//                resp.setSuccess(false);
//                resp.setErrorCode(ErrorCodeType.CARD_NOT_RULE.getValue());
//                resp.setErrorMsg(ErrorCodeType.CARD_NOT_RULE.getText());
//                return false;
//            }
            if (null != activity.getRule()) {
                List<TranRecord> tranRecords = tranRecordService.getTranRecordByCardNum(record.getCardNum(), activity.getId());
                if (tranRecords.isEmpty()) {
                    saveRecordAndTranRecord(activity, record);
                } else {
                    for (String rule : activity.getRule().split(";")) {
                        String ruleName = rule.split(":")[0];
                        String ruleValue = rule.split(":")[1];
                        for (TranRecord tranRecord : tranRecords) {
                            if (ruleName.equals(tranRecord.getTranType())) {
                                compareRuleAndMax(ruleName, Integer.valueOf(ruleValue), tranRecord.getTranCount(), resp);
                                if (!resp.getSuccess()) {
                                    return false;
                                }
                            }
                        }
                    }
                    saveRecordAndTranRecord(activity, record);
                }
            }
            return true;
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean savePaymentRevoke(Record record, Response resp) {
        if (null == recordMapper.selectByOrderId(record.getOldOrderId())) {
            resp.setSuccess(false);
            resp.setErrorCode(ErrorCodeType.NOT_EXIST_ORDER_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ORDER_ERROR.getText());
            return false;
        }
        if (recordMapper.selectByOldOrderIdCount(record.getOldOrderId()) == 0) {
            Record oldRecord = recordMapper.selectByOrderId(record.getOldOrderId());
            Activity activity = activityService.getByActivityCode(oldRecord.getCode());
            record.setCode(oldRecord.getCode());
            record.setCardNum(oldRecord.getCardNum());
            record.setCardExpDate(oldRecord.getCardExpDate());
            record.setTranAmt(oldRecord.getTranAmt());
            recordMapper.save(record);
            tranRecordService.minusCountByCardNum(oldRecord.getCardNum(), activity.getId());
            return true;
        } else {
            resp.setSuccess(false);
            resp.setErrorCode(ErrorCodeType.ORDER_ALREADY_REVOKE.getValue());
            resp.setErrorMsg(ErrorCodeType.ORDER_ALREADY_REVOKE.getText());
            return false;
        }
    }

    public void saveRecordAndTranRecord(Activity activity, Record record) {
        recordMapper.save(record);
        for (String tranType : TranRecordType.tranTypes) {
            TranRecord tranRecord = new TranRecord();
            tranRecord.setActivityId(activity.getId());
            tranRecord.setCardNum(record.getCardNum());
            tranRecord.setTranType(tranType);
            tranRecord.setTranCount(1);
            tranRecord.setExpireDate(getExpireDateByTranType(tranType));
            tranRecordService.save(tranRecord);
        }
    }

    public void compareRuleAndMax(String ruleName, Integer ruleValue, Integer currentValue, Response resp) {
        switch (ruleName) {
            case CardRule.CardDayMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.CARD_DAY_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.CARD_DAY_MAX_OUT.getText());
                }
                break;
            case CardRule.CardWeekMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.CARD_WEEK_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.CARD_WEEK_MAX_OUT.getText());
                }
                break;
            case CardRule.CardMonthMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.CARD_MONTH_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.CARD_MONTH_MAX_OUT.getText());
                }
                break;
            case CardRule.CardYearMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.CARD_YEAR_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.CARD_YEAR_MAX_OUT.getText());
                }
                break;
            case CardRule.CardTotalMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.CARD_TOTAL_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.CARD_TOTAL_MAX_OUT.getText());
                }
                break;
        }
    }

    public Date getExpireDateByTranType(String tranType) {
        String expireDate = null;
        switch (tranType) {
            case CardRule.CardDayMax:
                expireDate = DateUtil.getCurDate("yyyy-MM-dd");
                break;
            case CardRule.CardWeekMax:
                expireDate = DateTimeUtils.getMaxDayOfCurrentWeek("yyyy-MM-dd");
                break;
            case CardRule.CardMonthMax:
                expireDate = DateTimeUtils.getMaxDayOfCurrentMonth("yyyy-MM-dd");
                break;
            case CardRule.CardYearMax:
                expireDate = DateTimeUtils.getMaxDayOfCurrentYear("yyyy-MM-dd");
                break;
            case CardRule.CardTotalMax:
                expireDate = "2099-12-31";
                break;
        }
        return DateUtil.strToDate(expireDate, "yyyy-MM-dd");
    }
}
