package com.dragon4.owo.ar_trace;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.dragon4.owo.ar_trace.R.id.rDeleteButton;

/**
 * Created by YejinChoi on 2017-06-04.
 */


/*Review 뷰를 정의하는 클래스*/

public class MyPageReviewlistItem_View extends LinearLayout{
    TextView rdatetext;
    TextView rplaceNametext;
    TextView rContent;


    public MyPageReviewlistItem_View(Context context) {
        super(context);
        init(context);
    }

    public MyPageReviewlistItem_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //SystemService --> Background Service

        //reviewlistview의 최상위 레이아웃인 LinearLayout을 붙임
        inflater.inflate(R.layout.mypage_rlistview_item,this,true);

        //mypage_rlistview_item.xml의 TextView를 참조하기 위함
        rdatetext = (TextView) findViewById(R.id.rdatetext);
        rplaceNametext = (TextView) findViewById(R.id.rplaceNametext);
        rContent = (TextView) findViewById(rDeleteButton);
    }

    public void setrDate(String rDate){rdatetext.setText(rDate);}

    public void setrPlaceName(String rplaceName){rplaceNametext.setText(rplaceName);}

    public void setrContent(String rCont){rContent.setText(rCont);}

}
