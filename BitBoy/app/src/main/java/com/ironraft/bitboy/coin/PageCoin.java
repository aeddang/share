package com.ironraft.bitboy.coin;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.bitgraph.PageBitGraph;
import com.ironraft.bitboy.bitlist.PageBitLists;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;

import java.util.HashMap;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PageCoin extends ViewCore implements PageBitLists.PageBitDelegate
{
    PageBitLists lists;
    //PageBitGraph graphs;

    public PageCoin(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_coin , this);
        FrameLayout listArea =(FrameLayout) findViewById(R.id._listArea);


        lists = new PageBitLists(MainActivity.getInstence().getApplicationContext(),new PageObject(0));
        listArea.addView(lists, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        /*
        FrameLayout graphArea =(FrameLayout) findViewById(R.id._graphArea);
        graphs = new PageBitGraph(MainActivity.getInstence().getApplicationContext(),new PageObject(0));
        graphArea.addView(graphs, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        */
    }

	protected void doResize() {
    	super.doResize();
        lists.resize();
        //graphs.resize();
    }
    protected void doMovedInit()
    {
        super.doMovedInit();

        lists.initCore();
        lists.movedInit();
        lists.delegate = this;
        lists.initCore();
        //graphs.movedInit();
	}

    protected void doRemove() {

        super.doRemove();
        if(lists != null) lists.removeViewCore();
        lists = null;
        /*
        if(graphs != null) graphs.removeViewCore();
        graphs = null;
        */
    }

    public void selectedBitList(BitTickerObject value)
    {
        /*
        if(value == null)
        {
            graphs.setSelectedSeq("");
        }
        else
        {
            graphs.setSelectedSeq(value.seq);
        }
        */
    }


}
