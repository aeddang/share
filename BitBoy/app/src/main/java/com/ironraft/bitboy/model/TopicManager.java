package com.ironraft.bitboy.model;


import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ironraft.bitboy.Config;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TopicManager {

    private final String TOPIC_KEY="TOPIC_KEY";

    public static final String TOPIC_NEWS ="news";
    public static final String TOPIC_COIN ="coin";
    public static final String TOPIC_RISE ="rise";
    public static final String TOPIC_DROP ="drop";
    public static final String TOPIC_DEAL_M ="deal_m";
    public static final String TOPIC_DEAL_H ="deal_h";

    private static TopicManager instence;
    private SharedPreferences shared;
    public static TopicManager getInstence()
    {
        if(instence == null){

            instence = new TopicManager();
        }
        return instence;
	}
    public TopicManager()
    {
        shared = MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
    }

    public boolean getRegisterTopic(String topic)
    {
        return shared.getBoolean(TOPIC_KEY+"_"+topic,true);
    }

    public void setRegisterTopic(String topic,boolean pushAble)
    {
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(TOPIC_KEY+"_"+topic,pushAble);
        editor.commit();
        updateTopic(topic,pushAble);
    }
    public void unRegisterLanguage()
    {
        updateTopic(TOPIC_RISE,false);
        updateTopic(TOPIC_DROP,false);
        updateTopic(TOPIC_DEAL_M,false);
        updateTopic(TOPIC_DEAL_H,false);

    }
    public void registerLanguage()
    {
        updateTopic(TOPIC_RISE,true);
        updateTopic(TOPIC_DROP,true);
        updateTopic(TOPIC_DEAL_M,true);
        updateTopic(TOPIC_DEAL_H,true);

    }

    public void updateCoin(String key,boolean pushAble)
    {
        String addKey = "_"+TOPIC_COIN+"_"+key;
        if(pushAble==false)
        {
            unregisterTopic(TOPIC_RISE+addKey);
            unregisterTopic(TOPIC_DROP+addKey);
            unregisterTopic(TOPIC_DEAL_M+addKey);
            unregisterTopic(TOPIC_DEAL_H+addKey);
        }
        else
        {
            if(getRegisterTopic(TOPIC_RISE)) registerTopic(TOPIC_RISE+addKey);
            if(getRegisterTopic(TOPIC_DROP)) registerTopic(TOPIC_DROP+addKey);
            if(getRegisterTopic(TOPIC_DEAL_M)) registerTopic(TOPIC_DEAL_M+addKey);
            if(getRegisterTopic(TOPIC_DEAL_H)) registerTopic(TOPIC_DEAL_H+addKey);
        }
    }

    private void updateTopic(String topic,boolean pushAble)
    {
        ArrayList<BitTickerObject> datas = BitDataManager.getInstence().getCurrentBitLists(true);
        for(int i =0;i<datas.size();++i)
        {
            String key = topic+"_"+TOPIC_COIN+"_"+datas.get(i).seq;
            if(pushAble==true)
            {
                registerTopic(key);
            }
            else
            {
                unregisterTopic(key);
            }
        }
    }
    private void registerTopic(String topic)
    {
        String topicLang = topic + "_" + LanguageFactory.getInstence().getLanguageType();
        //Log.i("registerTopic",topicLang);
        FirebaseMessaging.getInstance().subscribeToTopic(topicLang);
    }

    private void unregisterTopic(String topic)
    {
        String topicLang = topic + "_" + LanguageFactory.getInstence().getLanguageType();
        //Log.i("unregisterTopic",topicLang);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicLang);
    }

}

