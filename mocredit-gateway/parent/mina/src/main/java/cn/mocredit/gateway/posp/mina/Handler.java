package cn.mocredit.gateway.posp.mina;

import cn.mocredit.gateway.message.CxForJson;
import cn.mocredit.gateway.message.HxForJson;
import cn.mocredit.gateway.message.MessageObject;
import cn.mocredit.gateway.posp.bc.binding.BinarySource;
import cn.mocredit.gateway.posp.bc.binding.XMLResult;
import cn.mocredit.gateway.posp.bc.util.HexBinary;
import cn.mocredit.gateway.posp.bc.util.SocketClient;
import cn.mocredit.gateway.posp.service.PospService;
import cn.mocredit.gateway.util.HttpUtil;
import cn.mocredit.gateway.util.JacksonJsonMapper;
import cn.mocredit.gateway.decode.Trancsct;
import cn.mocredit.gateway.decode.qr.Decoder;
import cn.mocredit.gateway.decode.qr.Encoder;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
@Component
public class Handler extends IoHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PospService pospService;
    @Autowired
    private Trancsct trancsct;

    @Value("${mina.verifyCodeUrl}")
    private String verifyCodeUrl;

	@Override
	public void sessionCreated(IoSession session) {
		// 显示客户端的ip和端口
		logger.info(session.getRemoteAddress().toString());
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		/*入口参数进来*/
		/**
		 * 获取报文头标识位，交易分流处理
		 * 报文头-保留使用-N6
		 * 雅高卡-xx0002
		 * 雅酷卡-xx0003
		 * 二维码消费-xx0004
		 * 电子卷兑换-xx0005
		 * 积分兑换-xx0006
		 * pos终端参数管理-xx0009
		 * 
		 */
		String bodyStr = message.toString().substring(22,26);
		/**
		 * 声明业务处理类
		 */
		byte[] posResponse = null;
		if("0002".equals(bodyStr)){/*储值卡-雅高*/
			logger.info("雅高储值卡开始["+message+"]");
			byte[] sendByte = null;
			String ResponseStr = SocketClient.sentMessage("219.238.232.139", 9125, sendByte);
			posResponse= HexBinary.decode(ResponseStr);
		}else if("0003".equals(bodyStr)){/*储值卡-雅酷*/
			logger.info("雅酷储值卡开始["+message+"]");
			String ResponseStr = null;
			MessageObject handRequest = new MessageObject();
//			Trancsct tran = new TrancsctImpl();
			Decoder decoder = new Decoder();
			Encoder encoder = new Encoder();
			XMLResult result = new XMLResult();
			BinarySource source = new BinarySource();
			System.out.println("开始decoder");
			handRequest = trancsct.valueCardHandle(decoder.decode(message, source, result, "0"));
			if(handRequest.getMesstype()!="0810") {
				byte[] sendByte = null;
				ResponseStr = SocketClient.sentMessage("219.238.232.139", 9125, sendByte);
				posResponse= HexBinary.decode(ResponseStr);
			}else {
				posResponse = encoder.encode(source, result, handRequest, "0");
			}
		}else if("0004".equals(bodyStr)){/*二维码*/
			logger.info("二维码开始["+message+"]");
			String ResponseStr = null;
			MessageObject handRequest = new MessageObject();
//			Trancsct tran = new TrancsctImpl();
			Decoder decoder = new Decoder();
			Encoder encoder = new Encoder();
			XMLResult result = new XMLResult();
			BinarySource source = new BinarySource();
			System.out.println("开始decoder");
			handRequest = trancsct.qrHandle(decoder.decode(message, source, result, "0"));
			if(handRequest.getMesstype()!="1110") {
				byte[] sendByte = null;
				ResponseStr = SocketClient.sentMessage("219.238.232.139", 9125, sendByte);
				posResponse= HexBinary.decode(ResponseStr);
			}else if("1110".equals(handRequest.getMesstype())) {
				posResponse = encoder.encode(source, result, handRequest, "0");
			}
		}else if("0005".equals(bodyStr)){/*电子券兑换*/
			logger.info("电子券兑换开始["+message+"]");
			String ResponseStr = null;
			MessageObject handRequest = new MessageObject();
//			Trancsct tran = new TrancsctImpl();
			Decoder decoder = new Decoder();
			Encoder encoder = new Encoder();
			XMLResult result = new XMLResult();
			BinarySource source = new BinarySource();
			System.out.println("开始decoder");
			handRequest = trancsct.qrHandle(decoder.decode(message, source, result, "0"));
			if("1260".equals(handRequest.getMesstype())) {
				byte[] sendByte = null;
				ResponseStr = SocketClient.sentMessage("219.238.232.139", 9125, sendByte);
				posResponse= HexBinary.decode(ResponseStr);
			}else if("1110".equals(handRequest.getMesstype()) || "1270".equals(handRequest.getMesstype())){
				posResponse = encoder.encode(source, result, handRequest, "0");
			}
		}else if("0006".equals(bodyStr)){/*积分兑换*/
			System.out.println("application的访问地址："+verifyCodeUrl);
			String ResponseStr = null;
			MessageObject handRequest = new MessageObject();
			Decoder decoder = new Decoder();
			Encoder encoder = new Encoder();
			XMLResult result = new XMLResult();
			BinarySource source = new BinarySource();
			System.out.println("开始decoder");
			handRequest = trancsct.integralHandle(decoder.decode(message, source, result, "0"));
			System.out.println("解包："+handRequest);
			if("1290".equals(handRequest.getMesstype())){
				StringBuffer bUrl = new StringBuffer();
				bUrl.append("?bType=01");
				if(handRequest.getField09()!=null){
					bUrl.append("&aId="+handRequest.getField09());
				}else{
					bUrl.append("&aId=");
				}
				bUrl.append("&aId="+handRequest.getField09());
				bUrl.append("&sId=222");
				if(handRequest.getField02_Primary_Account_Number()!=null && handRequest.getField02_Primary_Account_Number().length()>6){
					bUrl.append("&cBin="+handRequest.getField02_Primary_Account_Number().substring(0, 6));
				}else{
					bUrl.append("&cBin=");
				}
				
				bUrl.append("&ip="+session.getRemoteAddress());
				if(handRequest.getField06()!=null){
					bUrl.append("&code="+handRequest.getField06());
				}else{
					bUrl.append("&code=");
				}
				try {
					
				} catch (Exception e) {
					
				}
				JSONObject jSONObject = new JSONObject();
				//---------------------------------------------xf
				jSONObject.put("amount", "");
				jSONObject.put("useCount",Integer.parseInt(handRequest.getField04_Amount_Of_Transactions()));
				jSONObject.put("device",handRequest.getField41_Card_Acceptor_Terminal_ID());
				jSONObject.put("storeId","8110");
				jSONObject.put("storeName","老机具");
				jSONObject.put("requestSerialNumber",handRequest.getField11_System_Trace_Audit_Number());
				jSONObject.put("shopId","222");
				jSONObject.put("shopName","222222");
				jSONObject.put("code",HexBinary.toStringHex(handRequest.getField06().length()%2<1?handRequest.getField06():"3"+handRequest.getField06()));
				String ss = HttpUtil.httpForJson("http://192.168.1.10:8080/verify_code"+"/ActivityCode/verifyCode", jSONObject);
				HxForJson resJson = JacksonJsonMapper.jsonToObject(ss, HxForJson.class);

				if(resJson.getSuccess().equals("true")) {
					handRequest.setField39_Response_Code("00");
					StringBuffer f5= new StringBuffer();
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getTicketTitle()).length()/2).length()>1)
						f5.append("01"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getTicketTitle()).length()/2)+
								HexBinary.str2HexStr(resJson.getData().getTicketTitle()));
					else
						f5.append("010"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getTicketTitle()).length()/2)+
								HexBinary.str2HexStr(resJson.getData().getTicketTitle()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()).length()/2).length()>1)
						f5.append("02"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()).length()/2)+
							HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()));
					else
						f5.append("020"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()).length()/2)+
								HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2).length()>1)
						f5.append("03"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					else
						f5.append("030"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2).length()>1)
						f5.append("04"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					else
						f5.append("040"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)).length()/2).length()>1)
						f5.append("05"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)));
					else
						f5.append("050"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)));
					handRequest.setField05_Mobile_Number(f5.toString());
				}else if(resJson.getSuccess().equals("false")){
					handRequest.setField39_Response_Code(resJson.getErrorCode());
					
				}else{
					handRequest.setField39_Response_Code("98");
				}
				handRequest.setField06(null);
				posResponse = encoder.encode(source, result, handRequest, "0");
			}else if("1410".equals(handRequest.getMesstype())){
				StringBuffer bUrl = new StringBuffer();
				bUrl.append("?bType=01");
				if(handRequest.getField09()!=null){
					bUrl.append("&aId="+handRequest.getField09());
				}else{
					bUrl.append("&aId=");
				}
				bUrl.append("&aId="+handRequest.getField09());
				bUrl.append("&sId=222");
				if(handRequest.getField02_Primary_Account_Number()!=null && handRequest.getField02_Primary_Account_Number().length()>6){
					bUrl.append("&cBin="+handRequest.getField02_Primary_Account_Number().substring(0, 6));
				}else{
					bUrl.append("&cBin=");
				}
				
				bUrl.append("&ip="+session.getRemoteAddress());
				if(handRequest.getField06()!=null){
					bUrl.append("&code="+handRequest.getField06());
				}else{
					bUrl.append("&code=");
				}
				JSONObject jSONObject = new JSONObject();
				jSONObject.put("device",handRequest.getField41_Card_Acceptor_Terminal_ID());
				jSONObject.put("requestSerialNumber",handRequest.getField61_Original_Message().substring(6,12));
				jSONObject.put("verifyType", "2");
				if(handRequest.getField06()!=null && handRequest.getField06().equals(""))
					jSONObject.put("code", HexBinary.toStringHex(handRequest.getField06().length()%2<1?handRequest.getField06():"3"+handRequest.getField06()));
				else
					jSONObject.put("code", "");
				//				HttpUtil.httpForJson(verifyCodeUrl+"ActivityCode/verifyCode"+bUrl, jSONObject);
				CxForJson resJson = JacksonJsonMapper.jsonToObject(HttpUtil.httpForJson("http://192.168.1.10:8080/verify_code"+"/ActivityCode/correctOrRevokeActivityCode", jSONObject),CxForJson.class);
				if(resJson.getSuccess().equals("true")) {
					handRequest.setField39_Response_Code("00");
					StringBuffer f5= new StringBuffer();
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getTicketTitle()).length()/2).length()>1)
						f5.append("01"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getTicketTitle()).length()/2)+
								HexBinary.str2HexStr(resJson.getData().getTicketTitle()));
					else
						f5.append("010"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getTicketTitle()).length()/2)+
								HexBinary.str2HexStr(resJson.getData().getTicketTitle()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()).length()/2).length()>1)
						f5.append("02"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()).length()/2)+
							HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()));
					else
						f5.append("020"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()).length()/2)+
								HexBinary.str2HexStr(resJson.getData().getIssueEnterpriseName()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2).length()>1)
						f5.append("03"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					else
						f5.append("030"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2).length()>1)
						f5.append("04"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					else
						f5.append("040"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getActivityName()).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getActivityName()));
					if(Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)).length()/2).length()>1)
						f5.append("05"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)));
					else
						f5.append("050"+Integer.toHexString(HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)).length()/2)
								+HexBinary.str2HexStr(resJson.getData().getEndTime().substring(0,10)));
					handRequest.setField05_Mobile_Number(f5.toString());
				}else {
					handRequest.setField39_Response_Code("98");
				}
				handRequest.setField06(null);
				posResponse = encoder.encode(source, result, handRequest, "0");
			}else if("1330".equals(handRequest.getMesstype())){
				StringBuffer bUrl = new StringBuffer();
				bUrl.append("?bType=01");
				if(handRequest.getField09()!=null){
					bUrl.append("&aId="+handRequest.getField09());
				}else{
					bUrl.append("&aId=");
				}
				bUrl.append("&aId="+handRequest.getField09());
				bUrl.append("&sId=222");
				if(handRequest.getField02_Primary_Account_Number()!=null && handRequest.getField02_Primary_Account_Number().length()>6){
					bUrl.append("&cBin="+handRequest.getField02_Primary_Account_Number().substring(0, 6));
				}else{
					bUrl.append("&cBin=");
				}
				
				bUrl.append("&ip="+session.getRemoteAddress());
				if(handRequest.getField06()!=null){
					bUrl.append("&code="+handRequest.getField06());
				}else{
					bUrl.append("&code=");
				}
				JSONObject jSONObject = new JSONObject();
				jSONObject.put("device",handRequest.getField41_Card_Acceptor_Terminal_ID());
				jSONObject.put("requestSerialNumber",handRequest.getField38_Authorization_Identification_Response() );
				jSONObject.put("verifyType", "1");
				if(handRequest.getField06()!=null && handRequest.getField06().equals(""))
					jSONObject.put("code", HexBinary.toStringHex(handRequest.getField06().length()%2<1?handRequest.getField06():"3"+handRequest.getField06()));
				else
					jSONObject.put("code", "");
				String tempStr = HttpUtil.httpForJson("http://192.168.1.10:8080/verify_code"+"/ActivityCode/correctOrRevokeActivityCode", jSONObject);
				System.out.println("验码返回："+tempStr);
				CxForJson resJson = JacksonJsonMapper.jsonToObject(tempStr,CxForJson.class);
				if(resJson.getSuccess().equals("true")) {
					handRequest.setField39_Response_Code("00");
				}else {
					handRequest.setField39_Response_Code("98");
				}
				handRequest.setField06(null);
				posResponse = encoder.encode(source, result, handRequest, "0");
			}
		}else if("0009".equals(bodyStr)){/*终端参数管理*/
			logger.info("终端参数管理开始["+message+"]");
			byte[] sendByte = null;
			String ResponseStr = SocketClient.sentMessage("219.238.232.139", 9125, sendByte);
			posResponse= HexBinary.decode(ResponseStr);
		}
		
		/** 返回pos终端*/
		logger.info("resposne POS["+HexBinary.encode(posResponse)+"]");
		session.write(posResponse);
	}
}
