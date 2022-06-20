package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;
    private Sensor accelerometer;
    private TextView text;

    private float[] gravity = new float[3];
    private float[] motion = new float[3];

    private double ration;
    private double mAngle;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        text = findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onPause() {
        manager.unregisterListener(this, accelerometer);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        for (int i = 0; i < 3; i++) {
            gravity[i] = (float) (0.1 * sensorEvent.values[i] + 0.9 * gravity[i]);
            motion[i] = sensorEvent.values[i] - gravity[i];
        }

        ration = gravity[1] / SensorManager.GRAVITY_EARTH;

        if (ration > 1.0) ration = 1.0;
        if (ration < -1.0) ration = -1.0;

        mAngle = Math.toDegrees(Math.acos(ration));

        if (gravity[2] < 0)
            mAngle = -mAngle;

        if (counter++ % 10 == 0) {
            @SuppressLint("DefaultLocale")
            String message = String.format("Unprocessed values:\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
                            "Gravity var:\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
                            "Movement var:\nX: %8.4f\nY: %8.4f\nZ: %8.4f\nAngle: %8.1f",
                    sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2],
                    gravity[0], gravity[1], gravity[2],
                    motion[0], motion[1], motion[2],
                    mAngle);

            text.setText(message);
            text.invalidate();
            counter = 1;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // ignore
    }
}