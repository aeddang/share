package com.ironraft.bitboy.bitlist;


import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ironraft.bitboy.Config;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.bitdata.BitDataManager;

import java.util.HashMap;

import lib.core.PageObject;


public class ListBitSelectAble extends ListBit implements CompoundButton.OnCheckedChangeListener
{
	private Switch switchBtn;

    public ListBitSelectAble(Context context)
	{
		super(context);
		switchBtn=(Switch) findViewById(R.id._switchBtn);


    }

	protected void setViewInflate(Context context)
	{
		View.inflate(context, R.layout.list_bit_seletable, this);
	}

	protected void doUpdateData()
	{
		switchBtn.setOnCheckedChangeListener(null);
		switchBtn.setChecked(info.isSelected);
		switchBtn.setOnCheckedChangeListener(this);
	}

	protected void doClick(View arg)
	{
		PageObject pobj = new PageObject(Config.POPUP_COIN_INFO);
		pobj.info = new HashMap<String, Object>();
		pobj.info.put("data", this.info);
		MainActivity.getInstence().addSinglePopup(pobj);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    	info.isSelected = isChecked;
    	if(isChecked==false) setSelected(false);
		BitDataManager.getInstence().setSelect(info.seq,info.isSelected);
	}

}
