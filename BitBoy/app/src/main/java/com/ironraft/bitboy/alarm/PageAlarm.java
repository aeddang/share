package com.ironraft.bitboy.alarm;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.TopicManager;
import java.util.ArrayList;
import lib.core.PageObject;
import lib.core.ViewCore;



public class PageAlarm extends ViewCore
{

    private ArrayList<ListAlarm> lists;
    private ArrayList<String> datas;
    private CustomBaseAdapter adapter;
    private ListView listView;


    public PageAlarm(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);

		View.inflate(context, R.layout.page_alarm, this);
        listView=(ListView) findViewById(R.id._lists);

        lists = new ArrayList<ListAlarm>();
        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);
        doUpdate();
    }

    protected void doUpdate()
    {
        super.doUpdate();
        for(int i=0;i< lists.size();++i)
        {
            lists.get(i).updateData();
        }
    }

	protected void doResize() {
    	super.doResize();

    }
    protected void doMovedInit()
    {
	    super.doMovedInit();
	    initList();

	}


    public void initList()
    {
        MainActivity.getInstence().loadingStop();

        datas = new ArrayList<String>();
        datas.add(TopicManager.TOPIC_RISE);
        datas.add(TopicManager.TOPIC_DROP);
        datas.add(TopicManager.TOPIC_DEAL_M);
        datas.add(TopicManager.TOPIC_DEAL_H);
        adapter.notifyDataSetChanged();
    }

    protected void doRemove() {

        super.doRemove();
        removeLists();
    }

    private void removeLists()
    {
        if( lists!=null)
        {
            for(int i=0;i< lists.size();++i)
            {
                ListAlarm list=lists.get(i);
                list.removeList();
            }
            lists=null;
        }


    }

    private class CustomBaseAdapter extends BaseAdapter
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

        public String getItem(int position)
        {
            return datas.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {
            ListAlarm list;
            String type=getItem(position);
            if(convertview instanceof ListAlarm){
                list= (ListAlarm)convertview;
            }else{

                list=new ListAlarm(MainActivity.getInstence());
                lists.add(list);

            }
            list.setType(type);
            return list;
        }
    }


}
