package com.credit.korea.KoreaCredit.card.thiscard;


import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.SelectObject;
import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;
import com.credit.korea.KoreaCredit.card.search.ListGroupSearch;
import com.credit.korea.KoreaCredit.listresult.PopupListResult;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.mypage.MyPageInfo;
import com.credit.korea.KoreaCredit.mypage.MySearchObject;
import com.credit.korea.KoreaCredit.mypage.consume.AddedGroup;
import com.credit.korea.KoreaCredit.mypage.consume.BenifitGroup;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;
import com.credit.korea.KoreaCredit.mypage.consume.DefaultObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;


public class GroupThis extends FrameLayout implements View.OnClickListener ,ConsumeInfo.ConsumeInfoModifyDelegate,ConsumeInfo.ConsumeInfoDelegate,
                                                        DataFactory.DataFactoryDelegate ,PopupListResult.PopupListResultDelegate,TextView.OnEditorActionListener{

    private EditText inputCompany,inputPrice;
    private LinearLayout paturnStack;
    private Button btnCompany,btnPaturn;
    private ImageButton btnComplete;
    public GroupThisDelegate delegate;

    private DefaultObject currentDefaultObj;
    private SelectObject selectCompany;
    private  ArrayList<SelectObject> companyDatas;
    private boolean isInitMsg;

    public interface GroupThisDelegate
    {
        void onThisStart(GroupThis v,Map<String,String> sendParams);


    }


    public GroupThis(Context context)
	{
        super(context);
        View.inflate(context, R.layout.group_cardthis,this);
        isInitMsg=true;
        inputCompany = (EditText) findViewById(R.id._inputCompany);
        inputPrice = (EditText) findViewById(R.id._inputPrice);
        paturnStack = (LinearLayout) findViewById(R.id._paturnStack);

        btnPaturn = (Button) findViewById(R.id._btnPaturn);
        btnCompany = (Button) findViewById(R.id._btnCompany );

        btnComplete = (ImageButton) findViewById(R.id._btnComplete);
        btnPaturn.setOnClickListener(this);
        btnCompany.setOnClickListener(this);


        btnComplete.setOnClickListener(this);

        inputCompany.setOnEditorActionListener(this);

        currentDefaultObj= ConsumeInfo.getInstence().getDefaultData();

        if(currentDefaultObj!=null) {
            onLoadDefaultData(currentDefaultObj);
        }



    }
    public void initGroup(){
        DataFactory.getInstence().delegate=this;

        ConsumeInfo.getInstence().delegate=this;
        ConsumeInfo.getInstence().mdDelegate=this;
        if(currentDefaultObj==null) {
            ConsumeInfo.getInstence().loadDefaultData(false);
        }



    }
    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        int result = actionId & EditorInfo.IME_MASK_ACTION;
        switch (result) {
            case EditorInfo.IME_ACTION_DONE:
                onSearch();
                break;

        }
        return false;
    }
    public void onClick(View v) {
        if(v==btnComplete){
            onComplete();

        }else if(v== btnCompany) {
            onSearch();

        }else{
            PageObject pInfo = new PageObject(Config.PAGE_MYPAGE);
            Map<String,Object> pinfo=new HashMap<String,Object>();
            pInfo.info=pinfo;

            pInfo.info.put("pageIdx",2);

            MainActivity.getInstence().changeView(pInfo);

        }


    }

    private void onComplete() {

        Map<String, String> params = new HashMap<String, String>();

        MainActivity mainActivity = ( MainActivity)MainActivity.getInstence();

        String key = inputCompany.getText().toString();
        /*
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_card_book_input_company, null);
            inputCompany.requestFocus();
            return;
        }*/
        if(selectCompany!=null){
            params.put("sv_value", DataFactory.getInstence().getUriencode(selectCompany.title));
            params.put("sv_idx", selectCompany.id);
        }else{

            mainActivity.viewAlert("", R.string.msg_card_book_input_company, null);
            inputCompany.requestFocus();
            return;
        }


        key = inputPrice.getText().toString();
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_card_book_input_price, null);
            inputPrice.requestFocus();
            return;
        }

        params.put("price", key);





        if(delegate!=null){
            delegate.onThisStart(this,params);
        }

    }
    public  void isActiveCheck(){
        if(currentDefaultObj==null){

            return;
        }
        if(currentDefaultObj.isActive==false){
            addInfo();
        }

    }


    private void onSearch() {
        String key = inputCompany.getText().toString();
        if (key.equals("") == true) {
            MainActivity.getInstence().viewAlert("", R.string.msg_card_book_input_company, null);
            inputCompany.requestFocus();
            return;
        }

        MainActivity.getInstence().hideKeyBoard();
        companyDatas=null;
        DataFactory.getInstence().loadSearchCompanys(key);
    }




    public  void onLoadBenefitGroups(ArrayList<SelectObject> datas){}
    public  void onLoadBenefitKinds(ArrayList<SelectObject> datas){}
    public  void onLoadCompanySort(ArrayList<SelectObject> datas){}
    public  void onLoadCompanys(ArrayList<SelectObject> datas){

        if(datas.size()<1){
            MainActivity.getInstence().viewAlert("", R.string.msg_no_data, null);
            return;
        }
        companyDatas=datas;

        ArrayList<String> results=new ArrayList<String>();
        for(int i=0;i<datas.size();++i){
            results.add(datas.get(i).title);

        }

        PageObject pInfo=new PageObject(Config.POPUP_LIST_RESULT);
        Map<String,Object> pinfo=new HashMap<String,Object>();
        pinfo.put("datas", results);
        pInfo.dr=1;
        pInfo.info=pinfo;
        PopupListResult pop=(PopupListResult)MainActivity.getInstence().addPopup(pInfo);
        pop.delegate=this;
    }


    public  void removeGroup(){
        delegate=null;

        if(ConsumeInfo.getInstence().delegate==this){
            ConsumeInfo.getInstence().delegate=null;
        }
        if(ConsumeInfo.getInstence().mdDelegate==this){
            ConsumeInfo.getInstence().mdDelegate=null;
        }
        if(DataFactory.getInstence().delegate==this){
            DataFactory.getInstence().delegate=null;
        }

    }


    public void  onModifyDefaultData(boolean result){

        if(result==false){
            onModifyError();
            return;
        }
        MainActivity.getInstence().changeViewBack();
        ConsumeInfo.getInstence().loadDefaultData(true);

    }
    public void  onModifyAddedDatas(boolean result){}
    public void  onModifyBenifitDatas(boolean result){}

    private void onModifyError(){

        MainActivity.getInstence().viewAlert("",R.string.msg_consume_modify_error,null);
    }

    private void addInfo(){
        if(isInitMsg==false){
            return;
        }
        isInitMsg=false;
        AlertObject aInfo=new AlertObject();

        aInfo.isDimed=true;

        aInfo.subTitleID=R.string.msg_card_more_recommend;

        if(MemberInfo.getInstence().getLoginState()==false){

            aInfo.selectID= R.string.msg_card_more_join;
        }else {
            aInfo.selectID = R.string.msg_card_more_paturn;

        }

        AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,new AlertView.AlertViewDelegate(){

            @Override
            public void onSelected(AlertView v, int selectIdx) {
                if(selectIdx==0){
                    if(MemberInfo.getInstence().getLoginState()==false){
                        PageObject pInfo= new PageObject(Config.PAGE_MEMBER);
                        MainActivity.getInstence().changeView(pInfo);

                    }else {
                        PageObject pInfo=new PageObject(Config.PAGE_MYPAGE);
                        Map<String,Object> pinfo=new HashMap<String,Object>();
                        pInfo.info=pinfo;
                        pInfo.info.put("pageIdx",2);


                        MainActivity.getInstence().changeView(pInfo);
                    }

                }else{


                }

            }
        });
        MainActivity.getInstence().body.addView(alertView);
    }

    public void onLoadDefaultData(DefaultObject defaultObject){
        paturnStack.removeAllViews();
        currentDefaultObj=defaultObject;
        ListGroupSearch list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("연간카드이용금액",defaultObject.getConsumePerYearOnly());
        paturnStack.addView(list);

        list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("월최소이용금액",defaultObject.getConsumePerMonthMin());
        paturnStack.addView(list);

        list=new ListGroupSearch(MainActivity.getInstence());
        list.setData("가계연간수입",defaultObject.getIncomePerYear()+"...");
        paturnStack.addView(list);



    }
    public void onLoadAddedDatas(ArrayList<AddedGroup> addeds){}
    public void onLoadBenifitDatas(ArrayList<BenifitGroup> benifits){}


    public void onSelectData(int idx){

        if(companyDatas==null){
            return;
        }
        selectCompany = companyDatas.get(idx);
        inputCompany.setText(selectCompany.title);

    }










 /////////////////////////////////////////









}
