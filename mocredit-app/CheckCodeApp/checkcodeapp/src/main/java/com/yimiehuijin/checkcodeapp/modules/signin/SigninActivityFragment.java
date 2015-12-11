package com.yimiehuijin.checkcodeapp.modules.signin;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yimiehuijin.checkcodeapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SigninActivityFragment extends Fragment {

    public SigninActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }
}
