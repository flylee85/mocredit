package cn.mocredit.posp.impl.cmsb.points;

import cn.mocredit.posp.bc.util.MessageObject;

public interface Trans {
	public MessageObject action(MessageObject request,String flag,MessageObject termRequest);
}
