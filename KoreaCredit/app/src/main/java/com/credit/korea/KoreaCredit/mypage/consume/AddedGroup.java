package com.credit.korea.KoreaCredit.mypage.consume;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddedGroup
{




    public String title,seq;
    public boolean isMultiSelected,isSelected;
    public ArrayList<AddedObject>lists;

    public AddedGroup() {
        lists=new ArrayList<AddedObject>();
        title="";
        seq="";
        isMultiSelected=false;
        isSelected=false;
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
                AddedObject obj=new AddedObject();
                obj.setData(jdata);
                if(obj.isSelected==true){
                    isSelected=true;
                }
                lists.add(obj);
            }

        } catch (JSONException e) {
            //e.printStackTrace();
        }


    }





}






