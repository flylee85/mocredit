package cn.m.mt.barcodeservice.service;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import cn.emay.alipay.api.ApiException;
import cn.emay.alipay.api.request.CloseTradeRequest;
import cn.emay.alipay.api.request.PaymentRequest;
import cn.emay.alipay.api.request.QueryRequest;
import cn.emay.alipay.api.request.RefundRequest;
import cn.emay.alipay.api.response.CloseTradeResponse;
import cn.emay.alipay.api.response.PaymentResponse;
import cn.emay.alipay.api.response.QueryResponse;
import cn.emay.alipay.api.response.RefundResponse;
import cn.m.mt.po.Account;
import cn.m.mt.po.Aorder;
import cn.m.mt.po.Eitem;
import cn.m.mt.util.VerifyErrorException;

public interface BarcodeService {

	/**
	 * @根据密码获得可参加活动 设备端验证
	 * @参数说明 imei：门店设备编号  verifyPwd：数字二维码
	 * @author:lilonglong
	 * @createtime:2011-05-31
	 * @return String
	 */
	public String getMMSByIMEI(String imei, String verifyPwd);
	
	
	
	/**
	 * @根据日期统计设备所刷的优惠 统计
	 * @参数说明 beginDateTime：开始日期、 endDateTime：结束日期、   imei：终端号
	 * @author:lilonglong
	 * @createtime:2011-06-03
	 * @return List<Coupon>
	 */
	public String getStatByDatetimeIMEI(XMLGregorianCalendar beginDateTime,XMLGregorianCalendar endDateTime, String imei);
	

	/**
	 * 生成二维码
	 * @param type 类型
	 * @param first 起始值，一个时为0
	 * @param end 结束值，为起始值+需要的个数
	 * @return
	 */
	public List<String> createBarcode(String type, int first,int end);
	/**
	 * 生成一个二维码
	 * @param type
	 * @return
	 */
	public String createBarcode(String type);
	/**
	 * 生成一批二维码
	 * @param type
	 * @param length
	 * @return
	 */
	public List<String> createBarcode(String type, int length);
	/**
	 * Description: 专门提供会员获取优惠活动使用
	 * @Version1.0 2011-6-13 下午04:26:08 by 凡红恩（fanhongen@emay.cn）创建
	 * @param charcode 会员数字和字母组合二维码
	 * @param cid 优惠卷id
	 * @param num 二维码可使用次数
	 * @return 一次性数字二维码
	 */
	public String bindActiveToHY(String charcode,String numcode,long num,long cid);
	
	

	/**
	 * Description: 终端单码单活动验证
	 * @Version1.0 2011-6-1 下午05:07:23 by 凡红恩（fanhongen@emay.cn）创建
	 * @param charcode 数字或字母二维码
	 * @param imei  终端设备编号
	 * @return 
	 */
	public String[] terminalBarcodeVerify(String charcode,String imei,int usernum) throws VerifyErrorException;
	
	
	/**
	 * Description: web单码多活动中选择了其中的某一个活动后的验证
	 * @Version1.0 2011-6-1 下午05:20:54 by 凡红恩（fanhongen@emay.cn）创建
	 * @param numcode 数字二维码
	 * @param storeuuid 门店uuid
	 * @param couponuuid 优惠卷uuid
	 * @return
	 */
	public String webBarcodeVerify(String numcode,String storeuuid,String couponuuid) throws VerifyErrorException;


	/**
	 * Description: 更新打印结果
	 * @Version1.0 2011-6-8 下午12:09:44 by 凡红恩（fanhongen@emay.cn）创建
	 * @param imei 设备终端编号
	 * @param barcode 数字或字母组合二维码
	 * @param printno 打印编号
	 * @param isPrinted true 打印成功、false 打印失败
	 * @return 1 记录失败、0 记录成功
	 */
	public int updatePrintResult(String imei, String barcode, String printno,boolean isPrinted);
	
	/**
	 * Description: 终端更新
	 * @Version1.0 2011-07-28 下午12:09:44 by 李龙龙 创建
	 * @param currentVersionNumber 当前版本号
	 * @param mobileType 终端类型
	 * @param IMEI 终端号
	 * @return 1 "ok" 没有可更新的  2 url 返回最新版本路径
	 */
	public String GetNewVersionByMobileType(String currentVersionNumber, String mobileType,String IMEI);

	//检查心跳
	public String healthCheckByImeiAndImsi(String imei,String imsi);
	

	/**
	 * 终端单码多活动中选择了其中的某一个活动后的验证和次数
	 * @author  liuguoqing
	 * @date： 日期：2011-12-22 时间：下午05:19:07
	 * @param charcode 数字或字母二维码
	 * @param imei 终端设备编号
	 * @param couponuuid 优惠卷uuid
	 * @param printno 打印流水号，根据终端号，日期等生成的自增号
	 * @param usernum 次数
	 * @throws VerifyErrorException 
	 */
	public String[] terminalBarcodeVerify(String charcode, String imei,String couponuuid, String printno, int usernum)throws VerifyErrorException;


