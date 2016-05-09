package cn.m.common.hibernate3.restrictions;

import cn.m.common.hibernate3.Condition;

@SuppressWarnings("serial")
public class Between extends Condition {
	private String field;
	private Object start;
	private Object end;

	public Between(String field, Object start,Object end) {
		this.field = field;
		this.start = start;
		this.end = end;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getStart() {
		return start;
	}

	public void setStart(Object start) {
		this.start = start;
	}

	public Object getEnd() {
		return end;
	}

	public void setEnd(Object end) {
		this.end = end;
	}
}
