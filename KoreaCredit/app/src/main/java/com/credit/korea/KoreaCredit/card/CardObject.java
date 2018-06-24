package com.credit.korea.KoreaCredit.card;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import lib.CommonUtil;


public class CardObject extends Object
{
    public static final int TYPE_MYCARD=0;
    public static final int TYPE_SEARCH_CARD=1;
    public static final int TYPE_RECOMMEND_CARD=2;
    public static final int TYPE_THIS_CARD=3;

    public static final int TYPE_FAV_CARD=4;
    public static final int TYPE_DETAIL_CARD=5;
    public static final int TYPE_FIND_CARD=6;
    public static final int TYPE_NAME_CARD=7;
    public static final int TYPE_COMPARE_CARD=8;
    public int cardType;


    public String idx,seq,title,name,kind,period,sms,imgPath,yy,mm,haveopt,extnum;
    public String smsNumber;
    public String cardOther,cardLink,isMycard,jointCard,feedom,feeover,cardIntro,addNum,updateDate,tel;
    public String overallBenef,ttlBenef,overallIother,
            amt_benef,beforeuse_benef,type_benef;
    private String bene_gettotal,beneTotal;
    public String pricePerYear;
    public float rate;
    public boolean isMine,isSelected,isOpen,isInterest;

    public ArrayList<CardUnit> linkCards;

    public ArrayList<CardOption> intros;
    public ArrayList<CardOption> benefits,benefitLimits;
    public ArrayList<CardOption> svrs;
    public ArrayList<CardOption> subInfos;


    public CardObject() {
        idx="";
        seq="";
        title ="";
        name="";
        kind="";
        period="";
        sms="";
        imgPath="";
        yy="";
        mm="";
        tel="";
        haveopt="";

        cardOther="";
        cardLink="";
        isMycard="";
        jointCard="";
        feedom="";
        feeover="";
        cardIntro="";
        addNum="";
        updateDate="";

        overallBenef="";
        ttlBenef="";
        overallIother="";
        beneTotal="";
        bene_gettotal="";

        pricePerYear="";
        extnum="";
        rate=0.f;
        isMine=false;
        isSelected=false;
        isOpen=false;
        isInterest=false;

        smsNumber="0000-0000";

        intros=new ArrayList<CardOption>();
        linkCards=new ArrayList<CardUnit>();
        benefits=new ArrayList<CardOption>();
        benefitLimits=new ArrayList<CardOption>();
        svrs=new ArrayList<CardOption>();
        subInfos=new ArrayList<CardOption>();
    }

