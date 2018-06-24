package com.credit.korea.KoreaCredit.card.book;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BookUnit
{




    public String seq,title,usedate;
    public double price;

    public String cardName,group,kind,sort;
    public String cardID,groupID,kindID,sortID;

    public BookUnit() {

        seq="";
        price=0.f;

        usedate="";
        title="";


    }

    public void setData(JSONObject obj){



        try {

            seq=obj.getString("bookuse_idx");
            title =obj.getString("shop_name");
            usedate = obj.getString("usedate");
            price = obj.getDouble("price");
            cardName = obj.getString("card_name");
            group= obj.getString("group_name");
            kind= obj.getString("sub_name");
            sort= obj.getString("sv_value");

            cardID= obj.getString("card_idx");
            groupID= obj.getString("group_idx");
            kindID= obj.getString("sub_idx");
            sortID= obj.getString("sv_idx");
            //"bookuse_idx": "114","usedate": "2015-12-01","price": "34500","sv_value": "토이저러스","group_name": "학습","sub_name": "학원","sub_idx": "11","group_idx": "4","sv_idx": "25"
        } catch (JSONException e) {

            Log.i("", "JSONException : " + e.toString());

        }

    }





}






