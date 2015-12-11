package com.yimiehuijin.codeandbonuslibrary.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.data.ActivityInfo;
import com.yimiehuijin.codeandbonuslibrary.data.BonusConsumePostData;

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
	public static final String NAME_CARDBIN = "cardbin"; // 版本2新加入字段
	
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
	public static final String NAME_TRADE_TYPE = "type";

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
//		trade_sql.append(NAME_ORDER_ID).append(" varchar PRIMARY KEY,");
//		trade_sql.append(NAME_CARD_NO).append(" varchar,");
		trade_sql.append(NAME_ORDER_AMT).append(" varchar,");
//		trade_sql.append(NAME_ACTIVITYID).append(" varchar,");
		trade_sql.append(NAME_TRADE_TYPE).append(" varchar)");
//		trade_sql.append(NAME_EXPDATE).append(" varchar)");

		db.execSQL(sql.toString());
		db.execSQL(sigin_sql.toString());
		db.execSQL(activity_json_sql.toString());
		db.execSQL(order_sql.toString());
		db.execSQL(trade_sql.toString());
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

	public boolean insertOrder(BonusConsumePostData data,String flag){
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

	public List<BonusConsumePostData> getOrders(String flag){
		List<BonusConsumePostData> list = new ArrayList<BonusConsumePostData>();
		Cursor cursor = getReadableDatabase().rawQuery("select * from "+TABLE_ORDER_INFO+" where "+NAME_ORDER_FLAG+" =?",new String[]{flag});
		while(cursor.moveToNext()){
			BonusConsumePostData data = new BonusConsumePostData();
			data.amt = cursor.getString(cursor.getColumnIndex(NAME_ORDER_AMT));
			data.orgOrderId = cursor.getString(cursor.getColumnIndex(NAME_ORDER_ID));
			data.exp_date = cursor.getString(cursor.getColumnIndex(NAME_EXPDATE));
			data.cardNo = cursor.getString(cursor.getColumnIndex(NAME_CARD_NO));
		}
		return list;
	}

	public boolean insertTradeRecord(String data,String type){
		StringBuffer insert_sql = new StringBuffer();
		insert_sql.append("insert into ").append(TABLE_TRADE_LIST);
		insert_sql.append("(");
		insert_sql.append(NAME_ORDER_AMT).append(",");
		insert_sql.append(NAME_TRADE_TYPE).append(")");
		insert_sql.append(" values(?,?)");
		getWritableDatabase().execSQL(insert_sql.toString(),new String[]{data,type});
		return true;
	}

	public List<String> getTradelist(String type){
		List<String> list = new ArrayList<String>();
		Cursor cursor = getReadableDatabase().rawQuery("select * from "+TABLE_TRADE_LIST+" where "+NAME_TRADE_TYPE+" =?",new String[]{type});
		while(cursor.moveToNext()){
			String data = cursor.getString(cursor.getColumnIndex(NAME_ORDER_AMT));
			list.add(data);
		}
		return list;
	}

	public void insertActivities(List<ActivityInfo> activities) {
		getWritableDatabase().execSQL("delete from " + TABLE_ACTIVITIES);
		StringBuffer insert_sql = new StringBuffer();
		insert_sql.append("insert into ").append(TABLE_ACTIVITIES);
		insert_sql.append("(");
		insert_sql.append(NAME_ACTIVITYID).append(",");
		insert_sql.append(NAME_ACTIVITYNAME).append(",");
		insert_sql.append(NAME_ACTIVITYRULE).append(",");
		insert_sql.append(NAME_ACTIVITY).append(",");
		insert_sql.append(NAME_ENTERISENAME).append(",");
		insert_sql.append(NAME_AMT).append(",");
		insert_sql.append(NAME_ETIME).append(",");
		insert_sql.append(NAME_STIME).append(",");
		insert_sql.append(NAME_CARDBIN).append(")");
		insert_sql.append(" values(?,?,?,?,?,?,?,?,?)");

		SQLiteDatabase db = getWritableDatabase();
		SQLiteStatement state = db.compileStatement(insert_sql.toString());
		db.beginTransaction();
		try {
			for (ActivityInfo ai : activities) {
				state.bindString(1, getAINoNull(ai.activityId));
				state.bindString(2, getAINoNull(ai.activityName));
				state.bindString(3, getAINoNull(ai.remark));
				state.bindString(4, getAINoNull(ai.activityType));
				state.bindString(5, getAINoNull(ai.enterpriseName));
				state.bindString(6, getAINoNull(ai.amt));
				state.bindString(7, getAINoNull(ai.eTime));
				state.bindString(8, getAINoNull(ai.sTime));
				state.bindString(9, getAINoNull(ai.cardBin));
				state.executeInsert();
			}
		} catch (Exception e) {
			System.out.println("db error "+e.getMessage());
		} finally {
			db.setTransactionSuccessful();
			db.endTransaction();
		}
		state.close();
	}
	
	private String getAINoNull(String value){
		return value == null?"":value;
	}

	public void updateSigninInfo(String akey, String oldkey) {
		System.out.println("new akey =" + akey);
		if (oldkey == null || findAkey(oldkey) == null) {
			insertNewAkey(akey);
		} else {
			getWritableDatabase().execSQL(
					"update " + TABLE_SIGIN_INFO + " set " + NAME_AKEY + "='"
							+ akey + "'" + " where " + NAME_AKEY + "='"
							+ oldkey + "'");
		}
	}

	public List<ActivityInfo> getActivitys(String bin) {
		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from " + TABLE_ACTIVITIES + " where " + NAME_CARDBIN
						+ "=?", new String[] { bin });
		List<ActivityInfo> ailist = new ArrayList<ActivityInfo>();
		while (cursor.moveToNext()) {
			ActivityInfo ai = new ActivityInfo();
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

	public void insertNewAkey(String akey) {
		getWritableDatabase().execSQL(
				"insert into " + TABLE_SIGIN_INFO + "(" + NAME_AKEY + ","
						+ NAME_LAST_SIGNIN_TIME + ")" + " values(?,?)",
				new Object[]{akey, System.currentTimeMillis()});
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
