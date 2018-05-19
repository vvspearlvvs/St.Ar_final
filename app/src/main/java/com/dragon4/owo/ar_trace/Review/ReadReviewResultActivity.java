package com.dragon4.owo.ar_trace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon4.owo.ar_trace.Configure.ClientInstance;
import com.dragon4.owo.ar_trace.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 예린 on 2017-06-04.
 */

public class ReadReviewResultActivity extends AppCompatActivity {

    GridView reviewListGridView;
    List<ReviewItem> reviewItemList = new ArrayList<>();
    String locationCode;
    String placeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_search_result);

        locationCode = getIntent().getExtras().getString("locationCode");
        placeName = getIntent().getExtras().getString("placeName");

        ReviewItem currentReview = ClientInstance.getInstanceClient().getStatsFromServer(User.getMyInstance().getUserEmail(), locationCode);

        TextView placeNameTV = (TextView) findViewById(R.id.review_search_result_placeName);
        TextView locationCodeTV = (TextView) findViewById(R.id.review_search_result_locationCode);
        RatingBar avgRating = (RatingBar) findViewById(R.id.review_search_result_ratingBar);
        TextView ratingTV = (TextView) findViewById(R.id.review_search_result_ratingScore);
        TextView reviewCnt = (TextView) findViewById(R.id.review_search_result_review_count);

        placeNameTV.setText(placeName);
        locationCodeTV.setText(locationCode);
        avgRating.setRating(currentReview.getRating());
        ratingTV.setText(currentReview.getRating()+"");
        reviewCnt.setText("리뷰 " + currentReview.getCommentCnt() + "개");

        reviewListGridView = (GridView) findViewById(R.id.review_search_listview);
        ReviewSearchAdapter adapter = new ReviewSearchAdapter(getLayoutInflater());
        reviewListGridView.setAdapter(adapter);
        //adapter.setReviewList(reviewItemList);
        // 서버에서 장소별 리뷰 받아오기
        getReviews(adapter);

    }

    public void getReviews(ReviewSearchAdapter adapter) {
        reviewItemList = ClientInstance.getInstanceClient().getReviewsFromServer(User.getMyInstance().getUserEmail(), locationCode);
        if(reviewItemList == null) {
            Toast.makeText(this, "해당 장소에 대한 리뷰가 존재하지 않습니다!", Toast.LENGTH_LONG);
        } else {
            adapter.setReviewList(reviewItemList);
        }
    }

    public void onBackBtnClicked(View view) {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
    }

}