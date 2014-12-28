package com.braillenotes;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class Notes extends Activity {
	private Button btnCompose, btnOpen;
	private GestureDetector gestureDetector;
	private int tripleTapCounter;
	private long tripleTapTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		
		tripleTapCounter = 0;
		tripleTapTime = 0;
		gestureDetector = new GestureDetector(this, new GestureListener());
		
		btnCompose = (Button) findViewById(R.id.btnCompose);
		btnOpen = (Button) findViewById(R.id.btnOpen);
		
		btnCompose.setOnTouchListener(new OnTouchListener() {
	       	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	        public boolean onTouch(View v, MotionEvent event) {
	        		//vibe.vibrate(150);
	        		//tts.speak(lang_en.COMPOSE, TextToSpeech.QUEUE_FLUSH, null);
	    			
				
	        		if(event.getAction()==MotionEvent.ACTION_DOWN)
	        		{
	        			//check if talk mode is on
	        			if(CreateTTSObj.getTalkMode() == 1)
	        				BrailleNotes.tts.speak(getResources().getString(R.string.compose), TextToSpeech.QUEUE_FLUSH, null);
	        			vibe.vibrate(400);
	        		}
	        		gestureDetector.onTouchEvent(event);
					return false;
				}
			});
		
		btnOpen.setOnTouchListener(new OnTouchListener() {
	       	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	        public boolean onTouch(View v, MotionEvent event) {
	        		//vibe.vibrate(150);
	        		//tts.speak(lang_en.COMPOSE, TextToSpeech.QUEUE_FLUSH, null);
	    			
				
	        		if(event.getAction()==MotionEvent.ACTION_DOWN)
	        		{
	        			//check if talk mode is on
	        			if(CreateTTSObj.getTalkMode() == 1)
	        				BrailleNotes.tts.speak(getResources().getString(R.string.open), TextToSpeech.QUEUE_FLUSH, null);
	        			vibe.vibrate(400);
	        		}
	        		gestureDetector.onTouchEvent(event);
					return false;
				}
			});
		gestureDetector = new GestureDetector(this, new GestureListener());
	}

	public boolean onTouchEvent(MotionEvent event)
    {
    	return gestureDetector.onTouchEvent(event);
    }
	
	@Override
	protected void onResume()
	{
		super.onResume();
    	tripleTapCounter = 0;
        tripleTapTime = 0;
	}
	
	private class GestureListener extends GestureDetector.SimpleOnGestureListener {
		
		@Override
        public boolean onDown(MotionEvent e) {
        	tripleTapCounter ++;
        	
    		if(tripleTapCounter > 1)
    		{
    			if(System.currentTimeMillis() - tripleTapTime > 500)
    			{
    				tripleTapCounter = 0;
    				tripleTapTime = 0;
    			}
    			else
    			{
    				if(tripleTapCounter == 3)
    				{
    					tripleTapCounter = 0;
						tripleTapTime = 0;
    					//call method to continue to the next activity
    					if(CreateTTSObj.getTalkMode() == 1)
	    					BrailleNotes.tts.speak(getResources().getString(R.string.going_back_main_menu), TextToSpeech.QUEUE_FLUSH, null);
						finish();
    				}
    				else
    				{
    					tripleTapTime = System.currentTimeMillis();
    				}
    			}
    		}
    		else
    		{
    			tripleTapTime = System.currentTimeMillis();
    		}
    		return true;
        }
		
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            int composeLocation[]=new int[2];
            int openLocation[]=new int[2];

        	btnCompose.getLocationOnScreen(composeLocation);
        	btnOpen.getLocationOnScreen(openLocation);
            
        	if(composeLocation[1]<=e.getRawY() && e.getRawY()<=composeLocation[1]+btnCompose.getHeight())
        	{
        		Intent intent=new Intent(Notes.this,ComposeMain.class);
        		startActivity(intent);
        	}
        	
        	else if(openLocation[1]<=e.getRawY() && e.getRawX()<openLocation[0]+btnOpen.getWidth() && e.getRawY()<openLocation[1]+btnOpen.getHeight())
        	{   
        		File file = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.folder));
        		if(file.exists() && (file.listFiles()).length != 0)
        		{
        			//call ListNotes
        			Intent intent = new Intent(getApplicationContext(), ListNotes.class);
        			startActivity(intent);
        		}
        		else
        		{
        			Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_files_saved), Toast.LENGTH_SHORT).show();
        			if(CreateTTSObj.getTalkMode() == 1)
        				BrailleNotes.tts.speak(getResources().getString(R.string.no_files_saved), TextToSpeech.QUEUE_FLUSH, null);
        		}
        	}
            return true;
        }
    }
}
