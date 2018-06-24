package com.ironraft.bitboy.bitlist;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.bitdata.BitSet;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import java.util.ArrayList;
import java.util.Map;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.observers.Observer;
import lib.observers.ObserverController;


public class PageBitLists extends ViewCore implements Observer
{
    public static final String TYPE_ALL = "0";
    public static final String TYPE_SELECTED = "1";


    private TextView noData;
    private ArrayList<ListBit> lists;
    private ArrayList<BitTickerObject> datas;
    private CustomBaseAdapter adapter;
    private ListView listView;
    public  PageBitDelegate delegate;
    private ListBit selectedList;
    private String type;
    public PageBitLists(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
        if(pageInfo.info!=null){
            type = (String)pageInfo.info.get("type");
        }else{
            type = TYPE_ALL;
        }

		View.inflate(context, R.layout.page_lists, this);
        listView=(ListView) findViewById(R.id._lists);
        noData=(TextView) findViewById(R.id._noData);
        noData.setTypeface(FontFactory.getInstence().FontTypeRegula);
        lists = new ArrayList<ListBit>();
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

    }

	protected void doResize() {
    	super.doResize();

    }
    protected void doMovedInit()
    {
	    super.doMovedInit();
	    ObserverController.shareInstence().registerObserver(this, BitDataManager.NotificatioBitDataLoadComplete);
        MainActivity.getInstence().loadingStart(false);
        BitDataManager.getInstence().loadBitDatas();
	}

    public void notification(String notify, Object value, Map<String,Object> userData)
    {
        MainActivity.getInstence().loadingStop();
        if(datas == null)
        {
            initList();
        }
        else
        {
            updateList();
        }
    }
    public void initList()
    {
        MainActivity.getInstence().loadingStop();

        datas = (type == TYPE_SELECTED) ? BitDataManager.getInstence().getCurrentBitLists(true)
                    :BitDataManager.getInstence().getCurrentBitLists(false);

        if(datas.size()<=0)
        {
            noData.setVisibility(View.VISIBLE);
        }
        else
        {
            noData.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }
    public void updateList()
    {
        BitSet set = BitDataManager.getInstence().getCurrentBitSet();
        for(int i =0; i<datas.size();++i)
        {
            BitTickerObject data = datas.get(i);
            BitTickerObject updateData = set.getBitTicker(data.seq);
            if(updateData != null) data.updateData(updateData);
        }
        if( lists==null) return;
        for(int i=0;i< lists.size();++i)
        {
            lists.get(i).updateData();
        }
    }

    protected void doRemove() {

        super.doRemove();
        ObserverController.shareInstence().removeObserver(this);
        removeLists();
    }

    private void removeLists()
    {
        selectedList = null;
        if( lists!=null)
        {
            for(int i=0;i< lists.size();++i)
            {
                ListBit list=lists.get(i);
                list.removeList();
            }
            lists=null;
        }


    }

    private class CustomBaseAdapter extends BaseAdapter implements ListBit.ListBitDelegate
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

        public BitTickerObject getItem(int position)
        {

            return datas.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {
            ListBit list;
            BitTickerObject data=getItem(position);

            switch(type)
            {
                case TYPE_SELECTED:
                    list= (convertview instanceof ListBitSelected)
                            ? (ListBitSelected)convertview
                            : new ListBitSelected(MainActivity.getInstence());
                    break;
                default:
                    list= (convertview instanceof ListBitSelectAble)
                            ? (ListBitSelectAble)convertview
                            : new ListBitSelectAble(MainActivity.getInstence());
                    break;
            }
            list.delegate = this;
            if(lists.indexOf(list) == -1) lists.add(list);
            list.setData(data);
            return list;
        }

        public void selected(ListBit list, BitTickerObject value)
        {

            if(selectedList!=null) selectedList.setSelected(false);
            if(selectedList == list)
            {
                value = null;
                selectedList=null;
            }
            else
            {
                selectedList = list;
                selectedList.setSelected(true);
            }
            if(delegate !=null) delegate.selectedBitList(value);

        }
    }



    public interface PageBitDelegate
    {
        void selectedBitList(BitTickerObject value);

    }

}
