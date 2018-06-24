package com.credit.korea.KoreaCredit.member;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;

public class MyInfoObject
{




    public String id,email,phone,snsTypeValue,smsTime;
    public int snsType;
    public boolean isSMS,isPush;



    public MyInfoObject() {
        id="";
        email="";
        phone="";

        snsType=-1;
        isSMS=true;
        isPush=true;


    }

    public void setData(JSONObject obj){
        try {
            id=obj.getString("uid");
            email =obj.getString("email");
            phone=obj.getString("hp");
            smsTime=obj.getString("sms_time");
            String push=obj.getString("push");
            isPush=push.equals("Y");



            String pat=obj.getString("sms_auto");
            isSMS=pat.equals("Y");
            MemberInfo.getInstence().registerSMS(isSMS);
            snsTypeValue=obj.getString("login_type");
            if(snsTypeValue.equals("")==false){

                for(int i=0;i<MemberInfo.TYPE_SNS.length;++i){
                    if(MemberInfo.TYPE_SNS[i].equals(snsTypeValue)==true){
                        snsType=i;
                    }
                }

            }

        } catch (JSONException e) {

        }

    }



}






