package com.dragon4.owo.ar_trace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 예린 on 2017-06-04.
 */

public class ReviewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main);
//        RelativeLayout writeReviewBtn = (RelativeLayout) findViewById(R.id.writeReviewBtn);
//        RelativeLayout readReviewBtn = (RelativeLayout) findViewById(R.id.readReviewBtn);

        LinearLayout writeReviewBtn = (LinearLayout) findViewById(R.id.writeReviewBtn);
        LinearLayout readReviewBtn = (LinearLayout) findViewById(R.id.readReviewBtn);


    }
        /*View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent;

                switch (v.getId()) {

                    // "리뷰 쓰기" 버튼을 눌렀을 때
                    case R.id.writeReviewBtn:
                        intent = new Intent(ReviewMainActivity.this, WriteReviewActivity.class);
                        startActivity(intent);
                        break;

                    // "리뷰 보기" 버튼을 눌렀을 때
                    case R.id.readReviewBtn:
                        intent = new Intent(ReviewMainActivity.this, ReadReviewActivity.class);
                        startActivity(intent);
                        break;




                }
            }
        };*/

    public void onReviewWriteButtonClicked(View view){
        Intent it = new Intent(ReviewMainActivity.this, WriteReviewSelectLocationActivity.class);
        startActivity(it);
        //finish();
    }

    public void onReviewReadButtonClicked(View view){
        Intent it = new Intent(ReviewMainActivity.this,ReadReviewActivity.class);
        startActivity(it);
        //finish();
    }
    public void onHomeButtonClicked(View view){
        Intent it = new Intent(ReviewMainActivity.this,MainMenuActivity.class);
        startActivity(it);
        finish();
    }

}

