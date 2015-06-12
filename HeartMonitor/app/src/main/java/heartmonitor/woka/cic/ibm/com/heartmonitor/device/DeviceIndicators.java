package heartmonitor.woka.cic.ibm.com.heartmonitor.device;

/**
 * Created by Mohamed Wagdy on 09-06-2015.
 */
public class DeviceIndicators {

    private static DeviceIndicators instance;

    private int heartRate;

    private boolean activated;

    private DeviceIndicators() {
        heartRate = 80;
        activated = false;
    }

    public static DeviceIndicators getInstance() {
        if(instance == null) {
            instance = new DeviceIndicators();
        }

        return instance;
    }


    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
