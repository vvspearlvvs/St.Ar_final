package com.dragon4.owo.ar_trace;

/**
 * Created by YejinChoi on 2017-06-06.
 */

/*Review search 뷰리스트에 리뷰장소이름,리뷰장소카테고리, 리뷰 장소 전화번호, 리뷰 장소 실제 주소를 ArrayList로 만들기 위함*/
public class ReviewSearchlistItem {
    String rv_slist_item_name ;
    String rv_slist_item_category;
    String rv_slist_item_call_number;
    String rv_slist_item_address;

    public ReviewSearchlistItem(String rv_slist_item_name, String rv_slist_item_category, String rv_slist_item_call_number, String rv_slist_item_address) {
        this.rv_slist_item_name = rv_slist_item_name;
        this.rv_slist_item_category = rv_slist_item_category;
        this.rv_slist_item_call_number = rv_slist_item_call_number;
        this.rv_slist_item_address = rv_slist_item_address;
    }

    public String getRv_slist_item_name() {
        return rv_slist_item_name;
    }

    public void setRv_slist_item_name(String rv_slist_item_name) {
        this.rv_slist_item_name = rv_slist_item_name;
    }

    public String getRv_slist_item_category() {
        return rv_slist_item_category;
    }

    public void setRv_slist_item_category(String rv_slist_item_category) {
        this.rv_slist_item_category = rv_slist_item_category;
    }

    public String getRv_slist_item_call_number() {
        return rv_slist_item_call_number;
    }

    public void setRv_slist_item_call_number(String rv_slist_item_call_number) {
        this.rv_slist_item_call_number = rv_slist_item_call_number;
    }

    public String getRv_slist_item_address() {
        return rv_slist_item_address;
    }

    public void setRv_slist_item_address(String rv_slist_item_address) {
        this.rv_slist_item_address = rv_slist_item_address;
    }

    @Override
    public String toString() {
        return "ReviewSearchlistItem{" +
                "rv_slist_item_name='" + rv_slist_item_name + '\'' +
                ", rv_slist_item_category='" + rv_slist_item_category + '\'' +
                ", rv_slist_item_call_number='" + rv_slist_item_call_number + '\'' +
                ", rv_slist_item_address='" + rv_slist_item_address + '\'' +
                '}';
    }
}
