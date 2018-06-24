package com.credit.korea.KoreaCredit.mypage.consume;


import org.json.JSONException;
import org.json.JSONObject;

public class AddedObject
{




    public String seq,title;
    public boolean isSelected;
    public AddedObject() {
        seq="";
        title ="";

        isSelected=true;
    }

    public void setData(JSONObject data){
        try {

            title=data.getString("title");
            seq=data.getString("seq");

            String isSel=data.getString("isSelected");
            isSelected=isSel.equals("Y");

        } catch (JSONException e) {
            //e.printStackTrace();
        }

    }




}






