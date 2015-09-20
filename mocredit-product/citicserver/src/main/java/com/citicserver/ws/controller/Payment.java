package com.citicserver.ws.controller;

import javax.jws.WebParam;

public interface Payment {
	String login(@WebParam(name = "paraXML") String paraXML);

	String logout(@WebParam(name = "paraXML") String paraXML);

	String changePassword(@WebParam(name = "paraXML") String paraXML);

	String dividedPayment(@WebParam(name = "paraXML") String paraXML);

	String dividedPaymentReversal(@WebParam(name = "paraXML") String paraXML);

	String hirePurchaseReturn(@WebParam(name = "paraXML") String paraXML);

	String getQuestResult(@WebParam(name = "paraXML") String paraXML);

	String confirmInfo(@WebParam(name = "paraXML") String paraXML);

	String checkAccount(@WebParam(name = "paraXML") String paraXML);

	String MaintainSession(@WebParam(name = "paraXML") String paraXML);

	String dowanload(@WebParam(name = "paraXML") String paraXML);

	String upload(@WebParam(name = "paraXML") String paraXML);

	String pareq(@WebParam(name = "paraXML") String paraXML);

	String payment(@WebParam(name = "paraXML") String paraXML);
}
