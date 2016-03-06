package com.example.jason.helloworld.common;

public class TvShowsUrl {
    //http://192.168.199.210/  http://114.215.132.250
    public final static String BASE_URL = "http://192.168.199.210:8081";
    public final static String LOGIN_URL = BASE_URL + "/user/login";
    public final static String BASE_LOAD_IMAGE_URL = BASE_URL + "/pics/";
    public final static String USER_INFO_URL = BASE_URL + "/user/";
    public final static String USER_IF_REPEAT = BASE_URL + "/user/checkUsernameIfRepeat/";
    public final static String USER_REGISTER = BASE_URL + "/user/register";
    public final static String TVSHOWS_URL = BASE_URL + "/tvShow/listLatest";
    public final static String PIC_URL = BASE_URL + "/pics/";
    public final static String UPLOAD_PIC_URL = BASE_URL + "/upload/picture";
    public final static String SEND_ACTIVITY = BASE_URL + "/activity/send";
    public final static String ACTIVITY_IMAGE_URL = BASE_URL;
    public final static String ACTIVITIES_GET = BASE_URL + "/activity/3";
    public final static String ACTIVITIES_GET_ALL = BASE_URL + "/activity/all";
    //TODO dynamic generate the user id
}
