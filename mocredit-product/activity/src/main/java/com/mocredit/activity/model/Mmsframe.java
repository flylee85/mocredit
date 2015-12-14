package com.mocredit.activity.model;

public class Mmsframe implements java.io.Serializable {

	// Fields

	private Long id;
	private int mmsId;
	private String pic;
	private String pictype;
	private String text;
	private Integer frame_no;
	private String createtime;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMmsId() {
		return mmsId;
	}

	public void setMmsId(int mmsId) {
		this.mmsId = mmsId;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPictype() {
		return this.pictype;
	}

	public void setPictype(String pictype) {
		this.pictype = pictype;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getFrame_no() {
		return frame_no;
	}

	public void setFrame_no(Integer frame_no) {
		this.frame_no = frame_no;
	}

	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}