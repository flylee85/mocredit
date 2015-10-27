package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.TActivityCode;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by YHL on 2015/7/7.
 */
public interface ActivityCodeMapper {

    /**
     * 插入一条券码对象
     * @param activityCode
     * @return
     */
    int insertActivityCode(TActivityCode activityCode);

    /**
     * 根据券码号，查询券码对象表
     * @param code
     * @return
     *   返回一个列表
     */
    List<TActivityCode> findActivityCodeByCode(String code);

    /**
     * 根据券码主键获取券码对象
     * @param id
     * @return
     *  如果找到，返回一个券码对象，否则返回 NULL
     */
    TActivityCode findActivityCodeById(String id);

    /**
     * 更新券码对象
     * @param activityCode
     * @return
     */
    int updateActivityCode(TActivityCode activityCode);

    /**
     * 根据ID删除券码对象
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 使用行锁定的方式，锁定某个行数据
     * <pre>
     *    <b> 注意：慎重使用此方法，此方法仅仅用于 验码核销 事务中。</b>
     * </pre>
     * @param id  主键
     */
    public TActivityCode selectActivityCodeForUpdateById(String id);

    /**
     * 批量保存 券码
     * <b>注意：要求所有券码对象存在主键</b>
     * @param activityCodes
     * @return 返回受到影响的行数
     */
    public int batchSave(@Param("activityCodes")List<TActivityCode> activityCodes);


    /**
     * 更新活动。
     * 更新活动信息的时候，会更新此活动下的 券码信息。
     * 所以在更新前，应该锁定券码，然后在更新。
     * @param activityId 活动ID
     * @param activityName 活动名称
     * @param issueEnterpriseId   发行企业ID
     * @param issueEnterpriseName 发行企业名字
     * @param contractId     合同ID
     * @param amount          金额
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param selectDate     选择的日期（例如，只能周六周天使用等）
     * @return
     */
    public int updateActActivity(@Param("activityId") String activityId,
                                 @Param("activityName") String activityName,
                                 @Param("issueEnterpriseId")  String issueEnterpriseId,
                                 @Param("issueEnterpriseName")String issueEnterpriseName,
                                 @Param("contractId")    String contractId,
                                 @Param("amount")         BigDecimal amount,
                                 @Param("startTime")     Date startTime,
                                 @Param("endTime")       Date endTime,
                                 @Param("selectDate")    String selectDate,
                                 @Param("maxNum")        Integer maxNum
                                );
}
