package com.deliverycommerce.searchmap;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.LocationInfo.AreaInfoDelegate;
import com.deliverycommerce.LocationInfo.LocationInfoDelegate;
import com.deliverycommerce.R;
import com.deliverycommerce.delivery.DeliveryInfo;
import com.deliverycommerce.delivery.DeliveryLists;
import com.deliverycommerce.searchmap.PageSearchMap.MapDelegate;
import com.deliverycommerce.searchmap.PageSearchPost.PostDelegate;
import com.deliverycommerce.selectarea.PopupSelectArea.SelectAreaDelegate;
import com.deliverycommerce.web.PopupWeb.PopupWebDelegate;








import lib.CommonUtil;
import lib.ViewUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.SlideBox;
import lib.view.SlideBox.SlideBoxDelegate;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;

import android.content.Context;


import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;







@SuppressLint("NewApi")
public class PopupSearchMap extends ViewCore implements  OnClickListener,SlideBoxDelegate,PostDelegate,MapDelegate{
	
	private static int pageIdx=0;
	private static final int PAGE_NUM=2;
	private FrameLayout body;
	private ImageButton backBtn,positionBtn;
	private SlideBox slideBox;
	private ArrayList<Button> tabs;
	private TextView title;
    private ViewCore currentMap;
	public SearchMapDelegate delegate;
    private Boolean isActiveBT;
	public PopupSearchMap(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.popup_search_map,this);  
	    title=(TextView) findViewById(R.id._title);
	    body=(FrameLayout) findViewById(R.id._body);
	    positionBtn=(ImageButton) findViewById(R.id._positionBtn);
	    backBtn=(ImageButton) findViewById(R.id._backBtn);
	    slideBox=(SlideBox)findViewById(R.id._slideBox);
	    
	    tabs=new ArrayList<Button>();
	    for(int i=0;i<PAGE_NUM;++i){
	    	 int bid = this.getResources().getIdentifier( "_tab"+i, "id", mainActivity.getPackageName());
	    	 Button tab= (Button)findViewById(bid);
	    	 tabs.add(tab);
	    	 tab.setOnClickListener(this);
	    	 tab.setSelected(false);
	    	 
	    }
        MainActivity ac=(MainActivity)mainActivity;
        isActiveBT=ac.isOpenBottomMenu();
	    positionBtn.setOnClickListener(this);
	    backBtn.setOnClickListener(this);
	    slideBox.initBox(this);
    }
	
	
	
	
	public void onClick(View arg) {
		
		if(backBtn==arg){
			mainActivity.removePopup(this);
		}else if(arg==positionBtn){
			if(currentMap!=null){

                currentMap.sendCommand("me");
            }
		}else{
			int idx=tabs.indexOf(arg);
			if(idx!=-1){
				if(pageIdx==idx){
					//slideBox.moveIndex(idx);
					
				}else if(pageIdx<idx){
					slideBox.moveLeft();
				}else{
					slideBox.moveRight();
					
				}
				
				
				
			}
			
		}
		
		
	}
    protected void doResize() { 
    	super.doResize();
		
    }
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	    
	    slideBox.setSlide(pageIdx, PAGE_NUM);
	    MainActivity ac=(MainActivity)mainActivity;
    	ac.closeBottomMenu();
	} 
	public void mapSelected(ArrayList<String> areas){
		
		Log.i("","areas.size(): =" + areas.size());
        if(areas.size()==3){

            Log.i("",areas.get(0));
            Log.i("",areas.get(1));
            Log.i("",areas.get(2));
        }

        if(delegate!=null){delegate.areaSelected(areas);}
	}
	public void postSelected(ArrayList<String> areas){
        Log.i("","areas.size(): =" + areas.size());
        if(areas.size()==3){

            Log.i("",areas.get(0));
            Log.i("",areas.get(1));
            Log.i("",areas.get(2));
        }
		if(delegate!=null){delegate.areaSelected(areas);}
	}
	
    protected void doRemove() { 
    	
    	super.doRemove();
        currentMap=null;
        if(slideBox!=null){
    		slideBox.removeSlide();
    		slideBox=null;
    	}
        MainActivity ac=(MainActivity)mainActivity;
        if( isActiveBT){
            ac.openBottomMenu();

        }
    } 
    
    private void setSlide(FrameLayout frame, int idx){
		
		if(idx<0 || idx>=PAGE_NUM){
			return;
		}
		ViewCore currentView;
		PageObject pInfo=new PageObject(Config.POPUP_SEARCH_MAP);
	    switch(idx){
	    case 0:
	    	currentView=new PageSearchPost(mainActivity,pInfo);
	    	PageSearchPost postView=(PageSearchPost)currentView;
	    	postView.scrollView.setParent(slideBox);
	    	postView.delegate=this;
	    	break;
	    default:
	    	currentView=new PageSearchMap(mainActivity,pInfo);
	    	PageSearchMap mapView=(PageSearchMap)currentView;
	    	mapView.delegate=this;
	    	break;
	    
	    
	    }
	
		frame.addView(currentView, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
		
	} 
	
	public void  moveInit(SlideBox slideView,FrameLayout frame,int dr,int idx){
		
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
                currentMap=currentView;
			}
			
			
		}
		int pos=0;
		pageIdx=idx;
		
		for(int i=0;i<tabs.size();++i){
			Button btn= tabs.get(i);
			
			if(i==idx){
				btn.setSelected(true);
			}else{
				btn.setSelected(false);
				
			}
			
	    }
		
	}
	
	
	public void onTouchStart(SlideBox slideView){};
	public void onTouchEnd(SlideBox slideView){};
	
	public void onTopSlide(SlideBox slideView){};
	public void onEndSlide(SlideBox slideView){};
	public void selectSlide(SlideBox slideView,int idx){};
	
	
	public interface SearchMapDelegate
	{
		void areaSelected(ArrayList<String> areas);
		
	}
}
