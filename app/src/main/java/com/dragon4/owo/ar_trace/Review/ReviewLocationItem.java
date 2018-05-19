package com.dragon4.owo.ar_trace;

/**
 * Created by 예린 on 2017-06-05.
 */

public class ReviewLocationItem {
    private String visitDate;
    private String placeName;
    private String locationCode;

    public ReviewLocationItem(String visitDate, String placeName, String locationCode) {
        this.visitDate = visitDate;
        this.placeName = placeName;
        this.locationCode = locationCode;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getPlaceName() {
        return placeName;
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

    @Override
    public String toString() {
        return "ReviewLocationItem{" +
                "visitDate='" + visitDate + '\'' +
                ", placeName='" + placeName + '\'' +
                ", locationCode='" + locationCode + '\'' +
                '}';
    }
}
