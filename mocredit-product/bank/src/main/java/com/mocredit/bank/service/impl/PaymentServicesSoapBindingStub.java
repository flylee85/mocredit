
package com.mocredit.bank.service.impl;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.mocredit.bank.entity.BankLog;
import com.mocredit.bank.persistence.BankLogMapper;
import com.mocredit.bank.service.PaymentServices;
import com.mocredit.bank.util.Variable;
import com.mocredit.base.util.Banks;
import com.mocredit.base.util.SpringContextUtils;

@org.springframework.stereotype.Service("soapStub")
public class PaymentServicesSoapBindingStub extends org.apache.axis.client.Stub implements PaymentServices {
	private static Service service = new Service();
	private String endPoint;
	private BankLogMapper bankLogMapper;

	public PaymentServicesSoapBindingStub() {
		super();
	}

	public PaymentServicesSoapBindingStub(String serviceURL) {
		this.endPoint = serviceURL;
	}

	@Override
	public String maintainSession(String paraXML) throws RemoteException {
		return call("maintainSession", paraXML, "", 0);
	}

	@Override
	public String getDynamicPwd(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkAccount(String paraXML) throws RemoteException {
		return call("checkAccount", paraXML, ""	, 0);
	}

	@Override
	public String dividedPayment(String paraXML, String cardNum, int requestId) throws RemoteException {
		return call("dividedPayment", paraXML, cardNum, requestId);
	}

	@Override
	public String changePassword(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String logout(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upload(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String settltment(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestResult(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String confirmInfo(String paraXML,String cardNum,int requestId) throws RemoteException {
		return call("confirmInfo", paraXML, cardNum, requestId);
	}

	@Override
	public String hirePurchaseReturn(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String download(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dividedPaymentReversal(String paraXML,String cardNum,int requestId) throws RemoteException {
		return call("dividedPaymentReversal", paraXML, cardNum, requestId);
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public String login(String paraXML, String cardNum, int requestId) throws RemoteException {
		return call("login", paraXML, cardNum, requestId);
	}

	/**
	 * 调用webservice
	 * 
	 * @param method
	 * @param paramXml
	 * @return
	 * @throws RemoteException
	 */
	private String call(String method, String paramXml, String cardNum, int requestId) throws RemoteException {
		Call call;
		try {
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			e.printStackTrace();
			return "";
		}
		call.setTargetEndpointAddress(endPoint);
		call.setMaintainSession(true);
		call.addParameter("paraXML", org.apache.axis.Constants.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.Constants.XSD_STRING);
//		call.setUseSOAPAction(true);
//		call.setSOAPActionURI("http://service.payment.citiccard.com");
		call.setOperationName(new QName("http://controller.ws.citicserver.com/",method));
		String resultA = (String) call.invoke(new Object[] { paramXml });

		
		if(null==bankLogMapper){
			bankLogMapper=SpringContextUtils.getBean("bankLogMapper");
		}
		// 记录银行请求日志
		BankLog bank = new BankLog();
		bank.setCardNum(cardNum);
		bank.setBank(Banks.CITIC.getName());
		bank.setUrl(Variable.ZXWSURL);
		bank.setOperate(method);
		bank.setRequestId(requestId);
		bank.setReqParam(paramXml);
		bank.setRespResult(resultA);
		bankLogMapper.save(bank);
		return resultA;
	}

}
