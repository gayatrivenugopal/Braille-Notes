package com.braillenotes;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

//OnInitListener to use TTS
public class BrailleNotes extends Activity implements OnInitListener{
	private Button btnNotes, btnHelp, btnSettings;//, btnAbout;
	private GestureDetector gestureDetector;
	public static TextToSpeech tts;
	public static int filename_in_braille;
	public SharedPreferences preferences;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braillenotes);
        
        CreateTTSObj.createPref(getApplicationContext());
        
        ChartModel.initialize();
        
        btnNotes = (Button)findViewById(R.id.btnNotes);
        btnHelp = (Button)findViewById(R.id.btnHelp);
       // btnAbout = (Button)findViewById(R.id.btnAbout);
        btnSettings = (Button)findViewById(R.id.btnSettings);
        btnSettings.setText(getResources().getString(R.string.menu_settings));
        CreateTTSObj.talkOn();
        
        gestureDetector = new GestureDetector(this, new GestureListener());
        
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        
        startActivityForResult(checkIntent, 0);
        
       
        Toast.makeText(this, lang_en.TAP_TWICE, Toast.LENGTH_LONG).show();
        
        btnNotes.setOnTouchListener(new OnTouchListener() {
       	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

        public boolean onTouch(View v, MotionEvent event) {
        		//vibe.vibrate(150);
        		//tts.speak(lang_en.COMPOSE, TextToSpeech.QUEUE_FLUSH, null);
    			
			
        		if(event.getAction()==MotionEvent.ACTION_DOWN)
        		{
        			//check if talk mode is on
        			if(CreateTTSObj.getTalkMode() == 1)
        				tts.speak(getResources().getString(R.string.notes), TextToSpeech.QUEUE_FLUSH, null);
        			vibe.vibrate(400);
        		}
        		gestureDetector.onTouchEvent(event);
				return false;
			}
		});
        
        btnHelp.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

    		public boolean onTouch(View v, MotionEvent event) {
    			if(event.getAction()==MotionEvent.ACTION_DOWN)
        		{
        			//check if talk mode is on
    				if(CreateTTSObj.getTalkMode() == 1)
    					tts.speak(lang_en.HELP, TextToSpeech.QUEUE_FLUSH, null);
        			vibe.vibrate(400);
        		}
    			gestureDetector.onTouchEvent(event);
    			return false;
    		}
    	});
        
        btnSettings.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

    		public boolean onTouch(View v, MotionEvent event) {
    			if(event.getAction()==MotionEvent.ACTION_DOWN)
        		{
        			//check if talk mode is on
    				if(CreateTTSObj.getTalkMode() == 1)
    					tts.speak(btnSettings.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        			vibe.vibrate(400);
        		}
    			gestureDetector.onTouchEvent(event);
    			return false;
    		}
    	});
        
       /* btnAbout.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

    		public boolean onTouch(View v, MotionEvent event) {
    			if(event.getAction()==MotionEvent.ACTION_DOWN)
        		{
        			//check if talk mode is on
    				if(CreateTTSnPref.getTalkMode() == 1)
    					tts.speak(lang_en.ABOUT, TextToSpeech.QUEUE_FLUSH, null);
        			vibe.vibrate(400);
        		}
    			gestureDetector.onTouchEvent(event);
    			return false;
    		}
    	});*/
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0)
        {
        	if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
        	{
    	    //success, create the TTS instance
        		try
        		{
        			tts = new TextToSpeech(this, this);
        			CreateTTSObj.setObject(tts);
        			//tts.speak("Hellooooooooooooooooooooooo", TextToSpeech.QUEUE_FLUSH, null);
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
            }
        	else
        	{
        	 //missing data, install it
        		Intent installIntent = new Intent();
        	 	installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        		startActivity(installIntent);
        	}
        }
    }

    
    public void onInit(int status)
    {
    	if(status == TextToSpeech.ERROR)
    	{
    		Toast.makeText(this, "Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
    	}
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
    }
    
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            int notesLocation[]=new int[2];
            int helpLocation[]=new int[2];
            int speechSettingsLocation[]=new int[2];
          //  int aboutLocation[]=new int[2];
        	btnNotes.getLocationOnScreen(notesLocation);
        	btnHelp.getLocationOnScreen(helpLocation);
        	btnSettings.getLocationOnScreen(speechSettingsLocation);
        	//btnAbout.getLocationOnScreen(aboutLocation);
            
        	if(notesLocation[1]<=e.getRawY() && e.getRawY()<=notesLocation[1]+btnNotes.getHeight())
        	{
        		//Intent intent=new Intent(BrailleNotes.this,ComposeMain.class);
        		Intent intent=new Intent(BrailleNotes.this,Notes.class);
        		startActivity(intent);
        	}
        	
        	else if(helpLocation[1]<=e.getRawY() && e.getRawX()<helpLocation[0]+btnHelp.getWidth() && e.getRawY()<helpLocation[1]+btnHelp.getHeight())
        	{   
        		Intent helpActivity = new Intent(BrailleNotes.this, Help.class);
        		//Toast.makeText(getApplicationContext(), lang_en.HELP , Toast.LENGTH_SHORT).show();
        		startActivity(helpActivity);
        	}
        	
        	else if(speechSettingsLocation[1]<=e.getRawY() && e.getRawX()<=speechSettingsLocation[0]+btnSettings.getWidth() && e.getRawY()<speechSettingsLocation[1]+btnSettings.getHeight())
        	{
        		//Toast.makeText(getApplicationContext(), "Speech Settings" , Toast.LENGTH_SHORT).show();
        		Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
        		startActivity(intent);
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