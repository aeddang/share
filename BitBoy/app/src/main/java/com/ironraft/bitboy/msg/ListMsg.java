package com.ironraft.bitboy.msg;


import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ironraft.bitboy.Config;
import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.msg.MsgObject;

import java.util.HashMap;

import lib.core.PageObject;


public class ListMsg extends FrameLayout implements View.OnClickListener
{

	private TextView title;
	private MsgObject info;
	private Button btn;
	public  ListMsgDelegate delegate;

    public ListMsg(Context context)
	{
		super(context);
		View.inflate(context, R.layout.list_msg, this);

		title=(TextView) findViewById(R.id._title);
		btn=(Button) findViewById(R.id._btn);

		title.setTypeface(FontFactory.getInstence().FontTypeRegula);

		btn.setOnClickListener(this);

		Animation iniAni = AnimationUtils.loadAnimation(MainActivity.getInstence(), R.anim.motion_in);
		this.startAnimation(iniAni);

    }

	public void setData(MsgObject _info)
	{
		info=_info;
		updateData();
	}
	public void updateData()
	{
		title.setText(info.getTitle()+" "+info.getNotification());
	}
	public void  removeList()
	{
		info = null;
		//ViewUtil.remove(this);

	}
	public void onClick(View arg)
	{
		if(delegate !=null) delegate.selected(this,info);

		PageObject pobj = new PageObject(Config.POPUP_MSG);
		pobj.info = new HashMap<String, Object>();
		pobj.info.put("data", this.info);
		MainActivity.getInstence().addSinglePopup(pobj);

	}

	public interface ListMsgDelegate
	{
		void selected(ListMsg list, MsgObject value);

	}
}
