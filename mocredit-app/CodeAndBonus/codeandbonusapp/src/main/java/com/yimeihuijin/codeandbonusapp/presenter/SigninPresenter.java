package com.yimeihuijin.codeandbonusapp.presenter;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.model.ConsumeModel;
import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.model.SigninModel;
import com.yimeihuijin.codeandbonusapp.modules.consumeview.ConsumeFragment;
import com.yimeihuijin.codeandbonusapp.utils.BusProvider;
import com.yimeihuijin.codeandbonusapp.utils.StringUtils;
import com.yimeihuijin.commonlibrary.Presenter.BasePresenter;
import com.yimeihuijin.commonlibrary.base.BaseFragment;
import com.yimeihuijin.commonlibrary.constants.URLs;
import com.yimeihuijin.commonlibrary.widgets.dialog.InputDialog;

import java.util.HashMap;

import cn.weipass.pos.sdk.Scanner;

/**
 * 签到界面的表现层，负责处理签到页面和标题栏的用户操作，并通知SigninModel进行相应的网络请求和数据库操作，并把接收到的数据在签到页面和标题栏进行显示
 * Created by Chanson on 2015/12/15.
 */

public class SigninPresenter extends BasePresenter implements SigninModel.ISigninPresenter{

    public static final String CODE_TOTAL = "totalCode";
    public static final String CODE_TOTAL_AMT = "totalCodeAmt";
    public static final String BONUS_TOTAL = "totalBonus";
    public static final String BONUS_TOTAL_AMT = "totalBonusAmt";
    public static final String CODE_REVOKE_TOTAL = "totalCodeRevoke";
    public static final String CODE_REVOKE_TOTAL_AMT = "totalCodeRevokeAmt";
    public static final String BONUS_REVOKE_TOTAL = "totalBonusRevoke";
    public static final String BONUS_REVOKE_TOTAL_AMT = "totalBonusRevokeAmt";

    private ISigninView view;
    private SigninModel model;
    private ConsumeFragment fragment;
    private Handler handler;

    private String ipPattern = "[1-2]?\\d?\\d.[1-2]?\\d?\\d.[1-2]?\\d?\\d.[1-2]?\\d?\\d";
    

