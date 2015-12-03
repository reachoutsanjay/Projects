package com.qburst.getalert;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GetMapForMark extends FragmentActivity implements LocationListener,OnClickListener{
	
	
	 private GoogleMap MAP;  
     private boolean markClick;  
     private PolylineOptions rectoptions;  
     private Polyline poliline;  

     ProgressDialog dialog;
     
     double currentLatitude;
     double currentLongitude;
     double markLatitude;
     double markLongitude;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_map_for_mark);
		
		initializeViews();
		
		SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

		MAP = fm.getMap();  
        MAP.setMyLocationEnabled(true);  
        MAP.setMapType(GoogleMap.MAP_TYPE_NORMAL);  
        
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria,true);
		Location location = locationManager.getLastKnownLocation(provider);
		
		if(location!=null){	
			onLocationChanged(location);
		}
		
		locationManager.requestLocationUpdates(provider, 0, 2000, this);
        
        MAP.setOnMapClickListener(new OnMapClickListener() {
			
		
			public void onMapClick(LatLng point) {

				 
                     // TODO Auto-generated method stub  
                     MAP.addMarker(new MarkerOptions().position(point).title(  
                               point.toString()));  
                   
                     markLatitude = point.latitude;
                     markLongitude = point.longitude;

			}
		});
        
       
        MAP.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				 if (markClick) {  
                     if (poliline != null) {  
                          poliline.remove();  
                          poliline = null;  
                     }  
                     rectoptions.add(marker.getPosition());  
                     rectoptions.color(Color.BLUE);  
                     poliline = MAP.addPolyline(rectoptions);  
                } else {  
                     if (poliline != null) {  
                          poliline.remove();  
                          poliline = null;  
                     }  
                     rectoptions = new PolylineOptions().add(marker.getPosition());
                                 
                     markClick = true;  
                }  
                return false;  
           }  
				
			
		});
        
        markClick = false; 

	}

	
private void initializeViews() {
	
		
		Button ok = (Button) findViewById(R.id.okMap);
		ok.setOnClickListener(this);
		
		Button cancel = (Button) findViewById(R.id.cancelMap);
		cancel.setOnClickListener(this);
	
		
	}

	
     public void onLocationChanged(Location location) {
		
    	 TextView tvLocation = (TextView) findViewById(R.id.tv_location);
 		
 		 currentLatitude = location.getLatitude();
 		 currentLongitude = location.getLongitude();
 		
 	    LatLng latlng = new LatLng(currentLatitude, currentLongitude);
 	    
 	    MAP.moveCamera(CameraUpdateFactory.newLatLng(latlng));
 	    MAP.animateCamera(CameraUpdateFactory.zoomTo(10));
 	    MAP.setMapType(GoogleMap.MAP_TYPE_NORMAL);
 	    
 	    Toast.makeText(GetMapForMark.this, "coordinates =" + currentLatitude + currentLongitude, Toast.LENGTH_SHORT).show();
 	    
 	    tvLocation.setText("Latitude:" +  currentLatitude  + ", Longitude:"+ currentLongitude );
	}

	

	


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	


	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.okMap){
			
			Intent data = new Intent();
			Bundle b = new Bundle();
			
			
			b.putDouble("c-lat", currentLatitude);
			Log.d("tag" , ""+currentLatitude);
			b.putDouble("c-lon", currentLongitude);
			Log.d("tag" , ""+currentLongitude);
			
			b.putDouble("lat",markLatitude);
			Log.d("tag" , ""+markLatitude);
			b.putDouble("log", markLongitude);
			Log.d("tag" , ""+markLongitude);
			
			
			data.putExtras(b);
			setResult(Activity.RESULT_OK, data);
			finish();
	
			
		}
		else if(v.getId() == R.id.cancelMap){
			Intent cancel = new Intent(GetMapForMark.this, AddAlarm.class);
	        startActivity(cancel);
			
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_map_for_mark, menu);
		return true;
	}
}
