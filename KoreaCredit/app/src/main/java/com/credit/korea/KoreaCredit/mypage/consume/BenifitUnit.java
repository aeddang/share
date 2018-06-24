package com.credit.korea.KoreaCredit.mypage.consume;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BenifitUnit
{




    public String seq,title;
    public boolean isSelected;
    public BenifitUnit() {

        seq="";


        title="";
        isSelected=false;


    }

    public void setData(JSONObject data){
        try {

            title=data.getString("title");
            seq=data.getString("seq");

            String isSel=data.getString("isSelected");
            //Log.i("", "isSel : " + isSel);
            isSelected=isSel.equals("Y");
           // Log.i("", "isSelected : " + isSelected);
        } catch (JSONException e) {
            Log.i("", "BenifitUnit : JSONException");
        }

    }





}






