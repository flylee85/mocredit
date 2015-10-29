package com.mocredit.verifyCode.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.enums.ActivityBlackListsType;
import com.mocredit.base.enums.ErrorCode;
import com.mocredit.base.enums.VerifyCodeStatus;
import com.mocredit.base.util.ActivityCodeUtils;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.TemplateUtil;
import com.mocredit.base.util.UUIDUtils;
import com.mocredit.log.task.SmsSendTask;
import com.mocredit.verifyCode.dao.ActActivityStoreMapper;
import com.mocredit.verifyCode.dao.ActivityCodeBlackListsMapper;
import com.mocredit.verifyCode.dao.ActivityCodeMapper;
import com.mocredit.verifyCode.dao.ActivityInfoMapper;
import com.mocredit.verifyCode.dao.VerifiedCodeMapper;
import com.mocredit.verifyCode.model.ActActivityStore;
import com.mocredit.verifyCode.model.ActivityCodeBlackLists;
import com.mocredit.verifyCode.model.TActivityCode;
import com.mocredit.verifyCode.model.TActivityInfo;
import com.mocredit.verifyCode.model.TVerifiedCode;
import com.mocredit.verifyCode.service.ActivityCodeService;
import com.mocredit.verifyCode.vo.ActActivityCodeVO;
import com.mocredit.verifyCode.vo.SmsVO;

/**
 * Created by YHL on 2015/7/7 13:54 .
 *
 * @author YHL
 * @version 1.0
 * @package com.mocredit.verifyCode.service.impl
 * @email yanghongli@mocredit.cn
 */
@Service
public class ActivityCodeServiceImpl implements ActivityCodeService {

    private Logger logger=Logger.getLogger(ActivityCodeServiceImpl.class.getName());

    @Autowired
    private ActivityCodeMapper acm; //券码mapper

    @Autowired
    private VerifiedCodeMapper vcm; //验码记录mapper

    @Autowired
    private ActActivityStoreMapper actActivityStoreMapper;

    @Autowired
    private ActivityCodeBlackListsMapper activityCodeBlackListsMapper;

    @Autowired
    private ActivityInfoMapper activityInfoMapper;



    public boolean save(TActivityCode activityCode) {
        int count=acm.insertActivityCode(activityCode);
        return count>0?true:false;
    }

    public List<TActivityCode> findByCode(String code) {
        List<TActivityCode> returnList = new ArrayList();
        returnList=acm.findActivityCodeByCode(code);
        return returnList;
    }

