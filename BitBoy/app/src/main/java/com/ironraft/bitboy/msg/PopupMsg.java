package com.ironraft.bitboy.msg;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ironraft.bitboy.FontFactory;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.AppManager;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import com.ironraft.bitboy.model.msg.MsgObject;

import lib.core.PageObject;
import lib.core.PopupCore;


public class PopupMsg extends PopupCore implements View.OnClickListener
{
    private Button btnClose;
    private TextView desc,title;
    private MsgObject data;
    public PopupMsg(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
        if(pageInfo.info!=null){
            data = (MsgObject)pageInfo.info.get("data");
        }else{
            data = new MsgObject();
        }
        View.inflate(context, R.layout.popup_msg, this);
        body=(FrameLayout)findViewById(R.id._body);
        btnClose=(Button)findViewById(R.id._btnClose);
        desc =(TextView) findViewById(R.id._desc);
        title =(TextView) findViewById(R.id._title);

        desc.setTypeface(FontFactory.getInstence().FontTypeRegula);
        title.setTypeface(FontFactory.getInstence().FontTypeBold);
        btnClose.setOnClickListener(this);

        doUpdate();
    }

    protected void doUpdate()
    {
        super.doUpdate();
        title.setText(data.getTitle());
        desc.setText(data.getMsg());
    }

	protected void doResize() {
    	super.doResize();

    }
    protected void doMovedInit() {
	    super.doMovedInit();
	}
    protected void doRemove() {

        super.doRemove();
    }


    public void onClick(View arg) {

        if(arg == btnClose) MainActivity.getInstence().removePopup(this);

    }
}
