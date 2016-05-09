package cn.mocredit.posp.impl.cmsb.points;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m.common.hibernate3.Finder;
import cn.m.mt.dao.DeviceDao;
import cn.m.mt.dao.SD_POINTS_INFO_Dao;
import cn.m.mt.dao.SD_POS_TERM_Dao;
import cn.m.mt.dao.SD_TERM_KEY_Dao;
import cn.m.mt.dao.SD_TRAN_LS_Dao;
import cn.m.mt.po.Device;
import cn.m.mt.po.SD_POINTS_INFO;
import cn.m.mt.po.SD_POS_TERM;
import cn.m.mt.po.SD_TRAN_LS;
import cn.mocredit.posp.bc.util.CodeInfo;
import cn.mocredit.posp.bc.util.DateUtils;
import cn.mocredit.posp.bc.util.MessageObject;

/**
 * 民生积分交易处理
 * @author Superdo
 *
 */
@Service
@Transactional
public class PointsHandleImpl implements PointsHandle {
	private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	@Autowired
	SD_TRAN_LS_Dao SD_TRAN_LS_dao;
	@Autowired
	SD_TERM_KEY_Dao SD_TERM_KEY_dao;
	@Autowired
	SD_POS_TERM_Dao SD_POS_TERM_dao;
	@Autowired
	SD_POINTS_INFO_Dao SD_POINTS_INFO_dao;
	@Autowired
	private DeviceDao devicedao;

