package com.mocredit.bank.persistence;


import com.mocredit.bank.entity.BankLog;
/**
 * 
 * @author liaoying
 * Created on 2015年8月19日
 *
 */
public interface BankLogMapper {

    /**
     * 添加一条日志记录
     * @return
     */
    public int save(BankLog log);


}
