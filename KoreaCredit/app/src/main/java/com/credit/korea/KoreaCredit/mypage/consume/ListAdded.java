package com.credit.korea.KoreaCredit.mypage.consume;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.R;

import lib.ViewUtil;


public class ListAdded extends FrameLayout{

    private TextView text,title;

    public ListAdded(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_consume_added,this);
        text=(TextView) findViewById(R.id._text);
        title=(TextView) findViewById(R.id._title);


        text.setTypeface(FontFactory.getInstence().FONT_KR);
        title.setTypeface(FontFactory.getInstence().FONT_KR);


    }
    public void removeList()
    {
        ViewUtil.remove(this);


    }
    public void setData(AddedGroup data)
    {
        title.setText("• "+data.title);

        String str="";

        for(int i=0;i<data.lists.size();++i){
            AddedObject obj=data.lists.get(i);
            if(obj.isSelected==true){
                if(str.equals("")==true){

                    str=obj.title;
                }else{
                    str=str+","+obj.title;
                }

            }

        }

        text.setText(str);




    }






 /////////////////////////////////////////









}
