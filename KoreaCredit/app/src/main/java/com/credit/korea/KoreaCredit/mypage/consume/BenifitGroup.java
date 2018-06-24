package com.credit.korea.KoreaCredit.mypage.consume;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BenifitGroup
{




    public String title,seq;
    public boolean isMultiSelected;
    public ArrayList<BenifitObject>lists;

    public BenifitGroup() {
        lists=new ArrayList<BenifitObject>();
        title="";
        seq="";
        isMultiSelected=false;
    }

    public void setData(JSONObject data){

        try {
            String isMulti=data.getString("multiSelectedAble");
            isMultiSelected=isMulti.equals("Y");

            title=data.getString("title");
            seq=data.getString("seq");
            JSONArray datas =  data.getJSONArray("datas");
            for(int i=0;i<datas.length();++i) {
                JSONObject jdata = datas.getJSONObject(i);
                BenifitObject obj=new BenifitObject();
                obj.setData(jdata);
                lists.add(obj);
            }

        } catch (JSONException e) {
            Log.i("", "BenifitGroup : JSONException");
        }



    }





}






