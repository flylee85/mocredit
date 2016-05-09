package cn.m.mt.charge.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m.common.hibernate3.Finder;
import cn.m.common.page.Pagination;
import cn.m.mt.charge.service.ChargeService;
import cn.m.mt.charge.service.bo.AccountBo;
import cn.m.mt.charge.util.ChargeVariable;
import cn.m.mt.dao.AccountDao;
import cn.m.mt.dao.ChargeDao;
import cn.m.mt.po.Account;
import cn.m.mt.po.Charge;
import cn.m.mt.po.Enterprise;
import cn.m.mt.po.Shop;
import cn.m.mt.po.Shoporder;
import cn.m.mt.po.User;
import cn.m.mt.service.impl.BaseServiceImpl;
import cn.m.mt.util.DateTimeUtils;
import cn.m.mt.util.MD5Helper;
@Service
@Transactional
public class ChargeServiceImpl extends BaseServiceImpl  implements ChargeService {
	@Autowired
	private AccountDao accountdao;
	@Autowired
	private ChargeDao chargedao;
	
	public Charge createCharge(Account account, double amount, int status,
			String source,Shoporder shoporder) {
		return this.createCharge(account, amount, status, ChargeVariable.TYPE_INCOME, source, shoporder, "充值", "", null);
	}

	public Charge submitCharge(String tid, int status, String source,String paymenttype) {
		Charge charge = chargedao.findUniqueByProperty("tid", tid);
		if(charge!=null){
			charge.setStatus(status);
			if(source!=null&&!"".equals(source)){
				charge.setSource(source);
			}
			if(paymenttype!=null&&!"".equals(paymenttype)){
				charge.setPaymenttype(paymenttype);
			}
			if(status==ChargeVariable.CHARGE_PAID){
				Account ac = charge.getAccount();
				ac.setBalance((ac.getBalance()==null?0:ac.getBalance())+charge.getAmount());
				charge.setBalance(ac.getBalance());
				accountdao.update(ac);
			}
			return (Charge) chargedao.update(charge);
		}
		return null;
	}

	public Charge payment(Account outaccount, Account inaccount, double amount,
			String name, String descr, Shoporder shoporder) {
		String insource = null;
		if(inaccount.getShop()!=null){
			insource = inaccount.getShop().getName();
		}else if(inaccount.getEnterprise()!=null){
			insource = inaccount.getEnterprise().getEntname();
		}else if(inaccount.getUser()!=null){
			if(inaccount.getUser().getName()!=null&&!"".equals(inaccount.getUser().getName())){
				insource = inaccount.getUser().getName();
			}else{
				insource = inaccount.getUser().getPhone();
			}
		}
		String outsource = null;
		if(outaccount.getShop()!=null){
			outsource = outaccount.getShop().getName();
		}else if(outaccount.getEnterprise()!=null){
			outsource = outaccount.getEnterprise().getEntname();
		}else if(outaccount.getUser()!=null){
			if(outaccount.getUser().getName()!=null&&!"".equals(outaccount.getUser().getName())){
				outsource = outaccount.getUser().getName();
			}else{
				outsource = outaccount.getUser().getPhone();
			}
		}
		//支出账户
		Charge charge = createCharge(outaccount, amount, ChargeVariable.CHARGE_PAID, ChargeVariable.TYPE_PAY, insource, shoporder, name, descr, "余额支付");
		//收入账户
		createCharge(inaccount, amount, ChargeVariable.CHARGE_PAID, ChargeVariable.TYPE_INCOME, outsource, shoporder, name, descr, null);
		return charge;
	}

	private Charge createCharge(Account account, double amount, int status,int type,
			String source,Shoporder shoporder,String name,String descr,String paymenttype) {
		Charge charge = new Charge();
		charge.setStatus(status);
		charge.setAmount(amount);
		if(shoporder!=null){
			charge.setShoporder(shoporder);
		}
		charge.setAccount(account);
		charge.setName(name);
		charge.setDescr(descr);
		charge.setType(type);
		charge.setSource(source);
		charge.setTid(getTid());
		charge.setPaymenttype(paymenttype);
		charge.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		if(status == ChargeVariable.CHARGE_PAID){
		if(type==ChargeVariable.TYPE_PAY){//支出
				charge.setBalance((account.getBalance()==null?0:account.getBalance())-amount);
				account.setBalance((account.getBalance()==null?0:account.getBalance())-amount);
				account.setTotalmoneyout((account.getTotalmoneyout()==null?0:account.getTotalmoneyout())+amount);
				accountdao.update(account);
		}else{
				charge.setBalance((account.getBalance()==null?0:account.getBalance())+amount);
				account.setBalance((account.getBalance()==null?0:account.getBalance())+amount);
				account.setTotalmoneyin((account.getTotalmoneyin()==null?0:account.getTotalmoneyin())+amount);
				accountdao.update(account);
			}
		}
		return chargedao.save(charge);
	}
	
