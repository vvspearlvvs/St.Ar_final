package com.dragon4.owo.ar_trace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon4.owo.ar_trace.ARCore.Marker.ARMarker;
import com.dragon4.owo.ar_trace.ARCore.Marker.NaverSearchMarker;
import com.dragon4.owo.ar_trace.ARCore.NaverHttpHandler;
import com.dragon4.owo.ar_trace.ARCore.data.DataProcessor.DataConvertor;
import com.dragon4.owo.ar_trace.ARCore.data.DataSource;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReadReviewActivity extends AppCompatActivity {

    LinearLayout mainView;  // 리뷰 보기 전체 뷰
    AutoCompleteTextView searchBar;
    ReviewSearchViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_search);

        mainView = (LinearLayout) findViewById(R.id.activity_review_read);

        // 검색창
        searchBar = (AutoCompleteTextView) findViewById(R.id.review_search_searchbar);

        // 키보드 외의 화면을 터치했을 시 키보드 사라지게
        FrameLayout viewLayout = (FrameLayout) findViewById(R.id.review_search_listview_layout);
        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
            }
        });

        //searchReview();
        Button searchBtn = (Button) findViewById(R.id.review_searchbtn);

        final ListView searchListView = (ListView) findViewById(R.id.review_search_select_item_listview);
        final Context context = this;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        adapter = new ReviewSearchViewAdapter(getLayoutInflater());
        searchListView.setAdapter(adapter);

    }

    // 검색버튼 눌렀을 때
    public void onSearchButtonClicked(View view) {
        searchBar = (AutoCompleteTextView) findViewById(R.id.review_search_searchbar);
        String queryString = searchBar.getText().toString();

        if (queryString.equals("")) {
            Toast.makeText(this, "리뷰를 검색할 장소를 입력해주세요", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, queryString + "(으)로 검색", Toast.LENGTH_LONG).show();
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE); //안드로이드 키보드 숨기기
            imm.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
            //showResultView();

            DataConvertor dataConvertor = new DataConvertor();

            List<ARMarker> searchList = null;
            String encodedQueryString = null;
            try {
                encodedQueryString = URLEncoder.encode(queryString, "UTF-8");
                String searchURL = DataSource.createNaverSearchRequestURL(encodedQueryString);
                String searchRawData = new NaverHttpHandler().execute(searchURL).get();
                searchList = dataConvertor.load(searchRawData, DataSource.DATASOURCE.SEARCH, DataSource.DATAFORMAT.NAVER_SEARCH);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            List<NaverSearchMarker> dataList = new ArrayList<>();
            for (int i = 0; i < searchList.size(); i++) {
                //Log.d("searchList 흠? ", searchList.get(i).getTitle());
                dataList.add((NaverSearchMarker) searchList.get(i));
            }

            //ReviewSearchViewAdapter adapter = new ReviewSearchViewAdapter(getLayoutInflater());
            //listView.setAdapter(adapter);
            adapter.setDataList(dataList);
        }

    }

    /*public void showResultView() {
        Intent it = new Intent(ReadReviewActivity.this,ReadReviewResultActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
    }*/

    public void onSearchItemClicked(View view) {
        // 검색결과 리뷰 창으로 넘어가기
        Intent it = new Intent(ReadReviewActivity.this,ReadReviewResultActivity.class);
        String locationCode  = view.findViewById(R.id.review_search_select_item_layout).getTag().toString();
        TextView tv = (TextView) findViewById(R.id.review_search_select_item_placename);
        String placeName = tv.getText().toString();
        it.putExtra("locationCode", locationCode);
        it.putExtra("placeName", placeName);
        //Toast.makeText(this, locationCode, Toast.LENGTH_LONG).show();
        startActivity(it);
        overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);

    }
}

/* 자동완성으로 보여줄 리스트 어댑터 */
class ReviewSearchViewAdapter extends BaseAdapter {
    private List<NaverSearchMarker> dataList = new ArrayList<>();
    private String currentText;
    LayoutInflater inflater;

    public ReviewSearchViewAdapter(LayoutInflater inflater) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_review_search_select_item, null);
        //TextView searchItem = (TextView) view.findViewById(R.id.search_item);
        TextView placeNameTV = (TextView) view.findViewById(R.id.review_search_select_item_placename);
        TextView categoryTV = (TextView) view.findViewById(R.id.review_search_select_item_category);
        TextView callNumberTV = (TextView) view.findViewById(R.id.review_search_select_item_call_number);
        TextView locationCodeTV = (TextView) view.findViewById(R.id.review_search_select_item_locationcode);

        final NaverSearchMarker currentData = dataList.get(i);

        placeNameTV.setText(currentData.getTitle());
        categoryTV.setText(currentData.getCategory());
        callNumberTV.setText(currentData.getTelephone());
        locationCodeTV.setText(currentData.getRoadAddress());

        LinearLayout searchItem = (LinearLayout) view.findViewById(R.id.review_search_select_item_layout);
        searchItem.setTag(locationCodeTV.getText().toString()); // locationCode를 태그에 저장!

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
}

