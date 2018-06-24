package com.deliverycommerce.idel;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.UserInfo;
import com.deliverycommerce.delivery.DeliveryObject;
import com.deliverycommerce.delivery.SortObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.jsonmanager.JsonManager;
import lib.jsonmanager.JsonManager.JsonManagerDelegate;
import lib.view.RackableListView;


@SuppressLint("NewApi")
public class IdelLists extends ViewCore implements OnItemClickListener,JsonManagerDelegate

{


    private JsonManager jsonManager;
	private ArrayList<IdelList> lists;
    private CustomBaseAdapter adapter;
	private ArrayList<IdelObject> dataA;
	public RackableListView listView;
	private FrameLayout body;
    private String order,addr;
	private String bgColor;
    private ArrayList<JsonManager>addManager;
    private ArrayList< IdelObject>idelManager;
    public Boolean isLive;
	private Boolean loadComplete;
    private TextView infoTxt;
	public IdelLists(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.idel_lists,this);
	    body=(FrameLayout) findViewById(R.id._body);
	    dataA=new ArrayList<IdelObject>();
	    listView=new RackableListView(context);
	    listView.isHorizontal=false;
        infoTxt=(TextView) findViewById(R.id._infoTxt);
	    int resID = this.getResources().getIdentifier( "transparent", "drawable", mainActivity.getPackageName());
	    listView.setSelector(resID);
	    body.addView(listView,0, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));

        addManager=new ArrayList<JsonManager>();
        idelManager=new ArrayList<IdelObject>();
        jsonManager=new JsonManager(this);
	    loadComplete=false;
	    lists=new ArrayList<IdelList>();
	    dataA=new ArrayList<IdelObject>();
	    adapter=new CustomBaseAdapter();

	    listView.setOnItemClickListener(this);
	    listView.setAdapter(adapter);
	    listView.setDivider(null);

        isLive=true;
        infoTxt.setText("");
    }
	
	
	
	
	protected void doResize() { 
    	super.doResize();
    	
    }
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	    
	    
	  
	} 
	
	
	
    protected void doRemove() { 
    	
    	super.doRemove();
        if(jsonManager!=null){
            jsonManager.destory();
            jsonManager=null;

        }
    	removeLists();
        removeManagers();
    } 
    private void removeLists(){
    	
    	if( lists!=null){
    		for(int i=0;i< lists.size();++i){
                IdelList list=lists.get(i);
    			 list.removeList();
    		}
    		lists=null;
    		
    	}



    }
    private void removeManagers(){


        if(addManager!=null){
            for(int i=0;i< addManager.size();++i){
                JsonManager jm=addManager.get(i);
                jm.destory();
            }
            addManager=null;

        }

    }
    public void setLiveType(){
        removeLists();
        lists=new ArrayList<IdelList>();
        dataA=new ArrayList<IdelObject>();
        adapter.notifyDataSetChanged();
        isLive=true;
        infoTxt.setText("scan...");
    }
    public void addData(IdelObject data){


        for(int i=0;i< dataA.size();++i){
            IdelObject bc=dataA.get(i);
            if(data.code.equals(bc.code)==true){

                return;
            }
        }


        JsonManager jManager=new JsonManager(this);
        Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("apuid", UserInfo.getInstence().userID);

        paramA.put("lot", LocationInfo.getInstence().getLongitude()+"");
        paramA.put("lat", LocationInfo.getInstence().getLatitude()+"");
        paramA.put("bid", data.code);

        idelManager.add(data);
        addManager.add(jManager);
        jManager.loadData(Config.API_LOAD_BEACON_INFO,paramA);

        //dataA.add(data);
        //adapter.notifyDataSetChanged();
       // infoTxt.setText("");
    }
    public void addDataComplete(){
        infoTxt.setText("");
        adapter.notifyDataSetChanged();

    }
    public void setLoadType(ArrayList<String> areas, String _order)
    {
        removeLists();
        removeManagers();
        lists=new ArrayList<IdelList>();
        dataA=new ArrayList<IdelObject>();
        adapter.notifyDataSetChanged();
        isLive=false;

        if(areas.size()>=2) {
            addr="";
            for(int i=0;i<areas.size();++i){
                String area=areas.get(i);
                String[] areaA=area.split("\\|");
                if(areaA.length==2) {
                    String area0 = areaA[0];
                    String area1 = areaA[1];

                    area0 = LocationInfo.getInstence().getAdressCodeByString(area0);
                    area0 = LocationInfo.getInstence().getAreaKey(area0);

                    if(i==0){
                        addr=area0;
                    }else{
                        addr=addr+"_"+area0;

                    }
                    addr=addr+"|"+area1;
                }


            }

        }
        loadData();
    }


    public void loadNextData(){

        loadData();
    }
    private void loadData(){

        Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("apuid", UserInfo.getInstence().userID);
        paramA.put("lot", LocationInfo.getInstence().getLongitude()+"");
        paramA.put("lat", LocationInfo.getInstence().getLatitude()+"");
        try {
            paramA.put("addr", URLEncoder.encode(addr, "UTF-8"));

        } catch (UnsupportedEncodingException e) {

        }

        infoTxt.setText("");
        MainActivity.getInstence().loadingStart(false);
        jsonManager.loadData(Config.API_LOAD_BEACONS_INFO,paramA);

    }

    public void idelDataUpdate(ArrayList<IdelObject> datas){
        if(datas==null){
            loadComplete=true;
            return;
        }
        if(datas.size()==0) {
            loadComplete = true;
            return;
        }
        dataA.addAll(datas);
        adapter.notifyDataSetChanged();
    }
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        IdelObject data=dataA.get(position);
        Map<String,Object> pinfo=new HashMap<String,Object>();
        pinfo.put("titleStr", data.storename);
        pinfo.put("pageUrl", Config.WEB_PAGE_STORE+"?store_idx="+data.store_idx);
        PageObject pInfo=new PageObject(Config.POPUP_WEB_INFO);
        pInfo.dr=1;
        pInfo.info=pinfo;
        MainActivity.getInstence().addPopup(pInfo);
	}
	
	//delegate



    public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
    {
        MainActivity.getInstence().loadingStop();

        if(xml_path.equals(Config.API_LOAD_BEACONS_INFO)==true){

            onBeaconsCompleted(result);
        }else{
            int idx=addManager.indexOf(manager);
            if(idx!=-1){

                onBeaconCompleted(idx, result);
            }



        }


    }
    private void onBeaconCompleted(int idx,JSONObject result)
    {

        IdelObject idel=idelManager.get(idx);

        idelManager.remove(idx);
        addManager.remove(idx);
        if(idel==null){

            return;
        }
        JSONArray results = null;

        try {
            results = result.getJSONArray("datalist");
        } catch (JSONException e) {
            return;
        }
        if( results.length()<1){

            return;
        }
        JSONObject data=null;
        try {
            data = results.getJSONObject(0);
            idel.setData(data);
            dataA.add(idel);
            adapter.notifyDataSetChanged();
            infoTxt.setText("");
        } catch (JSONException e) {

            return;
        }


    }
    private void onBeaconsCompleted(JSONObject result)
    {
        JSONArray results = null;

        try {
            results = result.getJSONArray("datalist");
        } catch (JSONException e) {
            if(dataA.size()==0){
                infoTxt.setText(R.string.msg_no_data);
            }
            return;
        }

        if( results.length()<1){
            if(dataA.size()==0){
                infoTxt.setText(R.string.msg_no_data);
            }
            return;
        }
        ArrayList<IdelObject> lists=new ArrayList<IdelObject>();
        for(int i=0;i<results.length();++i){
            IdelObject del=new IdelObject();
            try {
                del.setData(results.getJSONObject(i));

            } catch (JSONException e) {

            }

            lists.add(del);
        }
        idelDataUpdate(lists);
    }

    public void onJsonLoadErr(JsonManager manager,String xml_path) {
        MainActivity.getInstence().viewAlert("", R.string.msg_network_err, null);
        MainActivity.getInstence().loadingStop();


    }




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
     
    	public IdelObject getItem(int position)
    	{       
    		return dataA.get(position);
    	}  
        public long getItemId(int position) 
    	{        
    		return position;    
    	}   
    	
        
    	public View getView(int position, View convertview, ViewGroup parent) 
    	{
            IdelList list;
    		if(convertview==null){
    			list=new IdelList(mainActivity);

    		}else{
    			list = (IdelList)convertview;
    		}
            if(lists.indexOf(list) != -1){
                lists.add(list);
            }
            IdelObject data=getItem(position);
    		list.setData(data);
    		if(position==(dataA.size()-1) && loadComplete==false && isLive==false){
    			//loadNextData();
    			
    		}
    		
			return list;
    	} 
    	
    }
    
}
