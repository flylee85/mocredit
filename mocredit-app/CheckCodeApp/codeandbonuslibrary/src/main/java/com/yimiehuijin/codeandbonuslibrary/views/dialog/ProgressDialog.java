package com.yimiehuijin.codeandbonuslibrary.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yimiehuijin.codeandbonuslibrary.R;

public class ProgressDialog extends Dialog {

	private TextView msg;

	private Button confirm;

	private IDialogListener listener;

	public ProgressDialog(Context context) {
		this(context, null);
	}

	public ProgressDialog(Context context, final IDialogListener listener){
		super(context, R.style.CustomProgressDialog);
		// TODO Auto-generated constructor stub
		View v = LayoutInflater.from(context).inflate(R.layout.dialog_progress,
				null);
		this.listener = listener;
		setContentView(v);
		setCanceledOnTouchOutside(false);
		msg = (TextView) v.findViewById(R.id.dialog_msg);
		confirm = (Button) v. findViewById(R.id.dialog_confirm);
		if(listener == null){
			confirm.setVisibility(View.GONE);
		}
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener !=null) {
					listener.onConfirm();
				}
			}
		});
	}

	public void setMsg(String msg) {
		this.msg.setText(msg);
	}

	public static interface IDialogListener{
		public void onConfirm();
	}

}
