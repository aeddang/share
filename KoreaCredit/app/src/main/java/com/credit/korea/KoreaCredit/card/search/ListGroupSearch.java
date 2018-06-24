package com.credit.korea.KoreaCredit.card.search;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;

import lib.ViewUtil;


public class ListGroupSearch extends FrameLayout{

    private TextView text,title;

    public ListGroupSearch(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_group_search,this);
        text=(TextView) findViewById(R.id._text);
        title=(TextView) findViewById(R.id._title);


        text.setTypeface(FontFactory.getInstence().FONT_KR);
        title.setTypeface(FontFactory.getInstence().FONT_KR);


    }
    public void removeList()
    {
        ViewUtil.remove(this);


    }
    public void setData(String tit, String txt)
    {
        title.setText("• "+tit);
        text.setText(txt);


    }






 /////////////////////////////////////////









}
