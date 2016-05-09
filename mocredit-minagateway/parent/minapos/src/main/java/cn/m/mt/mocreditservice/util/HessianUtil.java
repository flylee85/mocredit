package cn.m.mt.mocreditservice.util;

import cn.m.mt.mocreditservice.service.OrderService;

import com.caucho.hessian.client.HessianProxyFactory;



public class HessianUtil<T> {
	private String url ="";
	private Class<T> myClass;
	
	public HessianUtil(String url,Class<T> myClass){
		this.url = url;
		this.myClass=myClass;
	}
	@SuppressWarnings("unchecked")
	public T getHessianInt() {
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			return (T)factory.create(myClass, url);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;

		}

}
	public static void main(String[] args){
		String url = "http://219.238.232.140/NocardpayWS/remote/orderservice";
		HessianUtil<OrderService> orderservice = new HessianUtil<OrderService>(url,OrderService.class);
//		`
//		HessianUtil<OrderService> orderservice = new HessianUtil<OrderService>(url,OrderService.class);
		String rutstr = orderservice.getHessianInt().redVorder(null, "12345678", "0", null, null, "000000", "0428");
		System.out.println(rutstr);
	}

}
