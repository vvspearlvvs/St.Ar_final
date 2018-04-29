package com.dragon4.owo.ar_trace.Network;

import android.content.Context;
import android.widget.TextView;

import com.dragon4.owo.ar_trace.ARCore.Activity.TraceRecyclerViewAdapter;
import com.dragon4.owo.ar_trace.Model.AD;
import com.dragon4.owo.ar_trace.Model.ReviewListPlace;
import com.dragon4.owo.ar_trace.Model.SearchPlace;
import com.dragon4.owo.ar_trace.Model.Trace;
import com.dragon4.owo.ar_trace.Model.User;
import com.dragon4.owo.ar_trace.Model.VisitPlace;
import com.dragon4.owo.ar_trace.MyPageReviewlistItem;
import com.dragon4.owo.ar_trace.MyPageSearchlistItem;
import com.dragon4.owo.ar_trace.MyPageVisitedlistItem;
import com.dragon4.owo.ar_trace.RegistReviewObj;
import com.dragon4.owo.ar_trace.ReviewItem;
import com.dragon4.owo.ar_trace.ReviewLocationItem;

import java.io.File;
import java.util.ArrayList;

public interface ClientSelector {

    void uploadUserDataToServer(User user, final Context googleSignInContext);//유저로그인
    void uploadImageToServer(Trace trace, File file); // 파일 업로드 하는 함수
    void uploadTraceToServer(Trace trace); // Trace 객체들을 올리는 함수
    void sendTraceLikeToServer(final boolean isLikeClicked, Trace trace); //
    void getReviewNumberFromServer(String buildingID, TextView reviewNumber);
    ArrayList<Trace> getTraceDataFromServer(String traceKey, TraceRecyclerViewAdapter mAdapter); // 서버 데이터들
    void getTraceLikeInformation(final Trace trace, final TraceRecyclerViewAdapter adapter, final TraceRecyclerViewAdapter.TraceViewHolder reviewHolder);

    //마이페이지_최근검색기록 가져오는 함수
    ArrayList<MyPageSearchlistItem> getMyPageSlistFromServer(String userID, ArrayList<MyPageSearchlistItem>  searchedList );

    //마이페이지_방문기록 리스트 개수 가져오는 함수
    String getMyPageVlistNumFromServer(String userID,TextView log_vlist);

    //마이페이지_방문기록 가져오는 함수
   ArrayList<MyPageVisitedlistItem> getMyPageVlistFromServer(String userID,ArrayList<MyPageVisitedlistItem> visitedList);

    //광고 보여주는 함수
    ArrayList<AD> getAdToServer(String address);
    //내가 쓴 리뷰 요청하는 함수
    ArrayList<MyPageReviewlistItem> getMyPageRlistFromServer(String userID,ArrayList<MyPageReviewlistItem> writtenList);

    //서버에게 방문 기록 추가 요청하는 함수
    void sendvisitToServer(VisitPlace visitPlace);

    //서버에게 검색 기록 추가 요청하는 함수
    void sendsearchToServer(SearchPlace searchPlace);

    //유저 로그아웃
    //void UserLogout(String userID);

    //마이페이지 리뷰보기에서 리뷰 삭제하는 함수
    void sendDeleteReviewToServer(String reviewID);

    //네비게이션 종료시 리뷰리스트에 추가 요청하는 함수
    void sendReviewListToServer(ReviewListPlace reviewListPlace);

    //마이페이지 리뷰보기에서 리뷰리스트에서 삭제하는 함수
    void sendDeleteReviewListToServer(String userID,String placeName);

    // 후기작성 리스트 가져오기
    ArrayList<ReviewLocationItem> getReviewLocationListFromServer(String userID);

    // 리뷰 등록 시 이미지 업로드하고 그 URL받아오기
    String uploadImageToServer(final String filePath);

    // 리뷰 등록
    boolean registReviewToServer(RegistReviewObj review);

    // 리뷰 등록시 리뷰 리스트에서 삭제
    void deleteReviewListFromServer(String userID, String placeName);

    // 장소별 리뷰 가져오기
    ArrayList<ReviewItem> getReviewsFromServer(String userID, String locationCode);

    // 리뷰 통계 가져오기 (별점 평균, 리뷰 개수)
    ReviewItem getStatsFromServer(String userID, String locationCode);
}