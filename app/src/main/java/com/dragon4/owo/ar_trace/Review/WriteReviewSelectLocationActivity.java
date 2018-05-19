package com.dragon4.owo.ar_trace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dragon4.owo.ar_trace.Configure.ClientInstance;
import com.dragon4.owo.ar_trace.Model.User;

import java.util.ArrayList;

/**
 * Created by 예린 on 2017-06-05.
 */

public class WriteReviewSelectLocationActivity extends AppCompatActivity {

    ArrayList<ReviewLocationItem> resultLocationList = new ArrayList<>();   //후기작성리스트


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write1);

        ListView reviewLocationListView = (ListView) findViewById(R.id.write_review_location_listview);
        ReviewLocationListAdapter adapter = new ReviewLocationListAdapter(getLayoutInflater(), this.getApplicationContext());
        reviewLocationListView.setAdapter(adapter);
        addDataToSearchList(adapter);

    }

    /* 후기작성 리스트 불러오기 */
    public void addDataToSearchList(ReviewLocationListAdapter adapter) {
        // 서버에 후기작성 리스트 데이터 요청 후 리턴 데이터로 리스트 생성

        //py_mypage_slistitem = ClientInstance.getInstanceClient().getMyPageSlistFromServer(User.getMyInstance().getUserEmail(), py_mypage_slistitem);
        resultLocationList = ClientInstance.getInstanceClient().getReviewLocationListFromServer(User.getMyInstance().getUserEmail());

        adapter.setLocationList(resultLocationList);

    }

    /* 후기작성 리스트 관리 */
    class ReviewLocationListAdapter extends BaseAdapter {
        Context context;
        ArrayList<ReviewLocationItem> locationList;
        LayoutInflater inf;

        ReviewLocationListAdapter(LayoutInflater inf, Context c) {
            this.inf = inf;
            this.context = c;
            locationList = new ArrayList<ReviewLocationItem>();
        }

        @Override
        public int getCount() {
            return locationList.size();
        }

        @Override
        public Object getItem(int position) {
            return locationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        } //id특정하게 만들어서 리턴해도 된다

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = inf.inflate(R.layout.activity_review_write1_item, null);

            TextView visitDateTV = (TextView) view.findViewById(R.id.write_review_list_visitDate);
            TextView placeNameTV = (TextView) view.findViewById(R.id.write_review_list_placeName);
            Button writeBtn = (Button) view.findViewById(R.id.write_review_list_writebtn);

            final ReviewLocationItem currentData = locationList.get(position);

            visitDateTV.setText(currentData.getVisitDate());
            placeNameTV.setText(currentData.getPlaceName());
            writeBtn.setId(position);
            writeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onWriteBtn(view);
                }
            });

            return view;
        }

        public void setLocationList(ArrayList<ReviewLocationItem> locationList) {
            this.locationList = locationList;
        }
    }

    /* "작성" 버튼 눌렀을 경우 */
    public void onWriteBtn(View view) {
        int position = view.getId();

        //Log.d("여기야ㅑㅑㅑㅑㅑㅑㅑㅑㅑ ", ""+position);
        Intent intent = new Intent(WriteReviewSelectLocationActivity.this, WriteReviewActivity.class);
        intent.putExtra("placeName", resultLocationList.get(position).getPlaceName());
        intent.putExtra("locationCode", resultLocationList.get(position).getLocationCode());
        startActivity(intent);
    }
}
