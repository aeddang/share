package com.credit.korea.KoreaCredit.card.recommend;


import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.card.search.ListGroupSearch;
import com.credit.korea.KoreaCredit.listresult.ListResult;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.mypage.consume.AddedGroup;
import com.credit.korea.KoreaCredit.mypage.consume.BenifitGroup;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;
import com.credit.korea.KoreaCredit.mypage.consume.DefaultObject;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.CommonUtil;
import lib.core.PageObject;


public class GroupRecommend extends FrameLayout implements View.OnClickListener,ConsumeInfo.ConsumeInfoModifyDelegate,ConsumeInfo.ConsumeInfoDelegate,MyCardInfo.MyCardDelegate {


    private LinearLayout paturnStack,interestCardStack,myCardStack;
    private Button btnPaturn;
    private ImageButton btnComplete;
    public GroupRecommendDelegate delegate;
    private  DefaultObject currentDefaultObj;
    private  ArrayList<CardObject> cards;
    private ArrayList<ListGroupRecommend>cardLists;

    private ArrayList<TextView> texts;
    private ArrayList<LinearLayout> boxs;

    private static String finalSelectKeys="";

    private boolean isCompleteStart,isInitMsg;

    public interface GroupRecommendDelegate
    {
        void onSearchStart(GroupRecommend v,String cards);


    }


    public GroupRecommend(Context context)
	{
        super(context);
        View.inflate(context, R.layout.group_cardrecommend,this);
        paturnStack = (LinearLayout) findViewById(R.id._paturnStack);
        interestCardStack = (LinearLayout) findViewById(R.id._interestCardStack);
        myCardStack = (LinearLayout) findViewById(R.id._myCardStack);

        btnPaturn = (Button) findViewById(R.id._btnPaturn);
        btnComplete = (ImageButton) findViewById(R.id._btnComplete);

        texts=new ArrayList<TextView>();
        boxs=new ArrayList<LinearLayout>();
        for(int i=0;i<3;++i){
            int tid = getResources().getIdentifier("_text" + i, "id", MainActivity.getInstence().getPackageName());
            TextView text= (TextView)findViewById(tid);
            text.setTypeface(FontFactory.getInstence().FONT_KR);
            texts.add(text);

            int bid = getResources().getIdentifier("_box" + i, "id", MainActivity.getInstence().getPackageName());
            LinearLayout box= (LinearLayout)findViewById(bid);
            box.setVisibility(View.GONE);
            boxs.add(box);



        }

        btnPaturn.setOnClickListener(this);

        btnComplete.setOnClickListener(this);

        isInitMsg=true;


        cards=MyCardInfo.getInstence().getMyCards();
        isCompleteStart=true;

        if(cards!=null) {
            onLoadMyCards(cards);
        }

    }
    public void initGroup(){
        ConsumeInfo.getInstence().delegate=this;
        ConsumeInfo.getInstence().mdDelegate=this;
        MyCardInfo.getInstence().delegate=this;

        if(cards==null) {

            MyCardInfo.getInstence().loadMyCards(false);
        }else{
            onSearchStart();
        }

    }
    public void onClick(View v) {
        if(v==btnComplete){
            onSearchStart();

        }else{
            /*
            PageObject pInfo= new PageObject(Config.POPUP_CONSUME_DEFAULT);
            Map<String,Object> pinfo=new HashMap<String,Object>();
            pInfo=new PageObject(Config.POPUP_CONSUME_DEFAULT);
            pinfo.put("myObj", currentDefaultObj);
            pInfo.dr=1;
            pInfo.info=pinfo;
            MainActivity.getInstence().addPopup(pInfo);*/

            PageObject pInfo = new PageObject(Config.PAGE_MYPAGE);
            Map<String,Object> pinfo=new HashMap<String,Object>();
            pInfo.info=pinfo;

            pInfo.info.put("pageIdx",2);

            MainActivity.getInstence().changeView(pInfo);

        }


    }
    private void onSearchStart(){

        isCompleteStart=false;
        if(checkSelectedSize()==false){
            MainActivity.getInstence().viewAlert("", R.string.msg_card_limit_card_size, null);
            return;
        }



        if(delegate!=null){
            String cardIds="";
            for(int i=0;i<cardLists.size();++i){
                boolean isCheck=cardLists.get(i).getChecked();
                if(isCheck==true){
                    if(cardIds.equals("")==true){

                        cardIds=cards.get(i).seq;
                    }else{
                        cardIds += ("|"+cards.get(i).seq);
                    }

                }

            }
            delegate.onSearchStart(this,cardIds);
        }
    }
    public  void removeGroup(){
        delegate=null;
        removeCardLists();
        if(ConsumeInfo.getInstence().delegate==this){
            ConsumeInfo.getInstence().delegate=null;
        }
        if(ConsumeInfo.getInstence().mdDelegate==this){
            ConsumeInfo.getInstence().mdDelegate=null;
        }
        if(MyCardInfo.getInstence().delegate==this){
            MyCardInfo.getInstence().delegate=null;
        }


    }



    public void  onModifyDefaultData(boolean result){

        if(result==false){
            onModifyError();
            return;
        }
        MainActivity.getInstence().changeViewBack();
        ConsumeInfo.getInstence().loadDefaultData(true);

    }
    public void  onModifyAddedDatas(boolean result){}
    public void  onModifyBenifitDatas(boolean result){}

    private void onModifyError(){

        MainActivity.getInstence().viewAlert("",R.string.msg_consume_modify_error,null);
    }

