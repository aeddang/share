package com.deliverycommerce.web;






import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.UserInfo;
import com.deliverycommerce.delivery.DeliveryObject;
import com.deliverycommerce.selectarea.AreaList;
import com.deliverycommerce.selectarea.PopupSelectArea;
import com.deliverycommerce.selectarea.PopupSelectArea.SelectAreaDelegate;

import lib.CommonUtil;
import lib.core.PageObject;
import lib.core.ViewCore;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Bitmap;


import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;







@SuppressLint({ "NewApi", "JavascriptInterface", "SetJavaScriptEnabled" })
public class PageWeb extends ViewCore  implements OnKeyListener, OnClickListener,SelectAreaDelegate{
	
	
	private FrameLayout body;
	
	private ImageButton backBtn,menuBtn,positionSelectBtn;
	private Button searchBtn;
	private TextView title;
	private EditText searchTxt;
	private WebView webView;
	private String pageUrl,titleStr,type,pos;
	private PopupSelectArea popupSelectArea;
	private Handler openHandler=new Handler();
	public PageWeb(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
		
		if(pageInfo.info!=null){
	    	pageUrl=(String)pageInfo.info.get("pageUrl"); 
	    	
	    	if(pageInfo.info.get("titleStr")==null){
	    		titleStr="";
	    	}else{
	    		titleStr=(String)pageInfo.info.get("titleStr");
	    	}
	    	
	    	if(pageInfo.info.get("type")==null){
	    		type="N";
	    	}else{
	    		type=(String)pageInfo.info.get("type");
	    	}
	    	if(pageInfo.info.get("pos")==null){
	    		pos="M";
	    	}else{
	    		pos=(String)pageInfo.info.get("pos");
	    	}
	    	
		}else{
			pageUrl="";
			titleStr="page";
			type="N";
			pos="M";
		}
		if(type.equals("S")){
			View.inflate(context, R.layout.page_search,this);  
			searchTxt=(EditText) findViewById(R.id._searchText);
			searchBtn=(Button) findViewById(R.id._searchBtn);
			searchBtn.setOnClickListener(this);
			
			if(!titleStr.equals("")){
				searchTxt.setText(titleStr);
			}
			searchTxt.setOnKeyListener( this);
			
		}else if(type.equals("P")){
			View.inflate(context, R.layout.page_area_search,this);  
			title=(TextView) findViewById(R.id._title);
			positionSelectBtn=(ImageButton) findViewById(R.id._positionSelectBtn); 
			title.setText(titleStr);
			positionSelectBtn.setOnClickListener(this);
		}else {
			View.inflate(context, R.layout.page_web,this);  
			title=(TextView) findViewById(R.id._title);
			title.setText(titleStr);
			
		}
		popupSelectArea=null;
	    body=(FrameLayout) findViewById(R.id._body);
	    backBtn=(ImageButton) findViewById(R.id._backBtn);
	    menuBtn=(ImageButton) findViewById(R.id._menuBtn);
	    
	    webView=(WebView) findViewById(R.id._webView);
	  
	    backBtn.setOnClickListener(this);
	    menuBtn.setOnClickListener(this);
	    if(pos.equals("M")){
	    	backBtn.setVisibility(View.GONE);
	    	menuBtn.setVisibility(View.VISIBLE);
	    }else{
	    	backBtn.setVisibility(View.VISIBLE);
	    	menuBtn.setVisibility(View.GONE);
	    }
	    
	    
	    
	    webView.setWebViewClient(new CustomWebViewClient() ); // 응룡프로그램에서 직접 url 처리
		WebSettings set = webView.getSettings();
	    set.setJavaScriptEnabled(true);
	    set.setBuiltInZoomControls(false);
	    webView.addJavascriptInterface(new JavaScriptExtention(), Config.WEB_PTC);
	  
    }
	
    protected void doDirectBack() { 
    	if(popupSelectArea!=null){
    		mainActivity.removePopup(popupSelectArea);
    		popupSelectArea=null;
    		isDirectBack=false;
    	}else{
    		if(webView.canGoBack()){
        		webView.goBack();
        		isDirectBack=false;
        	}else{
        		isDirectBack=true;
        		
        	}
        	
    		
    	}
    	
    	
    	
    	
    }
    protected void doRemovePopup(ViewCore pop) { 
       if(pop==popupSelectArea){
    	   popupSelectArea=null;
    	   MainActivity ac=(MainActivity)mainActivity;
       	   ac.openBottomMenu();
       }
    	
    }
	protected void doMovedInit() { 
	    super.doMovedInit();
	    
	    MainActivity ac=(MainActivity)mainActivity;
    	ac.openBottomMenu();
	    
	    if(pageUrl.indexOf("?")==-1){
	    	pageUrl=pageUrl+"?apuid="+UserInfo.getInstence().userID;
	    }else{
	    	pageUrl=pageUrl+"&apuid="+UserInfo.getInstence().userID;
	    }
	    if(type.equals("S")){
	    	
	    	if(!titleStr.equals("")){
	    		loadWeb();
	 	    }else{
	 	    	webView.loadUrl(pageUrl);
	 	    }
	    }else{
	    	webView.loadUrl(pageUrl);
	    }
	    
	    
	    
	} 
	
