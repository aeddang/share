package com.credit.korea.KoreaCredit.mypage;


import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.member.ListInterest;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.member.MyInfoObject;
import com.credit.korea.KoreaCredit.member.PopupModify;

import java.util.ArrayList;
import java.util.HashMap;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.sns.SNSInfo;
import lib.view.RackableScrollView;


public class PageMyInfo extends ViewCore implements View.OnClickListener,MyPageInfo.InfoInfoDelegate ,PopupModify.PopupModifyDelegate{

    private TextView textID,textPW,textEmail,textPhone;
    private ImageButton btnModify,btnExit;
    private Button checkSmsOk, checkPushOk;
    private ImageView txtSmsOk, txtPushOk;
    private ImageView imgSns;
    private PopupModify popupModify;
    private MyInfoObject myObj;
    private LinearLayout inputIDBox;

    public PageMyInfo(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_mypage_info,this);
        imgSns=(ImageView) findViewById(R.id._imgSns);
        inputIDBox=(LinearLayout) findViewById(R.id._inputIDBox);
        textID=(TextView) findViewById(R.id._textID);
        textPW=(TextView) findViewById(R.id._textPW);
        textEmail=(TextView) findViewById(R.id._textEmail);
        textPhone=(TextView) findViewById(R.id._textPhone);


        checkSmsOk = (Button) findViewById(R.id._checkSmsOk);
        checkPushOk = (Button) findViewById(R.id._checkPushOk);

        txtSmsOk = (ImageView) findViewById(R.id._txtSmsOk);
        txtPushOk = (ImageView) findViewById(R.id._txtPushOk);
        btnModify=(ImageButton) findViewById(R.id._btnModify);
        btnExit=(ImageButton) findViewById(R.id._btnExit);

        btnModify.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        checkSmsOk.setSelected(false);
        checkPushOk.setSelected(false);




        myObj=MyPageInfo.getInstence().getMyInfo();
        if(myObj!=null){

            onLoadMyInfo(myObj);
        }


    }
    protected void doResetOnListener() {
        MyPageInfo.getInstence().infoDelegate=this;
    }
	protected void doResize()
    {
    	super.doResize();

    }
    public void onClick(View v) {

        if(v == btnModify){
            addPopup();
        }else if(v == btnExit){
            MainActivity.getInstence().viewAlertSelect("",R.string.msg_modify_exit,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PageObject pInfo;
                    if(which==-1){

                        MemberInfo.getInstence().memberExit();

                    }else{


                    }

                }
            });

        }

    }

	protected void doMovedInit() {
	    super.doMovedInit();

        MyPageInfo.getInstence().infoDelegate=this;
        if(myObj==null) {
            MyPageInfo.getInstence().loadMyInfo(false);
        }

	}

    public void onModifyComplete(){

        MyPageInfo.getInstence().loadMyInfo(true);
        removePopup();
    }
    protected void doRemovePopup(ViewCore pop) {
        if(pop==popupModify){
            popupModify=null;

        }

    }
    private void addPopup(){
        removePopup();
        if(myObj==null){
            MainActivity.getInstence().viewAlert("",R.string.msg_modify_data_none,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PageObject pInfo;
                    if(which==-1){

                        MyPageInfo.getInstence().loadMyInfo(true);

                    }else{


                    }

                }
            });
            return;
        }
        PageObject pInfo=new PageObject(Config.POPUP_MODIFY);
        pInfo.dr=1;
        pInfo.info=new HashMap<String,Object>();
        pInfo.info.put("myObj",myObj );

        popupModify=(PopupModify) mainActivity.addPopup(pInfo);
        popupModify.delegate=this;
    }
    private void removePopup(){
        if(popupModify!=null){
            mainActivity.removePopup(popupModify);
            popupModify=null;
        }

    }
    protected void doRemove() {

        super.doRemove();
        removePopup();

        if(MyPageInfo.getInstence().infoDelegate==this)
        {
            MyPageInfo.getInstence().infoDelegate = null;
        }


    }
    public void onLoadMyInfo(MyInfoObject obj)
    {
        myObj=obj;
        if(myObj.snsType==-1){
            imgSns.setVisibility(View.GONE);
            inputIDBox.setVisibility(View.VISIBLE);
            textID.setText(obj.id);

        }else{
            imgSns.setVisibility(View.VISIBLE);
            inputIDBox.setVisibility(View.GONE);
            switch(myObj.snsType){
                case SNSInfo.TYPE_GOOGLE:

                    imgSns.setImageResource(R.drawable.img_sns_g);
                    break;
                case SNSInfo.TYPE_NAVER:

                    imgSns.setImageResource(R.drawable.img_sns_n);
                    break;
                case SNSInfo.TYPE_FACEBOOK:

                    imgSns.setImageResource(R.drawable.img_sns_f);
                    break;
                case SNSInfo.TYPE_KAKAO:

                    imgSns.setImageResource(R.drawable.img_sns_k);
                    break;

            }
        }


        textEmail.setText(obj.email);
        textPhone.setText(obj.phone);


       // checkSmsOk.setSelected(myObj.isSMS);
        if(myObj.isSMS==true){

            txtSmsOk.setImageResource(R.drawable.myinfo_text3);
        }else{
            txtSmsOk.setImageResource(R.drawable.myinfo_text4);

        }

        //checkPushOk.setSelected(myObj.isPush);
        if(myObj.isPush==true){

            txtPushOk.setImageResource(R.drawable.myinfo_text3);
        }else{
            txtPushOk.setImageResource(R.drawable.myinfo_text4);

        }





    }




 /////////////////////////////////////////









}
