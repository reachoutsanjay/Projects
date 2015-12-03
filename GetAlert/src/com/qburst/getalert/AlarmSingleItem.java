package com.qburst.getalert;

import java.util.ArrayList;

import com.google.android.gms.internal.al;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlarmSingleItem extends LinearLayout {

	public AlarmSingleItem(Context context, AlarmSetterDB alarmSetterDB) {
		super(context);
		
		LayoutInflater MyInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		MyInflator.inflate(R.layout.alarm_item, this, true);
		
		TextView Name;
		
	    Name = (TextView)findViewById(R.id.tvAlarmName);
		
		Name.setText(alarmSetterDB.alarmName);
		
		
		
	}

	

}
