package com.deliverycommerce;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;


import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class UserInfo implements JsonManagerDelegate{
	
	
	
	private final String USER_PW="userpw";
	private final String USER_ID="userid";
	
	
	public String userID,userPW,appPW,nicName,userNO,finalFVopt;
	public UserInfoDelegate delegate;
	
	public Boolean isLogin;
	private JsonManager jsonManager;
	private static UserInfo instence;

	private SharedPreferences settings;
    
	public static UserInfo getInstence(){
    	 if(instence == null) {
         	instence = new UserInfo();
         }
         return instence;
	}
	
    public UserInfo() {
    	
    	isLogin=false;
    	settings =  MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
    	jsonManager=new JsonManager(this);
    	
    	userID=settings.getString(USER_ID, "");
    	userPW= settings.getString(USER_PW, "");
    	
    	
    }
   
    public void registerLogin(String id,String pw){
 	     SharedPreferences.Editor editor = settings.edit();
 	     if(!id.equals("")){
 	    	 editor.putString(USER_ID, id);
 	    	 userID=id;
 	     }
 	     editor.putString(USER_PW, pw);
	     editor.commit();
	     userPW=pw;
   }
   public Boolean goAutoLogin(){
   	     
	   if(!userID.equals("")&& !userPW.equals("")){
		   goLogin(userID,userPW);
		   return true;
	   }else{
		   return false;
	   }
	   
   }
    public void goLogin(String id,String pw){
    	
    	userID=id;
	    userPW=pw;
    	MainActivity.getInstence().loadingStart(true);
    	
    	Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("uid", userID);
        paramA.put("pass", userPW);
        jsonManager.loadData(Config.API_LOGIN,paramA);
    }
    public void logout(){
    	isLogin=false;
	    registerLogin("","");
	    MainActivity.getInstence().changeViewMain();
	    
    }
    public void goLogout(){
    	logout();
	    if(delegate!=null){delegate.loginUpdate();}
    }
   
    
    public void goJoin(Map<String,String> paramA){
    	userID=paramA.get("uid");
	    userPW=paramA.get("pass");
	    
    	MainActivity.getInstence().loadingStart(true);
    	jsonManager.loadData(Config.API_JOIN,paramA);
    }
    public void goFavorite(String key,String opt){
    	Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("apuid", userID);
        paramA.put("store_idx", key);
        paramA.put("opt", opt);
        finalFVopt=opt;
        jsonManager.loadData(Config.API_FAV_ADD,paramA);
    }
    public void findPW(String uid){
    	Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("uid", uid);
        
        MainActivity.getInstence().loadingStart(true);
        jsonManager.loadData(Config.API_FIND_PW,paramA);
    	
    }
    
    public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
    { 
	    MainActivity.getInstence().loadingStop();
	    
	    if(xml_path.equals(Config.API_LOGIN)){
	    	loginComplete(result);
	    }else if(xml_path.equals(Config.API_JOIN)){
	    	joinComplete(result);
	    	
	    }else if(xml_path.equals(Config.API_FIND_PW)){
	    	findPWComplete(result);
	    	
	    }else if(xml_path.equals(Config.API_FAV_ADD)){
	    	favComplete(result);
	    	
	    }
	    
	} 
    private void favComplete(JSONObject result){
    	JSONArray results = null;
		
    	try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			favFail();
		}
		if( results.length()<1){
			favFail();
			return;
		}
	    JSONObject data=null;
		try {
			data = results.getJSONObject(0);
		} catch (JSONException e) {
			favFail();
			return;
		}
	    String resultStr="";
		try {
			resultStr = data.getString("result");
		} catch (JSONException e) {
			favFail();
			return;
		}
		if(resultStr.equals("success")){
			if(finalFVopt.equals("2")){
	    		MainActivity.getInstence().viewAlert("", R.string.msg_favadd_success, null); 
	    	}else{
	    		MainActivity.getInstence().viewAlert("", R.string.msg_favremove_success, null); 
	    	}
 	     }else{
 	    	favFail();
 	    		
 	    	
 	    	
 		 }	
	}
    private void favFail(){
    	if(finalFVopt.equals("2")){
    		MainActivity.getInstence().viewAlert("", R.string.msg_favadd_fail, null); 
    	}else{
    		MainActivity.getInstence().viewAlert("", R.string.msg_favremove_fail, null); 
    	}
    	
		
    }
    private void findPWComplete(JSONObject result){
    	JSONArray results = null;
		
    	try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			findPWFail(R.string.msg_findpw_fail);
		}
		if( results.length()<1){
			findPWFail(R.string.msg_findpw_fail);
			return;
		}
	    JSONObject data=null;
		try {
			data = results.getJSONObject(0);
		} catch (JSONException e) {
			findPWFail(R.string.msg_findpw_fail);
			return;
		}
	    String resultStr="";
		try {
			resultStr = data.getString("result");
		} catch (JSONException e) {
			findPWFail(R.string.msg_login_fail);
			return;
		}
		if(resultStr.equals("success")){
			MainActivity.getInstence().viewAlert("", R.string.msg_findpw_success, null); 
 	     }else{
 	    	 
 	    	if(resultStr.equals("fail12")){
 	    		findPWFail(R.string.msg_findpw_noid); 
 	    		
 	    	}else if(resultStr.equals("fail15")){
 	    		findPWFail(R.string.msg_findpw_nophone); 
 	    		
 	    	}else if(resultStr.equals("fail16")){
 	    		findPWFail(R.string.msg_findpw_noid); 
 	    		
 	    	}else{
 	    		findPWFail(R.string.msg_findpw_fail);
 	    		
 	    	}
 	    	 
 	    	
 		 }	
	}
    private void findPWFail(int rid){
    	MainActivity.getInstence().viewAlert("", rid, null); 
    }
    private void loginComplete(JSONObject result){
    	JSONArray results = null;
		
    	try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			loginFail(R.string.msg_login_fail);
		}
		if( results.length()<1){
			loginFail(R.string.msg_login_fail);
			return;
		}
	    JSONObject data=null;
		try {
			data = results.getJSONObject(0);
		} catch (JSONException e) {
			loginFail(R.string.msg_login_fail);
			return;
		}
	    String resultStr="";
		try {
			resultStr = data.getString("result");
		} catch (JSONException e) {
			loginFail(R.string.msg_login_fail);
			return;
		}
		if(resultStr.equals("success")){
 	    	isLogin=true;
 	    	try {
 	    		nicName=data.getString("nick");
			} catch (JSONException e) {
				nicName="";
				return;
			}
 	    	try {
 	    		userNO=data.getString("mb_no");
			} catch (JSONException e) {
				userNO="";
				return;
			}
 	    	registerLogin(userID,userPW);
 	    	if(delegate!=null){delegate.loginUpdate();}
 	     }else{
 	    	 
 	    	if(resultStr.equals("fail12")){
 	    		loginFail(R.string.msg_id_wrong); 
 	    		
 	    	}else if(resultStr.equals("fail13")){
 	    		loginFail(R.string.msg_password_wrong); 
 	    		
 	    	}else if(resultStr.equals("fail14")){
 	    		loginFail(R.string.msg_findpw_sendpw_fail); 
 	    		
 	    	}else{
 	    		loginFail(R.string.msg_login_fail); 
 	    		
 	    	}
 	    	 
 	    	
 		 }	
	}
    private void loginFail(int rid){
    	MainActivity.getInstence().viewAlert("", rid, null); 
		logout();
    }
    
    private void joinComplete(JSONObject result){
    	JSONArray results = null;
		
    	try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			joinFail(R.string.msg_join_fail);
		}
		if( results.length()<1){
			joinFail(R.string.msg_join_fail);
			return;
		}
	    JSONObject data=null;
		try {
			data = results.getJSONObject(0);
		} catch (JSONException e) {
			joinFail(R.string.msg_join_fail);
			return;
		}
	    String resultStr="";
		try {
			resultStr = data.getString("result");
		} catch (JSONException e) {
			joinFail(R.string.msg_join_fail);
			return;
		}
		if(resultStr.equals("success")){
			goAutoLogin();
 	     }else{
 	    	 
 	    	if(resultStr.equals("fail23")){
 	    		loginFail(R.string.msg_join_nick_wrong); 
 	    		
 	    	}else if(resultStr.equals("fail21")){
 	    		loginFail(R.string.msg_join_id_wrong); 
 	    		
 	    	}else{
 	    		joinFail(R.string.msg_join_fail); 
 	    		
 	    	}
 	    	 
 	    	
 		 }	
	}
    private void joinFail(int rid){
    	MainActivity.getInstence().viewAlert("", rid, null); 
    	logout();
    }
    public void onJsonLoadErr(JsonManager manager,String xml_path) {
		MainActivity.getInstence().viewAlert("", R.string.msg_network_err, null);
		MainActivity.getInstence().loadingStop();
		
		
		
	} 
	
	public interface UserInfoDelegate
	{
		void loginUpdate();
		
	}
}

