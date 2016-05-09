package cn.m.mt.barcodeservice.service.impl;

import static java.util.regex.Pattern.compile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.alipay.api.ApiException;
import cn.emay.alipay.api.domain.Order;
import cn.emay.alipay.api.request.CloseTradeRequest;
import cn.emay.alipay.api.request.PaymentRequest;
import cn.emay.alipay.api.request.QueryRequest;
import cn.emay.alipay.api.request.RefundRequest;
import cn.emay.alipay.api.response.CloseTradeResponse;
import cn.emay.alipay.api.response.PaymentResponse;
import cn.emay.alipay.api.response.QueryResponse;
import cn.emay.alipay.api.response.RefundResponse;
import cn.m.common.hibernate3.Condition;
import cn.m.common.hibernate3.Finder;
import cn.m.common.hibernate3.restrictions.OrderBy;
import cn.m.mt.barcodeservice.bo.ChecklogBo;
import cn.m.mt.barcodeservice.service.BarcodeService;
import cn.m.mt.barcodeservice.service.CallbackThread;
import cn.m.mt.barcodeservice.util.ToolUtils;
import cn.m.mt.barcodeservice.util.Variable;
import cn.m.mt.barcodeservice.yht.YhtWSClient;
import cn.m.mt.barcodeservice.zx.TestZXWsClient;
import cn.m.mt.barcodeservice.zx.ZXWsClient;
import cn.m.mt.charge.service.ChargeService;
import cn.m.mt.dao.AccountDao;
import cn.m.mt.dao.AorderDao;
import cn.m.mt.dao.BarcodeDao;
import cn.m.mt.dao.CebbcodelibraryDao;
import cn.m.mt.dao.ChargeDao;
import cn.m.mt.dao.ChecklogDao;
import cn.m.mt.dao.CodelibraryDao;
import cn.m.mt.dao.DeviceDao;
import cn.m.mt.dao.DevicemodifyDao;
import cn.m.mt.dao.DeviceofupdateDao;
import cn.m.mt.dao.DeviceversionDao;
import cn.m.mt.dao.EitemAdvStoreDao;
import cn.m.mt.dao.EitemDao;
import cn.m.mt.dao.EitemstoreDao;
import cn.m.mt.dao.EnterpriseDao;
import cn.m.mt.dao.EorderDao;
import cn.m.mt.dao.FourcodelibraryDao;
import cn.m.mt.dao.JiZhangDao;
import cn.m.mt.dao.PaymentDao;
import cn.m.mt.dao.PaymentcodelibraryDao;
import cn.m.mt.dao.StoreDao;
import cn.m.mt.dao.UserDao;
import cn.m.mt.jmsclient.MessageSender;
import cn.m.mt.po.Account;
import cn.m.mt.po.Aflowlog;
import cn.m.mt.po.Aorder;
import cn.m.mt.po.Barcode;
import cn.m.mt.po.Cebbcodelibrary;
import cn.m.mt.po.Charge;
import cn.m.mt.po.Checklog;
import cn.m.mt.po.Codelibrary;
import cn.m.mt.po.Device;
import cn.m.mt.po.Devicemodify;
import cn.m.mt.po.Deviceofupdate;
import cn.m.mt.po.Deviceversion;
import cn.m.mt.po.Eitem;
import cn.m.mt.po.EitemAdvStore;
import cn.m.mt.po.Eitemstore;
import cn.m.mt.po.Enterprise;
import cn.m.mt.po.Eorder;
import cn.m.mt.po.Fourcodelibrary;
import cn.m.mt.po.JiZhang;
import cn.m.mt.po.Overorderlog;
import cn.m.mt.po.Payment;
import cn.m.mt.po.Paymentcodelibrary;
import cn.m.mt.po.Shop;
import cn.m.mt.po.Store;
import cn.m.mt.po.User;
import cn.m.mt.util.DateTimeUtils;
import cn.m.mt.util.MD5Helper;
import cn.m.mt.util.MustlogException;
import cn.m.mt.util.RandomUtil;
import cn.m.mt.util.VerifyErrorException;
import cn.m.mt.util.WarnException;

