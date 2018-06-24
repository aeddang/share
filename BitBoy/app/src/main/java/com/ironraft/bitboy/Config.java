package com.ironraft.bitboy;


public class Config {
	public static final String PREFS_NAME="bitboy";



    public static final int PAGE_HOME=101;
    public static final int PAGE_MSG=201;
    public static final int PAGE_COIN=301;
    public static final int PAGE_ALARM=401;

    public static final int POPUP_SETUP=1100;
    public static final int POPUP_MSG=1200;
    public static final int POPUP_COIN_INFO=1300;

    public static final String LOCAL_FILE_PATH="bitboy";
    public static final String API_PATH="http://110.45.244.77:8800/bitboy/api/";

    public static final String API_REGISTER_PUSH_TOKEN =API_PATH+"registerpush.php";
    public static final String API_UNREGISTER_PUSH_TOKEN =API_PATH+"unregisterpush.php";
    public static final String API_LOAD_BIT_SETUP =API_PATH+"bithumb.json";
    public static final String API_LOAD_BIT_DATAS =API_PATH+"loaddata.php?seq=BT_ticker&type=ticker";
    public static final String API_INPUT_MSG =API_PATH+"inputmsg.php";
    public static final String API_LOAD_MSG =API_PATH+"loadmsg.php";


}
