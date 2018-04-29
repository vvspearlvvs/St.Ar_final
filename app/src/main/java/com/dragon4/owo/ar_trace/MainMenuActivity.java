package com.dragon4.owo.ar_trace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dragon4.owo.ar_trace.ARCore.MixView;

/**
 * Created by YejinChoi on 2017-05-21.
 */


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onMypageButtonClicked(View v){ //마이페이지로
        Intent intent = new Intent(this, MyPageActivity.class);
        Log.d("TAG","mypage 이동되니?");
        if(getIntent().getExtras() != null)
            intent.putExtras(getIntent().getExtras());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onMapButtonClicked(View v){ //지도페이지로
        Intent intent = new Intent(MainMenuActivity.this, MixView.class);
        if(getIntent().getExtras() != null)
            intent.putExtras(getIntent().getExtras());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    public void onReviewButtonClicked(View v){ //리뷰페이지로
        Intent intent = new Intent(MainMenuActivity.this, ReviewMainActivity.class);
        if(getIntent().getExtras() != null)
            intent.putExtras(getIntent().getExtras());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
