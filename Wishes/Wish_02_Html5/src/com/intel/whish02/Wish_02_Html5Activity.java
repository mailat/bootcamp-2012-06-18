package com.intel.whish02;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Wish_02_Html5Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        WebView gameWebView = (WebView)this.findViewById(R.id.gameWebView);
        gameWebView.setFocusableInTouchMode(false);
        gameWebView.setWebViewClient(new WebViewClient(){
            @Override  
            public boolean shouldOverrideUrlLoading(WebView view, String url)  
            {
                view.loadUrl(url);
                return true;  
            }
        });

        //enable JS
        WebSettings webSettings = gameWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);        

        //load the game online
        gameWebView.loadUrl("http://xwuz.com/bubble/");
        //gameWebView.loadUrl("http://m.kicktipp.de/"); //mobile site
        
    }
}