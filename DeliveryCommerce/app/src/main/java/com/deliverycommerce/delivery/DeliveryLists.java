package com.deliverycommerce.delivery;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deliverycommerce.Config;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.delivery.DeliveryInfo.DeliveryDataDelegate;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;

import lib.view.RackableListView;




import android.util.Log;
import android.view.View;
import android.view.ViewGroup;



import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Point;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import android.widget.BaseAdapter;
import android.widget.TextView;


@SuppressLint("NewApi")
public class DeliveryLists extends ViewCore implements OnItemClickListener,DeliveryDataDelegate
{
	
	
	
	private ArrayList<DeliveryList> lists;
    private CustomBaseAdapter adapter;
	private ArrayList<DeliveryObject> dataA;
	private String typecode,position,order,addr1,addr2;
	public RackableListView listView;
	private FrameLayout body;
	
	private String bgColor;
	private int pageIdx;
	private Boolean loadComplete;
    private TextView infoTxt;
	public DeliveryLists(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.delivery_lists,this);  
	    body=(FrameLayout) findViewById(R.id._body);
	    dataA=new ArrayList<DeliveryObject>();
	    pageIdx=0;
	    listView=new RackableListView(context);
	    listView.isHorizontal=false;
        infoTxt=(TextView) findViewById(R.id._infoTxt);
	    int resID = this.getResources().getIdentifier( "transparent", "drawable", mainActivity.getPackageName());
	    listView.setSelector(resID);
	    body.addView(listView,0, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
	    if(pageInfo.info!=null){
	    	typecode=(String)pageInfo.info.get("typecode"); 
	    	position=(String)pageInfo.info.get("position"); 
	    	order=(String)pageInfo.info.get("order"); 
	    	addr1=(String)pageInfo.info.get("addr1"); 
	    	addr2=(String)pageInfo.info.get("addr2"); 
		}else{
			typecode="";
			position="";
			order="";
			addr1="";
	    	addr2="";
		}
	    loadComplete=false;
	    lists=new ArrayList<DeliveryList>();
	    dataA=new ArrayList<DeliveryObject>();
	    adapter=new CustomBaseAdapter();
	    
	    DeliveryInfo.getInstence().delegateD=this;   
	    listView.setOnItemClickListener(this);
	    listView.setAdapter(adapter);
	    listView.setDivider(null);
	    Log.i("","typecode="+typecode+" position="+position+" order="+order+" addr1="+addr1+" addr2="+addr2);

        infoTxt.setText("");
	    DeliveryInfo.getInstence().loadDeliveryDataLists(typecode, position,order,addr1,addr2,pageIdx);
    }
	
	
	
	
	protected void doResize() { 
    	super.doResize();
    	
    }
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	    
	    
	  
	} 
	
	
	
    protected void doRemove() { 
    	
    	super.doRemove();
    	removeLists();
    	
    } 
    private void removeLists(){
    	
    	if( lists!=null){
    		for(int i=0;i< lists.size();++i){
    			 DeliveryList list=lists.get(i);
    			 list.removeList();
    		}
    		lists=null;
    		
    	}
 	   
    } 
    public void deliveryDataUpdate(ArrayList<DeliveryObject> datas){
    	if(datas==null){
    		loadComplete=true;
            if(dataA.size()==0){
                infoTxt.setText(R.string.msg_no_data);
            }
    		return;
    	}
    	if(datas.size()==0){
    		loadComplete=true;
            if(dataA.size()==0){
                infoTxt.setText(R.string.msg_no_data);
            }
    		return;
    	}
    	
    	pageIdx++;
    	dataA.addAll(datas);
		adapter.notifyDataSetChanged();
    }
    public void loadNextData(){
    	pageIdx++;
        infoTxt.setText("");
		DeliveryInfo.getInstence().loadDeliveryDataLists(typecode, position,order,addr1,addr2,pageIdx);
    	
    }
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		
		DeliveryObject data=dataA.get(position);
		Map<String,Object> pinfo=new HashMap<String,Object>();
    	pinfo.put("titleStr", data.storename);
    	pinfo.put("pageUrl", Config.WEB_PAGE_STORE+"?store_idx="+data.store_idx);
		PageObject pInfo=new PageObject(Config.POPUP_WEB_INFO);
		pInfo.dr=1;
		pInfo.info=pinfo;
    	MainActivity.getInstence().addPopup(pInfo);
	}
	
	//delegate
	
	
    
    
   
    
    
	

	private class CustomBaseAdapter extends BaseAdapter 
    {         
    	
    	    
    	      
    	public CustomBaseAdapter()
    	{           
            super();
    	}    
    	public int getCount() 
    	{       
    		return  dataA.size(); 
    	}     
     
    	public DeliveryObject getItem(int position) 
    	{       
    		return dataA.get(position);
    	}  
        public long getItemId(int position) 
    	{        
    		return position;    
    	}   
    	
        
    	public View getView(int position, View convertview, ViewGroup parent) 
    	{      
    		DeliveryList list;
    		if(convertview==null){
    			list=new DeliveryList(mainActivity);
    		}else{
    			list = (DeliveryList)convertview;
    		}
            if(lists.indexOf(list) != -1){
                lists.add(list);
            }
    		DeliveryObject data=getItem(position);
    		list.setData(data,typecode);
    		if(position==(dataA.size()-1) && loadComplete==false){
    			loadNextData();
    			
    		}
    		
			return list;
    	} 
    	
    }
    
}
