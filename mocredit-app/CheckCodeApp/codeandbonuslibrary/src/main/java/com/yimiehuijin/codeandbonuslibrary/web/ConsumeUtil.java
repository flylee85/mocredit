package com.yimiehuijin.codeandbonuslibrary.web;

import android.content.Context;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumePostData;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumeResponse;
import com.yimiehuijin.codeandbonuslibrary.data.PostData;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;

import java.sql.Time;

/**
 * Created by Chanson on 2015/11/24.
 */
public class ConsumeUtil {

    public static final String[] FLAGS = new String[]{"0","1","2"};

    public static final String FLAG_CONSUME = FLAGS[1];
    public static final String FLAG_REVOKE = FLAGS[2];

    private ConsumeListener listener;
    /**
     * 如果该标志为false,表示该实例用于冲正之前冲正失败的交易，将不会保存记录到数据库，冲正成功后也不会再次发起交易
     */
    private boolean isStore = true;

    private String flag;

    private PostUtil postUtil;

    private BonusConsumePostData tmpData;

    private boolean postOnce = false;

    private String url,correctUrl;

    private int retryNum = 0;

    private int consumeRetryNum = 0;

    public static  final String TAG_NORMAL = "consume_normal";
    public static  final String TAG_CORRECT = "consume_correct";
    public static  final String TAG_CORRECT_TRIBLE = "consume_correct_trible";

    public ConsumeUtil(Context context, String url, String correctUrl, ConsumeListener ilistener, String flag) throws Exception {
        this(context,url,correctUrl,ilistener,flag,true);
    }

    private boolean checkFlag(String flag){
        for(String f:FLAGS){
            if(f.equals(flag)){
                return true;
            }
        }
        return false;
    }

    public ConsumeUtil(Context context, String url, String correctUrl, ConsumeListener ilistener, final String flag, boolean isStore) throws Exception {
        if(!checkFlag(flag)){
            throw new Exception();
        }
        this.flag = flag;
        this.isStore = isStore;
        this.url = url;
        this.correctUrl = correctUrl;
        listener = ilistener;
        postUtil = new PostUtil( new PostUtil.PostResponseListener() {
            @Override
            public void onResponse(String response, String tag) {
                PostData ret = new Gson().fromJson(response, PostData.class);
                BonusConsumeResponse bcp = null;
                if (ret != null) {
                    try {
                        bcp = new Gson().fromJson(ret.jData,
                                BonusConsumeResponse.class);
                    }catch (Exception e){
                        bcp = null;
                    }
                    if(bcp == null){
                        listener.failure("交易失败");
                        complete(tmpData.orderId);
                        return;
                    }
                    if(bcp.state == null) {
                        if (!bcp.success) {
                            if (TAG_NORMAL.equals(tag)) {
                                listener.failure(bcp.errorMsg);
                                complete(tmpData.orderId);
                            } else {
                                onErroResponse(new VolleyError(bcp.errorMsg), tag);
                            }
                            return;
                        }
                    }else{
                        if (!"ok".equals(bcp.state)) {
                            if (TAG_NORMAL.equals(tag)) {
                                listener.failure(bcp.errorMes);
                                complete(tmpData.orderId);
                            } else {
                                onErroResponse(new VolleyError(bcp.errorMes), tag);
                            }
                            return;
                        }
                    }
                }
                if(TAG_NORMAL.equals(tag)){
                    listener.success(bcp);
                    App.getInstance().getDBHelper().insertTradeRecord(tmpData.amt,flag);
                    complete(tmpData.orderId);
                }else if(TAG_CORRECT.equals(tag)){
                    listener.correctSuccess();
                    complete(tmpData.orderId);
                }else if(TAG_CORRECT_TRIBLE.equals(tag)){
                    listener.correctSuccess();
                    complete(tmpData.orderId);
                }else{
                    listener.failure(bcp.errorMsg);
                    complete(tmpData.orderId);
                }
            }

            @Override
            public void onErroResponse(VolleyError error, String tag){
                if(error instanceof TimeoutError || error.getCause() instanceof  TimeoutError){
                    if(TAG_NORMAL.equals(tag)){
                        BonusConsumePostData data = new BonusConsumePostData();
                        data.orgOrderId = tmpData.orderId;
                        data.amt = tmpData.amt;
                        data.cardNo = tmpData.cardNo;
                        data.exp_date = tmpData.exp_date;
                        correctPost(data,TAG_CORRECT);
                    }
                }else{
                    if(TAG_NORMAL.equals(tag)){
                        listener.failure("交易失败");
                        complete(tmpData.orderId);
                    }
                }
                consumeError(tag);
            }
        },context);
    }

    private void consumeError(String tag){

       if(TAG_CORRECT.equals(tag)){
            BonusConsumePostData data = new BonusConsumePostData();
            data.orgOrderId = tmpData.orderId;
            data.amt = tmpData.amt;
            data.cardNo = tmpData.cardNo;
            data.exp_date = tmpData.exp_date;
           triblePost(data,TAG_CORRECT_TRIBLE);
        }else if(TAG_CORRECT_TRIBLE.equals(tag)){
            correctRetry();
            if(retryNum > 2){
                listener.correctFailure();
                retryNum = 0;
            }
        }else if(TAG_NORMAL.equals(tag)){

       }else{
            listener.failure("交易失败");
            complete(tmpData.orderId);
        }
    }


    private synchronized void correctRetry(){
        retryNum ++;
    }

    private void complete(String orderid){
        App.getInstance().getDBHelper().deleteOrder(orderid);
        postOnce = false;
    }

    public void consumePost(BonusConsumePostData data){
        if(postOnce){
            return;
        }
        tmpData = data;
        postOnce = true;
        if(isStore) {
            App.getInstance().getDBHelper().insertOrder(data, flag);
        }
        PostData postData = new PostData(data);
        postUtil.post(url,postData,TAG_NORMAL);
    }

    public void correctPost(BonusConsumePostData data,String tag){
        if(tmpData == null){
            tmpData = new BonusConsumePostData();
            tmpData.orderId = data.orgOrderId;
            tmpData.amt = data.amt;
            tmpData.exp_date = data.exp_date;
            tmpData.cardNo = data.cardNo;
            tmpData.activitId = data.activitId;
        }
        PostData postData = new PostData(data);
        postUtil.post(correctUrl,postData,tag);
    }

    public void triblePost(BonusConsumePostData data,String tag){
        if(tmpData == null){
            tmpData = new BonusConsumePostData();
            tmpData.orderId = data.orgOrderId;
            tmpData.amt = data.amt;
            tmpData.exp_date = data.exp_date;
            tmpData.cardNo = data.cardNo;
            tmpData.activitId = data.activitId;
        }
        PostData postData = new PostData(data);
        postUtil.triblePost(correctUrl, postData, tag);
    }

    public static interface ConsumeListener{
        public void success(BonusConsumeResponse data);
        public void failure(String msg);
        public void correctFailure();
        public void correctSuccess();
    }
}
