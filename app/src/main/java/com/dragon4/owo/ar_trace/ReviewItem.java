package com.dragon4.owo.ar_trace;

/**
 * Created by 예린 on 2017-06-03.
 * "리뷰 보기" 메뉴에서 보여질 리뷰 객체
 */

public class ReviewItem {
    private String reviewID;        // 리뷰 ID
    private String locationCode;     // 장소 코드
    private String placeName;        // 장소 이름
    private String writerProfile;   // 글쓴이 프로필 사진URL
    private String writerName;      // 글쓴이 이름
    private String date;            // 리뷰 쓴 날짜
    private String reviewImg;       // 리뷰에 등록된 사진
    private String content;         // 리뷰 내용
    private int likeCnt;            // 좋아요 갯수
    private int commentCnt;         // 댓글 갯수
    private char likeState;          // 좋아요 클릭 상태
    private float rating;

    public ReviewItem(int commentCnt, float rating) {
        this.commentCnt = commentCnt;
        this.rating = rating;
    }

    public ReviewItem(String locationCode, String placeName, String writerProfile, String writerName, String date, String reviewImg, String content, int likeCnt, int commentCnt, char likeState) {
        this.locationCode = locationCode;
        this.placeName = placeName;
        this.writerProfile = writerProfile;
        this.writerName = writerName;
        this.date = date;
        this.reviewImg = reviewImg;
        this.content = content;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.likeState = likeState;
    }

    public ReviewItem(String reviewID, String locationCode, String placeName, String writerName, String date, String reviewImg, String content, float rating, int likeCnt) {
        this.reviewID = reviewID;
        this.locationCode = locationCode;
        this.placeName = placeName;
        this.writerName = writerName;
        this.date = date;
        this.reviewImg = reviewImg;
        this.content = content;
        this.rating = rating;
        this.likeCnt = likeCnt;
    }

    public ReviewItem(String writerProfile, String writerName, String date, String reviewImg, String content, int likeCnt, int commentCnt, char likeState) {
        this.writerProfile = writerProfile;
        this.writerName = writerName;
        this.date = date;
        this.reviewImg = reviewImg;
        this.content = content;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.likeState = likeState;
    }

    public ReviewItem(String reviewID, String locationCode, String placeName, String writerProfile, String writerName, String date, String reviewImg, String content, int likeCnt, int commentCnt, char likeState) {
        this.reviewID = reviewID;
        this.locationCode = locationCode;
        this.placeName = placeName;
        this.writerProfile = writerProfile;
        this.writerName = writerName;
        this.date = date;
        this.reviewImg = reviewImg;
        this.content = content;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.likeState = likeState;
    }

    public ReviewItem(String writerName, String content, int likeCnt, int commentCnt, char likeState) {
        this.writerName = writerName;
        this.content = content;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.likeState = likeState;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getWriterProfile() {
        return writerProfile;
    }

    public void setWriterProfile(String writerProfile) {
        this.writerProfile = writerProfile;
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

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReviewImg() {
        return reviewImg;
    }

    public void setReviewImg(String reviewImg) {
        this.reviewImg = reviewImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public char getLikeState() {
        return likeState;
    }

    public void setLikeState(char likeState) {
        this.likeState = likeState;
    }

    @Override
    public String toString() {
        return "ReviewItem{" +
                "reviewID='" + reviewID + '\'' +
                ", locationCode='" + locationCode + '\'' +
                ", placeName='" + placeName + '\'' +
                ", writerProfile='" + writerProfile + '\'' +
                ", writerName='" + writerName + '\'' +
                ", date='" + date + '\'' +
                ", reviewImg='" + reviewImg + '\'' +
                ", content='" + content + '\'' +
                ", likeCnt=" + likeCnt +
                ", commentCnt=" + commentCnt +
                ", likeState=" + likeState +
                '}';
    }

}