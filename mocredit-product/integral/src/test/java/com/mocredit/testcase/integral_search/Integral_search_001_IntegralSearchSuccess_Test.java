package com.mocredit.testcase.integral_search;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Integral_search_001_IntegralSearchSuccess_Test {
    @Parameters({"baseUrl","paymentUrl"})
	@Test
	public void testPayment(String baseUrl,String paymentUrl){
		System.out.println("###"+baseUrl+paymentUrl+"###");
	}
}
