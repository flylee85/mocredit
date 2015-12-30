package com.mocredit.order.service;

import com.mocredit.order.entity.UserDisplay;
import com.mocredit.order.vo.UserDisplayVo;

public interface UserDisplayService extends BaseService<UserDisplay> {

	boolean update(UserDisplayVo userDisplayVo);

}
