package com.yimiehuijin.bonusconusmeapp.modules.signin;

import android.content.Intent;
import android.widget.TextView;

import com.yimiehuijin.bonusconusmeapp.R;
import com.yimiehuijin.bonusconusmeapp.base.IBaseActivity;
import com.yimiehuijin.bonusconusmeapp.modules.consume.BonusConsumeActivity;
import com.yimiehuijin.bonusconusmeapp.service.WangposService;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.web.SigninUtil;

public class SigninActivity extends IBaseActivity {

    private TextView describe;

    private static final String INIT_WEIPOS_ERROR = "初始化旺POS失败：";
    private static final String INIT_WEIPOS_JSON_ERROR = "初始化旺POS失败：无法解析设备信息:";

    private final String TAG_SIGNIN = "singin";
    private final String TAG_SIGNIN_RECEIPT = "receipt";
    private final String TAG_ENCRYPT_TEST = "encrypt_test";
    private final String TAG_COMM_TEST = "comm_test";

    private SigninUtil.SigninListener signListener;

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.activity_loading;
    }

    @Override
    public void initAactivity() {

        describe = (TextView) f(R.id.loading_dsc);
        loadData();
    }

    private void loadData() {
        if(App.getInstance().deviceInfo == null){
            describe.setText("初始化失败");
        }else{
            signListener = new SigninUtil.SigninListener() {

                @Override
                public void onSigninSuccess() {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(SigninActivity.this,
                            WangposService.class);
                    startService(i);
                    i = new Intent(SigninActivity.this, BonusConsumeActivity.class);
                    startActivity(i);
                }

                @Override
                public void onSigninFailure() {
                    // TODO Auto-generated method stub
                    describe.setText("签到失败");
                }
            };
            new SigninUtil(signListener, SigninActivity.this).signIn();
        }

    }

}
