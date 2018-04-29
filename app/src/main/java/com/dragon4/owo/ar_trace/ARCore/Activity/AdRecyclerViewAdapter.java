package com.dragon4.owo.ar_trace.ARCore.Activity;


//0603팝업추가부분
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragon4.owo.ar_trace.Model.AD;
import com.dragon4.owo.ar_trace.Network.ClientSelector;
import com.dragon4.owo.ar_trace.R;

import java.util.ArrayList;

/**
 * Created by samsung on 2017-06-03.
 */

public class AdRecyclerViewAdapter extends RecyclerView.Adapter {
    public ArrayList<AD> adList;
    public ClientSelector clientSelector;

    public AdRecyclerViewAdapter(ClientSelector clientSelector) {
        this.clientSelector = clientSelector;
    }
    public class AdViewHolder extends RecyclerView.ViewHolder {
        public TextView locationIDview;
        public TextView placeNameview;
        public TextView rankview;
        public TextView contentview;

        public AdViewHolder(View itemView) {
            super(itemView);
            locationIDview = (TextView) itemView.findViewById(R.id.ar_mixview_ad_locationid);
            placeNameview = (TextView) itemView.findViewById(R.id.ar_mixview_ad_placename);
            rankview = (TextView) itemView.findViewById(R.id.ar_mixview_ad_ranking);
            contentview = (TextView) itemView.findViewById(R.id.ar_mixview_ad_content);
        }


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ar_mixview_ad_item, parent, false);
        AdRecyclerViewAdapter.AdViewHolder vh = new AdRecyclerViewAdapter.AdViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AD ad = adList.get(position);
        final AdRecyclerViewAdapter.AdViewHolder AdHolder = (AdRecyclerViewAdapter.AdViewHolder)holder;

        // 광고정보 표시
        AdHolder.locationIDview.setText(ad.getContent());
        AdHolder.placeNameview.setText(ad.getPlaceName());
        AdHolder.rankview.setText(ad.getRank());
        AdHolder.contentview.setText(ad.getContent());

    }

    @Override
    public int getItemCount() {
        return adList.size();
    }
    public void setList(ArrayList<AD> adList) { this.adList = adList; }

}