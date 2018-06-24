package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.HashMap;

import lib.CommonUtil;
import lib.ViewUtil;
import lib.core.PageObject;
import lib.imagemanager.ImageLoader;


public class ListCompare extends FrameLayout {

    private TextView textTitle,textName,textCompare,textGep;
    private CardObject info;


    public ListCompare(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_compare,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        textName=(TextView) findViewById(R.id._textName);
        textCompare=(TextView) findViewById(R.id._textCompare);
        textGep=(TextView) findViewById(R.id._textGep);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textName.setTypeface(FontFactory.getInstence().FONT_KR);
        textCompare.setTypeface(FontFactory.getInstence().FONT_KR);
        textGep.setTypeface(FontFactory.getInstence().FONT_KR);

    }

    public void setData(CardObject _info,int idx,double gep)
    {
        info=_info;
        if(idx==0){
            textTitle.setTextColor(Color.parseColor("#22b4ed"));
            textTitle.setText("추천카드");
            textGep.setText("△(추천-보유)");
        }else{
            textTitle.setTextColor(Color.parseColor("#21356e"));
            textTitle.setText("보유카드"+idx);
            if(gep==0.f){

                textGep.setText("-");
            }else{

                String str= String.format("%.1f",gep);
                textGep.setText(str+"만원");

            }


        }

        textCompare.setText(info.getBeneGetTotalView());
        textName.setText(info.title);





    }


    public void  removeList()
    {

        ViewUtil.remove(this);

    }





 /////////////////////////////////////////









}
