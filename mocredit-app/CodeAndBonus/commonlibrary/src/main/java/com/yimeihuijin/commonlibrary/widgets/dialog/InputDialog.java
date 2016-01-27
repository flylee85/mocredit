package com.yimeihuijin.commonlibrary.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yimeihuijin.commonlibrary.R;

/**
 * Created by Chanson on 2016/1/27.
 */
public class InputDialog extends Dialog implements View.OnClickListener{

    private IDialogInputListener listener;
    private TextView title;
    private EditText content;
    private Button confirm,cancel;

    public InputDialog(Context context,IDialogInputListener listener) {
        super(context, R.style.CustomProgressDialog);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_input,
                null);
        this.listener = listener;
        setContentView(v);
        setCanceledOnTouchOutside(false);
        title = (TextView) v.findViewById(R.id.alert_title);
        title.setCompoundDrawablePadding(4);
        content = (EditText) v.findViewById(R.id.alert_alert);
        (confirm = (Button) v.findViewById(R.id.alert_confirm)).setOnClickListener(this);
        (cancel = (Button) v.findViewById(R.id.alert_cancel)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.alert_confirm){
            String msg = listener.onInputConfirm(content.getText().toString());
            if(msg == null){
                dismiss();
            }else{
                setError(msg);
            };
        }else{
            dismiss();
        }
    }

    public void setError(String text){
        content.setError(text);
    }

    public void setTitle(String text){
        title.setText(text);
    }

    public void setHint(String text){
        content.setHint(text);
    }

    public void setContent(String text){
        content.setText(text);
    }

    public interface  IDialogInputListener{
        public String onInputConfirm(String text);
    }
}
