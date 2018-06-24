package com.credit.korea.KoreaCredit.mypage;


import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.card.CardInfo;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.listresult.ListResult;
import com.credit.korea.KoreaCredit.listresult.PopupListResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PageMyCard extends ViewCore implements View.OnClickListener,MyCardInfo.MyCardDelegate,CardInfo.CardSearchDelegate,
        PopupListResult.PopupListResultDelegate,TextView.OnEditorActionListener {

    private LinearLayout myCardListBox,cardListBox,requestListBox;
    private Button btnSearch;
    private EditText inputKey;
    private MyCardInfo info;
    private ArrayList<ListCard> cardLists;
    private ArrayList<ListMyCard> myCardLists;
    private ArrayList< ListCounsel> counselLists;


    private ArrayList<CardObject> searchDatas;


    public PageMyCard(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_mypage_card,this);
        cardListBox=(LinearLayout) findViewById(R.id._cardListBox);
        myCardListBox=(LinearLayout) findViewById(R.id._myCardListBox);
        requestListBox=(LinearLayout) findViewById(R.id._requestListBox);
        btnSearch=(Button) findViewById(R.id._btnSearch);
        inputKey=(EditText) findViewById(R.id._inputKey);

        btnSearch.setOnClickListener(this);

        info=MyCardInfo.getInstence();
        ArrayList<CardObject> myCards=info.getMyCards();
        if(myCards!=null){

            onLoadMyCards(myCards);
        }

        ArrayList<MyCounselObject> counsels=info.getMyCounsels();

        if(counsels!=null){

            onLoadMyCounsels(counsels);
        }
        inputKey.setOnEditorActionListener(this);
    }
    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        int result = actionId & EditorInfo.IME_MASK_ACTION;
        switch (result) {
            case EditorInfo.IME_ACTION_DONE:
                onSearch();
                break;

        }
        return false;
    }
    protected void doResetOnListener() {
        info.delegate=this;
    }
	protected void doResize()
    {
    	super.doResize();

    }
    private void onSearch(){
        String key = inputKey.getText().toString();
        if (key.equals("") == true) {
            MainActivity.getInstence().viewAlert("", R.string.msg_card_find_input_key, null);
            inputKey.requestFocus();
            return;
        }

        MainActivity.getInstence().hideKeyBoard();

        CardInfo.getInstence().loadNameCards(key);


    }
    public void onClick(View v) {

        if(v == btnSearch){


            onSearch();
        }

    }
    public void onLoadCards(ArrayList<CardObject> cards){

        if(cards.size()<1)
        {
            MainActivity.getInstence().viewAlert("", R.string.msg_no_data, null);
            return;
        }
        searchDatas=cards;

        ArrayList<String> results=new ArrayList<String>();
        for(int i=0;i<searchDatas.size();++i)
        {
            results.add(searchDatas.get(i).title);

        }

        PageObject pInfo=new PageObject(Config.POPUP_LIST_RESULT);
        Map<String,Object> pinfo=new HashMap<String,Object>();
        pinfo.put("datas", results);
        pInfo.dr=1;
        pInfo.info=pinfo;
        PopupListResult pop=(PopupListResult)MainActivity.getInstence().addPopup(pInfo);
        pop.delegate=this;
    }
    public void onSelectData(int idx){

        if(searchDatas==null){
            return;
        }
        CardObject card = searchDatas.get(idx);
        PageObject pInfo=new PageObject(Config.POPUP_CARD_ADD);
        pInfo.info=new HashMap<String,Object>();
        pInfo.info.put("title","카드등록");
        pInfo.info.put("cardObj",card);
        pInfo.dr=1;
        MainActivity.getInstence().addPopup(pInfo);

    }

	protected void doMovedInit() {
	    super.doMovedInit();

        CardInfo.getInstence().delegate=this;
        info.delegate=this;
        if(info.getMyCards()==null) {
            info.loadMyCards(false);
        }
        if(info.getMyCounsels()==null) {
            info.loadMyCounsels(false);
        }


	}
    private void removeCardLists(){

        if(cardLists!=null){
            for(int i=0;i<cardLists.size();++i){

                cardLists.get(i).removeList();

            }
            cardLists.clear();
            cardLists=null;

        }

        if(myCardLists!=null){
            for(int i=0;i<myCardLists.size();++i){

                myCardLists.get(i).removeList();

            }
            myCardLists.clear();
            myCardLists=null;

        }

        myCardListBox.removeAllViews();
        cardListBox.removeAllViews();


    }
    private void removeCounselLists(){


        if(counselLists!=null){
            for(int i=0;i<counselLists.size();++i){

                counselLists.get(i).removeList();

            }
            counselLists.clear();
            counselLists=null;

        }
        requestListBox.removeAllViews();

    }
    public void onModifyMyCards()
    {
        info.loadMyCards(true);

    }
    public void onLoadMyCards(ArrayList<CardObject> myCards){
        removeCardLists();
        cardLists=new ArrayList<ListCard>();
        myCardLists=new ArrayList<ListMyCard>();

        for(int i=0;i<myCards.size();++i){

            CardObject obj=myCards.get(i);
            if(obj.isMine==true){
                ListMyCard card=new ListMyCard(mainActivity);

                myCardListBox.addView(card);
                card.setData(myCards.get(i));

                myCardLists.add(card);

            }else{
                ListCard card=new ListCard(mainActivity);
                cardListBox.addView(card);
                card.setData(myCards.get(i));

                cardLists.add(card);

            }
        }
        if(myCardLists.size()<1){

            myCardListBox.addView(getNoData());
        }
        if(cardLists.size()<1){

            cardListBox.addView(getNoData());
        }


    }
    private ListResult getNoData(){

        ListResult nodata=new ListResult(mainActivity);
        String noStr=mainActivity.getString(R.string.msg_no_data);
        nodata.setData(noStr);
        return nodata;
    }


    public void onLoadMyCounsels(ArrayList<MyCounselObject> myCounsels){
        removeCounselLists();
        counselLists=new ArrayList<ListCounsel>();
        for(int i=0;i<myCounsels.size();++i){
            ListCounsel list=new ListCounsel(mainActivity);
            requestListBox.addView(list);
            list.setData(myCounsels.get(i));

            counselLists.add(list);


        }
        if(counselLists.size()<1){

            requestListBox.addView(getNoData());
        }


    }



    protected void doRemove() {

        super.doRemove();
        removeCardLists();
        removeCounselLists();

        if(info!=null)
        {
            if(info.delegate==this){
                info.delegate=null;
            }
            info=null;
        }
        if(CardInfo.getInstence().delegate==this){
            CardInfo.getInstence().delegate=null;
        }

    }





 /////////////////////////////////////////









}
