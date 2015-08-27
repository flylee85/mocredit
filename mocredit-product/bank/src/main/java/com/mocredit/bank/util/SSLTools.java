package com.mocredit.bank.util;

import java.security.Security;

import com.mocredit.bank.service.PaymentServices;
import com.mocredit.bank.service.impl.PaymentServicesSoapBindingStub;

public class SSLTools {
	private static PaymentServices services = null;

	/**
	 * 获取SSLLocator
	 * 
	 * @param keyStore
	 *            私钥
	 * @param keyStorePassword
	 *            私钥密码
	 * @param keyStoreType
	 *            "JKS","PKCS12"
	 * @param trustStore
	 *            公钥(必须把根证书同时导入)
	 * @param trustStorePassword
	 *            公钥存储密码
	 * @param trustStoreType
	 *            "JKS","PKCS12"
	 * @return
	 */
//	public static PaymentServicesServiceLocator getSSLLocator(String keyStore,
//			String keyStorePassword, String keyStoreType, String trustStore,
//			String trustStorePassword, String trustStoreType) {
//		if (locator == null) {
//			SSLClientAxisEngineConfig axisConfig = new SSLClientAxisEngineConfig();
//			axisConfig.setProtocol("TLS");
//			axisConfig.setAlgorithm("SunX509");
//			axisConfig.setKeyStore(keyStore);
//			axisConfig.setKeyStoreType(keyStoreType);
//			axisConfig.setKeyStorePassword(keyStorePassword);
//			axisConfig.setTrustStore(trustStore);
//			axisConfig.setTrustStoreType(trustStoreType);
//			axisConfig.setTrustStorePassword(trustStorePassword);
//			locator = new PaymentServicesServiceLocator(axisConfig);
//			locator.setMaintainSession(true);
//		}
//		return locator;
//	}

	/**
	 * 获取SSLServices
	 * 
	 * @param keyStore
	 *            私钥
	 * @param keyStorePassword
	 *            私钥密码
	 * @param keyStoreType
	 *            "JKS","PKCS12"
	 * @param trustStore
	 *            公钥(必须把根证书同时导入)
	 * @param trustStorePassword
	 *            公钥存储密码
	 * @param trustStoreType
	 *            "JKS","PKCS12"
	 * @param serviceURL
	 *            service地址
	 * @return
	 */
	public static PaymentServices getSSLService(String keyStore,
			String keyStorePassword, String keyStoreType, String trustStore,
			String trustStorePassword, String trustStoreType, String serviceURL) {
		if (services == null) {
			setSSLProperty(keyStore, keyStorePassword, keyStoreType, trustStore, trustStorePassword, trustStoreType);
			services=new PaymentServicesSoapBindingStub(serviceURL);
		}
		return services;
	}
	
	/**设置https环境
	 * java环境下设置SSL环境
	 * 路径参数需要根据实际情况修改
	 */
	private static void setSSLProperty(String keyStore,
			String keyStorePassword, String keyStoreType, String trustStore,
			String trustStorePassword, String trustStoreType) {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("java.protocol.handler.pkgs",
				"com.sun.net.ssl.internal.www.protocol");
		System.setProperty("javax.net.ssl.keyStore", keyStore);
		System
				.setProperty("javax.net.ssl.trustStore",
						trustStore);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
		System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
		System.setProperty("javax.net.ssl.trustStoreType",trustStoreType);
	}
	
}
