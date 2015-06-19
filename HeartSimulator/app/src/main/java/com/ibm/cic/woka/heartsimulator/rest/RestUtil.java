package com.ibm.cic.woka.heartsimulator.rest;

import android.util.Base64;

import com.ibm.cic.woka.heartsimulator.application.ApplicationControl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 19-06-2015.
 */
public class RestUtil {

    public String post(String url, Map<String, String> params) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        appendPostParams(post, params);

        appendHttpHeader(post);

        return executeHttpRequest(post, client);

    }

    public String delete(String url) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(url);

        appendHttpHeader(delete);

        return executeHttpRequest(delete, client);

    }

    public String get(String url, Map<String, String> params) throws IOException{

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        appendHttpHeader(get);

        return executeHttpRequest(get, client);

    }

    private String executeHttpRequest(HttpUriRequest request, HttpClient client) throws IOException {
        HttpResponse httpResponse =  client.execute(request);

        if(httpResponse.getStatusLine().getStatusCode() < 200 || httpResponse.getStatusLine().getStatusCode() > 299) {
            return "FAIL";
        }

        return EntityUtils.toString(httpResponse.getEntity());

    }

    private void appendPostParams(HttpPost post, Map<String, String> params) throws UnsupportedEncodingException {
        HttpEntity entity = null;

        List<NameValuePair> nameValuePairs = new ArrayList<>(params.size());

        Iterator<String> itr = params.keySet().iterator();

        while(itr.hasNext()) {
            String key = itr.next();
            nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
        }

        String strParams = new JSONObject(params).toString();

        entity = new StringEntity(strParams);


        post.setEntity(entity);
    }

    private void appendHttpHeader(AbstractHttpMessage message) {

        message.addHeader("authorization", "Basic " + Base64.encodeToString((ApplicationControl.getInstance().getApiKey() + ":" + ApplicationControl.getInstance().getApiToken()).getBytes(), Base64.NO_WRAP));

        message.addHeader("accept", "application/json");
        message.addHeader("Content-Type", "application/json");

    }
}
