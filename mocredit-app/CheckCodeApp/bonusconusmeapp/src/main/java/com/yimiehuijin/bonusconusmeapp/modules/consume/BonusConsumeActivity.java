package com.yimiehuijin.bonusconusmeapp.modules.consume;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import cn.weipass.pos.sdk.MagneticReader;
import cn.weipass.pos.sdk.Scanner;
import cn.weipass.pos.sdk.Scanner.OnResultListener;
import cn.weipass.pos.sdk.impl.WeiposImpl;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yimiehuijin.bonusconusmeapp.R;
import com.yimiehuijin.bonusconusmeapp.base.IBaseActivity;
import com.yimiehuijin.bonusconusmeapp.modules.setting.SettingActivity;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.data.CheckCodeResponse;
import com.yimiehuijin.codeandbonuslibrary.data.PostData;
import com.yimiehuijin.codeandbonuslibrary.utils.BankInfoUtil;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;
import com.yimiehuijin.codeandbonuslibrary.views.CodeScreen;
import com.yimiehuijin.codeandbonuslibrary.views.dialog.ProgressDialog;

public class BonusConsumeActivity extends IBaseActivity {

	CodeScreen screen;

	private Button delete;

	private String QR_INFO = "";

	private ProgressDialog dialog;

	private MagneticReader mMagneticReader;


	private Button keyboard;

	private Button cancel;

	private final int STATE_CONSUME = 0X01;
	private final int STATE_CANCEL = 0X02;

	private int consume_state = STATE_CONSUME;



	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_code_scan;
	}

	@Override
	public void initAactivity() {
		findView();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (mMagneticReader == null) {
			try {
				// 初始化磁条卡sdk对象
				mMagneticReader = WeiposImpl.as().openMagneticReader();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	protected void findView() {
		// TODO Auto-generated metfhod stub
		keyboard = (Button) f(R.id.code_scan_keyboard);
		screen = (CodeScreen)f(R.id.code_scan_screen);
		delete = (Button) f(R.id.code_scan_delete);
		cancel = (Button) f(R.id.code_scan_cancel);
		delete.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				screen.clearScreen();
				return true;
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.code_scan_num0:
			screen.inputCode('0');
			break;
		case R.id.code_scan_num1:
			screen.inputCode('1');
			break;
		case R.id.code_scan_num2:
			screen.inputCode('2');
			break;
		case R.id.code_scan_num3:
			screen.inputCode('3');
			break;
		case R.id.code_scan_num4:
			screen.inputCode('4');
			break;
		case R.id.code_scan_num5:
			screen.inputCode('5');
			break;
		case R.id.code_scan_num6:
			screen.inputCode('6');
			break;
		case R.id.code_scan_num7:
			screen.inputCode('7');
			break;
		case R.id.code_scan_num8:
			screen.inputCode('8');
			break;
		case R.id.code_scan_num9:
			screen.inputCode('9');
			break;
		case R.id.code_scan_delete:
			screen.deleteBackCode();
			break;
		case R.id.code_scan_confirm:
			if (consume_state == STATE_CONSUME) {
				getMagneticReaderInfo();
			} else {
				cancelConsume(screen.getCode());
			}
			// bonusConsume();
			break;
		case R.id.code_scan_keyboard:
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			break;
		case R.id.code_scan_cancel:
			if (STATE_CONSUME == consume_state) {
				consume_state = STATE_CANCEL;
				cancel.setText("消费");
				screen.setHints("请输入订单号\n或扫描小票条码后点击确定");
			} else {
				consume_state = STATE_CONSUME;
				cancel.setText("撤销");
				screen.setHints("请输入银行卡号\n刷卡后请点击确定");
			}
			break;
		}
	}

	private void cancelConsume(String id) {
		Intent i = new Intent(this, ConsumeCancelActivity.class);
		i.putExtra("orderid", id);
		startActivity(i);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			return super.dispatchKeyEvent(event);
		}
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_DEL:
			screen.deleteBackCode();
			return super.dispatchKeyEvent(event);
		case KeyEvent.KEYCODE_ENTER:
			keyboard.performClick();
			return super.dispatchKeyEvent(event);
		}
		String s = KeyEvent.keyCodeToString(event.getKeyCode());
		if (s.split("_")[1].length() > 1) {
			return super.dispatchKeyEvent(event);
		}
		if (!StringUtils.isNumberOrAlphabet(s.split("_")[1])) {
			return super.dispatchKeyEvent(event);
		}
		screen.inputCode(s.split("_")[1].toCharArray()[0]);
		return super.dispatchKeyEvent(event);
	}

	private void bonusConsumeResult() {

	}

	@Override
	protected void onActionBarClick(int flag) {
		// TODO Auto-generated method stub
		super.onActionBarClick(flag);
		switch (flag) {
		case IBaseActivity.LEFT_CLICK:
			Scanner scanner = WeiposImpl.as().openScanner();
			if (scanner != null) {
				scanner.scan(Scanner.TYPE_QR, new OnResultListener() {

					@Override
					public void onResult(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							if (StringUtils.isNumber(arg1)) {
								QR_INFO = arg1;
								screen.clearScreen();
								screen.inputString(arg1);
							} else {
								Toast.makeText(BonusConsumeActivity.this,
										"码格式不正确", Toast.LENGTH_LONG).show();
								screen.clearScreen();
							}
						}
					}
				});
			}
			break;
		case IBaseActivity.MENU_CLICK:
			Intent i = new Intent(this, SettingActivity.class);
			startActivity(i);
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
//			Toast.makeText(this, "获取磁条卡数据成功,内容：" + decodeData,
//					Toast.LENGTH_LONG).show();
			Intent i;
			i = new Intent(this, PayActivity.class);
			String id = "none";
			String name = "none";
			String bin = "none";
			String date = "none";
			if (decodeData.contains("=")) {
				id = decodeData.split("=")[0];
				date = decodeData.split("=")[1].subSequence(0, 4).toString();
			}
			bin = decodeData.subSequence(0, 6).toString();
			name = BankInfoUtil.getNameOfBank(bin.toCharArray(), 0);
//			i.putExtra("id", id);
//			i.putExtra("bin", bin);
//			i.putExtra("name", name);
//			i.putExtra("date", date);

			i.putExtra("id", "6226011030776167");
			i.putExtra("bin", "622601");
			i.putExtra("name", "中信银行");
			i.putExtra("date", "0819");
			startActivity(i);
		} else {
			Toast.makeText(this, "获取磁条卡数据失败，请确保已经刷卡", Toast.LENGTH_LONG).show();
		}
	}

	private void bonusConsume() {

	}

}
