package com.credit.korea.KoreaCredit.card;


import org.json.JSONException;
import org.json.JSONObject;

public class CardOption
{




    public String title,subTitle,detail;


    public CardOption() {

        title="";
        subTitle="";
        detail="";

    }

    public void setData(JSONObject obj){

        try {


            title =obj.getString("title");
            detail =obj.getString("detail");


        } catch (JSONException e) {
        }
        try {


            subTitle =obj.getString("sub");

        } catch (JSONException e) {
        }


    }









}






