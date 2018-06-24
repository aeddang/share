package com.credit.korea.KoreaCredit.mypage.consume;


import android.util.Log;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.mypage.MyPageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;

public class ConsumeInfo implements DataManager.JsonManagerDelegate
{
    public static final String MODIFY_BENEFIT_ADD="beneadd";
    public static final String MODIFY_BENEFIT_DELETE="benedel";
    public static final String MODIFY_BENEFIT_REPLACE="beneedit";
    private String currentModifyType;

    public ArrayList<String> births;
    private ArrayList<AddedGroup> addeds;
    private ArrayList<BenifitGroup> benifits;
    private DefaultObject defaultObject;

    private static ConsumeInfo instence;
    private DataManager jsonManager;


    public ConsumeInfoDelegate delegate;
    public ConsumeInfoModifyDelegate mdDelegate;
    public ConsumeAutoUpdateDelegate updateDelegate;

    public String currentAutoUpdate;
    public boolean isSMS;
    public interface ConsumeAutoUpdateDelegate
    {
        void onLoadAutoUpdate(boolean isAutoUpdate);
        void onModifyAutoUpdate(boolean result);



    }
    public interface ConsumeInfoDelegate
    {
        void onLoadDefaultData(DefaultObject defaultObject);
        void onLoadAddedDatas(ArrayList<AddedGroup> addeds);
        void onLoadBenifitDatas(ArrayList<BenifitGroup> benifits);


    }
    public interface ConsumeInfoModifyDelegate
    {

        void onModifyDefaultData(boolean result);
        void onModifyAddedDatas(boolean result);
        void onModifyBenifitDatas(boolean result);



    }
    public static ConsumeInfo getInstence(){

        if(instence==null){
            instence=new ConsumeInfo();

        }

        return instence;


    }

