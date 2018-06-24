package com.credit.korea.KoreaCredit.card.book;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BookObject
{




    public String seq,title;
    public double consumePoint,usedPoint,adjustPoint;

    public BookObject() {

        seq="";
        consumePoint=0.f;
        usedPoint=0.f;
        adjustPoint=0.f;

        title="";


    }

    public void setData(JSONObject obj){


        try {

            seq=obj.getString("book_idx");
            title =obj.getString("title");
            consumePoint = obj.getDouble("gubun1_value");
            usedPoint = obj.getDouble("gubun2_value");




        } catch (JSONException e) {

            Log.i("","JSONException : "+e.toString());

        }

        try {


            adjustPoint = obj.getDouble("total_per");

        } catch (JSONException e) {

            Log.i("","JSONException : "+e.toString());

        }

    }





}






