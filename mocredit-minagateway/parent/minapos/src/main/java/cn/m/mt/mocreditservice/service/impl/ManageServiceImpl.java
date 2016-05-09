package cn.m.mt.mocreditservice.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;

import cn.m.common.hibernate3.Finder;
import cn.m.mt.barcodeservice.service.BarcodeService;
import cn.m.mt.barcodeservice.service.IntegralService;
import cn.m.mt.dao.PaymentDao;
import cn.m.mt.mocreditservice.bo.AmountBo;
import cn.m.mt.mocreditservice.bo.BankBo;
import cn.m.mt.mocreditservice.bo.CodeBillBo;
import cn.m.mt.mocreditservice.bo.CodePaymentBo;
import cn.m.mt.mocreditservice.bo.EitemBo;
import cn.m.mt.mocreditservice.bo.EitemListBo;
import cn.m.mt.mocreditservice.bo.IntegralBo;
import cn.m.mt.mocreditservice.bo.LoginBo;
import cn.m.mt.mocreditservice.bo.OrderBo;
import cn.m.mt.mocreditservice.bo.redPaymentBo;
import cn.m.mt.mocreditservice.bo.redVerifyBarcodeBo;
import cn.m.mt.mocreditservice.service.ManageService;
import cn.m.mt.mocreditservice.service.OrderService;
import cn.m.mt.mocreditservice.util.DesTools;
import cn.m.mt.mocreditservice.util.ErrorMessage;
import cn.m.mt.mocreditservice.util.HessianUtil;
import cn.m.mt.mocreditservice.util.HttpClientUtil;
import cn.m.mt.mocreditservice.util.IntegerUtil;
import cn.m.mt.mocreditservice.util.ResourceUtil;
import cn.m.mt.mocreditservice.util.StringUtils;
import cn.m.mt.mocreditservice.util.XmlUtil;
import cn.m.mt.po.Payment;
import cn.m.mt.service.impl.BaseServiceImpl;
import cn.m.mt.util.ASCIIUtil;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ManageServiceImpl extends BaseServiceImpl implements ManageService {
	@Value("${integralURL}")
    String integralURL;
	@Value("${codeURL}")
	String codeURL;
	@Autowired
	BarcodeService barcodeservice;
	@Autowired
	IntegralService integralservice;
	@Autowired
	PaymentDao paymentDao;
	
	private static Map<String, String> activityMap = new HashMap<String, String>() {
		private static final long serialVersionUID = -8579044176001505123L;

		{
			put("COSTAHB", "1791");
			put("TPYKF", "1699");
			put("HXYH01", "41");
			put("MS0000", "15");
			put("MS0003", "17");
			put("MS0004", "18");
			put("MS0002", "22");
			put("MS0111", "26");
			put("MS0005", "29");
		}
		
		public String get(Object key) {
			String value = super.get(key);
			if (value == null) {
				return key.toString();
			}
			return value;
		}
	};
	
	public static void main(String[] args) {
		System.out.println(activityMap.get("MS0000"));
		System.out.println(activityMap.get("qwqwqw"));
	}
	
	public IsoMessage signIn(IsoMessage response) {
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String rutstr = barcodeservice.getBatchnoByImei(imei);
		System.out.println("rutstr===="+rutstr);
		List<LoginBo> loginlist= (List<LoginBo>) XmlUtil.getBO(
				new LoginBo().getClass(), rutstr);
		LoginBo eb = loginlist.get(0);
		String batchno = null;
		String mankey = "11111111";
//		String mackey = KeyUtil.getDESKey(8);
		String mackey = "11111111";
		if ("false".equals(eb.getIsSuccess())) {
			response.setValue(39, ASCIIUtil.String2HexString("08"),
					IsoType.NUMERIC, 4);
			response.setValue(63, null,
					IsoType.ALPHA, 0);
			return response;
		}else{
			batchno = eb.getBatchno();
			if(batchno==null||batchno.equals("null"))
				batchno="000001";
			String newbatchno = String.format("%06d", Integer.parseInt(batchno)+1);
			barcodeservice.setNewBatchnoByImei(imei,newbatchno,ASCIIUtil.encode(mackey));
//			mankey = ASCIIUtil.decode(eb.getMankey());
//			mackey = ASCIIUtil.decode(eb.getMackey());
		}
		batchno = "000800"+batchno;
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		
		response.setValue(60, batchno,
				IsoType.ALPHA, batchno.length());
		
		String desstr = DesTools.str2des(mankey, mackey);
		desstr = "00" + desstr.length() / 2 + desstr;
		response.setValue(62, desstr, IsoType.ALPHA, desstr.length());
		response.setValue(63, null, IsoType.LLLVAR, 0);
		return response;
	}

	public IsoMessage signOut(IsoMessage response) {
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		return response;
	}

	public IsoMessage getActivityList(IsoMessage response) {
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		//TODO 获取活动列表
//		String rutstr = barcodeservice.getEitemListByImei(imei, null);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("enCode", imei);
		String rutstr = HttpClientUtil.post(integralURL + "activityOldSyn", paramsMap);
		System.out.println("====rutstr===="+rutstr);
		List<EitemListBo> eitemlist = (List<EitemListBo>) XmlUtil.getBO(
				new EitemListBo().getClass(), rutstr);
		EitemListBo eb = eitemlist.get(0);
		if ("false".equals(eb.getIsSuccess()) && !"没有兑换的活动".equals(eb.getResultInfo())) {
			String errorcode = ErrorMessage.getErrorCode(eb.getResultInfo());
			 if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", eb.getResultInfo());
				String s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		StringBuffer eitemliststr = new StringBuffer();
		StringBuffer eitemstr = new StringBuffer();
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		String str1 = "";
		
		for (EitemListBo eitem : eitemlist) {
			if(null != eitem.getEitemid()){
				eitemstr.append("A1");
				eitemstr.append("01");
				
				str1 = ASCIIUtil.String2HexString(eitem.getEitemname());
				eitemstr.append(IntegerUtil.intToString16(str1.length() / 2));
				eitemstr.append(str1);
				
				eitemstr.append("02");
				str1 = ASCIIUtil.String2HexString(eitem.getEitemid());
				eitemstr.append(IntegerUtil.intToString16(str1.length() / 2));
				eitemstr.append(str1);
				
				eitemstr.append("03");
				eitemstr.append("01");
				String ispayment = eitem.getIspayment();
				if(null == ispayment || "null".equals(ispayment))
					ispayment = "0";
				eitemstr.append(IntegerUtil.intToString16(1));//Integer.parseInt(ispayment)
				
				eitemstr.append("04");
				eitemstr.append("01");
				eitemstr.append(IntegerUtil.intToString16(1));//Integer.parseInt(eitem.getIszxcheck())
				
				eitemstr.append("05");
				eitemstr.append("01");
				System.out.println("====1====="+eitem.getEitemid());
				String codetimes = IntegerUtil.intToString16(Integer.parseInt(eitem
						.getCodetimes()));
				eitemstr.append(codetimes);
//				eitemstr.append("06");
//				str1 = ASCIIUtil.String2HexString(eitem.getEntname());
//				eitemstr.append(IntegerUtil.intToString16(str1.length() / 2));
//				eitemstr.append(str1);
				
			
			}
		}
		String temp = eitemstr.toString();

		eitemliststr.append(String.format("%04d", temp.length() / 2));
		eitemliststr.append(temp);
		response.setValue(5, eitemliststr.toString(), IsoType.ALPHA,
				eitemliststr.toString().length());
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		return response;
	}

	public IsoMessage getBankList(IsoMessage response) {
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		//TODO 获取活动列表
//		String rutstr = integralservice.getBank(imei);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("enCode", imei);
		String rutstr = HttpClientUtil.post(integralURL + "bankList", paramsMap);
		System.out.println("====rutstr===="+rutstr);
		List<BankBo> banklist = (List<BankBo>) XmlUtil.getBO(
				new BankBo().getClass(), rutstr);
		BankBo tempbank = banklist.get(0);
		if ("false".equals(tempbank.getIsSuccess())) {
			String errorcode = ErrorMessage
					.getErrorCode(tempbank.getErrorMsg());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", tempbank.getErrorMsg());
				String s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}

		StringBuffer eitemstr = new StringBuffer();
		for (BankBo bank : banklist) {
			eitemstr.append("A1");
			eitemstr.append("01");
			String bankname = ASCIIUtil.String2HexString(bank.getBankname());
			eitemstr.append(IntegerUtil.intToString16(bankname.length() / 2));
			eitemstr.append(bankname);
			eitemstr.append("02");
			String bankid = ASCIIUtil.String2HexString(bank.getBankid());
			eitemstr.append(IntegerUtil.intToString16(bankid.length() / 2));
			eitemstr.append(bankid);
		}
		response.setValue(5, eitemstr.toString(), IsoType.LLLVAR, eitemstr
				.toString().length());
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		return response;
	}

	public IsoMessage verifyBarcode(IsoMessage response) {
		String amcoutStr = response.getField(4).getValue().toString();
		String amcout = Integer.parseInt(amcoutStr) + "";
		String charcode = null;
		Object charcodeobj = response.getField(6);
		if (charcodeobj != null) {
			charcode =((IsoValue<String>) charcodeobj).getValue().toString();
			charcode = ASCIIUtil.decode(charcode);
		}
		String eitemid = response.getField(9).getValue().toString();
		eitemid = ASCIIUtil.decode(eitemid);
		eitemid = eitemid.replace(" ", "");
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		if ("".equals(charcode) || null == charcode) {
			String account = new String((byte[])response.getField(35).getValue());
			charcode = account.split("=")[0] + "@" + account;
		}
		String searchno = response.getField(11).getValue().toString();
		String eitemtype = response.getField(60).getValue().toString();
		String batchno = eitemtype.substring(6, 12);
		System.out.println(imei + "======" + charcode + "=====" + eitemid
				+ "=====" + amcout);
//		String rutstr = barcodeservice.barcodeVerify(imei, charcode, eitemid,
//				amcout, searchno, batchno);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("device", imei);
		paramsMap.put("code", charcode);
		paramsMap.put("searchNo", searchno);
		paramsMap.put("batchNo", batchno);
		String rutstr = HttpClientUtil.post(codeURL + "verifyCodeForOld", paramsMap);
		
		System.out.println("==rutstr==" + rutstr);
		List<EitemBo> eitemlist = (List<EitemBo>) XmlUtil.getBO(
				new EitemBo().getClass(), rutstr);
		EitemBo eitem = eitemlist.get(0);
		if ("false".equals(eitem.getIsSuccess())) {
			String errorcode = ErrorMessage.getErrorCode(eitem.getError());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", eitem.getError());
				String s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		StringBuffer orderstr = new StringBuffer();
		orderstr.append("01");
		String printtitle = ASCIIUtil.String2HexString(eitem.getPrintTitle());
		orderstr.append(IntegerUtil.intToString16(printtitle.length() / 2));
		orderstr.append(printtitle);
		orderstr.append("02");
		String entname = ASCIIUtil.String2HexString(eitem.getEntname());
		orderstr.append(IntegerUtil.intToString16(entname.length() / 2));
		orderstr.append(entname);
		orderstr.append("03");
		String mmsname = ASCIIUtil.String2HexString(eitem.getMmsName());
		orderstr.append(IntegerUtil.intToString16(mmsname.length() / 2));
		orderstr.append(mmsname);
		orderstr.append("04");
		String description = ASCIIUtil.String2HexString(eitem.getDescription());
		orderstr.append(IntegerUtil.intToString16(description.length() / 2));
		orderstr.append(description);
		orderstr.append("05");
		String validTo = ASCIIUtil.String2HexString(eitem.getValidTo());
		orderstr.append(IntegerUtil.intToString16(validTo.length() / 2));
		orderstr.append(validTo);
		orderstr.append("06");
		orderstr.append("0100");
		String advertisement = eitem.getAdvertisement();
//		if(advertisement==null || "".equals(advertisement))
//			advertisement = "0";
//		orderstr.append(advertisement);
		if("01".equals(advertisement)){
			orderstr.append("07");
			String advcharcode = ASCIIUtil.String2HexString(eitem.getAdvcharcode());
			orderstr.append(IntegerUtil.intToString16(advcharcode.length() / 2));
			orderstr.append(advcharcode);
			orderstr.append("08");
			String advnumcode = ASCIIUtil.String2HexString(eitem.getAdvnumcode());
			orderstr.append(IntegerUtil.intToString16(advnumcode.length() / 2));
			orderstr.append(advnumcode);
			orderstr.append("09");
			String advcontent = ASCIIUtil.String2HexString(eitem.getAdvcontent());
			orderstr.append(IntegerUtil.intToString16(advcontent.length() / 2));
			orderstr.append(advcontent);
		}
		String ruteitem = orderstr.toString();
		response.setValue(5, ruteitem, IsoType.LLLVAR, ruteitem.length());
		response.setValue(6, null, IsoType.LLLVAR, 0);
		eitemid = eitem.getMmsId();
		eitemid = String.format("%-10s", eitemid);
		eitemid = ASCIIUtil.String2HexString(eitemid);
		response.setValue(9, eitemid, IsoType.ALPHA, eitemid.length());
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		String posno = ASCIIUtil.String2HexString(eitem.getPosno());
		if("".equals(posno))
			posno = "0000000000"; 
		response.setValue(37, posno, IsoType.ALPHA, posno.length());
		return response;
	}

	public IsoMessage billing(IsoMessage response) {

		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String url = ResourceUtil.HESSIAN_URL;
		String amcoutStr = response.getField(4).getValue().toString();
		String amcout = Integer.parseInt(amcoutStr) + "";
		String gzStr = response.getField(11).getValue().toString();
		String eitemtype = response.getField(60).getValue().toString();
		String batchno = eitemtype.substring(6, 12);
		batchno += "," + gzStr;
		HessianUtil<OrderService> orderservice = new HessianUtil<OrderService>(
				url, OrderService.class);
		DecimalFormat df = new DecimalFormat(".00");   
		amcout = df.format(Double.parseDouble(amcout)/100); 
		String rutstr = orderservice.getHessianInt().sendmessage(amcout, imei,
				batchno);
		System.out.println("===rutstr===="+rutstr);
		List<CodeBillBo> eitemlist = (List<CodeBillBo>) XmlUtil.getBO(
				new CodeBillBo().getClass(), rutstr);
		CodeBillBo codebill = eitemlist.get(0);
		if ("false".equals(codebill.getIsSuccess())) {
			String errorcode = ErrorMessage
					.getErrorCode(codebill.getErrorMsg());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", codebill.getErrorMsg());
				String s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			 }
			if (errorcode == null || "".equals(errorcode))
				errorcode = "98";
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		String codeinfo = codebill.getCodeinfo();
		codeinfo = ASCIIUtil.String2HexString(codeinfo);
		codeinfo = String.format("%04d", codeinfo.length() / 2) + codeinfo;
		response.setValue(6, codeinfo, IsoType.NUMERIC, codeinfo.length());
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		String str = ASCIIUtil.String2HexString(codebill.getPosno());
		response.setValue(37, str, IsoType.NUMERIC, str.length());
		return response;
	}

	public IsoMessage showCardBalance(IsoMessage response) {
		String code = response.getField(6).getValue().toString();
		String imei = response.getField(41).getValue().toString();
		String rutstr = barcodeservice.showBarcodeAmount(
				ASCIIUtil.decode(imei), ASCIIUtil.decode(code));
		System.out.println("====rutstr====" + rutstr);
		List<AmountBo> amountbolist = (List<AmountBo>) XmlUtil.getBO(
				new AmountBo().getClass(), rutstr);
		AmountBo amountbo = amountbolist.get(0);
		String resultcode = "00";
		if (amountbo.getIsSuccess().equals("true")) {
			String s = ASCIIUtil.String2HexString("C"
					+ ASCIIUtil.String2Add0(
							""
									+ ((int) (Double.parseDouble(amountbo
											.getAmount()) * 100)), 12));
			response.setValue(54, s, IsoType.LLLVAR, s.length());
		} else {
			resultcode = ErrorMessage.getErrorCode(amountbo.getErrorMsg());
			if(resultcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", amountbo.getErrorMsg());
				String s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			 }
		}
		String posno = ASCIIUtil.String2HexString("000000000000");
		response.setValue(37, posno, IsoType.ALPHA, posno.length());
		response.setValue(39, ASCIIUtil.String2HexString(resultcode),
				IsoType.NUMERIC, 4);
		return response;
	}

	public IsoMessage showIntegral(IsoMessage response) {
		String account =new String((byte[])response.getField(35).getValue());
//		account = account.substring(2, response.getField(35).getLength());
		String accounts[] = account.split("D");
		account = accounts[0];
		if(accounts.length>1){
			account = account+"@"+accounts[0]+"="+accounts[1];
		}
		account = account.replace("D", "@");///"6226890031192346@160410115879960000000";
		String imei = response.getField(41).getValue().toString();
		String rutstr = integralservice.queryIntegral(ASCIIUtil.decode(imei),
				account);
		System.out.println("====rutstr====" + rutstr);
		List<IntegralBo> integralbolist = (List<IntegralBo>) XmlUtil.getBO(
				new IntegralBo().getClass(), rutstr);
		IntegralBo integralbo = integralbolist.get(0);
		String resultcode = "00";
		if (integralbo.getIsSuccess().equals("true")) {
			
			String s = ASCIIUtil.String2HexString("C"
					+ASCIIUtil.String2Add0(
							Integer.parseInt(integralbo.getPointAmt())*100+"", 12));
			System.out.println(s+"==="+s.length());
			response.setValue(54, s, IsoType.ALPHA, s.length());
		} else {
			resultcode = ErrorMessage.getErrorCode(integralbo.getErrorMsg());
			if(resultcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", integralbo.getErrorMsg());
				String s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			 }
		}
		String posno = ASCIIUtil.String2HexString("000000000000");
		response.setValue(37, posno, IsoType.ALPHA, posno.length());
		response.setValue(39, ASCIIUtil.String2HexString(resultcode),
				IsoType.NUMERIC, 4);
		return response;
	}

	public IsoMessage showOrder(IsoMessage response) {
		String p = response.getField(37).getValue().toString();
		p = ASCIIUtil.decode(p);
		String imei = response.getField(41).getValue().toString();
		String patchno = response.getField(60).getValue().toString()
				.substring(6, 12);

		imei = ASCIIUtil.decode(imei);
		HessianUtil<OrderService> orderservice = new HessianUtil<OrderService>(
				ResourceUtil.HESSIAN_URL, OrderService.class);
		String str = orderservice.getHessianInt().queryorder(p, imei, patchno);
		List<OrderBo> orderlist = (List<OrderBo>) XmlUtil.getBO(
				new OrderBo().getClass(), str);
		OrderBo orderbo = orderlist.get(0);
		if ("false".equals(orderbo.getIsSuccess())) {
			String errorcode = ErrorMessage.getErrorCode(orderbo.getErrorMsg());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", orderbo.getErrorMsg());
				String s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		StringBuffer orderstr = new StringBuffer();
		String str1 = "";
		orderstr.append("01");
		str1 = ASCIIUtil.String2HexString(ResourceUtil.ZX_PRINTTITLE);
		orderstr.append(IntegerUtil.intToString16(str1.length() / 2));
		orderstr.append(str1);
		
		orderstr.append("02");
		str1 = ASCIIUtil.String2HexString(orderbo.getCardtype());
		orderstr.append(IntegerUtil.intToString16(str1.length() / 2));
		orderstr.append(str1);
		
		orderstr.append("03");
		str1 = ASCIIUtil.String2HexString(orderbo.getCardno());
		orderstr.append(IntegerUtil.intToString16(str1.length() / 2));
		orderstr.append(str1);
		
		String temp = orderstr.toString();
		StringBuffer order5str = new StringBuffer();
		order5str.append(String.format("%04d", temp.length() / 2));
		order5str.append(temp);
		response.setValue(5, order5str.toString(), IsoType.ALPHA,
				order5str.length());
		String amtStr = orderbo.getAmt();
		int amt = (int) (Double.parseDouble(amtStr) * 100);
		amtStr = String.format("%012d", amt);
		response.setValue(4, amtStr, IsoType.ALPHA, amtStr.length());

		response.setValue(39, ASCIIUtil.String2HexString("00"), IsoType.ALPHA,
				4);
		response.setValue(38,
				ASCIIUtil.String2HexString(orderbo.getStdauthid()),
				IsoType.ALPHA, 12);
		orderservice.getHessianInt().updatePrint(orderbo.getOrderno(), imei, "1");
		return response;
	}


	public IsoMessage Revocation(IsoMessage response,int redVerifytype) {
		String flowno = null;
		String batchno = null;
		String searchno = null;
		String posno = null;
		String time = null;
		System.out.println("===redVerifytype==="+redVerifytype);
		if(redVerifytype == 1){//冲正
			String message = response.getField(60).getValue().toString();
			searchno = response.getField(11).getValue().toString();
			batchno = message.substring(6, 12);
		}else if(redVerifytype == 2){//撤销
			String message = response.getField(61).getValue().toString();
			batchno = message.substring(4, 10);
			searchno = message.substring(10, 16);
		}else if(redVerifytype == 3){//退货
			posno = response.getField(37).getValue().toString();
			posno = ASCIIUtil.decode(posno);
			String message = response.getField(61).getValue().toString();
			time = message.substring(16, 20);
		}
		
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String type = response.getField(60).getValue().toString();
		type = type.substring(4,6);
		/*O1:二维码收银
		02:权益兑换
		03:积分兑换
		04:储值卡*/
		String s = "";
		String returncode = "00";
		
		System.out.println("==posno=="+posno);
		System.out.println("==time=="+time);
		System.out.println("==batchno=="+batchno);
		System.out.println("==searchno=="+searchno);
		String rutstr = "";
		String issuccess ="";
			System.out.println("===type===="+type);
			if(type.equals("02")){//验码撤销
//				rutstr = barcodeservice.redVBarcode(flowno, imei, batchno, searchno, posno, time);
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("device", imei);
				paramsMap.put("searchNo", searchno);
				paramsMap.put("batchNo", batchno);
				redVerifyBarcodeBo retrunbo = null;
				List<redVerifyBarcodeBo> returnlist = null;
				try{
					rutstr = HttpClientUtil.post(codeURL + "revokeActivityCodeForOld", paramsMap);
					returnlist = (List<redVerifyBarcodeBo>) XmlUtil.getBO(new redVerifyBarcodeBo().getClass(), rutstr);
					retrunbo = returnlist.get(0);
					String eitemid = retrunbo.getEitemid();
					eitemid = String.format("%-10s", eitemid);
					eitemid = ASCIIUtil.String2HexString(eitemid);
					response.setValue(9, eitemid, IsoType.ALPHA, eitemid.length());
					issuccess = retrunbo.getIsSuccess();
				}catch(Exception e){
					System.out.println("new gateway (Revocation) error!");
					issuccess = "false";
					retrunbo = new redVerifyBarcodeBo();
					retrunbo.setResultInfo("交易失败,不是有效的码");
					response.setValue(63, "0", IsoType.ALPHA, "0".length());
				}
				if(!"true".equals(issuccess)){
					 s = retrunbo.getResultInfo();
					 returncode =  ErrorMessage.getErrorCode(retrunbo.getResultInfo().replace("!", ""));
					 if(returncode.equals("99")){
						 Map<String,String> data = new HashMap<String, String>();
						data.put("01", retrunbo.getResultInfo());
						String errorMsg = StringUtils.Map2TLV(data);
						response.setValue(5, errorMsg, IsoType.ALPHA, errorMsg.length());
					 }
					 response.setValue(63, "0", IsoType.ALPHA, "0".length());
				}else if(redVerifytype == 2 || redVerifytype == 3){
					Map<String,String> data = new HashMap<String, String>();
					if(redVerifytype == 3)
						data.put("01", "二维码权益退货");
					else
						data.put("01", "二维码权益撤销");
					data.put("02", retrunbo.getEitemname());
					data.put("03", retrunbo.getEitemname());
					data.put("04", retrunbo.getDescription());
					data.put("05", retrunbo.getValidto());
					data.put("06", retrunbo.getChecknum());
					String point = String.format("%012d",
							Integer.parseInt(retrunbo.getChecknum()));
					response.setValue(4, point, IsoType.ALPHA, point.length());
					s = StringUtils.Map2TLV(data);
				}
			}else if(type.equals("03")){//积分撤销
				String account = null;
				if(redVerifytype == 1 || redVerifytype == 2 || redVerifytype == 3){
					IsoValue<Object> field = response.getField(35);
					if (field != null) {
//						account = new String((byte[])field.getValue());
						account = field.getValue().toString();
						account = account.substring(0, field.getLength());
						account = account.split("D")[0];
						if("7330015000464523".equals(account))
							account= "5201080000040103";
					} else {
						Finder finder = new Finder("from Payment where 1=1");
						if (batchno != null) {
							finder.append(" and batchno=:batchno");
							finder.setParam("batchno", batchno);
						}
						if (searchno != null) {
							finder.append(" and searchno=:searchno");
							finder.setParam("searchno", searchno);
						}
						if (imei != null) {
							finder.append(" and imei=:imei");
							finder.setParam("imei", imei);
						}
						List<Payment> list = paymentDao.find(finder);
						if (list != null && list.size() == 1) {
							account = list.get(0).getPanstr();
						}
					}
				}
				System.out.println("===account==="+account);
				//TODO 撤销
//				rutstr = integralservice.revIntegral(imei, flowno,account,batchno, searchno, posno, time);
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("imei", imei);
				paramsMap.put("account", account);
				paramsMap.put("searchno", searchno);
				paramsMap.put("batchno", batchno);
				rutstr = HttpClientUtil.post(integralURL + "paymentRevokeOld", paramsMap);
				System.out.println("=====rutstr==="+rutstr);
				List<IntegralBo> returnlist = (List<IntegralBo>) XmlUtil.getBO(new IntegralBo().getClass(), rutstr);
				IntegralBo retrunbo = returnlist.get(0);
				issuccess = retrunbo.getIsSuccess();
				if(!"true".equals(issuccess)){
					 s = retrunbo.getErrorMsg();
					 returncode = ErrorMessage.getErrorCode(s.replace("!", ""));
					 if(returncode.equals("99")){
						 Map<String,String> data = new HashMap<String, String>();
						data.put("01", s);
						String errorMsg = StringUtils.Map2TLV(data);
						response.setValue(5, errorMsg, IsoType.ALPHA, errorMsg.length());
					 }
				} else if (redVerifytype == 1 || redVerifytype == 2 || redVerifytype == 3){
					String eitemid = retrunbo.getEitemid();
					eitemid = String.format("%-10s", eitemid);
					eitemid = ASCIIUtil.String2HexString(eitemid);
					response.setValue(9, eitemid, IsoType.ALPHA, eitemid.length());
					String point = String.format("%012d",
							Integer.parseInt(retrunbo.getPoint())*100);
					response.setValue(4, point, IsoType.ALPHA, point.length());
					
					/*01	小票title
					02	活动有效期
					03	活动名称
					04	交易类型
					05	积分
					06	备注*/
					Map<String,String> data = new HashMap<String, String>();
					if(redVerifytype == 3)
						data.put("01", "积分兑换退货");
					else
						data.put("01", "积分兑换撤销");
					data.put("02", retrunbo.getEitemname());
					data.put("03", retrunbo.getPayway().replace("0",""));
					s = StringUtils.Map2TLV(data);
				}
			}else if(type.endsWith("04")){
				rutstr = barcodeservice.redVerifyPayment(flowno, batchno, searchno, posno, time);
				System.out.println("===rutstr=="+rutstr);
				List<redPaymentBo> returnlist = (List<redPaymentBo>) XmlUtil.getBO(new redPaymentBo().getClass(), rutstr);
				redPaymentBo retrunbo = returnlist.get(0);
				issuccess = retrunbo.getIsSuccess();
				if(!"true".equals(issuccess)){
					 s = retrunbo.getErrorMsg();
					 returncode = ErrorMessage.getErrorCode(s.replace("!", ""));
					 if(returncode.equals("99")){
						 Map<String,String> data = new HashMap<String, String>();
						data.put("01", s);
						String errorMsg = StringUtils.Map2TLV(data);
						response.setValue(5, errorMsg, IsoType.ALPHA, errorMsg.length());
					 }
				}else if(redVerifytype == 2 || redVerifytype == 3){
					
					String eitemid = retrunbo.getEitemid();
					eitemid = String.format("%-10s", eitemid);
					eitemid = ASCIIUtil.String2HexString(eitemid);
					response.setValue(9, eitemid, IsoType.ALPHA, eitemid.length());
					String point = String.format("%012d",
							(int)(Double.parseDouble(retrunbo.getPoint()) * 100));
					response.setValue(4, point, IsoType.ALPHA, point.length());
					
					/*01	小票title
					02	有效期
					03	交易类型
					04	备注
					*/
					Map<String,String> data = new HashMap<String, String>();
					if(redVerifytype == 3)
						data.put("01", "储值卡消费退货");
					else
						data.put("01", "储值卡消费撤销");
					data.put("02", retrunbo.getValidto());
					data.put("03", retrunbo.getPaymenttype());
					data.put("04", retrunbo.getDescr());
					s = StringUtils.Map2TLV(data);
				}
				
			}
		
		if(redVerifytype == 2 || redVerifytype == 3){
			if("99".equals(returncode)){
				Map<String,String> data = new HashMap<String, String>();
				data.put("01", s);
				s = StringUtils.Map2TLV(data);
				response.setValue(5,s, IsoType.ALPHA, s.length());
			}else if("00".equals(returncode)){
				response.setValue(5,s, IsoType.ALPHA, s.length());
			}else{
				response.setValue(5,null, IsoType.ALPHA, 0);
			}
		}else{
			response.setValue(5,null, IsoType.ALPHA, 0);
		}
		response.setValue(39, ASCIIUtil.String2HexString(returncode),
				IsoType.NUMERIC, 4);
		response.setValue(6, null, IsoType.ALPHA, 0);
		return response;
	}


	public IsoMessage verifyIntegration(IsoMessage response) {
		IsoValue<String> amcoutStr = response.getField(4);
		String amcout = "";
		if (amcoutStr != null) {
			amcout = Long.parseLong(amcoutStr.getValue().toString()) / 100 + "";
			
		}

		String charcode = null;
		IsoValue<String> charcodeobj = response.getField(2);
		if (charcodeobj != null) {
			charcode = charcodeobj.getValue().toString();
			charcode = ASCIIUtil.decode(charcode);
		}
		String eitemid = response.getField(9).getValue().toString();
//		String eitemid = new String((byte[])response.getField(9).getValue());
		
		eitemid = ASCIIUtil.decode(eitemid);
		eitemid = eitemid.replace(" ", "");
		
		System.out.println("eitemid or code :" + eitemid);
		eitemid = activityMap.get(eitemid);
		System.out.println("eitemid :" + eitemid);
		
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String account = response.getField(35).getValue().toString();
//		String account = new String((byte[])response.getField(35).getValue());
		account = account.substring(0, response.getField(35).getLength());
		String[] acs = account.split("D");
		account = acs[0] + "@" + acs[0]+"="+acs[1];
		String expDate = acs[1].substring(0, 4);
		String expDateStr = expDate.substring(2, 4) + expDate.substring(0,2);
		String searchno = response.getField(11).getValue().toString();
		String eitemtype = response.getField(60).getValue().toString();
		String batchno = eitemtype.substring(6, 12);
		System.out.println(imei + "====" + account + "=====" + expDateStr + "====="+ amcout
				+ "======" + eitemid);
		//TODO 积分消费
//		String rutstr = integralservice.exchangeIntegral(imei, account, amcout,
//				eitemid, batchno, searchno);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("enCode", imei);
		paramsMap.put("activityId", eitemid);
		paramsMap.put("cardNum", acs[0]);
//		paramsMap.put("cardNum", "6226230180000893");
		paramsMap.put("cardExpDate", expDateStr);
//		paramsMap.put("cardExpDate", "4208");
		paramsMap.put("searchno", searchno);
		paramsMap.put("batchno", batchno);
		String rutstr = HttpClientUtil.post(integralURL + "paymentOld", paramsMap);
		System.out.println("==rutstr====" + rutstr);
		List<IntegralBo> integralbolist = (List<IntegralBo>) XmlUtil.getBO(
				new IntegralBo().getClass(), rutstr);
		IntegralBo integralbo = integralbolist.get(0);
		if ("false".equals(integralbo.getIsSuccess())) {
			String errorcode = ErrorMessage.getErrorCode(integralbo
					.getErrorMsg());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", integralbo
						.getErrorMsg());
				String errorMsg = StringUtils.Map2TLV(data);
				response.setValue(5, errorMsg, IsoType.ALPHA, errorMsg.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		StringBuffer orderstr = new StringBuffer();
		orderstr.append("01");
		String printtitle = ASCIIUtil.String2HexString("汇金通消费单");
		orderstr.append(IntegerUtil.intToString16(printtitle.length() / 2));
		orderstr.append(printtitle);
		orderstr.append("02");
		String eitemname = ASCIIUtil.String2HexString(integralbo.getEitemname());
		orderstr.append(IntegerUtil.intToString16(eitemname.length() / 2));
		orderstr.append(eitemname);
		orderstr.append("03");
		String type = integralbo.getPayway();
		orderstr.append(IntegerUtil.intToString16(2));
		orderstr.append(ASCIIUtil.String2HexString(type.replace("0","")));
		String ruteitem = orderstr.toString();
		String cardno = account.split("@")[0];
		String cardnolen = cardno.length()+"";
		if(cardno.length() <= 19 && cardno.length()%2 != 0){
			cardno = cardno + "0";
		}
		cardno = cardnolen + cardno;
		response.setValue(2, cardno, IsoType.ALPHA, cardno.length());

		response.setValue(5, ruteitem, IsoType.LLLVAR, ruteitem.length());
		String point = String.format("%012d",
				Integer.parseInt(integralbo.getPoint()) * 100);
		response.setValue(4, point, IsoType.ALPHA, point.length());
		response.setValue(6, null, IsoType.LLLVAR, 0);
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		String posno = ASCIIUtil.String2HexString(integralbo.getPosno());
		response.setValue(37, posno, IsoType.ALPHA, posno.length());
		return response;
	}

	public IsoMessage quryTodayOrder(IsoMessage response) {
		String url = ResourceUtil.HESSIAN_URL;
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		HessianUtil<OrderService> orderservice = new HessianUtil<OrderService>(
				url, OrderService.class);
		String patchno = response.getField(60).getValue().toString()
				.substring(6, 12);
		String rutstr = orderservice.getHessianInt().qureytodayorder(null,
				imei, patchno);
		System.out.println("====rutstr==="+rutstr);
		List<CodeBillBo> orderlist = (List<CodeBillBo>) XmlUtil.getBO(
				new CodeBillBo().getClass(), rutstr);
		CodeBillBo ordertemp = orderlist.get(0);
		if ("false".equals(ordertemp.getIsSuccess())) {
			String errorcode = ErrorMessage.getErrorCode(ordertemp
					.getErrorMsg());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", ordertemp
						.getErrorMsg());
				String errorMsg = StringUtils.Map2TLV(data);
				response.setValue(5, errorMsg, IsoType.ALPHA, errorMsg.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		StringBuffer orderstr = new StringBuffer();
		String str1 = "";
		for (CodeBillBo orderbo : orderlist) {
			if(null!=orderbo.getPosno()){
				orderstr.append("A1");
				orderstr.append("01");
				str1 = ASCIIUtil.String2HexString(orderbo.getPosno());
				orderstr.append(IntegerUtil.intToString16(str1.length() / 2));
				orderstr.append(str1);
				orderstr.append("02");
				String amtStr = orderbo.getAmt();
				int amt = (int) (Double.parseDouble(amtStr) * 100);
				amtStr = String.format("%012d", amt);
				orderstr.append("06");
				orderstr.append(amtStr);
				orderstr.append("03");
				orderstr.append("05");
				String time = orderbo.getOrdertime();
				time = time.replace("-", "").replace(":", "").replace(" ", "");
				orderstr.append(time.substring(4, 14));
			}
		}
		String orderinfo = orderstr.toString();
		String orderinfoLen = String.format("%04d", orderinfo.length() / 2);
		orderinfo = orderinfoLen + orderinfo;
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		response.setValue(5, orderinfo, IsoType.ALPHA, orderinfo.length());
		return response;
	}

	public IsoMessage codepayment(IsoMessage response) {
		IsoValue<String> amcoutStr = response.getField(4);
		String amcout = "";
		if (amcoutStr != null) {
			amcout = Double.parseDouble(amcoutStr.getValue().toString())/100 + "";
		}

		String charcode = null;
		IsoValue<String> charcodeobj = response.getField(6);
		if (charcodeobj != null) {
			charcode = charcodeobj.getValue().toString();
			charcode = ASCIIUtil.decode(charcode);
		}
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String searchno = response.getField(11).getValue().toString();
		String eitemtype = response.getField(60).getValue().toString();
		String batchno = eitemtype.substring(6, 12);
		System.out.println(imei + "====" + charcode + "=======" + amcout);
		String rutstr = barcodeservice.barcodepayment(imei, charcode, null,
				amcout, null, batchno, searchno);
		List<CodePaymentBo> orderlist = (List<CodePaymentBo>) XmlUtil.getBO(
				new CodePaymentBo().getClass(), rutstr);
		System.out.println("===rutstr===" + rutstr);
		CodePaymentBo codePayment = orderlist.get(0);
		if ("false".equals(codePayment.getSuccess())) {
			String errorcode = ErrorMessage
					.getErrorCode(codePayment.getError());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", codePayment
						.getError());
				String errorMsg = StringUtils.Map2TLV(data);
				response.setValue(5, errorMsg, IsoType.ALPHA, errorMsg.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		StringBuffer orderstr = new StringBuffer();
		orderstr.append("01");
		String str = ASCIIUtil.String2HexString(codePayment.getPrintTitle());
		orderstr.append(IntegerUtil.intToString16(str.length() / 2));
		orderstr.append(str);
		orderstr.append("02");
		String time = codePayment.getEndtime().substring(0, 10);
		time = ASCIIUtil.String2HexString(time);
		orderstr.append(IntegerUtil.intToString16(time.length() / 2));
		orderstr.append(time);
		orderstr.append("03");
		str = ASCIIUtil.String2HexString(codePayment.getRemark());
		orderstr.append(IntegerUtil.intToString16(str.length() / 2));
		orderstr.append(str);
		response.setValue(5, orderstr.toString(), IsoType.LLLVAR, orderstr
				.toString().length());
		response.setValue(6, null, IsoType.LLLVAR, 0);
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		String posno = ASCIIUtil.String2HexString(codePayment.getPosno());
		response.setValue(37, posno, IsoType.ALPHA, posno.length());
		return response;
	}

	public IsoMessage getBankEitemList(IsoMessage response) {
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
//		String rutstr = integralservice.getEitemByBank(imei, null);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("enCode", imei);
		String rutstr = HttpClientUtil.post(integralURL + "activityByBankId", paramsMap);
		System.out.println("====rutstr===="+rutstr);
		List<BankBo> banklist = (List<BankBo>) XmlUtil.getBO(
				new BankBo().getClass(), rutstr);
		BankBo tempbank = banklist.get(0);
		if ("false".equals(tempbank.getIsSuccess())) {
			String errorcode = ErrorMessage
					.getErrorCode(tempbank.getErrorMsg());
			if(errorcode.equals("99")){
				 Map<String,String> data = new HashMap<String, String>();
				data.put("01", tempbank
						.getErrorMsg());
				String errorMsg = StringUtils.Map2TLV(data);
				response.setValue(5, errorMsg, IsoType.ALPHA, errorMsg.length());
			 }
			response.setValue(39, ASCIIUtil.String2HexString(errorcode),
					IsoType.NUMERIC, 4);
			return response;
		}
		if (null != tempbank.getOuterid()) {
			StringBuffer eitemstr = new StringBuffer();
			for (BankBo bank : banklist) {
				eitemstr.append("A1");
				eitemstr.append("01");
				String eitemname = ASCIIUtil.String2HexString(bank
						.getEitemname());
				eitemstr.append(IntegerUtil.intToString16(eitemname.length() / 2));
				eitemstr.append(eitemname);
				eitemstr.append("02");
				String eitemid = ASCIIUtil.String2HexString(bank.getOuterid());//
				eitemstr.append(IntegerUtil.intToString16(eitemid.length() / 2));
				eitemstr.append(eitemid);
				eitemstr.append("03");
				String bankid = ASCIIUtil.String2HexString(bank.getBankid());
				eitemstr.append(IntegerUtil.intToString16(bankid.length() / 2));
				eitemstr.append(bankid);
				eitemstr.append("04");
//				String expointType = bank.getExpointtype();
//				if(expointType.equals("2")){
//					expointType = "3";
//				}
				String expointType = "3";// 0:"否",1:"输入积分兑换",2:"输入金额兑换",3:"固定积分"
				String expointtype = ASCIIUtil.String2HexString(expointType);
				eitemstr.append(IntegerUtil.intToString16(expointtype.length() / 2));
				eitemstr.append(expointtype);
			}
			response.setValue(5, eitemstr.toString(), IsoType.LLLVAR, eitemstr
					.toString().length());
		}
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		return response;

	}




	public IsoMessage todaySettle(IsoMessage response) {
		String eitemtype = response.getField(60).getValue().toString();
		String reqtype = eitemtype.substring(4, 6);
		String batchno = eitemtype.substring(6, 12);
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String privateStr = response.getField(48).getValue().toString();
		int totalnum = Integer.parseInt(privateStr.substring(0, 3));
		int totalAmt = Integer.parseInt(privateStr.substring(3, 15));
		int refundAmt = Integer.parseInt(privateStr.substring(15, 18));
		int refundNum = Integer.parseInt(privateStr.substring(18, 30));
		int amt = totalAmt - refundAmt;
		int num = totalnum - refundNum;
		HessianUtil<OrderService> orderservice = new HessianUtil<OrderService>(
				ResourceUtil.HESSIAN_URL, OrderService.class);
		String rutstr = null;
		System.out.println("====reqtype====="+reqtype);
		if ("01".equals(reqtype)){
			rutstr =  orderservice.getHessianInt().nocardpaySettle(imei,amt,num,batchno);//无卡支付兑换结算
		}else if ("02".equals(reqtype)){
			rutstr =  barcodeservice.barcodeSettle(imei, amt, num,batchno);//二维码兑换结算
		}else if ("03".equals(reqtype)){
			rutstr = integralservice.integrationSettle(imei, amt, num ,batchno);//积分兑换结算
		}else if ("04".equals(reqtype)){
			rutstr = barcodeservice.codepaySettle(imei, amt, num,batchno);//储值卡结算
		}
		
		System.err.println("===rutstr===="+rutstr);
//		List<SettleBo> settleList = (List<SettleBo>)  XmlUtil.getBO(new SettleBo().getClass(), rutstr);
//		privateStr = privateStr.substring(0,privateStr.length()-1);
//		SettleBo settle = settleList.get(0);
//		privateStr = privateStr+"1";
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		response.setValue(48, privateStr,IsoType.ALPHA,privateStr.length());
		String adminid = response.getField(63).getValue().toString();
		response.setValue(63, adminid,IsoType.LLLVAR,adminid.length());
		return response;
	
	}

	public IsoMessage reconciliation(IsoMessage response) {
		String eitemtype = response.getField(60).getValue().toString();
		String reqtype = eitemtype.substring(4, 6);
		String batchno = eitemtype.substring(6, 12);
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String orders = response.getField(5).getValue().toString();
		orders = ASCIIUtil.decode(orders);
		int orderlen = Integer.parseInt(orders.substring(0,2));
		orders = orders.substring(2,orders.length());
		int startlen = 0;
		int endlen = 40;
		for(int i=0;i<orderlen;i++){
			String order = orders.substring(startlen,endlen);
			String charcode = order.substring(8,28);
			String amcout = order.substring(28,40);
			barcodeservice.reconciliation(reqtype,batchno,imei,null,charcode,amcout,order.substring(2,8));
			startlen = startlen+40;
			endlen = endlen + 40;
		}
		response.setValue(5, null,IsoType.ALPHA, 0);
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		return response;
	}

	public IsoMessage healthCheckByImei(IsoMessage response) {
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		barcodeservice.healthCheckByImeiAndImsi(imei,null);
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		response.setValue(62, null,
				IsoType.ALPHA, 0);
		return response;
	}

	@Override
	public IsoMessage getEitemAdvByImei(IsoMessage response) {
		String imei = response.getField(41).getValue().toString();
		imei = ASCIIUtil.decode(imei);
		String str = barcodeservice.getEitemAdvByImei(imei);
		if(str != null){
			String strs[] = str.split("@@");
			String advcontent = strs[0];
			advcontent = ASCIIUtil.String2HexString(advcontent);
			response.setValue(5, advcontent, IsoType.LLLVAR,
					advcontent.length());
			String codes[] = strs[1].split(",");
			StringBuffer sb = new StringBuffer();
			for(String code : codes){
				sb.append("A1");
				sb.append("01");
				code = ASCIIUtil.String2HexString(code);
				sb.append(IntegerUtil.intToString16(code.length() / 2));
				sb.append(code);
			}
			
			response.setValue(6, sb.toString(), IsoType.LLLVAR,
					sb.toString().length());
		}
		response.setValue(39, ASCIIUtil.String2HexString("00"),
				IsoType.NUMERIC, 4);
		return response;
	}

	

}
