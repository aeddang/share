package com.ironraft.bitboy.leftmenu;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ironraft.bitboy.Config;
import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;


import java.util.ArrayList;
import java.util.Map;

import lib.core.PageObject;


public class LeftMenu extends FrameLayout
{
	private TextView text;
	private ArrayList<ListLeftMenu> lists;

	private LinearLayout listBody;
	public static final int[] MENUS={
			R.string.title_home_eng
	};


	public static final int[] PAGE_IDS={
											Config.PAGE_HOME
										};

	public LeftMenu(Context context)
	{
		super(context);
	    View.inflate(context, R.layout.left_menu, this);
		listBody =(LinearLayout) findViewById(R.id._listBody);
		text=(TextView) findViewById(R.id._text);

    }

	public void init(){

		text.setTypeface(FontFactory.getInstence().FontTypeRegula);
		setMenu();
	}



	private void setMenu()
	{
		lists = new ArrayList<ListLeftMenu>();
		for(int i=0;i<LeftMenu.MENUS.length;++i)
		{
			PageObject data = new PageObject(LeftMenu.PAGE_IDS[i]);

			ListLeftMenu  list = new ListLeftMenu(MainActivity.getInstence());
			listBody.addView(list);
			list.setTitle(LeftMenu.MENUS[i],data);

		}


	}

}