    public ConsumeInfo() {


        births=new ArrayList<String>();

        long now = System.currentTimeMillis();

        Date dat = new Date(now);
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyy");
        int startYear=Integer.parseInt(dateNow.format(dat))-14;
        int endYear=startYear-86;
        for(int i=startYear;i>=endYear;--i){

            births.add(String.valueOf(i));
        }
        currentAutoUpdate="";
        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);
    }

    public void resetInfo(){

        addeds=null;
        benifits=null;
        defaultObject=null;

    }

    public DefaultObject getDefaultData(){
        return defaultObject;
    }
    public void loadDefaultData(boolean isReset){

        if(isReset==true){

            defaultObject=null;
        }


        if(defaultObject==null) {

            Map<String, String> sendParams = new HashMap<String, String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());
            MainActivity.getInstence().loadingStart(false);
            jsonManager.loadData(Config.API_LOAD_CONSUME_DEFAULT, sendParams);
        }else{

            if(delegate!=null){
                delegate.onLoadDefaultData(defaultObject);
            }
        }



    }

    public void loadAutoUpdate(){

        if(currentAutoUpdate.equals("")==false){
            if(updateDelegate!=null){
                updateDelegate.onLoadAutoUpdate(currentAutoUpdate.equals("Y"));
            }
            return;
        }

        Map<String, String> sendParams = new HashMap<String, String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());

        jsonManager.loadData(Config.API_LOAD_AUTOUPDATE, sendParams);


    }
    private void onLoadAutoUpdate(JSONArray results){


        try {


            boolean isSuccess=DataFactory.getInstence().getSuccess(results);
            if(isSuccess==true) {
                JSONObject data = results.getJSONObject(0);
                String sms=data.getString("sms_auto");
                isSMS=sms.equals("Y");

                currentAutoUpdate=data.getString("pat_info");
                if(updateDelegate!=null){
                    updateDelegate.onLoadAutoUpdate(currentAutoUpdate.equals("Y"));
                }


            }else{
                MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);

            }
        } catch (JSONException e) {
            Log.i("", "onLoadDefaultData ");
            MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
            return;
        }



    }

    public void resetAutoUpdate(){

        currentAutoUpdate="";
    }
    public void modifyAutoUpdateComplete(boolean isAuto){
        if(isAuto==true){
            currentAutoUpdate = "Y";
            isSMS=true;
        }else{
            currentAutoUpdate = "N";
        }
    }
    public void modifyAutoUpdate(boolean isAuto){


        Map<String, String> sendParams = new HashMap<String, String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        if(isAuto==true){
            sendParams.put("pat_info", "Y");
        }else{
            sendParams.put("pat_info", "N");
        }

        jsonManager.loadData(Config.API_MODIFY_AUTOUPDATE, sendParams);
        MyPageInfo.getInstence().resetMyInfo();

    }

    private void onModifyAutoUpdate(JSONArray results){


        boolean isSuccess=DataFactory.getInstence().getSuccess(results);
        if(isSuccess==true) {
            if(updateDelegate!=null){
                updateDelegate.onModifyAutoUpdate(true);
            }


        }else{
            if(updateDelegate!=null){
                updateDelegate.onModifyAutoUpdate(false);
            }

        }


    }

    private void onLoadDefaultData(JSONArray results){


        try {


            boolean isSuccess=DataFactory.getInstence().getSuccess(results);
            if(isSuccess==true) {
                defaultObject=new DefaultObject();
                JSONObject data = results.getJSONObject(0);
                defaultObject.setData(data);
            }else{

                String resultStr = results.getJSONObject(0).getString("result");
                boolean isNone = resultStr.toUpperCase().equals("NO");
                if(isNone==true){
                    defaultObject=new DefaultObject();
                }else{

                    isNone = resultStr.toUpperCase().equals("N");
                    if(isNone==true){
                        defaultObject=new DefaultObject();
                    }else{


                        MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
                        return;
                    }
                }




            }
        } catch (JSONException e) {
            Log.i("", "onLoadDefaultData ");
            MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
            return;
        }

        if(delegate!=null && defaultObject!=null){
            delegate.onLoadDefaultData(defaultObject);
        }

    }

    public ArrayList<AddedGroup> getAddedData(){
        return addeds;
    }
    public void loadAddedData(boolean isReset){

        if(isReset==true){
            addeds=null;
        }else{

        }
        if(addeds==null){
            Map<String, String> sendParams = new HashMap<String, String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());
            MainActivity.getInstence().loadingStart(false);
            jsonManager.loadData(Config.API_LOAD_CONSUME_ADDED,sendParams);
        }
        if(delegate!=null && addeds!=null){
            delegate.onLoadAddedDatas(addeds);
        }

    }
    private void onLoadAddedDatas(JSONArray results){

        addeds=new ArrayList<AddedGroup>();
        try {

            for(int i=0;i<results.length();++i) {
                JSONObject jdata = results.getJSONObject(i);
                AddedGroup obj=new AddedGroup();
                obj.setData(jdata);
                addeds.add(obj);
            }

        } catch (JSONException e) {
            Log.i("", "onLoadAddedDatas ");
            MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
            return;
        }

        if(delegate!=null && addeds!=null){
            delegate.onLoadAddedDatas(addeds);
        }

    }

    public ArrayList<BenifitGroup> getBenifitData(){
        return benifits;
    }
    public void loadBenifitData(boolean isReset){

        if(isReset==true){
            benifits=null;
        }else{

        }
        if(benifits==null){
            Map<String, String> sendParams = new HashMap<String, String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());
            MainActivity.getInstence().loadingStart(false);
            jsonManager.loadData(Config.API_LOAD_CONSUME_BENIFIT,sendParams);
        }
        if(delegate!=null && benifits!=null){
            delegate.onLoadBenifitDatas(benifits);
        }

    }
    private void onLoadBenifitDatas(JSONArray results){

        benifits=new ArrayList<BenifitGroup>();


        try {
            Log.i("","results : "+results.length());
            for(int i=0;i<results.length();++i) {
                JSONObject jdata = results.getJSONObject(i);
                BenifitGroup obj=new BenifitGroup();
                obj.setData(jdata);
                benifits.add(obj);
            }

        } catch (JSONException e) {

            Log.i("", "onLoadBenifitDatas ");
            MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
            return;
        }

        if(delegate!=null && benifits!=null){
            delegate.onLoadBenifitDatas(benifits);
        }

    }
    public void modifyDefaultData(Map<String, String> params)
    {

        params.put("opt", "basicedit");
        params.put("uid", MemberInfo.getInstence().getID());
        MainActivity.getInstence().loadingStart(false);

        jsonManager.loadData(Config.API_MODIFY_CONSUME_DEFAULT,params);

    }

    public void onModifyDefaultData(JSONArray results){

        boolean isSuccess=DataFactory.getInstence().getSuccess(results);
        if(mdDelegate!=null){
            mdDelegate.onModifyDefaultData(isSuccess);
        }

    }


    public void modifyAddedData(ArrayList<AddedGroup> addeds)
    {

        Map<String, String> sendParams = new HashMap<String, String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("opt", "subedit");

        String str="";
        for(int i=0;i<addeds.size();++i){

            for(int x=0;x<addeds.get(i).lists.size();++x){
                if(addeds.get(i).lists.get(x).isSelected==true) {
                    if (str.equals("") == true) {
                        str = addeds.get(i).lists.get(x).seq;

                    } else {
                        str = str + "|" + addeds.get(i).lists.get(x).seq;

                    }
                }

            }
        }

        sendParams.put("seldata", str);
        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_MODIFY_CONSUME_ADDED,sendParams);
    }
    private void onModifyAddedData(JSONArray results){

        boolean isSuccess=DataFactory.getInstence().getSuccess(results);
        if(mdDelegate!=null){
            mdDelegate.onModifyAddedDatas(isSuccess);
        }
    }

    public void modifyBenefitData(ArrayList<BenifitGroup> benifits)
    {
        currentModifyType = MODIFY_BENEFIT_ADD;

        Map<String, String> sendParams = new HashMap<String, String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("opt", currentModifyType);

        String str="";
        for(int i=0;i<benifits.size();++i){

            for(int x=0;x<benifits.get(i).lists.size();++x){
                if(benifits.get(i).lists.get(x).isSelected==true) {
                    if (str.equals("") == true) {
                        str = benifits.get(i).lists.get(x).seq;

                    } else {
                        str = str + "|" + benifits.get(i).lists.get(x).seq;

                    }
                }

            }
        }

        sendParams.put("seldata", str);
        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_MODIFY_CONSUME_BENIFIT,sendParams);

    }
    public void deleteBenefitData(BenifitObject benifit)
    {
        currentModifyType = MODIFY_BENEFIT_DELETE;
        Map<String, String> sendParams = new HashMap<String, String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("opt", currentModifyType);
        sendParams.put("bene_idx", benifit.seq);
        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_MODIFY_CONSUME_BENIFIT,sendParams);

    }
    public void modifyBenefitDetailData(Map<String, String> sendParams)
    {
        currentModifyType = MODIFY_BENEFIT_REPLACE;

        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("opt", currentModifyType);

        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_MODIFY_CONSUME_BENIFIT,sendParams);

    }
    private void onModifyBenefitData(JSONArray results){

        boolean isSuccess=DataFactory.getInstence().getSuccess(results);
        if(mdDelegate!=null){
            mdDelegate.onModifyBenifitDatas(isSuccess);
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

        if(path.equals(Config.API_LOAD_CONSUME_ADDED)){

             onLoadAddedDatas(results);

        }else if(path.equals(Config.API_LOAD_CONSUME_BENIFIT)){

             onLoadBenifitDatas(results);

        }else if(path.equals(Config.API_LOAD_CONSUME_DEFAULT)){

            onLoadDefaultData(results);

        }else if(path.equals(Config.API_LOAD_CONSUME_DEFAULT)){

            onLoadDefaultData(results);

        }else if(path.equals(Config.API_MODIFY_CONSUME_DEFAULT)){

            onModifyDefaultData(results);

        }else if(path.equals(Config.API_MODIFY_CONSUME_BENIFIT)){

            onModifyBenefitData(results);

        }else if(path.equals(Config.API_MODIFY_CONSUME_ADDED)){

            onModifyAddedData(results);

        }else if(path.equals(Config.API_LOAD_AUTOUPDATE)){

            onLoadAutoUpdate(results);

        }else if(path.equals(Config.API_MODIFY_AUTOUPDATE)){

            onModifyAutoUpdate(results);

        }

    }
    public void onJsonParseErr(DataManager manager,String path) {
        MainActivity.getInstence().loadingStop();
        MainActivity.getInstence().viewAlert("",R.string.msg_data_parse_err,null);
    }
    public void onJsonLoadErr(DataManager manager,String path) {

        MainActivity.getInstence().loadingStop();
        MainActivity.getInstence().viewAlert("", R.string.msg_network_err,null);

    }



}






