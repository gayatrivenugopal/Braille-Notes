package com.braillenotes;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Tutorial extends Activity {
	private ImageButton cell0,cell1,cell2,cell3,cell4,cell5;
	private TextToSpeech tts;
	private Iterator<?> keyIter;
	private GestureDetector gestureDetector;
	private int cellOn[];
	private int prevCellOn[][];
	private String charactrs[];
	private int prevIndex;
	private int currIndex;
	private int tripleTapCounter;
	private long tripleTapTime;                        
	private Set<?> keys;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        
        tts=CreateTTSObj.getObject();
        if(CreateTTSObj.getTalkMode() == 1)
			 tts.speak(lang_en.TAP_THRICE_MAIN_SCREEN, TextToSpeech.QUEUE_FLUSH, null);
      Toast.makeText(getApplicationContext(), lang_en.TAP_THRICE_MAIN_SCREEN, Toast.LENGTH_SHORT).show();
    
        try
        {
        	Thread.sleep(3000);
        }
        catch(InterruptedException ie)
        {
        	Log.e("BrailleNotes",ie.toString());
        }
        
        gestureDetector = new GestureDetector(this, new GestureListener());	 
	     
        cell0 = (ImageButton)findViewById(R.id.imgCell0);
        cell1 = (ImageButton)findViewById(R.id.imgCell1);
        cell2 = (ImageButton)findViewById(R.id.imgCell2);
        cell3 = (ImageButton)findViewById(R.id.imgCell3);
        cell4 = (ImageButton)findViewById(R.id.imgCell4);
        cell5 = (ImageButton)findViewById(R.id.imgCell5);
       
       
       
        BitmapFactory.decodeResource(getResources(),R.drawable.raiseddot);
        BitmapFactory.decodeResource(getResources(),R.drawable.dot);
        keys = (ChartModel.getChart()).keySet();
        tripleTapCounter = 0;
        tripleTapTime = 0;
        charactrs = new String[100];
        
		keyIter = keys.iterator();
		
		cellOn = new int[]{0,0,0,0,0,0};
		prevCellOn = new int[100][6];
		currIndex = 0;
			  
		String key = keyIter.next().toString(); 
		BitSet value = (ChartModel.getChart()).get(key);
			
		for(int i=0; i<6; i++)
		{
			if(value.get(i) == true)
			{
				cellOn[i] = 1;
			}
		}
		charactrs[prevIndex] = key;
		prevIndex++;
		if(CreateTTSObj.getTalkMode() == 1)
			tts.speak(key, TextToSpeech.QUEUE_FLUSH, null);
		Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
		
        cell0.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

            public boolean onTouch(View v, MotionEvent event) {
	            	if(event.getAction()==MotionEvent.ACTION_DOWN)
	            	{
	            		if(cellOn[0] == 1)
	            		{
	            			vibe.vibrate(350);
	            			if(CreateTTSObj.getTalkMode() == 1)
		        				tts.speak(lang_en.ONE+" "+lang_en.IS_SELECTED, TextToSpeech.QUEUE_FLUSH, null);
	            			Toast.makeText(getApplicationContext(), lang_en.ONE+" "+lang_en.IS_SELECTED, Toast.LENGTH_SHORT).show();
	            		}
	            		else
	            			vibe.vibrate(50);
	            	}
	    			return false;
    			}
    		});
        
        cell1.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

            public boolean onTouch(View v, MotionEvent event) {
            	if(event.getAction()==MotionEvent.ACTION_DOWN)
            	{
            		if(cellOn[1] == 1)
            		{
            			vibe.vibrate(350);
            			if(CreateTTSObj.getTalkMode() == 1)
            				tts.speak(lang_en.TWO+" "+lang_en.IS_SELECTED, TextToSpeech.QUEUE_FLUSH, null);
            			Toast.makeText(getApplicationContext(), lang_en.TWO+" "+lang_en.IS_SELECTED, Toast.LENGTH_SHORT).show();
            		}
            		else
            			vibe.vibrate(50);
            	}	
    			return false;
			}
		});

        cell2.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

            public boolean onTouch(View v, MotionEvent event) {
            	if(event.getAction()==MotionEvent.ACTION_DOWN)
            	{
            		if(cellOn[2] == 1)
            		{
            			vibe.vibrate(350);
            			if(CreateTTSObj.getTalkMode() == 1)
            				tts.speak(lang_en.THREE+" "+lang_en.IS_SELECTED, TextToSpeech.QUEUE_FLUSH, null);
            			Toast.makeText(getApplicationContext(), lang_en.THREE+" "+lang_en.IS_SELECTED, Toast.LENGTH_SHORT).show();
            		}
            		else
            			vibe.vibrate(50);
            	}	
    			return false;
			}
		});
    
        cell3.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

            public boolean onTouch(View v, MotionEvent event) {
            	if(event.getAction()==MotionEvent.ACTION_DOWN)
            	{
            		if(cellOn[3] == 1)
            		{
            			vibe.vibrate(350);
            			if(CreateTTSObj.getTalkMode() == 1)
            				tts.speak(lang_en.FOUR+" "+lang_en.IS_SELECTED, TextToSpeech.QUEUE_FLUSH, null);
            			Toast.makeText(getApplicationContext(), lang_en.FOUR+" "+lang_en.IS_SELECTED, Toast.LENGTH_SHORT).show();
            		}
            		else
            			vibe.vibrate(50);
            	}	
    			return false;
			}
		});
        
        cell4.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

            public boolean onTouch(View v, MotionEvent event) {
            	if(event.getAction()==MotionEvent.ACTION_DOWN)
            	{
            		if(cellOn[4] == 1)
            		{
            			vibe.vibrate(350);
            			if(CreateTTSObj.getTalkMode() == 1)
            				tts.speak(lang_en.FIVE+" "+lang_en.IS_SELECTED, TextToSpeech.QUEUE_FLUSH, null);
            			Toast.makeText(getApplicationContext(), lang_en.FIVE+" "+lang_en.IS_SELECTED, Toast.LENGTH_SHORT).show();
            		}
            		else
            			vibe.vibrate(50);
            	}	
    			return false;
			}
		});
        
        cell5.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

            public boolean onTouch(View v, MotionEvent event) {
            	if(event.getAction()==MotionEvent.ACTION_DOWN)
            	{
            		if(cellOn[5] == 1)
            		{
            			vibe.vibrate(350);
            			if(CreateTTSObj.getTalkMode() == 1)
            				tts.speak(lang_en.SIX+" "+lang_en.IS_SELECTED, TextToSpeech.QUEUE_FLUSH, null);
            			Toast.makeText(getApplicationContext(), lang_en.SIX+" "+lang_en.IS_SELECTED, Toast.LENGTH_SHORT).show();
            		}
            		else
            			vibe.vibrate(50);
            	}
    			return false;
			}
		});
    }
    
    public boolean onTouchEvent(MotionEvent event)
    {
    	return gestureDetector.onTouchEvent(event);
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
            return false;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            
            return true;
        }
        
        public boolean onFling(MotionEvent e1, MotionEvent e2,  
                final float velocityX, final float velocityY)
        { 
        	//Right to Left
            if(e1.getX()>e2.getX() && e2.getEventTime()-e1.getEventTime()>=200 && e1.getX()-e2.getX()>=100)
            {
            	if(currIndex == prevIndex - 1 && prevIndex < keys.size())
            	{
	            	currIndex = prevIndex;
	            	cellOn = new int[]{0,0,0,0,0,0};
	      			String key = keyIter.next().toString(); 
	      			charactrs[prevIndex] = key;
	            	prevCellOn[prevIndex++]=cellOn;
	      			BitSet value = (ChartModel.getChart()).get(key);
	      			if(CreateTTSObj.getTalkMode() == 1)
	    				tts.speak(key, TextToSpeech.QUEUE_FLUSH, null);
	      			Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
	      			for(int i=0; i<6; i++)
	      			{
	      				if(value.get(i) == true)
	      				{
	      					cellOn[i] = 1;
	      				}
	      			}
            	}
            	else
            	{
            		currIndex++;
            		cellOn = prevCellOn[currIndex];
            		if(CreateTTSObj.getTalkMode() == 1)
	    				tts.speak(charactrs[currIndex], TextToSpeech.QUEUE_FLUSH, null);
            		Toast.makeText(getApplicationContext(), charactrs[currIndex], Toast.LENGTH_SHORT).show();
            		
            	}
            }
            
            //Left to Right
            else if(e2.getX()>e1.getX() && e2.getEventTime()-e1.getEventTime()>=200 && e2.getX()-e1.getX()>=100)
            {
            	if(currIndex!=0)
            	{
            		currIndex--;
        			cellOn = prevCellOn[currIndex];
        			if(CreateTTSObj.getTalkMode() == 1)
	    				tts.speak(charactrs[currIndex], TextToSpeech.QUEUE_FLUSH, null);
        			Toast.makeText(getApplicationContext(), charactrs[currIndex], Toast.LENGTH_SHORT).show();
            	}
            	
            }
            return true;
        }  
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
    	finish();
    }
    
    public void onResume()
    {
    	super.onResume();
    	if(tts!=null && tts.isSpeaking())
    	{
    		tts.stop();
    	}
    }
}
