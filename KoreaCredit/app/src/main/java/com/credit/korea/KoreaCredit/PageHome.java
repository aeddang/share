package com.credit.korea.KoreaCredit;


import android.annotation.SuppressLint;
import android.content.Context;

import android.content.DialogInterface;
import android.view.View;

import android.widget.Button;

import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.credit.korea.KoreaCredit.member.MemberInfo;

import java.util.ArrayList;
import java.util.HashMap;

import lib.core.PageObject;
import lib.core.ViewCore;


@SuppressLint("NewApi")
public class PageHome extends ViewCore implements View.OnClickListener{






    private ArrayList<Button> myBtns;
    private Button btnLogin,btnJoin,btnLogout,btnFind,btnInterest,btnFindPW,btnFindID;
    private ImageButton btnQnA,btnEvent,btnGuide;
    private LinearLayout btnWhat,btnSearch,btnRecommend,btnBook;



    public PageHome(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_home,this);



        btnLogin=(Button) findViewById(R.id._btnLogin);
        btnLogout=(Button) findViewById(R.id._btnLogout);
        btnJoin=(Button) findViewById(R.id._btnJoin);
        btnFindPW=(Button) findViewById(R.id._btnFindPW);
        btnFindID=(Button) findViewById(R.id._btnFindID);
        btnQnA=(ImageButton) findViewById(R.id._btnQnA);
        btnEvent=(ImageButton) findViewById(R.id._btnEvent);
        btnGuide=(ImageButton) findViewById(R.id._btnGuide);
        btnWhat=(LinearLayout) findViewById(R.id._btnWhat);
        btnSearch=(LinearLayout) findViewById(R.id._btnSearch);
        btnRecommend=(LinearLayout) findViewById(R.id._btnRecommend);
        btnBook=(LinearLayout) findViewById(R.id._btnBook);

        btnFind=(Button) findViewById(R.id._btnFind);
        btnInterest=(Button) findViewById(R.id._btnInterest);

        myBtns=new ArrayList<Button>();
        for(int i=0;i<4;++i){
            int rid = this.getResources().getIdentifier( "_btnMypage"+i, "id", mainActivity.getPackageName());
            Button btn=(Button) findViewById(rid);
            myBtns.add(btn);
            btn.setOnClickListener(this);
            btn.setVisibility(View.GONE);

        }


        btnLogout.setOnClickListener(this);

        btnLogin.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnFindPW.setOnClickListener(this);
        btnFindID.setOnClickListener(this);

        btnQnA.setOnClickListener(this);
        btnEvent.setOnClickListener(this);
        btnGuide.setOnClickListener(this);

        btnFind.setOnClickListener(this);
        btnInterest.setOnClickListener(this);

        btnWhat.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRecommend.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        setLoginState();
    }
    private void setLoginState(){

        boolean isLogin= MemberInfo.getInstence().getLoginState();

        if(isLogin==true){

            btnJoin.setVisibility(View.GONE);
            btnFindID.setVisibility(View.GONE);
            btnFindPW.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            for(int i=0;i<myBtns.size();++i){
                myBtns.get(i).setVisibility(View.VISIBLE);

            }
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            btnFindID.setVisibility(View.VISIBLE);
            btnFindPW.setVisibility(View.VISIBLE);
            btnJoin.setVisibility(View.VISIBLE);
            for(int i=0;i<myBtns.size();++i){
                myBtns.get(i).setVisibility(View.GONE);

            }
        }


    }
	protected void doResize() {
    	super.doResize();

    }
    public void onClick(View v) {


        PageObject pInfo=null;
        boolean isPop=false;


        if(v==btnInterest){
            isPop=true;
            pInfo=new PageObject(Config.POPUP_CARD_HIT);
            pInfo.dr=1;
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("title","인기카드");

        }else if(v==btnLogout){
            if(MemberInfo.getInstence().getLoginState()==true){
                mainActivity.viewAlertSelect("", R.string.msg_logout_check, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    if(which==-1){
                        MemberInfo.getInstence().logOut();
                        setLoginState();
                    }
                    }
                });

                return;

            }

        }else if(v==btnLogin){

                pInfo=new PageObject(Config.PAGE_MEMBER);
                pInfo.info=new HashMap<String,Object>();
                pInfo.info.put("pageIdx",3);



        }

        else if(v==btnJoin){
            if(MemberInfo.getInstence().getLoginState()==true){
                pInfo=new PageObject(Config.PAGE_MYPAGE);

            }else{
                pInfo=new PageObject(Config.PAGE_MEMBER);
                pInfo.info=new HashMap<String,Object>();
                pInfo.info.put("pageIdx",0);

            }



        }else if(v==btnFindID){

            pInfo=new PageObject(Config.PAGE_MEMBER);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("pageIdx",1);
        }else if(v==btnFindPW){

            pInfo=new PageObject(Config.PAGE_MEMBER);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("pageIdx",2);
        }else if(v==btnQnA){
            isPop=true;
            pInfo=new PageObject(Config.POPUP_WEB);
            pInfo.dr=1;
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("title","묻고답하기");
            pInfo.info.put("pageUrl",Config.WEB_QNA+"?uid="+MemberInfo.getInstence().getID());

        }else if(v==btnEvent){
            isPop=true;
            pInfo=new PageObject(Config.POPUP_WEB);
            pInfo.dr=1;
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("title","이벤트");
            pInfo.info.put("pageUrl",Config.WEB_EVENT+"?uid="+MemberInfo.getInstence().getID());

        }else if(v==btnGuide){


            isPop=true;
            pInfo=new PageObject(Config.POPUP_WEB);
            pInfo.dr=1;
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("title","이용안내");
            pInfo.info.put("pageUrl",Config.WEB_GUIDE);

        }else if(v==btnWhat){
            pInfo=new PageObject(Config.PAGE_CARD);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("pageIdx",0);

        }else if(v==btnSearch){

            pInfo=new PageObject(Config.PAGE_CARD);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("pageIdx",1);

        }else if( v==btnFind){
            isPop=true;
            pInfo=new PageObject(Config.POPUP_CARD_FIND);
            pInfo.dr=1;

        }else if(v==btnRecommend){
            pInfo=new PageObject(Config.PAGE_CARD);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("pageIdx",2);

        }else if(v==btnBook){

            if(MemberInfo.getInstence().getLoginState()==false) {
                MainActivity ac= (MainActivity) MainActivity.getInstence();
                ac.checkLoginPage();
                return;
            }
            pInfo=new PageObject(Config.PAGE_CARD);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("pageIdx",3);

        }else {
            int idx = myBtns.indexOf(v);
            if(idx!=-1){
                pInfo=new PageObject(Config.PAGE_MYPAGE);
                pInfo.info=new HashMap<String,Object>();
                pInfo.info.put("pageIdx",idx);

            }


        }
        if(pInfo!=null){
            if(isPop==false) {
                mainActivity.changeView(pInfo);
            }else{

                mainActivity.addPopup(pInfo);
            }
        }


    }


	protected void doMovedInit() {
	    super.doMovedInit();


	}

    protected void doRemove() {

        super.doRemove();



    }


}
