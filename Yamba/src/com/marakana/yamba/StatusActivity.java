package com.marakana.yamba;

import java.util.Properties;
import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener, TextWatcher {
	private static final String TAG = "StatusActivity";
	EditText editText;
	Button updateButton;
	Twitter twitter;
	TextView textCount;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        //look after the EditText, Button, TextView
        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(this); // attach a TextWatcher to the edit text
        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);
        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText("140");
        textCount.setTextColor(Color.GREEN);
        
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
				return (status.text);
			}
			catch (Throwable e)
			{
				Log.d(TAG, "We failed to post a message" + e.toString());
				return ("Failed to post");
			}
			finally
			{
				Log.d(TAG, "we tried to sent the message" + editText.getText().toString());				
			}
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			//Part 1: homework we log the message
			Log.d(TAG, "Message sent: " + editText.getText().toString());

			//Part 2: we display the I am working
			Toast.makeText(StatusActivity.this, "It is working " + result, Toast.LENGTH_LONG).show();
			
			//Part 3: we clear the text
			editText.setText("");
		}

	}

	//we are reacting on how many chars are available
	@Override
	public void afterTextChanged(Editable statusText) {
		//the count number display
		int count = 140 - statusText.length();
		textCount.setText(Integer.toString(count));
		
		//the color change for counter
		textCount.setTextColor(Color.GREEN);
		if ( count < 10)
			textCount.setTextColor(Color.YELLOW);
		if ( count < 0)
			textCount.setTextColor(Color.RED);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

}