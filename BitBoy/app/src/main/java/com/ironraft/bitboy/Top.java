package com.ironraft.bitboy;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import lib.core.ActivityCore;
import lib.core.PageObject;


public class Top extends FrameLayout implements View.OnClickListener
{
	private Button btnHome;
	private Button btnSetup;



	public Top(Context context)
	{
		super(context);
	    View.inflate(context, R.layout.top, this);
		btnHome =(Button) findViewById(R.id._btnHome);
		btnSetup=(Button) findViewById(R.id._btnSetup);
    }

	public void init()
	{
		btnHome.setOnClickListener(this);
		btnSetup.setOnClickListener(this);
	}

	public void onClick(View arg)
	{
		if (arg == btnHome) ActivityCore.getInstence().changeView(new PageObject(Config.PAGE_HOME));
		if (arg == btnSetup) ActivityCore.getInstence().addSinglePopup(new PageObject(Config.POPUP_SETUP));
	}



}
