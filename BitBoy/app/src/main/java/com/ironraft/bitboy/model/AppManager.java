package com.ironraft.bitboy.model;



import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ironraft.bitboy.Config;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.bitdata.BitGraphInfo;
import com.ironraft.bitboy.model.bitdata.BitGraphObject;
import com.ironraft.bitboy.model.bitdata.BitObject;
import com.ironraft.bitboy.model.bitdata.BitSet;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.CustomTimer;
import lib.datamanager.DataManager;
import lib.observers.ObserverController;


public class AppManager extends Object implements DataManager.JsonManagerDelegate
{
    private final String ORIGIN_PUSH_TOKEN_KEY="ORIGIN_PUSH_TOKEN_KEY";
    private final String PUSH_TOKEN_KEY="PUSH_TOKEN_KEY";
    private final String PUSH_ABLE_KEY="PUSH_ABLE_KEY";

    private static AppManager instence;

    public static AppManager getInstence()
    {
        if(instence == null){

            instence = new AppManager();
        }
        return instence;
    }
    private DataManager jsonManager;
    private SharedPreferences shared;


    public AppManager() {

        instence=this;
        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);
        shared = MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
        initRegisterPushToken();
    }
    public  void removeInfo()
    {
        if(jsonManager != null)
        {
            jsonManager.destory();
            jsonManager=null;
        }
    }


    public boolean getPushAble()
    {
        if(shared.getString(ORIGIN_PUSH_TOKEN_KEY,"") == "") return false;
        return shared.getBoolean(PUSH_ABLE_KEY,true);
    }

    public void setPushAble(boolean pushAble)
    {
        String token = shared.getString(ORIGIN_PUSH_TOKEN_KEY,"");
        if(token == "") return;
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(PUSH_ABLE_KEY,pushAble);
        editor.commit();
        if(pushAble ==true)
        {
            registerPushToken(token);
        }
        else
        {
            unregisterPushToken(token);
        }
    }

    private String getPushToken()
    {
        return shared.getString(PUSH_TOKEN_KEY,"");
    }

    private void removePushToken()
    {
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(PUSH_TOKEN_KEY,"");
        editor.commit();
    }

    private void setPushToken(String token)
    {
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(PUSH_TOKEN_KEY,token);
        editor.putString(ORIGIN_PUSH_TOKEN_KEY,token);
        editor.commit();
    }

    public void initRegisterPushToken()
    {
        if(getPushAble()==false) return;
        String token = getPushToken();
        if(token == "") return;
        registerPushToken(token);
    }
    public void sendMsg()
    {
        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("title_kor","제목" );
            jsonObject.put("notification_kor","알림" );
            jsonObject.put("msg_kor","내용" );
            jsonObject.put("title_eng","title" );
            jsonObject.put("notification_eng","notification" );
            jsonObject.put("msg_eng","msg" );

        } catch (JSONException e) {
        }
        String jsonStr = "";
        try {
            jsonStr = URLEncoder.encode(jsonObject.toString(), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
        }

        if(getPushAble() == false) return;
        Map<String,String> sendParams=new HashMap<String, String>();
        sendParams.put("id","root");
        sendParams.put("pw","dkdldjs333$");
        sendParams.put("value",jsonStr);

        jsonManager.loadData(Config.API_INPUT_MSG,sendParams);

    }
    private void registerTopic(String topic)
    {
        FirebaseMessaging.getInstance().subscribeToTopic(topic + "_" + LanguageFactory.getInstence().getLanguageType());
    }
    private void unregisterTopic(String topic)
    {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic + "_" + LanguageFactory.getInstence().getLanguageType());
    }
    public void updatePushToken()
    {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TopicManager.TOPIC_NEWS + "_" + LanguageFactory.ENG);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TopicManager.TOPIC_NEWS + "_" + LanguageFactory.KOR);
        registerTopic(TopicManager.TOPIC_NEWS);
        /*
        if(getPushAble() == false) return;
        Map<String,String> sendParams=new HashMap<String, String>();
        String token = shared.getString(ORIGIN_PUSH_TOKEN_KEY,"");
        sendParams.put("token",token);
        sendParams.put("lang", LanguageFactory.getInstence().getLanguageType());
        jsonManager.loadData(Config.API_REGISTER_PUSH_TOKEN,sendParams);
        */

    }
    public void registerPushToken(String token)
    {
        setPushToken(token);
        registerTopic(TopicManager.TOPIC_NEWS);
        /*
        if(getPushAble() == false) return;
        Map<String,String> sendParams=new HashMap<String, String>();
        sendParams.put("token",token);
        sendParams.put("lang", LanguageFactory.getInstence().getLanguageType());
        jsonManager.loadData(Config.API_REGISTER_PUSH_TOKEN,sendParams);
        */

    }
    public void unregisterPushToken(String token)
    {
        unregisterTopic(TopicManager.TOPIC_NEWS);
        /*
        setPushToken(token);
        Map<String,String> sendParams=new HashMap<String, String>();
        sendParams.put("token",token);
        jsonManager.loadData(Config.API_UNREGISTER_PUSH_TOKEN,sendParams);
        */
    }

    public void onJsonCompleted(DataManager manager, String path, JSONObject result) {
        Boolean isAble = APIManager.getInstence().getResultAble(result, path);
        if (isAble == false)
        {
            return;
        }
        removePushToken();
    }

    public void onJsonParseErr(DataManager manager, String path)
    {
    }
    public void onJsonLoadErr(DataManager manager, String path)
    {
    }

}






