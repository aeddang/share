package com.credit.korea.KoreaCredit.member;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.sns.SNSInfo;
import lib.view.RackableScrollView;


@SuppressLint({ "NewApi", "JavascriptInterface" })
public class PopupModify extends ViewCore implements OnClickListener,
        MemberInfo.ModifyInfoDelegate,TextWatcher {

    private TextView textID;
    private EditText inputPW, inputPWCheck, inputPhone0,inputPhone1,inputPhone2 ,inputEmail0,inputEmail1;

    private ImageButton btnComplete;

    private Button checkSmsOk, checkPushOk,checkSmsNo, checkPushNo;
    private ImageView txtSmsOk, txtPushOk;

    private ImageView imgSns;
    private LinearLayout inputIDBox;



    private MyInfoObject myObj;

    public PopupModifyDelegate delegate;

    public interface PopupModifyDelegate
    {

        void onModifyComplete();


    }

    public PopupModify(Context context, PageObject pageInfo) {
        super(context, pageInfo);


        View.inflate(context, R.layout.popup_member_modify, this);
        if(pageInfo.info!=null){
            myObj=(MyInfoObject)pageInfo.info.get("myObj");

        }else{
            myObj=new MyInfoObject();

        }
        imgSns=(ImageView) findViewById(R.id._imgSns);
        inputIDBox=(LinearLayout) findViewById(R.id._inputIDBox);
        textID = (TextView) findViewById(R.id._textID);
        inputPW = (EditText) findViewById(R.id._inputPW);
        inputPWCheck = (EditText) findViewById(R.id._inputPWCheck);
        inputPhone0=(EditText) findViewById(R.id._inputPhone0);
        inputPhone1=(EditText) findViewById(R.id._inputPhone1);
        inputPhone2=(EditText) findViewById(R.id._inputPhone2);
        inputEmail0=(EditText) findViewById(R.id._inputEmail0);
        inputEmail1=(EditText) findViewById(R.id._inputEmail1);



        btnComplete = (ImageButton) findViewById(R.id._btnComplete);


        checkSmsOk = (Button) findViewById(R.id._checkSmsOk);
        checkPushOk = (Button) findViewById(R.id._checkPushOk);
        checkSmsNo = (Button) findViewById(R.id._checkSmsNo);
        checkPushNo = (Button) findViewById(R.id._checkPushNo);
        txtSmsOk = (ImageView) findViewById(R.id._txtSmsOk);
        txtPushOk = (ImageView) findViewById(R.id._txtPushOk);





        btnComplete.setOnClickListener(this);


        textID.setText(MemberInfo.getInstence().getID());
        inputPW.setText(MemberInfo.getInstence().getPW());
        inputPWCheck.setText(MemberInfo.getInstence().getPW());

        checkSmsOk.setOnClickListener(this);

        checkSmsNo.setOnClickListener(this);
        checkPushOk.setOnClickListener(this);
        checkPushNo.setOnClickListener(this);

        inputPhone1.addTextChangedListener(this);
        setData(myObj);


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
    public void beforeTextChanged(CharSequence s, int start,
                                  int count, int after){}
    public void onTextChanged(CharSequence s, int start, int before, int count){}
    public void afterTextChanged(Editable edit){
        String s = edit.toString();
        if (s.length() > 3){

            inputPhone2.requestFocus();
        }

    }
    private void setData(MyInfoObject _myObj) {
        myObj=_myObj;
        if(myObj.snsType==-1){
            imgSns.setVisibility(View.GONE);
            inputIDBox.setVisibility(View.VISIBLE);
            textID.setText(myObj.id);

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


        String[] pA=myObj.phone.split("-");

        if(pA.length>0) {
            inputPhone0.setText(pA[0]);
        }
        if(pA.length>1) {
            inputPhone1.setText(pA[1]);
        }
        if(pA.length>2) {
            inputPhone2.setText(pA[2]);
        }
        String[] eA=myObj.email.split("@");
        if(eA.length>0) {
            inputEmail0.setText(eA[0]);
        }
        if(eA.length>1) {
            inputEmail1.setText(eA[1]);
        }


        checkSmsOk.setSelected(myObj.isSMS);
        if(myObj.isSMS==true){

            checkSmsNo.setSelected(false);
        }else{
            checkSmsNo.setSelected(true);

        }

        checkPushOk.setSelected(myObj.isPush);
        if(myObj.isPush==true){

            checkPushNo.setSelected(false);
        }else{
            checkPushNo.setSelected(true);

        }


    }

    protected void doResize() {
        super.doResize();

    }

    public void onClick(View v) {

        if (v == btnComplete) {
            onComplete();

        }else if(v== checkSmsOk) {
            if (checkSmsOk.isSelected() == false) {
                checkSmsOk.setSelected(true);
                checkSmsNo.setSelected(false);
            }else{
               // checkSmsOk.setSelected(false);
                //checkSmsNo.setSelected(true);

            }

        }else if(v== checkSmsNo) {
            if (checkSmsNo.isSelected() == false) {
                MainActivity.getInstence().viewAlertSelect("",R.string.msg_join_sendsms_autoupdate,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PageObject pInfo;
                        if(which==-1){

                            checkSmsOk.setSelected(false);
                            checkSmsNo.setSelected(true);

                        }else{


                        }

                    }
                });

            }else{
                //checkSmsOk.setSelected(true);
               // checkSmsNo.setSelected(false);

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

        }

        else if(v== checkSmsOk) {
            if (checkSmsOk.isSelected() == false) {
                checkSmsOk.setSelected(true);
                txtSmsOk.setImageResource(R.drawable.myinfo_text3);
            }else{

                MainActivity.getInstence().viewAlertSelect("",R.string.msg_join_sendsms_autoupdate,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PageObject pInfo;
                        if(which==-1){

                            checkSmsOk.setSelected(false);
                            txtSmsOk.setImageResource(R.drawable.myinfo_text4);

                        }else{


                        }

                    }
                });
                return;



            }

        }else if(v== checkPushOk) {
            if (checkPushOk.isSelected() == false) {
                checkPushOk.setSelected(true);
                txtPushOk.setImageResource(R.drawable.myinfo_text3);
            }else{
                checkPushOk.setSelected(false);
                txtPushOk.setImageResource(R.drawable.myinfo_text4);

            }

        }

    }





    protected void doMovedInit() {
        super.doMovedInit();


        MemberInfo.getInstence().modifyDelegate = this;
        MemberInfo.getInstence().loadSido();

    }

    protected void doRemove() {

        super.doRemove();
        if (MemberInfo.getInstence().modifyDelegate == this) {
            MemberInfo.getInstence().modifyDelegate = null;
        }


    }





    public void onLoadModify(boolean result) {
        if (result == true) {
            mainActivity.viewAlert("", R.string.msg_modify_complete, null);
            if(delegate!=null){

                delegate.onModifyComplete();
            }

        } else {
            mainActivity.viewAlert("", R.string.msg_modify_complete_fail, null);
        }

    }

    /////////////////////////////////////////


    private void onComplete() {

        Map<String, String> params = new HashMap<String, String>();




        String key = inputPW.getText().toString();
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
        params.put("login_type",myObj.snsTypeValue);



        MemberInfo.getInstence().loadModify(params,sendSms);

    }
}
