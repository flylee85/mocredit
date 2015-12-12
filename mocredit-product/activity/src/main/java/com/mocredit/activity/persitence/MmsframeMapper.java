package com.mocredit.activity.persitence;

import java.util.List;

import com.mocredit.activity.model.Mms;
import com.mocredit.activity.model.Mmsframe;

/**
 * 彩信帧
 * 
 * @author William
 *
 */
public interface MmsframeMapper {

	int saveMms(Mms mms);

	// 根据主键获取彩信帧
	Mms getMmsById(long id);
	
	Mms getMmsByActivityId(long activityId);

	int updateMms(Mms mms);
	
	int saveMmsframe(Mmsframe mmsframe);

	// 根据主键获取彩信帧
	Mmsframe getMmsframeById(long id);

	// 根据活动id获取彩信帧
	List<Mmsframe> getMmsframeListByMMSId(int mmsId);

	// 根据主键删除彩信帧
	int deleteMmsframeById(long id);

	// 修改一条彩信帧
	int updateMmsframe(Mmsframe mmsframe);

}
