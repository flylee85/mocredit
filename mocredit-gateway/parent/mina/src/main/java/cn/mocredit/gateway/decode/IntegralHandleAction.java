package cn.mocredit.gateway.decode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mocredit.gateway.message.MessageObject;
import cn.mocredit.gateway.posp.bc.util.DateUtils;
import cn.mocredit.gateway.posp.bc.util.FormatException;
@Service
@Transactional
public class IntegralHandleAction implements IHandleAction {

	@Override
	public MessageObject signIn(MessageObject request) throws FormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageObject purchase(MessageObject request) throws FormatException {
		if("1280".equals(request.getMesstype())) {
			request.setMesstype("1290");
			/** 设置受卡方坐在地时间 */
			request.setField12_Time_Of_Local_Transaction(DateUtils.getCurrentTime());
			/** 设置受卡方坐在地日期*/
			request.setField13_Date_Of_Local_Transaction(DateUtils.getCurrentDate().substring(4));
			request.setField37_Retrieval_Reference_Number("123456789012");
			request.setField39_Response_Code("00");
		}else if("1290".equals(request.getMesstype())) {
			
			request.setField39_Response_Code("00");
		}else{
			throw new FormatException("getTrancode Exception");
		}
		return request;
	}

	@Override
	public MessageObject reversalPurchase(MessageObject request) throws FormatException {
		if("1320".equals(request.getMesstype())) {
			request.setMesstype("1330");
			request.setField06(null);
			/** 设置受卡方坐在地时间 */
			request.setField12_Time_Of_Local_Transaction(DateUtils.getCurrentTime());
			/** 设置受卡方坐在地日期*/
			request.setField13_Date_Of_Local_Transaction(DateUtils.getCurrentDate().substring(4));
			request.setField37_Retrieval_Reference_Number("123456789012");
			request.setField39_Response_Code("00");
			String field60 = request.getField60_Reserved_Private();
			/** 批次号增1*/
			String batchNo = String.format("%06d", Integer.parseInt("000011")+1);
		}else if("111000".equals(request.getTranCode())) {
			
		}else{
			throw new FormatException("getTrancode Exception");
		}
		return request;
	}

	@Override
	public MessageObject revokePurchase(MessageObject request) throws FormatException {
		if("1400".equals(request.getMesstype())) {
			request.setMesstype("1410");
			request.setField06(null);
			/** 设置受卡方坐在地时间 */
			request.setField12_Time_Of_Local_Transaction(DateUtils.getCurrentTime());
			/** 设置受卡方坐在地日期*/
			request.setField13_Date_Of_Local_Transaction(DateUtils.getCurrentDate().substring(4));
			request.setField37_Retrieval_Reference_Number("123456789012");
			request.setField39_Response_Code("00");
		}else if("111000".equals(request.getTranCode())) {
			
		}else{
			throw new FormatException("getTrancode Exception");
		}
		return request;
	}

	@Override
	public MessageObject reversalRevokePurchase(MessageObject request) throws FormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageObject down(MessageObject request) throws FormatException {
		// TODO Auto-generated method stub
		return null;
	}

}
