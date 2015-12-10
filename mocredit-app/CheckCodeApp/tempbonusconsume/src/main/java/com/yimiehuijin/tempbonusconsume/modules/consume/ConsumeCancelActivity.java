package com.yimiehuijin.tempbonusconsume.modules.consume;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yimiehuijin.codeandbonuslibrary.constants.URLs;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumePostData;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumeResponse;
import com.yimiehuijin.codeandbonuslibrary.utils.BankInfoUtil;
import com.yimiehuijin.codeandbonuslibrary.web.ConsumeUtil;
import com.yimiehuijin.tempbonusconsume.R;
import com.yimiehuijin.tempbonusconsume.base.IBaseActivity;

import cn.weipass.pos.sdk.MagneticReader;
import cn.weipass.pos.sdk.impl.WeiposImpl;

public class ConsumeCancelActivity extends IBaseActivity implements
		OnClickListener {

	private Button cancel, confirm;
	private TextView orderId;
	private MagneticReader mMagneticReader;
	private String id;

	private String cardNo, expDate,newId;

	private ConsumeUtil consumeUtil;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_consume_cancel;
	}

	@Override
	public void initAactivity() {
		findView();
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		try {
			consumeUtil = new ConsumeUtil(this, URLs.URL_BONUS_REVOKE, URLs.URL_BONUS_REVOKE_CORRECT, new ConsumeUtil.ConsumeListener() {
                @Override
                public void success(BonusConsumeResponse data) {
                    checkCodeResult(data);
                }

                @Override
                public void failure(String msg) {
                    cancelFailed(msg);
                }

                @Override
                public void correctFailure() {
                    cancelFailed("撤销失败");
                }

				@Override
				public void correctSuccess() {
					dismissProgressDialog();
				}
			},ConsumeUtil.FLAG_REVOKE);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		id = getIntent().getExtras().getString("orderid");
		orderId.setText(id);
	}

	private void cancelFailed(String msg) {
		dismissProgressDialog();
		BonusConsumeResponse bcr = new BonusConsumeResponse();
		bcr.success = false;
		bcr.errorMes = msg;
		bcr.data = "";
		checkCodeResult(bcr);
	}

	protected void findView() {
		// TODO Auto-generated method stub
		cancel = (Button) f(R.id.consume_cancel_back);
		confirm = (Button) f(R.id.consume_cancel_confirm);
		orderId = (TextView) f(R.id.consume_cancel_orderid);
		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.consume_cancel_back:
			finish();
			break;
		case R.id.consume_cancel_confirm:
			getMagneticReaderInfo();
			break;
		}
	}

	private void getMagneticReaderInfo() {
		if (mMagneticReader == null) {
			Toast.makeText(this, "初始化磁条卡sdk失败", Toast.LENGTH_LONG).show();
			return;
		}
		// 刷卡后，主动获取磁卡的byte[]数据
		// byte[] cardByte = mMagneticReader.readCard();

		// 磁卡刷卡后，主动获取解码后的字符串数据信息
		String decodeData = mMagneticReader.getCardDecodeData();
		if (decodeData != null && decodeData.length() != 0) {
			/**
			 * 1：刷会员卡返回会员卡号后面变动的卡号，前面为固定卡号（没有写入到磁卡中）
			 * 如会员卡号：9999100100030318，读卡返回数据为00030318，前面99991001在磁卡中没有写入
			 * 2：刷银行卡返回数据格式为：卡号=有效期。
			 */
			Toast.makeText(this, "获取磁条卡数据成功,内容：" + decodeData,
					Toast.LENGTH_LONG).show();
			cardNo = "none";
			String name = "none";
			String bin = "none";
			expDate = "none";
			if (decodeData.contains("=")) {
				cardNo = decodeData.split("=")[0];
				expDate = decodeData.split("=")[1].subSequence(0, 4).toString();
			}
			bin = decodeData.subSequence(0, 6).toString();
			name = BankInfoUtil.getNameOfBank(bin.toCharArray(), 0);
			BonusConsumePostData bcpd = new BonusConsumePostData();
			bcpd.cardNo = cardNo;
			bcpd.exp_date = expDate;
			bcpd.orgOrderId = this.id.toLowerCase();
			newId = bcpd.orderId;
			showProgressDialog("正在提交...");
			consumeUtil.consumePost(bcpd);
		} else {
			Toast.makeText(this, "获取磁条卡数据失败，请确保已经刷卡", Toast.LENGTH_LONG).show();
		}
	}

	private void checkCodeResult(BonusConsumeResponse response) {
		dismissProgressDialog();
		Intent i = new Intent(this, BonusConsumeResultActivity.class);

		i.putExtra("result", response.success);
		i.putExtra("data", response);

		i.putExtra("orderid", newId);
		i.putExtra("cardNo",cardNo);
		i.putExtra("oldid",this.id.toLowerCase());
		i.putExtra("from",BonusConsumeResultActivity.FROM_REVOKE);
		startActivity(i);
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (mMagneticReader == null) {
			try {
				// 初始化磁条卡sdk对象
				mMagneticReader = WeiposImpl.as().openMagneticReader();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		super.onResume();
	}

}
