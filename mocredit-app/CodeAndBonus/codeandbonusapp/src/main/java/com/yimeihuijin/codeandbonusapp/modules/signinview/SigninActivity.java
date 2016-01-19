package com.yimeihuijin.codeandbonusapp.modules.signinview;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.modules.consumeview.ConsumeFragment;
import com.yimeihuijin.codeandbonusapp.presenter.SigninPresenter;
import com.yimeihuijin.codeandbonusapp.service.WangposService;
import com.yimeihuijin.commonlibrary.base.BaseActivity;
import com.yimeihuijin.commonlibrary.base.BaseFragment;

/**
 * 签到页面和其他页面的容器，包括了标题栏
 * Created by Chanson on 2015/12/15.
 */
public class SigninActivity extends BaseActivity implements SigninPresenter.ISigninView{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView leftMenu;
    private TextView runningMsg;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView version;

    private SigninPresenter presenter;

    private ConsumeFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contain_fragment_with_toolbar;
    }

    protected void initialize() {
        initToolbar();
        fragment = new ConsumeFragment(this);
        runningMsg = (TextView) f(R.id.signin_running_msg);
        presenter = new SigninPresenter(this);
        presenter.onCreate();
    }

    private void initToolbar(){
        toolbar = (Toolbar) f(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                presenter.onAction(item.getItemId());
                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) f(R.id.drawer);
        leftMenu = (ListView) f(R.id.left_menu_list);
        version = (TextView) f(R.id.version);
        leftMenu.setAdapter(new ArrayAdapter<String>(this, R.layout.layout_simpleitem, new String[]{ "打印清单","重置密钥"}));
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setRunningMsg(String msg) {
        runningMsg.setText(msg);
    }

    @Override
    public void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void gotoFragment(BaseFragment fragment) {
        replace(fragment, R.id.container_main_fragment);
    }

    @Override
    public void goBackToFragment(BaseFragment fragment) {
        replace(fragment, R.id.container_main_fragment, false);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }


    @Override
    public void gotoService() {
        Intent i = new Intent(this, WangposService.class);
        startService(i);
    }

    @Override
    public ListView getLeftMenu() {
        return leftMenu;
    }

    @Override
    public void clearFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public Looper getLooper(){
        return getMainLooper();
    }

    @Override
    public TextView getVersionTextView() {
        return version;
    }


    @Override
    public void showDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void dismisDialog() {
        dismissProgressDialog();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            return super.dispatchKeyEvent(event);
        }
        presenter.onKeyEvent(event);
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
