package cn.m.common.hibernate3.restrictions;

import cn.m.common.hibernate3.Condition;

/**
 * >=
 * @author mazhengtao
 * @date 2010-8-26
 */
@SuppressWarnings("serial")
public class Like extends Condition {
	
	private String field;
	
	private String value;//key,key%,%key,%key%
	
	public Like(String field, String value) {
		this.field = field;
		this.value=value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
