package com.ibm.cic.woka.heartsimulator.application;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.ibm.cic.woka.heartsimulator.device.DeviceIndicators;
import com.ibm.cic.woka.heartsimulator.mqtt.MQTTHandler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class ApplicationMonitor {

    public static void initializeDevice(Context context) {
        ApplicationControl.getInstance().initialize(context);
    }

    public static String getMacAddress(final Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getMacAddressAsKey(final Context context) {
        String macAddress = getMacAddress(context);
        macAddress = macAddress.replace(":" , "");
        return macAddress;
    }

    public static void increaseHeartRate() {
        int heartRate = DeviceIndicators.getInstance().getHeartRate();
        heartRate ++;
        DeviceIndicators.getInstance().setHeartRate(heartRate);
    }

    public static void decreaseHeartRate() {
        int heartRate = DeviceIndicators.getInstance().getHeartRate();
        heartRate --;

        if(heartRate < 0) {
            return;
        }

        DeviceIndicators.getInstance().setHeartRate(heartRate);
    }

    public static void activateSimulator(final Context context) {
        try {

            if(!ApplicationControl.getInstance().isDeviceRegistered(context)) {
                Toast.makeText(context, "Registering your device", Toast.LENGTH_SHORT).show();
                ApplicationControl.getInstance().registerDevice(context);
                return;
            }

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            options.setUserName("use-token-auth");
            options.setPassword(ApplicationControl.getInstance().getAuthToken().toCharArray());

            MQTTHandler.getInstance().createClient(context).connect(options);

            // start sending heart rate
            timer = new Timer();
            timer.schedule(new SendTimerTask(context), 0, 5000);

            DeviceIndicators.getInstance().setActivated(true);
        } catch(MqttException e) {
            if (e.getReasonCode() == 3) {
                // error while connecting to the broker
            }
            Toast.makeText(context, "Failed to connect: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void deactivateSimulator(Context context) {
        if(!MQTTHandler.getInstance().getClient(context).isConnected()) {
            return;
        }
        try {
            MQTTHandler.getInstance().getClient(context).disconnect();
            if(timer != null) {
                timer.cancel();
            }
            DeviceIndicators.getInstance().setActivated(false);
        } catch(MqttException e) {
            if (e.getReasonCode() == 3) {
                // error while connecting to the broker
            }
            Toast.makeText(context, "Failed to connect: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static Timer timer;


    private static class SendTimerTask extends TimerTask {

        private Context context;

        public SendTimerTask(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            if(!MQTTHandler.getInstance().getClient(context).isConnected()) {
                Log.e("Sensor", "Client is not connected");
                return;
            }
            String topic = "iot-2/evt/heartrate/fmt/json";
            String message =  "{\"d\": " +
                        "{" +
                            "\"heart_rate\" : " + DeviceIndicators.getInstance().getHeartRate() +
                        "}" +
                    "}";

            try {
                MQTTHandler.getInstance().sendMessage(topic, message);
            } catch(MqttException ex) {
                Log.e("Sensor", "Could not send heart rate: " + ex.getMessage());
            }

        }
    }
}
