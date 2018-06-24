package com.deliverycommerce;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;

public class SetupInfo {





	private final String IDEL_KEY="IDEL_KEY";
    private final String IDEL_TIME_KEY="IDEL_TIME_KEY";

	private static SetupInfo instence;
	private SharedPreferences settings;
    public static Boolean isBeaconSuport;
	public static SetupInfo getInstence(){

         return instence;
	}

    public SetupInfo() {
    	instence=this;
    	
    	settings =  MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isBeaconSuport=true;
        }else{

            isBeaconSuport=false;
        }

    	
    }

    

    public void registerPush(Boolean ac){
 	     SharedPreferences.Editor editor = settings.edit();
	     editor.putBoolean(Config.PUSH_KEY,ac);
	     editor.commit();
	  
    }
    public void registerIdel(Boolean ac){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IDEL_KEY,ac);
        editor.commit();

    }

    public void registerIdelTime(int t){
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(IDEL_TIME_KEY,t);
        editor.commit();

    }

    public Boolean getPush(){
        return settings.getBoolean(Config.PUSH_KEY,isBeaconSuport);

    }
    public Boolean getIdel(){
        return settings.getBoolean(IDEL_KEY,isBeaconSuport);

    }

    public int getIdelTime(){
        return settings.getInt(IDEL_TIME_KEY,0);

    }

}

