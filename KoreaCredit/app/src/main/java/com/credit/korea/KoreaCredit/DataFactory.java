package com.credit.korea.KoreaCredit;


import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.member.MyInfoObject;
import com.credit.korea.KoreaCredit.mypage.MyPageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;


public class DataFactory  implements DataManager.JsonManagerDelegate{



    private static DataFactory instence;

    public static final String NONE_SELECT="선택안함";
    public static final String[] SEARCH_TYPES={"검색기준","검색대상","신용카드사","이용국가","항공마일리지"};

    public static final String[] SEARCH_STANDARDS={"할인혜택우선","적립혜택우선","총혜택우선","마일리지우선"};
    public static final String[] SEARCH_KINDS={"일반신용카드","프리미엄카드","체크선불카드","마일리지카드"};
    public static final String[] SEARCH_KINDS_MIN={"일반신용카드","프리미엄카드","체크선불카드"};
    public static final String[] SEARCH_COMPANYS={"신한카드","삼성카드","현대카드","KB국민카드","롯데카드","하나카드","우리카드","NH농협카드","씨티카드","비씨카드"};

    public static final String[] SEARCH_NATIONS={"무관","국내","국내외"};
    public static final String[] SEARCH_AIR={"대한항공","아시아나항공","기타항공"};




    public DataFactoryDelegate delegate;

    public interface DataFactoryDelegate
    {



        void onLoadBenefitGroups(ArrayList<SelectObject> datas);
        void onLoadBenefitKinds(ArrayList<SelectObject> datas);
        void onLoadCompanySort(ArrayList<SelectObject> datas);
        void onLoadCompanys(ArrayList<SelectObject> datas);


    }


	public static DataFactory getInstence()
    {


        return instence;
	}

    private DataManager jsonManager;


    public String smsNumbers;
    private ArrayList<SelectObject> benefitGroups;

    private ArrayList<String> sendStrings;

