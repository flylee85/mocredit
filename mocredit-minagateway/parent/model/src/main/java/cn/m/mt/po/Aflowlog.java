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
 * Aflowlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="aflowlog"
  
)

public class Aflowlog  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Store store;
     private String alipayid;
     private String numcode;
     private Double paymoney;
     private String des;
     private String createtime;


    // Constructors

    /** default constructor */
    public Aflowlog() {
    }

    
    /** full constructor */
    public Aflowlog(Store store, String alipayid, String numcode, Double paymoney, String des, String createtime) {
        this.store = store;
        this.alipayid = alipayid;
        this.numcode = numcode;
        this.paymoney = paymoney;
        this.des = des;
        this.createtime = createtime;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="storeid")

    public Store getStore() {
        return this.store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
    
    @Column(name="alipayid", length=40)

    public String getAlipayid() {
        return this.alipayid;
    }
    
    public void setAlipayid(String alipayid) {
        this.alipayid = alipayid;
    }
    
    @Column(name="numcode", length=20)

    public String getNumcode() {
        return this.numcode;
    }
    
    public void setNumcode(String numcode) {
        this.numcode = numcode;
    }
    
    @Column(name="paymoney", precision=10, scale=0)

    public Double getPaymoney() {
        return this.paymoney;
    }
    
    public void setPaymoney(Double paymoney) {
        this.paymoney = paymoney;
    }
    
    @Column(name="des", length=100)

    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
    
    @Column(name="createtime", length=50)

    public String getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
   








}