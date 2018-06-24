package com.credit.korea.KoreaCredit.mypage.consume;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.member.MemberInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


@SuppressLint({ "NewApi", "JavascriptInterface" })
public class PopupConsumeDefault extends ViewCore implements OnClickListener, AdapterView.OnItemSelectedListener,View.OnKeyListener,MemberInfo.WhereInfoDelegate
{


    private EditText inputYear, inputMonth, inputIncomeYear,inputFamilly;
    private Spinner spinnerBirth;
    private Spinner spinnerSiDo,spinnerGuGun;
    private ImageButton btnComplete;

    private Button checkMail, checkFemail,checkMerried, checkUnMerried;

    private String selectedBirth;
    private DefaultObject myObj;

    private ArrayList<String> sidoLists;
    private ArrayList<String> gugunLists;
    private String selectedSido,selectedGugun;

    private boolean isInit;
    public PopupConsumeDefault(Context context, PageObject pageInfo) {
        super(context, pageInfo);


        View.inflate(context, R.layout.popup_consume_default, this);
        if(pageInfo.info!=null){
            myObj=(DefaultObject)pageInfo.info.get("myObj");

        }else{
            myObj=new DefaultObject();

        }
        isInit=true;

        inputYear = (EditText) findViewById(R.id._inputYear);
        inputMonth = (EditText) findViewById(R.id._inputMonth);
        inputIncomeYear=(EditText) findViewById(R.id._inputIncomeYear);
        inputFamilly=(EditText) findViewById(R.id._inputFamilly);


        spinnerBirth = (Spinner) findViewById(R.id._spinnerBirth);

        btnComplete = (ImageButton) findViewById(R.id._btnComplete);


        checkMail = (Button) findViewById(R.id._checkMail);
        checkFemail = (Button) findViewById(R.id._checkFemail);
        checkMerried = (Button) findViewById(R.id._checkMerried);
        checkUnMerried = (Button) findViewById(R.id._checkUnMerried);


        spinnerSiDo=(Spinner) findViewById(R.id._spinnerSiDo);
        spinnerGuGun=(Spinner) findViewById(R.id._spinnerGuGun);

        btnComplete.setOnClickListener(this);

        spinnerBirth.setOnItemSelectedListener(this);
        spinnerSiDo.setOnItemSelectedListener(this);
        spinnerGuGun.setOnItemSelectedListener(this);

        inputYear.setOnKeyListener(this);
        checkMail.setOnClickListener(this);
        checkFemail.setOnClickListener(this);

        checkMerried.setOnClickListener(this);
        checkUnMerried.setOnClickListener(this);




        selectedSido = myObj.sido;
        selectedGugun = myObj.gugun;
        sidoLists=null;
        gugunLists=null;
        setData(myObj);

        MemberInfo.getInstence().whereDelegate=this;
        MemberInfo.getInstence().loadSido();


        inputYear.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputYear.setText("");
                }

            }
        });

        inputMonth.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputMonth.setText("");
                }

            }
        });

        inputIncomeYear.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputIncomeYear.setText("");
                }

            }
        });


    }
    private void setData(DefaultObject _myObj) {



        myObj=_myObj;
        //textMonth.setText(String.valueOf((int)Math.round(myObj.consumePerYear)));
        inputYear.setText(String.valueOf((int)Math.round(myObj.consumePerYear)));
        inputMonth.setText(String.valueOf((int)Math.round(myObj.consumePerMonthMin)));
        inputIncomeYear.setText(String.valueOf((int)Math.round(myObj.incomePerYear)));
        inputFamilly.setText(String.valueOf(myObj.familly));

        checkMail.setSelected(myObj.isMail);
        checkFemail.setSelected(myObj.isFeMail);

        checkMerried.setSelected(myObj.isMerried);
        if(myObj.isMerried==true) {
            checkUnMerried.setSelected(false);
        }else{
            checkUnMerried.setSelected(true);

        }



        selectedBirth = String.valueOf(myObj.birth);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, ConsumeInfo.getInstence().births);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBirth.setAdapter(dataAdapter);
        int selectedIDX=0;
        for(int i=0;i<ConsumeInfo.getInstence().births.size();++i){
            if(ConsumeInfo.getInstence().births.get(i).equals(selectedBirth)==true){
                selectedIDX=i;
            }

        }

        spinnerBirth.setSelection(selectedIDX);

    }

    protected void doResize() {
        super.doResize();

    }
    public boolean onKey(View v, int keyCode, KeyEvent event){

        String str = inputYear.getText().toString();
        /*
        try{
            float value= Float.valueOf(str);
            textMonth.setText(myObj.getConsumePerMonth(value));

        }catch(NumberFormatException e){

            textMonth.setText("");
        }
       */

        return false;
    }
    public void onClick(View v) {

        if (v == btnComplete) {
            onComplete();

        }else if(v== checkMail) {
            if (checkMail.isSelected() == true) {
                checkMail.setSelected(false);
                checkFemail.setSelected(true);

            }else{
                checkMail.setSelected(true);
                checkFemail.setSelected(false);

            }

        }else if(v== checkFemail) {
            if (checkFemail.isSelected() == true) {
                checkMail.setSelected(true);
                checkFemail.setSelected(false);

            }else{
                checkMail.setSelected(false);
                checkFemail.setSelected(true);

            }

        }else if(v== checkMerried) {
            if (checkMerried.isSelected() == true) {
                checkMerried.setSelected(false);
                checkUnMerried.setSelected(true);

            }else{
                checkMerried.setSelected(true);
                checkUnMerried.setSelected(false);

            }

        }else if(v== checkUnMerried) {
            if (checkUnMerried.isSelected() == true) {
                checkMerried.setSelected(true);
                checkUnMerried.setSelected(false);

            }else{
                checkMerried.setSelected(false);
                checkUnMerried.setSelected(true);

            }

        }

    }


    public void onLoadSiDo(ArrayList<String> lists){

        sidoLists=lists;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item,lists);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSiDo.setAdapter(dataAdapter);



        if(isInit==true){
            int idx=lists.indexOf(selectedSido);
            if(idx!=-1){

                spinnerSiDo.setSelection(idx);
            }else{
                isInit=false;
                spinnerSiDo.setSelection(0);
            }
        }else{
            isInit=false;
            spinnerSiDo.setSelection(0);
        }

    }
    public void onLoadGuGun(ArrayList<String> lists){

        Log.i("","onLoadGuGun : "+isInit);
        gugunLists=lists;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item,lists);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuGun.setAdapter(dataAdapter);
        if(isInit==true){
            int idx=lists.indexOf(selectedGugun);

            Log.i("","onLoadGuGun : "+idx+" "+selectedGugun);
            if(idx!=-1){

                spinnerGuGun.setSelection(idx);
            }else{
                spinnerGuGun.setSelection(0);

            }
        }else{
            spinnerGuGun.setSelection(0);
        }
        isInit=false;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spnir = (Spinner)parent;

        if(spnir.equals(spinnerBirth)){

            selectedBirth=ConsumeInfo.getInstence().births.get(position);
            return;
        }
        if(sidoLists==null){

            return;
        }

        if(spnir.equals(spinnerSiDo)){

            selectedSido=sidoLists.get(position);
            if(isInit==false){
                selectedGugun="";
            }else{

            }

            if(selectedSido.equals(MemberInfo.NOME_SELECTED)==true){


            }else{
                MemberInfo.getInstence().loadGuGun(selectedSido);
            }


        }else{

            if(gugunLists==null){

                return;
            }
            selectedGugun=gugunLists.get(position);

        }
    }




    public void onNothingSelected(AdapterView<?> parent) {


    }

    protected void doMovedInit() {
        super.doMovedInit();



    }

    protected void doRemove() {

        super.doRemove();
        if(MemberInfo.getInstence().whereDelegate==this)
        {
            MemberInfo.getInstence().whereDelegate = null;
        }

    }


    /////////////////////////////////////////


    private void onComplete() {

        Map<String, String> params = new HashMap<String, String>();

        String key = inputYear.getText().toString();
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_consume_input_year, null);
            inputYear.requestFocus();
            return;
        }
        params.put("yearmoney", key);
        key = inputMonth.getText().toString();
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_consume_input_month, null);
            inputMonth.requestFocus();
            return;
        }
        params.put("monthmoney", key);
        key = inputIncomeYear.getText().toString();
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_consume_input_income_year, null);
            inputIncomeYear.requestFocus();
            return;
        }
        params.put("yearincome", key);

        key = inputFamilly.getText().toString();
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_consume_input_familly, null);
            inputFamilly.requestFocus();
            return;
        }
        params.put("familynum", key);



        if(selectedSido.equals(MemberInfo.NOME_SELECTED)==true){
            mainActivity.viewAlert("",R.string.msg_join_input_sido,null);
            return;
        }else{
            params.put("addr1", DataFactory.getInstence().getUriencode(selectedSido));

        }
        if(selectedGugun.equals(MemberInfo.NOME_SELECTED)==true || selectedGugun.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_join_input_sido,null);
            return;
        }else{
            params.put("addr2",DataFactory.getInstence().getUriencode(selectedGugun));

        }







        if (checkMail.isSelected() == true) {

            params.put("sex", DataFactory.getInstence().getUriencode("남자"));
        } else {
            params.put("sex", DataFactory.getInstence().getUriencode("여자"));

        }
        if (checkMerried.isSelected() == true) {

            params.put("marry", DataFactory.getInstence().getUriencode("기혼"));
        } else {
            params.put("marry", DataFactory.getInstence().getUriencode("미혼"));

        }

        params.put("birthyear", selectedBirth);


        ConsumeInfo.getInstence().modifyDefaultData(params);

    }
}
