package cn.m.mt.restbarcodeservice.controller;

import cn.m.mt.barcodeservice.service.BarcodeService;
import cn.m.mt.barcodeservice.service.IntegralService;
import cn.m.mt.restbarcodeservice.args.Args;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class CallController {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
	BarcodeService bs;
	@Autowired
	IntegralService is;

	@RequestMapping(value = "call", method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String printWelcome(String arg_json) throws Exception {
		Args a = readJsonString(arg_json, Args.class);
		if (a==null){
			return "";
		}
        String name = a.getMethodName();
        log.info("============methodName============:"+name);
        if ("healthCheckByImeiAndImsi".equals(name)) {
			return objToJson(bs.healthCheckByImeiAndImsi(a.getImei(), a.getImsi()));
		}
		if ("getEitemListByImei".equals(name)) {
			return objToJson(bs.getEitemListByImei(a.getImei(), a.getEitemids()));
		}
		if ("showBarcodeAmount".equals(name)) {
			return objToJson(bs.showBarcodeAmount(a.getImei(), a.getCode()));
		}
		if ("barcodeVerify6".equals(name)) {
			return objToJson(bs.barcodeVerify(a.getImei(), a.getCharcode(), a.getEitmid(),
					a.getAmcout(), a.getSearchno(), a.getBatchno()));
		}
		if ("redVBarcode".equals(name)) {
			return objToJson(bs.redVBarcode(a.getId(), a.getEitmid(), a.getBatchno(), a.getSearchno(), a.getPosno(),a.getTime()));
		}
		if ("redVerifyPayment".equals(name)) {
			return objToJson(bs.redVerifyPayment(a.getOrderid(), a.getBatchno(), a.getSearchno(), a.getPosno(), a.getTime()));
		}
		if ("barcodepayment7".equals(name)) {
			return objToJson(bs.barcodepayment(a.getImei(), a.getCharcode(), a.getOid(), a.getAmcout(), a.getStoreid(),
					a.getBatchno(), a.getSearchno()));
		}
		if ("barcodeSettle".equals(name)) {
			return objToJson(bs.barcodeSettle(a.getImei(), a.getAmt(), a.getNum(), a.getBatchno()));
		}
		if ("codepaySettle".equals(name)) {
			return objToJson(bs.codepaySettle(a.getImei(), a.getAmt(), a.getNum(), a.getBatchno()));
		}
		if ("reconciliation".equals(name)) {
			bs.reconciliation(a.getReqtype(), a.getBatchno(), a.getImei(),
					a.getPosno(), a.getCharcode(), a.getAmcout(), a.getSearchno());
			return "";
		}
		if ("getBatchnoByImei".equals(name)) {
			return objToJson(bs.getBatchnoByImei(a.getImei()));
		}
		if ("setNewBatchnoByImei".equals(name)) {
			bs.setNewBatchnoByImei(a.getImei(), a.getBatchno(), a.getMackey());
			return "";
		}
		if ("getEitemAdvByImei".equals(name)) {
			return objToJson(bs.getEitemAdvByImei(a.getImei()));
		}
		
		if ("getBank".equals(name)) {
			return objToJson(is.getBank(a.getImei()));
		}
		if ("getEitemByBank".equals(name)) {
			return objToJson(is.getEitemByBank(a.getImei(), a.getBankid()));
		}
		if ("queryIntegral".equals(name)) {
			return objToJson(is.queryIntegral(a.getImei(), a.getAccount()));
		}
		if ("exchangeIntegral".equals(name)) {
			return objToJson(is.exchangeIntegral(a.getImei(), a.getAccount(), a.getAmcout(),
					a.getEitmid(), a.getBatchno(), a.getSearchno()));
		}
		if ("revIntegral".equals(name)) {
			return objToJson(is.revIntegral(a.getImei(), a.getOrderid(), a.getAccount(), a.getBatchno(),
					a.getSearchno(), a.getPosno(), a.getTime()));
		}
		if ("integrationSettle".equals(name)) {
			return objToJson(is.integrationSettle(a.getImei(), a.getAmt(), a.getNum(), a.getBatchno()));
		}
		return "";
	}

	private <T> T readJsonString(String j, Class<T> expectType) {
		try {
			return new ObjectMapper().readValue(j, expectType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String prefix = "" + (char) 34;
	private String objToJson(Object o) {
		if (o == null) {
			return null;
		}
		try {
			String s = new ObjectMapper().writeValueAsString(o);
			return s.startsWith(prefix)&& s.endsWith(prefix)?s.substring(1,s.length()-1):s;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		Args a = new Args();
		a.setMethodName("verifyZXBarcode");
		String s = callBarcodeservice(a, String.class);
		System.out.println(s);
	}

	private static <T> T callBarcodeservice(Args a, Class<T> expectType) {
		String url = "http://localhost:8080/call";
		try {
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("arg_json", new ObjectMapper().writeValueAsString(a));
			return new RestTemplate().postForObject(url, map, expectType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}