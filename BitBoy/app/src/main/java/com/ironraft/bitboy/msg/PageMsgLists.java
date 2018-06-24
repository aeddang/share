package com.ironraft.bitboy.msg;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.bitlist.ListBit;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.bitdata.BitSet;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;
import com.ironraft.bitboy.model.msg.MsgManager;
import com.ironraft.bitboy.model.msg.MsgObject;

import java.util.ArrayList;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.observers.Observer;
import lib.observers.ObserverController;


public class PageMsgLists extends ViewCore implements MsgManager.MsgDataDelegate
{

    private TextView noData;
    private ArrayList<ListMsg> lists;
    private ArrayList<MsgObject> datas;
    private CustomBaseAdapter adapter;
    private ListView listView;

    public PageMsgLists(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
		View.inflate(context, R.layout.page_msg, this);
        listView=(ListView) findViewById(R.id._lists);
        noData=(TextView) findViewById(R.id._noData);
        noData.setTypeface(FontFactory.getInstence().FontTypeRegula);
        lists = new ArrayList<ListMsg>();
        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);
        noData.setVisibility(View.GONE);
        doUpdate();
    }
    protected void doUpdate()
    {
        super.doUpdate();
        noData.setText(LanguageFactory.getInstence().getResorceID("msg_no_data"));
        if( lists!=null)
        {
            for(int i=0;i< lists.size();++i)
            {
                lists.get(i).updateData();
            }

        }

    }
	protected void doResize() {
    	super.doResize();

    }
    protected void doMovedInit()
    {
	    super.doMovedInit();
	    MainActivity.getInstence().loadingStart(false);
        MsgManager.getInstence().loadMsgs(this);
	}

    public void onLoadMsg(ArrayList<MsgObject> msgs)
    {
        MainActivity.getInstence().loadingStop();
        datas = msgs;
        adapter.notifyDataSetChanged();
    }
    public void onLoadMsgError()
    {
        MainActivity.getInstence().loadingStop();
        noData.setVisibility(View.VISIBLE);
    }

    protected void doRemove() {

        super.doRemove();
        MsgManager.getInstence().unloadMsg();
        removeLists();
    }

    private void removeLists()
    {
        if( lists!=null)
        {
            for(int i=0;i< lists.size();++i)
            {
                ListMsg list=lists.get(i);
                list.removeList();
            }
            lists=null;
        }


    }



    private class CustomBaseAdapter extends BaseAdapter implements ListMsg.ListMsgDelegate
    {

        public CustomBaseAdapter()
        {
            super();
        }
        public int getCount()
        {
            if(datas==null){
                return 0;
            }

            return datas.size();
        }

        public MsgObject getItem(int position)
        {

            return datas.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {
            ListMsg list;
            MsgObject data=getItem(position);
            if(convertview instanceof ListBit)
            {
                list= (ListMsg)convertview;
            }
            else
            {

                list=new ListMsg(MainActivity.getInstence());
                list.delegate = this;
                lists.add(list);

            }
            list.setData(data);
            return list;
        }

        public void selected(ListMsg list, MsgObject value)
        {

        }
    }


}
