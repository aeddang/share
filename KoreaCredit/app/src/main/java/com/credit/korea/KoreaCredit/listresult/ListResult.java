package com.credit.korea.KoreaCredit.listresult;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;


public class ListResult extends FrameLayout {

    private TextView textTitle;

    public ListResult(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list,this);
        textTitle=(TextView) findViewById(R.id._textTitle);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);



    }

    public void setData(String text)
    {
        textTitle.setText(text);

    }






 /////////////////////////////////////////









}
