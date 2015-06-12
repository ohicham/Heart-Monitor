package com.ibm.cic.woka.heartmonitor.device;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class DeviceIndicators {

    private static DeviceIndicators instance;

    private boolean activated;

    private DeviceIndicators() {
        activated = false;
    }

    public static DeviceIndicators getInstance() {
        if(instance == null) {
            instance = new DeviceIndicators();
        }

        return instance;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
