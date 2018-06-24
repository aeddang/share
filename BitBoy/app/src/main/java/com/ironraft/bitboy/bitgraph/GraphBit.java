package com.ironraft.bitboy.bitgraph;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.bitdata.BitGraphInfo;
import com.ironraft.bitboy.model.bitdata.BitGraphObject;
import com.ironraft.bitboy.model.bitdata.BitObject;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;

import java.util.ArrayList;

import lib.CommonUtil;
import lib.ViewUtil;


public class GraphBit extends FrameLayout
{

	private TextView top,bottom;

	private GraphLine graph;
	private BitGraphInfo info;
    public GraphBit(Context context)
	{
		super(context);
	    View.inflate(context, R.layout.graph_bit, this);
		top=(TextView) findViewById(R.id._top);
		bottom=(TextView) findViewById(R.id._bottom);
		top.setTypeface(FontFactory.getInstence().FontTypeThin);
		bottom.setTypeface(FontFactory.getInstence().FontTypeThin);

		FrameLayout graphArea =(FrameLayout) findViewById(R.id._graphArea);
		graph = new GraphLine(context);
		graphArea.addView(graph,FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);



    }
	public void setSetting(BitObject set)
	{
		graph.setSetting(set);
		top.setTextColor(Color.parseColor(set.color));
		bottom.setTextColor(Color.parseColor(set.color));
	}
	public void setData(BitGraphInfo _info)
	{
		info = _info;
		if(info.graphs.size()<2)
		{
			top.setVisibility(View.GONE);
			bottom.setVisibility(View.GONE);
			return;
		}
		top.setText(CommonUtil.getPriceStr(info.top));
		bottom.setText(CommonUtil.getPriceStr(info.bottom));
		graph.setData(info);

	}
	public void onResize(int wid)
	{
		if(info.graphs.size()<2) return;
		int len = info.graphs.size()-1;
		LayoutParams layout=(LayoutParams)top.getLayoutParams();
		int boxW = (int)Math.floor(layout.width);
		int tx = wid * info.topIdx/len - (boxW/2);
		int bx = wid * info.bottomIdx/len - (boxW/2);
		tx = (tx<0)? 0 : tx;
		bx = (bx<0)? 0 : bx;
		tx = (tx+boxW>wid)? wid-boxW : tx;
		bx = (bx+boxW>wid)? wid-boxW : bx;
		ViewUtil.moveFrame(top,tx,-1);
		ViewUtil.moveFrame(bottom,bx,-1);
	}
	public void active()
	{
		if(info.graphs.size()>=2)
		{
			top.setVisibility(View.VISIBLE);
			bottom.setVisibility(View.VISIBLE);
			return;
		}
		this.setAlpha(1.f);
	}

	public void passive()
	{
		this.setAlpha(0.2f);
		top.setVisibility(View.GONE);
		bottom.setVisibility(View.GONE);
	}


	public void  removeGraph()
	{
		graph.removeLine();
		info = null;
		ViewUtil.remove(this);
	}

}
