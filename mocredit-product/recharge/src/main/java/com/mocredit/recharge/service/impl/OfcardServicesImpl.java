package com.mocredit.recharge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mocredit.base.util.DateTimeUtils;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.recharge.model.Code;
import com.mocredit.recharge.model.RechangeBo;
import com.mocredit.recharge.model.Record;
import com.mocredit.recharge.persitence.RecordMapper;
import com.mocredit.recharge.service.OfcardServices;
import com.mocredit.recharge.util.MD5;
import com.mocredit.recharge.util.RechargeStatus;
import com.mocredit.recharge.util.RechargeTypeCode;
import com.mocredit.recharge.util.XmlUtils;

@Service
@Transactional
public class OfcardServicesImpl implements OfcardServices {

	private static final Log log = LogFactory.getLog(OfcardServicesImpl.class);
	@Autowired
	private RecordMapper recordMapper;
	@Value("${recharge.ofcard.url}")
	private String url;
	@Value("${recharge.ofcard.version}")
	private String version;
	@Value("${recharge.ofcard.sendtype}")
	private String sendType;
	@Value("${recharge.ofcard.keystr}")
	private String keystr;
	@Value("${recharge.ofcard.user}")
	private String user;
	@Value("${recharge.ofcard.pwd}")
	private String pwd;
	@Value("${recharge.ofcard.noticeurl}")
	private String noticeUrl;

	@Override
	public RechangeBo rechange(String phone, int cardnum, String sporderid, Date time, Code code, String server) {
		Map<String, Object> requestParamsMap = new HashMap<>();
		MD5 md5 = new MD5();
		String md5password = md5.cell32(this.pwd);
		requestParamsMap.put("userid", this.user);
		requestParamsMap.put("userpws", md5password);
		requestParamsMap.put("version", version);
		requestParamsMap.put("cardid", sendType);
		String cradnumstr = "" + cardnum;
		requestParamsMap.put("cardnum", cradnumstr);
		requestParamsMap.put("sporder_id", sporderid);
		String sporderTime = DateTimeUtils.dateToStr(time, "yyyyMMddHHmmss");
		requestParamsMap.put("sporder_time", sporderTime);
		requestParamsMap.put("game_userid", phone);
		requestParamsMap.put("eorderid", code.getCodeId());
		// userid+userpws+cardid+cardnum+sporder_id+sporder_time+ game_userid
		// http://api2.ofpay.com/onlineorder.do?

		String pagestr = "";
		String md5str = "";
		pagestr += this.user;
		pagestr += md5password;
		pagestr += this.sendType;
		pagestr += cradnumstr;
		pagestr += sporderid;
		pagestr += sporderTime;
		pagestr += phone;
		pagestr += this.keystr;
		md5str = md5.cell32(pagestr).toUpperCase();
		requestParamsMap.put("md5_str", md5str);
		requestParamsMap.put("ret_url", noticeUrl);
		StringBuilder builder = new StringBuilder("[RECHARGE_LOGINFO][BEFORE OFCARD_RECHARGE][POST METHOD PARAM] :: ");
		for (Entry<String, Object> param : requestParamsMap.entrySet()) {
			builder.append("[");
			builder.append(param.getKey()).append("]:").append(param.getValue());
			builder.append(",");
		}
		log.info(builder.toString());
		Record ofcardrecord = new Record();
		ofcardrecord.setOrderid(sporderid);
		ofcardrecord.setAmount(Double.valueOf(cardnum));
		ofcardrecord.setCreatetime(time);
		ofcardrecord.setPhone(phone);
		ofcardrecord.setMctype(String.valueOf(RechargeTypeCode.OFCARD.getValue()));
		ofcardrecord.setStatus(RechargeStatus.SENDING.getValue());
		ofcardrecord.setCode(code.getCode());
		ofcardrecord.setCodeid(code.getCodeId());
		ofcardrecord.setServer(server);
		recordMapper.save(ofcardrecord);
		String restr = HttpUtil.doPostByHttpConnection(url + "onlineorder.do", requestParamsMap);
		// String restr = "<?xml version=\"1.0\"
		// encoding=\"gb2312\"?><orderinfo><err_msg></err_msg><retcode>1</retcode><orderid>S1301065430819</orderid><cardid>151201</cardid><cardnum>1</cardnum><ordercash>19.850</ordercash><cardname>北京联通话费20元直充</cardname><sporder_id>20130106112643316265947</sporder_id><game_userid>18600429925</game_userid><game_state>0</game_state></orderinfo>";
		// String restr = "<?xml version=\"1.0\"
		// encoding=\"gb2312\"?><orderinfo><err_msg>商户IP验证错误，访问的IP：119.161.158.92</err_msg><retcode>1002</retcode><orderid></orderid><cardid>140101</cardid><cardnum>0.4</cardnum><ordercash></ordercash><cardname></cardname><sporder_id>20130106115111717848116</sporder_id><game_userid>18600429925</game_userid><game_area></game_area><game_srv></game_srv><game_state></game_state></orderinfo>";
		log.info("restr===============" + restr);
		if (StringUtils.isEmpty(restr)) {
			Record record2 = new Record();
			record2.setId(ofcardrecord.getId());
			record2.setStatus(RechargeStatus.FAILED.getValue());
			record2.setErrmessage("返回数据有异常");
			recordMapper.update(record2);
			log.info("[RECHARGE_LOGINFO][AFTER OFCARD RECHARGE][RETCODE] :: 返回数据有异常");
			return null;
		}
		Map resultmap = XmlUtils.Xml2Map(restr);

		Record record2 = new Record();
		record2.setId(ofcardrecord.getId());
		// 充值结果校验
		int retcode = Integer.parseInt(resultmap.get("retcode").toString());
		log.info("[RECHARGE_LOGINFO][AFTER OFCARD RECHARGE][RETCODE] :: " + retcode);
		Object error = resultmap.get("err_msg");
		if (retcode == 1) {
			// 如果成功将为1，澈消(充值失败)为9，充值中为0,只能当状态为9时，商户才可以退款给用户。
			int gameState = Integer.parseInt(resultmap.get("game_state").toString());
			switch (gameState) {
			case 1:
			case 0:
				record2.setStatus(RechargeStatus.SENDED.getValue());
				break;
			case 9:
				record2.setStatus(RechargeStatus.FAILED.getValue());
				break;
			default:
				record2.setStatus(RechargeStatus.FAILED.getValue());
				record2.setErrmessage("欧飞返回码不正确,返回码：" + gameState);
				break;
			}
		} else {
			log.info("[RECHARGE_LOGINFO][AFTER OFCARD RECHARGE][RETCODE] :: " + retcode + "  [ERR_MSG] : "
					+ error.toString());
			record2.setStatus(RechargeStatus.FAILED.getValue());
			record2.setErrmessage(null == error ? "充值失败" : error.toString());
		}
		Date nowDate = new Date();
		String currenttime = DateTimeUtils.dateToStr(nowDate, "yyyy-MM-dd HH:mm:ss");

		String record_info = "Ofcardrecord [code=" + code.getCode() + ", sporderid=" + sporderid + ", creatime="
				+ currenttime + ", status=" + retcode + ", errmessage=" + error + ", cardnum=" + cardnum + ", mctype="
				+ RechargeTypeCode.OFCARD.getValue() + ", phone=" + phone + "]";
		log.info("[RECHARGE_LOGINFO][111BEFORE SAVE OFCARDRECORD]" + record_info);
		recordMapper.update(record2);
		return null;
	}