    public DataFactory() {
    	instence=this;
        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);
        sendStrings=new ArrayList<String>();
    }



    public void loadBenefitGroups(){

        if(benefitGroups!=null){
            if(delegate!=null) {
                delegate.onLoadBenefitGroups(benefitGroups);

            }
            return;

        }

        MainActivity.getInstence().loadingStart(false);


        jsonManager.loadData(Config.API_LOAD_BENEFIT_GROUP);

    }
    private void onLoadBenefitGroup(JSONArray datas){
        benefitGroups=new ArrayList<SelectObject>();
        for(int i=0;i<datas.length();++i){
            try {
                SelectObject obj=new SelectObject();
                obj.setDataBenit(datas.getJSONObject(i));

                benefitGroups.add(obj);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null) {
            delegate.onLoadBenefitGroups(benefitGroups);

        }
    }

    public void loadBenefitKinds(String key){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("group_idx", key);

        jsonManager.loadData(Config.API_LOAD_BENEFIT_GROUP_SUB,sendParams);

    }
    private void onLoadBenefitGroupSub(JSONArray datas){
        ArrayList<SelectObject> kinds=new ArrayList<SelectObject>();
        for(int i=0;i<datas.length();++i){
            try {
                SelectObject obj=new SelectObject();
                obj.setDataBenitSub(datas.getJSONObject(i));

                kinds.add(obj);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null) {
            delegate.onLoadBenefitKinds(kinds);

        }
    }

    public void loadCompanySort(String gid,String sid){

        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("group_idx", gid);
        sendParams.put("sub_idx", sid);

        jsonManager.loadData(Config.API_LOAD_COMPANY_SORT,sendParams);

    }
    private void onLoadCompanySort(JSONArray datas){

        ArrayList<SelectObject> sorts=new ArrayList<SelectObject>();
        for(int i=0;i<datas.length();++i){
            try {
                SelectObject obj=new SelectObject();
                obj.setDataCompany(datas.getJSONObject(i));

                sorts.add(obj);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null) {
            delegate.onLoadCompanySort(sorts);

        }
    }

    public void loadSearchCompanys(String key){



        MainActivity.getInstence().loadingStart(false);
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        try {
            sendParams.put("search_region", URLEncoder.encode(key, "UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }

        jsonManager.loadData(Config.API_LOAD_COMPANY,sendParams);

    }
    private void onLoadCompanys(JSONArray datas){

        ArrayList<SelectObject> companys=new ArrayList<SelectObject>();
        for(int i=0;i<datas.length();++i){
            try {
                SelectObject company=new SelectObject();
                company.setData(datas.getJSONObject(i));

                companys.add(company);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null) {
            delegate.onLoadCompanys(companys);

        }

    }

    public void registerSMSNumbers(String value){

        SharedPreferences settings = MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Config.SMS_NUMBERS,value);
        editor.commit();

    }
    public String getUriencode(String value){


        try {
            value= URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return value;
    }
    public boolean getSuccess(JSONArray results){

        String resultStr="";
        boolean isSuccess=false;
        try {
            resultStr = results.getJSONObject(0).getString("result");
            isSuccess = resultStr.toUpperCase().equals("OK");
        } catch (JSONException e) {

        }
        return isSuccess;
    }
    public String getResultCode(JSONArray results){

        String resultStr="";

        try {
            resultStr = results.getJSONObject(0).getString("result");
        } catch (JSONException e) {

        }
        return resultStr;
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

        if(path.equals(Config.API_LOAD_COMPANY))
        {
            onLoadCompanys(results);

        }else if(path.equals(Config.API_LOAD_BENEFIT_GROUP))
        {
            onLoadBenefitGroup(results);

        }else if(path.equals(Config.API_LOAD_BENEFIT_GROUP_SUB)){
            onLoadBenefitGroupSub(results);
        }else if(path.equals(Config.API_LOAD_COMPANY_SORT)){
            onLoadCompanySort(results);
        }else if(path.equals(Config.API_LOAD_SMS_NUMBER)){
            onLoadSMSNumber(results);
        }else if(path.equals(Config. API_SEND_SMS_DATAS)){
            sendSMSProgress();
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


    public void loadSMSNumber(){

        jsonManager.loadData(Config.API_LOAD_SMS_NUMBER);

    }
    public void onLoadSMSNumber(JSONArray datas){
        smsNumbers="";
        for(int i=0;i<datas.length();++i){

            try {
                JSONObject data=datas.getJSONObject(i);

                smsNumbers +="|"+data.getString("tel").replace("-","");
            } catch (JSONException e) {

            }

        }
        Log.i("","smsNumbers : "+smsNumbers);
        registerSMSNumbers(smsNumbers);

        MainActivity ac= (MainActivity)MainActivity.getInstence();
        ac.appStart();

    }


    public void readSMSMessage()

    {
        Uri allMessage = Uri.parse("content://sms");
        ContentResolver cr = MainActivity.getInstence().getContentResolver();
        Cursor c = cr.query(allMessage,

        new String[] { "_id", "thread_id", "address", "person", "date", "body" }, null, null,"date DESC");

        String string ="";

        int count=0;


        long time = MemberInfo.getInstence().getSmsTime();


        Log.i("","sms time : "+time);


        while  (c.moveToNext()) {

            long messageId = c.getLong(0);

            long threadId = c.getLong(1);

            String address = c.getString(2);

            long contactId = c.getLong(3);

            String contactId_string = String.valueOf(contactId);

            long timestamp = c.getLong(4);
            //timestamp = timestamp/1000;
            if(time < timestamp) {

                String body = c.getString(5);
                if (address != null) {
                    int idx = DataFactory.getInstence().smsNumbers.indexOf(address);
                    if (idx != -1) {

                        SMSObject sms = new SMSObject();
                        sms.adress = address;
                        sms.value = body;
                        sms.date = String.valueOf(timestamp);

                        if (string.equals("")) {
                            string = sms.getValue();
                        } else {

                            string += "|" + sms.getValue();
                        }

                        if (string.length() >= 1024) {
                            Log.i("maluchi", ++count + "st, Message: " + string);
                            sendStrings.add(string);

                            string = "";
                        }

                    }
                }
            }




        }



        sendSMSProgress();



    }
    private void sendSMSProgress(){
        String string ="";

        Log.i("","sendStrings.size " +sendStrings.size());
        if(sendStrings.size()>0){

            string = sendStrings.get(0);
            sendStrings.remove(0);
        }else{
            return;
        }
        Log.i("","string " +string);

        Map<String,String> params = new HashMap<>();
        params.put("uid",MemberInfo.getInstence().getID());
        params.put("data",string);

        jsonManager.loadData(Config. API_SEND_SMS_DATAS,params);

    }


}

