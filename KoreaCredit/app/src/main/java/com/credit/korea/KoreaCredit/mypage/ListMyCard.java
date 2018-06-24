package com.credit.korea.KoreaCredit.mypage;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;

import java.util.HashMap;

import lib.ViewUtil;
import lib.core.PageObject;
import lib.imagemanager.ImageLoader;


public class ListMyCard extends FrameLayout implements View.OnClickListener,ImageLoader.ImageLoaderDelegate,AlertView.AlertViewDelegate{

    private TextView textTitle,textName,textSms,textPeriod,textKind;
    private Button btnDelete;
    private ImageView imageCard;
    private ImageLoader loader;
    private CardObject info;
    public ListMyCard(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_mycard,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        textName=(TextView) findViewById(R.id._textName);
        textSms=(TextView) findViewById(R.id._textSms);
        textPeriod=(TextView) findViewById(R.id._textPeriod);
        textKind=(TextView) findViewById(R.id._textKind);
        imageCard=(ImageView) findViewById(R.id._imageCard);

        btnDelete=(Button) findViewById(R.id._btnDelete);


        btnDelete.setOnClickListener(this);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR_B);
        textName.setTypeface(FontFactory.getInstence().FONT_KR);
        textSms.setTypeface(FontFactory.getInstence().FONT_KR);
        textPeriod.setTypeface(FontFactory.getInstence().FONT_KR);
        textKind.setTypeface(FontFactory.getInstence().FONT_KR);

        textTitle.setOnClickListener(this);
        imageCard.setOnClickListener(this);

    }

    public void setData(CardObject _info)
    {
        info=_info;
        textTitle.setText(info.title);
        textName.setText(info.name);
        textSms.setText(info.sms);
        textPeriod.setText(info.period);
        textKind.setText(info.kind);
        loadImg();
    }
    public void loadImg()
    {
        removeLoader();
        loader=new ImageLoader(this);
        loader.loadImg(info.imgPath);
    }
    public void onImageLoadCompleted(ImageLoader loader,Bitmap image){

        imageCard.setImageBitmap(image);


    }
    public void onClick(View v){

        if(v==btnDelete){
            AlertObject aInfo=new AlertObject();
            aInfo.iconID=R.drawable.icon_delete;
            aInfo.isDimed=true;
            aInfo.titleStr="아래카드을 삭제하시겠습니까?";
            aInfo.subTitleStr=info.title;

            AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,this);
            MainActivity.getInstence().body.addView(alertView);

        }else if(v==textTitle || v==imageCard){

            PageObject pInfo=new PageObject(Config.POPUP_CARD_DETAIL);
            pInfo.info=new HashMap<String,Object>();

            pInfo.info.put("cardObj",info);
            pInfo.dr=1;
            MainActivity.getInstence().addPopup(pInfo);

        }

    }
    public void onSelected(AlertView v,int selectIdx){

        if(selectIdx==0){
            MyCardInfo.getInstence().deleteMyCard(info);

        }


    }
    private void removeLoader()
    {
        if(loader!=null){
            imageCard.setImageResource(R.drawable.transparent);
            loader.removeLoader();
            loader=null;
        }
    }

    public void  removeList()
    {
        removeLoader();
        ViewUtil.remove(this);

    }





 /////////////////////////////////////////









}
