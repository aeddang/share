package com.ironraft.bitboy.alarm;


import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.bitlist.PageBitLists;
import com.ironraft.bitboy.model.TopicManager;



public class ListAlarm extends FrameLayout implements CompoundButton.OnCheckedChangeListener
{

	private TextView desc;
	private ImageView icon;
	private Switch switchBtn;
	private String type;

    public ListAlarm(Context context)
	{
		super(context);
		View.inflate(context, R.layout.list_alarm, this);
		switchBtn=(Switch) findViewById(R.id._switchBtn);
		desc=(TextView) findViewById(R.id._desc);
		desc.setTypeface(FontFactory.getInstence().FontTypeBold);
		icon=(ImageView) findViewById(R.id._icon);

		Animation iniAni = AnimationUtils.loadAnimation(MainActivity.getInstence(), R.anim.motion_in);
		this.startAnimation(iniAni);

    }

	public void setType(String _type)
	{
		type = _type;
		int iconID = MainActivity.getInstence().getResources().getIdentifier( "icon_"+type, "drawable", MainActivity.getInstence().getPackageName());
		icon.setImageResource(iconID);
		switchBtn.setChecked(TopicManager.getInstence().getRegisterTopic(type));
		switchBtn.setOnCheckedChangeListener(this);
		updateData();
	}
	public void updateData()
	{
		String descKey = "text_alarm_"+ type;
		desc.setText(LanguageFactory.getInstence().getResorceID(descKey));
	}

	public void  removeList()
	{

	}


	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		TopicManager.getInstence().setRegisterTopic(type,isChecked);
	}

}
