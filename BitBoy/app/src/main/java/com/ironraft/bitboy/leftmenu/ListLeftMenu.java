package com.ironraft.bitboy.leftmenu;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import lib.core.LeftMenuActivityCore;
import lib.core.PageObject;

public class ListLeftMenu extends FrameLayout implements View.OnClickListener
{
	private Button btn;
	private TextView text;
	private PageObject info;
    public ListLeftMenu(Context context)
	{
		super(context);
	    View.inflate(context, R.layout.list_left_menu, this);

		btn=(Button) findViewById(R.id._btn);
		text=(TextView) findViewById(R.id._text);
		text.setTypeface(FontFactory.getInstence().FontTypeRegula);


    }

	public void setTitle(int resID, PageObject _info)
	{
		text.setText(resID);
		info = _info;
		btn.setOnClickListener(this);
	}

	public void onClick(View arg)
	{
		LeftMenuActivityCore ac = (LeftMenuActivityCore) LeftMenuActivityCore.getInstence();
		ac.closeLeftMenu();
		ac.removePopup();
		if(info.pageID>=1000)
		{
			MainActivity.getInstence().addSinglePopup(info);
		}
		else
		{
			info.dr = 1;
			MainActivity.getInstence().changeView(info);
		}

	}
}
