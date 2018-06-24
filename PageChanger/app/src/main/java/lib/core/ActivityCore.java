package lib.core;

import java.util.ArrayList;

import com.aeddang.pagechanger.R;

import lib.ViewUtil;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ActivityCore extends Activity implements  AnimationListener
{

	public static final int HOME=0;
	protected ViewCore currentView;
	protected FrameLayout viewer,body;
	protected ProgressBar loadingBar;
	protected ImageView dimed;
	
	
	private Animation currentAni;
	protected PageObject currentPageObj,homePageObject;
	protected ArrayList<PageObject> historyA;
    private Boolean isChangeAble,isInit;
	private static ActivityCore instence;
	
	
	private Handler removeHandler=new Handler();
	
	public static ActivityCore getInstence(){
		return instence;
	}
	public int getScreenOrientation() {
	     int rotation = getWindowManager().getDefaultDisplay().getRotation();
	     return rotation;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		instence=this;
        isChangeAble=true;
        isInit=false;
        
        setContentView(R.layout.activity_main);
        loadingBar=(ProgressBar) findViewById(R.id._loadingBar);
        dimed=(ImageView) findViewById(R.id._dimed);
        body=(FrameLayout) findViewById(R.id._body);
        viewer=(FrameLayout) findViewById(R.id._viewer);
        
        Log.i("","onCreate :"+viewer);
      
        historyA=new  ArrayList<PageObject>();
     }
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                     if(!isChangeAble){
                     	return true;
                     }
                     changeViewBack();
                break;
                 
                
         }
		 return false;
    }


    
    
    public void hideKeyBoard()
	{
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE); 
        if(imm!=null){
        	if(this.getCurrentFocus()!=null){
        		imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        	}
        	
        }
	}
	public void changeViewMain() {
    	PageObject pobj=new PageObject(HOME);
    	pobj.dr=-1;	
    	changeView( pobj);
    }
    public void changeViewBack() {
    	
    	if(currentView!=null){
    		if(!currentView.directBack()){
        		return;
        	}
        }
    	if(currentPageObj==null)
    	{
    		changeViewBackDirect();
    	}else{
    		if(currentPageObj.isHome){
         	    viewAlertSelect("", R.string.msg_app_out, new DialogInterface.OnClickListener() {
     			public void onClick(DialogInterface dialog, int which) {
     				Log.i("witch","which="+which);
     				if(which==-1){
     					android.os.Process.killProcess(android.os.Process.myPid());
     				}else{
     					
     				}
     				
     			}
     		});
            }else{
        	   changeViewBackDirect();
            }
    	}
    	
    }
    
    public void changeViewBackDirect() {
    	
    	if(currentPageObj!=null){
    		currentPageObj.isHistory=false;
    	}
    	
    	PageObject pobj;
    	int size=historyA.size()-1;
    	if(size<0){
    		pobj=new PageObject(HOME);
    		
    	}else{
    		pobj=historyA.get(size);
    		historyA.remove(size);
    	}
    	
    	if(pobj.pageID==currentPageObj.pageID){
    		changeViewBack();
    		return;
    	}
    	pobj.dr=-1;	
    	Log.i("", "changeViewBackDirect historyA.size="+historyA.size());
    	changeView(pobj);
    }
    
    
    private void addHistory(PageObject info){
    	
    	if(!info.isHistory){
    		return;
    	}
    	if(info.isHome){
    		homePageObject=info;
    		historyA.clear();
    		return;
    	}
    	historyA.add(info);
    	Log.i("", "historyA.size="+historyA.size());
    }
    public int getCurrentPageID(){
    	
    	if(currentPageObj==null){
    		return -1;
    	}else{
    		return currentPageObj.pageID;
    		
    	}
    } 
    public void changeView(PageObject info) {
        if(!isChangeAble){
        	return;
        	
        }
        
        
        loadingStop();
        isChangeAble=false;
    	currentPageObj=info;
    	changeInit();
        creatView();
        
        if(info.isHome==true){
        	historyA.clear();
        }
        
        viewer.addView(currentView, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
        changeStart();  

    }
    protected void changeInit() { 
    	Animation removeAni;
        switch(currentPageObj.dr){
        case 1:
        	 currentAni = AnimationUtils.loadAnimation(this, R.anim.motion_left_in);
        	 removeAni = AnimationUtils.loadAnimation(this, R.anim.motion_right_out);
        	break;
        case -1:
       	     currentAni = AnimationUtils.loadAnimation(this, R.anim.motion_right_in);
       	     removeAni = AnimationUtils.loadAnimation(this, R.anim.motion_left_out);
       	    break;
       	default:
       		currentAni = AnimationUtils.loadAnimation(this, R.anim.motion_in);
       	    removeAni = AnimationUtils.loadAnimation(this, R.anim.motion_out);
       		break;
        	
        }
        if(currentView!=null){
        	currentView.startAnimation( removeAni );
        }
    }
    protected void changeStart() { 
    	currentAni.setAnimationListener(this);
        currentView.startAnimation( currentAni );
    }
    protected void creatView() { 
    	/*
    	switch(currentPageObj.pageID){
    	case HOME:
    		info.isHome=true;
    		currentView=new Main(this, info);
    		break;
    	}
        */
    }
    protected void changeEnd() { 
    	removeCore();
    }
    private void removeCore(){
    	
		
		int num=viewer.getChildCount();
		for(int i=0;i<num;++i){
		    ViewCore core=(ViewCore) viewer.getChildAt(i);
		    if(currentView==core){
				
			}else{
				if(core!=null){
					addHistory(core.pageInfo);
					core.removeViewCore();
					viewer.removeView(core);
				}
			}
		}
		if(currentView!=null){
			  currentView.movedInit();
		}
		isChangeAble=true;
    }
    protected void doAnimationEnd(Animation arg) { 
    	if( currentAni==arg){
			
			removeHandler.post(new Runnable() {
		        public void run() {
		        	 removeCore();
		        }
			});	
    	}
    }
    public void onAnimationEnd(Animation arg) {
		
    	doAnimationEnd(arg);
    }
    public void loadingStart(Boolean isDimed){
    	if(isDimed){
    		dimed.setVisibility(View.VISIBLE);
    		
    	}else{
    		dimed.setVisibility(View.GONE);
    	}
    	loadingBar.setVisibility(View.VISIBLE); 
    }
    public void loadingStop(){
    	 dimed.setVisibility(View.GONE);
    	 loadingBar.setVisibility(View.GONE);  
    }
    public void viewAlertSelect(String title,int msgId,OnClickListener listener) {
    	String msg=this.getString(msgId);
    	viewAlertSelect(title,msg,listener);
    	
    }
    public void viewAlert(String title,int msgId,OnClickListener listener) {
    	String msg=this.getString(msgId);
    	viewAlert(title,msg,listener);
    }
    
    public void viewAlertSelect(String title,String msg,OnClickListener listener) {
    	
    	AlertDialog.Builder  alert = new AlertDialog.Builder(this);
    	alert.setTitle(title);
    	alert.setMessage(msg);
    	alert.setPositiveButton(R.string.btn_ok, listener);
    	alert.setNegativeButton(R.string.btn_no, listener);
    	alert.show();
    }
    public void viewAlertSelectWithMenu(String title,String msg,CharSequence[] items,OnClickListener listener) {
    	
    	AlertDialog.Builder  alert = new AlertDialog.Builder(this);
    	alert.setTitle(title);
    	alert.setPositiveButton(items[0], listener);
    	alert.setNegativeButton(items[1], listener);
    	
    	alert.show();
    }
    public void viewAlert(String title,String msg,OnClickListener listener) {
    	
    	AlertDialog.Builder  alert = new AlertDialog.Builder(this);
    	alert.setTitle(title);
    	alert.setMessage(msg);
    	alert.setPositiveButton(R.string.btn_ok, listener);
        alert.show();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

}
