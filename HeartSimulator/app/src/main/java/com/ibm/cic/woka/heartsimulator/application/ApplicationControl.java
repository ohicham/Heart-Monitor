package com.ibm.cic.woka.heartsimulator.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.ibm.cic.woka.heartsimulator.R;
import com.ibm.cic.woka.heartsimulator.rest.RestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class ApplicationControl {

    private static ApplicationControl instance;

    private String deviceId;
    private String organizationId;
    private String authToken;
    private String deviceType;
    private String apiKey;
    private String apiToken;

    private ApplicationControl() {
        organizationId = "7tzvjp";
        deviceType = "Android";
        apiKey = "a-7tzvjp-107egjvidi";
        apiToken = "YdS&wooynHFgxO_cB+";
    }

    public String getDeviceUrl() {
        return "d:" + organizationId + ":" + deviceType + ":" + deviceId;
    }

    public static ApplicationControl getInstance() {
        if (instance == null) {
            instance = new ApplicationControl();
        }

        return instance;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getServerUrl() {
        return "tcp://messaging.internetofthings.ibmcloud.com:1883";
    }

    public String getAPIUrl() {
        return "https://" + ApplicationControl.getInstance().getOrganizationId() + ".internetofthings.ibmcloud.com/api/v0001";
    }

    public void initialize(Context context) {
        if(isDeviceRegistered(context)) {

            SharedPreferences authKeyPref = context.getSharedPreferences(context.getString(R.string.device_pref_file), Context.MODE_PRIVATE);

            authToken = authKeyPref.getString(context.getString(R.string.auth_token), "");
            deviceId = authKeyPref.getString(context.getString(R.string.device_id), "");
        }
    }

    public boolean isDeviceRegistered(Context context) {
        SharedPreferences authKeyPref = context.getSharedPreferences(context.getString(R.string.device_pref_file), Context.MODE_PRIVATE);

        return authKeyPref.getBoolean(context.getString(R.string.is_registered), false);

    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void registerDevice(Context context) {

        // delete device if already exists
        new DeleteDeviceTask(context).execute();

        new RegisterDeviceTask(context).execute();
    }
}
