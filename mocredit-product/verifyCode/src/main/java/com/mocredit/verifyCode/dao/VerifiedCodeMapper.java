package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.TVerifiedCode;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 提供已验证码的表的操作
 * Created by YHL on 15/7/9 09:47.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface VerifiedCodeMapper {

    /**
     * 根据 券码号，获取该券码的验证记录列表
     * @param code
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByCode(String code);

    /**
     * 根据券码和 POS的序列号，找到对应的验证记录列表
     *
     * @param code
     * @param requestSeriaNumber
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByCodeAndRequestSerialNumber(@Param("code") String code, @Param("request_serial_number") String requestSeriaNumber);

    /**
     * 根据码序列号（券码ID）获取券码的验证记录列表
     * @param codeSerialNumber
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByActiveCodeId(String codeSerialNumber);


    /**
     * 插入一条验码使用的记录
     * @param verifiedCode
     * @return
     */
    public int insertVerifiedCode(TVerifiedCode verifiedCode);


    /**
     * 根据码序列号（券码ID）获取券码的一条记录
     * @param codeSerialNumber
     * @return
     */
    public TVerifiedCode findVerifiedCodesByDeviceAndRequestSerialNumber(@Param("device") String device, @Param("request_serial_number") String requestSeriaNumber , @Param("date")Date date);

    /**
     * 根据码和校验类型获取验证记录列表
     * @param code
     * @return
     */
    public List<TVerifiedCode> findVerifiedCodesByCodeAndType(Map<String, Object> param);
    
}
