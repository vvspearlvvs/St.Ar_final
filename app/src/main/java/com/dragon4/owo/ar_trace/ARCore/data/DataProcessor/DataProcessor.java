package com.dragon4.owo.ar_trace.ARCore.data.DataProcessor;

import com.dragon4.owo.ar_trace.ARCore.Marker.ARMarker;
import com.dragon4.owo.ar_trace.ARCore.data.DataSource;

import org.json.JSONException;

import java.util.List;


public interface DataProcessor {
    List<ARMarker> load(String rawData, DataSource.DATASOURCE datasource) throws JSONException;

}
