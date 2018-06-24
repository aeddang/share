package com.ironraft.bitboy.bitgraph;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.bitdata.BitGraphInfo;
import com.ironraft.bitboy.model.bitdata.BitGraphObject;
import com.ironraft.bitboy.model.bitdata.BitObject;

import lib.CommonUtil;
import lib.ViewUtil;


public class GraphLine extends View
{

	private Paint paint;
	private BitGraphInfo info;
    public GraphLine(Context context)
	{
		super(context);

    }
	public void setSetting(BitObject set)
	{
		paint = new Paint();

		paint.setColor(Color.parseColor(set.color));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2 * MainActivity.getInstence().dpi);
		paint.setStrokeCap(Paint.Cap.ROUND);
	}
	public void setData(BitGraphInfo _info)
	{
		info = _info;
		invalidate();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);


		if(paint != null)
		{
			int wid = canvas.getWidth();
			int hei = canvas.getHeight();

			float w = wid/(info.graphs.size()-1);

			float pX = -1;
			float pY = -1;
			long range = info.top - info.bottom;
			if(range<=0) range = 1;

			//Log.i("GraphBit","range :"+range);
			for(int i = 0;i<info.graphs.size();++i)
			{
				BitGraphObject graph = info.graphs.get(i);
				float x = i * w;
				long gep = graph.point-info.bottom;
				float h = (float)((long)hei * gep/range);
				float y = (float)hei - h;
				if(pX != -1)
				{
					canvas.drawLine(pX,pY,x,y,paint);
				}

				pX = x;
				pY = y;
			}
			//canvas.restore();


		}

	}

	public void  removeLine()
	{
		ViewUtil.remove(this);
		info = null;
	}

}
