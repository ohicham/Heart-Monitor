package com.ibm.cic.woka.heartsimulator.application;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class ApplicationControl {

    private static ApplicationControl instance;

    private String deviceId;
    private String organizationId;
    private String authToken;
    private String deviceType;

    private ApplicationControl() {
        deviceId = "ccfa00cb7c4b";
        organizationId = "7tzvjp";
        authToken = "UysZk7wKKWi(9GMbK!";
        deviceType = "Android";
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
}
