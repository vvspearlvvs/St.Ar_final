package com.dragon4.owo.ar_trace;

/**
 * Created by 예린 on 2017-06-06.
 * 리뷰 등록 시 필요한 부분을 저장할 객체
 */

public class RegistReviewObj {
    private String locationCode;
    private String placeName;
    private String content;
    private String userID;
    private String writeDate;
    private String imageURL;
    private float rating;
    private int likeNum;

    public RegistReviewObj(String locationCode, String placeName, String content, String userID, String writeDate, String imageURL, float rating, int likeNum) {
        this.locationCode = locationCode;
        this.placeName = placeName;
        this.content = content;
        this.userID = userID;
        this.writeDate = writeDate;
        this.imageURL = imageURL;
        this.rating = rating;
        this.likeNum = likeNum;
    }

    public RegistReviewObj(String locationCode, String placeName, String content, String userID, String imageURL, float rating, int likeNum) {
        this.locationCode = locationCode;
        this.placeName = placeName;
        this.content = content;
        this.userID = userID;
        this.imageURL = imageURL;
        this.rating = rating;
        this.likeNum = likeNum;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    @Override
    public String toString() {
        return "RegistReviewObj{" +
                "locationCode='" + locationCode + '\'' +
                ", placeName='" + placeName + '\'' +
                ", content='" + content + '\'' +
                ", userID='" + userID + '\'' +
                ", writeDate='" + writeDate + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", rating=" + rating +
                ", likeNum=" + likeNum +
                '}';
    }
}