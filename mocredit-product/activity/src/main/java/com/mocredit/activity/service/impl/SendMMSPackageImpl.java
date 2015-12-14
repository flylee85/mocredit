package com.mocredit.activity.service.impl;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.activity.service.SendMMSPackage;
import com.wemediacn.mms.MMSServiceLocator;
import com.wemediacn.mms.MMSServiceSoap;

@Service
public class SendMMSPackageImpl implements SendMMSPackage {
	private static final MMSServiceLocator mmsserviceLocator = new MMSServiceLocator();
	public String  sendMMSPackage(String packagexml,String mmstoken) {
		System.out.println("=====mmstoken===="+mmstoken);
		System.out.println("=====packagexml===="+packagexml);
		String result="";
		try {
			MMSServiceSoap mmsservice = mmsserviceLocator
					.getMMSServiceSoap();
			 result= mmsservice.postMMSMessage(packagexml, mmstoken);
		} catch (ServiceException e) {
			e.printStackTrace();
			return result;
		} catch (RemoteException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

}