package cn.mocredit.posp.task;

import static cn.mocredit.posp.bc.util.HexBinary.decode;
import static java.lang.String.format;
import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import cn.m.mt.po.Bank;
import cn.m.mt.po.SD_POINTS_INFO;
import cn.m.mt.po.SD_POS_TERM;
import cn.m.mt.po.SD_TERM_KEY;
import cn.m.mt.po.SD_TRAN_LS;
import cn.mocredit.posp.bc.util.AmountUtil;
import cn.mocredit.posp.bc.util.DateUtils;
import cn.mocredit.posp.bc.util.DesEncrypt;
import cn.mocredit.posp.bc.util.MessageObject;
import cn.mocredit.posp.main.PosAction;

@Service
@Transactional
public class Task {
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

	private static Logger logger = Logger.getLogger(Task.class);
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

	public void qianDao() {
		List<SD_POS_TERM> allPos = SD_POS_TERM_dao.findAll();
		if (allPos == null) {
			return;
		}
		for (SD_POS_TERM pos : allPos) {
	        if ("000000000012345".equals(pos.getMERCH_ID()) && "10200001".equals(pos.getTERM_ID())) continue;
			/**
			 * 商户终端类型校验，只签到民生虚拟终端*
			 * 商户终端类型 
			 * 		00-亿美汇金 01-雅高 02-雅酷 03-民生积分
			 */
			if (!"03".equals(pos.getTERM_TYPE())) continue;
			MessageObject req = new MessageObject();
			req.setMesstype("0800");
			/**获取终端流水号+1,初次签到默认为 000001*/
			req.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(pos.getPOS_NO())+1));
			/**设置终端编号*/
			req.setField41_Card_Acceptor_Terminal_ID(pos.getTERM_ID());
			/**设置商户编号*/
			req.setField42_Card_Acceptor_ID( pos.getMERCH_ID());
			/**设置60.1交易类型码、60.2批次号、60.3网络管理信息吗 001-单倍长密钥算法终端用001,003-双倍长密钥算法终端用003*/
			req.setField60_Reserved_Private("00" + pos.getBATCH_NO() + "003");
			/**设置操作员，默认为01加一个空格*/
			req.setField63_Reserved_Private("01 ");
			/**设置交易码*/
			req.setTranCode("080000");
			try {
				/**调用管理类交易发送民生并返回*/
				MessageObject res = posAction.manageTran(req);
				String f39 = res.getField39_Response_Code();
				pos.setRETCODE(f39 == null || f39.length() == 0 ? "EMPTY" : f39);
				if (f39 != null && !f39.equals("") && f39.equals("00")) {
					/**签到成功，设置虚拟终端信息表*/
					pos.setSTATUS("1");//设置签到状态0-正常 1-签到 2-签退 3-异常
					/** 跟新终端流水号、批次号、更新时间*/
					pos.setPOS_NO(String.format("%06d", Integer.parseInt(pos.getPOS_NO())+1));
					pos.setBATCH_NO(res.getField60_Reserved_Private().substring(2, 8));//设置批次号
					pos.setUPDATE_TIME(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());//设置更新时间
					Finder finder = new Finder("from SD_TERM_KEY where TERM_ID=:TERM_ID and MERCH_ID=:MERCH_ID and TERM_TYPE='03'");
					finder.setParam("TERM_ID", pos.getTERM_ID());
					finder.setParam("MERCH_ID", pos.getMERCH_ID());
					List<SD_TERM_KEY> find = SD_TERM_KEY_dao.find(finder);
					boolean empty = find==null||find.size()==0;
					SD_TERM_KEY sd_TERM_KEY = empty?new SD_TERM_KEY():find.get(0);
					
					String field62 = res.getField62_Reserved_Private();
					/**设置终端密钥表*/
					if (field62==null || field62.equals("") || field62.length()<48) {
						throw new Exception("签到密钥返回格式错误");
					}
					/*设置lmktpk*/
					sd_TERM_KEY.setLMK_TPK(field62.substring(0,32));
					/*设置lmktak*/
					sd_TERM_KEY.setLMK_TAK(field62.substring(40,72));
					//民生提供的主密钥
//					String lmkTmk = "FD4FF48ADA3E6E102013A24A04D36298";
					//costa咖啡测试2014-03-14测试
//					String lmkTmk = "A4F8D99EF116529E8F67EFD3047A3438";
					/*设置tmktak*/
					sd_TERM_KEY.setTMK_TAK(DesEncrypt.decryptDES(sd_TERM_KEY.getLMK_TMK(), field62.substring(40,72)));
					/*设置tmktpk*/
//					sd_TERM_KEY.setTMK_TPK(DesEncrypt.decryptDES(lmkTmk, field62.substring(0,32)));
					sd_TERM_KEY.setUPDATE_TIME(DATE_FORMAT.format(new Date()));
					if (empty) {
						sd_TERM_KEY.setMERCH_ID(pos.getMERCH_ID());
						sd_TERM_KEY.setTERM_ID(pos.getTERM_ID());
						sd_TERM_KEY.setTERM_TYPE("03");
						sd_TERM_KEY.setOPER_ID("01 ");
						SD_TERM_KEY_dao.save(sd_TERM_KEY);
					}
					/**调用更新活动列表交易*/
					gxhdlb(pos.getTERM_ID(), pos.getMERCH_ID(), pos.getBATCH_NO());
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
	 * 更新活动列表
	 * @param term_ID
	 * @param merch_ID
	 * @param batch_NO
	 * @throws Exception
	 */
	private void gxhdlb(String term_ID, String merch_ID, String batch_NO) throws Exception {
		// 获得活动列表
		MessageObject hdReq = new MessageObject();
		hdReq.setMesstype("0820");
		hdReq.setField41_Card_Acceptor_Terminal_ID(term_ID);
		hdReq.setField42_Card_Acceptor_ID(merch_ID);
		hdReq.setField60_Reserved_Private("00" + batch_NO + "384");
		hdReq.setField62_Reserved_Private("100");
		hdReq.setTranCode("082000384");
		List<String> chanPinBianHao = new ArrayList<String>();
		int sum = 0;
		do {
			MessageObject manageTran = posAction.manageTran(hdReq);
			String f39 = manageTran.getField39_Response_Code();
			if (f39==null||!f39.equals("00")) {
				logger.error("E33BE1C7C6E5436B8AA195D367B85BFF 获得活动列表出错：39域不为00");
				break;
			}
			String hd = manageTran.getField62_Reserved_Private();
			if (hd.length() % 2 != 0) {
				hd = "3" + hd;
			}
//			String head = hd.substring(0, 1);
			String head = hd.substring(0, 2);
//			if ("0".equals(head)){
			if("30".equals(head)){	
				logger.error("09ECF638FD1E4FD0B4563B05FD0B5E44  获得活动列表出错：民生返回了空列表！");
				break;
			}
			List<String> bianHao = chaiFen(hd, 2, 30);
			for(int i=0;i<bianHao.size();i++) {
				logger.info("["+i+"]"+bianHao.get(i));
			}
			chanPinBianHao.addAll(bianHao);
//			if ("1".equals(head) || "3".equals(head)) {
			if ("31".equals(head) || "33".equals(head)) {
				break;
//			} else if ("2".equals(head)) {
			} else if ("32".equals(head)) {
				sum += bianHao.size();
//				hdReq.setField62_Reserved_Private("1" + (format("%02d", sum)));
				hdReq.setField62_Reserved_Private("1" + (format("%02d", sum)));
			}
		} while (true);
		String hql = "from SD_POINTS_INFO where TREM_ID='" + term_ID + "' and MERCH_ID='" + merch_ID + "'";
		List<SD_POINTS_INFO> infoList = SD_POINTS_INFO_dao.find(new Finder(hql));
		HashMap<String, SD_POINTS_INFO> map = new HashMap<String, SD_POINTS_INFO>();
		HashMap<String, String> pToA = new HashMap<String, String>();
		HashMap<String, String> aToP = new HashMap<String, String>();
		if (infoList != null) {
			for (SD_POINTS_INFO info : infoList) {
				map.put(info.getACTIVTY_ID(), info);
				pToA.put(info.getPRODUCT_ID(),info.getACTIVTY_ID());
				aToP.put(info.getACTIVTY_ID(), info.getPRODUCT_ID());
			}
		}
		for (String a : map.keySet()) {
			if (!chanPinBianHao.contains(aToP.get(a))) {
				SD_POINTS_INFO sd_POINTS_INFO = map.get(a);
				String activty_ID = sd_POINTS_INFO.getACTIVTY_ID();
				if ("MSYH02".equals(activty_ID) || "MS0001".equals(activty_ID)) {
					continue;
				}
				sd_POINTS_INFO.setSTATUS("1");// 将不在活动列表中的活动设置为停用
			}
		}
		Set<String> allActivtyIdSet = new HashSet<String>();
		for (SD_POINTS_INFO info : SD_POINTS_INFO_dao.findAll()) {
			allActivtyIdSet.add(info.getACTIVTY_ID());
		}
		for (String bh : chanPinBianHao) {
			SD_POINTS_INFO info = null;
			String activty_id = pToA.get(bh);
			if (activty_id != null) {
				info = map.get(activty_id);
			}else {
				info = new SD_POINTS_INFO();
				info.setMERCH_ID(merch_ID);
				info.setTREM_ID(term_ID);
				info.setPRODUCT_ID(bh);
				for (int i = 0; i < 10000; i++) {
					String s = "MS" + format("%04d", i);
					if (!allActivtyIdSet.contains(s)) {
						info.setACTIVTY_ID(s);
						break;
					}
				}
				info.setACTIVTY_TYPE("1");
				info.setBANK_ID(bankId());
				info.setSTATUS("0");
				info = SD_POINTS_INFO_dao.save(info);
				map.put(info.getACTIVTY_ID(), info);
			}
			info.setSTATUS("0");
			MessageObject req = new MessageObject();
			req.setMesstype("0800");
			req.setField41_Card_Acceptor_Terminal_ID(term_ID);
			req.setField42_Card_Acceptor_ID(merch_ID);
			req.setField60_Reserved_Private("00" + batch_NO + "373");
			req.setField62_Reserved_Private(bh);
			req.setTranCode("080000373");
			String hdxq = "3"+posAction.manageTran(req).getField62_Reserved_Private();
			info.setPOINTS(new String(decode(hdxq.substring(30, 50)),"GBK").trim());
			info.setPRODUCT_NAME(new String(decode(hdxq.substring(50, 170)), "GBK").trim());
		}
	}
	
	private String bankId() {
		List<Bank> bk = bankDao.findByProperty("sname", "cmbc");
		return (bk == null || bk.size() != 1) ? "" : bk.get(0).getId().toString();
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
	public void doNothing(){
		
	}
	/**
	 * 签退
	 * @param request
	 */
	public void signOut(SD_POS_TERM sdPosTerm){
			/**
			 * 商户终端类型校验，只签到民生虚拟终端*
			 * 商户终端类型 
			 * 		00-亿美汇金 01-雅高 02-雅酷 03-民生积分
			 */
			MessageObject req = new MessageObject();
			req.setMesstype("0820");
			/**获取终端流水号+1,初次签到默认为 000001*/
			req.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(sdPosTerm.getPOS_NO())+1));
			/**设置终端编号*/
			req.setField41_Card_Acceptor_Terminal_ID(sdPosTerm.getTERM_ID());
			/**设置商户编号*/
			req.setField42_Card_Acceptor_ID( sdPosTerm.getMERCH_ID());
			req.setField60_Reserved_Private("00" + sdPosTerm.getBATCH_NO() + "002");
			/**设置交易码*/
			req.setTranCode("082000002");
			try {
				/**调用管理类交易发送民生并返回*/
				MessageObject res = posAction.manageTran(req);
				String f39 = res.getField39_Response_Code();
				
				sdPosTerm.setRETCODE(f39 == null || f39.length() == 0 ? "EMPTY" : f39);
				if (f39 != null && !f39.equals("") && f39.equals("00")) {
					/**签到成功，设置虚拟终端信息表*/
					sdPosTerm.setSTATUS("2");//设置签到状态0-正常 1-签到 2-签退 3-异常
					/** 跟新终端流水号、批次号、更新时间*/
					sdPosTerm.setPOS_NO("000000");
					sdPosTerm.setBATCH_NO(res.getField60_Reserved_Private().substring(2, 8));//设置批次号
					sdPosTerm.setUPDATE_TIME(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());//设置更新时间
				} else {
					sdPosTerm.setSTATUS("3");
					sdPosTerm.setUPDATE_TIME(DateUtils.getCurrentDate()+DateUtils.getCurrentTime());
				}
			} catch (Exception e) {
				logger.error(getStackTrace(e));
			}
//		}
	}
	/**
	 * 批结算
	 * @param request
	 */
//	public void batchSettlement(MessageObject request) {
	public void batchSettlement() {
//		request.setMesstype("0500");
//		request.setField11_System_Trace_Audit_Number("000252");
//		request.setField41_Card_Acceptor_Terminal_ID("00000001");
//    	request.setField42_Card_Acceptor_ID("305310000000160");
//    	request.setField48_Additional_Data("00000000000000000000000000000010000000000000000000000000000001");
//    	request.setField49_Currency_Code_Of_Transaction("156");
//    	request.setField60_Reserved_Private("00000121201");
//    	request.setField63_Reserved_Private("01 ");
//    	request.setTranCode("0500201");
    	List<SD_POS_TERM> allPos = SD_POS_TERM_dao.findAll();
		if (allPos == null) {
			return;
		}
		for (SD_POS_TERM pos : allPos) {
			/**
			 * 商户终端类型校验，只签到民生虚拟终端*
			 * 商户终端类型 
			 * 		00-亿美汇金 01-雅高 02-雅酷 03-民生积分
			 */
			if (!"03".equals(pos.getTERM_TYPE())) continue;
			MessageObject req = new MessageObject();
			req.setMesstype("0500");
			/**获取终端流水号+1*/
			req.setField11_System_Trace_Audit_Number(String.format("%06d", Integer.parseInt(pos.getPOS_NO())+1));
			/**设置终端编号*/
			req.setField41_Card_Acceptor_Terminal_ID(pos.getTERM_ID());
			/**设置商户编号*/
			req.setField42_Card_Acceptor_ID( pos.getMERCH_ID());
			req.setField60_Reserved_Private("00" + pos.getBATCH_NO() + "201");
			/**结算总额和笔数统计算法如下：
			 	借记总金额：∑（消费金额 + 预授权完成金额+离线结算金额+结算调整金额）
				借记总笔数：∑（消费笔数 + 预授权完成笔数+离线结算笔数+结算调整金额）
				贷记总金额：∑ ( 退货金额 + 消费撤消金额 + 预授权完成撤消金额 ）
				贷记总笔数：∑（退货笔数 + 消费撤消笔数 + 预授权完成撤消笔数 )
			 */
			String hql = "from SD_TRAN_LS where MERCH_ID='"+ pos.getMERCH_ID()
					+ "' and TERM_ID='"+ pos.getTERM_ID()
					+ "' and BATCH_NO='"+ pos.getBATCH_NO()
					+ "' and TRAN_TYPE='22' and TRAN_FLAG='0'";
			List<SD_TRAN_LS> list = SD_TRAN_LS_dao.find(new Finder(hql));
			String sum = "0";
			if (list!=null) {
				for (SD_TRAN_LS ls : list) {
					sum = AmountUtil.sumAmount(sum,ls.getTRAN_AMT().toString());
				}
				String format = String.format("%012d", Integer.parseInt(sum.replaceAll("\\.", "")));
				String size = String.format("%03d", list.size());
				req.setField48_Additional_Data(format+size+"00000000000000010000000000000000000000000000001");
			}else{
				req.setField48_Additional_Data("00000000000000000000000000000010000000000000000000000000000001");
			}
			req.setField49_Currency_Code_Of_Transaction("156");
			req.setField63_Reserved_Private("01 ");
			/**设置交易码*/
			req.setTranCode("0500201");
			try {
				/**调用管理类交易发送民生并返回*/
				MessageObject res = posAction.manageTran(req);
				/**签退*/
				signOut(pos);
			} catch (Exception e) {
				logger.error(getStackTrace(e));
			}
		}
	}
	public static void main(String[] args) {
//		String s = "1303030303030303030303030333232303030303030303030303030333233303030303030303030303030333234303030303030303030303030333235303030303030303030303030333231303030303030303030303030323435303030303030303030303030323433303030303030303030303030323434303030303030303030303030323631303030303030303030303030333033";
//		s =s.substring(1,s.length());
//		List<String> bianhaos = chaiFen(s, 0, 30);
//		System.out.println(s);
//		for(int i=0;i<bianhaos.size();i++) {
//			System.out.println(bianhaos.get(i));
//		}
//		System.out.println("1234567890098765432112345".substring(15, 25));
//		Task task = new Task();
//		task.qianDao();
//		String a = "30303030303030303030303032343335303020202020202020ccd7b2cd4220202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020";	
//		try {
//			String s2 = new String(decode(a.substring(0,30)),"GBK");
//			System.out.println("["+s2.trim()+"]");
//			String s = new String(decode(a.substring(30, 50)),"GBK").trim();
//			System.out.println("["+s+"]");
//			String s1 =new String(decode(a.substring(50, 170)),"GBK").trim();
//			System.out.println("["+s1+"]");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String format = String.format("%012d", Integer.parseInt("200"));
		String size = String.format("%03d", 1);
		System.out.println(size);
		System.out.println(format);
	}
}
