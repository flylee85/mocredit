package com.mocredit.bank.persistence;


import com.mocredit.bank.entity.InRequest;
/**
 * 
 * @author liaoying
 * Created on 2015年8月19日
 *
 */
public interface InRequestMapper {

    /**
     * 添加一条日志记录
     * @return
     */
    public int save(InRequest interact);


}
