package com.mocredit.integral.persistence;


import com.mocredit.integral.entity.Payment;

/**
 * Created by YHL on 15/7/22 22:51.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface OrderMapper {

    /**
     * 添加一条日志记录
     * @return
     */
    public int save(Payment payment);


}
