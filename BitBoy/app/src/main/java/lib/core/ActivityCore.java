package lib.core;

import java.util.ArrayList;


import lib.ViewUtil;
import lib.observers.ObserverController;


import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;


public class ActivityCore extends AppCompatActivity implements  AnimationListener,View.OnClickListener
{
	public static final int SCREEN_CHANGED_TYPE_ORIENTATION_RESIZED=-3;
	public static final int SCREEN_CHANGED_TYPE_ORIENTATION=0;
	public static final int SCREEN_CHANGED_TYPE_RESIZED=-1;
	public static final int SCREEN_CHANGED_TYPE_NONE=-2;

	public static final int HOME=0;
	public static final String NotificationKeyBoardShow = "NotificationKeyBoardShow";
	public static final String NotificationKeyBoardHidden = "NotificationKeyBoardHidden";

	public static final String NotificationOrientationChanged = "NotificationOrientationChanged";

	protected ViewCore currentView;
	public FrameLayout body;
	public FrameLayout viewer,popupViewer;
    private Button viewerDimed;
	protected ProgressBar loadingBar;
    protected ImageView loadingImage;
	protected ImageView dimed;
	

	private Animation currentAni,introAni,loadingAni;
	protected PageObject currentPageObj,homePageObject;
	protected ArrayList<PageObject> historyA;
	protected ArrayList<ViewCore> popupA;
	protected FrameLayout intro;
    private Boolean isChangeAble,isDimed;
    protected Boolean isInit=false;
    private static ActivityCore instence;
	public float dpi;

