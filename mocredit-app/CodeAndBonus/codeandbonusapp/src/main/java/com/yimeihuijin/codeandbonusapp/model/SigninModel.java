package com.yimeihuijin.codeandbonusapp.model;

import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.model.vo.VO;
import com.yimeihuijin.codeandbonusapp.utils.PostUtil;
import com.yimeihuijin.commonlibrary.constants.Constants;
import com.yimeihuijin.commonlibrary.constants.URLs;

/**
 * Created by Chanson on 2015/12/15.
 *
 * 该类主要用于签到，实例化该类时必须实现ISigninPresenter接口
 *
 */
public class SigninModel extends Model{

    private ISigninPresenter presenter;

    public SigninModel(ISigninPresenter presenter){
        this.presenter = presenter;
        init();
    }

    private PostUtil.PostResponseListener postListener;

    private PostUtil postUtil;

    private static final String TAG_SIGNIN = "su_signin";

    private static final String TAG_RECEIPT = "su_receipt";

    private static final String TAG_ENCRYPT_TEST = "su_encrypt_test";

    private static final String TAG_COMM_TEST = "su_comm_test";

    private static final String TAG_ACTIVITIES = "su_activities";

    private void init() {
        if (postListener == null) {
            postListener = new PostUtil.PostResponseListener() {

                @Override
                public void onResponse(String response, String tag) {
                    // TODO Auto-generated method stub
                    if (TAG_SIGNIN.equals(tag)) {
                        VO.SO sr = new Gson().fromJson(response,
                                VO.SO.class);
                        System.out.println("new akey=" + sr.newakey);
                        if ("ok".equals(sr.state)) { //签到成功
                            App.tmpkey = sr.newakey;
                            App.jieruhao = sr.jieruhao;
                            signInReceipt();  //发起签到回执
                        } else {
                            presenter.signinFailure("签到失败"); //签到失败
                        }
                    } else if (TAG_RECEIPT.equals(tag)) { //签到回执的返回信息
                        execute(new UpdateSigninInfoRunnable(App.tmpkey,App.akey)); //签到绘制成功，更新数据库中存贮的akey
                        App.akey = App.tmpkey;
                        presenter.signinSuccess();
                        updateActivities(); //发起更新活动列表请求
                    } else if (TAG_ENCRYPT_TEST.equals(tag)) {
                        presenter.signinSuccess();  //加密测试成功
                        updateActivities(); //发起更新活动列表请求
                    } else if (TAG_COMM_TEST.equals(tag)) {
                        signInReceipt(); //通信测试成功，再次发起通信回执请求
                    } else if (TAG_ACTIVITIES.equals(tag)) { //获取活动列表成功
                        VO.ARO ar = null;
                        try {
                            ar = new Gson().fromJson(response,
                                    VO.ARO.class);
                        } catch (Exception e) {

                        }
                        if (ar == null) {
                            return;
                        }
                        execute(new StoreActivitysRunnable(response)); //存储获取到的活动列表
                    }
                }

                @Override
                public void onErroResponse(VolleyError error, String tag) {
                    // TODO Auto-generated method stub
                   if (TAG_RECEIPT.equals(tag)) {
                        encryptTest(); //签到回执失败，发起加密测试请求
                    } else if (TAG_ENCRYPT_TEST.equals(tag)) {
                        communicationTest();//加密测试失败，发起通信测试请求
                    }else {
                        presenter.signinFailure(error.getMessage());
                    }
                }
            };

            postUtil = new PostUtil(postListener);
        }
    }

    public void reSignin(){ //使用初始akey重新尝试签到
        App.akey = Constants.INIT_AKEY;
        postSignIn();
    }

    /**
     * 签到
     */
    public void signIn() {
        if (App.akey == null) { //数据库中没有存储akey,则使用初始akey
            App.akey = Constants.INIT_AKEY;
            postSignIn();
        } else {
            Long date = App.getInstance().getDBHelper().findAkeyTime(App.akey); //数据库中有akey,则查询akey存储时间
            Long interval = System.currentTimeMillis() - date;
            if ((interval / (1000 * 60 * 60)) >= 24) { //上次签到时间超过24小时，则重新签到
                postSignIn();
            } else {
                presenter.signinSuccess();
                updateActivities();
            }
        }

    }

    public void postSignIn() {
        PO post = new PO(new Object());
        post(URLs.URL_SIGNIN, post, TAG_SIGNIN);
    }

    private void post(String url, PO data, String tag) {
        postUtil.post(url, data, tag);
    }

    /**
     * 签到回执
     */
    private void signInReceipt() {
        VO.SRO enpost = new VO.SRO();
        enpost.oldAkey = App.akey;
        enpost.newAkey = App.tmpkey;
        enpost.state = "ok";
        PO post = new PO(enpost);
        post(URLs.URL_SIGNIN_RECEIPT, post, TAG_RECEIPT);
    }

    /**
     * 加密测试
     */
    private void encryptTest() {
        String tmp = App.akey;
        App.akey = App.tmpkey;
        App.tmpkey = tmp;
        VO.ETO enpost = new VO.ETO();
        PO post = new PO(enpost);
        post(URLs.URL_ENCRYPT_TEST, post, TAG_ENCRYPT_TEST);
        App.tmpkey = App.akey;
        App.akey = tmp;
    }

    /**
     * 通信测试
     */
    private void communicationTest() {
        PostUtil.commTest(TAG_COMM_TEST, postListener);
    }

    public void updateActivities() {
        PO post = new PO(new Object());
        post(URLs.URL_ACTIVITIES, post, TAG_ACTIVITIES);
    }


    public interface ISigninPresenter{
        public void signinSuccess();
        public void signinFailure(String msg);
    }

    /**
     * 用于存储活动列表到数据库的Runnable
     */
    public static class StoreActivitysRunnable implements  Runnable{

        private String jdata;

        public StoreActivitysRunnable(String jdata){
            this.jdata = jdata;
        }

        @Override
        public void run() {
            App.getInstance().getDBHelper().insertActivitiesJson(jdata);
        }
    }

    /**
     * 用于更新数据库中akey的Runnable
     */
    public class UpdateSigninInfoRunnable implements Runnable{

        public String tmpKey,aKey;

        public UpdateSigninInfoRunnable(String tmpKey,String aKey){
            this.aKey = aKey;
            this.tmpKey =tmpKey;
        }

        @Override
        public void run() {
            App.getInstance().getDBHelper().updateSigninInfo(tmpKey, aKey);
        }
    }
}
