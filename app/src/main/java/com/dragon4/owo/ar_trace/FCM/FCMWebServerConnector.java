package com.dragon4.owo.ar_trace.FCM;

import android.util.Log;

import com.dragon4.owo.ar_trace.Model.Trace;
import com.dragon4.owo.ar_trace.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FCMWebServerConnector {
    private Gson gson;
    private static String pushServerURL;

    public FCMWebServerConnector() {
        gson = new GsonBuilder().create();
    }

    public static void setPushServerURL(String pushServerURL) {
        FCMWebServerConnector.pushServerURL = pushServerURL;
    }

    public void sendLikePush(Trace trace) {
        try {

            Log.i("pushServerURL",FCMWebServerConnector.pushServerURL);
            JSONObject obj = new JSONObject();
            obj.put("userToken", trace.getUserToken());
            obj.put("userID", User.getMyInstance().getuserID());
            obj.put("userName", User.getMyInstance().getUserName());
            obj.put("buildingID", trace.getLocationID());
            obj.put("traceID", trace.getTraceID());
            obj.put("title", trace.getPlaceName());

            String response = new FCMHttpHandler().execute(FCMWebServerConnector.pushServerURL,"POST",gson.toJson(obj)).get();
            Log.i("sendLikePush", response);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}