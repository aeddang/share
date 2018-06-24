package com.credit.korea.KoreaCredit.mypage;


import com.credit.korea.KoreaCredit.DataFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MyCounselObject
{




    public String seq,title,date;

    public MyCounselObject() {
        date="";
        seq="";
        title ="";



    }

    public void setData(JSONObject obj){
        try {
            date=obj.getString("cust_date");
            seq=obj.getString("cust_idx");
            title =obj.getString("cardname");



        } catch (JSONException e) {

        }



    }



}






