package com.ironraft.bitboy.coin;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.bitgraph.PageBitGraph;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;

import java.util.HashMap;

import lib.core.PageObject;
import lib.core.PopupCore;


public class PopupCoinInfo extends PopupCore implements View.OnClickListener
{
    private Button btnClose;
    private TextView desc,title;
    private BitTickerObject data;
    PageBitGraph graphs;
    public PopupCoinInfo(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
        if(pageInfo.info!=null){
            data = (BitTickerObject)pageInfo.info.get("data");
        }else{
            data = new BitTickerObject("error","error");
        }
        View.inflate(context, R.layout.popup_coin_info, this);
        body=(FrameLayout)findViewById(R.id._body);
        btnClose=(Button)findViewById(R.id._btnClose);
        desc =(TextView) findViewById(R.id._desc);
        title =(TextView) findViewById(R.id._title);

        desc.setTypeface(FontFactory.getInstence().FontTypeRegula);
        title.setTypeface(FontFactory.getInstence().FontTypeBold);
        btnClose.setOnClickListener(this);

        PageObject pobj = new PageObject(0);
        pobj.info = new HashMap<String, Object>();
        pobj.info.put("type", data.seq);
        FrameLayout graphArea =(FrameLayout) findViewById(R.id._graphArea);
        graphs = new PageBitGraph(context,pobj);
        graphArea.addView(graphs, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        doUpdate();
    }

    protected void doUpdate()
    {
        super.doUpdate();
        title.setText(data.getTitle());
        desc.setText(data.getDesc());
    }

	protected void doResize() {
    	super.doResize();
        graphs.resize();

    }
    protected void doMovedInit() {
        super.doMovedInit();
        graphs.movedInit();
	}
    protected void doRemove() {

        super.doRemove();
        if(graphs != null) graphs.removeViewCore();
        graphs = null;
    }


    public void onClick(View arg) {

        if(arg == btnClose) MainActivity.getInstence().removePopup(this);

    }
}
