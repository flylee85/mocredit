package cn.mocredit.gateway.posp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mocredit.gateway.data.entities.MpTempKey;
import cn.mocredit.gateway.data.repository.MpTempKeyRepository;
//import cn.mocredit.gateway.data.repository.CodeRepository;
import cn.mocredit.gateway.posp.service.PospService;

@Service
@Transactional
public class PospServiceImpl implements PospService{
	@Autowired
	private MpTempKeyRepository mpTempKeyRepository;

	@Override
	public void addTerm(MpTempKey mpTermKey){
		mpTempKeyRepository.save(mpTermKey);
	}
	
	@Override
	public void updateTerm(MpTempKey mpTermKey){
		MpTempKey findOne = mpTempKeyRepository.findOne(mpTermKey.getId());
		
	}
	@Override
	public void test() {
	}

}
