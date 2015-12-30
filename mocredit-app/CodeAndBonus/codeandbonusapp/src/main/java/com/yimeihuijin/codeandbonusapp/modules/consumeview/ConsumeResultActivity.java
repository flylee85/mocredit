package com.yimeihuijin.codeandbonusapp.modules.consumeview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.presenter.ConsumeResultPresenter;
import com.yimeihuijin.commonlibrary.base.BaseActivity;
import com.yimeihuijin.commonlibrary.widgets.dialog.ProgressDialog;

/**
 * 消费/撤销结果页面
 * Created by Chanson on 2015/12/17.
 */
public class ConsumeResultActivity extends BaseActivity implements ConsumeResultPresenter.IConsumeResultView,View.OnClickListener{

    private ImageView icon;
    private TextView msg;
    private Button back,print;

    private ConsumeResultPresenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    protected void initialize() {

        icon = (ImageView) f(R.id.consume_result_icon);
        msg = (TextView) f(R.id.consume_result_msg);
        (back = (Button) f(R.id.consume_result_back)).setOnClickListener(this);
        (print = (Button) f(R.id.consume_result_print)).setOnClickListener(this);
        presenter = new ConsumeResultPresenter(this);
        presenter.onCreate();
    }

    @Override
    public ImageView getImage() {
        return icon;
    }

    @Override
    public TextView getMsgTextView() {
        return msg;
    }

    @Override
    public Button getPrintButton() {
        return print;
    }

    @Override
    public void showPrintDialog(String msg,ProgressDialog.IDialogListener listener) {
        showProgressDialog(msg,listener);
    }

    @Override
    public void onClick(View view) {
        presenter.onAction(view.getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
