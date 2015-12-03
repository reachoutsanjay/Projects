package com.qburst.getalert;




import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class ProximityReceiver {
	
	String TAG = "TTMS LOG : ";

	Intent i;

	LocationManager locmanager;

	double currentLat, currentLog;
	
	
	public void onReceive(Context context, Intent intent) {
		
		boolean isEntered = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
		
		String temp2 = intent.getAction();
		
		Log.i("", TAG + temp2 + " :Proximity : " + isEntered);
		
		if (!isEntered) {
			
			String temp1 = (temp2.replace("com.qburst.getalert.Receiver", "")).trim();
			long _id = Long.parseLong(temp1);
			
			
			AlarmFunctions.cancelThisAlarm(_id, context);
			Log.i("", TAG + _id + " alarm canceled");
			
			locmanager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
			
			Location location = locmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			currentLat = location.getLatitude();
			currentLog = location.getLongitude();
			
			Log.i("", TAG + "Updated location : " + currentLat + ", " + currentLog);
			
			DataBaseFunctions dbFunction = DataBaseFunctions.getSharedDataBaseFunuctionClass(context);
			AlarmSetterDB alarmSetterDB = dbFunction.view(_id);
//			
//			String temp[] = CommonFunctions.getDistance(current_lat, current_log, asd.latest_lat, asd.latest_log);
//			asd.distance = temp[0];
//			asd.milis = CommonFunctions.convertTimeToMillis(temp[1]);
			
			
			alarmSetterDB.currentLatitude = currentLat;
			alarmSetterDB.currentLongitude = currentLog;
			
			dbFunction.update(alarmSetterDB);
			
			AlarmFunctions.setAnAlarm(alarmSetterDB._id, context, alarmSetterDB);
			
			Log.i("", TAG + "Proximity updated.");
		}
	}

}
