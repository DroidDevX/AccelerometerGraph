package com.example.accelerometergraph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";

    TextView xTextView;
    TextView yTextView;
    TextView zTextView;


    private SensorManager sensorManager;
    private Sensor sensor;
    AccelerometerGraph graph;
    boolean accelerometerSupported =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xTextView = findViewById(R.id.xTV);
        yTextView = findViewById(R.id.yTV);
        zTextView = findViewById(R.id.zTV);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSupported =true;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            graph = new MPAcceleromterGraph();
            transaction.replace(R.id.chartContainer,graph);
            transaction.commit();
        }
        else
            Log.e(TAG, "Accelerometer sensor does not exist");


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        xTextView.setText(String.format(Locale.ENGLISH,"X: %.01f",sensorEvent.values[0]));
        yTextView.setText(String.format(Locale.ENGLISH,"Y: %.01f",sensorEvent.values[1]));
        zTextView.setText(String.format(Locale.ENGLISH,"Z: %.01f",sensorEvent.values[2]));
        graph.drawData(new AccelerometerGraph.Cords(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometerSupported){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(sensor!=null)
            sensorManager.unregisterListener(this);
    }
}