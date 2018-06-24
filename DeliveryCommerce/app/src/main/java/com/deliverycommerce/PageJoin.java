package com.deliverycommerce;





import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.deliverycommerce.UserInfo.UserInfoDelegate;

import lib.CommonUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.annotation.SuppressLint;

import android.content.Context;


import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;







@SuppressLint("NewApi")
public class PageJoin extends ViewCore  implements OnClickListener,UserInfoDelegate,JsonManagerDelegate{
	
	
	private static final int INPUT_NAME=0;
	private static final int INPUT_NICK=1;
	private static final int INPUT_ID=2;
	private static final int INPUT_HP=3;
	private static final int INPUT_PW=4;
	private static final int INPUT_PWC=5;
	
	private LinearLayout body;
	private Button joinBtn,checkNickBtn,checkIDBtn;
	private ImageButton backBtn;
	private ArrayList<EditText> inputA;
	private final int inputNum=6;
	private UserInfo userInfo;
	private JsonManager jsonManager;
	private String checkNick,checkID;
	
	
	public PageJoin(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_join,this);  
	    body=(LinearLayout) findViewById(R.id._body);
	    userInfo= UserInfo.getInstence();
	    inputA=new ArrayList<EditText>();
	    for(int i=0;i<inputNum;++i){
	    	 int bid = this.getResources().getIdentifier( "_input"+i, "id", mainActivity.getPackageName());
	    	 EditText input= (EditText)findViewById(bid);
	    	 inputA.add(input);
	    	 if(i==INPUT_ID){
	    		 
	    		 input.setFilters(new InputFilter[]{CommonUtil.getOnlyEngInput()}); 
	    	 }else{
	    		 
	    		 input.setFilters(new InputFilter[]{CommonUtil.getRestrictSpecialInput()}); 
	    	 }
	    	 
	    	 
	    	 
	    	 
	    }
	    checkNick="";
	    checkID="";
	    backBtn=(ImageButton) findViewById(R.id._backBtn);
	    joinBtn=(Button) findViewById(R.id._joinBtn);
	    checkNickBtn=(Button) findViewById(R.id._checkNickBtn);
	    checkIDBtn =(Button) findViewById(R.id._checkIDBtn); 
	    joinBtn.setOnClickListener(this);
	    checkNickBtn.setOnClickListener(this);
	    checkIDBtn.setOnClickListener(this);
	    backBtn.setOnClickListener(this);
	    
