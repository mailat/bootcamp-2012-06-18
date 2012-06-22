package com.intel.whish03;

import android.graphics.Canvas;

public class SheepRunThread extends Thread {
    private SheepSurface panel;
    private boolean run = false;

    public SheepRunThread(SheepSurface pan) {
        panel = pan;
    }
    
    public void setRunning(boolean Run) {
        run = Run;
    }

    public boolean isRunning() {
        return run;
    }


    @Override
    public void run() {
        Canvas c;
        while (run) {
            c = null;
            try {
                c = panel.getHolder().lockCanvas(null);
                  synchronized (panel.getHolder()) {
                	
                	panel.updatePhysics();
                	panel.onDraw(c);
                	
                }
            } finally {
                if (c != null) {
                    panel.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }    
}