	protected int finalOrientation =-1;
	protected int willChangeOrientation = SCREEN_CHANGED_TYPE_NONE;
    private Handler removeHandler=new Handler();
	private int backKeyCount = 0;
	public static ActivityCore getInstence(){
		return instence;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		if(isInit==true){
			return;
		}
		instence=this;
        isChangeAble=true;
        isDimed=false;
        dpi=this.getApplicationContext().getResources().getDisplayMetrics().density;
        setContentView(R.layout.activity_main);
        loadingBar=(ProgressBar) findViewById(R.id._loadingBar);
        // loadingImage=(ImageView) findViewById(R.id._loadingImage);
        dimed=(ImageView) findViewById(R.id._dimed);
        body=(FrameLayout) findViewById(R.id._body);
        viewer=(FrameLayout) findViewById(R.id._viewer);
		popupViewer=(FrameLayout) findViewById(R.id._popupViewer);
        intro=(FrameLayout) findViewById(R.id._intro);

        viewerDimed=(Button) findViewById(R.id._viewerDimed);
        viewerDimed.setVisibility(View.GONE);
        viewerDimed.setOnClickListener(this);
        Log.i("","dpi :"+dpi);
        if(loadingImage!=null) {

            loadingAni = AnimationUtils.loadAnimation(this, R.anim.motion_rotate);
			loadingImage.setVisibility(View.GONE);
        }
        historyA=new  ArrayList<PageObject>();
        popupA=new ArrayList<ViewCore>();


        if(getActionBar() != null){
            getActionBar().hide();
        }


     }
	public int getScreenOrientation() {
	     int rotation = getWindowManager().getDefaultDisplay().getRotation();
	     return rotation;
	}
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                     if(!isChangeAble || isDimed==true){
                     	return true;
                     }
					//SoundManeger.getInstence().play();
                     changeViewBack();
                break;
                 
                
         }
		 return false;
    }

	public Point getAppSize(){

		Point rec=new Point();
		rec.x=body.getWidth();
		rec.y=body.getHeight();
		return rec;
	}

	public void update() {
		super.onPause();
		if(currentView!=null){
			currentView.onUpdate();

		}
		for (int i = 0; i < this.popupA.size() ; i++)
		{
			this.popupA.get(i).onUpdate();
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		if(currentView!=null){
			currentView.onPause();

		}
		for (int i = 0; i < this.popupA.size() ; i++)
		{
			this.popupA.get(i).onPause();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();

		if(currentView!=null){
			currentView.onResume();

		}
		for (int i = 0; i < this.popupA.size() ; i++)
		{
			this.popupA.get(i).onResume();
		}

	}
    public void onClick(View arg) {
		if(viewerDimed==arg){
            hideKeyBoard();
        }

    }
	public void resizeScreen(int proposedWidth,int proposedheight)
	{
		for (int i = 0; i < this.popupA.size() ; i++)
		{
			this.popupA.get(i).setBodySizeChanged(proposedWidth,proposedheight);
		}
		if(willChangeOrientation >= SCREEN_CHANGED_TYPE_ORIENTATION)
		{
			if(finalOrientation !=willChangeOrientation){

				finalOrientation = willChangeOrientation;
				ObserverController.shareInstence().notifyPost(NotificationOrientationChanged,finalOrientation, null);
				orientationChanged(finalOrientation);
			}
			willChangeOrientation = SCREEN_CHANGED_TYPE_RESIZED;
		}


	}

    public void keyBoardShow(int diff)
    {
		viewerDimed.setVisibility(View.VISIBLE);
		ObserverController.shareInstence().notifyPost(NotificationKeyBoardShow, diff, null);

    }
    public void keyBoardHidden()
    {
		viewerDimed.setVisibility(View.GONE);
		ObserverController.shareInstence().notifyPost(NotificationKeyBoardHidden, null, null);

    }
    
    public void hideKeyBoard()
	{
        viewerDimed.setVisibility(View.GONE);
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


    	if(popupA.size()>0){
            ViewCore pop=popupA.get(popupA.size()-1);
            if(!pop.directBack()){
                return;
            }
    		removePopup(pop);
    		return;		
    	}
    	
    	if(currentView!=null){
    		if(!currentView.directBack()){
        		return;
        	}
        }
    	if(currentPageObj==null)
    	{
    		changeViewBackDirect();
    	}else{
    		if(currentPageObj.isHome)
    		{
				backKeyCount ++;
				if(backKeyCount == 1)
				{
					int msgID = LanguageFactory.getInstence().getResorceID("msg_app_out");
					Toast.makeText(this,msgID,Toast.LENGTH_LONG).show();
				}
				else
				{
					android.os.Process.killProcess(android.os.Process.myPid());
				}
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
    	//Log.i("", "changeViewBackDirect historyA.size="+historyA.size());
        prevChangeViewBack(pobj);
    	changeView(pobj);
    }
    protected void configurationChanged(Configuration newConfig) {

    }
	protected void orientationChanged(int orientation) {

		Log.i("orientationChanged", "orientationChanged  : "+orientation);
	}
    @Override
    public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);

		if(this.getScreenOrientation() != newConfig.orientation){
			willChangeOrientation = newConfig.orientation;
		}
		Log.i("willChangeOrientation", "willChangeOrientation  : "+willChangeOrientation);
		if(currentView!=null){
    		currentView.configurationChanged(newConfig);
    	}
        configurationChanged(newConfig);
    }
    protected void prevChangeViewBack(PageObject info) {

    }
    public  void removeIntroStart(int offset){

		if(intro!=null)
		{

			introAni = AnimationUtils.loadAnimation(this, R.anim.motion_out);
            introAni.setStartOffset(offset);
            introAni.setAnimationListener(this);
            intro.startAnimation( introAni );
		}
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

	public Boolean hasPopup(int id)
	{
		for (int i = 0; i < this.popupA.size() ; i++)
		{
			if(this.popupA.get(i).pageInfo.pageID == id)
			{
				return true;
			}
		}
		return false;

	}

	public ViewCore addSinglePopup(PageObject info)
	{

		if(this.hasPopup(info.pageID) == true)
		{
			return null;
		}
		return this.addPopup(info);
	}
    
    public ViewCore addPopup(PageObject info)
	{
        if(info.dr == 2){
			info.dr = 1;
		}
		Animation ani=getInAni(info);
    	ViewCore pop=creatPopup(info);
    	if(pop==null){
    		
    		return null;
    	}
		backKeyCount = 0;
    	popupA.add(pop);
		popupViewer.addView(pop, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		pop.initCore();
		popupAniStart(pop,ani,true);
        return pop;
    }
    public void removePopup() {

        if(popupA.size()>0){
            removePopup(popupA.get(0));
        }

    }
    public void removePopup(ViewCore pop) {
        
    	if(popupA.indexOf(pop)!=-1){
            loadingStop();
    		popupA.remove(pop);
    		Animation ani=getOutAni(pop.pageInfo);
    		popupAniStart(pop, ani, false);
    	}

    }
    public Animation getInAni(PageObject pObj) { 
    	Animation ani;
        switch(pObj.dr){
        case 1:
        	ani = AnimationUtils.loadAnimation(this, R.anim.motion_up_in);
        	break;
        case -1:
        	ani = AnimationUtils.loadAnimation(this, R.anim.motion_down_in);  
       	    break;
       	default:
       		ani  = AnimationUtils.loadAnimation(this, R.anim.motion_in);
       		break;
        	
        }
        return ani;
        
    }
    public Animation getOutAni(PageObject pObj) { 
    	Animation ani;
        switch(pObj.dr){
        case 1:
        	ani = AnimationUtils.loadAnimation(this, R.anim.motion_up_out);
        	break;
        case -1:
        	ani = AnimationUtils.loadAnimation(this, R.anim.motion_down_out);  
       	    break;
       	default:
       		ani  = AnimationUtils.loadAnimation(this, R.anim.motion_out);
       		break;
        	
        }
        return ani;
        
    }
    
    protected ViewCore creatPopup(PageObject pinfo) { 
    	/*
    	ViewCore pop;
    	switch(pinfo.pageID){
    		case Config.POPUP_SELECT_AREA:
    			pop=new PopupSelectArea(this, pinfo);
    			break;
    	}
        
    	return pop;
        */
    	return null;
    }
    protected void doPopupAnimationEnd(ViewCore pop,Boolean isOpen) { 
    	if(isOpen){
    		pop.movedInit();
    	}else{
    		pop.removeViewCore();
    		ViewUtil.remove(pop);
			if(popupA.size() == 0){

				popupViewer.setVisibility(View.GONE);
			}

    	}
    }
    protected void popupAniStart(final ViewCore pop,Animation ani,final Boolean isOpen) {
        if(isOpen==false){
            pop.removeViewInit();

        }else{
			popupViewer.setVisibility(View.VISIBLE);

		}

    	ani.setAnimationListener(new AnimationListener() {
    		public void onAnimationEnd(Animation arg) {
    			Handler popupHandler=new Handler();
    			popupHandler.post(new Runnable() {
    		        public void run() {
    		        	doPopupAnimationEnd(pop,isOpen);
    		        }
    			});	
    			
    	    }
    		@Override
    		public void onAnimationRepeat(Animation arg0) {}
    		@Override
    		public void onAnimationStart(Animation arg0) {}
		});
    	pop.startAnimation( ani );
    }
   
  /////////////////////////////////////////////////////////////////////////////////////
    
    

    public void changeView(PageObject info) {

    	
    	
    	if(!isChangeAble){
        	return;
        	
        }
		backKeyCount = 0;
        loadingStop();
        isChangeAble=false;
        
        if(info.dr==2){
    		if(currentPageObj!=null){
        		if(info.pageID>currentPageObj.pageID){
        			info.dr=1;
        			
        		}else if(info.pageID<currentPageObj.pageID){
            		info.dr=-1;
        		}else{
        			info.dr=0;
        			
        		}
        	}else{
        		info.dr=0;
        		
        	}
    		
    	}
    	currentPageObj=info;
    	changeInit();
        creatView();
        
        if(info.isHome==true){
        	historyA.clear();
        }
        
        viewer.addView(currentView, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
		currentView.initCore();
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
            currentView.removeViewInit();
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
    
    private void removeIntro()
	{
		ViewUtil.remove(intro);
    	intro=null;
    }
    protected void doAnimationEnd(Animation arg) { 
    	if( currentAni==arg){
			
			removeHandler.post(new Runnable() {
		        public void run() {
		        	 removeCore();
		        }
			});	
    	}else if(introAni==arg){
			removeHandler.post(new Runnable() {
		        public void run() {
		        	 removeIntro();
		        }
		    });
			
		}
    }
    public void onAnimationEnd(Animation arg) {
		doAnimationEnd(arg);
    }
    public void loadingStart(Boolean _isDimed){
        isDimed=_isDimed;
    	if(isDimed){
    		dimed.setVisibility(View.VISIBLE);
    		
    	}else{
    		dimed.setVisibility(View.GONE);
    	}
        if(loadingBar!=null) {
            loadingBar.setVisibility(View.VISIBLE);
        }
        if(loadingImage!=null){

            loadingImage.setAnimation(loadingAni);
            loadingImage.setVisibility(View.VISIBLE);
        }
    }
    public void loadingStop(){
        isDimed=false;
    	 dimed.setVisibility(View.GONE);
    	if(loadingBar!=null) {
            loadingBar.setVisibility(View.GONE);
        }
        if(loadingImage!=null){
            loadingImage.setAnimation(null);
            loadingImage.setVisibility(View.GONE);
        }
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
   public void viewAlertSelectWithMenu(String title,int msgId,CharSequence[] items,OnClickListener listener) {

       String msg=this.getString(msgId);
       viewAlertSelectWithMenu(title,msg,items,listener);
   }
   public void viewAlertSelectWithMenu(String title,String msg,CharSequence[] items,OnClickListener listener) {

       AlertDialog.Builder  alert = new AlertDialog.Builder(this);
       alert.setTitle(title);
       alert.setMessage(msg);
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
		//getMenuInflater().inflate(R.menu.main, menu);
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
