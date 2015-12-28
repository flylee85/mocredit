package cn.mocredit.gateway.posp.xnzd;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.mocredit.gateway.decode.jh.Decoder;
import cn.mocredit.gateway.decode.jh.Encoder;
import cn.mocredit.gateway.posp.bc.binding.BinarySource;
import cn.mocredit.gateway.posp.bc.binding.XMLResult;
import cn.mocredit.gateway.posp.bc.util.FormatException;
import cn.mocredit.gateway.posp.bc.util.MessageObject;
import cn.mocredit.gateway.posp.bc.util.SocketClient;
import cn.mocredit.gateway.wangpos.bo.DemoRetData;
public class testchexiao {
	
	public static MessageObject chexiao(){
		MessageObject response = new MessageObject();
		MessageObject request = new MessageObject();
		Encoder encoder = new Encoder();
		Decoder decoder = new Decoder();
		XMLResult result = new XMLResult();
		BinarySource source = new BinarySource();
		request.setMesstype("0200");
		request.setField02_Primary_Account_Number("6222529917267236");
		request.setField03_Processing_Code("200005");
		request.setField04_Amount_Of_Transactions("000000000100");
		request.setField11_System_Trace_Audit_Number(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField12_Time_Of_Local_Transaction(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField13_Date_Of_Local_Transaction(new SimpleDateFormat("MMdd").format(new Date()));
		request.setField14_Date_Of_Expired("2509");
		request.setField22_Point_Of_Service_Entry_Mode("012");
		request.setField25_Point_Of_Service_Condition_Mode("00");
		request.setField38_Authorization_Identification_Response("S75121");
		request.setField41_Card_Acceptor_Terminal_ID("55550004");
		request.setField42_Card_Acceptor_ID("111111111111119");
		request.setField49_Currency_Code_Of_Transaction("156");
		request.setField60_Reserved_Private("010000");
		request.setField62_Reserved_Private(
				new SimpleDateFormat("HHmmss").format(new Date())+
				new SimpleDateFormat("HHmmss").format(new Date())+
				"380746140745");
		byte[] sentOppaByte = null;
		try {
			decoder.decode("02000d600100010008102000000000000000000001", source, result, "1");
		} catch (FormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sentOppaByte =	encoder.encode(source, result, request, "1");
		try {
			String	oppaResponseStr = SocketClient.sentMessage("182.180.50.168",7665, sentOppaByte);
			System.out.println("返回"+oppaResponseStr);
			decoder.decode(oppaResponseStr, source, result, "1");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
	
	public static MessageObject chongzheng(){
		MessageObject response = new MessageObject();
		MessageObject request = new MessageObject();
		Encoder encoder = new Encoder();
		Decoder decoder = new Decoder();
		XMLResult result = new XMLResult();
		BinarySource source = new BinarySource();
		request.setMesstype("0400");
		request.setField02_Primary_Account_Number("6222529917267236");
		request.setField03_Processing_Code("200005");
		request.setField04_Amount_Of_Transactions("000000000100");
		request.setField11_System_Trace_Audit_Number(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField12_Time_Of_Local_Transaction(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField13_Date_Of_Local_Transaction(new SimpleDateFormat("MMdd").format(new Date()));
		request.setField14_Date_Of_Expired("2509");
		request.setField22_Point_Of_Service_Entry_Mode("012");
		request.setField25_Point_Of_Service_Condition_Mode("00");
		request.setField38_Authorization_Identification_Response("S74973");
		request.setField41_Card_Acceptor_Terminal_ID("55550004");
		request.setField42_Card_Acceptor_ID("111111111111119");
		request.setField49_Currency_Code_Of_Transaction("156");
		request.setField60_Reserved_Private("010000");
		request.setField62_Reserved_Private("143150143150");
		byte[] sentOppaByte = null;
		try {
			decoder.decode("02000d600100010008102000000000000000000001", source, result, "1");
		} catch (FormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sentOppaByte =	encoder.encode(source, result, request, "1");
		try {
			String	oppaResponseStr = SocketClient.sentMessage("182.180.50.168",7665, sentOppaByte);
			System.out.println("返回"+oppaResponseStr);
			decoder.decode(oppaResponseStr, source, result, "1");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
	public static MessageObject xfchongzheng(){
		MessageObject response = new MessageObject();
		MessageObject request = new MessageObject();
		Encoder encoder = new Encoder();
		Decoder decoder = new Decoder();
		XMLResult result = new XMLResult();
		BinarySource source = new BinarySource();
		request.setMesstype("0400");
		request.setField02_Primary_Account_Number("6222529917267236");
		request.setField03_Processing_Code("180005");
		request.setField04_Amount_Of_Transactions("000000000100");
//		request.setField11_System_Trace_Audit_Number(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField11_System_Trace_Audit_Number("183321");
		request.setField12_Time_Of_Local_Transaction(new SimpleDateFormat("HHmmss").format(new Date()));
		request.setField13_Date_Of_Local_Transaction(new SimpleDateFormat("MMdd").format(new Date()));
		request.setField14_Date_Of_Expired("2509");
		request.setField22_Point_Of_Service_Entry_Mode("012");
		request.setField25_Point_Of_Service_Condition_Mode("00");
		request.setField38_Authorization_Identification_Response("S75210");
		request.setField41_Card_Acceptor_Terminal_ID("55550004");
		request.setField42_Card_Acceptor_ID("111111111111119");
		request.setField49_Currency_Code_Of_Transaction("156");
		request.setField60_Reserved_Private("010000");
		request.setField61_Original_Message("00000000040000000000000100000000000000M11BBFDB7D6B2E2CAD4123");
		request.setField62_Reserved_Private("767912152115");
		byte[] sentOppaByte = null;
		try {
			decoder.decode("02000d600100010008102000000000000000000001", source, result, "1");
		} catch (FormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sentOppaByte =	encoder.encode(source, result, request, "1");
		try {
			String	oppaResponseStr = SocketClient.sentMessage("182.180.50.168",7665, sentOppaByte);
			System.out.println("返回"+oppaResponseStr);
			decoder.decode(oppaResponseStr, source, result, "1");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
	public static void main(String[] args) {
//		testchexiao.chexiao();
		testchexiao.xfchongzheng();
	}
}
