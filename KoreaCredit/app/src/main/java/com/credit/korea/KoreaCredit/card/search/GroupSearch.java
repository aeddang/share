package com.credit.korea.KoreaCredit.card.search;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.mypage.consume.AddedGroup;
import com.credit.korea.KoreaCredit.mypage.consume.BenifitGroup;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;
import com.credit.korea.KoreaCredit.mypage.consume.DefaultObject;
import com.credit.korea.KoreaCredit.mypage.MyPageInfo;
import com.credit.korea.KoreaCredit.mypage.MySearchObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;


public class GroupSearch extends FrameLayout implements View.OnClickListener,ConsumeInfo.ConsumeInfoModifyDelegate,ConsumeInfo.ConsumeInfoDelegate ,MyPageInfo.SearchDelegate {


    private LinearLayout searchStack,paturnStack;
    private Button btnSearch,btnPaturn;
    private ImageButton btnComplete;
    public GroupSearchDelegate delegate;
    private  DefaultObject currentDefaultObj;
    private  MySearchObject currentMySearchObject;
    private boolean isInitMsg;
    public interface GroupSearchDelegate
    {
        void onSearchStart(GroupSearch v);


    }


    public GroupSearch(Context context)
	{
        super(context);
        isInitMsg=true;
        View.inflate(context, R.layout.group_cardsearch,this);
        searchStack = (LinearLayout) findViewById(R.id._searchStack);
        paturnStack = (LinearLayout) findViewById(R.id._paturnStack);

        btnSearch = (Button) findViewById(R.id._btnSearch);
        btnPaturn = (Button) findViewById(R.id._btnPaturn);
        btnComplete = (ImageButton) findViewById(R.id._btnComplete);
        btnSearch.setOnClickListener(this);
        btnPaturn.setOnClickListener(this);
        btnComplete.setOnClickListener(this);

        currentMySearchObject=MyPageInfo.getInstence().getMySearchOption();

        if(currentMySearchObject!=null) {
            onLoadMySerch(currentMySearchObject);
        }

    }
    public void initGroup(){
        ConsumeInfo.getInstence().delegate=this;
        ConsumeInfo.getInstence().mdDelegate=this;
        MyPageInfo.getInstence().searchDelegate=this;


        if(currentMySearchObject==null) {
            MyPageInfo.getInstence().loadMySearchOption(false);
        }


    }
    private void onSearchStart(){


        if(delegate!=null){

            delegate.onSearchStart(this);
        }
    }
    public void onClick(View v) {
        if(v==btnComplete){
            onSearchStart();

        }else{



            PageObject pInfo = new PageObject(Config.PAGE_MYPAGE);
            Map<String,Object> pinfo=new HashMap<String,Object>();
            pInfo.info=pinfo;
            if(v==btnPaturn) {
                pInfo.info.put("pageIdx",2);

            }else {

                pInfo.info.put("pageIdx",3);

            }



            MainActivity.getInstence().changeView(pInfo);

        }


    }

    public  void removeGroup(){
        delegate=null;
        if(ConsumeInfo.getInstence().delegate==this){
            ConsumeInfo.getInstence().delegate=null;
        }
        if(ConsumeInfo.getInstence().mdDelegate==this){
            ConsumeInfo.getInstence().mdDelegate=null;
        }
        if(MyPageInfo.getInstence().searchDelegate==this){
            MyPageInfo.getInstence().searchDelegate=null;
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
    public void onModifyMySerch(boolean result){

        if(result==false){
            onModifyError();
            return;
        }


        MyPageInfo.getInstence().loadMySearchOption(true);
        MainActivity.getInstence().changeViewBack();

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
        }else {
            if (type == 0) {
                aInfo.selectID = R.string.msg_card_more_paturn;
            } else {
                aInfo.selectID = R.string.msg_card_more_search;
            }
        }

        AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,new AlertView.AlertViewDelegate(){

            @Override
            public void onSelected(AlertView v, int selectIdx) {
                if(selectIdx==0){

                    if(MemberInfo.getInstence().getLoginState()==false){
                        PageObject pInfo= new PageObject(Config.PAGE_MEMBER);
                        MainActivity.getInstence().changeView(pInfo);

                    }else {
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
    public  void onLoadMySerch(MySearchObject obj){
        searchStack.removeAllViews();

        currentMySearchObject = obj;
        int num=0;
        for(int i=0;i< DataFactory.SEARCH_TYPES.length;++i){
            String value=obj.getValue(i);
            if(value.equals("")==false){
                ListGroupSearch list=new ListGroupSearch(MainActivity.getInstence());
                if(num==2) {
                    list.setData(DataFactory.SEARCH_TYPES[i], value+"...");
                }else{

                    list.setData(DataFactory.SEARCH_TYPES[i], value);
                }
                num++;

                searchStack.addView(list);
                if(num==3) {
                    break;
                }

            }

        }

        currentDefaultObj=ConsumeInfo.getInstence().getDefaultData();
        if(currentDefaultObj!=null) {
            onLoadDefaultData(currentDefaultObj);
        }else {
            ConsumeInfo.getInstence().loadDefaultData(false);
        }
    }
    public  void isActiveCheck(){
        if(currentMySearchObject==null){

            return;
        }
        if(currentMySearchObject.isActive==false){
            addInfo(1);
        }
        if(currentDefaultObj==null){

            return;
        }
        if(currentDefaultObj.isActive==false){
            addInfo(0);
        }

    }
    public void onLoadDefaultData(DefaultObject defaultObject){
        paturnStack.removeAllViews();
        currentDefaultObj=defaultObject;
        ListGroupSearch list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("연간이용금액",defaultObject.getConsumePerYearOnly());
        paturnStack.addView(list);

        list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("월최소이용금액",defaultObject.getConsumePerMonthMin());
        paturnStack.addView(list);

        list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("가계연간수입",defaultObject.getIncomePerYear()+"...");
        paturnStack.addView(list);



    }
    public void onLoadAddedDatas(ArrayList<AddedGroup> addeds){}
    public void onLoadBenifitDatas(ArrayList<BenifitGroup> benifits){}





 /////////////////////////////////////////









}
