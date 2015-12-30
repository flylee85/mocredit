package com.mocredit.recharge.service.impl;

import java.util.Date;

import com.mocredit.recharge.model.Code;
import com.mocredit.recharge.service.OfcardServices;

public class OfcardSend implements Runnable {
	private OfcardServices ofcardServices;
	private String phone;
	private int price;
	private String tid;
	private Date time;
	private Code barcode;
	private String server;

	public OfcardSend(String phone, int price, String tid, Date time, OfcardServices ofcardServices, Code barcode,
			String server) {
		this.phone = phone;
		this.price = price;
		this.tid = tid;
		this.time = time;
		this.ofcardServices = ofcardServices;
		this.barcode = barcode;
		this.server = server;
	}

	@Override
	public void run() {
		ofcardServices.rechange(phone, price, tid, time, barcode, this.server);
	}

}
