package com.mocredit.integral.persistence;


import com.mocredit.integral.entity.Interact;
/**
 * 
 * @author liaoying
 * Created on 2015年8月19日
 *
 */
public interface InteractRequestMapper {

    /**
     * 添加一条日志记录
     * @return
     */
    public int save(Interact interact);


}
