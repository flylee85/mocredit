package com.yimiehuijin.checkcodeapp.modules.checkcode;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yimiehuijin.checkcodeapp.R;
import com.yimiehuijin.checkcodeapp.base.IBaseActivity;
import com.yimiehuijin.codeandbonuslibrary.data.CheckCodeResponse;
import com.yimiehuijin.codeandbonuslibrary.utils.PrinterUtils;

/**
 * Created by Chanson on 2015/11/23.
 */
public class CheckcodeResultActivity extends IBaseActivity {

    private Bundle data;

    private TextView content, error;

    private boolean result;

    private CheckCodeResponse ret;

    @Override
    public int getLayoutId() {
        result = getIntent().getExtras().getBoolean("result");
        if (result) {
            return R.layout.activity_code_scan_result;
        } else {
            return R.layout.activity_code_scan_result_fail;
        }
    }

    @Override
    public void initAactivity() {
        if (result) {
            content = (TextView) f(R.id.checkcode_result);
        } else {
            error = (TextView) f(R.id.checkcode_result_error);
        }

        data = getIntent().getExtras();

        ret = (CheckCodeResponse) data
                .getSerializable("data");
        if (ret == null && result) {
        } else if (ret != null && result) {
            content.setText(ret.posSuccessMsg);
        } else if (ret != null && !result) {
            if (ret.errorMes != null || "".equals(ret.errorMes)) {
                error.setText(ret.errorMes);
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_code_result_back:
                finish();
                break;
            case R.id.check_code_result_print:
                PrinterUtils.printNormal(ret.printInfo);
                PrinterUtils.printNormal(ret.erweima);
                break;
            case R.id.check_code_result_fail_back:
                finish();
                break;
        }
    }
}
