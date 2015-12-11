package com.yimiehuijin.checkcodeapp.modules.checkcode;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.yimiehuijin.checkcodeapp.R;
import com.yimiehuijin.checkcodeapp.base.IBaseActivity;
import com.yimiehuijin.checkcodeapp.modules.setting.SettingActivity;
import com.yimiehuijin.codeandbonuslibrary.data.CheckCode;
import com.yimiehuijin.codeandbonuslibrary.data.CheckCodeResponse;
import com.yimiehuijin.codeandbonuslibrary.data.PostData;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;
import com.yimiehuijin.codeandbonuslibrary.views.CodeScreen;

import cn.weipass.pos.sdk.MagneticReader;
import cn.weipass.pos.sdk.Scanner;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * Created by Chanson on 2015/11/23.
 */
public class CheckcodeActivity extends IBaseActivity{

    CodeScreen screen;
    private Button delete;
    private String QR_INFO = "";
    private MagneticReader mMagneticReader;
    private Button keyboard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_code_scan;
    }

    @Override
    public void initAactivity() {
        keyboard = (Button) f(R.id.code_scan_keyboard);
        screen = (CodeScreen) f(R.id.code_scan_screen);
        delete = (Button) f(R.id.code_scan_delete);
        delete.setOnLongClickListener(new View.OnLongClickListener() {

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
                checkCode(screen.getCode());
                break;
            case R.id.code_scan_keyboard:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
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

    private void checkCodeResult(CheckCodeResponse response, boolean isSuccess) {
        Intent i = new Intent(this, CheckcodeResultActivity.class);

        i.putExtra("result", isSuccess);
        i.putExtra("data", response);
        startActivity(i);
    }

    @Override
    protected void onActionBarClick(int flag) {
        // TODO Auto-generated method stub
        super.onActionBarClick(flag);
        switch (flag) {
            case IBaseActivity.LEFT_CLICK:
                Scanner scanner = WeiposImpl.as().openScanner();
                if (scanner != null) {
                    scanner.scan(Scanner.TYPE_QR, new Scanner.OnResultListener() {

                        @Override
                        public void onResult(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            if (arg1 != null) {
                                if (StringUtils.isNumber(arg1)) {
                                    QR_INFO = arg1;
                                    screen.clearScreen();
                                    screen.inputString(arg1);
                                } else {
                                    Toast.makeText(CheckcodeActivity.this,
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

    private void checkCode(String code) {

        showProgressDialog("正在发送请求...");
        CheckCode cc = new CheckCode();
        cc.code = code;
        cc.orderId = StringUtils.getOrderId();

        PostData data = new PostData(cc);
        //postT(URLs.URL_CHECKCODE, data);
    }
}