	/**
	 * 签到
	 * @param request
	 * @return
	 */
	public MessageObject signIn(MessageObject request,String trancode){
		if(trancode.equals("080000")){
//			request.setMesstype("0200");
//			request.setField04_Amount_Of_Transactions("000000000999");
//			request.setField11_System_Trace_Audit_Number("000000"+1);
//			
//			request.setField41_Card_Acceptor_Terminal_ID("");
//			request.setField42_Card_Acceptor_ID("");
//			
//			request.setField60_Reserved_Private("22"+"批次号等等");
//			request.setField62_Reserved_Private("活动编号");
			System.out.println("签到");
			return request;
			
		}else if(trancode.equals("081000")){
//			request.setMesstype("1290");
			return request;
		}
		return request;
		
	}
	public MessageObject batchSettlement(MessageObject request,String trancode){
		return request;
	}
	/**
	 * 签退
	 * @param request
	 * @return
	 */
	public MessageObject signOut(MessageObject request,String trancode){
		
		return request;
		
	}
	/**
	 * 活动列表下载
	 * @param request
	 * @return
	 */
	public MessageObject activtyDown(MessageObject request,String trancode){
		
		return request;
		
	}
	/**
	 * 消费
	 * @param request
	 * @return
	 */
	public MessageObject purchase(MessageObject request,String trancode,MessageObject termRequest){
		if(trancode.equals("0200000422")){
			Finder finder = new Finder("from SD_POS_TERM where shopid="+ termRequest.getTranCode()
					+ " AND TERM_TYPE='03'");
//			finder.setParam("shopid", termRequest.getTranCode());
			List<SD_POS_TERM> allPos = SD_POS_TERM_dao.find(finder);
			SD_POS_TERM sdPosTerm = allPos.get(0);
			request.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));//pos.getPOS_NO())+1));
			request.setField14_Date_Of_Expired(termRequest.getField14_Date_Of_Expired());
			request.setField22_Point_Of_Service_Entry_Mode("010");
			request.setField41_Card_Acceptor_Terminal_ID(sdPosTerm.getTERM_ID());
			request.setField42_Card_Acceptor_ID(sdPosTerm.getMERCH_ID());
			request.setField60_Reserved_Private("22"+sdPosTerm.getBATCH_NO());
			request.setField64_MAC("3131313131313131");
			request.setField37_Retrieval_Reference_Number(null);
			/**终端流水加1 */
			sdPosTerm.setPOS_NO(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			return request;
		}else if(trancode.endsWith("0210000022")){
			addSdTranLs(request, "03", termRequest);
			return request;
		}
		return null;
	}
	/**
	 * 消费撤销
	 * @param request
	 * @return
	 */
	public MessageObject revokePurchase(MessageObject request,String tranCode,MessageObject termRequest){
		if(tranCode.equals("0200200423")){
			Finder finder = new Finder("from SD_POS_TERM where shopid="+ termRequest.getTranCode()
					+ " AND TERM_TYPE='03'");
			List<SD_POS_TERM> allPos = SD_POS_TERM_dao.find(finder);
			SD_POS_TERM sdPosTerm = allPos.get(0);
			request.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			request.setField14_Date_Of_Expired(termRequest.getField14_Date_Of_Expired());
			request.setField22_Point_Of_Service_Entry_Mode("010");
			Finder finderLs = new Finder("from SD_TRAN_LS where TRAN_STATUS='0' AND WL_TERM_ID=:WL_TERM_ID "
					+ "AND WL_BATCH_NO=:WL_BATCH_NO "
					+ "AND WL_POSNO=:WL_POSNO "
					+ "AND CARD_NO=:CARD_NO ");
			finderLs.setParam("WL_TERM_ID", termRequest.getField41_Card_Acceptor_Terminal_ID());
			finderLs.setParam("WL_BATCH_NO", termRequest.getField60_Reserved_Private());
			finderLs.setParam("WL_POSNO", termRequest.getField11_System_Trace_Audit_Number());
			finderLs.setParam("CARD_NO", termRequest.getField02_Primary_Account_Number());
			List<SD_TRAN_LS> tranLs = SD_TRAN_LS_dao.find(finderLs);
			SD_TRAN_LS sdTranLs = tranLs.get(0);
			request.setField37_Retrieval_Reference_Number(sdTranLs.getTRAN_SERIAL());
			request.setField38_Authorization_Identification_Response(sdTranLs.getAUTH_NO());
//			request.setField41_Card_Acceptor_Terminal_ID(sdPosTerm.getTERM_ID());
//			request.setField42_Card_Acceptor_ID(sdPosTerm.getMERCH_ID());
			request.setField60_Reserved_Private("23"+sdPosTerm.getBATCH_NO());
			request.setField61_Original_Message(sdTranLs.getBATCH_NO()+sdTranLs.getTERM_SERIAL());
//			request.setField62_Reserved_Private("4143544E"+"303030303030303030303030333235");
			request.setField64_MAC("3131313131313131");
			/**终端流水加1 */
			sdPosTerm.setPOS_NO(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			return request;
		}else if(tranCode.endsWith("0210200023")){
			addSdTranLs(request, "03", termRequest);
			return request;
		}
		return null;
		
	}
	/**
	 * 消费冲正
	 * @param request
	 * @return
	 */
	public MessageObject reversalPurchase(MessageObject request,String tranCode,MessageObject termRequest){
		if(tranCode.equals("0400000422")){
			Finder finder = new Finder("from SD_POS_TERM where shopid="+ termRequest.getTranCode()
					+ " AND TERM_TYPE='03'");
			List<SD_POS_TERM> allPos = SD_POS_TERM_dao.find(finder);
			SD_POS_TERM sdPosTerm = allPos.get(0);
			request.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			request.setField14_Date_Of_Expired(termRequest.getField14_Date_Of_Expired());
			request.setField22_Point_Of_Service_Entry_Mode("010");
			Finder finderLs = new Finder("from SD_TRAN_LS where TRAN_STATUS='0' AND WL_TERM_ID=:WL_TERM_ID "
					+ "AND WL_BATCH_NO=:WL_BATCH_NO "
					+ "AND WL_POSNO=:WL_POSNO "
					+ "AND CARD_NO=:CARD_NO ");
			finderLs.setParam("WL_TERM_ID", termRequest.getField41_Card_Acceptor_Terminal_ID());
			finderLs.setParam("WL_BATCH_NO", termRequest.getField60_Reserved_Private());
			finderLs.setParam("WL_POSNO", termRequest.getField11_System_Trace_Audit_Number());
			finderLs.setParam("CARD_NO", termRequest.getField02_Primary_Account_Number());
			List<SD_TRAN_LS> tranLs = SD_TRAN_LS_dao.find(finderLs);
			SD_TRAN_LS sdTranLs = tranLs.get(0);
//			request.setField37_Retrieval_Reference_Number(sdTranLs.getTRAN_SERIAL());
			request.setField38_Authorization_Identification_Response(sdTranLs.getAUTH_NO());
			request.setField39_Response_Code("98");
			request.setField60_Reserved_Private("22"+sdTranLs.getBATCH_NO());
			request.setField61_Original_Message(null);
			request.setField64_MAC("3131313131313131");
			/**终端流水加1 */
			sdPosTerm.setPOS_NO(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			return request;
			//消费冲正
//			request.setField11_System_Trace_Audit_Number("000005");//pos.getPOS_NO())+1));
//			request.setField14_Date_Of_Expired("3406");
//			request.setField22_Point_Of_Service_Entry_Mode("010");
//			request.setField37_Retrieval_Reference_Number("145619260096");
//			request.setField38_Authorization_Identification_Response("000000");
//			request.setField39_Response_Code("98");
//			request.setField41_Card_Acceptor_Terminal_ID("00000001");//pos.getTERM_ID());
//			request.setField42_Card_Acceptor_ID("305310000000160");//pos.getMERCH_ID());
//			request.setField60_Reserved_Private("22"+"000126");//pos.getBATCH_NO());
//			request.setField62_Reserved_Private("4143544E"+"303030303030303030303030333233");
//			request.setField64_MAC("3131313131313131");
		}else if(tranCode.endsWith("0410000022")){
			addSdTranLs(request, "03", termRequest);
			return request;
		}
		return null;
	}
	/**
	 * 消费撤销冲正
	 * @param request
	 * @return
	 */
	public MessageObject reversalRevokePurchase(MessageObject request,String tranCode,MessageObject termRequest){
		//消费撤销冲正
//		request.setField02_Primary_Account_Number("6226230180000018");
//		request.setField11_System_Trace_Audit_Number("000015");//pos.getPOS_NO())+1));
//		request.setField03_Processing_Code("200000");
//		request.setField14_Date_Of_Expired("3406");
//		request.setField22_Point_Of_Service_Entry_Mode("010");
//		request.setField38_Authorization_Identification_Response("062143");
//		request.setField39_Response_Code("98");
//		request.setField41_Card_Acceptor_Terminal_ID("00000001");//pos.getTERM_ID());
//		request.setField42_Card_Acceptor_ID("305310000000160");//pos.getMERCH_ID());
//		request.setField60_Reserved_Private("23"+"000126");//pos.getBATCH_NO());
//		request.setField61_Original_Message("000126000015");
//		request.setField62_Reserved_Private("4143544E"+"303030303030303030303030333235");
//		request.setField64_MAC("3131313131313131");
//		return request;
		return request;
		
	}
	/**
	 * 领奖查询
	 */
	public MessageObject prizeQuery(MessageObject request,String tranCode,MessageObject termRequest){
		if(tranCode.equals("070000")){
			Finder finder = new Finder("from SD_POS_TERM where shopid="+ termRequest.getTranCode()
					+ " AND TERM_TYPE='04'");
	//		finder.setParam("shopid", termRequest.getTranCode());
			List<SD_POS_TERM> allPos = SD_POS_TERM_dao.find(finder);
			SD_POS_TERM sdPosTerm = allPos.get(0);
			request.setField03_Processing_Code("000000");//领奖查询-交易处理吗
			request.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));//pos.getPOS_NO())+1));
			request.setField14_Date_Of_Expired(termRequest.getField14_Date_Of_Expired());
			request.setField22_Point_Of_Service_Entry_Mode("010");
			request.setField37_Retrieval_Reference_Number(null);
			request.setField41_Card_Acceptor_Terminal_ID(sdPosTerm.getTERM_ID());
			request.setField42_Card_Acceptor_ID(sdPosTerm.getMERCH_ID());
			request.setField49_Currency_Code_Of_Transaction("0007");
			request.setField60_Reserved_Private(sdPosTerm.getBATCH_NO());
			request.setField62_Reserved_Private(null);
			request.setField64_MAC("3131313131313131");
			/**终端流水加1 */
			sdPosTerm.setPOS_NO(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			return request;
		}else if(tranCode.equals("071001")){
			addSdTranLs(request, "04", termRequest);
			return request;
		}
		return null;
	}
	/**
	 * 领奖
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prize(MessageObject request,String tranCode,MessageObject termRequest){
		if(tranCode.equals("070001")){
			Finder finder = new Finder("from SD_POS_TERM where shopid="+ termRequest.getTranCode()
					+ " AND TERM_TYPE='04'");
			List<SD_POS_TERM> allPos = SD_POS_TERM_dao.find(finder);
			SD_POS_TERM sdPosTerm = allPos.get(0);
//			request.setField03_Processing_Code("010000");//领奖查询-交易处理吗
			request.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));//pos.getPOS_NO())+1));
			request.setField14_Date_Of_Expired(termRequest.getField14_Date_Of_Expired());
			request.setField37_Retrieval_Reference_Number(null);
			request.setField41_Card_Acceptor_Terminal_ID(sdPosTerm.getTERM_ID());
			request.setField42_Card_Acceptor_ID(sdPosTerm.getMERCH_ID());
			request.setField49_Currency_Code_Of_Transaction("0007D");
			request.setField60_Reserved_Private(sdPosTerm.getBATCH_NO());
			request.setField64_MAC("3131313131313131");
			/**终端流水加1 */
			sdPosTerm.setPOS_NO(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			return request;
		}else if(tranCode.equals("071001")){
			addSdTranLs(request, "04", termRequest);
			return request;
		}
		return null;
	}
	/**
	 * 领奖冲正
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject reversalPrize(MessageObject request,String tranCode,MessageObject termRequest){
		if(tranCode.equals("072001")){
			Finder finderLs = new Finder("from SD_TRAN_LS where TRAN_STATUS='0' AND WL_TERM_ID=:WL_TERM_ID "
					+ "AND WL_BATCH_NO=:WL_BATCH_NO "
					+ "AND WL_POSNO=:WL_POSNO "
					+ "AND CARD_NO=:CARD_NO ");
			finderLs.setParam("WL_TERM_ID", termRequest.getField41_Card_Acceptor_Terminal_ID());
			finderLs.setParam("WL_BATCH_NO", termRequest.getField60_Reserved_Private());
			finderLs.setParam("WL_POSNO", termRequest.getField11_System_Trace_Audit_Number());
			finderLs.setParam("CARD_NO", termRequest.getField02_Primary_Account_Number());
			List<SD_TRAN_LS> tranLs = SD_TRAN_LS_dao.find(finderLs);
			SD_TRAN_LS sdTranLs = tranLs.get(0);
			request.setField11_System_Trace_Audit_Number(sdTranLs.getTERM_SERIAL());
			request.setField14_Date_Of_Expired(termRequest.getField14_Date_Of_Expired());
			request.setField37_Retrieval_Reference_Number(null);
			request.setField41_Card_Acceptor_Terminal_ID(sdTranLs.getTERM_ID());
			request.setField42_Card_Acceptor_ID(sdTranLs.getMERCH_ID());
			request.setField48_Additional_Data("");
			request.setField49_Currency_Code_Of_Transaction("0007D");
			request.setField54_Balance_Amount("");
			request.setField60_Reserved_Private(sdTranLs.getBATCH_NO());
			request.setField64_MAC("3131313131313131");
			return request;
		}else {
			addSdTranLs(request, "04", termRequest);
			return request;
		}
	}
	/**
	 * 领奖签到
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeSignIn(MessageObject request,String tranCode){
		return request;
	}
	/**
	 * 领奖签退（未使用）
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeSignOut(MessageObject request,String tranCode){
		return request;
	}
	
	/**
	 * 领奖KEK下载
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeKekDown(MessageObject request,String tranCode){
		return request;
	}
	/**
	 * 领奖活动参数下载（未使用）
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeDown(MessageObject request,String tranCode){
		return request;
	}
	/**
	 * 积分消费活动参数查询(签到后交易)
	 * @param request
	 * @param tranCode
	 * @return
	 */
	public MessageObject parameterQuery(MessageObject request,String tranCode) {
		return request;
	}
	/**
	 * 积分消费活动参数下载(签到后交易)
	 * @param request
	 * @param tranCode
	 * @return
	 */
	public MessageObject parameterDown(MessageObject request,String tranCode) {
		return request;
	}
	
	public void addSdTranLs(MessageObject response,String type,MessageObject posRequest) {
		SD_TRAN_LS sdTranLs = new SD_TRAN_LS();
		/*消费/冲正*/
		String f39 = response.getField39_Response_Code();
		f39 = f39 == null ? "" : f39;
		String reasultDesc = CodeInfo.MIN_SHENG_RES_FIELD_39.get(f39);
		reasultDesc = reasultDesc == null ? "" : reasultDesc;
		if(response.getTranCode().equals("0210000022")||response.getTranCode().equals("0410000022")
				||response.getTranCode().equals("071001")||response.getTranCode().equals("073001")) {
			/*交易类型*/
			sdTranLs.setTRAN_TYPE("22");
			if(response.getTranCode().equals("0410000022")||response.getTranCode().equals("073001")){
				Finder finderLs = new Finder("from SD_TRAN_LS where TRAN_STATUS='0' AND WL_TERM_ID=:WL_TERM_ID "
						+ "AND WL_BATCH_NO=:WL_BATCH_NO "
						+ "AND WL_POSNO=:WL_POSNO "
						+ "AND CARD_NO=:CARD_NO ");
				finderLs.setParam("WL_TERM_ID", posRequest.getField41_Card_Acceptor_Terminal_ID());
				finderLs.setParam("WL_BATCH_NO", posRequest.getField60_Reserved_Private());
				finderLs.setParam("WL_POSNO", posRequest.getField11_System_Trace_Audit_Number());
				finderLs.setParam("CARD_NO", posRequest.getField02_Primary_Account_Number());
				List<SD_TRAN_LS> tranLs = SD_TRAN_LS_dao.find(finderLs);
				if (tranLs!=null&&tranLs.size()==1) {
					sdTranLs.setORDERID(tranLs.get(0).getORDERID());
				}
				sdTranLs.setTRAN_FLAG("2");
				/*原交易类型*/
				sdTranLs.setORIG_TRAN_TYPE("22");
				if(f39.equals("00")){
					sdTranLs.setRESERVE("消费冲正成功");
				}else {
					if(f39.equals("12")){
						sdTranLs.setRESERVE("消费冲正失败");
					}else{
						sdTranLs.setRESERVE(reasultDesc);
					}
				}
			} else {
				sdTranLs.setORDERID(posRequest.getField37_Retrieval_Reference_Number());
				sdTranLs.setTRAN_FLAG("0");
				if(f39.equals("00")){
					sdTranLs.setRESERVE("消费成功");
				}else {
					if(f39.equals("12")){
						sdTranLs.setRESERVE("消费失败");
					}else{
						sdTranLs.setRESERVE(reasultDesc);
					}
				}
			}
		}
		/*消费撤销/冲正*/
		if(response.getTranCode().equals("0210200023")||response.getTranCode().equals("0410000023")){
			/*交易类型*/
			sdTranLs.setTRAN_TYPE("23");
			/*交易标志*/
			if(response.getTranCode().equals("0210200023")) {
				Finder finderLs = new Finder("from SD_TRAN_LS where TRAN_STATUS='0' AND WL_TERM_ID=:WL_TERM_ID "
						+ "AND WL_BATCH_NO=:WL_BATCH_NO "
						+ "AND WL_POSNO=:WL_POSNO "
						+ "AND CARD_NO=:CARD_NO ");
				finderLs.setParam("WL_TERM_ID", posRequest.getField41_Card_Acceptor_Terminal_ID());
				finderLs.setParam("WL_BATCH_NO", posRequest.getField61_Original_Message().subSequence(0, 6));
				finderLs.setParam("WL_POSNO", posRequest.getField61_Original_Message().substring(6,posRequest.getField61_Original_Message().length()));
				finderLs.setParam("CARD_NO", posRequest.getField02_Primary_Account_Number());
				List<SD_TRAN_LS> tranLs = SD_TRAN_LS_dao.find(finderLs);
				if (tranLs!=null&&tranLs.size()==1) {
					sdTranLs.setORDERID(tranLs.get(0).getORDERID());
				}
				/*原交易类型*/
				sdTranLs.setORIG_TRAN_TYPE("22");
				sdTranLs.setTRAN_FLAG("1");
				if(f39.equals("00")){
					sdTranLs.setRESERVE("消费撤销成功");
				}else {
					if(f39.equals("12")){
						sdTranLs.setRESERVE("消费撤销失败");
					}else{
						sdTranLs.setRESERVE(reasultDesc);
					}
				}
			}else {
				/*原交易类型*/
				sdTranLs.setORIG_TRAN_TYPE("23");
				sdTranLs.setTRAN_FLAG("2");
				if(f39.equals("00")){
					sdTranLs.setRESERVE("消费撤销冲正成功");
				}else {
					if(f39.equals("12")){
						sdTranLs.setRESERVE("消费撤销冲正失败");
					}else{
						sdTranLs.setRESERVE(reasultDesc);
					}
				}
			}
				
		}
		/*POS中心流水*/
		sdTranLs.setTRAN_SERIAL(response.getField37_Retrieval_Reference_Number());
		BigDecimal tranAmt = null;
		if(!"0710".equals(response.getMesstype())) {
			/*交易金额*/
			DecimalFormat myformat = new DecimalFormat();
	    	myformat.applyPattern("0.00");
	    	tranAmt = new BigDecimal(response.getField04_Amount_Of_Transactions().length() == 0 ?
					"" : ("" + myformat.format(Double.parseDouble(response.getField04_Amount_Of_Transactions()) * 0.01)));
			sdTranLs.setTRAN_AMT(tranAmt);
			sdTranLs.setREMAIN_TRAN_AMT(tranAmt);
		}
		
		/*交易日期*/
		sdTranLs.setTRAN_DATE(YEAR_FORMAT.format(new Date()) + response.getField13_Date_Of_Local_Transaction());
		sdTranLs.setTERM_DATE(response.getField13_Date_Of_Local_Transaction());
		/*交易时间*/
		sdTranLs.setTRAN_TIME(response.getField12_Time_Of_Local_Transaction());
		sdTranLs.setTERM_TIME(response.getField12_Time_Of_Local_Transaction());
		/*pos终端流水*/
		sdTranLs.setTERM_SERIAL(response.getField11_System_Trace_Audit_Number());
		/*批次号*//*卡号*/
		if("0710".equals(response.getMesstype())){
			sdTranLs.setBATCH_NO(response.getField60_Reserved_Private());
			sdTranLs.setCARD_NO(posRequest.getField02_Primary_Account_Number());
		}else {
			sdTranLs.setBATCH_NO(response.getField60_Reserved_Private().substring(2,8));
			sdTranLs.setCARD_NO(response.getField02_Primary_Account_Number());
		}
		
		/*卡商类型  01-雅高；02-雅酷*/
		sdTranLs.setVALUECARD_TYPE(type);
		/*终端编号*/
		sdTranLs.setTERM_ID(response.getField41_Card_Acceptor_Terminal_ID());
		/*商户编号*/
		sdTranLs.setMERCH_ID(response.getField42_Card_Acceptor_ID());
		/*Ic55*/
		sdTranLs.setIC_55(response.getField55_IC_DATA());
		/*币种代码*/
		if("0710".equals(response.getMesstype())){
			sdTranLs.setCCY_CODE("156");
		}else {
			sdTranLs.setCCY_CODE(response.getField49_Currency_Code_Of_Transaction());
		}
		/*服务点输入方式码*/
		sdTranLs.setINPUT_MODE(posRequest.getField22_Point_Of_Service_Entry_Mode());
		/*条件服务码*/
		sdTranLs.setCONDITION_MODE(response.getField25_Point_Of_Service_Condition_Mode());
		/*交易返回码*/
		sdTranLs.setRESP_CODE(f39);
		/*系统参考号*/
		sdTranLs.setREFER_NO(response.getField37_Retrieval_Reference_Number());
		sdTranLs.setEXP_DATE(response.getField14_Date_Of_Expired());
		if(posRequest.getField38_Authorization_Identification_Response()==null){
			sdTranLs.setAUTH_NO(response.getField38_Authorization_Identification_Response());
		}else {
			sdTranLs.setAUTH_NO(posRequest.getField38_Authorization_Identification_Response());
		}
		if(f39.equals("00")){
			sdTranLs.setTRAN_STATUS("0");
		}else {
			sdTranLs.setTRAN_STATUS("1");
		}
		sdTranLs.setAUTH_AMT(tranAmt);
		sdTranLs.setAUTH_DATE(response.getField13_Date_Of_Local_Transaction());
		sdTranLs.setEXP_DATE(response.getField14_Date_Of_Expired());
		sdTranLs.setTERM_TYPE("0");
		/**
		 * 01-POS
		 * 02-自助终端
		 * 03-网上银行
		 * 04-电话银行
		 * 05-银联
		 */
		sdTranLs.setCHANNEL_TYPE("01");
		/***/
		sdTranLs.setWL_BATCH_NO(posRequest.getField60_Reserved_Private());
		sdTranLs.setWL_POSNO(posRequest.getField11_System_Trace_Audit_Number());
		sdTranLs.setWL_TERM_ID(posRequest.getField41_Card_Acceptor_Terminal_ID());
		Device d = checkDevice(posRequest.getField41_Card_Acceptor_Terminal_ID());
		if (d != null) {
			sdTranLs.setStore(d.getStore());
		}
		sdTranLs.setACTIVTY_ID(posRequest.getField62_Reserved_Private());
		SD_TRAN_LS_dao.save(sdTranLs);
	}
	private Device checkDevice (String imei){
		if (imei == null) {
			return null;
		}
		List<Device> devicelist = devicedao.findByProperty("devcode", imei);
		if (null == devicelist || devicelist.isEmpty()) {
			return null;
		}
		return devicelist.get(0);  
	}

}
