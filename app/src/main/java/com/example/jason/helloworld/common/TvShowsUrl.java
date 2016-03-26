package com.example.jason.helloworld.common;

public class TvShowsUrl {
    //http://192.168.199.210/  http://114.215.132.250
    public final static String BASE_URL = "http://192.168.199.210:8081";
    public final static String LOGIN_URL = BASE_URL + "/user/login";
    public final static String BASE_LOAD_IMAGE_URL = BASE_URL + "/pics/";
    public final static String USER_INFO_URL = BASE_URL + "/user/";
    public final static String USER_INFO_UPDATE = BASE_URL + "/user/update";
    public final static String USER_MODIFY_PWD = BASE_URL + "/user/updatePwd";
    public final static String USER_IF_REPEAT = BASE_URL + "/user/checkUsernameIfRepeat/";
    public final static String USER_REGISTER = BASE_URL + "/user/register";

    public final static String TVSHOWS_URL = BASE_URL + "/tvShow/listLatest";
    public final static String COLLECT_TVSHOW_URL = BASE_URL + "/tvShow";
    public final static String CANCLE_COLLECT_TVSHOW_URL = BASE_URL + "/tvShow/deleteChooseTvShow";
    public final static String CHECK_IF_TVSHOW_COLLECTED_URL = BASE_URL + "/tvShow/checkIfCollected";
    public final static String LIST_CHOOSED_TVSHOWS = BASE_URL + "/tvShow/listChooseTvShows";
    public final static String COMMENT_ACTIVITY = BASE_URL + "/activity/comments/comment";

    public final static String FIND_FOLLOWING = BASE_URL + "/follower/following";
    public final static String FIND_FOLLOWER = BASE_URL + "/follower/follower";
    public final static String CANCEL_FOLLOW = BASE_URL + "/follower";
    public final static String ADD_FOLLOW = BASE_URL + "/follower/followUser";


    public final static String PIC_URL = BASE_URL + "/pics/";
    public final static String UPLOAD_PIC_URL = BASE_URL + "/upload/picture";

    public final static String SEND_ACTIVITY = BASE_URL + "/activity/send";
    public final static String DELETE_ACTIVITY = BASE_URL + "/activity/delete";
    public final static String ACTIVITY_IMAGE_URL = BASE_URL;
    public final static String ACTIVITIES_GET = BASE_URL + "/activity";
    public final static String ACTIVITIES_GET_ALL = BASE_URL + "/activity/all";
    //TODO dynamic generate the user id
}
