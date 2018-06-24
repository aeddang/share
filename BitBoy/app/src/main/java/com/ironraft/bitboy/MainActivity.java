package com.ironraft.bitboy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ironraft.bitboy.alarm.PageAlarm;
import com.ironraft.bitboy.coin.PageCoin;
import com.ironraft.bitboy.coin.PopupCoinInfo;
import com.ironraft.bitboy.home.PageHome;
import com.ironraft.bitboy.leftmenu.LeftMenu;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.msg.PageMsgLists;
import com.ironraft.bitboy.msg.PopupMsg;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import lib.Aes;
import lib.core.ActivityCore;
import lib.core.LeftMenuActivityCore;
import lib.core.LeftMenuCore;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.observers.Observer;
import lib.observers.ObserverController;


public class MainActivity extends LeftMenuActivityCore implements Observer
{
    private LeftMenu menu;
    private Top top;
    private Bottom bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("FIND","onCreate : "+ isInit);

        if(isInit==true){
            return;

        }
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("MY KEY HASH:", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        isInit = true;
        FontFactory fonts = new FontFactory(this);
        ObserverController.shareInstence().registerObserver(this, BitDataManager.NotificatioBitDataSetupComplete);
        ObserverController.shareInstence().registerObserver(this, BitDataManager.NotificatioBitDataSetupError);

        BitDataManager bitDataManager = new BitDataManager();
        setupInit();
        topInit();
        bottomInit();

    }

    public void setupInit()
    {
        BitDataManager.getInstence().loadBitSetup();
        this.loadingStart(true);

    }


    public void appInit() {
        loadingStop();
        removeIntroStart(1000);
        if (currentView == null)
        {
            PageObject pobj=new PageObject(Config.PAGE_HOME);
            pobj.dr = 0;
            this.changeView(pobj);
        }
        menu.init();
        passiveMenu(null);

    }

    public void notification(String notify,Object value, Map<String,Object> userData)
    {
        switch (notify)
        {

            case BitDataManager.NotificatioBitDataSetupComplete:
                this.appInit();
                break;
            case BitDataManager.NotificatioBitDataSetupError:

                int msgID = LanguageFactory.getInstence().getResorceID("msg_retry");
                viewAlert("",msgID, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity ac = (MainActivity)ActivityCore.getInstence();
                        ac.setupInit();
                    }
                });
                break;
        }

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void creatView() {

        super.creatView();
        bottom.setSelected(currentPageObj.pageID);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        switch (currentPageObj.pageID) {
            case HOME:
            case Config.PAGE_HOME:

                currentPageObj.isHome = true;
                currentView = new PageHome(this, currentPageObj);
                break;
            case Config.PAGE_MSG:
                currentView = new PageMsgLists(this, currentPageObj);
                break;
            case Config.PAGE_COIN:
                currentView = new PageCoin(this, currentPageObj);
                break;
            case Config.PAGE_ALARM:
                currentView = new PageAlarm(this, currentPageObj);
                break;
        }
    }


    @Override
    protected ViewCore creatPopup(PageObject pinfo)
    {
        super.creatPopup(pinfo);
        ViewCore pop = null;

        switch (pinfo.pageID) {

            case Config.POPUP_SETUP:
                pop = new PopupSetup(this, pinfo);
                break;
            case Config.POPUP_MSG:
                pop = new PopupMsg(this, pinfo);
                break;
            case Config.POPUP_COIN_INFO:
                pop = new PopupCoinInfo(this, pinfo);
                break;
        }
        return pop;
    }

    @Override
    public void initMenu(LeftMenuCore view) {
        super.initMenu(view);
        menu = new LeftMenu(this.getApplicationContext());
        view.body.addView(menu, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }
    public void topInit() {

        top = new Top(this.getApplicationContext());
        FrameLayout topArea =(FrameLayout) findViewById(R.id._topArea);
        topArea.addView(top, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        top.init();
    }

    public void bottomInit() {

        bottom = new Bottom(this.getApplicationContext());
        FrameLayout bottomArea =(FrameLayout) findViewById(R.id._bottomArea);
        bottomArea.addView(bottom, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        bottom.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BitDataManager.getInstence().pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        BitDataManager.getInstence().resume();

    }

    public void recivePushMsg(Map<String,String> msg)
    {
        String title = msg.get("title");
        String message = msg.get("message");
        if(title == null) title = "";
        if(message == null) message = "";
        String more = LanguageFactory.getInstence().getResorceString("select_more");
        String confirm = LanguageFactory.getInstence().getResorceString("select_confirm");
        CharSequence[] items = {more,confirm};

        viewAlertSelectWithMenu(title, message,items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {

                if(which==-1)
                {
                    removePopup();
                    ActivityCore.getInstence().changeView(new PageObject(Config.PAGE_MSG));
                }else{

                }

            }
        });

    }

}