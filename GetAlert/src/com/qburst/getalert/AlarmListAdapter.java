package com.qburst.getalert;
import java.util.ArrayList;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class AlarmListAdapter extends BaseAdapter{
	
	Context context;
	ArrayList<AlarmSetterDB> allAlarms;
	public AlarmListAdapter(Context context, ArrayList<AlarmSetterDB> alarmDetails) {
		super();
		this.context = context;
		this.allAlarms = alarmDetails;
	}
	@Override
	public int getCount() {
		
		return allAlarms.size();
	}
	@Override
	public Object getItem(int position) {

		return null;
	}
	@Override
	public long getItemId(int position) {

		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return new AlarmSingleItem(context, allAlarms.get(position));
	}
	

}
