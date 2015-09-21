package com.citicserver.ws.controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.citicserver.ws.util.DateTimeUtils;

@WebService
public class PaymentController implements Payment {

	@Override
	public String login(@WebParam(name = "paraXML") String paraXML) {
		String result = getTemplate("login");
		Document resultDoc = null;
		try {
			resultDoc = DocumentHelper.parseText(paraXML);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = resultDoc.getRootElement();
		result = String.format(result, root.element("terminalID").getText(), DateTimeUtils.getDate("yyyyMMdd"),
				DateTimeUtils.getDate("HHmmss"), root.element("merchantID").getText(),
				root.element("merchantName").getText(), root.element("merchantType").getText(),
				root.element("password").getText());
		return result;
	}

	@Override
	public String logout(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changePassword(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dividedPayment(@WebParam(name = "paraXML") String paraXML) {
		String result = getTemplate("dividedpayment");
		Document resultDoc = null;
		try {
			resultDoc = DocumentHelper.parseText(paraXML);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = resultDoc.getRootElement();
		String terminalID = root.element("terminalID").getText();
		String posID = root.element("posID").getText();
		String posTime = root.element("posTime").getText();
		String merchantID = root.element("merchantID").getText();
		String merchantName = root.element("merchantName").getText();
		String batchNo = root.element("batchNo").getText();
		String serialNo = root.element("serialNo").getText();
		String orderID = root.element("orderID").getText();
		String pan = root.element("pan").getText();
		String productType = root.element("productType").getText();
		String productNum = root.element("productNum").getText();
		String payWay = root.element("payWay").getText();
		String transAmt = root.element("transAmt").getText();
		String currCode = root.element("currCode").getText();
		String dividedNum = root.element("dividedNum").getText();
		String inputType = root.element("inputType").getText();
		String posConditionCode = root.element("posConditionCode").getText();
		result = String.format(result, terminalID, posID, posTime, DateTimeUtils.getDate("yyyyMMdd"),
				DateTimeUtils.getDate("HHmmss"), root.element("merchantID").getText(), merchantID, merchantName,
				batchNo, serialNo, orderID,pan,productType,productNum,payWay,transAmt,currCode,currCode,dividedNum,inputType,posConditionCode,DateTimeUtils.getDate("yyyyMMddHHmmssSSS"));
		return result;
	}

	@Override
	public String dividedPaymentReversal(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hirePurchaseReturn(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestResult(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String confirmInfo(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkAccount(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String MaintainSession(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dowanload(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upload(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pareq(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String payment(@WebParam(name = "paraXML") String paraXML) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getTemplate(String template) {
		StringBuilder result = new StringBuilder("");
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("wstemplate/" + template + ".tpl").getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();

	}
}
