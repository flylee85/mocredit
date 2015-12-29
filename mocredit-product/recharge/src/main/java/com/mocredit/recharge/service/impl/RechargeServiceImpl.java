package com.mocredit.recharge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.DateTimeUtils;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.RandomUtil;
import com.mocredit.recharge.model.Code;
import com.mocredit.recharge.model.Record;
import com.mocredit.recharge.persitence.RecordMapper;
import com.mocredit.recharge.service.OfcardServices;
import com.mocredit.recharge.service.RechargeService;
import com.mocredit.recharge.service.YiChongBaoServices;
import com.mocredit.recharge.util.RechargeStatus;
import com.mocredit.recharge.util.RechargeTypeCode;

@Service
@Transactional
public class RechargeServiceImpl implements RechargeService {
	private static final Log log = LogFactory.getLog(RechargeServiceImpl.class);
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private OfcardServices ofcardServices;
	@Autowired
	private YiChongBaoServices yichongbaoServices;
	@Autowired
	private RecordMapper recordMapper;
	@Value("${recharge.istest}")
	private int isTest; // 测试开关
	@Value("${recharge.verifycode.url}")
	private String verifyUrl;// 验码URL
	@Value("${recharge.channel}") // 充值平台
	private int channel;

	@Override
	public String rechargeByCode(String code, String phone, String server) {
		// 生成订单号
		Date nowDate = new Date();
		String currenttime = DateTimeUtils.dateToStr(nowDate, "yyyyMMddHHmmss");
		String tid = "CZ" + currenttime + RandomUtil.getRandom();

		Code verifyCode = null;
		try {
			verifyCode = this.verifyCode(code, tid, phone);
		} catch (Exception e) {
			return "Error:充值时发生异常";
		}
		if (!verifyCode.isSuccess()) {
			return "Error:" + verifyCode.getErrorMsg().replaceAll("\n", "");
		}
		/* 测试情况下 */
		if (isTest == 1) {
			Record record = new Record();
			record.setOrderid(tid);
			record.setAmount(Double.valueOf(verifyCode.getAmount()));
			record.setCreatetime(nowDate);
			record.setPhone(phone);
			if (RechargeTypeCode.OFCARD.getValue() == channel) {
				log.info("测试殴飞充值接口phone:" + phone);
			} else if (RechargeTypeCode.YICHONGBAO.getValue() == channel) {
				log.info("测试易充宝充值接口phone:" + phone);
			} else {
				return "Error:该码不能用于该充值";
			}
			record.setMctype(verifyCode.getActivityOutCode());
			record.setStatus(RechargeStatus.SUCCEED.getValue());
			recordMapper.save(record);
			/* 验码通过 */
		} else {
			if (RechargeTypeCode.OFCARD.getValue() == channel) {
				log.info("调用殴飞充值接口phone:" + phone);
				taskExecutor.execute(new OfcardSend(phone, verifyCode.getAmount(), tid, nowDate, ofcardServices,
						verifyCode, server));
			} else if (RechargeTypeCode.YICHONGBAO.getValue() == channel) {
				log.info("调用易充宝充值接口phone:" + phone);
				taskExecutor.execute(new YichongbaoSend(phone, String.valueOf(verifyCode.getAmount()), tid, nowDate,
						yichongbaoServices, verifyCode));
			}
		}
		return "Success:充值成功！";
	}

	/**
	 * 异步通知接口
	 */
	@Override
	public boolean notice(Map<String, Object> param, String channel) {
		if (StringUtils.isEmpty(channel)) {
			return false;
		}
		/**
		 * 欧飞充值<br/>
		 * ret_code 充值后状态， 1代表成功，9代表撤消 <br/>
		 * sporder_id SP订单号<br/>
		 * ordersuccesstime 处理时间 <br/>
		 * err_msg 失败原因<br/>
		 * 提交方式为：POST
		 */
		if (channel.equals(String.valueOf(RechargeTypeCode.OFCARD.getValue()))) {
			// 参数校验
			if (StringUtils.isEmpty(param.get("ret_code")) || StringUtils.isEmpty(param.get("sporder_id"))) {
				return false;
			}
			Object ordertime = param.get("ordersuccesstime");
			Object errorMsg = param.get("err_msg");
			return ofcardServices.notice(param.get("ret_code").toString(), param.get("sporder_id").toString(),
					null == ordertime ? "" : ordertime.toString(), null == errorMsg ? "" : errorMsg.toString());
			/**
			 * 易充宝<br/>
			 * 暂未实现
			 */
		} else {

		}
		return false;
	}

	/**
	 * 调用验码
	 * 
	 * @param code
	 * @param orderId
	 * @return
	 */
	private Code verifyCode(String code, String orderId, String phone) {
		Map<String, Object> requestParamsMap = new HashMap<>();
		requestParamsMap.put("orderId", orderId);
		requestParamsMap.put("code", code);
		requestParamsMap.put("phone", phone);
		String retstr = HttpUtil.doPostByHttpConnection(this.verifyUrl, requestParamsMap);
		return JSON.parseObject(retstr, Code.class);
	}

}
