package com.ironraft.bitboy.bitgraph;


import android.content.Context;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.bitdata.BitGraphInfo;
import com.ironraft.bitboy.model.bitdata.BitSet;


import java.util.ArrayList;

import lib.ViewUtil;


public class GraphGraduation extends FrameLayout
{

	private ArrayList<TextView> views;

    public GraphGraduation(Context context)
	{
		super(context);
		View.inflate(context, R.layout.graph_graduation, this);

		views = new ArrayList<TextView>();
		for(int i=0;i<5;++i)
		{
			int rid = this.getResources().getIdentifier( "_view"+i, "id", MainActivity.getInstence().getPackageName());
			TextView view = (TextView) findViewById(rid);
			views.add(view);
		}
    }

	public void setData(ArrayList<BitSet> sets)
	{
		int size = views.size();
		int range = (int)Math.round((sets.size()-1)/(size-1));

		for(int i=0;i<size;++i)
		{
			if(i == (size - 1))
			{
				views.get(0).setText(sets.get(sets.size()-1).getBitTime());
			}
			else
			{
				views.get((size-1)-i).setText(sets.get(range*i).getBitTime());
			}
		}

	}




}
