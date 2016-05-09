package cn.m.mt.barcodeservice.service;

public interface IntegralService {

	String getBank(String imei);

	String getEitemByBank(String imei, String bankid);

	String exchangeIntegral(String imei, String account, String integral,
			String outerid);
	/**
	 * 积分撤销
	  * revocationIntegral 
	  * @param imei
	  * @param orderid
	  * @param account
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-25 上午10:47:35
	 */
	String revocationIntegral(String imei, String orderid,String account);
	/**
	 * 当天撤销
	  * RevocationIntegral 
	  * @param imei
	  * @param batchno
	  * @param searchno
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-25 下午5:44:17
	 */
	public String redVerifyIntegralBySearchno( String imei,String account,String batchno,String searchno);
	/**
	 * 退货
	  * Returngoods 
	  * @param imei
	  * @param posno
	  * @param time
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-25 下午5:07:28
	 */
	public String redVerifyIntegralByPosno( String imei,String account,String posno,String time);
	String queryIntegral(String imei, String account);

	String getPaymentCount(String imei,String outerid, String starttime, String endtime);

	String getPaymentListByEitem(String imei, String outerid, String starttime,
			String endtime);

	String exchangeIntegral(String imei, String account, String amcout,
			String eitemid, String batchno, String searchno);
	
	public String revIntegral(String imei, String orderid,String account,String batchno,String searchno,String posno,String time);

	String integrationSettle(String imei, int amt, int num, String batchno);
	
}
