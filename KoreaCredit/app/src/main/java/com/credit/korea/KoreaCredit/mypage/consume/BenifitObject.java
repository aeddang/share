package com.credit.korea.KoreaCredit.mypage.consume;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BenifitObject
{




    public String seq,title,consumePoint_unit,consumePointsub_unit,usedPoint_unit,usedPointsub_unit;
    public float consumePoint,usedPoint,adjustPoint,consumeRate,usedRate,consumePointsub,usedPointsub;

    public ArrayList<BenifitUnit> lists;
    public boolean isSelected,sub_isuse,point_isyear;
    public BenifitObject() {

        seq="";
        consumePoint=0.f;
        usedPoint=0.f;
        adjustPoint=0.f;
        consumeRate=0.f;
        usedRate=0.f;
        title="";
        isSelected=false;
        lists=new ArrayList<BenifitUnit>();

    }

    public void setData(JSONObject data){

        title=getStringValue(data,"title");
        seq=getStringValue(data,"seq");

        consumePoint=getFloatValue(data,"consumePoint");
        usedPoint=getFloatValue(data,"usedPoint");
        adjustPoint=getFloatValue(data,"adjustPoint");

        consumeRate=getFloatValue(data,"consumeRate");
        usedRate=getFloatValue(data,"usedRate");

        String isSel=getStringValue(data,"isSelected");
        isSelected=isSel.equals("Y");
        Log.i("", "isSel : " + isSelected);


        String issub=getStringValue(data,"sub_isuse");
        sub_isuse = issub.equals("Y");
        consumePointsub=getFloatValue(data,"consumePointsub");
        usedPointsub=getFloatValue(data,"usedPointsub");


        consumePoint_unit=getStringValue(data,"consumePoint_unit");
        consumePointsub_unit=getStringValue(data,"consumePointsub_unit");

        usedPoint_unit=getStringValue(data,"usedPoint_unit");
        usedPointsub_unit=getStringValue(data,"usedPointsub_unit");

        String ispoint=getStringValue(data,"point_isyear");
        point_isyear = ispoint.equals("Y");


        try {


            JSONArray datas =  data.getJSONArray("datas");
            for(int i=0;i<datas.length();++i) {
                JSONObject jdata = datas.getJSONObject(i);
                BenifitUnit obj=new BenifitUnit();
                obj.setData(jdata);

                lists.add(obj);
            }

        } catch (JSONException e) {
            Log.i("", "BenifitObject : JSONException");
        }

    }
    private float getFloatValue(JSONObject obj,String key){
        float value=0.f;
        try {
            value=(float)obj.getDouble(key);
        } catch (JSONException e) {

            Log.i("","JSONException : "+key);

        }
        return value;
    }
    private String getStringValue(JSONObject obj,String key){
        String value="";
        try {
            value=obj.getString(key);
        } catch (JSONException e) {

            Log.i("","JSONException : "+key);

        }
        return value;
    }




}






