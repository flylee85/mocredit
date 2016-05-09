package cn.m.mt.barcodeservice.util;

import java.io.IOException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


public class PostUtils {
	private static Client client = Client.create();
	public static String sendBarcodeStatus(String url) throws IOException{
		WebResource res = client.resource(url);
		return res.post(String.class);
}
	
}