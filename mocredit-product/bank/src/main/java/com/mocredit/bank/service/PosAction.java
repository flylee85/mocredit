package com.mocredit.bank.service;

import com.mocredit.bank.entity.MessageObject;

public interface PosAction {
	public MessageObject messReceived(MessageObject request) throws Exception;
	public MessageObject manageTran(MessageObject posRequest) throws Exception;
}
