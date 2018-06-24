package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;

import lib.ViewUtil;


public class ListCardAddBenit extends FrameLayout{

    private TextView text,title;

    public ListCardAddBenit(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_card_add_benit,this);
        text=(TextView) findViewById(R.id._text);
        title=(TextView) findViewById(R.id._title);


        text.setTypeface(FontFactory.getInstence().FONT_KR);
        title.setTypeface(FontFactory.getInstence().FONT_KR);


    }
    public void removeList()
    {
        ViewUtil.remove(this);


    }
    public void setData(CardOption data)
    {
        title.setText("• "+data.title);
        if(data.subTitle.equals("")==false) {
            text.setText("-" + data.subTitle);
        }else{
            if(data.detail.equals("")==false) {
                text.setText("-" + data.detail);
            }else{
                text.setVisibility(View.GONE);
            }

        }


    }
    public void setData(CardUnit data,int idx)
    {
        //title.setText(idx+")"+data.title);
       // text.setText(data.detail);


    }






 /////////////////////////////////////////









}
