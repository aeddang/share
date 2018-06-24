package com.deliverycommerce.web;






import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.UserInfo;
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







@SuppressLint({ "NewApi", "JavascriptInterface" })
public class PopupWeb extends ViewCore  implements  OnClickListener{
	
	
	private FrameLayout body;
	
	private ImageButton backBtn;
	private TextView title;
	
	private WebView webView;
	private String pageUrl,titleStr;
	private Boolean isActiveBT;
	public PopupWebDelegate delegate;
	
	public PopupWeb(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
		
		if(pageInfo.info!=null){
	    	pageUrl=(String)pageInfo.info.get("pageUrl"); 
	    	
	    	if(pageInfo.info.get("titleStr")==null){
	    		titleStr="";
	    	}else{
	    		titleStr=(String)pageInfo.info.get("titleStr");
	    	}
	    	
	    	
	    	
		}else{
			pageUrl="";
			titleStr="page";
			
		}
		 
		View.inflate(context, R.layout.popup_web,this);  
		
		
		title=(TextView) findViewById(R.id._title);
		title.setText(titleStr);
		body=(FrameLayout) findViewById(R.id._body);
	    backBtn=(ImageButton) findViewById(R.id._backBtn);
	    webView=(WebView) findViewById(R.id._webViewPop);
	    
	    backBtn.setOnClickListener(this);

        MainActivity ac=(MainActivity)mainActivity;
        isActiveBT=ac.isOpenBottomMenu();
	    webView.setWebViewClient(new CustomWebViewClientPopup() ); // 응룡프로그램에서 직접 url 처리
		WebSettings set = webView.getSettings();
	    set.setJavaScriptEnabled(true);
	    set.setBuiltInZoomControls(false);
	    webView.addJavascriptInterface(new JavaScriptExtentionPopup(), Config.WEB_PTC);
	  
    }
	
    
	protected void doMovedInit() { 
	    super.doMovedInit();
	    
	    MainActivity ac=(MainActivity)mainActivity;

    	ac.closeBottomMenu();
    
	    webView.loadUrl(pageUrl);
	   
	} 
	
    protected void doRemove() { 
    	
    	super.doRemove();
        mainActivity.loadingStop();
        webView.setWebViewClient(null);
    	webView.stopLoading();
    	webView.removeJavascriptInterface(Config.WEB_PTC);
    	webView.destroy();
    	webView=null;
    	CommonUtil.clearApplicationCache(mainActivity, null);

        MainActivity ac=(MainActivity)mainActivity;
        if( isActiveBT){
            ac.openBottomMenu();

        }
    } 
    
   
    public void onClick(View v) {
		
			 
		if(v==backBtn){
			if(delegate!=null){delegate.closePopup(this);}
			MainActivity ac=(MainActivity)mainActivity;
		    if( isActiveBT){
		    	 ac.openBottomMenu();
		    	
		    }
			mainActivity.removePopup(this);
		}
		
	} 
	 final class CustomWebViewClientPopup extends WebViewClient
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
	 final class JavaScriptExtentionPopup{
		 JavaScriptExtentionPopup(){}
		  
     }

	  
	 
	 public interface PopupWebDelegate
		{
			void closePopup(ViewCore pop);
			
		}
}
