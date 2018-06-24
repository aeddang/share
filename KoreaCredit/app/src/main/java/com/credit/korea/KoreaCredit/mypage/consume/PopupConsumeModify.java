package com.credit.korea.KoreaCredit.mypage.consume;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.ArrayList;

import lib.core.PageObject;
import lib.core.ViewCore;


@SuppressLint({ "NewApi", "JavascriptInterface" })
public class PopupConsumeModify extends ViewCore implements OnClickListener
{

    private ImageView imgTitle;

    private ImageButton btnComplete;

    private LinearLayout lists;


    private ArrayList<AddedGroup> adatas;
    private ArrayList<BenifitGroup> bdatas;


    public PopupConsumeModify(Context context, PageObject pageInfo) {
        super(context, pageInfo);


        View.inflate(context, R.layout.popup_consume_list, this);
        if(pageInfo.info!=null){
            adatas=(ArrayList<AddedGroup>)pageInfo.info.get("adatas");
            bdatas=(ArrayList<BenifitGroup>)pageInfo.info.get("bdatas");

        }else{


        }

        imgTitle = (ImageView) findViewById(R.id._imgTitle);
        lists = (LinearLayout) findViewById(R.id._lists);
        btnComplete = (ImageButton) findViewById(R.id._btnComplete);



        btnComplete.setOnClickListener(this);

        if(adatas!=null){
            imgTitle.setImageResource(R.drawable.consume_md_title1);
            setAddedData(adatas);
        }
        if(bdatas!=null){
            imgTitle.setImageResource(R.drawable.consume_md_title2);
            setBenifitData(bdatas);

        }







    }

    private void setBenifitData(ArrayList<BenifitGroup> datas) {


        for(int i=0;i<datas.size();++i) {

            if(datas.get(i).title.equals("기타")==false) {
                GroupSelect group = new GroupSelect(MainActivity.getInstence());
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i != 0) {

                    layout.topMargin = 10 * (int) MainActivity.getInstence().dpi;
                }

                lists.addView(group, layout);
                group.setBenifitGroup(datas.get(i));
            }
        }


    }
    private void setAddedData(ArrayList<AddedGroup> datas) {


        for(int i=0;i<datas.size();++i) {
            if(datas.get(i).title.equals("기타")==false) {
                GroupSelect group = new GroupSelect(MainActivity.getInstence());
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i != 0) {

                    layout.topMargin = 10 * (int) MainActivity.getInstence().dpi;
                }

                lists.addView(group, layout);
                group.setAddedGroup(datas.get(i));
            }
        }


    }

    protected void doResize() {
        super.doResize();

    }

    public void onClick(View v) {

        if (v == btnComplete) {
            onComplete();

        }
    }



    protected void doMovedInit() {
        super.doMovedInit();



    }

    protected void doRemove() {

        super.doRemove();

    }


    /////////////////////////////////////////


    private void onComplete() {

        //Map<String, String> params = new HashMap<String, String>();

       if(adatas!=null){
           ConsumeInfo.getInstence().modifyAddedData(adatas);

       }else if(bdatas!=null){

           ConsumeInfo.getInstence().modifyBenefitData(bdatas);
       }




    }
}
