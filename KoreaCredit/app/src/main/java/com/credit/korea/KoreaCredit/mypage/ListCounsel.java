package com.credit.korea.KoreaCredit.mypage;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.R;

import lib.ViewUtil;


public class ListCounsel extends FrameLayout{

    private TextView textTitle,textDate;
    private MyCounselObject info;
    public ListCounsel(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_counsel,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        textDate=(TextView) findViewById(R.id._textDate);




    }

    public void setData(MyCounselObject _info)
    {
        info=_info;
        textTitle.setText(info.title);
        textDate.setText(info.date);


    }


    public void  removeList()
    {

        ViewUtil.remove(this);

    }





 /////////////////////////////////////////









}
