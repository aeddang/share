package com.aeddang.clipmaker;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lib.datamanager.DataManager;


public class SetupInfo implements DataManager.JsonManagerDelegate {




    private final String FINAL_DATA="FINAL_DATA";
	private final String GIF_KEY="GIF_KEY";
    private final String RESOULTION_KEY="RESOULTION_KEY";
    private final String FR_KEY="FR_KEY";

    private final String CAMERA_TYPE="CAMERA_TYPE";
    private final String DETECT_KEY="DETECT_KEY";


    public final int FRAME_UNIT=15;

    private final String DATA_DEFAULT="{}";
    private final int RESOULTION_DEFAULT=480;
    private final int FR_DEFAULT=2;

    public final int RESOULTION_MIN=320;
    public final int FR_MIN=1;

    private int resoultion;
    private int fr;



	private static SetupInfo instence;
	private SharedPreferences settings;
    private DataManager jsonManager;

    public ArrayList<GroupObject> groupA;

	public static SetupInfo getInstence(){

         return instence;
	}

    public SetupInfo() {
    	instence=this;
    	settings =  MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);

        groupA=new ArrayList<GroupObject>();
        jsonManager=new DataManager("GET");
        jsonManager.setOnJsonDelegate(this);

        resoultion=settings.getInt(RESOULTION_KEY,RESOULTION_DEFAULT);
        fr=settings.getInt(FR_KEY,FR_DEFAULT);
        if(fr<FR_MIN){
            fr=FR_MIN;
        }

    }
    public void registerData(String jsonStr){

      //  Log.i("","jsonStr : "+jsonStr);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(FINAL_DATA,jsonStr);
        editor.commit();

    }
    public String getData(){
        return settings.getString(FINAL_DATA,DATA_DEFAULT);

    }
    public void registerFR(int f){
        fr=f;
        //Log.i("","fr : "+fr);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(FR_KEY,fr);
        editor.commit();

    }
    public int getFR(){
        return fr;

    }
    public int getScanTime(int f){
       // Log.i("","fr : "+fr);

        int t=(int)Math.round(1000.f/(double)(FRAME_UNIT/f));
        Log.i("","scan t: "+t);
        return t;

    }
    public int getScanTime(){

        return getScanTime(fr);

    }
    public void registerResoultion(int r){
        resoultion=r;
        Log.i("","resoultion : "+resoultion);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(RESOULTION_KEY,r);
        editor.commit();

    }
    public int getResoultion(){
        return resoultion;

    }
    public void registerCameraType(boolean isFront){

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(CAMERA_TYPE,isFront);
        editor.commit();

    }
    public boolean getCameraType(){
        return settings.getBoolean(CAMERA_TYPE,false);

    }
    public void registerDetect(boolean isDetect){

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(DETECT_KEY,isDetect);
        editor.commit();

    }
    public boolean getDetect(){
        return settings.getBoolean(DETECT_KEY,false);

    }

    public void loadData(){
        MainActivity.getInstence().loadingStart(true);
        jsonManager.loadData(Config.DATA_SETUP);
    }
    public void onJsonCompleted(DataManager manager,String xml_path,JSONObject result)
    {
        MainActivity.getInstence().loadingStop();
       // Log.i("","onloadComplete");
        if(xml_path.equals(Config.DATA_SETUP)){
             setupComplete(result);
        }

    }
    public void onJsonLoadErr(DataManager manager,String xml_path) {
        MainActivity.getInstence().loadingStop();

        MainActivity.getInstence().viewAlertSelect("", R.string.msg_network_err, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               // Log.i("witch", "which=" + which);
                if(which==-1){
                   loadData();
                }else{
                    MainActivity ac=(MainActivity)MainActivity.getInstence();
                    JSONObject result=new JSONObject();
                    try {
                        result=new JSONObject(getData());
                    } catch (JSONException e) {

                    }


                    setupComplete(result);
                }

            }
        });




    }
    private void setupComplete(JSONObject result){



        JSONArray datas = null;

        try {
            datas = result.getJSONArray("groups");
            registerData(result.toString());
        } catch (JSONException e) {
            datas = new JSONArray();
        }
        groupA=new ArrayList<GroupObject>();

        JSONObject data=null;
        GroupObject dataObj=null;
        try {
            for(int i=0;i<datas.length();++i){
                data = datas.getJSONObject(i);
                dataObj = new GroupObject();
                dataObj.setData(data,GIF_KEY);

                groupA.add(dataObj);
            }




        } catch (JSONException e) {


        }
        MainActivity ac=(MainActivity)MainActivity.getInstence();
        ac.appStart();
    }
    /*
    public void registerPush(Boolean ac){
 	     SharedPreferences.Editor editor = settings.edit();
	     editor.putBoolean(Config.PUSH_KEY,ac);
	     editor.commit();
	  
    }
    public Boolean getPush(){
        return settings.getBoolean(Config.PUSH_KEY,isBeaconSuport);

    }
   */

}

