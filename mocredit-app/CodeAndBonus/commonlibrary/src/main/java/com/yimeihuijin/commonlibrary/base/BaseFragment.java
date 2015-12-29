package com.yimeihuijin.commonlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chanson on 2015/12/15.
 */
public abstract class BaseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(),null);
        findViews(v);
        initialize();
        return v;
    }
    public abstract void initialize();
    public abstract void findViews(View v);
    public abstract int getLayoutId();
}
