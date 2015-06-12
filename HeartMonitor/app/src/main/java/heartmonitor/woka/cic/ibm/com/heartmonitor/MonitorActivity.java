package heartmonitor.woka.cic.ibm.com.heartmonitor;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import heartmonitor.woka.cic.ibm.com.heartmonitor.application.ApplicationMonitor;
import heartmonitor.woka.cic.ibm.com.heartmonitor.device.DeviceIndicators;


public class MonitorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
    }

    public void onToggleMonitoringClick(View view) {
        ImageView activationIV = (ImageView) findViewById(R.id.activateImgBtn);

        if(DeviceIndicators.getInstance().isActivated()) {
            ApplicationMonitor.deactivateSimulator(this);
            activationIV.setImageResource(R.drawable.off);
        } else {
            ApplicationMonitor.activateSimulator(this);
            activationIV.setImageResource(R.drawable.on);
        }
    }

}
