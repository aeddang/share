package com.ironraft.bitboy.bitlist;


import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;


public class ListBitSelected extends ListBit
{
    public ListBitSelected(Context context)
	{
		super(context);
    }
	protected void setViewInflate(Context context)
	{
		View.inflate(context, R.layout.list_bit_selected, this);
	}

	protected void doClick(View arg)
	{
		if(delegate !=null) delegate.selected(this,info);
		BitDataManager.getInstence().loadBitDatas();
	}

}
