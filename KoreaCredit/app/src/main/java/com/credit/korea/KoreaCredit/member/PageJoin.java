package com.credit.korea.KoreaCredit.member;


import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.SNSManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.sns.SNSInfo;
import lib.sns.SNSUserObject;



public class PageJoin extends ViewCore implements View.OnClickListener,
        MemberInfo.JoinInfoDelegate ,SNSManager.SNSManagerDelegate,TextView.OnEditorActionListener,TextWatcher {


    private LinearLayout inputIDBox;
    private EditText inputID, inputPW, inputPWCheck, inputPhone0, inputPhone1, inputPhone2, inputEmail0, inputEmail1, inputSendPW;
    private ArrayList<EditText> inputA;
    private Button btnIDCheck, btnProvision, btnPrivacy, btnSendPW, btnPWConfirm;

    private Button btnN, btnG, btnF, btnC;

    private ImageButton btnComplete;
    private ImageView imgSns;
    private Button checkProvision, checkPrivacy, checkSendSMS, checkSendEMail;//check
    private Button checkSmsOk, checkPushOk,checkSmsNo, checkPushNo;
    private ImageView txtSmsOk, txtPushOk;

    private boolean isConfirm, isSNSMode;
    private String checkID;
    private SNSUserObject snsUser;

    public PageJoin(Context context, PageObject pageInfo) {
        super(context, pageInfo);
        View.inflate(context, R.layout.page_member_join, this);
        inputIDBox = (LinearLayout) findViewById(R.id._inputIDBox);
        inputID = (EditText) findViewById(R.id._inputID);
        inputPW = (EditText) findViewById(R.id._inputPW);
        inputPWCheck = (EditText) findViewById(R.id._inputPWCheck);
        inputPhone0 = (EditText) findViewById(R.id._inputPhone0);
        inputPhone1 = (EditText) findViewById(R.id._inputPhone1);
        inputPhone2 = (EditText) findViewById(R.id._inputPhone2);
        inputEmail0 = (EditText) findViewById(R.id._inputEmail0);
        inputEmail1 = (EditText) findViewById(R.id._inputEmail1);
        inputSendPW = (EditText) findViewById(R.id._inputSendPW);

        inputID.setOnEditorActionListener(this);
        inputPhone1.addTextChangedListener(this);
        inputSendPW.setOnEditorActionListener(this);


        btnIDCheck = (Button) findViewById(R.id._btnIDCheck);
        btnProvision = (Button) findViewById(R.id._btnProvision);
        btnPrivacy = (Button) findViewById(R.id._btnPrivacy);
        btnSendPW = (Button) findViewById(R.id._btnSendPW);
        btnPWConfirm = (Button) findViewById(R.id._btnPWConfirm);
        btnComplete = (ImageButton) findViewById(R.id._btnComplete);


        btnN = (Button) findViewById(R.id._btnN);
        btnG = (Button) findViewById(R.id._btnG);
        btnF = (Button) findViewById(R.id._btnF);
        btnC = (Button) findViewById(R.id._btnC);
        imgSns = (ImageView) findViewById(R.id._imgSns);

        checkProvision = (Button) findViewById(R.id._checkProvision);
        checkPrivacy = (Button) findViewById(R.id._checkPrivacy);
        checkSendSMS = (Button) findViewById(R.id._checkSendSMS);
        checkSendEMail = (Button) findViewById(R.id._checkSendEMail);
        checkSmsOk = (Button) findViewById(R.id._checkSmsOk);
        checkPushOk = (Button) findViewById(R.id._checkPushOk);
        checkSmsNo = (Button) findViewById(R.id._checkSmsNo);
        checkPushNo = (Button) findViewById(R.id._checkPushNo);
        txtSmsOk = (ImageView) findViewById(R.id._txtSmsOk);
        txtPushOk = (ImageView) findViewById(R.id._txtPushOk);




        checkSendSMS.setSelected(true);
        checkSendEMail.setSelected(false);

        checkProvision.setSelected(false);
        checkPrivacy.setSelected(false);

        btnProvision.setTypeface(FontFactory.getInstence().FONT_KR);
        btnPrivacy.setTypeface(FontFactory.getInstence().FONT_KR);


        checkSmsOk.setSelected(true);
        checkPushOk.setSelected(true);


        checkSendSMS.setOnClickListener(this);
        checkSendEMail.setOnClickListener(this);

        checkProvision.setOnClickListener(this);
        checkPrivacy.setOnClickListener(this);

        checkSmsOk.setOnClickListener(this);
        checkSmsNo.setOnClickListener(this);
        checkPushOk.setOnClickListener(this);
        checkPushNo.setOnClickListener(this);


        btnN.setOnClickListener(this);
        btnG.setOnClickListener(this);
        btnF.setOnClickListener(this);
        btnC.setOnClickListener(this);


        btnIDCheck.setOnClickListener(this);
        btnProvision.setOnClickListener(this);
        btnPrivacy.setOnClickListener(this);
        btnSendPW.setOnClickListener(this);
        btnPWConfirm.setOnClickListener(this);
        btnComplete.setOnClickListener(this);


        imgSns.setVisibility(View.GONE);
        isConfirm = false;

        checkID = "";


        isSNSMode = false;
        snsUser = null;


        inputPW.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputPW.setText("");
                }

            }
        });
        inputPWCheck.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputPWCheck.setText("");
                }

            }
        });

    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        int result = actionId & EditorInfo.IME_MASK_ACTION;
        switch (result) {
            case EditorInfo.IME_ACTION_DONE:
                if (view == inputID) {

                    onCheckID();
                } else {
                    onConfirm();
                }
                break;
            case EditorInfo.IME_ACTION_NEXT:
                // next stuff
                break;
        }
        return false;
    }
    public void beforeTextChanged(CharSequence s, int start,
                                  int count, int after){}
    public void onTextChanged(CharSequence s, int start, int before, int count){}
    public void afterTextChanged(Editable edit){
        String s = edit.toString();
        if (s.length() > 3){

            inputPhone2.requestFocus();
        }

    }

	protected void doResize()
    {
    	super.doResize();

    }
    public void onClick(View v) {



        if(v==btnIDCheck){
            onCheckID();
        }

        else if(v==btnProvision){


            PageObject pInfo=new PageObject(Config.POPUP_WEB);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("title","이용약관");
            pInfo.info.put("pageUrl",Config.WEB_AGREEMENT);
            pInfo.dr=1;
            MainActivity.getInstence().addPopup(pInfo);

        }else if(v==btnPrivacy){
            PageObject pInfo=new PageObject(Config.POPUP_WEB);
            pInfo.info=new HashMap<String,Object>();
            pInfo.info.put("title","개인정보취급방침");
            pInfo.info.put("pageUrl",Config.WEB_PRIVACY);
            pInfo.dr=1;
            MainActivity.getInstence().addPopup(pInfo);

        }else if(v== btnSendPW){
            onSendConfirm();

        }else if(v== btnPWConfirm){
            onConfirm();

        }else if(v== btnComplete){
            onComplete();

        }else if(v== checkSendSMS) {
            if (checkSendSMS.isSelected() == true) {
                checkSendSMS.setSelected(false);
            }else{
                checkSendSMS.setSelected(true);

            }


            if (checkSendSMS.isSelected() == true) {
                checkSendEMail.setSelected(false);

            } else {
                checkSendEMail.setSelected(true);

            }
        }else if(v== checkSendEMail) {
            if (checkSendEMail.isSelected() == true) {
                checkSendEMail.setSelected(false);

            }else{
                checkSendEMail.setSelected(true);

            }

            if (checkSendEMail.isSelected() == true) {
                checkSendSMS.setSelected(false);

            } else {
                checkSendSMS.setSelected(true);

            }
        }else if(v== checkSmsOk) {
            if (checkSmsOk.isSelected() == false) {
                checkSmsOk.setSelected(true);
                checkSmsNo.setSelected(false);
            }else{
                //checkSmsOk.setSelected(false);
                //checkSmsNo.setSelected(true);

            }

        }else if(v== checkSmsNo) {
            if (checkSmsNo.isSelected() == false) {
                checkSmsOk.setSelected(false);
                checkSmsNo.setSelected(true);
            }else{
                //checkSmsOk.setSelected(true);
                //checkSmsNo.setSelected(false);

            }

        }else if(v== checkPushOk) {
            if (checkPushOk.isSelected() == false) {
                checkPushOk.setSelected(true);
                checkPushNo.setSelected(false);
            }else{
                //checkPushOk.setSelected(false);
                //checkPushNo.setSelected(true);

            }

        }else if(v== checkPushNo) {
            if (checkPushNo.isSelected() == false) {

                checkPushOk.setSelected(false);
                checkPushNo.setSelected(true);
            }else{
                //checkPushOk.setSelected(true);
                //checkPushNo.setSelected(false);

            }

        }else if(v== checkProvision) {
            if (checkProvision.isSelected() == true) {
                checkProvision.setSelected(false);

            }else{
                checkProvision.setSelected(true);

            }

        }else if(v== checkPrivacy) {
            if (checkPrivacy.isSelected() == true) {
                checkPrivacy.setSelected(false);

            }else{
                checkPrivacy.setSelected(true);

            }

        }else{
            if(v==btnN) {
                if(btnN.isSelected()){
                    SNSManager.getInstence().logout(SNSInfo.TYPE_NAVER);
                }else{
                    SNSManager.getInstence().login(SNSInfo.TYPE_NAVER);
                }

            }else if(v==btnG) {
                if(btnG.isSelected()){
                    SNSManager.getInstence().logout(SNSInfo.TYPE_GOOGLE);
                }else{
                    SNSManager.getInstence().login(SNSInfo.TYPE_GOOGLE);
                }

            }else if(v==btnF) {
                if(btnF.isSelected()){
                    SNSManager.getInstence().logout(SNSInfo.TYPE_FACEBOOK);
                }else{
                    SNSManager.getInstence().login(SNSInfo.TYPE_FACEBOOK);
                }

            }else if(v==btnC) {
                if(btnC.isSelected()){
                    SNSManager.getInstence().logout(SNSInfo.TYPE_KAKAO);
                }else{
                    SNSManager.getInstence().login(SNSInfo.TYPE_KAKAO);
                }

            }




        }

    }


	protected void doMovedInit() {
	    super.doMovedInit();

        MemberInfo.getInstence().joinDelegate=this;
        MemberInfo.getInstence().loadSido();
        SNSManager.getInstence().delegate=this;

	}
    protected void doRemove() {

        super.doRemove();
        if(MemberInfo.getInstence().joinDelegate==this)
        {
            MemberInfo.getInstence().joinDelegate = null;
        }

        if(SNSManager.getInstence().delegate==this)
        {
            SNSManager.getInstence().delegate = null;
        }
        if(MemberInfo.getInstence().getLoginState()==false){

            SNSManager.getInstence().logoutAll();
        }
    }

    public void onLoadCheckID(boolean able){
        if(able==true){
            checkID=inputID.getText().toString();
            mainActivity.viewAlert("",R.string.msg_join_success_check_id,null);

        }else{
            checkID="";
            mainActivity.viewAlert("",R.string.msg_join_fail_check_id,null);
        }
    }
    public void onLoadConfirmPW(boolean result){

        if(result==true){
            isConfirm=true;
            mainActivity.viewAlert("",R.string.msg_join_success_confirm,null);

        }else{
            isConfirm=false;
            mainActivity.viewAlert("",R.string.msg_join_fail_confirm,null);
        }


    }
    public void onSendPw(boolean result){

        if(result==true){
            mainActivity.viewAlert("",R.string.msg_join_send_confirm_complete,null);
        }else{
            mainActivity.viewAlert("",R.string.msg_join_send_confirm_fail,null);
        }

    }
    public void onLoadJoin(boolean result){
        if(result==true){
            mainActivity.viewAlert("",R.string.msg_join_complete,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PageObject pInfo;
                    if(which==-1){

                        MemberInfo.getInstence().loadLoginState();

                    }else{


                    }

                }
            });
        }else{
            mainActivity.viewAlert("",R.string.msg_join_complete_fail,null);
        }

    }
 /////////////////////////////////////////
    private String getSendValue(boolean isSMS){

        String key="";
        String value="";
        if(isSMS==true){
            key=inputPhone0.getText().toString();

            value = value+key;
            if(key.equals("")==true){
                mainActivity.viewAlert("",R.string.msg_member_input_phone,null);
                inputPhone0.requestFocus();
                return "";
            }
            key=inputPhone1.getText().toString();
            value = value+key;
            if(key.equals("")==true){
                mainActivity.viewAlert("",R.string.msg_member_input_phone,null);
                inputPhone1.requestFocus();
                return "";
            }
            key=inputPhone2.getText().toString();
            value = value+key;
            if(key.equals("")==true){
                mainActivity.viewAlert("",R.string.msg_member_input_phone,null);
                inputPhone2.requestFocus();
                return "";
            }
        }else {
            key = inputEmail0.getText().toString();
            value = value + key;
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_member_input_email, null);
                inputEmail0.requestFocus();
                return "";
            }
            key = inputEmail1.getText().toString();
            value = value + "@" + key;
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_member_input_email, null);
                inputEmail1.requestFocus();
                return "";
            }

        }
        return value;
    }


    private void onSendConfirm(){

        isConfirm=false;
        boolean isSMS=checkSendSMS.isSelected();


        String value=getSendValue(isSMS);
        if(value.equals("")==true){
            return;
        }
        MemberInfo.getInstence().sendPW(isSMS,value);
    }

    private void onCheckID(){
        String key=inputID.getText().toString();
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_id,null);
        }else{
            checkID="";
            MemberInfo.getInstence().loadCheckID(key);
        }
    }
    private void onConfirm(){
        String key=inputSendPW.getText().toString();
        if(key.equals("")==true){
             mainActivity.viewAlert("",R.string.msg_join_input_confirm,null);

        }else{
            boolean isSMS=checkSendSMS.isSelected();
            String value=getSendValue(isSMS);
            if(value.equals("")==true){
                return;
            }
            MemberInfo.getInstence().loadConfirmPW(isSMS,value,key);
        }
    }
    private void onComplete(){

        Map<String,String> params=new HashMap<String,String>();
        String key="";

        if(snsUser==null) {
            key = inputID.getText().toString();
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_member_input_id, null);
                inputID.requestFocus();
                return;
            }


            if (checkID.equals(key) == false) {
                mainActivity.viewAlert("", R.string.msg_join_error_check_id, null);
                return;
            }
            params.put("userid", key);


            key = inputPW.getText().toString();

            if(key.length()<4){

                mainActivity.viewAlert("", R.string.msg_member_input_pw_size_error, null);
                inputPW.requestFocus();
                return;
            }

            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_member_input_pw, null);
                inputPW.requestFocus();
                return;
            }

            key = inputPWCheck.getText().toString();
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_join_input_pwcheck, null);
                inputPWCheck.requestFocus();
                return;
            }
            if (inputPW.getText().toString().equals(key) == false) {
                mainActivity.viewAlert("", R.string.msg_join_error_check_pw, null);
                inputPWCheck.setText("");
                inputPWCheck.requestFocus();
                return;
            }
            params.put("pass", key);
        }else{
            params.put("userid", snsUser.userSEQ);
            params.put("pass", snsUser.userToken);
        }

        key=inputPhone0.getText().toString();
        String phone="";
        phone = phone+key;
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_phone,null);
            inputPhone0.requestFocus();
            return;
        }
        key=inputPhone1.getText().toString();
        phone = phone+"-"+key;
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_phone,null);
            inputPhone1.requestFocus();
            return;
        }
        key=inputPhone2.getText().toString();
        phone = phone+"-"+key;
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_phone,null);
            inputPhone2.requestFocus();
            return;
        }
        params.put("hp",phone);

        String email="";
        key=inputEmail0.getText().toString();
        email=email+key;
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_email,null);
            inputEmail0.requestFocus();
            return;
        }
        key=inputEmail1.getText().toString();
        email=email+"@"+key;
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_email,null);
            inputEmail1.requestFocus();
            return;
        }
        params.put("email",email);



        boolean sendSms=false;
        if(checkSmsOk.isSelected()==true){
            sendSms=true;
            params.put("sms_auto", "Y");
        }else{
            params.put("sms_auto","N");

        }
        if(checkPushOk.isSelected()==true){

            params.put("push","Y");
        }else{
            params.put("push","N");

        }

        if(checkProvision.isSelected()==false){
            mainActivity.viewAlert("",R.string.msg_join_agree_provision,null);
            return;

        }
        if(checkPrivacy.isSelected()==false){
            mainActivity.viewAlert("",R.string.msg_join_agree_privacy,null);
            return;

        }

        if(isConfirm==false){
            mainActivity.viewAlert("",R.string.msg_join_error_check_confirm,null);
            inputSendPW.requestFocus();
            return;

        }

        key=inputSendPW.getText().toString();
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_join_input_confirm,null);
            inputSendPW.requestFocus();
            return;

        }
        params.put("rcvnum",key);

        MemberInfo.getInstence().loadJoin(params,sendSms);



    }

    private void setSNSMode(boolean ac){
        isSNSMode=ac;

        if(ac==true){
            inputIDBox.setVisibility(View.GONE);
            imgSns.setVisibility(View.VISIBLE);
            if(snsUser.userID.equals("")==false){
                String[] emails = snsUser.userID.split("@");
                if(emails.length==2){
                    inputEmail0.setText(emails[0]);
                    inputEmail1.setText(emails[1]);

                }


            }


        }else{
            inputIDBox.setVisibility(View.VISIBLE);
            imgSns.setVisibility(View.GONE);
            snsUser=null;

        }


    }

    public void onLogin(SNSManager m, SNSUserObject obj, int type){
        snsUser=obj;
        setSNSMode(true);
        switch (type){
            case SNSInfo.TYPE_GOOGLE:
                btnG.setSelected(true);
                imgSns.setImageResource(R.drawable.img_sns_g);
                break;
            case SNSInfo.TYPE_NAVER:
                btnN.setSelected(true);
                imgSns.setImageResource(R.drawable.img_sns_n);
                break;
            case SNSInfo.TYPE_FACEBOOK:
                btnF.setSelected(true);
                imgSns.setImageResource(R.drawable.img_sns_f);
                break;
            case SNSInfo.TYPE_KAKAO:
                btnC.setSelected(true);
                imgSns.setImageResource(R.drawable.img_sns_k);
                break;




        }

    }
    public void onLogout(SNSManager m, int type){

        setSNSMode(false);

        Log.i("","onLogout : "+type);
        switch (type){
            case SNSInfo.TYPE_GOOGLE:
                btnG.setSelected(false);
                break;
            case SNSInfo.TYPE_NAVER:
                btnN.setSelected(false);
                break;
            case SNSInfo.TYPE_FACEBOOK:
                btnF.setSelected(false);
                break;
            case SNSInfo.TYPE_KAKAO:
                btnC.setSelected(false);
                break;




        }

    }
    public void onError(SNSManager m,int type ,int eType){


    }






}
