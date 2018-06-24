package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;

import lib.ViewUtil;


public class ListCardAddBenit2 extends FrameLayout{

    private TextView text,title,textDesc;

    public ListCardAddBenit2(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_card_add_benit2,this);
        text=(TextView) findViewById(R.id._text);
        title=(TextView) findViewById(R.id._title);
        textDesc=(TextView) findViewById(R.id._textDesc);

        text.setTypeface(FontFactory.getInstence().FONT_KR);
        title.setTypeface(FontFactory.getInstence().FONT_KR);
        textDesc.setTypeface(FontFactory.getInstence().FONT_KR);


    }
    public void removeList()
    {
        ViewUtil.remove(this);


    }
    public void setData(CardOption data)
    {
        title.setText(data.title);
        if(data.subTitle.equals("")==false) {
            text.setText("-" + data.subTitle);
        }else{
            text.setVisibility(View.GONE);
        }

        if(data.detail.equals("")==false) {
            textDesc.setText(data.detail);
        }else{
            textDesc.setVisibility(View.GONE);
        }

    }
    public void setData(CardUnit data,int idx)
    {
        //title.setText(idx+")"+data.title);
       // text.setText(data.detail);


    }






 /////////////////////////////////////////









}
