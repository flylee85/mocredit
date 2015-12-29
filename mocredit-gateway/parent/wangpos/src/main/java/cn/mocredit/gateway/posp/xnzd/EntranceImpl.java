package cn.mocredit.gateway.posp.xnzd;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mocredit.gateway.data.entities.MpTempKey;
import cn.mocredit.gateway.data.repository.MpTempKeyRepository;
import cn.mocredit.gateway.decode.jh.Decoder;
import cn.mocredit.gateway.decode.jh.Encoder;
import cn.mocredit.gateway.posp.bc.binding.BinarySource;
import cn.mocredit.gateway.posp.bc.binding.XMLResult;
import cn.mocredit.gateway.posp.bc.util.FormatException;
import cn.mocredit.gateway.posp.bc.util.MessageObject;
import cn.mocredit.gateway.posp.bc.util.SocketClient;
import cn.mocredit.gateway.util.JiaoHangCodeInfo;
import cn.mocredit.gateway.wangpos.bo.DemoData;
import cn.mocredit.gateway.wangpos.bo.DemoRetData;

@Service
public class EntranceImpl implements Entrance {
	@Autowired
	private MpTempKeyRepository mpTempKeyRepository;
//	@Autowired
//	private Decoder decoder;
//	@Autowired
//	private Encoder encoder;
	
	private String MERCH_ID;
	private String MERCH_TYPE;
	private String TERM_ID;
//	private String jiaoHangServerIp = "180.169.17.168";
//	private int port = 7666;
	private String jiaoHangServerIp = "182.180.50.168";
	private int port = 7665;
	
