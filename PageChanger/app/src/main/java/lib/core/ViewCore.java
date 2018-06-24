package lib.core;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;



public class ViewCore extends FrameLayout
{ 
 
	protected ActivityCore mainActivity;
	private Boolean isMoveInit;
	protected Boolean isDirectBack;
	public PageObject pageInfo;
	public ViewCore(Context context,PageObject _pageInfo) { 
        super(context); 
        pageInfo=_pageInfo;
        isDirectBack=true;
        isMoveInit=false;
        mainActivity= ActivityCore.getInstence();
      
       // View.inflate(context, R.layout.view_main,this);  

    } 
 
	public void movedInit() { 
	    if(isMoveInit){
	    	return;
	    }	
	    isMoveInit=true;
		doMovedInit();
    } 
	public void updatePage() { 
		   
		doUpdatePage();
    } 
	public void loginUpdate() { 
		doLoginUpdate();
    } 
    public Boolean directBack() { 
    	
    	doDirectBack();
    	return isDirectBack;
    } 
	public void activityResult(int requestCode, Intent data) { 
    	
		doActivityResult(requestCode, data);
    } 
	
    public void removeViewCore() { 
    	
    	doRemove();
    	
    } 
    public void onPause(){ 
    	
    	
    } 
    public void onResume(){ 
    	
    	
    } 
    protected void doUpdatePage() { 
        
    }
    protected void doLoginUpdate() { 
        
    }
    protected void doActivityResult(int requestCode, Intent data) { 
        
    }
    protected void doMovedInit() { 
        
    }
    protected void doDirectBack() { 
        
    }
    protected void doRemove() { 
        
    } 
    protected void doPause() { 
        
    } 
    protected void doResume() { 
    
    } 
} 
