package com.deliverycommerce.searchmap;






import java.util.ArrayList;

import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.searchmap.PopupSearchMap.SearchMapDelegate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import lib.ViewUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;
import lib.view.RackableScrollView;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.content.Context;
import android.location.Location;


import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;








@SuppressLint("NewApi")
public class PageSearchMap extends ViewCore  implements OnClickListener,OnMapClickListener{
	
	

	private Button searchBtn;
	public MapDelegate delegate;
	private MapFragment mapView;
	private GoogleMap map;
	private Marker selectMK;
	private Marker meMK;
    private Handler mapFindHandler;
    private Runnable mapRunnable;

    private static Location selectPos;
    private final float zoom = 12.8f;
	public PageSearchMap(Context context, PageObject  pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.tab_select_area1,this);
	    
	    searchBtn=(Button) findViewById(R.id._searchBtn);
	    MainActivity ac=(MainActivity) mainActivity; 
	    mapView=ac.getShareMap();
	    FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
        ft.add(R.id._mapView, mapView);
        ft.commit();
	    searchBtn.setOnClickListener(this);
        mapFindHandler=new Handler();
	    
    }
	private void moveMap(Location pos,float zoom){
		
	    if(pos==null){
            mainActivity.viewAlert("", R.string.msg_search_location_error, null);
            return;
        }

        CameraPosition camPos = new CameraPosition.Builder().target(new LatLng(pos.getLatitude(),pos.getLongitude())).zoom(zoom).build();
        CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
        map.moveCamera(camUpdate);
		
	}
    private Marker addMarker(Location pos,String text)
    {
    	 MarkerOptions opt = new MarkerOptions();
    	 opt.position(new LatLng(pos.getLatitude(),pos.getLongitude()));// 위도 • 경도
    	 opt.title(text); // 제목 미리보기
    	 Marker cm=map.addMarker(opt);
    	 cm.showInfoWindow();

    	 return cm;
    
		
	}
    public void onMapClick(LatLng point) {
         Log.i("Map","point : " +point.toString());
         if(selectMK!=null){
             selectMK.showInfoWindow();
        	 selectMK.setPosition(point);
         }
    }

    private void setMap() {
    	map=mapView.getMap();
    	if(map==null){
    	   return;		
    	}
        mainActivity.loadingStop();
	    map.setOnMapClickListener(this);
	    Location myPos=LocationInfo.getInstence().geMyLocation();
	    if(myPos!=null){

		    meMK=addMarker(myPos,"내위치");
        }

        if(selectPos==null){
            if(myPos!=null){
                selectMK=addMarker(myPos,"선택위치");
            }
            moveMap(myPos,zoom);
        }else{
            selectMK=addMarker(selectPos,"선택위치");
            moveMap(selectPos,zoom);
        }


   }
    protected void doSendCommand(String command){
         if(command.equals("me")){

             if(map==null){
                 return;
             }
             Location myPos=LocationInfo.getInstence().geMyLocation();
             if(myPos!=null){
                moveMap(myPos,zoom);
                meMK.showInfoWindow();
             }
         }
    }

	protected void doMovedInit() { 
	    super.doMovedInit();
        mainActivity.loadingStart(false);
        mapRunnable=new Runnable(){
            public void run() {

                setMap();
                if(map!=null){

                }else{
                    if(mapFindHandler!=null){
                        mapFindHandler.postDelayed(mapRunnable,1000);
                    }
                }
            };
        };

        mapFindHandler.postDelayed(mapRunnable,1000);


	    
	    
	    
	    
	} 
	
    protected void doRemove() { 
    	
    	super.doRemove();
    	if(mapFindHandler!=null){
            mapFindHandler.removeCallbacks(mapRunnable);
            mapFindHandler=null;
        }
    	
    	if(map!=null){
    		map.clear();
    		map=null;
    	}
    	if(mapView!=null){
    		FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
    		ft.remove(mapView);
    		ft.commit();
    		mapView=null;
    	}

    } 
 
  
	//delegate
    
    
    
	 public void onClick(View v) {
		
			 
		if(v==searchBtn){
			
			
			String add=LocationInfo.getInstence().getGeoLocation(selectMK.getPosition().latitude,selectMK.getPosition().longitude);
            selectPos=new Location("dummyprovider");
            selectPos.setLatitude(selectMK.getPosition().latitude);
            selectPos.setLongitude(selectMK.getPosition().longitude);

            ArrayList<String> areas=new ArrayList<String>();
            areas.add(LocationInfo.getInstence().getAdressByString(1,add));
            areas.add(LocationInfo.getInstence().getAdressByString(2,add));
            areas.add(LocationInfo.getInstence().getAdressByString(3,add));

			if(delegate!=null){delegate.mapSelected(areas);}
		}
		
		
	} 
    
    
	 public interface MapDelegate
	 {
			void mapSelected(ArrayList<String> areas);
			
	 }
    
}
