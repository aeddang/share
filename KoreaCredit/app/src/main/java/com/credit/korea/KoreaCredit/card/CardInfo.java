package com.credit.korea.KoreaCredit.card;


import android.util.Log;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.SelectObject;
import com.credit.korea.KoreaCredit.member.MemberInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;


public class CardInfo implements DataManager.JsonManagerDelegate{



    private static CardInfo instence;


    public CardSearchDelegate delegate;
    public CardDetailDelegate detailDelegate;
    public CardCompareDelegate compareDelegate;


    public interface CardSearchDelegate
    {
        void onLoadCards(ArrayList<CardObject> cards);

    }
    public interface CardDetailDelegate
    {
        void onLoadCard(CardObject card);

    }
    public interface CardCompareDelegate
    {
        void onLoadCards(ArrayList<CardObject> cards);

    }

	public static CardInfo getInstence()
    {

        if(instence==null){

            instence= new CardInfo();
        }
        return instence;
	}

    private DataManager jsonManager;





    public CardInfo() {
    	instence=this;
        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);

    }
    public void loadDetailCard(String cardNo){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("cardno", cardNo);

        jsonManager.loadData(Config.API_LOAD_DETAIL_CARD,sendParams);


    }

    public void loadCompareCard(String cardNo){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("cardno", cardNo);

        jsonManager.loadData(Config.API_LOAD_COMPARE_CARD,sendParams);


    }
    private void onLoadCompareCards(JSONArray datas){

        ArrayList<CardObject> cards=new ArrayList<CardObject>();
        for(int i=0;i<datas.length();++i){
            try {
                CardObject card=new CardObject();
                card.setDataDefault (datas.getJSONObject(i),CardObject.TYPE_COMPARE_CARD);

                cards.add(card);
            } catch (JSONException e) {

            }


        }
        if(compareDelegate!=null) {
            compareDelegate.onLoadCards(cards);
        }

    }

    private void onLoadDetailCard(JSONArray datas){
        boolean isSuccess=DataFactory.getInstence().getSuccess(datas);
        if(isSuccess==false){

            if(detailDelegate!=null) {
                detailDelegate.onLoadCard(null);

            }
            return;
        }

        ArrayList<CardObject> cards=new ArrayList<CardObject>();
        for(int i=0;i<datas.length();++i){
            try {
                CardObject card=new CardObject();
                card.setDataDefault(datas.getJSONObject(i),CardObject.TYPE_DETAIL_CARD);

                cards.add(card);
            } catch (JSONException e) {

            }


        }


        if(detailDelegate!=null) {
            if(cards.size()<1){
                detailDelegate.onLoadCard(null);
            }else{
                detailDelegate.onLoadCard(cards.get(0));
            }


        }

    }

    public void loadNameCards(String key){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("card_name", DataFactory.getInstence().getUriencode(key));

        jsonManager.loadData(Config.API_LOAD_NAME_CARD,sendParams);


    }
    private void onLoadNameCard(JSONArray datas){

        ArrayList<CardObject> cards=new ArrayList<CardObject>();
        for(int i=0;i<datas.length();++i){
            try {
                CardObject card=new CardObject();
                card.setDataName(datas.getJSONObject(i));

                cards.add(card);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null) {
            delegate.onLoadCards(cards);

        }

    }

    public void loadFavoritCards(){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());

        jsonManager.loadData(Config.API_LOAD_FAVORIT_CARD,sendParams);


    }
    private void onLoadFavCards(JSONArray datas){

        ArrayList<CardObject> cards=new ArrayList<CardObject>();
        for(int i=0;i<datas.length();++i){
            try {
                CardObject card=new CardObject();
                card.setDataFav(datas.getJSONObject(i));

                cards.add(card);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null) {
            delegate.onLoadCards(cards);

        }

    }
    public void loadSearchCards(){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());

        jsonManager.loadData(Config.API_LOAD_SEARCH_CARD,sendParams);


    }
    public void unLoadSearchCards(){


        jsonManager.removeLoader(Config.API_LOAD_SEARCH_CARD);


    }
    public void loadRecommendCards(String cards){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("card_sel", cards);

        jsonManager.loadData(Config.API_LOAD_RECOMMEND_CARD,sendParams);


    }
    public void unLoadRecommendCards(){


        jsonManager.removeLoader(Config.API_LOAD_RECOMMEND_CARD);


    }
    public void loadThisCards(Map<String,String> sendParams){

        MainActivity.getInstence().loadingStart(false);

        sendParams.put("uid", MemberInfo.getInstence().getID());
        jsonManager.loadData(Config.API_LOAD_THIS_CARD,sendParams);


    }
    public void unLoadThisCards(){


        jsonManager.removeLoader(Config.API_LOAD_THIS_CARD);


    }

    public void loadFindCards(Map<String,String> sendParams){

        MainActivity.getInstence().loadingStart(false);

        sendParams.put("uid", MemberInfo.getInstence().getID());
        jsonManager.loadData(Config.API_LOAD_FIND_CARD,sendParams);


    }
    public void unLoadFindCards(){


        jsonManager.removeLoader(Config.API_LOAD_FIND_CARD);


    }


    private void onLoadCards(JSONArray datas,int type){

        ArrayList<CardObject> cards=new ArrayList<CardObject>();
        for(int i=0;i<datas.length();++i){
            try {
                CardObject card=new CardObject();
                card.setDataDefault (datas.getJSONObject(i),type);

                cards.add(card);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null) {
            delegate.onLoadCards(cards);

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

        if(path.equals(Config.API_LOAD_SEARCH_CARD))
        {
           onLoadCards(results,CardObject.TYPE_SEARCH_CARD);

        }else if(path.equals(Config.API_LOAD_COMPARE_CARD)) {
            onLoadCompareCards(results);
        }
        else if(path.equals(Config.API_LOAD_RECOMMEND_CARD))
        {
            onLoadCards(results,CardObject.TYPE_RECOMMEND_CARD);


        }else if(path.equals(Config.API_LOAD_THIS_CARD))
        {
            onLoadCards(results,CardObject.TYPE_THIS_CARD);


        }else if(path.equals(Config.API_LOAD_FIND_CARD))
        {
            onLoadCards(results,CardObject.TYPE_FIND_CARD);


        }else if(path.equals(Config.API_LOAD_FAVORIT_CARD))
        {
            onLoadFavCards(results);


        }else if(path.equals(Config.API_LOAD_DETAIL_CARD)){

            onLoadDetailCard(results);
        }else if(path.equals(Config.API_LOAD_NAME_CARD)){

            onLoadNameCard(results);
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

