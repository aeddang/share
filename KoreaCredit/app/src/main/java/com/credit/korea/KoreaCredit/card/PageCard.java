package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.card.book.PageBook;
import com.credit.korea.KoreaCredit.card.recommend.PageCardRecommend;
import com.credit.korea.KoreaCredit.card.search.PageCardSearch;
import com.credit.korea.KoreaCredit.card.thiscard.PageCardThis;
import com.credit.korea.KoreaCredit.member.MemberInfo;

import java.util.HashMap;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.SlideBox;


public class PageCard extends ViewCore implements View.OnClickListener, SlideBox.SlideBoxDelegate {

    private Button btnHome,btnWhat,btnSearch,btnRecommend,btnBook,btnMypage;
    private FrameLayout btnWhatPoint,btnSearchPoint,btnRecommendPoint,btnBookPoint;
    private SlideBox slideBox;
    private MemberInfo mInfo;
    private int pageIdx;

    private static int finalIndex=0;
    private static int PAGE_NUM=4;
    public PageCard(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_card,this);


        if(pageInfo.info!=null){
            pageIdx=(int)pageInfo.info.get("pageIdx");
            Log.i("","pageIdx : "+pageIdx);
        }else{
            pageIdx=finalIndex;

            Log.i("","finalIndex : "+finalIndex);

        }


        mInfo=MemberInfo.getInstence();
        CardInfo cardInfo=CardInfo.getInstence();

        btnHome=(Button) findViewById(R.id._btnHome);
        btnWhat=(Button) findViewById(R.id._btnWhat);
        btnRecommend=(Button) findViewById(R.id._btnRecommend);
        btnSearch=(Button) findViewById(R.id._btnSearch);
        btnBook=(Button) findViewById(R.id._btnBook);
        btnMypage=(Button) findViewById(R.id._btnMypage);

        btnWhatPoint=(FrameLayout) findViewById(R.id._btnWhatPoint);
        btnRecommendPoint=(FrameLayout) findViewById(R.id._btnRecommendPoint);
        btnSearchPoint=(FrameLayout) findViewById(R.id._btnSearchPoint);
        btnBookPoint=(FrameLayout) findViewById(R.id._btnBookPoint);


        slideBox=(SlideBox) findViewById(R.id._slideBox);

        btnHome.setOnClickListener(this);
        btnWhat.setOnClickListener(this);
        btnRecommend.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        btnMypage.setOnClickListener(this);


        slideBox.initBox(this);
    }

	protected void doResize() {
    	super.doResize();

    }
    public void onClick(View v) {

        if(v==btnHome){
             mainActivity.changeViewMain();
        }else if(v==btnWhat){
             slideBox.moveIndex(0);
        }else if(v==btnSearch){
            slideBox.moveIndex(1);

        }else if(v==btnRecommend){

            slideBox.moveIndex(2);
        }else if(v==btnBook){
            if(MemberInfo.getInstence().getLoginState()==false) {
                MainActivity ac= (MainActivity) MainActivity.getInstence();
                ac.checkLoginPage();
                return;
            }
            slideBox.moveIndex(3);
        }else if(v==btnMypage){
            PageObject pInfo=new PageObject(Config.PAGE_MYPAGE);
          //  pInfo.info=new HashMap<String,Object>();
         //   pInfo.info.put("pageIdx",0);
            mainActivity.changeView(pInfo);
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
        PageObject pInfo=new PageObject(Config.PAGE_CARD);




        
        switch (idx){
            case 0:
                currentView=new PageCardThis(mainActivity,pInfo);



                break;
            case 1:
                currentView=new PageCardSearch(mainActivity,pInfo);



                break;
            case 2:
                currentView=new PageCardRecommend(mainActivity,pInfo);


                break;

            default:
                currentView=new PageBook(mainActivity,pInfo);

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
        btnWhat.setSelected(false);
        btnRecommend.setSelected(false);
        btnSearch.setSelected(false);
        btnBook.setSelected(false);



        btnWhatPoint.setVisibility(View.GONE);
        btnRecommendPoint.setVisibility(View.GONE);
        btnSearchPoint.setVisibility(View.GONE);
        btnBookPoint.setVisibility(View.GONE);


        switch (idx){
            case 0:

                btnWhat.setSelected(true);
                btnWhatPoint.setVisibility(View.VISIBLE);
                break;
            case 1:

                btnSearch.setSelected(true);
                btnSearchPoint.setVisibility(View.VISIBLE);
                break;
            case 2:
                btnRecommend.setSelected(true);
                btnRecommendPoint.setVisibility(View.VISIBLE);

                break;
            case 3:

                btnBook.setSelected(true);
                btnBookPoint.setVisibility(View.VISIBLE);
                break;




        }
        pageIdx=idx;
        finalIndex=idx;

    }



    public void onTouchStart(SlideBox slideView){};
    public void onTouchEnd(SlideBox slideView){};

    public void onTopSlide(SlideBox slideView){};
    public void onEndSlide(SlideBox slideView){};
    public void selectSlide(SlideBox slideView,int idx){};

}
