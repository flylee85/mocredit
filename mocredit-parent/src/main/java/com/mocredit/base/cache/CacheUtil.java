/**
 * Copyright (c) 2010 CEPRI,Inc.All rights reserved.
 * Created by 2010-7-16 
 */
package com.mocredit.base.cache;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.mocredit.base.log.BaseLog;
import com.mocredit.base.util.PropertyUtil;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * 缓存工具类
 * @author zhaiguangtao
 * @date 2010-7-16
 */
public class CacheUtil {
    // 创建全局的唯一实例
    private static MemCachedClient mcc = null;
    
    private CacheUtil(){};
    
    // 设置与缓存服务器的连接池
    private static MemCachedClient init() {
        mcc = new MemCachedClient();
        
        // 服务器列表和其权重
        final String[] servers  = PropertyUtil.getProInfo("config", "cacheServer").split(","); 
        final String[] weightAry = PropertyUtil.getProInfo("config", "cacheWeight").split(",");
        final Integer[] weights = new Integer[weightAry.length];
        
        // 获取socket连接池的实例对象
        final SockIOPool pool = SockIOPool.getInstance();
        
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Integer.parseInt(weightAry[i]);
        }

        // 设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);
        pool.setFailover(true);// 故障自动转移

        // 设置初始连接数、最小和最大连接数
        pool.setInitConn(10);
        pool.setMinConn(15);
        pool.setMaxConn(300);
        
        //设置一个连接最大空闲时间5分钟
//        pool.setMaxIdle(1000 * 60 * 5);
//        pool.setMaxBusyTime(1000 * 30);
        
        // 设置主线程的睡眠时间    每隔30秒醒来 然后  开始维护 连接数大小
//        pool.setMaintSleep(30);

        //  关闭nagle算法 设置TCP的参数:读取超时,连接超时 3秒
//        pool.setNagle(false);
//        pool.setSocketTO(60);
//        pool.setSocketConnectTO(0);
        
        // 使用连接资源以前是否需要验证连接资源有效
        pool.setAliveCheck(true);

        // 初始化连接池
        pool.initialize();
      
        // 【以下API已过时，不予使用】压缩设置，超过指定大小（单位为K）的数据都会被压缩
        // mcc.setCompressEnable(true);
        // mcc.setCompressThreshold(64 * 1024);
        return mcc;
    }
    
    /**
     * @description:获取实例
     * @param MemCachedClient mcc
     */
    public static MemCachedClient getInstance() {
        if (mcc == null) {
            mcc = init();
        }
        return mcc;
    }

	/**
	 * @description: 获取缓存连接状态
	 * @return：true 连接成功
	 */
	public static boolean connStat() {
		try {
			return !(getInstance()==null || getInstance().stats().isEmpty());
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "findObject", e);
			return false;
		}
	}

	/**
	 * @description: 获取缓存信息
	 * @param key：缓存的key
	 * @return：被缓存对象
	 * @Exception：空指针，memcache连接异常
	 */
	public static Object findObject(String key) throws NullPointerException {
		try {
			if(key == null || "".equals(key.trim())){
				return false;
			}
			return getInstance().get(key);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "findObject", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description:存入缓存对象，永不失效
	 * @param key
	 * @param value
	 */
	public static void addObject(String key, Object value) throws NullPointerException {
		try {
			if(key == null || "".equals(key.trim())){
				return;
			}
			getInstance().set(key, value);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "put 存入缓存对象", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 存入缓存对象，自定义实效日期
	 * @param key
	 * @param value
	 * @param expire ：失效时间 例如：
	 * private static final Date CACHE_EXPIRE = new Date(1000*60); // 缓存有效期1分钟
	 */
	public static void addObject(String key, Object value, Date expire)
			throws NullPointerException {
		try {
			if(key == null || "".equals(key.trim())){
				return;
			}
			getInstance().set(key, value, expire);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "put 存入缓存对象", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 存入缓存对象，当天有效
	 * @param key
	 * @param value
	 */
	public static void addObjectToday(String key, Object value){
		try {
			if(key == null || "".equals(key.trim())){
				return;
			}
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            addObject(key, value, new Date(cal.getTimeInMillis() - System.currentTimeMillis()));
        } catch (Exception e) {
            BaseLog.e(CacheUtil.class, "put 存入缓存对象", e);
            throw new NullPointerException();
        }
	}

	/**
	 * @description: 删除缓存
	 * @param key
	 */
	public static void delObject(String key){
		try {
			if(key == null || "".equals(key.trim())){
				return;
			}
			getInstance().delete(key);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "delObject", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 判断该key是否在缓存中存在
	 * @param key
	 */
	public static boolean keyExists(String key){
		try {
			if(key == null || "".equals(key.trim())){
				return false;
			}
			return getInstance().keyExists(key);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "keyExists", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 更新缓存中已存在对象
	 * @param key
	 * @param value
	 */
	public static void updObject(String key, Object value){
		try {
			if(key == null || "".equals(key.trim())){
				return;
			}
			getInstance().replace(key, value);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "updObject", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 更新缓存中已存在对象,带有失效时间
	 * @param key: 传入对象key
	 * @param value：传入对象值
	 * @param expire ：失效时间
	 * private static final Date CACHE_EXPIRE = new Date(1000*60); // 缓存有效期1分钟
	 */
	public static void updObject(String key, Object value, Date expire)
			throws NullPointerException {
		try {
			if(key == null || "".equals(key.trim())){
				return;
			}
			getInstance().replace(key, value, expire);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "updObject", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 更新缓存中已存在对象,当天有效
	 * @param key :传入对象key
	 * @param value: 传入对象值
	 */
	public static void updObjectToday(String key, Object value){
		try {
			if(key == null || "".equals(key.trim())){
				return;
			}
			Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            updObject(key, value, new Date(cal.getTimeInMillis()- System.currentTimeMillis()));
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "updObject", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 获取批量缓存对象，以字符串数组方式返回
	 * @param key
	 * @return：Object[]
	 * @Exception：空指针，memcache连接异常
	 */
	public static Object[] findArrayObjects(String keys[]){
		try {
			return getInstance().getMultiArray(keys);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "findArrayObjects", e);
			throw new NullPointerException();
		}
	}

	/**
	 * @description: 获取批量缓存对象，以map键值对方式返回
	 * @param key
	 * @return：Map<String, Object>
	 */
	public static Map<String, Object> findMapObjects(String keys[])
			throws NullPointerException {
		try {
			return getInstance().getMulti(keys);
		} catch (Exception e) {
			BaseLog.e(CacheUtil.class, "findMapObjects", e);
			throw new NullPointerException();
		}
	}
 
}
