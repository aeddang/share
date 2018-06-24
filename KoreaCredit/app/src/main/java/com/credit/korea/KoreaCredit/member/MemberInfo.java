package com.credit.korea.KoreaCredit.member;


import android.content.SharedPreferences;
import android.util.Log;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.SNSManager;
import com.credit.korea.KoreaCredit.card.book.BookInfo;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyPageInfo;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.datamanager.DataManager;
import lib.sns.SNSUserObject;


public class MemberInfo implements DataManager.JsonManagerDelegate,SNSManager.SNSManagerDelegate {

    public static final String NOME_SELECTED="선택안함";

    public static final String[] TYPE_SNS={"google","naver","face","kakao"};

    private static MemberInfo instence;
	private SharedPreferences settings;
    private DataManager jsonManager,jsonLoaderGET;
    public JoinInfoDelegate joinDelegate;
    public FindInfoDelegate findDelegate;
    public LoginInfoDelegate loginDelegate;
    public WhereInfoDelegate whereDelegate;
    public ModifyInfoDelegate modifyDelegate;

    private String userID,userPW,smsTime,modifyUserPW;
    private int snsType;
    private boolean isLogin,finalSendSms;
    private ArrayList<String> sidoLists;
    private Map<String,ArrayList<String>> gugunLists;
    private String currentSIDO;

    public String pushToken;

    public interface WhereInfoDelegate
    {

        void onLoadSiDo(ArrayList<String> lists);
        void onLoadGuGun(ArrayList<String> lists);


    }

    public interface ModifyInfoDelegate
    {

        void onLoadModify(boolean result);

    }
    public interface JoinInfoDelegate
    {
        void onSendPw(boolean result);

        void onLoadCheckID(boolean able);
        void onLoadConfirmPW(boolean result);
        void onLoadJoin(boolean result);

    }
    public interface FindInfoDelegate
    {
        void onFindID(boolean result);
        void onFindPW(boolean result);

    }
    public interface LoginInfoDelegate
    {
        void onLogin(boolean result);
        void onLogout(boolean result);
    }

	public static MemberInfo getInstence(){

        if(instence==null){
            instence=new MemberInfo();

        }

        return instence;
	}

