package cn.mocredit.gateway.decode;

import cn.mocredit.gateway.message.MessageObject;
import cn.mocredit.gateway.posp.bc.util.FormatException;

public interface Trancsct {
	/**
	 * 二维码
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject qrHandle(MessageObject request) throws FormatException;
	/**
	 * 积分兑换
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject integralHandle(MessageObject request) throws FormatException;
	 /** 积分兑换
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public MessageObject valueCardHandle(MessageObject request) throws FormatException;
}
