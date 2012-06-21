package com.marakana.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApplication extends Application implements OnSharedPreferenceChangeListener {
	private static final String TAG = "YambaApplication";
	Twitter twitter = null;
	SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
        //get the username, password, apiroot from preferences
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.prefs.registerOnSharedPreferenceChangeListener(this);	
        Log.d(TAG, "YambaApplication onCreate");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
        Log.d(TAG, "YambaApplication onTerminate");		
	}
	
    /**
     * getTwitter
     * get a twitter instance with credentials from preferences
     * @return Twitter
     */
	public synchronized Twitter getTwitter() {
		if ( this.twitter == null)
		{
			//get the values from the preferences
			String username, password, apiRoot;
			username = this.prefs.getString("username", "");
			password = this.prefs.getString("password", "");
			apiRoot = this.prefs.getString("apiRoot", "http://yamba.marakana.com/api");
			
			//react on clicking the button
			this.twitter = new Twitter(username, password);
			this.twitter.setAPIRootUrl(apiRoot);
		}
		
        return (this.twitter);
	}
	
	@Override
	public synchronized void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		this.twitter = null;
	}	
	
}
