package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;

import java.util.HashMap;
import java.util.Map;

import lib.CustomTimer;
import lib.ViewUtil;
import lib.core.PageObject;
import lib.imagemanager.ImageLoader;


public class ListHitCard extends FrameLayout implements View.OnClickListener , CustomTimer.TimerDelegate{
    private static final float spd=0.1f;
    private static final float maxPoint=100.f;

    private TextView textTitle;
    private CardObject info;
    private Button btn0,btn1;


    private TextView textConsume;
    private FrameLayout consumeBar;
    private LinearLayout barBox;
    private CustomTimer timer;

    private float cPct,tPct;

    public ListHitCard(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_hit_card,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        btn0=(Button) findViewById(R.id._btn0);
        btn1=(Button) findViewById(R.id._btn1);

        textConsume=(TextView) findViewById(R.id._textConsume);
        barBox=(LinearLayout) findViewById(R.id._barBox);
        consumeBar=(FrameLayout) findViewById(R.id._consumeBar);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        textTitle.setOnClickListener(this);
        textConsume.setTypeface(FontFactory.getInstence().FONT_ENG);
        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        btn0.setTypeface(FontFactory.getInstence().FONT_KR);
        btn1.setTypeface(FontFactory.getInstence().FONT_KR);

        timer=new CustomTimer(10,-1,this);

    }

    public void setData(CardObject _info)
    {
        info=_info;

        textTitle.setText(info.title);
        textConsume.setText(String.valueOf(info.rate)+"%");


        cPct=0;
        tPct=info.rate/maxPoint;
        setBar(0,consumeBar);
        timer.timerStart();
        setBtnState();


    }
    public void onTime(CustomTimer timer){

        cPct+=spd;
        if(cPct>tPct){
            cPct=tPct;
            setBar(cPct,consumeBar);
            timer.timerStop();



        }else{
            setBar(cPct,consumeBar);

        }


    }
    private void setBar(float pct,FrameLayout bar)
    {
        Log.i("", "pct :" + pct);

        int w= barBox.getWidth()-textConsume.getWidth();
        Log.i("","barBox w :"+barBox.getWidth()+" W:"+w);

        w = (int)Math.round((float)w * pct);
        LinearLayout.LayoutParams layout= (LinearLayout.LayoutParams)bar.getLayoutParams();

        layout.width=w;
        bar.setLayoutParams(layout);

    }
    public void onComplete(CustomTimer timer){

    }

    private void setBtnState() {



            if(info.isMine==true){
                btn0.setSelected(true);
                btn0.setText("보유카드");
                btn0.setTextColor(Color.parseColor("#ffffff"));

            }else{
                btn0.setSelected(false);
                btn0.setText("보유카드등록");
                btn0.setTextColor(Color.parseColor("#666666"));

            }
            if(info.isInterest==true){
                btn1.setSelected(true);
                btn1.setText("관심카드");
                btn1.setTextColor(Color.parseColor("#ffffff"));

            }else{
                if(info.isMine==true){
                    btn1.setText("관심카드");

                }else{

                    btn1.setText("관심카드등록");

                }
                btn1.setSelected(false);

                btn1.setTextColor(Color.parseColor("#666666"));

            }


    }


    public void onClick(View v){
        if(v==textTitle) {

            PageObject pInfo = new PageObject(Config.POPUP_CARD_DETAIL);
            pInfo.info = new HashMap<String, Object>();

            pInfo.info.put("cardObj", info);
            pInfo.dr = 1;
            MainActivity.getInstence().addPopup(pInfo);
        }else{
            if(v==btn1){
                if(btn1.isSelected()==false){
                    if(info.isMine==false){

                        MyCardInfo.getInstence().requestCard(info,MyCardInfo.REQUEST_TYPE_INTEREST_CARD);
                    }else{

                        MainActivity.getInstence().viewAlert("",R.string.msg_card_request_interest_anable,null);
                    }


                }

            }else if(v==btn0){
                if(btn0.isSelected()==false){

                    PageObject pInfo=new PageObject(Config.POPUP_CARD_ADD);
                    pInfo.info=new HashMap<String,Object>();
                    pInfo.info.put("title","카드등록");
                    pInfo.info.put("cardObj",info);
                    pInfo.dr=1;
                    MainActivity.getInstence().addPopup(pInfo);

                }

            }


        }

    }




    public void  removeList()
    {
        if(timer!=null){
            timer.removeTimer();
            timer=null;
        }
        //ViewUtil.remove(this);

    }





 /////////////////////////////////////////









}
