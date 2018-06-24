package com.deliverycommerce;
import java.util.Date;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;


import com.deliverycommerce.UserInfo.UserInfoDelegate;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View.OnClickListener;

public class MyApplication extends Application implements BootstrapNotifier
{
    private BackgroundPowerSaver backgroundPowerSaver;
    private RegionBootstrap regionBootstrap;
    
    
    public void onCreate() {
        super.onCreate();
        
        Log.i("", "MyApplication call");
        // Simply constructing this class and holding a reference to it in your custom Application class
        // enables auto battery saving of about 60%
        backgroundPowerSaver = new BackgroundPowerSaver(this);
        
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        Region region = new Region("org.altbeacon.beacon.startup.StartupBroadcastReceiver", null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);

    }
    
    @Override
	public void didDetermineStateForRegion(int arg0, Region arg1) {
    	Log.d("", "Got a didDetermineStateForRegion call");
	}

    @Override
	public void didEnterRegion(Region arg0) {
	   Log.d("", "Got a didEnterRegion call");
	
	    SharedPreferences settings =getApplicationContext().getSharedPreferences(Config.PREFS_NAME, 0);
        if(settings.getBoolean(Config.PUSH_KEY,true)!=true){

            return;
        }
        Intent mintent=new Intent(getApplicationContext(),MainActivity.class);
        mintent.putExtra("pageID", Config.PAGE_IDEL);
		     CNotification.addNotification(getApplicationContext(), mintent, Config.NOTICE_ID, R.drawable.icon_noti,
      			                      "DeliveryCommerce", "[배달커머스]", "근처에 비콘이있습니다");
	   
	   // This call to disable will make it so the activity below only gets launched the first time a beacon is seen (until the next time the app is launched)
	   // if you want the Activity to launch every single time beacons come into view, remove this call.  
	  // regionBootstrap.disable();
	   //Intent intent = new Intent(this, MainActivity.class);
	   // IMPORTANT: in the AndroidManifest.xml definition of this activity, you must set android:launchMode="singleInstance" or you will get two instances
	   // created when a user launches the activity manually and it gets launched from here.
	  // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	  // startActivity(intent);
	}

	@Override
	public void didExitRegion(Region arg0) {
		Log.d("", "Got a didExitRegion call");
	}        
}
