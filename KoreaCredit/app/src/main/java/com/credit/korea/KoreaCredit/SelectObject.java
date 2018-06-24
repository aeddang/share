package com.credit.korea.KoreaCredit;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class SelectObject
{


    public static final String NONE_ID="NONE";
    public static final String NONE_TITLE="직접입력";

    public String id,title;

    public SelectObject() {
        id="";
        title ="";

    }
    public void setNoneData(){
        id=NONE_ID;
        title =NONE_TITLE;

    }
    public void setData(JSONObject obj){
        if(obj==null){

            return;
        }

        try {

            id=obj.getString("sv_idx");
            title =obj.getString("value");



        } catch (JSONException e) {

            Log.i("", "JSONException : " + e.toString());

        }

    }

    public void setDataBenit(JSONObject obj){

        try {

            id=obj.getString("group_idx");
            title =obj.getString("group_value");



        } catch (JSONException e) {

            Log.i("", "JSONException : " + e.toString());

        }

    }
    public void setDataBenitSub(JSONObject obj){

        try {

            id=obj.getString("sub_idx");
            title =obj.getString("sub_value");



        } catch (JSONException e) {

            Log.i("", "JSONException : " + e.toString());

        }

    }

    public void setDataCompany(JSONObject obj){

        try {

            id=obj.getString("sv_idx");
            title =obj.getString("sv_value");



        } catch (JSONException e) {

            Log.i("", "JSONException : " + e.toString());

        }

    }



}






