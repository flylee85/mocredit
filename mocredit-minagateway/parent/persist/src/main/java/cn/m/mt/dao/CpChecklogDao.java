package cn.m.mt.dao;
import cn.m.mt.po.CpChecklog;

/**
 * 
 * @description
 *
 * @author 
 */
public interface CpChecklogDao extends BaseDao<CpChecklog> {
	/**
	 * 查询已验证数，总可刷码数
	 * @author zhangzs
	 * @param eitemid 活动号
	 * @return 已验证数，总可刷码数
	 */
	public long[]  getCheckAndSumPhone(long eitemid);
}