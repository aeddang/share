package com.deliverycommerce;









import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;



import android.util.Log;
import android.view.View;

import android.view.View.OnClickListener;

import android.view.animation.TranslateAnimation;


import android.content.Context;


import android.widget.FrameLayout;
import android.widget.ImageButton;





public class NaviBottom extends FrameLayout implements OnClickListener{
	
	
	private ArrayList<ImageButton> btnA;
	private final int btnNum=4;
	private MainActivity mainActivity;
	public Boolean isActive=false;
	public NaviBottom(Context context) {
		 super(context);
		 isActive=false;
	     View.inflate(context, R.layout.menu_bottom,this);  
	     mainActivity=(MainActivity) MainActivity.getInstence();
	     btnA=new ArrayList<ImageButton>();
	     for(int i=0;i<btnNum;++i){
	    	 int bid = this.getResources().getIdentifier( "_btn"+i, "id", mainActivity.getPackageName());
	    	 ImageButton btn= (ImageButton)findViewById(bid);
	    	 btn.setOnClickListener(this);
	    	 btnA.add(btn);
	     }
	     
	}
	
	public void viewBox(int idx) {
		
		for(int i=0;i<btnNum;++i){
	    	if(idx==i){
	    		btnA.get(i).setSelected(true);
	    	}else{
	    		btnA.get(i).setSelected(false);
	    	}
	     }
		
		 if(!isActive){
			 isActive=true;
			 LayoutParams layout=( LayoutParams)getLayoutParams();
			 int dfY=layout.topMargin;
			 layout.topMargin=0;
			 layout.leftMargin=0;
			 setLayoutParams(layout);
			
			 TranslateAnimation moveAni=new TranslateAnimation(0,0,dfY,0);
			 moveAni.setDuration(300);
			 startAnimation(moveAni);
			 
		 }
		
	}
    public void hideBox() {
    	Log.i("","HIDEBOX CALL");
		if(isActive){
			Log.i("","HIDEBOX");
			 isActive=false;
			 LayoutParams layout=( LayoutParams)getLayoutParams();
			 int dfY=layout.topMargin;
			 int moveY=layout.height;
			 layout.topMargin=moveY;
			 setLayoutParams(layout);
			 TranslateAnimation moveAni=new TranslateAnimation(0,0,dfY-moveY,0);
			 moveAni.setDuration(300);
			 startAnimation(moveAni);
			 
		 }
		
	}
	
	
    public void onClick(View v) {
		
		int idx=btnA.indexOf(v);
		
		PageObject pInfo;
		Map<String,Object> pinfo=new HashMap<String,Object>();
		pinfo.put("pos", "M");
		
		if(idx!=-1){
		    switch(idx){
		    case 0:
		        pInfo=new PageObject(Config.PAGE_DELIVERY);
		        
				mainActivity.changeView(pInfo);
		    	break;
		    case 1:
		    	pInfo=new PageObject(Config.PAGE_IDEL);
		    	//pinfo.put("type", "P");
		    	//pinfo.put("titleStr", Config.WEB_TITLE_IDEL);
		    	// pinfo.put("pageUrl", Config.WEB_PAGE_IDEL);
		    	// pInfo.info=pinfo;
		    	mainActivity.changeView(pInfo);
		    	break;
		    case 2:
		    	
		    	pInfo=new PageObject(Config.PAGE_SEARCH);
		    	pinfo.put("type", "S");
		    	
		    	pinfo.put("pageUrl", Config.WEB_PAGE_SEARCH);
		    	pInfo.info=pinfo;
		    	mainActivity.changeView(pInfo);
		    	break;
		    case 3:
		    	pInfo=new PageObject(Config.PAGE_AD);
		    	pinfo.put("titleStr", Config.WEB_TITLE_AD);
		    	 pinfo.put("pageUrl", Config.WEB_PAGE_AD);
		    	 pInfo.info=pinfo;
		    	mainActivity.changeView(pInfo);
		    	break;
		   
		    }
			
		}
    	
	} 
	

}
