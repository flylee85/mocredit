package com.mocredit.manage.persitence;

import com.mocredit.manage.model.Merchant;

public interface MerchantMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_merchant
     *
     * @mbggenerated Thu Oct 29 12:05:06 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_merchant
     *
     * @mbggenerated Thu Oct 29 12:05:06 CST 2015
     */
    int insert(Merchant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_merchant
     *
     * @mbggenerated Thu Oct 29 12:05:06 CST 2015
     */
    int insertSelective(Merchant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_merchant
     *
     * @mbggenerated Thu Oct 29 12:05:06 CST 2015
     */
    Merchant selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_merchant
     *
     * @mbggenerated Thu Oct 29 12:05:06 CST 2015
     */
    int updateByPrimaryKeySelective(Merchant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_merchant
     *
     * @mbggenerated Thu Oct 29 12:05:06 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(Merchant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_merchant
     *
     * @mbggenerated Thu Oct 29 12:05:06 CST 2015
     */
    int updateByPrimaryKey(Merchant record);
}