package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member")
public class Member implements java.io.Serializable {

	// Fields

	private Long id;
	private String mobile;
	private String province;
	private String city;
	private String nickname;
	private String email;
	private String createtime;
	private String address;
	private String sex;
	private String birthday;
	private String professional;
	private Integer grade;
	private String username;
	private Integer subemail;
	private Integer status;
	private String company;
	private String job;
	private Integer subsms;
	private String password;

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** full constructor */
	public Member(String mobile, String province, String city, String nickname,
			String email, String createtime, String address, String sex,
			String birthday, String professional, Integer grade,
			String username, Integer subemail, Integer status, String company,
			String job, Integer subsms, String password) {
		this.mobile = mobile;
		this.province = province;
		this.city = city;
		this.nickname = nickname;
		this.email = email;
		this.createtime = createtime;
		this.address = address;
		this.sex = sex;
		this.birthday = birthday;
		this.professional = professional;
		this.grade = grade;
		this.username = username;
		this.subemail = subemail;
		this.status = status;
		this.company = company;
		this.job = job;
		this.subsms = subsms;
		this.password = password;
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

	@Column(name = "mobile", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "province", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "nickname", length = 20)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "sex", length = 2)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "birthday", length = 20)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "professional", length = 20)
	public String getProfessional() {
		return this.professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	@Column(name = "grade")
	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Column(name = "username", length = 30)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "subemail")
	public Integer getSubemail() {
		return this.subemail;
	}

	public void setSubemail(Integer subemail) {
		this.subemail = subemail;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "company", length = 50)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "job", length = 20)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "subsms")
	public Integer getSubsms() {
		return this.subsms;
	}

	public void setSubsms(Integer subsms) {
		this.subsms = subsms;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}