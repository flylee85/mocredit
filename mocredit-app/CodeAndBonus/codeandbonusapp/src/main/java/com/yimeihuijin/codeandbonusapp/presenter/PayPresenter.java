package com.yimeihuijin.codeandbonusapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.model.ConsumeModel;
import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.modules.consumeview.ConsumeResultActivity;
import com.yimeihuijin.codeandbonusapp.utils.BusProvider;
import com.yimeihuijin.commonlibrary.Presenter.BasePresenter;
import com.yimeihuijin.commonlibrary.utils.BankInfoUtil;
import com.yimeihuijin.commonlibrary.utils.StateLock;

/**
 * 主界面刷卡后，进入的活动选择界面的表现层，负责活动选择，发起积分消费请求给ConsumeModel,把CconsumeModel传回的消费结果进行处理，并控制view显示
 * Created by Chanson on 2015/12/17.
 */
public class PayPresenter extends BasePresenter implements ConsumeModel.IConsumePresenter{

    private IPayView view;
    private ConsumeModel consumeModel;
    private DeviceModel.Card card;

    public PayPresenter(IPayView view){
        this.view = view;
        consumeModel = new ConsumeModel(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        BusProvider.get().registerSticky(this);
        ConsumeModel.ActivitiesAdapter adapter = consumeModel.getAdapter();
        if(adapter.getCount() < 1){
            view.noActivities();
        }
        view.getList().setAdapter(adapter);
        view.getList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                consumeModel.getAdapter().setSeletecedItem(i);
                consumeModel.getAdapter().notifyDataSetChanged();
            }
        });
        DeviceModel.Card card = BusProvider.get().getStickyEvent(DeviceModel.Card.class);
        if (card != null) {
            onEvent(card);
        }
    }

    public void onAction(int id){
        switch (id){
            case R.id.pay_confirm:
                if(StateLock.isGlobalLocked()){
                    return;
                }
                StateLock.lock();
                consumeModel.todo(null,false);
                break;
            case R.id.pay_back:
                view.goBack();
                break;
        }
    }

    /**
     * 获取ConsumePresenter发送的卡片信息
     * @param card
     */
    public void onEvent(DeviceModel.Card card){
        this.card = card;
        view.setBankName(BankInfoUtil.getNameOfBank(card.cardbin.toCharArray(),0));
        view.setCardNo(card.cardno);
        consumeModel.setCard(card);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.get().unregister(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void conusmeComplete(ConsumeResultPresenter.ConsumeResultObject data) {
       Intent i = new Intent(view.getMain(), ConsumeResultActivity.class);
        view.getMain().startActivity(i);
        BusProvider.get().postSticky(data);
    }


    public interface IPayView extends SigninPresenter.IDialogView{
        public ListView getList();
        public void setBankName(String name);
        public void goBack();
        public Activity getMain();
        public void noActivities();
        public void setCardNo(String no);
    }
}