    private void addInfo(final int type){
        if(isInitMsg==false){
            return;
        }
        isInitMsg=false;
        AlertObject aInfo=new AlertObject();

        aInfo.isDimed=true;

        aInfo.subTitleID=R.string.msg_card_more_recommend;
        if(MemberInfo.getInstence().getLoginState()==false){

            aInfo.selectID= R.string.msg_card_more_join;
        }else{

            if(type==0){
                aInfo.selectID= R.string.msg_card_more_paturn;
            }else{
                aInfo.selectID= R.string.msg_card_more_card;
            }

        }




        AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,new AlertView.AlertViewDelegate(){

            @Override
            public void onSelected(AlertView v, int selectIdx) {
                if(selectIdx==0){

                    if(MemberInfo.getInstence().getLoginState()==false){
                        PageObject pInfo= new PageObject(Config.PAGE_MEMBER);
                        MainActivity.getInstence().changeView(pInfo);

                    }else{

                        PageObject pInfo=new PageObject(Config.PAGE_MYPAGE);
                        Map<String,Object> pinfo=new HashMap<String,Object>();
                        pInfo.info=pinfo;
                        if(type==0){


                            pInfo.info.put("pageIdx",3);

                        }else{


                            pInfo.info.put("pageIdx",2);

                        }

                        MainActivity.getInstence().changeView(pInfo);
                    }


                }else{


                }

            }
        });
        MainActivity.getInstence().body.addView(alertView);
    }
    public void setResult(float gettotal ,float usedtotal){

        setText(1, Html.fromHtml("연간혜택금액예상 <font color='#00bff3'>" + CommonUtil.getPriceStr(gettotal) + "만원</font>"));
        setText(2, Html.fromHtml("연간이용금액추천 <font color='#00bff3'>"+CommonUtil.getPriceStr(usedtotal)+"만원</font>"));
    }


    private void setText(int idx,CharSequence value) {

        boxs.get(idx).setVisibility(View.VISIBLE);

        texts.get(idx).setText(value);


    }
    public void onLoadDefaultData(DefaultObject defaultObject){
        paturnStack.removeAllViews();
        currentDefaultObj=defaultObject;
        ListGroupSearch list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("연간카드이용금액",defaultObject.getConsumePerYearOnly());
        paturnStack.addView(list);

        list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("월최소이용금액",defaultObject.getConsumePerMonthMin());
        paturnStack.addView(list);

        list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("가계연간수입",defaultObject.getIncomePerYear()+"...");
        paturnStack.addView(list);

        setText(0, Html.fromHtml("연간이용금액(소비패턴) <font color='#00bff3'>"+defaultObject.getConsumePerYearOnly()+"</font>"));

        if(isCompleteStart==true){
            onSearchStart();
        }

    }
    public void onLoadAddedDatas(ArrayList<AddedGroup> addeds){}
    public void onLoadBenifitDatas(ArrayList<BenifitGroup> benifits){}


    public void onModifyMyCards(){
        MyCardInfo.getInstence().loadMyCards(true);
        onSearchStart();

    }
    private boolean checkSelectedSize(){

        int num=0;
        for(int i=0;i<cardLists.size();++i){
            boolean isCheck=cardLists.get(i).getChecked();
            if(isCheck==true){

                num++;
            }


        }
        if(num>4){
            return false;

        }else{
            return  true;
        }
    }


    private void removeCardLists(){
        Log.i("","REMOVE CARD "+finalSelectKeys);
        if(cardLists!=null){
            finalSelectKeys="";

            for(int i=0;i<cardLists.size();++i){
                boolean isCheck=cardLists.get(i).getChecked();
                if(isCheck==true){
                    if(finalSelectKeys.equals("")==true){

                        finalSelectKeys=cards.get(i).seq;
                    }else{
                        finalSelectKeys += ("|"+cards.get(i).seq);
                    }

                }
                cardLists.get(i).removeList();

            }
            cardLists.clear();
            cardLists=null;
        }

        Log.i("","REMOVE CARD COMPLETE "+finalSelectKeys);
    }
    public void onLoadMyCards(ArrayList<CardObject> myCards){

        removeCardLists();

        Log.i("","onLoadMyCards COMPLETE "+finalSelectKeys);
        myCardStack.removeAllViews();
        interestCardStack.removeAllViews();
        cards=myCards;
        cardLists=new ArrayList<ListGroupRecommend>();


        for(int i=0;i<myCards.size();++i){

            CardObject obj=myCards.get(i);
            ListGroupRecommend  card=new ListGroupRecommend(MainActivity.getInstence());
            if(finalSelectKeys.equals("")==true){

               card.setData(obj, obj.isMine);

            }else{
                int find=finalSelectKeys.indexOf(obj.seq);
                if(find==-1){
                    card.setData(obj,false);
                }else{
                    card.setData(obj,true);
                }
            }




            cardLists.add(card);
            if(obj.isMine==true){
                myCardStack.addView(card);
            }else{
                interestCardStack.addView(card);

            }
        }





        currentDefaultObj=ConsumeInfo.getInstence().getDefaultData();
        if(currentDefaultObj!=null) {
            onLoadDefaultData(currentDefaultObj);
        }else {
            ConsumeInfo.getInstence().loadDefaultData(false);
        }

    }

    private ListResult getNoData(){

        ListResult nodata=new ListResult(MainActivity.getInstence());
        String noStr=MainActivity.getInstence().getString(R.string.msg_no_data);
        nodata.setData(noStr);
        return nodata;
    }
    public void onLoadMyCounsels(ArrayList<MyCounselObject> myCounsels){}

    public  void isActiveCheck(){
        if(cardLists==null){

            return;
        }
        if(cardLists.size()<1){
            addInfo(1);
        }
        if(currentDefaultObj==null){

            return;
        }
        if(currentDefaultObj.isActive==false){

            addInfo(0);
        }

    }


 /////////////////////////////////////////









}
