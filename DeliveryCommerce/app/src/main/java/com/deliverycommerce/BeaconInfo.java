package com.deliverycommerce;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import org.altbeacon.beacon.Beacon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deliverycommerce.LocationInfo.LocationInfoDelegate;

import lib.CommonUtil;
import lib.CustomTimer;
import lib.CustomTimer.TimerDelegate;
import lib.core.PageObject;
import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;


import android.content.Context;

import android.content.SharedPreferences;

import android.util.Log;

public class BeaconInfo implements JsonManagerDelegate,LocationInfoDelegate,TimerDelegate{
	
	
	private JsonManager jsonManager;
	private static BeaconInfo instence;
	private Context context;
	private Map<String,String> findOpt;
	private SharedPreferences settings;
	
	private long finalAdTime;

    public BeaconInfoDelegate delegate;

	
	private CustomTimer checkTimer;
	private Boolean isLoading;

    public ArrayList<String>beacons;

    public long adDelayTime=0;

    public static BeaconInfo getInstence(){
    	 
         return instence;
	}
	
    public BeaconInfo(Context _context) {
    	
    	context=_context;
    	instence=this;
    	isLoading=false;
    	jsonManager=new JsonManager(this);
    	settings =  MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
    	finalAdTime=settings.getLong(Config.AD_TIME_KEY, 0);

    	Log.i("", "settingsAdTime : "+finalAdTime);
    	checkTimer=new CustomTimer(5000,1,this);

        beacons=new ArrayList<String>();
        setAdDelayTime();
    	Log.i("", "finalAdTime : "+finalAdTime +" adDelayTime : "+adDelayTime);
    }
    public void setAdDelayTime(){

        int t=SetupInfo.getInstence().getIdelTime();
        switch(t){

            case 1:
                adDelayTime=1000*60*60;
                break;
            case 2:
                adDelayTime=1000*60*60*2;
                break;
            case 3:
                adDelayTime=1000*60*60*3;
                break;
            default:
                adDelayTime=1000*60;
                break;
        }

    }
    private void registerAdTime(){
	     SharedPreferences.Editor editor = settings.edit();
	     Date now=new Date();
	     finalAdTime=now.getTime();
	     Log.i("", "registerAdTime : "+finalAdTime);
	     editor.putLong(Config.AD_TIME_KEY, finalAdTime);
	     editor.commit();

   }

    private  Boolean checkAdAble(){
    	if(isLoading){
    		return false;
    		
    	}
    	
    	Date now=new Date();
	    long cTime=now.getTime();
    	long gep=cTime-finalAdTime;
    	if(gep<adDelayTime && finalAdTime!=0){
	    	return false;
	    }else{
	    	return true;
	    	
	    }

    	
    	
	}
    public void beaconReset(){

        beacons=new ArrayList<String>();
    }

    public void beaconNotifier(Collection<Beacon> bcs){

        ArrayList<String> updates=new ArrayList<String>();

        ArrayList<Beacon> adds=new ArrayList<Beacon>();
        boolean isEnted = false;
        //Log.i("","Beacon id : "+bcs.size());


        for (Beacon bc : bcs) {

            //Log.i("","Beacon id : "+bc.getId1().toString());
            String idt=bc.getId1().toString();
            if(updates.indexOf(idt)==-1) {
                if (beacons.indexOf(idt) == -1) {
                    beaconEnter(bc);
                    adds.add(bc);
                    isEnted = true;
                } else {

                }
                updates.add(idt);
            }





         }
         beacons=updates;
         if(isEnted){

              if(delegate!=null){delegate.beaconUpdate(adds);}

         }
    }
    private void beaconEnter(Beacon bc){
        if(SetupInfo.getInstence().getIdel()==true){
            if(checkAdAble()==true) {
                loadAd(bc);
            }
        }

    }

    private void loadAd(Beacon bc){
    	Date now=new Date();

    	Log.i("", "LocationInfo start");
    	LocationInfo linfo=LocationInfo.getInstence();	
        if(linfo.isChecking){
    	    return;
        }else{
        	
        	
        	isLoading=true;
        	findOpt=new HashMap<String,String>();
            findOpt.put("apuid",UserInfo.getInstence().userID);
            findOpt.put("dcode",bc.getId1().toString());
            locationUpdate();
        }	
       
    } 
    public void onTime(CustomTimer timer){
		
	}
	public void onComplete(CustomTimer timer){
		
		Log.i("", "LocationInfo find fail");
		LocationInfo.getInstence().checkLocationStop();
		LocationInfo.getInstence().delegate=null; 
		findOpt=null;
		isLoading=false;
	}
    public void locationUpdate(){
    	checkTimer.timerStop();
		if(findOpt==null){
			return;
		}
		
		LocationInfo linfo=LocationInfo.getInstence();	
		if(linfo.delegate==this){
			linfo.delegate=null; 
		}
		findOpt.put("lat", linfo.getLatitude()+"");
        findOpt.put("lot", linfo.getLongitude()+"");
        jsonManager.loadData(Config.API_LOAD_BEACON,findOpt);
        findOpt=null;
    	
    	
    }
    public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
    {


       	isLoading=false;
	    
	    JSONArray results = null;
	    ArrayList<String> lists=new ArrayList<String>();
	    try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			return;
		}
	    if( results.length()<1){
			return;
		}
	    JSONObject data=null;
		try {
			data = results.getJSONObject(0);
		} catch (JSONException e) {
			return;
		}
	    
	    String resultStr="";
		try {
			resultStr = data.getString("result");
		} catch (JSONException e) {
			return;
		}
		if(resultStr.equals("success")){
			AdObject ad=new AdObject();
			ad.setData(data);
			registerAdTime();
			
			Map<String,Object> pinfo=new HashMap<String,Object>();
			PageObject pInfo=new PageObject(Config.POPUP_AD);
	    	pinfo.put("adInfo", ad);
			pInfo.dr=1;
			pInfo.info=pinfo;
			MainActivity.getInstence().addPopup(pInfo);
			
		}else{
 	    }	
		

		
	} 
   
    public void onJsonLoadErr(JsonManager manager,String xml_path) {
		
	}

    public interface BeaconInfoDelegate
    {
        void beaconUpdate(ArrayList<Beacon> adds);

    }
   
}

