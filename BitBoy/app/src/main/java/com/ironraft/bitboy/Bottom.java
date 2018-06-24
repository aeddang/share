package com.ironraft.bitboy;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ironraft.bitboy.model.AppManager;

import lib.core.ActivityCore;
import lib.core.PageObject;


public class Bottom extends FrameLayout implements View.OnClickListener
{
	private Button btnMsg;
	private Button btnCoin;
	private Button btnAlarm;
	private ImageView imgMsg,imgCoin,imgAlarm;

	public Bottom(Context context)
	{
		super(context);
	    View.inflate(context, R.layout.bottom, this);
		btnMsg =(Button) findViewById(R.id._btnMsg);
		btnCoin=(Button) findViewById(R.id._btnCoin);
		btnAlarm=(Button) findViewById(R.id._btnAlarm);

		imgMsg =(ImageView) findViewById(R.id._imgMsg);
		imgCoin =(ImageView) findViewById(R.id._imgCoin);
		imgAlarm =(ImageView) findViewById(R.id._imgAlarm);
    }

	public void init()
	{
		btnMsg.setOnClickListener(this);
		btnCoin.setOnClickListener(this);
		btnAlarm.setOnClickListener(this);
	}

	public void onClick(View arg)
	{
		if (arg == btnAlarm) ActivityCore.getInstence().changeView(new PageObject(Config.PAGE_ALARM));
		if (arg == btnCoin) ActivityCore.getInstence().changeView(new PageObject(Config.PAGE_COIN));
		if (arg == btnMsg) ActivityCore.getInstence().changeView(new PageObject(Config.PAGE_MSG));

	}

	public void setSelected(int pageID)
	{
		if(pageID == Config.PAGE_MSG)
		{
			imgMsg.setSelected(true);
		}
		else
		{
			imgMsg.setSelected(false);
		}
		if(pageID == Config.PAGE_COIN)
		{
			imgCoin.setSelected(true);
		}
		else
		{
			imgCoin.setSelected(false);
		}
		if(pageID == Config.PAGE_ALARM)
		{
			imgAlarm.setSelected(true);
		}
		else
		{
			imgAlarm.setSelected(false);
		}
	}

}
