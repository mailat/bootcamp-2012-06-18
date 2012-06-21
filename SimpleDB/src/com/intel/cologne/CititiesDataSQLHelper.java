package com.intel.cologne;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CititiesDataSQLHelper extends SQLiteOpenHelper {
	//database info
	private static final String TAG = "CititiesDataSQLHelper";
	private static final String DATABASE_NAME = "cities.db";
	private static final int DATABASE_VERSION = 1;
	
	//table name
	static final String TABLE = "cities";
	
	//table fields
	private static final String CITY = "city";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";	
	
	public CititiesDataSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create tables here pls
		String sql = "CREATE TABLE cities ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"CITY TEXT NOT NULL, " +
				"LATITUDE REAL, " +
				"LONGITUDE REAL);";
		
		Log.d(TAG, "onCreate " + sql);
		db.execSQL(sql);
		
		insertInitialValues(db);
	}

	private void insertInitialValues(SQLiteDatabase db) {
		//Method 1: pure SQL insert
		db.execSQL("insert into cities values ( null, 'Cologne', 24.36, 46.16);");
		
		//Method: ContentValues
		ContentValues cv = new ContentValues();
		
		cv.put(CITY, "Portland" );
		cv.put(LATITUDE, "45.5236" );
		cv.put(LONGITUDE, "122.6750" );
		db.insert(TABLE, null, cv);
		
		cv.put(CITY, "Kuala Lumpur" );
		cv.put(LATITUDE, "3.08" );
		cv.put(LONGITUDE, "101.42" );
		db.insert(TABLE, null, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newsVersion) {
		Log.d(TAG, "onUpgrade ");
		//drop the old tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);
		onCreate(db);
	}
}