/* GridView에 리뷰를 뿌려줄 어댑터*/
class ReviewSearchAdapter extends BaseAdapter {
    Context context;
    int layout;
    LayoutInflater inf;
    List<com.dragon4.owo.ar_trace.ReviewItem> reviewList = new ArrayList<>();

    public ReviewSearchAdapter(LayoutInflater inf) {
        this.inf = inf;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inf.inflate(R.layout.activity_review_search_item, null);
        ImageView writerProfile = (ImageView) view.findViewById(R.id.review_box_user_profile_image);
        TextView writerName = (TextView) view.findViewById(R.id.review_box_user_name);
        TextView date = (TextView) view.findViewById(R.id.review_box_date);
        ImageView reviewImg = (ImageView) view.findViewById(R.id.review_box_img);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.review_box_ratingbar);
        TextView ratingScore = (TextView) view.findViewById(R.id.review_box_rating_score);
        TextView content = (TextView) view.findViewById(R.id.review_box_content);
        TextView countView = (TextView) view.findViewById(R.id.review_box_count);
        final ImageButton likeBtn = (ImageButton) view.findViewById(R.id.review_box_likeButton);
        final ImageButton commentBtn = (ImageButton) view.findViewById(R.id.review_box_commentButton);
        final ImageButton sharingBtn = (ImageButton) view.findViewById(R.id.review_box_sharingButton);

        //-------------------------------------------------------------------
        //final NaverSearchMarker currentData = dataList.get(i);
        final com.dragon4.owo.ar_trace.ReviewItem currentData = reviewList.get(position);

        // 추가해야됨 : 좋아요 버튼 클릭시 활성화
        // 추가해야됨 : 댓글 버튼 클릭시 활성화
        // 추가해야됨 : 공유 버튼 클릭시 활성화


        //!!참고용!!######################################################################################
        // 네비 버튼 클릭시 활성화
        /*TextView navi = (TextView)view.findViewById(R.id.ar_mixview_search_listview_navi);
        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runNavi(currentData.getLatitude(),currentData.getLongitude());
            }
        });
        navi.setTag(currentData.getAddress());*/
        //######################################################################################



        //RecyclerView.ViewHolder holder;
        //final TraceRecyclerViewAdapter.TraceViewHolder traceHolder = (TraceRecyclerViewAdapter.TraceViewHolder)holder;

        // 현재 리뷰의 데이터 입력
        //writerProfile = //프로필 이미지 등록
        writerName.setText(currentData.getWriterName());
        date.setText(currentData.getDate());

        String[] imgURL = currentData.getReviewImg().split("/");
        //Log.d("이미지ㅣㅣㅣㅣㅣ", imgURL[(imgURL.length) - 1]);
        String requestURL = "http://203.249.22.16:3333/image/" + imgURL[(imgURL.length) - 1];
        Picasso.with(reviewImg.getContext()).load(requestURL).into(reviewImg); //리뷰 이미지 등록
        ratingBar.setRating(currentData.getRating());
        ratingScore.setText(currentData.getRating()+"");
        content.setText(currentData.getContent());
        countView.setText("좋아요 " + currentData.getLikeCnt() + "개 댓글 " + currentData.getCommentCnt() + "개");

        // 좋아요 상태
        if (currentData.getLikeState() == 'y') {
            likeBtn.setImageResource(R.drawable.like_o);
            likeBtn.setTag("y");
        } else if (currentData.getLikeState() == 'n') {
            likeBtn.setImageResource(R.drawable.like_x);
            likeBtn.setTag("n");
        }

        // 좋아요 버튼 클릭시!
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageButton btn = (ImageButton) view;

                if(view.getTag() == "y") {
                    view.setTag("n");

                    btn.setImageResource(R.drawable.like_x);
                    //view.setBackgroundResource(R.drawable.like_x);
                    // 서버로 좋아요 여부 바꾸기기
                } else {
                    view.setTag("y");

                    btn.setImageResource(R.drawable.like_o);
                    //view.setBackgroundResource(R.drawable.like_o);
                    // 서버로 좋아요 여부 바꾸기기
                }
            }
        });

        return view;
    }

    public void setReviewList(List<com.dragon4.owo.ar_trace.ReviewItem> reviewList) {
        this.reviewList = reviewList;
    }

}