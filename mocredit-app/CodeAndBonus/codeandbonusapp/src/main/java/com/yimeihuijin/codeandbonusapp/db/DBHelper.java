package com.yimeihuijin.codeandbonusapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.google.gson.Gson;
import com.yimeihuijin.codeandbonusapp.model.ConsumeModel;
import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.model.vo.VO;
import com.yimeihuijin.codeandbonusapp.presenter.SigninPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

	/**
	 * 1-->2 add header table 2-->3 update info 3--> update info haha
	 * 
	 */
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "wang_pos";

	public static final String TABLE_ACTIVITIES = "activities";
	public static final String NAME_ACTIVITYID = "activityid";
	public static final String NAME_ACTIVITY = "activity";
	public static final String NAME_ACTIVITYNAME = "activityname";
	public static final String NAME_AMT = "activityamt";
	public static final String NAME_ENTERISENAME = "enterprisename";
	public static final String NAME_ACTIVITYRULE = "activityrule";
	public static final String NAME_STIME = "stime";
	public static final String NAME_ETIME = "etime";
	public static final String NAME_CARDBIN = "cardbin";
	
	public static final String TABLE_ACTIVITIES_JSON = "activities_json";
	public static final String NAME_ACTIVITIES_JSON = "json";

	public static final String TABLE_SIGIN_INFO = "signin_info";
	public static final String NAME_AKEY = "akey";
	public static final String NAME_LAST_SIGNIN_TIME = "last_time";
	public static final String NAME_SERIES_NUM = "series_num";

	public static final String TABLE_ORDER_INFO = "order_info";
	public static final String NAME_ORDER_ID = "orderid";
	public static final String NAME_CARD_NO = "cardNo";
	public static final String NAME_EXPDATE = "exp_date";
	public static final String NAME_ORDER_AMT = "amt";
	public static final String NAME_ORDER_FLAG = "flag";

	public static final String TABLE_TRADE_LIST = "trade_list";
	public static final String NAME_TRADE_MODE = "mode";
	public static final String NAME_TRADE_STATE = "state";

	public static final String TABLE_CODE_INFO = "code_info";
	public static final String NAME_ID = "order_id";
	public static final String NAME_POSNO = "posno";
	public static final String NAME_MMSID = "mmsid";
	public static final String NAME_BATCHNO ="batch_no";
	public static final String NAME_SERACHNO = "serach_no";

	public DBHelper(final Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * Creates database the first time we try to open it.
	 */
	@Override
	public void onCreate(final SQLiteDatabase db) {
		StringBuffer sql = new StringBuffer();
		sql.append("create table ").append(TABLE_ACTIVITIES);
		sql.append("(");
		sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(NAME_ACTIVITYID).append(" varchar,");
		sql.append(NAME_ACTIVITYNAME).append(" varchar,");
		sql.append(NAME_ACTIVITY).append(" varchar,");
		sql.append(NAME_ENTERISENAME).append(" varchar,");
		sql.append(NAME_ACTIVITYRULE).append(" varchar,");
		sql.append(NAME_AMT).append(" varchar,");
		sql.append(NAME_STIME).append(" varchar,");
		sql.append(NAME_ETIME).append(" varchar");
		sql.append(NAME_CARDBIN).append(" varchar");
		sql.append(")");

		StringBuffer sigin_sql = new StringBuffer();
		sigin_sql.append("create table ").append(TABLE_SIGIN_INFO);
		sigin_sql.append("(");

		sigin_sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
		sigin_sql.append(NAME_AKEY).append(" varchar,");
		sigin_sql.append(NAME_SERIES_NUM).append(" varchar,");
		sigin_sql.append(NAME_LAST_SIGNIN_TIME).append(" long)");
		
		StringBuffer activity_json_sql = new StringBuffer();
		activity_json_sql.append("create table ").append(TABLE_ACTIVITIES_JSON).append("(");
		activity_json_sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
		activity_json_sql.append(NAME_ACTIVITIES_JSON).append(" varchar)");

		StringBuffer order_sql = new StringBuffer();
		order_sql.append("create table ").append(TABLE_ORDER_INFO).append("(");
		order_sql.append(NAME_ORDER_ID).append(" varchar PRIMARY KEY,");
		order_sql.append(NAME_CARD_NO).append(" varchar,");
		order_sql.append(NAME_ORDER_AMT).append(" varchar,");
		order_sql.append(NAME_ACTIVITYID).append(" varchar,");
		order_sql.append(NAME_ORDER_FLAG).append(" varchar,");
		order_sql.append(NAME_EXPDATE).append(" varchar)");

		StringBuffer trade_sql = new StringBuffer();
		trade_sql.append("create table ").append(TABLE_TRADE_LIST).append("(");
		trade_sql.append(NAME_ORDER_AMT).append(" integer,");
		trade_sql.append(NAME_TRADE_STATE).append(" varchar,");
		trade_sql.append(NAME_TRADE_MODE).append(" varchar)");


		StringBuffer code_sql = new StringBuffer();
		code_sql.append("create table ").append(TABLE_CODE_INFO);
		code_sql.append("(");

		code_sql.append(NAME_ID).append(" varchar PRIMARY KEY,");
		code_sql.append(NAME_POSNO).append(" varchar,");
		code_sql.append(NAME_BATCHNO).append(" varchar,");
		code_sql.append(NAME_SERACHNO).append(" varchar,");
		code_sql.append(NAME_MMSID).append(" varchar)");

		db.execSQL(sql.toString());
		db.execSQL(sigin_sql.toString());
		db.execSQL(activity_json_sql.toString());
		db.execSQL(order_sql.toString());
		db.execSQL(trade_sql.toString());
		db.execSQL(code_sql.toString());
	}
	
	public void insertActivitiesJson(String json){
		getWritableDatabase().execSQL("delete from " + TABLE_ACTIVITIES_JSON);
		StringBuffer insert_sql = new StringBuffer();
		insert_sql.append("insert into ").append(TABLE_ACTIVITIES_JSON).append("(");
		insert_sql.append(NAME_ACTIVITIES_JSON).append(") values(?)");
		while(json.length() > 4000){
			getWritableDatabase().execSQL(insert_sql.toString(), new String[]{json.substring(0, 4000)});
			json = json.substring(4000);
		}
		getWritableDatabase().execSQL(insert_sql.toString(), new String[]{json});
	}
	
	public <T>T getActivitiesJson(Class<T> cls){
		Cursor cursor = getReadableDatabase().rawQuery("select * from "+TABLE_ACTIVITIES_JSON, new String[]{});
		StringBuffer s = new StringBuffer();
		while(cursor.moveToNext()){
			s.append(cursor.getString(cursor.getColumnIndex(NAME_ACTIVITIES_JSON)));
		}
		return new Gson().fromJson(s.toString(), cls);
	}

	public boolean insertOrder(VO.BonusConsumeObject data,String flag){
		Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_ORDER_INFO + " where " + NAME_ORDER_ID + "=?", new String[]{data.orderId==null?"":data.orderId});
		if(data == null || cursor.moveToNext()){
			return false;
		}
		StringBuffer insert_sql = new StringBuffer();
		insert_sql.append("insert into ").append(TABLE_ORDER_INFO);
		insert_sql.append("(");
		insert_sql.append(NAME_EXPDATE).append(",");
		insert_sql.append(NAME_CARD_NO).append(",");
		insert_sql.append(NAME_ORDER_ID).append(",");
		insert_sql.append(NAME_ORDER_AMT).append(",");
		insert_sql.append(NAME_ORDER_FLAG).append(")");
		insert_sql.append(" values(?,?,?,?,?)");
		getWritableDatabase().execSQL(insert_sql.toString(),new String[]{data.exp_date,data.cardNo,data.orderId,data.amt,flag});
		return true;
	}

	public void deleteOrder(String orderId){
		getWritableDatabase().delete(TABLE_ORDER_INFO,NAME_ORDER_ID+"=?",new String[]{orderId});
	}

	public LinkedList<VO.BonusConsumeObject> getOrders(){
		LinkedList<VO.BonusConsumeObject> list = new LinkedList<VO.BonusConsumeObject>();
		Cursor cursor = getReadableDatabase().rawQuery("select * from "+TABLE_ORDER_INFO,new String[]{});
		while(cursor.moveToNext()){
			VO.BonusConsumeObject data = new VO.BonusConsumeObject();
			data.amt = cursor.getString(cursor.getColumnIndex(NAME_ORDER_AMT));
			data.orgOrderId = cursor.getString(cursor.getColumnIndex(NAME_ORDER_ID));
			data.exp_date = cursor.getString(cursor.getColumnIndex(NAME_EXPDATE));
			data.cardNo = cursor.getString(cursor.getColumnIndex(NAME_CARD_NO));
		}
		return list;
	}

	public void insertCode(VO.CodeConsumeResponseObject ccro){
		ContentValues cv = new ContentValues();
		cv.put(NAME_ID, ccro.orderId);
		cv.put(NAME_POSNO, ccro.posno);
		cv.put(NAME_MMSID,ccro.mmsid);
		cv.put(NAME_BATCHNO,ccro.batchno);
		cv.put(NAME_SERACHNO, ccro.ymOrderId);
		long l = getWritableDatabase().insert(TABLE_CODE_INFO, null,cv );
	}

	public VO.CodeRevoke getCodeRevoke(String orderid){
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_CODE_INFO+" where "+NAME_ID+"=?", new String[]{orderid});
		VO.CodeRevoke ret = new VO.CodeRevoke(orderid);
		ret.requestSerialNumber = orderid;
		ret.device = DeviceModel.getInstance().getDevice().en;
		if (cursor.moveToFirst()) {
			ret.posno = cursor.getString(cursor.getColumnIndex(NAME_POSNO));
		}
		cursor.close();
		return ret;
	}

	public boolean insertTradeRecord(int amt,String mode,String state){
		StringBuffer insert_sql = new StringBuffer();
		insert_sql.append("insert into ").append(TABLE_TRADE_LIST);
		insert_sql.append("(");
		insert_sql.append(NAME_ORDER_AMT).append(",");
		insert_sql.append(NAME_TRADE_STATE).append(",");
		insert_sql.append(NAME_TRADE_MODE).append(")");
		insert_sql.append(" values(?,?,?)");
		getWritableDatabase().execSQL(insert_sql.toString(),new Object[]{amt,state,mode});
		return true;
	}

	public HashMap<String,Integer> getTradelist(){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		Cursor cursor = getReadableDatabase().rawQuery("select * from "+TABLE_TRADE_LIST,new String[]{});
		int totalCode = 0;
		int totalCodeAmt = 0;
		int totalBonus = 0;
		int totalBonusAmt = 0;
		int totalCodeRevoke = 0;
		int totalCodeRevokeAmt = 0;
		int totalBonusRevoke = 0;
		int totalBonusRevokeAmt = 0;
		while(cursor.moveToNext()){
			if(String.valueOf(ConsumeModel.MODE_BONUS).equals(cursor.getString(cursor.getColumnIndex(NAME_TRADE_MODE)))){
				if(String.valueOf(ConsumeModel.STATE_CONSUME).equals(cursor.getString(cursor.getColumnIndex(NAME_TRADE_STATE)))){
					totalBonusAmt += cursor.getInt(cursor.getColumnIndex(NAME_ORDER_AMT));
					totalBonus ++;
				}else{
					totalBonusRevokeAmt += cursor.getInt(cursor.getColumnIndex(NAME_ORDER_AMT));
					totalBonusRevoke ++;
				}
			}else{
				if(String.valueOf(ConsumeModel.STATE_CONSUME).equals(cursor.getString(cursor.getColumnIndex(NAME_TRADE_STATE)))){
					totalCodeAmt += cursor.getInt(cursor.getColumnIndex(NAME_ORDER_AMT));
					totalCode ++;
				}else{
					totalCodeRevokeAmt += cursor.getInt(cursor.getColumnIndex(NAME_ORDER_AMT));
					totalCodeRevoke ++;
				}
			}
		}
		map.put(SigninPresenter.CODE_TOTAL,totalCode);
		map.put(SigninPresenter.CODE_TOTAL_AMT,totalCodeAmt);
		map.put(SigninPresenter.BONUS_TOTAL,totalBonus);
		map.put(SigninPresenter.BONUS_TOTAL_AMT,totalBonusAmt);
		map.put(SigninPresenter.CODE_REVOKE_TOTAL,totalCodeRevoke);
		map.put(SigninPresenter.CODE_REVOKE_TOTAL_AMT,totalCodeRevokeAmt);
		map.put(SigninPresenter.BONUS_REVOKE_TOTAL,totalBonusRevoke);
		map.put(SigninPresenter.BONUS_REVOKE_TOTAL_AMT,totalBonusRevokeAmt);
		return map;
	}


	
	private String getAINoNull(String value){
		return value == null?"":value;
	}

	public boolean updateSigninInfo(String akey) {
		getWritableDatabase().execSQL("delete from "+TABLE_SIGIN_INFO);
		return insertNewAkey(akey);
	}

	public List<VO.AO> getActivitys(String bin) {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_ACTIVITIES + " where " + NAME_CARDBIN
						+ "=?", new String[] { bin });
		List<VO.AO> ailist = new ArrayList<VO.AO>();
		while (cursor.moveToNext()) {
			VO.AO ai = new VO.AO();
			ai.activityType = cursor.getString(cursor.getColumnIndex(NAME_ACTIVITY));
			ai.activityId = cursor.getString(cursor
					.getColumnIndex(NAME_ACTIVITYID));
			ai.activityName = cursor.getString(cursor
					.getColumnIndex(NAME_ACTIVITYNAME));
			ai.remark = cursor.getString(cursor
					.getColumnIndex(NAME_ACTIVITYRULE));
			ai.amt = cursor.getString(cursor.getColumnIndex(NAME_AMT));
			ai.cardBin = cursor.getString(cursor.getColumnIndex(NAME_CARDBIN));
			ai.enterpriseName = cursor.getString(cursor
					.getColumnIndex(NAME_ENTERISENAME));
			ai.eTime = cursor.getString(cursor.getColumnIndex(NAME_ETIME));
			ai.sTime = cursor.getString(cursor.getColumnIndex(NAME_STIME));
			ailist.add(ai);
		}
		return ailist;
	}

	public boolean insertNewAkey(String akey) {
		ContentValues cv = new ContentValues();
		cv.put(NAME_AKEY,akey);
		cv.put(NAME_LAST_SIGNIN_TIME,System.currentTimeMillis());
		return getWritableDatabase().insert(TABLE_SIGIN_INFO,null,cv) >= 0;
//		getWritableDatabase().execSQL(
	}

	public String findAkey(String akey) {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_SIGIN_INFO + " where " + NAME_AKEY
						+ "=?", new String[]{akey});
		String s = null;
		if (cursor.moveToFirst()) {
			s = cursor.getString(cursor.getColumnIndex(NAME_AKEY));
		}
		cursor.close();
		return s;
	}

	public String findAkey() {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_SIGIN_INFO, new String[] {});
		int index = cursor.getColumnIndex(NAME_AKEY);
		String ret = null;
		if (cursor.moveToFirst()) {
			ret = cursor.getString(index);
		}
		cursor.close();
		return ret;
	}

	public String findSeriesNumber() {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_SIGIN_INFO, new String[] {});
		int index = cursor.getColumnIndex(NAME_SERIES_NUM);
		String ret = null;
		if (cursor.moveToFirst()) {
			ret = cursor.getString(index);
		}
		cursor.close();
		return ret;
	}

	public Long findAkeyTime(String akey) {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_SIGIN_INFO + " where " + NAME_AKEY
						+ "=?", new String[] { akey });
		Long s = 0l;
		if (cursor.moveToFirst()) {
			s = cursor.getLong(cursor.getColumnIndex(NAME_LAST_SIGNIN_TIME));
		}
		cursor.close();
		return s;
	}

	/**
	 * Updates the database format when a content provider is used with a
	 * database that was created with a different format.
	 * 
	 * Note: to support downgrades, creating a table should always drop it first
	 * if it already exists.
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase db, int oldV, final int newV) {

		for (int version = oldV + 1; version <= newV; version++) {
			upgradeTo(db, version);
		}
	}

	/**
	 * Upgrade database from (version - 1) to version.
	 */
	private void upgradeTo(SQLiteDatabase db, int version) {
		switch (version) {
		case 1:
			break;
		default:
//			throw new IllegalStateException("Don't know how to upgrade to "
//					+ version);
		}
	}


}
