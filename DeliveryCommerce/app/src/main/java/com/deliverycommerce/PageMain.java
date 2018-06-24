package com.deliverycommerce;





import java.util.ArrayList;








import lib.core.PageObject;
import lib.core.ViewCore;
import android.view.View;
import android.view.View.OnClickListener;

import android.annotation.SuppressLint;

import android.content.Context;


import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;







@SuppressLint("NewApi")
public class PageMain extends ViewCore implements  OnClickListener{
	
	
	private FrameLayout body;
	
	
	
	public PageMain(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_main,this);  
	    body=(FrameLayout) findViewById(R.id._body);
	    
	    
	    
    }
	
	
	
	
	public void onClick(View arg) {
		
	}
    protected void doResize() { 
    	super.doResize();
		
    }
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	   
	    
	} 
	
    
    protected void doRemove() { 
    	
    	super.doRemove();
    	
    	
    } 
    
    
}
