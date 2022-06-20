package com.example.lightsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager manager;
    private Sensor light;
    private TextView text;
    private StringBuilder message = new StringBuilder(2048);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        light = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        text = findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        manager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        manager.unregisterListener(this, light);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        message.insert(0, "Getting info from sensor " + sensorEvent.values[0] + " lux\n");
        text.setText(message);
        text.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        message.insert(0, sensor.getName() + " Accuracy changed: " + accuracy
                + (accuracy == 1 ? "(LOW)" : (accuracy == 2 ? "(MIDDLE)" : " (HIGH)")) + '\n');
        text.setText(message);
        text.invalidate();
    }
}