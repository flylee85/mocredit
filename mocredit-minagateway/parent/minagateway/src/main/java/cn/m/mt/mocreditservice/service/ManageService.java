package cn.m.mt.mocreditservice.service;

import cn.m.mt.service.BaseService;

import com.solab.iso8583.IsoMessage;

public interface ManageService extends BaseService {

	IsoMessage signIn(IsoMessage m);

	IsoMessage signOut(IsoMessage response);

	IsoMessage getActivityList(IsoMessage response);
	/**
	 * 储值卡查询余额
	  * showCardBalance 
	  * @param response
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-17 上午10:27:01
	 */
	IsoMessage showCardBalance(IsoMessage response);
	/**
	 * 积分查询
	  * showIntegral 
	  * @param response
	  * @return 
	  * @return String 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-18 下午2:14:05
	 */
	IsoMessage showIntegral(IsoMessage response);

	IsoMessage getBankList(IsoMessage response);

	IsoMessage verifyBarcode(IsoMessage response);

	IsoMessage billing(IsoMessage response);
	/**
	 * 查询订单状态
	  * showOrder 
	  * @param response
	  * @return 
	  * @return IsoMessage 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-22 下午1:51:53
	 */
	IsoMessage showOrder(IsoMessage response);

	IsoMessage verifyIntegration(IsoMessage response);

	IsoMessage quryTodayOrder(IsoMessage response);

	IsoMessage codepayment(IsoMessage response);

	IsoMessage getBankEitemList(IsoMessage response);
	/**
	 * 撤销,冲正,退货
	  * Revocation 
	  * @param response
	  * @param type 1:冲正;2:撤销;3:退货
	  * @return 
	  * @return IsoMessage 
	  * @author 刘强 416063607@qq.com
	  * @date 2013-4-26 下午2:58:36
	 */
	IsoMessage Revocation(IsoMessage response,int type);


	IsoMessage todaySettle(IsoMessage response);

	IsoMessage reconciliation(IsoMessage response);


	IsoMessage healthCheckByImei(IsoMessage response);

	IsoMessage getEitemAdvByImei(IsoMessage response);
	
}
