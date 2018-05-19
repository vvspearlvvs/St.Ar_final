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

/*뷰를 정의하는 클래스*/
public class MyPageVisitedlistItem_View extends LinearLayout{
    TextView vdatetext;
    TextView vplaceNametext;

    public MyPageVisitedlistItem_View(Context context) {
        super(context);
        init(context);
    }

    public MyPageVisitedlistItem_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.mypage_vlistview_item,this,true);

        vdatetext = (TextView) findViewById(R.id.vdatetext);
        vplaceNametext = (TextView) findViewById(R.id.vplaceNametext);
    }

    public void setvDate(String vdate){
        vdatetext.setText(vdate);
    }

    public void setvPlaceName(String vplaceName){
        vplaceNametext.setText(vplaceName);
    }
}
