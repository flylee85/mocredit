package cn.m.mt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.m.mt.service.BaseService;

@Transactional
public class BaseServiceImpl implements BaseService{
	protected Logger log = LoggerFactory.getLogger(getClass());
}
