package com.ibm.cic.woka.heartsimulator.mqtt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ibm.cic.woka.heartsimulator.application.ApplicationControl;
import com.ibm.cic.woka.heartsimulator.device.DeviceIndicators;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class MQTTHandler {

    private static MQTTHandler instance;
    private static MqttClient client;

    public static MQTTHandler getInstance() {
        if(instance == null) {
            instance = new MQTTHandler();
        }
        return instance;
    }

    private MQTTHandler() {

    }

    public MqttClient getClient(final Context context) {
        return client;
    }

    public MqttClient createClient(final Context context) throws MqttException {
        String deviceUrl  = ApplicationControl.getInstance().getDeviceUrl();
        String serverUrl  = ApplicationControl.getInstance().getServerUrl();

        client = new MqttClient(serverUrl, deviceUrl, null);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                Log.d("listener", "Connection lost");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                Log.d("listener", "Message arrived");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.d("listener", "Delivery complete");
            }
        });

        return client;
    }

    public void sendMessage(String topic, String message) throws MqttException {
        MqttMessage mqttMsg = new MqttMessage(message.getBytes());
        client.publish(topic, mqttMsg);
    }
}
