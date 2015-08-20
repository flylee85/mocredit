
package com.mocredit.integral.service.impl;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.mocredit.integral.service.PaymentServices;

public class PaymentServicesSoapBindingStub extends org.apache.axis.client.Stub implements PaymentServices {
	private  static Service service = new Service();
	private String endPoint;

	public PaymentServicesSoapBindingStub(String serviceURL) {
		this.endPoint = serviceURL;
	}

	@Override
	public String maintainSession(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDynamicPwd(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkAccount(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dividedPayment(String paraXML) throws RemoteException {
		return call("dividedPayment", paraXML);
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
	public String confirmInfo(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
	public String dividedPaymentReversal(String paraXML) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String paraXML) throws RemoteException {
		return call("login", paraXML);
	}

	/**
	 * 调用webservice
	 * 
	 * @param method
	 * @param paramXml
	 * @return
	 * @throws RemoteException
	 */
	private String call(String method, String paramXml) throws RemoteException {
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
		call.setUseSOAPAction(true);
		call.setSOAPActionURI("http://service.payment.citiccard.com");

		call.setOperationName(method);
		String resultA = (String) call.invoke(new Object[] { paramXml });
		return resultA;
	}

}
