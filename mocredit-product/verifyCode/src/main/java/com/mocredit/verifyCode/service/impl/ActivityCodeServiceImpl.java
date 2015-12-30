package com.mocredit.verifyCode.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.enums.ActivityBlackListsType;
import com.mocredit.base.enums.ActivityCodeStatus;
import com.mocredit.base.enums.ErrorCode;
import com.mocredit.base.enums.ExchangeChannel;
import com.mocredit.base.enums.VerifyCodeStatus;
import com.mocredit.base.enums.VerifyLogCode;
import com.mocredit.base.util.ActivityCodeUtils;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.base.util.TemplateUtil;
import com.mocredit.base.util.TemplateUtil.TemplateField;
import com.mocredit.base.util.UUIDUtils;
import com.mocredit.log.task.SmsSendTask;
import com.mocredit.verifyCode.dao.ActActivityStoreMapper;
import com.mocredit.verifyCode.dao.ActivityCodeBlackListsMapper;
import com.mocredit.verifyCode.dao.ActivityCodeMapper;
import com.mocredit.verifyCode.dao.ActivityInfoMapper;
import com.mocredit.verifyCode.dao.VerifiedCodeMapper;
import com.mocredit.verifyCode.model.ActActivityStore;
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

	private Logger logger = Logger.getLogger(ActivityCodeServiceImpl.class.getName());

	@Autowired
	private ActivityCodeMapper acm; // 券码mapper

	@Autowired
	private VerifiedCodeMapper vcm; // 验码记录mapper

	@Autowired
	private ActActivityStoreMapper actActivityStoreMapper;

	@Autowired
	private ActivityCodeBlackListsMapper activityCodeBlackListsMapper;

	@Autowired
	private ActivityInfoMapper activityInfoMapper;

	public boolean save(TActivityCode activityCode) {
		int count = acm.insertActivityCode(activityCode);
		return count > 0 ? true : false;
	}

	public List<TActivityCode> findByCode(String code) {
		List<TActivityCode> returnList = new ArrayList<TActivityCode>();
		returnList = acm.findActivityCodeByCode(code);
		return returnList;
	}

	/**
	 * //判断 对当前码的操作是否在进行，如果进行则不操作，如果没有进行，则开始操作码
	 *
	 * @param amount
	 *            要使用的金额 。如果是消费定额的（数据库中存放的金额，则这里为NULL）
	 * @param useCount
	 *            要使用的次数
	 * @param device
	 *            机具ID
	 * @param store_id
	 *            兑换的门店ID
	 * @param store_code
	 *            门店编码
	 * @param request_serial_number
	 *            POS请求的序列号
	 * @param code
	 *            券码
	 * @return
	 */
	public AjaxResponseData verifyCodeForNewPos(String device, String request_serial_number, String code) {
		AjaxResponseData ard = new AjaxResponseData();
		Object[] verifyCode = verifyCode(ard, code, device, request_serial_number, true, ExchangeChannel.POS);

		if (null == verifyCode) {
			return ard;
		}
		TActivityCode activityCode = (TActivityCode) verifyCode[0];
		ActActivityStore activityStore = (ActActivityStore) verifyCode[1];
		TActivityInfo activityInfo = (TActivityInfo) verifyCode[2];
		// 如果是按照时间判断有效的。
		if (activityCode != null) { // 属于有效的

			Map<TemplateUtil.TemplateField, String> dataMap = initTemplateVar(device, request_serial_number, code,
					activityCode, activityStore);
					// dataMap.put(TemplateUtil.TemplateField.AMOUNT
					// ,String.valueOf(
					// amount.setScale(2,BigDecimal.ROUND_HALF_UP) ));

			// 操作成功，放入数据用于返回、
			Map<String, Object> m = new HashMap<String, Object>();
			// m.put("remainTimes", remainTimes);
			// m.put("remainAmount", remainAmount);
			// m.put("useCount", useCount);
			// m.put("amount", amount);
			m.put("activityId", activityCode.getActivityId());
			m.put("activityName", activityCode.getActivityName());

			m.put("startTime", activityCode.getStartTime());
			m.put("endTime", activityCode.getEndTime());
			m.put("issueEnterpriseId", activityCode.getIssueEnterpriseId());
			m.put("issueEnterpriseName", activityCode.getIssueEnterpriseName());
			m.put("ticketTitle", activityInfo.getTicketTitle());
			// 票据模板转换
			m.put("ticketContent", TemplateUtil.buildStringFromTemplate(dataMap, activityInfo.getTicketContent()));
			// POS显示信息模板转换
			m.put("posSuccessMsg", TemplateUtil.buildStringFromTemplate(dataMap, activityInfo.getPosSuccessMsg()));
			ard.setSuccess(true);
			ard.setData(m);
			ard.setRedundancyData(activityCode);

			SmsVO s = new SmsVO();
			// s.setAmount( amount.multiply( new
			// BigDecimal(useCount)).setScale(2, BigDecimal.ROUND_HALF_UP) );
			s.setCustormMobile(activityCode.getCustomMobile());
			s.setCustormName("");
			s.setCode(activityCode.getCode());
			// 短信模板转换
			s.setTemplateString(TemplateUtil.buildStringFromTemplate(dataMap, activityInfo.getSuccessSmsMsg()));

			SmsSendTask.smsList.add(s);

			/* 同步到订单接口 开始 */
			// String sysOrderUrl = PropertiesUtil.getValue("syn.synOrderInfo");
			// Map<String, String> httpPostMap = new HashMap<String,String>();
			// httpPostMap.put("orderId", request_serial_number);
			// httpPostMap.put("storeId", activityStore.getStoreId());
			// httpPostMap.put("storeName", activityStore.getStoreName());
			// httpPostMap.put("timestamp",
			// DateUtil.dateToStr(tvc.getVerifyTime(), "yyyyMMddHHmmss"));
			// httpPostMap.put("code", code);
			// httpPostMap.put("activityId", activityCode.getActivityId());
			// HttpUtil.doRestfulByHttpConnection(sysOrderUrl,
			// JSON.toJSONString(httpPostMap));
			/* 同步到订单接口 结束 */
		}
		ard.setRedundancyData(activityCode);
		return ard;
	}

	/**
	 * 初始化模板变量
	 * 
	 * @param device
	 * @param request_serial_number
	 * @param code
	 * @param activityCode
	 * @param activityStore
	 * @return
	 */
	private Map<TemplateUtil.TemplateField, String> initTemplateVar(String device, String request_serial_number,
			String code, TActivityCode activityCode, ActActivityStore activityStore) {
		// 构建用于替换模板的数据
		Map<TemplateUtil.TemplateField, String> dataMap = new HashMap<TemplateField, String>();
		dataMap.put(TemplateUtil.TemplateField.STORE_NAME, activityStore.getStoreName());
		dataMap.put(TemplateUtil.TemplateField.STORE_CODE, activityStore.getStoreCode());
		dataMap.put(TemplateUtil.TemplateField.DEVICE_CODE, device);
		dataMap.put(TemplateUtil.TemplateField.SHOP_NAME, activityStore.getShopName());
		dataMap.put(TemplateUtil.TemplateField.SHOP_CODE, activityStore.getShopCode());
		dataMap.put(TemplateUtil.TemplateField.ACTIVITY_NAME, activityCode.getActivityName());
		dataMap.put(TemplateUtil.TemplateField.CODE, code);
		dataMap.put(TemplateUtil.TemplateField.AMOUNT, String.valueOf(activityCode.getAmount()));
		dataMap.put(TemplateUtil.TemplateField.CODE_SERIAL_NUMBER, request_serial_number);
		dataMap.put(TemplateUtil.TemplateField.CUSTOM_MOBILE, activityCode.getCustomMobile());
		dataMap.put(TemplateUtil.TemplateField.CUSTOM_NAME, activityCode.getCustomName());
		dataMap.put(TemplateUtil.TemplateField.START_TIME,
				DateUtil.dateToStr(activityCode.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		dataMap.put(TemplateUtil.TemplateField.END_TIME,
				DateUtil.dateToStr(activityCode.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
		dataMap.put(TemplateUtil.TemplateField.ENTERPRISE_CODE, activityCode.getEnterpriseCode());
		dataMap.put(TemplateUtil.TemplateField.ENTERPRISE_NAME, activityCode.getIssueEnterpriseName());
		dataMap.put(TemplateUtil.TemplateField.MAX_NUM, String.valueOf(activityCode.getMaxNum()));
		dataMap.put(TemplateUtil.TemplateField.ORDER_CODE, activityCode.getOrderCode());
		dataMap.put(TemplateUtil.TemplateField.ACTIVITY_OUT_CODE, activityCode.getOutCode());
		dataMap.put(TemplateUtil.TemplateField.ACTIVITY_CODE, activityCode.getActivityCode());
		dataMap.put(TemplateUtil.TemplateField.SELECT_DATE, activityCode.getSelectDate());
		String dateTime = DateUtil.getLongCurDate();
		String[] dateTimes = dateTime.split(" ");
		dataMap.put(TemplateUtil.TemplateField.SYSDATE, dateTimes[0]);
		dataMap.put(TemplateUtil.TemplateField.SYSTIME, dateTimes[1]);
		dataMap.put(TemplateUtil.TemplateField.NEW_LINE, "\n");
		return dataMap;
	}

	public String verifyCodeForOldPos(String batchNo, String searchNo, String device, String code) {
		AjaxResponseData ard = new AjaxResponseData();
		String requestSerialNumber = genRequestSerailNumber(batchNo, searchNo, device);
		Object[] verifyCode = verifyCode(ard, code, device, requestSerialNumber, true, ExchangeChannel.POS);
		StringBuilder str = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table>");
		if (null == verifyCode) {
			str.append("<isSuccess>false</isSuccess>").append("<error>").append(ard.getErrorMsg())
					.append("</error></Table></NewDataSet>");
			return str.toString();
		}

		TActivityCode activityCode = (TActivityCode) verifyCode[0];
		ActActivityStore activityStore = (ActActivityStore) verifyCode[1];
		TActivityInfo activityInfo = (TActivityInfo) verifyCode[2];
		str.append("<isSuccess>true</isSuccess>");
		str.append("<IsNeedConfirm>false</IsNeedConfirm>");
		str.append("<ConfirmNumber></ConfirmNumber>");
		str.append("<PwdInfoId></PwdInfoId>");
		str.append("<MmsId>").append(activityInfo.getActivityId()).append("</MmsId>");
		str.append("<MastMmsId>0</MastMmsId>");
		str.append("<MmsName>" + activityCode.getActivityName() + "</MmsName>");
		str.append("<Description></Description>");
		str.append("<MmsTitle></MmsTitle>");
		str.append("<ValidFrom></ValidFrom>");
		str.append("<ValidTo>" + DateUtil.dateToStr(activityCode.getEndTime(), "yyyy-MM-dd") + "</ValidTo>");
		str.append("<ResendContent></ResendContent>");
		str.append("<ValidTimes>1</ValidTimes>");
		str.append("<ShopName>" + activityStore.getStoreName() + "</ShopName>");
		str.append("<ShopFamilyName>" + activityStore.getShopName() + "</ShopFamilyName>");
		str.append("<MmsFamilyName>" + activityStore.getShopName() + "</MmsFamilyName>");
		str.append("<Advertising></Advertising>");
		str.append("<PrintNo>" + requestSerialNumber + "</PrintNo>");
		str.append("<LastVerifyNumber>1</LastVerifyNumber>");
		str.append("<verifyPwd>" + code + "</verifyPwd>");
		str.append("<CreatedOn>" + DateUtil.dateToStr(activityCode.getCreateTime(), "yyyy-MM-dd") + "</CreatedOn>");
		str.append("<CurrentDateTime>" + DateUtil.getLongCurDate() + "</CurrentDateTime>");
		str.append("<ShopId>" + activityStore.getStoreId() + "</ShopId>");
		str.append("<Entname>" + activityCode.getIssueEnterpriseName() + "</Entname>");

		// 小票内容模板转换
		Map<TemplateField, String> templateVar = initTemplateVar(device, requestSerialNumber, code, activityCode,
				activityStore);
		str.append("<Printstr>" + TemplateUtil.buildStringFromTemplate(templateVar, activityInfo.getTicketContent())
				+ "</Printstr>");
		str.append("<PrintTitle>");
		if (StringUtils.isEmpty(activityInfo.getTicketTitle())) {
			str.append("汇金通消费单");
		} else {
			str.append(TemplateUtil.buildStringFromTemplate(templateVar, activityInfo.getTicketTitle()));
		}
		str.append("</PrintTitle>");
		str.append("<ShopNo>");
		String batchno = "000000000001";// TODO 不确定作用
		str.append(batchno);
		str.append("</ShopNo>");
		str.append("<TranType>权益兑换</TranType>");
		str.append("<BacthNo>");
		str.append(String.format("%09d", Integer.valueOf(activityCode.getActivityId())));
		str.append("</BacthNo>");
		str.append("<AdminNo>001");
		str.append("</AdminNo>");
		str.append("<PrintInfo>");
		str.append("</PrintInfo>");
		str.append("<Result>验证成功</Result>");
		str.append("<Canusenum>1</Canusenum>");
		String posNO = getPosNO();

		str.append("<posno>" + posNO + "</posno>");
		str.append("</Table>");
		str.append("</NewDataSet>");

		return str.toString();
	}

	public AjaxResponseData verifyCodeForRecharge(String orderId, String code, String phone) {
		AjaxResponseData ard = new AjaxResponseData();
		Object[] verifyCode = verifyCode(ard, code, phone, orderId, false, ExchangeChannel.RECHARGE);

		// 返回数据
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == verifyCode) {
			returnMap.put("isSuccess", false);
			returnMap.put("errorMsg", ard.getErrorMsg());
			ard.setData(returnMap);
			return ard;
		}
		TActivityCode activityCode = (TActivityCode) verifyCode[0];
		// 如果是按照时间判断有效的。
		if (activityCode != null) { // 属于有效的
			returnMap.put("orderId", orderId);
			returnMap.put("code", code);
			returnMap.put("codeId", activityCode.getCodeSerialNumber());
			returnMap.put("activityOutCode", activityCode.getOutCode());
			returnMap.put("amount", activityCode.getAmount());
			returnMap.put("isSuccess", true);
		} else {
			returnMap.put("isSuccess", false);
			returnMap.put("errorMsg", ard.getErrorMsg());
		}
		ard.setData(returnMap);
		return ard;
	}

	private String genRequestSerailNumber(String batchNo, String searchNo, String device) {
		return device + batchNo + searchNo;
	}

	/**
	 * 生成POSNO
	 * 
	 * @return
	 */
	private String getPosNO() {
		Random rd = new Random();
		DecimalFormat df1 = new DecimalFormat("000");
		String currDate1 = new SimpleDateFormat("hhmmssSSS").format(new Date());// 生成日期格式为：年年月月日日、如：110608(11年06月08日)
		String increase1 = df1.format(rd.nextInt(999));// 4位自增长数
		return increase1 + currDate1;
	}

	/**
	 * 验码消费
	 * 
	 * @param ard
	 * @param code
	 * @param device
	 *            设备号；充值时为手机号
	 * @param request_serial_number
	 * @param checkDevice
	 *            是否校验设备号
	 * @return 返回码、门店、活动信息对象，如果校验未通过，返回null
	 */
	private Object[] verifyCode(AjaxResponseData ard, String code, String device, String request_serial_number,
			boolean checkDevice, ExchangeChannel channel) {
		// 判断券码的规则合法性
		if (!ActivityCodeUtils.verifyActivityCode(code)) {
			ard.setSuccess(false);
			ard.setErrorMsg("券码非法!");
			ard.setErrorCode(ErrorCode.CODE_15.getCode());
			return null;
		}

		// 判断是否能查询到该码
		List<TActivityCode> queryList = this.findByCode(code);
		if (null == queryList || queryList.size() < 1) {
			ard.setSuccess(false);
			ard.setErrorMsg("无效的券码!");
			ard.setErrorCode(ErrorCode.CODE_15.getCode());
			return null;
		}

		TActivityCode activityCode = queryList.get(0);
		List<TActivityInfo> activityInfos = activityInfoMapper.findByActivityId(activityCode.getActivityId());
		TActivityInfo activityInfo = activityInfos.get(0);
		// 锁
		acm.selectActivityCodeForUpdateById(activityCode.getId());
		// activityCode.setMaxNum(5);
		// acm.updateActivityCode(activityCode);

		/** 判断码是否已经使用过 **/
		if (!"123456789012345".equals(code) && ActivityCodeStatus.USED.getValue().equals(activityCode.getStatus())) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("code", code);
			param.put("verifyType", VerifyCodeStatus.VERIFYCODE.getValue());
			List<TVerifiedCode> verifiedCode = vcm.findVerifiedCodesByCodeAndType(param);
			ard.setSuccess(false);
			TVerifiedCode oneCode = verifiedCode.get(0);
			ard.setErrorMsg("当前券码已于时间" + DateUtil.dateToStr(oneCode.getVerifyTime(), "yyyy-MM-dd \n HH:mm:ss") + "在"
					+ oneCode.getShopName() + oneCode.getStoreName() + "\n使用过。");
			ard.setErrorCode(ErrorCode.CODE_51.getCode());
			addLog(device, request_serial_number, activityCode, new ActActivityStore(), VerifyCodeStatus.VERIFYCODE,
					VerifyLogCode.VERIFY_HAS_USED);
			return null;
		}

		// 兑换渠道校验
		if (null == activityCode.getExchangeChannel()
				|| !activityCode.getExchangeChannel().contains(channel.getValue())) {
			ard.setSuccess(false);
			ard.setErrorMsg("此券码不适用于该兑换方式!");
			ard.setErrorCode(ErrorCode.CODE_14.getCode());
			addLog(device, request_serial_number, activityCode, new ActActivityStore(), VerifyCodeStatus.VERIFYCODE,
					VerifyLogCode.VERIFY_INVALID_CHANNEL);
			return null;
		}
		// 手机充值时校验手机号
		if (channel.equals(ExchangeChannel.RECHARGE) && !device.equals(activityCode.getCustomMobile())) {
			ard.setSuccess(false);
			ard.setErrorMsg("不能用于该手机号");
			ard.setErrorCode(ErrorCode.CODE_14.getCode());
			return null;
		}
		// 判断券码是否适用当前的门店 ，活动和门店的映射
		List<ActActivityStore> actActivityStores = actActivityStoreMapper
				.findByActivityId(activityCode.getActivityId());

		// 编码不为空，优先适用门店编码获取
		boolean canUse = false;
		ActActivityStore activityStore = null;
		/* 如果要校验设备号，校验该码是否适用于该设备所在门店 */
		if (checkDevice) {
			String resultStr = HttpUtil.doRestful(PropertiesUtil.getValue("MANAGE.GET_STORE_ID.URL") + "/" + device,
					"");
			@SuppressWarnings("unchecked")
			Map<String, Object> resultData = JSON.parseObject(resultStr, Map.class);
			String storeId = resultData.get("data").toString();
			for (ActActivityStore aas : actActivityStores) {
				if (aas.getStoreId().equals(storeId)) {
					canUse = true;
					activityStore = aas;
					break;
				}
			}
			if (!canUse) {
				ard.setSuccess(false);
				ard.setErrorMsg("此券码不适用于当前门店!");
				ard.setErrorCode(ErrorCode.CODE_59.getCode());
				addLog(device, request_serial_number, activityCode, new ActActivityStore(), VerifyCodeStatus.VERIFYCODE,
						VerifyLogCode.VERIFY_INVALID_STORE);
				return null;
			}
		} else {
			activityStore = new ActActivityStore();
		}
		canUse = false;
		// 增加 券码适用的 选择日期（适用于 星期几）；
		String current_day_of_week = DateUtil.getWeekDayForToday();
		String[] selectDate = activityCode.getSelectDate().split(ActivityCodeUtils.SPLIT_CHAR_COMMA);
		for (String d : selectDate) {
			if (!StringUtils.isEmpty(d) && d.trim().equals(current_day_of_week)) {
				canUse = true;
			}
		}
		if (!canUse) {
			ard.setSuccess(false);
			ard.setErrorMsg("此券码不在适用星期范围内!");
			ard.setErrorCode(ErrorCode.CODE_61.getCode());
			addLog(device, request_serial_number, activityCode, activityStore, VerifyCodeStatus.VERIFYCODE,
					VerifyLogCode.VERIFY_INVALID_WEEK);
			return null;
		}
		// 判断活动有效期
		int effective = activityCode.effective();
		if (0 == effective) {
			addLog(device, request_serial_number, activityCode, activityStore, VerifyCodeStatus.VERIFYCODE,
					VerifyLogCode.VERIFY_SUCCESS);

			TActivityCode activityCodeForUpdate = new TActivityCode();
			activityCodeForUpdate.setStatus(ActivityCodeStatus.USED.getValue());
			activityCodeForUpdate.setId(activityCode.getId());
			acm.updateActivityCode(activityCodeForUpdate);
			logger.debug("标记码已使用:" + code);

			return new Object[] { activityCode, activityStore, activityInfo };
		} else if (effective < 0) {
			addLog(device, request_serial_number, activityCode, activityStore, VerifyCodeStatus.VERIFYCODE,
					VerifyLogCode.VERIFY_NOT_STARTED);
		} else {
			addLog(device, request_serial_number, activityCode, activityStore, VerifyCodeStatus.VERIFYCODE,
					VerifyLogCode.VERIFY_HAS_ENDED);
		}
		ard.setSuccess(false);
		ard.setErrorMsg("当前使用时间不在有效的活动时间范围内!");
		ard.setErrorCode(ErrorCode.CODE_60.getCode());
		return null;
	}

	/**
	 * 记录操作记录
	 * 
	 * @param device
	 * @param request_serial_number
	 * @param activityCode
	 * @param activityStore
	 */
	private void addLog(String device, String request_serial_number, TActivityCode activityCode,
			ActActivityStore activityStore, VerifyCodeStatus verifyCodeStatus, VerifyLogCode logCode) {
		TVerifiedCode tvc = new TVerifiedCode();
		tvc.setId(UUIDUtils.UUID32());
		tvc.setCode(activityCode.getCode());
		tvc.setCodeSerialNumber(activityCode.getId());
		tvc.setVerifyTime(new Date());
		tvc.setActivityId(activityCode.getActivityId());
		tvc.setActivityName(activityCode.getActivityName());
		// tvc.setAmount(amount);
		tvc.setDevice(device); // 机具id
		tvc.setStoreId(activityStore.getStoreId()); // 门店ID
		tvc.setStartTime(activityCode.getStartTime());
		tvc.setEndTime(activityCode.getEndTime());
		tvc.setNumber(1); // 一个码固定只能使用一次
		tvc.setRequestSerialNumber(request_serial_number); // POS请求序列号
		tvc.setIssueEnterpriseId(activityCode.getIssueEnterpriseId());
		tvc.setIssueEnterpriseName(activityCode.getIssueEnterpriseName());
		tvc.setShopId(activityStore.getShopId()); // 执行企业的编号
		tvc.setShopName(activityStore.getShopName());
		tvc.setStoreName(activityStore.getStoreName());
		tvc.setVerifyType(verifyCodeStatus.getValue()); // 设置状态为验码核销
		tvc.setContractId(activityCode.getContractId());
		tvc.setStatus(logCode.getValue());
		int count = vcm.insertVerifiedCode(tvc);
		logger.debug("使用记录:" + count);
	}

	public AjaxResponseData revokeForNewPos(String request_serial_number, String device) {
		AjaxResponseData ard = new AjaxResponseData();
		// 码校验
		TVerifiedCode verifyCodes = vcm.findVerifiedCodesByDeviceAndRequestSerialNumber(device, request_serial_number,
				new Date());
		revoke(request_serial_number, device, ard, verifyCodes);
		return ard;
	}

	public String revokeForOldPos(String batchNo, String searchNo, String device) {
		AjaxResponseData ard = new AjaxResponseData();
		String requestSerailNumber = genRequestSerailNumber(batchNo, searchNo, device);
		// 码校验
		TVerifiedCode verifyCodes = vcm.findVerifiedCodesByDeviceAndRequestSerialNumber(device, requestSerailNumber,
				new Date());
		Object[] revoke = revoke(requestSerailNumber, device, ard, verifyCodes);
		StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table>");
		if (ard.isSuccess()) {
			TActivityCode activityCode = (TActivityCode) revoke[0];
			sb.append("<isSuccess>true</isSuccess>");
			sb.append("<checknum>1</checknum>");
			sb.append("<description></description>");
			sb.append("<eitemname>" + activityCode.getActivityName() + "</eitemname>");
			sb.append("<mmsfamilyname>").append(activityCode.getIssueEnterpriseName()).append("</mmsfamilyname>");
			sb.append("<validto>" + DateUtil.dateToStr(activityCode.getEndTime(), "yyyy-MM-dd") + "</validto>");
			sb.append("<eitemid>" + activityCode.getActivityId() + "</eitemid>");
		} else {
			sb.append("<isSuccess>false</isSuccess>");
			sb.append("<resultInfo>").append(ard.getErrorMsg()).append("</resultInfo>");
		}
		sb.append("</Table>");
		sb.append("</NewDataSet>");
		return sb.toString();
	}

	public AjaxResponseData revokeForSys(String request_serial_number, String device) {
		AjaxResponseData ard = new AjaxResponseData();
		// 码校验
		TVerifiedCode verifyCodes = vcm.findByDeviceAndRequestSerialNumber(device, request_serial_number);
		revoke(request_serial_number, device, ard, verifyCodes);
		return ard;
	}

	/**
	 * 消费撤销
	 * 
	 * @param request_serial_number
	 * @param device
	 * @param ard
	 * @param verifyCodes
	 * @return
	 */
	private Object[] revoke(String request_serial_number, String device, AjaxResponseData ard,
			TVerifiedCode verifyCodes) {
		if (null == verifyCodes) {
			ard.setSuccess(false);
			ard.setErrorMsg("不存在该交易记录！");
			ard.setErrorCode(ErrorCode.CODE_25.getCode());
			return null;
		}
		TActivityCode activityCode = acm.findActivityCodeById(verifyCodes.getCodeSerialNumber());
		if (null == activityCode) {
			ard.setSuccess(false);
			ard.setErrorMsg("无效的码！");
			ard.setErrorCode(ErrorCode.CODE_14.getCode());
			return null;
		}
		if (!ActivityCodeStatus.USED.getValue().equals(activityCode.getStatus())) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法类型请求操作：该码已被撤销！");
			ard.setErrorCode(ErrorCode.CODE_51.getCode());
			addLog(device, request_serial_number, activityCode, new ActActivityStore(), VerifyCodeStatus.REVOKE,
					VerifyLogCode.REVOKE_HAS_REVOKED);
			return null;
		}

		// 撤销码
		TActivityCode activityCodeForUpdate = new TActivityCode();
		activityCodeForUpdate.setStatus(ActivityCodeStatus.NOT_USED.getValue());
		activityCodeForUpdate.setId(activityCode.getId());
		acm.updateActivityCode(activityCodeForUpdate);
		addLog(device, request_serial_number, activityCode, new ActActivityStore(), VerifyCodeStatus.REVOKE,
				VerifyLogCode.REVOKE_SUCCESS);

		ard.setSuccess(true);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityId", activityCode.getActivityId());
		m.put("activityName", activityCode.getActivityName());
		m.put("startTime", activityCode.getStartTime());
		m.put("endTime", activityCode.getEndTime());
		m.put("issueEnterpriseId", activityCode.getIssueEnterpriseId());
		m.put("issueEnterpriseName", activityCode.getIssueEnterpriseName());
		List<TActivityInfo> activityInfo = activityInfoMapper.findByActivityId(activityCode.getActivityId());
		m.put("ticketTitle", activityInfo.get(0).getTicketTitle());
		ard.setData(m);

		return new Object[] { activityCode };
	}

	public AjaxResponseData importActActivityCodeWithTran(ActActivityCodeVO actActivityCodeVO) {
		AjaxResponseData ard = new AjaxResponseData();

		// 保存活动与门店
		this.actActivityStoreMapper.batchSave(actActivityCodeVO.getActActivityStores());
		List<TActivityCode> newCodes = new ArrayList<TActivityCode>();
		for (TActivityCode c : actActivityCodeVO.getActivityCodeList()) {
			c.setStatus(ActivityCodeStatus.NOT_USED.getValue());
			newCodes.add(c);
		}
		this.acm.batchSave(newCodes);
		// 保存附属信息
		activityInfoMapper.deleteByActivityId(actActivityCodeVO.getActivityId());
		TActivityInfo activityInfo = new TActivityInfo();
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

		int c = activityCodeBlackListsMapper.addBlackListsByActivityId(activity_id,
				ActivityBlackListsType.CANCEL.getValue(), ActivityBlackListsType.CANCEL.getText());
		if (c >= 0) {
			ard.setSuccess(true);
			ard.setErrorMsg("");
		} else {
			ard.setSuccess(false);
			ard.setErrorMsg("码删除失败!");
			ard.setErrorCode(ErrorCode.CODE_99.getCode());
		}
		return ard;
	}

	public AjaxResponseData updateActActivityWithTran(ActActivityCodeVO actActivityCodeVO) {
		AjaxResponseData ard = new AjaxResponseData();

		// 1.临时加入黑名单，状态为锁定
		this.activityCodeBlackListsMapper.addBlackListsByActivityId(actActivityCodeVO.getActivityId(),
				ActivityBlackListsType.LOCKING.getValue(), ActivityBlackListsType.LOCKING.getText());
		// 2.更新 券码表中的数据
		this.acm.updateActActivity(actActivityCodeVO.getActivityId(), actActivityCodeVO.getActivityName(),
				actActivityCodeVO.getEnterpriseId(), actActivityCodeVO.getEnterpriseName(),
				actActivityCodeVO.getContractId(), actActivityCodeVO.getAmount(), actActivityCodeVO.getStartTime(),
				actActivityCodeVO.getEndTime(), actActivityCodeVO.getSelectDate(), actActivityCodeVO.getMaxNumber(),
				actActivityCodeVO.getOutCode(), actActivityCodeVO.getEnterpriseCode(),
				actActivityCodeVO.getActivityCode(), ActivityCodeStatus.NOT_USED.getValue(),
				actActivityCodeVO.getExchangeChannel());
		// 3.更新活动关联的门店
		this.actActivityStoreMapper.deleteByActivityId(actActivityCodeVO.getActivityId());
		this.actActivityStoreMapper.batchSave(actActivityCodeVO.getActActivityStores());
		// 4.解除黑名单的锁定
		this.activityCodeBlackListsMapper.deleteBlackListsByActivityId(actActivityCodeVO.getActivityId());

		// 5更新活动附属信息
		activityInfoMapper.deleteByActivityId(actActivityCodeVO.getActivityId());
		TActivityInfo activityInfo = new TActivityInfo();
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

	public AjaxResponseData delayForSys(Date endTime, String codeId) {
		AjaxResponseData ard = new AjaxResponseData();
		TActivityCode activityCode = new TActivityCode();
		activityCode.setEndTime(endTime);
		activityCode.setId(codeId);
		int count = acm.updateActivityCode(activityCode);
		if (count == 0) {
			ard.setSuccess(false);
			ard.setErrorMsg("码不存在，延期失败");
		}
		return ard;
	}
}
