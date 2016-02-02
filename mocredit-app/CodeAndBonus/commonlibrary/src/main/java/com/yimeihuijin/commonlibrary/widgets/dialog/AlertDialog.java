package com.yimeihuijin.commonlibrary.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yimeihuijin.commonlibrary.R;

/**
 * Created by Chanson on 2016/1/22.
 */
public class AlertDialog extends Dialog implements View.OnClickListener {

    private  ProgressDialog.IDialogListener listener;
    private TextView title,content;
    private Button confirm,cancel;

    public AlertDialog(Context context, final ProgressDialog.IDialogListener listener) {
        super(context, R.style.CustomProgressDialog);
        // TODO Auto-generated constructor stub
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_alert,
                null);
        this.listener = listener;
        setContentView(v);
        setCanceledOnTouchOutside(false);
        title = (TextView) v.findViewById(R.id.alert_title);
        title.setCompoundDrawablePadding(4);
        content = (TextView) v.findViewById(R.id.alert_alert);
        (confirm = (Button) v.findViewById(R.id.alert_confirm)).setOnClickListener(this);
        (cancel = (Button) v.findViewById(R.id.alert_cancel)).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.alert_confirm) {
            listener.onConfirm();
            dismiss();
        }else if(i == R.id.alert_cancel){
            dismiss();
            listener.onCancel();
        }
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setContent(String content){
        this.content.setText(content);
    }
}
