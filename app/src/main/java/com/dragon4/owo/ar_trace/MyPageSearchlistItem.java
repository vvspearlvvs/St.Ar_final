package com.dragon4.owo.ar_trace;

/**
 * Created by YejinChoi on 2017-05-30.
 */

/*뷰리스트에 날짜,장소이름 항목 ArrayList로 만들기 위함*/

public class MyPageSearchlistItem {
    String date;
    String placeName;
    String sID;

    public MyPageSearchlistItem(String date, String placeName,String sID) {
        this.date = date;
        this.placeName = placeName;
        this.sID = sID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    /*객체 안에 들어있는 아이템을 String으로 보여줌*/

    @Override
    public String toString() {
        return "MyPageSearchlistItem{" +
                "date='" + date + '\'' +
                ", placeName='" + placeName + '\'' +
                ", sID='" + sID + '\'' +
                '}';
    }
}
