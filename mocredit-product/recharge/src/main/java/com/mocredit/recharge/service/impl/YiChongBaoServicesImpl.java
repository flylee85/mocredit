package com.mocredit.recharge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.base.util.DateTimeUtils;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.recharge.model.Code;
import com.mocredit.recharge.model.RechangeBo;
import com.mocredit.recharge.model.Record;
import com.mocredit.recharge.persitence.RecordMapper;
import com.mocredit.recharge.service.YiChongBaoServices;
import com.mocredit.recharge.util.MD5;
import com.mocredit.recharge.util.RechargeStatus;
import com.mocredit.recharge.util.RechargeTypeCode;
import com.mocredit.recharge.util.XmlUtils;

@Service
@Transactional
public class YiChongBaoServicesImpl implements YiChongBaoServices {
	@Autowired
	private RecordMapper recordMapper;
	@Value("${recharge.ycb.url}")
	private String url;
	@Value("${recharge.ycb.user}")
	private String user;
	@Value("${recharge.ycb.pwd}")
	private String pwd;

	@Override
	public RechangeBo rechange(String photonum, String amount, String orderid, Date time, Code code) {
		Map<String, Object> postMethod = new HashMap<>();
		String ordertime = DateTimeUtils.dateToStr(time, "yyyyMMddHHmmss");
		postMethod.put("ispid", "");
		postMethod.put("amount", subZeroAndDot(amount));
		postMethod.put("dealerid", this.user);
		postMethod.put("orderid", orderid);
		postMethod.put("photonum", photonum);
		postMethod.put("ordertime", ordertime);
		postMethod.put("mark", "");
		// ispid=%s&amount=%s&dealerid=%s&orderid=%s&photonum=%s&ordertime=%s&mark=%s&dealerkey=%s
		String sign = "ispid=&amount=" + amount + "&dealerid=" + this.user + "&orderid=" + orderid + "&photonum="
				+ photonum + "&ordertime=" + ordertime + "&mark=&dealerkey=" + this.pwd;
		MD5 md5 = new MD5();
		sign = md5.cell32(sign);
		postMethod.put("sign", sign);

		Record record = new Record();
		record.setOrderid(orderid);
		record.setAmount(Double.valueOf(amount));
		record.setCreatetime(time);
		record.setPhone(photonum);
		record.setMctype(String.valueOf(RechargeTypeCode.YICHONGBAO.getValue()));
		record.setStatus(RechargeStatus.SENDING.getValue());
		record.setCode(code.getCode());
		recordMapper.save(record);

		String restr = HttpUtil.doPostByHttpConnection(this.url + "charge/recharge.do", postMethod);
		Map<String, String> resultmap = XmlUtils.getItemMap(restr);
		RechangeBo rb = new RechangeBo();
		if (resultmap != null) {
			rb.setRetcode(Integer.parseInt(resultmap.get("resultno")));
			rb.setCardid(resultmap.get("balance"));
			// rb.setErrmsg(resultmap.get("err_msg").toString());
			rb.setCardnum(resultmap.get("orderamount"));
			// rb.setCardname(resultmap.get("cardname"));
			rb.setSporderid(orderid);
			rb.setPhone(photonum);
			rb.setOrderid(resultmap.get("serialid"));
			rb.setStatus(0);
			
			Record record2 = new Record();
			record2.setId(record.getId());
			record2.setStatus(RechargeStatus.SENDED.getValue());
			recordMapper.update(record2);
			return rb;
		}
		return null;
	}

	private String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}
}
