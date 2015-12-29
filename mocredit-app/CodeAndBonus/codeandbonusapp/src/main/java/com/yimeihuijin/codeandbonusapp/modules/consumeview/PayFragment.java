package com.yimeihuijin.codeandbonusapp.modules.consumeview;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.presenter.PayPresenter;
import com.yimeihuijin.codeandbonusapp.presenter.SigninPresenter;
import com.yimeihuijin.commonlibrary.base.BaseFragment;

/**
 * 积分消费活动选择页面
 * Created by Chanson on 2015/12/17.
 */
public class PayFragment extends BaseFragment implements View.OnClickListener,PayPresenter.IPayView{

    private PayPresenter presenter;
    private ListView listView;
    private TextView backName,cardNo;
    private Button confirm,back;
    private SigninPresenter.ISigninView view;

    public PayFragment(SigninPresenter.ISigninView view){
        this.view = view;
    }

    @Override
    public void initialize() {
        presenter = new PayPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void findViews(View v) {
        listView = (ListView) v.findViewById(R.id.pay_activities);
        (back = (Button) v.findViewById(R.id.pay_back)).setOnClickListener(this);
        (confirm = (Button) v.findViewById(R.id.pay_confirm)).setOnClickListener(this);
        backName = (TextView) v.findViewById(R.id.pay_bankname);
        cardNo = (TextView) v.findViewById(R.id.pay_cardid);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pay;
    }

    @Override
    public void onClick(View view) {
        presenter.onAction(view.getId());
    }

    @Override
    public ListView getList() {
        return listView;
    }

    @Override
    public void setBankName(String name) {
        backName.setText(name);
    }

    @Override
    public void goBack() {
        view.goBack();
    }

    @Override
    public Activity getMain() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setCardNo(String no) {
        cardNo.setText(no);
    }

    @Override
    public void showDialog(String msg) {
        view.showDialog(msg);
    }

    @Override
    public void dismisDialog() {
        view.dismisDialog();
    }
}
