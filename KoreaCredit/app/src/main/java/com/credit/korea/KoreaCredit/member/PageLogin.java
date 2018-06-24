package com.credit.korea.KoreaCredit.member;


import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.SNSManager;

import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.sns.SNSInfo;
import lib.sns.SNSUserObject;
import lib.view.RackableScrollView;


public class PageLogin extends ViewCore implements View.OnClickListener,MemberInfo.LoginInfoDelegate ,SNSManager.SNSManagerDelegate,TextView.OnEditorActionListener {

    private EditText inputID,inputPW;
    private ImageButton btnComplete;
    private Button btnN,btnG,btnF,btnC;

    public PageLogin(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_member_login,this);

        inputID=(EditText) findViewById(R.id._inputID);
        inputPW=(EditText) findViewById(R.id._inputPW);

        btnComplete=(ImageButton) findViewById(R.id._btnComplete);

        btnN=(Button) findViewById(R.id._btnN);
        btnG=(Button) findViewById(R.id._btnG);
        btnF=(Button) findViewById(R.id._btnF);
        btnC=(Button) findViewById(R.id._btnC);
        btnComplete.setOnClickListener(this);

        btnN.setOnClickListener(this);
        btnG.setOnClickListener(this);
        btnF.setOnClickListener(this);
        btnC.setOnClickListener(this);
        inputPW.setOnEditorActionListener(this);



        inputPW.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputPW.setText("");
                }

            }
        });
    }

	protected void doResize()
    {
    	super.doResize();

    }
    public void onClick(View v) {

       if(v== btnComplete){
            onComplete();

        }else if(v==btnN) {
            SNSManager.getInstence().login(SNSInfo.TYPE_NAVER);


        }else if(v==btnG) {
            SNSManager.getInstence().login(SNSInfo.TYPE_GOOGLE);


        }else if(v==btnF) {
           SNSManager.getInstence().login(SNSInfo.TYPE_FACEBOOK);

        }else if(v==btnC) {
           SNSManager.getInstence().login(SNSInfo.TYPE_KAKAO);


        }

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
	protected void doMovedInit() {
	    super.doMovedInit();

        MemberInfo.getInstence().loginDelegate=this;
        MemberInfo.getInstence().loadSido();
        SNSManager.getInstence().delegate=this;

	}
    protected void doRemove() {

        super.doRemove();
        if(MemberInfo.getInstence().loginDelegate==this)
        {
            MemberInfo.getInstence().loginDelegate = null;
        }
        if(SNSManager.getInstence().delegate==this)
        {
            SNSManager.getInstence().delegate = null;
        }

    }
    public void onLogin(boolean result){

        if(result==true){
            mainActivity.viewAlert("",R.string.msg_login_complete,null);
            mainActivity.changeViewMain();
        }else{
            SNSManager.getInstence().logoutAll();
            //mainActivity.viewAlert("",R.string.msg_login_complete_fail,null);
        }
    }
    public void onLogout(boolean result){
        /*
        if(result==true){
            mainActivity.viewAlert("",R.string.msg_logout_complete,null);
            mainActivity.changeViewMain();
        }else{

        }*/

    }


 /////////////////////////////////////////

    private void onComplete(){

        Map<String,String> params=new HashMap<String,String>();

        String key=inputID.getText().toString();
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_id,null);
            inputID.requestFocus();
            return;
        }

        params.put("id",key);


        key=inputPW.getText().toString();
        if(key.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_member_input_pw,null);
            inputPW.requestFocus();
            return;
        }


        params.put("pw",key);


        MemberInfo.getInstence().loadLogin(params);



    }

    public void onLogin(SNSManager m, SNSUserObject obj, int type){

        Map<String,String> params=new HashMap<String,String>();
        params.put("id",obj.userSEQ);
        params.put("pw",obj.userToken);

        MemberInfo.getInstence().loadLogin(params);

    }
    public void onLogout(SNSManager m, int type){


    }
    public void onError(SNSManager m,int type ,int eType){


    }





}
