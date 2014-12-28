package com.braillenotes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class NotesAdapter extends ArrayAdapter{
	private ArrayList<String> values;
	private Context context;
	private static Vibrator vibe;
	private int doubleTapCounter;
	private long doubleTapTime;
	private String text = "";
	
	@SuppressWarnings("unchecked")
	public NotesAdapter(Context context, ArrayList<String> values, Vibrator vibe) {
		super(context, R.layout.row, values);
		this.values = values;
		this.context = context;
		NotesAdapter.vibe = vibe;
		this.doubleTapCounter = 0;
		this.doubleTapTime = 0;
	}
	
	public View getView(int position, View view, ViewGroup parent)
	{
		final ViewGroup parentClass = parent;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.note);
		textView.setText(values.get(position));
		final int pos = position;
		textView.setOnTouchListener(new OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	        		if(event.getAction()==MotionEvent.ACTION_DOWN)
	        		{    	
	        			//check for double tap event
	        			doubleTapCounter ++;
	            		if(doubleTapCounter > 1)
	            		{
	            			if(System.currentTimeMillis() - doubleTapTime > 500)
	            			{
	            				doubleTapCounter = 0;
	            				doubleTapTime = 0;		
	            			}
	            			else
	            			{
	            				if(doubleTapCounter == 2)
	            				{
	            					String line;
	            					doubleTapCounter = 0;
	        						doubleTapTime = 0;
	        						text = "";
	        						//open note
	        						File file = new File(Environment.getExternalStorageDirectory() + File.separator + parentClass.getContext().getResources().getString(R.string.folder) + File.separator + values.get(pos) + ".txt");
	        						try {
										BufferedReader br = new BufferedReader(new FileReader(file));
										try {
											while ((line = br.readLine()) != null) {
												text += line;
											}
										} catch (IOException e) {
											e.printStackTrace();
										}
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
	        						Intent intent = new Intent(parentClass.getContext(), ComposeMain.class);
	        						intent.putExtra("edit", text);
	        						parentClass.getContext().startActivity(intent);
	            				}
	            				else
	            				{
	            					//check if talk mode is on
	        	        			if(CreateTTSObj.getTalkMode() == 1)
	        	        				BrailleNotes.tts.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
	        	        			NotesAdapter.vibe.vibrate(400);
	            					doubleTapTime = System.currentTimeMillis();
	            				}
	            			}
	            		}
	            		else
	            		{
	            			//check if talk mode is on
		        			if(CreateTTSObj.getTalkMode() == 1)
		        				BrailleNotes.tts.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
		        			NotesAdapter.vibe.vibrate(400);
	            			doubleTapTime = System.currentTimeMillis();
	            		}
	        		}
	        		return false;
				}
			});
		return rowView;
	}
	
}