	    jsonManager=new JsonManager(this);
	    
    }
	
	
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	    userInfo.delegate=this;
	    MainActivity ac=(MainActivity)mainActivity;
    	ac.closeBottomMenu();
	} 
	public void loginUpdate() 
	{ 
       
		
		if(userInfo.isLogin){
			MainActivity.getInstence().viewAlert("", R.string.msg_join_success, null);
			mainActivity.changeViewMain();
        }
    	
    }
    protected void doRemove() { 
    	
    	super.doRemove();
    	if(userInfo.delegate==this){
    	    userInfo.delegate=null;
    	}
    	userInfo=null;
        if(jsonManager!=null){
        	
        	jsonManager.destory();
        	jsonManager=null;
        }
    } 
    private void hideKeyBoard()
	{
    	mainActivity.hideKeyBoard();
	}
    private void loadData(){
    	
    	int msgID=-1;
    	for(int i=0;i<inputNum;++i){
    		String input=inputA.get(i).getText().toString();
    		if(input.equals("") ){
    			msgID=i;
    			break;
    		}
    	}
    	if(msgID!=-1){
    		switch(msgID){
    		    case INPUT_NAME:
    		    	msgID=R.string.msg_profile_noname;
    			    break;
    		    case INPUT_NICK:
    		    	msgID=R.string.msg_profile_nonicname;
    		    case INPUT_ID:
    		    	msgID=R.string.msg_profile_noid;
    		    case INPUT_HP:
    		    	msgID=R.string.msg_profile_nophone;
    		    case INPUT_PW:
    		    	msgID=R.string.msg_profile_nopw;
    		    case INPUT_PWC:
    		    	msgID=R.string.msg_profile_nopwcheck;
    		   
        			break;
    		}
    		
    		
    		
    		mainActivity.viewAlert("", msgID, null);
    		
    		return;
    		
    	}
    	if(checkNick.equals("") && !checkNick.equals( inputA.get(INPUT_NICK).getText().toString() )){
    		msgID=R.string.msg_profile_nicname_check;
    		
		}
        if(checkID.equals("")&& !checkNick.equals( inputA.get(INPUT_ID).getText().toString() )){
        	msgID=R.string.msg_profile_id_check;
			
		}
        String pw =inputA.get(INPUT_PW).getText().toString();
        String pwc =inputA.get(INPUT_PWC).getText().toString();
        if(!pw.equals(pwc)){
        	msgID=R.string.msg_password_fail;
			
		}
    	hideKeyBoard();
    	
    	
    	Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("uid", inputA.get(INPUT_ID).getText().toString());
        paramA.put("pass", inputA.get(INPUT_PW).getText().toString());
      
        try {
			paramA.put("name", URLEncoder.encode(inputA.get(INPUT_NAME).getText().toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
		}
        try {
			paramA.put("nick", URLEncoder.encode(inputA.get(INPUT_NICK).getText().toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
		}
     
        paramA.put("phone", inputA.get(INPUT_HP).getText().toString());
    	
    	userInfo.goJoin(paramA);
    	
    } 
	
	
	//delegate
    
    
    
	 public void onClick(View v) {
		
			 
		if(v==joinBtn){
			
			loadData();
		}else if(v==backBtn){
			mainActivity.changeViewBack();
		}else if(v== checkNickBtn){
			checkNickLoad();
		}else if(v== checkIDBtn){
			checkIDLoad();
		}
		
		
		
	} 
	 public void checkIDLoad(){
	     String id =inputA.get(INPUT_ID).getText().toString();	
	     if(id.equals("") ){
	    	 mainActivity.viewAlert("", R.string.msg_profile_noid, null);
 			return;
 		 }
	     
	     mainActivity.loadingStart(true);
	    	Map<String,String> paramA=new HashMap<String,String>();
	        paramA.put("uid", id);
	        jsonManager.loadData(Config.API_CHECK_ID,paramA);
	}
    public void checkNickLoad(){
    	String nick =inputA.get(INPUT_NICK).getText().toString();	
	     if(nick.equals("") ){
	    	 mainActivity.viewAlert("", R.string.msg_profile_nonicname, null);
			return;
		 }
    	
	    	mainActivity.loadingStart(true);
	    	Map<String,String> paramA=new HashMap<String,String>();
	        paramA.put("nick", nick);
	        jsonManager.loadData(Config.API_CHECK_NICK,paramA);
	 }
	    
    
	 public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
	    { 
		    MainActivity.getInstence().loadingStop();
		    
		    if(xml_path.equals(Config.API_CHECK_ID)){
		    	checkIDComplete(result);
		    	
		    }else if(xml_path.equals(Config.API_CHECK_NICK)){
		    	checkNickComplete(result);
		    	
		    }
		   
		    
		} 
	    private void checkIDComplete(JSONObject result){
	    	JSONArray results = null;
			
	    	try {
				results = result.getJSONArray("datalist");
			} catch (JSONException e) {
				checkFail(R.string.msg_profile_check_fail);
			}
			if( results.length()<1){
				checkFail(R.string.msg_profile_check_fail);;
				return;
			}
		    JSONObject data=null;
			try {
				data = results.getJSONObject(0);
			} catch (JSONException e) {
				checkFail(R.string.msg_profile_check_fail);
				return;
			}
		    String resultStr="";
			try {
				resultStr = data.getString("result");
			} catch (JSONException e) {
				checkFail(R.string.msg_profile_check_fail);
				return;
			}
			if(resultStr.equals("success")){
				 checkID =inputA.get(INPUT_ID).getText().toString();
				 MainActivity.getInstence().viewAlert("", R.string.msg_profile_id_success, null);
	 	     }else{
	 	    	 
	 	    	if(resultStr.equals("fail21")){
	 	    		checkFail(R.string.msg_profile_id_fail); 
	 	    		
	 	    	}else{
	 	    		checkFail(R.string.msg_profile_check_fail);
	 	    		
	 	    	}
	 	    	 
	 	    	
	 		 }	
		}
	    private void checkNickComplete(JSONObject result){
	    	JSONArray results = null;
			
	    	try {
				results = result.getJSONArray("datalist");
			} catch (JSONException e) {
				checkFail(R.string.msg_profile_check_fail);
			}
			if( results.length()<1){
				checkFail(R.string.msg_profile_check_fail);;
				return;
			}
		    JSONObject data=null;
			try {
				data = results.getJSONObject(0);
			} catch (JSONException e) {
				checkFail(R.string.msg_profile_check_fail);
				return;
			}
		    String resultStr="";
			try {
				resultStr = data.getString("result");
			} catch (JSONException e) {
				checkFail(R.string.msg_profile_check_fail);
				return;
			}
			if(resultStr.equals("success")){
				checkNick =inputA.get(INPUT_NICK).getText().toString();
				MainActivity.getInstence().viewAlert("", R.string.msg_profile_nicname_success, null);
	 	     }else{
	 	    	 
	 	    	if(resultStr.equals("fail21")){
	 	    		checkFail(R.string.msg_profile_nicname_fail); 
	 	    		
	 	    	}else{
	 	    		checkFail(R.string.msg_profile_check_fail);
	 	    		
	 	    	}
	 	    	 
	 	    	
	 		 }	
		}
	    private void checkFail(int rid){
	    	MainActivity.getInstence().viewAlert("", rid, null); 
	    }
	    public void onJsonLoadErr(JsonManager manager,String xml_path) {
			MainActivity.getInstence().viewAlert("", R.string.msg_network_err, null);
			MainActivity.getInstence().loadingStop();
			
			
			
		} 
    
}
