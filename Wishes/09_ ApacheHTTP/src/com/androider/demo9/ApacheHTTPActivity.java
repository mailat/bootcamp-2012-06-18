package com.androider.demo9;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
    
/**
 * ApacheHTTPActivity - cum este vremea in bucuresti?
 */

public class ApacheHTTPActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        getUrlContent("http://www.google.com/ig/api?weather=Paris");
    }
    
    public void getUrlContent(String url) {
        new SafeURLReader().execute(url);
    }
    
    private class SafeURLReader extends AsyncTask<String, Void, Void> {
    	
        private final HttpClient client = new DefaultHttpClient();
        private String response;
        private String error = null;
        private ProgressDialog progressDialog = new ProgressDialog(ApacheHTTPActivity.this);
        
        protected void onPreExecute() {
        	progressDialog.setMessage("Descarcam vremea ...");
        	progressDialog.show();
        }

        protected Void doInBackground(String... urls) {
            try {
                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = client.execute(httpget, responseHandler);
            } catch (Throwable e) {
            	error = e.getMessage();
                cancel(true);
            }
            
            return null;
        }
        
        protected void onPostExecute(Void unused) {
        	progressDialog.dismiss();
            TextView xmlResponse = (TextView) findViewById(R.id.xmlResponse); 
        	
            if (error != null) {
                xmlResponse.setText(error);
            } else {
                xmlResponse.setText(response);
                Log.d("Demo9", "Am descarcat XML-ul: " + response);
            }
        }
        
    }
}
