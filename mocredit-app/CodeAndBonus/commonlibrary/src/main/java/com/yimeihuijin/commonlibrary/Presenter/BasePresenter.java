package com.yimeihuijin.commonlibrary.Presenter;

/**
 * Created by Chanson on 2015/12/15.
 */
public abstract class BasePresenter {

    public abstract void onCreate();

    public abstract void onDestroy();

    public abstract void onResume();

    public interface IBaseView{
        public int getLayoutId();
    }
}
