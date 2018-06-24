package com.deliverycommerce.idel;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.deliverycommerce.BeaconInfo;
import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.LocationInfo.LocationInfoDelegate;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.SetupInfo;
import com.deliverycommerce.delivery.DeliveryInfo;
import com.deliverycommerce.delivery.DeliveryInfo.DeliveryInfoDelegate;
import com.deliverycommerce.delivery.DeliveryLists;
import com.deliverycommerce.delivery.SortList;
import com.deliverycommerce.searchmap.PopupSearchMap;
import com.deliverycommerce.searchmap.PopupSearchMap.SearchMapDelegate;
import com.deliverycommerce.selectarea.PopupSelectArea;
import com.deliverycommerce.selectarea.PopupSelectArea.SelectAreaDelegate;
import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.CustomTimer;
import lib.CustomTimer.TimerDelegate;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.SlideBox;
import lib.view.SlideBox.SlideBoxDelegate;


@SuppressLint("NewApi")
public class PageIdel extends ViewCore implements  OnClickListener ,
LocationInfoDelegate,SelectAreaDelegate,OnItemSelectedListener,TimerDelegate,BeaconInfo.BeaconInfoDelegate {


	private static Boolean locationSetting=false;

	private TextView sortText,positionText;
    private LinearLayout sortBox;
	private ImageButton positionSelectBtn,positionBtn,menuBtn;

	private Spinner selectSpinner;
	private CustomTimer checkTimer;
	private PopupSelectArea searchPopup;
    public ArrayList<String> orderMenuLists;
    public ArrayList<String> orderCodeLists;
    private FrameLayout lists;
    private IdelLists idelLists;
    private BeaconInfo beaconInfo;
    private static int orderIdx=0;
	public PageIdel(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_idel,this);
	    sortText=(TextView) findViewById(R.id._sortText);
	    positionText=(TextView) findViewById(R.id._positionText);
        sortBox=(LinearLayout) findViewById(R.id._sortBox);
	    selectSpinner=(Spinner) findViewById(R.id._selectSpinner);
	    positionSelectBtn=(ImageButton) findViewById(R.id._positionSelectBtn);
	    positionBtn=(ImageButton) findViewById(R.id._positionBtn);
	    menuBtn=(ImageButton) findViewById(R.id._menuBtn);
        lists=(FrameLayout) findViewById(R.id._lists);
	    checkTimer=new CustomTimer(5000,1,this);

        orderMenuLists=new ArrayList<String>();
        orderMenuLists.add("이름순");
        orderMenuLists.add("거리순");
        orderMenuLists.add("가격순");

        orderCodeLists=new ArrayList<String>();
        orderCodeLists.add("0");
        orderCodeLists.add("1");
        orderCodeLists.add("2");


	    menuBtn.setOnClickListener(this);
	    positionBtn.setOnClickListener(this);
	    selectSpinner.setOnItemSelectedListener(this);
	    positionSelectBtn.setOnClickListener(this);

        positionText.setText("위치");
        sortBox.setVisibility(View.GONE);
        PageObject pInfo=new PageObject(Config.PAGE_IDEL);
        Map<String,Object> pinfo=new HashMap<String,Object>();
        pInfo.info=pinfo;
        idelLists=new IdelLists(mainActivity,pInfo);
        lists.addView(idelLists, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    }
	
	protected void doRemovePopup(ViewCore pop) { 
	       
	    if(searchPopup==pop){
	    	searchPopup=null;
	    }
		
		
		MainActivity ac=(MainActivity)mainActivity;
	    ac.openBottomMenu();
	     
	 }
	

	protected void doResize() { 
    	super.doResize();
    	
    }
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	    MainActivity ac=(MainActivity)mainActivity;
        beaconInfo = BeaconInfo.getInstence();
        idelLists.isLive=SetupInfo.isBeaconSuport;
        ac.openBottomMenu();
        orderUpdate();
	} 
	private void orderUpdate(){
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item,orderMenuLists);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectSpinner.setAdapter(dataAdapter);
        if(SetupInfo.isBeaconSuport==false){
            findLocation();
        }else{

            checkBluetooth();
        }





	}
    private void checkBluetooth() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();



        if(adapter.getState() == BluetoothAdapter.STATE_TURNING_ON ||
                adapter.getState() == BluetoothAdapter.STATE_ON)
        {
            findLocation();
        }
        else
        {
            mainActivity.viewAlertSelect("", R.string.msg_idel_bluetoothon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {

                    if(which==-1){
                        adapter.enable();

                    }else{
                        //findLocation();

                    }
                    findLocation();
                }
            });
        }
    }
	private void findLocation(){

		if(LocationInfo.getInstence().isChecking==true){
			locationUpdate();
			return;
			
		}
		mainActivity.loadingStart(true);
		LocationInfo.getInstence().delegate=this;
		LocationInfo.getInstence().checkLocationStart();
		checkTimer.resetTimer();
		checkTimer.timerStart();
	}
	private void findEndLocation(){
		mainActivity.loadingStop();
		if(LocationInfo.getInstence().delegate==this){
			LocationInfo.getInstence().delegate=null;
			LocationInfo.getInstence().checkLocationStop();
		}
		
		checkTimer.timerStop();
	}
	public void onTime(CustomTimer timer){
		
	}
	public void onComplete(CustomTimer timer){

		mainActivity.viewAlert("", R.string.msg_search_location_error, null);
		locationUpdate();
	}
	public void locationUpdate(){
		findEndLocation();
		
		String location=LocationInfo.getInstence().getAdress(3);
        setTitle(location);

        if(idelLists.isLive==true){

            liveUpdate();
        }else{
            ArrayList<String> areas =new ArrayList<String>();
            areas.add(LocationInfo.getInstence().getAdress(1));
            areas.add(LocationInfo.getInstence().getAdress(2));
            //areas.add(LocationInfo.getInstence().getAdress(3));
            listUpdate(areas);

        }


	}
    private void setTitle(String title){
        if(title.equals("")){

        }else{
            positionText.setText(title);

        }
    }
    private void liveUpdate(){
        positionBtn.setSelected(true);
        idelLists.setLiveType();
        sortBox.setVisibility(View.GONE);
        beaconInfo.delegate=this;
        beaconInfo.beaconReset();

    }
    private void listUpdate(ArrayList<String> areas){
        positionBtn.setSelected(false);
        Log.i("","areas.size() : "+areas.size());
        if(areas.size()>=1) {
            setTitle(areas.get(0).replace("|"," "));
        }
        beaconInfo.delegate=null;
       // sortBox.setVisibility(View.VISIBLE);
        sortText.setText(orderMenuLists.get(orderIdx));
        idelLists.setLoadType(areas,orderCodeLists.get(orderIdx));

    }
    public void areaSelected(ArrayList<String> areas){
        if(searchPopup!=null){
            mainActivity.removePopup( searchPopup);
            searchPopup=null;
        }


        listUpdate(areas);
    }
    public void beaconUpdate(ArrayList<Beacon> adds){
        if(idelLists.isLive){
            for(int i=0;i<adds.size();++i){
                IdelObject idel=new IdelObject();
                Beacon bc=adds.get(i);
                idel.code=bc.getId1().toString();
                idelLists.addData(idel);
            }
            idelLists.addDataComplete();
        }

    }

    protected void doRemove() { 
    	
    	super.doRemove();
    	if(LocationInfo.getInstence().delegate==this){
			LocationInfo.getInstence().delegate=null;
			LocationInfo.getInstence().checkLocationStop();
		}
    	

    	if(checkTimer!=null){
    		checkTimer.removeTimer();
    		checkTimer=null;
    		
    	}
    	searchPopup=null;

    	
    } 
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(idelLists.isLive==true){
            mainActivity.viewAlert("",R.string.msg_idel_order_permit,null);
            return;
        }

        if(orderIdx==position){
            return;
        }
        orderIdx=position;
        listUpdate(new ArrayList<String>());

    }
 
    public void onNothingSelected(AdapterView<?> parent) {
       
    	
    }

    public void onClick(View arg) {
		if(arg==positionBtn){

            if(SetupInfo.isBeaconSuport==false){
                mainActivity.viewAlert("",R.string.msg_androidvs_low,null);
                return;
            }else{
                idelLists.isLive=true;
                checkBluetooth();
            }

			return;
		}else if(arg==menuBtn){
			MainActivity ac=(MainActivity)mainActivity;
			ac.openLeftMenu();
			return;
		}else if(arg==positionSelectBtn){
			
			PageObject pInfo=new PageObject(Config.POPUP_SELECT_AREA);
			pInfo.dr=1;
			searchPopup=(PopupSelectArea)mainActivity.addPopup(pInfo);
			searchPopup.delegate=this;
			return;
		}
    	

		
	}

}
