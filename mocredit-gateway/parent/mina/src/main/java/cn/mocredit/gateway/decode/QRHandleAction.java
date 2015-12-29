package cn.mocredit.gateway.decode;

import static cn.mocredit.gateway.util.Util.uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.mocredit.gateway.data.entities.MpTempKey;
import cn.mocredit.gateway.message.MessageObject;
import cn.mocredit.gateway.posp.bc.util.DateUtils;
import cn.mocredit.gateway.posp.bc.util.DesTools;
import cn.mocredit.gateway.posp.bc.util.FormatException;
import cn.mocredit.gateway.posp.service.PospService;
@Service
@Transactional
public class QRHandleAction implements HandleAction {
    @Autowired
    private PospService pospService;

	@Override
	public MessageObject signIn(MessageObject request) throws FormatException {
		if("1100".equals(request.getMesstype())) {
			MpTempKey mpTermKey = new MpTempKey();
			request.setMesstype("1110");
			/** 设置受卡方坐在地时间 */
			request.setField12_Time_Of_Local_Transaction(DateUtils.getCurrentTime());
			/** 设置受卡方坐在地日期*/
			request.setField13_Date_Of_Local_Transaction(DateUtils.getCurrentDate().substring(4));
			request.setField37_Retrieval_Reference_Number("123456789012");
			request.setField39_Response_Code("00");
			String field60 = request.getField60_Reserved_Private();
			/** 批次号增1*/
			String batchNo = String.format("%06d", Integer.parseInt("000011")+1);
			/** 设置60域*/
			request.setField60_Reserved_Private(field60.substring(0,2)+ batchNo +field60.substring(8,field60.length()));
			/** 设置62域*/
			request.setField62_Reserved_Private(DesTools.str2des("11111111", "11111111"));
			mpTermKey.setId(uuid());
			mpTermKey.setMERCH_ID(request.getField42_Card_Acceptor_ID());
			mpTermKey.setTERM_ID(request.getField41_Card_Acceptor_Terminal_ID());
			mpTermKey.setMERCH_TYPE("05");
			mpTermKey.setOPER_ID("01 ");
			mpTermKey.setBATCH_NO(batchNo);
			mpTermKey.setFLAG("1");
			mpTermKey.setUPDATE_TIME("20150806122450");
			mpTermKey.setSTORE_ID("000");
			mpTermKey.setLMK_TMK("000");
			mpTermKey.setTMK_CHECK_VALUE("000");
			mpTermKey.setLMK_TAK("000");
			mpTermKey.setLMK_TPK("000");
			mpTermKey.setTMK_TAK("000");
			mpTermKey.setTMK_TPK("000");
			mpTermKey.setZMK_TMK("000");
			System.out.println(pospService);
			pospService.addTerm(mpTermKey);
		}else if("111000".equals(request.getTranCode())) {
			
		}else{
			throw new FormatException("getTrancode Exception");
		}
		return request;
	}

	@Override
	public MessageObject purchase(MessageObject request) throws FormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageObject reversalPurchase(MessageObject request) throws FormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageObject revokePurchase(MessageObject request) throws FormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageObject reversalRevokePurchase(MessageObject request) throws FormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageObject down(MessageObject request) throws FormatException {
		if("1260".equals(request.getMesstype())) {
			request.setMesstype("1270");
			/** 设置受卡方坐在地时间 */
			request.setField12_Time_Of_Local_Transaction(DateUtils.getCurrentTime());
			/** 设置受卡方坐在地日期*/
			request.setField13_Date_Of_Local_Transaction(DateUtils.getCurrentDate().substring(4));
			request.setField37_Retrieval_Reference_Number("123456789012");
			request.setField39_Response_Code("00");
			String downType = request.getField60_Reserved_Private().substring(0, 2);
			if(downType.equals("01")) {
				request.setField05_Mobile_Number("A101D1E9C2EBB2E2CAD4BBEEB6AF0200000103004005106706F73");
				request.setField05_Mobile_Number(null);
			}else if (downType.equals("02")){
				request.setField05_Mobile_Number("A10108D6D0D0C5D2F8D0D0020131A10108C3F1C9FAD2F8D0D0020133A10108D5D0C9CCD2F8D0D0020134");
			}else if (downType.equals("03")){
				request.setField05_Mobile_Number("A10108CCECCCECBEABB2CA333635BBFDB7D6BBEEB6AF310201330301340431");
			}
			
		}else if("111000".equals(request.getTranCode())) {
			
		}else{
			throw new FormatException("getTrancode Exception");
		}
		return request;
	}

}
