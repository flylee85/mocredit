package com.mocredit.recharge.service.impl;

import java.util.Date;

import com.mocredit.recharge.model.Code;
import com.mocredit.recharge.service.YiChongBaoServices;

public class YichongbaoSend implements Runnable {
	private YiChongBaoServices yichongbaoServices;
	private String phone;
	private String price;
	private String tid;
	private Date time;
	private Code barcode;

	public YichongbaoSend(String phone, String price, String tid, Date time, YiChongBaoServices yichongbaoServices,
			Code barcode) {
		this.phone = phone;
		this.price = price;
		this.tid = tid;
		this.time = time;
		this.yichongbaoServices = yichongbaoServices;
		this.barcode = barcode;
	}

	@Override
	public void run() {
		yichongbaoServices.rechange(phone, price, tid, time, barcode);
	}

}
