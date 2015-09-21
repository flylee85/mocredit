package com.mocredit.testcase.activity_synchronization;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Activity_synchronization_001_ActivitySynchronizationSuccess_Test {
    @Parameters({"baseUrl","paymentUrl"})
	@Test
	public void testPayment(String baseUrl,String paymentUrl){
		System.out.println("###"+baseUrl+paymentUrl+"###");
	}
}
