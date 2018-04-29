package com.dragon4.owo.ar_trace.Model;

import java.util.List;

public class User {

    private String userID;
    private String userName;
    private String userEmail;
    private String userImageURL;
    private String userToken;
    private List<TracePointer> userTraceList;

    private static User currentUser;

    public static User getMyInstance() {
        if (currentUser == null){
            currentUser = new User();
        }
        return currentUser;
    }
    public static void setMyInstance(User user) { currentUser = user;}

    public User() {
    }

    public void setUserAttribute(String userID, String userName, String userEmail, String userImageURL) {
        setuserID(userID);
        setUserName(userName);
        setUserEmail(userEmail);
        setUserImageURL(userImageURL);
        /*
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImageURL = userImageURL;
        */
    }


    public String getuserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<TracePointer> getUserTraceList() {
        return userTraceList;
    }

    public void setUserTraceList(List<TracePointer> userTraceList) {
        this.userTraceList = userTraceList;
    }
}
