package cn.m.mt.charge.service.bo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cn.m.mt.po.Charge;
import cn.m.mt.po.Enterprise;
import cn.m.mt.po.Shop;
import cn.m.mt.po.User;

public class AccountBo implements Serializable {
 	private static final long serialVersionUID = 1L;
	private Long id;
	private Shop shop;
	private Enterprise enterprise;
	private User user;
	private Double balance;
	private Double totalmoneyin;
	private Double totalmoneyout;
	private Set<Charge> charges = new HashSet<Charge>(0);
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Enterprise getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getTotalmoneyin() {
		return totalmoneyin;
	}
	public void setTotalmoneyin(Double totalmoneyin) {
		this.totalmoneyin = totalmoneyin;
	}
	public Double getTotalmoneyout() {
		return totalmoneyout;
	}
	public void setTotalmoneyout(Double totalmoneyout) {
		this.totalmoneyout = totalmoneyout;
	}
	public Set<Charge> getCharges() {
		return charges;
	}
	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}
	
	
}
