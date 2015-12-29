package cn.mocredit.gateway.posp.service;

import cn.mocredit.gateway.data.entities.MpTempKey;

public interface PospService {
	void test();

	public abstract void updateTerm(MpTempKey mpTermKey);

	public abstract void addTerm(MpTempKey mpTermKey);
}
