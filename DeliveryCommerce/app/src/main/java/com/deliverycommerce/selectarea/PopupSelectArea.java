package com.deliverycommerce.selectarea;





import java.util.ArrayList;

import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.LocationInfo.AreaInfoDelegate;
import com.deliverycommerce.LocationInfo.LocationInfoDelegate;
import com.deliverycommerce.R;
import com.deliverycommerce.delivery.DeliveryInfo;








import lib.ViewUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.annotation.SuppressLint;

import android.content.Context;


import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;







@SuppressLint("NewApi")
public class PopupSelectArea extends ViewCore implements  OnClickListener, AreaInfoDelegate{
	
	
	private FrameLayout body;
	private Button backBtn;
	private LinearLayout lists;
	private LocationInfo locationInfo;
	private ArrayList<String> selectLists;
	private ArrayList<AreaList> areaListLists;
	
	public SelectAreaDelegate delegate;
	
	public PopupSelectArea(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.popup_select_area,this);  
	    body=(FrameLayout) findViewById(R.id._body);
	    lists=(LinearLayout) findViewById(R.id._lists);
	    backBtn=(Button) findViewById(R.id._backBtn);
	    locationInfo=LocationInfo.getInstence();
	    selectLists=new ArrayList<String>();
	    
	    backBtn.setOnClickListener(this);
	    
	    areaListLists=new  ArrayList<AreaList>();;
    }
	
	
	
	
	public void onClick(View arg) {
		
		if(backBtn==arg){
			ArrayList<String>addAreas=new ArrayList<String>();
			ArrayList<String>removeAreas=new ArrayList<String>();
			for(int i=0;i<areaListLists.size();++i){
				AreaList list=areaListLists.get(i);
				ArrayList<String> addA=list.getRegisterArea();
				ArrayList<String> removeA=list.getUnRegisterArea();
				
				addAreas.addAll(addA);
				removeAreas.addAll(removeA);
			}
			
			addAreas.addAll(selectLists);
			addAreas.removeAll(removeAreas);
			
			if(delegate!=null){delegate.areaSelected(addAreas);}
			
		}
		
		
	}
    protected void doResize() { 
    	super.doResize();
		
    }
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	    locationInfo.delegateA=this;
	    mainActivity.loadingStart(false);
	    selectLists= locationInfo.getRegisterArea();
	    locationInfo.loadAreaLists();
	    
	    MainActivity ac=(MainActivity)mainActivity;
    	ac.closeBottomMenu();
	} 
	
	public void areaUpdate(int type,ArrayList<String> areaLists){
	
		MainActivity.getInstence().loadingStop();
		for(int i=0;i<areaLists.size();++i){
			AreaList list=new AreaList(MainActivity.getInstence(),areaLists.get(i),selectLists);
			areaListLists.add(list);
			lists.addView(list);
	    }
		
	}
    protected void doRemove() { 
    	
    	super.doRemove();
    	if(locationInfo.delegateA==this){
    		locationInfo.delegateA=null;
    		
    	}
    	for(int i=0;i<areaListLists.size();++i){
			AreaList list=areaListLists.get(i);
			list.remove();
			
	    }
    	delegate=null;
    } 
    
    public interface SelectAreaDelegate
	{
		void areaSelected(ArrayList<String> areas);
		
	}
}
