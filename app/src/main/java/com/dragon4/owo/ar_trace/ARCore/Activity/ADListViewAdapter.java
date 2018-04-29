package com.dragon4.owo.ar_trace.ARCore.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dragon4.owo.ar_trace.Model.AD;
import com.dragon4.owo.ar_trace.R;

import java.util.ArrayList;

/**
 * Created by samsung on 2017-06-04.
 */


public class ADListViewAdapter extends BaseAdapter {
    private ArrayList<AD> adList = new ArrayList<>();
    LayoutInflater inflater;

    public TextView locationIDview;
    public TextView placeNameview;
    public TextView rankview;
    public TextView contentview;

    public ADListViewAdapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public int getCount() {
        return adList.size();
    }

    @Override
    public Object getItem(int i) {
        return adList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.layout_ar_mixview_ad_item, null);
        locationIDview = (TextView) view.findViewById(R.id.ar_mixview_ad_locationid);
        placeNameview = (TextView) view.findViewById(R.id.ar_mixview_ad_placename);
        rankview = (TextView) view.findViewById(R.id.ar_mixview_ad_ranking);
        contentview = (TextView) view.findViewById(R.id.ar_mixview_ad_content);

        final AD currentData = adList.get(i);

        // 광고정보 표시
        locationIDview.setText(currentData.getLocationCode());
        placeNameview.setText(currentData.getPlaceName());
        rankview.setText(""+currentData.getRank());
        contentview.setText(currentData.getContent());


        return view;

    }


    public void setAdList(ArrayList<AD> adList) {
        this.adList = adList;
    }



    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }
}
