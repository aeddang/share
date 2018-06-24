package com.credit.korea.KoreaCredit.mypage;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.mypage.consume.PageConsume;
import com.credit.korea.KoreaCredit.member.MemberInfo;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.SlideBox;


public class PageMypage extends ViewCore implements View.OnClickListener, SlideBox.SlideBoxDelegate {

    private Button btnHome,btnInfo,btnCard,btnSearch,btnPaturn;
    private FrameLayout btnInfoPoint,btnCardPoint,btnSearchPoint,btnPaturnPoint;
    private SlideBox slideBox;
    private MemberInfo mInfo;
    private int pageIdx;

    private static int fianlIndex=0;
    private static int PAGE_NUM=4;
    public PageMypage(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_mypage,this);


        if(pageInfo.info!=null){
            pageIdx=(int)pageInfo.info.get("pageIdx");

        }else{
            pageIdx=fianlIndex;

        }
        mInfo=MemberInfo.getInstence();


        btnHome=(Button) findViewById(R.id._btnHome);
        btnInfo=(Button) findViewById(R.id._btnInfo);
        btnCard=(Button) findViewById(R.id._btnCard);
        btnSearch=(Button) findViewById(R.id._btnSearch);
        btnPaturn=(Button) findViewById(R.id._btnPaturn);

        btnInfoPoint=(FrameLayout) findViewById(R.id._btnInfoPoint);
        btnCardPoint=(FrameLayout) findViewById(R.id._btnCardPoint);
        btnSearchPoint=(FrameLayout) findViewById(R.id._btnSearchPoint);
        btnPaturnPoint=(FrameLayout) findViewById(R.id._btnPaturnPoint);

        slideBox=(SlideBox) findViewById(R.id._slideBox);

        btnHome.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnCard.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnPaturn.setOnClickListener(this);


        slideBox.initBox(this);
    }

	protected void doResize() {
    	super.doResize();

    }
    public void onClick(View v) {

        if(v==btnHome){
             mainActivity.changeViewMain();
        }else if(v==btnInfo){
             slideBox.moveIndex(0);
        }else if(v==btnCard){
            slideBox.moveIndex(1);

        }else if(v==btnSearch){

            slideBox.moveIndex(3);
        }else if(v==btnPaturn){
            slideBox.moveIndex(2);
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
                currentView=new PageMyInfo(mainActivity,pInfo);
                PageMyInfo pageMyInfo=(PageMyInfo)currentView;


                break;
            case 1:
                currentView=new PageMyCard(mainActivity,pInfo);
                PageMyCard pageMyCard=(PageMyCard)currentView;


                break;
            case 2:

                currentView=new PageConsume(mainActivity,pInfo);
                PageConsume pageConsumeo=(PageConsume)currentView;
                break;
            case 3:
                currentView=new PageMySearch(mainActivity,pInfo);
                PageMySearch pageMySearch=(PageMySearch)currentView;


                break;

            default:


                return;




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
        btnInfo.setSelected(false);
        btnCard.setSelected(false);
        btnSearch.setSelected(false);
        btnPaturn.setSelected(false);



        btnInfoPoint.setVisibility(View.GONE);
        btnCardPoint.setVisibility(View.GONE);
        btnSearchPoint.setVisibility(View.GONE);
        btnPaturnPoint.setVisibility(View.GONE);


        switch (idx){
            case 0:

                btnInfo.setSelected(true);
                btnInfoPoint.setVisibility(View.VISIBLE);
                break;
            case 1:

                btnCard.setSelected(true);
                btnCardPoint.setVisibility(View.VISIBLE);
                break;
            case 2:

                btnPaturn.setSelected(true);
                btnPaturnPoint.setVisibility(View.VISIBLE);
                break;
            case 3:
                btnSearch.setSelected(true);
                btnSearchPoint.setVisibility(View.VISIBLE);

                break;




        }
        pageIdx=idx;
        fianlIndex=idx;

    }



    public void onTouchStart(SlideBox slideView){};
    public void onTouchEnd(SlideBox slideView){};

    public void onTopSlide(SlideBox slideView){};
    public void onEndSlide(SlideBox slideView){};
    public void selectSlide(SlideBox slideView,int idx){};

}
