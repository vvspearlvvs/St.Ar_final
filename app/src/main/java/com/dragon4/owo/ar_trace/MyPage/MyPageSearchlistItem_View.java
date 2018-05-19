package com.dragon4.owo.ar_trace;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YejinChoi on 2017-05-30.
 */


/*Search 뷰를 정의하는 클래스*/

public class MyPageSearchlistItem_View extends LinearLayout{
    TextView searchdatetext; //TextView를 어디서든 사용하기 위함
    TextView searchplaceNametext;

    public MyPageSearchlistItem_View(Context context) {
        super(context);
        init(context);
    }

    public MyPageSearchlistItem_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //SystemService --> Background Service

        //searchlistview의 최상위 레이아웃인 LinearLayout을 붙임
        inflater.inflate(R.layout.mypage_slistview_item,this,true);

        //mypage_slistview_item.xml의 TextView를 참조하기 위함
        searchdatetext = (TextView) findViewById(R.id.searchdatetext);
        searchplaceNametext = (TextView) findViewById(R.id.searchplaceNametext);
    }

    //TextView에 Data전달받으면 설정하기 위함
    public void setDate(String date){
        searchdatetext.setText(date);
    }

    public void setPlaceName(String placeName){
        searchplaceNametext.setText(placeName);
    }
}
