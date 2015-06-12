package com.ibm.cic.woka.heartmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ibm.cic.woka.heartmonitor.application.ApplicationMonitor;
import com.ibm.cic.woka.heartmonitor.device.DeviceIndicators;

public class MonitorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        ApplicationMonitor.initialize(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationMonitor.resumeListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ApplicationMonitor.holdListening();
    }

}
