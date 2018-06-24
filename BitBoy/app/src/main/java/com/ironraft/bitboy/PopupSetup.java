package com.ironraft.bitboy;


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

import com.ironraft.bitboy.model.AppManager;
import com.ironraft.bitboy.model.bitdata.BitDataManager;
import lib.core.PageObject;
import lib.core.PopupCore;


public class PopupSetup extends PopupCore implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private Button btnClose;
    private Switch switchAutoLoad,switchKor,switchEng,switchPush;

    private TextView textVS,textVSNum;

    public PopupSetup(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);


        View.inflate(context, R.layout.popup_setup, this);
        body=(FrameLayout)findViewById(R.id._body);
        btnClose=(Button)findViewById(R.id._btnClose);
        switchAutoLoad=(Switch) findViewById(R.id._switchAutoLoad);
        switchPush=(Switch) findViewById(R.id._switchPush);
        switchKor=(Switch) findViewById(R.id._switchKor);
        switchEng=(Switch) findViewById(R.id._switchEng);


        textVS=(TextView) findViewById(R.id._textVS);
        textVSNum=(TextView) findViewById(R.id._textVSNum);

        switchAutoLoad.setTypeface(FontFactory.getInstence().FontTypeBold);
        switchPush.setTypeface(FontFactory.getInstence().FontTypeBold);
        switchKor.setTypeface(FontFactory.getInstence().FontTypeBold);
        switchEng.setTypeface(FontFactory.getInstence().FontTypeBold);

        textVS.setTypeface(FontFactory.getInstence().FontTypeRegula);
        textVSNum.setTypeface(FontFactory.getInstence().FontTypeRegula);

        switchPush.setChecked(AppManager.getInstence().getPushAble());
        switchAutoLoad.setChecked(BitDataManager.getInstence().getAutoLoad());
        switchPush.setOnCheckedChangeListener(this);
        switchAutoLoad.setOnCheckedChangeListener(this);
        btnClose.setOnClickListener(this);

        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        textVSNum.setText(version);

        doUpdate();
    }

    protected void doUpdate()
    {
        super.doUpdate();
        switchAutoLoad.setText(LanguageFactory.getInstence().getResorceID("text_set_alram"));
        switchPush.setText(LanguageFactory.getInstence().getResorceID("text_set_update"));
    }

	protected void doResize() {
    	super.doResize();

    }
    protected void doMovedInit() {
	    super.doMovedInit();
        onLanguageChanged();


	}
    protected void doRemove() {

        super.doRemove();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == switchAutoLoad) BitDataManager.getInstence().setAutoLoad(isChecked);
        if(buttonView == switchPush) AppManager.getInstence().setPushAble(isChecked);

        if(buttonView == switchEng) LanguageFactory.getInstence().setLanguageType(LanguageFactory.ENG);
        if(buttonView == switchKor) LanguageFactory.getInstence().setLanguageType(LanguageFactory.KOR);
        if(buttonView == switchEng || buttonView == switchKor) onLanguageChanged();
    }

    public void onClick(View arg) {

        if(arg == btnClose) MainActivity.getInstence().removePopup(this);

    }
    private void copyClipBoard()
    {
        ClipboardManager clipboard = (ClipboardManager) MainActivity.getInstence().getSystemService(Context.CLIPBOARD_SERVICE);
        //ClipData clip = ClipData.newPlainText(textDonation.getText(),  textBitID.getText());
       // clipboard.setPrimaryClip(clip);
    }
    private void onLanguageChanged()
    {

        switchEng.setOnCheckedChangeListener(null);
        switchKor.setOnCheckedChangeListener(null);
        switchEng.setClickable(false);
        switchKor.setClickable(false);
        switchEng.setChecked(false);
        switchKor.setChecked(false);

        switch (LanguageFactory.getInstence().getLanguageType())
        {
            case LanguageFactory.ENG:
                switchEng.setChecked(true);
                switchKor.setOnCheckedChangeListener(this);
                switchKor.setClickable(true);
                break;
            case LanguageFactory.KOR:
                switchKor.setChecked(true);
                switchEng.setOnCheckedChangeListener(this);
                switchEng.setClickable(true);


                break;
        }
    }


}
