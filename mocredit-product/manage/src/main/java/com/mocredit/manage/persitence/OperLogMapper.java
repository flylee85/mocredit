package com.mocredit.manage.persitence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mocredit.manage.model.OptLog;

public interface OperLogMapper {

	int insert(OptLog log);

	List<OptLog> selectByRefId(@Param("relId") String refId);
}