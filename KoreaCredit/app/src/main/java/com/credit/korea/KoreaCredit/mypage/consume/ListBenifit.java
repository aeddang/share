package com.credit.korea.KoreaCredit.mypage.consume;


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
import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;

import java.util.HashMap;

import lib.CustomTimer;
import lib.ViewUtil;
import lib.core.PageObject;


public class ListBenifit extends FrameLayout implements View.OnClickListener, CustomTimer.TimerDelegate, AlertView.AlertViewDelegate {

    private static final float spd=4.0f;
    private static final float maxPoint=100.f;
    private TextView textType,textTitle,textUsed,textConsume;
    private FrameLayout consumeBar,usedBar;
    private LinearLayout barBox;
    private Button btnModify,btnDelete;
    private CustomTimer timer;
    private FrameLayout cBar;
    private float cPct,tPct;
    private BenifitObject info;
    public ListBenifit(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_consume_benifit,this);
        textType=(TextView) findViewById(R.id._textType);
        textTitle=(TextView) findViewById(R.id._textTitle);

        textUsed=(TextView) findViewById(R.id._textUsed);
        textConsume=(TextView) findViewById(R.id._textConsume);
        barBox=(LinearLayout) findViewById(R.id._barBox);
        consumeBar=(FrameLayout) findViewById(R.id._consumeBar);
        usedBar=(FrameLayout) findViewById(R.id._usedBar);

        btnModify=(Button) findViewById(R.id._btnModify);
        btnDelete=(Button) findViewById(R.id._btnDelete);


        textType.setTypeface(FontFactory.getInstence().FONT_KR);
        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);

        textUsed.setTypeface(FontFactory.getInstence().FONT_KR);
        textConsume.setTypeface(FontFactory.getInstence().FONT_KR);

        btnModify.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

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
                tPct=(float)info.usedRate/maxPoint;
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
        //Log.i("","barBox w :"+barBox.getWidth()+" W:"+w);

        w = (int)Math.round((float)w * pct);
        LinearLayout.LayoutParams layout= (LinearLayout.LayoutParams)bar.getLayoutParams();

        layout.width=w;
        bar.setLayoutParams(layout);

    }
    public void onClick(View v) {

        if(v== btnModify){
            PageObject pInfo=new PageObject(Config.POPUP_CONSUME_MODIFY_DETAIL);
            pInfo.dr=1;
            pInfo.info=new HashMap<String,Object>();
            pInfo.info. put("myObj",info);
            MainActivity.getInstence().addPopup(pInfo);

        }else if(v== btnDelete){

            AlertObject aInfo=new AlertObject();
            aInfo.iconID=R.drawable.icon_delete;
            aInfo.isDimed=true;
            aInfo.subTitleStr=info.title+" 혜택항목을 삭제하시겠습니까?";
            AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,this);
            MainActivity.getInstence().body.addView(alertView);
        }

    }
    public void onSelected(AlertView v,int selectIdx){

        if(selectIdx==0){
            ConsumeInfo.getInstence().deleteBenefitData(info);

        }


    }
    public void removeList()
    {
        if(timer!=null){
            timer.removeTimer();
            timer=null;
        }
        ViewUtil.remove(this);

    }
    public void setData(BenifitObject data)
    {
        info=data;
        textType.setText(data.title);

        String txt="";
        for(int i=0;i<data.lists.size();++i){
            BenifitUnit unit=data.lists.get(i);
            if(unit.isSelected==true){
                if(txt.equals("")==true){
                    txt=unit.title;
                }else{

                    txt=txt+", "+unit.title;
                }

            }

        }

        consumeBar.setBackgroundColor(Color.parseColor("#cccccc"));
        if(info.usedPoint>=info.consumePoint){

            usedBar.setBackgroundColor(Color.parseColor("#22b4ed"));
            textUsed.setTextColor(Color.parseColor("#22b4ed"));
        }else{

            usedBar.setBackgroundColor(Color.parseColor("#cccccc"));
            textUsed.setTextColor(Color.parseColor("#cccccc"));
        }

        textTitle.setText(txt);

        //textPoint.setText("적용 "+String.valueOf((int)Math.round(data.adjustPoint)));
        textConsume.setText(String.valueOf((int)Math.round(data.consumePoint)));
        textUsed.setText(String.valueOf((int)Math.round(data.usedPoint)));

        setBar(0,consumeBar);
        setBar(0,usedBar);
        cPct=0;
        tPct=(float)data.consumeRate/maxPoint;
        if(tPct>=1.0f){
            tPct = 1.0f;

        }else{


        }
        cBar=consumeBar;
        timer.timerStart();

    }






 /////////////////////////////////////////









}
