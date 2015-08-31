package com.mocredit.bank.util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.bank.entity.MessageObject;
import com.mocredit.bank.entity.TiShopMerchant;
import com.mocredit.bank.entity.XMLResult;
import com.mocredit.bank.service.ShopMerchantService;

@Service
public class PointsEncoder {
	public PointsEncoder() {
	}
	
	private static Logger LOG = Logger.getLogger(PointsEncoder.class);
	private static PointsEncodeDetail encodeDetail = new PointsEncodeDetail();
	@Autowired
	private ShopMerchantService merchanService;
	
	String charset = "utf-8";
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	public byte[] encode(BinarySource source, XMLResult xmlResult,MessageObject request,String flag) {
		byte[] result = null;
		try {
			encodeDetail.encode(request, baos, charset,flag);
			byte[] bodyBuf = baos.toByteArray();
			byte[] bodyByte = null;
			if(flag.equals("0")) {
				if("0200".equals(request.getMesstype())||"0400".equals(request.getMesstype())) {
					byte[] bodyTempBuf = baos.toByteArray();
					byte[] macByte = handleMAC(request, bodyTempBuf);
					bodyByte = new byte[bodyBuf.length+8];
					System.arraycopy(bodyBuf, 0, bodyByte, 0, bodyBuf.length);
					System.arraycopy(macByte, 0, bodyByte, bodyBuf.length, macByte.length);
				} else {
					bodyByte = bodyBuf;
				}
			}else {
				bodyByte = bodyBuf;
			}
			/** 获得tpdu+报文头*/
			byte[] headBuf = genHeadBuf(xmlResult,flag);
			LOG.debug("send headBuf hexstrign is:["+HexBinary.encode(headBuf)+"]");
			LOG.debug("send body hexstrign is:["+HexBinary.encode(bodyByte)+"]");
			byte[] message = new byte[(bodyByte.length+headBuf.length)];
			System.arraycopy(headBuf, 0, message, 0, headBuf.length);
			if(flag!=null && flag.equals("0"))
				System.arraycopy(bodyByte, 0, message, 11, bodyByte.length);
			else if(flag!=null && flag.equals("1"))
				System.arraycopy(bodyByte, 0, message, 5, bodyByte.length);
			//System.out.println("报文长度："+HexBinary.encode(String.format("%04d", headBuf.length + bodyBuf.length).getBytes()));
			/** 报文前添加4位报文长度*/
			ByteBuffer temp = ByteBuffer.allocate(2);
			if(Integer.toHexString(message.length).length()>2){
				int i = Integer.parseInt(Integer.toHexString(message.length).substring(0,1));
				temp.put((byte)i);
				temp.put((byte)message.length);
			}else {
				temp.put((byte)0x00);
				temp.put((byte)message.length);
			}
			
			byte[] messageLen = temp.array();
			result = new byte[(messageLen.length+message.length)];
			System.arraycopy(messageLen, 0, result, 0, messageLen.length);
			System.arraycopy(message, 0, result, 2, message.length);
			LOG.info("Encoder result["+HexBinary.encode(result)+"]");
		} catch (BindingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * generate HeadBuf
	 * @return
	 */
	private byte[] genHeadBuf(XMLResult XMLresult,String flag) {
		byte[] result = null;
		byte[] headBuf =null;
		byte[] tpdu = null;
//		byte[] destination = new byte[2];
//		byte[] sourceByte = new byte[2];
		if(flag!=null && flag.equals("0")) {//报文头处理
			/*拼接headbuf*/
			if(null != XMLresult.getProperty("headBuf")){
				headBuf = (byte[])XMLresult.getProperty("headBuf");
			}else {/*拼接默认headbuf*/
				ByteBuffer temp = ByteBuffer.allocate(6);
				temp.put((byte)0x60);
				temp.put((byte)0x22);
				temp.put((byte)0x10);
				// 规范里保留使用的3个字节
				temp.put((byte)0x00);
				temp.put((byte)0x00);
				temp.put((byte)0x00);
				headBuf = temp.array();
			}
			/*拼接TPDU*/
			if(null != XMLresult.getProperty("tpdu")) {
				tpdu = (byte[])XMLresult.getProperty("tpdu");
//				// 复制源地址 目的地址
//				System.arraycopy(tpdu, 1, destination, 0, destination.length);
//				System.arraycopy(tpdu, 3, sourceByte, 0, sourceByte.length);
//				// 调换源地址目的地址
//				System.arraycopy(sourceByte, 0, tpdu, 1, sourceByte.length);
//				System.arraycopy(destination, 0, tpdu, 3, destination.length);
			}else {/*拼接默认TPDU 雅酷约定6000320000*/
				ByteBuffer temp = ByteBuffer.allocate(5);
				temp.put((byte)0x60);
				temp.put((byte)0x00);
				temp.put((byte)0x32);
				temp.put((byte)0x00);
				temp.put((byte)0x00);
				tpdu = temp.array();
			}
			/*拼接TPDU+报文头*/
			result = new byte[(headBuf.length+tpdu.length)];
			System.arraycopy(tpdu, 0, result, 0, tpdu.length);
			System.arraycopy(headBuf, 0, result, 5, headBuf.length);
		} else if(flag!=null && flag.equals("1")) {//无报文头处理
			/*拼接TPDU*/
			if(null != XMLresult.getProperty("tpdu")) {
				result = (byte[])XMLresult.getProperty("tpdu");
			}else {/*拼接默认TPDU 雅酷约定6000320000*/
				ByteBuffer temp = ByteBuffer.allocate(5);
				temp.put((byte)0x60);
				temp.put((byte)0x00);
				temp.put((byte)0x32);
				temp.put((byte)0x00);
				temp.put((byte)0x00);
				result = temp.array();
			}
		}
		return result;
	}
	
	
	/**
	 * handle MAC
	 * @param request
	 * @param macSource
	 * @return
	 * @throws FormatException
	 */
	private byte[] handleMAC(MessageObject request, byte[] bodySource) throws FormatException {
		byte[] macByte = null;
		/* 管理类交易不校验MAC，不转加密PIN*/
		String messageType = request.getMesstype();
		if ("0800".equals(messageType)||"0500".equals(messageType)
				||"0820".equals(messageType)||"0500".equals(messageType)||"0320".equals(messageType)) {
			return macByte;
		}
		TiShopMerchant merchant = merchanService.getMerchantByTermId(request.getField41_Card_Acceptor_Terminal_ID());
//		String resultMac = MacECBUtils.clacMsyhMac(HexBinary.decode("131383F2573B1CBA9B9E6D54EF75DFD9".substring(0,16)),
//				HexBinary.decode("131383F2573B1CBA9B9E6D54EF75DFD9".substring(16,32)), bodySource);
		String resultMac = MacECBUtils.clacMsyhMac(HexBinary.decode(merchant.getTmkTak().substring(0,16)),
				HexBinary.decode(merchant.getTmkTak().substring(16,32)), bodySource);

		LOG.info("resultMac="+resultMac);
		if(null == resultMac || "".equals(resultMac)){
			LOG.info("mac generate failed.");
			throw new FormatException("A0");
		}else {
			LOG.info("mac generate successful.");
		}
		macByte = HexBinary.decode(resultMac);
		/*PIN转加密 如果包含52域 就进行加密机解密，核心算法加密 放在具体交易里校验*/
		return macByte;
	}
	
	public static void main(String[] args) {
//		System.out.println(String.format("%04d", 60));
//		System.out.println(HexBinary.encode(String.format("%04d", 60).getBytes()));
//		System.out.println(HexBinary.encode(String.format("%04d", 60).getBytes()));
//		String c = "3C";
//		System.out.println(HexBinary.encode(c.getBytes()));
//		System.out.println(HexBinary.encode(Integer.toHexString(Integer.parseInt(String.format("%04d", 60))).getBytes()));
//		System.out.println(HexBinary.encode(Integer.toHexString(60).getBytes()));
		
		int a=97;
//		ByteBuffer temp = ByteBuffer.allocate(2);
//		temp.put((byte)0x00);
//		temp.put((byte)a);
//		byte[] b = temp.array();
		
//		System.out.println(Integer.toHexString(a));
//		System.out.println(Integer.toHexString(a).toString().length());
//		if(Integer.toHexString(a).length()>2){
//			ByteBuffer temp = ByteBuffer.allocate(2);
//			int i = Integer.parseInt(Integer.toHexString(a).substring(0,1));
//			temp.put((byte)i);
//			temp.put((byte)a);
//			byte[] b = temp.array();
//			System.out.println(HexBinary.encode(b));
//		}else{
//			System.out.println("等于或小于2");
//			ByteBuffer temp = ByteBuffer.allocate(2);
//			temp.put((byte)0x00);
//			temp.put((byte)a);
//			byte[] messageLen = temp.array();
//			System.out.println(HexBinary.encode(messageLen));
//		}
//		
		int message = 318;
		System.out.println(Integer.toHexString(message));
		ByteBuffer temp = ByteBuffer.allocate(2);
		if(Integer.toHexString(message).length()>2){
			int i = Integer.parseInt(Integer.toHexString(message).substring(0,1));
			temp.put((byte)i);
			temp.put((byte)message);
		}else {
			temp.put((byte)0x00);
			temp.put((byte)message);
		}
		
		byte[] messageLen = temp.array();
		System.out.println(HexBinary.encode(messageLen));
		
//		String s = HexBinary.BytesToStr(BCD.str2Bcd("003c600003000060210000000008000020000000C00012000059313034303030363730303030303030303030303030303400110000000100300003303120"));
//		System.out.println(s);
	}
}
