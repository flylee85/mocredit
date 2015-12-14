package com.mocredit.activity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.activity.model.Mms;
import com.mocredit.activity.model.Mmsframe;
import com.mocredit.activity.persitence.MmsframeMapper;
import com.mocredit.activity.service.MmsframeService;

@Service
public class MmsframeServiceImpl implements MmsframeService {

	@Autowired
	private MmsframeMapper mmsframeMapper;
	
	@Override
	public int saveMmsframe(Mmsframe mmsframe) {
		return mmsframeMapper.saveMmsframe(mmsframe);
	}
	@Override
	public Mmsframe getMmsframeById(long id) {
		return mmsframeMapper.getMmsframeById(id);
	}
	@Override
	public List<Mmsframe> getMmsframeListByMMSId(int mmsId) {
		return mmsframeMapper.getMmsframeListByMMSId(mmsId);
	}
	@Override
	public int deleteMmsframeByActivityId(long id) {
		return mmsframeMapper.deleteMmsframeByActivityId(id);
	}
	@Override
	public int updateMmsframe(Mmsframe mmsframe) {
		return mmsframeMapper.updateMmsframe(mmsframe);
	}
	@Override
	public int saveMMS(Mms mms) {
		return mmsframeMapper.saveMms(mms);
	}
	@Override
	public Mms getMmsById(long id) {
		return mmsframeMapper.getMmsById(id);
	}
	@Override
	public Mms getMmsByActivityId(long activityId) {
		return mmsframeMapper.getMmsByActivityId(activityId);
	}
	@Override
	public int updateMms(Mms mms) {
		return mmsframeMapper.updateMms(mms);
	}
	@Override
	public int deleteMmsByActivityId(long activityId) {
		return mmsframeMapper.deleteMmsByActivityId(activityId);
	}
	
	
}
