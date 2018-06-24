package com.credit.korea.KoreaCredit.member;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.R;

import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.RackableScrollView;


public class PageFind extends ViewCore implements View.OnClickListener,MemberInfo.FindInfoDelegate,TextWatcher,TextView.OnEditorActionListener {

    public static final String TYPE_ID="idtype";
    public static final String TYPE_PW="pwtype";

    private EditText inputPhone0,inputPhone1,inputPhone2,inputEmail0,inputEmail1;
    private ImageButton btnComplete;

    private Button checkSendSMS,checkSendEMail;

    private LinearLayout boxPhone,boxEmail;
    private ImageView textTitle,textSubTitle,textPhone,textEmail;
    private String type;

    public PageFind(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_member_find,this);


        inputPhone0=(EditText) findViewById(R.id._inputPhone0);
        inputPhone1=(EditText) findViewById(R.id._inputPhone1);
        inputPhone2=(EditText) findViewById(R.id._inputPhone2);
        inputEmail0=(EditText) findViewById(R.id._inputEmail0);
        inputEmail1=(EditText) findViewById(R.id._inputEmail1);

        btnComplete=(ImageButton) findViewById(R.id._btnComplete);


        textTitle=(ImageView) findViewById(R.id._textTitle);
        textSubTitle=(ImageView) findViewById(R.id._textSubTitle);
        checkSendSMS=(Button) findViewById(R.id._checkSendSMS);
        checkSendEMail=(Button) findViewById(R.id._checkSendEMail);

        textPhone=(ImageView) findViewById(R.id._textPhone);
        textEmail=(ImageView) findViewById(R.id._textEmail);
        boxPhone=(LinearLayout) findViewById(R.id._boxPhone);
        boxEmail=(LinearLayout) findViewById(R.id._boxEmail);




        if(pageInfo.info!=null){
            type=(String)pageInfo.info.get("type");

        }else{
            type=TYPE_ID;

        }
        if(type.equals(TYPE_ID)==true){
            textTitle.setImageResource(R.drawable.findid_title);
            textSubTitle.setImageResource(R.drawable.findid_text0);

        }else{
            textTitle.setImageResource(R.drawable.findpw_title);
            textSubTitle.setImageResource(R.drawable.findpw_text0);

        }



        checkSendSMS.setSelected(true);
        checkSendEMail.setSelected(false);



        checkSendSMS.setOnClickListener(this);
        checkSendEMail.setOnClickListener(this);

        btnComplete.setOnClickListener(this);


        inputPhone1.addTextChangedListener(this);
        inputPhone2.setOnEditorActionListener(this);

        inputEmail1.setOnEditorActionListener(this);
        setInputState();


    }

	protected void doResize()
    {
    	super.doResize();

    }
    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        int result = actionId & EditorInfo.IME_MASK_ACTION;
        switch (result) {
            case EditorInfo.IME_ACTION_DONE:
                onComplete();
                break;
            case EditorInfo.IME_ACTION_NEXT:
                // next stuff
                break;
        }
        return false;
    }
    public void onClick(View v) {

        if(v== btnComplete){
            onComplete();

        }else if(v== checkSendSMS) {
            if (checkSendSMS.isSelected() == true) {
                checkSendSMS.setSelected(false);
                checkSendEMail.setSelected(true);
            }else{
                checkSendSMS.setSelected(true);
                checkSendEMail.setSelected(false);
            }


            setInputState();
        }else if(v== checkSendEMail) {
            if (checkSendEMail.isSelected() == true) {
                checkSendEMail.setSelected(false);
                checkSendSMS.setSelected(true);

            }else{
                checkSendEMail.setSelected(true);
                checkSendSMS.setSelected(false);

            }

            setInputState();
        }


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
    private void setInputState(){

        if (checkSendSMS.isSelected() == true) {

            textPhone.setVisibility(View.VISIBLE);
            textEmail.setVisibility(View.GONE);
            boxPhone.setVisibility(View.VISIBLE);
            boxEmail.setVisibility(View.GONE);

        } else {
            textPhone.setVisibility(View.GONE);
            textEmail.setVisibility(View.VISIBLE);
            boxPhone.setVisibility(View.GONE);
            boxEmail.setVisibility(View.VISIBLE);

        }
    }

	protected void doMovedInit() {
	    super.doMovedInit();

        MemberInfo.getInstence().findDelegate=this;


	}
    protected void doRemove() {

        super.doRemove();
        if(MemberInfo.getInstence().findDelegate==this)
        {
            MemberInfo.getInstence().findDelegate = null;
        }


    }
    public void onFindID(boolean result){
        if(result==true){
            mainActivity.viewAlert("",R.string.msg_findid_complete,null);
        }else{
            mainActivity.viewAlert("",R.string.msg_findid_complete_fail,null);
        }
    }
    public void onFindPW(boolean result){
        if(result==true){
            mainActivity.viewAlert("",R.string.msg_findpw_complete,null);
        }else{
            mainActivity.viewAlert("",R.string.msg_findpw_complete_fail,null);
        }
    }
 /////////////////////////////////////////

    private void onComplete(){

        Map<String,String> params=new HashMap<String,String>();

        String key="";





        if(checkSendSMS.isSelected()==true){

            params.put("sms", "Y");

            String phone="";
            key=inputPhone0.getText().toString();
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

            params.put("phone",phone);
        }else{
            params.put("sms","N");

        }
        if(checkSendEMail.isSelected()==true){

            params.put("ems","Y");
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
        }else{
            params.put("ems","N");

        }

        MemberInfo.getInstence().loadFindIDPW(params,type);





    }







}
