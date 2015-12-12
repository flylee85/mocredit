package com.mocredit.activity.model;

import java.util.List;

public class Mms {
	private int id;
	private int activityId;
	private Integer mmspackageid;
	private String subject;
	private boolean isresend;
	private Integer code_no;
	private String mmsJson;
	private String createtime;
	private List<Mmsframe> frames;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public Integer getMmspackageid() {
		return mmspackageid;
	}

	public void setMmspackageid(Integer mmspackageid) {
		this.mmspackageid = mmspackageid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isIsresend() {
		return isresend;
	}

	public void setIsresend(boolean isresend) {
		this.isresend = isresend;
	}

	public Integer getCode_no() {
		return code_no;
	}

	public void setCode_no(Integer code_no) {
		this.code_no = code_no;
	}

	public String getMmsJson() {
		return mmsJson;
	}

	public void setMmsJson(String mmsJson) {
		this.mmsJson = mmsJson;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public List<Mmsframe> getFrames() {
		return frames;
	}

	public void setFrames(List<Mmsframe> frames) {
		this.frames = frames;
	}

}
