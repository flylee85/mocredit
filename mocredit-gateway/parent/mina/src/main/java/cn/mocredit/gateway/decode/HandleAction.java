package cn.mocredit.gateway.decode;

import cn.mocredit.gateway.message.MessageObject;
import cn.mocredit.gateway.posp.bc.util.FormatException;

public interface HandleAction {
	/**
	 * 签到
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject signIn(MessageObject request) throws FormatException;
	/**
	 * 活动下载
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject down(MessageObject request) throws FormatException;
	/**
	 * 消费
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject purchase(MessageObject request) throws FormatException;
	/**
	 * 消费冲正
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject reversalPurchase(MessageObject request) throws FormatException;
	/**
	 * 消费撤销
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject revokePurchase(MessageObject request) throws FormatException;
	/**
	 * 消费撤销冲正
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject reversalRevokePurchase(MessageObject request) throws FormatException;
}
