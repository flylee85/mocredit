package com.yimeihuijin.codeandbonusapp.modules.consumeview;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.presenter.ConsumeRevokePresenter;
import com.yimeihuijin.codeandbonusapp.presenter.SigninPresenter;
import com.yimeihuijin.commonlibrary.base.BaseFragment;

/**
 * 积分消费撤销页面
 * Created by Chanson on 2015/12/21.
 */
public class ConsumeRevokeFragment extends BaseFragment implements ConsumeRevokePresenter.IConsumeRevokeView,View.OnClickListener{

    private ConsumeRevokePresenter presenter;

    private TextView orderId;

    private Button confirm,back;

    private SigninPresenter.ISigninView view;

    public ConsumeRevokeFragment(SigninPresenter.ISigninView view){
        this.view = view;
    }

    @Override
    public void initialize() {
        presenter = new ConsumeRevokePresenter(this);
        presenter.onCreate();

    }

    @Override
    public void findViews(View v) {
        orderId = (TextView) v.findViewById(R.id.consume_cancel_orderid);

        (confirm = (Button) v.findViewById(R.id.consume_cancel_confirm)).setOnClickListener(this);
        (back = (Button) v.findViewById(R.id.consume_cancel_back)).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bonus_revoke;
    }

    @Override
    public TextView getOrderIdTextView() {
        return orderId;
    }

    @Override
    public void goBack() {
        view.goBack();
    }

    @Override
    public Activity getMain() { //获取activity
        return getActivity();
    }

    @Override
    public void onClick(View view) {
        presenter.onAction(view.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