	public DemoRetData jhSign(DemoData demoData) {
		Encoder encoder1 = new Encoder();
		Decoder decoder1 = new Decoder();
		DemoRetData demoRetData = new DemoRetData();
		MessageObject request = new MessageObject();
		MessageObject response = new MessageObject();
		XMLResult result = new XMLResult();
		BinarySource source = new BinarySource();
		request.setMesstype("0820");
		request.setTranCode("082000");
		request.setField03_Processing_Code("910000");
		request.setField11_System_Trace_Audit_Number("000111");
		request.setField12_Time_Of_Local_Transaction(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField13_Date_Of_Local_Transaction(new SimpleDateFormat("MMdd").format(new Date()));
		request.setField25_Point_Of_Service_Condition_Mode("00");
		request.setField41_Card_Acceptor_Terminal_ID("55550004");
		request.setField42_Card_Acceptor_ID("111111111111119");
//		request.setField41_Card_Acceptor_Terminal_ID("88888883");
//		request.setField42_Card_Acceptor_ID("002777111111111");
		request.setField60_Reserved_Private("010000");
		request.setField61_Original_Message("0000000000");
		request.setField64_MAC("4236463145363644");
		byte[] sentOppaByte = null;
		try {
			decoder1.decode("02000d600100010008102000000000000000000001", source, result, "1");
		} catch (FormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sentOppaByte =	encoder1.encode(source, result, request, "1");
		try {
//			String	oppaResponseStr = SocketClient.sentMessage(jiaoHangServerIp,port, sentOppaByte);
			String	oppaResponseStr = "020305600100010008302038008002F00009910000000111140244082600303035353535303030343131313131313131313131313131390011BBFDB7D6B2E2CAD431323308896BF8A992378A90022130343233313731343232303030313232BBFDB7D6504F53B2E2CAD4B9E3B8E665646677656677233130303130352331313031313232323232B2FAC6B7C3E8CAF63233313120203130303130303232313423313130313132333435364141412020202020202020202020313030303130303030312331313031313332343337B2E2CAD4C9CCC6B7414243202020313030333231303030322331313031373737373737B5E7B7E7C9C831323220202020203130303030313030393823313130314B4643303031C8ABB3A1B2FAC6B7CDA8B6D220203030303030303030303123710F12498ED0DBE30384000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
			System.out.println("返回报文"+oppaResponseStr);
			response = decoder1.decode(oppaResponseStr, source, result,"1");
			if(response.getField39_Response_Code()!=null || !response.getField39_Response_Code().equals("")&&response.getField39_Response_Code().length()==2){
				//如果签到39返回00 记录密钥表更新mac密钥更新批次号更新pos流水号
//				MpTempKey key = new MpTempKey();
//				key = mpTempKeyRepository.searchBySomeId("", "", "").get(0);
				
			}else{
				response.setField39_Response_Code("98");
			}
		} catch (Exception e) {
			response.setField39_Response_Code("98");
			e.printStackTrace();
		}
		// Trancsct tran = new TrancsctImpl();
		return demoRetData;

	}
	
	@Override
	public DemoRetData signOut(DemoData demoData) {
		Encoder encoder1 = new Encoder();
		Decoder decoder1 = new Decoder();
		DemoRetData demoRetData = new DemoRetData();
		MessageObject request = new MessageObject();
		MessageObject response = new MessageObject();
		XMLResult result = new XMLResult();
		BinarySource source = new BinarySource();
		request.setMesstype("0820");
		request.setTranCode("082000");
		request.setField03_Processing_Code("920000");
		request.setField11_System_Trace_Audit_Number("000023");
		request.setField12_Time_Of_Local_Transaction(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField13_Date_Of_Local_Transaction(new SimpleDateFormat("MMdd").format(new Date()));
		request.setField25_Point_Of_Service_Condition_Mode("00");
		request.setField41_Card_Acceptor_Terminal_ID("55550004");
		request.setField42_Card_Acceptor_ID("111111111111119");
		request.setField60_Reserved_Private("010000");
		request.setField61_Original_Message("0000000000");
		request.setField62_Reserved_Private("000001000023");
		System.out.println(request);
		byte[] sentOppaByte = null;
		try {
			decoder1.decode("02000d600100010008102000000000000000000001", source, result, "1");
		} catch (FormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sentOppaByte =	encoder1.encode(source, result, request, "1");
		try {
			String	oppaResponseStr = SocketClient.sentMessage(jiaoHangServerIp,port, sentOppaByte);
			System.out.println("返回码："+oppaResponseStr);
			response = decoder1.decode(oppaResponseStr, source, result,"1");
			if(response.getField39_Response_Code()!=null || !response.getField39_Response_Code().equals("")&&response.getField39_Response_Code().length()==2){
				//如果签到39返回00 记录密钥表更新mac密钥更新批次号更新pos流水号
//				MpTempKey key = new MpTempKey();
//				key = mpTempKeyRepository.searchBySomeId("", "", "").get(0);
				
			}else{
				response.setField39_Response_Code("98");
			}
		} catch (Exception e) {
			response.setField39_Response_Code("98");
			e.printStackTrace();
		}
		// Trancsct tran = new TrancsctImpl();
		return demoRetData;

	}

	/**
	 * 积分消费
	 * 
	 * @param dEmodata
	 * @return
	 */
	@Override
	public DemoRetData xf(DemoData dEmodata) {
		MessageObject xfcx = xfcx(dEmodata);
		DemoRetData demoRetData = new DemoRetData();
		if(!"00".equals(xfcx.getField39_Response_Code())){
			demoRetData.setState("error");
			demoRetData.setErrorCode(xfcx.getField39_Response_Code());
			demoRetData.setErrorMes(JiaoHangCodeInfo.JIAO_HANG_XIANG_YING_MA.get(xfcx.getField39_Response_Code()));
			return demoRetData;

		}
		if(xfcx.getField39_Response_Code().equals("00")) {
			Encoder encoder1 = new Encoder();
			Decoder decoder1 = new Decoder();
			MessageObject response = new MessageObject();
			MessageObject request = new MessageObject();
			XMLResult result = new XMLResult();
			BinarySource source = new BinarySource();
			request.setMesstype("0200");
			request.setField02_Primary_Account_Number(dEmodata.getCardNo());
//			request.setField02_Primary_Account_Number("6222529917267236");
			request.setField03_Processing_Code("180005");
			//表中查询当前批次的流水号
			request.setField11_System_Trace_Audit_Number(new SimpleDateFormat("HHmmss").format(new Date()));
			//固定位数12位如果不够前补0
//			request.setField04_Amount_Of_Transactions(qianBuQi(dEmodata.getAmt(), "0", 12));
			request.setField04_Amount_Of_Transactions(qianBuQi("100", "0", 12));
			request.setField12_Time_Of_Local_Transaction(new SimpleDateFormat("HHmmss").format(new Date()));
			request.setField13_Date_Of_Local_Transaction(new SimpleDateFormat("MMdd").format(new Date()));
//			request.setField14_Date_Of_Expired("2509");
			request.setField14_Date_Of_Expired(dEmodata.getExp_date().substring(0,4));
			request.setField22_Point_Of_Service_Entry_Mode("012");
			request.setField25_Point_Of_Service_Condition_Mode("00");
			request.setField41_Card_Acceptor_Terminal_ID("55550004");
			request.setField42_Card_Acceptor_ID("111111111111119");
//			request.setField41_Card_Acceptor_Terminal_ID("88888883");
//			request.setField42_Card_Acceptor_ID("002777111111111");
			//先两位00+（12位的）request.setField11_System_Trace_Audit_Number("");
			request.setField49_Currency_Code_Of_Transaction("156");
			request.setField54_Balance_Amount("7777771");
			request.setField60_Reserved_Private("010000");
//			request.setField61_Original_Message("00000000000004000000000000010000000000M11BBFDB7D6B2E2CAD4313233");
			request.setField61_Original_Message("00000000040000000000000100000000000000M11BBFDB7D6B2E2CAD4123");
			request.setField62_Reserved_Private(
					(new Random().nextInt(800000)+100000)
					+
					new SimpleDateFormat("HHmmss").format(new Date()));
			try {
				decoder1.decode("02000d600100010008102000000000000000000001", source, result, "1");
			} catch (FormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//表中查询当前6位批次号+6位流水号（共12位）
			byte[] sentOppaByte = null;
			sentOppaByte =	encoder1.encode(source, result, request, "1");
			try {
				
				String	oppaResponseStr = SocketClient.sentMessage(jiaoHangServerIp,port, sentOppaByte);
				System.out.println("fanhui"+oppaResponseStr);
				response = decoder1.decode(oppaResponseStr, source, result,"1");
				if(response.getField39_Response_Code()!=null || response.getField39_Response_Code().equals("")&&response.getField39_Response_Code().length()==2){
					//判断查询结果是否为00成功如果成功继续调用积分消费方法，否则返回终端终止交易
					if(response.getField39_Response_Code()!=null||!response.getField39_Response_Code().equals("")&&response.getField39_Response_Code().equals("00")){//查询结果00
						//记录日志
						//返回旺pos终端
//						demoRetData.setState("ok");
//						demoRetData.setErrorCode(response.getField39_Response_Code());
//						demoRetData.setErrorMes("");
						
					}else if(response.getField39_Response_Code().equals("00")&&response.getField39_Response_Code().length()==2){//消费结果非00
						//记录日志
						//返回旺pos终端
//						demoRetData.setState("error");
//						demoRetData.setErrorCode(response.getField39_Response_Code());
//						demoRetData.setErrorMes("");
					}else{
						//记录日志
						//返回旺pos终端
//						demoRetData.setState("error");
//						demoRetData.setErrorCode("98");
//						demoRetData.setErrorMes("通讯超时，交易失败");
					}
				}else{
					response.setField39_Response_Code("98");
				}
			} catch (Exception e) {
				response.setField39_Response_Code("98");
				e.printStackTrace();
			}
			if(response.getField39_Response_Code().equals("00")) {
				
			}
//			return demoRetData;
			demoRetData.setState("ok");
			demoRetData.setErrorMes(JiaoHangCodeInfo.JIAO_HANG_XIANG_YING_MA.get(xfcx.getField39_Response_Code()));
			demoRetData.setErrorCode(xfcx.getField39_Response_Code());
			return demoRetData;
		}else {
			demoRetData.setState("error");
			demoRetData.setErrorCode(xfcx.getField39_Response_Code());
			demoRetData.setErrorMes(JiaoHangCodeInfo.JIAO_HANG_XIANG_YING_MA.get(xfcx.getField39_Response_Code()));
			return demoRetData;
		}
		
	}

	/**
	 * 积分撤销
	 * 
	 * @param dEmodata
	 * @return
	 */
	@Override
	public DemoRetData cx(DemoData dEmodata) {
		DemoRetData demoRetData = new DemoRetData();

		return demoRetData;
	}

	/**
	 * 积分冲正
	 * 
	 * @param dEmodata
	 * @return
	 */
	@Override
	public DemoRetData cz(DemoData dEmodata) {
		DemoRetData demoRetData = new DemoRetData();

		return demoRetData;
	}
	/**
	 * 积分消费查询
	 */
	@Override
	public MessageObject xfcx(DemoData dEmodata) {
		MessageObject response = new MessageObject();
		try {
			Encoder encoder1 = new Encoder();
			Decoder decoder1 =  new Decoder();
//			MessageObject response = new MessageObject();
			MessageObject request = new MessageObject();
			XMLResult result = new XMLResult();
			BinarySource source = new BinarySource();
			request.setMesstype("0200");
		request.setField02_Primary_Account_Number(dEmodata.getCardNo());
//			request.setField02_Primary_Account_Number("6222529917267236");
			request.setField03_Processing_Code("300002");
			//表中查询当前批次的流水号
			
			request.setField11_System_Trace_Audit_Number(new SimpleDateFormat("HHmmss").format(new Date()));
			//固定位数12位如果不够前补0
//		request.setField04_Amount_Of_Transactions(qianBuQi(dEmodata.getAmt(), "0", 12));
			request.setField04_Amount_Of_Transactions(qianBuQi("100", "0", 12));
			request.setField12_Time_Of_Local_Transaction(new SimpleDateFormat("HHmmss").format(new Date()));
			request.setField13_Date_Of_Local_Transaction(new SimpleDateFormat("MMdd").format(new Date()));
//		request.setField14_Date_Of_Expired("2509");
			request.setField22_Point_Of_Service_Entry_Mode("012");
			request.setField25_Point_Of_Service_Condition_Mode("00");
			request.setField41_Card_Acceptor_Terminal_ID("55550004");
			request.setField42_Card_Acceptor_ID("111111111111119");
//		request.setField41_Card_Acceptor_Terminal_ID("88888883");
//		request.setField42_Card_Acceptor_ID("002777111111111");
			//先两位00+（12位的）request.setField11_System_Trace_Audit_Number("");
			request.setField49_Currency_Code_Of_Transaction("156");
			request.setField54_Balance_Amount("777777N");
			request.setField60_Reserved_Private("010000");
			request.setField60_Reserved_Private("010000");
			request.setField61_Original_Message("00");
//		request.setField62_Reserved_Private("000022000070");
			request.setField62_Reserved_Private(
					new SimpleDateFormat("HHmmss").format(new Date())+
					new SimpleDateFormat("HHmmss").format(new Date()));
			try {
				decoder1.decode("02000d600100010008102000000000000000000001", source, result, "1");
			} catch (FormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//表中查询当前6位批次号+6位流水号（共12位）
			byte[] sentOppaByte = null;
			sentOppaByte =	encoder1.encode(source, result, request, "1");
			try {
				
//				String	oppaResponseStr = SocketClient.sentMessage(jiaoHangServerIp,port, sentOppaByte);
				String oppaResponseStr = "02016960010001000210703800800AC0041D16622252991726723630000200000000010013591414002208260035353030303439373531313030303535353530303034313131313131313131313131313139003630303030303030343030303030303030303030303031303030303030303030303030303000034E3737001730302020202020202020202020202020200018313335393134303030303030303030303030E3BF51B58FDCEA5D03AB0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
				System.out.println("返回"+oppaResponseStr);
				response = decoder1.decode(oppaResponseStr, source, result,"1");
				if(response.getField39_Response_Code()!=null || response.getField39_Response_Code().equals("")&&response.getField39_Response_Code().length()==2){
					//判断查询结果是否为00成功如果成功继续调用积分消费方法，否则返回终端终止交易
					if(response.getField39_Response_Code()!=null||!response.getField39_Response_Code().equals("")&&response.getField39_Response_Code().equals("00")){//查询结果00
						//调用消费接口
						
						
						
					}else if(response.getField39_Response_Code()!=null||!response.getField39_Response_Code().equals("")&&response.getField39_Response_Code().equals("00")){//查询结果非00
						//记录日志
						//返回旺pos终端
					}
				}else{
					response.setField39_Response_Code("98");
				}
			} catch (Exception e) {
				response.setField39_Response_Code("98");
				e.printStackTrace();
			}
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return response;
		}
		
	}
	/**
	 * @param str	待补齐字符串
	 * @param zhanWeiFu 占位符
	 * @param zongChang	总长度
	 * @return
	 */
	public static String qianBuQi(String str, String zhanWeiFu,int zongChang){
		if(str.length()>=zongChang){
			return str;
		}
		String zhanWei = range(0, zongChang - str.length()).mapToObj(i -> zhanWeiFu).collect(joining());
		return zhanWei+str;
	}
	public static void main(String[] args) {
		EntranceImpl impl = new EntranceImpl();
		DemoData dd = new DemoData();
		dd.setActivitId("111111");
		dd.setAmt("2");
		dd.setCardNo("6226123456789012456789");
		dd.setExp_date("2501");
//		impl.jhSign(dd);
//		impl.signOut(dd);
		
		System.out.println("##################"+impl.xfcx(dd).getField39_Response_Code());
//		impl.xf(dd);
	}
}
