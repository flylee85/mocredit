package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Eitemstore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eitemstore")
public class Eitemstore implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Store store;
	private String createtime;
	private String updatetime;

	// Constructors

	/** default constructor */
	public Eitemstore() {
	}

	/** minimal constructor */
	public Eitemstore(Eitem eitem, Store store) {
		this.eitem = eitem;
		this.store = store;
	}

	/** full constructor */
	public Eitemstore(Eitem eitem, Store store, String createtime,
			String updatetime) {
		this.eitem = eitem;
		this.store = store;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eitemid", nullable = false)
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeid", nullable = false)
	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "updatetime", length = 20)
	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}