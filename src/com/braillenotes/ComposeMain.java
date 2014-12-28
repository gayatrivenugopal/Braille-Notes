package com.braillenotes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class ComposeMain extends Activity{
		private ImageButton cell0,cell1,cell2,cell3,cell4,cell5;
		private TextToSpeech tts;
		private GestureDetector gestureDetector;
	//	private SensorManager mSensorManager;
		//private ShakeListener mSensorListener;
		private BitSet symbol;
		//private int currentContext;
		private int fileSavingMode;
		private String text;
		private char character;
		private int cellOn[], characterIndex;
		int lastIndex, readingFile;
		private Bitmap raisedDot,blankDot;
		private long downTime, upTime;
		private int tripleTapCounter;
		private long tripleTapTime;
		private String strRecipients, strCc, strBcc, strSubject, strBody;
		
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.compose_main);
	        this.text = "";
	        PatternHandler.restore();
	        readingFile = 0;
	        characterIndex = 0;
	        cell0 = (ImageButton)findViewById(R.id.imgCell0);
	        cell1 = (ImageButton)findViewById(R.id.imgCell1);
	        cell2 = (ImageButton)findViewById(R.id.imgCell2);
	        cell3 = (ImageButton)findViewById(R.id.imgCell3);
	        cell4 = (ImageButton)findViewById(R.id.imgCell4);
	        cell5 = (ImageButton)findViewById(R.id.imgCell5);
	        symbol = new BitSet(6);
	        
	        raisedDot = BitmapFactory.decodeResource(getResources(),R.drawable.raiseddot);
	        blankDot = BitmapFactory.decodeResource(getResources(),R.drawable.dot);
	        
	        cellOn = new int[]{0,0,0,0,0,0};
	        
	        if(getIntent().hasExtra("braille"))
	        {
	        	fileSavingMode = 1;	        	
	        }
	        else
	        {
	        	fileSavingMode = 0;
	        }
	       if(getIntent().hasExtra("edit"))
	        {
	        	readingFile = 1;
	        	//get the content typed by the user
	        	this.text = getIntent().getStringExtra("edit");
	        	getNextLetter();
	        	PatternHandler.restore();
	        }
	        
	        gestureDetector = new GestureDetector(this, new GestureListener());	        
	        
	        downTime=0;
	        upTime=0;
	        lastIndex = -2;
	      //  currentContext = 0;
	        tripleTapCounter = 0;
	        tripleTapTime = 0;
	        strRecipients = "";
	        strCc = "";
	        strBcc = "";
	        strSubject ="";
	        strBody = "";
	        
	        
	        tts=CreateTTSObj.getObject();
	        
	        
	       
	        
	       /* mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	        mSensorListener = new ShakeListener();   

	        mSensorListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

	          public void onShake() {
	        	  
	        	  
	        	  //CHECK FOR RIGHT TO LEFT AND VICE-VERSA
	        	  
	        	  
	            //Intent optionsIntent  = new Intent(ComposeMain.this, Options.class);
	            //startActivity(optionsIntent);
	            //Are you sure?
	            //Recipient's id/s separated by commas
	            //subject
	          }
	        });*/
	        
	        cell0.setOnTouchListener(new OnTouchListener() {
	           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	            public boolean onTouch(View v, MotionEvent event) {
		            	if(event.getAction()==MotionEvent.ACTION_DOWN)
		            	{
		            		
		            		
		            		
		            		vibe.vibrate(350);		
		            		downTime=System.currentTimeMillis();
		            	}
		            	if(event.getAction()==MotionEvent.ACTION_UP)
		            	{
		            		upTime=System.currentTimeMillis();
		            		
		            		if(upTime-downTime<1000) //only touch, no long click
		            		{
		            			if(CreateTTSObj.getTalkMode() == 1)
		            				tts.speak(lang_en.ONE, TextToSpeech.QUEUE_FLUSH, null);
			    			}
		            		else
		            		{
		            			tts.stop();
		    	        		if(cellOn[0]==0)
		    	        		{
		    	        			cell0.setImageBitmap(raisedDot);
		    	        			if(CreateTTSObj.getTalkMode() == 1)
		    	        				tts.speak(lang_en.ONE+lang_en.SELECTED, TextToSpeech.QUEUE_FLUSH, null);
		    	        		}
		    	        		else
		    	        		{
		    	        			cell0.setImageBitmap(blankDot);
		    	        			if(CreateTTSObj.getTalkMode() == 1)
		    	        				tts.speak(lang_en.ONE+lang_en.CLEARED, TextToSpeech.QUEUE_FLUSH, null);
		    	        		}
		    	        		cellOn[0]=1-cellOn[0];
		            		}
		            		
		            	}
		            			
		    			return false;
	    			}
	    		});
	        
	        cell1.setOnTouchListener(new OnTouchListener() {
	           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	            public boolean onTouch(View v, MotionEvent event) {
	            	if(event.getAction()==MotionEvent.ACTION_DOWN)
	            	{
	            		vibe.vibrate(350);	
	            		downTime=System.currentTimeMillis();
	            	}
	            	if(event.getAction()==MotionEvent.ACTION_UP)
	            	{
	            		upTime=System.currentTimeMillis();
	            		
	            		if(upTime-downTime<1000) //only touch, no long click
	            		{
	            			if(CreateTTSObj.getTalkMode() == 1)
	            				tts.speak(lang_en.TWO, TextToSpeech.QUEUE_FLUSH, null);
		    			}
	            		else
	            		{
	            			tts.stop();
	    	        		if(cellOn[1]==0)
	    	        		{
	    	        			cell1.setImageBitmap(raisedDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.TWO+lang_en.SELECTED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		else
	    	        		{
	    	        			cell1.setImageBitmap(blankDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.TWO+lang_en.CLEARED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		cellOn[1]=1-cellOn[1];
	            		}
	            	}			
	    			return false;
    			}
    		});

	        cell2.setOnTouchListener(new OnTouchListener() {
	           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	            public boolean onTouch(View v, MotionEvent event) {
	            	if(event.getAction()==MotionEvent.ACTION_DOWN)
	            	{
	            		vibe.vibrate(350);	
	            		downTime=System.currentTimeMillis();
	            	}
	            	if(event.getAction()==MotionEvent.ACTION_UP)
	            	{
	            		upTime=System.currentTimeMillis();
	            		
	            		if(upTime-downTime<1000) //only touch, no long click
	            		{
	            			if(CreateTTSObj.getTalkMode() == 1)
	            				tts.speak(lang_en.THREE, TextToSpeech.QUEUE_FLUSH, null);
		    			}
	            		else
	            		{
	            			tts.stop();
	    	        		if(cellOn[2]==0)
	    	        		{
	    	        			cell2.setImageBitmap(raisedDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.THREE+lang_en.SELECTED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		else
	    	        		{
	    	        			cell2.setImageBitmap(blankDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.THREE+lang_en.CLEARED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		cellOn[2]=1-cellOn[2];
	            		}
	            	}			
	    			return false;
    			}
    		});
	    
	        cell3.setOnTouchListener(new OnTouchListener() {
	           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	            public boolean onTouch(View v, MotionEvent event) {
	            	if(event.getAction()==MotionEvent.ACTION_DOWN)
	            	{
	            		vibe.vibrate(350);	
	            		downTime=System.currentTimeMillis();
	            	}
	            	if(event.getAction()==MotionEvent.ACTION_UP)
	            	{
	            		upTime=System.currentTimeMillis();
	            		
	            		if(upTime-downTime<1000) //only touch, no long click
	            		{
	            			if(CreateTTSObj.getTalkMode() == 1)
	            				tts.speak(lang_en.FOUR, TextToSpeech.QUEUE_FLUSH, null);
		    			}
	            		else
	            		{
	            			tts.stop();
	    	        		if(cellOn[3]==0)
	    	        		{
	    	        			cell3.setImageBitmap(raisedDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.FOUR+lang_en.SELECTED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		else
	    	        		{
	    	        			cell3.setImageBitmap(blankDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.FOUR+lang_en.CLEARED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		cellOn[3]=1-cellOn[3];
	            		}
	            	}				
	    			return false;
    			}
    		});
	        
	        cell4.setOnTouchListener(new OnTouchListener() {
	           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	            public boolean onTouch(View v, MotionEvent event) {
	            	if(event.getAction()==MotionEvent.ACTION_DOWN)
	            	{
	            		vibe.vibrate(350);	
	            		downTime=System.currentTimeMillis();
	            	}
	            	if(event.getAction()==MotionEvent.ACTION_UP)
	            	{
	            		upTime=System.currentTimeMillis();
	            		
	            		if(upTime-downTime<1000) //only touch, no long click
	            		{
	            			if(CreateTTSObj.getTalkMode() == 1)
	            				tts.speak(lang_en.FIVE, TextToSpeech.QUEUE_FLUSH, null);
		    			}
	            		else
	            		{
	            			tts.stop();
	    	        		if(cellOn[4]==0)
	    	        		{
	    	        			cell4.setImageBitmap(raisedDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.FIVE+lang_en.SELECTED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		else
	    	        		{
	    	        			cell4.setImageBitmap(blankDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.FIVE+lang_en.CLEARED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		cellOn[4]=1-cellOn[4];
	            		}
	            	}				
	    			return false;
    			}
    		});
	        
	        cell5.setOnTouchListener(new OnTouchListener() {
	           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

	            public boolean onTouch(View v, MotionEvent event) {
	            	if(event.getAction()==MotionEvent.ACTION_DOWN)
	            	{
	            		vibe.vibrate(350);	
	            		downTime=System.currentTimeMillis();
	            	}
	            	if(event.getAction()==MotionEvent.ACTION_UP)
	            	{
	            		upTime=System.currentTimeMillis();
	            		
	            		if(upTime-downTime<1000) //only touch, no long click
	            		{
	            			if(CreateTTSObj.getTalkMode() == 1)
	            				tts.speak(lang_en.SIX, TextToSpeech.QUEUE_FLUSH, null);
		    			}
	            		else
	            		{
	            			tts.stop();
	    	        		if(cellOn[5]==0)
	    	        		{
	    	        			cell5.setImageBitmap(raisedDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.SIX+lang_en.SELECTED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		else
	    	        		{
	    	        			cell5.setImageBitmap(blankDot);
	    	        			if(CreateTTSObj.getTalkMode() == 1)
	    	        				tts.speak(lang_en.SIX+lang_en.CLEARED, TextToSpeech.QUEUE_FLUSH, null);
	    	        		}
	    	        		cellOn[5]=1-cellOn[5];
	            		}
	            	}			
	    			return false;
    			}
    		});
	    }
	    
	   /* @Override
	    protected void onResume() {
	      super.onResume();
	      mSensorManager.registerListener(mSensorListener,
	          mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	          SensorManager.SENSOR_DELAY_UI);
	    }*/
	  

	    
	   /* @Override
	    protected void onPause() {
	      mSensorManager.unregisterListener(mSensorListener);
	      super.onStop();
	    }*/
	    
	    public void restore()
        {
        	cellOn=new int[]{0,0,0,0,0,0};
        	
        	tripleTapCounter = 0;
 	        tripleTapTime = 0;
 	        
        	cell0.setImageBitmap(blankDot);
        	cell1.setImageBitmap(blankDot);
        	cell2.setImageBitmap(blankDot);
        	cell3.setImageBitmap(blankDot);
        	cell4.setImageBitmap(blankDot);
        	cell5.setImageBitmap(blankDot);

        	strRecipients = "";
            strCc = "";
            strBcc = "";
            strSubject ="";
            strBody = "";
        }
	    
	    public void getNextLetter()
	    {
	    	if(characterIndex < text.length())
	    	{
	    		this.character = text.toCharArray()[characterIndex++];
	    		BitSet pattern = ChartModel.getBits(Character.toString(this.character));
		    	cellOn=new int[]{0,0,0,0,0,0};
		    	cell0.setImageBitmap(blankDot);
	        	cell1.setImageBitmap(blankDot);
	        	cell2.setImageBitmap(blankDot);
	        	cell3.setImageBitmap(blankDot);
	        	cell4.setImageBitmap(blankDot);
	        	cell5.setImageBitmap(blankDot);
	
		    	for(int i=0; i<6; i++)
		    	{
		    		if(pattern.get(i))
		    		{
			    		switch(i)
			    		{
			    			case 0:
			    				cell0.setImageBitmap(raisedDot);
			    				cellOn[i] = 1;
			    				break;
			    			case 1:
			    				cell1.setImageBitmap(raisedDot);
			    				cellOn[i] = 1;
			    				break;
			    			case 2:
			    				cell2.setImageBitmap(raisedDot);
			    				cellOn[i] = 1;
			    				break;
			    			case 3:
			    				cell3.setImageBitmap(raisedDot);
			    				cellOn[i] = 1;
			    				break;
			    			case 4:
			    				cell4.setImageBitmap(raisedDot);
			    				cellOn[i] = 1;
			    				break;
			    			case 5:
			    				cell5.setImageBitmap(raisedDot);
			    				cellOn[i] = 1;
			    				break;
			    		}
		    		}
		    	}
		    	
	        	tripleTapCounter = 0;
	 	        tripleTapTime = 0;
	 	     
	        	strRecipients = "";
	            strCc = "";
	            strBcc = "";
	            strSubject ="";
	            strBody = "";
	    	}
	    	else
	    		restore();
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
        					//call method to continue to the next activity
    						saveWord();
    						goToNext();
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
	            	
	            	if(lastIndex == -2)
	            	{
	            		// new character to be saved
		            	save(-1);		            	
	            	}
	            	else
	            	{
	            		//character to be saved at lastIndex
		            	save(lastIndex);
	            		
	            			lastIndex++;
	            			
	            			if(PatternHandler.getWord().size() == lastIndex)
	            			{
	            				lastIndex = -2;
	            				if(readingFile == 0)
	            					restore();
	            			//	else
	            				//	getNextLetter();
	            				speakString();
	            			}
	            			else
	            			{
	            			
		            				if(lastIndex <= PatternHandler.getWord().size())
		            				{
		            					symbol = PatternHandler.getSymbol(lastIndex);
		            					//display next
		            					String str = PatternHandler.getWordAt(lastIndex);
		            					
		            					Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();
		            					if(CreateTTSObj.getTalkMode() == 1)
		            						tts.speak(str,TextToSpeech.QUEUE_FLUSH, null);
		        		    			
					            		for(int i=0; i<symbol.length(); i++)
					            		{
					            			switch(i)
					            			{
					            			case 0:
					            				if(symbol.get(i))
					            				{
					            					cell0.setImageBitmap(raisedDot);
					            					cellOn[0]=1;
					            				}
					            				else
					            				{
					            					cell0.setImageBitmap(blankDot);
					            					cellOn[0]=0;
					            				}
					            				break;
					            			case 1:
					            				if(symbol.get(i))
					            				{
					            					cell1.setImageBitmap(raisedDot);
					            					cellOn[1]=1;
					            				}
					            				else
					            				{
					            					cell1.setImageBitmap(blankDot);
					            					cellOn[1]=0;
					            				}
					            				break;
					            			case 2:
					            				if(symbol.get(i))
					            				{
					            					cell2.setImageBitmap(raisedDot);
					            					cellOn[2]=1;
					            				}
					            				else
					            				{
					            					cell2.setImageBitmap(blankDot);
					            					cellOn[2]=0;
					            				}
					            				break;
					            			case 3:
					            				if(symbol.get(i))
					            				{
					            					cell3.setImageBitmap(raisedDot);
					            					cellOn[3]=1;
					            				}
					            				else
					            				{
					            					cell3.setImageBitmap(blankDot);
					            					cellOn[3]=0;
					            				}
					            				break;
					            			case 4:
					            				if(symbol.get(i))
					            				{
					            					cell4.setImageBitmap(raisedDot);
					            					cellOn[4]=1;
					            				}
					            				else
					            				{
					            					cell4.setImageBitmap(blankDot);
					            					cellOn[4]=0;
					            				}
					            				break;
					            			case 5:
					            				if(symbol.get(i))
					            				{
					            					cell5.setImageBitmap(raisedDot);
					            					cellOn[5]=1;
					            				}
					            				else
					            				{
					            					cell5.setImageBitmap(blankDot);
					            					cellOn[5]=0;
					            				}
					            				break;
					            			}
					            		}
		            			}
		            			else
		            			{
		            				lastIndex = -2;
		            			}
	            			}  		
	            	}
	            }
	            
	            //Left to Right
	            else if(e2.getX()>e1.getX() && e2.getEventTime()-e1.getEventTime()>=200 && e2.getX()-e1.getX()>=100)
	            {
	            	String str="";
	            	int flag = 0;
	            	
	            	if(PatternHandler.getWord().size()!=0)
	            	{   		
	            		if(lastIndex==-2)
	            			lastIndex = PatternHandler.getWord().size() - 1;
	            		else if(lastIndex ==0)
	            			flag = 1;
	            		else if(lastIndex>0)
	            			lastIndex--;
	            		
	            		if(lastIndex != -1 && flag!=1)
	            		{
	            			restore();
			            		
			            		str=(String) PatternHandler.getWord().get(lastIndex);
			            		
			            		Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();
			            		if(CreateTTSObj.getTalkMode() == 1)
			            			tts.speak(str,TextToSpeech.QUEUE_FLUSH, null);
			            		
			            		//pass this character to pattern handler and get the symbol, fill cell with the symbol pattern
			            		symbol = PatternHandler.getSymbol(str);
			            		
			            		for(int i=0; i<symbol.length(); i++)
			            		{
			            			switch(i)
			            			{
			            			case 0:
			            				if(symbol.get(i))
			            				{
			            					cell0.setImageBitmap(raisedDot);
			            					cellOn[0]=1;
			            				}
			            				else
			            				{
			            					cell0.setImageBitmap(blankDot);
			            					cellOn[0]=0;
			            				}
			            				break;
			            			case 1:
			            				if(symbol.get(i))
			            				{
			            					cell1.setImageBitmap(raisedDot);
			            					cellOn[1]=1;
			            				}
			            				else
			            				{
			            					cell1.setImageBitmap(blankDot);
			            					cellOn[1]=0;
			            				}
			            				break;
			            			case 2:
			            				if(symbol.get(i))
			            				{
			            					cell2.setImageBitmap(raisedDot);
			            					cellOn[2]=1;
			            				}
			            				else
			            				{
			            					cell2.setImageBitmap(blankDot);
			            					cellOn[2]=0;
			            				}
			            				break;
			            			case 3:
			            				if(symbol.get(i))
			            				{
			            					cell3.setImageBitmap(raisedDot);
			            					cellOn[3]=1;
			            				}
			            				else
			            				{
			            					cell3.setImageBitmap(blankDot);
			            					cellOn[3]=0;
			            				}
			            				break;
			            			case 4:
			            				if(symbol.get(i))
			            				{
			            					cell4.setImageBitmap(raisedDot);
			            					cellOn[4]=1;
			            				}
			            				else
			            				{
			            					cell4.setImageBitmap(blankDot);
			            					cellOn[4]=0;
			            				}
			            				break;
			            			case 5:
			            				if(symbol.get(i))
			            				{
			            					cell5.setImageBitmap(raisedDot);
			            					cellOn[5]=1;
			            				}
			            				else
			            				{
			            					cell5.setImageBitmap(blankDot);
			            					cellOn[5]=0;
			            				}
			            				break;
			            			}
			            		}
	            		}
	            		else
	            		{
	            			finish();
	            		}
	            	}
	            	else
	            	{
	            		finish();
	            	}
	            }
	            return true;  
	        }  
	    }
	    
	    public void onResume()
	    {
	    	super.onResume();
	    	if(tts!=null && tts.isSpeaking())
	    	{
	    		tts.stop();
	    	}
	    	super.onResume();
	    	tripleTapCounter = 0;
 	        tripleTapTime = 0;
	    }
	    
	    @Override
	    public void onStop()
	    {
	    	super.onStop();
	    	finish();
	    }
	    
	    public void save(int index)
	    {
	    	boolean success = false;
	    	
	    	if(cellOn[5] == 1 && cellOn[0] == 0 && cellOn[1] == 0 && cellOn[2] == 0 && cellOn[3] == 0 && cellOn[4] == 0)
	    	{
	    		PatternHandler.setCaps();
	    	}
	    	
	    	if(cellOn[0] == 0 && cellOn[2] == 0 && cellOn[1] == 1 && cellOn[2] == 1 && cellOn[3] == 1 && cellOn[4] == 1)
	    	{
	    		Log.e("This","setting number");
	    		PatternHandler.setNumericIndicator();
	    	}
	    	
			//pass cell array to pattern matcher
	    	//if(characterIndex-1 < text.length())
	    	{
	    		
	    		success = PatternHandler.matchPattern(cellOn, index);
	    		if(characterIndex == text.length())
	    			characterIndex++;
	    	}
			    
	    	if(success == true)
			{
	    		if(readingFile == 0)
	    			restore();
	    		else
	    		{
	    			getNextLetter();
	    			
	    		}
	    			
			    speakString();
			}
			else
			{
				if(characterIndex  >= text.length() && readingFile == 1)
				{
					characterIndex++;
					if(CreateTTSObj.getTalkMode() == 1)
				 		tts.speak(lang_en.END_OF_NOTE, TextToSpeech.QUEUE_FLUSH, null);
					Toast.makeText(getApplicationContext(), lang_en.END_OF_NOTE,Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), lang_en.INVALID_PATTERN,Toast.LENGTH_SHORT).show();
				 	if(CreateTTSObj.getTalkMode() == 1)
				 		tts.speak(lang_en.INVALID_PATTERN, TextToSpeech.QUEUE_FLUSH, null);
				 	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;
				 	vibe.vibrate(100);
				 	vibe.vibrate(100);
				 	vibe.vibrate(100);
				}
			}
	    }
	    
	  /*  public void save(int index, int flag)
	    {
	    	if(flag == -5)
	    	{
			  //pass cell array to pattern matcher
		    	boolean success = PatternHandler.matchPattern(cellOn, index, flag);
		    	if(success == true)
		    	{
		    		restore();
		    		
		    		speakString();
		    		
		    	}
		
		    	else
		    	{
		    		Toast.makeText(getApplicationContext(), lang_en.INVALID_PATTERN,Toast.LENGTH_SHORT).show();
		    		tts.speak(lang_en.INVALID_PATTERN, TextToSpeech.QUEUE_FLUSH, null);
		    	}
	    	}
	    }*/
	    
	    public void speakString()
	    {
	    	String str="";
    		for(int i=0; i<(PatternHandler.getWord()).size(); i++)
    		{
    			str+=PatternHandler.getWord().get(i);
    		}
    		if(lastIndex == str.length() || lastIndex == -2)
    		{
    			Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();
    			if(CreateTTSObj.getTalkMode() == 1)
    				tts.speak(str,TextToSpeech.QUEUE_FLUSH, null);
    		}
	    }
	    
	    public void goToNext()
	    {
	    	PatternHandler.saveBody();
	    	//open new activity
	    	if(fileSavingMode == 0)
	    	{
	    		Intent intent = new Intent(getApplicationContext(), TextActivity.class);
	    		//Toast.makeText(getApplicationContext(), PatternHandler.getBody(), Toast.LENGTH_SHORT).show();
	    		intent.putStringArrayListExtra("text", PatternHandler.getBody());
	    		startActivity(intent);
	    	}
	    	else
	    	{
	    		String string = "";
				for(int i=0; i<PatternHandler.getBody().size(); i++)
				{
					string += (PatternHandler.getBody()).get(i);
				}
				File dir = new File(Environment.getExternalStorageDirectory(), File.separator + getResources().getString(R.string.folder));;
				dir.mkdirs();
	    		File file = new File( dir, string + ".txt");
	    		BufferedWriter bw;
				try {
					bw = new BufferedWriter(new FileWriter(file));
					bw.write(text);
					bw.close();
					Toast.makeText(getApplicationContext(), "File Saved as "+ string, Toast.LENGTH_SHORT).show();
		    		if(CreateTTSObj.getTalkMode() == 1)
	    				tts.speak("File Saved as "+ string,TextToSpeech.QUEUE_FLUSH, null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	    		finish();
	    	}
	    	/*if(currentContext == 0)
      	  {
	        	  currentContext = lang_en.RECIPIENT;
	        	  Toast.makeText(getApplicationContext(),lang_en.RECIPIENT_DETAILS, Toast.LENGTH_SHORT).show();
	        	  if(CreateTTSObj.getTalkMode() == 1)
	        		  tts.speak(lang_en.RECIPIENT_DETAILS, TextToSpeech.QUEUE_FLUSH, null);
	        	  PatternHandler.saveBody();
      	  }
      	  else if(currentContext == lang_en.RECIPIENT)// done entering recipients' details
      	  {
      		currentContext = lang_en.CC;
      		Toast.makeText(getApplicationContext(),lang_en.CC_DETAILS, Toast.LENGTH_SHORT).show();
      		if(CreateTTSObj.getTalkMode() == 1)
      			tts.speak(lang_en.CC_DETAILS, TextToSpeech.QUEUE_FLUSH, null);
	        	  PatternHandler.saveRecipient();
      	  }
      	  else if(currentContext == lang_en.CC)// done entering recipients' details
      	  {
      		currentContext = lang_en.BCC;
      		Toast.makeText(getApplicationContext(),lang_en.BCC_DETAILS, Toast.LENGTH_SHORT).show();
      		if(CreateTTSObj.getTalkMode() == 1)
      			tts.speak(lang_en.BCC_DETAILS, TextToSpeech.QUEUE_FLUSH, null);
	        	  PatternHandler.saveCC();
      	  }
      	  else if(currentContext == lang_en.BCC)// done entering recipients' details
      	  {
      		currentContext = lang_en.SUBJECT;
      		Toast.makeText(getApplicationContext(),lang_en.SUBJECT_DETAILS, Toast.LENGTH_SHORT).show(); 
      		if(CreateTTSObj.getTalkMode() == 1)
      		  tts.speak(lang_en.SUBJECT_DETAILS, TextToSpeech.QUEUE_FLUSH, null);
	        	  PatternHandler.saveBCC();
      	  }
      	  else if(currentContext == lang_en.SUBJECT)// done entering recipients' details
      	  {
      		currentContext = 0;
      		PatternHandler.saveSubject();
      		sendMail(PatternHandler.getRecipients(),PatternHandler.getCc(), PatternHandler.getBcc(), PatternHandler.getSubject(), PatternHandler.getBody());
    		
      	  }
      	  */
      	  
      	  restore();
      	  
	    }
	    
	    public void saveWord()
	    {
	    	if(lastIndex == -2)
        	{
        		// new character to be saved
            	save(-1);		            	
        	}
        	else
        	{       		
        		//character to be saved at lastIndex
            	save(lastIndex);
        		
        		lastIndex = -2;
        		restore();
        		speakString();
        	}
	    }
	    
		public void sendMail(ArrayList<?> recipients, ArrayList<?> cc, ArrayList<?> bcc, ArrayList<?> subject, ArrayList<?> body)
	    {
	    	
	    	for(int i=0; i<recipients.size(); i++)
			{
	    		//Toast.makeText(getApplicationContext(), PatternHandler.getWord().get(i).toString(), Toast.LENGTH_SHORT).show();
				strRecipients+=PatternHandler.getRecipients().get(i).toString();
			}
	    	
	    	for(int i=0; i<cc.size(); i++)
			{
				strCc+=PatternHandler.getCc().get(i).toString();
			}
	    	
	    	for(int i=0; i<bcc.size(); i++)
			{
				strBcc+=PatternHandler.getBcc().get(i).toString();
			}
	    	
	    	for(int i=0; i<subject.size(); i++)
			{
				strSubject+=PatternHandler.getSubject().get(i).toString();
			}
	    	
	    	for(int i=0; i<body.size(); i++)
			{
				strBody+=PatternHandler.getBody().get(i).toString();
			}
	    	
	    	strRecipients+=",";
	    	strCc+=",";
	    	strBcc+=",";
	    	
	    	
	 	    String emailList[] = strRecipients.split(",");  
	 	    String emailCCList[] = strCc.split(",");  
	 	    String emailBCCList[] = strBcc.split(",");
	 	    
	 	    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);     	
	 	    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailList);  
	 	    emailIntent.putExtra(android.content.Intent.EXTRA_CC, emailCCList);  
	 	    emailIntent.putExtra(android.content.Intent.EXTRA_BCC, emailBCCList);  
	 	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, strSubject);  
	 	    emailIntent.setType("plain/text");  
	 	  
	 	    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, strBody);  
	 	    PatternHandler.restore();
	 	    startActivity(emailIntent);
	 	    //emailIntent.setAction(Intent.ACTION_SEND);
	 	    //Intent mailIntent = new Intent(Options.this, SelectId.class);
	 	    //startActivity(mailIntent);
	    }
}
