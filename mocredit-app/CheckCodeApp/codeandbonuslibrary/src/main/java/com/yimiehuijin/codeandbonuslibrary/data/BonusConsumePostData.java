package com.yimiehuijin.codeandbonuslibrary.data;


import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;
import com.yimiehuijin.codeandbonuslibrary.web.PostUtil;

public class BonusConsumePostData {
	public String orderId;
	public String cardNo;
	public String exp_date;
	public String activitId;
	public String amt;
	public String orgOrderId;
	
	public BonusConsumePostData(){
		orderId = StringUtils.getOrderId();
	}
}
