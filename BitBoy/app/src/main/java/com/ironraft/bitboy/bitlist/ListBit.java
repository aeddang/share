package com.ironraft.bitboy.bitlist;


import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.bitdata.BitTickerObject;
import lib.CommonUtil;


public class ListBit extends FrameLayout implements View.OnClickListener
{

	protected TextView title,viewTitle,price;
	protected BitTickerObject info;
	protected Button btn;
	public  ListBitDelegate delegate;

    public ListBit(Context context)
	{
		super(context);
		setViewInflate(context);
		title=(TextView) findViewById(R.id._title);
		title.setTypeface(FontFactory.getInstence().FontTypeBold);
		viewTitle=(TextView) findViewById(R.id._viewTitle);
		price=(TextView) findViewById(R.id._price);
		btn=(Button) findViewById(R.id._btn);

		viewTitle.setTypeface(FontFactory.getInstence().FontTypeRegula);
		price.setTypeface(FontFactory.getInstence().FontTypeBold);
		btn.setOnClickListener(this);

		Animation iniAni = AnimationUtils.loadAnimation(MainActivity.getInstence(), R.anim.motion_in);
		this.startAnimation(iniAni);

    }
	protected void setViewInflate(Context context)
	{
		View.inflate(context, R.layout.list_bit_seletable, this);
	}

	public void setData(BitTickerObject _info)
	{
		info=_info;
		updateData();
	}
	public void updateData()
	{
		title.setText(info.seq);
		viewTitle.setText("/"+info.view);
		price.setText(CommonUtil.getPriceStr(info.getViewPrice()));
		doUpdateData();
	}
	protected void doUpdateData(){}

	public void  removeList()
	{
		info = null;
	}
	public void onClick(View arg)
	{
		doClick(arg);

	}

	protected void doClick(View arg)
	{
		if(delegate !=null) delegate.selected(this,info);
		BitDataManager.getInstence().loadBitDatas();
	}

	public interface ListBitDelegate
	{
		void selected(ListBit list, BitTickerObject value);

	}
}
