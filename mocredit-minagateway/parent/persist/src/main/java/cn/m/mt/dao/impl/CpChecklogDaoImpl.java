package cn.m.mt.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.m.mt.dao.CpChecklogDao;
import cn.m.mt.po.CpChecklog;

/**
 * 
 * @description 
 *
 * @author 
 */
@Repository
public class CpChecklogDaoImpl extends BaseDaoImpl<CpChecklog> implements CpChecklogDao {
	@Override
	public long[] getCheckAndSumPhone(long eitemid) {
		String sql="select "+
				"(select sum(checknum) from checklog as c "+
				"where (c.eitemid = e.id or c.eitemid = e.eitemid) "+
				"and c.checktype =0 ) as checkednum,"+
				"e.codetimes * (select count(id) from eorder as eo1 where (e.id = eo1.eitemid or e.eitemid = eo1.eitemid)) as sumcheck "+
				"from eitem e where e.id=:eitemid";
		Session session=getSession();
		session.beginTransaction();
		Object obj=session.createSQLQuery(sql).setLong("eitemid", eitemid).uniqueResult();
		long[] num=new long[]{0,0};
		if(obj!=null)
		{
			Object[] objs=(Object[])obj;
			if(null!=objs[0])
				num[0]=Long.valueOf(objs[0].toString());//已验证数
			if(null!=objs[1])
				num[1]=Long.valueOf(objs[1].toString());//总可刷码数
		}
		return num;
	}
}