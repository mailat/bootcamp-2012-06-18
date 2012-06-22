package com.intel.whish03;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    
	SheepSurface sheepSurface;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sheepSurface = new SheepSurface(this); 
        setContentView(sheepSurface);      
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        sheepSurface.terminateThread();
        System.gc();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        if (sheepSurface.surfaceCreated == true)
        {
        	sheepSurface.createThread();
        }
    }
}