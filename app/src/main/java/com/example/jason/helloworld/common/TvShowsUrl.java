package com.example.jason.helloworld.common;

public class TvShowsUrl {
    //http://192.168.199.210/  http://114.215.132.250
    private final static String BASE_URL = "http://192.168.199.210:8081";
    public final static String LOGIN_URL = BASE_URL + "/user/login";
    public final static String BASE_LOAD_IMAGE_URL = BASE_URL + "/pics/";
    public final static String TVSHOWS_URL = BASE_URL + "/tvShow/listLatest";
    public final static String PIC_URL = BASE_URL + "/pics/";
    public final static String UPLOAD_PIC_URL = BASE_URL + "/upload/picture";
    public final static String SEND_ACTIVITY = BASE_URL + "/activity/send";
}
