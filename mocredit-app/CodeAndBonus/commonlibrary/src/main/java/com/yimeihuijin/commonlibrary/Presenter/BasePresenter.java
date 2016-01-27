package com.yimeihuijin.commonlibrary.Presenter;

import com.yimeihuijin.commonlibrary.utils.StateLock;

/**
 * Created by Chanson on 2015/12/15.
 */
public abstract class BasePresenter {

    public void onCreate(){
        StateLock.unlock();
    }

    public void onDestroy(){
        StateLock.unlock();
    };

    public abstract void onResume();

    public interface IBaseView{
        public int getLayoutId();
    }
}
