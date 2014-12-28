package com.braillenotes;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ListNotes extends Activity {
	private ListView listView;
	private ArrayList<String> fileNames;
	private GestureDetector gestureDetector;
	private int tripleTapCounter;
	private long tripleTapTime;
	private TextToSpeech tts;
	private int deleteFile = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_notes);
		
		this.tripleTapCounter = 0;
		this.tripleTapTime = 0;
		tts=CreateTTSObj.getObject();
		
		listView = (ListView) findViewById(R.id.listNotes);
		//get filenames
		File dir = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.folder));
		File[] fileArray = dir.listFiles();
		fileNames = new ArrayList<String>();
		for(int i=0; i<fileArray.length; i++)
		{
			fileNames.add(fileArray[i].getName().substring(0,fileArray[i].getName().lastIndexOf(".txt")));
		}
		final Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;
		
		gestureDetector = new GestureDetector(this, new GestureListener());
		listView.setAdapter(new NotesAdapter(getApplicationContext(),fileNames, vibe));
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				//delete
				//long press for the first time
				if(deleteFile == -1)
				{
					if(CreateTTSObj.getTalkMode() == 1)
	    				tts.speak(lang_en.DELETION_CONFIRMATION, TextToSpeech.QUEUE_FLUSH, null);
					deleteFile = position;
				}
				//long press again on the same file
				else if(deleteFile == position)
				{
					File fileToBeDeleted = new File(Environment.getExternalStorageDirectory() + File.separator + 
							getResources().getString(R.string.folder) + File.separator + fileNames.get(position)+".txt");
					String fileName = fileNames.get(position);
					if(fileToBeDeleted.delete())
					{
						fileNames.remove(position);
						listView.setAdapter(new NotesAdapter(getApplicationContext(),fileNames, vibe));
						if(CreateTTSObj.getTalkMode() == 1)
							tts.speak("Deleted " + fileName + " successfully.", TextToSpeech.QUEUE_FLUSH, null);
						Toast.makeText(getApplicationContext(), "Deleted " + fileName + " successfully.", Toast.LENGTH_SHORT).show();
						//empty directory
						if(fileNames.size() == 0)
							finish();
					}
					else
					{
						if(CreateTTSObj.getTalkMode() == 1)
							tts.speak("Could not delete " + fileName, TextToSpeech.QUEUE_FLUSH, null);
						Toast.makeText(getApplicationContext(), "Could not delete " + fileName, Toast.LENGTH_SHORT).show();
					}
					deleteFile = -1;
				}
				//long press but on a different file
				else
				{
					deleteFile = -1;
				}
				return false;
			}
		});
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		this.tripleTapCounter = 0;
		this.tripleTapTime = 0;
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
	            return false;
	        }
	 }
}
