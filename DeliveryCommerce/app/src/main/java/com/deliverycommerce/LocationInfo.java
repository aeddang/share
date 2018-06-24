package com.deliverycommerce;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deliverycommerce.delivery.DeliveryObject;
import com.deliverycommerce.delivery.SortObject;

import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationInfo implements JsonManagerDelegate,LocationListener{
	
	
	private static final String [] CHECK_AREA0 ={"강원","강원도"};
	private static final String [] CHECK_AREA1 ={"경기","경기도"};
	private static final String [] CHECK_AREA2 ={"경남","경상남도"};
	private static final String [] CHECK_AREA3 ={"경북","경상북도"};
	private static final String [] CHECK_AREA4 ={"광주","광주시","광주광역시"};
	private static final String [] CHECK_AREA5 ={"대구","대구시","대구광역시"};
	private static final String [] CHECK_AREA6 ={"대전","대전시","대전광역시"};
	private static final String [] CHECK_AREA7 ={"부산","부산시","부산광역시"};
	private static final String [] CHECK_AREA8 ={"서울","서울시","서울특별시"};
	private static final String [] CHECK_AREA9 ={"세종","세종시","세종광역시","세종특별시","세종특별자치구"};
	private static final String [] CHECK_AREA10 ={"울산","울산시","울산광역시"};
	private static final String [] CHECK_AREA11 ={"인천","인천시","인천광역시"};
	
	private static final String [] CHECK_AREA12 ={"전남","전라남도"};
	private static final String [] CHECK_AREA13 ={"전북","전라북도"};
	private static final String [] CHECK_AREA14 ={"제주","제주시","제주도"};
	private static final String [] CHECK_AREA15 ={"충남","충청남도"};
	private static final String [] CHECK_AREA16 ={"충북","충청북도"};
	
	private final String AREA_KEY="AREA_KEY_1.0";
	private final String ADRESS_KEY="ADRESS_KEY";
	private LocationManager locManager;
	private Geocoder geoCoder;
	private Location myLocation = null;
	//private double latPoint, lngPoint; 
	
	private String fullAdress; 
	public LocationInfoDelegate delegate;
	
	public AreaInfoDelegate delegateA;
	private JsonManager jsonManager;
	private static LocationInfo instence;
	
	public ArrayList<String> posLists;
	public ArrayList<String> posCodeLists;
	public ArrayList<String [] > checkLists;
	public Boolean isChecking=false;
	private SharedPreferences settings;
    
	public static LocationInfo getInstence(){
    	 
         return instence;
	}
	
    public LocationInfo(Context context) {
    	instence=this;
    	
    	settings =  MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
    	jsonManager=new JsonManager(this);
  
    	locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    	
    	geoCoder = new Geocoder(context, Locale.KOREA);
    	
    	Criteria criteria = new Criteria();
        String provider =locManager.getBestProvider(criteria, true);
       
    	checkLists =  new ArrayList<String [] >();
    	
    	checkLists.add(CHECK_AREA0);
    	checkLists.add(CHECK_AREA1);
    	checkLists.add(CHECK_AREA2);
    	checkLists.add(CHECK_AREA3);
    	checkLists.add(CHECK_AREA4);
    	checkLists.add(CHECK_AREA5);
    	checkLists.add(CHECK_AREA6);
    	checkLists.add(CHECK_AREA7);
    	checkLists.add(CHECK_AREA8);
    	checkLists.add(CHECK_AREA8);
    	checkLists.add(CHECK_AREA10);
    	checkLists.add(CHECK_AREA11);
    	checkLists.add(CHECK_AREA12);
    	checkLists.add(CHECK_AREA13);
    	checkLists.add(CHECK_AREA14);
    	checkLists.add(CHECK_AREA15);
    	checkLists.add(CHECK_AREA16);
    	
    	 myLocation = locManager.getLastKnownLocation(provider);
    	 fullAdress = settings.getString(ADRESS_KEY, "");
    	 if(myLocation!=null){
    		 Log.i("","me : "+myLocation.toString());
    		 fullAdress= getGeoLocation(myLocation.getLatitude(), myLocation.getLongitude());
    		 
    	 }else{
    		 
    		 Log.i("","me : location null");
    	 }
    	
    }
    public void checkLocationStart() {
    	if(isChecking==true){
    		return;
    		
    	}
    	
    	isChecking=true;
    	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    	locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
    }
    public void checkLocationStop() {
    	if(isChecking==false){
    		return;
    		
    	}
    	isChecking=false;
    	locManager.removeUpdates(this);
    }
    @Override
    public void onLocationChanged(Location location) {
     // TODO Auto-generated method stub
    	myLocation = location;
    	if(myLocation != null) {
  		   
  		   fullAdress=getGeoLocation(myLocation.getLatitude(),myLocation.getLongitude());
  		   registerAdress(fullAdress);
    	}
    	
    	checkLocationStop();
    	
    	if(delegate!=null){delegate.locationUpdate();}
    
    }
    
    public void setAdress(String add1,String add2,String add3){
    	fullAdress="nation"+" "+add1+" "+add2+" "+add3;
    }  
    private String changeCode(String str,String[] checks){
    	String rstr="";
    	for(int i=0;i<checks.length;++i){
			if(str.equals(checks[i])){
				rstr=checks[0];
				break;
			}
    	}
    	return rstr;
    }
    public void registerAdress(String adress){
 	     SharedPreferences.Editor editor = settings.edit();
	     editor.putString(ADRESS_KEY, adress);
	     editor.commit();
	  
   }
    public void registerArea(String area){
  	     SharedPreferences.Editor editor = settings.edit();
	     editor.putString(AREA_KEY, area);
	     editor.commit();
	  
   }
   public ArrayList<String> getRegisterArea(){
	   String areaStr=settings.getString(AREA_KEY, "");
	   String[] areas=areaStr.split("\\ ");
	   ArrayList<String> returnA=new ArrayList<String>();  
	   for(int i=0;i<areas.length;++i){
		   returnA.add(areas[i]);
        }
	   return returnA;
   }
   public double getLatitude(){
	   if(myLocation!=null){
  		  return myLocation.getLatitude();
  		
  	 }else{
  		return 0;
  	 }
  	
   } 
   public Location geMyLocation(){
	    return myLocation;
  
  	
   }
   public double getLongitude(){
	   if(myLocation!=null){
  		   return myLocation.getLongitude();
  		 
  	 }else{
  		 
  		return 0;
  	 }
  	
   } 
   public String getFullAdress(){
       
	   return fullAdress;
  	
   } 
   public String getAdress(int type){
        
    	return getAdressByString(type,fullAdress);
    	
    	
    }
    public String getAdressCodeByString(String addr){

        for(int i=0;i<checkLists.size();++i){
            String cs=changeCode(addr,checkLists.get(i));
            if(!cs.equals("")){
                addr=cs;
                break;
            }
        }
        return addr;


    }
    public String getAdressByString(int type,String add){

        String[] adressA=add.split("\\ ");
        if(adressA.length<type+1){
            return "";
        }
        String str=adressA[type];
        String cs="";
        if(type==1){
            str=getAdressCodeByString(str);
        }
        return str;
   	
   	
   } 
   public String getGeoLocation(double latPoint,double lngPoint) {
	   StringBuffer mAddress = new StringBuffer();
	  
		   try {
			   // 위도,경도를 이용하여 현재 위치의 주소를 가져온다. 
			   List<Address> addresses;
			   addresses = geoCoder.getFromLocation(latPoint, lngPoint, 1);
			   for(Address addr: addresses){
				   int index = addr.getMaxAddressLineIndex();
				   for(int i=0;i<=index;i++){
					   mAddress.append(addr.getAddressLine(i));
					   mAddress.append(" ");
				   }
			   }
		   } catch (IOException e) {
       
		   }
		   Log.e("","Address : " + mAddress);
		   return mAddress.toString();
	  
	   
    }
    
   
   public void loadAreaLists(){
       if(posLists!=null){
    	   
    	   if(delegateA!=null){delegateA.areaUpdate(0,posLists);}
    	   return;
       }
     
       jsonManager.loadData(Config.API_POSITION_LIST_0,null);
   	
   }
   public String getAreaKey(String key){
        Map<String,String> paramA=new HashMap<String,String>();
        int idx=posLists.indexOf(key);
        if(idx==-1){
            return "";
        }
        String code=posCodeLists.get(idx);
        return code;

    }
    public void loadGuAreaLists(String key){
	   Map<String,String> paramA=new HashMap<String,String>();

	   String code=getAreaKey(key);
	   paramA.put("addr1",code);
       jsonManager.loadData(Config.API_POSITION_LIST_1,paramA);
	   	
   } 
   public void loadDongAreaLists(String key ,String key2){
	 
	   Map<String,String> paramA=new HashMap<String,String>();
	   try {
		   paramA.put("addr1", URLEncoder.encode(key, "UTF-8"));
		   paramA.put("addr2", URLEncoder.encode(key2, "UTF-8"));
   	
		} catch (UnsupportedEncodingException e) {
			
		}
      
       jsonManager.loadData(Config.API_POSITION_LIST_2,paramA);
   } 
    public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
    { 
	   
	    int type=0;
	    if(xml_path.equals(Config.API_POSITION_LIST_0)){
	    	type=0;
	    }else if(xml_path.equals(Config.API_POSITION_LIST_1)){
	    	type=1;
	    	
	    }else if(xml_path.equals(Config.API_POSITION_LIST_2)){
	    	type=2;
	    	
	    }
	    
	    JSONArray results = null;
	    ArrayList<String> lists=new ArrayList<String>();
	    ArrayList<String> codes=new ArrayList<String>();
	   
	    try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			results=new JSONArray();
		}
		
		Log.i("","LISTSIZE : "+results.length()+" type : "+type);
		for(int i=0;i<results.length();++i){
			
			try {
				JSONObject obj=results.getJSONObject(i);
				String key="";
				String code="";
				if(type==0){
					key=obj.getString("sido");
					code=obj.getString("code");
				}else if(type==1){
					key=obj.getString("gugun");
				}else{
					key=obj.getString("dong");
				}
				lists.add(key);
				codes.add(code);
				
			} catch (JSONException e) {
				
			}
		}
		if(type==0){
			
			posLists=lists;
			posCodeLists=codes;
		}
	    
	    if(delegateA!=null){delegateA.areaUpdate(type,lists);}
	} 
    
    
   
    public void onJsonLoadErr(JsonManager manager,String xml_path) {
		MainActivity.getInstence().viewAlert("", R.string.msg_network_err, null);
		MainActivity.getInstence().loadingStop();
		
		
		
	} 
	
    public void onStatusChanged(String provider, int status, Bundle extras){}

    public void onProviderEnabled(String provider){}

    public void onProviderDisabled(String provider){}
    
    
	public interface LocationInfoDelegate
	{
		void locationUpdate();
		
	}
	public interface AreaInfoDelegate
	{
		void areaUpdate(int type,ArrayList<String> areaLists);
		
	}
}