    public MemberInfo() {


        settings =  MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
        pushToken="";
        instence=this;
        userID = settings.getString(Config.KEY_ID,"");
        userPW = settings.getString(Config.KEY_PW,"");
        snsType = settings.getInt(Config.KEY_SNS_TYPE,-1);
        Log.i("","snsType : "+snsType+" userID : "+userID+" userPW : "+userPW);
        if(snsType!=-1){
            isLogin = true;
        }else{

            if(!userID.equals("") &&!userPW.equals("")) {
                isLogin = true;
            }else{
                isLogin = false;
            }
        }

        sidoLists=new ArrayList<String>();
        gugunLists=new HashMap<String,ArrayList<String>>();



        ArrayList<String> lists=new ArrayList<String>();
        lists=new ArrayList<String>();


        jsonLoaderGET=new DataManager("GET");
        jsonLoaderGET.setOnJsonDelegate(this);
        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);



    }

    public boolean loadLoginState(){

        if(isLogin==true){
            if(snsType==-1) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userID);
                params.put("pw", userPW);
                loadLogin(params);
            }else{
                SNSManager.getInstence().delegate=this;
                SNSManager.getInstence().autoLogin(snsType);

            }


        }

        return isLogin;
    }


    public void onLogin(SNSManager m, SNSUserObject obj, int type){

        Map<String,String> params=new HashMap<String,String>();
        params.put("id",obj.userSEQ);
        params.put("pw",obj.userToken);

        loadLogin(params);

    }
    public void onLogout(SNSManager m, int type){


    }
    public void onError(SNSManager m,int type ,int eType){

        logoutFn();
        MainActivity.getInstence().viewAlert("",R.string.msg_login_sns_fail,null);
        PageObject pInfo=new PageObject(Config.PAGE_MEMBER);
        pInfo.info=new HashMap<String,Object>();
        pInfo.info.put("pageIdx",3);
        MainActivity.getInstence().changeView(pInfo);

    }
    public boolean getLoginState(){

        return isLogin;
    }

    public String getID(){

        return userID;
    }

    public String getPW(){

        return userPW;
    }
    public void setSmsTime(String stmp){
        if(stmp.equals("")==true){

            return;
        }

        smsTime = stmp;

    }
    public long getSmsTime(){

        if(smsTime.equals("")==true){
            return 0;
        }


        return Long.valueOf(smsTime);

    }
    public void registerSNS(int type){

        snsType=type;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Config.KEY_SNS_TYPE,type);
        editor.commit();

    }
    public void unregisterSNS(int type){

       if(snsType==type){

           SharedPreferences.Editor editor = settings.edit();
           editor.putInt(Config.KEY_SNS_TYPE,-1);
           editor.commit();
       }


    }
    public void registerSMS(boolean isReceive){

        //  Log.i("","jsonStr : "+jsonStr);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Config.RECIEVE_SMS,isReceive);
        editor.commit();

    }

    public void loadCheckID(String id){

        Map<String,String> sendParams=new HashMap<String,String>();

        sendParams.put("userid",id);

        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_CHECK_ID,sendParams);


    }
    public void onLoadCheckID(JSONArray results){

        boolean isSuccess=DataFactory.getInstence().getSuccess(results);

        if(joinDelegate!=null){
            joinDelegate.onLoadCheckID(isSuccess);
        }


    }

    public void sendPW(boolean isSMS,String value){

        Map<String,String> params=new HashMap<String,String>();
        params.put("sendvalue",value);
        if(isSMS==true){
            params.put("sendtype","hp");
        }else{
            params.put("sendtype","email");
        }

        MainActivity.getInstence().loadingStart(true);
        jsonManager.loadData(Config.API_SEND_PW,params);



    }

    public void onLoadSendPW(JSONArray results){

        boolean isSuccess=DataFactory.getInstence().getSuccess(results);
        if(joinDelegate!=null){
            joinDelegate.onSendPw(isSuccess);
        }


    }
    public void loadConfirmPW(boolean isSMS,String value,String pw){

        //http://www.koreacreditcard.com/app/send_key.asp


        Map<String,String> params=new HashMap<String,String>();
        params.put("sendvalue",value);
        if(isSMS==true){
            params.put("sendtype","hp");
        }else{
            params.put("sendtype","email");
        }
        params.put("rcvnum",pw);
        MainActivity.getInstence().loadingStart(true);
        jsonManager.loadData(Config.API_SEND_PW_CHECK,params);

    }
    public void onLoadConfirmPW(JSONArray results){

        boolean isSuccess=DataFactory.getInstence().getSuccess(results);
        if(joinDelegate!=null){
            joinDelegate.onLoadConfirmPW(isSuccess);
        }


    }
    public void loadSido(){

         if(sidoLists.size()<1){

             MainActivity.getInstence().loadingStart(false);
             jsonManager.loadData(Config.API_LOAD_SIDO);

         }else{
             if(whereDelegate!=null){
                 whereDelegate.onLoadSiDo(sidoLists);
             }

         }

    }
    public void loadGuGun(String sido){

        ArrayList<String> lists=gugunLists.get(sido);


        if(lists==null){
            currentSIDO=sido;


            MainActivity.getInstence().loadingStart(false);
            jsonLoaderGET.loadData(Config.API_LOAD_GUGUN+"?sido="+DataFactory.getInstence().getUriencode(sido));
        }else{
            if(whereDelegate!=null){
                whereDelegate.onLoadGuGun(lists);
            }

        }

    }
    private void sidoComplete(JSONArray results){

        sidoLists.add(NOME_SELECTED);
        for(int i=0;i<results.length();++i){
            JSONObject sido=null;
            try {
                sido=results.getJSONObject(i);
                sidoLists.add(sido.getString("sido"));

            } catch (JSONException e) {

            }


        }
        if(whereDelegate!=null){
            whereDelegate.onLoadSiDo(sidoLists);
        }


    }
    private void gugunComplete(JSONArray results){



        ArrayList<String> lists=new ArrayList<String>();
        lists.add(NOME_SELECTED);
        for(int i=0;i<results.length();++i){
            JSONObject gugun=null;
            try {
                gugun=results.getJSONObject(i);
                lists.add(gugun.getString("gugun"));

            } catch (JSONException e) {

            }


        }
        if(lists.size()>0){
            gugunLists.put(currentSIDO,lists);
            if(whereDelegate!=null){
                whereDelegate.onLoadGuGun(lists);
            }
        }




    }

    public void loadJoin(Map<String,String> params,boolean sendSms){

        finalSendSms = sendSms;
        params.put("phone_code",pushToken);
        params.put("phone_os","Android");
        params.put("phone_type",android.os.Build.MODEL);
        if(snsType==-1){

            params.put("login_type","");
        }else{
            params.put("login_type",TYPE_SNS[snsType]);

        }

        userID = (String)params.get("userid");
        userPW = (String)params.get("pass");

        MainActivity.getInstence().loadingStart(true);


        jsonManager.loadData(Config.API_JOIN,params);
    }
    private void joinComplete(JSONArray results){

        if(joinDelegate!=null){

            boolean isSuccess=DataFactory.getInstence().getSuccess(results);
            if(isSuccess==true){
                isLogin=true;
                //loadLoginState();

            }else{
                userID = "";
                userPW = "";
            }


            joinDelegate.onLoadJoin(isSuccess);
        }
    }


    public void loadModify(Map<String,String> params,boolean sendSms){



        modifyUserPW  = params.get("pass");

        finalSendSms = sendSms;
        params.put("uid", getID());

        MainActivity.getInstence().loadingStart(true);
        jsonManager.loadData(Config.API_MODIFY_MEMBER,params);
    }
    private void onModifyMember(JSONArray results){
        if(finalSendSms==true){

            DataFactory.getInstence().readSMSMessage();
            finalSendSms=false;
        }


        boolean isSuccess=DataFactory.getInstence().getSuccess(results);
        if(isSuccess==true){
            if(modifyUserPW!=null) {
                userPW = modifyUserPW;
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(Config.KEY_PW,userPW);
                editor.commit();
            }

        }
        modifyUserPW=null;

        if(modifyDelegate!=null){
            modifyDelegate.onLoadModify(isSuccess);
        }
        ConsumeInfo.getInstence().resetAutoUpdate();

    }
    public void memberExit(){
        Map<String,String> params = new HashMap<>();
        params.put("id",getID());
        if(snsType!=-1) {
            params.put("login_type", TYPE_SNS[snsType]);
        }else{

            params.put("login_type", "");
        }
        jsonManager.loadData(Config.API_EXIT_MEMBER,params);


    }
    private void exitComplete(JSONArray results){

        boolean isSuccess=DataFactory.getInstence().getSuccess(results);

        if(isSuccess){
            logOut();
            MainActivity.getInstence().changeViewMain();
        }else{
            MainActivity.getInstence().viewAlert("",R.string.msg_member_exit_fail,null);

        }

    }

    public void logOut(){


        logoutFn();
        if(loginDelegate!=null){
            loginDelegate.onLogout(true);
        }

    }
    private void logoutFn(){

        unregisterSNS(snsType);
        userID="";
        userPW="";
        snsType=-1;
        isLogin=false;
        registerSMS(false);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Config.KEY_ID,userID);
        editor.putString(Config.KEY_PW,userPW);
        editor.commit();

        SNSManager.getInstence().logoutAll();
        ConsumeInfo.getInstence().resetInfo();
        MyCardInfo.getInstence().resetInfo();
        MyPageInfo.getInstence().resetInfo();
        BookInfo.getInstence().resetInfo();

    }
    public void loadLogin(Map<String,String> params){
        Map<String,String> sendParams=new HashMap<String,String>();
        userID=params.get("id");
        userPW=params.get("pw");
        if(snsType==-1){

            sendParams.put("login_type","");
        }else{
            sendParams.put("login_type",TYPE_SNS[snsType]);

        }

        isLogin=false;
        sendParams.put("id",userID);
        sendParams.put("pass",userPW);
        MainActivity.getInstence().loadingStart(true);
        jsonManager.loadData(Config.API_LOGIN,sendParams);
    }
    private String getStringValue(JSONObject obj,String key){
        String value="";
        try {
            value=obj.getString(key);
        } catch (JSONException e) {

            Log.i("","JSONException : "+key);

        }
        return value;
    }
    private void loginComplete(JSONArray results){
        Log.i("","loginComplete");
        try {
            JSONObject obj=results.getJSONObject(0);
            String result=obj.getString("result");

            Log.i("","loginComplete : "+result);
            if(result.equals("OK")==true){
                isLogin=true;

                SharedPreferences.Editor editor = settings.edit();
                String sms=getStringValue(obj,"sms1");

                smsTime=getStringValue(obj,"sms_time");

                boolean isSMS=sms.equals("Y");
                registerSMS(isSMS);
                editor.putString(Config.KEY_ID,userID);
                editor.putString(Config.KEY_PW,userPW);
                editor.commit();

                ConsumeInfo.getInstence().resetInfo();
                MyCardInfo.getInstence().resetInfo();
                MyPageInfo.getInstence().resetInfo();


                if(finalSendSms==true){

                    DataFactory.getInstence().readSMSMessage();
                    finalSendSms=false;
                }

                if(loginDelegate!=null){
                    loginDelegate.onLogin(true);
                }else{
                    MainActivity.getInstence().changeViewMain();

                }
            }else{

                logoutFn();
                onLoginError(result);

            }


        } catch (JSONException e) {
            Log.i("","loginComplete : JSONException");
            logoutFn();
            onLoginError("");

        }



    }
    private void onLoginError(String type){
        Log.i("","loginComplete type : "+type);

        if(type.equals("fail_107")==true){
            MainActivity.getInstence().viewAlert("",R.string.msg_login_complete_fail_pw,null);
        }else if(type.equals("fail_109")==true){

            MainActivity.getInstence().viewAlert("",R.string.msg_login_complete_fail_id,null);
        }else{
            MainActivity.getInstence().viewAlert("",R.string.msg_login_complete_fail,null);
        }
        if(loginDelegate!=null){

            loginDelegate.onLogin(false);
        }else{

            PageObject pInfo=new PageObject(Config.PAGE_MEMBER);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("pageIdx",3);
            MainActivity.getInstence().changeView(pInfo);
        }


    }

    public void loadFindIDPW(Map<String,String> params,String type){
        Map<String,String> sendParams=new HashMap<String,String>();
        if(params.get("sms").equals("Y")==true){
            sendParams.put("ftype","hp");
            sendParams.put("hp",params.get("phone"));
            sendParams.put("email","");
        }else{
            sendParams.put("ftype","email");
            sendParams.put("email",params.get("email"));
            sendParams.put("hp",params.get(""));
        }
        if(type.equals(PageFind.TYPE_ID)==true){
            sendParams.put("opt","id");
        }else{
            sendParams.put("opt","pw");
        }

        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_FIND_IDPW, sendParams);


    }
    private void findComplete(JSONArray results){

        try {
            JSONObject obj=results.getJSONObject(0);
            String result=obj.getString("result");
            String opt=obj.getString("opt");
            if(result.equals("OK")==true){


                if(findDelegate!=null){
                    if(opt.equals("id")==true) {
                        findDelegate.onFindID(true);
                    }else{
                        findDelegate.onFindPW(true);
                    }
                }
            }else{

                if(findDelegate!=null){
                    if(opt.equals("id")==true) {
                        findDelegate.onFindID(false);
                    }else{
                        findDelegate.onFindPW(false);
                    }
                }
            }


        } catch (JSONException e) {
            userID="";
            userPW="";
            isLogin=false;
            if(loginDelegate!=null){
                loginDelegate.onLogin(false);
            }
        }



    }

    public void onJsonCompleted(DataManager manager,String path,JSONObject result)
    {
        MainActivity.getInstence().loadingStop();
        JSONArray results = null;
        try {
            results = result.getJSONArray("datalist");
        } catch (JSONException e) {
            Log.i("", "error path :" + path);
            MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
            return;
        }

        if(path.equals(Config.API_LOGIN)){

              loginComplete(results);

        }else if(path.equals(Config.API_FIND_IDPW)){

              findComplete(results);

        }else if(path.equals(Config.API_JOIN)){

              joinComplete(results);

        }else if(path.equals(Config.API_LOAD_SIDO)){

             sidoComplete(results);

        }else if(path.indexOf(Config.API_LOAD_GUGUN)!=-1){

            gugunComplete(results);

        }else if(path.equals(Config.API_CHECK_ID)){

            onLoadCheckID(results);

        }else if(path.equals(Config.API_MODIFY_MEMBER)){

            onModifyMember(results);

        }else if(path.equals(Config.API_SEND_PW)){

            onLoadSendPW(results);

        }else if(path.equals(Config.API_SEND_PW_CHECK)){

            onLoadConfirmPW(results);

        }else if(path.equals(Config.API_EXIT_MEMBER)){

            exitComplete(results);

        }

    }
    public void onJsonParseErr(DataManager manager,String path) {
        MainActivity.getInstence().loadingStop();
        MainActivity.getInstence().viewAlert("",R.string.msg_data_parse_err,null);
    }
    public void onJsonLoadErr(DataManager manager,String path) {
        MainActivity.getInstence().loadingStop();
        MainActivity.getInstence().viewAlert("",R.string.msg_network_err,null);




    }


}

