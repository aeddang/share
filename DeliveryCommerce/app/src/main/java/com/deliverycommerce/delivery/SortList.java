package com.deliverycommerce.delivery;





import java.util.ArrayList;

import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;


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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;






@SuppressLint("NewApi")
public class SortList extends FrameLayout{
	
	public Button btn;
	public SortList(Context context,String text) 
	{
		super(context); 
	    View.inflate(context, R.layout.sort_list,this);  
	    btn=(Button) findViewById(R.id._btn);
	    btn.setText(text);
	    
	    MainActivity ac=(MainActivity)MainActivity.getInstence();
	    btn.setTypeface(ac.myfontB);
    }
	
    
}
