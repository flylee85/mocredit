package com.mocredit.activity.service;

import java.util.List;

import com.mocredit.activity.model.Mms;
import com.mocredit.activity.model.Mmsframe;

/**
 * 彩信帧
 * 
 * @author William
 *
 */
public interface MmsframeService {
	
	int saveMMS(Mms mms);
	
	Mms getMmsById(long id);
	
	int updateMms(Mms mms);
	Mms getMmsByActivityId(long activityId);
	
	int saveMmsframe(Mmsframe mmsframe);
	
	// 根据主键获取彩信帧
	Mmsframe getMmsframeById(long id);

	// 根据活动id获取彩信帧
	List<Mmsframe> getMmsframeListByMMSId(int activityId);

	// 根据主键删除彩信帧
	int deleteMmsframeById(long id);

	// 修改一条彩信帧
	int updateMmsframe(Mmsframe mmsframe);

}
