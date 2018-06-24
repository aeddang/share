package com.deliverycommerce.searchmap;





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
import com.deliverycommerce.delivery.DeliveryObject;
import com.deliverycommerce.delivery.SortObject;
import com.deliverycommerce.searchmap.PageSearchMap.MapDelegate;

import lib.ViewUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;
import lib.view.RackableScrollView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.annotation.SuppressLint;

import android.content.Context;


import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;








@SuppressLint("NewApi")
public class PageSearchPost extends ViewCore  implements OnClickListener,OnKeyListener,JsonManagerDelegate{
	
	

	private Button searchBtn;
	private TextView currentPosition,infoTxt;
	private EditText searchText;
	private LinearLayout lists;
	
	private ArrayList<DongList> selectA;
	
	public RackableScrollView scrollView;
	public PostDelegate delegate;
	private JsonManager jsonManager;
	public PageSearchPost(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.tab_select_area0,this);  
	    lists=(LinearLayout) findViewById(R.id._lists);
	    scrollView=(RackableScrollView) findViewById(R.id._scrollView);
	    searchBtn=(Button) findViewById(R.id._searchBtn);
	    searchText=(EditText) findViewById(R.id._searchText);
	    infoTxt=(TextView) findViewById(R.id._infoTxt);
	    currentPosition=(TextView) findViewById(R.id._currentPosition);
	    scrollView.isHorizontal=false;
	     
	    searchBtn.setOnClickListener(this);
	    searchText.setOnKeyListener(this);
	    
	    
	    currentPosition.setText(LocationInfo.getInstence().getFullAdress());
	    jsonManager=new JsonManager(this);
	    
    }
	
	 private void hideKeyBoard()
		{
	    	mainActivity.hideKeyBoard();
		}
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	    
	   
	} 
	
    protected void doRemove() { 
    	
    	super.doRemove();
    	removeLists();
        if(jsonManager!=null){
        	jsonManager.destory();	
        	jsonManager=null;
        }
    
    } 
    
   
    public boolean onKey(View v, int keyCode, KeyEvent event) {
		
    	
   	 if(event.getAction() == KeyEvent.ACTION_UP) {
            switch(keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                   loadPostLists();
                   return true;
             }
       }
     	return false;
    }
	
	 public void onClick(View v) {
		
			 
		if(v==searchBtn){
			
			loadPostLists();
			return;
		}
		int idx=selectA.indexOf(v);
		if(idx!=-1){
		
			DongList list=(DongList)v;
			ArrayList<String> areas=new ArrayList<String>();
            areas.add(list.info.addr1);
            areas.add(list.info.addr2);
            areas.add(list.info.addr3);
			if(delegate!=null){delegate.postSelected(areas);}
		}
		
		
	} 
	 
	private void removeLists() 
	{
		if(selectA==null){
			return;
		}	
		for(int i=0;i<selectA.size() ;++i){
			selectA.get(i).setOnClickListener(null);
			ViewUtil.remove (selectA.get(i));
	    }	
		selectA.clear();
		selectA=null;
	}
    protected void doSendCommand(String command){
        searchText.setText(LocationInfo.getInstence().getAdress(2));
        loadPostLists();
    }
	 
	 public void loadPostLists(){
	    	
	        if(searchText.getText().toString().equals("")){
	        	mainActivity.viewAlert("", R.string.msg_nosearch_keyword, null);
	        	return;
	        }	
	        hideKeyBoard();
	        removeLists();
	    	MainActivity.getInstence().loadingStart(true);

            Map<String,String> paramA=new HashMap<String,String>();

            try {
                paramA.put("searchtxt", URLEncoder.encode(searchText.getText().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {

            }
	    	jsonManager.loadData(Config.API_POST_SEARCH_LIST,paramA);
	        
	    }
	    
	    public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
	    { 
		    MainActivity.getInstence().loadingStop();
		    if(xml_path.equals(Config.API_POST_SEARCH_LIST)){
	 	    	postListComplete(result);
	 	    }
		} 
	    
	  
	    
	    private void postListComplete(JSONObject result){
	    	JSONArray results = null;
			
	    	try {
				results = result.getJSONArray("datalist");
			} catch (JSONException e) {
				noDataComplete();
				return;
			}
			if( results.length()<1){
				noDataComplete();
				return;
			}
			 selectA=new ArrayList<DongList>();
			for(int i=0;i<results.length();++i){
				DongObject data=new DongObject();
				try{
					data.setData(results.getJSONObject(i));
				}catch(JSONException e){
					
				}
				
				DongList list=new DongList(mainActivity,data);
				list.setOnClickListener(this);
			    lists.addView(list);
			    selectA.add(list);
			}
			infoTxt.setText(R.string.msg_search_success);		
	 }
     public void noDataComplete() { 
			
			infoTxt.setText(R.string.msg_search_nodata);		 
	 }   
	 public void onJsonLoadErr(JsonManager manager,String xml_path) {
		MainActivity.getInstence().viewAlert("", R.string.msg_network_err, null);
		MainActivity.getInstence().loadingStop();
		infoTxt.setText(R.string.msg_network_err);		 
	 }
	 public interface PostDelegate
	 {
			void postSelected(ArrayList<String> areas);
			
	 }
    
     
    
}
