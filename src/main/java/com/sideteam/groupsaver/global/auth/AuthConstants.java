package com.sideteam.groupsaver.global.auth;

public class AuthConstants {

    public final static String TOKEN_TYPE = "Bearer ";
    public final static String REFRESH_TOKEN = "RefreshToken";

    public final static String passwordRegexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$";
    public final static String phoneNumberRegexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$";

}
