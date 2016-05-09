package cn.mocredit.posp.main;

import org.apache.log4j.Logger;

import cn.mocredit.posp.bc.util.MessageObject;

public class App {
	private static Logger logger = Logger.getLogger(App.class);
	public static void main(String[] args) {
		PosAction posAction = new PosActionImpl();
		MessageObject request = new MessageObject();
		MessageObject response = new MessageObject();
    	/*3.4.1银联数据民生积分签到-开始*/
//    	request.setMesstype("0800");
//    	request.setField11_System_Trace_Audit_Number("000001");
//    	request.setField41_Card_Acceptor_Terminal_ID("00000001");
//    	request.setField42_Card_Acceptor_ID("305310000000160");
//    	request.setField60_Reserved_Private("00000001003");
//    	request.setField63_Reserved_Private("01 ");
//    	request.setTranCode("080000");
    	/*3.4.1银联数据民生积分签到-结束*/
    	
    	/*3.4.2积分消费活动参数查询-开始*/
//    	request.setMesstype("0820");
//    	request.setField41_Card_Acceptor_Terminal_ID("00000001");
//    	request.setField42_Card_Acceptor_ID("305310000000160");
//    	request.setField60_Reserved_Private("00000125384");
//    	request.setField62_Reserved_Private("100");
//    	request.setTranCode("082000384");
    	/*3.4.2积分消费活动参数查询-结束*/
		
    	/*3.4.21POS积分消费活动参数下载-开始*/
//    	request.setMesstype("0800");
//    	request.setField41_Card_Acceptor_Terminal_ID("00000001");
//    	request.setField42_Card_Acceptor_ID("305310000000160");
//    	request.setField60_Reserved_Private("00000119373");
//    	request.setField62_Reserved_Private("303030303030303030303030333033");
//    	request.setTranCode("080000373");
    	/*3.4.21POS积分消费活动参数下载-结束*/
		
		/*3.4.3 批结算-开始*/
//		request.setMesstype("0500");
//		request.setField11_System_Trace_Audit_Number("000252");
//		request.setField41_Card_Acceptor_Terminal_ID("00000001");
//    	request.setField42_Card_Acceptor_ID("305310000000160");
//    	request.setField48_Additional_Data("00000000000000000000000000000010000000000000000000000000000001");
//    	request.setField49_Currency_Code_Of_Transaction("156");
//    	request.setField60_Reserved_Private("00000121201");
//    	request.setField63_Reserved_Private("01 ");
//    	request.setTranCode("0500201");
		/*3.4.3 批结算-结束*/
		/*3.4.2签退-开始*/
//		request.setMesstype("0820");
//		request.setField11_System_Trace_Audit_Number("000252");
//		request.setField41_Card_Acceptor_Terminal_ID("00000001");
//    	request.setField42_Card_Acceptor_ID("305310000000160");
//    	request.setField60_Reserved_Private("00000119002");
//		request.setTranCode("082000002");
		/*3.4.2签退-结束*/
    	
    	/*3.2.46积分扣减活动消费-开始*/
//		request.setMesstype("1280");
//		request.setField02_Primary_Account_Number("123456789012345678");
//		request.setField03_Processing_Code("000000");
//		request.setField04_Amount_Of_Transactions("000000000999");
//		request.setField11_System_Trace_Audit_Number("000120");
//		request.setField22_Point_Of_Service_Entry_Mode("010");
//		request.setField25_Point_Of_Service_Condition_Mode("04");
//		request.setField41_Card_Acceptor_Terminal_ID("00000001");
//    	request.setField42_Card_Acceptor_ID("305310000000160");
//    	request.setField60_Reserved_Private("00000119");
//    	request.setField62_Reserved_Private("4143544E303030303030303030303030333332");
//    	request.setTranCode("0200000422");
		
		
//    	request.setField02_Primary_Account_Number("6259130683279248");
//    			request.setField02_Primary_Account_Number("4218710000002182");
    	
    	request.setMesstype("1280");
    	request.setField02_Primary_Account_Number("6226230180000018");
    	request.setField11_System_Trace_Audit_Number("000201");
    	request.setField60_Reserved_Private("03000250");
    	request.setField41_Card_Acceptor_Terminal_ID("81101234");
    	request.setField62_Reserved_Private("MSYH01");
    	request.setTranCode("1028");
    	/*3.2.46积分扣减活动消费-结束*/
    	
    	/*消费冲正 -开始*/
//    	request.setMesstype("1320");
//    	request.setField02_Primary_Account_Number("4218710000002182");
//    	request.setField11_System_Trace_Audit_Number("000201");
//    	request.setField41_Card_Acceptor_Terminal_ID("81101234");
//    	request.setField60_Reserved_Private("03000250");
//    	request.setField41_Card_Acceptor_Terminal_ID("81101234");
//    	request.setField62_Reserved_Private("MSYH01");
//    	request.setTranCode("1028");
    	/*消费冲正 -结束*/
    	
    	
    	/*消费撤销-开始*/
//    	request.setMesstype("1400");
//    	request.setField02_Primary_Account_Number("6226230180000018");
//    	request.setField11_System_Trace_Audit_Number("000201");
//    	request.setField60_Reserved_Private("03000250");
//    	request.setField41_Card_Acceptor_Terminal_ID("81101234");
//    	request.setField62_Reserved_Private("MSYH01");
//    	request.setTranCode("1028");
    	/*消费撤销-结束*/
    	
    	try {
    		 /*管理类交易*/
//    		response = posAction.manageTran(request);
    		/*联机类交易*/
			response = posAction.messReceived(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
