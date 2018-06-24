package com.credit.korea.KoreaCredit;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;

import lib.CommonUtil;
import lib.GraphicUtil;
import lib.core.PageObject;
import lib.core.ViewCore;


@SuppressLint({ "NewApi", "JavascriptInterface" })
public class PopupWeb extends ViewCore implements OnClickListener
{


	private FrameLayout body;

	private TextView textTitle;
	
	private WebView webView;
	private String pageUrl,titleStr;




	
	public PopupWeb(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
		
		if(pageInfo.info!=null){
	    	pageUrl=(String)pageInfo.info.get("pageUrl");
	    	
	    	if(pageInfo.info.get("title")==null){
	    		titleStr="";
	    	}else{
	    		titleStr=(String)pageInfo.info.get("title");
	    	}
        }else{
			pageUrl="";
			titleStr="page";
			
		}
		 
		View.inflate(context, R.layout.popup_web, this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        body=(FrameLayout) findViewById(R.id._body);
        webView=(WebView) findViewById(R.id._webViewPop);
	    


	    webView.setWebViewClient(new CustomWebViewClientPopup() ); // 응룡프로그램에서 직접 url 처리
		WebSettings set = webView.getSettings();
	    set.setJavaScriptEnabled(true);
	    set.setBuiltInZoomControls(false);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR_B);
        textTitle.setText(titleStr);

    }
	
    
	protected void doMovedInit() { 
	    super.doMovedInit();


        webView.loadUrl(pageUrl);

	}
    protected void doDirectBack() {
        if(webView.canGoBack()){
            webView.goBack();
            isDirectBack=false;
        }else{
            isDirectBack=true;

        }

    }

    protected void doRemove() { 
    	
    	super.doRemove();
        mainActivity.loadingStop();
        webView.setWebViewClient(null);
    	webView.stopLoading();
    	webView.destroy();
    	webView=null;

    	CommonUtil.clearApplicationCache(mainActivity, null);

        MainActivity ac=(MainActivity)mainActivity;

    }

    public void onClick(View v) {
		
			 
		//if(v==backBtn){

		//}
		
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

				super.onPageStarted(view, url, favicon);

                mainActivity.loadingStart(false);
		
				
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				
				super.onPageFinished(view, url);

                pageUrl=url;
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


	  
	 

}
