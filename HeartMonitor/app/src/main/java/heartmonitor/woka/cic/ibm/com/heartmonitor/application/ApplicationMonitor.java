package heartmonitor.woka.cic.ibm.com.heartmonitor.application;

import android.content.Context;

import heartmonitor.woka.cic.ibm.com.heartmonitor.device.DeviceIndicators;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class ApplicationMonitor {


    public static void activateSimulator(final Context context) {
        DeviceIndicators.getInstance().setActivated(true);
    }

    public static void deactivateSimulator(Context context) {
        DeviceIndicators.getInstance().setActivated(false);
    }

}