	/**
	 * 生成订单
	 */
	private static  String getTid() {
		String tid = DateTimeUtils.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
		tid = tid + validateCode(6);
		return tid;
	}
	private static String validateCode(int code_len) {  
        int count = 0;  
        char str[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };  
        StringBuffer pwd = new StringBuffer("");  
        Random r = new Random();  
        while (count < code_len) {  
            int i = Math.abs(r.nextInt(10));  
            if (i >= 0 && i < str.length) {  
                pwd.append(str[i]);  
                count++;  
            }  
        }  
        return pwd.toString();  
    }
	@SuppressWarnings("unused")
	public Account createAccount(User user, Shop shop, Enterprise enterprise,String pwd) {
		if(user == null && shop ==null && enterprise == null)
			return null;
		if(pwd!=null && !"".equals(pwd)){
			try {
				pwd = MD5Helper.EncoderByMd5(pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			if(user!=null){
				pwd = user.getPassword();
			}
		}
		Account account = new Account();
		account.setPassword(pwd);
		if(user!=null){
			account.setUser(user);
		}else if(shop!=null){
			account.setShop(shop);
		}else if(enterprise!=null){
			account.setEnterprise(enterprise);
		}
		account.setBalance(0.0);
		account = accountdao.save(account);
		return account;
	}
	
	public int updateAccountPwd(Account account, String pwd) {
		try {
			pwd = MD5Helper.EncoderByMd5(pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		account.setPassword(pwd);
		accountdao.update(account);
		return 1;
	}
	
	public AccountBo getAccount(Account account) {
		try {
			account.setPassword(MD5Helper.EncoderByMd5(account.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		List<Account> accountlist = accountdao.findByEgList(account);
		if(accountlist ==null || accountlist.isEmpty())
			return null;
		account= accountlist.get(0);
		AccountBo accountbo = new AccountBo();
		accountbo.setBalance(account.getBalance());
		accountbo.setCharges(account.getCharges());
		accountbo.setEnterprise(account.getEnterprise());
		accountbo.setId(account.getId());
		accountbo.setShop(account.getShop());
		accountbo.setTotalmoneyin(account.getTotalmoneyin());
		accountbo.setTotalmoneyout(account.getTotalmoneyout());
		accountbo.setUser(account.getUser());
		return accountbo;
	}

	public Pagination<Charge> getChargePage(Charge charge, String starttime,
			String endtime, int pageNo, String key, String value) {
		Finder finder = new Finder(" from Charge where account.id =:accountid ");
		finder.setParam("accountid",charge.getAccount().getId());
		if(charge.getStatus()!=null&&charge.getStatus()!=-1){
			finder.append(" and status = :status");
			finder.setParam("status", charge.getStatus());
		}
		if(charge.getType()!=null&&charge.getType()!=-1){
			finder.append(" and type = :type");
			finder.setParam("type", charge.getType());
		}
		if(charge.getPaymenttype()!=null && !"".equals(charge.getPaymenttype())){
			finder.append(" and paymenttype like :paymenttype");
			finder.setParam("paymenttype", "'%"+charge.getPaymenttype()+"%'");
		}
		if(charge.getSource()!=null && !"".equals(charge.getSource())){
			finder.append(" and source like :source");
			finder.setParam("source", "'%"+charge.getSource()+"%'");
		}
		if(!StringUtils.isEmpty(starttime)){
			starttime +=" 00:00:00";
			finder.append(" and createtime>=:starttime");
			finder.setParam("starttime", starttime);		
		}
		if(!StringUtils.isEmpty(endtime)){
			endtime += " 23:59:59";
			finder.append(" and createtime<=:enddate");
			finder.setParam("enddate", endtime);	
		}
		if(key!=null && !"".equals(key)){
			finder.append(" and ");
			finder.append(key);
			finder.append(" like :value");
			finder.setParam("value", "%"+value+"%");
		}
		finder.append(" order by id desc");
		return chargedao.find(finder, pageNo, 10);
	}

	public Pagination<Charge> getChargePage(Charge charge, int pageNo) {
		Finder finder = new Finder(" from Charge where account.id =:accountid ");
		finder.setParam("accountid",charge.getAccount().getId());
		if(charge.getStatus()!=null&&charge.getStatus()!=-1){
			finder.append(" and status = :status");
			finder.setParam("status", charge.getStatus());
		}
		if(charge.getType()!=null&&charge.getType()!=-1){
			finder.append(" and type = :type");
			finder.setParam("type", charge.getType());
		}
		if(charge.getPaymenttype()!=null && !"".equals(charge.getPaymenttype())){
			finder.append(" and paymenttype like :paymenttype");
			finder.setParam("paymenttype", "'%"+charge.getPaymenttype()+"%'");
		}
		if(charge.getSource()!=null && !"".equals(charge.getSource())){
			finder.append(" and source like :source");
			finder.setParam("source", "'%"+charge.getSource()+"%'");
		}
		finder.append(" order by id desc");
		return chargedao.find(finder, pageNo, 10);
	}

	public Charge getChargeByTid(String tid) {
		return chargedao.findUniqueByProperty("tid", tid);
	}




}
