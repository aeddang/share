package com.credit.korea.KoreaCredit.mypage;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.mypage.consume.AddedGroup;
import com.credit.korea.KoreaCredit.mypage.consume.BenifitGroup;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;
import com.credit.korea.KoreaCredit.mypage.consume.DefaultObject;
import com.credit.korea.KoreaCredit.mypage.consume.GroupSelect;
import com.credit.korea.KoreaCredit.mypage.consume.ListBenifit;

import java.util.ArrayList;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.Gesture;


@SuppressLint({ "NewApi", "JavascriptInterface" })
public class ListAddedModify extends LinearLayout implements ConsumeInfo.ConsumeInfoDelegate ,ConsumeInfo.ConsumeInfoModifyDelegate
{

    private LinearLayout lists;
    private boolean isInit=false;
    public ArrayList<AddedGroup> adatas;


    public ListAddedModify(Context context) {
        super(context);


    }
    public ListAddedModify(Context _context,AttributeSet attrs) {

        super(_context,attrs,0);

    }
    public void setActive(boolean ac){

        if(ac==true){
            if(isInit==false){
                ConsumeInfo.getInstence().delegate=this;
                ConsumeInfo.getInstence().mdDelegate=this;
                ConsumeInfo.getInstence().loadAddedData(false);
            }
            this.setVisibility(View.VISIBLE);
        }else{
            this.setVisibility(View.GONE);

        }

    }
    public void remove(){
        if(ConsumeInfo.getInstence().delegate == this){
            ConsumeInfo.getInstence().delegate=null;
        }

    }

    private void setAddedData(ArrayList<AddedGroup> datas) {

        this.removeAllViews();
        Log.i("","setAddedData "+datas.size());
        for(int i=0;i<datas.size();++i) {
            GroupSelect group = new GroupSelect(MainActivity.getInstence());
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i!=0){

                layout.topMargin=10*(int)MainActivity.getInstence().dpi;
            }

            this.addView(group, layout);
            group.setAddedGroup(datas.get(i));
        }


    }




    /////////////////////////////////////////
    public void onLoadDefaultData(DefaultObject defaultObject){
    }
    public void onLoadAddedDatas(ArrayList<AddedGroup> addeds){
        isInit=true;
        adatas=addeds;
        setAddedData(adatas);
    }
    public void onLoadBenifitDatas(ArrayList<BenifitGroup> benifits){

    }

    public void onModifyDefaultData(boolean result){


    }
    public void onModifyAddedDatas(boolean result){
        if(result==false){
            onModifyError();
            return;
        }
        Log.i("","onModifyAddedDatas ");

        MainActivity.getInstence().viewAlert("",R.string.msg_consume_added_modify_complete,null);
        ConsumeInfo.getInstence().loadAddedData(true);
    }
    public void onModifyBenifitDatas(boolean result){

    }

    private void onModifyError(){

        MainActivity.getInstence().viewAlert("",R.string.msg_consume_modify_error,null);
    }
}
