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
import lib.ViewUtil;
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
public class AreaList extends FrameLayout implements  AreaInfoDelegate,OnClickListener{
	
	private TextView title;
	private LinearLayout lists;
    private ImageView selectBtn;
    private FrameLayout listBtn;
    private String key;
    private ArrayList<AreaListSub> areaSubLists;
    private ArrayList<String> selected;
	public AreaList(Context context,String _key,ArrayList<String> _selected) 
	{
		super(context); 
		key=_key;
		selected=_selected;
	    View.inflate(context, R.layout.area_list,this);  
	    title=(TextView) findViewById(R.id._title);
	    lists=(LinearLayout) findViewById(R.id._lists);
	    listBtn=(FrameLayout) findViewById(R.id._listBtn);
	    selectBtn=(ImageView) findViewById(R.id._selectBtn);
	    selectBtn.setSelected(false);
	    listBtn.setOnClickListener(this);
	    title.setText(key);
	    
	    
    }
	 public void onClick(View arg) {
	    if(listBtn.isSelected()){
	    	areaHidden();
	    	listBtn.setSelected(false);
	    }else{
	    	listBtn.setSelected(true);
	    	if(areaSubLists==null || areaSubLists.size()<1){
	    		MainActivity.getInstence().loadingStart(true);
	    		LocationInfo.getInstence().delegateA=this;
	    		LocationInfo.getInstence().loadGuAreaLists(key);
	    	}else{
	    		areaView();
	    	}
	    }
		 
		
	    
	    	
	}
	 public void remove(){
		 if(LocationInfo.getInstence().delegateA==this){
			 
			 LocationInfo.getInstence().delegateA=null;
		 }
		 ViewUtil.remove (this);
	 }
	public ArrayList<String> getRegisterArea(){
		ArrayList<String> registerArea=new ArrayList<String>();
		if(areaSubLists==null){
			return registerArea;
		}
		for(int i=0;i<areaSubLists.size() ;++i){
			AreaListSub list=areaSubLists.get(i);
			if(list.selectBtn.isSelected()){
				if(selected.indexOf(list.key)==-1){
                   // Log.i("","areaSubLists add : "+key+"|"+list.key);
					registerArea.add(key+"|"+list.key);
				}
			}
	    }
		return registerArea;
	}
	public ArrayList<String> getUnRegisterArea(){
		ArrayList<String> registerArea=new ArrayList<String>();
		if(areaSubLists==null){
			return registerArea;
		}
		for(int i=0;i<areaSubLists.size() ;++i){
			AreaListSub list=areaSubLists.get(i);
			if(selected.indexOf(key+"|"+list.key)!=-1 && list.selectBtn.isSelected()==false){
				registerArea.add(key+"|"+list.key);
			}
	    }
		return registerArea;
	} 
	private void areaView(){
		if(areaSubLists==null){
			return;
		}
		for(int i=0;i<areaSubLists.size() ;++i){
			lists.addView(areaSubLists.get(i));
	    }
			
	}
	private void areaHidden(){
		if(areaSubLists==null){
			return;
		}
		for(int i=0;i<areaSubLists.size() ;++i){
			ViewUtil.remove (areaSubLists.get(i));
	    }
	}
	public void areaUpdate(int type,ArrayList<String> areaLists){
		areaSubLists=new ArrayList<AreaListSub>();	
		MainActivity.getInstence().loadingStop(); 
		for(int i=0;i<areaLists.size() ;++i){
			String cKey= areaLists.get(i);
			int isSelIdx= selected.indexOf(key+"|"+cKey);
			Boolean isSel;
			if(isSelIdx==-1){
				isSel=false;
			}else{
				isSel=true;
			}
			AreaListSub list=new AreaListSub(MainActivity.getInstence(),areaLists.get(i),isSel);
			areaSubLists.add(list);
			lists.addView(list);
		 }
	}
	
    
}
