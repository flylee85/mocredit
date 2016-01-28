package com.yimeihuijin.codeandbonusapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.model.ConsumeModel;
import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.modules.consumeview.ConsumeResultActivity;
import com.yimeihuijin.codeandbonusapp.utils.BusProvider;
import com.yimeihuijin.commonlibrary.Presenter.BasePresenter;
import com.yimeihuijin.commonlibrary.utils.StateLock;

/**
 * 消费撤销页面的表现层
 * Created by Chanson on 2015/12/21.
 */
public class ConsumeRevokePresenter extends BasePresenter implements ConsumeModel.IConsumePresenter{

    private IConsumeRevokeView view;
    private ConsumeModel model;
    private String orderId;

    public ConsumeRevokePresenter(IConsumeRevokeView view){
        this.view = view;
        model = new ConsumeModel(this);
    }

    public void onAction(int id){
        switch (id){
            case R.id.consume_cancel_back:
                view.goBack();
                break;
            case R.id.consume_cancel_confirm:
                DeviceModel.Card card = DeviceModel.getInstance().getCard();
                if(card != null){
                    if(StateLock.isGlobalLocked()){
                        return;
                    }
                    StateLock.lock();
                    view.showDialog("正在撤销，请稍候...");
                    model.setCard(card);
                    model.todo(orderId,true);
                }else{
                    Toast.makeText(App.getInstance(), "未获取到刷卡信息，请重新刷卡", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void onEvent(String orderId){
        this.orderId = orderId;
        view.getOrderIdTextView().setText(orderId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BusProvider.get().registerSticky(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.get().unregister(this);
    }

    @Override
    public void onResume() {
        view.getOrderIdTextView().setText(orderId);
    }

    @Override
    public void conusmeComplete(ConsumeResultPresenter.ConsumeResultObject data) {
        view.dismisDialog();
        Intent i = new Intent(view.getMain(), ConsumeResultActivity.class);
        view.getMain().startActivity(i);
        BusProvider.get().postSticky(data);
    }

    public interface IConsumeRevokeView extends SigninPresenter.IDialogView{
        public TextView getOrderIdTextView();
        public void goBack();
        public Activity getMain();
    }
}
