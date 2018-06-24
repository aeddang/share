package com.credit.korea.KoreaCredit.mypage;


import android.util.Log;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.member.MyInfoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;


public class MyPageInfo implements DataManager.JsonManagerDelegate {


    private static MyPageInfo instence;

    private DataManager jsonManager;

    private MyInfoObject myInfoObj;
    private MySearchObject mySearchObj;

    public InfoInfoDelegate infoDelegate;
    public SearchDelegate searchDelegate;


    public interface InfoInfoDelegate
    {
        void onLoadMyInfo(MyInfoObject obj);


    }
    public interface SearchDelegate
    {
        void onLoadMySerch(MySearchObject obj);
        void onModifyMySerch(boolean result);

    }


	public static MyPageInfo getInstence(){

        if(instence==null){
            instence=new MyPageInfo();

        }

        return instence;
	}

    public MyPageInfo() {
    	instence=this;

        myInfoObj=null;
        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);



    }

    public void resetMyInfo(){

         myInfoObj=null;
    }
    public MyInfoObject getMyInfo(){

        return myInfoObj;
    }
    public void loadMyInfo(boolean isReset){


        if(isReset==true){

            myInfoObj=null;
        }


        if(myInfoObj==null) {

            Map<String, String> sendParams = new HashMap<String, String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());
            MainActivity.getInstence().loadingStart(false);
            jsonManager.loadData(Config.API_LOAD_MYINFO, sendParams);
        }else{

            if(infoDelegate!=null){
                infoDelegate.onLoadMyInfo(myInfoObj);
            }
        }


    }

    public void modifyMySearchOption(MySearchObject data){

        Map<String, String> sendParams = new HashMap<String, String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("value",data.getSendParamString());
        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_MODIFY_MYSEARCH_OPTION, sendParams);



    }
    public void resetInfo(){

        mySearchObj=null;
        myInfoObj=null;

    }
    public MySearchObject getMySearchOption(){

        return mySearchObj;
    }
    public void loadMySearchOption(boolean isReset){
        if(isReset==true){

            mySearchObj=null;
        }


        if(mySearchObj==null) {

            Map<String, String> sendParams = new HashMap<String, String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());
            MainActivity.getInstence().loadingStart(false);
            jsonManager.loadData(Config.API_LOAD_MYSEARCH_OPTION, sendParams);
        }else{

            if(searchDelegate!=null){
                searchDelegate.onLoadMySerch(mySearchObj);
            }
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

        if(path.equals(Config.API_LOAD_MYINFO)){



            boolean isSuccess=DataFactory.getInstence().getSuccess(results);
            if(isSuccess==true) {
                myInfoObj=new MyInfoObject();
                JSONObject data = null;
                try {
                    data = results.getJSONObject(0);
                    myInfoObj.setData(data);
                    MemberInfo.getInstence().setSmsTime(myInfoObj.smsTime);

                } catch (JSONException e) {
                    MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
                }

            }else{
                MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
                return;

            }
            if(infoDelegate!=null){
                infoDelegate.onLoadMyInfo(myInfoObj);
            }

        }else if(path.equals(Config.API_LOAD_MYSEARCH_OPTION)){
            mySearchObj=new MySearchObject();
            if(results.length()>0) {

                mySearchObj.setData(results);

            }else{
                //MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
                //return;

            }
            if(searchDelegate!=null){
                searchDelegate.onLoadMySerch(mySearchObj);
            }

        }else if(path.equals(Config.API_MODIFY_MYSEARCH_OPTION)){
            if(searchDelegate!=null){

                searchDelegate.onModifyMySerch(DataFactory.getInstence().getSuccess(results));
            }

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

