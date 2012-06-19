package com.marakana.yamba;

import java.util.Properties;
import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {
	private static final String TAG = "StatusActivity";
	EditText editText;
	Button updateButton;
	Twitter twitter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        //look after the EditText, Button
        editText = (EditText) findViewById(R.id.editText);
        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);
        
        //react on clicking the button
        twitter = new Twitter("student", "password");
        //twitter = new Twitter("marius", "parola");
        twitter.setAPIRootUrl("http://yamba.marakana.com/api");
        
        Properties properties = System.getProperties();
        //properties.put("http.proxyHost", "proxy-us.intel.com"); // US
        properties.put("http.proxyHost", "proxy.iind.intel.com"); // INDIA
        properties.put("http.proxyPort", "911");
    }

    //this is called when the button is clicker
	@Override
	public void onClick(View v) {
		//post an update to twitter
		new PostToTwitter().execute(editText.getText().toString());
	}
	
	//AsyncTask posting to yamba
	class PostToTwitter extends AsyncTask<String, Integer, String> 
	{

		// the background activity
		@Override
		protected String doInBackground(String... params) {
			Log.d(TAG, "we just click on the button, before message sent");
			
			try{
				Twitter.Status status = twitter.setStatus(editText.getText().toString());
				Log.d(TAG, "we just sent the message");
				return (status.text);
			}
			catch (Throwable e)
			{
				Log.d(TAG, "We failed to post a message" + e.toString());
				return ("Failed to post");
			}
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
		}

	}
}