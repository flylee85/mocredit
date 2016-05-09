package cn.m.mt.mocreditservice.service;

public interface OrderService {

	String sendmessage(String p, String imei, String mac);

	String queryorder(String p, String imei, String mac);

	String qureytodayorder(String p, String imei, String mac);

	String loginsign(String p, String imei, String mac);

	String updatePrint(String p, String imei, String mac);

	String updatePassword(String p, String imei, String mac);

	String orderReversal(String p, String imei, String mac);
	

	String getIntegralOrderList(String p, String imei);
	/**
	 * 撤销二维码支付
	 * @param p
	 * @param imei
	 * @param mac
	 * @param batchno
	 * @param searchno
	 * @param posno
	 * @param time
	 * @return
	 */
	public String redVorder(String p, String imei, String mac, String batchno,
			String searchno, String posno, String time);

	public String nocardpaySettle(String imei, int amt, int num,String batchno);
	
	public String getBatchnoByImei(String imei);
	
	public void setNewBatchnoByImei(String imei, String newbatchno,String mackey);
	
	public void setNewBatchnoByImei(String imei, String newbatchno);
}
