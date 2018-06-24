package com.credit.korea.KoreaCredit.card.book;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;

import java.util.HashMap;
import java.util.Map;

import lib.CommonUtil;
import lib.CustomTimer;
import lib.core.PageObject;


public class ListBookDesc extends FrameLayout implements View.OnClickListener ,AlertView.AlertViewDelegate{


    private TextView textDate,textPrice,textTitle;


    private Button btnModify,btnDelete;

    private BookUnit info;
    public ListBookDesc(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_cardbook_desc,this);
        textDate=(TextView) findViewById(R.id._textDate);

        textPrice=(TextView) findViewById(R.id._textPrice);
        textTitle=(TextView) findViewById(R.id._textTitle);


        btnModify=(Button) findViewById(R.id._btnModify);
        btnDelete=(Button) findViewById(R.id._btnDelete);



        textDate.setTypeface(FontFactory.getInstence().FONT_KR);
        textPrice.setTypeface(FontFactory.getInstence().FONT_KR);
        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        btnModify.setOnClickListener(this);
        btnDelete.setOnClickListener(this);





    }

    public void onComplete(CustomTimer timer){

    }


    public void onClick(View v) {

        if(v== btnModify){
            PageObject pInfo=new PageObject(Config.POPUP_CARD_BOOK_ADDED);
            Map<String,Object> pinfo=new HashMap<String,Object>();
            pinfo.put("data", info);
            pInfo.dr=1;
            pInfo.info=pinfo;
            MainActivity.getInstence().addPopup(pInfo);
        }else if(v== btnDelete){
            AlertObject aInfo=new AlertObject();
            aInfo.iconID=R.drawable.icon_delete;
            aInfo.isDimed=true;
            aInfo.titleStr="해당 카드 이용실적을 삭제하시겠습니까?";


            aInfo.subTitleStr=textTitle.getText().toString()+" "+textPrice.getText().toString()+" "+info.usedate;

            AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,this);
            MainActivity.getInstence().body.addView(alertView);
        }

    }
    public void onSelected(AlertView v,int selectIdx){

        if(selectIdx==0){
            BookInfo.getInstence().deleteBookUnit(info);

        }


    }
    public void removeList()
    {


    }
    public void setData(BookUnit data)
    {
        info=data;
        textTitle.setText(data.title);

        textPrice.setText(CommonUtil.getPriceStr((float)data.price)+"원");

       // String dateStr=CommonUtil.getDateByCode(Integer.parseInt(data.date), ".");
        textDate.setText(data.usedate);




    }






 /////////////////////////////////////////









}
