package lib.core;

import lib.CustomTimer;
import lib.CustomTimer.TimerDelegate;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;



public class ViewCore extends FrameLayout implements  TimerDelegate
{ 
 
	protected ActivityCore mainActivity;
	private Boolean isMoveInit;
	private CustomTimer resizeTimer;
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
	protected void setResizeHendler() { 
		removeResizeTimer();
		resizeTimer=new  CustomTimer(100,1,this);
		resizeTimer.resetTimer();
    } 
	
	public void onTime(CustomTimer timer){
		
	}
	public void onComplete(CustomTimer timer){
    	resize();
    }
	protected void removeResizeTimer() { 
		if(resizeTimer!=null){
			resizeTimer.removeTimer();
			resizeTimer=null;
		}
    } 
	public void movedInit() { 
	    if(isMoveInit){
	    	return;
	    }	
	    isMoveInit=true;
		doMovedInit();
    } 
	
	public void removePopup(ViewCore pop){
		doRemovePopup( pop);
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
    public void sendCommand(String command) {

        doSendCommand(command);
    }


    public void configurationChanged(){
    	
		doConfigurationChanged();
		if(resizeTimer!=null)
		{
			resizeTimer.timerStop();
			resizeTimer.resetTimer();
			resizeTimer.timerStart();
		}
    }
    public void removeViewInit() {


        doRemoveInit();

    }
    public void removeViewCore() { 
    	
    	removeResizeTimer();
    	doRemove();
    	mainActivity=null;
    } 
  
    public void resize(){ 
    	int w= this.getWidth();
    	int h= this.getHeight();
        Log.i("","ViewCore Resize w : "+w+" h : "+h);
    	doResize();
    } 
   
    public void onPause(){ 
    	
    	
    } 
    public void onResume(){ 
    	
    	
    }
    protected void doRemovePopup(ViewCore pop) { 
        
    }
    protected void doSendCommand(String command){
    }
    protected void doResize() { 
    	 
    	// Log.i("","ViewCore Resize!!");
    }
    protected void doUpdatePage() { 
        
    }
    protected void doLoginUpdate() { 
        
    }
    protected void doActivityResult(int requestCode, Intent data) { 
        
    }
    protected void doConfigurationChanged() { 
        
    }
    protected void doMovedInit() { 
        
    }
    protected void doDirectBack() { 
        
    }
    protected void doRemoveInit() {
        
    }
    protected void doRemove() {

    }
    protected void doPause() { 
        
    } 
    protected void doResume() { 
    
    } 
} 
