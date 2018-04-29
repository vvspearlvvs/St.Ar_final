package com.dragon4.owo.ar_trace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dragon4.owo.ar_trace.Configure.ClientInstance;
import com.dragon4.owo.ar_trace.FCM.FCMWebServerConnector;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(com.dragon4.owo.ar_trace.R.layout.activity_main);

        // Client 설정 - Firebase
       // ClientInstance.setInstanceClient("FIREBASE");
        // Client 설정 - Python
        ClientInstance.setInstanceClient("PYTHON");

        // 푸쉬 서버 설정
        FCMWebServerConnector.setPushServerURL("http://" + "YOUR_PUSH_SERVER_IP" + "/PushServer.php");

        // 네이버 키 설정도 해야됨...!

        //Intent SignInIntent = new Intent(MainActivity.this,GoogleSignActivity.class);

        Intent SignInIntent = new Intent(MainActivity.this, InitActivity.class);
        startActivity(SignInIntent);
    }

}
