package cn.m.common.hibernate3.restrictions;

import cn.m.common.hibernate3.Condition;

/**
 * >=
 * @author mazhengtao
 * @date 2010-8-26
 */
@SuppressWarnings("serial")
public class NotEq extends Condition {
	private String field;
	private Object pram;

	public NotEq(String field, Object pram) {
		this.field = field;
		this.pram = pram;
	}

	public String getField() {
		return field;
	}

	public Object getPram() {
		return pram;
	}
	
	
}
