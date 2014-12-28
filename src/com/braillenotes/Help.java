package com.braillenotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;



public class Help extends Activity implements OnInitListener{

	private String instructions[];
	private ListView listvu;
	private TextToSpeech tts;
	private GestureDetector gestureDetector;
	private int tripleTapCounter;
	private long tripleTapTime;
	//private Vibrator vibe;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        //vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;
        checkTTS();
       
        tripleTapCounter = 0;
        tripleTapTime = 0;
        
        instructions = new String[17];
      
        instructions[0]=Html.fromHtml("The app will let you write in Grade 1 Braille.<br/>").toString();
        instructions[1]=Html.fromHtml("To compose a note, double tap Notes option. Then double tap Compose option.<br/>").toString();
        instructions[2]=Html.fromHtml("To write a character, touch various portions of the screen to familiarize yourself with the location of the 6 dots.<br/>").toString();
        instructions[3]=Html.fromHtml("To go back to the previous screen from the screen with the dots, move your finger on the screen from right to left.<br/>").toString();
        instructions[4]=Html.fromHtml("To select or deselect a dot, press the dot for some time.<br/>").toString();
        instructions[5]=Html.fromHtml("Once you are done entering the pattern, move your finger on the screen from right to left to go to the next pattern.<br/>").toString();
        instructions[6]=Html.fromHtml("To check what pattern you entered previously, move your finger on the screen from left to right.<br/>").toString();
        instructions[7]=Html.fromHtml("Once you are done selecting a pattern, tap three to four times on the screen to save the note.<br/>").toString();
        instructions[8]=Html.fromHtml("Similarly, tap three to four times on the screen to to go back to the previous menu.<br/>").toString();
        instructions[9]=Html.fromHtml("To open a note, double tap Notes option. Then double tap Open option. Move your finger over the screen. The voice assistant will read aloud the name of the file as you move your finger over each option. Double tap to open a note. Move your finger on the screen from right to left to go through all the characters in your note. You may edit the note by changing the pattern in the same way you composed the note. Please make sure that you go through all the characters till the end while reading, otherwise if you save the note, only the portion till where you read will be saved, thus assuming that the remaining portion is not required. Tap a few times to save the note.<br/>").toString();
        instructions[10]=Html.fromHtml("To delete a note, place your finger on the name of the file in the list for some time. You will be asked to repeat this action to confirm deletion. The note will get deleted and the list of notes will get updated. To go back, tap three to four times in the empty area abouve the list of files.<br/>").toString();
        instructions[11]=Html.fromHtml("A sighted person may be required initially to train you to use the app.<br/>").toString();
        instructions[12]=Html.fromHtml("The tutorial will help you to understand the pattern used for each character by informing you about the dots selected for a particular pattern. Do not turn the Voice Assistant Off as the assistant will guide you through the tutorial.<br/>").toString();
        instructions[13]=Html.fromHtml("During the tutorial, your device will vibrate for a long duration to tell you that a dot is selected and for a short duration to indicate that the particular dot is not selected.<br/>").toString();
        instructions[14]=Html.fromHtml("To go the next or previous pattern, move your finger on the screen from right to left, and left to right, respectively.<br/>").toString();
        instructions[15]=Html.fromHtml("Move your finger on the screen from right to left to start the tutorial.<br/>").toString();
        instructions[16]=lang_en.TAP_THRICE_MAIN_SCREEN;
        
        if(CreateTTSObj.getTalkMode() == 1)
        {
		 tts.speak("Welcome to BrailleNotes."+instructions[0]+instructions[1]+instructions[2]+
				 instructions[3]+instructions[4]+
				 instructions[5]+instructions[6]+
				 instructions[7]+instructions[8]+
				 instructions[9]+instructions[10]+
				 instructions[11]+instructions[12]+
				 instructions[13]+instructions[14]+
				 instructions[15]+instructions[16],
				 TextToSpeech.QUEUE_FLUSH, null);    
        }
        display();

    }
    
    public void onPause()
    {
    	super.onPause();
    	if(tts!=null && tts.isSpeaking())
    		tts.stop();
    }
    
    public void onStop()
    {
    	super.onStop();
    	if(tts!=null && tts.isSpeaking())
    		tts.stop();
    	finish();
    }
    
    public void display()
    {
    	listvu = (ListView)findViewById(R.id.listview);
    	
    	MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getApplicationContext(), instructions);

    	listvu.setAdapter(adapter);
    	listvu.setDivider(null);
        gestureDetector = new GestureDetector(this, new GestureListener());
        
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event); 
            }};
        listvu.setOnTouchListener(gestureListener);
    	
    	/*listvu.setOnItemLongClickListener (new OnItemLongClickListener() {
    		  public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
    			 String selectedEmail = listvu.getItemAtPosition(position).toString();
    			
    			 for(int i=0; i<listvu.getCount(); i++)
    			 {
    				 listvu.getChildAt(i).setBackgroundColor(Color.BLACK);
    			 }
    			 view.setBackgroundColor(Color.DKGRAY);
		       	 SharedPreferences.Editor editor = CreateTTSnPref.getPref().edit();
		       	 editor.putString("defaultEmail", selectedEmail); // value to store
		       	 editor.commit();
		       	 checkTTS();
		       	if(tts.isSpeaking())
   				 tts.stop();
		       	if(CreateTTSnPref.getTalkMode() == 1)
		       		tts.speak(selectedEmail+" "+lang_en.SELECTED, TextToSpeech.QUEUE_FLUSH, null);

		       	 return true;
    		  }
    		});*/
    	
    }

    
    
    public void checkTTS()
    {
    	if(CreateTTSObj.isSet())
			 tts = CreateTTSObj.getObject();
		 else
		 {
			 Intent checkIntent = new Intent();
		     checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		     startActivityForResult(checkIntent, 0);
		 }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0)
        {
        	if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
        	{
    	    //success, create the TTS instance
        		tts = new TextToSpeech(this, this);
        		CreateTTSObj.setObject(tts);
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
    		finish();
    	}
    }
    @Override
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
     				//Toast.makeText(getApplicationContext(), ">500", Toast.LENGTH_SHORT).show();
     			}
     			else
     			{
     				if(tripleTapCounter == 3)
     				{
     					tripleTapCounter = 0;
 						tripleTapTime = 0;
 						tts.stop();
     					finish();
     				}
     				else
     				{
     					tripleTapTime = System.currentTimeMillis();
     					//Toast.makeText(getApplicationContext(), "<500", Toast.LENGTH_SHORT).show();
     				}
     			}
     		}
     		else
     		{
     			tripleTapTime = System.currentTimeMillis();
     			//Toast.makeText(getApplicationContext(), "counter<=1", Toast.LENGTH_SHORT).show();
     		}
             return false;
         }
        
        public boolean onFling(MotionEvent e1, MotionEvent e2,  
                final float velocityX, final float velocityY)
        { 
        	
            //Right to Left
           if(e1.getX()>e2.getX() && e2.getEventTime()-e1.getEventTime()>=200 && e1.getX()-e2.getX()>=100)
            {
        		Intent tutIntent  = new Intent(Help.this, Tutorial.class);
    			startActivity(tutIntent);
    			finish();
    			return true;
            }
           //Left to Right
          /* else if(e2.getX()>e1.getX() && e2.getEventTime()-e1.getEventTime()>=200 && e2.getX()-e1.getX()>=100)
           {
        	   	Intent mainIntent  = new Intent(Help.this, BrailleNotes.class);
        	   	finish();
   				startActivity(mainIntent);
   				return true;
           }*/
            return true;  
        }  
    }
   }
