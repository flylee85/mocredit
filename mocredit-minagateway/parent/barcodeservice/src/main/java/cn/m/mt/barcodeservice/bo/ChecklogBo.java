package cn.m.mt.barcodeservice.bo;

public class ChecklogBo {
	private String name;
	private long id;
	private long total =0;
	//验证成功数
	private long succtotal=0;
	//无效的活动次数
	private long failtotal = 0;
	//过期的活动次数
	private long expitotal = 0;
	//未开始的活动次数
	private long nstotal = 0;
	//不能再参加互斥活动次数
	private long mutetotal = 0;
	//不是在有效的门店内
	private long nistotal = 0;
	private String storename;
	private String shopname;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getSucctotal() {
		return succtotal;
	}
	public void setSucctotal(long succtotal) {
		this.succtotal = succtotal;
	}
	public long getFailtotal() {
		return failtotal;
	}
	public void setFailtotal(long failtotal) {
		this.failtotal = failtotal;
	}
	public long getExpitotal() {
		return expitotal;
	}
	public void setExpitotal(long expitotal) {
		this.expitotal = expitotal;
	}
	public long getNstotal() {
		return nstotal;
	}
	public void setNstotal(long nstotal) {
		this.nstotal = nstotal;
	}
	public long getMutetotal() {
		return mutetotal;
	}
	public void setMutetotal(long mutetotal) {
		this.mutetotal = mutetotal;
	}
	public long getNistotal() {
		return nistotal;
	}
	public void setNistotal(long nistotal) {
		this.nistotal = nistotal;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String toString(){
		//总数
		total = succtotal+failtotal+expitotal+nstotal+mutetotal+nistotal;
		StringBuffer  returnChecklog = new StringBuffer();
		returnChecklog.append("<ID>"+id+"</ID>");
		returnChecklog.append("<活动名称>"+name+"</活动名称>");
		returnChecklog.append("<验证总数>"+total+"</验证总数>");
		returnChecklog.append("<验证成功>"+succtotal+"</验证成功>");
		returnChecklog.append("<超过有效的活动次数>"+failtotal+"</超过有效的活动次数>");
		returnChecklog.append("<活动已过期>"+expitotal+"</活动已过期>");
		returnChecklog.append("<活动未开始>"+nstotal+"</活动未开始>");
		returnChecklog.append("<不能再参加互斥活动>"+mutetotal+"</不能再参加互斥活动>");
		returnChecklog.append("<不是在有效的门店内>"+nistotal+"</不是在有效的门店内>");
		returnChecklog.append("<门店名称>"+storename+"</门店名称>");
		returnChecklog.append("<商家名称>"+shopname+"</商家名称>");
		return returnChecklog.toString();
	}
	public String getPrintInfo() {
		total = succtotal+failtotal+expitotal+nstotal+mutetotal+nistotal;
		StringBuffer  returnChecklog = new StringBuffer();
		returnChecklog.append("\n\n活动名称:");
		returnChecklog.append(name);
		returnChecklog.append("\n验证总数");
		returnChecklog.append(total);
		returnChecklog.append("\n验证成功:");
		returnChecklog.append(succtotal);
		returnChecklog.append("\n超过有效的活动次数:");
		returnChecklog.append(failtotal);
		returnChecklog.append("\n活动已过期:"+expitotal);
		returnChecklog.append("\n活动未开始:"+nstotal);
		returnChecklog.append("\n不能再参加互斥活动:"+mutetotal);
		returnChecklog.append("\n不是在有效的门店内:"+nistotal);
		return returnChecklog.toString();
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

}
