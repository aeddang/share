package com.credit.korea.KoreaCredit.member;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.R;

import java.util.HashMap;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.SlideBox;


public class PageMember extends ViewCore implements View.OnClickListener, SlideBox.SlideBoxDelegate {

    private Button btnHome,btnFindID,btnFindPW,btnJoin,btnLogin;
    private FrameLayout btnFindIDPoint,btnFindPWPoint,btnJoinPoint,btnLoginPoint;
    private SlideBox slideBox;
    private MemberInfo mInfo;
    private int pageIdx;
    private static int PAGE_NUM=4;
    public PageMember(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_member,this);


        if(pageInfo.info!=null){
            pageIdx=(int)pageInfo.info.get("pageIdx");

        }else{
            pageIdx=0;

        }
        mInfo=MemberInfo.getInstence();


        btnHome=(Button) findViewById(R.id._btnHome);
        btnFindID=(Button) findViewById(R.id._btnFindID);
        btnLogin=(Button) findViewById(R.id._btnLogin);
        btnJoin=(Button) findViewById(R.id._btnJoin);
        btnFindPW=(Button) findViewById(R.id._btnFindPW);

        btnFindIDPoint=(FrameLayout) findViewById(R.id._btnFindIDPoint);
        btnLoginPoint=(FrameLayout) findViewById(R.id._btnLoginPoint);
        btnJoinPoint=(FrameLayout) findViewById(R.id._btnJoinPoint);
        btnFindPWPoint=(FrameLayout) findViewById(R.id._btnFindPWPoint);

        slideBox=(SlideBox) findViewById(R.id._slideBox);

        btnHome.setOnClickListener(this);
        btnFindID.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnFindPW.setOnClickListener(this);

        slideBox.initBox(this);
    }

	protected void doResize() {
    	super.doResize();

    }
    public void onClick(View v) {

        if(v==btnHome){
             mainActivity.changeViewMain();
        }else if(v==btnFindID){
             slideBox.moveIndex(1);
        }else if(v==btnFindPW){
            slideBox.moveIndex(2);

        }else if(v==btnLogin){

            slideBox.moveIndex(3);
        }else if(v==btnJoin){
            slideBox.moveIndex(0);
        }

    }


	protected void doMovedInit() {
	    super.doMovedInit();
        slideBox.setSlide(pageIdx, PAGE_NUM);

	}

    protected void doRemove() {

        super.doRemove();
        mInfo=null;
        if(slideBox!=null){
            slideBox.removeSlide();
            slideBox=null;
        }

    }
    private void setSlide(FrameLayout frame, int idx){

        if(idx<0 || idx>=PAGE_NUM){
            return;
        }
        ViewCore currentView;
        PageObject pInfo=new PageObject(Config.PAGE_MEMBER);



        switch (idx){
            case 0:
                currentView=new PageJoin(mainActivity,pInfo);
                PageJoin joinView=(PageJoin)currentView;

                break;
            case 3:
                currentView=new PageLogin(mainActivity,pInfo);
                PageLogin loginView=(PageLogin)currentView;

                break;
            default:
                if(idx==1){
                    pInfo.info=new HashMap<String,Object>();
                    pInfo.info.put("type",PageFind.TYPE_ID);

                }else if(idx==2){
                    pInfo.info=new HashMap<String,Object>();
                    pInfo.info.put("type",PageFind.TYPE_PW);

                }
                currentView=new PageFind(mainActivity,pInfo);
                PageFind findView=(PageFind)currentView;

                break;



        }

        LayoutParams layout=new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT );
        layout.gravity= Gravity.TOP;
        frame.addView(currentView,layout) ;

    }

    public void  moveInit(SlideBox slideView,FrameLayout frame,int dr,int idx){
        //Log.i("","moveInit :"+idx);

        setSlide(frame,idx+dr);


    }
    public void clearFrame(SlideBox slideView,FrameLayout cframe){
        View child=cframe.getChildAt(0);
        if(child!=null){
            if(child instanceof ViewCore==true){
                ViewCore currentView=(ViewCore)child;
                currentView.removeViewCore();
            }
        }
        cframe.removeAllViews();
    }

    public void frameSizeChange(SlideBox slideView,FrameLayout cframe,int idx){

    }
    public void moveComplete(SlideBox slideView,FrameLayout cframe,int idx){
        mainActivity.loadingStop();
        View child=cframe.getChildAt(0);
        if(child!=null){
            if(child instanceof ViewCore==true){
                ViewCore currentView=(ViewCore)child;
                currentView.movedInit();
            }


        }

        btnJoin.setSelected(false);
        btnLogin.setSelected(false);
        btnFindID.setSelected(false);
        btnFindPW.setSelected(false);

        btnJoinPoint.setVisibility(View.GONE);
        btnLoginPoint.setVisibility(View.GONE);
        btnFindIDPoint.setVisibility(View.GONE);
        btnFindPWPoint.setVisibility(View.GONE);

        switch (idx){
            case 0:

                btnJoin.setSelected(true);
                btnJoinPoint.setVisibility(View.VISIBLE);
                break;
            case 1:

                btnFindID.setSelected(true);
                btnFindIDPoint.setVisibility(View.VISIBLE);
                break;
            case 2:

                btnFindPW.setSelected(true);
                btnFindPWPoint.setVisibility(View.VISIBLE);
                break;
            case 3:

                btnLogin.setSelected(true);
                btnLoginPoint.setVisibility(View.VISIBLE);
                break;




        }
        pageIdx=idx;


    }



    public void onTouchStart(SlideBox slideView){};
    public void onTouchEnd(SlideBox slideView){};

    public void onTopSlide(SlideBox slideView){};
    public void onEndSlide(SlideBox slideView){};
    public void selectSlide(SlideBox slideView,int idx){};

}
