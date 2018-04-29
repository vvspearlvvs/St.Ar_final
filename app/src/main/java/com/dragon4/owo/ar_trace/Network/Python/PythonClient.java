package com.dragon4.owo.ar_trace.Network.Python;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.dragon4.owo.ar_trace.ARCore.Activity.TraceRecyclerViewAdapter;
import com.dragon4.owo.ar_trace.FCM.FCMWebServerConnector;
import com.dragon4.owo.ar_trace.Model.AD;
import com.dragon4.owo.ar_trace.Model.ReviewListPlace;
import com.dragon4.owo.ar_trace.Model.SearchPlace;
import com.dragon4.owo.ar_trace.Model.Trace;
import com.dragon4.owo.ar_trace.Model.User;
import com.dragon4.owo.ar_trace.Model.VisitPlace;
import com.dragon4.owo.ar_trace.MyPageReviewlistItem;
import com.dragon4.owo.ar_trace.MyPageSearchlistItem;
import com.dragon4.owo.ar_trace.MyPageVisitedlistItem;
import com.dragon4.owo.ar_trace.Network.ClientSelector;
import com.dragon4.owo.ar_trace.RegistReviewObj;
import com.dragon4.owo.ar_trace.ReviewItem;
import com.dragon4.owo.ar_trace.ReviewLocationItem;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class PythonClient implements ClientSelector{

    private Gson gson;

    private String pythonDataServerURL;
    private String pythonImageServerURL;
    String imageURL;

    private boolean isWorking = false;
    public boolean isWorking() {
        return isWorking;
    }

    public PythonClient() {
        gson =  new GsonBuilder().create();
        pythonDataServerURL = "http://203.249.22.16:3332";
        pythonImageServerURL = "http://203.249.22.16:3333"; // 이미지 서버
    }

    @Override
    public void uploadUserDataToServer(User currentUser, Context googleSignInContext){

        final String uploadTraceURL = pythonDataServerURL + "/users/login";

        try {
            if(User.getMyInstance().getUserToken() != null)
                currentUser.setUserToken(User.getMyInstance().getUserToken());
            else
                currentUser.setUserToken(FirebaseInstanceId.getInstance().getToken());

            String response = new PythonHTTPHandler().execute(uploadTraceURL,"POST",gson.toJson(currentUser)).get();
            Log.i("uploadTraceInstance",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        final Intent loginReceiver = new Intent();
        loginReceiver.setAction("LOGIN_SUCCESS");
        googleSignInContext.sendBroadcast(loginReceiver);
    }

    @Override
    public void uploadImageToServer(Trace trace, final File file) {

        final String uploadTraceURL = pythonImageServerURL + "/upload?";
        final String encodeFormat = "UTF-8";

        new Thread(new Runnable() {

            MultipartUtility multipartUtility = null;

            @Override
            public void run() {

                try {
                    multipartUtility = new MultipartUtility(uploadTraceURL,encodeFormat);
                    multipartUtility.addFilePart("file",new File(file.getPath()));

                    String response = multipartUtility.finish();

                    if(response != null)
                        Log.i("responseTest",response);
                    else
                        Log.i("responseTest","fail");

                    // 파라미터 추가가능
                    // multipartUtility.addFormField("key","value");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public void uploadTraceToServer(Trace trace) {

        String uploadTraceURL = pythonDataServerURL + "/reviews";

        try {
            String response = new PythonHTTPHandler().execute(uploadTraceURL,"POST",gson.toJson(trace)).get();
            Log.i("uploadTraceInstance",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Trace> getTraceDataFromServer(String placeName, TraceRecyclerViewAdapter mAdapter) {

        String getTraceDataListURL = pythonDataServerURL + "/reviews?";

        // 하나의 장소에 대해서 리뷰들을 가져오는것.
        final ArrayList<Trace> traceList = new ArrayList<>();
        mAdapter.setList(traceList);

        try {
            getTraceDataListURL += URLEncoder.encode(placeName,"UTF-8");
            String response = new PythonHTTPHandler().execute(getTraceDataListURL, "GET").get();
            JSONObject root = new JSONObject(response);

            // TODO: 2017. 2. 6. 제이썬으로 바꾼뒤 배열에 추가하는 과정을 리턴해야된다

        } catch (InterruptedException | ExecutionException | JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return traceList;
    }

    @Override
    public void sendTraceLikeToServer(final boolean isLikeClicked, Trace trace) {
        if(!isWorking) {
            isWorking = true;
            sendTraceLikeToPython(isLikeClicked,trace);

            if (isLikeClicked && trace.getuserID().compareTo(User.getMyInstance().getuserID()) != 0) {
                FCMWebServerConnector connector = new FCMWebServerConnector();
                connector.sendLikePush(trace);
            }
        }
    }

    private void sendTraceLikeToPython(final boolean isLikeClicked, final Trace trace) {

        final String traceLikeURL = pythonDataServerURL;
        // TODO: 2017. 2. 16. python 좋아요 기능 구현
    }

    @Override
    public void getReviewNumberFromServer(String placeName, TextView reviewNumber) {

        String getReviewNumberURL = pythonDataServerURL + "/reviews/count?";

        try {
            getReviewNumberURL += URLEncoder.encode(placeName,"UTF-8");
            String response = new PythonHTTPHandler().execute(getReviewNumberURL, "GET").get();
            JSONObject jsonObject = new JSONObject(response);
            String traceNumber = jsonObject.getString("data");
            reviewNumber.setText(traceNumber);

        } catch (InterruptedException | ExecutionException | UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getTraceLikeInformation(Trace trace, TraceRecyclerViewAdapter adapter, TraceRecyclerViewAdapter.TraceViewHolder reviewHolder) {

    }

    //마이페이지 최근 검색 리스트 가져오는 함수
    public ArrayList<MyPageSearchlistItem> getMyPageSlistFromServer(String userID, ArrayList<MyPageSearchlistItem>  searchedList ){

        //ArrayList<MyPageSearchlistItem>  searchedList = new ArrayList<MyPageSearchlistItem>();
        String newstring = "";

        String MyPageSlistURL = pythonDataServerURL + "/search?userID=";

        try {
            MyPageSlistURL += URLEncoder.encode(userID,"UTF-8");
            //MyPageSlistURL += userID;
            String response = new PythonHTTPHandler().execute(MyPageSlistURL, "GET",userID).get();
            Log.d("TAG","getMyPageSlistFromServer 실행중");
            Log.d("TAG",response);
            // MyPageVlistURL += URLEncoder.encode(userID,"UTF-8");
            //String response = new PythonHTTPHandler().execute(MyPageVlistURL, "GET",userID, "userID").get();
            Log.d("TAG","get => response 됨!");


            char [] rech = response.toCharArray();
            Log.d("TAG","rech");
            Log.d("TAG",rech.toString());


            int c = 0;
            String str = "";
            for (int i = 0; i < rech.length; i++) {
               // Log.d("TAG","rech for문");
                if (rech[i] == 'u') {
                    if (rech[i + 1] == '\'')
                        str += rech[++i];
                    else
                        str+= rech[i];
                } else {
                    str += rech[i];
                }
              //  Log.d("TAG", "str : " + str);
            }
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jarray = jsonObject.getJSONArray("data");
            //Log.d("TAG", jarray+"");
            //jarray.getJSONObject(0);

            for(int i=0;i<jarray.length();i++){
                jarray.getJSONObject(i);
                Log.d("TAG","jarray["+i+"] : "+jarray.get(i));

                String sc_sDate = jarray.getJSONObject(i).getString("searchDate");
                Log.d("TAG","searchDate : "+sc_sDate);
                String sc_sPlace = jarray.getJSONObject(i).getString("placeName");
                Log.d("TAG","placeName : "+sc_sPlace);
                String sc_sID = jarray.getJSONObject(i).getString("searchID");

                boolean isin = false;
                for(int j = 0 ; j<searchedList.size();j++){
                    if(searchedList.get(j).getsID().equals(sc_sID)){
                        isin = true;
                        break;
                    }
                }
                if(!isin){
                    MyPageSearchlistItem sc_search_list = new MyPageSearchlistItem(sc_sDate,sc_sPlace,sc_sID);
                    searchedList.add(sc_search_list);
                    Log.d("TAG", "밖에서 추가"+sc_sPlace);
                }

            }

            Log.d("TAG","getMyPageSlistFromServer 끝");
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return searchedList;
    }

    //방문기록 리스트 개수 가져오는 함수
    @Override
    public String getMyPageVlistNumFromServer(String userID,TextView log_vlist) {
        Log.d("TAG","getMyPageVlistNumFromServer 들어왔어");
        String getMyPageVlistNumURL = pythonDataServerURL + "/visit/count?userID=";
        String vlistNum = "";
        try {
            getMyPageVlistNumURL += URLEncoder.encode(userID,"UTF-8");
            //getMyPageVlistNumURL += userID;
            String response = new PythonHTTPHandler().execute(getMyPageVlistNumURL, "GET",userID).get();
            Log.d("TAG","getMyPageVlistNumFromServer 실행중이다");
            //getMyPageVlistNumURL += URLEncoder.encode(userID,"UTF-8");
            Log.d("TAG",userID);
            Log.d("TAG",getMyPageVlistNumURL);
           // String response = new PythonHTTPHandler().execute(getMyPageVlistNumURL, "GET", userID, "userID").get();

            int cnt = 0;
            char[] rp = response.toCharArray();
            String newstring = "";
            for(int i = 0 ; i<rp.length;i++){
                if(rp[i] == 'u'){
                    if(rp[i+1] == '\'')
                        newstring += rp[++i];
                }else{
                    newstring += rp[i];
                }
                Log.i("newstring",newstring);
            }
            JSONObject jsonObject = new JSONObject(newstring);

            vlistNum = jsonObject.get("data")+"";

            Log.d("TAG TraceNumber :  ",vlistNum);

            Log.d("TAG","getMyPageVlistNumFromServer 끝");
        //} catch (InterruptedException | ExecutionException | UnsupportedEncodingException | JSONException e) {
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return vlistNum;
    }

    //방문기록 리스트 가져오는 함수
    @Override
   public ArrayList<MyPageVisitedlistItem> getMyPageVlistFromServer(String userID,ArrayList<MyPageVisitedlistItem>  visitedList) {
            String newstring = "";

            String MyPageVlistURL = pythonDataServerURL + "/visit?userID=";

            try {
                MyPageVlistURL += URLEncoder.encode(userID,"UTF-8");
                //MyPageVlistURL += userID;
                String response = new PythonHTTPHandler().execute(MyPageVlistURL, "GET",userID).get();

                char [] rech = response.toCharArray();

                int c = 0;
                String str = "";
                for (int i = 0; i < rech.length; i++) {
                    if (rech[i] == 'u') {
                        if (rech[i + 1] == '\'')
                            str += rech[++i];
                        else
                            str+= rech[i];
                    } else {
                        str += rech[i];
                    }
                }
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jarray = jsonObject.getJSONArray("data");

                for(int i=0;i<jarray.length();i++){
                    jarray.getJSONObject(i);
                    Log.d("TAG","jarray["+i+"] : "+jarray.get(i));

                    String sc_vPlace = jarray.getJSONObject(i).getString("placeName");
                    String sc_vDate = jarray.getJSONObject(i).getString("visitDate");
                    String sc_vID = jarray.getJSONObject(i).getString("visitID");

                    boolean isin = false;
                    for(int j = 0 ; j<visitedList.size();j++){
                        if(visitedList.get(j).getvID().equals(sc_vID)){
                            isin = true;
                            break;
                        }
                    }
                    if(!isin){
                        MyPageVisitedlistItem sc_visit_list = new MyPageVisitedlistItem(sc_vDate,sc_vPlace,sc_vID);
                        visitedList.add(sc_visit_list);
                    }

                }
            } catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        return visitedList;
        }

    //마이페이지 내가 쓴 리뷰 리스트 가져오는 함수
    public ArrayList<MyPageReviewlistItem> getMyPageRlistFromServer(String userID,ArrayList<MyPageReviewlistItem> writtenList){
        String newstring = "";

        String MyPageRlistURL = pythonDataServerURL + "/reviews/byuser?userID=";

        try {
            MyPageRlistURL += URLEncoder.encode(userID,"UTF-8");
            //MyPageRlistURL += userID;
            String response = new PythonHTTPHandler().execute(MyPageRlistURL, "GET",userID).get();

            char [] rech = response.toCharArray();

            int c = 0;
            String str = "";
            for (int i = 0; i < rech.length; i++) {
                if (rech[i] == 'u') {
                    if (rech[i + 1] == '\'')
                        str += rech[++i];
                    else
                        str+= rech[i];
                } else {
                    str += rech[i];
                }
            }
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jarray = jsonObject.getJSONArray("data");
            jarray.getJSONObject(0);

            for(int i=0;i<jarray.length();i++){
                jarray.getJSONObject(i);
                Log.d("TAG","jarray["+i+"] : "+jarray.get(i));

                String sc_rDate = jarray.getJSONObject(i).getString("writeDate");
                String sc_rPlace = jarray.getJSONObject(i).getString("placeName");
                String sc_rID = jarray.getJSONObject(i).getString("reviewID");
                String sc_rcontent = jarray.getJSONObject(i).getString("content");

                boolean isin = false;
                for(int j = 0 ; j<writtenList.size();j++){
//                    if(writtenList.get(j).getRdate().equals(sc_rDate)&&writtenList.get(j).getRplaceName().equals(sc_rPlace)){
//                        isin = true;
//                        break;
//                    }
                    if(writtenList.get(j).getrID().equals(sc_rID)){
                        isin = true;
                        break;
                    }
                }
                if(!isin){
                    MyPageReviewlistItem sc_review_list = new MyPageReviewlistItem(sc_rDate,sc_rPlace,sc_rcontent,sc_rID);
                    writtenList.add(sc_review_list);
                }
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return writtenList ;

    }

    //광고창보여주는거
    public ArrayList<AD> getAdToServer(String address) {
        String AdlistURL = pythonDataServerURL + "/ad?area=";
        //################33 url 만드는 부분 고침!!!
        final ArrayList<AD> adList = new ArrayList<>();


        try {
            //############ 이부분 주석처리함
            Log.d("인코딩 전", address);
            String address2 = java.net.URLEncoder.encode(new String(address.getBytes("UTF-8")));
//            Log.d("인코딩 후", address2);
            // AdlistURL += URLEncoder.encode(address,"UTF-8");
            AdlistURL += address2;
            String response = new PythonHTTPHandler().execute(AdlistURL, "GET", address2, "area").get();
            Log.d("TAG","리스폰스"+ response);
            //  JSONObject root = new JSONObject(response);

            char [] rech = response.toCharArray();
            Log.d("TAG","rech");
            Log.d("TAG",rech.toString());


            int c = 0;
            String str = "";
            for (int i = 0; i < rech.length; i++) {
                if (rech[i] == 'u') {
                    if (rech[i + 1] == '\'')
                        str += rech[++i];
                    else
                        str+= rech[i];
                } else {
                    str += rech[i];
                }
            }
            JSONObject jsonObject = new JSONObject(str);
            JSONArray dArray = jsonObject.getJSONArray("data");


            for(int i = 0; i<dArray.length(); i++){
                if(dArray.getJSONObject(i)!=null){
                    String vlocationCode = dArray.getJSONObject(i).getString("locationCode");;
                    String vplaceName = dArray.getJSONObject(i).getString("placeName");;
                    String vcontent = dArray.getJSONObject(i).getString("details");;
                    String vrank = dArray.getJSONObject(i).getString("ranking");;

                    AD addItem = new AD(vlocationCode, vplaceName, vcontent, Integer.parseInt(vrank));
                    Log.d("TAG", "AD 아이템들"+addItem.toString());
                    adList.add(i,addItem);
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return adList;
    }

    //서버에게 방문기록 추가요청하는 함수
    public void sendvisitToServer(VisitPlace visitplace){
        String visitURL = pythonDataServerURL+ "/visit";

        try {
            String response = new PythonHTTPHandler().execute(visitURL,"POST",gson.toJson(visitplace)).get();
            Log.i("sendvisit placename",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    //서버에게 검색기록 추가 요청하는 함수
    public void sendsearchToServer(SearchPlace searchPlace){
        String visitURL = pythonDataServerURL+ "/search";

        try {
            String response = new PythonHTTPHandler().execute(visitURL,"POST",gson.toJson(searchPlace)).get();
            Log.i("send search placename",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    //리뷰 삭제하는 함수(리뷰삭제)
    public void sendDeleteReviewToServer(String reviewID){
        String reviewDeleteURL = pythonDataServerURL+ "/reviews?reviewID=";

        try {
            reviewDeleteURL += reviewID;

            String response = new PythonHTTPHandler().execute(reviewDeleteURL, "DELETE",reviewID).get();
            Log.i("send review placename",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    //네비게이션 종료시 리뷰리스트에 추가 요청하는 함수
    public void sendReviewListToServer(ReviewListPlace reviewListPlace){
        String reviewlistaddURL = pythonDataServerURL+ "/reviewlist";

        try {
            String response = new PythonHTTPHandler().execute(reviewlistaddURL,"POST",gson.toJson(reviewListPlace)).get();
            Log.i("send search placename",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    //마이페이지 리뷰보기에서 리뷰리스트에서 삭제하는 함수 (리뷰리스트삭제)
    public void sendDeleteReviewListToServer(String userID,String placeName){
        //TODO ㄹ뷰리스트삭제 형식 맞는지 확인하기
        String reviewDeleteURL = pythonDataServerURL+ "/reviewlist?userID=";

        try {
            reviewDeleteURL += userID;
            reviewDeleteURL += "&placeName=";
            String response = new PythonHTTPHandler().execute(reviewDeleteURL,"DELETE",placeName).get();
            Log.i("send review placename",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    // 예린 - 후기작성 리스트 가져오기
    public ArrayList<ReviewLocationItem> getReviewLocationListFromServer(String userID) {
        ArrayList<ReviewLocationItem> resultLocationList = new ArrayList<>();

        String ReviewLocationListURL = pythonDataServerURL + "/reviewlist?userID=";

        try {
            ReviewLocationListURL += userID;
            String response = new PythonHTTPHandler().execute(ReviewLocationListURL, "GET",userID).get();
            Log.d("TAG","getReviewLocationListFromServer 요청");

            char [] rech = response.toCharArray();
            //Log.d("TAG","rech");
            //Log.d("TAG",rech.toString());

            int c = 0;
            String str = "";
            for (int i = 0; i < rech.length; i++) {
                Log.d("TAG","rech for문");
                if (rech[i] == 'u') {
                    if (rech[i + 1] == '\'')
                        str += rech[++i];
                    else
                        str+= rech[i];
                } else {
                    str += rech[i];
                }
                Log.d("TAG", "str : " + str);
            }
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jarray = jsonObject.getJSONArray("data");
            Log.d("TAG", jarray+"");
            jarray.getJSONObject(0);

            for(int i=0;i<jarray.length();i++){
                jarray.getJSONObject(i);
                Log.d("TAG","jarray["+i+"] : "+jarray.get(i));

                String sc_rDate = jarray.getJSONObject(i).getString("visitDate");
                Log.d("TAG","searchDate : "+sc_rDate);
                String sc_rPlace = jarray.getJSONObject(i).getString("placeName");
                Log.d("TAG","placeName : "+sc_rPlace);
                String sc_rCode = jarray.getJSONObject(i).getString("locationCode");
                Log.d("TAG","locationCode : "+sc_rCode);

                resultLocationList.add(new ReviewLocationItem(sc_rDate, sc_rPlace, sc_rCode));
                /*boolean isin = false;
                for(int j = 0 ; j<writtenList.size();j++){
                    if(writtenList.get(j).getRplaceName().equals(sc_rPlace)&&writtenList.get(i).getRdate().equals(sc_rDate)){
                        isin = true;
                        break;
                    }
                }
                if(!isin){
                    MyPageReviewlistItem sc_review_list = new MyPageReviewlistItem(sc_rDate,sc_rPlace,sc_rcontent);
                    writtenList.add(sc_review_list);
                    Log.d("TAG", "밖에서 추가"+sc_rPlace);
                }*/

            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return resultLocationList;
    }

    // 예린 - 리뷰 등록 시 이미지 업로드하고 그 URL받아오기
    public String uploadImageToServer(final String filePath) {
        final String uploadTraceURL = pythonImageServerURL + "/upload";
        final String encodeFormat = "UTF-8";
        new Thread(new Runnable() {
            MultipartUtility multipartUtility = null;

            @Override
            public void run() {
                try {
                    multipartUtility = new MultipartUtility(uploadTraceURL, encodeFormat);
                    multipartUtility.addFilePart("file", new File(filePath));

                    imageURL = multipartUtility.finish();
                    if (imageURL != null) {
                        Log.i("responseTest", imageURL);
                    }
                    else
                        Log.i("responseTest", "fail");

                    // 파라미터 추가가능
                    //multipartUtility.addFormField("key","value");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return imageURL;
    }

    // 예린 - 리뷰 등록
    public boolean registReviewToServer(RegistReviewObj review) {

        String registReviewURL = pythonDataServerURL + "/reviews";
        String response = null;

        try {
            response = new PythonHTTPHandler().execute(registReviewURL,"POST",gson.toJson(review)).get();
            Log.i("registReviewInstance",response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(response != null) {
            return true;
        }
        return false;
    }

    // 예린 - 리뷰 리스트에서 아이템 삭제
    public void deleteReviewListFromServer(String userID, String placeName) {
        String deleteReviewListURL = pythonDataServerURL + "/reviewlist?";

        try {

            String encodedName = java.net.URLEncoder.encode(new String(placeName.getBytes("UTF-8")));

            deleteReviewListURL += "userID=" + userID + "&placeName=" + encodedName;
            new PythonHTTPHandler().execute(deleteReviewListURL, "DELETE",userID).get();
            Log.d("TAG","deleteReviewListFromServer 요청");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 예린 - 장소별 리뷰 받아오기
    public ArrayList<ReviewItem> getReviewsFromServer(String userID, String locationCode) {
        ArrayList<ReviewItem> resultList = new ArrayList<>();

        String getReviewListURL = pythonDataServerURL + "/reviews?locationCode=";
        String getLikeStatusURL = pythonDataServerURL + "/like?";

        try {
            // 리뷰 리스트 가져오기
            getReviewListURL += java.net.URLEncoder.encode(new String(locationCode.getBytes("UTF-8")));
            String response_review = new PythonHTTPHandler().execute(getReviewListURL, "GET",userID).get();

            // 각 리뷰에 대한 좋아요 여부 가져오기
            getLikeStatusURL += "userID=" + userID + "&locationCode=" + java.net.URLEncoder.encode(new String(locationCode.getBytes("UTF-8")));
            String response_likeStatus = new PythonHTTPHandler().execute(getLikeStatusURL, "GET",userID).get();

            char [] rech = response_review.toCharArray();

            int c = 0;
            String str = "";
            for (int i = 0; i < rech.length; i++) {
                Log.d("TAG","rech for문");
                if (rech[i] == 'u') {
                    if (rech[i + 1] == '\'')
                        str += rech[++i];
                    else
                        str+= rech[i];
                } else {
                    str += rech[i];
                }
                Log.d("여기", "str : " + str);
            }
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jarray = jsonObject.getJSONArray("data");
            Log.d("여기", jarray+"");
            jarray.getJSONObject(0);

            for(int i=0;i<jarray.length();i++){
                jarray.getJSONObject(i);
                Log.d("TAG","jarray["+i+"] : "+jarray.get(i));

                String reviewID = jarray.getJSONObject(i).getString("reviewID");
                String location_code = jarray.getJSONObject(i).getString("locationCode");
                String placeName = jarray.getJSONObject(i).getString("placeName");
                String content = jarray.getJSONObject(i).getString("content");
                String writerID = jarray.getJSONObject(i).getString("userID");
                String writeDate = jarray.getJSONObject(i).getString("writeDate");
                String imageURL = jarray.getJSONObject(i).getString("imageURL");
                String rating = jarray.getJSONObject(i).getString("rating");
                String likeNum = jarray.getJSONObject(i).getString("likeNum");

                //String reviewID, String locationCode, String placeName, String writerName, String date, String reviewImg, String content, int likeCnt
                ReviewItem item = new ReviewItem(reviewID, location_code, placeName, writerID, writeDate, imageURL, content, Float.parseFloat(rating), Integer.parseInt(likeNum));
                //Log.d("바로여기", item.toString());

                // 각 리뷰에 대한 좋아요 여부 item 객체에 넣어주기
                char [] rech2 = response_likeStatus.toCharArray();

                str = "";
                for (int j = 0; j < rech2.length; j++) {
                    Log.d("TAG","rech for문");
                    if (rech2[j] == 'u') {
                        if (rech2[j + 1] == '\'')
                            str += rech2[++j];
                        else
                            str+= rech2[j];
                    } else {
                        str += rech2[j];
                    }
                    Log.d("여기", "str : " + str);
                }
                JSONObject jsonObject2 = new JSONObject(str);
                JSONArray jarray2 = jsonObject2.getJSONArray("data");
                Log.d("여기", jarray2+"");
                if(jarray2 == null) {
                    item.setLikeState('n');
                } else {
                    for(int j=0;j<jarray2.length();j++) {
                        jarray2.getJSONObject(j);
                        Log.d("제발 ㅠㅠ", "jarray2["+i+"] : "+jarray2.get(j));

                        // 여긴보류
                        if(item.getReviewID().equals(jarray2.getJSONObject(j).getString("reviewID"))) {
                            item.setLikeState(jarray2.getJSONObject(j).getString("status").charAt(0));
                        }

                        //String reviewID = jarray2.getJSONObject(i).getString("reviewID");
                    }
                }

                resultList.add(item);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    // 예린 - 장소 통계 가져오기 (별점평균, 리뷰 개수)
    // ReviewItem의 rating, commentCnt에 저장해서 넘겨줌!!!
    public ReviewItem getStatsFromServer(String userID, String locationCode) {
        ReviewItem resultItem = null;

        String getReviewCntURL = pythonDataServerURL + "/reviews/count?locationCode=";  //리뷰 총 개수
        String getAVGRatingURL = pythonDataServerURL + "/rating?locationCode=";   // 별점 평균

        try {
            getReviewCntURL += java.net.URLEncoder.encode(new String(locationCode.getBytes("UTF-8")));
            String response = new PythonHTTPHandler().execute(getReviewCntURL, "GET",userID).get();
            Log.d("헬로헬로 리뷰개수", response);

            char [] rech = response.toCharArray();

            int c = 0;
            String str = "";
            for (int i = 0; i < rech.length; i++) {
                Log.d("TAG","rech for문");
                if (rech[i] == 'u') {
                    if (rech[i + 1] == '\'')
                        str += rech[++i];
                    else
                        str+= rech[i];
                } else {
                    str += rech[i];
                }
                Log.d("TAG", "str : " + str);
            }
            JSONObject jsonObject = new JSONObject(str);
            String reviewCnt = jsonObject.getString("data");
            Log.d("여기야ㅑㅑㅑㅎㅎ???11", reviewCnt);

            getAVGRatingURL += java.net.URLEncoder.encode(new String(locationCode.getBytes("UTF-8")));
            response = new PythonHTTPHandler().execute(getAVGRatingURL, "GET",locationCode).get();
            Log.d("여기야ㅑㅑㅑㅎㅎ???22", response);

            rech = response.toCharArray();
            str = "";

            for (int i = 0; i < rech.length; i++) {
                Log.d("TAG","rech for문");
                if (rech[i] == 'u') {
                    if (rech[i + 1] == '\'')
                        str += rech[++i];
                    else
                        str+= rech[i];
                } else {
                    str += rech[i];
                }
                Log.d("TAG", "str : " + str);
            }
            jsonObject = new JSONObject(str);
            String avgRating = jsonObject.getString("data");
            Log.d("여기흠", avgRating);

            resultItem = new ReviewItem(Integer.parseInt(reviewCnt), Float.parseFloat(avgRating));
//            resultItem.setCommentCnt(Integer.parseInt(reviewCnt));
//            resultItem.setRating(Float.parseFloat(avgRating));

            Log.d("여기ㅣㅣㅣ1111", resultItem.toString());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("여기ㅣㅣㅣ22222", resultItem.toString());
        return resultItem;
    }
}