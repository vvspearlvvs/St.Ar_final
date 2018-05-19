package com.dragon4.owo.ar_trace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.dragon4.owo.ar_trace.Configure.ClientInstance;
import com.dragon4.owo.ar_trace.Model.User;

import java.util.ArrayList;

/**
 * Created by YejinChoi on 2017-05-21.
 */


public class MyPageActivity extends AppCompatActivity implements OnTabChangeListener {
    String[] tabNames = {"SearchTab", "VisitTab", "ReviewTab"};
    ListView slistView;
    ListView vlistView;
    ListView rlistView;
    Searchlist_Adapter s_adapter ;
    Visitedlist_Adapter v_adapter;
    Reviewlist_Adapter r_adapter;

    ArrayList<MyPageSearchlistItem> py_mypage_slistitem = new ArrayList<MyPageSearchlistItem>();
    ArrayList<MyPageVisitedlistItem> py_mypage_vlistitem = new ArrayList<MyPageVisitedlistItem>();
    ArrayList<MyPageReviewlistItem> py_mypage_rlistitem = new ArrayList<MyPageReviewlistItem>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "mypage oncreate 되니?");
        setContentView(R.layout.activity_my_page);
        s_adapter = new Searchlist_Adapter(this.getApplicationContext());
        v_adapter = new Visitedlist_Adapter(this.getApplicationContext());
        r_adapter = new Reviewlist_Adapter(getLayoutInflater(), this.getApplicationContext());

        Log.d("TAG", "mypage Tab 되니?");
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();
        tabHost1.setOnTabChangedListener(this);

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("SearchTab");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("최근 검색 기록");
        tabHost1.addTab(ts1);

        slistView = (ListView) findViewById(R.id.searchlistview);

        final View s_header = getLayoutInflater().inflate(R.layout.mypage_slistview_hearder,null,false);
        Log.d("TAG","header 붙여짐?");
        slistView.addHeaderView(s_header);
        Log.d("TAG","header 붙여졌다");

        slistView.setAdapter(s_adapter);
        Log.d("TAG", "slistView에 데이터 넣기");

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("VisitTab");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("방문 기록");
        tabHost1.addTab(ts2);

        vlistView = (ListView) findViewById(R.id.visitedlistview);

        final View v_header = getLayoutInflater().inflate(R.layout.mypage_vlistview_header,null,false);
        vlistView.addHeaderView(v_header);

        vlistView.setAdapter(v_adapter);

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("ReviewTab");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("내가 쓴 리뷰");
        tabHost1.addTab(ts3);

        rlistView = (ListView)findViewById(R.id.reviewlistview);

        final View r_header = getLayoutInflater().inflate(R.layout.mypage_rlistview_header,null,false);
        rlistView.addHeaderView(r_header);

        rlistView.setAdapter(r_adapter);

    }

    public void onHomeButtonClicked(View view) {
        Intent it = new Intent(MyPageActivity.this, MainMenuActivity.class);
        startActivity(it);
        finish();
    }

    public void addDataToSearchList(Searchlist_Adapter s_adapter) {
        // 서버에 최근 검색기록 데이터 요청 후 리턴 데이터로 리스트 생성

        py_mypage_slistitem = ClientInstance.getInstanceClient().getMyPageSlistFromServer(User.getMyInstance().getUserEmail(), py_mypage_slistitem);

        for(int i =0 ;i<py_mypage_slistitem.size();i++){
            s_adapter.addItem(py_mypage_slistitem.get(i));
        }

    }

    public void addDataToVisitList(Visitedlist_Adapter v_adapter) {
        // 서버에 방문 기록 데이터 요청 후 리턴 데이터로 리스트 생성

        py_mypage_vlistitem = ClientInstance.getInstanceClient().getMyPageVlistFromServer(User.getMyInstance().getUserEmail(),py_mypage_vlistitem);

        for(int i =0 ;i<py_mypage_vlistitem.size();i++){
            v_adapter.addItem(py_mypage_vlistitem.get(i));
        }
    }

    public void addDataToReviewList(Reviewlist_Adapter r_adapter) {
        // 서버에 최근 검색기록 데이터 요청 후 리턴 데이터로 리스트 생성

        py_mypage_rlistitem = ClientInstance.getInstanceClient().getMyPageRlistFromServer(User.getMyInstance().getUserEmail(),py_mypage_rlistitem);

        for(int i =0 ;i<py_mypage_rlistitem.size();i++){
            r_adapter.addItem(py_mypage_rlistitem.get(i));
        }
    }

    public void onTabChanged(String tabId) {
        if (tabNames[0].equals(tabId)) {
            addDataToSearchList(s_adapter);
        }
        if (tabNames[1].equals(tabId)) {
            addDataToVisitList(v_adapter);
        }
        if (tabNames[2].equals(tabId)) {
            addDataToReviewList(r_adapter);
        }

    }

    /*slistView의 데이터를 Adapter가 관리*/
    class Searchlist_Adapter extends BaseAdapter {
        Context context;
        ArrayList<MyPageSearchlistItem> sitems;

        Searchlist_Adapter(Context c) {
            this.context = c;
            sitems = new ArrayList<MyPageSearchlistItem>();
        }


        @Override
        public int getCount() {
            return sitems.size();
        }

        @Override
        public Object getItem(int position) {
            return sitems.get(position);
        }

        public void addItem(MyPageSearchlistItem item) {
            boolean isin = false;
            for(int i = 0 ; i < sitems.size(); i++){
                if(sitems.get(i).getsID().equals(item.getsID())) {
                    isin = true;
                    break;
                }
            }

            if(!isin)
                sitems.add(item);
        }

        @Override
        public long getItemId(int position) {
            return position;
        } //id특정하게 만들어서 리턴해도 된다

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyPageSearchlistItem_View view = new MyPageSearchlistItem_View(context);
            //어떤 view든 안드로이드에서는 context 객체로 받음

            MyPageSearchlistItem item = sitems.get(position);
            view.setDate(item.getDate());
            view.setPlaceName(item.getPlaceName());

            return view;
        }

    }

    /*vlistview의 데이터를 Adapter가 관리*/
    class Visitedlist_Adapter extends BaseAdapter {
        Context context;
        ArrayList<MyPageVisitedlistItem> vitems;

        Visitedlist_Adapter(Context c) {
            this.context = c;
            vitems = new ArrayList<MyPageVisitedlistItem>();
        }

        @Override
        public int getCount() {
            return vitems.size();
        }

        @Override
        public Object getItem(int position) {
            return vitems.get(position);
        }

        public void addItem(MyPageVisitedlistItem item) {
            boolean isin = false;
            for(int i = 0 ; i < vitems.size(); i++){
                if(vitems.get(i).getvID().equals(item.getvID())){
                    isin = true;
                    break;
                }
            }

            if(!isin)
                vitems.add(item);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyPageVisitedlistItem_View view = new MyPageVisitedlistItem_View(context);
            //어떤 view든 안드로이드에서는 context 객체로 받음

            MyPageVisitedlistItem vitem = vitems.get(position);
            view.setvDate(vitem.getVdate());
            view.setvPlaceName(vitem.getVplaceName());

            return view;
        }
    }

    class Reviewlist_Adapter extends BaseAdapter{
        Context context;
        ArrayList<MyPageReviewlistItem> ritems;
        LayoutInflater inf;
        //Button rDeleteButton = (Button)findViewById(R.id.rDeleteButton);

        Reviewlist_Adapter(LayoutInflater inf, Context c) {
            this.inf = inf;
            this.context = c;
            ritems = new ArrayList<MyPageReviewlistItem>();
        }

        @Override
        public int getCount() {
            return ritems.size();
        }

        @Override
        public Object getItem(int position) {
            return ritems.get(position);
        }

        public void addItem(MyPageReviewlistItem item) {
            boolean isin = false;
            for(int i = 0 ; i < ritems.size(); i++){
                if(ritems.get(i).getRdate().equals(item.getRdate()) && ritems.get(i).getRplaceName().equals(item.getRplaceName())) {
                    Log.d("TAG","Ritems placeName : "+ritems.get(i).getRplaceName());
                    Log.d("TAG","Item placeName : "+item.getRplaceName());
                    isin = true;
                    break;
                }
            }

            if(!isin) {
                ritems.add(item);
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyPageReviewlistItem_View view = new MyPageReviewlistItem_View(context);
            //어떤 view든 안드로이드에서는 context 객체로 받음

            MyPageReviewlistItem ritem = ritems.get(position);
            view.setrDate(ritem.getRdate());
            view.setrPlaceName(ritem.getRplaceName());
            view.setrContent(ritem.getRcontent());

//            Button deleteBtn = (Button) convertView.findViewById(R.id.rDeleteButton);
//
//            deleteBtn.setId(position);
//            deleteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onReviewDeleteBtn(view);
//                }
//            });

            return view;
        }

//        public void onReviewDeleteBtn(View view) {
//            int position = view.getId();
//
//            //String rID = ritems.get(position).getrID();
//            //Log.d("TAG","rID : "+rID);
//            String rID = ritems.get(position).getrID();
//            Log.d("TAG","rPlace : "+rID);
//
//           Log.d("TAG","onReviewDeleteBtn들어감?");
//            ClientInstance.getInstanceClient().sendDeleteReviewToServer(User.getMyInstance().getUserEmail());
//            //ClientInstance.getInstanceClient().sendDeleteReviewListToServer(User.getMyInstance().getUserEmail(),rID);
//        }
    }
}


