package com.aeddang.clipmaker.pagerecord;



import android.content.Context;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.aeddang.clipmaker.DataObject;
import com.aeddang.clipmaker.R;
import com.aeddang.clipmaker.SetupInfo;

import java.util.ArrayList;


import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.RackableGridView;




public class DecoLists extends ViewCore implements OnItemClickListener
{


	
	private ArrayList<DecoList> lists;
    private CustomBaseAdapter adapter;
	private ArrayList<DataObject> dataA;
	public RackableGridView listView;
	private FrameLayout body;
	

	private int pageIdx,size;
	private Boolean loadComplete;
    private TextView infoTxt;

    public DecoListsDelegate delegate;
    public interface DecoListsDelegate
    {
        void selectList(DataObject obj,DecoList list);
    }


	public DecoLists(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.deco_lists,this);
	    body=(FrameLayout) findViewById(R.id._body);
        listView=(RackableGridView) findViewById(R.id._listView);
	    pageIdx=0;


        infoTxt=(TextView) findViewById(R.id._infoTxt);



        int listNum=4;
        size=(int)mainActivity.dpi*80;
        if(pageInfo.info!=null){
            pageIdx=(int)pageInfo.info.get("pageIdx");
            int listSize=(int)pageInfo.info.get("listSize");
            if(listSize>0){
                listNum=(int)Math.floor((double)listSize/size);
            }
            if(listNum<1){
                listNum=1;
            }

            size=(int)Math.floor(listSize/listNum);
            Log.i("","LIST_SIZE : "+listSize+" "+listNum+" "+size);



		}else{
            pageIdx=0;

		}
        listView.isHorizontal=false;
        listView.setVerticalScrollBarEnabled(false);
        listView.setColumnWidth(size);
        listView.setNumColumns(listNum);


       // int resID = this.getResources().getIdentifier( "sel_press", "drawable", mainActivity.getPackageName());
       // listView.setSelector(resID);




        lists=new ArrayList<DecoList>();
        dataA= SetupInfo.getInstence().groupA.get(pageIdx).dataA;
	    if(dataA.size()<1){
            infoTxt.setVisibility(View.VISIBLE);
            infoTxt.setText(R.string.msg_no_data);
        }else{

            infoTxt.setVisibility(View.GONE);
        }
        for (int i = 0; i < dataA.size(); ++i) {

            //dataA.get(i).loadImages();

        }


        adapter=new CustomBaseAdapter();

	    listView.setOnItemClickListener(this);

        listView.setAdapter(adapter);


    }
	
	
	
	
	protected void doResize() { 
    	super.doResize();
    	
    }
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();

        //adapter.notifyDataSetChanged();
	    
	  
	} 
	
	
	
    protected void doRemove() { 
    	
    	super.doRemove();
    	removeLists();
    	if(dataA!=null) {

            for (int i = 0; i < dataA.size(); ++i) {

                //dataA.get(i).clearImages();

            }
            dataA=null;
        }
        delegate=null;
    } 
    private void removeLists(){
    	
    	if( lists!=null){
    		for(int i=0;i< lists.size();++i){
    			 DecoList list=lists.get(i);
    			 list.removeList();
    		}
    		lists=null;
    		
    	}


 	   
    } 

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DecoList list =(DecoList) view;
        list.roadImgs();

	}

	//delegate
	
	
    
    
   
    
    
	

	private class CustomBaseAdapter extends BaseAdapter implements DecoList.DecoListDelegate {

        public  void selectList(DataObject obj,DecoList list){

              if(delegate!=null){


                  delegate.selectList(obj,list);
              }
        }
    	      
    	public CustomBaseAdapter()
    	{           
            super();
    	}    
    	public int getCount() 
    	{       

            return dataA.size();
    	}     
     
    	public DataObject getItem(int position)
        {

            return dataA.get(position);
    	}  
        public long getItemId(int position) 
    	{        
    		return position;    
    	}   
    	
        
    	public View getView(int position, View convertview, ViewGroup parent) 
    	{      
    		DecoList list;
    		if(convertview==null){
    			list=new DecoList(mainActivity,this);
                list.setLayoutParams(new GridView.LayoutParams(size, size));


    		}else{
    			list = (DecoList)convertview;
    		}
            if(lists.indexOf(list) != -1){
                lists.add(list);
            }

    		list.setData(getItem(position));

    		return list;
    	} 
    	
    }
    
}
