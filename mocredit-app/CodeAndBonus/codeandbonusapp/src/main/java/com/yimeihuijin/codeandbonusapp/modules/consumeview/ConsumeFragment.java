package com.yimeihuijin.codeandbonusapp.modules.consumeview;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.presenter.ConsumePresenter;
import com.yimeihuijin.codeandbonusapp.presenter.SigninPresenter;
import com.yimeihuijin.commonlibrary.base.BaseFragment;
import com.yimeihuijin.commonlibrary.widgets.CodeScreen;
import com.yimeihuijin.commonlibrary.widgets.FlashButton;
import com.yimeihuijin.commonlibrary.widgets.FlashLayout;

/**
 * 主界面
 * Created by Chanson on 2015/12/16.
 */
public class ConsumeFragment extends BaseFragment implements View.OnClickListener,ConsumePresenter.IConsumeView{

    private FlashButton button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,buttonConfirm;
    private ImageButton buttonDel;
    private Button keyboard,stateSwitch;
    private CodeScreen screen;
    private FlashLayout parent;

    private SigninPresenter.ISigninView view;
    private ConsumePresenter presenter;

    public ConsumeFragment(SigninPresenter.ISigninView view){
        this.view = view;
        presenter = new ConsumePresenter(this);
    }

    @Override
    public void initialize() {
        presenter.onCreate();
    }

    @Override
    public void findViews(View v){
        (button0 = (FlashButton) v.findViewById(R.id.code_scan_num0)).setOnClickListener(this);
        (button1 = (FlashButton) v.findViewById(R.id.code_scan_num1)).setOnClickListener(this);
        (button2 = (FlashButton) v.findViewById(R.id.code_scan_num2)).setOnClickListener(this);
        (button3 = (FlashButton) v.findViewById(R.id.code_scan_num3)).setOnClickListener(this);
        (button4 = (FlashButton) v.findViewById(R.id.code_scan_num4)).setOnClickListener(this);
        (button5 = (FlashButton) v.findViewById(R.id.code_scan_num5)).setOnClickListener(this);
        (button6 = (FlashButton) v.findViewById(R.id.code_scan_num6)).setOnClickListener(this);
        (button7 = (FlashButton) v.findViewById(R.id.code_scan_num7)).setOnClickListener(this);
        (button8 = (FlashButton) v.findViewById(R.id.code_scan_num8)).setOnClickListener(this);
        (button9 = (FlashButton) v.findViewById(R.id.code_scan_num9)).setOnClickListener(this);
        parent = (FlashLayout) v.findViewById(R.id.code_scan_parent);

        button0.setParentView(parent);
        button1.setParentView(parent);
        button2.setParentView(parent);
        button3.setParentView(parent);
        button4.setParentView(parent);
        button5.setParentView(parent);
        button6.setParentView(parent);
        button7.setParentView(parent);
        button8.setParentView(parent);
        button9.setParentView(parent);

        (buttonConfirm = (FlashButton) v.findViewById(R.id.code_scan_confirm)).setOnClickListener(this);
        (buttonDel = (ImageButton) v.findViewById(R.id.code_scan_delete)).setOnClickListener(this);

        buttonConfirm.setParentView(parent);

        (keyboard = (Button) v.findViewById(R.id.code_scan_keyboard)).setOnClickListener(this);
        (stateSwitch = (Button) v.findViewById(R.id.code_scan_cancel)).setOnClickListener(this);

        screen = (CodeScreen)v.findViewById(R.id.code_scan_screen);

        buttonDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                screen.clearScreen();
                return true;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_consume;
    }

    @Override
    public CodeScreen getScreen() {
        return screen;
    }

    @Override
    public Button getKeyBord() {
        return keyboard;
    }

    @Override
    public void showKeyBoard() {
        view.showKeyBoard();
    }

    @Override
    public void setSwitch(String text) {
        stateSwitch.setText(text);
    }

    @Override
    public void gotoPay() {
        view.gotoFragment(new PayFragment(view));
    }

    @Override
    public void gotoCancel() {
        view.gotoFragment(new ConsumeRevokeFragment(view));
    }

    public void refresh(){
        presenter.refreshView();
    }

    @Override
    public Activity getAvtivity() {
        return getActivity();
    }

    @Override
    public void showDialog(String msg) {
        view.showDialog(msg);
    }

    @Override
    public void dismisDialog() {
        view.dismisDialog();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onClick(View view) {
        presenter.onAction(view.getId());
    }
}
