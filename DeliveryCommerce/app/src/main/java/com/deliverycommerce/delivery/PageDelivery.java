package com.deliverycommerce.delivery;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.LocationInfo.LocationInfoDelegate;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.delivery.DeliveryInfo.DeliveryInfoDelegate;
import com.deliverycommerce.searchmap.PopupSearchMap;
import com.deliverycommerce.searchmap.PopupSearchMap.SearchMapDelegate;
import com.deliverycommerce.web.PopupWeb;


import lib.CustomTimer;
import lib.CustomTimer.TimerDelegate;
import lib.core.PageObject;
import lib.core.ViewCore;

import lib.view.SlideBox;

import lib.view.SlideBox.SlideBoxDelegate;



import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;

import android.content.Context;


import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.Spinner;

import android.widget.LinearLayout;
import android.widget.TextView;






@SuppressLint("NewApi")
public class PageDelivery extends ViewCore implements  OnClickListener ,SlideBoxDelegate,
DeliveryInfoDelegate,LocationInfoDelegate,OnItemSelectedListener,SearchMapDelegate,TimerDelegate{
	
	
	private static Boolean locationSetting=false;
	private static final Object[][] String = null;
	private int pageIdx=-1;
	private int pageNum=0;
	
	private DeliveryInfo deliveryInfo;
	private ArrayList<Button> btnA;
	private SlideBox slideBox;
	private TextView sortText,positionText;
	private HorizontalScrollView menuBox;
	private ImageButton positionSelectBtn,positionBtn,menuBtn;
	private LinearLayout menuLists;
	private Spinner selectSpinner;
	private Boolean isSlideInit=false;
	private CustomTimer checkTimer;
	private PopupSearchMap searchPopup;

    private String addr1,addr2,addr3;

    public PageDelivery(Context context, PageObject  pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_delivery,this);  
	    slideBox=(SlideBox) findViewById(R.id._slideBox);
	    sortText=(TextView) findViewById(R.id._sortText);
	    positionText=(TextView) findViewById(R.id._positionText);
	    menuBox=(HorizontalScrollView) findViewById(R.id._menuBox);
	    menuLists=(LinearLayout) findViewById(R.id._menuLists);
	    
	    selectSpinner=(Spinner) findViewById(R.id._selectSpinner);
	    positionSelectBtn=(ImageButton) findViewById(R.id._positionSelectBtn);
	    positionBtn=(ImageButton) findViewById(R.id._positionBtn);
	    menuBtn=(ImageButton) findViewById(R.id._menuBtn);
	    btnA=new ArrayList<Button>();
	    checkTimer=new CustomTimer(5000,1,this);
	    
	    
	    slideBox.initBox(this);
	    menuBtn.setOnClickListener(this);
	    positionBtn.setOnClickListener(this);
	    selectSpinner.setOnItemSelectedListener(this);
	    positionSelectBtn.setOnClickListener(this);
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
	    setResizeHendler();
	    deliveryInfo=DeliveryInfo.getInstence();
	    deliveryInfo.delegate=this;
	    deliveryInfo.loadDeliveryOrderLists();
	    
	    MainActivity ac=(MainActivity)mainActivity;
    	ac.openBottomMenu();
	} 
	public void deliveryOrderUpdate(){
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item,deliveryInfo.orderMenuLists);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		selectSpinner.setAdapter(dataAdapter);
		
		
		if(deliveryInfo.orderLists.size()>0){
			sortText.setText(deliveryInfo.orderLists.get(deliveryInfo.finalOrder).name);
		}
		deliveryInfo.loadDeliverySortLists();
		
	}
	public void deliverySortUpdate(){
		pageNum=deliveryInfo.sortLists.size();
		pageIdx=deliveryInfo.finalIndex;
		
		for(int i=0;i<pageNum;++i){
		    SortList list=new SortList(MainActivity.getInstence(),deliveryInfo.sortLists.get(i).name);
		    btnA.add(list.btn);
		    menuLists.addView(list);
		    list.btn.setOnClickListener(this);
		}	
		mainActivity.loadingStart(true);
		if(LocationInfo.getInstence().getFullAdress().equals("")==true || locationSetting==false){
			locationSetting=true;
			findLocation();
		}else{
			
			locationUpdate();
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
		//LocationInfo.getInstence().checkLocationStop();
		mainActivity.viewAlert("", R.string.msg_search_location_error, null);
		locationUpdate();
	}
	public void locationUpdate(){
		findEndLocation();

        addr1=LocationInfo.getInstence().getAdress(1);
        addr2=LocationInfo.getInstence().getAdress(2);
        addr3=LocationInfo.getInstence().getAdress(3);
        positionBtn.setSelected(true);
        setTitle();


		if(isSlideInit==false){
			isSlideInit=true;
			slideBox.setSlide(pageIdx, pageNum);
		}else{
			slideBox.resetSlide(pageIdx, pageNum);
			
		}
	}
    private void setTitle(){
        if(addr3.equals("")){
            positionText.setText("위치");
        }else{
            positionText.setText(addr3);

        }
    }
    protected void doRemove() { 
    	
    	super.doRemove();
    	if(LocationInfo.getInstence().delegate==this){
			LocationInfo.getInstence().delegate=null;
			LocationInfo.getInstence().checkLocationStop();
		}
    	
    	if(slideBox!=null){
    		slideBox.removeSlide();
    		slideBox=null;
    	}
    	if(checkTimer!=null){
    		checkTimer.removeTimer();
    		checkTimer=null;
    		
    	}
    	
    	//deliveryInfo.registerDelivery();
    	deliveryInfo.registerOrder();
    	if(deliveryInfo.delegate==this){
    		deliveryInfo.delegate=null;
    		
    	}
    	searchPopup=null;
    	deliveryInfo=null;
    	
    }

    public void areaSelected(ArrayList<String> areas){
        if(searchPopup!=null){
            mainActivity.removePopup( searchPopup);
            searchPopup=null;
        }
        if(areas.size()<3)
        {
            mainActivity.viewAlert("",R.string.msg_select_addr_error,null);
            return;
        }
        positionBtn.setSelected(false);

        addr1=LocationInfo.getInstence().getAdressCodeByString(areas.get(0));
        addr2=areas.get(1);
        addr3=areas.get(2);
        setTitle();
        slideBox.moveIndex(pageIdx);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(isSlideInit==false){
        	
        	return;
        }
    	deliveryInfo.finalOrder=position;
    	sortText.setText(deliveryInfo.orderLists.get(deliveryInfo.finalOrder).name);
    	slideBox.moveIndex(pageIdx);
    }
 
    public void onNothingSelected(AdapterView<?> parent) {
       
    	
    }

    public void onClick(View arg) {
		if(arg==positionBtn){
			
			findLocation();;
			return;
		}else if(arg==menuBtn){
			MainActivity ac=(MainActivity)mainActivity;
			ac.openLeftMenu();
			return;
		}else if(arg==positionSelectBtn){
			
			PageObject pInfo=new PageObject(Config.POPUP_SEARCH_MAP);
			pInfo.dr=1;
			searchPopup=(PopupSearchMap)mainActivity.addPopup(pInfo);
			searchPopup.delegate=this;
			return;
		}
    	
    	int idx =btnA.indexOf(arg);
	    if(idx!=-1){
	    	slideBox.moveIndex(idx);
	    }
		
	}
    private void setSlide(FrameLayout frame, int idx){
		
		if(idx<0 || idx>=pageNum){
			return;
		}
		DeliveryLists currentView;
		PageObject pInfo=new PageObject(Config.PAGE_DELIVERY);
		Map<String,Object> pinfo=new HashMap<String,Object>();
		pinfo.put("typecode", deliveryInfo.getDeliverysCode(idx));
		pinfo.put("position", addr3);
		
		pinfo.put("order", deliveryInfo.getOrderCode());
		pinfo.put("addr1", addr1);
		pinfo.put("addr2", addr2);

		
		pInfo.info=pinfo;
		currentView=new DeliveryLists(mainActivity,pInfo);
		currentView.listView.setParent(slideBox);
		frame.addView(currentView, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
		
	} 
	
	public void  moveInit(SlideBox slideView,FrameLayout frame,int dr,int idx){
		//Log.i("","moveInit :"+idx);
		
		setSlide(frame,idx+dr);
		
		
	}
	public void clearFrame(SlideBox slideView,FrameLayout cframe){
		View child=cframe.getChildAt(0);
		if(child!=null){
			if(child instanceof ViewCore==true){
				ViewCore currentView=(ViewCore)child;
				currentView.removeViewCore();
			}
		}
	    cframe.removeAllViews();
	}
	
    public void frameSizeChange(SlideBox slideView,FrameLayout cframe,int idx){
    	View child=cframe.getChildAt(0);
		if(child!=null){
			if(child instanceof ViewCore==true){
				ViewCore currentView=(ViewCore)child;
				//currentView.configurationChanged();
			}
			
			
		}
	}
	public void moveComplete(SlideBox slideView,FrameLayout cframe,int idx){
		
		View child=cframe.getChildAt(0);
		if(child!=null){
			if(child instanceof ViewCore==true){
				ViewCore currentView=(ViewCore)child;
				currentView.movedInit();
			}
			
			
		}
		int pos=0;
		pageIdx=idx;
		//deliveryInfo.finalIndex=idx;
		for(int i=0;i<btnA.size();++i){
			Button btn= btnA.get(i);
			
			if(i==idx){
				btn.setSelected(true);
				ObjectAnimator objectAnimator = ObjectAnimator.ofInt(menuBox, "scrollX", 0,pos);
				objectAnimator.setDuration(100);
				objectAnimator.start();
			}else{
				btn.setSelected(false);
				
			}
			pos+=btn.getWidth();
		    
	    }
		
	}
	
	
	public void onTouchStart(SlideBox slideView){};
	public void onTouchEnd(SlideBox slideView){};
	
	public void onTopSlide(SlideBox slideView){};
	public void onEndSlide(SlideBox slideView){};
	public void selectSlide(SlideBox slideView,int idx){};
}
