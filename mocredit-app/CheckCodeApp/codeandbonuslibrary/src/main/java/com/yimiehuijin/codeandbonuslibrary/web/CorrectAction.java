package com.yimiehuijin.codeandbonuslibrary.web;

import android.content.Context;

import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.constants.URLs;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumePostData;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumeResponse;

import java.util.List;

/**
 * Created by Chanson on 2015/12/9.
 */
public class CorrectAction extends PosAction {

    private Context context;

    public CorrectAction(Context context,PosActionListener listener) {
        super(listener);
        this.context = context;
    }

    @Override
    public void start() {

    }

    public void correct(){
        List<BonusConsumePostData> list = App.getInstance().getDBHelper().getOrders(ConsumeUtil.FLAG_CONSUME);
        for(BonusConsumePostData data:list){
            ConsumeManager.getInstance().in(data);
        }
        if(ConsumeManager.getInstance().size() > 0) {
            consumeCorrect(ConsumeManager.getInstance().out());
        }else{
            if(listener != null){
                listener.succees();
            }
        }
    }

    private void consumeCorrect(BonusConsumePostData data){
        if(ConsumeManager.getInstance().size() < 1){
            correct();
            return;
        }
        try {
            new ConsumeUtil(context, URLs.URL_BONUS_CONSUME, URLs.URL_BONUS_CONSUME_CORRECT, new ConsumeUtil.ConsumeListener() {
                @Override
                public void success(BonusConsumeResponse data) {
                    consumeCorrect(ConsumeManager.getInstance().out());
                }

                @Override
                public void failure(String msg) {
                    consumeCorrect(ConsumeManager.getInstance().out());
                }

                @Override
                public void correctFailure() {
                    consumeCorrect(ConsumeManager.getInstance().out());
                }

                @Override
                public void correctSuccess() {
                    consumeCorrect(ConsumeManager.getInstance().out());
                }
            }, ConsumeUtil.FLAG_CONSUME, false).correctPost(data,ConsumeUtil.TAG_CORRECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
