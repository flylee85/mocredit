package com.mocredit.testcase.integral_consume_correct;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Integral_consume_correct_001_IntegralConsumeCorrectSuccess_Test {
    @Parameters({"baseUrl","paymentUrl"})
	@Test
	public void testPayment(String baseUrl,String paymentUrl){
		System.out.println("###"+baseUrl+paymentUrl+"###");
	}
}
