package com.credit.korea.KoreaCredit.card.recommend;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.card.CardObject;

import lib.ViewUtil;


public class ListGroupRecommend extends FrameLayout implements View.OnClickListener{

    private TextView textTitle;
    private Button btnCheck;
    public ListGroupRecommend(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_group_recommend,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        btnCheck=(Button) findViewById(R.id._btnCheck);


        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        btnCheck.setSelected(false);
        btnCheck.setOnClickListener(this);


    }
    public void removeList()
    {
        ViewUtil.remove(this);


    }
    public void onClick(View v) {
         if(btnCheck.isSelected()==true){
             btnCheck.setSelected(false);
         }else{
             btnCheck.setSelected(true);
         }


    }
    public boolean getChecked()
    {
        return btnCheck.isSelected();
    }
    public void setData(CardObject data,boolean isSelect)
    {
        textTitle.setText(data.title);
        btnCheck.setSelected(isSelect);


    }






 /////////////////////////////////////////









}
