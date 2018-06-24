package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.listresult.ListResult;
import com.credit.korea.KoreaCredit.mypage.ListCard;
import com.credit.korea.KoreaCredit.mypage.ListCounsel;
import com.credit.korea.KoreaCredit.mypage.ListMyCard;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import java.util.ArrayList;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PopupCardCompare extends ViewCore implements CardInfo.CardCompareDelegate{

    private LinearLayout stackCard,stackCardSum;



    private String cardSeq;

    public PopupCardCompare(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.popup_card_compare,this);
        CardObject cardObj=null;

        if(pageInfo.info!=null){
            if(pageInfo.info.get("cardSeq")!=null) {
                cardSeq = (String) pageInfo.info.get("cardSeq");
            }

        }else{


        }


        stackCard=(LinearLayout) findViewById(R.id._stackCard);
        stackCardSum=(LinearLayout) findViewById(R.id._stackCardSum);






    }

	protected void doResize()
    {
    	super.doResize();

    }


	protected void doMovedInit() {
	    super.doMovedInit();


        CardInfo.getInstence().compareDelegate = this;
        CardInfo.getInstence().loadCompareCard(cardSeq);



	}
    public void onLoadCards(ArrayList<CardObject> cards){

        if(cards==null){
            MainActivity.getInstence().viewAlert("",R.string.msg_no_data,null);
            return;
        }else{
            if(cards.size()<1){
                MainActivity.getInstence().viewAlert("",R.string.msg_no_data,null);
                return;
            }
            setCompareCard(cards);
        }



    }
    private void setCompareCard(ArrayList<CardObject> cards){

        ListSearchCard card=new ListSearchCard(MainActivity.getInstence());
        stackCard.addView(card);
        card.setData(cards.get(0),-1);




        float std=0.f;
        for(int i=0;i<cards.size();++i){

            CardObject obj=cards.get(i);

            ListCompare list=new ListCompare(mainActivity);
            if(i==0){
                std=cards.get(i).getBeneGetTotalNum();
                list.setData(cards.get(i),i,std);
            }else{

                list.setData(cards.get(i),i,std-cards.get(i).getBeneGetTotalNum());
            }
            stackCardSum.addView(list);
        }

    }




    private ListResult getNoData(){

        ListResult nodata=new ListResult(mainActivity);
        String noStr=mainActivity.getString(R.string.msg_no_data);
        nodata.setData(noStr);
        return nodata;
    }



    protected void doRemove() {

        super.doRemove();


        if(CardInfo.getInstence().compareDelegate==this){
            CardInfo.getInstence().compareDelegate=null;
        }


    }





 /////////////////////////////////////////









}
