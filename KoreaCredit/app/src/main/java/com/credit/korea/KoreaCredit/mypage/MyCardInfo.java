package com.credit.korea.KoreaCredit.mypage;


import android.util.Log;

import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.member.MemberInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;


public class MyCardInfo implements DataManager.JsonManagerDelegate {

    public static final int REQUEST_TYPE_MYCARD=2;
    public static final int REQUEST_TYPE_INTEREST_CARD=1;
    public static final int REQUEST_TYPE_COUNSEL=3;

    private DataManager jsonManager;

    private  ArrayList<CardObject> myCards;
    private  ArrayList<MyCounselObject> myCounsels;
    public MyCardDelegate delegate;
    private int currentRequestType;


    public interface MyCardDelegate
    {
        void onModifyMyCards();

        void onLoadMyCards(ArrayList<CardObject> myCards);
        void onLoadMyCounsels(ArrayList<MyCounselObject> myCounsels);


    }


    private static MyCardInfo instence;



    public static MyCardInfo getInstence(){

        if(instence==null){
            instence=new MyCardInfo();

        }

        return instence;
    }

    public MyCardInfo() {

        instence=this;
        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);



    }

    public void resetInfo(){

        myCounsels = null;
        myCards=null;

    }
    public void removeInfo(){

        delegate=null;
        if(jsonManager!=null){


           jsonManager.destory();
           jsonManager=null;
       }
    }
    public void deleteMyCard(CardObject card){
        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("card_idx", card.idx);
        sendParams.put("opt", "del");

        jsonManager.loadData(Config.API_DELETE_CARD, sendParams);

    }
    private void onDeleteCard(JSONArray datas) {

        boolean isSuccess = DataFactory.getInstence().getSuccess(datas);

        if(isSuccess==true){
            MainActivity.getInstence().viewAlert("",R.string.msg_card_delete_complete,null);
            if(delegate!=null){

                delegate.onModifyMyCards();
            }
        }else{
            MainActivity.getInstence().viewAlert("",R.string.msg_card_delete_fail,null);

        }
    }
    public ArrayList<MyCounselObject> getMyCounsels(){

        return myCounsels;

    }

    public void requestCard(CardObject card,int type){

        MemberInfo minfo= MemberInfo.getInstence();
        if(minfo.getLoginState()==false){

                MainActivity ac= (MainActivity)MainActivity.getInstence();
                ac.checkLoginPage();
                return;


        }

        currentRequestType=type;
        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("cardno", card.seq);
        sendParams.put("opt", String.valueOf(type));

        if(type==REQUEST_TYPE_MYCARD){
            sendParams.put("yy", card.yy);
            sendParams.put("mm", card.mm);
            sendParams.put("smsphone", card.sms);
            sendParams.put("extnum", card.extnum);

        }

        jsonManager.loadData(Config.API_REQUEST_CARD, sendParams);


    }
    private void onRequestCard(JSONArray datas){

        boolean isSuccess= DataFactory.getInstence().getSuccess(datas);
        String resultStr=DataFactory.getInstence().getResultCode(datas);

        switch(currentRequestType){
            case  REQUEST_TYPE_COUNSEL:
                if(isSuccess==true){
                    MainActivity.getInstence().viewAlert("",R.string.msg_card_request_counsel_complete,null);
                    myCounsels=null;
                }else{
                    MainActivity.getInstence().viewAlert("",R.string.msg_card_request_counsel_fail,null);
                }

                break;
            case  REQUEST_TYPE_MYCARD:
                if(isSuccess==true){
                    MainActivity.getInstence().viewAlert("",R.string.msg_card_request_my_complete,null);
                    if(delegate!=null){

                        delegate.onModifyMyCards();
                    }
                }else{


                    if(resultStr.equals("fail_219")==true){
                        MainActivity.getInstence().viewAlert("",R.string.msg_card_request_my_full,null);
                    }else{
                        MainActivity.getInstence().viewAlert("",R.string.msg_card_request_my_fail,null);
                    }



                }
                break;
            case  REQUEST_TYPE_INTEREST_CARD:
                if(isSuccess==true){
                    MainActivity.getInstence().viewAlert("",R.string.msg_card_request_interest_complete,null);
                    if(delegate!=null){

                        delegate.onModifyMyCards();
                    }
                }else{

                    if(resultStr.equals("fail_218")==true){
                        MainActivity.getInstence().viewAlert("",R.string.msg_card_request_interest_full,null);
                    }else{
                        MainActivity.getInstence().viewAlert("",R.string.msg_card_request_interest_fail,null);
                    }

                }
                break;


        }


    }


    public void loadMyCounsels(boolean isReset){

        if(isReset==true){
            myCounsels = null;
        }else{


        }
        if(myCounsels ==null) {
            MainActivity.getInstence().loadingStart(false);
            Map<String,String> sendParams=new HashMap<String,String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());

            jsonManager.loadData(Config.API_LOAD_MYCOUNSEL,sendParams);
        }
        if(delegate!=null && myCounsels !=null){
            delegate.onLoadMyCounsels(myCounsels);
        }

    }
    public void onLoadMyCounsels(JSONArray datas){


        myCounsels=new ArrayList<MyCounselObject>();
        for(int i=0;i<datas.length();++i){
            try {
                MyCounselObject data=new MyCounselObject();
                data.setData(datas.getJSONObject(i));

                myCounsels.add(data);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null){
            delegate.onLoadMyCounsels(myCounsels);
        }

    }
    public ArrayList<CardObject> getMyCards(){

        return myCards;

    }
    public void loadMyCards(boolean isReset){

        if(isReset==true){
            myCards = null;
        }else{


        }
        if(myCards ==null) {
            MainActivity.getInstence().loadingStart(false);
            Map<String,String> sendParams=new HashMap<String,String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());
            sendParams.put("opt", "0");
            jsonManager.loadData(Config.API_LOAD_MYCARD,sendParams);
        }
        if(delegate!=null && myCards !=null){
           delegate.onLoadMyCards(myCards);
        }

    }
    public void onLoadMyCards(JSONArray datas){


        myCards=new ArrayList<CardObject>();
        for(int i=0;i<datas.length();++i){
            try {
                CardObject card=new CardObject();
                card.setData(datas.getJSONObject(i));

                myCards.add(card);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null){
            delegate.onLoadMyCards(myCards);
        }

    }


    public void onJsonCompleted(DataManager manager,String path,JSONObject result)
    {
        MainActivity.getInstence().loadingStop();
        JSONArray results = null;
        try {
            results = result.getJSONArray("datalist");
        } catch (JSONException e) {
            Log.i("", "error path :" + path);
            MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
            return;

        }

        if(path.equals(Config.API_LOAD_MYCARD)){

            onLoadMyCards(results);

        }else if(path.equals(Config.API_LOAD_MYCOUNSEL)){
            onLoadMyCounsels(results);


        }else if(path.equals(Config.API_REQUEST_CARD))
        {
            onRequestCard(results);


        }else if(path.equals(Config.API_DELETE_CARD))
        {
            onDeleteCard(results);


        }

    }
    public void onJsonParseErr(DataManager manager,String path) {
        MainActivity.getInstence().loadingStop();
        MainActivity.getInstence().viewAlert("",R.string.msg_data_parse_err,null);
    }
    public void onJsonLoadErr(DataManager manager,String path) {
        MainActivity.getInstence().loadingStop();

        MainActivity.getInstence().viewAlert("",R.string.msg_network_err,null);
    }


}

