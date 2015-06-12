package com.ibm.cic.woka.heartsimulator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cic.woka.heartsimulator.application.ApplicationMonitor;
import com.ibm.cic.woka.heartsimulator.device.DeviceIndicators;


public class HeartRateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_rate);
        updateHeartRateTextView();
    }

    public void onDecreaseHRClick(View view) {
        ApplicationMonitor.decreaseHeartRate();
        updateHeartRateTextView();
    }

    public void onIncreaseHRClick(View view) {
        ApplicationMonitor.increaseHeartRate();
        updateHeartRateTextView();
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

    private void updateHeartRateTextView() {
        TextView heartRateTV = (TextView) findViewById(R.id.heart_rate);
        heartRateTV.setText(String.valueOf(DeviceIndicators.getInstance().getHeartRate()));
    }

}
