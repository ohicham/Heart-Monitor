package com.ibm.cic.woka.heartmonitor.application;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.push.IBMPush;
import com.ibm.mobile.services.push.IBMPushNotificationListener;
import com.ibm.mobile.services.push.IBMSimplePushNotification;

import com.ibm.cic.woka.heartmonitor.device.DeviceIndicators;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class ApplicationMonitor {

    public static final String TAG = ApplicationMonitor.class.getName();

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


    private static IBMPush push = null;
    private static IBMPushNotificationListener notificationListener = null;

    public static void initialize(final Context context) {
        IBMBluemix.initialize(context, "4a3b5364-4186-46e7-83ab-450ec54ce91f", "96479983bc48cb282888f259bdeeb7882768a617", "iot-heart-monitor.mybluemix.net");
        push = IBMPush.initializeService();
        String deviceId = getMacAddressAsKey(context);
        push.register(deviceId, deviceId).continueWith(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                if(task.isFaulted()) {
                    Log.i(TAG, "Error : " + task.getError().getMessage());
                } else {
                    Log.i(TAG, "Device id : " + task.getResult());
                }

                return null;
            }
        });

        notificationListener = new IBMPushNotificationListener() {

            @Override
            public void onReceive(final IBMSimplePushNotification message) {
                showSimplePushMessage(message);
            }

        };

    }

    private static void showSimplePushMessage(final IBMSimplePushNotification message) {
        Log.i(TAG, message.toString());
    }

    public static void activateSimulator(final Context context) {
        DeviceIndicators.getInstance().setActivated(true);

    }

    public static void deactivateSimulator(Context context) {
        DeviceIndicators.getInstance().setActivated(false);
    }

    public static void resumeListening() {
        if (push != null) {
            push.listen(notificationListener);
        }
    }

    public static void holdListening() {
        if (push != null) {
            push.hold();
        }
    }
}
