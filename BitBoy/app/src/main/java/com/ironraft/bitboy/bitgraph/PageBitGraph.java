package com.ironraft.bitboy.bitgraph;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.bitlist.ListBit;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.bitdata.BitGraphInfo;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;

import java.util.ArrayList;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.observers.Observer;
import lib.observers.ObserverController;


public class PageBitGraph extends ViewCore implements Observer
{
    public static final String TYPE_ALL = "0";
    public static final String TYPE_SELECTED = "1";

    private FrameLayout graphArea;
    private GraphGraduation graduation;
    private TextView noData;
    private ArrayList<GraphBit> lists;
    private String selectedSeq;
    private String type;

    public PageBitGraph(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_graph, this);
        if(pageInfo.info!=null)
        {
            type = (String)pageInfo.info.get("type");
        }else{
            type = TYPE_SELECTED;
        }
        graphArea = (FrameLayout) findViewById(R.id._graphArea);
        FrameLayout graduationArea =(FrameLayout) findViewById(R.id._graduationArea);
        graduation = new GraphGraduation(context);
        graduationArea.addView(graduation,FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        noData=(TextView) findViewById(R.id._noData);
        noData.setTypeface(FontFactory.getInstence().FontTypeRegula);
        noData.setVisibility(View.GONE);
        doUpdate();
    }
    protected void doUpdate()
    {
        super.doUpdate();
        noData.setText(LanguageFactory.getInstence().getResorceID("msg_no_data"));
    }
    protected void doMovedInit()
    {
	    super.doMovedInit();
	    ObserverController.shareInstence().registerObserver(this, BitDataManager.NotificatioBitDataLoadComplete);
        MainActivity.getInstence().loadingStart(false);
        BitDataManager.getInstence().loadBitDatas();
	}
    public void setSelectedSeq(String _selectedSeq)
    {
        selectedSeq = _selectedSeq;
    }

    public void notification(String notify, Object value, Map<String,Object> userData)
    {
        removeLists();
        lists = new ArrayList<GraphBit>();
        ArrayList<BitGraphInfo> graphs = getGraphs();

        if(graphs.size()<1)
        {
            noData.setVisibility(View.VISIBLE);
        }
        else
        {
            noData.setVisibility(View.GONE);
        }
        for(int i = 0; i<graphs.size();++i)
        {
            BitGraphInfo ginfo = graphs.get(i);
            GraphBit list = new GraphBit(MainActivity.getInstence());
            list.setSetting(ginfo.setting);
            list.setData(ginfo);
            if(selectedSeq == ginfo.setting.seq)
            {
                graphArea.addView(list);
                list.active();
            }
            else
            {
                graphArea.addView(list,0);
                list.passive();
            }
            lists.add(list);
            list.onResize(this.getWidth());
        }
        graduation.setData(BitDataManager.getInstence().getAllBitSets());

    }
    private  ArrayList<BitGraphInfo> getGraphs()
    {
        ArrayList<BitGraphInfo> graphs = null;
        switch (type)
        {
            case TYPE_ALL:
                graphs = BitDataManager.getInstence().getBitTickerGraphs(false);
                break;
            case TYPE_SELECTED:
                graphs = BitDataManager.getInstence().getBitTickerGraphs(true);
                break;
            default:
                graphs = BitDataManager.getInstence().getBitTickerGraphs(type);
                setSelectedSeq(type);
                break;
        }
        return graphs;
    }
    protected void doResize()
    {
        super.doResize();
        if( lists==null) return;
        for(int i=0;i< lists.size();++i) {
            lists.get(i).onResize(this.getWidth());
        }

    }
    protected void doRemove() {

        super.doRemove();
        ObserverController.shareInstence().removeObserver(this);
        removeLists();
    }

    private void removeLists()
    {

        if( lists!=null)
        {
            for(int i=0;i< lists.size();++i){
                GraphBit list=lists.get(i);
                list.removeGraph();
            }
            lists=null;
        }

    }

}
