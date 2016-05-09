package cn.m.mt.charge.service;

import cn.m.common.page.Pagination;
import cn.m.mt.charge.service.bo.AccountBo;
import cn.m.mt.po.Account;
import cn.m.mt.po.Charge;
import cn.m.mt.po.Enterprise;
import cn.m.mt.po.Shop;
import cn.m.mt.po.Shoporder;
import cn.m.mt.po.User;
import cn.m.mt.service.BaseService;


public interface ChargeService extends BaseService {
	
	/**
	 * 充值
	  * createCharge 
	  * @param account 账户
	  * @param amount 充值金额
	  * @param status 状态
	  * @param source 来源
	  * @param shoporder 本次充值关联的订单
	  * @return 
	  * @return Charge 
	  * @author 刘强 416063607@qq.com
	  * @date 2012-7-26 下午4:34:15
	 */
	public Charge createCharge(Account account,double amount,int status,String source,Shoporder shoporder);
	/**
	 * 确认订单
	  * submitCharge 
	  * @param tid 流水号 
	  * @param status 确认状态
	  * @param source 来源
	  * @return 
	  * @return Charge 
	  * @author 刘强 416063607@qq.com
	  * @date 2012-7-26 下午4:36:01
	 */
	public Charge submitCharge(String tid,int status,String source,String paymenttype);
	
	/**
	 * 支付
	  * payment 
	  * @param outaccount 支出账户
	  * @param inaccount 收入账户
	  * @param amount 金额
	  * @param name 名称 
	  * @param descr 明细
	  * @param shoporder 交易订单
	  * @return 
	  * @return Charge 
	  * @author 刘强 416063607@qq.com
	  * @date 2012-7-26 下午4:45:58
	 */
	public Charge payment(Account outaccount,Account inaccount,double amount,String name,String descr,Shoporder shoporder);
	
	/**
	 * 创建账户
	  * createAccount 
	  * @param user 用户
	  * @param shop 商家
	  * @param enterprise 企业
	  * @param pwd 明码密码
	  * @return Account （错误返回null）
	  * @author 刘国庆
	 */
	public Account createAccount(User user,Shop shop,Enterprise enterprise,String pwd);
	
	/**
	 * 更新支付密码
	  * updateAccountPwd 
	  * @param account 账户
	  * @param pwd 明码新密码
	  * @return int（0:失败， 1:成功） 
	  * @author 刘国庆
	 */
	public int updateAccountPwd(Account account,String pwd);
	
	/**
	 * 根据用户名和密码获得账户，
	  * getAccount 
	  * @param account 账户(密码为明码)
	  * @return AccountBo（错误返回null）
	  * @author 刘国庆
	 */
	public AccountBo getAccount(Account account);
	
	
	/**
	 * 根据用户名和密码获得账户，
	  * getChargePage 
	  * @param charge 交易明细（全部状态传入-1，全部类型传入-1）
	  * @param starttime 开始时间 
	  * @param endtime 结束时间
	  * @param pageNo 分页
	  * @return Pagination<Charge>
	  * @author 刘国庆
	 */
	public Pagination<Charge> getChargePage(Charge charge,String starttime,String endtime,int pageNo,String key,String value);
	
	
	/**
	 * 根据用户名和密码获得账户，
	  * getChargePage 
	  * @param charge 交易明细（全部状态传入-1，全部类型传入-1）
	  * @param pageNo 分页
	  * @return Pagination<Charge>
	  * @author 刘国庆
	 */
	public Pagination<Charge> getChargePage(Charge charge,int pageNo); 
	
	/**
	 * 根据tid获得charge的详细信息
	  * getChargePage 
	  * @param tid 分页
	  * @return Charge
	  * @author 刘国庆
	 */
	public Charge getChargeByTid(String tid); 
}
