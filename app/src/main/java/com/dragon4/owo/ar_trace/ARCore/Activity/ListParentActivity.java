package com.dragon4.owo.ar_trace.ARCore.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon4.owo.ar_trace.ARCore.Marker.NaverSearchMarker;
import com.dragon4.owo.ar_trace.ARCore.NaverHttpHandler;
import com.dragon4.owo.ar_trace.ARCore.Navigator;
import com.dragon4.owo.ar_trace.ARCore.data.DataSource;
import com.dragon4.owo.ar_trace.Configure.ClientInstance;
import com.dragon4.owo.ar_trace.Model.ReviewListPlace;
import com.dragon4.owo.ar_trace.Model.SearchPlace;
import com.dragon4.owo.ar_trace.Model.User;
import com.dragon4.owo.ar_trace.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListParentActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void runNavi(Double lat, Double lon) {
        Intent naviIntent = new Intent();
        naviIntent.putExtra("lat", lon);
        naviIntent.putExtra("lon", lat);

        if(lat == null || lon == null )
            Toast.makeText(getApplicationContext(), "해당 지역의 좌표를 받아올 수 없습니다", Toast.LENGTH_LONG).show();

        setResult(RESULT_OK, naviIntent);
        finish();

        Navigator navigator = Navigator.getNavigator();
        if (navigator != null) {
            navigator.run(lat, lon);
        } else
            Toast.makeText(getApplicationContext(), "네비게이션 기능을 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "지금부터 안내를 시작합니다!", Toast.LENGTH_LONG).show();
        Log.i("TAG", "startactivity");
        Log.i("TAG", "**************************************************************************88");
       // startActivity(naviIntent);
    }


    protected class SearchListViewAdapter extends BaseAdapter {
        private List<NaverSearchMarker> dataList = new ArrayList<>();
        private String currentText;
        private LayoutInflater inflater;

        public SearchListViewAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.layout_ar_mixview_search_listview_item, null);
            final TextView name = (TextView) view.findViewById(R.id.ar_mixview_search_listview_item_name);
            TextView callNumber = (TextView) view.findViewById(R.id.ar_mixview_search_listview_item_call_number);
            TextView category = (TextView) view.findViewById(R.id.ar_mixview_search_listview_item_category);
            final TextView address = (TextView) view.findViewById(R.id.ar_mixview_search_listview_item_address);
            TextView reviewNumber = (TextView) view.findViewById(R.id.ar_mixview_search_listview_item_review_number);

            final NaverSearchMarker currentData = dataList.get(i);

            // 네비 버튼 클릭시 활성화
            TextView navi = (TextView)view.findViewById(R.id.ar_mixview_search_listview_navi);
            navi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SearchPlace searchPlace = new SearchPlace();
                    final ReviewListPlace reviewListPlace = new ReviewListPlace();
                    String searchPlace_Name = name.getText().toString();
                    Log.d("TAG","searchPlace_Name : "+searchPlace_Name);
                    searchPlace.setUserID(User.getMyInstance().getUserEmail());
                    searchPlace.setPlaceName(searchPlace_Name);
                    //최근 검색 기록에 추가
                    ClientInstance.getInstanceClient().sendsearchToServer(searchPlace);

                    String reviewPlace_Name = name.getText().toString();
                    String reviewPlace_Addr = address.getText().toString();
                    reviewListPlace.setUserID(User.getMyInstance().getUserEmail());
                    reviewListPlace.setPlaceName(reviewPlace_Name);
                    reviewListPlace.setLocationCode(reviewPlace_Addr);

                    //리뷰리스트에 추가
                    ClientInstance.getInstanceClient().sendReviewListToServer(reviewListPlace);

                    String raddr = currentData.getAddress();
                    String[] loc = raddr.split(" ");
                    String realLoc = loc[loc.length-2] + " " + loc[loc.length-1];

                    Log.d("ITEMTAG", "pName : " + realLoc);

                    Log.d("TAG","road address : "+raddr);
                    Double lat = 0.0;
                    Double lon = 0.0;

                    try {
                        String reqURL = DataSource.createNaverGeoAPIRequestURL(realLoc);
                        Log.d("URL??", "rdata : " + reqURL);
                        String rdata = new NaverHttpHandler().execute(reqURL).get();
                        Log.d("ITEMTAG", "rdata : " + rdata);

                        JSONObject root = convertToJSON(rdata);

                        JSONArray dArray = null;
                        dArray = root.getJSONObject("result").getJSONArray("items");

                        JSONObject getObject = null;

                        for (int i = 0; i < dArray.length(); i++) {
                            getObject = dArray.getJSONObject(i);

                            if (getObject.getJSONObject("point") != null) {
                                lon =  getObject.getJSONObject("point").getDouble("x");
                                lat =  getObject.getJSONObject("point").getDouble("y");
                                break;
                            }
                        }
                    } catch (Exception e) {

                    }
                    runNavi(lat,lon);
                }
            });
            navi.setTag(currentData.getAddress());

            
            // 리뷰 버튼 클릭시 활성화.
            LinearLayout reviewText = (LinearLayout)view.findViewById(R.id.ar_mixview_search_listview_item_review);
            reviewText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListParentActivity.this, TraceActivity.class);
                    intent.putExtra("title", currentData.getTitle());
                    intent.putExtra("lat", currentData.getLatitude());
                    intent.putExtra("lon", currentData.getLongitude());
                    startActivity(intent);
                    finish();
                }
            });
            reviewText.setTag(currentData.getAddress());

            // 데이터들 보여주기
            name.setText(currentData.getTitle());
            callNumber.setText(currentData.getTelephone());
            category.setText(currentData.getCategory());
            //ClientSelector clientSelector = new FirebaseClient();
            //clientSelector.getReviewNumberFromServer(currentData.getTitle(), reviewNumber);

            // 신주소 구주소 전환.
            if(currentData.getRoadAddress().length() > 0) {
                address.setText(currentData.getRoadAddress());
            }
            else {
                address.setText(currentData.getAddress());
            }

            address.setTag(address.getText().toString());

            return view;
        }

        public void setDataList(List<NaverSearchMarker> dataList) {
            this.dataList = dataList;
        }

        public String getCurrentText() {
            return currentText;
        }

        public void setCurrentText(String currentText) {
            this.currentText = currentText;
        }

        private JSONObject convertToJSON(String rawData){
            try {
                return new JSONObject(rawData);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}