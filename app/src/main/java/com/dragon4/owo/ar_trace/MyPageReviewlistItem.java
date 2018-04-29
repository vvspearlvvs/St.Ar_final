package com.dragon4.owo.ar_trace;

/**
 * Created by YejinChoi on 2017-06-04.
 */


/*mypage review 뷰리스트에 리뷰작성날짜,리뷰장소이름, 리뷰 내용 항목 ArrayList로 만들기 위함*/
public class MyPageReviewlistItem {
    String rdate;
    String  rplaceName;
    String rcontent;
    String rID;

    public MyPageReviewlistItem(String rdate, String rplaceName, String rcontent,String rID) {
        this.rdate = rdate;
        this.rplaceName = rplaceName;
        this.rcontent = rcontent;
        this.rID = rID; //review ID
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRplaceName() {
        return rplaceName;
    }

    public void setRplaceName(String rplaceName) {
        this.rplaceName = rplaceName;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public String getrID() {
        return rID;
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    @Override
    public String toString() {
        return "MyPageReviewlistItem{" +
                "rdate='" + rdate + '\'' +
                ", rplaceName='" + rplaceName + '\'' +
                ", rcontent='" + rcontent + '\'' +
                ", rID='" + rID + '\'' +
                '}';
    }
}