    public void setData(JSONObject obj){
        cardType=TYPE_MYCARD;
        idx=getStringValue(obj,"card_idx");
        seq=getStringValue(obj,"card_no");
        title =getStringValue(obj,"card_name");
        name=getStringValue(obj,"card_company");
        kind=getStringValue(obj,"card_type");
        smsNumber=getStringValue(obj,"card_sendphone");

        yy=getStringValue(obj,"card_yy");
        mm=getStringValue(obj,"card_mm");

        period="만료기간(월/년) "+mm+"/"+yy;

        sms=getStringValue(obj,"sms_phone");

        imgPath=getStringValue(obj,"img");
        haveopt=getStringValue(obj,"haveopt");

        isMine=haveopt.equals("보유카드");
        imgPath=getImgPath(imgPath);


    }
    public void setDataName(JSONObject obj){
        cardType=TYPE_NAME_CARD;
        seq=getStringValue(obj,"cardno");

        title =getStringValue(obj,"cardname");
        name=getStringValue(obj,"cardcompany");
        smsNumber=getStringValue(obj,"card_sendphone");
        isMycard=getStringValue(obj,"ismycard");
        isMine=isMycard.equals("2");
        isInterest=isMycard.equals("1");



    }
    public void setDataFav(JSONObject obj){
        cardType=TYPE_FAV_CARD;
        seq=getStringValue(obj,"cardno");
        rate=getFloatValue(obj,"cardrate");
        title =getStringValue(obj,"cardname");
        name=getStringValue(obj,"cardcompany");
        smsNumber=getStringValue(obj,"card_sendphone");
        isMycard=getStringValue(obj,"cardhave");
        isMine=isMycard.equals("2");
        isInterest=isMycard.equals("1");



    }
    public String getBeneGetTotalView(){


        if(bene_gettotal.equals("")==true ){
            return "-";
        }else{

            return bene_gettotal+"만원";

        }

    }
    public float getBeneGetTotalNum(){

         String numstr=CommonUtil.onlyNum(bene_gettotal);
         if(numstr.equals("")==true){
             return 0.f;
         }else{

             return Float.valueOf(numstr);

         }

    }
    public String getBeneTotalView(){


        if(beneTotal.equals("")==true){
            return "-";
        }else{

            return beneTotal+"만원";

        }

    }
    public float getBeneTotalNum(){

        String numstr=CommonUtil.onlyNum(beneTotal);
        if(numstr.equals("")==true){
            return 0.f;
        }else{

            return Float.valueOf(numstr);

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
    private String getImgPath(String path){

        String sUrl="";
        String eUrl="";
        sUrl = path.substring(0, path.lastIndexOf("/")+1);
        eUrl = path.substring(path.lastIndexOf("/")+1, path.length()); // 한글과 공백을 포함한 부분
        try {
            eUrl = URLEncoder.encode(eUrl, "EUC-KR").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {

        }
        path = sUrl+ eUrl;
        return path;
    }
    public void setDataDefault(JSONObject obj,int type){

        cardType=type;
        seq=getStringValue(obj,"cardno");
        title =getStringValue(obj,"cardname");
        name=getStringValue(obj,"cardcompany");

        kind=getStringValue(obj,"whatcard");

        cardOther=getStringValue(obj,"cardother");
        cardLink=getStringValue(obj,"cardlink");
        imgPath=getStringValue(obj,"img");

        isMycard=getStringValue(obj,"ismycard");
        isMine=isMycard.equals("2");
        isInterest=isMycard.equals("1");
        smsNumber=getStringValue(obj,"card_sendphone");
        jointCard=getStringValue(obj,"joint_card");
        feedom=getStringValue(obj,"feedom");
        feeover=getStringValue(obj,"feeover");
        cardIntro=getStringValue(obj,"cardintro");
        tel=getStringValue(obj,"tel");
        addNum=getStringValue(obj,"add_num");
        updateDate=getStringValue(obj,"update_date");

        overallBenef=getStringValue(obj,"overall_benef");
        ttlBenef=getStringValue(obj,"ttl_benef");
        overallIother=getStringValue(obj,"overall_iother");
        beneTotal=getStringValue(obj,"bene_total");
        bene_gettotal=getStringValue(obj,"bene_gettotal");
        amt_benef=getStringValue(obj,"amt_benef");
        beforeuse_benef=getStringValue(obj,"beforeuse_benef");
        type_benef=getStringValue(obj,"type_benef");

        pricePerYear=feedom;  // ", 국내외 "+feeover;

        imgPath=getImgPath(imgPath);


        CardOption opt=new CardOption();
        opt.title=cardIntro;
        intros.add(opt);
        if(cardOther.equals("")==false) {
            opt = new CardOption();
            opt.title = cardOther;
            intros.add(opt);
        }




        try {


            String linkcardinfo=obj.getString("linkcardinfo");
            if(linkcardinfo.equals("")==false){
                String[] sA=linkcardinfo.split("\\,");
                for(int i=0;i<sA.length;++i){

                    CardUnit unit=new CardUnit();
                    unit.setData(sA[i]);
                    linkCards.add(unit);
                }
            }





        } catch (JSONException e) {



        }
        try {
            JSONArray benifs = obj.getJSONArray("bene");


            for(int i=0;i<benifs.length();++i){

                JSONObject uobj=benifs.getJSONObject(i);
                CardOption copt=new CardOption();
                copt.setData(uobj);
                benefits.add(copt);
            }
            if(overallIother.equals("")==false) {
                opt = new CardOption();
                opt.title = overallIother;
                benefitLimits.add(opt);
            }



        } catch (JSONException e) {



        }
        try {
            JSONArray adds = obj.getJSONArray("svr"); //부가기능


            for(int i=0;i<adds.length();++i){

                JSONObject uobj=adds.getJSONObject(i);
                CardOption copt=new CardOption();
                copt.setData(uobj);
                svrs.add(copt);
            }



        } catch (JSONException e) {



        }
        try {
            JSONArray adds = obj.getJSONArray("subinfo");  //부가기능


            for(int i=0;i<adds.length();++i){

                JSONObject uobj=adds.getJSONObject(i);
                CardOption copt=new CardOption();
                copt.setData(uobj);
                subInfos.add(copt);
            }



        } catch (JSONException e) {



        }




    }



}






