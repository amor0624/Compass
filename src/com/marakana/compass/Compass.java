package com.marakana.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class Compass extends Activity implements SensorEventListener {
	SensorManager sensorManager;
	Sensor sensor;
	Rose rose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Set full screen view
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Create new instance of custom Rose and set it on the screen
		rose = new Rose(this);
		setContentView(rose);
		
		//Get sensor and sensor manager
		//Accessing the sensor is a one-time activity, we do it when out app is created.
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		Log.d("Compass", "onCreated");
	}

	@Override
    protected void onPause() {
	    super.onPause();
	    sensorManager.unregisterListener(this);
    }

	@Override
    protected void onResume() {
	    super.onResume();
	    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

	//Ignore accuracy changes
	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

	//Listen to sensor and provide output
	@Override
    public void onSensorChanged(SensorEvent event) {
	    int orientation = (int) event.values[0];
	    Log.d("Compass", "Got sensor event: "+ event.values[0]);
	    rose.setDirection(orientation);
    }

}
