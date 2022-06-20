package com.example.allsensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.textView);
        SensorManager manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder message = new StringBuilder(2048);
        message.append("Sensors found in device:\n");

        for (Sensor sensor : sensors) {
            message.append(sensor.getName() + "\n");
            message.append("Type: " + sensorTypes.get(sensor.getType()) + "\n");
            message.append("Manufacture: " + sensor.getVendor() + "\n");
            message.append("Version: " + sensor.getVersion() + "\n");
            message.append("Resolution: " + sensor.getResolution() + "\n");
            message.append("Maximum range: " + sensor.getMaximumRange() + "\n");
            message.append("Power: " + sensor.getPower() + "mA\n");
            message.append("--------------------------------\n");
        }

        text.setText(message);
    }

    private HashMap<Integer, String> sensorTypes = new HashMap<>();

    {
        sensorTypes.put(Sensor.TYPE_ACCELEROMETER, "TYPE_ACCELEROMETER");
        sensorTypes.put(Sensor.TYPE_GYROSCOPE, "TYPE_GYROSCOPE");
        sensorTypes.put(Sensor.TYPE_LIGHT, "TYPE_LIGHT");
        sensorTypes.put(Sensor.TYPE_MAGNETIC_FIELD, "TYPE_MAGNETIC_FIELD");
        sensorTypes.put(Sensor.TYPE_ORIENTATION, "TYPE_ORIENTATION");
        sensorTypes.put(Sensor.TYPE_PRESSURE, "TYPE_PRESSURE");
        sensorTypes.put(Sensor.TYPE_PROXIMITY, "TYPE_DISTANCE");
        sensorTypes.put(Sensor.TYPE_TEMPERATURE, "TYPE_TEMPERATURE");
        sensorTypes.put(Sensor.TYPE_GRAVITY, "TYPE_GRAVITY");
        sensorTypes.put(Sensor.TYPE_LINEAR_ACCELERATION, "TYPE_LINEAR_ACCELERATION");
        sensorTypes.put(Sensor.TYPE_ROTATION_VECTOR, "TYPE_ROTATION_VECTOR");
    }
}