package com.credit.korea.KoreaCredit.mypage.consume;


import org.json.JSONException;
import org.json.JSONObject;

import lib.CommonUtil;

public class DefaultObject
{




    public float consumePerYear,consumePerMonthMin,incomePerYear;
    public int familly,birth;
    public boolean isMail,isFeMail,isMerried,isActive;

    public String sido,gugun;

    public DefaultObject() {
        consumePerYear=0.f;
        consumePerMonthMin = 0.f;
        incomePerYear=0.f;
        familly=1;
        birth=0;
        sido="";
        gugun="";
        isMail=false;
        isFeMail=true;
        isMerried=true;
        isActive=false;

    }

    public void setData(JSONObject obj){
        try {


            consumePerYear=getFloatValue(obj,"yearmoney");

            consumePerMonthMin =getFloatValue(obj,"monthmoney");
            incomePerYear=getFloatValue(obj,"yearincome");
            familly=getIntValue(obj,"familynum");
            birth=getIntValue(obj,"birthyear");

            sido=obj.getString("addr1");
            gugun=obj.getString("addr2");

            String sex=obj.getString("sex");
            isMail=sex.equals("남자");
            if(isMail==true){
                isFeMail=false;
            }else{
                isFeMail=true;
            }
            String marry=obj.getString("marry");
            isMerried=marry.equals("기혼");
            isActive=true;
        } catch (JSONException e) {

        }



    }
    private int getIntValue(JSONObject obj,String key){
        String value="";
        try {
            value=obj.getString(key);
        } catch (JSONException e) {

            value="";

        }
        if(value.equals("")){
            return 0;
        }
        return Integer.parseInt(value);
    }
    private Float getFloatValue(JSONObject obj,String key){
        String value="";
        try {
            value=obj.getString(key);
        } catch (JSONException e) {

            value="";

        }
        if(value.equals("")){
            return 0.f;
        }
        return Float.parseFloat(value);
    }
    public String getConsumePerYearOnly(){


        String str=CommonUtil.getPriceStr(Math.round(consumePerYear),0)+"만원";
        return str;
    }
    public String getConsumePerYear(){


        String str=CommonUtil.getPriceStr(Math.round(consumePerYear),0)+"만원/년 "+getConsumePerMonth(consumePerYear);
        return str;
    }
    public String getConsumePerMonth(float num){

        float per= num/12.f;

        String str="(월평균 이용액 "+CommonUtil.getPriceStr(Math.round(per),0)+"만원)";
        return str;
    }

    public String getConsumePerMonthMin(){


        String str=CommonUtil.getPriceStr(Math.round(consumePerMonthMin),0)+"만원/월";
        return str;
    }

    public String getIncomePerYear(){


        String str=CommonUtil.getPriceStr(Math.round(incomePerYear),0)+"만원/년";
        return str;
    }

    public String getSex(){

        if(isMail==true){

            return "남자";
        }else if(isFeMail==true){
            return  "여자";

        }else{
            return  "";

        }


    }
    public String getMerried(){

        if(isMerried==true){

            return "기혼";
        }else{
            return  "미혼";

       }


    }
    public String getBirth(){

         return birth+"년";



    }
    public String getFamilly(){

        return familly+"명";



    }





}






