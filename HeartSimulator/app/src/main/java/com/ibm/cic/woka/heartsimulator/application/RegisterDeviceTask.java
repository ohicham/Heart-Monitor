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
public class RegisterDeviceTask extends AsyncTask<String, Void, String> {

    Context context;

    public RegisterDeviceTask(Context context) {
        this.context = context;
    }

    protected String doInBackground(String... urls) {
        try {

            String apiURL = ApplicationControl.getInstance().getAPIUrl() + "/devices";
            String type = "Android";
            String id = ApplicationMonitor.getMacAddressAsKey(context);

            Map<String,String> map = new HashMap<>();
            map.put("type", type);
            map.put("id", id);

            return new RestUtil().post(apiURL, map);

        } catch (Exception e) {
            return "FAIL";
        }

    }

    protected void onPostExecute(String result) {
        if(result.equals("FAIL") || result.isEmpty()) {
            Toast.makeText(context, "Failed to register device", Toast.LENGTH_LONG).show();
            return;
        }

        String password = "";
        String id = "";
        try {
            JSONObject jsonResult = new JSONObject(result);
            password = jsonResult.getString("password");
            id = jsonResult.getString("id");
        } catch (JSONException e) {
            Toast.makeText(context, "Failed to process response", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences authKeyPref = context.getSharedPreferences(context.getString(R.string.device_pref_file), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = authKeyPref.edit();

        editor.putBoolean(context.getString(R.string.is_registered), true);
        editor.putString(context.getString(R.string.device_id), id);
        editor.putString(context.getString(R.string.auth_token), password);

        editor.commit();

        ApplicationControl.getInstance().initialize(context);

        Toast.makeText(context, "Device registered successfully, press again to activate", Toast.LENGTH_LONG).show();

    }
}
