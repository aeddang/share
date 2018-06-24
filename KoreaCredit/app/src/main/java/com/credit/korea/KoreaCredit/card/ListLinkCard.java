package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.HashMap;
import java.util.Map;

import lib.ViewUtil;
import lib.core.PageObject;
import lib.imagemanager.ImageLoader;


public class ListLinkCard extends FrameLayout implements View.OnClickListener,ImageLoader.ImageLoaderDelegate{

    private TextView textTitle;

    private ImageView img;
    private ImageLoader loader;
    private CardUnit info;
    private Button btnMy;

    public ListLinkCard(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_link_card,this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        btnMy=(Button) findViewById(R.id._btnMy);

        img=(ImageView) findViewById(R.id._img);

        img.setOnClickListener(this);
        btnMy.setOnClickListener(this);
        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        btnMy.setTypeface(FontFactory.getInstence().FONT_KR);

    }

    public void setData(CardUnit _info)
    {
        info=_info;

        textTitle.setText(info.name);
        setBtnState();
        loadImg();

    }

    private void setBtnState() {

        if(info.isMine==true){
            btnMy.setSelected(true);
            btnMy.setText("보유카드");
            btnMy.setTextColor(Color.parseColor("#ffffff"));

        }else{
            btnMy.setSelected(false);
            btnMy.setText("보유카드와 비교");
            btnMy.setTextColor(Color.parseColor("#666666"));

        }

    }
    public void loadImg()
    {
        removeLoader();
        loader=new ImageLoader(this);
        loader.loadImg(info.imgPath);
    }
    public void onImageLoadCompleted(ImageLoader loader,Bitmap image){

        img.setImageBitmap(image);


    }
    public void onClick(View v){
        if(v==img) {

            PageObject pInfo = new PageObject(Config.POPUP_CARD_DETAIL);
            pInfo.info = new HashMap<String, Object>();

            pInfo.info.put("cardUnit", info);
            pInfo.dr = 1;
            MainActivity.getInstence().addPopup(pInfo);
        }else{
            if(btnMy.isSelected()==false){

                PageObject pInfo=new PageObject(Config.POPUP_CARD_COMPARE);
                Map<String,Object> pinfo=new HashMap<String,Object>();
                pinfo.put("cardSeq", info.seq);
                pInfo.dr=1;
                pInfo.info=pinfo;
                MainActivity.getInstence().addPopup(pInfo);

            }


        }

    }


    private void removeLoader()
    {
        if(loader!=null){
            img.setImageResource(R.drawable.transparent);
            loader.removeLoader();
            loader=null;
        }
    }

    public void  removeList()
    {
        removeLoader();
       // ViewUtil.remove(this);

    }





 /////////////////////////////////////////









}
