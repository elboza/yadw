package com.iazasoft.yadw;

import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;



public class YadwMini extends AppWidgetProvider {
	
	public static String YADW_WIDGET_UPDATE = "com.iazasoft.yadw.WIDGET_UPDATE";
	public static String YADW_WIDGET_PREFS = "com.iazasoft.yadw.WIDGET_PREFS";
	public static String YADW_EDIT_PREFS="com.iazasoft.yadw.EDIT_PREFS";
	static int[] widgets;
	
	public static class UpdateServiceMini extends Service {

		private SimpleDateFormat formatter=new SimpleDateFormat("EEE");
		private SimpleDateFormat formatter2=new SimpleDateFormat("d");
		private SimpleDateFormat formatter3=new SimpleDateFormat("MMM");
		
		public void onStart(Intent intent, int startId) {
			if(YADW_WIDGET_UPDATE.equals(intent.getAction())
					||(YADW_WIDGET_PREFS.equals(intent.getAction()))
					){
        		//Log.d("FFF","update action");
        		//Toast.makeText(this, "update service",Toast.LENGTH_SHORT).show();
        		yadwUpdate(this);
        	}
			stopSelf();
		}
		@Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		private void yadwUpdate(Context context){
			String giornosett=formatter.format(new Date());
			String now=formatter2.format(new Date());
			String month=formatter3.format(new Date());
			for(int widget:widgets){
				Log.d("YADW",Integer.toString(widget));
				RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.main_mini);
				LoadPreferences(this,widget,views);
				views.setTextViewText(R.id.textView2, giornosett);
				views.setTextViewText(R.id.textView1, now);
				views.setTextViewText(R.id.textView3,month);
				AppWidgetManager manager = AppWidgetManager.getInstance(this);
				Intent intent = new Intent(context, PrefsMini.class);
				intent.putExtra("id", widget);
	            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				views.setOnClickPendingIntent(R.id.button, pendingIntent);
	            manager.updateAppWidget(widget, views);
			}
			//ComponentName thisWidget = new ComponentName(context, YadwMini.class);
			//appWidgetManager.updateAppWidget(thisWidget, views);
		}
		private void LoadPreferences(Context context,int widgetId,RemoteViews rv){
			int fontsizeDate=10;
			int fontsizeDay=6;
            SharedPreferences sharedPreferences = context.getSharedPreferences(YADW_EDIT_PREFS+Integer.toString(widgetId), 0);
            int fontDelta=sharedPreferences.getInt("fontDelta",6);
            String backgroundStyle=sharedPreferences.getString("backgroundStyle","blue");
            String fontColor=sharedPreferences.getString("fontColor","term");
            rv.setFloat(R.id.textView1, "setTextSize", fontsizeDate+fontDelta);
            rv.setFloat(R.id.textView2, "setTextSize", fontsizeDay+fontDelta);
            rv.setFloat(R.id.textView3, "setTextSize", fontsizeDay+fontDelta);
            if(backgroundStyle.equals("term")){
            	rv.setImageViewResource(R.id.imageView1, R.drawable.term_border);
            }
            else if(backgroundStyle.equals("blue")){
            	rv.setImageViewResource(R.id.imageView1, R.drawable.border);
            }
            else if(backgroundStyle.equals("black")){
            	rv.setImageViewResource(R.id.imageView1, R.drawable.black);
            }
            else if(backgroundStyle.equals("transparent")){
            	rv.setImageViewResource(R.id.imageView1, R.drawable.transparent);
            }
            if(fontColor.equals("green")){
            	rv.setTextColor(R.id.textView1,Color.parseColor("#00ff00"));
            	rv.setTextColor(R.id.textView2,Color.parseColor("#00cc00"));
            	rv.setTextColor(R.id.textView3,Color.parseColor("#00cc00"));
            }
            else if(fontColor.equals("white")){
            	rv.setTextColor(R.id.textView1,Color.WHITE);
            	rv.setTextColor(R.id.textView2,Color.LTGRAY);
            	rv.setTextColor(R.id.textView3,Color.LTGRAY);
            }
            else if(fontColor.equals("yellow")){
            	rv.setTextColor(R.id.textView1,Color.parseColor("#FFFF00"));
            	rv.setTextColor(R.id.textView2,Color.parseColor("#FFD700"));
            	rv.setTextColor(R.id.textView3,Color.parseColor("#FFD700"));
            }
            else if(fontColor.equals("blue")){
            	rv.setTextColor(R.id.textView1,Color.parseColor("#00BFFF"));
            	rv.setTextColor(R.id.textView2,Color.parseColor("#4169E1"));
            	rv.setTextColor(R.id.textView3,Color.parseColor("#4169E1"));
            }
            else if(fontColor.equals("red")){
            	rv.setTextColor(R.id.textView1,Color.parseColor("#FF0000"));
            	rv.setTextColor(R.id.textView2,Color.parseColor("#FF6347"));
            	rv.setTextColor(R.id.textView3,Color.parseColor("#FF6347"));
            }
            else if(fontColor.equals("black")){
            	rv.setTextColor(R.id.textView1,Color.parseColor("#000000"));
            	rv.setTextColor(R.id.textView2,Color.parseColor("#696969"));
            	rv.setTextColor(R.id.textView3,Color.parseColor("#696969"));
            }
            else if(fontColor.equals("pink")){
            	rv.setTextColor(R.id.textView1,Color.parseColor("#DDA0DD"));
            	rv.setTextColor(R.id.textView2,Color.parseColor("#D8BFD8"));
            	rv.setTextColor(R.id.textView3,Color.parseColor("#D8BFD8"));
            }
            //bBorder = sharedPreferences.getBoolean("ckBorder", true);
            //bBackground = sharedPreferences.getBoolean("ckBackground",true);
            //String footguyColor=sharedPreferences.getString("FootguyColor","white");
            //fontsize=sharedPreferences.getInt("myFontSize",12);
            
            
		}
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if ((YADW_WIDGET_UPDATE.equals(intent.getAction()))
				||
				(YADW_WIDGET_PREFS.equals(intent.getAction()))
				) {
			Intent updateintent=new Intent(context,UpdateServiceMini.class);
			updateintent.setAction(intent.getAction());
			context.startService(updateintent);
		}
	}
	@Override
	public void onUpdate(Context context,AppWidgetManager appWidgetManager,int[] appWidgetIds){
		widgets=appWidgetIds;
		Intent updateintent=new Intent(context,UpdateServiceMini.class);
		updateintent.setAction(YADW_WIDGET_UPDATE);
		context.startService(updateintent);
		
		//Log.d("YADW","onUpdateMini");		
	}
}