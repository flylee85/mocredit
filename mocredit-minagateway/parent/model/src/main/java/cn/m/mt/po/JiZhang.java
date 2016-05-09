package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jizhang")
public class JiZhang implements java.io.Serializable {

	private static final long serialVersionUID = 177927621268062758L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	private String createtime;
	private String devcode;
	private String inputlog;
	private String posno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eitemid", nullable = false)
	private Eitem eitem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeid", nullable = false)
	private Store store;
	
	public String getCreatetime() {
		return createtime;
	}
	
	public String getDevcode() {
		return devcode;
	}

	public Eitem getEitem() {
		return eitem;
	}

	public Long getId() {
		return id;
	}

	public String getInputlog() {
		return inputlog;
	}

	public Store getStore() {
		return store;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public void setDevcode(String devcode) {
		this.devcode = devcode;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInputlog(String inputlog) {
		this.inputlog = inputlog;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getPosno() {
		return posno;
	}

	public void setPosno(String posno) {
		this.posno = posno;
	}

}