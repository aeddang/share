package com.deliverycommerce;









import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import lib.core.PageObject;



import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.view.View.OnClickListener;

import android.view.animation.TranslateAnimation;


import android.content.Context;
import android.content.DialogInterface;


import android.widget.FrameLayout;
import android.widget.ImageButton;





public class NaviLeft extends FrameLayout implements OnClickListener{
	
	private ArrayList<ImageButton> btnTopA;
	private ArrayList<ImageButton> btnA;
	private ImageButton setupBtn;
	
	private final int btnNum=4;
	private final int btnTopNum=3;
	private MainActivity mainActivity;
	private Boolean isActive;
	
    public NaviLeft(Context _context,AttributeSet attrs) {
		
		super(_context,attrs,0);
		
		
	}
	public NaviLeft(Context context) {
		 super(context);
		
	     
	}
	public  void initNavi(Context context){
		
		isActive=false;
	     View.inflate(context, R.layout.menu_left,this);  
	     mainActivity=(MainActivity) MainActivity.getInstence();
	     btnA=new ArrayList<ImageButton>();
	     for(int i=0;i<btnNum;++i){
	    	 int bid = this.getResources().getIdentifier( "_btn"+i, "id", mainActivity.getPackageName());
	    	 ImageButton btn= (ImageButton)findViewById(bid);
	    	 btn.setOnClickListener(this);
	    	 btnA.add(btn);
	     }
	     btnTopA=new ArrayList<ImageButton>();
	     for(int i=0;i<btnTopNum;++i){
	    	 int bid = this.getResources().getIdentifier( "_btnTop"+i, "id", mainActivity.getPackageName());
	    	 ImageButton btn= (ImageButton)findViewById(bid);
	    	 btn.setOnClickListener(this);
	    	 btnTopA.add(btn);
	    	 
	    	 
	     }
	     
	     setupBtn=(ImageButton) findViewById(R.id._setupBtn);
	     setupBtn.setOnClickListener(this);
	}
	public void viewBox() {
		
		if(UserInfo.getInstence().isLogin==true){
			btnTopA.get(0).setSelected(true);	
    	}else{
    		btnTopA.get(0).setSelected(false);
    		
    	} 
		
		
		
		 if(!isActive){
			 isActive=true;
			 LayoutParams layout=( LayoutParams)getLayoutParams();
			 int dfX=layout.leftMargin;
			 layout.leftMargin=0;
			 setLayoutParams(layout);
			
			 TranslateAnimation moveAni=new TranslateAnimation(dfX,0,0,0);
			 moveAni.setDuration(300);
			 startAnimation(moveAni);
			 
		 }
		
	}
    public void hideBox() {
		
		if(isActive){
			 isActive=false;
			 LayoutParams layout=( LayoutParams)getLayoutParams();
			 int dfX=layout.leftMargin;
			 int moveX=-layout.width;
			 layout.leftMargin=moveX;
			 setLayoutParams(layout);
			 TranslateAnimation moveAni=new TranslateAnimation(dfX-moveX,0,0,0);
			 moveAni.setDuration(300);
			 startAnimation(moveAni);
			 
		 }
		
	}
    private boolean checkLogin() {
    
    	if(UserInfo.getInstence().isLogin==false){
    		 mainActivity.viewAlertSelect("", R.string.msg_gologin_check, new DialogInterface.OnClickListener() {
	     			public void onClick(DialogInterface dialog, int which) {
	     				PageObject pInfo;
	     				if(which==-1){
	     					pInfo=new PageObject(Config.PAGE_LOGIN);
	    		    		pInfo.dr=0;
	    					mainActivity.changeView(pInfo);		
	     				}else{
	     					
	     					
	     				}
	     				
	     			}
	    		});
    	}
    	return UserInfo.getInstence().isLogin;
    	
    }
	
    public void onClick(View v) {
		
    	PageObject pInfo;
    	if(v==setupBtn){
            pInfo=new PageObject(Config.PAGE_SETUP);
            pInfo.dr=0;
            mainActivity.changeView(pInfo);
    		return;
			
		}
    	
    	
    	
    	
    	Map<String,Object> pinfo=new HashMap<String,Object>();
    	pinfo.put("pos", "S");
    	int idx=btnA.indexOf(v);
		
		if(idx!=-1){
		    switch(idx){
		    case 0:
		    	 if(!checkLogin()){
		    		 return;
		    	 }
		    	
		    	 pInfo=new PageObject(Config.PAGE_FAV);
		    	 pInfo.dr=0;
		    	 pinfo.put("type", "S");
		    	
		    	 pinfo.put("pageUrl", Config.WEB_PAGE_FAV);
		    	 pInfo.info=pinfo;
				 mainActivity.changeView(pInfo);
		    	break;
		    case 1:
		    	if(!checkLogin()){
		    		 return;
		    	 }
		    	pInfo=new PageObject(Config.PAGE_INOUT);
		    	pInfo.dr=0;
		    	pinfo.put("titleStr", Config.WEB_TITLE_INOUT);
		    	 pinfo.put("pageUrl", Config.WEB_PAGE_INOUT);
		    	 pInfo.info=pinfo;
				 mainActivity.changeView(pInfo);
		    	break;
		    case 2:
		    	pInfo=new PageObject(Config.PAGE_NOTICE);
		    	pInfo.dr=0;
		    	pinfo.put("titleStr", Config.WEB_TITLE_NOTICE);
		    	 pinfo.put("pageUrl", Config.WEB_PAGE_NOTICE);
		    	 pInfo.info=pinfo;
				 mainActivity.changeView(pInfo);
		    	break;
		    case 3:
		    	pInfo=new PageObject(Config.PAGE_ADINFO);
		    	pInfo.dr=0;
		    	pinfo.put("titleStr", Config.WEB_TITLE_ADINFO);
		    	 pinfo.put("pageUrl", Config.WEB_PAGE_ADINFO);
		    	 pInfo.info=pinfo;
				 mainActivity.changeView(pInfo);
		    	break;
		   
		    }
			
		}
    	
		idx=btnTopA.indexOf(v);
		
		
		
		
		if(idx!=-1){
		    switch(idx){
		    case 0:
		    	if(UserInfo.getInstence().isLogin==true){
		    		UserInfo.getInstence().logout();
		    		return;
		    	}else{
		    		pInfo=new PageObject(Config.PAGE_LOGIN);
		    		pInfo.dr=0;
					mainActivity.changeView(pInfo);
		    		
		    	}
		       
		    	break;
		    case 1:
		    	if(!checkLogin()){
		    		 return;
		    	 }
		    	 pInfo=new PageObject(Config.PAGE_POINT);
		    	 pInfo.dr=0;
		    	 pinfo.put("titleStr", Config.WEB_TITLE_POINT);
		    	 pinfo.put("pageUrl", Config.WEB_PAGE_POINT);
		    	 pInfo.info=pinfo;
				 mainActivity.changeView(pInfo);
		    	break;
		    case 2:
		    	if(!checkLogin()){
		    		 return;
		    	 }
		    	 pInfo=new PageObject(Config.PAGE_COUPON);
		    	 pInfo.dr=0;
		    	 pinfo.put("titleStr", Config.WEB_TITLE_COUPON);
		    	 pinfo.put("pageUrl", Config.WEB_PAGE_COUPON);
		    	 pInfo.info=pinfo;
		    	 mainActivity.changeView(pInfo);
		    	break;
		    
		   
		    }
			
		}
		
		
	} 
	

}
