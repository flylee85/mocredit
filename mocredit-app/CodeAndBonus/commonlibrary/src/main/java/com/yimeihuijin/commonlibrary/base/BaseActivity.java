package com.yimeihuijin.commonlibrary.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yimeihuijin.commonlibrary.widgets.dialog.ProgressDialog;

/**
 * Created by Chanson on 2015/12/15.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initialize();
    }

    public abstract int getLayoutId();
    protected abstract void initialize();

    protected void replace(BaseFragment fragment,int id){
        replace(fragment,id,false);
    }

    protected void replace(BaseFragment fragment,int id,boolean isClear){
        if(isDestroyed() || isFinishing()){
            return;
        }
        if(isClear) {
            getSupportFragmentManager().popBackStackImmediate(null, 1);
        }
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(id, fragment).addToBackStack(fragment.getClass().toString()).commitAllowingStateLoss();
    }

    protected View f(int id){
        return findViewById(id);
    }

    protected void showProgressDialog(String msg){
        showProgressDialog(msg, null);
    }

    protected void showProgressDialog(String msg,ProgressDialog.IDialogListener listener){
        if(dialog == null){
            dialog = new ProgressDialog(this,listener);
        }
        dialog.setMsg(msg);
        if(!isActivityAvaliable() || dialog.isShowing()) {
            return;
        }
        dialog.show();
    }

    protected void dismissProgressDialog(){
        if(dialog == null){
            return;
        }
        dialog.dismiss();
    }


    protected boolean isActivityAvaliable(){
        return !(this == null || this.isFinishing() || this.isDestroyed());
    }

}
