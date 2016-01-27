package com.yimeihuijin.codeandbonusapp.modules.consumeview;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.presenter.ConsumePresenter;
import com.yimeihuijin.codeandbonusapp.presenter.SigninPresenter;
import com.yimeihuijin.commonlibrary.base.BaseFragment;
import com.yimeihuijin.commonlibrary.widgets.CodeScreen;
import com.yimeihuijin.commonlibrary.widgets.FlashButton;
import com.yimeihuijin.commonlibrary.widgets.FlashLayout;

/**
 * 主界面
 * Created by Chanson on 2015/12/16.
 */
public class ConsumeFragment extends BaseFragment implements View.OnClickListener,ConsumePresenter.IConsumeView{

    private FlashButton button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,buttonCancel;
    private ImageButton buttonDel,buttonConfirm,keyboard;
    private CodeScreen[] screens;
    private FlashLayout parent;
    private ViewPager pager;
    private TextView[] indicators = new TextView[2];

    public static final int POSITION_CONSUME = 1;
    public static final int POSITION_CODE = 0;

    public static final String[] TAB_TITLE = new String[]{"码券兑换","积分消费"};
    public static final String[] TAB_HINT = new String[]{"请输入码券号\n撤销请输入验码订单号","请刷卡后点击消费按钮\n撤销请输入消费订单号"};
    private CodeScreen[] codeScreens;

    private SigninPresenter.ISigninView view;
    private ConsumePresenter presenter;

    public ConsumeFragment(SigninPresenter.ISigninView view){
        this.view = view;
        presenter = new ConsumePresenter(this);
    }

    @Override
    public void initialize() {
        presenter.onCreate();
    }

    @Override
    public void findViews(View v){
        (button0 = (FlashButton) v.findViewById(R.id.code_scan_num0)).setOnClickListener(this);
        (button1 = (FlashButton) v.findViewById(R.id.code_scan_num1)).setOnClickListener(this);
        (button2 = (FlashButton) v.findViewById(R.id.code_scan_num2)).setOnClickListener(this);
        (button3 = (FlashButton) v.findViewById(R.id.code_scan_num3)).setOnClickListener(this);
        (button4 = (FlashButton) v.findViewById(R.id.code_scan_num4)).setOnClickListener(this);
        (button5 = (FlashButton) v.findViewById(R.id.code_scan_num5)).setOnClickListener(this);
        (button6 = (FlashButton) v.findViewById(R.id.code_scan_num6)).setOnClickListener(this);
        (button7 = (FlashButton) v.findViewById(R.id.code_scan_num7)).setOnClickListener(this);
        (button8 = (FlashButton) v.findViewById(R.id.code_scan_num8)).setOnClickListener(this);
        (button9 = (FlashButton) v.findViewById(R.id.code_scan_num9)).setOnClickListener(this);
        (buttonCancel = (FlashButton) v.findViewById(R.id.code_scan_cancel)).setOnClickListener(this);
        parent = (FlashLayout) v.findViewById(R.id.code_scan_parent);
        pager = (ViewPager) v.findViewById(R.id.code_scan_pager);
        (indicators[POSITION_CODE] = (TextView) v.findViewById(R.id.code_scan_indicator1)).setText(TAB_TITLE[POSITION_CODE]);
        (indicators[POSITION_CONSUME] = (TextView) v.findViewById(R.id.code_scan_indicator2)).setText(TAB_TITLE[POSITION_CONSUME]);

        button0.setParentView(parent);
        button1.setParentView(parent);
        button2.setParentView(parent);
        button3.setParentView(parent);
        button4.setParentView(parent);
        button5.setParentView(parent);
        button6.setParentView(parent);
        button7.setParentView(parent);
        button8.setParentView(parent);
        button9.setParentView(parent);
        buttonCancel.setParentView(parent);

        (buttonConfirm = (ImageButton) v.findViewById(R.id.code_scan_consume)).setOnClickListener(this);
        (buttonDel = (ImageButton) v.findViewById(R.id.code_scan_delete)).setOnClickListener(this);


        (keyboard = (ImageButton) v.findViewById(R.id.code_scan_keyboard)).setOnClickListener(this);


        buttonDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getScreen().clearScreen();
                return true;
            }
        });
        initViewPager();
    }

    private void initViewPager(){

        CodeScreen screen1 = new CodeScreen(getAvtivity());
        CodeScreen screen2 = new CodeScreen(getAvtivity());
        screen1.setCodeSize(getResources().getDimensionPixelSize(R.dimen.p40));
        screen2.setCodeSize(getResources().getDimensionPixelSize(R.dimen.p40));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        screen1.setLayoutParams(params);
        screen1.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        screen1.setTextColor(Color.WHITE);
        screen2.setLayoutParams(params);
        screen2.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        screen2.setTextColor(Color.WHITE);
        screens = new CodeScreen[]{screen1,screen2};
        pager.setAdapter(new MyPagerAdapter());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_consume;
    }

    @Override
    public CodeScreen getScreen() {
        return screens[getPosition()];
    }

    @Override
    public ImageButton getKeyBord() {
        return keyboard;
    }

    @Override
    public void showKeyBoard() {
        view.showKeyBoard();
    }

    @Override
    public void gotoPay() {
        view.gotoFragment(new PayFragment(view));
    }

    @Override
    public void gotoCancel() {
        view.gotoFragment(new ConsumeRevokeFragment(view));
    }

    @Override
    public int getPosition() {
        return pager.getCurrentItem();
    }

    @Override
    public ViewPager getPager() {
        return pager;
    }

    @Override
    public TextView[] getIndicators() {
        return indicators;
    }

    @Override
    public Activity getAvtivity() {
        return getActivity();
    }

    @Override
    public void showDialog(String msg) {
        view.showDialog(msg);
    }

    @Override
    public void dismisDialog() {
        view.dismisDialog();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onClick(View view) {
        presenter.onAction(view.getId());
    }

    private class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return TAB_TITLE.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            CodeScreen s = screens[position];
            s.setHints(TAB_HINT[position]);
            container.addView(s);
            return screens[position];
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLE[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
