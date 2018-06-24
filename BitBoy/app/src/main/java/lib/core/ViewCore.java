package lib.core;

import lib.CustomTimer;
import lib.CustomTimer.TimerDelegate;
import lib.ViewUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ironraft.bitboy.R;


public class ViewCore extends FrameLayout implements  TimerDelegate
{
    protected Button btnBack;
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
        this.setClickable(true);
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
    public void initCore() {
        //btnBack=(Button) findViewById(R.id._btnBack);

        doInit();
    }
    public void movedInit() {
	    if(isMoveInit){
	    	return;
	    }
       // btnBack=(Button) findViewById(R.id._btnBack);
	    isMoveInit=true;

        if(btnBack!=null) {
            btnBack.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    ActivityCore.getInstence().changeViewBack();
                }
            });
        }
        doMovedInit();
    } 
	

    public Boolean directBack() { 
    	
    	doDirectBack();
    	return isDirectBack;
    } 
	public void activityResult(int requestCode, Intent data) { 
    	
		doActivityResult(requestCode, data);
    }

    public void configurationChanged(Configuration newConfig){
    	
		doConfigurationChanged(newConfig);
		if(resizeTimer!=null)
		{
			resizeTimer.timerStop();
			resizeTimer.resetTimer();
			resizeTimer.timerStart();
		}
    }
    public void setBodySizeChanged(int proposedWidth,int proposedheight)
    {
        doSetBodySizeChanged(proposedWidth, proposedheight);
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
       // Log.i("", "ViewCore Resize w : " + w + " h : " + h);
    	doResize();
    }

    public void onUpdate()
    {
        doUpdate();
    }
    public void onPause(){ 
    	
    	doPause();
    } 
    public void onResume(){ 
    	
    	doResume();
    }

    protected void doUpdate() {

        // Log.i("","ViewCore Resize!!");
    }
    protected void doResize() { 
    	 
    	// Log.i("","ViewCore Resize!!");
    }
    protected void doSetBodySizeChanged(int proposedWidth,int proposedheight) {

    }
    protected void doActivityResult(int requestCode, Intent data) { 
        
    }
    protected void doConfigurationChanged(Configuration newConfig) {
        
    }
    protected void doInit() {

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
