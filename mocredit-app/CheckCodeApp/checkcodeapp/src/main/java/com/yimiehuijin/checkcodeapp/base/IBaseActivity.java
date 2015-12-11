package com.yimiehuijin.checkcodeapp.base;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.yimiehuijin.checkcodeapp.R;
import com.yimiehuijin.codeandbonuslibrary.views.dialog.ProgressDialog;

/**
 * Created by Chanson on 2015/11/23.
 */
public abstract class IBaseActivity extends FragmentActivity{

    private ProgressDialog dialog;

    public static final int MENU_CLICK = 0x01;

    public static final int LEFT_CLICK = 0x02;

    private ImageButton left, menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initAactivity();
    }

    protected abstract int getLayoutId();

    public abstract void initAactivity();

    protected View f(int id){
        return findViewById(id);
    }

    protected void showProgressDialog(String msg){
        if(dialog == null){
            dialog = new ProgressDialog(this);
        }
        if(!isActivityAvaliable() || dialog.isShowing()){
            return;
        }
        dialog.setMsg(msg);
        dialog.show();
    }

    protected boolean isActivityAvaliable(){
        return !(this == null || this.isFinishing() || this.isDestroyed());
    }

    protected void dismissProgressDialog(){
        if(dialog == null){
            return;
        }
        dialog.dismiss();
    }

    private void initActionBar() {
        if (getActionBar() != null) {
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            View actionbar = LayoutInflater.from(this).inflate(
                    R.layout.actionbar, null);
            getActionBar().setCustomView(actionbar, lp);
            getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getActionBar().setDisplayShowCustomEnabled(true);

            left = (ImageButton) actionbar.findViewById(R.id.left);
            menu = (ImageButton) actionbar.findViewById(R.id.menu);
            left.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    onActionBarClick(LEFT_CLICK);
                }
            });
            menu.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    onActionBarClick(MENU_CLICK);
                }
            });
        }
    }

    protected  void onActionBarClick(int flag) {

    }

}
