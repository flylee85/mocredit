package cn.m.common.hibernate3.restrictions;

import java.util.ArrayList;
import java.util.List;

import cn.m.common.hibernate3.Condition;


@SuppressWarnings("serial")
public class In extends Condition {
	
	private List<Object> inlist = new ArrayList<Object>();
	
	public In(String field,List<Object> inlist){
		this.field = field;
		this.inlist = inlist;
	}
	
	public static In in(String field,List<Object> inlist){
		return new In(field,inlist);
	}

	public List<Object> getInlist() {
		return inlist;
	}

	public void setInlist(List<Object> inlist) {
		this.inlist = inlist;
	}

}
