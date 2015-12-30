package com.yimeihuijin.codeandbonusapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.model.ConsumeModel;
import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.modules.consumeview.ConsumeResultActivity;
import com.yimeihuijin.codeandbonusapp.utils.BusProvider;
import com.yimeihuijin.commonlibrary.Presenter.BasePresenter;
import com.yimeihuijin.commonlibrary.widgets.CodeScreen;

/**
 * 主界面的表现层
 * Created by Chanson on 2015/12/16.
 */
public class ConsumePresenter extends BasePresenter implements ConsumeModel.IConsumePresenter{

    private IConsumeView view;

    private ConsumeModel model;

    public ConsumePresenter(IConsumeView view){
        this.view = view;
        model = new ConsumeModel(this);
    }

    public void onAction(int id){
        switch (id){
            case R.id.code_scan_num0:
                view.getScreen().inputCode('0');
                break;
            case R.id.code_scan_num1:
                view.getScreen().inputCode('1');
                break;
            case R.id.code_scan_num2:
                view.getScreen().inputCode('2');
                break;
            case R.id.code_scan_num3:
                view.getScreen().inputCode('3');
                break;
            case R.id.code_scan_num4:
                view.getScreen().inputCode('4');
                break;
            case R.id.code_scan_num5:
                view.getScreen().inputCode('5');
                break;
            case R.id.code_scan_num6:
                view.getScreen().inputCode('6');
                break;
            case R.id.code_scan_num7:
                view.getScreen().inputCode('7');
                break;
            case R.id.code_scan_num8:
                view.getScreen().inputCode('8');
                break;
            case R.id.code_scan_num9:
                view.getScreen().inputCode('9');
                break;
            case R.id.code_scan_delete:
                view.getScreen().deleteBackCode();
                break;
            case R.id.code_scan_confirm:
                onConsume();
                break;
            case R.id.code_scan_keyboard:
                this.view.showKeyBoard();
                break;
            case R.id.code_scan_cancel:
                view.setSwitch(ConsumeModel.switchState());
                view.getScreen().setHints(ConsumeModel.getHint());
                break;
        }
    }

    public void onConsume(){
        if(ConsumeModel.isCodeConsuming()){
            view.showDialog("正在验码...");
            model.todo(view.getScreen().getCode());
        }else{
            if(ConsumeModel.isConsuming()){
                DeviceModel.Card card = DeviceModel.getInstance().getCard();
                if(card != null){
                    view.gotoPay();
                    BusProvider.get().postSticky(card);
                }else{
                    Toast.makeText(App.getInstance(),"未获取到刷卡信息，请重新刷卡",Toast.LENGTH_LONG).show();
                }
            }else{
                view.gotoCancel();
                BusProvider.get().postSticky(view.getScreen().getCode());
            }
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onResume() {
        refreshView();
    }

    public void refreshView(){
        view.setSwitch(ConsumeModel.getState());
        view.getScreen().setHints(ConsumeModel.getHint());
    }

    @Override
    public void conusmeComplete(ConsumeResultPresenter.ConsumeResultObject data) {
        view.dismisDialog();
        Intent i = new Intent(view.getAvtivity(), ConsumeResultActivity.class);
        view.getAvtivity().startActivity(i);
        BusProvider.get().postSticky(data);
    }

    public interface IConsumeView extends SigninPresenter.IDialogView{
        public CodeScreen getScreen();
        public Button getKeyBord();
        public void showKeyBoard();
        public void setSwitch(String text);
        public void gotoPay();
        public void gotoCancel();
        public Activity getAvtivity();
    }
}