@Service
@Transactional
public class BarcodeServiceImpl implements BarcodeService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private BarcodeDao barcodeDao;
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private ChecklogDao checklogDao;
	@Autowired
	private CodelibraryDao codelibraryDao;
	@Autowired
	private CebbcodelibraryDao cebbcodelibraryDao;
	@Autowired
	private FourcodelibraryDao fourcodelibraryDao;
	@Autowired
	private PaymentcodelibraryDao paymentcodelibraryDao;
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private EorderDao eorderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AorderDao aorderDao;
	@Autowired
	private DevicemodifyDao devicemodifyDao;
	@Autowired
	private DeviceofupdateDao deviceofupdateDao;
	@Autowired
	private DeviceversionDao deviceversionDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private EitemstoreDao eitemstoreDao;
	@Autowired
	private EitemDao eitemDao;
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private ChargeDao chargedao;
	@Autowired
	private MessageSender messageSender;
	@Autowired
	private EnterpriseDao enterpriseDao;
	// @Autowired
	// private ReconciliationDao reconciliationDao;
	@Autowired
	private EitemAdvStoreDao ditemAdvStoreDao;
	@Autowired
	private JiZhangDao jiZhangDao;
	
	private boolean istest = false;
	private String yhturl = Variable.YHTURL;
	private String resserverurl = Variable.RESSERVERURL;

	private static String itemuiid = "2530ce14-c786-421e-8cf9-246343027cb1";// 固定的淘宝商品活动ID
	private static String devicebindtest = "3a60cb78-f342-44f6-91c9-46fb05c1e832";// 固定的测试设备绑定couponuiid
	private static String testcode = "00";
	private static int tempint = 0;
	private static byte[] lock = new byte[0];
	private static byte[] lock1 = new byte[0];
	private static byte[] lock2 = new byte[0];
	private static byte[] lock3 = new byte[0];

	private String testDeviceBind(String imei, String numcode) {
		String storename = "";
		String shopname = "";
		Long shopid = 0L;
		Long storeid = 0L;
		String schql = " from Device where  devcode=:imei";
		Finder devfinder = new Finder(schql);
		devfinder.setParam("imei", imei);
		List<Device> sclist = deviceDao.find(devfinder);
		String CurrentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());// 当前时间
		StringBuffer sbstr = new StringBuffer();
		String yht = null;
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append("<Table>");
		sbstr.append("<IsNeedConfirm>true</IsNeedConfirm>");
		sbstr.append("<ConfirmNumber></ConfirmNumber>");
		sbstr.append("<PwdInfoId></PwdInfoId>");
		sbstr.append("<MmsId>" + itemuiid + "</MmsId>");
		sbstr.append("<MastMmsId>" + itemuiid + "</MastMmsId>");
		sbstr.append("<MmsName>设备绑定测试</MmsName>");
		sbstr.append("<Description>设备绑定测试</Description>");
		sbstr.append("<MmsTitle></MmsTitle>");
		sbstr.append("<ValidFrom>2000-01-01</ValidFrom>");
		sbstr.append("<ValidTo>2020-12-31</ValidTo>");
		sbstr.append("<ResendContent></ResendContent>");
		sbstr.append("<ValidTimes>99999999</ValidTimes>");
		if (sclist != null && sclist.size() > 0) {
			shopname = sclist.get(0).getShop().getName();
			storename = sclist.get(0).getStore().getName();
			shopid = sclist.get(0).getShop().getId();
			storeid = sclist.get(0).getStore().getId();
		} else {
			String[] params = new String[1];
			params[0] = "IMEI";
			yht = YhtWSClient.getYhtContent("TestDeviceByIMEI", params,
					new Object[] { imei }, yhturl);
			if (null != yht && !"".equals(yht)) {
				sbstr.delete(0, sbstr.length());
				sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
				sbstr.append("<NewDataSet>");
				sbstr.append(yht);
				sbstr.append("</NewDataSet>");
				return sbstr.toString();
			}
			storename = "设备尚未初始化";
		}
		sbstr.append("<ShopName>" + storename + "</ShopName>");
		sbstr.append("<ShopFamilyName>" + shopname + "</ShopFamilyName>");
		sbstr.append("<MmsFamilyName>" + shopname + "</MmsFamilyName>");
		sbstr.append("<Advertising></Advertising>");
		sbstr.append("<PrintNo>0000000000000000000</PrintNo>");
		sbstr.append("<LastVerifyNumber>999999999</LastVerifyNumber>");
		sbstr.append("<verifyPwd>" + testcode + "</verifyPwd>");
		sbstr.append("<CreatedOn>" + CurrentDateTime + "</CreatedOn>");
		sbstr.append("<CurrentDateTime>" + CurrentDateTime
				+ "</CurrentDateTime>");
		sbstr.append("<ShopId>" + devicebindtest + "</ShopId>");

		sbstr.append("<Entname>设备绑定测试</Entname>");
		sbstr.append("<Printstr></Printstr>");
		sbstr.append("<PrintTitle>汇金通消费单");
		sbstr.append("</PrintTitle>");
		sbstr.append("<ShopNo>000000000000");
		sbstr.append("</ShopNo>");
		sbstr.append("<TranType>权益兑换");
		sbstr.append("</TranType>");
		sbstr.append("<BacthNo>000000000");
		sbstr.append("</BacthNo>");
		sbstr.append("<AdminNo>001");
		sbstr.append("</AdminNo>");

		sbstr.append("<PrintInfo>");
		sbstr.append("\n\n");
		sbstr.append("汇金通消费单");
		sbstr.append("\n\n");
		sbstr.append("商户名称:");
		sbstr.append(shopname);
		sbstr.append("\n商户号:");
		String batchno = String.format("%012d",
				Long.valueOf(shopid + "" + storeid));
		sbstr.append(batchno);
		sbstr.append("\n门店名称:");
		sbstr.append(storename);
		sbstr.append("\n活动发起方:");
		sbstr.append(shopname);// entname
		sbstr.append("\n终端号:");
		sbstr.append(imei);
		sbstr.append("\n活动名称:设备绑定测试");
		sbstr.append("\n活动描述:设备绑定测试");
		sbstr.append("\n有效期:2020-12-31");
		sbstr.append("\n本次使用数:$usernum");
		sbstr.append("\n交易类型:权益兑换");
		sbstr.append("\n批次号:0000000000000000000");// tid
		sbstr.append("\n订单号:0000000000000000000");
		sbstr.append("\n日期时间:");
		sbstr.append(CurrentDateTime);
		sbstr.append("\n操作员:001");
		sbstr.append("</PrintInfo>");
		sbstr.append("<Canusenum>1</Canusenum>");
		sbstr.append("</Table>");

		String[] params = new String[2];
		params[0] = "IMEI";
		params[1] = "verifyPwd";
		String testyht = YhtWSClient.getYhtContentToString("TestDeviceByIMEI",
				params, new Object[] { imei, numcode }, yhturl);
		if (testyht != null && !"".equals(testyht))
			sbstr.append(testyht);
		sbstr.append("</NewDataSet>");
		return sbstr.toString();
	}

	public String getMMSByIMEI(String imei, String verifyPwd) {
		if (testcode.equals(verifyPwd)) {
			String testDeviceBind = testDeviceBind(imei, verifyPwd);
			return testDeviceBind;
		}
		StringBuffer returnValidata = new StringBuffer();
		returnValidata.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnValidata.append("<NewDataSet>");
		Object obj = checkImeiAndCode(imei, verifyPwd);
		if (obj instanceof Integer) {
			int result = (Integer) obj;
			log.info("==getMMSByIMEI code==" + verifyPwd
					+ "======in integer===" + result + "==imei==" + imei);
			if (result == 1 || result == 2) {
				String[] params = new String[2];
				params[0] = "IMEI";
				params[1] = "verifyPwd";
				String yht = YhtWSClient.getYhtContentToString("GetMMSByIMEI",
						params, new Object[] { imei, verifyPwd }, yhturl);
				if (null == yht) {
					returnValidata
							.append("<Table><ErrorMessage>交易失败,不是有效的码</ErrorMessage></Table>");
				} else
					returnValidata.append(yht);

			} else if (result == 3) {
				returnValidata.append("<Table>");
				returnValidata
						.append("<ErrorMessage>交易失败,该码不能在此门店使用</ErrorMessage>");
				returnValidata.append("</Table>");
			} else if (result == 4) {
				returnValidata.append("<Table>");
				returnValidata
						.append("<ErrorMessage>交易失败,不是有效的码</ErrorMessage>");
				returnValidata.append("</Table>");
			} else if (result == 5) {
				returnValidata.append("<Table>");
				returnValidata
						.append("<ErrorMessage>交易失败,该码已经过期</ErrorMessage>");
				returnValidata.append("</Table>");
			} else if (result == 6) {
				returnValidata.append("<Table>");
				returnValidata
						.append("<ErrorMessage>交易失败,该码已经作废</ErrorMessage>");
				returnValidata.append("</Table>");
			} else if (result == 7) {
				returnValidata.append("<Table>");
				returnValidata
						.append("<ErrorMessage>交易失败,没有找到对应的活动</ErrorMessage>");
				returnValidata.append("</Table>");
			}
		} else {
			log.info("==getMMSByIMEI code==" + verifyPwd
					+ "==in object==imei =" + imei);
			Barcode barcode = null;
			Store store = null;
			List<Eitem> eitemlist = null;
			if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				barcode = (Barcode) objs[0];
				store = (Store) objs[1];
				eitemlist = (List<Eitem>) objs[2];
			}
			if (verifyPwd.length() != 14 && barcode == null) {
				returnValidata.append("<Table>");
				returnValidata
						.append("<ErrorMessage>交易失败,不是有效的码</ErrorMessage>");
				returnValidata.append("</Table>");
			}
			returnValidata.append(returnStr(store, barcode, imei, eitemlist,
					verifyPwd));
		}
		returnValidata.append("</NewDataSet>");

		return returnValidata.toString();
	}

	private String returnStr(Store store, Barcode barcode, String imei,
			List<Eitem> eitemlist, String verifyPwd) {
		// 截取设备ID的后4位
		String subImei = imei.substring(imei.length() - 4, imei.length());
		StringBuffer str = new StringBuffer();
		String printNo = "";// 打印流水号
		String MmsId = "";// 活动ID
		String MastMmsId = "";// 主活动ID
		String MmsName = "";// 活动名称
		// *******************************************************************
		// 开始生成打印号printNo
		// *******************************************************************
		String currDate = new SimpleDateFormat("yyMMdd").format(new Date());// 生成日期格式为：年年月月日日、如：110608(11年06月08日)
		DecimalFormat df = new DecimalFormat("0000");
		Random rd = new Random();
		String increase = df.format(rd.nextInt(9999));// 4位自增长数
		// printNo生成规则：设备ID的后4位+日期6位+4位增长数
		printNo = subImei + "" + currDate + "" + increase;
		// 结束生成打印号printNo
		// *******************************************************************
		String Description = "";// 活动描述
		String ValidTo = "";// 活动结束日期
		long ValidTimes = 1;
		int LastVerifyNumber = 1;
		String CreatedOn = "";// 创建时间
		if (barcode != null) {
			CreatedOn = barcode.getCreattime();
			ValidTimes = barcode.getCanusenum();
			LastVerifyNumber = barcode.getCanusenum().intValue();// 剩余次数
			verifyPwd = barcode.getNumcode();
		}
		String ShopName = "";// 商户名称
		String ShopFamilyName = "";// 商家名称
		String MmsFamilyName = "";// 活动发起方
		String CurrentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());// 当前时间
		String ShopId = "";// 门店ID
		for (Eitem eitem : eitemlist) {
			MmsId = eitem.getId().toString();
			MastMmsId = MmsId;
			MmsName = eitem.getName();
			if (MmsName != null) {
				int length = MmsName.length();
				MmsName = MmsName.substring(0, length > 13 ? 13 : length);
			}else{
				MmsName = "";
			}
			Description = "";
			if (null != eitem.getSmscontent()
					&& !"".equals(eitem.getSmscontent())) {
				Description = eitem.getSmscontent();
			} else if (null != eitem.getCheckinfo()
					&& !"".equals(eitem.getCheckinfo())) {
				Description = eitem.getCheckinfo();
			} else if (null != eitem.getPrintstr()
					&& !"".equals(eitem.getPrintstr())) {
				Description = eitem.getPrintstr();
			} else {
				Description = eitem.getName();
			}
			ShopName = store.getName();
			ShopFamilyName = store.getShop().getName();
			MmsFamilyName = ShopFamilyName;
			if (null != barcode) {
				Eorder eorder = barcode.getEorder();
				if (eorder.getBuynick() != null) {
					Description = Description.replace("$name",
							eorder.getBuynick());
				} else {
					Description = Description.replace("$name", "");
				}
				Description = Description.replace("$pwd", "二维码");
				if (eorder.getExtfield1() != null) {
					Description = Description.replace("$f1",
							eorder.getExtfield1());
				} else {
					Description = Description.replace("$f1", "");
				}

				if (eorder.getExtfield2() != null) {
					Description = Description.replace("$f2",
							eorder.getExtfield2());
				} else {
					Description = Description.replace("$f2", "");
				}
				if (eorder.getExtfield3() != null) {
					Description = Description.replace("$f3",
							eorder.getExtfield3());
				} else {
					Description = Description.replace("$f3", "");
				}
				ValidTo = eitem.getEndtime();
				if (ValidTo != null && ValidTo.length() > 10)
					ValidTo = ValidTo.substring(0, 10);
				if (eitem.getPrintstr() != null
						&& !"".equals(eitem.getPrintstr())) {

					String printstr = eitem.getPrintstr();
					if (null != eorder.getBuynick()
							&& !"".equals(eorder.getBuynick()))
						printstr = printstr.replace("$name",
								eorder.getBuynick());
					if (null != barcode.getNumcode()
							&& "".equals(barcode.getNumcode()))
						printstr = printstr.replace("$pwd",
								barcode.getNumcode());
					if (null != eorder.getExtfield1()
							&& !"".equals(eorder.getExtfield1()))
						printstr = printstr.replace("$f1",
								eorder.getExtfield1());
					if (null != eorder.getExtfield2()
							&& !"".equals(eorder.getExtfield2()))
						printstr = printstr.replace("$f2",
								eorder.getExtfield2());
					if (null != eorder.getExtfield3()
							&& !"".equals(eorder.getExtfield3()))
						printstr = printstr.replace("$f3",
								eorder.getExtfield3());
				}
			} else {
				Description = Description.replace("$name", "");
				Description = Description.replace("$pwd", "二维码");
				Description = Description.replace("$f1", "");
				ValidTo = eitem.getEndtime();
				if (ValidTo != null && ValidTo.length() > 10)
					ValidTo = ValidTo.substring(0, 10);
			}

			String ValidFrom = barcode.getCreattime();
			if (ValidFrom.length() > 10)
				ValidFrom = ValidFrom.substring(0, 10);
			String entname = eitem.getEnterprise().getEntname();
			String printstr = "";

			if (eitem.getPrintstr() != null && !"".equals(eitem.getPrintstr())) {
				printstr = eitem.getPrintstr();
				printstr = printstr.replace("$pwd", verifyPwd);
				MmsFamilyName = MmsFamilyName + "\n" + printstr;
			}

			ShopId = store.getShop().getUuid();
			str.append("<Table>");
			str.append("<IsNeedConfirm>false</IsNeedConfirm>");
			str.append("<ConfirmNumber></ConfirmNumber>");
			str.append("<PwdInfoId></PwdInfoId>");
			str.append("<MmsId>" + MmsId + "</MmsId>");
			str.append("<MastMmsId>" + MastMmsId + "</MastMmsId>");
			str.append("<MmsName>" + MmsName + "</MmsName>");
			str.append("<Description>" + Description + "</Description>");
			str.append("<MmsTitle></MmsTitle>");
			str.append("<ValidFrom>" + ValidFrom + "</ValidFrom>");
			str.append("<ValidTo>" + ValidTo + "</ValidTo>");
			str.append("<ResendContent></ResendContent>");
			str.append("<ValidTimes>" + ValidTimes + "</ValidTimes>");
			str.append("<ShopName>" + ShopName + "</ShopName>");
			str.append("<ShopFamilyName>" + ShopFamilyName
					+ "</ShopFamilyName>");
			str.append("<MmsFamilyName>" + MmsFamilyName + "</MmsFamilyName>");
			str.append("<Advertising></Advertising>");
			str.append("<PrintNo>" + printNo + "</PrintNo>");
			str.append("<LastVerifyNumber>" + LastVerifyNumber
					+ "</LastVerifyNumber>");
			str.append("<verifyPwd>" + verifyPwd + "</verifyPwd>");
			str.append("<CreatedOn>" + CreatedOn + "</CreatedOn>");
			str.append("<CurrentDateTime>" + CurrentDateTime
					+ "</CurrentDateTime>");
			str.append("<ShopId>" + ShopId + "</ShopId>");
			str.append("<Entname>" + entname + "</Entname>");
			str.append("<Printstr>" + printstr + "</Printstr>");
			str.append("<PrintTitle>");
			if (eitem.getPrintTitle() != null
					|| !"".equals(eitem.getPrintTitle()))
				str.append("汇金通消费单");
			else
				str.append(eitem.getPrintTitle());
			str.append("</PrintTitle>");

			str.append("<ShopNo>");
			String batchno = String.format("%012d",
					Long.valueOf(store.getShop().getId() + "" + store.getId()));
			str.append(batchno);
			str.append("</ShopNo>");
			str.append("<TranType>权益兑换");
			str.append("</TranType>");
			str.append("<BacthNo>");
			if (barcode != null) {
				str.append(String.format("%09d", barcode.getId()));
			} else {
				str.append(String.format("%09d", eitem.getId()));
			}
			str.append("</BacthNo>");
			str.append("<AdminNo>001");
			str.append("</AdminNo>");
			str.append("<PrintInfo>");
			str.append("\n\n");
			if (eitem.getPrintTitle() != null
					|| !"".equals(eitem.getPrintTitle()))
				str.append("汇金通消费单");
			else
				str.append(eitem.getPrintTitle());
			str.append("\n\n");
			str.append("商户名称:");
			str.append(ShopFamilyName);
			str.append("\n商户号:");
			str.append(batchno);
			str.append("\n门店名称:");
			str.append(ShopName);
			str.append("\n活动发起方:");
			str.append(eitem.getEnterprise().getEntname());// entname
			str.append("\n终端号:");
			str.append(imei);
			str.append("\n活动名称:");
			str.append(MmsName);
			str.append("\n活动描述:");
			str.append(Description);
			str.append("\n有效期:");
			str.append(ValidTo);
			str.append("\n本次使用数:$usernum");
			str.append("\n交易类型:权益兑换");
			str.append("\n批次号:");// tid
			if (barcode != null) {
				str.append(String.format("%9d", barcode.getId()));
			} else {
				str.append(String.format("%9d", eitem.getId()));
			}
			str.append("\n订单号:");
			str.append(printNo);
			str.append("\n日期时间:");
			str.append(CurrentDateTime);
			str.append("\n操作员:001");
			/*
			 * str.append("\n\n"); str.append("\n\n"); str.append("商家名称:");
			 * str.append(ShopFamilyName); str.append("\n");
			 * str.append("门店名称:"); str.append(ShopName);
			 * str.append("\n--------------------------------\n");
			 * str.append("活动名称:"); str.append(MmsName); str.append("\n");
			 * str.append("活动描述:"); str.append(Description); str.append("\n");
			 * str.append("活动有效期:"); str.append(ValidFrom); str.append("至");
			 * str.append(ValidTo); str.append("\n"); str.append("活动发起方:");
			 * str.append(ShopFamilyName);
			 * str.append("\n--------------------------------\n");
			 * str.append("本次使用数:$usernum\n"); str.append("流水号:");
			 * str.append(printNo); str.append("\n"); str.append("终端号:");
			 * str.append(imei); str.append("\n"); str.append("打印时间:");
			 * str.append(CurrentDateTime);
			 */
			str.append("</PrintInfo>");
			str.append("<Canusenum>" + ValidTimes + "</Canusenum>");
			str.append("</Table>");
		}
		return str.toString();
	}

	public String getStatByDatetimeIMEI(XMLGregorianCalendar beginDateTime,
			XMLGregorianCalendar endDateTime, String imei) {
		String starttime = beginDateTime.getYear() + "-"
				+ day(beginDateTime.getMonth()) + "-"
				+ month(beginDateTime.getDay()) + " 00:00:00";
		String endtime = endDateTime.getYear() + "-"
				+ day(endDateTime.getMonth()) + "-"
				+ month(endDateTime.getDay()) + " 23:59:59";
		List<ChecklogBo> list = new ArrayList<ChecklogBo>();
		// 查询日志表 checklog
		// 统计验证总次数
		/*************************** 1、统计淘宝商品 start **********************/
		Finder finder = new Finder(
				" select a.eitem.name as name,a.checktype,count(a.eitem.name) as itemtotal,a.eitem.id as id from Checklog as a where a.checktype=0 and a.createtime>=:starttime and a.createtime<=:endtime");
		finder.append(" and a.imei=:imei group by a.eitem.name,a.checktype order by a.createtime desc");
		finder.setParam("starttime", starttime);
		finder.setParam("endtime", endtime);
		finder.setParam("imei", imei);
		List checkloglist = checklogDao.find(finder);

		String schql = " from Device where  devcode=:imei";
		Finder devfinder = new Finder(schql);
		devfinder.setParam("imei", imei);
		List<Device> sclist = deviceDao.find(devfinder);
		String CurrentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());// 当前时间
		String shopname = "";
		String storename = "";
		if (sclist != null && sclist.size() > 0) {
			shopname = sclist.get(0).getShop().getName();
			storename = sclist.get(0).getStore().getName();
		}
		/*************************** 将统计的智惠商品与淘宝商品合并成为一个LIST列表返回 **********************/
		if (checkloglist != null && checkloglist.size() > 0) {
			String type = "";
			for (int i = 0; i < checkloglist.size(); i++) {
				ChecklogBo checklogBo = new ChecklogBo();
				Object[] checklogObj = (Object[]) checkloglist.get(i);
				type = (String) checklogObj[1];
				checklogBo.setId((Long) checklogObj[3]);
				checklogBo.setName((String) checklogObj[0]);
				checklogBo.setStorename(storename);
				checklogBo.setShopname(shopname);
				if (Variable.CHECKTYPE_0.equals(type)) {
					checklogBo.setSucctotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_1.equals(type)) {
					checklogBo.setFailtotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_2.equals(type)) {
					checklogBo.setExpitotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_3.equals(type)) {
					checklogBo.setNstotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_4.equals(type)) {
					checklogBo.setMutetotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_5.equals(type)) {
					checklogBo.setNistotal((Long) checklogObj[2]);
				} else {
					checklogBo.setFailtotal(checklogBo.getFailtotal()
							+ (Long) checklogObj[2]);
				}
				list.add(checklogBo);
			}
		}
		String[] params = new String[3];
		params[0] = "beginDateTime";
		params[1] = "endDateTime";
		params[2] = "IMEI";
		starttime = starttime.replaceAll(" ", "T");
		endtime = endtime.replaceAll(" ", "T");
		String yht = YhtWSClient.getYhtContent("GetStatOKByDatetimeIMEI",
				params, new Object[] { starttime, endtime, imei }, yhturl);
		return returnChecklogData(list, yht);
	}

	private String day(int day_month) {
		String strday = "";
		if (day_month < 10) {
			strday = "0" + day_month;
		} else {
			strday = "" + day_month;
		}
		return strday;
	}

	private String month(int month) {
		String strmonth = "";
		if (month < 10) {
			strmonth = "0" + month;
		} else {
			strmonth = "" + month;
		}
		return strmonth;
	}

	private String returnChecklogData(List<ChecklogBo> list, String yhtTable) {
		StringBuffer returnChecklog = new StringBuffer();
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ChecklogBo cb = list.get(i);
				returnChecklog.append("<Table>");
				returnChecklog.append(cb.toString());
				returnChecklog.append("</Table>");

			}
		}
		if (yhtTable != null) {
			returnChecklog.append(yhtTable);
		}
		if (yhtTable == null && (list == null || list.size() == 0)) {
			returnChecklog.append("<Table>");
			returnChecklog.append("<ErrorMessage>此设备未刷过码</ErrorMessage>");
			returnChecklog.append("</Table>");
		}

		returnChecklog.append("</NewDataSet>");
		return returnChecklog.toString();
	}

	@Override
	public String createBarcode(String type) {
		return createBarcode(type, 0, 1).get(0);
	}

	@Override
	public List<String> createBarcode(String type, int length) {
		// log.debug(" ---in createBarcode---");
		// List<String> barcodelist = new ArrayList<String>();
		// String barcode = "";
		// if (Variable.CODE_TYPE_NORMAL.equals(type)) {
		// synchronized (this) {
		// Condition[] conds = new Condition[1];
		// conds[0] = OrderBy.asc("id");
		// List<Codelibrary> codelist = codelibraryDao.findByEgList(
		// new Codelibrary(), false, conds, 0, length);
		// log.debug(" --- get codelist ok---");
		// long maxid = Long.MAX_VALUE;
		// for (Codelibrary code : codelist) {
		// barcode = code.getNumcode() + "," + code.getCharcode();
		// barcodelist.add(barcode);
		// maxid = code.getId();
		// }
		// log.debug(" --- for ok---");
		// if (maxid != Long.MAX_VALUE)
		// codelibraryDao
		// .updateByhql(" delete Codelibrary  where id <= "
		// + maxid);
		// log.debug(" --- delelt ok---");
		// }
		// } else if (Variable.CODE_TYPE_BANK.equals(type)) {
		// synchronized (this) {
		// Condition[] conds = new Condition[1];
		// conds[0] = OrderBy.asc("id");
		// Cebbcodelibrary cebbcode = new Cebbcodelibrary();
		// cebbcode.setStatus(0);
		// List<Cebbcodelibrary> cebblist = cebbcodelibraryDao
		// .findByEgList(cebbcode, true, conds, 0, length);
		// Long minid = Long.MAX_VALUE;
		// for (Cebbcodelibrary temp : cebblist) {
		// barcode = temp.getNumcode() + "," + temp.getCharcode();
		// barcodelist.add(barcode);
		// minid = temp.getId();
		// }
		// if (minid != Long.MAX_VALUE)
		// cebbcodelibraryDao
		// .updateByhql(" delete Codelibrary where id <= "
		// + minid);
		// }
		//
		// }
		return createBarcode(type, 0, length);
	}

	@Override
	public synchronized List<String> createBarcode(String type, int first,
			int end) {
		List<String> barcodelist = new ArrayList<String>();
		String barcode = "";
		if (Variable.CODE_TYPE_NORMAL.equals(type)) {
			synchronized (lock) {
				Condition[] conds = new Condition[1];
				conds[0] = OrderBy.asc("id");
				Codelibrary codelibrary = new Codelibrary();
				codelibrary.setStatus(0);
				List<Codelibrary> codelist = codelibraryDao.findByEgList(
						codelibrary, false, conds, first, end);
				long maxid = Long.MAX_VALUE;
				long minid = Long.MAX_VALUE;
				for (Codelibrary code : codelist) {
					if (minid == Long.MAX_VALUE) {
						minid = code.getId();
					}
					barcode = code.getNumcode() + "," + code.getCharcode();
					barcodelist.add(barcode);
					maxid = code.getId();
				}
				if (maxid != Long.MAX_VALUE)
					codelibraryDao
							.updateByhql(" update Codelibrary set status = 1 where id <= "
									+ maxid + " and id>=" + minid);
				return barcodelist;
			}
		} else if (Variable.CODE_TYPE_BANK.equals(type)) {
			synchronized (lock1) {
				Condition[] conds = new Condition[1];
				conds[0] = OrderBy.asc("id");
				Cebbcodelibrary cebbcode = new Cebbcodelibrary();
				cebbcode.setStatus(0);
				List<Cebbcodelibrary> cebblist = cebbcodelibraryDao
						.findByEgList(cebbcode, true, conds, first, end);
				Long minid = Long.MAX_VALUE;
				long maxid = Long.MAX_VALUE;
				for (Cebbcodelibrary temp : cebblist) {
					if (minid == Long.MAX_VALUE) {
						minid = temp.getId();
					}
					barcode = temp.getNumcode() + "," + temp.getCharcode();
					barcodelist.add(barcode);
					maxid = temp.getId();
				}
				if (minid != Long.MAX_VALUE) {
					cebbcodelibraryDao
							.updateByhql(" update Cebbcodelibrary set status = 1 where id <= "
									+ maxid + " and id>=" + minid);
				}
				return barcodelist;
			}

		} else if (Variable.CODE_TYPE_ONLY.equals(type)) {
			synchronized (lock2) {
				Condition[] conds = new Condition[1];
				conds[0] = OrderBy.asc("id");
				Fourcodelibrary cebbcode = new Fourcodelibrary();
				cebbcode.setStatus(0);
				first = tempint;
				tempint++;
				if (tempint >= 100)
					tempint = 0;
				List<Fourcodelibrary> cebblist = fourcodelibraryDao
						.findByEgList(cebbcode, true, conds, first, end);
				Long minid = Long.MAX_VALUE;
				long maxid = Long.MAX_VALUE;
				for (Fourcodelibrary temp : cebblist) {
					if (minid == Long.MAX_VALUE) {
						minid = temp.getId();
					}
					barcode = temp.getNumcode() + "," + temp.getCharcode();
					barcodelist.add(barcode);
					maxid = temp.getId();
				}
				if (minid != Long.MAX_VALUE)
					fourcodelibraryDao
							.updateByhql(" update Fourcodelibrary set status = 1 where id <= "
									+ maxid + " and id>=" + minid);
				return barcodelist;
			}
		} else if (Variable.CODE_TYPE_PAYMENT.equals(type)) {
			synchronized (lock3) {
				Condition[] conds = new Condition[1];
				conds[0] = OrderBy.asc("id");
				Paymentcodelibrary cebbcode = new Paymentcodelibrary();
				cebbcode.setStatus(0);
				List<Paymentcodelibrary> cebblist = paymentcodelibraryDao
						.findByEgList(cebbcode, true, conds, first, end);
				Long minid = Long.MAX_VALUE;
				long maxid = Long.MAX_VALUE;
				for (Paymentcodelibrary temp : cebblist) {
					if (minid == Long.MAX_VALUE) {
						minid = temp.getId();
					}
					barcode = temp.getNumcode() + "," + temp.getCharcode();
					barcodelist.add(barcode);
					maxid = temp.getId();
				}
				if (minid != Long.MAX_VALUE)
					paymentcodelibraryDao
							.updateByhql(" update Paymentcodelibrary set status = 1 where id <= "
									+ maxid + " and id>=" + minid);
				return barcodelist;
			}
		}
		return barcodelist;
	}

	public String bindActiveToHY(String charcode, String numcode, long num,
			long cid) {
		if (numcode == null) {
			String hql = "from Barcode a where a.charcode=? and a.coupon.id=?";
			List<Barcode> list = barcodeDao.find(hql, new Object[] { charcode,
					cid });
			if (list != null && list.size() > 0) {
				numcode = list.get(0).getNumcode();// 使用以前的二维码
			} else {
				numcode = createBarcode("code").split(",")[0];// 重新生成二维码
			}
			bindActiveToBarcode(Variable.HUIYUAN, numcode, charcode, num,
					Variable.SOURCE_EMAY, cid);
		} else {
			bindActiveToBarcode(Variable.HUIYUAN, numcode, charcode, num,
					Variable.SOURCE_EMAY, cid);
		}
		return numcode;
	}

	/**
	 * (非 Javadoc)
	 * 
	 * 绑定优惠到二维码
	 * 
	 * @param codetype
	 *            二维码类型
	 * @param numcode
	 *            数字码
	 * @param charcode
	 *            字符码
	 * @param num
	 *            绑定次数
	 * @param source
	 *            来源
	 * @param cid
	 *            优惠id
	 * @see cn.m.mt.webservice.Wsservice#bindActiveToBarcode(java.lang.String,
	 *      java.lang.String, long, java.lang.String, long[])
	 */
	private void bindActiveToBarcode(String codetype, String numcode,
			String charcode, long num, String source, long... cid) {
		// cid记得可能会变成优惠券的uuid
		if (StringUtils.isEmpty(numcode)) {
			throw new WarnException("数字码不能为空");
		}
		if (StringUtils.isEmpty(charcode)) {
			throw new WarnException("字符码不能为空");
		}
		if (cid == null || cid.length == 0) {
			throw new WarnException("优惠ID不能为空");
		}
		if (codetype == null || "".equals(codetype.trim())) {
			codetype = Variable.YICIMA;
		}
		for (long couponid : cid) {
			String hql = "from Barcode a where a.numcode=? and a.charcode=? and a.coupon.id=?";
			List<Barcode> list = barcodeDao.find(hql, new Object[] { numcode,
					charcode, cid });
			Barcode barcode = new Barcode();
			barcode.setNumcode(numcode);
			barcode.setCharcode(charcode);
			barcode.setSource(source);
			barcode.setCodetype(codetype);
			barcode.setCreattime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			long canusenum = 0;
			if (list != null && list.size() > 0) {
				// barcode = list.remove(0);
				if (barcode.getCanusenum() != null) {
					canusenum = barcode.getCanusenum();
				}
				for (Barcode obj : list) {
					if (obj.getCanusenum() != null) {
						if (canusenum + obj.getCanusenum() < Integer.MAX_VALUE) {// 防止联会可用次数超出最大值
							canusenum += obj.getCanusenum();
						}
					}
					barcodeDao.delete(obj);
				}
				barcode.setCanusenum(canusenum);

			}
			if (num == Integer.MAX_VALUE) {
				barcode.setCanusenum(new Long(Integer.MAX_VALUE));
			} else {
				if (canusenum + num <= Integer.MAX_VALUE) {
					barcode.setCanusenum(canusenum + num);
				}
			}
			barcodeDao.save(barcode);
		}

	}

	public String[] terminalBarcodeVerify(String charcode, String imei,
			int usernum) throws VerifyErrorException {
		return barcodeVerify(null, charcode, imei, null, null, null, usernum,
				Variable.CHECKMODE_DEVICE);
	}

	public String[] barcodeVerify(String numcode, String charcode, String imei,
			String storeuuid, String couponuuid, String printno, int usednum,
			int checkmode) throws VerifyErrorException {
		return new String[]{};
	}

	private void updateEorder(Barcode barcode) {
		Eorder eorder = barcode.getEorder();
		long codetimes = eorder.getEitem().getCodetimes();
		long canusenum = barcode.getCanusenum();
		Integer refundnum = eorder.getRefundnum();
		if (null == refundnum)
			refundnum = 0;
		Long temp = eorder.getNum() - refundnum - canusenum / codetimes;
		Integer overnum = Integer.parseInt(temp.toString());
		eorder.setOvernum(overnum);
		eorderDao.update(eorder);
		int num = (int) (eorder.getNum() * codetimes - canusenum);
		if ((num - 1) % codetimes == 0) {
			Overorderlog overorder = new Overorderlog();
			overorder.setEitem(barcode.getEitem());
			Double payment = eorder.getPayment();
			if (null != payment && !payment.isNaN() && eorder.getNum() != 0)
				overorder.setPayment(eorder.getPayment() / eorder.getNum());
			overorder.setTid(eorder.getTid());
			overorder.setCreatetime(DateTimeUtils.dateToStr(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
			eorderDao.saveOrUpdate(overorder);
		}

	}

	private void writeLog(String imei, String storeuuid, Barcode barcode,
			String twocode, String checktype, String des, String printno,
			int checknum, int checkmode, Eitem eitem, String createtime,
			String batchno, String searchno, String posno) {
		log.debug("====in writeLog===");
		Checklog checklog = new Checklog();
		Device device = null;
		Store store = null;
		if (!StringUtils.isEmpty(imei)) {
			List<Device> list = deviceDao.findByProperty("devcode", imei);
			if (list != null && list.size() == 1) {
				device = list.get(0);
				store = device.getStore();
				checklog.setDevice(device);
				checklog.setStore(store);
			}
			checklog.setImei(imei);// 记录终端号
		}
		if (store == null) {
			store = storeDao.findUniqueByProperty("guid", storeuuid);
			checklog.setStore(store);
		}
		if (store != null) {
			checklog.setStoreuuid(store.getGuid());// 记录门店uuid
		}
		if (!StringUtils.isEmpty(printno)) {
			checklog.setPrintno(printno);
			checklog.setPrintresult(Variable.PRINT_0);
		}
		if (eitem != null) {
			checklog.setEitem(eitem);
			checklog.setBarcode(barcode);
		} else if (barcode != null) {
			checklog.setEitem(barcode.getEitem());// 设置淘宝商品
			checklog.setBarcode(barcode);
		}
		if (barcode != null && barcode.getEorder() != null) {
			checklog.setPhone(barcode.getEorder().getPhone());
		}
		checklog.setChecknum(checknum);
		checklog.setCheckmode(checkmode);
		checklog.setCharcode(twocode);
		checklog.setChecktype(checktype);
		checklog.setDes(des);
		checklog.setCreatetime(createtime);
		if (null != batchno && !"".equals(batchno)) {
			checklog.setBatchno(batchno);
			checklog.setSearchno(searchno);
			checklog.setPosno(posno);
		}
		checklogDao.save(checklog);
	}

	public String webBarcodeVerify(String numcode, String storeuuid,
 String couponuuid) throws VerifyErrorException {
		String devcode = null;
		Store store = storeDao.findUniqueByProperty("guid", storeuuid);
		if (store != null) {
			String hql = "from Device where status = 0 and store.id = " + store.getId();
			List<Device> list = deviceDao.find(new Finder(hql));
			if (list != null && !list.isEmpty()) {
				devcode = list.get(0).getDevcode();
			}else {
				hql = "from Device where store.id = " + store.getId();
				list = deviceDao.find(new Finder(hql));
				if (list != null && !list.isEmpty()) {
					devcode = list.get(0).getDevcode();
				}
			}
		}
		return barcodeVerify(numcode, null, devcode, storeuuid, couponuuid, null, 1, Variable.CHECKMODE_MANUAL)[0];
	}

	public int updatePrintResult(String imei, String barcode, String printno,
			boolean isPrinted) {

		boolean bool = false;
		if (barcode.equals(testcode)) {
			return 1;
		}
		Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
		if (pt.matcher(barcode).matches()) {// 数字二维码
			if (barcode.length() == 12 && barcode.startsWith("0")) {
				bool = true;
			}
		} else {// 带有英文字母的二维码
			if (barcode.length() == 12 && barcode.startsWith("E")) {
				bool = true;
			}
		}
		if (bool) {
			String hql = "from Checklog where imei=? and charcode=? and printno=?";
			List<Checklog> list = checklogDao.find(hql, new String[] { imei,
					barcode, printno });
			if (list != null && list.size() == 1) {
				Checklog entity = list.get(0);
				if (isPrinted) {
					entity.setPrintresult(Variable.PRINT_1);
				} else {
					entity.setPrintresult(Variable.PRINT_2);
				}
				checklogDao.update(entity);
				return 1;
			}
			return 0;
		} else {
			String[] params = new String[4];
			params[0] = "IMEI";
			params[1] = "verifyPwd";
			params[2] = "printNo";
			params[3] = "IsPrinted";
			String yht[] = YhtWSClient.getYhtContentToStringList(
					"UpdatePrintResult", params, new Object[] { imei, barcode,
							printno, isPrinted + "" }, yhturl);
			if (yht == null) {
				return 0;
			}
			return Integer.parseInt(yht[0]);
		}
	}

	public String GetNewVersionByMobileType(String currentVersionNumber,
			String mobileType, String IMEI) {
		String datetime = DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss");
		Device device = deviceDao.findUniqueByProperty("devcode", IMEI);
		if (device != null) {// 设备表中存在更新
			device.setOnlinetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			deviceDao.update(device);
		} else {
			return "OK," + datetime;
		}
		Finder finder = new Finder(
				" from Devicemodify as df where df.devicecode=:IMEI and df.devicetype=:mobileType");
		finder.setParam("IMEI", IMEI);
		finder.setParam("mobileType", mobileType);
		List<Devicemodify> dflist = devicemodifyDao.find(finder);
		if (dflist.size() > 0 && dflist != null) {
			Devicemodify dv = dflist.get(0);
			String mversion = dv.getDeviceversion().getVersionno().toString();
			if (currentVersionNumber.compareTo(mversion) < 0) {// 当前版本小于数据库中的版本
				String versionPath = resserverurl + "files/"
						+ Variable.DEVICE_DIR + "/"
						+ dv.getDeviceversion().getVersionurl();
				// updateDevice(IMEI, dv.getDeviceversion(), mobileType,
				// datetime,dv);
				// adeviceDao.delete(dv);//删除待更新表中的数据
				return versionPath + "," + datetime;
			}
		}
		Finder finder1 = new Finder(
				" from Deviceversion ds where ds.type=1 order by ds.createtime desc");
		List<Deviceversion> list = deviceversionDao.find(finder1);
		if (list != null && !list.isEmpty()) {
			Deviceversion deviceversion = list.get(0);
			if (currentVersionNumber.compareTo(deviceversion.getVersionno()
					.toString()) < 0) {// 当前版本小于数据库中的版本
				String versionPath = resserverurl + "files/"
						+ Variable.DEVICE_DIR + "/"
						+ deviceversion.getVersionurl();
				// updateDevice(IMEI, deviceversion, mobileType, datetime,null);
				return versionPath + "," + datetime;
			}
		}
		return "OK," + datetime;
	}

	private void updateDevice(String IMEI, Deviceversion dv, String mobileType,
			String datetime, Devicemodify df) {
		// 更新设备表
		Device device = deviceDao.findUniqueByProperty("devcode", IMEI);
		if (device != null) {
			device.setDeviceversion(dv);
			device.setUpdatetime(datetime);
			device.setVersionno(dv.getVersionno());
			device.setUpdatetype(mobileType);
			deviceDao.update(device);
		}
		// 保存设备更新记录到Deviceofupdate表
		Deviceofupdate dou = new Deviceofupdate();
		dou.setDevicecode(IMEI);
		dou.setDeviceversion(dv);
		dou.setDevicetype(mobileType);
		dou.setCreatetime(datetime);
		deviceofupdateDao.save(dou);

		// 删除设备待更新表devicemodify
		if (df != null)
			devicemodifyDao.delete(df);
	}

	public String healthCheckByImeiAndImsi(String imei, String imsi) {
		Device device = deviceDao.findUniqueByProperty("devcode", imei);
		if (device != null) {// 设备表中存在更新
			device.setOnlinetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			deviceDao.update(device);
		}
		return "OK";
	}

	public String[] terminalBarcodeVerify(String charcode, String imei,
			String couponuuid, String printno, int usernum)
			throws VerifyErrorException {
		return barcodeVerify(null, charcode, imei, null, couponuuid, printno,
				usernum, Variable.CHECKMODE_DEVICE);

	}

	public String getOrderInfoByDatetimeIMEI(String id, String beginDateTime,
			String endDateTime, String imei) {
		beginDateTime = beginDateTime + " 00:00:00";
		endDateTime = endDateTime + " 23:59:59";
		// 截取设备ID的后4位
		StringBuffer str = new StringBuffer();
		str.append("<?xml version='1.0' encoding='UTF-8'?>");
		str.append("<NewDataSet>");
		Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
		if (pt.matcher(id).matches()) {
			Device device = deviceDao.findUniqueByProperty("devcode", imei);
			Finder finder = new Finder(
					"from Checklog where checktype=0 and eitem.id=:eitemid");
			finder.setParam("eitemid", Long.valueOf(id));
			finder.append(" and createtime>=:starttime and createtime<=:endtime");
			finder.setParam("starttime", beginDateTime);
			finder.setParam("endtime", endDateTime);
			finder.append(" and imei=:devcode order by createtime desc");
			finder.setParam("devcode", imei);
			List<Checklog> list = checklogDao.find(finder);
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					Checklog checklog = list.get(i);
					Eitem eitem = checklog.getEitem();
					String MmsName = "";// 活动名称
					String printNo = checklog.getPrintno();
					String Description = "";// 活动描述
					String ValidFrom = "";// 活动开始日期
					String ValidTo = "";// 活动结束日期
					String ShopName = "";// 商户名称
					String ShopFamilyName = "";// 商家名称
					String MmsFamilyName = "";// 活动发起方

					String CurrentDateTime = checklog.getCreatetime();
					MmsName = eitem.getName();
					Description = "";
					if (null != eitem.getSmscontent()
							&& !"".equals(eitem.getSmscontent())) {
						Description = eitem.getSmscontent();
					} else if (null != eitem.getCheckinfo()
							&& !"".equals(eitem.getCheckinfo())) {
						Description = eitem.getCheckinfo();
					} else if (null != eitem.getPrintstr()
							&& !"".equals(eitem.getPrintstr())) {
						Description = eitem.getPrintstr();
					} else {
						Description = eitem.getName();
					}
					String printstr = eitem.getPrintstr();
					long ValidTimes = checklog.getChecknum();
					int LastVerifyNumber = 0;

					String verifyPwd = checklog.getCharcode();// 验证码
					String CreatedOn = checklog.getCreatetime();
					Barcode barcode = checklog.getBarcode();
					if (barcode != null) {
						LastVerifyNumber = barcode.getCanusenum().intValue();// 剩余次数
						ValidFrom = barcode.getCreattime();
						ValidTo = barcode.getEndate();
						CreatedOn = barcode.getCreattime();// 创建时间
						Eorder eorder = barcode.getEorder();
						if (eorder.getBuynick() != null) {
							Description = Description.replace("$name",
									eorder.getBuynick());
						} else {
							Description = Description.replace("$name", "");
						}
						Description = Description.replace("$pwd", "二维码");
						if (eorder.getExtfield1() != null) {
							Description = Description.replace("$f1",
									eorder.getExtfield1());
						} else {
							Description = Description.replace("$f1", "");
						}

						if (eorder.getExtfield2() != null) {
							Description = Description.replace("$f2",
									eorder.getExtfield2());
						} else {
							Description = Description.replace("$f2", "");
						}
						if (eorder.getExtfield3() != null) {
							Description = Description.replace("$f3",
									eorder.getExtfield3());
						} else {
							Description = Description.replace("$f3", "");
						}
						if (printstr != null && !"".equals(printstr)) {
							if (null != eorder.getBuynick()
									&& !"".equals(eorder.getBuynick()))
								printstr = printstr.replace("$name",
										eorder.getBuynick());
							printstr = printstr.replace("$pwd",
									checklog.getCharcode());
							if (null != eorder.getExtfield1()
									&& !"".equals(eorder.getExtfield1()))
								printstr = printstr.replace("$f1",
										eorder.getExtfield1());
							if (null != eorder.getExtfield2()
									&& !"".equals(eorder.getExtfield2()))
								printstr = printstr.replace("$f2",
										eorder.getExtfield2());
							if (null != eorder.getExtfield3()
									&& !"".equals(eorder.getExtfield3()))
								printstr = printstr.replace("$f3",
										eorder.getExtfield3());
							MmsFamilyName = MmsFamilyName + "\n" + printstr;
						}
					} else {
						Description = Description.replace("$name", "");
						Description = Description.replace("$pwd", "二维码");
						Description = Description.replace("$f1", "");
					}

					ShopName = device.getStore().getName();
					ShopFamilyName = device.getStore().getShop().getName();
					MmsFamilyName = device.getStore().getShop().getName();
					if (ValidFrom.length() > 10)
						ValidFrom = ValidFrom.substring(0, 10);

					if (ValidTo.length() > 10)
						ValidTo = ValidTo.substring(0, 10);
					if (eitem != null)
						printstr = eitem.getPrintstr();
					String entname = eitem.getEnterprise().getEntname();
					str.append("<Table>");
					str.append("<IsNeedConfirm>false</IsNeedConfirm>");
					str.append("<ConfirmNumber></ConfirmNumber>");
					str.append("<PwdInfoId></PwdInfoId>");
					str.append("<MmsId>" + eitem.getId() + "</MmsId>");
					str.append("<MastMmsId>" + eitem.getId() + "</MastMmsId>");
					str.append("<MmsName>" + MmsName + "</MmsName>");
					str.append("<Description>" + Description + "</Description>");
					str.append("<MmsTitle></MmsTitle>");
					str.append("<ValidFrom>" + ValidFrom + "</ValidFrom>");
					str.append("<ValidTo>" + ValidTo + "</ValidTo>");
					str.append("<ResendContent></ResendContent>");
					str.append("<ValidTimes>" + ValidTimes + "</ValidTimes>");
					str.append("<ShopName>" + ShopName + "</ShopName>");
					str.append("<ShopFamilyName>" + ShopFamilyName
							+ "</ShopFamilyName>");
					str.append("<MmsFamilyName>" + MmsFamilyName
							+ "</MmsFamilyName>");
					str.append("<Advertising></Advertising>");
					str.append("<PrintNo>" + printNo + "</PrintNo>");
					str.append("<LastVerifyNumber>" + LastVerifyNumber
							+ "</LastVerifyNumber>");
					str.append("<verifyPwd>" + verifyPwd + "</verifyPwd>");
					str.append("<CreatedOn>" + CreatedOn + "</CreatedOn>");
					str.append("<CurrentDateTime>" + CurrentDateTime
							+ "</CurrentDateTime>");
					str.append("<Entname>" + entname + "</Entname>");
					str.append("<Printstr>" + printstr + "</Printstr>");
					str.append("<PrintTitle>");
					if (eitem.getPrintTitle() != null
							|| !"".equals(eitem.getPrintTitle()))
						str.append("汇金通消费单");
					else
						str.append(eitem.getPrintTitle());
					str.append("</PrintTitle>");

					str.append("<ShopNo>");
					Store store = checklog.getStore();
					String batchno = String.format(
							"%012d",
							Long.valueOf(store.getShop().getId() + ""
									+ store.getId()));
					str.append(batchno);
					str.append("</ShopNo>");
					str.append("<TranType>权益兑换");
					str.append("</TranType>");
					str.append("<BacthNo>");
					if (barcode != null) {
						str.append(String.format("%09d", barcode.getId()));
					} else {
						str.append(String.format("%09d", checklog.getId()));
					}
					str.append("</BacthNo>");
					str.append("<AdminNo>001");
					str.append("</AdminNo>");
					str.append("<Verifynum>");
					str.append(checklog.getChecknum());
					str.append("</Verifynum>");

					str.append("<PrintInfo>");
					str.append("\n\n");
					if (eitem.getPrintTitle() != null
							|| !"".equals(eitem.getPrintTitle()))
						str.append("汇金通消费单");
					else
						str.append(eitem.getPrintTitle());
					str.append("\n\n");
					str.append("商户名称:");
					str.append(ShopFamilyName);
					str.append("\n商户号:");
					str.append(batchno);
					str.append("\n门店名称:");
					str.append(ShopName);
					str.append("\n活动发起方:");
					str.append(eitem.getEnterprise().getEntname());// entname
					str.append("\n终端号:");
					str.append(imei);
					str.append("\n活动名称:");
					str.append(MmsName);
					str.append("\n活动描述:");
					str.append(Description);
					str.append("\n有效期:");
					str.append(ValidTo);
					str.append("\n本次使用数:");
					str.append(checklog.getChecknum());
					str.append("\n交易类型:权益兑换");
					str.append("\n批次号:");// tid
					if (barcode != null)
						str.append(String.format("%09d", barcode.getId()));
					else
						str.append(String.format("%09d", checklog.getId()));
					str.append("\n订单号:");
					str.append(printNo);
					str.append("\n日期时间:");
					str.append(CurrentDateTime);
					str.append("\n操作员:001");
					str.append("</PrintInfo>");
					str.append("</Table>");

				}
			} else {
				String[] params = new String[4];
				params[0] = "MmsId";
				params[1] = "beginDateTime";
				params[2] = "endDateTime";
				params[3] = "IMEI";
				String yht = YhtWSClient.getYhtContent(
						"GetOrderInfoByDatetimeIMEI", params, new Object[] {
								id, beginDateTime, endDateTime, imei }, yhturl);
				str.append(yht);
			}
		} else {
			String[] params = new String[4];
			params[0] = "MmsId";
			params[1] = "beginDateTime";
			params[2] = "endDateTime";
			params[3] = "IMEI";
			String yht = YhtWSClient.getYhtContent(
					"GetOrderInfoByDatetimeIMEI", params, new Object[] { id,
							beginDateTime, endDateTime, imei }, yhturl);
			str.append(yht);
		}
		str.append("</NewDataSet>");
		return str.toString();

	}

	public String[] verifyZXBarcode(String numcode, String imei, Eitem eitem,
			int num, String ctime, String batchno, String searchno, String posno) {
		String[] result = new String[4];
		Eorder eorder = new Eorder();
		eorder.setExtfield1(numcode);
		List<Eorder> eorderlist = eorderDao.findByEgList(eorder);
		Device device = deviceDao.findUniqueByProperty("devcode", imei);
		if (device == null) {
			result[0] = "";
			result[1] = "不是合法的机具";
			return result;
		}
		String outid = eitem.getProductcode();
		System.out.println("----numcode---" + numcode + "----outerid--" + outid
				+ "----- eorderlist -----" + eorderlist);
		if (eorderlist != null && !eorderlist.isEmpty()) {
			eorder = eorderlist.get(0);
			Store store = device.getStore();
			Finder finder = new Finder(
					" from Eitemstore where eitem.outerid = :outerid and store.id = :storeid");
			finder.setParam("outerid", eitem.getOuterid());
			finder.setParam("storeid", store.getId());
			List<Eitemstore> eitemstorelist = eitemstoreDao.find(finder);
			if (null == eitemstorelist || eitemstorelist.isEmpty()) {
				finder = new Finder(
						" from Eitemstore where eitem.eitem.id = :eitemid and store.id = :storeid");
				finder.setParam("eitemid", eitem.getId());
				finder.setParam("storeid", store.getId());
				eitemstorelist = eitemstoreDao.find(finder);
			}
		}
		Shop shop = device.getStore().getShop();
		log.debug("----numcode 1---" + numcode + "----outerid 1--" + outid);
		Payment payment = new Payment();
		log.debug("----1---------");
		String orderid = String.format("%019d", Long.parseLong(DateTimeUtils
				.dateToStr(new Date(), "yyyyMMddHHmmssSSS")));
		if(batchno==null || "".equals(batchno))
		batchno  = String
				.format("%06d", Integer.parseInt(DateTimeUtils.dateToStr(
						new Date(), "yyMMdd")));
		String serialno = String
				.format("%06d", Integer.parseInt(DateTimeUtils.dateToStr(
						new Date(), "HHmmss")));
//		String panstr = String.format("%1$-19s", numcode);
		String panstr = numcode;
		String transamt = String.format("%013d", 0);
		String timestamp = DateTimeUtils.dateToStr(new Date(),
				"yyyyMMddHHmmssSSS");
		String productnum = String.format("%02d", num);
		payment.setBatchno(batchno);
        payment.setSearchno(searchno);
		payment.setMobile("");
		payment.setOrderid(orderid);
		payment.setPanstr(panstr);
		payment.setProductnum(productnum);
		payment.setProducttype(outid);
		payment.setSerialno(serialno);
		payment.setTimestamp(timestamp);
		payment.setTransamt(transamt);
		payment.setStatus(new Short("0"));
		payment.setImei(imei);
		payment.setPayway("03");
		payment.setMerchantid(eitem.getMerchantid());
		payment.setMerchantpassword(eitem.getMerchantpassword());
		if (istest) {
			payment = TestZXWsClient.barcodeDividedPayment(payment);
		} else {
			payment = ZXWsClient.barcodeDividedPayment(payment);
		}

		paymentDao.save(payment);
		if (payment.getRetcode().equals("0000000")) {
			log.debug("====in writeLog===");
			Checklog checklog = new Checklog();
			Store store = device.getStore();
			checklog.setDevice(device);
			checklog.setStore(store);
			checklog.setImei(imei);
			checklog.setStoreuuid(store.getGuid());
			checklog.setEitem(eitem);
			// String subImei = imei.substring(imei.length() - 4,
			// imei.length());
			// String currDate = new SimpleDateFormat("yyMMdd").format(new
			// Date());// 生成日期格式为：年年月月日日、如：110608(11年06月08日)
			// DecimalFormat df = new DecimalFormat("0000");
			// Random rd = new Random();
			// String increase = df.format(rd.nextInt(9999));// 4位自增长数
			// // printNo生成规则：设备ID的后4位+日期6位+4位增长数
			// String printno = subImei + "" + currDate + "" + increase;
			checklog.setPrintno(payment.getOrderid());
			checklog.setChecknum(num);
			checklog.setCheckmode(1);
			checklog.setPhone("");
			checklog.setCharcode(numcode);
			checklog.setChecktype("0");
			checklog.setDes("权益兑换");
			checklog.setCreatetime(ctime);
			checklog.setBatchno(batchno);
			checklog.setSearchno(searchno);
			checklog.setPosno(posno);
			checklogDao.save(checklog);
			log.debug("=====save checklog=============");

		}
		result[0] = payment.getCommentres();
		result[1] = payment.getRetcode();
		result[2] = payment.getOrderid();
		result[3] = ctime;
		/*
		 * if ("0000000".equals(result[1])) { if (device == null) device =
		 * deviceDao.findUniqueByProperty("devcode", imei); if (device != null)
		 * { Eitem eitem = eitemDao.findUniqueByProperty("outerid", outid); if
		 * (eitem != null) { writeLog(imei, "", null, numcode,
		 * Variable.CHECKTYPE_0, "", "", 1, Variable.CHECKMODE_DEVICE, null); }
		 * } }
		 */
		return result;

	}

	public String getEorderStatByDatetimeIMEI(
			XMLGregorianCalendar beginDateTime,
			XMLGregorianCalendar endDateTime, String imei) {

		String starttime = beginDateTime.getYear() + "-"
				+ day(beginDateTime.getMonth()) + "-"
				+ month(beginDateTime.getDay()) + " 00:00:00";
		String endtime = endDateTime.getYear() + "-"
				+ day(endDateTime.getMonth()) + "-"
				+ month(endDateTime.getDay()) + " 23:59:59";
		String couponname = "";
		List<ChecklogBo> list = new ArrayList<ChecklogBo>();
		// 查询日志表 checklog
		// 统计验证总次数
		Finder finder = new Finder(
				" select a.eitem.name as name,a.checktype,count(a.eitem.name) as itemtotal,a.eitem.id as id from Checklog as a where a.checktype=0 and a.createtime>=:starttime and a.createtime<=:endtime");
		finder.append(" and a.imei=:imei group by a.eitem.name,a.checktype order by a.createtime desc");
		finder.setParam("starttime", starttime);
		finder.setParam("endtime", endtime);
		finder.setParam("imei", imei);

		List checkloglist = checklogDao.find(finder);
		/*************************** 将统计的智惠商品与淘宝商品合并成为一个LIST列表返回 **********************/
		if (checkloglist != null && checkloglist.size() > 0) {
			String type = "";
			for (int i = 0; i < checkloglist.size(); i++) {
				ChecklogBo checklogBo = new ChecklogBo();
				Object[] checklogObj = (Object[]) checkloglist.get(i);
				type = (String) checklogObj[1];
				checklogBo.setId((Long) checklogObj[3]);
				checklogBo.setName((String) checklogObj[0]);
				if (Variable.CHECKTYPE_0.equals(type)) {
					checklogBo.setSucctotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_1.equals(type)) {
					checklogBo.setFailtotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_2.equals(type)) {
					checklogBo.setExpitotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_3.equals(type)) {
					checklogBo.setNstotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_4.equals(type)) {
					checklogBo.setMutetotal((Long) checklogObj[2]);
				} else if (Variable.CHECKTYPE_5.equals(type)) {
					checklogBo.setNistotal((Long) checklogObj[2]);
				} else {
					checklogBo.setFailtotal(checklogBo.getFailtotal()
							+ (Long) checklogObj[2]);
				}
				list.add(checklogBo);
			}

		}
		return returnChecklogData(list, beginDateTime.toString(),
				endDateTime.toString());

	}

	public String getEorderInfoByDatetimeIMEI(String id, String beginDateTime,
			String endDateTime, String imei) {
		beginDateTime = beginDateTime + " 00:00:00";
		endDateTime = endDateTime + " 23:59:59";
		// 截取设备ID的后4位
		StringBuffer str = new StringBuffer();
		str.append("<?xml version='1.0' encoding='UTF-8'?>");
		str.append("<NewDataSet>");
		Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
		if (pt.matcher(id).matches()) {
			Device device = deviceDao.findUniqueByProperty("devcode", imei);
			Finder finder = new Finder(
					"from Checklog where checktype=0 and eitem.id=:eitemid");
			finder.setParam("eitemid", Long.valueOf(id));
			finder.append(" and createtime>=:starttime and createtime<=:endtime");
			finder.setParam("starttime", beginDateTime);
			finder.setParam("endtime", endDateTime);
			finder.append(" and imei=:devcode order by createtime desc");
			finder.setParam("devcode", imei);
			List<Checklog> list = checklogDao.find(finder);

			// if (list.isEmpty()) {
			// finder = new Finder(
			// "from Checklog where checktype=0 and coupon.id=:eitemid");
			// finder.setParam("eitemid", Long.valueOf(id));
			// finder.append(" and createtime>=:starttime and createtime<=:endtime");
			// finder.setParam("starttime", beginDateTime);
			// finder.setParam("endtime", endDateTime);
			// finder.append(" and device.devcode=:devcode order by createtime desc");
			// finder.setParam("devcode", imei);
			// list = checklogDao.find(finder);
			// }
			for (int i = 0; i < list.size(); i++) {
				Checklog checklog = list.get(i);
				Barcode barcode = checklog.getBarcode();
				Eitem eitem = checklog.getEitem();
				long ValidTimes = barcode.getCanusenum();
				String MmsId = "";// 活动ID
				String MastMmsId = "";// 主活动ID
				String MmsName = "";// 活动名称
				String printNo = checklog.getPrintno();
				String Description = "";// 活动描述
				String ValidFrom = "";// 活动开始日期
				String ValidTo = "";// 活动结束日期
				String ShopName = "";// 商户名称
				String ShopFamilyName = "";// 商家名称
				String MmsFamilyName = "";// 活动发起方
				int LastVerifyNumber = barcode.getCanusenum().intValue();// 剩余次数
				String verifyPwd = barcode.getNumcode();// 验证码
				String CreatedOn = barcode.getCreattime();// 创建时间
				String CurrentDateTime = checklog.getCreatetime();
				String ShopId = "";// 门店ID
				MmsId = itemuiid;
				MastMmsId = itemuiid;
				MmsName = barcode.getEitem().getName();
				Description = barcode.getEitem().getSmscontent();
				Eorder eorder = barcode.getEorder();
				if (eorder.getBuynick() != null) {
					Description = Description.replace("$name",
							eorder.getBuynick());
				} else {
					Description = Description.replace("$name", "");
				}
				Description = Description.replace("$pwd", "二维码");
				if (eorder.getExtfield1() != null) {
					Description = Description.replace("$f1",
							eorder.getExtfield1());
				} else {
					Description = Description.replace("$f1", "");
				}

				if (eorder.getExtfield2() != null) {
					Description = Description.replace("$f2",
							eorder.getExtfield2());
				} else {
					Description = Description.replace("$f2", "");
				}
				if (eorder.getExtfield3() != null) {
					Description = Description.replace("$f3",
							eorder.getExtfield3());
				} else {
					Description = Description.replace("$f3", "");
				}
				ShopName = device.getStore().getName();
				ShopFamilyName = device.getStore().getShop().getName();
				MmsFamilyName = device.getStore().getShop().getName();
				ValidFrom = barcode.getCreattime();
				if (ValidFrom.length() > 10) {
					ValidFrom = ValidFrom.substring(0, 10);
				}
				ValidTo = barcode.getEndate();
				if (ValidTo.length() > 10) {
					ValidTo = ValidTo.substring(0, 10);
				}
				String printstr = barcode.getEitem().getPrintstr();
				if (eitem != null)
					printstr = eitem.getPrintstr();
				if (printstr != null && !"".equals(printstr)) {
					if (null != barcode.getEorder().getBuynick()
							&& !"".equals(barcode.getEorder().getBuynick()))
						printstr = printstr.replace("$name", barcode
								.getEorder().getBuynick());
					if (null != barcode.getNumcode()
							&& !"".equals(barcode.getNumcode()))
						printstr = printstr.replace("$pwd",
								barcode.getNumcode());
					if (null != barcode.getEorder().getExtfield1()
							&& !"".equals(barcode.getEorder().getExtfield1()))
						printstr = printstr.replace("$f1", barcode.getEorder()
								.getExtfield1());
					if (null != barcode.getEorder().getExtfield2()
							&& !"".equals(barcode.getEorder().getExtfield2()))
						printstr = printstr.replace("$f2", barcode.getEorder()
								.getExtfield2());
					if (null != barcode.getEorder().getExtfield3()
							&& !"".equals(barcode.getEorder().getExtfield3()))
						printstr = printstr.replace("$f3", barcode.getEorder()
								.getExtfield3());
					MmsFamilyName = MmsFamilyName + "\n" + printstr;
				}

				ShopId = device.getStore().getShop().getUuid();
				str.append("<Table>");
				str.append("<IsNeedConfirm>false</IsNeedConfirm>");
				str.append("<ConfirmNumber></ConfirmNumber>");
				str.append("<PwdInfoId></PwdInfoId>");
				str.append("<MmsId>" + MmsId + "</MmsId>");
				str.append("<MastMmsId>" + MastMmsId + "</MastMmsId>");
				str.append("<MmsName>" + MmsName + "</MmsName>");
				str.append("<Description>" + Description + "</Description>");
				str.append("<MmsTitle></MmsTitle>");
				str.append("<ValidFrom>" + ValidFrom + "</ValidFrom>");
				str.append("<ValidTo>" + ValidTo + "</ValidTo>");
				str.append("<ResendContent></ResendContent>");
				str.append("<ValidTimes>" + ValidTimes + "</ValidTimes>");
				str.append("<ShopName>" + ShopName + "</ShopName>");
				str.append("<ShopFamilyName>" + ShopFamilyName
						+ "</ShopFamilyName>");
				str.append("<MmsFamilyName>" + MmsFamilyName
						+ "</MmsFamilyName>");
				str.append("<Advertising></Advertising>");
				str.append("<PrintNo>" + printNo + "</PrintNo>");
				str.append("<LastVerifyNumber>" + LastVerifyNumber
						+ "</LastVerifyNumber>");
				str.append("<verifyPwd>" + verifyPwd + "</verifyPwd>");
				str.append("<CreatedOn>" + CreatedOn + "</CreatedOn>");
				str.append("<CurrentDateTime>" + CurrentDateTime
						+ "</CurrentDateTime>");
				str.append("<ShopId>" + ShopId + "</ShopId>");
				str.append("</Table>");
			}
		}
		str.append("</NewDataSet>");
		return str.toString();
	}

	public String getCharcodeByNumcode(String numcode) {
		Cebbcodelibrary cebbcode = cebbcodelibraryDao.findUniqueByProperty(
				"numcode", numcode);
		String codes = cebbcode.getNumcode() + "," + cebbcode.getCharcode();
		cebbcodelibraryDao.delete(cebbcode);
		return codes;
	}

	private String returnChecklogData(List<ChecklogBo> list,
			String beginDateTime, String endDateTime) {
		StringBuffer returnChecklog = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n\n\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;统计结果");
		sb.append("\n\n统计时间");
		sb.append(beginDateTime);
		sb.append("至");
		sb.append(endDateTime);
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ChecklogBo cb = list.get(i);
				returnChecklog.append("<Table>");
				returnChecklog.append(cb.toString());
				returnChecklog.append("</Table>");
				sb.append(cb.getPrintInfo());
			}
		}
		returnChecklog.append("<PrintInfo>");
		returnChecklog.append(sb.toString());
		returnChecklog.append("</PrintInfo>");
		if (list == null || list.size() == 0) {
			returnChecklog.append("<Table>");
			returnChecklog.append("<ErrorMessage>此设备未刷过码</ErrorMessage>");
			returnChecklog.append("</Table>");
		}
		returnChecklog.append("</NewDataSet>");
		return returnChecklog.toString();
	}

	public String adminTaobaoCodeVerify(String numcode, String imei, int num)
			throws VerifyErrorException {
		return barcodeVerify(numcode, null, imei, null, itemuiid, null, num,
				Variable.CHECKMODE_ADMIN)[0];
	}

	public boolean isIstest() {
		return istest;
	}

	public void setIstest(boolean istest) {
		this.istest = istest;
		if (!istest) {
			yhturl = Variable.YHTURL;
			resserverurl = Variable.RESSERVERURL;
		} else {
			yhturl = Variable.TEST_YHTURL;
			resserverurl = Variable.TEST_RESSERVERURL;
		}
	}

	private Object checkImeiAndCode(String imei, String code) {
		Integer result = 0;
		List<Device> devicelist = deviceDao.findByProperty("devcode", imei);
		if (null == devicelist || devicelist.isEmpty()) {
			Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
			if (pt.matcher(code).matches()) {// 数字二维码
				if (code.length() == 12 && code.startsWith("0")) {
					return 1;// 可能是网页验证
				}
			}
			return 2;// 调用亿惠通
		}
		Store store = devicelist.get(0).getStore();
		if (code.length() == 14) {
			List<Eitem> eitemlist = new ArrayList<Eitem>();
			Finder finder = new Finder(
					" from Eitemstore where store.id =:storeid");
			finder.setParam("storeid", store.getId());
			finder.append(" and eitem.iszxcheck > 0 and eitem.endtime > now() ");

			List<Eitemstore> slist = eitemstoreDao.find(finder);
			if (slist != null) {
				for (Eitemstore st : slist) {
					eitemlist.add(st.getEitem());
				}
				Object[] ojes = new Object[3];
				ojes[0] = null;
				ojes[1] = store;
				ojes[2] = eitemlist;
				return ojes;
			} else {
				return 7;
			}
			/*
			 * Barcode eg = new Barcode(); Eorder eorder = new Eorder();
			 * eorder.setExtfield1(code); eg.setEorder(eorder); List<Barcode>
			 * barcodelist = barcodeDao.findByEgList(eg); if (null ==
			 * barcodelist || barcodelist.isEmpty()) { return 2;// 调用亿惠通 } else
			 * { Barcode barcode = null; for (Barcode b : barcodelist) { if
			 * (b.getCanusenum() != null && b.getCanusenum() > 0) { barcode = b;
			 * break; } } if (barcode == null) { barcode = barcodelist.get(0); }
			 * if (barcode.getEitem().getIszxcheck().intValue() !=
			 * Variable.ISZXCHECK) return 4;// 不是有效的码 List<Eitem> eitemlist =
			 * new ArrayList<Eitem>(); result = checkEitem(barcode, store,
			 * eitemlist); if (result == 0) { if
			 * ("5".equals(barcode.getEorder().getStatus())) { return 6;//
			 * ("该码已经作废"); } Object[] ojes = new Object[3]; ojes[0] = barcode;
			 * ojes[1] = store; ojes[2] = eitemlist; return ojes; } }
			 */} else {
			Barcode barcode = null;
			Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
			boolean bool = false;
			if (pt.matcher(code).matches()) {// 数字二维码
				if (code.length() == 12 && code.startsWith("0")) {
					bool = true;
					// barcode = barcodeDao.findUniqueByProperty("numcode",
					// code);
					Barcode eg = new Barcode();
					eg.setNumcode(code);
					List<Barcode> list = barcodeDao.findByEgList(eg);
					if (list != null && !list.isEmpty()) {
						if (list.size() > 1) {
							for (Barcode b : list) {
								if (b.getCanusenum() != null
										&& b.getCanusenum() > 0) {
									barcode = b;
									break;
								}
							}
							if (barcode == null) {
								barcode = list.get(0);
							}
						} else {
							barcode = list.get(0);
						}
					}
				}
			} else {// 带有英文字母的二维码
				if (code.length() == 10 && code.startsWith("E")) {
					bool = true;
					// barcode = barcodeDao.findUniqueByProperty("charcode",
					// code);
					Barcode eg = new Barcode();
					eg.setCharcode(code);
					List<Barcode> list = barcodeDao.findByEgList(eg);
					if (list != null && !list.isEmpty()) {
						if (list.size() > 1) {
							for (Barcode b : list) {
								// if (b.getCanusenum() != null
								// && b.getCanusenum() > 0) {
								// barcode = b;
								// break;
								// }
								String f1 = b.getEorder().getExtfield1();
								if (f1 != null && f1.length() == 14) {
									barcode = b;
									break;
								}
							}
							if (barcode == null) {
								barcode = list.get(0);
							}
						} else {
							barcode = list.get(0);
						}
					}
				}
			}
			if (bool) {
				if (null == barcode) {
					return 4;// 不是有效的码
				} else {
					String f1 = barcode.getEorder().getExtfield1();
					if (f1 != null && f1.length() == 14) {// 需要调用中信接口验证的码
						if (barcode.getEitem().getIszxcheck().intValue() < Variable.ISZXCHECK)
							return 4;// 不是有效的码
					}
					List<Eitem> eitemlist = new ArrayList<Eitem>();
					result = checkEitem(barcode, store, eitemlist);
					if (result == 0) {
						if ("5".equals(barcode.getEorder().getStatus())) {
							return 6;// ("该码已经作废");
						}
						Object[] ojes = new Object[3];
						ojes[0] = barcode;
						ojes[1] = store;
						ojes[2] = eitemlist;
						return ojes;

					}
				}
			} else {
				return 2;// 调用亿惠通
			}
		}

		return result;
	}

	private int checkEitem(Barcode barcode, Store store, List<Eitem> eitemlist) {
		Finder finder = null;
		Eitem eitem = barcode.getEitem();
		if (eitem.getIszxcheck() != null
				&& eitem.getIszxcheck().intValue() >= Variable.ISZXCHECK) {
			finder = new Finder(
					" from Eitemstore where eitem.outerid = :outerid and store.id = :storeid");
			finder.setParam("outerid", eitem.getOuterid());
			finder.setParam("storeid", store.getId());
		} else {
			finder = new Finder(
					" from Eitemstore where eitem.id = :eitemid and store.id = :storeid");
			finder.setParam("eitemid", eitem.getId());
			finder.setParam("storeid", store.getId());
		}
		List<Eitemstore> eitemstorelist = eitemstoreDao.find(finder);
		if (null == eitemstorelist || eitemstorelist.isEmpty()) {
			finder = new Finder(
					" from Eitemstore where eitem.eitem.id = :eitemid and store.id = :storeid");
			finder.setParam("eitemid", eitem.getId());
			finder.setParam("storeid", store.getId());
			eitemstorelist = eitemstoreDao.find(finder);
			if (null == eitemstorelist || eitemstorelist.isEmpty())
				return 3;// 该码不能在此门店使用
		}
		for (Eitemstore estore : eitemstorelist) {
			eitemlist.add(estore.getEitem());
		}

		String nowdate = DateTimeUtils.getDate("yyyy-MM-dd");
		String endtime = barcode.getEndate();
		if (null != endtime) {
			if (endtime.length() > 10) {
				endtime = endtime.substring(0, 10);
			}
			if (endtime.compareTo(nowdate) < 0) {
				return 5;// 该码已经过期
			}
		}
		return 0;
	}

	@Override
	public PaymentResponse payment(PaymentRequest paymentRequest,
			Aorder aorder, Account shopaccount) throws ApiException {
		String code = paymentRequest.getBarcode();
		String amount = paymentRequest.getAmount();
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setIs_success("F");
		Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
		User user = null;
		if (pt.matcher(code).matches()) {// 数字二维码
			user = userDao.findUniqueByProperty("numcode", code);
		} else {
			user = userDao.findUniqueByProperty("charcode", code);
		}
		if (user == null) {
			paymentResponse.setError(Variable.BARCODEUNKNOWN);
			return paymentResponse;
		}
		Account useraccount = user.getAccounts().iterator().next();
		if (useraccount.getBalance() == null
				|| useraccount.getBalance().doubleValue() == 0) {
			paymentResponse.setError(Variable.ERROR_BALANCE);
		}
		double barcodeAmount = useraccount.getBalance();
		double eorderAmount = 0;
		try {
			eorderAmount = Double.valueOf(amount);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			paymentResponse.setError(Variable.ERROR_PARAM);
			return paymentResponse;
		}
		if (eorderAmount > barcodeAmount) {
			paymentResponse.setError(Variable.ERROR_BALANCE);
			return paymentResponse;
		}
		Charge charge = chargeService.payment(useraccount, shopaccount,
				Double.valueOf(amount), "二维码支付", "二维码支付", null);
		if (charge == null) {
			paymentResponse.setError(Variable.ERROR_PAY);
			return paymentResponse;
		}
		useraccount.setBalance(barcodeAmount - eorderAmount);
		accountDao.update(useraccount);
		paymentResponse.setIs_success("T");
		Order order = new Order();
		String tid = DateTimeUtils.getDate("yyyyMMddHHmm");
		while (true) {
			String str = RandomUtil.randomStr(4);
			String alipayid = tid + str;
			Aorder aordertemp = aorderDao.findUniqueByProperty("alipayid",
					alipayid);
			tid = alipayid;
			if (aordertemp == null)
				break;

		}
		order.setAlipay_trade_no(tid);
		// 更新aorder
		aorder.setAlipayid(tid);
		aorder.setCreatetime(DateTimeUtils.getDate());
		aorder.setStatus("1");
		aorderDao.update(aorder);
		List<Order> orderlist = new ArrayList<Order>();
		orderlist.add(order);
		paymentResponse.setOrder(orderlist);
		return paymentResponse;
	}

	@Override
	public QueryResponse query(QueryRequest queryRequest) throws ApiException {
		return null;
	}

	@Override
	public RefundResponse refund(RefundRequest refundRequest)
			throws ApiException {
		// String alipayid = refundRequest.getAlipay_trade_no();
		// Aorder order = aorderDao.findUniqueByProperty("alipayid", alipayid);
		return null;
	}

	@Override
	public CloseTradeResponse closeTrade(CloseTradeRequest closeTrade)
			throws ApiException {
		return null;
	}

	@Override
	public String redVBarcode(String id, String imei, String batchno,
			String searchno, String posno, String time) {
		StringBuffer returnChecklog = new StringBuffer();
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		returnChecklog.append("<Table>");
		Device device = deviceDao.findUniqueByProperty("devcode", imei);
		if (device == null) {
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<resultInfo>撤销失败,没有找到对应的门店</resultInfo>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();

		}
		Checklog checklog = null;// checklogDao.findUniqueByProperty("printno",
									// id);
		if (batchno != null && searchno != null) {
			Checklog c = new Checklog();
			c.setSearchno(searchno);
			c.setBatchno(batchno);
			c.setImei(imei);
			c.setDevice(device);
			List<Checklog> clist = checklogDao.findByEgList(c);
			if (clist != null && !clist.isEmpty()) {
				checklog = clist.get(0);
			}
		} else if (posno != null && time != null) {
			Finder finder = new Finder("from Checklog ");
			finder.append(" where posno like :posno");
			finder.setParam("posno", posno);
			finder.append(" and substring(createtime,6,5) like :time");
			time = time.substring(0, 2) + "_" + time.substring(2, 4);
			finder.setParam("time", time);
			List<Checklog> clist = checklogDao.find(finder);
			if (clist != null && !clist.isEmpty()) {
				checklog = clist.get(0);
			}
		} else if (id != null) {
			checklog = checklogDao.findUniqueByProperty("printno", id);
		}
		if (checklog == null) {
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<resultInfo>撤销失败,没有找到对应活动</resultInfo>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();

		}

		Eitem eitem = checklog.getEitem();
		Barcode barcode = checklog.getBarcode();
		String Description = "";// 活动描述
		String ValidTo = "";// 活动有效期
		String MmsFamilyName = eitem.getEnterprise().getEntname();// 活动发起方
		if (null != eitem.getSmscontent() && !"".equals(eitem.getSmscontent())) {
			Description = eitem.getSmscontent();
		} else if (null != eitem.getCheckinfo()
				&& !"".equals(eitem.getCheckinfo())) {
			Description = eitem.getCheckinfo();
		} else if (null != eitem.getPrintstr()
				&& !"".equals(eitem.getPrintstr())) {
			Description = eitem.getPrintstr();
		} else {
			Description = eitem.getName();
		}
		if (null != barcode) {
			Eorder eorder = barcode.getEorder();
			if (eorder.getBuynick() != null) {
				Description = Description.replace("$name", eorder.getBuynick());
			} else {
				Description = Description.replace("$name", "");
			}
			Description = Description.replace("$pwd", "二维码");
			if (eorder.getExtfield1() != null) {
				Description = Description.replace("$f1", eorder.getExtfield1());
			} else {
				Description = Description.replace("$f1", "");
			}

			if (eorder.getExtfield2() != null) {
				Description = Description.replace("$f2", eorder.getExtfield2());
			} else {
				Description = Description.replace("$f2", "");
			}
			if (eorder.getExtfield3() != null) {
				Description = Description.replace("$f3", eorder.getExtfield3());
			} else {
				Description = Description.replace("$f3", "");
			}
			ValidTo = barcode.getEndate();
			if (ValidTo != null && ValidTo.length() > 10)
				ValidTo = ValidTo.substring(0, 10);
			
			barcode.setCanusenum(barcode.getCanusenum() + checklog.getChecknum());
			barcodeDao.update(barcode);
			eorder.setOvernum(eorder.getOvernum() - checklog.getChecknum());
			eorderDao.update(eorder);
		}else {
			Payment payment = paymentDao.findUniqueByProperty("orderid", checklog.getPrintno());
			if (payment != null) {
				String rest = refZXBarcode(payment);
				if (rest.startsWith("error:")) {
					returnChecklog.append("<isSuccess>false</isSuccess>");
					returnChecklog.append("<resultInfo>" + rest + "</resultInfo>");
					returnChecklog.append("</Table>");
					returnChecklog.append("</NewDataSet>");
					return returnChecklog.toString();
				}
			}
		}

		returnChecklog.append("<checknum>" + checklog.getChecknum()
				+ "</checknum>");
		returnChecklog.append("<description>" + Description + "</description>");
		returnChecklog.append("<eitemname>" + eitem.getName() + "</eitemname>");
		returnChecklog.append("<mmsfamilyname>" + MmsFamilyName
				+ "</mmsfamilyname>");
		returnChecklog.append("<validto>" + ValidTo + "</validto>");
		if (eitem.getIszxcheck() == 0)
			returnChecklog.append("<eitemid>" + eitem.getId() + "</eitemid>");
		else
			returnChecklog.append("<eitemid>" + eitem.getProductcode()
					+ "</eitemid>");
		checklogDao.delete(checklog);
		returnChecklog.append("<isSuccess>true</isSuccess>");
		returnChecklog.append("</Table>");
		returnChecklog.append("</NewDataSet>");
		return returnChecklog.toString();
	}

	public void redVerifyBarcode(String charcode) {
		Checklog eg = new Checklog();
		eg.setCharcode(charcode);
		eg.setDes("验证成功");
		eg.setImei("0000000000000");
		eg.setCheckmode(0);
		eg.setChecktype("0");
		List<Checklog> checklogList = checklogDao.findByEgList(eg);
		Checklog checklog = checklogList.get(0);
		if (checklog != null) {
			checklogDao.delete(checklog);
		}
		Barcode barcode = checklog.getBarcode();
		barcode.setCanusenum(barcode.getCanusenum() + checklog.getChecknum());
		barcodeDao.update(barcode);
		Eorder eorder = barcode.getEorder();
		eorder.setOvernum(eorder.getOvernum() - checklog.getChecknum());
		eorderDao.update(eorder);
	}

	@Override
	public String barcodepayment(String imei, String code, String oid,
			String amount, String storeid) {

		return barcodepayment(imei, code, oid, amount, storeid, null, null);
	}

	private String getOverMssages(Aorder order, String eitemname) {
		String shopno = String.format(
				"%012d",
				Long.valueOf(order.getShop().getId() + ""
						+ order.getStore().getId()));
		String batchno = String.format("%06d", order.getId());
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<Table>");
		sbstr.append("<success>true</success>");
		sbstr.append("<error></error>");
		sbstr.append("<printTitle>二维码储值卡签购单</printTitle>");
		sbstr.append("<shopName>" + order.getShop().getName() + "</shopName>");
		sbstr.append("<shopNo>" + shopno + "</shopNo>");
		sbstr.append("<storeName>" + order.getStore().getName()
				+ "</storeName>");
		sbstr.append("<imei>" + order.getImei() + "</imei>");
		sbstr.append("<cardType>储值卡</cardType>");
		sbstr.append("<endtime>" + order.getBarcode().getEndate()
				+ "</endtime>");
		sbstr.append("<tranType>消费</tranType>");
		sbstr.append("<batchno>" + batchno + "</batchno>");
		sbstr.append("<serialnum>" + order.getSerialnum() + "</serialnum>");
		sbstr.append("<amont>" + order.getPayment() + "</amont>");
		sbstr.append("<times>" + order.getCreatetime() + "</times>");
		sbstr.append("<adminNo>001</adminNo>");
		sbstr.append("<remark>" + eitemname + "</remark>");
		sbstr.append("<printmessage>");
		sbstr.append("二维码储值卡签购单\n\n\n\n商户名称:");
		sbstr.append(order.getShop().getName());
		sbstr.append("\n\n商户号:");
		sbstr.append(shopno);
		sbstr.append("\n\n门店名称:");
		sbstr.append(order.getStore().getName());
		sbstr.append("\n\n终端号:");
		sbstr.append(order.getImei());
		sbstr.append("\n\n卡类别:储值卡");
		sbstr.append("\n\n有效期:");
		sbstr.append(order.getBarcode().getEndate());
		sbstr.append("\n\n交易类型:消费");
		sbstr.append("\n\n批次号:");
		sbstr.append(batchno);
		sbstr.append("\n\n订单号:");
		sbstr.append(order.getSerialnum());
		sbstr.append("\n\n订单号:");
		sbstr.append(order.getAlipayid());
		sbstr.append("\n\n金额:");
		sbstr.append(order.getPayment());
		sbstr.append("\n\n日期时间:");
		sbstr.append(order.getCreatetime());
		sbstr.append("\n\n操作员:001");
		sbstr.append("\n\n备注:");
		sbstr.append("</printmessage>");
		sbstr.append("<orderid>");
		sbstr.append(order.getAlipayid());
		sbstr.append("</orderid>");
		sbstr.append("<posno>");
		sbstr.append(order.getPosno());
		sbstr.append("</posno>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();
	}

	private String getBarcodeErrorMessage(String bool, String errorMessage) {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<Table>");
		sbstr.append("<success>");
		sbstr.append(bool);
		sbstr.append("</success>");
		sbstr.append("<error>");
		sbstr.append(errorMessage);
		sbstr.append("</error>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();

	}

	private String getErrorMessage(String bool, String errorMessage) {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<Table>");
		sbstr.append("<isSuccess>");
		sbstr.append(bool);
		sbstr.append("</isSuccess>");
		sbstr.append("<error>");
		sbstr.append(errorMessage);
		sbstr.append("</error>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();

	}

	private String getAorderStatusStrByRequestid(String requestid,
			String eitemname) {
		StringBuffer sbstr = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				sbstr.append(getErrorMessage("false", "交易失败,系统错误,请重试"));
				return sbstr.toString();
			}
			Aorder aorder = aorderDao.findUniqueByProperty("requestid",
					requestid);
			if (aorder.getStatus().equals("1")) {
				sbstr.append(getOverMssages(aorder, eitemname));
				return sbstr.toString();
			} else if (aorder.getStatus().equals("2")) {
				sbstr.append(getErrorMessage("false", "交易失败,该订单已退款"));
				return sbstr.toString();
			} else if (aorder.getStatus().equals("3")) {
				sbstr.append(getErrorMessage("false", aorder.getErrormessage()));
				return sbstr.toString();
			}
		}
		sbstr.append(returnTrading());
		return sbstr.toString();
	}

	private String returnTrading() {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<Table>");
		sbstr.append("<trading>false</trading>");
		sbstr.append("<error>");
		sbstr.append("</error>");
		sbstr.append("<printmessage>");
		sbstr.append("</printmessage>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();
	}

	private String retstr(String str) {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append(str);
		return sbstr.toString();
	}

	private String retErrorMsg(String errorCode, String errorMsg) {
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

	private String refAorder(String orderid, Account shopaccount) {
		StringBuffer returnChecklog = new StringBuffer();
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		returnChecklog.append("<Table>");
		if (shopaccount == null) {
			return retstr(retErrorMsg("3", "退款失败,订单号有误"));
		}
		Aorder order = aorderDao.findUniqueByProperty("alipayid", orderid);
		if (order != null) {
			if ("2".equals(order.getStatus())) {
				return retstr(retErrorMsg("2", "撤销失败,没有找到对应的门店"));
			} else if (!"1".equals(order.getStatus())) {
				return retstr(retErrorMsg("1", "款失败,该订单未支付"));
			}
		} else {
			return retstr(retErrorMsg("3", "退款失败,订单号有误"));
		}
		
		order.setRefundtime(DateTimeUtils.getDate());
		order.setStatus("2");
		aorderDao.update(order);
		Aflowlog flow = new Aflowlog();
		flow.setAlipayid(order.getAlipayid());
		flow.setStore(order.getStore());
		flow.setCreatetime(DateTimeUtils.getDate());
		flow.setDes("管理员操作退款");
		flow.setNumcode(order.getCode());
		flow.setPaymoney(order.getPayment());
		aorderDao.saveOrUpdate(flow);
		/* -手机退款更新barcode余额 开始- */
		Barcode barcode = order.getBarcode();
		if (barcode != null) {
			barcode.setAmount(barcode.getAmount() + order.getPayment());
			barcodeDao.update(barcode);
		}

		/* -手机退款更新barcode余额 结束- */
		User user = order.getUser();
		Account useraccount = null;
		if (user != null) {
			useraccount = user.getAccounts().iterator().next();
			chargeService.payment(shopaccount, useraccount, order.getPayment(),
					barcode.getEitem().getName(),
					"本次退款方为二维码储值卡：" + barcode.getNumcode(), null);
		}

		if (useraccount == null) {
			Charge charge = new Charge();
			charge.setStatus(1);
			charge.setAmount(order.getPayment());
			charge.setAccount(shopaccount);
			charge.setName(barcode.getEitem().getName());
			charge.setDescr("本次退款方为二维码储值卡：" + barcode.getNumcode());
			charge.setType(1);
			charge.setSource("二维码储值卡：" + barcode.getNumcode());
			charge.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			DecimalFormat df = new DecimalFormat("0000");
			String tid = DateTimeUtils.getDate("yyyyMMddHHmmSSS");
			Random rd = new Random();
			String increase = df.format(rd.nextInt(9999));
			tid = tid + increase;
			charge.setTid(tid);
			charge.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			double amount = order.getPayment();
			charge.setBalance((shopaccount.getBalance() == null ? 0
					: shopaccount.getBalance()) - amount);
			shopaccount.setBalance((shopaccount.getBalance() == null ? 0
					: shopaccount.getBalance()) - amount);
			shopaccount
					.setTotalmoneyout((shopaccount.getTotalmoneyout() == null ? 0
							: shopaccount.getTotalmoneyout())
							+ amount);
			accountDao.update(shopaccount);
			chargedao.save(charge);
		}

		/********** 发送通知短信 ************/
		Eitem eitem = barcode.getEitem();
		Shop shop = shopaccount.getShop();
		String content = eitem.getRefundSms();
		if (content != null && !"".equals(content)) {
			Enterprise enterprise = eitem.getEnterprise();
			content = content
					.replace("$expense", order.getPayment().toString());
			content = content.replace("$balance", barcode.getAmount()
					.toString());
			content = content.replace("$shopName", shop.getName());
			try {
				int smscount = (int) Math.ceil(content.length() / 67.0);
				if (enterprise.getSmsbalance() != null
						&& enterprise.getSmsbalance() > 0
						&& enterprise.getSmsbalance() - smscount >= 0) {
					enterprise.setSmsbalance(enterprise.getSmsbalance()
							- smscount);
					content = content.replace("~", "^");
					content = barcode.getEorder().getPhone() + "~" + content
							+ "~" + enterprise.getId() + "~6";
					log.debug("==content==" + content);
					messageSender.simpleSend(content);
					enterpriseDao.update(enterprise);
				}
			} catch (Exception es) {
				es.printStackTrace();
			}
		}
		
		/*
		 * 02 有效期 03 交易类型 04 备注
		 */
		String ValidTo = barcode.getEndate();
		if (ValidTo != null && ValidTo.length() > 10)
			ValidTo = ValidTo.substring(0, 10);
		returnChecklog.append("<validto>" + ValidTo + "</validto>");
		returnChecklog.append("<paymenttype>1</paymenttype>");
		returnChecklog.append("<eitemname>" + eitem.getName() + "</eitemname>");
		returnChecklog.append("<descr>" + "本次退款方为二维码储值卡："
				+ barcode.getNumcode() + "</descr>");
		returnChecklog.append("<isSuccess>true</isSuccess>");
		returnChecklog.append("<eitemid>" + eitem.getId() + "</eitemid>");
		returnChecklog.append("<point>" + order.getPayment() + "</point>");
		returnChecklog.append("</Table>");
		returnChecklog.append("</NewDataSet>");
		return returnChecklog.toString();
	}

	public String redVerifyPayment(String orderid, String batchno,
			String searchno, String posno, String time) {
		Aorder aorder = null;
		if (batchno != null && searchno != null) {
			Aorder ao = new Aorder();
			ao.setSearchno(searchno);
			ao.setBatchno(batchno);
			List<Aorder> alist = aorderDao.findByEgList(ao);
			if (alist != null && !alist.isEmpty()) {
				aorder = alist.get(0);
			}
		} else if (posno != null && time != null) {
			Finder finder = new Finder("from Aorder ");
			finder.append(" where posno like :posno");
			finder.setParam("posno", posno);
			finder.append(" and substring(createtime,6,5) like :time");
			time = time.substring(0, 2) + "_" + time.substring(2, 4);
			finder.setParam("time", time);
			List<Aorder> alist = aorderDao.find(finder);
			if (alist != null && !alist.isEmpty()) {
				aorder = alist.get(0);
			}
		} else if (orderid != null) {
			aorder = aorderDao.get(orderid);
		}
		if(aorder == null)
			return refAorder(orderid, null);
		orderid = aorder.getAlipayid();
		Set<Account> accounts = aorder.getShop().getAccounts();
		if (accounts == null || accounts.size() == 0) {
			return refAorder(orderid, null);
		}
		Account account = accounts.iterator().next();
		return refAorder(orderid, account);
	}

	@Override
	public String refundpay(String orderid, Account shopaccount) {
		Aorder order = aorderDao.findUniqueByProperty("alipayid", orderid);
		if (order != null) {
			if ("2".equals(order.getStatus())) {
				return "退款失败,该订单已经退款";
			} else if (!"1".equals(order.getStatus())) {
				return "退款失败,该订单未支付";
			}
		} else {
			return "退款失败,订单号有误";
		}

		order.setRefundtime(DateTimeUtils.getDate());
		order.setStatus("2");
		aorderDao.update(order);
		Aflowlog flow = new Aflowlog();
		flow.setAlipayid(order.getAlipayid());
		flow.setStore(order.getStore());
		flow.setCreatetime(DateTimeUtils.getDate());
		flow.setDes("管理员操作退款");
		flow.setNumcode(order.getCode());
		flow.setPaymoney(order.getPayment());
		aorderDao.saveOrUpdate(flow);
		/* -手机退款更新barcode余额 开始- */
		Barcode barcode = order.getBarcode();
		if (barcode != null) {
			barcode.setAmount(barcode.getAmount() + order.getPayment());
			barcodeDao.update(barcode);
		}

		/* -手机退款更新barcode余额 结束- */
		User user = order.getUser();
		Account useraccount = null;
		if (user != null) {
			useraccount = user.getAccounts().iterator().next();
			chargeService.payment(shopaccount, useraccount, order.getPayment(),
					barcode.getEitem().getName(),
					"本次退款方为二维码储值卡：" + barcode.getNumcode(), null);
		}
		if (useraccount == null) {
			Charge charge = new Charge();
			charge.setStatus(1);
			charge.setAmount(order.getPayment());
			charge.setAccount(shopaccount);
			charge.setName(barcode.getEitem().getName());
			charge.setDescr("本次退款方为二维码储值卡：" + barcode.getNumcode());
			charge.setType(1);
			charge.setSource("二维码储值卡：" + barcode.getNumcode());
			charge.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			DecimalFormat df = new DecimalFormat("0000");
			String tid = DateTimeUtils.getDate("yyyyMMddHHmmSSS");
			Random rd = new Random();
			String increase = df.format(rd.nextInt(9999));
			tid = tid + increase;
			charge.setTid(tid);
			charge.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			double amount = order.getPayment();
			charge.setBalance((shopaccount.getBalance() == null ? 0
					: shopaccount.getBalance()) - amount);
			shopaccount.setBalance((shopaccount.getBalance() == null ? 0
					: shopaccount.getBalance()) - amount);
			shopaccount
					.setTotalmoneyout((shopaccount.getTotalmoneyout() == null ? 0
							: shopaccount.getTotalmoneyout())
							+ amount);
			accountDao.update(shopaccount);
			chargedao.save(charge);
		}

		/********** 发送通知短信 ************/
		Eitem eitem = barcode.getEitem();
		Shop shop = shopaccount.getShop();
		String content = eitem.getRefundSms();
		if (content != null && !"".equals(content)) {
			Enterprise enterprise = eitem.getEnterprise();
			content = content
					.replace("$expense", order.getPayment().toString());
			content = content.replace("$balance", barcode.getAmount()
					.toString());
			content = content.replace("$shopName", shop.getName());
			try {
				int smscount = (int) Math.ceil(content.length() / 67.0);
				if (enterprise.getSmsbalance() != null
						&& enterprise.getSmsbalance() > 0
						&& enterprise.getSmsbalance() - smscount >= 0) {
					enterprise.setSmsbalance(enterprise.getSmsbalance()
							- smscount);
					content = content.replace("~", "^");
					content = barcode.getEorder().getPhone() + "~" + content
							+ "~" + enterprise.getId() + "~6";
					log.debug("==content==" + content);
					messageSender.simpleSend(content);
					enterpriseDao.update(enterprise);
				}
			} catch (Exception es) {
				es.printStackTrace();
			}
		}
		return "退款成功";
	}

	@Override
	public String makeupprint(String numcode, String datetime, String imei) {
		List<Device> devicelist = deviceDao.findByProperty("devcode", imei);

		StringBuffer returnChecklog = new StringBuffer();
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		returnChecklog.append("<Table>");
		if (null == devicelist || devicelist.isEmpty()) {
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<resultInfo>不是合法的机具</resultInfo>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();
		}
		Barcode barcode = barcodeDao.findUniqueByProperty("numcode", numcode);
		Device dev = devicelist.get(0);
		Store store = dev.getStore();
		Checklog ck = new Checklog();
		Shop shop = dev.getShop();
		ck.setStore(store);
		ck.setBarcode(barcode);
		ck.setChecktype("0");
		List<Checklog> cklist = checklogDao.findByEgList(ck);
		Eitem eitem = ck.getEitem();
		for (Checklog cl : cklist) {
			String Description = "";
			if (null != eitem.getSmscontent()
					&& !"".equals(eitem.getSmscontent())) {
				Description = eitem.getSmscontent();
			} else if (null != eitem.getCheckinfo()
					&& !"".equals(eitem.getCheckinfo())) {
				Description = eitem.getCheckinfo();
			} else if (null != eitem.getPrintstr()
					&& !"".equals(eitem.getPrintstr())) {
				Description = eitem.getPrintstr();
			} else {
				Description = eitem.getName();
			}

			if (null != barcode) {
				Eorder eorder = barcode.getEorder();
				if (eorder.getBuynick() != null) {
					Description = Description.replace("$name",
							eorder.getBuynick());
				} else {
					Description = Description.replace("$name", "");
				}
				Description = Description.replace("$pwd", "二维码");
				if (eorder.getExtfield1() != null) {
					Description = Description.replace("$f1",
							eorder.getExtfield1());
				} else {
					Description = Description.replace("$f1", "");
				}

				if (eorder.getExtfield2() != null) {
					Description = Description.replace("$f2",
							eorder.getExtfield2());
				} else {
					Description = Description.replace("$f2", "");
				}
				if (eorder.getExtfield3() != null) {
					Description = Description.replace("$f3",
							eorder.getExtfield3());
				} else {
					Description = Description.replace("$f3", "");
				}

				if (eitem == null)
					eitem = cl.getEitem();
				returnChecklog.append("<checktime>" + cl.getCreatetime()
						+ "</checktime>");
				returnChecklog.append("<PrintInfo>");
				returnChecklog.append("\n\n");
				if (eitem.getPrintTitle() != null
						|| !"".equals(eitem.getPrintTitle()))
					returnChecklog.append("汇金通消费单");
				else
					returnChecklog.append(eitem.getPrintTitle());
				returnChecklog.append("\n\n");
				returnChecklog.append("商户名称:");
				returnChecklog.append(shop.getName());
				returnChecklog.append("\n商户号:");
				String batchno = String.format(
						"%012d",
						Long.valueOf(store.getShop().getId() + ""
								+ store.getId()));
				returnChecklog.append(batchno);
				returnChecklog.append("\n门店名称:");
				returnChecklog.append(shop.getName());
				returnChecklog.append("\n活动发起方:");
				returnChecklog.append(eitem.getEnterprise().getEntname());// entname
				returnChecklog.append("\n终端号:");
				returnChecklog.append(imei);
				returnChecklog.append("\n活动名称:");
				returnChecklog.append(eitem.getName());
				returnChecklog.append("\n活动描述:");
				returnChecklog.append(Description);
				returnChecklog.append("\n有效期:");
				returnChecklog.append(barcode.getEndate());
				returnChecklog.append("\n本次使用数:");
				returnChecklog.append(cl.getChecknum());
				returnChecklog.append("\n交易类型:权益兑换");
				returnChecklog.append("\n批次号:");// tid
				if (barcode != null) {
					returnChecklog
							.append(String.format("%9d", barcode.getId()));
				} else {
					returnChecklog.append(String.format("%9d", eitem.getId()));
				}
				returnChecklog.append("\n订单号:");
				returnChecklog.append(cl.getPrintno());
				returnChecklog.append("\n日期时间:");
				returnChecklog.append(cl.getCreatetime());
				returnChecklog.append("\n操作员:001");
				returnChecklog.append("</PrintInfo>");
			}
		}
		returnChecklog.append("</Table>");
		returnChecklog.append("</NewDataSet>");
		return returnChecklog.toString();
	}

	@Override
	public String getEitemListByImei(String imei, String eitemids) {
		StringBuffer returnValidata = new StringBuffer();
		returnValidata.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnValidata.append("<NewDataSet>");
//		List<Device> devicelist = deviceDao.findByProperty("devcode", imei);
//		if (null == devicelist || devicelist.isEmpty()) {
//			returnValidata.append("<Table>");
//			returnValidata.append("<isSuccess>false</isSuccess>");
//			returnValidata.append("<resultInfo>不是合法的机具</resultInfo>");
//			returnValidata.append("</Table>");
//			returnValidata.append("</NewDataSet>");
//			return returnValidata.toString();
//		}
//		Store store = devicelist.get(0).getStore();
//		String endtime = DateTimeUtils.getDate("yyyy-MM-dd");
//		endtime = endtime + " 23:59:59";
//		Finder finder = new Finder(
//				" from Eitemstore where store.id=:storeid and ((eitem.eitem is not null and eitem.endtime = '') or eitem.endtime is null or eitem.endtime>=:endtime) and eitem.bank is null and ( eitem.iszxcheck >0 or eitem.ispayment = 1) order by eitem.ispayment ");
//		finder.setParam("storeid", store.getId());
//		finder.setParam("endtime", endtime);
//		List<Eitemstore> eslist = eitemstoreDao.find(finder);
//		if (eslist == null || eslist.size() == 0) {
//			returnValidata.append("<Table>");
//			returnValidata.append("<isSuccess>false</isSuccess>");
//			returnValidata.append("<resultInfo>没有兑换的活动</resultInfo>");
//			returnValidata.append("</Table>");
//			returnValidata.append("</NewDataSet>");
//			return returnValidata.toString();
//		}
		List<Eitemstore> eslist = new ArrayList<Eitemstore>();
		Eitemstore store = new Eitemstore();
		Eitem et1 = new Eitem();
		et1.setName("招商银行 周三5折活动日验码验码验码");
		et1.setId(1l);
		et1.setIspayment(0);
		et1.setIszxcheck(0);
		Enterprise ent = new Enterprise();
		ent.setEntname("招商银行");
		et1.setEnterprise(ent);
		et1.setCodetimes(1);
		store.setEitem(et1);
		eslist.add(store);
		for (Eitemstore estore : eslist) {
			Eitem et = estore.getEitem();
			returnValidata.append("<Table>");
			returnValidata.append("<eitemname>");
			returnValidata.append(et.getName());
			returnValidata.append("</eitemname>");
			returnValidata.append("<eitemid>");
			returnValidata.append(et.getId());
			returnValidata.append("</eitemid>");
			returnValidata.append("<ispayment>");
			returnValidata.append(et.getIspayment());
			returnValidata.append("</ispayment>");
			returnValidata.append("<iszxcheck>");
			returnValidata.append(et.getIszxcheck());
			returnValidata.append("</iszxcheck>");
			returnValidata.append("<codetimes>");
			returnValidata.append(et.getCodetimes());
			returnValidata.append("</codetimes>");
			returnValidata.append("<entname>");
			returnValidata.append(et.getEnterprise().getEntname());
			returnValidata.append("</entname>");
			returnValidata.append("</Table>");
		}
		/*
		 * if(eitemids!=null && !"".equals(eitemids)){ String[] ids =
		 * eitemids.split(","); if(ids.length == elist.size()){ int len = 0;
		 * for(String id:ids){ for(Eitem et:elist){
		 * if(et.getId().intValue()==Long.valueOf(id).intValue()){ len++; break;
		 * } } } if(len ==ids.length){ returnValidata.append("<Table>");
		 * returnValidata.append("<isSuccess>false</isSuccess>");
		 * returnValidata.append("</Table>");
		 * returnValidata.append("</NewDataSet>"); return
		 * returnValidata.toString(); } } }
		 */
		returnValidata.append("</NewDataSet>");
		return returnValidata.toString();

	}

	@Override
	public String barcodeVerify(String imei, String charcode, String eitemid,
			String usernum) {
		return barcodeVerify(imei, charcode, eitemid, usernum, null, null);

	}

	private String returnCheckStr(Store store, Barcode barcode, String imei,
			Eitem eitem, String verifyPwd, int usednum, String result,
			String printNo, String ctime, String type, String posno) {
		// 截取设备ID的后4位
		StringBuffer str = new StringBuffer();
		// String printNo = "";// 打印流水号
		String MmsId = "";// 活动ID
		String MastMmsId = "";// 主活动ID
		String MmsName = "";// 活动名称
		// *******************************************************************
		// 开始生成打印号printNo
		// *******************************************************************
		// printNo = subImei + "" + currDate + "" + increase;
		// 结束生成打印号printNo
		// *******************************************************************
		String Description = "";// 活动描述
		String ValidTo = "";// 活动结束日期
		long ValidTimes = 1;
		int LastVerifyNumber = 1;
		String CreatedOn = "";// 创建时间
		if (barcode != null) {
			CreatedOn = barcode.getCreattime();
			ValidTimes = barcode.getCanusenum();
			LastVerifyNumber = barcode.getCanusenum().intValue();// 剩余次数
			verifyPwd = barcode.getNumcode();
		}
		String ShopName = "";// 商户名称
		String ShopFamilyName = "";// 商家名称
		String MmsFamilyName = "";// 活动发起方
		String ShopId = "";// 门店ID
		MmsId = eitem.getId().toString();
		MastMmsId = MmsId;
		MmsName = eitem.getName();
		Description = "";
		if (null != eitem.getSmscontent() && !"".equals(eitem.getSmscontent())) {
			Description = eitem.getSmscontent();
		} else if (null != eitem.getCheckinfo()
				&& !"".equals(eitem.getCheckinfo())) {
			Description = eitem.getCheckinfo();
		} else if (null != eitem.getPrintstr()
				&& !"".equals(eitem.getPrintstr())) {
			Description = eitem.getPrintstr();
		} else {
			Description = eitem.getName();
		}
		String entname = eitem.getEnterprise().getEntname();
		String printstr = "";
		if (null != barcode) {
			Eorder eorder = barcode.getEorder();
			if (eorder.getBuynick() != null) {
				Description = Description.replace("$name", eorder.getBuynick());
			} else {
				Description = Description.replace("$name", "");
			}
			Description = Description.replace("$pwd", "二维码");
			if (eorder.getExtfield1() != null) {
				Description = Description.replace("$f1", eorder.getExtfield1());
			} else {
				Description = Description.replace("$f1", "");
			}

			if (eorder.getExtfield2() != null) {
				Description = Description.replace("$f2", eorder.getExtfield2());
			} else {
				Description = Description.replace("$f2", "");
			}
			if (eorder.getExtfield3() != null) {
				Description = Description.replace("$f3", eorder.getExtfield3());
			} else {
				Description = Description.replace("$f3", "");
			}
			ValidTo = barcode.getEndate();
			if (ValidTo != null && ValidTo.length() > 10)
				ValidTo = ValidTo.substring(0, 10);
			if (eitem.getPrintstr() != null && !"".equals(eitem.getPrintstr())) {

				printstr = eitem.getPrintstr();
				if (null != eorder.getBuynick()
						&& !"".equals(eorder.getBuynick()))
					printstr = printstr.replace("$name", eorder.getBuynick());
				if (null != barcode.getNumcode()
						&& "".equals(barcode.getNumcode()))
					printstr = printstr.replace("$pwd", barcode.getNumcode());
				if (null != eorder.getExtfield1()
						&& !"".equals(eorder.getExtfield1()))
					printstr = printstr.replace("$f1", eorder.getExtfield1());
				if (null != eorder.getExtfield2()
						&& !"".equals(eorder.getExtfield2()))
					printstr = printstr.replace("$f2", eorder.getExtfield2());
				if (null != eorder.getExtfield3()
						&& !"".equals(eorder.getExtfield3()))
					printstr = printstr.replace("$f3", eorder.getExtfield3());
			}
		} else {
			Description = Description.replace("$name", "");
			Description = Description.replace("$pwd", "二维码");
			Description = Description.replace("$f1", "");
			ValidTo = eitem.getEndtime();
			if (ValidTo != null && ValidTo.length() > 10)
				ValidTo = ValidTo.substring(0, 10);
			if (eitem.getPrintstr() != null && !"".equals(eitem.getPrintstr())) {
				printstr = eitem.getPrintstr();
				printstr = printstr.replace("$pwd", verifyPwd);
				MmsFamilyName = MmsFamilyName + "\n" + printstr;
			}
		}
		ShopName = store.getName();
		ShopFamilyName = store.getShop().getName();
		MmsFamilyName = ShopFamilyName;
		ShopId = store.getShop().getUuid();
		str.append("<Table>");
		str.append("<isSuccess>true</isSuccess>");
		str.append("<IsNeedConfirm>false</IsNeedConfirm>");
		str.append("<ConfirmNumber></ConfirmNumber>");
		str.append("<PwdInfoId></PwdInfoId>");
		str.append("<MmsId>" + MmsId + "</MmsId>");
		str.append("<MastMmsId>" + MastMmsId + "</MastMmsId>");
		str.append("<MmsName>" + MmsName + "</MmsName>");
		str.append("<Description>" + Description + "</Description>");
		str.append("<MmsTitle></MmsTitle>");
		str.append("<ValidFrom></ValidFrom>");
		str.append("<ValidTo>" + ValidTo + "</ValidTo>");
		str.append("<ResendContent></ResendContent>");
		str.append("<ValidTimes>" + ValidTimes + "</ValidTimes>");
		str.append("<ShopName>" + ShopName + "</ShopName>");
		str.append("<ShopFamilyName>" + ShopFamilyName + "</ShopFamilyName>");
		str.append("<MmsFamilyName>" + MmsFamilyName + "</MmsFamilyName>");
		str.append("<Advertising></Advertising>");
		str.append("<PrintNo>" + printNo + "</PrintNo>");
		str.append("<LastVerifyNumber>" + LastVerifyNumber
				+ "</LastVerifyNumber>");
		str.append("<verifyPwd>" + verifyPwd + "</verifyPwd>");
		str.append("<CreatedOn>" + CreatedOn + "</CreatedOn>");
		str.append("<CurrentDateTime>" + ctime + "</CurrentDateTime>");
		str.append("<ShopId>" + ShopId + "</ShopId>");
		str.append("<Entname>" + entname + "</Entname>");
		str.append("<Printstr>" + printstr + "</Printstr>");
		str.append("<PrintTitle>");
		if (eitem.getPrintTitle() != null || !"".equals(eitem.getPrintTitle()))
			str.append("汇金通消费单");
		else
			str.append(eitem.getPrintTitle());
		str.append("</PrintTitle>");

		str.append("<ShopNo>");
		String batchno = String.format("%012d",
				Long.valueOf(store.getShop().getId() + "" + store.getId()));
		str.append(batchno);
		str.append("</ShopNo>");
		str.append("<TranType>" + type);
		str.append("</TranType>");
		str.append("<BacthNo>");
		if (barcode != null) {
			str.append(String.format("%09d", barcode.getId()));
		} else {
			str.append(String.format("%09d", eitem.getId()));
		}
		str.append("</BacthNo>");
		str.append("<AdminNo>001");
		str.append("</AdminNo>");
		str.append("<PrintInfo>");
		str.append("</PrintInfo>");
		str.append("<Result>");
		str.append(result);
		str.append("</Result>");
		str.append("<Canusenum>" + usednum + "</Canusenum>");
		str.append("<posno>" + posno + "</posno>");
		str.append("</Table>");
		return str.toString();
	}

	private String newTestDeviceBind(String imei, String numcode) {
		String storename = "设备尚未初始化";
		String shopname = "设备尚未初始化";
		String schql = " from Device where  devcode=:imei";
		Finder devfinder = new Finder(schql);
		devfinder.setParam("imei", imei);
		List<Device> sclist = deviceDao.find(devfinder);
		String CurrentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());// 当前时间
		if (sclist != null && sclist.size() > 0) {
			shopname = sclist.get(0).getShop().getName();
			storename = sclist.get(0).getStore().getName();
		}
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append("<Table>");
		sbstr.append("<IsNeedConfirm>false</IsNeedConfirm>");
		sbstr.append("<ConfirmNumber></ConfirmNumber>");
		sbstr.append("<PwdInfoId></PwdInfoId>");
		sbstr.append("<MmsId>" + itemuiid + "</MmsId>");
		sbstr.append("<MastMmsId>" + itemuiid + "</MastMmsId>");
		sbstr.append("<MmsName>设备绑定测试</MmsName>");
		sbstr.append("<Description>设备绑定测试</Description>");
		sbstr.append("<MmsTitle></MmsTitle>");
		sbstr.append("<ValidFrom></ValidFrom>");
		sbstr.append("<ValidTo>2020-12-31</ValidTo>");
		sbstr.append("<ResendContent></ResendContent>");
		sbstr.append("<ValidTimes>99999999</ValidTimes>");
		sbstr.append("<ShopName>" + storename + "</ShopName>");
		sbstr.append("<ShopFamilyName>" + shopname + "</ShopFamilyName>");
		sbstr.append("<MmsFamilyName>" + shopname + "</MmsFamilyName>");
		sbstr.append("<Advertising></Advertising>");
		sbstr.append("<PrintNo>0000000000000000000</PrintNo>");
		sbstr.append("<LastVerifyNumber>999999999</LastVerifyNumber>");
		sbstr.append("<verifyPwd>" + testcode + "</verifyPwd>");
		sbstr.append("<CreatedOn>" + CurrentDateTime + "</CreatedOn>");
		sbstr.append("<CurrentDateTime>" + CurrentDateTime
				+ "</CurrentDateTime>");
		sbstr.append("<ShopId>" + devicebindtest + "</ShopId>");
		sbstr.append("<Entname>设备绑定测试 </Entname>");
		sbstr.append("<Printstr></Printstr>");
		sbstr.append("<PrintTitle>汇金通消费单");
		sbstr.append("</PrintTitle>");

		sbstr.append("<ShopNo>000000000000");
		sbstr.append("</ShopNo>");
		sbstr.append("<TranType>权益兑换");
		sbstr.append("</TranType>");
		sbstr.append("<BacthNo>000000000");
		sbstr.append("</BacthNo>");
		sbstr.append("<AdminNo>001");
		sbstr.append("</AdminNo>");
		sbstr.append("<PrintInfo>");
		sbstr.append("</PrintInfo>");
		sbstr.append("<Result>验证成功");
		sbstr.append("</Result>");
		sbstr.append("<Canusenum>0</Canusenum>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();
	}

	@Override
	public String getEitemBarcodeByImei(String imei) {
		StringBuffer returnValidata = new StringBuffer();
		returnValidata
				.append("<?xml version='1.0' encoding='UTF-8'?><ArrayOfCoupon xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		List<Device> devicelist = deviceDao.findByProperty("devcode", imei);
		if (null == devicelist || devicelist.isEmpty()) {
			returnValidata.append("<Coupon>");
			returnValidata.append("</Coupon>");
			return returnValidata.toString();
		}
		Store store = devicelist.get(0).getStore();
		String endtime = DateTimeUtils.getDate("yyyy-MM-dd");
		endtime = endtime + " 23:59:59";
		Finder finder = new Finder(
				" from Eitemstore where store.id=:storeid and (eitem.endtime = '' or eitem.endtime is null or eitem.endtime>=:endtime) and eitem.bank is null  order by eitem.ispayment ");
		finder.setParam("storeid", store.getId());
		finder.setParam("endtime", endtime);
		List<Eitemstore> eslist = eitemstoreDao.find(finder);
		if (eslist == null || eslist.size() == 0) {
			returnValidata.append("<Coupon>");
			returnValidata.append("</Coupon>");
			return returnValidata.toString();
		}
		for (Eitemstore estore : eslist) {
			returnValidata.append("<Coupon>");
			Eitem eitem = estore.getEitem();
			List<Barcode> codelist = barcodeDao.findByProperty("eitem.id",
					eitem.getId());
			returnValidata.append("<couponid>" + eitem.getId() + "</couponid>");
			returnValidata.append("<name>" + eitem.getName() + "</name>");
			// returnValidata.append("<descr>"+eitem.getDescr()+"</descr>");
			returnValidata.append("<endtime>" + eitem.getEndtime()
					+ "</endtime>");
			returnValidata.append("<codes>");
			for (Barcode barcode : codelist) {
				returnValidata.append("<Barcode>");
				returnValidata.append("<id>" + barcode.getId() + "</id>");
				returnValidata.append("<numcode>" + barcode.getNumcode()
						+ "</numcode>");
				returnValidata.append("<charcode>" + barcode.getCharcode()
						+ "</charcode>");
				returnValidata.append("</Barcode>");

			}
			returnValidata.append("</codes>");
			returnValidata.append("</Coupon>");
		}

		returnValidata.append("</ArrayOfCoupon>");
		return returnValidata.toString();
	}

	@Override
	public String updateZJBarcodeStatus(String imei, String eitemid,
			String barcodeids) {
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		List<Device> devicelist = deviceDao.findByProperty("devcode", imei);
		if (null == devicelist || devicelist.isEmpty()) {
			return sbstr.append(getErrorMessage("false", "交易失败,设备未绑定"))
					.toString();
		}

		if (barcodeids != null) {
			String ids[] = barcodeids.split(",");
			Long bids[] = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				bids[i] = Long.valueOf(ids[i]);
			}
			Finder finder = new Finder(
					" from Barcode where id in (:ids) and canusenum > 0 ");
			finder.setParamList("ids", bids);
			List<Barcode> barcodelist = barcodeDao.find(finder);
			for (Barcode barcode : barcodelist) {
				barcodeVerify(imei, barcode.getNumcode(), eitemid, "1");
			}
		} else {
			return sbstr.append(getErrorMessage("true", "")).toString();
		}
		return sbstr.append(getErrorMessage("true", "")).toString();
	}

	@Override
	public String[] verifyZXBarcode(String numcode, String imei, String outid,
			int num) {
		Eitem eitem = eitemDao.findUniqueByProperty("productcode", outid);
		String[] result = new String[2];
		if (eitem == null) {
			result[0] = "";
			result[1] = "没有找到对应的活动";
			return result;
		}
		return verifyZXBarcode(numcode, imei, eitem, num,
				DateTimeUtils.getDate(),null,null,null);
	}

	private String refZXBarcode(Payment payment) {
		String rest = "";
		String date = DateTimeUtils.getDate("yyyyMMdd");
		String authdate = payment.getAuthdate();
		if (date.equals(authdate)) {
			if (istest) {
				rest = TestZXWsClient.dividedPaymentReversal(payment);
			} else {
				rest = ZXWsClient.dividedPaymentReversal(payment);
			}
		} else {
			if (istest) {
				rest = TestZXWsClient.hirePurchaseReturn(payment);
			} else {
				rest = ZXWsClient.hirePurchaseReturn(payment);
			}
		}

		return rest;
	}

	@Override
	public String revocationExchange(String imei, String id) {

		StringBuffer returnChecklog = new StringBuffer();
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		Device device = deviceDao.findUniqueByProperty("devcode", imei);
		if (device == null) {
			returnChecklog.append("<Table>");
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<resultInfo>撤销失败,没有找到对应的门店</resultInfo>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();

		}
		Checklog checklog = checklogDao.findUniqueByProperty("printno", id);
		if (id.length() == 19) {
			Payment payment = paymentDao.findUniqueByProperty("orderid", id);
			if (payment == null) {
				returnChecklog.append("<Table>");
				returnChecklog.append("<isSuccess>false</isSuccess>");
				returnChecklog.append("<resultInfo>撤销失败,没有找到对应活动</resultInfo>");
				returnChecklog.append("</Table>");
				returnChecklog.append("</NewDataSet>");
				return returnChecklog.toString();
			}
			String rest = "";
			if (checklog == null || payment.getStatus() != 1
					&& payment.getStatus() != 2) {
				rest = "撤销失败,订单状态有误,请与管理员联系";
			}
			if (!rest.endsWith("")) {
				returnChecklog.append("<Table>");
				returnChecklog.append("<isSuccess>false</isSuccess>");
				returnChecklog.append("<resultInfo>" + rest + "</resultInfo>");
				returnChecklog.append("</Table>");
				returnChecklog.append("</NewDataSet>");
				return returnChecklog.toString();
			}
			rest = refZXBarcode(payment);
			if (rest.startsWith("error:")) {
				returnChecklog.append("<Table>");
				returnChecklog.append("<isSuccess>false</isSuccess>");
				returnChecklog.append("<resultInfo>" + rest + "</resultInfo>");
				returnChecklog.append("</Table>");
				returnChecklog.append("</NewDataSet>");
				return returnChecklog.toString();
			}
			payment.setStatus((short) 4);
			paymentDao.update(payment);
			returnChecklog.append(returnCheckStr(device.getStore(), null, imei,
					checklog.getEitem(), checklog.getCharcode(),
					Integer.valueOf(payment.getProductnum()), "撤销成功",
					payment.getOrderid(), DateTimeUtils.getDate(), "权益撤销", ""));
			returnChecklog.append("</NewDataSet>");
			checklogDao.delete(checklog);
			return returnChecklog.toString();
		}
		if (checklog == null) {
			returnChecklog.append("<Table>");
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<resultInfo>撤销失败,没有找到对应活动</resultInfo>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();

		}
		Barcode barcode = checklog.getBarcode();
		barcode.setCanusenum(barcode.getCanusenum() + checklog.getChecknum());
		barcodeDao.update(barcode);
		Eorder eorder = barcode.getEorder();
		eorder.setOvernum(eorder.getOvernum() - checklog.getChecknum());
		eorderDao.update(eorder);
		checklogDao.delete(checklog);
		returnChecklog.append(returnCheckStr(device.getStore(), barcode, imei,
				checklog.getEitem(), checklog.getCharcode(),
				checklog.getChecknum(), "撤销成功", checklog.getPrintno(),
				DateTimeUtils.getDate(), "权益撤销", ""));
		returnChecklog.append("</NewDataSet>");
		return returnChecklog.toString();
	}

	@Override
	public String getDevicePwd(String imei) {
		StringBuffer returnChecklog = new StringBuffer();
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		returnChecklog.append("<Table>");
		Device device = deviceDao.findUniqueByProperty("devcode", imei);
		if (device == null) {
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<errorMsg>没有找到对应的门店</errorMsg>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();

		}
		String pwd = device.getAdminpassword();
		if (pwd == null || pwd.equals("")) {
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<errorMsg>机具没有被重置密码,请与管理员联系</errorMsg>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();
		}
		returnChecklog.append("<isSuccess>true</isSuccess>");
		returnChecklog.append("<password>");
		returnChecklog.append(pwd);
		returnChecklog.append("</password></Table>");
		returnChecklog.append("</NewDataSet>");
		device.setAdminpassword(null);
		deviceDao.update(device);
		return returnChecklog.toString();
	}

	@Override
	public String showBarcodeAmount(String imei, String code) {
		StringBuffer returnChecklog = new StringBuffer();
		returnChecklog.append("<?xml version='1.0' encoding='UTF-8'?>");
		returnChecklog.append("<NewDataSet>");
		returnChecklog.append("<Table>");

		Device device = deviceDao.findUniqueByProperty("devcode", imei);
		if (device == null) {
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<errorMsg>不是合法的机具</errorMsg>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();
		}
		Barcode barcode = null;
		Finder finder = new Finder("from Barcode");
		finder.append(" where (numcode like :numcode or charcode like :charcode)");
		finder.setParam("numcode", code);
		finder.setParam("charcode", code);
		finder.append(" and eitem.id in (select es.eitem.id from Eitemstore es where es.store.id =:storeid)");
		finder.setParam("storeid", device.getStore().getId());
		List<Barcode> blist = barcodeDao.find(finder);
		if (blist != null && !blist.isEmpty()) {
			barcode = blist.get(0);
		}
		if (barcode == null) {
			returnChecklog.append("<isSuccess>false</isSuccess>");
			returnChecklog.append("<errorMsg>没有找到对应的码。</errorMsg>");
			returnChecklog.append("</Table>");
			returnChecklog.append("</NewDataSet>");
			return returnChecklog.toString();
		}
		double amount = barcode.getAmount();
		returnChecklog.append("<isSuccess>true</isSuccess>");
		returnChecklog.append("<errorMsg></errorMsg>");
		returnChecklog.append("<amount>");
		returnChecklog.append(amount);
		returnChecklog.append("</amount></Table>");
		returnChecklog.append("</NewDataSet>");
		return returnChecklog.toString();
	}

	@Override
	public String barcodeVerify(String imei, String charcode, String eitemid,
			String amcout, String searchno, String batchno) {
		String posno = ToolUtils.getPosno();
		int usednum = Integer.valueOf(amcout);
		String subImei = imei.substring(imei.length() - 4, imei.length());
		String currDate = new SimpleDateFormat("yyMMdd").format(new Date());// 生成日期格式为：年年月月日日、如：110608(11年06月08日)
		DecimalFormat df = new DecimalFormat("0000");
		Random rd = new Random();
		String increase = df.format(rd.nextInt(9999));// 4位自增长数
		// printNo生成规则：设备ID的后4位+日期6位+4位增长数
		String printno = subImei + "" + currDate + "" + increase;

		StringBuffer sbstr = new StringBuffer();
		String ctime = DateTimeUtils.getDate();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		if (testcode.equals(charcode) || testcode.equals(charcode)) {
			return newTestDeviceBind(imei, charcode);
		}
		Barcode barcode = null;
		if (null == charcode || "".equals(charcode))
			return sbstr.append(getErrorMessage("false", "交易失败,不是有效的码"))
					.toString();
		if (null == eitemid || "".equals(eitemid))
			return sbstr.append(getErrorMessage("false", "交易失败,活动不能为空"))
					.toString();
		List<Device> devicelist = deviceDao.findByProperty("devcode", imei);
		if (null == devicelist || devicelist.isEmpty()) {
			return sbstr.append(getErrorMessage("false", "交易失败,设备未绑定"))
					.toString();
		}
		Device device = devicelist.get(0);
		Store store = device.getStore();
		Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
		Barcode eg = new Barcode();
		if (pt.matcher(charcode).matches()) {// 数字二维码
			eg.setNumcode(charcode);
		} else {
			eg.setCharcode(charcode);
		}
		Eitem eitem = null;
		if (eitemid.equals("0")) {
			List<Barcode> blist = barcodeDao.findByEgList(eg);
			if (blist == null || blist.size() == 0) {
				String jiZhang = jiZhang(device, charcode);
				return jiZhang != null ? jiZhang : sbstr.append(getErrorMessage("false", "交易失败,不是有效的码")).toString();
			}
			barcode = blist.get(0);
			eitem = barcode.getEitem();
		} else
			eitem = eitemDao.findUniqueByProperty("id", Long.valueOf(eitemid));

		if (eitem == null)
			return sbstr.append(getErrorMessage("false", "交易失败,不是有效的码"))
					.toString();
		if(eitem.getIspayment()!=null && eitem.getIspayment()==1){
			return sbstr.append(getErrorMessage("false", "交易失败,没有找到对应的活动"))
					.toString();
		}
		String weeklimit = eitem.getWeeklimit();
		if (weeklimit != null && !weeklimit.equals("")) {
			Calendar calendar = Calendar.getInstance();
			String dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) + "";
			if (weeklimit.indexOf(dayOfWeek) == -1) {
				log.info("eitem.getName():" + eitem.getName() + "  dayOfWeek:"
						+ dayOfWeek + "  weeklimit:" + weeklimit);
				return sbstr.append(
						getErrorMessage("false",
								"交易失败,不是活动日,不能参与“" + eitem.getName() + "”活动"))
						.toString();
			}
		}

		// if (eitem.getIszxcheck() > 0 && (charcode.length() == 14 ||
		// charcode.length()==16)) {
		if (eitem.getIszxcheck() > 0) {
			Finder f = new Finder(" from Eitemstore where store.id = :storeid and eitem.id = :eitemid");
			f.setParam("storeid", store.getId());
			f.setParam("eitemid", eitem.getId());
			List<Eitemstore> list = eitemstoreDao.find(f);
			if (list == null || list.size() == 0) {
				return sbstr.append(getErrorMessage("false", "交易失败,该活动不能在此门店进行")).toString();
			}
			
			charcode = charcode.split("D")[0];
			if ("7330015000464523".equals(charcode)) {
				charcode = "5201080000040103";
			}
			String[] strs = verifyZXBarcode(charcode, imei, eitem, usednum,
					ctime,batchno, searchno, posno);
			if (!"0000000".equals(strs[1])) {
				return sbstr.append(getErrorMessage("false", strs[0]))
						.toString();
			} else {
				//TODO 成功
				sbstr.append(returnCheckStr(store, barcode, imei, eitem,
						charcode, usednum, "验证成功", strs[2], ctime, "权益兑换",
						posno));
				sbstr.append("</NewDataSet>");
				return sbstr.toString();
			}
		}
		if (barcode == null) {
			List<Barcode> blist = barcodeDao.findByEgList(eg);
			if (blist == null || blist.size() == 0) {
				return sbstr.append(getErrorMessage("false", "交易失败,不是有效的码"))
						.toString();
			}
			barcode = blist.get(0);
		}
		Eorder eorder = barcode.getEorder();

		try {
			if (eitem.getId() != barcode.getEitem().getId()) {
				throw new MustlogException(Variable.CHECKTYPE_5,
						"交易失败,选择的活动与二维码不匹配");
			}

			String nowdate = DateTimeUtils.getDate("yyyy-MM-dd");
			String endtime = barcode.getEndate();
			if (null != endtime) {
				if (endtime.length() > 10) {
					endtime = endtime.substring(0, 10);
				}
				if (endtime.compareTo(nowdate) < 0) {
					throw new MustlogException(Variable.CHECKTYPE_2,
							"交易失败,该码已经过期");
				}
			}
			if ("5".equals(eorder.getStatus())) {
				throw new MustlogException(Variable.CHECKTYPE_6, "交易失败,该码已经作废");
			}
			if (barcode.getCanusenum() <= 0) {
				throw new MustlogException(Variable.CHECKTYPE_1,
						"交易失败,该码已没有使用次数");
			}
			Finder finder = new Finder(
					" from Eitemstore where store.id = :storeid and eitem.id = :eitemid");
			finder.setParam("storeid", store.getId());
			finder.setParam("eitemid", eitem.getId());
			List<Eitemstore> elist = eitemstoreDao.find(finder);
			if (elist == null || elist.size() == 0) {
				throw new MustlogException(Variable.CHECKTYPE_5,
						"交易失败,该码不能在此门店使用");
			}

		} catch (MustlogException ex) {
			if (barcode != null) {
				writeLog(imei, "", barcode, charcode, ex.getLogtype(),
						ex.getMes(), null, usednum, Variable.CHECKMODE_DEVICE,
						eitem, ctime, batchno, searchno, posno);
			}
			return sbstr.append(getErrorMessage("false", ex.getMes()))
					.toString();
		}

		try {

			if (barcode.getCanusenum() - usednum >= 0) {
				barcode.setCanusenum(barcode.getCanusenum() - usednum);
				barcode.setUsetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			} else {
				if (barcode.getCanusenum() == 0) {
					if (barcode.getUsetime() == null) {
						throw new MustlogException(Variable.CHECKTYPE_1,
								"交易失败,该码已经废除,如有疑问请联系客服人员");
					} else {
						throw new MustlogException(Variable.CHECKTYPE_1,
								"交易失败,该码的可使用次数为 0,上次成功使用时间："
										+ barcode.getUsetime());
					}
				} else {
					throw new MustlogException(Variable.CHECKTYPE_1,
							"交易失败,该码的可使用次数不够,可使用次数为：" + barcode.getCanusenum());
				}
			}

			barcodeDao.update(barcode);
		} catch (MustlogException ex) {
			if (barcode != null) {
				writeLog(imei, "", barcode, charcode, ex.getLogtype(),
						ex.getMes(), null, usednum, Variable.CHECKMODE_DEVICE,
						eitem, DateTimeUtils.getDate(), batchno, searchno,
						posno);
			}
			return sbstr.append(getErrorMessage("false", ex.getMes()))
					.toString();
		}
		try {
			writeLog(imei, "", barcode, charcode, Variable.CHECKTYPE_0, "验证成功",
					printno, usednum, Variable.CHECKMODE_DEVICE, eitem, ctime,
					batchno, searchno, posno);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String result = "验证失败";
		if (eitem != null) {
			updateEorder(barcode);// 更新eordr表中的使用件数
			String checkinfo = "";
			String eitemName = "";
			eitemName = eitem.getName();
			checkinfo = eitem.getCheckinfo();
			if (null != checkinfo && !"".equals(checkinfo)) {
				result = checkinfo;
			} else {
				result = "验证成功,商品名称为：" + eitemName;
			}
			if (barcode.getCanusenum() < 10000)
				result = result + ",该码的剩余可使用次数为:" + barcode.getCanusenum();

		}
		sbstr.append(returnCheckStr(store, barcode, imei, eitem, charcode,
				usednum, result, printno, ctime, "权益兑换", posno));
		sbstr.append("</NewDataSet>");
		String ssssssssssssssssssss = sbstr.toString();
		return ssssssssssssssssssss;
	}

	private String jiZhang(Device d, String charcode) {
		if (!compile("[0-9]*").matcher(charcode).matches() 
				|| !charcode.startsWith("1") 
				|| charcode.length() < 14
				|| !"00".equals(charcode.substring(11, 13))) {
			return null;
		}
		String phone = charcode.substring(0, 11);
		String jine = charcode.substring(13, charcode.length());
		Finder finder = new Finder("from Eitemstore es where es.store.id = :storeid and es.eitem.jizhang = 'true'");
		Store store = d.getStore();
		finder.setParam("storeid", store.getId());
		List<Eitemstore> list = eitemstoreDao.find(finder);
		if (list == null || list.size() == 0) {
			return null;
		}
		Set<Long> set = new HashSet<Long>();
		Eitem eitem = null;
		for (Eitemstore es : list) {
			Eitem e = es.getEitem();
			eitem = e;
			set.add(e.getId());
		}
		if (set.size() != 1) {
			return null;
		}
		String posno = ToolUtils.getPosno();
		JiZhang save = new JiZhang();
		save.setPosno(posno);
		save.setDevcode(d.getDevcode());
		save.setEitem(eitem);
		save.setStore(store);
		save.setInputlog(charcode);
		String date = DateTimeUtils.getDate();
		save.setCreatetime(date);
		jiZhangDao.save(save);
		
		String ret = "<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table>"
				+ "<isSuccess>true</isSuccess>"
				+ "<IsNeedConfirm>false</IsNeedConfirm>"
				+ "<ConfirmNumber></ConfirmNumber><PwdInfoId></PwdInfoId>"
				+ "<MmsId>" + eitem.getId() + "</MmsId>"
				+ "<MastMmsId>" + eitem.getId() + "</MastMmsId>"
				+ "<MmsName>" + eitem.getName() + "</MmsName>"
				+ "<Description>" + eitem.getDescr() + "</Description>"
				+ "<MmsTitle></MmsTitle><ValidFrom></ValidFrom>"
				+ "<ValidTo>" + eitem.getEndtime() + "</ValidTo>"
				+ "<ResendContent></ResendContent>"
				+ "<ValidTimes>0</ValidTimes>"
				+ "<ShopName>"+ store.getName() + "</ShopName>"
				+ "<ShopFamilyName>北京亿美汇金信息技术有限公司</ShopFamilyName>"
				+ "<MmsFamilyName>北京亿美汇金信息技术有限公司</MmsFamilyName>"
				+ "<Advertising></Advertising>"
				+ "<PrintNo>12341412018504</PrintNo>"
				+ "<LastVerifyNumber>0</LastVerifyNumber>"
				+ "<verifyPwd>010060670807</verifyPwd>"
				+ "<CreatedOn>" + date + "</CreatedOn>"
				+ "<CurrentDateTime>" + date + "</CurrentDateTime>"
				+ "<ShopId>" + store.getShop().getUuid() + "</ShopId>"
				+ "<Entname>" + eitem.getEnterprise().getEntname() + "</Entname>"
				+ "<Printstr>"+eitem.getPrintstr() + "</Printstr>"
				+ "<PrintTitle>"+eitem.getPrintTitle() + "</PrintTitle>"
				+ "<ShopNo>000010324446</ShopNo>"
				+ "<TranType>权益兑换</TranType>"
				+ "<BacthNo>072348138</BacthNo>"
				+ "<AdminNo>001</AdminNo>"
				+ "<PrintInfo></PrintInfo>"
				+ "<Result>恭喜你验证成功！</Result>"
				+ "<Canusenum>1</Canusenum>"
				+ "<posno>" + posno + "</posno>"
				+ "</Table></NewDataSet>";
		return ret;
	}

	@Override
	public String barcodepayment(String imei, String code, String oid,
			String amount, String storeid, String batchno, String searchno) {
		log.info("BarcodeServiceImpl.barcodepayment get arguments: oid="+oid+"\n imei="+
			imei+"\n amount="+amount+"\n code="+code);
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		Aorder aorder = null;
		if (oid != null)
			aorder = aorderDao.findUniqueByProperty("requestid", oid);
		Device device = null;
		Store store = null;
		// 判断二维码是否正确
		if (null == code) {
			sbstr.append(getBarcodeErrorMessage("false", "交易失败,不是有效的码"));
			log.debug("method :barcodepayment     code is null");
			return sbstr.toString();
		}
		Pattern pt = Pattern.compile("[0-9]*");// 判断是数字二维码还是带有英文的二维码
		Barcode barcode = null;
		if (pt.matcher(code).matches()) {// 数字二维码
			barcode = barcodeDao.findUniqueByProperty("numcode", code);
		} else {
			barcode = barcodeDao.findUniqueByProperty("charcode", code);
		}
		if (barcode == null) {
			sbstr.append(getBarcodeErrorMessage("false", "交易失败,不是有效的码"));
			log.debug(" method: barcodepayment     barcode is null");
			return sbstr.toString();
		}
		Long canusenum = barcode.getCanusenum();
		if (canusenum == null || canusenum <= 0) {
			sbstr.append(getBarcodeErrorMessage("false", "交易失败,此储值卡已无可用次数"));
			return sbstr.toString();
		}
		Eorder eorder = barcode.getEorder();
		if (eorder.getStatus().equals("5")) {
			sbstr.append(getBarcodeErrorMessage("false", "交易失败,二维码已经作废"));
			return sbstr.toString();
		}
		// 第一次请求,aorder表里没有记录
		Account shopaccount = null;
		if (aorder == null) {
			log.info("aorder is null");
			if (imei != null) {
				device = deviceDao.findUniqueByProperty("devcode", imei);
				if (device == null) {
					sbstr.append(getBarcodeErrorMessage("false", "交易失败,设备未绑定"));
					return sbstr.toString();
				}
				store = device.getStore();
			} else {
				store = storeDao.findUniqueByProperty("guid", storeid);
			}
			Eitem eitem = barcode.getEitem();
			if(eitem.getIspayment() == null || eitem.getIspayment()==0){
				sbstr.append(getBarcodeErrorMessage("false", "交易失败,不是有效的码"));
				return sbstr.toString();
			}
			
			Finder finder = new Finder(
					" from Eitemstore where store.id=:storeid and eitem.id=:eitemid");
			finder.setParam("storeid", store.getId());
			finder.setParam("eitemid", eitem.getId());
			List<Eitemstore> eslist = eitemstoreDao.find(finder);
			if (eslist == null || eslist.isEmpty()) {
				sbstr.append(getBarcodeErrorMessage("false", "交易失败,该门店无资格消费"));
				return sbstr.toString();
			}

			Shop shop = store.getShop();
			if (!shop.getAccounts().isEmpty())
				shopaccount = shop.getAccounts().iterator().next();
			else {
				shopaccount = new Account();
				shopaccount.setShop(shop);
				try {
					shopaccount.setPassword(MD5Helper.EncoderByMd5("123456"));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				shopaccount = accountDao.save(shopaccount);
			}
			if (amount == null || "".equals(amount)) {
				sbstr.append(getBarcodeErrorMessage("false", "交易失败,支付金额有误"));
				return sbstr.toString();
			}
			aorder = new Aorder();
			aorder.setShop(store.getShop());
			aorder.setStore(store);
			aorder.setPayment(Double.valueOf(amount));
			aorder.setRequestid(oid);
			aorder.setBarcode(barcode);
			aorder.setCode(code);
			aorder.setPaytype(1);
			DecimalFormat df = new DecimalFormat("0000");
			Random rd = new Random();
			String increase = df.format(rd.nextInt(9999));
			aorder.setSerialnum(DateTimeUtils.nowDateToStr() + increase);
			aorder.setCreatetime(DateTimeUtils.getDate());
			aorder.setStatus("0");
			String posno = ToolUtils.getPosno();
			aorder.setPosno(posno);
			aorder.setBatchno(batchno);
			aorder.setSearchno(searchno);
			try {
				aorder = aorderDao.save(aorder);
			} catch (Exception e) {
				sbstr.append(getBarcodeErrorMessage("false",
						"交易失败," + e.getMessage()));
				return sbstr.toString();
			}
			if (aorder != null) {
				try {
					Double useramount = barcode.getAmount();
					if (useramount == null || useramount == 0) {
						sbstr.append(getBarcodeErrorMessage("false",
								"交易失败,该码的余额为0"));
						aorder.setStatus("3");
						aorderDao.update(aorder);
						return sbstr.toString();
					}
					double payamout = Double.valueOf(amount);
					if (useramount < payamout) {
						sbstr.append(getBarcodeErrorMessage("false",
								"交易失败,该码的余额不足,余额为：" + useramount));
						aorder.setStatus("3");
						aorderDao.update(aorder);
						return sbstr.toString();
					}

					String nowdate = DateTimeUtils.getDate("yyyy-MM-dd");
					String endtime = barcode.getEndate();
					if (null != endtime) {
						if (endtime.length() > 10) {
							endtime = endtime.substring(0, 10);
						}
						if (endtime.compareTo(nowdate) < 0) {
							sbstr.append(getBarcodeErrorMessage("false",
									"交易失败,该码已经过期"));
							aorder.setStatus("3");
							aorderDao.update(aorder);
							return sbstr.toString();
						}
					}
					barcode.setAmount(useramount - payamout);
					barcode.setUsetime(DateTimeUtils.getDate());
					barcode.setCanusenum(barcode.getCanusenum() - 1);
					barcodeDao.update(barcode);
					String tid = DateTimeUtils.getDate("yyyyMMddHHmmSSS");
					increase = df.format(rd.nextInt(9999));
					tid = tid + increase;
					aorder.setAlipayid(tid);
					aorder.setImei(imei);
					aorder.setCreatetime(DateTimeUtils.getDate());
					aorder.setStatus("1");
					aorder = (Aorder) aorderDao.update(aorder);
					sbstr.append(getOverMssages(aorder, barcode.getEitem()
							.getName()));

					Charge charge = new Charge();
					charge.setAccount(shopaccount);
					charge.setStatus(1);
					charge.setAmount(payamout);
					charge.setName(eitem.getName());
					charge.setDescr("本次消费方为二维码储值卡：" + code + ",");
					charge.setType(0);
					charge.setSource("二维码储值卡：" + code);
					charge.setTid(tid);
					charge.setCreatetime(DateTimeUtils
							.getDate("yyyy-MM-dd HH:mm:ss"));
					charge.setBalance((shopaccount.getBalance() == null ? 0
							: shopaccount.getBalance()) + payamout);
					// System.out.println("=================== shopaccount.id:"
					// + shopaccount.getId());
					// System.out.println("==================== before update shopaccount.Balance:"
					// + shopaccount.getBalance());
					shopaccount
							.setBalance((shopaccount.getBalance() == null ? 0
									: shopaccount.getBalance()) + payamout);
					shopaccount
							.setTotalmoneyin((shopaccount.getTotalmoneyin() == null ? 0
									: shopaccount.getTotalmoneyin())
									+ payamout);
					accountDao.update(shopaccount);
					// System.out.println("====================  after shopaccount.Balance:"
					// + shopaccount.getBalance());
					chargedao.save(charge);

					/********** 发送通知短信 ************/
					String content = eitem.getExpenseSms();
					if (content != null && !"".equals(content)) {
						content = content.replace("$expense", amount);
						content = content.replace("$balance", barcode
								.getAmount().toString());
						content = content.replace("$shopName", shop.getName());
						Enterprise enterprise = eitem.getEnterprise();
						try {
							int smscount = (int) Math
									.ceil(content.length() / 67.0);
							if (enterprise.getSmsbalance() != null
									&& enterprise.getSmsbalance() > 0
									&& enterprise.getSmsbalance() - smscount >= 0) {
								enterprise.setSmsbalance(enterprise
										.getSmsbalance() - smscount);
								content = content.replace("~", "^");
								content = eorder.getPhone() + "~" + content
										+ "~" + enterprise.getId() + "~5";
								;
								log.debug("=========    send inform sms before send ================");
								messageSender.simpleSend(content);
								enterpriseDao.update(enterprise);
								log.debug("=========    send inform sms after send ================");
							}
						} catch (Exception es) {
							es.printStackTrace();
						}
					}
					log.info(" JMS Content is "+content);
					// 商户名称消费***元,卡内余额为***元【大美田园】

					// 数字码消费要改变数字码
					/*
					 * if(isnumber){
					 * updateBarcode(barcode,1,cn.m.mt.barcodeservice
					 * .util.Variable.CODE_TYPE_BANK); StringBuffer content =new
					 * StringBuffer(); content.append(eorder.getPhone());
					 * content.append("~电子凭证数字码已经更改为：");
					 * content.append(barcode.getNumcode());
					 * content.append(",二维码图片不变可继续使用"); content.append("~");
					 * content.append(eitem.getEnterprise().getId()); try {
					 * messageSender.simpleSend(content.toString()); } catch
					 * (Exception e) { e.printStackTrace(); } }
					 */
					log.info("return xml is :\n "+sbstr.toString());
					return sbstr.toString();
				} catch (Exception e) {
					sbstr.append(returnTrading());
					e.printStackTrace();
					log.info("return xml is :\n "+sbstr.toString());
					return sbstr.toString();
				}
			}
			log.info("return xml is :\n "+sbstr.toString());
			return sbstr.toString();
		} else {// 不是第一次请求
			log.info("aorder is NOT null, and aorder.status="+aorder.getStatus());
			System.out.println("aorder is NOT null, and aorder.status="+aorder.getStatus());
			if (aorder.getStatus().equals("1")) {
				sbstr.append(getOverMssages(aorder, barcode.getEitem()
						.getName()));
				return sbstr.toString();
			} else if (aorder.getStatus().equals("2")) {
				sbstr.append(getBarcodeErrorMessage("false", "交易失败,该订单已退款"));
				return sbstr.toString();
			} else if (aorder.getStatus().equals("3")) {
				sbstr.append(getBarcodeErrorMessage("false",
						aorder.getErrormessage()));
				return sbstr.toString();
			} else if (aorder.getStatus().equals("0")) {
				sbstr.append(getAorderStatusStrByRequestid(oid, barcode
						.getEitem().getName()));
				return sbstr.toString();
			}
		}
		log.info("return xml is :\n "+sbstr.toString());
		return sbstr.toString();

	}

	@Override
	public String redVerifyBarcode(String id, String imei) {
		return redVBarcode(id, imei, null, null, null, null);
	}

	@Override
	public String redVerifyBarcodeBySearchno(String imei, String batchno,
			String searchno) {
		return redVBarcode(null, imei, batchno, searchno, null, null);
	}

	@Override
	public String redVerifyBarcodeByPosno(String imei, String posno, String time) {
		return redVBarcode(null, imei, null, null, posno, time);
	}

	@Override
	public String refPayment(String orderid) {
		return redVerifyPayment(orderid, null, null, null, null);
	}

	@Override
	public String redVerifyPaymentBySearchno(String batchno, String searchno) {
		return redVerifyPayment(null, batchno, searchno, null, null);
	}

	@Override
	public String redVerifyPaymentByPosno(String posno, String time) {
		return redVerifyPayment(null, null, null, posno, time);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String barcodeSettle(String imei, int amt, int num,String batchno) {
		Finder finder = new Finder(
				" select sum(checknum),count(id)  from Checklog where checktype=0 and substring(createtime,1,10)= date(now()) ");
		finder.append(" and imei=:imei");
		finder.setParam("imei", imei);
		finder.append(" and batchno=:batchno");
		finder.setParam("batchno", batchno);
		List<Object[]> checkloglist = checklogDao.findObject(finder);
		Object[] obj = checkloglist.get(0);
		long todayamt = 0;
		long todaynum = 0;
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append("<Table>");
		if (obj[0] != null) {
			todayamt = (Long) obj[0];
			todaynum = (Long) obj[1];
		}
		sbstr.append("<isSuccess>");
		if (todayamt == amt && todaynum == num) {
			sbstr.append("true");
		} else {
			sbstr.append("false");
		}
		sbstr.append("</isSuccess>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();
	}

	@Override
	public String codepaySettle(String imei, int amt, int num, String batchno) {
		System.out.println("==="+imei+"======="+amt+"======="+num+"======="+batchno+"============");
		Finder finder = new Finder(
				" select sum(payment),count(id)  from Aorder where status = 1 and  substring(createtime,1,10)= date(now()) ");
		finder.append(" and imei =:imei");
		finder.setParam("imei", imei);
		finder.append(" and batchno =:batchno");
		finder.setParam("batchno", batchno);
		List<Object[]> aorderlist = aorderDao.findObject(finder);
		Object[] obj = aorderlist.get(0);
		double todayamt = 0;
		long todaynum = 0;
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append("<Table>");
		if (obj[0] != null) {
			todayamt = (Double) obj[0];
			todaynum = (Long) obj[1];
		}
		
		long tamt = (long) (todayamt*100);
		System.out.println("===tamt=="+tamt);
		System.out.println("===todaynum=="+todaynum);
		sbstr.append("<isSuccess>");
		if (tamt == amt && todaynum == num) {
			sbstr.append("true");
		} else {
			sbstr.append("false");
		}
		sbstr.append("</isSuccess>");
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();

	}

	@Override
	public void reconciliation(String reqtype, String batchno, String imei,
			String posno, String charcode, String amcout, String searchno) {
		// Reconciliation rec = new Reconciliation();
		// rec.setType(Integer.parseInt(reqtype));
		// rec.setBatchno(batchno);
		// rec.setImei(imei);
		// rec.setAmcout(Double.parseDouble(amcout));
		// rec.setCharcode(charcode);
		// rec.setCreatetime(DateTimeUtils.getDate());
		// rec.setPosno(posno);
		// rec.setSearchno(searchno);
		// reconciliationDao.save(rec);
	}

	@Override
	public String getBatchnoByImei(String imei) {
		Device dev = new Device();
		dev.setDevcode(imei);
		dev = deviceDao.findUniqueByProperty("devcode", imei);
		StringBuffer sbstr = new StringBuffer();
		sbstr.append("<?xml version='1.0' encoding='UTF-8'?>");
		sbstr.append("<NewDataSet>");
		sbstr.append("<Table>");
		if (dev != null){
			sbstr.append("<isSuccess>true</isSuccess>");
			sbstr.append("<batchno>");
			sbstr.append(dev.getBatchno());
			sbstr.append("</batchno>");
			sbstr.append("<mankey>");
			sbstr.append(dev.getDeskey());
			sbstr.append("</mankey>");
			sbstr.append("<mackey>");
			sbstr.append(dev.getMackey());
			sbstr.append("</mackey>");
			
		}else{
			sbstr.append("<isSuccess>false</isSuccess>");
		}
		sbstr.append("</Table>");
		sbstr.append("</NewDataSet>");
		return sbstr.toString();
	}

	@Override
	public void setNewBatchnoByImei(String imei, String batchno, String mackey) {
		Device dev = deviceDao.findUniqueByProperty("devcode", imei);
		if (dev != null) {
			dev.setBatchno(batchno);
			dev.setMackey(mackey);
			deviceDao.update(dev);
		}
	}

	
	
	@Override
	public String getEitemAdvByImei(String imei) {
		Device dev = deviceDao.findUniqueByProperty("devcode", imei);
		if(dev==null)
			return null;
		Finder finder = new Finder(" from EitemAdvStore where store.id = "+dev.getStore().getId());
		List<EitemAdvStore> elist = ditemAdvStoreDao.find(finder);
		if(elist!=null && elist.size()>0){
			Eitem eitem = elist.get(0).getEitem();
			List<String>  codelist = createBarcode(Variable.CODE_TYPE_NORMAL, 0, 1);
			StringBuffer sb = new StringBuffer();
			sb.append(eitem.getAdvcontent());
			sb.append("@@");
			for(String str :codelist ){
				initBarcode(eitem,str);
				sb.append(str.split(",")[1]);
				sb.append(",");
			}
			String rutstr = sb.toString();
			return rutstr.substring(0,rutstr.length()-1);
		}else
			return null;
		
	}
	@Override
	public String getEitemAdvByImeiAndCard(String imei, String cardNum, String atm) {
		Device dev = deviceDao.findUniqueByProperty("devcode", imei);
		if (dev == null) {
			log.error("找不到机具，不发送广告活动。机具号：" + imei + "卡号：" + cardNum + "刷卡金额：" + atm);
			return null;
		}
		if (cardNum == null || cardNum.trim().length() == 0) {
			log.error("卡号为空，不发送广告活动。机具号：" + imei + "刷卡金额：" + atm);
			return null;
		}
		cardNum = cardNum.trim();
		if (atm == null || atm.trim().length() == 0) {
			log.error("刷卡金额为空，不发送广告活动。机具号：" + imei + "卡号：" + cardNum);
			return null;
		}
		atm = atm.trim();
		Finder finder = new Finder(" from EitemAdvStore where store.id = " + dev.getStore().getId() + " order by eitem.advPriority desc ");
		List<EitemAdvStore> easList = ditemAdvStoreDao.find(finder);
		if (easList == null || easList.size() == 0) {
			return null;
		}
		List<Eitem> eList = new ArrayList<Eitem>();
		for (EitemAdvStore e : easList) {
			eList.add(e.getEitem());
		}
		for (Eitem eitem : eList) {
			if (DateTimeUtils.compareDate(eitem.getEndtime())) {
				log.info("广告活动已到期，所以不再发放。" + "活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				continue;
			}
			if (DateTimeUtils.compareDate(eitem.getAdvEndTime())) {
				log.info("广告活动发码截止日期已到，所以不再发放。" + "活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				continue;
			}
			if (DateTimeUtils.notStart(eitem.getAdvStartTime())) {
				log.info("广告活动发码尚未开始，所以不发放。" + "活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				continue;
			}
			if (!matchDate(eitem.getAdvSpecTime())) {
				log.info("广告活动发码日期列表不包括今天，所以不发放。" + "活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				continue;
			}
			if (eitem.getNum() == null || eitem.getNum() == 0) {
				log.info("广告活动的可用数量不足，所以不再发放。" + "活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				continue;
			}
			String unit = eitem.getAdvertiseunit();
			if (unit == null || unit.trim().length() == 0) {
				log.error("广告活动的周期（advertiseunit）为空。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				return null;
			}
			unit = unit.trim();
			Date d = null;
			String logString = null;
			if ("D".equalsIgnoreCase(unit)) {
				d = startOfToday();
				logString = "今天";
			} else if ("W".equalsIgnoreCase(unit)) {
				d = startOfThisMonday();
				logString = "本周";
			} else if ("M".equalsIgnoreCase(unit)) {
				d = startOfThisMonth();
				logString = "本月";
			} else {
				log.error("广告活动的周期（advertiseunit）不是期望的值：" + unit + "活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				return null;
			}
			String createtime = DateTimeUtils.dateToStr(d, "yyyy-MM-dd HH:mm:ss");
			
			String hql = " from Eorder where eitem.id = " + eitem.getId() + " and createtime > '" + createtime + "' and phone='" + cardNum + "'";
			int countQueryResult = eorderDao.countQueryResult(new Finder(hql));
			Integer maxNumPerCard = eitem.getMaxNumPerCard();
			int intMaxNumPerCard = maxNumPerCard == null ? 1 : maxNumPerCard;
			if (countQueryResult >= intMaxNumPerCard) {
				log.info(logString + "这张卡已经领取过" + countQueryResult + "次广告活动，不能多于" + intMaxNumPerCard + "次。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额：" + atm);
				continue;
			}
			String advRule = eitem.getAdvRule();
			if(advRule==null||advRule.trim().length()==0){
				log.error("广告活动的规则代码（advRule）为空。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				return null;
			}
			advRule = advRule.trim();
			if ("zxje".equalsIgnoreCase(advRule)) {
				double atmDouble = Double.parseDouble(atm);
				Double advMinAmnt = eitem.getAdvMinAmnt();
				if (advMinAmnt == null) {
					log.error("广告活动的最小消费金额（advMinAmnt）为空。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
					return null;
				}
				if (atmDouble < advMinAmnt) {
					log.info("本次刷卡金额不足" + advMinAmnt + "元，所以不发放。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额：" + atm);
					continue;
				}
			}else if ("mc".equalsIgnoreCase(advRule)) {
				Integer advertisemaxnum = eitem.getAdvertisemaxnum();
				if (advertisemaxnum == null) {
					log.error("广告活动的限定数量（advertisemaxnum）为空。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
					return null;
				}
				if (advertisemaxnum != 0) {
					hql = " from Eorder where eitem.id = " + eitem.getId() + " and createtime > '" + createtime + "' ";
					countQueryResult = eorderDao.countQueryResult(new Finder(hql));
					if (countQueryResult >= advertisemaxnum) {
						log.info(logString + "的前" + advertisemaxnum + "个广告活动已经发放完毕。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
						continue;
					}
				}
			}else if ("equalJe".equalsIgnoreCase(advRule)) {
				double atmDouble = Double.parseDouble(atm);
				Double advRule3Amnt = eitem.getAdvRule3Amnt();
				if (advRule3Amnt == null) {
					log.error("广告活动的消费金额（advRule3Amnt）为空。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
					return null;
				}
				if (atmDouble != advRule3Amnt) {
					log.info("本次刷卡金额没有严格等于" + advRule3Amnt + "元，所以不发放。活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额：" + atm);
					continue;
				}
			}else {
				log.error("广告活动的规则代码（advRule）不是期望的值：" + advRule + "活动名称：“" + eitem.getName() + "” 活动ID:" + eitem.getId() + "机具号：" + imei + "卡号：" + cardNum + "刷卡金额："+atm);
				return null;
			}
			List<String> codelist = createBarcode(Variable.CODE_TYPE_NORMAL, 0, 1);
			StringBuffer sb = new StringBuffer();
			sb.append(eitem.getAdvcontent());
			sb.append("@@");
			for (String str : codelist) {
				Eorder eorder = initBarcode(eitem, str);
				eorder.setPhone(cardNum);// 将卡号存储在手机字段中，因为此字段对无卡支付广告来说没有用。
				sb.append(str);
				sb.append(",");
			}
			String retstr = sb.toString();
			retstr = retstr.substring(0, retstr.length() - 1);
			log.info("发送条码： " + retstr);
			return retstr;
		}
		return null;
	}
	/**
	 * 判断今天是否在日期列表里。日期列表是一个单行的字符串，每个日期用逗号隔开，形如：2013-03-02,2013-07-10,2013-07-11
	 * @param s 日期列表，如果为空则会返回true
	 * @return 
	 */
	private boolean matchDate(String s) {
		if (s == null || s.trim().length() == 0) {
			return true;// 为空说明不需要此项限制，视为匹配。
		}
		s = s.trim();
		Set<String> set = new HashSet<String>();
		for (String as : s.split(",")) {
			set.add(as.trim());
		}
		boolean a = set.contains(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		boolean b = set.contains(new SimpleDateFormat("yyyy-M-d").format(new Date()));
		return a||b;
	}
	/**
	 * 获取今早零点
	 * @return
	 */
	private Date startOfToday() {
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.truncate(new Date(), Calendar.DATE));
		return c.getTime();
	}
	/**
	 * 获取本周一早零点
	 * @return
	 */
	private Date startOfThisMonday() {
		Calendar c = Calendar.getInstance();
		c.setTime(startOfToday());
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return c.getTime();
	}
	/**
	 * 获取本月1号早零点
	 * @return
	 */
	private Date startOfThisMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(startOfToday());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	private Eorder initBarcode(Eitem eitem,String code){
		Eorder eorder = new Eorder();
		// 保存订单
		String tid = DateTimeUtils.dateToStr(new Date(), "yyyyMMddhhmmssSSS");
		tid = tid + RandomUtil.getRandom();
		eorder.setTid(tid);
		double payment = 0;
		payment = Double.valueOf(1) * eitem.getPrice();
		String currenttime = DateTimeUtils.dateToStr(new Date(),
				"yyyy-MM-dd HH:mm:ss");
		int num = 1;
		eorder.setPayment(payment);// 实付金额
		eorder.setNum(num);// 购买商品数量
		eorder.setCreatetime(currenttime);
		eitem.setNum(eitem.getNum() - num);
		eitemDao.update(eitem);
		eorder.setRefundamount(0.0);
		eorder.setRefundnum(0);
		eorder.setOvernum(0);
		eorder.setEitem(eitem);
		eorder = eorderDao.save(eorder);
		// ================================================================
		// 生成二维码
		// ================================================================
		Barcode barcode = new Barcode();
		barcode.setEitem(eitem);// 关联淘宝商品信息
		barcode.setEorder(eorder);// 关联淘宝订单
		barcode.setCanusenum(Long.valueOf(num * eitem.getCodetimes()));
		barcode.setNumcode(code.split(",")[0]);
		barcode.setCharcode(code.split(",")[1]);
		barcode.setCodetype(Variable.YICIMA);
		barcode.setSource("OTHER");
		barcode.setCreattime(currenttime);
		String enddate = "";
		if(eitem.getCodeday()!=null && eitem.getCodeday()==0){
			enddate = eitem.getEndtime();
		}else if(eitem.getEndtime()==null || "".equals(eitem.getEndtime())){
			enddate = DateTimeUtils.getDateOfPre(eitem.getCodeday());
		}else{
			enddate = DateTimeUtils.getDateOfPre(eitem.getCodeday());
			if (enddate.compareTo(eitem.getEndtime()) > 0) {
				enddate = eitem.getEndtime();
			}
		}
		barcode.setEndate(enddate);// 有效期
		barcodeDao.save(barcode);
		return eorder;
	}
	
	private String getEitemAdvByStroe(Store store) {
		Finder finder = new Finder(" from EitemAdvStore where store.id = "+store.getId());
		List<EitemAdvStore> elist = ditemAdvStoreDao.find(finder);
		if(elist!=null && elist.size()>0){
			Eitem eitem = elist.get(0).getEitem();
			List<String>  codelist = createBarcode(Variable.CODE_TYPE_NORMAL, 0, 1);
			StringBuffer sb = new StringBuffer();
			sb.append(eitem.getAdvcontent());
			sb.append("@@");
			for(String str :codelist ){
				initBarcode(eitem,str);
				sb.append(str.split(",")[1]);
				sb.append(",");
			}
			String rutstr = sb.toString();
			return rutstr.substring(0,rutstr.length()-1);
		}else
			return null;
	}
}