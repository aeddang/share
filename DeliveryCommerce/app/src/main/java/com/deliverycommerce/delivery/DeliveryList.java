package com.deliverycommerce.delivery;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.deliverycommerce.Config;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.UserInfo;
import com.deliverycommerce.selectarea.PopupSelectArea;


import lib.CommonUtil;
import lib.GraphicUtil;
import lib.PhotoSelecter;
import lib.PhotoSelecter.PhotoSelecterDelegate;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.imagemanager.ImageLoader;
import lib.imagemanager.ImageLoader.ImageLoaderDelegate;

import lib.view.Gesture;
import lib.view.RackableScrollView;
import lib.view.SlideBox;
import lib.view.Gesture.GestureDelegate;
import lib.view.SlideBox.SlideBoxDelegate;



import android.net.Uri;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;






@SuppressLint("NewApi")
public class DeliveryList extends FrameLayout implements  OnClickListener,ImageLoaderDelegate{
	
	private TextView title,dc,dis,workTime,deil;
	public DeliveryObject info;
	private LinearLayout textBox;
	private FrameLayout imgBox;
	private ImageView img;
	private ImageLoader imageLoader;
	private ImageView likeBtn,callBtn;
	private Bitmap bitmap;
	private ArrayList<ImageView> stars;
	private final int starNum=5; 
	public DeliveryList(Context context) 
	{
		super(context); 
	    View.inflate(context, R.layout.delivery_list,this);  
	    title=(TextView) findViewById(R.id._title);
	    dis=(TextView) findViewById(R.id._dis);
	    workTime=(TextView) findViewById(R.id._workTime);
	    deil=(TextView) findViewById(R.id._deil);
	    dc=(TextView) findViewById(R.id._dc);
	    
	    textBox=(LinearLayout) findViewById(R.id._textBox);
	    imgBox=(FrameLayout) findViewById(R.id._imgBox);
	    img=(ImageView) findViewById(R.id._img);
	    
	    likeBtn=(ImageView) findViewById(R.id._likeBtn);
	    callBtn=(ImageView) findViewById(R.id._callBtn);
	    
	    stars=new ArrayList<ImageView>();
	    for(int i=0;i<starNum;++i){
	    	 int bid = this.getResources().getIdentifier( "_star"+i, "id", MainActivity.getInstence().getPackageName());
	    	 ImageView star= (ImageView)findViewById(bid);
	    	 stars.add(star);
	    	 
	    }
	    
	    MainActivity ac=(MainActivity)MainActivity.getInstence();
	    likeBtn.setOnClickListener(this);
	    callBtn.setOnClickListener(this);
	   // textBox.setOnClickListener(this);
	    //imgBox.setOnClickListener(this);
        img.setVisibility(View.GONE);
    }
	
	 public void onClick(View arg) {
		    
	    	if(likeBtn==arg){
	    		if(UserInfo.getInstence().isLogin==false){
	       		 		MainActivity.getInstence().viewAlertSelect("", R.string.msg_gologin_check, new DialogInterface.OnClickListener() {
	       		 			public void onClick(DialogInterface dialog, int which) {
	       		 				PageObject pInfo;
	       		 				if(which==-1){
	       		 					pInfo=new PageObject(Config.PAGE_LOGIN);
	       		 					pInfo.dr=0;
	       		 					MainActivity.getInstence().changeView(pInfo);		
	       		 				}else{
	   	     					
	   	     					
	       		 				}
	   	     				
	       		 			}
	       		 		});
	       		 	return;
	       	   }
	    		
	    		String opt;
	    		if(info.isfav.equals("Y")){
	    			opt="3";
	    			likeBtn.setSelected(false);
	    			info.isfav="N";
	    		}else{
	    			opt="2";
	    			info.isfav="Y";
	    			likeBtn.setSelected(true);
	    		}
	    		UserInfo.getInstence().goFavorite(info.store_idx, opt);
	    	}else if(callBtn==arg){
	    		
	    		MainActivity.getInstence().viewAlertSelect("", R.string.msg_call_check, new DialogInterface.OnClickListener() {
   		 			public void onClick(DialogInterface dialog, int which) {
   		 				PageObject pInfo;
   		 				if(which==-1){
   		 					Intent callIntent = new Intent(Intent.ACTION_CALL);
   		 					callIntent.setData(Uri.parse("tel:"+info.tel.replace("-", "")));
   		 					MainActivity.getInstence().startActivity(callIntent);		
   		 				}else{
	     					
	     					
   		 				}
	     				
   		 			}
   		 		});
	    		
	    		
	    	}else{
	    		
		  
	    	}
	    	
    }
	public void setData(DeliveryObject _info,String id) 
	{
		info=_info;
		title.setText(info.storename);
		
		 dis.setText(info.dist);
		 workTime.setText(info.work_time);
		 deil.setText(info.deli_comment);
		 dc.setText(info.discount);
		
		
		if(info.isfav.equals("Y")){
			likeBtn.setSelected(true);
		}else{
			likeBtn.setSelected(false);
		}
		
		for(int i=0;i<stars.size();++i){
			if((i+1)<=info.point){
				stars.get(i).setImageResource(R.drawable.del_star0);
			}else{
				float gep=(float)(i+1)-info.point;
				if(gep<=0.5){
					stars.get(i).setImageResource(R.drawable.del_star05);
				}else{
					stars.get(i).setImageResource(R.drawable.del_star1);
				}
			}
			
		}
		
		removeImage();
		imageLoader=new ImageLoader(this);
		imageLoader.loadImg(info.img);
    }
	/*
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		
		super.onLayout(changed, left, top, right, bottom);
		if( bitmap==null){
			imageLoader=new ImageLoader(this);
			imageLoader.loadImg(info.img);
		}else{
			
			
		}
		
	}
	*/
	public void  recicleList()
	{
		removeImage();
		
	} 
	public void  removeList()
	{
		removeImage();
		
	}
	private void  removeImage()
	{
        img.setVisibility(View.GONE);
        img.setImageBitmap(null);
    	 if(imageLoader!=null){
    		 imageLoader.removeLoader();
    		 imageLoader=null;
    	 }
    	 
    	 if(bitmap!=null){
    		 bitmap.recycle();
    		 bitmap=null;
    		 
    	 }
		
	}
	public void onImageLoadCompleted(ImageLoader loader,Bitmap image ){
   		
    	if( image==null){
    		Drawable drawable=getResources().getDrawable( R.drawable.transparent);
    		img.setImageDrawable(drawable);
    		return;
		}
    	bitmap=image;
        img.setVisibility(View.VISIBLE);
		img.setImageBitmap(image);
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
    
}
