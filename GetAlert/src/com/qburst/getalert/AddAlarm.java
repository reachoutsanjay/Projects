package com.qburst.getalert;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddAlarm extends Activity implements OnClickListener{
	
    EditText editAlarmName;
    EditText editAlarmDescription;
	
	AlarmSetterDB currentAlarm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alarm);
		initializeViews();
	}

	private void initializeViews() {

		Button map = (Button) findViewById(R.id.map);
		map.setOnClickListener(this);
		
		Button save = (Button) findViewById(R.id.ok);
		save.setOnClickListener(this);
		
		Button cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		
		 editAlarmName = (EditText) findViewById(R.id.alarm_name);
		 editAlarmName.setOnClickListener(this);
		
		 editAlarmDescription = (EditText) findViewById(R.id.alarm_description);
		 editAlarmDescription.setOnClickListener(this);

		
        currentAlarm = (AlarmSetterDB)getIntent().getSerializableExtra("currentAlarm");
		
		if(currentAlarm != null){
			
			editAlarmName.setText(currentAlarm.alarmName);
			editAlarmDescription.setText(currentAlarm.alarmDescription);
			
		}else{
			currentAlarm = new AlarmSetterDB();
		}
		
		
		
	}


	@Override
	public void onClick(View screenView) {
		
		switch (screenView.getId()) {
		
		
		case R.id.map:Intent getMap = new Intent(AddAlarm.this, GetMapForMark.class);
                      startActivityForResult(getMap, 123);
                      break;
                      
		case R.id.ok: AlarmFunctions.setAnAlarm(insertOrUpdateId(), this, currentAlarm);
		              setResult(RESULT_OK);
		              finish();
		              break;	
                      
		case R.id.cancel:finish();break;
//			             Intent back = new Intent(AddAlarm.this, GetAlertMainActivity.class);
//                         startActivity(back);
//                         break;

		
		}
		
	}	
		
	
	private long insertOrUpdateId() {
		
		long ThisId;
		DataBaseFunctions dbBaseFunctions = DataBaseFunctions.getSharedDataBaseFunuctionClass(this);
		
		if (currentAlarm._id == -1) {
			
			currentAlarm.alarmName = editAlarmName.getText().toString();
			currentAlarm.alarmDescription = editAlarmDescription.getText().toString();
			ThisId = dbBaseFunctions.insert(currentAlarm);
			
		} else {
			
			currentAlarm.alarmName = editAlarmName.getText().toString();
			currentAlarm.alarmDescription = editAlarmDescription.getText().toString();
			ThisId = dbBaseFunctions.update(currentAlarm);

		}
		return ThisId;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 123) {
			if (resultCode == Activity.RESULT_OK) {
				
				Bundle b = data.getExtras();
				
				
				currentAlarm.markLatitude= b.getDouble("lat");
				
				Log.d("tag" , ""+currentAlarm.markLatitude);

				currentAlarm.markLongitude = b.getDouble("log");
				
				Log.d("tag" , ""+currentAlarm.markLongitude);
				currentAlarm.currentLatitude = b.getDouble("c-lat");
				Log.d("tag" , ""+currentAlarm.currentLatitude);
				currentAlarm.currentLongitude = b.getDouble("c-lon");
				Log.d("tag" , ""+currentAlarm.currentLongitude);
				
			}
		}
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_alarm, menu);
		return true;
	}

	

}