    protected void doRemove() { 
    	
    	super.doRemove();
    	if(openHandler!=null){

    		openHandler=null;
    	}
        mainActivity.loadingStop();
    	webView.stopLoading();
    	webView.setWebViewClient(null);
    	webView.removeJavascriptInterface(Config.WEB_PTC);
    	webView.destroy();
    	webView=null;
    	if(popupSelectArea!=null){
    		mainActivity.removePopup(popupSelectArea);
    		popupSelectArea=null;
    		
    	}
    	CommonUtil.clearApplicationCache(mainActivity, null);
    } 
    
    public void areaSelected(ArrayList<String> areas){
    	if(popupSelectArea!=null){
    		mainActivity.removePopup(popupSelectArea);
    		
    		popupSelectArea=null;
    	}
    	MainActivity ac=(MainActivity)mainActivity;
    	ac.openBottomMenu();
    	String areaStr="";
    	for(int i=0;i<areas.size();++i){
    		if(i==0){
    			areaStr=areaStr+areas.get(i);
    		}else{
    			areaStr=areaStr+" "+areas.get(i);
    		}
    		
	    }
    	Log.i("","areaStr : "+areaStr);
    	LocationInfo.getInstence().registerArea(areaStr);
    	
    	String[] areaA=areaStr.split("\\ ");
    	String path="";
    	String adds="";
    	for(int i=0;i<areaA.length;++i){
    		if(i==0){
    			adds=areaA[i];
    		}else{
    			adds=adds+"|"+areaA[i];
    		}
    		
    	}
    	
    	
    	if(pageUrl.indexOf("?")==-1){
    		path=pageUrl+"?lat="+LocationInfo.getInstence().getLatitude()+"&lot="+LocationInfo.getInstence().getLongitude()+"&addrs="+adds;
	    }else{
	    	path=pageUrl+"&lat="+LocationInfo.getInstence().getLatitude()+"&lot="+LocationInfo.getInstence().getLongitude()+"&addrs="+adds;
	    }
    	
    	
	    webView.loadUrl(path);
    	
    	
    }
    public boolean onKey(View v, int keyCode, KeyEvent event) {
		
    	
     	  if(event.getAction() == KeyEvent.ACTION_UP) {
              switch(keyCode) {
                  case KeyEvent.KEYCODE_ENTER:
                 	   loadWeb();
                     return true;
               }
         }
     	
     	
        	return false;
      }
    private void loadWeb()
    {
    	if(searchTxt==null){
    		return;
    	}
    	
    	String key =searchTxt.getText().toString();
    	if(key.equals("")){
    		mainActivity.viewAlert("", R.string.msg_nosearch_keyword, null);
    		return;
    	}
    	
    	try {
    		key=URLEncoder.encode(key, "UTF-8");
 		} catch (UnsupportedEncodingException e) {
 			
 		}
    	String path="";
    	if(pageUrl.indexOf("?")==-1){
    		path=pageUrl+"?schTxt="+key;
	    }else{
	    	path=pageUrl+"&schTxt="+key;
	    }
	    webView.loadUrl(path);
    }
    
    
    
	 public void onClick(View v) {
		
			 
		if(v==backBtn){
			mainActivity.changeViewBack();
		}else if(v==menuBtn){
			MainActivity ac=(MainActivity)mainActivity;
			ac.openLeftMenu();
		}else if(v==searchBtn){
			 loadWeb();
			
		}else if(v==positionSelectBtn){
			PageObject pInfo=new PageObject(Config.POPUP_SELECT_AREA);
			pInfo.dr=1;
	    	popupSelectArea=(PopupSelectArea) mainActivity.addPopup(pInfo);
	    	popupSelectArea.delegate=this;
	    	
		}  
		
		
		
	} 
	 
	 public void  callStorePopup(String name ,String idx){
		 webView.stopLoading();
		 
		 Log.i("","callStorePopup : "+name+" "+idx);
			Map<String,Object> pinfo=new HashMap<String,Object>();
	    	pinfo.put("titleStr", name);
	    	pinfo.put("pageUrl", Config.WEB_PAGE_STORE+"?store_idx="+idx);
			PageObject pInfo=new PageObject(Config.POPUP_WEB_INFO);
			pInfo.dr=1;
			pInfo.info=pinfo;
	    	MainActivity.getInstence().addPopup(pInfo);
		 
	 }
	 
	 final class CustomWebViewClient extends WebViewClient
	 {
		 @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
		         return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.i("","WEB : "+url);
				super.onPageStarted(view, url, favicon);
                mainActivity.loadingStart(false);
		
				
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				
				super.onPageFinished(view, url);
				mainActivity.loadingStop();
				
			}

			@Override
			public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
				
				super.doUpdateVisitedHistory(view, url, isReload);
			}

			@Override
			public void onScaleChanged(WebView view, float oldScale, float newScale) {
				
				super.onScaleChanged(view, oldScale, newScale);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				
				super.onReceivedError(view, errorCode, description, failingUrl);
				MainActivity.getInstence().viewAlert("", R.string.msg_network_err, null);
			}
		 
		 
	 }
	 
	 final class JavaScriptExtention{
		    JavaScriptExtention(){}
		   @JavascriptInterface
		   public void openStorePage(final String name ,final String idx){
		    	
			   Log.i("","openStorePage : "+name+" "+idx);
			  
		    	openHandler.post(new Runnable() {
			        public void run() {
			        	 callStorePopup(name ,idx);
			        }
			    });
		   }
     } 
	   
}