    /**
     *  //判断 对当前码的操作是否在进行，如果进行则不操作，如果没有进行，则开始操作码
     *
     * @param amount   要使用的金额 。如果是消费定额的（数据库中存放的金额，则这里为NULL）
     * @param useCount 要使用的次数
     * @param device   机具ID
     * @param store_id 兑换的门店ID
     * @param store_code 门店编码
     * @param store_name 兑换的门店名称
     * @param request_serial_number POS请求的序列号
     * @param shop_id 商户ID
     * @param shop_name 商户名称
     * @param code                   券码
     * @return
     */
    public AjaxResponseData useActivityCodeWithTran(
                                            String device,
                                            String store_id,
                                            String store_code,
                                            String store_name,
                                            String request_serial_number,
                                            String shop_id,
                                            String shop_name,
                                            String code) {
        AjaxResponseData ard=new AjaxResponseData();
        //判断券码的规则合法性
        if( !ActivityCodeUtils.verifyActivityCode(code)){
            ard.setSuccess(false);
            ard.setErrorMsg("券码非法!");
            ard.setErrorCode(ErrorCode.CODE_15.getCode());
            return ard;
        }


        //测试事务代码
//        ActActivityStore aa=new ActActivityStore();
//        aa.setShopId("11111");
//        aa.setActivityId("222");
//        aa.setEnterpriseId("3333");
//        aa.setStoreId("444");
//        int a=actActivityStoreMapper.insert(aa);
//
//        System.out.println("######插入结果："+a);
        /**判断码是否已经使用过**/
        Map<String, Object> param=new HashMap<String, Object>();
        param.put("code", code);
        param.put("verifyType", VerifyCodeStatus.VERIFYCODE.getValue());
		List<TVerifiedCode> verifiedCode = vcm.findVerifiedCodesByCodeAndType(param);
		if(null!=verifiedCode&&verifiedCode.size()>1){
			 ard.setSuccess(false);
             ard.setErrorMsg("当前券码已经使用过!");
             ard.setErrorCode(ErrorCode.CODE_51.getCode());
             return ard ;
		}
        //判断是否在黑名单表中（黑名单表为 删除或者撤销活动的）
        List<ActivityCodeBlackLists> activityCodeBlackListses = this.activityCodeBlackListsMapper.findBlackListsByCode(code);
        if( null!=activityCodeBlackListses && activityCodeBlackListses.size()>0){
            ActivityCodeBlackLists acl=activityCodeBlackListses.get(0);
            ard.setSuccess(false);
            if( acl.getBlacklistsType() == ActivityBlackListsType.LOCKING.getValue()){
                ard.setErrorMsg("当前券码更新锁定中，请稍后重试！");
                ard.setErrorCode(ErrorCode.CODE_98.getCode());
            }else {
                ard.setErrorMsg("该券码号无法使用，原因为：" + acl.getBlacklistsDesc());
                ard.setErrorCode(ErrorCode.CODE_59.getCode());
            }
            return ard;
        }

        //判断是否能查询到该码
        List<TActivityCode> queryList = this.findByCode(code);
        if (null == queryList || queryList.size() < 1) {
            ard.setSuccess(false);
            ard.setErrorMsg("无法根据该券码获取到对应记录!");
            ard.setErrorCode(ErrorCode.CODE_15.getCode());
            return ard;
        }

        TActivityCode activityCode = queryList.get(0);
        List<TActivityInfo> activityInfos=activityInfoMapper.findByActivityId(activityCode.getActivityId());
        TActivityInfo activityInfo = activityInfos.get(0);
        //锁
        TActivityCode lockObj= acm.selectActivityCodeForUpdateById(activityCode.getId());
//        activityCode.setMaxNum(5);
//        acm.updateActivityCode(activityCode);

        //判断券码是否适用当前的门店 ，活动和门店的映射
        List<ActActivityStore> actActivityStores= actActivityStoreMapper.findByActivityId(activityCode.getActivityId());

        //编码不为空，优先适用门店编码获取
        boolean canUse=false;
//        store_code=null;//TODO 暂时store_code不能用
        if( !StringUtils.isEmpty(store_code) ){
            for (ActActivityStore aas : actActivityStores) {
                if (store_code.equals(aas.getStoreCode())) {
                    canUse = true;
                    break;
                }
            }
        }else {
            for (ActActivityStore aas : actActivityStores) {
                if (aas.getStoreId().equals(store_id)) {
                    canUse = true;
                    break;
                }
            }
        }
        if( !canUse ){
            ard.setSuccess(false);
            ard.setErrorMsg("此券码不适用于当前门店!");
            ard.setErrorCode(ErrorCode.CODE_59.getCode());
            return ard;
        }

        canUse=false;
        //增加 券码适用的 选择日期（适用于 星期几）；
        String current_day_of_week = DateUtil.getWeekDayForToday();
        String[] selectDate = activityCode.getSelectDate().split( ActivityCodeUtils.SPLIT_CHAR_COMMA);
        for(String d: selectDate ){
            if( !StringUtils.isEmpty(d) && d.trim().equals(current_day_of_week) ){
                canUse=true;
            }
        }
        if( !canUse ){
            ard.setSuccess(false);
            ard.setErrorMsg("此券码不在适用星期范围内!");
            ard.setErrorCode(ErrorCode.CODE_61.getCode());
            return ard;
        }


        //如果是按照时间判断有效的。
        if (activityCode.isEffective()) { //属于有效的
//            List<TVerifiedCode> verCodes = vcm.findVerifiedCodesByActiveCodeId(activityCode.getId());
//            int nextTimes = null == verCodes ? 1 : verCodes.size() + 1; //存放当前应该为第几次请求。
//
//            int currentUsedTimes = 0; //当前存在几条有效的使用记录（排除冲正与撤销的）
//            BigDecimal currentUsedAmount = new BigDecimal(0.00);
//            boolean isQuotas=true; //用使用次数 大于等于 此约定次数，才认为是 每次减掉使用金额，否则认为是消费次数
//            if( activityCode.getMaxNum() >= Constant.ACTIVITY_MORE_TIMES_NUMBER ){
//                isQuotas=false;
//            }
//            //标示是否可以进行验码核销的数据操作。
//            boolean canVerifyCode = false;
//            int remainTimes = 0; //剩余次数
//            BigDecimal remainAmount = new BigDecimal(0.00); //剩余金额
//
//            Map<String , TVerifiedCode> excludeMap = new HashMap<String, TVerifiedCode>();
//            for (TVerifiedCode obj : verCodes) {
//                //计算金额
//                if ( obj.getVerifyType().equals(VerifyCodeStatus.CORRECT.getValue() )
//                        || obj.getVerifyType().equals(VerifyCodeStatus.REVOKE.getValue() ) ) {
//                   excludeMap.put( obj.getRequestSerialNumber() , obj );
//                }
//            }
//            //遍历数据。排除掉
//            for (TVerifiedCode obj : verCodes) {
//                if(  null != excludeMap.get( obj.getRequestSerialNumber() ) ){
////                    verCodes.remove(obj );
//                    continue;
//                }
//                currentUsedAmount=currentUsedAmount.add( obj.getAmount() );
//                currentUsedTimes=currentUsedTimes+1;
//                logger.debug("#### 计算使用金额：" + currentUsedAmount +" \t #### 计算使用的次数：" + currentUsedTimes);
//            }
//            logger.debug("###### 最终计算：有效使用次数=" + currentUsedTimes + " ; 有效的使用金额=" + currentUsedAmount);
////            for (TVerifiedCode obj : verCodes) {
////                //计算金额
////                if (obj.getVerifyType().equals(VerifyCodeStatus.CORRECT.getValue())
////                        || obj.getVerifyType().equals(VerifyCodeStatus.REVOKE.getValue())) {
////                    currentUsedAmount = currentUsedAmount.subtract(obj.getAmount());
////                } else if (obj.getVerifyType().equals(VerifyCodeStatus.VERIFYCODE.getValue())) {
////                    currentUsedAmount = currentUsedAmount.add(obj.getAmount());
////                }
////                logger.debug("#### 计算使用金额：" + currentUsedAmount);
////                //计算次数
////                if (obj.getVerifyType().equals(VerifyCodeStatus.CORRECT.getValue())
////                        || obj.getVerifyType().equals(VerifyCodeStatus.REVOKE.getValue())) {
////                    currentUsedTimes -= 1;
////                }
////                logger.debug("#### 计算使用的次数：" + currentUsedTimes);
////            }
////            logger.debug("###### 最终计算：有效使用次数=" + currentUsedTimes + " ; 有效的使用金额=" + currentUsedAmount);
//             /*
//                    判断是否可以继续进行验码核销的保存数据操作
//                        -- 无限次
//                        -- 次数以及剩余金额满足 当前的核销条件（次数与金额）
//              */
//
//            remainTimes = activityCode.getMaxNum() - currentUsedTimes;
//            //定额消费，只判断次数
//            if( isQuotas ){
//                if ( remainTimes >= useCount  ) {
//                    canVerifyCode = true;
//                }
//                //修改一下 使用固定额度的优惠券，剩余金额和使用金额，用于信息通知
//                amount=activityCode.getAmount();
//                remainAmount= amount.multiply(new BigDecimal( remainTimes) );
//
//            }else{
//
//                remainAmount = activityCode.getAmount().subtract(currentUsedAmount).subtract(amount.multiply(new BigDecimal(useCount))); // 此次使用次数乘以金额
//                if (activityCode.getMaxNum() >= Constant.ACTIVITY_MORE_TIMES_NUMBER
//                        || (remainTimes >= useCount && remainAmount.compareTo(new BigDecimal(0.00)) > -1)) {
//                    canVerifyCode = true;
//                }
//            }
//
//
//            logger.debug("######## 判断是否可以继续核销：" + canVerifyCode + " \r 剩余次数=" + remainTimes + " ;剩余金额=" + remainAmount);

//            if (canVerifyCode) {
                //根据此次使用的次数，进行遍历保存验码核销的记录
                TVerifiedCode tvc = new TVerifiedCode();
                tvc.setId(UUIDUtils.UUID32());
                tvc.setCode(activityCode.getCode());
                tvc.setCodeSerialNumber(activityCode.getId());
                tvc.setVerifyTime(new Date());
                tvc.setActivityId(activityCode.getActivityId());
                tvc.setActivityName(activityCode.getActivityName());
//                tvc.setAmount(amount);
                tvc.setDevice(device); //机具id
                tvc.setStoreId(store_id); //门店ID
                tvc.setStartTime(activityCode.getStartTime());
                tvc.setEndTime(activityCode.getEndTime());
                tvc.setNumber(1); //一个码固定只能使用一次
                tvc.setRequestSerialNumber(request_serial_number); //POS请求序列号
                tvc.setIssueEnterpriseId(activityCode.getIssueEnterpriseId());
                tvc.setIssueEnterpriseName(activityCode.getIssueEnterpriseName());
                tvc.setShopId(shop_id); //执行企业的编号
                tvc.setShopName(shop_name);
                tvc.setVerifyType(VerifyCodeStatus.VERIFYCODE.getValue()); //设置状态为验码核销
                tvc.setContractId(activityCode.getContractId());
                int count = vcm.insertVerifiedCode(tvc);
                logger.debug("使用记录:" + count);
                //构建用于替换模板的数据
                Map<TemplateUtil.TemplateField,String> dataMap = new HashMap();
                dataMap.put(TemplateUtil.TemplateField.STORE_NAME ,store_name);
                dataMap.put(TemplateUtil.TemplateField.STORE_CODE ,store_id);
                dataMap.put(TemplateUtil.TemplateField.DEVICE_CODE ,device);
                dataMap.put(TemplateUtil.TemplateField.SHOP_NAME ,shop_name);
                dataMap.put(TemplateUtil.TemplateField.SHOP_CODE ,shop_id);
                dataMap.put(TemplateUtil.TemplateField.ACTIVITY_NAME ,activityCode.getActivityName());
                dataMap.put(TemplateUtil.TemplateField.CODE ,activityCode.getCode());
//                dataMap.put(TemplateUtil.TemplateField.AMOUNT ,String.valueOf( amount.setScale(2,BigDecimal.ROUND_HALF_UP) ));


                //操作成功，放入数据用于返回、
                Map m = new HashMap();
//                m.put("remainTimes", remainTimes);
//                m.put("remainAmount", remainAmount);
//                m.put("useCount", useCount);
//                m.put("amount", amount);
                m.put("activityId",activityCode.getActivityId());
                m.put("activityName",activityCode.getActivityName());

                m.put("startTime",activityCode.getStartTime());
                m.put("endTime",activityCode.getEndTime() );
                m.put("issueEnterpriseId",activityCode.getIssueEnterpriseId());
                m.put("issueEnterpriseName",activityCode.getIssueEnterpriseName());
                m.put("ticketTitle",activityInfo.getTicketTitle());
                m.put("ticketContent", TemplateUtil.buildStringFromTemplate(dataMap,activityInfo.getTicketContent() ) );
                ard.setSuccess(true);
                ard.setData(m);
                ard.setRedundancyData(activityCode);

                SmsVO s = new SmsVO();
//                s.setAmount( amount.multiply( new BigDecimal(useCount)).setScale(2, BigDecimal.ROUND_HALF_UP) );
                s.setCustormMobile( activityCode.getCustomMobile() );
                s.setCustormName("");
                s.setCode(activityCode.getCode());
                s.setTemplateString(activityInfo.getSuccessSmsMsg());

                SmsSendTask.smsList.add(s);
//            } else {
//                ard.setSuccess(false);
//                if (remainTimes < useCount) {
//                    ard.setErrorMsg("当前券码可使用次数不足!");
//                    ard.setErrorCode(ErrorCode.CODE_51.getCode());
//                    return ard ;
//                }
//                if (remainAmount.compareTo(new BigDecimal(0.00)) < 0) {
//                    ard.setErrorMsg("当前券码可用余额不足!");
//                    ard.setErrorCode(ErrorCode.CODE_51.getCode());
//                    return ard;
//                }
//            }
        } else {
            ard.setSuccess(false);
            ard.setErrorMsg("当前使用时间不在有效的活动时间范围内!");
            ard.setErrorCode(ErrorCode.CODE_60.getCode());
        }


        return ard;
    }

