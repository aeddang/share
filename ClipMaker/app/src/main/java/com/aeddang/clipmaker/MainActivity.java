package com.aeddang.clipmaker;



import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



import com.aeddang.clipmaker.pagecomplete.PageComplete;
import com.aeddang.clipmaker.pagecomplete.PopupShare;
import com.aeddang.clipmaker.pagerecord.PageRecord;
import com.aeddang.clipmaker.pagesetting.PageSetting;


import lib.ViewUtil;
import lib.core.ActivityCore;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.googleapi.GoogleDriveAPI;


public class MainActivity extends ActivityCore implements View.OnClickListener {
    private Button viewerDimed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isInit==true){
            return;

        }

        SetupInfo setupInfo=new SetupInfo();
        //GoogleDriveAPI googleAPI=new GoogleDriveAPI(this);
        // getWindow().requestFeature(Window.FEATURE_PROGRESS);


        viewerDimed=(Button) findViewById(R.id._viewerDimed);
        viewerDimed.setVisibility(View.GONE);
        viewerDimed.setOnClickListener(this);

        setupInfo.loadData();


    }
    public void appStart() {
        int recordHei=(int)Math.round((double)viewer.getWidth()/2.f)-(int)Math.round((double)loadingImage.getHeight()/2.f);
        ViewUtil.setFrame(loadingImage,-1,recordHei,-1,-1);

        removeIntroStart(0);
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
       // GoogleAPI.getInstence().refreshResults();
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleDriveAPI.getInstence().onActivityResult(requestCode, resultCode, data);

    }
    public void onClick(View arg) {
        if(viewerDimed==arg){
            //closeLeftMenu();
        }

    }
    @Override
    protected void creatView(){

        super.creatView();

        switch(currentPageObj.pageID){
            case HOME:
            case Config.PAGE_HOME:
                currentPageObj.isHome=true;
                currentView=new PageRecord(this, currentPageObj);
                break;
            case Config.PAGE_COMPLETE:

                currentView=new PageComplete(this, currentPageObj);
                break;
            case Config.PAGE_SETTING:

                currentView=new PageSetting(this, currentPageObj);
                break;


        }


    }
    @Override
    protected void configurationChanged() {
        super.configurationChanged();
    }
    @Override
    protected ViewCore creatPopup(PageObject pinfo) {

        ViewCore pop=null;

        switch(pinfo.pageID){

            case Config.POPUP_SHERE:
                pop=new PopupShare(this, pinfo);
                break;



        }

        return pop;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
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
}
