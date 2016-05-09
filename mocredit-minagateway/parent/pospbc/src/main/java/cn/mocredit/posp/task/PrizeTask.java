package cn.mocredit.posp.task;

import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m.common.hibernate3.Finder;
import cn.m.mt.dao.BankDao;
import cn.m.mt.dao.SD_POINTS_INFO_Dao;
import cn.m.mt.dao.SD_POS_TERM_Dao;
import cn.m.mt.dao.SD_TERM_KEY_Dao;
import cn.m.mt.dao.SD_TRAN_LS_Dao;
import cn.m.mt.po.SD_POS_TERM;
import cn.m.mt.po.SD_TERM_KEY;
import cn.mocredit.posp.bc.util.DateUtils;
import cn.mocredit.posp.bc.util.DesEncrypt;
import cn.mocredit.posp.bc.util.DesTools;
import cn.mocredit.posp.bc.util.HexBinary;
import cn.mocredit.posp.bc.util.MessageObject;
import cn.mocredit.posp.main.PosAction;

@Service
@Transactional
public class PrizeTask {
	@Autowired
	PosAction posAction;
	@Autowired
	SD_TRAN_LS_Dao SD_TRAN_LS_dao;
	@Autowired
	SD_TERM_KEY_Dao SD_TERM_KEY_dao;
	@Autowired
	SD_POS_TERM_Dao SD_POS_TERM_dao;
	@Autowired
	SD_POINTS_INFO_Dao SD_POINTS_INFO_dao;
	@Autowired
	BankDao bankDao;
	