    public AjaxResponseData correctActivityCodeWithTran(String request_serial_number,String code,String device ,VerifyCodeStatus verifyCodeStatus) {
        AjaxResponseData ard =new AjaxResponseData();

        //判断是否属于冲正与撤销
        if( verifyCodeStatus.getValue()!=VerifyCodeStatus.REVOKE.getValue() && verifyCodeStatus.getValue() != VerifyCodeStatus.CORRECT.getValue() ){
            ard.setSuccess(false);
            ard.setErrorMsg("非法类型请求操作：当前操作不属于冲正或者撤销！");
            ard.setErrorCode(ErrorCode.CODE_51.getCode());
            return ard;
        }
        //获取对应的
        Date date = new Date();
        List<TVerifiedCode> findCodes=this.vcm.findVerifiedCodesByDeviceAndRequestSerialNumber(device,request_serial_number , date);
        logger.debug("根据POS与券码获取到验证记录数：" + findCodes.size());
        if( null!=findCodes && findCodes.size()>0){
            TVerifiedCode verifiedCode= findCodes.get(0);
            TActivityCode activityCode=this.acm.findActivityCodeById( verifiedCode.getCodeSerialNumber());
            List<TActivityInfo> activityInfos=activityInfoMapper.findByActivityId(activityCode.getActivityId());
            TActivityInfo activityInfo = activityInfos.get(0);
            //判断是否做过此次冲正
            for( TVerifiedCode v : findCodes ){
                if( v.getNumber()<0){
                    ard.setSuccess(false);
                    ard.setErrorMsg("已经存在冲正记录！");
                    ard.setErrorCode(ErrorCode.CODE_94.getCode());
                    return ard;
                }
            }

            // 验码核销存在 按照次数 或者 金额 核销。使用数量与金额不会同时使用
            //遍历取得的列表。如果按照次数，则是多条（金额为0）。如果是金额（如果按照金额，应该是一条。）则设置金额即可。
            List<TVerifiedCode> verifiedCodes=findCodes;
            for( TVerifiedCode v:verifiedCodes ){
                TVerifiedCode newV=new TVerifiedCode();
                newV.setActivityId(v.getActivityId());
                newV.setActivityName(v.getActivityName());
                newV.setAmount(v.getAmount());
                newV.setCodeSerialNumber(v.getCodeSerialNumber());
                newV.setCode(v.getCode());
                newV.setDevice(v.getDevice());
                newV.setShopId(v.getShopId());
                newV.setShopName(v.getShopName());
                newV.setEndTime(activityCode.getEndTime()); //
                newV.setIssueEnterpriseName(v.getIssueEnterpriseName());
                newV.setIssueEnterpriseId(v.getIssueEnterpriseId());
                newV.setId(UUIDUtils.UUID32());
                newV.setNumber(-1); //根据数据库设计，撤销和冲正填写 负数
                newV.setRequestSerialNumber(v.getRequestSerialNumber());
                newV.setStartTime(activityCode.getStartTime());
                newV.setStoreId(v.getStoreId());
                newV.setStoreName(v.getStoreName());
                newV.setVerifyType( verifyCodeStatus.getValue());
                newV.setVerifyTime( new Date());
                this.vcm.insertVerifiedCode(newV );
            }
            ard.setSuccess(true);
            Map m=new HashMap();
            m.put("activityId",activityCode.getActivityId());
            m.put("activityName",activityCode.getActivityName());
            m.put("startTime",activityCode.getStartTime());
            m.put("endTime",activityCode.getEndTime() );
            m.put("issueEnterpriseId",activityCode.getIssueEnterpriseId());
            m.put("issueEnterpriseName",activityCode.getIssueEnterpriseName());
            m.put("ticketTitle",activityInfo.getTicketTitle());
            ard.setData(m);
        }else{
            ard.setSuccess(false);
            ard.setErrorMsg("无法获取相应验码信息！");
            ard.setErrorCode(ErrorCode.CODE_25.getCode());
        }
/*
        if( null!=findCodes && findCodes.size()>0){
            TActivityCode activityCode=findCodes.get(0);

            // 验码核销存在 按照次数 或者 金额 核销。使用数量与金额不会同时使用
            //遍历取得的列表。如果按照次数，则是多条（金额为0）。如果是金额（如果按照金额，应该是一条。）则设置金额即可。
            List<TVerifiedCode> verifiedCodes=this.vcm.findVerifiedCodesByCodeAndRequestSerialNumber(code,request_serial_number);
            for( TVerifiedCode v:verifiedCodes ){
                TVerifiedCode newV=new TVerifiedCode();
                newV.setActivityId(v.getActivityId());
                newV.setActivityName(v.getActivityName());
                newV.setAmount(v.getAmount());
                newV.setCodeSerialNumber(v.getCodeSerialNumber());
                newV.setCode(v.getCode());
                newV.setDevice(v.getDevice());
                newV.setShopId(v.getShopId());
                newV.setShopName(v.getShopName());
                newV.setEndTime(activityCode.getEndTime()); //
                newV.setIssueEnterpriseName(v.getIssueEnterpriseName());
                newV.setIssueEnterpriseId(v.getIssueEnterpriseId());
                newV.setId(UUIDUtils.UUID32());
                newV.setNumber(-1); //根据数据库设计，撤销和冲正填写 负数
                newV.setRequestSerialNumber(v.getRequestSerialNumber());
                newV.setStartTime(activityCode.getStartTime());
                newV.setStoreId(v.getStoreId());
                newV.setStoreName(v.getStoreName());
                newV.setVerifyType( verifyCodeStatus.getValue());
                newV.setVerifyTime( new Date());
                this.vcm.insertVerifiedCode(newV );
            }
            ard.setSuccess(true);
            Map m=new HashMap();
            m.put("activityId",activityCode.getActivityId());
            m.put("activityName",activityCode.getActivityName());
            m.put("startTime",activityCode.getStartTime());
            m.put("endTime",activityCode.getEndTime() );
            m.put("issueEnterpriseId",activityCode.getIssueEnterpriseId());
            m.put("issueEnterpriseName",activityCode.getIssueEnterpriseName());
            ard.setData(m);
        }else{
            ard.setSuccess(false);
            ard.setErrorMsg("无法根据券码获取相应信息！");
            ard.setErrorCode(ErrorCode.CODE_15.getCode());
        }
  */
        return ard;
    }

