package com.iazasoft.yadw;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PrefsMini extends Activity {
	public static String YADW_WIDGET_PREFS = "com.iazasoft.yadw.WIDGET_PREFS";
	public static String YADW_EDIT_PREFS="com.iazasoft.yadw.EDIT_PREFS";
	private String wID;
	Spinner lstColor,lstBackground;
	SeekBar sbFontSize;
	TextView yadwFontSizeText;
	int fontDelta=12;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.prefs);
	    Intent intent = this.getIntent();
	    int id=intent.getIntExtra("id",0);
	    wID=Integer.toString(id);
	    Log.d("PREFS",Integer.toString(id));
	    lstColor=(Spinner)findViewById(R.id.lstColor);
	    sbFontSize=(SeekBar)findViewById(R.id.seekBar1);
	    lstBackground=(Spinner)findViewById(R.id.lstBackground);
	    yadwFontSizeText=(TextView)findViewById(R.id.yadwFontSizeText);
	    lstColor.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	            //int item = lstColor.getSelectedItemPosition();
	            SavePreferencesStr("fontColor",lstColor.getSelectedItem().toString());
	        }


	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	        	
	        }
	    });
	    lstBackground.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	            //int item = lstColor.getSelectedItemPosition();
	            SavePreferencesStr("backgroundStyle",lstBackground.getSelectedItem().toString());
	        }


	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	        	
	        }
	    });
	    sbFontSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            
            public void onStopTrackingTouch(SeekBar arg0) {
                 SavePreferencesInt("fontDelta",fontDelta);
            }
           
            public void onStartTrackingTouch(SeekBar arg0) {
                 
            }
           
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            	fontDelta=arg1;
                yadwFontSizeText.setText("font size: +"+ fontDelta);
                 
                 
            }
     });
	    LoadPreferences();
	}
	private void LoadPreferences(){
		int fontsizeDate=10;
		int fontsizeDay=6;
        SharedPreferences sharedPreferences = getSharedPreferences(YADW_EDIT_PREFS+wID, 0);
        int fontDelta=sharedPreferences.getInt("fontDelta",6);
        String backgroundStyle=sharedPreferences.getString("backgroundStyle","blue");
        String fontColor=sharedPreferences.getString("fontColor","term");
        sbFontSize.setProgress(fontDelta);
        yadwFontSizeText.setText("font size: +"+ fontDelta);
        lstColor.setSelection(mparseColor(fontColor));
		lstColor.invalidate();
		lstBackground.setSelection(mparseBackground(backgroundStyle));
		lstBackground.invalidate();
	}
	private int mparseColor(String s){
		if(s.equals("white")) return 0;
		else if(s.equals("yellow")) return 1;
		else if(s.equals("red")) return 2;
		else if(s.equals("green")) return 3;
		else if(s.equals("blue")) return 4;
		else if(s.equals("pink")) return 5;
		else if(s.equals("black")) return 6;
		else return 0;
	}
	private int mparseBackground(String s){
		if(s.equals("term")) return 0;
		else if(s.equals("blue")) return 1;
		else if(s.equals("black")) return 2;
		else if(s.equals("transparent")) return 3;
		else return 0;
	}
	private void SavePreferencesStr(String key, String value){
		SharedPreferences sharedPreferences = getSharedPreferences(YADW_EDIT_PREFS+wID, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	private void SavePreferencesInt(String key, int value){
		SharedPreferences sharedPreferences = getSharedPreferences(YADW_EDIT_PREFS+wID, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	@Override
	public void onDestroy(){
		//Log.d("FFF","PREFS stopped");
		sendUpdateTrigger();
		super.onDestroy();
	}
	private void sendUpdateTrigger(){
		//Intent intent = new Intent(FOOTGUY_WIDGET_PREFS);
        
        Intent intent = new Intent(this,YadwMini.class);
        intent.setAction(YADW_WIDGET_PREFS);
        //sendBroadcast(intent);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        try{
        		pendingIntent.send();
        }
        catch(Exception e){
        	Log.d("FFF INTENT",e.toString());
        }
	}
}