	private static Logger logger = Logger.getLogger(PrizeTask.class);
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 领奖KEK下载
	 */
	public void prizeKEK() {
		List<SD_POS_TERM> allPos = SD_POS_TERM_dao.findAll();
		if (allPos == null) {
			return;
		}
		for (SD_POS_TERM pos : allPos) {
			if (!"04".equals(pos.getTERM_TYPE())) continue;
			MessageObject req = new MessageObject();
			req.setMesstype("0900");
			req.setField03_Processing_Code("020000");
			req.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(pos.getPOS_NO())+1));
			/**设置终端编号*/
			req.setField41_Card_Acceptor_Terminal_ID(pos.getTERM_ID());
//			req.setField41_Card_Acceptor_Terminal_ID("00000001");
			/**设置商户编号*/
			req.setField42_Card_Acceptor_ID( pos.getMERCH_ID());
//			req.setField42_Card_Acceptor_ID("000000000EM1001");
			req.setField60_Reserved_Private(pos.getBATCH_NO()+String.format("%06d", Integer.parseInt(pos.getPOS_NO())+1));
			req.setField63_Reserved_Private("01 ");
			/**设置交易码*/
			req.setTranCode("090002");
			try {
				/**调用管理类交易发送民生并返回*/
				MessageObject res = posAction.manageTran(req);
				String f39 = res.getField39_Response_Code();
				pos.setRETCODE(f39 == null || f39.length() == 0 ? "EMPTY" : f39);
				if (f39 != null && !f39.equals("") && f39.equals("00")) {
					/**签到成功，设置虚拟终端信息表*/
					/** 跟新终端流水号、批次号、更新时间*/
					pos.setUPDATE_TIME(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());//设置更新时间
					Finder finder = new Finder("from SD_TERM_KEY where TERM_ID=:TERM_ID and MERCH_ID=:MERCH_ID and TERM_TYPE='04'");
					finder.setParam("TERM_ID", pos.getTERM_ID());
					finder.setParam("MERCH_ID", pos.getMERCH_ID());
					List<SD_TERM_KEY> find = SD_TERM_KEY_dao.find(finder);
					boolean empty = find==null||find.size()==0;
					SD_TERM_KEY sd_TERM_KEY = empty?new SD_TERM_KEY():find.get(0);
					
					String field62 = res.getField62_Reserved_Private();
					/**设置终端密钥表*/
					if (field62==null || field62.equals("") || field62.length()<32) {
						throw new Exception("签到密钥返回格式错误");
					}
					sd_TERM_KEY.setLMK_TMK(field62);
					sd_TERM_KEY.setUPDATE_TIME(DATE_FORMAT.format(new Date()));
					sd_TERM_KEY.setMERCH_ID(pos.getMERCH_ID());
					sd_TERM_KEY.setTERM_ID(pos.getTERM_ID());
					sd_TERM_KEY.setTERM_TYPE("04");
					sd_TERM_KEY.setOPER_ID("01 ");
					if (empty) {
						SD_TERM_KEY_dao.save(sd_TERM_KEY);
					}
				} else {
					pos.setSTATUS("3");
					pos.setUPDATE_TIME(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());
				}
			} catch (Exception e) {
				logger.error(getStackTrace(e));
			}
			
		}
	}
	/**
	 * 领奖签到
	 */
	public void prizeLogIn() {
		List<SD_POS_TERM> allPos = SD_POS_TERM_dao.findAll();
		if (allPos == null) {
			return;
		}
		for (SD_POS_TERM pos : allPos) {
			if (!"04".equals(pos.getTERM_TYPE())) continue;
			MessageObject req = new MessageObject();
			req.setMesstype("0900");
			req.setField03_Processing_Code("000001");
			req.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(pos.getPOS_NO())+1));
			/**设置终端编号*/
			req.setField41_Card_Acceptor_Terminal_ID(pos.getTERM_ID());
			/**设置商户编号*/
			req.setField42_Card_Acceptor_ID( pos.getMERCH_ID());
			req.setField60_Reserved_Private(String.format("%06d", Integer.parseInt(pos.getBATCH_NO())+1)+String.format("%06d", Integer.parseInt(pos.getPOS_NO())+1));
			req.setField63_Reserved_Private("01 ");
			/**设置交易码*/
			req.setTranCode("090000");
			try {
				/**调用管理类交易发送民生并返回*/
				MessageObject res = posAction.manageTran(req);
				String f39 = res.getField39_Response_Code();
				pos.setRETCODE(f39 == null || f39.length() == 0 ? "EMPTY" : f39);
				if (f39 != null && !f39.equals("") && f39.equals("00")) {
					/**签到成功，设置虚拟终端信息表*/
					pos.setSTATUS("1");//设置签到状态0-正常 1-签到 2-签退 3-异常
					/** 跟新终端流水号、批次号、更新时间*/
					pos.setPOS_NO("000001");
					pos.setBATCH_NO(res.getField60_Reserved_Private().substring(0, 6));//设置批次号
					pos.setUPDATE_TIME(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());//设置更新时间
					Finder finder = new Finder("from SD_TERM_KEY where TERM_ID=:TERM_ID and MERCH_ID=:MERCH_ID and TERM_TYPE='04'");
					finder.setParam("TERM_ID", pos.getTERM_ID());
					finder.setParam("MERCH_ID", pos.getMERCH_ID());
					List<SD_TERM_KEY> find = SD_TERM_KEY_dao.find(finder);
					boolean empty = find==null||find.size()==0;
					String field62 = res.getField62_Reserved_Private();
					
					/**设置终端密钥表*/
					if (field62==null || field62.equals("") || field62.length()<32) {
						throw new Exception("签到密钥返回格式错误");
					}
					SD_TERM_KEY sd_TERM_KEY = empty?new SD_TERM_KEY():find.get(0);
					String lmkTmk = new String(HexBinary.decode(sd_TERM_KEY.getLMK_TMK()), "GB2312");
					String lmkTak =  new String(HexBinary.decode(field62), "GB2312");
					byte[] s = HexBinary.decode(lmkTak);
					byte[] k = HexBinary.decode(lmkTmk);
					try {
						//设置tmktak
						sd_TERM_KEY.setTMK_TAK(HexBinary.encode(DesTools.decrypt(s, k)));
					} catch (Exception e) {
						e.printStackTrace();
					}
					/*设置lmktak*/
					sd_TERM_KEY.setLMK_TAK(field62);
					sd_TERM_KEY.setUPDATE_TIME(DATE_FORMAT.format(new Date()));
					if (empty) {
						sd_TERM_KEY.setMERCH_ID(pos.getMERCH_ID());
						sd_TERM_KEY.setTERM_ID(pos.getTERM_ID());
						sd_TERM_KEY.setTERM_TYPE("04");
						sd_TERM_KEY.setOPER_ID("01 ");
						SD_TERM_KEY_dao.save(sd_TERM_KEY);
					}
				} else {
					pos.setSTATUS("3");
					pos.setUPDATE_TIME(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());
				}
			} catch (Exception e) {
				logger.error(getStackTrace(e));
			}
		}
	}
	
	public void prizeDown(String term_ID, String merch_ID, String batch_NO) throws Exception {
		MessageObject hdReq = new MessageObject();
		hdReq.setMesstype("0900");
		hdReq.setField03_Processing_Code("030000");
		hdReq.setField11_System_Trace_Audit_Number("000231");
		hdReq.setField41_Card_Acceptor_Terminal_ID(term_ID);
		hdReq.setField42_Card_Acceptor_ID(merch_ID);
		hdReq.setField49_Currency_Code_Of_Transaction("0007");
		hdReq.setField60_Reserved_Private("00");
		hdReq.setTranCode("090003");
		try {
			/**调用管理类交易发送民生并返回*/
			MessageObject res = posAction.manageTran(hdReq);
			String f39 = res.getField39_Response_Code();
			if(f39.equals("00")) {
				String f48 = res.getField48_Additional_Data().substring(2,res.getField48_Additional_Data().length());
				List<String> hd = chaiFen(f48, 2, f48.length());
			}
		} catch (Exception e) {
			logger.error(getStackTrace(e));
		}
	}
	/**
	 * 领奖查询
	 */
	public void prizeQuery() {
//		MessageObject hdReq = new MessageObject();
//		hdReq.setMesstype("0700");
		
//		hdReq.setField02_Primary_Account_Number("110102198604070086");
//		hdReq.setField02_Primary_Account_Number("4218709980350603");
		
//		hdReq.setField02_Primary_Account_Number("4218719920000275");
//		hdReq.setField03_Processing_Code("000000");
//		hdReq.setField11_System_Trace_Audit_Number("000117");
//		hdReq.setField14_Date_Of_Expired("1901");
//		hdReq.setField22_Point_Of_Service_Entry_Mode("010");
//		hdReq.setField41_Card_Acceptor_Terminal_ID("00000001");
//		hdReq.setField42_Card_Acceptor_ID("000000000EM1001");
//		hdReq.setField48_Additional_Data("010007000000000100天天民生日    ");
//		hdReq.setField49_Currency_Code_Of_Transaction("0007");
//		hdReq.setField54_Balance_Amount("01513229198511110010");
//		hdReq.setField60_Reserved_Private("000002");
//		hdReq.setField64_MAC("3131313131313131");
//		hdReq.setTranCode("070000");
		
		
//		MessageObject request = new MessageObject();
//		request.setMesstype("0720");
//		request.setField02_Primary_Account_Number("4218719920000275");
//		request.setField03_Processing_Code("010000");
//		request.setField11_System_Trace_Audit_Number("000126");
//		request.setField14_Date_Of_Expired("1901");
//		request.setField22_Point_Of_Service_Entry_Mode("010");
//		request.setField41_Card_Acceptor_Terminal_ID("00000001");
//		request.setField42_Card_Acceptor_ID("000000000EM1001");
//		request.setField48_Additional_Data("303130303037303030303030303030313030CCECCCECC3F1C9FAC8D520202020");
//		request.setField49_Currency_Code_Of_Transaction("0007D");
//		request.setField54_Balance_Amount("3031353133323239313938353131313130303130");
//		request.setField60_Reserved_Private("000002");
//		request.setField64_MAC("3131313131313131");
//		request.setTranCode("070001");
//		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQ"+request);
//		try {
//			/**调用管理类交易发送民生并返回*/
//			MessageObject res = posAction.manageTran(request);
//			String f39 = res.getField39_Response_Code();
//			if(f39.equals("00")) {
//				String f48 = res.getField48_Additional_Data().substring(2,res.getField48_Additional_Data().length());
//				List<String> hd = chaiFen(f48, 2, f48.length());
//			}
//		} catch (Exception e) {
//			logger.error(getStackTrace(e));
//		}
	}
	/**
	 * 领奖消费
	 */
	public void xiaofei(){
//		MessageObject request = new MessageObject();
//		request.setMesstype("0700");
//		request.setField02_Primary_Account_Number("4218719920000275");
//		request.setField03_Processing_Code("010000");
//		request.setField11_System_Trace_Audit_Number("000135");
//		request.setField14_Date_Of_Expired("1901");
//		request.setField22_Point_Of_Service_Entry_Mode("010");
//		request.setField41_Card_Acceptor_Terminal_ID("00000001");
//		request.setField42_Card_Acceptor_ID("000000000EM1001");
//		request.setField48_Additional_Data("010007000000000100天天民生日    ");
//		request.setField49_Currency_Code_Of_Transaction("0007D");
//		request.setField54_Balance_Amount("01513229198511110010");
//		request.setField60_Reserved_Private("000002");
//		request.setField64_MAC("3131313131313131");
//		request.setTranCode("070001");
	}
	
	/**
	 * 将字符串从某一位开始截取某长度的若干段
	 * 
	 * @param s
	 * @param start
	 * @param weiShu
	 * @return
	 */
	private static List<String> chaiFen(String s, int start, int weiShu) {
		ArrayList<String> ret = new ArrayList<String>();
		int end = start + weiShu;
		while (s.length() >= end) {
			ret.add(s.substring(start, end));
			start = end;
			end += weiShu;
		}
		return ret;
	}
	public static void main(String[] args) {
		String lmkTmk;
		try {
			lmkTmk = new String(HexBinary.decode("34633431346434623436346634303439"), "GB2312");
			String lmkTak =  new String(HexBinary.decode("34433636454335313734363335384330"), "GB2312");
			byte[] s = HexBinary.decode(lmkTak);
			byte[] k = HexBinary.decode(lmkTmk);
			try {
				//设置tmktak
				System.out.println(HexBinary.encode(DesTools.decrypt(s, k)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
