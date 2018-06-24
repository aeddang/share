package com.aeddang.pagechanger;



import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


import lib.PhotoSelecter;
import lib.core.ActivityCore;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.opengl.GLESObject;
import lib.opengl.GLESRenderer;
import lib.opengl.GLESSurfaceView;
import lib.opengl.assets.GLESCube;
import lib.opengl.assets.GLESRect;





import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;




public class MainActivity extends ActivityCore implements android.view.View.OnClickListener {

	
	public PhotoSelecter photoSelecter;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {

        
		photoSelecter=new PhotoSelecter(this);
		super.onCreate(savedInstanceState);
        PageObject pobj=new PageObject(HOME);
    	pobj.dr=0;	
    	changeView( pobj);
        
    }
    public void onClick(View arg) {
    	
    	
	}
    @Override
    protected void changeInit() { 
    	super.changeInit();
    }
    
    @Override
    protected void changeStart() { 
    	super.changeStart();
    }
    @Override
    protected void creatView(){
    	super.creatView();
    	switch(currentPageObj.pageID){
    	case HOME:
    		currentPageObj.isHome=true;
    		currentView=new Page(this, currentPageObj);
    		break;
    	}
    }
    
    @Override
    protected void changeEnd() { 
    	super.changeEnd();
    }
	@Override
	protected void onPause()
    {
		super.onPause();
	    if(currentView!=null){
	    	currentView.onPause();
	    	
	    }
	}
    @Override
	protected void onResume()
    {
    	super.onResume();
    	if(currentView!=null){
	    	currentView.onResume();
	    	
	    }
	
	}
    @Override  
    protected  void onActivityResult(int requestCode, int resultCode, Intent data)  
    {    
    	
       
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode != RESULT_OK)    
        	{      
        		Log.i("RESULT_OK","RESULT_OK");
        		return;    
        	}
            
            if(photoSelecter!=null){
            	photoSelecter.activityResult(requestCode, data);
            	
            }
        	if(currentView!=null){
        		currentView.activityResult(requestCode, data);
        	}
            
           
    	
    	
    }

}

 



 


