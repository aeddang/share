package com.credit.korea.KoreaCredit.card;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.listresult.ListResult;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;

import java.util.ArrayList;
import java.util.HashMap;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.imagemanager.ImageLoader;


@SuppressLint({ "NewApi", "JavascriptInterface" })
public class PopupDetailCard extends ViewCore implements OnClickListener,ImageLoader.ImageLoaderDelegate,CardInfo.CardDetailDelegate{


	private FrameLayout body;
    private TextView textTitle,textName,textKind,textPricePerYear;
    private Button btnIntrest,btnMy,btnSms,btnCounsel,btnIntrestBt,btnMyBt;
    private ImageButton btnSmsBt,btnCounselBt;
    private ImageView imageCard;
    private ImageLoader loader;


    private LinearLayout introStack,addedStack,benefitAllStack,benefitStack;

    private CardObject cardObj;
    private String cardSeq;




    public PopupDetailCard(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 

		if(pageInfo.info!=null){
            CardObject cObj;
            CardUnit cUnit;

            if(pageInfo.info.get("cardObj")!=null) {
                cObj = (CardObject) pageInfo.info.get("cardObj");
                cardSeq=cObj.seq;
                cardObj=cObj;
            }
            if(pageInfo.info.get("cardUnit")!=null) {
                cUnit = (CardUnit) pageInfo.info.get("cardUnit");
                cardSeq=cUnit.seq;
            }

        }else{
            cardSeq="";
			
		}

        Log.i("","PopupAddCard ");
		View.inflate(context, R.layout.popup_card_detail, this);
        textTitle=(TextView) findViewById(R.id._textTitle);
        textName=(TextView) findViewById(R.id._textName);

        textKind=(TextView) findViewById(R.id._textKind);

        textPricePerYear=(TextView) findViewById(R.id._textPricePerYear);
        imageCard=(ImageView) findViewById(R.id._imageCard);

        btnIntrest=(Button) findViewById(R.id._btnIntrest);
        btnMy=(Button) findViewById(R.id._btnMy);
        btnSms=(Button) findViewById(R.id._btnSms);
        btnCounsel=(Button) findViewById(R.id._btnCounsel);
        btnIntrestBt=(Button) findViewById(R.id._btnIntrestBt);
        btnMyBt=(Button) findViewById(R.id._btnMyBt);
        btnSmsBt=(ImageButton) findViewById(R.id._btnSmsBt);
        btnCounselBt=(ImageButton) findViewById(R.id._btnCounselBt);

        introStack=(LinearLayout) findViewById(R.id._introStack);
        addedStack=(LinearLayout) findViewById(R.id._addedStack);
        benefitAllStack=(LinearLayout) findViewById(R.id._benefitAllStack);
        benefitStack=(LinearLayout) findViewById(R.id._benefitStack);



        btnIntrest.setOnClickListener(this);
        btnMy.setOnClickListener(this);
        btnSms.setOnClickListener(this);
        btnCounsel.setOnClickListener(this);
        btnIntrestBt.setOnClickListener(this);
        btnMyBt.setOnClickListener(this);
        btnSmsBt.setOnClickListener(this);
        btnCounselBt.setOnClickListener(this);

        btnIntrest.setTypeface(FontFactory.getInstence().FONT_KR);
        btnMy.setTypeface(FontFactory.getInstence().FONT_KR);
        btnIntrestBt.setTypeface(FontFactory.getInstence().FONT_KR);
        btnMyBt.setTypeface(FontFactory.getInstence().FONT_KR);
        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textName.setTypeface(FontFactory.getInstence().FONT_KR);

        textKind.setTypeface(FontFactory.getInstence().FONT_KR);
        textPricePerYear.setTypeface(FontFactory.getInstence().FONT_KR);

    }
	
    
	protected void doMovedInit() { 
	    super.doMovedInit();
        CardInfo.getInstence().detailDelegate=this;
        CardInfo.getInstence().loadDetailCard(cardSeq);


	}

