package com.braillenotes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;

public class CreateTTSObj extends Application{
	private static TextToSpeech tts;
	private static int set;
	private static SharedPreferences preferences;
	private static int talk = 1;

	  public static TextToSpeech getObject(){
	    return tts;
	  }
	  
	  public static void setObject(TextToSpeech obj){
		set = 1;
	    tts = obj;
	  }
	  
	  public static void talkOn()
	  {
		  talk = 1;
	  }
	  
	  public static void talkOff()
	  {
		  talk = 0;
	  }
	  
	  public static int getTalkMode()
	  {
		  return talk;
	  }
	  
	  public static boolean isSet()
	  {
		  if(set == 1)
			  return true;
		  return false;
	  }
	  
	  public static void createPref(Context context)
	  {
		  preferences = PreferenceManager.getDefaultSharedPreferences(context);
	  }
	  
	  public static SharedPreferences getPref()
	  {
		  return preferences;
	  }
}
