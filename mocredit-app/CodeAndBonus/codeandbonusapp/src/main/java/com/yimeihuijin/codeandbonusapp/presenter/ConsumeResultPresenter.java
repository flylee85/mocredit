package com.yimeihuijin.codeandbonusapp.presenter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yimeihuijin.codeandbonusapp.R;
import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.utils.BusProvider;
import com.yimeihuijin.commonlibrary.Presenter.BasePresenter;
import com.yimeihuijin.commonlibrary.widgets.dialog.ProgressDialog;

import java.util.Objects;

/**
 * 消费结果页面的表现层
 * Created by Chanson on 2015/12/18.
 */
public class ConsumeResultPresenter extends BasePresenter{

    private IConsumeResultView view;

    private ConsumeResultObject data;

    public ConsumeResultPresenter(IConsumeResultView view){
        this.view = view;
    }

    public void onAction(int id){
        switch (id){
            case R.id.consume_result_back:
                view.finish();
                BusProvider.get().postSticky(new ResultBackAction());
                break;
            case R.id.consume_result_print:
                DeviceModel.getInstance().printNormal(data.printInfo);
                DeviceModel.getInstance().printQR(data.QR);
                break;
        }
    }


    public void onEvent(final ConsumeResultObject data){
        this.data = data;
        if(data.isSuccess){
            view.getImage().setImageResource(R.drawable.sign_check_icon);
            view.getPrintButton().setVisibility(View.VISIBLE);

            DeviceModel.getInstance().printNormal(data.printInfo);
            DeviceModel.getInstance().printNormal(DeviceModel.SIGN_INFO);
            DeviceModel.getInstance().printQR(data.QR);
            view.showPrintDialog("正在打印客户凭单，请打印完成后点击确定", new ProgressDialog.IDialogListener() {
                @Override
                public void onConfirm() {
                    DeviceModel.getInstance().printNormal(data.printInfo);
                    DeviceModel.getInstance().printQR(data.QR);
                }
            });
        }else{
            view.getImage().setImageResource(R.drawable.sign_error_icon);
            view.getPrintButton().setVisibility(View.GONE);
        }
        view.getMsgTextView().setText(data.msg);
    }

    @Override
    public void onCreate() {
        BusProvider.get().registerSticky(this);
//        ConsumeResultObject data = BusProvider.get().getStickyEvent(ConsumeResultObject.class);
//        if(data != null) {
//            onEvent(data);
//        }
    }

    @Override
    public void onDestroy() {
        BusProvider.get().unregister(this);
    }

    @Override
    public void onResume() {

    }

    public interface IConsumeResultView{
        public ImageView getImage();
        public TextView getMsgTextView();
        public Button getPrintButton();
        public void finish();
        public void showPrintDialog(String msg,ProgressDialog.IDialogListener listener);
    }

    public static class ConsumeResultObject{
        public String msg;
        public boolean isSuccess = false;
        public String printInfo;
        public String QR;
    }

    public class ResultBackAction{

    }
}
