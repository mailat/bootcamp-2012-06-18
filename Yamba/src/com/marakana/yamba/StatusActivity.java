package com.marakana.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        //look after the Edittext
        EditText editText = (EditText) findViewById(R.id.editText);
        Button updateButton = (Button) findViewById(R.id.updateButton);
        
        editText.setText("this is a text for you display");
    }
}