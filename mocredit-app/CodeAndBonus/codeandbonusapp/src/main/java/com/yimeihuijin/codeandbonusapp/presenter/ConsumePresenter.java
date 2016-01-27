package com.yimeihuijin.codeandbonusapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.model.ConsumeModel;
import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.modules.consumeview.ConsumeFragment;
import com.yimeihuijin.codeandbonusapp.modules.consumeview.ConsumeResultActivity;
import com.yimeihuijin.codeandbonusapp.utils.BusProvider;
import com.yimeihuijin.codeandbonusapp.utils.StringUtils;
import com.yimeihuijin.commonlibrary.Presenter.BasePresenter;
import com.yimeihuijin.commonlibrary.widgets.CodeScreen;
import com.yimeihuijin.commonlibrary.widgets.dialog.AlertDialog;
import com.yimeihuijin.commonlibrary.widgets.dialog.ProgressDialog;

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
            case R.id.code_scan_consume:
                onConsume(false);
                break;
            case R.id.code_scan_keyboard:
                this.view.showKeyBoard();
                break;
            case R.id.code_scan_cancel:
                if(view.getScreen().getCode().length() < 10 || !StringUtils.isNumberOrAlphabet(view.getScreen().getCode())) {
                    Toast.makeText(App.getInstance(),"订单号格式错误，请检查后重新输入！",Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog dialog = new AlertDialog(view.getAvtivity(), new ProgressDialog.IDialogListener() {
                    @Override
                    public void onConfirm() {
                        onConsume(true);
                    }
                });
                dialog.setContent("是否确定撤销订单\n"+view.getScreen().getCode());
                dialog.setTitle("订单撤销");
                dialog.show();
                break;
        }
    }

    public void onConsume(boolean isRevoke){
        if(ConsumeModel.isCodeConsuming()){
            view.showDialog("正在验码...");
            model.todo(view.getScreen().getCode(),isRevoke);
        }else{
            if(!isRevoke){
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
        view.getPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case ConsumeFragment.POSITION_CODE:
                        ConsumeModel.setModeTo(ConsumeModel.MODE_CODE);
                        break;
                    case ConsumeFragment.POSITION_CONSUME:
                        ConsumeModel.setModeTo(ConsumeModel.MODE_BONUS);
                        break;
                }
                for (TextView tv : view.getIndicators()) {
                    tv.setBackgroundResource(R.drawable.indicator_none);
                    tv.setTextColor(Color.GRAY);
                }
                view.getIndicators()[position].setBackgroundResource(R.drawable.indicator_line);
                view.getIndicators()[position].setTextColor(view.getAvtivity().getResources().getColor(R.color.theme_color1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        view.getIndicators()[ConsumeFragment.POSITION_CODE].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getPager().setCurrentItem(ConsumeFragment.POSITION_CODE);
            }
        });

        view.getIndicators()[ConsumeFragment.POSITION_CONSUME].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getPager().setCurrentItem(ConsumeFragment.POSITION_CONSUME);
            }
        });

        view.getPager().setCurrentItem(ConsumeFragment.POSITION_CODE,true);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onResume() {
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
        public ImageButton getKeyBord();
        public void showKeyBoard();
        public void gotoPay();
        public void gotoCancel();
        public int getPosition();
        public ViewPager getPager();
        public TextView[] getIndicators();
        public Activity getAvtivity();
    }
}