	/**
	 * 根据活动ID获得订单内容
	 * @author  liuguoqing
	 * @date： 日期：2012-1-12 时间：下午02:45:27
	 *@version 1.0
	 */
	public String getOrderInfoByDatetimeIMEI(String id,String beginDateTime,String endDateTime, String imei);
	
	/**
	 * * 验证中信验证码
	 * @param numcode 中信14位数字码
	 * @param imei imei号
	 * @param outid 外部编码（中信产品代码）
	 * @param num 验证数量
	 * @return
	 */
	public String[] verifyZXBarcode(String numcode,String imei,String outid,int num);


	/**
	 * 根据日期统计设备所刷的淘宝优惠 统计
	 * @author  liuguoqing
	 * @date： 日期：2012-4-28 时间：下午04:22:24
	
	 *@version 1.0
	 */
	public String getEorderStatByDatetimeIMEI(XMLGregorianCalendar beginDateTime,
			XMLGregorianCalendar endDateTime, String imei);


	/**
	 * 根据活动ID获得淘宝订单内容
	 * @author  liuguoqing
	 * @date： 日期：2012-4-28 时间：下午04:25:14
	
	 *@version 1.0
	 */
	public String getEorderInfoByDatetimeIMEI(String id, String beginDateTime,
			String endDateTime, String imei);
	/**
	 * Description: 根据数字码获取图形码。
	 */
	public String getCharcodeByNumcode(String numcode);

	
	public String[] barcodeVerify(String numcode, String charcode, String imei,
			String storeuuid, String couponuuid, String printno, int usednum,
			int checkmode) throws VerifyErrorException;
	
	/**
	 * 客服验证
	 * @param numcode 数字二维码
	 * @param imei 设备编号
	 * @param num 验证数量数
	 * @return
	 * @throws VerifyErrorException
	 */
	public String adminTaobaoCodeVerify(String numcode, String imei ,int num) throws VerifyErrorException ;

	public PaymentResponse payment(PaymentRequest paymentRequest, Aorder order, Account shopaccount) throws ApiException;
	
	public QueryResponse query(QueryRequest queryRequest) throws ApiException;
	
	public RefundResponse refund(RefundRequest refundRequest) throws ApiException;
	
	public CloseTradeResponse closeTrade(CloseTradeRequest closeTrade) throws ApiException;


	public String redVerifyBarcode(String id, String imei);
	/**
	 * 撤销
	  * RevocationBarcode 
	  * @param imei
	  * @param batchno
	  * @param searchno
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-25 下午5:07:20
	 */
	public String redVerifyBarcodeBySearchno( String imei,String batchno,String searchno);
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
	public String redVerifyBarcodeByPosno( String imei,String posno,String time);

	/**
	 * 回滚验证
	 * @param charcode
	 */
	public void redVerifyBarcode(String charcode);

	public String barcodepayment(String imei, String code, String oid,
			String amount,String storeid);


	/**
	 * 储值卡消费撤销
	  * refundpay 
	  * @param orderid
	  * @param shopaccount
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-25 上午10:55:39
	 */
	public String refPayment(String orderid);
	
	/**
	 * 撤销
	  * RevocationBarcode 
	  * @param imei
	  * @param batchno
	  * @param searchno
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-25 下午5:07:20
	 */
	public String redVerifyPaymentBySearchno(String batchno,String searchno);
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
	public String redVerifyPaymentByPosno(String posno,String time);
	
	public String refundpay(String orderid, Account shopaccount);



	public String makeupprint(String barcode, String datetime, String imei);



	public String getEitemListByImei(String imei, String eitemids);



	public String barcodeVerify(String imei, String charcode, String eitemid,
			String usernum);



	public String getEitemBarcodeByImei(String imei);



	public String updateZJBarcodeStatus(String imei, String eitemid,
			String barcodeids);



	public String revocationExchange(String imei, String id);



	public String getDevicePwd(String imei);

	public String showBarcodeAmount(String imei,String code);



	public String barcodeVerify(String imei, String charcode, String eitemid,
			String amcout, String searchno, String batchno);
	//撤销冲正权益
	public String redVBarcode(String id, String imei,String batchno,String searchno,String posno,String time);
	//撤销冲正储值卡支付
	public String redVerifyPayment(String orderid,String batchno,String searchno,String posno,String time);


	public String barcodepayment(String imei, String charcode, String oid ,
			String amcout, String storeid, String batchno, String searchno);



	public String barcodeSettle(String imei, int amt, int num, String batchno);



	public String codepaySettle(String imei, int amt, int num, String batchno);



	public void reconciliation(String reqtype, String batchno, String imei,
			String posno, String charcode, String amcout, String searchno);



	String getBatchnoByImei(String imei);



	public void setNewBatchnoByImei(String imei, String batchno, String mackey);


	public String getEitemAdvByImei(String imei);

	public String getEitemAdvByImeiAndCard(String imei, String cardNum, String atm);


	
}
