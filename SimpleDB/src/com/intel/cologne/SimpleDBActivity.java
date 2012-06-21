package com.intel.cologne;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class SimpleDBActivity extends Activity {
	TextView citiesListing;
	CititiesDataSQLHelper citiesData;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        citiesListing = (TextView) findViewById(R.id.citiesListing);
        citiesData = new CititiesDataSQLHelper(this);
        
        //get me the content of the cities
        showCities();
    }

    private void showCities() {
    	//Step 01: SQLiteDatabase
    	SQLiteDatabase db = citiesData.getWritableDatabase();
    	
    	Cursor cursor = db.query(CititiesDataSQLHelper.TABLE, null, null, null, null, null, null);
    	
    	//itereate in the resulted cursor
    	String tableValue = "Listing of the table records: \n\n";
    	while ( cursor.moveToNext())
    	{
    		long id = cursor.getLong(0);
    		String city = cursor.getString(1);
    		double lat = cursor.getLong(2);
    		double lon = cursor.getLong(3);
    		tableValue = tableValue + id + "-" + city + "-" + lat + "-" + lon + "\n";
    	}
    	
    	//put the result into the qui
    	citiesListing.setText(tableValue);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		citiesData.close();
	}
	
}