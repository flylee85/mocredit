package com.mocredit.bank.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mocredit.bank.entity.MessageObject;
import com.mocredit.bank.entity.XMLResult;
import com.mocredit.bank.service.PosAction;
import com.mocredit.bank.util.BinarySource;
import com.mocredit.bank.util.PointsDecoder;
import com.mocredit.bank.util.PointsEncoder;
import com.mocredit.bank.util.SocketClient;
import com.mocredit.bank.util.Variable;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Service
public class PosActionImpl implements PosAction {

	private static Logger logger = Logger.getLogger(PosActionImpl.class);
	/**
	 * 民生积分兑换IP
	 */
	private static String pointIP = "198.203.200.24";
	/**
	 * 民生积分兑换PORT
	 */
	private static int pointPort = 5000;
	/**
	 * 民生领奖IP
	 */
	private static String prizeIP = "198.203.200.53";
	/**
	 * 民生领奖PORT
	 */
	private static int prizePort = 5000;

	/**
	 * 联机类交易
	 */
	@Override
	public MessageObject messReceived(MessageObject posRequest) throws Exception {
		/**
		 * 返回使用 11域-终端流水号 60.1域-交易类型码 60.2域-批次号 41域-终端编号 shopid
		 */
		MessageObject termRequest = new MessageObject();
		/** 保存终端报文 */
		BeanUtils.copyProperties(termRequest, posRequest);
		/** 活动类别,1-民生积分、2民生领奖 */
		String cmsbFlag = "1";

		// if("1".equals(cmsbFlag))//1-民生积分；2-民生领奖
		// {//民生积分
		if (posRequest.getMesstype().equals("0200")) {// 消费
			posRequest.setField03_Processing_Code("000000");
			posRequest.setField25_Point_Of_Service_Condition_Mode("04");
			posRequest.setField60_Reserved_Private("22" + posRequest.getField60_Reserved_Private());
		} else if (posRequest.getMesstype().equals("0400")) {// 消费冲正
			posRequest.setField03_Processing_Code("000000");
			posRequest.setField25_Point_Of_Service_Condition_Mode("04");
			posRequest.setField60_Reserved_Private("22" + posRequest.getField60_Reserved_Private());
		} else if (posRequest.getMesstype().equals("0200")) {// 消费撤销
			posRequest.setField03_Processing_Code("200000");
			posRequest.setField25_Point_Of_Service_Condition_Mode("04");
			posRequest.setField60_Reserved_Private("23" + posRequest.getField60_Reserved_Private());
		}
		posRequest.setField49_Currency_Code_Of_Transaction("156");
		/** request对象处理 */
		// MessageObject cmsbSent = trans.action(posRequest,"0",termRequest);
		/** 解码编码初始化 */
		PointsEncoder encoderRemote = new PointsEncoder();
		PointsDecoder decoderProxy = new PointsDecoder();
		/** 存储TPUD+报文头 */
		BinarySource cmsbSource = new BinarySource();
		XMLResult cmsbResult = new XMLResult();
		decoderProxy.decode("00206003050000602200000000000000200000000000000000013131313131313131", cmsbSource,
				cmsbResult, "0");
		/** 编码发送民生银行积分 */
		System.out.println("打印出来：" + posRequest);
		byte[] cmsbSendByte = encoderRemote.encode(cmsbSource, cmsbResult, posRequest, "0");
		String cmsbResponseStr = SocketClient.sentMessage(Variable.MS_INTEGER_IP, Variable.MS_INTEGER_PORT, cmsbSendByte);
		logger.info("msyhResponse[" + cmsbResponseStr + "]");
		// 消费成功
		// String cmsbResponseStr =
		// "00E260838100056022070000000210703E00810ED0801716622623018000001800000000000000010000000514561903073406060604080305290031343536313932363030393635353134313930303030303030303031333035333130303030303030313630253033303530303031202020303330353030303120202020202031353600082200012600364143544E30303030303030393939303030303030303030303030303030303030303030300043435550202020202020202020202020202020202020202020202020202020202020202020202020202020204635353733323638";
		// 消费成功
		// String cmsbResponseStr =
		// "00E260838100056022070000000210703E00810ED0801716622623018000001800000000000000010000001115072703073406060604080305290031353037323732363031303230363231343330303030303030303031333035333130303030303030313630253033303530303031202020303330353030303120202020202031353600082200012600364143544E30303030303030393939303030303030303030303030303030303030303030300043435550202020202020202020202020202020202020202020202020202020202020202020202020202020203438453234433046";
		// xiaofeichexiaochenggong
		// String cmsbResponseStr =
		// "00E060838100056022070000000210703A00810ED080171662262301800000182000000000000001000000151531020307060604080305290031353331303232363031303730363231343330303030303030303031333035333130303030303030313630253033303530303031202020303330353030303120202020202031353600082300012600364143544E30303030303030393939303030303030303030303030303030303030303030300043435550202020202020202020202020202020202020202020202020202020202020202020202020202020204645343132324331";
		// xiaofeichongzheng
		// String
		// cmsbResponseStr="008760838100056022000000000410703A00810AD08011166226230180000018000000000000000100000005145619030712290408030529003134353631393236303130303030303030303030303133303533313030303030303031363025202020202020202020202020202020202020202020202020203135360008220001263746393438363944";
		/** 解码民生银行积分返回报文 */
		MessageObject cmsbResponse = decoderProxy.decode(cmsbResponseStr, cmsbSource, cmsbResult, "0");
		/** 返回MessageObject对象 */
		// MessageObject response = trans.action(cmsbResponse,"1",termRequest);
		return cmsbResponse;
		// return null;
		// }else if(cmsbFlag!=null && cmsbFlag.equals("2")) {//民生领奖
		// if(posRequest.getMesstype().equals("1280")){//消费>领奖
		// posRequest.setMesstype("0700");
		// posRequest.setField03_Processing_Code("000000");
		// /** request对象处理*/
		// MessageObject prizeSentQuery =
		// trans.action(posRequest,"0",termRequest);
		// /** 解码编码初始化*/
		//// PrizeDecoder decoderProxy = new PrizeDecoder();
		//// PrizeEncoder encoderRemote = new PrizeEncoder();
		// PrizeEncoder encoderRemote = new PrizeEncoder(SD_TERM_KEY_dao);
		// PrizeDecoder decoderProxy = new PrizeDecoder(SD_TERM_KEY_dao);
		// /** 存储TPUD+报文头*/
		// BinarySource prizeSource = new BinarySource();
		// XMLResult prizeResult = new XMLResult();
		// decoderProxy.decode("00206000230000602200000000000000200000000000000000013131313131313131",
		// prizeSource, prizeResult, "0");
		// /** 编码发送民生银行领奖*/
		// byte[] prizeSendByteQuery = encoderRemote.encode(prizeSource,
		// prizeResult, prizeSentQuery, "0");
		// String prizeResonseStrQuery = SocketClient.sentMessage(prizeIP,
		// prizePort, prizeSendByteQuery);
		// //95
		//// String prizeResonseStrQuery =
		// "005C60000003056022000000000710203800000AC10410000000000075114358040131343034303132323034393939353030303030303031303030303030303030454D31303031000230300012453030BFA8BAC5B2BBB4E6D40006000002";
		// //00
		//// String prizeResonseStrQuery =
		// "008860000003056022000000000710203800000EC10410000000000117151235040431343034303432323035353531353132333530303030303030303031303030303030303030454D313030310032303130303037303030303030303030313030CCECCCECC3F1C9FAC8D520202020002030313531333232393139383531313131303031300006000002";
		// //领奖查询返回
		// MessageObject prizeResponseQuery =
		// decoderProxy.decode(prizeResonseStrQuery, prizeSource, prizeResult,
		// "0");
		// if(!prizeResponseQuery.getField39_Response_Code().equals("00")){//如果查询失败直接返回pos
		// return prizeResponseQuery;
		// }
		// if(prizeResponseQuery.getField48_Additional_Data().equals("3030")) {
		// prizeResponseQuery.setField39_Response_Code("95");
		// return prizeResponseQuery;
		// }
		// posRequest.setMesstype("0700");
		// posRequest.setField03_Processing_Code("010000");
		// posRequest.setField48_Additional_Data(prizeResponseQuery.getField48_Additional_Data());
		// posRequest.setField54_Balance_Amount(prizeResponseQuery.getField54_Balance_Amount());
		// MessageObject prizeSent = trans.action(posRequest,"0",termRequest);
		// PrizeEncoder encoderRemotePrize = new PrizeEncoder(SD_TERM_KEY_dao);
		// PrizeDecoder decoderProxyPrize = new PrizeDecoder(SD_TERM_KEY_dao);
		// decoderProxy.decode("00206000230000602200000000000000200000000000000000013131313131313131",
		// prizeSource, prizeResult, "0");
		// /** 编码发送民生银行领奖*/
		// byte[] prizeSendByte = encoderRemotePrize.encode(prizeSource,
		// prizeResult, prizeSent, "0");
		// String prizeResonseStr = SocketClient.sentMessage(prizeIP, prizePort,
		// prizeSendByte);
		// //领奖查询返回
		// MessageObject prizeResponse =
		// decoderProxyPrize.decode(prizeResonseStr, prizeSource, prizeResult,
		// "0");
		// //记录日志流水
		// MessageObject response = trans.action(prizeResponse,
		// "1",termRequest);
		// return response;
		// }else if(posRequest.getMesstype().equals("1320")){//消费冲正>领奖冲正
		// posRequest.setMesstype("0720");
		// posRequest.setField03_Processing_Code("010000");
		// /** request对象处理*/
		// MessageObject prizeSent = trans.action(posRequest,"0",termRequest);
		// /** 解码编码初始化*/
		// PrizeDecoder decoderProxy = new PrizeDecoder();
		// PrizeEncoder encoderRemote = new PrizeEncoder();
		// /** 存储TPUD+报文头*/
		// BinarySource prizeSource = new BinarySource();
		// XMLResult prizeResult = new XMLResult();
		// /** 编码发送民生银行领奖*/
		// byte[] prizeSendByte = encoderRemote.encode(prizeSource, prizeResult,
		// prizeSent, "1");
		// String prizeResonseStr = SocketClient.sentMessage(prizeIP, prizePort,
		// prizeSendByte);
		// MessageObject prizeResponse = decoderProxy.decode(prizeResonseStr,
		// prizeSource, prizeResult, "1");
		// //记录日志流水
		// MessageObject response = trans.action(prizeResponse,
		// "1",termRequest);
		// return response;
		// }else {
		// posRequest.setField39_Response_Code("96");
		// return posRequest;
		// }
		// }else{
		// /** 报文类型第三位加1*/
		// String tempStr =
		// String.valueOf(Integer.parseInt(posRequest.getMesstype().substring(2,3))+1);
		// posRequest.setMesstype(StrUtil.replaceIndex(2,
		// posRequest.getMesstype(),tempStr));
		// posRequest.setField39_Response_Code("96");
		// return posRequest;
		// }
	}

