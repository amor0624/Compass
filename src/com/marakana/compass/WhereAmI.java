package com.marakana.compass;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WhereAmI extends Activity implements LocationListener {
	LocationManager locationManager;
	Geocoder geocoder;
	TextView textOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		textOut = (TextView) findViewById(R.id.textOut);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		geocoder = new Geocoder(this);

		// Initialize with the last known location
		Location lastLocation = locationManager
		        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (lastLocation != null)
			onLocationChanged(lastLocation);
	}

	@Override
	protected void onPause() {
		super.onPause();

		locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		        1000, 10, this);
	}

	// Called when location has changed
	@Override
	public void onLocationChanged(Location loc) {
		String text = String.format(
		        "Lat:\t %f\nLong:\t %f\nAlt:\t %f\nBearing:\t %f",
		        loc.getLatitude(), loc.getLongitude(), loc.getAltitude(),
		        loc.getBearing());
		textOut.setText(text);
		
		//Perform geocoding for this location
		try {
	        List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 10);
	        for (Address address: addresses	) {
	        	textOut.append("\n" + address.getAddressLine(0));
	        }
        } catch (IOException e) {
	        Log.e("WhereAmI", "Couldn't get Geocoder data", e);
        }
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}
