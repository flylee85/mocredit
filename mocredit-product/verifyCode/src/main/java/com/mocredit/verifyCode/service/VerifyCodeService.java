package com.mocredit.verifyCode.service;

import java.util.Map;

import com.mocredit.verifyCode.vo.VerifyCodeVO;

/**
 * 验码流水
 * 
 * @author liaoy
 *
 */
public interface VerifyCodeService {
	/**
	 * 获取分页的流水
	 * 
	 * @param verifyCode
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	Map<String, Object> getPage(VerifyCodeVO verifyCode, int pageSize, int pageNum);
}
