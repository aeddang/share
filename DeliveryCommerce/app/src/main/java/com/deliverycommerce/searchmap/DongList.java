package com.deliverycommerce.searchmap;





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
public class DongList extends FrameLayout{
	

	public DongObject info;
	private TextView txt;
	public DongList(Context context,DongObject _info) 
	{
		super(context); 
	    View.inflate(context, R.layout.dong_list,this);  
	    info=_info;
	    

	    txt=(TextView) findViewById(R.id._text);
	    txt.setText(info.addr1+" "+info.addr2+" "+info.addr3);
	    
	    MainActivity ac=(MainActivity)MainActivity.getInstence();
	    txt.setTypeface(ac.myfontB);
    }
	
    
}
