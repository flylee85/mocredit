package com.mocredit.bank.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.bank.entity.MessageObject;
import com.mocredit.bank.entity.XMLResult;

@Service
@Transactional
public class PointsDecoder {
	
	public PointsDecoder() {
	}
	
	private static Logger logger = Logger.getLogger(PointsDecoder.class);
	private static PointsDecodeDetail decodeDetail = new PointsDecodeDetail();
	public static void main(String[] args) throws FormatException {
		PointsDecoder d=new PointsDecoder();
		BinarySource cmsbSource=new BinarySource();
		XMLResult cmsbResult=new XMLResult();
		d.decode("00206003050000602200000000000000200000000000000000013131313131313131", cmsbSource, cmsbResult, "0");
	}
	public MessageObject decode(Object message,BinarySource source,XMLResult result, String flag) throws FormatException {
		org.jdom.Element elementRequest = null;
		MessageObject posRequest = new MessageObject();
		try {
			String byteStr = message.toString().substring(4);
			String charset="utf-8";
			String rootEleName = "request";
			byte[] messageByte = HexBinary.decode(byteStr);
			logger.debug("recv buf hex string is:[" + HexBinary.encode(messageByte) + "]");
			
			/*TPDU 说明：长度为5 个字节。*/
			byte[] tpdu = new byte[5];
			System.arraycopy(messageByte, 0, tpdu, 0, tpdu.length);
			/*报文头 BCD 6字节 解压缩后为12字节长度*/
			byte[] headBuf = new byte[6];
			if(flag!=null && flag.equals("0")) {
				System.arraycopy(messageByte, 5, headBuf, 0, headBuf.length);
				/*添加TPDU和报文头到请求上下文*/
				result.setProperty("headBuf", headBuf);
				source.setProperty("headBuf", headBuf);
			}
			/*添加TPDU和报文头到请求上下文*/
			result.setProperty("tpdu", tpdu);
			/*防止后续处理失败*/
			source.setProperty("tpdu", tpdu);
			byte[] bodyBuf = null;
			if(flag!=null && flag.equals("0")) {
				bodyBuf = new byte[messageByte.length - tpdu.length - headBuf.length];
				System.arraycopy(messageByte,11, bodyBuf, 0, bodyBuf.length);
			} else {
				bodyBuf = new byte[messageByte.length - tpdu.length];
				System.arraycopy(messageByte,5, bodyBuf, 0, bodyBuf.length);
			}
			
			elementRequest = decodeDetail.deCode(bodyBuf, bodyBuf.length, charset, rootEleName);
			/*设置Request-Bean*/
			posRequest.setMesstype(elementRequest.getContent(0).getValue());
			for(int i=0;i<elementRequest.getContentSize();i++) {
				if("02".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField02_Primary_Account_Number(elementRequest.getContent(i).getValue());
				if("03".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField03_Processing_Code(elementRequest.getContent(i).getValue());
				if("04".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField04_Amount_Of_Transactions(elementRequest.getContent(i).getValue());
				if("05".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField05_Mobile_Number(elementRequest.getContent(i).getValue());
				if("11".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField11_System_Trace_Audit_Number(elementRequest.getContent(i).getValue());
				if("12".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField12_Time_Of_Local_Transaction(elementRequest.getContent(i).getValue());
				if("13".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField13_Date_Of_Local_Transaction(elementRequest.getContent(i).getValue());
				if("14".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField14_Date_Of_Expired(elementRequest.getContent(i).getValue());
				if("15".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField15_Date_Of_Settlement(elementRequest.getContent(i).getValue());
				if("22".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField22_Point_Of_Service_Entry_Mode(elementRequest.getContent(i).getValue());
				if("23".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField23_Card_SN(elementRequest.getContent(i).getValue());
				if("25".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField25_Point_Of_Service_Condition_Mode(elementRequest.getContent(i).getValue());
				if("26".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField26_POS_PIN_Capture_Code(elementRequest.getContent(i).getValue());
				if("32".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField32_Acquiring_Institution(elementRequest.getContent(i).getValue());
				if("35".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField35_Track2_Data(elementRequest.getContent(i).getValue());
				if("36".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField36_Track3_Data(elementRequest.getContent(i).getValue());
				if("37".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField37_Retrieval_Reference_Number(elementRequest.getContent(i).getValue());
				if("38".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField38_Authorization_Identification_Response(elementRequest.getContent(i).getValue());
				if("39".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField39_Response_Code(elementRequest.getContent(i).getValue());
				if("41".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField41_Card_Acceptor_Terminal_ID(elementRequest.getContent(i).getValue());
				if("42".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField42_Card_Acceptor_ID(elementRequest.getContent(i).getValue());
				if("44".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField44_Additional_Response_Data(elementRequest.getContent(i).getValue());
				if("48".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField48_Additional_Data(elementRequest.getContent(i).getValue());
				if("49".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField49_Currency_Code_Of_Transaction(elementRequest.getContent(i).getValue());
				if("52".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField52_PIN_Data(elementRequest.getContent(i).getValue());
				if("53".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField53_Security_Control_Information(elementRequest.getContent(i).getValue());
				if("54".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField54_Balance_Amount(elementRequest.getContent(i).getValue());
				if("55".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField55_IC_DATA(elementRequest.getContent(i).getValue());
				if("58".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField58_PBOC_ELECTRONIC_DATA(elementRequest.getContent(i).getValue());
				if("60".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField60_Reserved_Private(elementRequest.getContent(i).getValue());
				if("61".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField61_Original_Message(elementRequest.getContent(i).getValue());
				if("62".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField62_Reserved_Private(elementRequest.getContent(i).getValue());
				if("63".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField63_Reserved_Private(elementRequest.getContent(i).getValue());
				if("64".equals(elementRequest.getContent(i).toString().substring(16,18)))
					posRequest.setField64_MAC(elementRequest.getContent(i).getValue());
			}
			/*向posRequest对象插入交易码属性,如果2域域值为空，将35域2磁道信息填写2域*/
//			if(flag!=null && flag.equals("0"))
			if(!posRequest.getMesstype().equals("0000")){
				posRequest = getBusinessCode(posRequest);
			}
			
			
			/*将messagetype放到source的proprety里*/
			source.setProperty("messtype", posRequest.getMesstype());
			result.setProperty("messtype", posRequest.getMesstype());
			/*验证MAC*/
			posRequest=handleMAC(posRequest, bodyBuf); 
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
//			if (e.getMessage().length() > 2) {
//				throw new FormatException("96", e);
//			}
			throw new FormatException(e.getMessage(), e);
		}
		return posRequest;
	}
	
	/**
	 * 网络管理类交易(报文类型：0320、0500、0620、0800、0820)，交易码由报文类型  + 60.3号域组成。
	 * 联机类（包含冲正类）交易(报文类型：0100、0200、0420),交易码由报文类型  + 交易处理码(3号域)前2位+25号域+60.1组成。
	 * 为联机消费类交易填写2域（for:35域2磁道信息)
	 * @param request
	 * @return POSRequest
	 */
	private MessageObject getBusinessCode(MessageObject request) {
		/* 交易码*/
		StringBuffer tranCode = null;
		/* 报文类型*/
		String messageType = request.getMesstype();
		
		/* 开始拼接交易码*/
		tranCode = new StringBuffer();
		tranCode.append(messageType);
		if("0320".equals(messageType) || "0500".equals(messageType) || "0510".equals(messageType) || "0620".equals(messageType)
		|| "0800".equals(messageType) || "0820".equals(messageType)
		||"0830".equals(messageType)){
			/* 获取60.3域域值*/
			String field60_3 = request.getField60_Reserved_Private().substring(8,11);
			tranCode.append(field60_3);
		}else if( "0810".equals(messageType)){
			tranCode.append("001");
		} else {
			/* 向tranCode中拼接3域的前两位*/
			tranCode.append(request.getField03_Processing_Code().substring(0, 2));
			/* 向tranCode中拼接25域*/
			tranCode.append("00");
			/* 向tranCode中拼接60.1域*/
			tranCode.append(request.getField60_Reserved_Private().substring(0, 2));
			
			/* 向tranCode中如果2域为空*/
			String field2 = request.getField02_Primary_Account_Number();
			/* 向tranCode中获取35域信息 2磁道*/
			String field35 = request.getField35_Track2_Data();
			/* 向tranCode中获取36域信息 3磁道*/
			String field36 = request.getField36_Track3_Data();
			
			/* 处理二域不存在情况*/
			if(null==field2 && null!=field35) {
				logger.info("field2 is null.");
				/* 从二磁道取主账号*/
				request.setField02_Primary_Account_Number(field35.substring(0,field35.indexOf("d")));
			} else if(field2==null && null!=field36) {
				/* 从三磁道取主账号*/
				request.setField02_Primary_Account_Number(field36.substring(2, field36.indexOf("d")));
			}
		}
		logger.info("TranCode:::["+tranCode.toString()+"]");
		/* 向POS请求对象中添加'交易码'属性*/
		request.setTranCode(tranCode.toString());
		return request;
	}
	
	/**
	 * handle MAC
	 * @param request
	 * @param macSource
	 * @return
	 * @throws FormatException
	 */
	private MessageObject handleMAC(MessageObject request, byte[] bodySource) throws FormatException {
		/* 管理类交易不校验MAC，不转加密PIN*/
		String messageType = request.getMesstype();
		if ("0800".equals(messageType)||"0810".equals(messageType)
				|| "0820".equals(messageType)|| "0830".equals(messageType)
			|| "0500".equals(messageType) || "0510".equals(messageType)
			|| "0320".equals(messageType)
			||"0000".equals(messageType)) {
			return request;
		}
		if(request.getField39_Response_Code().equals("00")) {
//			/* 如果64域为空抛出FormatException异常参数"A0"*/
//			String mac = request.getField64_MAC();
//			if(null==mac||mac.equals("")) {
//				logger.debug("Failed to verify the MAC, the lack of a MAC");
//				throw new FormatException("A0");
//			}
//			/** 添加 DATA(messtype---63域之间的字节，减去mac8字节)*/
//			byte[] dataSource = new byte[bodySource.length - 8];
//			System.arraycopy(bodySource, 0, dataSource, 0, dataSource.length);
//			String hql = "from SD_TERM_KEY where TERM_ID='" + request.getField41_Card_Acceptor_Terminal_ID() +
//					"' AND MERCH_ID='" + request.getField42_Card_Acceptor_ID() + "' AND TERM_TYPE='03'";
//			List<SD_TERM_KEY> termkeys = SD_TERM_KEY_dao.find(new Finder(hql));
//			SD_TERM_KEY sdTermKey = termkeys.get(0);
//			String resultMac = MacECBUtils.clacMsyhMac(HexBinary.decode(sdTermKey.getTMK_TAK().substring(0,16)),
//					HexBinary.decode(sdTermKey.getTMK_TAK().substring(16,32)), dataSource);
//			logger.info("resultMac="+resultMac);
//			if(!mac.equals(resultMac)){
//				logger.info("MAC verify failed.");
//				throw new FormatException("A0");
//			}else {
//				logger.info("MAC verify successful.");
//			}
			/*PIN转加密 如果包含52域 就进行加密机解密，核心算法加密 放在具体交易里校验*/
			return request;
		}else {//如果39域返回非00将不校验mac
			return request;
		}
	}
}
