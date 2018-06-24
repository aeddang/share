package com.deliverycommerce;





import java.util.ArrayList;


import com.deliverycommerce.UserInfo.UserInfoDelegate;

import lib.CommonUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
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







@SuppressLint("NewApi")
public class PageLogin extends ViewCore  implements OnClickListener,OnKeyListener,UserInfoDelegate{
	
	
	private FrameLayout body;
	private Button loginBtn,findPWBtn,joinBtn;
	private ImageButton backBtn;
	private ArrayList<EditText> inputA;
	private final int inputNum=2;
	private UserInfo userInfo;
	
	public PageLogin(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_login,this);  
	    body=(FrameLayout) findViewById(R.id._body);
	    userInfo= UserInfo.getInstence();
	    inputA=new ArrayList<EditText>();
	    for(int i=0;i<inputNum;++i){
	    	 int bid = this.getResources().getIdentifier( "_input"+i, "id", mainActivity.getPackageName());
	    	 EditText input= (EditText)findViewById(bid);
	    	 inputA.add(input);
	    	 input.setOnKeyListener(this);
	    	 input.setFilters(new InputFilter[]{CommonUtil.getRestrictSpecialInput()}); 
	    	 
	     }
	     
	     backBtn=(ImageButton) findViewById(R.id._backBtn);
	     loginBtn=(Button) findViewById(R.id._loginBtn);
	     findPWBtn=(Button) findViewById(R.id._findPWBtn);
	     joinBtn=(Button) findViewById(R.id._joinBtn);
	     
	     backBtn.setOnClickListener(this);
	     loginBtn.setOnClickListener(this);
	     findPWBtn.setOnClickListener(this);
	     joinBtn.setOnClickListener(this);
	    
	    
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
			MainActivity.getInstence().viewAlert("", R.string.msg_login_success, null);
			mainActivity.changeViewBack();
        }
		
    	
    }
    protected void doRemove() { 
    	
    	super.doRemove();
    	if(userInfo.delegate==this){
    	    userInfo.delegate=null;
    	}
    	userInfo=null;
    
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
    		    case 0:
    		    	msgID=R.string.msg_profile_noid;
    			    break;
    		    case 1:
    		    case 2:
    		    	msgID=R.string.msg_profile_nopw;
        			break;
    		}
    		mainActivity.viewAlert("", msgID, null);
    		
    		return;
    		
    	}
    	
    	hideKeyBoard();
    	userInfo.goLogin(inputA.get(0).getText().toString(), inputA.get(1).getText().toString());
    	
    } 
	
	
	//delegate
    
    public boolean onKey(View v, int keyCode, KeyEvent event) {
		
    	
    	 if(event.getAction() == KeyEvent.ACTION_UP) {
             switch(keyCode) {
                 case KeyEvent.KEYCODE_ENTER:
                	 loadData();
                    return true;
              }
        }
    	
    	
    	return false;
     }
     private void loadPW(){
    	
    	int msgID=-1;
    	for(int i=0;i<1;++i){
    		String input=inputA.get(i).getText().toString();
    		if(input.equals("") ){
    			msgID=i;
    			break;
    		}
    	}
    	if(msgID!=-1){
    		switch(msgID){
    		    case 0:
    		    	msgID=R.string.msg_profile_noid;
    			    break;
    		   
    		}
    		mainActivity.viewAlert("", msgID, null);
    		
    		return;
    		
    	}
    	
    	hideKeyBoard();
    	userInfo.findPW(inputA.get(0).getText().toString());
    	
    } 
	 public void onClick(View v) {
		
			 
		if(v==loginBtn){
			
			loadData();
		}else if(v==backBtn){
			mainActivity.changeViewBack();
		}else if(v== findPWBtn){
			loadPW();
		}else if(v== joinBtn){
			PageObject pInfo=new PageObject(Config.PAGE_JOIN);
			mainActivity.changeView(pInfo);
		}
		
		
		
	} 
    
    
    
    
}
