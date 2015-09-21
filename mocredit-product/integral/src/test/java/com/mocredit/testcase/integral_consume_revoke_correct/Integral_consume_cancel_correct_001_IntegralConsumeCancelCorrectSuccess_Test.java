package com.mocredit.testcase.integral_consume_revoke_correct;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Integral_consume_cancel_correct_001_IntegralConsumeCancelCorrectSuccess_Test {
    @Parameters({"baseUrl","paymentUrl"})
	@Test
	public void testPayment(String baseUrl,String paymentUrl){
		System.out.println("###"+baseUrl+paymentUrl+"###");
	}
}
