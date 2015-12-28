package cn.mocredit.gateway.posp.xnzd;

import cn.mocredit.gateway.posp.bc.util.MessageObject;
import cn.mocredit.gateway.wangpos.bo.DemoData;
import cn.mocredit.gateway.wangpos.bo.DemoRetData;

public interface Entrance {
	public DemoRetData jhSign(DemoData demoData);

	public abstract MessageObject xfcx(DemoData dEmodata);

	public abstract DemoRetData cz(DemoData dEmodata);

	public abstract DemoRetData cx(DemoData dEmodata);

	public abstract DemoRetData xf(DemoData dEmodata);

	public abstract DemoRetData signOut(DemoData demoData);
}
