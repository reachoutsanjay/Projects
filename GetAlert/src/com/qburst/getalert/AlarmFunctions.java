package com.qburst.getalert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

public class AlarmFunctions {
	
	static void setAnAlarm(long id, Context context, AlarmSetterDB currentAlarm){
		
		
		PendingIntent pendingIntent;
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent alarm = new Intent(context, NotificationPage.class);
		Log.i("" , "New alarm ID " + id);
		alarm.putExtra("a-m", id + "" );
		
		pendingIntent = PendingIntent.getActivity(context, (int) id, alarm, 0);
		
		alarmManager.set(AlarmManager.RTC, 1000, pendingIntent);
		
		
		LocationManager locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Intent proximity = new Intent(context, ProximityReceiver.class);
		proximity.setAction("com.qburst.qburst.Receiver" + id);
		
		pendingIntent = PendingIntent.getBroadcast(context, (int) id, proximity, 0);
		locManager.addProximityAlert(currentAlarm.markLatitude, currentAlarm.markLongitude, 1000, -1, pendingIntent);
		
		
	}
	



	static void cancelThisAlarm(long id, Context context) {
		
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent notify = new Intent(context, NotificationPage.class);
		
		PendingIntent pendingIntentNotify = PendingIntent.getActivity(context, (int)id, notify, 0);
		alarmManager.cancel(pendingIntentNotify);
		
		LocationManager locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		Intent proxy = new Intent(context, ProximityReceiver.class);
		proxy.setAction("com.qburst.getalert.Receiver" + id);
		
		PendingIntent pentingIntentProxy = PendingIntent.getBroadcast(context, (int)id, proxy, 0);
		locManager.removeProximityAlert(pentingIntentProxy);
	}
	
	
	
}
