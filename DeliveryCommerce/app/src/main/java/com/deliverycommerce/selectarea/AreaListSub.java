package com.deliverycommerce.selectarea;





import java.util.ArrayList;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.UserInfo;
import com.deliverycommerce.LocationInfo.AreaInfoDelegate;


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
public class AreaListSub extends FrameLayout implements OnClickListener{
	
	private TextView title;
	
	public Button selectBtn;
    public String key;
   
	public AreaListSub(Context context,String _key,Boolean select) 
	{
		super(context); 
		key=_key;
	    View.inflate(context, R.layout.area_list_sub,this);  
	    title=(TextView) findViewById(R.id._title);
	  
	    selectBtn=(Button) findViewById(R.id._selectBtn);
	    selectBtn.setSelected(select);
	    selectBtn.setOnClickListener(this);
	    title.setText(key);
	    
    }
	 public void onClick(View arg) {
		 if(selectBtn.isSelected()){
			 selectBtn.setSelected(false);
		 }else{
			 selectBtn.setSelected(true); 
			 
		 }
	    	
	}

    
}
