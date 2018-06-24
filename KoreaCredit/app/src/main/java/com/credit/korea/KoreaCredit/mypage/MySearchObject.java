package com.credit.korea.KoreaCredit.mypage;


import android.content.SharedPreferences;
import android.util.Log;

import com.credit.korea.KoreaCredit.DataFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class MySearchObject
{




    public String standard,kind,company,nation,cooperate, suspend,payed;
    public boolean isActive;



    private ArrayList<String> values;

    public MySearchObject() {

        values=new ArrayList<String>();
        isActive=false;
        for(int i=0;i<DataFactory.SEARCH_TYPES.length;++i){
            values.add("");

        }

    }
    public String getValue(int typeIdx){

        return  values.get(typeIdx);

    }
    public void setValue(int typeIdx ,String value){

        values.set(typeIdx,value);

    }
    public boolean isChecked(int typeIdx,int valueIdx){

        if(values.size()<=typeIdx){
            return false;
        }

        String selectedValues= values.get(typeIdx);
        String[] findValue={};
        switch (typeIdx){
            case 0:
                findValue=DataFactory.SEARCH_STANDARDS;
                break;
            case 1:
                findValue=DataFactory.SEARCH_KINDS;
                break;
            case 2:
                findValue=DataFactory.SEARCH_COMPANYS;
                break;
            case 3:
                findValue=DataFactory.SEARCH_NATIONS;
                break;
            case 4:
                findValue=DataFactory.SEARCH_AIR;
                break;

        }
        String myValues= findValue[valueIdx];

        Log.i("","selectedValues : "+selectedValues+" myValues :"+myValues);
        if(selectedValues.indexOf(myValues)!=-1){
            return true;

        }else{
            return false;
        }

    }
    public String getSendParamString(){

        String str="";
        for(int i=0;i< values.size();++i){

            if(values.get(i).equals("")==false) {
                if (str.equals("") == true) {
                    str = DataFactory.getInstence().getUriencode(DataFactory.SEARCH_TYPES[i]) + "_" + DataFactory.getInstence().getUriencode(values.get(i));
                } else {
                    str += "|" + DataFactory.getInstence().getUriencode(DataFactory.SEARCH_TYPES[i]) + "_" + DataFactory.getInstence().getUriencode(values.get(i));

                }
            }

        }
        return str;
    }


    public void setData(JSONArray datas){


        try {
            //JSONArray datas = obj.getJSONArray("datalist");

            ArrayList<String> keys=new ArrayList<String>();
            Collections.addAll(keys,DataFactory.SEARCH_TYPES);

            for(int i=0;i< datas.length();++i){

                JSONObject data=datas.getJSONObject(i);
                String opt = data.getString("opt");
                String optvalue = data.getString("optvalue");
                int idx = keys.indexOf(opt);
                Log.i("","opt idx : "+opt+" idx :"+idx);

                if (idx >= 0 && idx < values.size())
                {
                    Log.i("","optvalue : "+optvalue+" idx :"+idx);
                    values.set(idx, optvalue);
                }



            }

            isActive=true;



        } catch (JSONException e) {

            Log.i("","MySearchObject JSONException ");
        }


    }



}






