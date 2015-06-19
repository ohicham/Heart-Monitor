package com.ibm.cic.woka.heartsimulator.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ibm.cic.woka.heartsimulator.R;
import com.ibm.cic.woka.heartsimulator.rest.RestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 19-06-2015.
 */
public class DeleteDeviceTask extends AsyncTask<String, Void, Void> {

    Context context;

    public DeleteDeviceTask(Context context) {
        this.context = context;
    }

    protected Void doInBackground(String... urls) {
        try {

            String apiURL = ApplicationControl.getInstance().getAPIUrl() + "/devices/Android/" + ApplicationMonitor.getMacAddressAsKey(context);

            new RestUtil().delete(apiURL);

            return null;

        } catch (Exception e) {
            return null;
        }

    }
}
