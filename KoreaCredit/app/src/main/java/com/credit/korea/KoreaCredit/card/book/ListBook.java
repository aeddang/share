package com.credit.korea.KoreaCredit.card.book;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.HashMap;
import java.util.Map;

import lib.CustomTimer;
import lib.core.PageObject;


public class ListBook extends FrameLayout implements View.OnClickListener, CustomTimer.TimerDelegate {

    private static final float spd=0.1f;
    private static final float maxPoint=100.f;
    private TextView textType,textPoint,textUsed,textConsume;
    private FrameLayout consumeBar,usedBar;
    private LinearLayout barBox;
    private Button btnModify;
    private CustomTimer timer;
    private FrameLayout cBar;
    private float cPct,tPct;
    private BookObject info;
    public ListBook(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_cardbook,this);
        textType=(TextView) findViewById(R.id._textType);

        textPoint=(TextView) findViewById(R.id._textPoint);
        textUsed=(TextView) findViewById(R.id._textUsed);
        textConsume=(TextView) findViewById(R.id._textConsume);
        barBox=(LinearLayout) findViewById(R.id._barBox);
        consumeBar=(FrameLayout) findViewById(R.id._consumeBar);
        usedBar=(FrameLayout) findViewById(R.id._usedBar);

        btnModify=(Button) findViewById(R.id._btnModify);



        textType.setTypeface(FontFactory.getInstence().FONT_KR);

        textPoint.setTypeface(FontFactory.getInstence().FONT_KR);
        textUsed.setTypeface(FontFactory.getInstence().FONT_KR);
        textConsume.setTypeface(FontFactory.getInstence().FONT_KR);

        btnModify.setOnClickListener(this);


        timer=new CustomTimer(10,-1,this);


    }
    public void onTime(CustomTimer timer){

        cPct+=spd;
        if(cPct>tPct){
            cPct=tPct;
            if(cBar==consumeBar)
            {
                setBar(cPct,cBar);
                cPct=0;
                tPct=(float)info.usedPoint/maxPoint;
                if(tPct>=1.0f){
                    tPct = 1.0f;

                }else{


                }
                cBar=usedBar;
            }else{
                setBar(cPct,cBar);
                timer.timerStop();
            }


        }else{
            setBar(cPct,cBar);

        }


    }
    public void onComplete(CustomTimer timer){

    }

    private void setBar(float pct,FrameLayout bar)
    {
       // Log.i("","pct :"+pct);

        int w= barBox.getWidth()-textConsume.getWidth();
       // Log.i("","barBox w :"+barBox.getWidth()+" W:"+w);

        w = (int)Math.round((float)w * pct);
        LinearLayout.LayoutParams layout= (LinearLayout.LayoutParams)bar.getLayoutParams();

        layout.width=w;
        bar.setLayoutParams(layout);

    }
    public void onClick(View v) {

        if(v== btnModify){
             PageObject pInfo=new PageObject(Config.POPUP_CARD_BOOK_DESC);
             Map<String,Object> pinfo=new HashMap<String,Object>();
             pinfo.put("data", info);
             pInfo.dr=1;
             pInfo.info=pinfo;
             MainActivity.getInstence().addPopup(pInfo);

        }

    }

    public void removeList()
    {
        if(timer!=null){
            timer.removeTimer();
            timer=null;
        }


    }
    public void setData(BookObject data)
    {
        info=data;
        textType.setText(data.title);

        textPoint.setText(String.valueOf((int)Math.round(data.adjustPoint))+"%");
        textConsume.setText(String.valueOf((int)Math.round(data.consumePoint)));
        textUsed.setText(String.valueOf((int)Math.round(data.usedPoint)));
        consumeBar.setBackgroundColor(Color.parseColor("#cccccc"));
        if(data.usedPoint>=data.consumePoint){

            usedBar.setBackgroundColor(Color.parseColor("#22b4ed"));
            textUsed.setTextColor(Color.parseColor("#22b4ed"));

        }else{

            usedBar.setBackgroundColor(Color.parseColor("#cccccc"));
            textUsed.setTextColor(Color.parseColor("#cccccc"));
        }

        setBar(0,consumeBar);
        setBar(0,usedBar);
        cPct=0;
        tPct=(float)data.consumePoint/maxPoint;
        if(tPct>=1.0f){
            tPct = 1.0f;

        }else{


        }

        cBar=consumeBar;
        timer.timerStart();

    }






 /////////////////////////////////////////









}
