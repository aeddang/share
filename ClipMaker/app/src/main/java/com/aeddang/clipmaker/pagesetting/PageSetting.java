package com.aeddang.clipmaker.pagesetting;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aeddang.clipmaker.Config;
import com.aeddang.clipmaker.MainActivity;
import com.aeddang.clipmaker.R;
import com.aeddang.clipmaker.SetupInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import lib.CommonUtil;
import lib.CustomTimer;
import lib.GraphicUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.gifencoder.AnimatedGifEncoder;
import lib.view.SlideBox;
import lib.view.SlideBox.SlideBoxDelegate;


@SuppressLint("NewApi")
public class PageSetting extends ViewCore implements
        View.OnClickListener ,SeekBar.OnSeekBarChangeListener {




    private ImageButton backBtn;

    private SeekBar resoultionBar,fpsBar;
    private TextView resoultionTxt,fpsTxt;
    private final int resoultionProgress=50,fpsProgress=5;
    private final int resoultionRto=10;

    public PageSetting(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_setting,this);
        resoultionBar=(SeekBar) findViewById(R.id._resoultionBar);
        fpsBar=(SeekBar) findViewById(R.id._fpsBar);
        backBtn=(ImageButton) findViewById(R.id._backBtn);

        resoultionTxt=(TextView) findViewById(R.id._resoultionTxt);
        fpsTxt=(TextView) findViewById(R.id._fpsTxt);
        resoultionBar.setMax(resoultionProgress);
        fpsBar.setMax(fpsProgress);

        fpsBar.setProgress(getFpsProgress(SetupInfo.getInstence().getFR()));
        resoultionBar.setProgress(getResoultionProgress(SetupInfo.getInstence().getResoultion()));

        setResoultionTxt();
        setFpsTxt();
        resoultionBar.setOnSeekBarChangeListener(this);
        fpsBar.setOnSeekBarChangeListener(this);
        backBtn.setOnClickListener(this);


    }
    private void setResoultionTxt(){
        String desc="Resoultion : "+getResoultion(resoultionBar.getProgress())+"dpi";
        resoultionTxt.setText(desc);

    }

	private int getResoultionProgress(int num){
        num=num-SetupInfo.getInstence().RESOULTION_MIN;
        num=num/resoultionRto;
        return num;
    }
    private int getResoultion(int num){
        num=num*resoultionRto;
        num=num+SetupInfo.getInstence().RESOULTION_MIN;
        return num;
    }
    private void setFpsTxt(){
        String desc="FPS : "+String.format("%.1f" , getFps(fpsBar.getProgress()));
        fpsTxt.setText(desc);

    }

    private int getFpsProgress(int num){
        num=num-SetupInfo.getInstence().FR_MIN;
        return num;
    }
    private int getFrameRate(int num){
        num=num+SetupInfo.getInstence().FR_MIN;
        return num;
    }
    private double getFps(int num){
        num=getFrameRate(num);

        double fps = ((double)SetupInfo.getInstence().FRAME_UNIT/(double)num);
        return fps;
    }
	protected void doResize() {
    	super.doResize();

    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){

    }


    public void onStartTrackingTouch(SeekBar seekBar){

    }


    public void onStopTrackingTouch(SeekBar seekBar){
        if(seekBar==fpsBar){

            SetupInfo.getInstence().registerFR(getFrameRate(fpsBar.getProgress()));
            setFpsTxt();
        }else{
            SetupInfo.getInstence().registerResoultion(getResoultion(resoultionBar.getProgress()));
            setResoultionTxt();
        }

    }
    public void onClick(View v) {


        if(v==backBtn){
            mainActivity.changeViewBack();
        }

    }


}
