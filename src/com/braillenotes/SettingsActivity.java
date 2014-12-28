package com.braillenotes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class SettingsActivity extends Activity {
	private Button btnSpeechSettings, btnSaveSettings;
	private GestureDetector gestureDetector;
	private int tripleTapCounter;
	private long tripleTapTime ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		tripleTapCounter = 0;
	    tripleTapTime = 0;
		btnSpeechSettings = (Button) findViewById(R.id.btnSpeechSettings);
		btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
		gestureDetector = new GestureDetector(this, new GestureListener());
		
		if(CreateTTSObj.getTalkMode() == 0)
		{
			btnSpeechSettings.setText(getResources().getString(R.string.turn_on_voice));
			btnSpeechSettings.setContentDescription(getResources().getString(R.string.turn_on_voice));
		}
		if(BrailleNotes.filename_in_braille == 0)
		{
			btnSaveSettings.setText(getResources().getString(R.string.save_braille));
			btnSaveSettings.setContentDescription(getResources().getString(R.string.save_braille));
		}
		else
		{
			btnSaveSettings.setText(getResources().getString(R.string.save_key));
			btnSaveSettings.setContentDescription(getResources().getString(R.string.save_key));
		}
		btnSpeechSettings.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

    		public boolean onTouch(View v, MotionEvent event) {
    			if(event.getAction()==MotionEvent.ACTION_DOWN)
        		{
        			//check if talk mode is on
    				if(CreateTTSObj.getTalkMode() == 1)
    					BrailleNotes.tts.speak(btnSpeechSettings.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        			vibe.vibrate(400);
        		}
    			gestureDetector.onTouchEvent(event);
    			return false;
    		}
    	});
		
		btnSaveSettings.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

    		public boolean onTouch(View v, MotionEvent event) {
    			if(event.getAction()==MotionEvent.ACTION_DOWN)
        		{
        			//check if talk mode is on
    				if(CreateTTSObj.getTalkMode() == 1)
    					BrailleNotes.tts.speak(btnSaveSettings.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        			vibe.vibrate(400);
        		}
    			gestureDetector.onTouchEvent(event);
    			return false;
    		}
    	});
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
            int speechSettingsLocation[]=new int[2];
            int saveSettingsLocation[]=new int[2];
            
        	btnSpeechSettings.getLocationOnScreen(speechSettingsLocation);
        	btnSaveSettings.getLocationOnScreen(saveSettingsLocation);
            
        	if(speechSettingsLocation[1]<=e.getRawY() && e.getRawY()<=speechSettingsLocation[1]+btnSpeechSettings.getHeight())
        	{
        		if(btnSpeechSettings.getText().toString().equals(getResources().getString(R.string.turn_on_voice)))
				{
					btnSpeechSettings.setText(getResources().getString(R.string.turn_off_voice));
					btnSpeechSettings.setContentDescription(getResources().getString(R.string.turn_off_voice));
					BrailleNotes.tts.speak(getResources().getString(R.string.voice_is_on), TextToSpeech.QUEUE_FLUSH, null);
					CreateTTSObj.talkOn();
				}
				else
				{
					btnSpeechSettings.setText(getResources().getString(R.string.turn_on_voice));
					btnSpeechSettings.setContentDescription(getResources().getString(R.string.turn_on_voice));
					BrailleNotes.tts.speak(getResources().getString(R.string.voice_is_off), TextToSpeech.QUEUE_FLUSH, null);
					CreateTTSObj.talkOff();
				}
        	}
        	
        	else if(saveSettingsLocation[1]<=e.getRawY() && e.getRawY()<saveSettingsLocation[1]+btnSaveSettings.getHeight())
        	{   
        		if(btnSaveSettings.getText().toString().equals(getResources().getString(R.string.save_key)))
				{
					btnSaveSettings.setText(getResources().getString(R.string.save_braille));
					btnSaveSettings.setContentDescription(getResources().getString(R.string.save_braille));
					BrailleNotes.tts.speak(getResources().getString(R.string.save_method_is_key), TextToSpeech.QUEUE_FLUSH, null);
					//turn braille off
					BrailleNotes.filename_in_braille = 0;
				}
				else
				{
					btnSaveSettings.setText(getResources().getString(R.string.save_key));
					btnSaveSettings.setContentDescription(getResources().getString(R.string.save_key));
					BrailleNotes.tts.speak(getResources().getString(R.string.save_method_is_braille), TextToSpeech.QUEUE_FLUSH, null);
					//turn braille on
					BrailleNotes.filename_in_braille = 1;
				}
        	}
        	
        	/*else if(aboutLocation[1]<=e.getRawY() && e.getRawY()<=aboutLocation[1]+btnAbout.getHeight())
        	{
        		//Intent intent=new Intent(BrailleNotes.this,About.class);
				//startActivity(intent);
        	}*/
        	
            return true;
        }
    }

}
