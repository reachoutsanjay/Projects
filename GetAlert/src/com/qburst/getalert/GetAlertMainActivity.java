package com.qburst.getalert;




import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;


public class GetAlertMainActivity extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {
	
    ListView alarmList;
	
	ArrayList<AlarmSetterDB> alarmDetails;
	
	AlarmListAdapter adapter;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_alert_main);
		
		Button addAlarm = (Button) findViewById(R.id.add_alarm);
		addAlarm.setOnClickListener(this);
		
		alarmList = (ListView) findViewById(R.id.listView_alarms);
     	
     	alarmList.setAdapter(adapter);
     	alarmList.setOnItemClickListener(this);
     	alarmList.setOnItemLongClickListener(this);
     	
     	alarmDetails = new ArrayList<AlarmSetterDB>();
     	
     	populateAllAlarms();
		
	}

   

	private void populateAllAlarms() {
		
		DataBaseFunctions dataBaseFunctionObj = DataBaseFunctions.getSharedDataBaseFunuctionClass(getApplicationContext());
		
		alarmDetails = dataBaseFunctionObj.viewAll();
		
		adapter = new AlarmListAdapter(this, alarmDetails);
		alarmList.setAdapter(adapter);
	
	}



	@Override
	public void onClick(View v) {
		
		Intent alert = new Intent(GetAlertMainActivity.this, AddAlarm.class);
        startActivity(alert);
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		
		Intent showAlarm = new Intent(this, AddAlarm.class);
		showAlarm.putExtra ("current-alarm", alarmDetails.get(position));
		startActivity(showAlarm);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_alert_base, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		populateAllAlarms();
	}



	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		
		final int thisposition = position;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				DataBaseFunctions dbFunction = new DataBaseFunctions(GetAlertMainActivity.this);
				AlarmFunctions.cancelThisAlarm(alarmDetails.get(thisposition)._id, GetAlertMainActivity.this);
				dbFunction.delete(alarmDetails.get(thisposition));
				populateAllAlarms();
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		return true;
	}


}