	@Override
	public double searchBalance(String userid, String password) {
		Map<String, Object> postMethod = new HashMap<>();
		MD5 md5 = new MD5();
		String md5password = md5.cell32(password);
		postMethod.put("userid", userid);
		postMethod.put("userpws", md5password);
		postMethod.put("version", this.version);
		String restr = HttpUtil.doPostByHttpConnection(url + "queryuserinfo.do", postMethod);
		Map resultmap = XmlUtils.Xml2Map(restr);
		double balance = 0;
		if (resultmap != null) {
			if (resultmap.get("ret_leftcredit") != null) {
				balance = Double.parseDouble(resultmap.get("ret_leftcredit").toString());
			}
		}
		return balance;
	}

	@Override
	public boolean notice(String retcode, String sporderid, String ordersuccesstime, String errmsg) {
		log.info("[RECHARGE_LOGINFO][OFCARD RECHARGE NOTICE] :: retcode=" + retcode + ",sporderid=" + sporderid
				+ ",ordersuccesstime=" + ordersuccesstime + ",errmsg=" + errmsg);
		Record record = new Record();

		int retcodeInt = Integer.parseInt(retcode);
		record.setOrderid(sporderid);
		if (retcodeInt == 1) {
			Map<String, Object> requestParamsMap = new HashMap<>();
			requestParamsMap.put("userid", this.user);
			requestParamsMap.put("spbillid", sporderid);
			// 查询订单状态
			String retstr = HttpUtil.doPostByHttpConnection(this.url + "api/query.do", requestParamsMap);
			log.info("[RECHARGE_LOGINFO][OFCARD RECHARGE QUERY] :: this.user=" + this.user + ",sporderid=" + sporderid
					+ ",returnStr=" + retstr);
			// String retstr="1";
			// 1充值成功，0充值中，9充值失败，-1找不到此订单。如果返回-1，请您务必进入平台或者联系欧飞客服进行核实，避免给自己带来不必要的损失
			switch (retstr) {
			case "1":
				record.setStatus(RechargeStatus.SUCCEED.getValue());
				break;
			case "0":
				record.setStatus(RechargeStatus.SENDED.getValue());
				break;
			case "9":
				record.setStatus(RechargeStatus.FAILED.getValue());
				record.setErrmessage("主动查询订单确定订单充值失败，返回值：" + retstr);
				break;
			case "-1":
				record.setStatus(RechargeStatus.NOT_FOUND.getValue());
				break;
			default:
				record.setStatus(RechargeStatus.FAILED.getValue());
				record.setErrmessage("欧飞查询订单状态返回不正确，返回值：" + retstr);
				break;
			}
		} else {
			record.setStatus(RechargeStatus.FAILED.getValue());
			record.setErrmessage(errmsg);
		}

		record.setNoticetime(new Date());
		recordMapper.update(record);
		return true;
	}
}
