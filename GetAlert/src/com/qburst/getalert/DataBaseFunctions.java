package com.qburst.getalert;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.internal.al;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseFunctions {

	static SQLiteDatabase sqlDB;
	static DataBaseHelper helperDB;
	static DataBaseFunctions databaseFunctions;
	
	Context context;
	
	public  static synchronized DataBaseFunctions getSharedDataBaseFunuctionClass(Context context) {
		if(databaseFunctions==null) {
			databaseFunctions = new  DataBaseFunctions(context);
		}
		return databaseFunctions;
	}

	public DataBaseFunctions(Context context) {
		
		this.context = context;
		helperDB = new DataBaseHelper(context);
		 try {
			 
			 helperDB.createDataBase();
			  
			 } catch (IOException ioe) {
			  
			 throw new Error("Unable to create database");
			  
			 }
		 
		 try {
			 
			 helperDB.openDataBase();
			  
			 }catch(SQLException sqle){
			  
				 sqle.printStackTrace();
			 throw sqle;
			  
			 }
	}
	
	
	
	public long insert(AlarmSetterDB alarmSetterDB) {
		
		sqlDB = helperDB.getWritableDatabase(); // Open
		
		
		ContentValues values = new ContentValues();
		
		values.put("alarm_name", alarmSetterDB.alarmName);
		values.put("alarm_description", alarmSetterDB.alarmDescription);
		
		values.put("mark_latitude", alarmSetterDB.markLatitude);
		values.put("mark_longitude", alarmSetterDB.markLongitude);
		values.put("current_latitude", alarmSetterDB.currentLatitude);
		values.put("current_longitude", alarmSetterDB.currentLongitude);
		
	
		long result = sqlDB.insert("alarm_setter", null, values);
		if (result > -1) {
			Log.i("", TAG + "Inserted.");
		} else {
			Log.i("", TAG + "Not Inserted!");
		}
		sqlDB.close();
		return result;
	}

	public ArrayList<AlarmSetterDB> viewAll() {


		ArrayList<AlarmSetterDB> allAlarms = new ArrayList<AlarmSetterDB>();
		
		sqlDB = helperDB.getReadableDatabase();
		
		Cursor cursor = sqlDB.query("alarm_setter", null, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			
			for (; !cursor.isAfterLast(); cursor.moveToNext()) {
				
				AlarmSetterDB alarmSetterDB = new AlarmSetterDB();
				allAlarms.add(cursorToObject(cursor, alarmSetterDB));
			}
		}
		sqlDB.close();
		return allAlarms;
	}

	private AlarmSetterDB cursorToObject(Cursor cursor, AlarmSetterDB alarmSetterDB) {


		alarmSetterDB = new AlarmSetterDB();
		
		alarmSetterDB.alarmName = cursor.getString(cursor.getColumnIndex("alarm_name"));
		
		alarmSetterDB._id = Long.parseLong(cursor.getString(cursor.getColumnIndex("_id")));
		
		alarmSetterDB.markLatitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("mark_latitude")));
		alarmSetterDB.markLongitude= Double.parseDouble(cursor.getString(cursor.getColumnIndex("mark_longitude")));
		alarmSetterDB.currentLatitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("current_latitude")));
		alarmSetterDB.currentLongitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("current_longitude")));
		
		return alarmSetterDB;
	}

	public AlarmSetterDB view(long _id) {

		AlarmSetterDB alarmSetterDB = new AlarmSetterDB();
		
		sqlDB = helperDB.getReadableDatabase();
		String whereClause = "_id = " + _id;
		Cursor cursor = sqlDB.query("alarm_setter", null, whereClause, null, null, null, null);
		if (cursor.moveToFirst()) {
			alarmSetterDB = cursorToObject(cursor, alarmSetterDB);
		}
		return alarmSetterDB;
	}

	String TAG = "TTMS LOG : ";

	public long update(AlarmSetterDB alarmSetterDB) {

		sqlDB = helperDB.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("alarm_name", alarmSetterDB.alarmName);
      values.put("alarm_description", alarmSetterDB.alarmDescription);
		
		values.put("mark_latitude", alarmSetterDB.markLatitude);
		values.put("mark_longitude", alarmSetterDB.markLongitude);
		values.put("current_latitude", alarmSetterDB.currentLatitude);
		values.put("current_longitude", alarmSetterDB.currentLongitude);
		
		String whereClause = "_id = " + alarmSetterDB._id;
		int rows = sqlDB.update("alarm_setter", values, whereClause, null);
		if (rows > 0) {
			Log.i("", TAG + rows + " rows updated.");
		} else {
			Log.i("", TAG + "Not Updated!");
		}
		sqlDB.close();
		return alarmSetterDB._id;
	}

	public void delete(AlarmSetterDB alarmSetterDB) {

		sqlDB = helperDB.getWritableDatabase();

		String whereClause = "_id = " + alarmSetterDB._id;

		int rows = sqlDB.delete("alarm_setter", whereClause, null);
		if (rows > 0) {
			Log.i("", TAG + rows + " rows deleted.");
		} else {
			Log.i("", TAG + "Not Deleted!");
		}
		sqlDB.close();
	}
	
	
}
