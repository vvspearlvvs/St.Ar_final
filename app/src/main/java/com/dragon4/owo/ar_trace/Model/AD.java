package com.dragon4.owo.ar_trace.Model;



public class AD {
    private String locationCode; // 장소의 주소들
    private String placeName; // 주소를 경도 위도로
    private String content; // 설명
    private int rank;//순위

    public AD(String locationCode, String placeName, String content, int rank) {
        this.locationCode = locationCode;
        this.placeName = placeName;
        this.content = content;
        this.rank = rank;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "AD{" +
                "locationCode='" + locationCode + '\'' +
                ", placeName='" + placeName + '\'' +
                ", content='" + content + '\'' +
                ", rank=" + rank +
                '}';
    }
}