    public AjaxResponseData importActActivityCodeWithTran(ActActivityCodeVO actActivityCodeVO) {
        AjaxResponseData ard =new AjaxResponseData();

        //保存活动与门店
        this.actActivityStoreMapper.batchSave( actActivityCodeVO.getActActivityStores() );
        List<TActivityCode> newCodes=new ArrayList<TActivityCode>();
        for(TActivityCode c :actActivityCodeVO.getActivityCodeList()){
            c.setId(UUIDUtils.UUID32());
            newCodes.add(c);
        }
        this.acm.batchSave(newCodes);
        //保存附属信息
        activityInfoMapper.deleteByActivityId(actActivityCodeVO.getActivityId() );
        TActivityInfo activityInfo =new TActivityInfo();
        activityInfo.setId(UUIDUtils.UUID32());
        activityInfo.setActivityId(actActivityCodeVO.getActivityId());
        activityInfo.setTicketTitle(actActivityCodeVO.getTicketTitle());
        activityInfo.setTicketContent(actActivityCodeVO.getTicketContent());
        activityInfo.setPosSuccessMsg(actActivityCodeVO.getPosSuccessMsg());
        activityInfo.setSuccessSmsMsg(actActivityCodeVO.getSuccessSmsMsg());
        activityInfoMapper.save(activityInfo);

        ard.setSuccess(true);
        return ard;
    }

