package cn.mocredit.posp.main;

import cn.mocredit.posp.bc.util.MessageObject;

public interface PosAction {
	public MessageObject messReceived(MessageObject request) throws Exception;
	public MessageObject manageTran(MessageObject posRequest) throws Exception;
}