    public SigninPresenter(ISigninView view) {
        this.view = view;
        model = new SigninModel(this);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        BusProvider.get().registerSticky(this);

        view.setRunningMsg("正在签到，请稍候...");
        String versionName = "版本号:";
        try {
            PackageInfo pi = App.getInstance().getPackageManager().getPackageInfo("com.yimeihuijin.codeandbonusapp",0);
            versionName += pi.versionName;
        }catch (Exception e){
            versionName +="无";
        }
        view.getVersionTextView().setText(versionName);

        DeviceModel.getInstance().initialize(App.getInstance(), new DeviceModel.IDeviceInitPresenter() { //初始化机具
            @Override
            public void initOk() {
                model.signIn();
            }

            @Override
            public void initError(String msg) {
                signinFailure(msg);
            }
        });

        view.getLeftMenu().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {   //左侧滑动菜单点击事件
                switch (i) {
                    case 1:
                        reSignin();
                        break;
                    case 0:
                        printOrders();
                        break;
                    case 2:
                        changeIP();
                        break;

                }
            }
        });

        handler = new Handler(view.getLooper()){    //打印消费清单
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                HashMap<String,Integer> map = (HashMap<String, Integer>) msg.obj;
                String printInfo = "\n"+ DeviceModel.PRINT_DIVIDER+
                        "商户名称："+DeviceModel.getInstance().getDevice().mname==null?"":DeviceModel.getInstance().getDevice().mname+"\n"+
                        "日期："+StringUtils.getCurrentDate()+"\n"+
                        "时间："+StringUtils.getCurrentTime()+"\n"+ DeviceModel.PRINT_DIVIDER+
                        "验码笔数："+map.get(CODE_TOTAL)+"\n"+
                        "验码金额："+map.get(CODE_TOTAL_AMT)+"\n"+
                        "验码撤销总笔数："+map.get(CODE_REVOKE_TOTAL)+"\n"+
                        "验码撤销总金额："+map.get(CODE_REVOKE_TOTAL_AMT)+"\n"+
                        "积分消费笔数："+map.get(BONUS_TOTAL)+"\n"+
                        "积分消费金额："+map.get(BONUS_TOTAL_AMT)+"\n"+
                        "积分撤销总笔数："+map.get(BONUS_REVOKE_TOTAL)+"\n"+
                        "积分撤销总金额："+map.get(BONUS_REVOKE_TOTAL_AMT)+"\n"+DeviceModel.PRINT_DIVIDER+"\n";
                view.showDialog("正在打印凭单...");
                DeviceModel.getInstance().printNormal(printInfo);
                view.dismisDialog();
            }
        };

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.get().unregister(this);
    }

    public void onServiceSignin(){   //后台定时签到
        model.postSignIn();
    }

    @Override
    public void onResume() {
//        if(BusProvider.get().getStickyEvent(ConsumeResultPresenter.ResultBackAction.class) != null){
//            view.gotoFragment(fragment);
//        }
    }

    /**
     * 获取其他Fragment或Activity的回到主界面请求
     * @param action
     */
    public void onEvent(ConsumeResultPresenter.ResultBackAction action){
        view.goBackToFragment(fragment);
    }

    @Override
    public void signinSuccess() {
        view.setRunningMsg("签到成功");
        fragment = new ConsumeFragment(view);
        view.gotoFragment(fragment);
        view.gotoService();
    }

    /**
     * 重置签到（暂时对客户屏蔽）
     */
    public void reSignin(){
        if(fragment != null) {
            view.goBackToFragment(fragment);
            view.clearFragment(fragment);
        }
        model.reSignin();
    }

    public void printOrders(){
        view.showDialog("正在生成清单...");
        new Thread(){
            @Override
            public void run() {
                super.run();
                HashMap<String,Integer> map = App.getInstance().getDBHelper().getTradelist();
                Message msg = new Message();
                msg.obj = map;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private void changeIP(){
        InputDialog dialog = new InputDialog((Activity)view, new InputDialog.IDialogInputListener() {
            @Override
            public String onInputConfirm(String text) {
                if(text.matches(ipPattern)){
                    URLs.setIP(text,App.getInstance());
                    reSignin();
                    return null;
                }else{
                    return "IP地址格式错误，请重新输入！";
                }
            }
        });
        dialog.setTitle("IP修改");
        dialog.setHint("请输入新的IP");
        dialog.setContent(URLs.getIP());
        dialog.show();;
    }

    /**
     * 处理view的onKey事件
     * @param event
     */
    public void onKeyEvent(KeyEvent event){
        if(fragment == null){
            return;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DEL:
                fragment.getScreen().deleteBackCode();
                return;
            case KeyEvent.KEYCODE_ENTER:
                fragment.getKeyBord().performClick();
                return;
        }
        String s = KeyEvent.keyCodeToString(event.getKeyCode());
        if (s.split("_")[1].length() > 1) {
            return;
        }
        if (!StringUtils.isNumberOrAlphabet(s.split("_")[1])) {
            return;
        }
        fragment.getScreen().inputCode(s.split("_")[1].toCharArray()[0]);
    }

    /**
     *处理标题栏事件
     * @param id
     */
    public void onAction(int id){
        switch (id){
            case R.id.action_scan:
                if(fragment == null){
                    return;
                }
                view.goBackToFragment(fragment);
                DeviceModel.getInstance().scan(new Scanner.OnResultListener() {
                    @Override
                    public void onResult(int i, String s) {
                        if (s != null) {
                                fragment.getScreen().clearScreen();
                                fragment.getScreen().inputString(s.toUpperCase());
                        }
                    }
                });
                break;
            case android.R.id.home:
                if(view.getDrawer().isDrawerOpen(GravityCompat.START)){
                    view.getDrawer().closeDrawers();
                }else{
                    view.getDrawer().openDrawer(GravityCompat.START);
                }
                break;
        }
    }

    @Override
    public void signinFailure(String msg) {
        view.setRunningMsg(msg);
    }

    public interface ISigninView extends IDialogView{
        public void setRunningMsg(String msg);
        public void showKeyBoard();
        public void gotoFragment(BaseFragment fragment);
        public void goBackToFragment(BaseFragment fragment);
        public void goBack();
        public void gotoService();
        public DrawerLayout getDrawer();
        public ListView getLeftMenu();
        public void clearFragment(BaseFragment fragment);
        public Looper getLooper();
        public TextView getVersionTextView();
    }

    public interface IDialogView{
        public void showDialog(String msg);
        public void dismisDialog();
    }


}
