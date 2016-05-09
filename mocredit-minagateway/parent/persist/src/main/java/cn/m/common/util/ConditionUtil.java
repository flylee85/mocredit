package cn.m.common.util;

import cn.m.common.hibernate3.Condition;
import cn.m.common.hibernate3.restrictions.Ge;
import cn.m.common.hibernate3.restrictions.Le;

public class ConditionUtil {

	public static Condition[] getGeLe(String sdate,String edate,String parmname,int ad){
		Condition ge=null;
		Condition le=null;
		Condition[] condition=null;
		int i=0;
		if(sdate!=null&&!"".equals(sdate.trim())){
			i++;
			ge=new Ge(parmname, sdate);
		}
		if(edate!=null&&!"".equals(edate.trim())){
			i++;
			le=new Le(parmname, edate);
		}
		if(i>0){
			condition=new Condition[i+ad];
			for(int s=0;s<i;s++){
				if(ge!=null){
					condition[s]=ge;
					ge=null;
					continue;
				}
				if(le!=null){
					condition[s]=le;
					le=null;
					continue;
				}
			}
		}else{
			condition=new Condition[ad];
		}
		return condition;
	}
}
