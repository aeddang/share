package com.deliverycommerce.delivery;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.UserInfo;

import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;


import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class DeliveryInfo implements JsonManagerDelegate{
	
	
	
	private final String DELIVERY_KEY="DELIVERY_KEY";
	private final String ORDER_KEY="ORDER_KEY";
	
	
	
	
	public DeliveryInfoDelegate delegate;
	
	public DeliveryDataDelegate delegateD;
	
	public Boolean isLogin;
	private JsonManager jsonManager;
	private static DeliveryInfo instence;
	private String deliveryKEY,orderKEY;
	public ArrayList<SortObject> sortLists;
	public ArrayList<SortObject> orderLists;
	public ArrayList<String> orderMenuLists;
	public int finalIndex,finalOrder,finalPage=0;
	private SharedPreferences settings;
	private String loadTypeCode,loadPosition,loadOrder;
	private Map<String,ArrayList<DeliveryObject>> dataA;
	
    
	public static DeliveryInfo getInstence(){
    	 if(instence == null) {
         	instence = new DeliveryInfo();
         }
         return instence;
	}
	
    public DeliveryInfo() {
    	
    	isLogin=false;
    	settings =  MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
    	jsonManager=new JsonManager(this);
    	dataA=new HashMap<String,ArrayList<DeliveryObject>>();
    	loadPosition="";
    	loadTypeCode="";
    	deliveryKEY=settings.getString(DELIVERY_KEY, "");
    	orderKEY=settings.getString(ORDER_KEY, "");
    	sortLists=null;
    	orderLists=null;
    	
    }
    public String getOrderCode(){
    	if(orderLists==null){
    		return "";
    	}
    	if(orderLists.size()<=finalOrder){
    		return "";
    	}
    	return orderLists.get(finalOrder).code;
    	
    }
    public String getDeliverysCode(int idx){
    	if(sortLists==null){
    		return "";
    	}
    	if(sortLists.size()<=idx){
    		return "";
    	}
    	return sortLists.get(idx).code;
    }
    public void registerOrder(){
    	 if(orderLists==null){
    		 
    		 return;
    	 }
    	 if(orderLists.size()<=finalOrder){
    		 return;
    	 }
    	 String key=orderLists.get(finalOrder).code;
	     SharedPreferences.Editor editor = settings.edit();
	     if(!key.equals("")){
	    	 editor.putString(ORDER_KEY, key);
	    	 orderKEY=key;
	     }
	     editor.commit();
	  
   }
    
   
    
    public void registerDelivery(){
   	 if(sortLists==null){
   		 
   		 return;
   	 }
   	 if(sortLists.size()<=finalIndex){
   		 return;
   	 }
   	 String key=sortLists.get(finalIndex).code;
	     SharedPreferences.Editor editor = settings.edit();
	     if(!key.equals("")){
	    	 editor.putString(DELIVERY_KEY, key);
	    	 deliveryKEY=key;
	     }
	     editor.commit();
	  
   }
    public ArrayList<DeliveryObject> getDeliveryDataLists(String typecode){
    	ArrayList<DeliveryObject> lists=dataA.get(typecode);
    	if(lists==null){
    		lists=new ArrayList<DeliveryObject>();
    	}
    	return lists;
        
    }
    public void loadDeliveryDataLists(String typecode,String position,String order,
    								  String addr1,String addr2,int page){
    	loadTypeCode=typecode;
    	if(!position.equals(loadPosition)){
    		loadPosition=position;
    		dataA=new HashMap<String,ArrayList<DeliveryObject>>();
    	}
    	if(!order.equals(loadOrder)){
    		loadOrder=order;
    		dataA=new HashMap<String,ArrayList<DeliveryObject>>();
    	}
    	if(page==0){
    		if(dataA.get(typecode)!=null){
    			if(delegateD!=null){delegateD.deliveryDataUpdate(dataA.get(typecode));}
    			return;
    		}
    	}
    	finalPage=page;
    	Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("apuid", UserInfo.getInstence().userID);
        
        try {
        	
        	paramA.put("addr1", URLEncoder.encode(addr1, "UTF-8"));
            paramA.put("addr2", URLEncoder.encode(addr2, "UTF-8"));
            paramA.put("addr3", URLEncoder.encode(loadPosition, "UTF-8"));
    	
 		} catch (UnsupportedEncodingException e) {
 			
 		}
        
        paramA.put("lot", LocationInfo.getInstence().getLongitude()+"");
    	paramA.put("lat", LocationInfo.getInstence().getLatitude()+"");
        paramA.put("storetype", loadTypeCode);
        paramA.put("ordertype", loadOrder);
    	
        paramA.put("page", page+"");
    	 
    	
    	MainActivity.getInstence().loadingStart(false);
    	jsonManager.loadData(Config.API_DELIVERY_LIST,paramA);
        
    }
    public void loadDeliverySortLists(){
    	
    	if(sortLists!=null){
    		if(delegate!=null){delegate.deliverySortUpdate();}
    		return;
    	}
    	MainActivity.getInstence().loadingStart(true);
    	jsonManager.loadData(Config.API_SORT_LIST);
        
    }
    public void loadDeliveryOrderLists(){
    	
    	if(orderLists!=null){
    		if(delegate!=null){delegate.deliveryOrderUpdate();}
    		return;
    	}
    	MainActivity.getInstence().loadingStart(true);
    	jsonManager.loadData(Config.API_ORDER_LIST);
        
    }
    
    public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
    { 
	    MainActivity.getInstence().loadingStop();
	    if(xml_path.equals(Config.API_SORT_LIST)){
 	    	sortListComplete(result,0);
 	    }else if(xml_path.equals(Config.API_ORDER_LIST)){
 	    	sortListComplete(result,1);
 	    }else{
 	    	dataListComplete(result);
 	    }
	    	
	} 
    
    private void dataListComplete(JSONObject result){
    	JSONArray results = null;
		
    	try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			if(delegateD!=null){delegateD.deliveryDataUpdate(null);}
			return;
		}
		if( results.length()<1){
			if(delegateD!=null){delegateD.deliveryDataUpdate(null);}
			return;
		}
		ArrayList<DeliveryObject> lists=new ArrayList<DeliveryObject>();
		for(int i=0;i<results.length();++i){
			DeliveryObject del=new DeliveryObject();
			try {
				del.setData(results.getJSONObject(i));
				if(del.store_idx==null || del.store_idx.equals("")){
					
				}else{
					lists.add(del);
				}
				
				
			} catch (JSONException e) {
				
			}
		}
		if(finalPage==0){
			dataA.put(loadTypeCode, lists);
		}
		if(delegateD!=null){delegateD.deliveryDataUpdate(lists);}
	}
   
    
    private void sortListComplete(JSONObject result,int type){
    	JSONArray results = null;
		
    	try {
			results = result.getJSONArray("datalist");
		} catch (JSONException e) {
			sortListLoadEnd(type);
		}
		if( results.length()<1){
			sortListLoadEnd(type);
			return;
		}
		ArrayList<SortObject> lists= new ArrayList<SortObject>();
		if(type==1){
			orderMenuLists=new ArrayList<String>();
			
		}
		for(int i=0;i<results.length();++i){
			SortObject sort=new SortObject();
			try {
				if(type==0){
					sort.setData(results.getJSONObject(i));
				}else{
					sort.setOrderData(results.getJSONObject(i));
				}
				if(!sort.code.equals("")){
					if(type==0){
						if(sort.code.equals(deliveryKEY)){
							finalIndex=i;
						}
					}else{
						if(sort.code.equals(orderKEY)){
							finalOrder=i;
						}
						orderMenuLists.add(sort.name);
					}
					lists.add(sort);
				}
				
			} catch (JSONException e) {
				
			}
		}
		if(type==0){
			Log.i("","SORTLIT SET"+lists.size()+"   "+finalIndex);
			sortLists=lists;
			if(delegate!=null){delegate.deliverySortUpdate();}
		}else{
			Log.i("","ORDERLIT SET"+lists.size()+"   "+finalOrder);
			orderLists=lists;
			if(delegate!=null){delegate.deliveryOrderUpdate();}
			
		}
		
		
	    
	}
    private void sortListLoadEnd(int type)
    {
    	if(type==0){
			
			if(delegate!=null){delegate.deliverySortUpdate();}
		}else{
			
			if(delegate!=null){delegate.deliveryOrderUpdate();}
			
		}
    	
    }
    public void onJsonLoadErr(JsonManager manager,String xml_path) {
		MainActivity.getInstence().viewAlert("", R.string.msg_network_err, null);
		MainActivity.getInstence().loadingStop();
		
		if(loadTypeCode.equals("")){
			if(delegate!=null){delegate.deliverySortUpdate();}
	    }else{
	    	
	    	if(delegateD!=null){delegateD.deliveryDataUpdate(null);}
	    }
		
	} 
    
	public interface DeliveryInfoDelegate
	{
		void deliverySortUpdate();
		void deliveryOrderUpdate();
		
	}
    public interface DeliveryDataDelegate
	{
		
		void deliveryDataUpdate(ArrayList<DeliveryObject> datas);
		
	}
}

