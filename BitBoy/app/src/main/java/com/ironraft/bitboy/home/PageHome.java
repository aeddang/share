package com.ironraft.bitboy.home;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.bitgraph.PageBitGraph;
import com.ironraft.bitboy.bitlist.PageBitLists;
import com.ironraft.bitboy.leftmenu.LeftMenu;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;
import com.ironraft.bitboy.model.msg.MsgManager;
import com.ironraft.bitboy.model.msg.MsgObject;

import java.util.ArrayList;
import java.util.HashMap;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PageHome extends ViewCore implements PageBitLists.PageBitDelegate, MsgManager.MsgDataDelegate
{
    PageBitLists lists;
    PageBitGraph graphs;
    private TextView textMsg;
    private MsgObject msg;

    public PageHome(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_home , this);
        FrameLayout listArea =(FrameLayout) findViewById(R.id._listArea);
        textMsg = (TextView) findViewById(R.id._textMsg);

        PageObject pobj = new PageObject(0);
        pobj.info = new HashMap<String, Object>();
        pobj.info.put("type", PageBitLists.TYPE_SELECTED);
        lists = new PageBitLists(MainActivity.getInstence().getApplicationContext(),pobj);
        listArea.addView(lists, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout graphArea =(FrameLayout) findViewById(R.id._graphArea);
        graphs = new PageBitGraph(MainActivity.getInstence().getApplicationContext(),new PageObject(0));
        graphArea.addView(graphs, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        doUpdate();
    }
    protected void doUpdate()
    {
        super.doUpdate();
        if(msg == null)
        {
            textMsg.setText(LanguageFactory.getInstence().getResorceID("msg_no_data"));
        }
        else
        {
            textMsg.setText(msg.getNotification());
        }
    }
	protected void doResize() {
    	super.doResize();
        lists.resize();
        graphs.resize();
    }
    protected void doMovedInit()
    {
        super.doMovedInit();

        lists.initCore();
        lists.movedInit();
        lists.delegate = this;
        lists.initCore();
        graphs.movedInit();
        MsgManager.getInstence().loadMsgs(this);
	}

    protected void doRemove() {

        super.doRemove();
        MsgManager.getInstence().unloadMsg();
        if(lists != null) lists.removeViewCore();
        lists = null;
        if(graphs != null) graphs.removeViewCore();
        graphs = null;

    }

    public void selectedBitList(BitTickerObject value)
    {
        if(value == null)
        {
            graphs.setSelectedSeq("");
        }
        else
        {
            graphs.setSelectedSeq(value.seq);
        }
    }

    public void onLoadMsg(ArrayList<MsgObject> msgs)
    {
        msg = (msgs.size()>0)? msgs.get(0):null;
        doUpdate();
    }
    public void onLoadMsgError()
    {
        msg = null;
    }


}
