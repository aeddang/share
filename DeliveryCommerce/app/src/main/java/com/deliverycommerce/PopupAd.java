package com.deliverycommerce;






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
import com.deliverycommerce.selectarea.AreaList;
import com.deliverycommerce.selectarea.PopupSelectArea;
import com.deliverycommerce.selectarea.PopupSelectArea.SelectAreaDelegate;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.imagemanager.ImageLoader;
import lib.imagemanager.ImageLoader.ImageLoaderDelegate;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


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
public class PopupAd extends ViewCore  implements  OnClickListener,ImageLoaderDelegate{
	
	
	private FrameLayout body;
	
	private Button btnClose,btnClose1;
	private ImageView image;
	private Bitmap bitmap;
	private AdObject adInfo;
	private ImageLoader imageLoader;
	private Boolean isActiveBT;
	public PopupAd(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo);
        MainActivity ac=(MainActivity)mainActivity;
        isActiveBT=ac.isOpenBottomMenu();
		if(pageInfo.info!=null){
	    	
	    	if(pageInfo.info.get("adInfo")==null){
	    		adInfo=new AdObject();
	    	}else{
	    		adInfo=(AdObject)pageInfo.info.get("adInfo");
	    	}
	    	
	    	
	    	
		}else{
		
			
		}
		View.inflate(context, R.layout.popup_ad,this);  
		
		body=(FrameLayout) findViewById(R.id._body);
		btnClose=(Button) findViewById(R.id._btnClose);
		btnClose1=(Button) findViewById(R.id._btnClose1);
		image=(ImageView) findViewById(R.id._image);
	  
		btnClose.setOnClickListener(this);
		btnClose1.setOnClickListener(this);
		image.setOnClickListener(this);
	  
    }
	
    
	protected void doMovedInit() { 
	    super.doMovedInit();
	    
	    MainActivity ac=(MainActivity)mainActivity;
	    ac.closeBottomMenu();
	    imageLoader=new ImageLoader(this);
		imageLoader.loadImg(adInfo.img);
	} 
	
    protected void doRemove() { 
    	
    	super.doRemove();
    	removeImage();
        MainActivity ac=(MainActivity)mainActivity;
        if( isActiveBT){
            ac.openBottomMenu();

        }
    } 
    private void  removeImage()
	{

    	 if(imageLoader!=null){
    		 imageLoader.removeLoader();
    		 imageLoader=null;
    	 }
    	 
    	 if(bitmap!=null){
             image.setImageBitmap(null);
    		 bitmap.recycle();
    		 bitmap=null;
    		 
    	 }
		
	}
	public void onImageLoadCompleted(ImageLoader loader,Bitmap img ){
   		
    	if( img==null){
    		Drawable drawable=getResources().getDrawable( R.drawable.transparent);
    		image.setImageDrawable(drawable);
    		return;
		}
    	bitmap=img;
		
    	image.setImageBitmap(img);
	    /*
		LayoutParams layout=(LayoutParams)img.getLayoutParams();
	    int w=img.getWidth();
	    float sc=(float)image.getHeight()/(float)image.getWidth();
	    Log.i("","sc :"+sc);
	    int h=(int)Math.floor((float)w*sc);
	    layout.height=h;
	    Log.i("","H :"+h+"  "+w+"  ih: "+image.getHeight()+"  iw :"+image.getWidth());
	    img.setLayoutParams(layout);
		*/
   	}
   
    public void onClick(View v) {
		
			 
		if(v==image){
			Map<String,Object> pinfo=new HashMap<String,Object>();
			PageObject pInfo=new PageObject(Config.PAGE_IDEL);
	    	//pinfo.put("type", "P");
	    	//pinfo.put("titleStr", Config.WEB_TITLE_IDEL);
	    	//pinfo.put("pageUrl", adInfo.url);
	    	//pInfo.info=pinfo;
	    	mainActivity.changeView(pInfo);
		}else{
			
			
		}
		mainActivity.removePopup(this);

	   
	} 
	 
}
