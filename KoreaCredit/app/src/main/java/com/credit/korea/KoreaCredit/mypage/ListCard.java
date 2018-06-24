package com.credit.korea.KoreaCredit.mypage;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.R;

import java.util.HashMap;

import lib.ViewUtil;
import lib.core.PageObject;
import lib.imagemanager.ImageLoader;


public class ListCard extends FrameLayout implements View.OnClickListener,ImageLoader.ImageLoaderDelegate,AlertView.AlertViewDelegate{

    private TextView textTitle,textName,textKind;
    private ImageButton btnDelete,btnAdd;
    private ImageView imageCard;
    private ImageLoader loader;
    private CardObject info;
    public ListCard(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_card,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        textName=(TextView) findViewById(R.id._textName);

        textKind=(TextView) findViewById(R.id._textKind);
        imageCard=(ImageView) findViewById(R.id._imageCard);

        btnDelete=(ImageButton) findViewById(R.id._btnDelete);
        btnAdd=(ImageButton) findViewById(R.id._btnAdd);

        btnDelete.setOnClickListener(this);
        btnAdd.setOnClickListener(this);



    }

    public void setData(CardObject _info)
    {
        info=_info;
        textTitle.setText(info.title);
        textName.setText(info.name);

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

        if(v==btnAdd){
            PageObject pInfo=new PageObject(Config.POPUP_CARD_ADD);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("title","카드등록");
            pInfo.info.put("cardObj",info);
            pInfo.dr=1;
            MainActivity.getInstence().addPopup(pInfo);
        }else if(v==btnDelete){

            AlertObject aInfo=new AlertObject();
            aInfo.iconID=R.drawable.icon_delete;
            aInfo.isDimed=true;
            aInfo.titleStr="아래카드을 삭제하시겠습니까?";
            aInfo.subTitleStr=info.title;

            AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,this);
            MainActivity.getInstence().body.addView(alertView);
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