    protected void doRemove() {
        removeLoader();
        if(CardInfo.getInstence().detailDelegate==this){
            CardInfo.getInstence().detailDelegate=null;
        }
    	super.doRemove();


    }
    public void onLoadCard(CardObject card){
        if(card==null){

        }else{
           cardObj=card;

        }
        if(cardObj==null){
            return;
        }
        textTitle.setText(cardObj.title);
        textName.setText(cardObj.name);
        textKind.setText(cardObj.kind);
        textPricePerYear.setText(cardObj.pricePerYear);

        setBtnState();
        setAddStack(cardObj.intros,introStack);
        setAddStack(cardObj.subInfos,addedStack);

        ArrayList<CardOption> bene = new ArrayList<CardOption>();
        bene.addAll(cardObj.benefits);
        bene.addAll(cardObj.benefitLimits);
        setAddStack(bene,benefitAllStack);

        setAddStack(cardObj.svrs,benefitStack);
        loadImg();

    }
    private void setAddStack(ArrayList<CardOption> options,LinearLayout target) {

        if(options.size()<1){
            target.addView(getNoData());
        }else{
            for(int i=0;i<options.size();++i){
                ListCardAddBenit2 list=new ListCardAddBenit2(MainActivity.getInstence());
                list.setData(options.get(i));
                target.addView(list);

            }

        }




    }
    private ListResult getNoData(){

        ListResult nodata=new ListResult(mainActivity);
        String noStr="해당사항 없음";
        nodata.setData(noStr);
        return nodata;
    }

    public void loadImg()
    {
        removeLoader();
        loader=new ImageLoader(this);
        loader.loadImg(cardObj.imgPath);
    }
    public void onImageLoadCompleted(ImageLoader loader,Bitmap image){

        imageCard.setImageBitmap(image);


    }
    private void removeLoader()
    {
        if(loader!=null){
            imageCard.setImageResource(R.drawable.transparent);
            loader.removeLoader();
            loader=null;
        }
    }

    private void setBtnState() {
        if(cardObj.isMine==true){
            btnMy.setSelected(true);
            btnMy.setText("보유카드");
            btnMy.setTextColor(Color.parseColor("#ffffff"));

            btnMyBt.setSelected(true);
            btnMyBt.setText("보유카드");
            btnMyBt.setTextColor(Color.parseColor("#ffffff"));

        }else{
            btnMy.setSelected(false);
            btnMy.setText("보유카드등록");
            btnMy.setTextColor(Color.parseColor("#666666"));

            btnMyBt.setSelected(false);
            btnMyBt.setText("보유카드등록");
            btnMyBt.setTextColor(Color.parseColor("#666666"));

        }
        if(cardObj.isInterest==true){
            btnIntrest.setSelected(true);
            btnIntrest.setText("관심카드");
            btnIntrest.setTextColor(Color.parseColor("#ffffff"));

            btnIntrestBt.setSelected(true);
            btnIntrestBt.setText("관심카드");
            btnIntrestBt.setTextColor(Color.parseColor("#ffffff"));

        }else{
            btnIntrest.setSelected(false);
            btnIntrest.setText("관심카드등록");
            btnIntrest.setTextColor(Color.parseColor("#666666"));

            btnIntrestBt.setSelected(false);
            btnIntrestBt.setText("관심카드등록");
            btnIntrestBt.setTextColor(Color.parseColor("#666666"));

        }
    }
    public void onClick(View v) {


        if(v==btnIntrest|| v==btnIntrestBt){
            if(btnIntrest.isSelected()==false){
                MyCardInfo.getInstence().requestCard(cardObj,MyCardInfo.REQUEST_TYPE_INTEREST_CARD);

            }

        }else if(v==btnMy || v==btnMyBt){
            if(btnMy.isSelected()==false){

                PageObject pInfo=new PageObject(Config.POPUP_CARD_ADD);
                pInfo.info=new HashMap<String,Object>();
                pInfo.info.put("title","카드등록");
                pInfo.info.put("cardObj",cardObj);
                pInfo.dr=1;
                MainActivity.getInstence().addPopup(pInfo);
            }

        }else if(v==btnSms || v==btnSmsBt) {
            MainActivity mc=(MainActivity)MainActivity.getInstence();
            mc.callSms(cardObj);

        }
        else if(v==btnCounsel|| v==btnCounselBt) {

            MainActivity mc=(MainActivity)MainActivity.getInstence();
            mc.callCounsel(cardObj);
        }
		
	} 



	  
	 

}
