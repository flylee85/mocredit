package cn.m.common.hibernate3.restrictions;

import org.hibernate.criterion.Criterion;

import cn.m.common.hibernate3.Condition;

@SuppressWarnings("serial")
public class Or extends Condition {
	
	private Criterion condl;
	private Criterion condr;

	public Or(Criterion condl,Criterion condr) {
		this.condl=condl;
		this.condr=condr;
	}

	public Criterion getCondl() {
		return condl;
	}

	public Criterion getCondr() {
		return condr;
	}

}
