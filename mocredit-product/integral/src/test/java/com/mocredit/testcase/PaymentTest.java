package com.mocredit.testcase;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PaymentTest {
    @Parameters({"baseUrl","paymentUrl"})
	@Test
	public void testPayment(String baseUrl,String paymentUrl){
		System.out.println("###"+baseUrl+paymentUrl+"###");
	}
}
