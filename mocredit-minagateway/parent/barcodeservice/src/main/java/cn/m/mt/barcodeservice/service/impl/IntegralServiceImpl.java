package cn.m.mt.barcodeservice.service.impl;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.getInstance;
import static java.util.Collections.unmodifiableSet;
import static java.util.Locale.CHINA;
import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.m.common.hibernate3.Finder;
import cn.m.mt.barcodeservice.service.IntegralService;
import cn.m.mt.barcodeservice.util.ToolUtils;
import cn.m.mt.barcodeservice.zx.TestZXWsClient;
import cn.m.mt.barcodeservice.zx.ZXWsClient;
import cn.m.mt.dao.BankDao;
import cn.m.mt.dao.ChecklogDao;
import cn.m.mt.dao.DeviceDao;
import cn.m.mt.dao.EitemDao;
import cn.m.mt.dao.EitemstoreDao;
import cn.m.mt.dao.PaymentDao;
import cn.m.mt.dao.SD_CARD_TYPE_Dao;
import cn.m.mt.dao.SD_POINTS_INFO_Dao;
import cn.m.mt.dao.SD_TRAN_LS_Dao;
import cn.m.mt.po.Bank;
import cn.m.mt.po.Device;
import cn.m.mt.po.Eitem;
import cn.m.mt.po.Enterprise;
import cn.m.mt.po.Payment;
import cn.m.mt.po.SD_CARD_TYPE;
import cn.m.mt.po.SD_POINTS_INFO;
import cn.m.mt.po.SD_TRAN_LS;
import cn.m.mt.po.Shop;
import cn.m.mt.po.Store;
import cn.m.mt.service.impl.BaseServiceImpl;
import cn.m.mt.util.DateTimeUtils;
import cn.m.mt.util.RandomUtil;
@Service
public class IntegralServiceImpl extends BaseServiceImpl implements
	IntegralService {

	@Autowired
	private BankDao bankdao;
	@Autowired
	private DeviceDao devicedao;
	@Autowired
	private EitemstoreDao eitemstoredao;
	@Autowired
	private EitemDao eitemdao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private ChecklogDao checklogdao;
	@Autowired
	BankDao bankDao;
	@Autowired
	SD_POINTS_INFO_Dao SD_POINTS_INFO_dao;
	@Autowired
	SD_CARD_TYPE_Dao SD_CARD_TYPE_dao;
	@Autowired
	SD_TRAN_LS_Dao SD_TRAN_LS_dao;
	/** 离线活动的ACTIVTY_ID */
	public static final Set<String> LI_XIAN_HUO_DONG = initSetSet();
	private boolean istest = false;
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmssSSS");

	private static Set<String> initSetSet() {
		Set<String> s = new HashSet<String>();
		s.add("ZSYH11");//招商银行 周三5折活动日
		s.add("ZSYH12");//招商银行 更多活动敬请期待
		s.add("TJYH11");//天津银行北京分行  天津银行10元观影 北京耀莱成龙国际影城
		s.add("TJYH22");//天津银行总行  天津银行总行 优奶奶昔冰淇淋满30元减15元 天津银行优奶奶昔
		s.add("ZGYH01");//中国银行  中国银行周一“巴黎贝甜满50减25”活动
		s.add("BOB001");//北京银行  
		s.add("BOB002");//北京银行  
		s.add("BOB003");//北京银行  
		s.add("BOB004");//北京银行  
		s.add("CEB001");//光大银行
		s.add("CEB002");//光大银行
		return unmodifiableSet(s);
	}

	@Override
	public String getBank(String imei) {
		StringBuffer sbstr = new StringBuffer();
		Device device =  checkDevice(imei);
		if(device == null){
			return retstr(retErrorMsg("101","无使用权限!"));
		}
//		List<Bank> banklist = bankdao.findAll();
		List<Bank> banklist = new ArrayList<Bank>();
		Bank bank1 = new Bank();
		bank1.setId(1l);
		bank1.setBankname("中信银行");
		banklist.add(bank1);
		Bank bank2 = new Bank();
		bank2.setId(2l);
		bank2.setBankname("民生银行");
		banklist.add(bank2);
		
		for(Bank bank:banklist){
			sbstr.append("<Table>");
			sbstr.append("<bankname>");
			sbstr.append(bank.getBankname());
			sbstr.append("</bankname>");
			sbstr.append("<bankid>");
			sbstr.append(bank.getId());
			sbstr.append("</bankid>");
			sbstr.append("</Table>");
		}
		sbstr.append("</NewDataSet>");
		return retstr(sbstr.toString());
	}
	
	
	
	private String retstr(String str) {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append(str);
		return sbstr.toString();
	}

	private Bank getMinShengBank(Long id) {
		return bankdao.get(id);
	}

	private List<Eitem> getMinShengEitems(String imei,String bankId) {
		ArrayList<Eitem> ret = new ArrayList<Eitem>();
		List<Device> deviceList = devicedao.findByProperty("devcode", imei);
		if (deviceList == null || deviceList.size() != 1) {
			return ret;
		}
		String bankTest = bankId != null && bankId.trim().length() != 0 ? (" and i.BANK_ID='" + bankId.trim() + "'") : "";
		String hql = "select i from SD_POINTS_INFO i, SD_POS_TERM t where " 
				+ "i.MERCH_ID=t.MERCH_ID and i.TREM_ID=t.TERM_ID and i.STATUS='0' and t.STATUS='1' and (t.TERM_TYPE='03' or t.TERM_TYPE='04' or t.TERM_TYPE='05') and t.shop.id = " 
				+ deviceList.get(0).getShop().getId() + bankTest + " order by i.SHOW_ORDER";
		System.out.println("B6BBE643781C43F7B1D10FE1E082589C 根据机具号获得民生银行活动列表hql：" + hql);
		List<SD_POINTS_INFO> list = SD_POINTS_INFO_dao.find(new Finder(hql));
		if (list != null) {
			for (SD_POINTS_INFO i : list) {
				Eitem eitem = new Eitem();
				eitem.setExpointType(3);
				eitem.setBank(getMinShengBank(Long.parseLong(i.getBANK_ID())));
				eitem.setName(i.getPRODUCT_NAME());
				eitem.setOuterid(i.getACTIVTY_ID());
				ret.add(eitem);
			}
		}
		return ret;
	}

	@Override
	public String getEitemByBank(String imei, String bankid) {
		StringBuffer sbstr = new StringBuffer();
		Device device =  checkDevice(imei);
		if(device == null){
			return retstr(retErrorMsg("101","无使用权限!"));
		}
//		String endtime = DateTimeUtils.getDate("yyyy-MM-dd");
////		endtime = endtime+" 23:59:59";
//		Finder finder  = new Finder(" from Eitemstore where store.id=:storeid and (eitem.endtime = '' or eitem.endtime is null or eitem.endtime>=:endtime)  and eitem.bank is not null ");
//		finder.setParam("storeid", device.getStore().getId());
//		finder.setParam("endtime", endtime);
//		List<Eitemstore> eslist = eitemstoredao.find(finder);
//		List<Eitem> minShengEitems = getMinShengEitems(imei,bankid);
//		if ((eslist == null || eslist.size() == 0) && minShengEitems.size() == 0) {
//			sbstr.append("<Table>");
//			sbstr.append("<isSuccess>true</isSuccess>");
//			sbstr.append("</Table>");
//			sbstr.append("</NewDataSet>");
//			return retstr(sbstr.toString());
//		}
//		List<Eitem> elist = new ArrayList<Eitem>();
//		if (eslist != null) {
//			for (Eitemstore estore : eslist) {
//				elist.add(estore.getEitem());
//			}
//		}
//		elist.addAll(minShengEitems);
		/*if(bankid!=null && !"".equals(bankid)){
			String[] ids = bankid.split(",");
			if(ids.length  == elist.size()){
				int len = 0;
				for(String id:ids){
					for(Eitem et:elist){
						if(et.getOuterid().equals(id)){
							len++;
							break;
						}
					}
				}
				if(len ==ids.length){
					sbstr.append("<Table>");
					sbstr.append("<isSuccess>false</isSuccess>");
					sbstr.append("</Table>");
					sbstr.append("</NewDataSet>");
					return sbstr.toString();
				}
			}
		}*/
		
		List<Eitem> elist = new ArrayList<Eitem>();
		Eitem et1 = new Eitem();
		et1.setName("招商银行 周三5折活动日积分积分积分");
		et1.setId(1l);
		et1.setIspayment(0);
		et1.setIszxcheck(0);
		Enterprise ent = new Enterprise();
		ent.setEntname("招商银行");
		et1.setEnterprise(ent);
		et1.setCodetimes(1);
		Bank bank = new Bank();
		bank.setId(1l);
		et1.setBank(bank);
		et1.setExpointType(1);
		et1.setOuterid("12121212");
		elist.add(et1);
		for(Eitem et:elist){
					sbstr.append("<Table>");
					sbstr.append("<eitemname>");
					sbstr.append(et.getName());
					sbstr.append("</eitemname>");
					sbstr.append("<outerid>");
					sbstr.append(et.getOuterid());
					sbstr.append("</outerid>");
					sbstr.append("<bankid>");
					sbstr.append(et.getBank().getId());
					sbstr.append("</bankid>");
					// 0:"否",1:"输入积分兑换",2:"输入金额兑换",3:"固定积分"
					sbstr.append("<expointtype>");
					sbstr.append(et.getExpointType());
					sbstr.append("</expointtype>");
					sbstr.append("</Table>");
			}
		sbstr.append("</NewDataSet>");
		return retstr(sbstr.toString());
	}
	
	private String retErrorMsg(String errorCode,String errorMsg){
		StringBuffer sb = new StringBuffer();
		sb.append("<Table>");
		sb.append("<isSuccess>false</isSuccess>");
		sb.append("<errorCode>");
		sb.append(errorCode);
		sb.append("</errorCode>");
		sb.append("<errorMsg>");
		sb.append(errorMsg);
		sb.append("</errorMsg>");
		sb.append("</Table>");
		sb.append("</NewDataSet>");
		return sb.toString();
	}
	
	private Device checkDevice (String imei){
		List<Device> devicelist = devicedao.findByProperty("devcode", imei);
		if (null == devicelist || devicelist.isEmpty()) {
			return null;
		}
		return devicelist.get(0);
	}

	@Override
	public String exchangeIntegral(String imei, String account,
			String integral, String outerid) {
		return exchangeIntegral(imei,account,integral,outerid,null,null);
	}
	
	
	
	private String getOverMssages(Payment payment,Store store,Eitem eitem,String status) {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<Table>");
		sbstr.append("<isSuccess>true</isSuccess>");
		Shop shop = store.getShop();
		sbstr.append("<shopname>");
		sbstr.append(shop.getName());
		sbstr.append("</shopname>");
		sbstr.append("<shopno>");
		String batchno = String.format(
				"%012d",
				Long.valueOf(shop.getId() + ""
						+ store.getId()));
		sbstr.append(batchno);
		sbstr.append("</shopno>");
		
		sbstr.append("<storename>");
		sbstr.append(store.getName());
		sbstr.append("</storename>");
		sbstr.append("<imei>");
		sbstr.append(payment.getImei());
		sbstr.append("</imei>");
		sbstr.append("<eitemtime>");
		sbstr.append(eitem.getEndtime());
		sbstr.append("</eitemtime>");
		sbstr.append("<trantype>");
		sbstr.append(status);
		sbstr.append("</trantype>");
		sbstr.append("<batchno>");
		sbstr.append(payment.getBatchno());
		sbstr.append("</batchno>");
		sbstr.append("<orderid>");
		sbstr.append(payment.getOrderid());
		sbstr.append("</orderid>");
		sbstr.append("<point>");
		sbstr.append(Integer.valueOf(payment.getTransamt())/100);
		sbstr.append("</point>");
		sbstr.append("<trantime>");
		String times = null;
		try {
			times = DateTimeUtils.dateToStr(DateTimeUtils.strToDate(payment.getTimestamp(), "yyyyMMddHHmmssSSS"),"yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sbstr.append(times);
		sbstr.append("</trantime>");
		sbstr.append("<admin>001</admin>");
		sbstr.append("<remark></remark>");
		sbstr.append("<eitemname>");
		sbstr.append(eitem.getName());
		sbstr.append("</eitemname>");
		sbstr.append("<bankname>");
		Bank bank = eitem.getBank();
		if(bank!=null)
			sbstr.append(bank.getBankname());
		else
			sbstr.append("");
		sbstr.append("</bankname>");
		sbstr.append("<cardno>");
		String str = payment.getPanstr();
		String str1= str.substring(0,4);
		String str2= str.substring(str.length()-4,str.length());
		StringBuffer sr= new StringBuffer();
		sr.append(str1);
		for(int i = 0;i<str.length()-8;i++){
			sr.append("*");
		}
		sr.append(str2);
		sbstr.append(sr.toString());
		sbstr.append("</cardno>");
		sbstr.append("<posno>");
		sbstr.append(payment.getPosno());
		sbstr.append("</posno>");
		sbstr.append("<eitemid>");
		sbstr.append(payment.getProducttype());
		sbstr.append("</eitemid>");
		sbstr.append("<payway>");
		sbstr.append(payment.getPayway());
		sbstr.append("</payway>");
		sbstr.append("</Table>");
		
		return sbstr.toString();
	}
	private String getOverMssages2(Payment payment,Store store,Eitem eitem,String status) {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<Table>");
		sbstr.append("<isSuccess>true</isSuccess>");
		Shop shop = store.getShop();
		sbstr.append("<shopname>");
		sbstr.append(shop.getName());
		sbstr.append("</shopname>");
		sbstr.append("<shopno>");
		String batchno = String.format(
				"%012d",
				Long.valueOf(shop.getId() + ""
						+ store.getId()));
		sbstr.append(batchno);
		sbstr.append("</shopno>");
		
		sbstr.append("<storename>");
		sbstr.append(store.getName());
		sbstr.append("</storename>");
		sbstr.append("<imei>");
		sbstr.append(payment.getImei());
		sbstr.append("</imei>");
		sbstr.append("<eitemtime>");
		sbstr.append(eitem.getEndtime());
		sbstr.append("</eitemtime>");
		sbstr.append("<trantype>");
		sbstr.append(status);
		sbstr.append("</trantype>");
		sbstr.append("<batchno>");
		sbstr.append(payment.getBatchno());
		sbstr.append("</batchno>");
		sbstr.append("<orderid>");
		sbstr.append(payment.getOrderid());
		sbstr.append("</orderid>");
		sbstr.append("<point>");
		sbstr.append(payment.getTransamt());
		sbstr.append("</point>");
		sbstr.append("<trantime>");
		String times = null;
		try {
			times = DateTimeUtils.dateToStr(DateTimeUtils.strToDate(payment.getTimestamp(), "yyyyMMddHHmmssSSS"),"yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sbstr.append(times);
		sbstr.append("</trantime>");
		sbstr.append("<admin>001</admin>");
		sbstr.append("<remark></remark>");
		sbstr.append("<eitemname>");
		sbstr.append(eitem.getName());
		sbstr.append("</eitemname>");
		sbstr.append("<bankname>");
		Bank bank = eitem.getBank();
		if(bank!=null)
			sbstr.append(bank.getBankname());
		else
			sbstr.append("");
		sbstr.append("</bankname>");
		sbstr.append("<cardno>");
		String str = payment.getPanstr();
		String str1= str.substring(0,4);
		String str2= str.substring(str.length()-4,str.length());
		StringBuffer sr= new StringBuffer();
		sr.append(str1);
		for(int i = 0;i<str.length()-8;i++){
			sr.append("*");
		}
		sr.append(str2);
		sbstr.append(sr.toString());
		sbstr.append("</cardno>");
		sbstr.append("<posno>");
		sbstr.append(payment.getPosno());
		sbstr.append("</posno>");
		sbstr.append("<eitemid>");
		sbstr.append(payment.getProducttype());
		sbstr.append("</eitemid>");
		sbstr.append("<payway>");
		sbstr.append(payment.getPayway());
		sbstr.append("</payway>");
		sbstr.append("</Table>");
		
		return sbstr.toString();
	}

	public String revIntegral(String imei, String orderid,String account,String batchno,String searchno,String posno,String time) {
		Device device =  checkDevice(imei);
		if(device == null){
			return retstr(retErrorMsg("101","无使用权限!"));
		}
		
		Payment payment = new Payment();
		List<Payment> plist  = null;
		if(batchno != null && searchno != null){
			payment.setBatchno(batchno);
			payment.setSearchno(searchno);
			payment.setPanstr(account);
			payment.setImei(imei);
			
//			payment.setMerchantid(eitem.getMerchantid());
//			payment.setMerchantpassword(eitem.getMerchantpassword());
			plist = paymentDao.findByEgList(payment);
		}else if(posno != null && time != null){
			Finder finder = new Finder("from Payment");
			finder.append(" where panstr = :panstr");
			finder.setParam("panstr", account);
			finder.append(" and posno like :posno");
			finder.setParam("posno", posno);
			finder.append(" and  substring(authdate,5,4) like :time");
			finder.setParam("time", "%"+time);
			plist = paymentDao.find(finder);
		}else if(orderid != null){
				payment.setOrderid(orderid);
//				payment.setMerchantid(eitem.getMerchantid());
//				payment.setMerchantpassword(eitem.getMerchantpassword());
				plist = paymentDao.findByEgList(payment);
		}
		if(plist == null || plist.isEmpty()){
			SD_TRAN_LS findLiuShui = findLiuShui(imei, orderid, account, batchno, searchno, posno, time);
			if (findLiuShui!=null) {
				return minshengRevIntegral(findLiuShui, imei, orderid, account, batchno, searchno, posno, time);
			}
			return retstr(retErrorMsg("202","没有找到对应的订单!"));
		}
		payment = plist.get(0);
	//		if(payment.getStatus() != 1 && payment.getStatus() != 2){
	//			return retstr(retErrorMsg("203","积分不可撤销!"));
	//		}
		if(null !=account){
			if( account.equals("7330015000464523")){
				account = "5201080000040103";
				payment.setPanstr(account);
			}else
				payment.setPanstr(account);
		}
		String rest = "";
		String date = payment.getAuthdate();
		String cdate = DateTimeUtils.getDate("yyyyMMdd");
		System.out.println("====payment===="+payment.getId());
		if(cdate.equals(date) || (payment.getStatus()==1 || payment.getStatus()==2)){
			if (istest) {
				rest = TestZXWsClient.dividedPaymentReversal(payment);
			} else {
				rest = ZXWsClient.dividedPaymentReversal(payment);
			}
		}else{
			if (istest) {
				rest = TestZXWsClient.hirePurchaseReturn(payment);
			} else {
				rest = ZXWsClient.hirePurchaseReturn(payment);
			}
		}
		if(rest.startsWith("error")){
			return retstr(retErrorMsg("204",rest));
		}
		payment.setStatus((short)4);
		payment.setImei(imei);
		paymentDao.update(payment);
		Eitem eitem = eitemdao.findUniqueByProperty("productcode", payment.getProducttype());
		String str = retstr(getOverMssages(payment,device.getStore(),eitem,"积分撤销"));
		str += "</NewDataSet>";
		return str;
	}

	private SD_TRAN_LS findLiuShui(String imei, String orderid, String account, String batchno, String searchno, String posno, String time) {
		String hql = "";
		if (account == null || account.length() == 0) {
			hql = "from SD_TRAN_LS where TRAN_STATUS='0' and WL_TERM_ID='"
					+ imei + "' and WL_BATCH_NO='" + batchno + "' and WL_POSNO='" + searchno + "'";
		}else{
			if (batchno == null || batchno.trim().length() == 0) {
				hql = "from SD_TRAN_LS where TRAN_STATUS='0' and WL_TERM_ID='" 
						+ imei + "' and CARD_NO='" + account  
						+ "' and WL_POSNO='" + (searchno == null || searchno.trim().length() == 0 ? orderid : searchno) + "'";
			}else {
				hql = "from SD_TRAN_LS where TRAN_STATUS='0' and WL_TERM_ID='" 
						+ imei + "' and CARD_NO='" + account + "' and WL_BATCH_NO='" + batchno 
						+ "' and WL_POSNO='" + (searchno == null || searchno.trim().length() == 0 ? orderid : searchno) + "'";
			}
		}
		List<SD_TRAN_LS> find = SD_TRAN_LS_dao.find(new Finder(hql));
		return find == null || find.size() != 1 ? null : find.get(0);
	}
	private String minshengRevIntegral(SD_TRAN_LS liuShui,String imei, String orderid,String account,String batchno,String searchno,String posno,String time) {
		
		return "";
	}
	private String minshengRevIntegralLixian(SD_TRAN_LS liuShui,String imei, String orderid,String account,String batchno,String searchno,String posno,String time) {
//		if (1==1) {
			return "";
//		}
	}



	@Override
	public String queryIntegral(String imei, String account) {
		Device device =  checkDevice(imei);
		if(device == null){
			return retstr(retErrorMsg("101","无使用权限!"));
		}
		Finder finder = new Finder(" from Eitem where iszxcheck = 1 order by id desc limit 1");
		List<Eitem> eitemlist = eitemdao.find(finder);
		Eitem eitem = eitemlist.get(0);
		if(account.equals("6221885840083275")){
			account = "5201080000040103";
		}
		Payment payment = new Payment();
		System.out.println("account----"+account);
		payment.setPanstr(account);
		
		System.out.println(" merchantid, merchantpassword===="+eitem.getMerchantid()+","+eitem.getMerchantpassword());
		payment.setMerchantid(eitem.getMerchantid());
		payment.setMerchantpassword(eitem.getMerchantpassword());
		String pointAmt = "0";
		if (istest) {
			pointAmt = TestZXWsClient.confirmInfo(payment);
		} else {
			pointAmt = ZXWsClient.confirmInfo(payment);
		}
		if(pointAmt.startsWith("error")){
			return retstr(retErrorMsg("301",pointAmt));
		}
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<Table>");
		sbstr.append("<isSuccess>true</isSuccess>");
		sbstr.append("<pointAmt>");
		sbstr.append(pointAmt);
		sbstr.append("</pointAmt>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return retstr(sbstr.toString());
	}
	public boolean isIstest() {
		return istest;
	}
	
	public void setIstest(boolean istest) {
		this.istest = istest;
	}



	@Override
	public String getPaymentCount(String imei,String outerid, String starttime, String endtime) {
		System.out.println("==imei==="+imei+"====outerid===="+outerid);
		Device device =  checkDevice(imei);
		if(device == null){
			return retstr(retErrorMsg("101","无使用权限!"));
		}
		
		starttime = starttime.replace("-","") + "000000000";
		endtime = endtime.replace("-","") + "235959999";
		Finder finder = new Finder("select producttype,count(producttype),sum(transamt)  from Payment where producttype in (:outerid) and imei=:imei and status<>0  and timestamp >:starttime and timestamp < :endtime  group by producttype");
		String []outerids = outerid.split(",");
		finder.setParamList("outerid",outerids);
		finder.setParam("imei",imei);
		finder.setParam("starttime",starttime);
		finder.setParam("endtime",endtime);
		List sulist = paymentDao.find(finder);
		Finder finder1 = new Finder("select producttype,count(producttype),sum(transamt)  from Payment where producttype in (:outerid) and imei=:imei and status=4 and timestamp >:starttime and timestamp < :endtime  group by producttype");
		finder1.setParamList("outerid",outerids);
		finder1.setParam("imei",imei);
		finder1.setParam("starttime",starttime);
		finder1.setParam("endtime",endtime);
		List badlist = paymentDao.find(finder1);
		StringBuffer sbstr = new StringBuffer();
		Shop shop = device.getStore().getShop();
		Store store = device.getStore();
		String shopname = shop.getName();
		String storename = store.getName();
		String shopno = String.format(
				"%012d",
				Long.valueOf(shop.getId() + ""
						+ store.getId()));
		if(sulist !=null && sulist.size()!=0){
			for(int i = 0;i < sulist.size();i++){
				Object[] objs = (Object[]) sulist.get(i);
				sbstr.append("<Table>");
				sbstr.append("<shopname>");
				sbstr.append(shopname);
				sbstr.append("</shopname>");
				sbstr.append("<storename>");
				sbstr.append(storename);
				sbstr.append("</storename>");
				sbstr.append("<shopno>");
				sbstr.append(shopno);
				sbstr.append("</shopno>");
				sbstr.append("<outerid>");
				sbstr.append(objs[0]);
				sbstr.append("</outerid>");
				sbstr.append("<sumcount>");
				sbstr.append(objs[1]);
				sbstr.append("</sumcount>");
				sbstr.append("<integral>");
				sbstr.append(Integer.valueOf((String)objs[2])/100);
				sbstr.append("</integral>");
				boolean bool = true;
				for(int j = 0;j < badlist.size();j++){
					Object[] objs1 = (Object[]) badlist.get(j);
					if(objs1[0].equals(objs[0])){
						sbstr.append("<recount>");
						sbstr.append(objs1[1]);
						sbstr.append("</recount>");
						sbstr.append("<reintegral>");
						sbstr.append(Integer.valueOf((String)objs1[2])/100);
						sbstr.append("</reintegral>");
						bool = false;
					}
				}
				if(bool){
					sbstr.append("<recount>0");
					sbstr.append("</recount>");
					sbstr.append("<reintegral>0");
					sbstr.append("</reintegral>");
				}
				sbstr.append("</Table>");
			}
		}else{
			sbstr.append("<Table>");
			sbstr.append("</Table>");
		}
		sbstr.append("</NewDataSet>");
		return retstr(sbstr.toString());
	}



	@Override
	public String getPaymentListByEitem(String imei, String outerid,
			String starttime, String endtime) {
		Device device =  checkDevice(imei);
		if(device == null){
			return retstr(retErrorMsg("101","无使用权限!"));
		}
		Eitem eitem = eitemdao.findUniqueByProperty("outerid", outerid);
		if(eitem == null){
			return retstr(retErrorMsg("201","没有找到对应的活动!"));
		}
		starttime = starttime.replace("-","") + "000000000";
		endtime = endtime.replace("-","") + "235959999";
		Finder finder = new Finder(" from Payment where producttype =:outerid and imei = :imei and status<>0  and timestamp >:starttime and timestamp < :endtime ");
		finder.setParam("outerid",outerid);
		finder.setParam("starttime",starttime);
		finder.setParam("imei",imei);
		finder.setParam("endtime",endtime);
		List<Payment> list = paymentDao.find(finder);
		StringBuffer sbstr = new StringBuffer();
		if(list !=null && list.size()!=0){
			for(Payment payment:list){
				String trantype = "积分兑换";
				if(payment.getStatus()==4)
					trantype = "积分撤销";
				sbstr.append(getOverMssages(payment,device.getStore(),eitem,trantype) );
				
			}
		}else{
			sbstr.append("<Table>");
			sbstr.append("</Table>");
		}
		sbstr.append("</NewDataSet>");
		return retstr(sbstr.toString());
	}



	@Override
	public String revocationIntegral(String imei, String orderid, String account) {
		return revIntegral(imei, orderid, account, null, null, null, null);
	}



	@Override
	public String redVerifyIntegralBySearchno(String imei,String account, String batchno,
			String searchno) {
		return revIntegral(imei, null, account, batchno, searchno, null, null);
	}



	@Override
	public String redVerifyIntegralByPosno(String imei,String account, String posno, String time) {
		return revIntegral(imei, null, account, null, null, posno, time);
	}


	private SD_POINTS_INFO getMinShengEitem(String imei, String outerid) {
		List<Device> deviceList = devicedao.findByProperty("devcode", imei);
		if (deviceList == null || deviceList.size() != 1) {
			return null;
		}
		String hql = "select i from SD_POINTS_INFO i, SD_POS_TERM t where " 
				+ "i.MERCH_ID=t.MERCH_ID and i.TREM_ID=t.TERM_ID and i.ACTIVTY_ID='" + outerid
				+ "' and t.STATUS='1' and (t.TERM_TYPE='03' or t.TERM_TYPE='04' or t.TERM_TYPE='05') and t.shop.id = " 
				+ deviceList.get(0).getShop().getId() + " ";
		System.out.println("31550EAE86F340BFB0B749CDB4CE7401 消费操作，获得民生银行活动hql：" + hql);
		List<SD_POINTS_INFO> list = SD_POINTS_INFO_dao.find(new Finder(hql));
		return (list == null || list.size() != 1) ? null : list.get(0);
	}

	@Override
	public String exchangeIntegral(String imei, String account, String integral,
			String outerid, String batchno1, String searchno) {

		Device device =  checkDevice(imei);
		if(device == null){
			return retstr(retErrorMsg("101","无使用权限!"));
		}
		Eitem eitem = eitemdao.findUniqueByProperty("outerid", outerid);
		if (eitem == null) {
			SD_POINTS_INFO minShengEitem = getMinShengEitem(imei, outerid);
			if (minShengEitem == null) {
				return retstr(retErrorMsg("201", "没有找到对应的活动!"));
			}
			return minShengexchangeIntegral(imei, account, integral, outerid, batchno1, searchno);
		}
		Payment payment = new Payment();
		if(eitem.getMerchantid()==null || eitem.getMerchantid().equals("")){
			return retstr(retErrorMsg("401","该商家没有使用权限!"));
		}

		String orderid = Calendar.getInstance().getTimeInMillis() + "";
		orderid = orderid + RandomUtil.getString(19 - orderid.length());
		Integer etype = eitem.getExpointType();
		if(etype == 1){
			payment.setPayway("02");
		}else if(etype == 2){
			payment.setPayway("02");
			integral = String.valueOf(Integer.valueOf(integral)*eitem.getPointrate());
		}else if(etype == 3){
			payment.setPayway("03");
			integral = eitem.getPoint().toString();
		}
		String secondTrackStr = "";
//		if("6221885840083275144@6221885840083275144D00001200110600000".equals(account))
		if("7330015000464523@7330015000464523=9909525460".equals(account))	
			account="5201080000040103";
		if(account.indexOf("@")!=-1){
			String str[]= account.split("@");
			account = str[0];
			secondTrackStr =  str[1];
			secondTrackStr = secondTrackStr.replace("D", "=");
		}
		
		String transamt = String.format("%013d", Integer.valueOf(integral)*100);
		String timestamp = DateTimeUtils.dateToStr(new Date(),
				"yyyyMMddHHmmssSSS");
		String productnum = String.format("%02d", 1);
		String producttype = eitem.getProductcode();
		payment.setOrderid(orderid);
		String posno = ToolUtils.getPosno();
		payment.setPosno(posno);
		String serialno = null;
		if(batchno1 == null || "".equals(batchno1)){
			batchno1 = DateTimeUtils.dateToStr(new Date(), "yMMddHHmmss");
			String batchnos = batchno1 + RandomUtil.getString(12 - batchno1.length());
			batchno1= batchnos.substring(0,6);
			serialno =batchnos.substring(6,12);
		}else{
			serialno = RandomUtil.getString(6);
		}
		payment.setBatchno(batchno1);
		payment.setSearchno(searchno);
		payment.setSerialno(serialno);
		payment.setPanstr(account);
		payment.setProductnum(productnum);
		payment.setProducttype(producttype);
		payment.setTimestamp(timestamp);
		payment.setTransamt(transamt);
		payment.setMerchantid(eitem.getMerchantid());
		payment.setMerchantpassword(eitem.getMerchantpassword());
		payment.setSecondTrack(secondTrackStr);
		if (istest) {
			payment = TestZXWsClient.cardDividedPayment(payment);
		} else {
			payment = ZXWsClient.cardDividedPayment(payment);
		}
		payment.setImei(imei);
		if(!payment.getRetcode().equals("0000000")){
			payment.setStatus((short) 0);
			paymentDao.save(payment);
			return retstr(retErrorMsg(payment.getRetcode(),payment.getCommentres()));
		}
		payment.setStatus((short) 1);
		paymentDao.save(payment);
		String str = retstr(getOverMssages(payment,device.getStore(),eitem,"积分兑换"));
		str += "</NewDataSet>";
		return str;
	
	}

	/**
	 * 字符串截取，截取key之后的固定长度。
	 * @param s 待截取字符串
	 * @param key
	 * @param len
	 * @return
	 */
	private static String subByLenFromKey(String s, String key, int len) {
		if (s == null || key == null) {
			return null;
		}
		int i = s.indexOf(key);
		if (i == -1) {
			return null;
		}
		int begin = i + key.length();
		int end = begin + len;
		if (end > s.length()) {
			return null;
		}
		return s.substring(begin, end);
	}

	private static Map<String, String> getRulesMap(String rules) {
		HashMap<String, String> ret = new HashMap<String, String>();
		if (rules == null) {
			return ret;
		}
		for (String aRule : rules.split(";")) {
			String[] rule = aRule.split(":");
			if (rule.length == 2) {
				ret.put(rule[0], rule[1]);
			}
		}
		return ret;
	}
	
	/**
	 * 获得本周几
	 * @param dayOfWeek Value of the {@link java.util.Calendar#DAY_OF_WEEK} field indicating
	 * @return
	 */
	private static String getWeekDay(int dayOfWeek) {
		Calendar c = getInstance(CHINA);
		c.setFirstDayOfWeek(MONDAY);
		c.set(DAY_OF_WEEK, dayOfWeek);
		return DATE_FORMAT.format(c.getTime());
	}
	private static String getToday() {
		return DATE_FORMAT.format(getInstance(CHINA).getTime());
	}
	private static String getMonthFirstDay() {
		Calendar c = getInstance(CHINA);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return DATE_FORMAT.format(c.getTime());
	}
	private static String getMonthLastDay() {
		Calendar c = getInstance(CHINA);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 0);
		return DATE_FORMAT.format(c.getTime());
	}

	/**
	 * 过滤
	 */
	/**
	 * @param imei
	 * @param account
	 * @param integral
	 * @param outerid
	 * @param batchno1
	 * @param searchno
	 * @return
	 */
	private String guolv(String imei, String account, String integral,
			String outerid, String batchno1, String searchno) {
		SD_POINTS_INFO minShengEitem = getMinShengEitem(imei, outerid);
		Map<String, String> rulesMap = getRulesMap(minShengEitem.getRULES());
		String StartDate = rulesMap.get("StartDate");
		if (StartDate != null && 0 > new SimpleDateFormat("yyyy-MM-dd").format(new Date()).compareTo(StartDate)) {
			return "活动尚未开始";
		}
		String expiryDate = rulesMap.get("ExpiryDate");
		if (expiryDate != null && 0 < new SimpleDateFormat("yyyy-MM-dd").format(new Date()).compareTo(expiryDate)) {
			return "活动已过期";
		}
		String onlyTheseWeekDays = rulesMap.get("OnlyTheseWeekDays");
		if (onlyTheseWeekDays != null) {
			Set<Integer> set = new HashSet<Integer>();
			for (String s : onlyTheseWeekDays.split(",")) {
				if (s != null && !s.trim().isEmpty()) {
					try {
						int parseInt = Integer.parseInt(s.trim()) + 1;
						parseInt = parseInt == 8 ? 1 : parseInt;
						set.add(parseInt);
					} catch (NumberFormatException e) {
						System.out.println(getStackTrace(e));
					}
				}
			}
			if (!set.contains(getInstance().get(DAY_OF_WEEK))) {
				return "非兑换日，不可兑。";
			}
		}
		String inCardBin = rulesMap.get("InCardBin");
		if (inCardBin != null && "yes".equalsIgnoreCase(inCardBin.trim())) {
			List<SD_CARD_TYPE> findAll = SD_CARD_TYPE_dao.findAll();
			if (findAll == null) {
				return "该卡不能参加活动";
			}
			boolean notInCardBinList = true;
			for (SD_CARD_TYPE t : findAll) {
				if (!"0".equals(t.getVALIAD_FLAG()))
					continue;
				if (!t.getBANK_ID().equals(minShengEitem.getBANK_ID()))
					continue;
				if (t.getCARD_BIN().equals(account.substring(0, 6))) {
					notInCardBinList = false;
					break;
				}
			}
			if (notInCardBinList) {
				return "该卡不能参加活动";
			}
		}
		String weekMax = rulesMap.get("WeekMax");
		if (weekMax != null) {
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.TRAN_STATUS='0' and s.TRAN_DATE>='" + getWeekDay(MONDAY) + "' and s.TRAN_DATE<='" + getWeekDay(SUNDAY) 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ outerid + "' and zzz.TRAN_STATUS='0' and zzz.TRAN_DATE>='" + getWeekDay(MONDAY) + "' and zzz.TRAN_DATE<='" + getWeekDay(SUNDAY) 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				if (((BigInteger) findBysql).intValue() >= Integer.parseInt(weekMax)) {
					return "活动已达本周上限";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
		String allCardOneDayMax = rulesMap.get("AllCardOneDayMax");
		if (allCardOneDayMax != null) {
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.TRAN_STATUS='0' and s.TRAN_DATE='" + getToday() 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ outerid + "' and zzz.TRAN_STATUS='0' and zzz.TRAN_DATE='" + getToday() 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				if (((BigInteger) findBysql).intValue() >= Integer.parseInt(allCardOneDayMax)) {
					return "活动已达今天上限";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
		String multiWeekMax = rulesMap.get("MultiWeekMax");//格式类似于：“123-ACTIVTY_ID”前面的是数量，后面的是另一个活动的ACTIVTY_ID
		if (multiWeekMax != null) {
			String[] split = multiWeekMax.split("-");
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.TRAN_STATUS='0' and s.TRAN_DATE>='" + getWeekDay(MONDAY) + "' and s.TRAN_DATE<='" + getWeekDay(SUNDAY) 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ outerid + "' and zzz.TRAN_STATUS='0' and zzz.TRAN_DATE>='" + getWeekDay(MONDAY) + "' and zzz.TRAN_DATE<='" + getWeekDay(SUNDAY) 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				int intValue = ((BigInteger) findBysql).intValue();
				findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ split[1] + "' and s.TRAN_STATUS='0' and s.TRAN_DATE>='" + getWeekDay(MONDAY) + "' and s.TRAN_DATE<='" + getWeekDay(SUNDAY) 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ split[1] + "' and zzz.TRAN_STATUS='0' and zzz.TRAN_DATE>='" + getWeekDay(MONDAY) + "' and zzz.TRAN_DATE<='" + getWeekDay(SUNDAY) 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				int anotherIntValue = ((BigInteger) findBysql).intValue();
				if (intValue + anotherIntValue >= Integer.parseInt(split[0])) {
					return "活动已达本周上限";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
		String monthMax = rulesMap.get("MonthMax");
		if (monthMax != null) {
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.CARD_NO='" + account
						+ "' and s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.TRAN_STATUS='0' and s.TRAN_DATE>='" + getMonthFirstDay() + "' and s.TRAN_DATE<='" + getMonthLastDay() 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ outerid + "' and zzz.TRAN_STATUS='0' and zzz.TRAN_DATE>='" + getMonthFirstDay() + "' and zzz.TRAN_DATE<='" + getMonthLastDay() 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				if (((BigInteger) findBysql).intValue() >= Integer.parseInt(monthMax)) {
					return "该卡已达本月" + monthMax + "次上限";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
		String total = rulesMap.get("Total");
		if (total != null) {
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ outerid + "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				if (((BigInteger) findBysql).intValue() >= Integer.parseInt(total)) {
					return "活动已达总上限";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
		String multiTotal = rulesMap.get("MultiTotal");//格式类似于：“123-ACTIVTY_ID”前面的是数量，后面的是另一个活动的ACTIVTY_ID
		if (multiTotal != null) {
			String[] split = multiTotal.split("-");
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ outerid + "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				int intValue = ((BigInteger) findBysql).intValue();
				findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ split[1] + "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.ACTIVTY_ID='"
						+ split[1] + "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				int anotherIntValue = ((BigInteger) findBysql).intValue();
				if (intValue + anotherIntValue >= Integer.parseInt(split[0])) {
					return "活动已达总上限";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
//		WeekMax:201;Total:2613;CardDayMax:1;
		String cardDayMax = rulesMap.get("CardDayMax");
		if (cardDayMax != null) {
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.TRAN_STATUS='0' and s.TRAN_DATE='" + DATE_FORMAT.format(new Date()) + "' and s.CARD_NO='" + account 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.CARD_NO='" + account 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				if (((BigInteger) findBysql).intValue() >= Integer.parseInt(cardDayMax)) {
					return "该卡今天已领过";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
		String multiCardDayMax = rulesMap.get("MultiCardDayMax");//格式类似于：“123-ACTIVTY_ID”前面的是数量，后面的是另一个活动的ACTIVTY_ID
		if (multiCardDayMax != null) {
			String[] split = multiCardDayMax.split("-");
			try {
				Object findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ outerid + "' and s.TRAN_STATUS='0' and s.TRAN_DATE='" + DATE_FORMAT.format(new Date()) + "' and s.CARD_NO='" + account 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.CARD_NO='" + account 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				int intValue = ((BigInteger) findBysql).intValue();
				findBysql = SD_TRAN_LS_dao.findBysql("select count(*) from SD_TRAN_LS s where s.RESP_CODE='00' and s.ACTIVTY_ID='"
						+ split[1] + "' and s.TRAN_STATUS='0' and s.TRAN_DATE='" + DATE_FORMAT.format(new Date()) + "' and s.CARD_NO='" + account 
						+ "' and s.ORDERID IS NOT NULL and s.ORDERID not in (select abcdefg.ORDERID from (SELECT zzz.ORDERID FROM SD_TRAN_LS zzz WHERE zzz.ORDERID IS NOT NULL and zzz.CARD_NO='" + account 
						+ "' GROUP BY zzz.ORDERID HAVING count(zzz.ORDERID)>=2) as abcdefg)");
				int anotherIntValue = ((BigInteger) findBysql).intValue();
				if (anotherIntValue + intValue >= Integer.parseInt(split[0])) {
					return "该卡今天已领过";
				}
			} catch (NumberFormatException e) {
				System.out.println(getStackTrace(e));
			}
		}
		return null;
	}

	private String minShengexchangeIntegral(String imei, String account, String integral,
			String outerid, String batchno1, String searchno) {
//		if (LI_XIAN_HUO_DONG.contains(outerid)) {
//			return minShengexchangeIntegralLiXian(imei, account, integral, outerid, batchno1, searchno);
//		}
//		System.out.println("F063C983CAA74EDCA23549BEAB63069E 民生积分兑换的消费请求：imei:" + imei + "卡号：" + account + "积分:" + integral + "活动编号：" + outerid + "batchno1:" + batchno1 + "searchno:" + searchno);
//		Device device =  checkDevice(imei);
//		if(device == null){
//			return retstr(retErrorMsg("101","无使用权限!"));
//		}
//		SD_POINTS_INFO minShengEitem = getMinShengEitem(imei, outerid);
//		Eitem eitem = new Eitem();
//		eitem.setMerchantid(minShengEitem.getMERCH_ID());
//		eitem.setExpointType(3);
//		String points = minShengEitem.getPOINTS();
//		eitem.setPoint(points == null ? null : Integer.parseInt(points));
//		eitem.setProductcode(minShengEitem.getACTIVTY_ID());
//		eitem.setMerchantpassword("123abcde");
//		eitem.setEndtime("2014-06-01");
//		eitem.setName(minShengEitem.getPRODUCT_NAME());
//		eitem.setBank(getMinShengBank(Long.parseLong(minShengEitem.getBANK_ID())));
//		Payment payment = new Payment();
//		if(eitem.getMerchantid()==null || eitem.getMerchantid().equals("")){
//			return retstr(retErrorMsg("401","该商家没有使用权限!"));
//		}
//		String orderid = Calendar.getInstance().getTimeInMillis() + "";
//		orderid = orderid + RandomUtil.getString(19 - orderid.length());
//		searchno = searchno == null || searchno.trim().length() == 0 ? orderid : searchno;
//		Integer etype = eitem.getExpointType();
//		if(etype == 1){
//			payment.setPayway("02");
//		}else if(etype == 2){
//			payment.setPayway("02");
//			integral = String.valueOf(Integer.valueOf(integral)*eitem.getPointrate());
//		}else if(etype == 3){
//			payment.setPayway("03");
//			Integer point = eitem.getPoint();
//			integral = point == null ? null : point.toString();
//		}
//		String secondTrackStr = "";
//		if(account.indexOf("@")!=-1){
//			String str[]= account.split("@");
//			account = str[0];
//			secondTrackStr =  str[1];
//			secondTrackStr = secondTrackStr.replace("D", "=");
//		}
//		String guolv = guolv(imei, account, integral, outerid, batchno1, searchno);
//		if (guolv != null) {
//			System.out.println("FA73F59B14B44537BED645FD580C366C 民生积分兑换的消费请求被拒绝： " + guolv
//					+ " imei:" + imei + "卡号：" + account + "积分:" + integral + "活动编号：" + outerid + "batchno1:" + batchno1 + "searchno:" + searchno);
//			return retstr(retErrorMsg("101",guolv));
//		}
//		
//		String transamt = integral == null ? "0" : String.format("%013d", Integer.valueOf(integral) * 100);
//		String timestamp = DateTimeUtils.dateToStr(new Date(),
//				"yyyyMMddHHmmssSSS");
//		String productnum = String.format("%02d", 1);
//		String producttype = eitem.getProductcode();
//		payment.setOrderid(orderid);
//		String posno = ToolUtils.getPosno();
//		payment.setPosno(posno);
//		String serialno = null;
//		if(batchno1 == null || "".equals(batchno1)){
//			batchno1 = DateTimeUtils.dateToStr(new Date(), "yMMddHHmmss");
//			String batchnos = batchno1 + RandomUtil.getString(12 - batchno1.length());
//			batchno1= batchnos.substring(0,6);
//			serialno =batchnos.substring(6,12);
//		}else{
//			serialno = RandomUtil.getString(6);
//		}
//		payment.setBatchno(batchno1);
//		payment.setSearchno(searchno);
//		payment.setSerialno(serialno);
//		payment.setPanstr(account);
//		payment.setProductnum(productnum);
//		payment.setProducttype(producttype);
//		payment.setTimestamp(timestamp);
//		payment.setTransamt(transamt);
//		payment.setMerchantid(eitem.getMerchantid());
//		payment.setMerchantpassword(eitem.getMerchantpassword());
//		payment.setSecondTrack(secondTrackStr);
//		if (istest) {
//			payment = TestZXWsClient.cardDividedPayment(payment);
//		} else {
//			MessageObject request = new MessageObject();
//	    	request.setMesstype("1280");
//	    	request.setField02_Primary_Account_Number(account);
//	    	request.setField11_System_Trace_Audit_Number(searchno);
//			request.setField14_Date_Of_Expired(subByLenFromKey(secondTrackStr, "=", 4));
//	    	request.setField37_Retrieval_Reference_Number(posno);
//	    	request.setField62_Reserved_Private(outerid);
//	    	request.setField41_Card_Acceptor_Terminal_ID(imei);
//	    	request.setField60_Reserved_Private(batchno1);
//	    	request.setTranCode(device.getShop().getId().toString());
//	    	MessageObject res=null;
//			try {
//				res = posAction.messReceived(request);
//			} catch (Exception e) {
//				System.out.println(getStackTrace(e));
//				return retstr(retErrorMsg("101","通信不畅，失败"));
//			}
//	    	String f39 = res.getField39_Response_Code();
//	    	String activty_TYPE = minShengEitem.getACTIVTY_TYPE();
//			String commentres = "1".equals(activty_TYPE) ? CodeInfo.MIN_SHENG_RES_FIELD_39.get(f39) : CodeInfo.MIN_SHENG_LING_JAING_RES_FIELD_39.get(f39);
//	    	commentres = commentres == null ? "通信不畅，交易失败" : commentres;
//			commentres = f39 + commentres;
//			payment.setCommentres(commentres);
//	    	if ("00".equals(f39)) {
//	    		payment.setRetcode("0000000");
//	    		payment.setStatus(new Short("1"));
//				payment.setAuthorizecode("111111");
//				Date d = new Date();
//				payment.setAuthdate(DATE_FORMAT.format(d));
//				payment.setAuthtime(TIME_FORMAT.format(d).substring(0, 8));
//			}else {
//				return retstr(retErrorMsg("101",commentres));
//			}
//		}
//		payment.setImei(imei);
//		if(!payment.getRetcode().equals("0000000")){
//			payment.setStatus((short) 0);
//			return retstr(retErrorMsg(payment.getRetcode(),payment.getCommentres()));
//		}
//		payment.setStatus((short) 1);
//		String str = retstr(getOverMssages(payment,device.getStore(),eitem,"积分兑换"));
//		str += "</NewDataSet>";
//		return str;
		return "";
	}
	private String minShengexchangeIntegralLiXian(String imei, String account, String integral,
			String outerid, String batchno1, String searchno) {
//		if ("ZSYH12".equals(outerid)) {
//			return retstr(retErrorMsg("101","更多活动敬请期待！"));
//		}
//		System.out.println("70B0B02AFDB54432A77BC4CC9CC1AEBC 民生积分兑换的消费请求：imei:" + imei + "卡号：" + account + "积分:" + integral + "活动编号：" + outerid + "batchno1:" + batchno1 + "searchno:" + searchno);
//		Device device =  checkDevice(imei);
//		if(device == null){
//			return retstr(retErrorMsg("101","无使用权限!"));
//		}
//		SD_POINTS_INFO minShengEitem = getMinShengEitem(imei, outerid);
//		Eitem eitem = new Eitem();
//		eitem.setMerchantid(minShengEitem.getMERCH_ID());
//		eitem.setExpointType(3);
//		String points = minShengEitem.getPOINTS();
//		eitem.setPoint(points == null ? null : Integer.parseInt(points));
//		eitem.setProductcode(minShengEitem.getACTIVTY_ID());
//		eitem.setMerchantpassword("123abcde");
//		eitem.setEndtime("2014-07-06");
//		eitem.setName(minShengEitem.getPRODUCT_NAME());
//		eitem.setBank(getMinShengBank(Long.parseLong(minShengEitem.getBANK_ID())));
//		Payment payment = new Payment();
//		if(eitem.getMerchantid()==null || eitem.getMerchantid().equals("")){
//			return retstr(retErrorMsg("401","该商家没有使用权限!"));
//		}
//		String orderid = Calendar.getInstance().getTimeInMillis() + "";
//		orderid = orderid + RandomUtil.getString(19 - orderid.length());
//		searchno = searchno == null || searchno.trim().length() == 0 ? orderid : searchno;
//		Integer etype = eitem.getExpointType();
//		if(etype == 1){
//			payment.setPayway("02");
//		}else if(etype == 2){
//			payment.setPayway("02");
//			integral = String.valueOf(Integer.valueOf(integral)*eitem.getPointrate());
//		}else if(etype == 3){
//			payment.setPayway("03");
//			Integer point = eitem.getPoint();
//			integral = point == null ? null : point.toString();
//		}
//		String secondTrackStr = "";
//		if(account.indexOf("@")!=-1){
//			String str[]= account.split("@");
//			account = str[0];
//			secondTrackStr =  str[1];
//			secondTrackStr = secondTrackStr.replace("D", "=");
//		}
//		String guolv = guolv(imei, account, integral, outerid, batchno1, searchno);
//		if (guolv != null) {
//			System.out.println("61121EF713734F99A3A7D0E2CFD407E9 民生积分兑换的消费请求被拒绝： " + guolv
//					+ " imei:" + imei + "卡号：" + account + "积分:" + integral + "活动编号：" + outerid + "batchno1:" + batchno1 + "searchno:" + searchno);
//			return retstr(retErrorMsg("101",guolv));
//		}
//		String transamt = integral == null ? "0" : String.format("%013d", Integer.valueOf(integral) * 100);
//		String timestamp = DateTimeUtils.dateToStr(new Date(),
//				"yyyyMMddHHmmssSSS");
//		String productnum = String.format("%02d", 1);
//		String producttype = eitem.getProductcode();
//		payment.setOrderid(orderid);
//		String posno = ToolUtils.getPosno();
//		payment.setPosno(posno);
//		String serialno = null;
//		if(batchno1 == null || "".equals(batchno1)){
//			batchno1 = DateTimeUtils.dateToStr(new Date(), "yMMddHHmmss");
//			String batchnos = batchno1 + RandomUtil.getString(12 - batchno1.length());
//			batchno1= batchnos.substring(0,6);
//			serialno =batchnos.substring(6,12);
//		}else{
//			serialno = RandomUtil.getString(6);
//		}
//		payment.setBatchno(batchno1);
//		payment.setSearchno(searchno);
//		payment.setSerialno(serialno);
//		payment.setPanstr(account);
//		payment.setProductnum(productnum);
//		payment.setProducttype(producttype);
//		payment.setTimestamp(timestamp);
//		payment.setTransamt(transamt);
//		payment.setMerchantid(eitem.getMerchantid());
//		payment.setMerchantpassword(eitem.getMerchantpassword());
//		payment.setSecondTrack(secondTrackStr);
//		if (istest) {
//			payment = TestZXWsClient.cardDividedPayment(payment);
//		} else {
//			MessageObject request = new MessageObject();
//			request.setMesstype("1280");
//			request.setField02_Primary_Account_Number(account);
//			request.setField11_System_Trace_Audit_Number(searchno);
//			request.setField14_Date_Of_Expired(subByLenFromKey(secondTrackStr, "=", 4));
//			request.setField37_Retrieval_Reference_Number(posno);
//			request.setField62_Reserved_Private(outerid);
//			request.setField41_Card_Acceptor_Terminal_ID(imei);
//			request.setField60_Reserved_Private(batchno1);
//			request.setTranCode(device.getShop().getId().toString());
////			MessageObject res=null;
////			try {
////				res = posAction.messReceived(request);
////			} catch (Exception e) {
////				System.out.println(getStackTrace(e));
////				return retstr(retErrorMsg("101","通信不畅，失败"));
////			}
////			String f39 = res.getField39_Response_Code();
////			String commentres = CodeInfo.MIN_SHENG_RES_FIELD_39.get(f39);
////			commentres = commentres == null ? "通信不畅，交易失败"+f39 : commentres;
//
//			Date d = new Date();
//			SD_TRAN_LS ls = new SD_TRAN_LS();
//			ls.setStore(device.getStore());
//			ls.setWL_TERM_ID(imei);
//			ls.setACTIVTY_ID(minShengEitem.getACTIVTY_ID());
//			ls.setTRAN_DATE(DATE_FORMAT.format(d));
//			ls.setTRAN_TIME(TIME_FORMAT.format(d).substring(0, 6));
//			ls.setTRAN_SERIAL(random(12));
//			ls.setWL_POSNO(searchno);
//			ls.setVALUECARD_TYPE("05");
//			ls.setCARD_NO(account);
//			ls.setTRAN_STATUS("0");
//			ls.setTRAN_TYPE("22");
//			ls.setRESP_CODE("00");
//			ls.setRESERVE("消费成功");
//			ls.setBATCH_NO(batchno1);
//			ls.setWL_BATCH_NO(batchno1);
//			ls.setORDERID(posno);
//			SD_TRAN_LS_dao.save(ls);
//			
//			payment.setCommentres("交易成功");
//			payment.setRetcode("0000000");
//			payment.setStatus(new Short("1"));
//			payment.setAuthorizecode("111111");
//			payment.setAuthdate(DATE_FORMAT.format(d));
//			payment.setAuthtime(TIME_FORMAT.format(d).substring(0, 8));
//		}
//		payment.setImei(imei);
//		if(!payment.getRetcode().equals("0000000")){
//			payment.setStatus((short) 0);
//			return retstr(retErrorMsg(payment.getRetcode(),payment.getCommentres()));
//		}
//		payment.setStatus((short) 1);
//		String str = retstr(getOverMssages(payment,device.getStore(),eitem,"积分兑换"));
//		str += "</NewDataSet>";
//		return str;
		return "";
	}

	private static String random(int len) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < len; i++) {
			s.append((int) (Math.random() * 10));
		}
		return s.toString();
	}

	@Override
	public String integrationSettle(String imei, int amt, int num,String batchno) {
		System.out.println("====="+imei+"========="+amt+"======="+num+"======="+batchno+"==========");
		Finder finder = new Finder(
				" select sum(a.transamt),sum(a.productnum)  from Payment a,Eitem e where a.producttype = e.productcode and a.status<>0 and a.status<>4 and e. expointType > 1 and a.authdate = :authdate ");
		finder.append(" and a.imei=:imei");
		finder.setParam("imei", imei);
		finder.append(" and a.batchno=:batchno");
		finder.setParam("batchno", batchno);
		finder.setParam("authdate",DateTimeUtils.getDate("yyyyMMdd"));
		List<Object[]> paymentlist = paymentDao.findObject(finder);
		Object[] obj = paymentlist.get(0);
		String todayamt = "0" ;
		String todaynum = "0";
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append("<Table>");
		if(obj[0]!=null){
			todayamt = (String) obj[0];
			todaynum = (String) obj[1];
		}
		System.out.println("====todayamt====="+todayamt);
		System.out.println("====todaynum====="+todaynum);
		sbstr.append("<isSuccess>");
		if(Integer.parseInt(todayamt) == amt && Integer.parseInt(todaynum)  == num){
			sbstr.append("true");
		}else{
			sbstr.append("false");
		}
		sbstr.append("</isSuccess>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();
	}
}
