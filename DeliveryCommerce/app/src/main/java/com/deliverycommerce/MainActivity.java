package com.deliverycommerce;



import java.util.Collection;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import com.deliverycommerce.UserInfo.UserInfoDelegate;
import com.deliverycommerce.delivery.PageDelivery;
import com.deliverycommerce.idel.PageIdel;
import com.deliverycommerce.searchmap.PopupSearchMap;
import com.deliverycommerce.selectarea.PopupSelectArea;
import com.deliverycommerce.web.PageWeb;
import com.deliverycommerce.web.PopupWeb;
import com.google.android.gms.maps.MapFragment;
import com.google.android.maps.MapView;

import lib.core.ActivityCore;

import lib.core.PageObject;
import lib.core.ViewCore;


import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;


import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;


public class MainActivity extends ActivityCore implements OnClickListener,UserInfoDelegate,BeaconConsumer
 {

	public Typeface myfont,myfontB,myfontN;
	public LocationInfo locationInfo;
	private BeaconInfo beaconInfo;
	private FrameLayout naviBottomBox;
	private NaviBottom naviBottom;
	private NaviLeft naviLeft;
	private Button viewerDimed;

	private MapFragment map;
	
	private BeaconManager beaconManager;
    private Handler beaconHandler=new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        
        if(isInit==true){
        	return;
        	
        }
        SetupInfo setupInfo=new SetupInfo();
       // getWindow().requestFeature(Window.FEATURE_PROGRESS);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
        beaconManager.setForegroundScanPeriod(5000);
        beaconInfo=new BeaconInfo(this);
       
        naviBottomBox=(FrameLayout) findViewById(R.id._naviBottomBox);
        locationInfo=new LocationInfo(this);
        
        naviLeft=(NaviLeft) findViewById(R.id._naviLeft);
        naviLeft.initNavi(this);
        viewerDimed=(Button) findViewById(R.id._viewerDimed);
        
        
        
        naviBottom=new NaviBottom(this);
        int size=naviBottomBox.getLayoutParams().height;
        android.widget.FrameLayout.LayoutParams layout=new android.widget.FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,size);
        layout.gravity=Gravity.TOP|Gravity.LEFT;
        layout.topMargin=size;
  
        naviBottomBox.addView(naviBottom,layout);
        
        myfont=Typeface.createFromAsset(getAssets(), "fonts/NS.mp3");
        myfontB=Typeface.createFromAsset(getAssets(), "fonts/NS_B.mp3");
        myfontN=Typeface.createFromAsset(getAssets(), "fonts/AVB.mp3");
        
        viewerDimed.setVisibility(View.GONE);
        viewerDimed.setOnClickListener(this);
        
        if(UserInfo.getInstence().goAutoLogin()){
        	UserInfo.getInstence().delegate=this;
        	
        }else{
        	appStart();
        }
        

    }
	
	
	public MapFragment getShareMap() {
        if(map==null){
        	map=new MapFragment();
        }
        return map;
    }   
	protected void onDestroy() {
    	super.onDestroy();
        if(beaconHandler!=null){

            beaconHandler=null;
        }
    	beaconManager.unbind(this);
	}
	public void loginUpdate(){
		if(UserInfo.getInstence().delegate==this){
			UserInfo.getInstence().delegate=null;
		}
		appStart();
	}
	private void appStart() {

        Bundle extras = getIntent().getExtras();
        int pid=HOME;
        if(extras != null){
            pid=extras.getInt("pageID",HOME);
        }

		if(currentView==null){
        	PageObject pobj=new PageObject(pid);
        	pobj.dr=0;	
        	changeView( pobj);
        }
	    	
	}
    public void onClick(View arg) {
    	if(viewerDimed==arg){
    		closeLeftMenu();	
    	}
    	
	}
    public void openLeftMenu() {
    	naviLeft.viewBox();
    	viewerDimed.setVisibility(View.VISIBLE);
   	}
    public void closeLeftMenu() {
    	naviLeft.hideBox();
    	viewerDimed.setVisibility(View.GONE);
    	
   	}
    public Boolean isOpenBottomMenu() {
        if(naviBottom==null){
            return false;
        }

    	return naviBottom.isActive;
    	
   	}
    public void openBottomMenu() {
    	naviBottom.viewBox(0);
    	
   	}
    public void closeBottomMenu() {
    	naviBottom.hideBox();
    	
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
    	closeLeftMenu();
    	switch(currentPageObj.pageID){
    		case HOME:
    		case Config.PAGE_DELIVERY:
    	 		currentPageObj.isHome=true;
    	 		currentView=new PageDelivery(this, currentPageObj);
        		break;
            case Config.PAGE_IDEL:
                currentPageObj.isHome=true;
                currentView=new PageIdel(this, currentPageObj);
                break;
        		
    		case Config.PAGE_JOIN:
    	 		
        		currentView=new PageJoin(this, currentPageObj);
        		break;
    		case Config.PAGE_LOGIN:
    	 		
        		currentView=new PageLogin(this, currentPageObj);
        		break;
            case Config.PAGE_SETUP:

                currentView=new PageSetup(this, currentPageObj);
                break;
    	    default:
                currentPageObj.isHome=true;
        		currentView=new PageWeb(this, currentPageObj);
    		}
    	
    	
    }
    @Override
    protected ViewCore creatPopup(PageObject pinfo) { 
    	
    	ViewCore pop=null;
    	switch(pinfo.pageID){
    		case Config.POPUP_SELECT_AREA:
    			pop=new PopupSelectArea(this, pinfo);
    			break;
    		case Config.POPUP_WEB_INFO:
    			pop=new PopupWeb(this, pinfo);
    			break;
    		case Config.POPUP_SEARCH_MAP:
    			pop=new PopupSearchMap(this, pinfo);
    			break;
    		case Config.POPUP_AD:
    			pop=new PopupAd(this, pinfo);
    			break;


    	}
        
    	return pop;
    }
    @Override
    protected void changeEnd() { 
    	super.changeEnd();
    }
	@Override
	protected void onPause() {
		super.onPause();
		
		//if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
		
	    if(currentView!=null){
	    	currentView.onPause();
	    	
	    }
	}
    @Override
	protected void onResume() {
    	super.onResume();
    	//if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false); 
    	
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
            
            if(currentView!=null){
        		currentView.activityResult(requestCode, data);
        	}
            
           
    	
    	
    }
    private void beaconNotifier(Collection<Beacon> beacons){
        beaconInfo.beaconNotifier(beacons);

    }
    @Override
    public void onBeaconServiceConnect() {
    	Log.i("", "onBeaconServiceConnect");  
    	
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
        @Override
        	public void didEnterRegion(Region region) {
            	Log.i("", "I just saw an beacon for the first time!");
        	}

        	@Override
        	public void didExitRegion(Region region) {
        		Log.i("", "I no longer see an beacon");
        	}

        	@Override
            public void didDetermineStateForRegion(int state, Region region) {
        		Log.i("", "I have just switched from seeing/not seeing beacons: "+state);        
            }
        });
        beaconManager.setRangeNotifier(new RangeNotifier() {
			
	
            @Override 
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {

                /*
                if (beacons.size() > 0) {
                    for (Beacon beacon : beacons) {
                        Log.i("","The beacon " + beacon.toString() + " is about " + beacon.getId1());
                    }
                }*/

                beaconHandler.post(new Runnable() {
                    public void run() {
                        beaconNotifier(beacons);
                    }
                });

             }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("rangeid", null, null, null));
        } catch (RemoteException e) {    
        	Log.i("", "e : " +e.toString() );  
        }
       
        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("monitorid", null, null, null));
        } catch (RemoteException e) {
        	 Log.i("", "e : " +e.toString() ); 
        }
    }
	
}