package com.dragon4.owo.ar_trace.Model;

/**
 * Created by YejinChoi on 2017-06-05.
 */

public class VisitPlace {
    private String userID;
    private String placeName;
    private String locationCode;

    public String getUserID() {
        return userID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }


    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}
