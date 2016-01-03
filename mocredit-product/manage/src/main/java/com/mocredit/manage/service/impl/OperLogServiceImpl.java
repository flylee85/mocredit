package com.mocredit.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.manage.model.OptLog;
import com.mocredit.manage.persitence.OperLogMapper;
import com.mocredit.manage.service.OperLogService;

@Service
public class OperLogServiceImpl implements OperLogService {
	@Autowired
	private OperLogMapper logMapper;

	@Override
	public int add(String user, String operate, String data, String info, String relId) {
		OptLog log=new OptLog();
		log.setCtime(new Date());
		log.setData(data);
		log.setInfo(info);
		log.setOperate(operate);
		log.setRelId(relId);
		log.setUser(user);
		return logMapper.insert(log);
	}
	
	@Override
	public List<OptLog> getLogByRefId(String refId) {
		return logMapper.selectByRefId(refId);
	}
}
