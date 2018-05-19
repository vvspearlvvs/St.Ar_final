package com.dragon4.owo.ar_trace;

/**
 * Created by YejinChoi on 2017-05-30.
 */

/*visited 뷰리스트에 방문날짜,방문장소이름 항목 ArrayList로 만들기 위함*/

public class MyPageVisitedlistItem {
    String vdate;
    String vplaceName;
    String vID;

    public MyPageVisitedlistItem() {
    }

    public MyPageVisitedlistItem(String date, String vplaceName, String vID) {
        this.vdate = date;
        this.vplaceName = vplaceName;
        this.vID = vID;
    }

    public String getVdate() {
        return vdate;
    }

    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    public String getVplaceName() {
        return vplaceName;
    }

    public void setVplaceName(String vplaceName) {
        this.vplaceName = vplaceName;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    @Override
    public String toString() {
        return "MyPageVisitedlistItem{" +
                "vdate='" + vdate + '\'' +
                ", vplaceName='" + vplaceName + '\'' +
                ", vID='" + vID + '\'' +
                '}';
    }
}