package com.yimiehuijin.bonusconusmeapp.modules.consume;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yimiehuijin.bonusconusmeapp.R;
import com.yimiehuijin.bonusconusmeapp.base.IBaseActivity;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.constants.URLs;
import com.yimiehuijin.codeandbonuslibrary.data.ActivitiesResponse;
import com.yimiehuijin.codeandbonuslibrary.data.ActivityInfo;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumePostData;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumeResponse;
import com.yimiehuijin.codeandbonuslibrary.web.ConsumeUtil;
import com.yimiehuijin.codeandbonuslibrary.web.CorrectAction;
import com.yimiehuijin.codeandbonuslibrary.web.PosAction;

public class PayActivity extends IBaseActivity implements OnClickListener {

	private TextView name, id;

	private String bin, cardNo, date;

	private Button back;

	private ListView activities;

	private List<ActivityInfo> ailist;

	MyAdapter adapter;

	private String orderId;

	private ConsumeUtil consumeUtil;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_pay;
	}

	@Override
	public void initAactivity() {
		findView();
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		try {
			consumeUtil = new ConsumeUtil(this, URLs.URL_BONUS_CONSUME, URLs.URL_BONUS_CONSUME_CORRECT, new ConsumeUtil.ConsumeListener() {
                @Override
                public void success(BonusConsumeResponse data) {
                    checkCodeResult(data);
                }

                @Override
                public void failure(String msg) {
                    consumeFailed(msg);
                }

                @Override
                public void correctFailure() {
                    consumeFailed("交易失败");
                }

				@Override
				public void correctSuccess() {
					dismissProgressDialog();
				}
			},ConsumeUtil.FLAG_CONSUME);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String name = getIntent().getExtras().getString("name");
		cardNo = getIntent().getExtras().getString("id");
		bin = getIntent().getExtras().getString("bin");
		date = getIntent().getExtras().getString("date");
		this.name.setText(name);
		this.id.setText(cardNo);
		if (bin != null && !bin.isEmpty()) {
			ActivitiesResponse ar = App.getInstance().getDBHelper().getActivitiesJson(
					ActivitiesResponse.class);
			ailist = new ArrayList<ActivityInfo>();
			if(ar != null) {
				for (ActivityInfo ai : ar.data) {
					if(ai.cardBin == null){
						continue;
					}
					if ("".equals(ai.cardBin)) {
						ailist.add(ai);
					}else if(ai.cardBin.contains(bin)){
						ailist.add(ai);
					}
				}
			}
		}
		adapter = new MyAdapter();
		activities.setAdapter(adapter);
		activities.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				adapter.setSeletecedItem(position);
				adapter.notifyDataSetChanged();
			}
		});


	}

	private void consumeFailed(String msg) {
		dismissProgressDialog();
		BonusConsumeResponse bcr = new BonusConsumeResponse();
		bcr.success = false;
		bcr.errorMsg = msg;
		bcr.data = "";
		checkCodeResult(bcr);
	}

	private void checkCodeResult(BonusConsumeResponse response) {
		dismissProgressDialog();
		Intent i = new Intent(this, BonusConsumeResultActivity.class);
		if(response.success) {
			response.errorMsg = adapter.getSelectedItem().posSuccessMsg;
		}
		i.putExtra("result", response.success);
		i.putExtra("data", response);
		i.putExtra("from",BonusConsumeResultActivity.FROM_CONSUME);
		startActivity(i);
		finish();
	}

	protected void findView() {
		// TODO Auto-generated method stub
		name = (TextView) f(R.id.pay_bankname);
		id = (TextView) f(R.id.pay_cardid);
		back = (Button) f(R.id.pay_back);
		activities = (ListView) f(R.id.pay_activities);
		back.setOnClickListener(this);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(adapter.getSelectedItem() == null){
					Toast.makeText(PayActivity.this,"暂无任何可用活动",Toast.LENGTH_LONG).show();
					return;
				}

				showProgressDialog("处理中...");
				new Thread(){
					@Override
					public void run() {
						super.run();
						new CorrectAction(PayActivity.this, new PosAction.PosActionListener() {
							@Override
							public void succees() {
//								showProgressDialog("正在提交...");
								BonusConsumePostData bonus = new BonusConsumePostData();
								orderId = bonus.orderId;
								bonus.activitId = adapter.getSelectedItem().activityId;
								bonus.amt = adapter.getSelectedItem().amt;
								bonus.cardNo = cardNo;
								bonus.exp_date = date;
								consumeUtil.consumePost(bonus);
							}

							@Override
							public void fail() {

							}
						}).correct();
					}
				}.start();


			}
		});

		left.setImageResource(android.R.drawable.ic_menu_revert);
		menu.setVisibility(View.INVISIBLE);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

	private class MyAdapter extends BaseAdapter {

		private int selected = 0;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ailist.size();
		}

		@Override
		public ActivityInfo getItem(int position) {
			// TODO Auto-generated method stub
			return ailist.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void setSeletecedItem(int position) {
			selected = position;
		}

		public ActivityInfo getSelectedItem() {
			if(ailist.size() < 1){
				return null;
			}
			return ailist.get(selected);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh;
			if (convertView == null) {
				convertView = LayoutInflater.from(PayActivity.this).inflate(
						R.layout.item_acitivity, null);
			}
			vh = (ViewHolder) convertView.getTag();
			if (vh == null) {
				vh = new ViewHolder();
				vh.date = (TextView) convertView
						.findViewById(R.id.item_activity_date);
				vh.title = (TextView) convertView
						.findViewById(R.id.item_activity_title);
				vh.desc = (TextView) convertView
						.findViewById(R.id.item_activity_desc);
				vh.price = (TextView) convertView
						.findViewById(R.id.item_activity_price);
				convertView.setTag(vh);
			}
			vh.setHolder(getItem(position));
			if (selected == position) {
				convertView
						.setBackgroundResource(R.drawable.bg_item_activity_selected);
			} else {
				convertView.setBackgroundResource(R.drawable.bg_item_activity);
			}
			return convertView;
		}

		private class ViewHolder {
			public TextView title, price, date, desc;

			public void setHolder(ActivityInfo ai) {
				title.setText(ai.activityName);
				price.setText(ai.amt == null ? "0" : ai.amt + "积分");
				StringBuffer datesb = new StringBuffer().append(ai.sTime)
						.append("到").append(ai.eTime);
				date.setText(datesb.toString());
				desc.setText(ai.remark);
			}
		}

	}

	@Override
	protected void onActionBarClick(int flag) {
		// TODO Auto-generated method stub
		switch (flag) {
		case LEFT_CLICK:
			finish();
			break;
		}
	}

}
