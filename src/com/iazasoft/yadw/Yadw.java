package com.iazasoft.yadw;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class Yadw extends AppWidgetProvider {
	private SimpleDateFormat formatter=new SimpleDateFormat("EEEEEEEEE");
	private SimpleDateFormat formatter2=new SimpleDateFormat("d MMM yyy");
	
	@Override
	public void onUpdate(Context context,AppWidgetManager appWidgetManager,int[] appWidgetIds){
		String giornosett=formatter.format(new Date());
		String now=formatter2.format(new Date());
		
		RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.terminal);
		updateViews.setTextViewText(R.id.textView2, giornosett);
		updateViews.setTextViewText(R.id.textView1, now);
		appWidgetManager.updateAppWidget(appWidgetIds,updateViews);
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}