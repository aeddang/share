package com.ironraft.bitboy;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;

import com.ironraft.bitboy.model.AppManager;
import com.ironraft.bitboy.model.TopicManager;


public class LanguageFactory {


    private static LanguageFactory instence;
    private final String LANG_KEY="LANG_KEY";

    public final static String ENG = "eng";
    public final static String KOR = "kor";
    private String type = ENG;
    private SharedPreferences shared;

	public static LanguageFactory getInstence()
    {
        if(instence == null) instence = new LanguageFactory();
        return instence;
	}

    public LanguageFactory()
    {
    	instence=this;
        shared = MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
        type = getLanguageType();
    }

    public String getLanguageType()
    {
        return shared.getString(LANG_KEY,ENG);
    }
    public void setLanguageType(String _type)
    {
        TopicManager.getInstence().unRegisterLanguage();
        type = _type;
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(LANG_KEY,type);
        editor.commit();

        MainActivity.getInstence().update();
        AppManager.getInstence().updatePushToken();
        TopicManager.getInstence().registerLanguage();
    }

    public int getResorceID(String value)
    {
        int rid = MainActivity.getInstence().getResources().getIdentifier( value+"_"+type, "string", MainActivity.getInstence().getPackageName());
        return rid;
    }
    public String getResorceString(String value)
    {

        int rid = getResorceID(value);
        String str = MainActivity.getInstence().getResources().getString(rid);
        return str;

    }




}

