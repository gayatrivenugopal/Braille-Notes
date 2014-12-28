package com.braillenotes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TextActivity extends Activity {
	private EditText text;
	private GestureDetector gestureDetector;
	private Button btnSave;
	private int tripleTapCounter;
	private long tripleTapTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		
		this.tripleTapCounter = 0;
		this.tripleTapTime = 0;
		
		gestureDetector = new GestureDetector(this, new GestureListener());
		btnSave = (Button) findViewById(R.id.btnSave);
		text = (EditText) findViewById(R.id.text);
		if(getIntent().hasExtra("text"))
		{
			String string = "";
			for(int i=0; i<((ArrayList<String>)(getIntent().getExtras().getStringArrayList("text"))).size(); i++)
			{
				string += ((ArrayList<String>)(getIntent().getExtras().getStringArrayList("text"))).get(i);
			}
			text.setText(string);
		}
		
		btnSave.setOnTouchListener(new OnTouchListener() {
           	Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;

    		public boolean onTouch(View v, MotionEvent event) {
    			if(event.getAction()==MotionEvent.ACTION_DOWN)
        		{
        			//check if talk mode is on
    				if(CreateTTSObj.getTalkMode() == 1)
    					BrailleNotes.tts.speak(btnSave.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
	
	private class GestureListener extends GestureDetector.SimpleOnGestureListener {
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
	    					//call finish method to continue to the next activity
	    					if(CreateTTSObj.getTalkMode() == 1)
		    					BrailleNotes.tts.speak(getResources().getString(R.string.going_back), TextToSpeech.QUEUE_FLUSH, null);
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
            int saveLocation[]=new int[2];
            
        	btnSave.getLocationOnScreen(saveLocation);
 
        	if(saveLocation[1]<=e.getRawY() && e.getRawY()<saveLocation[1]+btnSave.getHeight())
        	{   
        		if(BrailleNotes.filename_in_braille == 0)
				{
					//alert dialog to accept file name
					AlertDialog.Builder alert = new AlertDialog.Builder(TextActivity.this);
					alert.setTitle("Save File");
					final EditText editFile = new EditText(getApplicationContext());
					editFile.setTextColor(Color.BLACK);
					editFile.setHint("Enter the name of the file to be saved. Existing files will be overwritten.");
					editFile.setContentDescription("Enter the name of the file to be saved. Existing files will be overwritten.");
					alert.setView(editFile);
					alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface arg0, int arg1) {
							File dir = new File(Environment.getExternalStorageDirectory(), File.separator + getResources().getString(R.string.folder));;
							dir.mkdirs();
							File f = new File(dir.getAbsolutePath(), editFile.getText().toString()+".txt");
							BufferedWriter bw;
							try {
								bw = new BufferedWriter(new FileWriter(f));
								bw.write(text.getText().toString());
								bw.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}							
							//check if talk mode is on
		    				if(CreateTTSObj.getTalkMode() == 1)
		    					BrailleNotes.tts.speak("File Saved!", TextToSpeech.QUEUE_FLUSH, null);
							Toast.makeText(getApplicationContext(), "File Saved!", Toast.LENGTH_SHORT).show();
							finish();
						}
					});
					alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
										
										public void onClick(DialogInterface arg0, int arg1) {
											//do nothing
										}
									});
					alert.show();
				}
				else
				{
					//go to BrailleNotes
					Intent intent = new Intent(getApplicationContext(), ComposeMain.class);
					intent.putExtra("braille", text.getText().toString());
					startActivity(intent);
				}
        	}
            return true;
        }
    }

}