	/**
	 * 管理类交易
	 */
	@Override
	public MessageObject manageTran(MessageObject posRequest) throws Exception {
		if (posRequest.getMesstype() != null
				&& (posRequest.getMesstype().equals("0800") || posRequest.getMesstype().equals("0820")
						|| posRequest.getMesstype().equals("0500") || posRequest.getMesstype().equals("0320") ||
						/* 银行数据临时添加联机类交易 */
						posRequest.getMesstype().equals("0200"))) {// 民生积分
			/** request对象处理 */
			// MessageObject cmsbSent = trans.action(posRequest,"1",null);
			/** 解码编码初始化 */
			PointsEncoder encoderRemote = new PointsEncoder();
			PointsDecoder decoderProxy = new PointsDecoder();
			/** 存储TPUD+报文头 */
			BinarySource cmsbSource = new BinarySource();
			XMLResult cmsbResult = new XMLResult();
			decoderProxy.decode("00206003050000602200000000000000200000000000000000013131313131313131", cmsbSource,
					cmsbResult, "0");
			System.out.println(posRequest);
			/** 编码发送民生银行积分 */
			byte[] cmsbSendByte = encoderRemote.encode(cmsbSource, cmsbResult, posRequest, "0");
			String cmsbResponseStr = SocketClient.sentMessage(Variable.MS_INTEGER_IP, Variable.MS_INTEGER_PORT, cmsbSendByte);
			logger.info("msyhResponse[" + cmsbResponseStr + "]");
			/** 解码民生银行积分返回报文 */
			MessageObject cmsbResponse = decoderProxy.decode(cmsbResponseStr, cmsbSource, cmsbResult, "0");
			// 记入流水
			/** 返回MessageObject对象 */
			// return trans.action(cmsbResponse,"1",null);
			return cmsbResponse;
			// }else if(posRequest.getMesstype()!=null && (
			// posRequest.getMesstype().equals("0900")||posRequest.getMesstype().equals("0920")
			// ||posRequest.getMesstype().equals("0720")
			// ||posRequest.getMesstype().equals("0700"))) {//民生领奖管理类交易
			// /** request对象处理*/
			//// MessageObject cmsbSent = trans.action(posRequest,"0",null);
			// /** 解码编码初始化*/
			// PrizeEncoder encoderRemote = new PrizeEncoder(SD_TERM_KEY_dao);
			// PrizeDecoder decoderProxy = new PrizeDecoder(SD_TERM_KEY_dao);
			// /** 存储TPUD+报文头*/
			// BinarySource cmsbSource = new BinarySource();
			// XMLResult cmsbResult = new XMLResult();
			// decoderProxy.decode("00206000230000602200000000000000200000000000000000013131313131313131",
			// cmsbSource, cmsbResult, "0");
			// /** 编码发送民生银行积分*/
			// byte[] cmsbSendByte = encoderRemote.encode(cmsbSource,
			// cmsbResult, posRequest, "0");
			// String cmsbResponseStr = SocketClient.sentMessage(prizeIP,
			// prizePort, cmsbSendByte);
			// //kek下载
			//// String cmsbResponseStr =
			// "006560000003056022000000000910203800000EC00014020000000025172534032531343033323532323034343231373235333430303030303030303031303030303030303030454D313030310012000120303030001634333431343034323464343134333436";
			// //签到
			//// String cmsbResponseStr =
			// "006260000003056022000000000910203800000EC00014000000000058113851033131343033333132323034393431313338353130303030303030303031303030303030303030454D3130303100060000020016334333313033454136464132433537313C3103EA6FA2C571";
			// /** 解码民生银行积分返回报文*/
			// MessageObject cmsbResponse = decoderProxy.decode(cmsbResponseStr,
			// cmsbSource, cmsbResult, "0");
			// //记入流水
			// /** 返回MessageObject对象*/
			// return cmsbResponse;
			// } else {//报文类型支持
			// String tempStr =
			// String.valueOf(Integer.parseInt(posRequest.getMesstype().substring(2,3))+1);
			// posRequest.setMesstype(StrUtil.replaceIndex(2,
			// posRequest.getMesstype(),tempStr));
			// posRequest.setField39_Response_Code("96");
			// return posRequest;
			// }
		}
		return null;
	}
	public static void main(String[] args) {
		
	}
}