    public AjaxResponseData deleteActActivityCodeWithTran(String activity_id) {
        AjaxResponseData ard = new AjaxResponseData();

        int c = activityCodeBlackListsMapper.addBlackListsByActivityId(activity_id, ActivityBlackListsType.CANCEL.getValue(),ActivityBlackListsType.CANCEL.getText() );
        if( c>=0){
            ard.setSuccess(true);
            ard.setErrorMsg("");
        }else{
            ard.setSuccess(false);
            ard.setErrorMsg("码删除失败!");
            ard.setErrorCode(ErrorCode.CODE_99.getCode());
        }
        return ard;
    }

    public AjaxResponseData updateActActivityWithTran(ActActivityCodeVO actActivityCodeVO) {
        AjaxResponseData ard = new AjaxResponseData();

        //1.临时加入黑名单，状态为锁定
        this.activityCodeBlackListsMapper.addBlackListsByActivityId(actActivityCodeVO.getActivityId(),
                ActivityBlackListsType.LOCKING.getValue(),
                ActivityBlackListsType.LOCKING.getText()
                );
        //2.更新 券码表中的数据
        int update_code_c = this.acm.updateActActivity(  actActivityCodeVO.getActivityId(),
                                                    actActivityCodeVO.getActivityName(),
                                                    actActivityCodeVO.getEnterpriseId(),
                                                    actActivityCodeVO.getEnterpriseName(),
                                                    actActivityCodeVO.getContractId(),
                                                    actActivityCodeVO.getAmount(),
                                                    actActivityCodeVO.getStartTime(),
                                                    actActivityCodeVO.getEndTime(),
                                                    actActivityCodeVO.getSelectDate(),
                                                    actActivityCodeVO.getMaxNumber()
                                                );
        //3.更新活动关联的门店
        int delete_store_c = this.actActivityStoreMapper.deleteByActivityId(actActivityCodeVO.getActivityId());
        int insert_store_c = this.actActivityStoreMapper.batchSave(actActivityCodeVO.getActActivityStores());
        //4.解除黑名单的锁定
        int delete_blacklist_c = this.activityCodeBlackListsMapper.deleteBlackListsByActivityId( actActivityCodeVO.getActivityId() );

        //5更新活动附属信息
        activityInfoMapper.deleteByActivityId(actActivityCodeVO.getActivityId() );
        TActivityInfo activityInfo =new TActivityInfo();
        activityInfo.setId(UUIDUtils.UUID32());
        activityInfo.setActivityId(actActivityCodeVO.getActivityId());
        activityInfo.setTicketTitle(actActivityCodeVO.getTicketTitle());
        activityInfo.setTicketContent(actActivityCodeVO.getTicketContent());
        activityInfo.setPosSuccessMsg(actActivityCodeVO.getPosSuccessMsg());
        activityInfo.setSuccessSmsMsg(actActivityCodeVO.getSuccessSmsMsg());
        activityInfoMapper.save(activityInfo);

        ard.setSuccess(true);
        return ard;
    }

    public AjaxResponseData startUsingActivityWithTran(String activity_id) {
        AjaxResponseData ard = new AjaxResponseData();

        this.activityCodeBlackListsMapper.deleteBlackListsByActivityId(activity_id);
        ard.setSuccess(true);
        return ard;
    }
}
