package com.credit.korea.KoreaCredit.mypage.consume;


import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import lib.core.PageObject;
import lib.core.ViewCore;


public class PageConsume extends ViewCore implements View.OnClickListener,ConsumeInfo.ConsumeInfoDelegate ,ConsumeInfo.ConsumeInfoModifyDelegate,ConsumeInfo.ConsumeAutoUpdateDelegate{

    private TextView textUsedYear,textUsedMonth,textIncome,textSex,textMerried,textFamilly,textBirth,textWhere;

    private Button btnModify,btnAddBenefit,btnAutoOk,btnAutoNo;
    private LinearLayout benifitStack;



    private ArrayList<BenifitGroup> currentBenifits;
    private DefaultObject currentDefaultObj;



    private ArrayList<ListBenifit> benifitLists;


    public PageConsume(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_mypage_consume,this);
        textUsedYear=(TextView) findViewById(R.id._textUsedYear);
        textUsedMonth=(TextView) findViewById(R.id._textUsedMonth);
        textIncome=(TextView) findViewById(R.id._textIncome);
        textWhere=(TextView) findViewById(R.id._textWhere);
        textSex=(TextView) findViewById(R.id._textSex);
        textMerried=(TextView) findViewById(R.id._textMerried);
        textFamilly=(TextView) findViewById(R.id._textFamilly);
        textBirth=(TextView)findViewById(R.id._textBirth);

        btnModify=(Button) findViewById(R.id._btnModify);

        btnAddBenefit =(Button) findViewById(R.id._btnAddBenefit);
        btnAutoOk =(Button) findViewById(R.id._btnAutoOk);
        btnAutoNo =(Button) findViewById(R.id._btnAutoNo);

        benifitStack=(LinearLayout) findViewById(R.id._benifitStack);

        textUsedYear.setTypeface(FontFactory.getInstence().FONT_KR);
        textUsedMonth.setTypeface(FontFactory.getInstence().FONT_KR);
        textIncome.setTypeface(FontFactory.getInstence().FONT_KR);
        textSex.setTypeface(FontFactory.getInstence().FONT_KR);
        textMerried.setTypeface(FontFactory.getInstence().FONT_KR);
        textFamilly.setTypeface(FontFactory.getInstence().FONT_KR);
        textBirth.setTypeface(FontFactory.getInstence().FONT_KR);
        textWhere.setTypeface(FontFactory.getInstence().FONT_KR);


        btnModify.setOnClickListener(this);

        btnAddBenefit.setOnClickListener(this);

        btnAutoOk.setOnClickListener(this);
        btnAutoNo.setOnClickListener(this);
        currentBenifits=ConsumeInfo.getInstence().getBenifitData();
        currentDefaultObj=ConsumeInfo.getInstence().getDefaultData();

        if(currentDefaultObj!=null){

            onLoadDefaultData(currentDefaultObj);
        }
        if(currentBenifits!=null){

            onLoadBenifitDatas(currentBenifits);
        }


    }

	protected void doResize()
    {
    	super.doResize();

    }
    public void onClick(View v) {

        if(v == btnModify){
            addPopup(0);
        }
        else if(v == btnAddBenefit){
            addPopup(2);
        }else if(v== btnAutoOk) {
            if (btnAutoOk.isSelected() == false) {
                changeAutoUpdate();
            }

        }else if(v== btnAutoNo) {
            if (btnAutoNo.isSelected() == false) {
                btnAutoOk.setSelected(false);
                btnAutoNo.setSelected(true);

            }
            modifyAutoUpdate();
        }

    }
    private void changeAutoUpdate(){

        //if(ConsumeInfo.getInstence().isSMS==false){

            MainActivity.getInstence().viewAlertSelect("",R.string.msg_consume_update_modify_anable,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PageObject pInfo;
                    if(which==-1){

                        btnAutoOk.setSelected(true);
                        btnAutoNo.setSelected(false);
                        modifyAutoUpdate();

                    }else{


                    }

                }
            });
            //return;
        //}
        //btnAutoOk.setSelected(true);
        //btnAutoNo.setSelected(false);
        //modifyAutoUpdate();
    }
    private void modifyAutoUpdate(){

        ConsumeInfo.getInstence().modifyAutoUpdate(btnAutoOk.isSelected());
    }


    protected void doResetOnListener() {
        ConsumeInfo.getInstence().delegate=this;
        ConsumeInfo.getInstence().mdDelegate=this;
    }
	protected void doMovedInit() {
	    super.doMovedInit();

        doResetOnListener();

        if(currentDefaultObj==null){

            ConsumeInfo.getInstence().loadDefaultData(false);
        }
        if(currentBenifits==null){

            ConsumeInfo.getInstence().loadBenifitData(false);
        }

        ConsumeInfo.getInstence().updateDelegate=this;
        ConsumeInfo.getInstence().loadAutoUpdate();






	}



    private void addPopup(int typeIDX){

        PageObject pInfo=null;
        Map<String,Object> pinfo=new HashMap<String,Object>();

        if(typeIDX==0) {
            if(currentDefaultObj==null){
                return;
            }
            pInfo=new PageObject(Config.POPUP_CONSUME_DEFAULT);
            pinfo.put("myObj", currentDefaultObj);

        }else {

            if(currentBenifits==null){
                return;
            }
            pInfo=new PageObject(Config.POPUP_CONSUME_MODIFY);
            pinfo.put("bdatas", currentBenifits);
            //pInfo.info.put("myObj", currentBenifits);

        }

        pInfo.dr=1;
        pInfo.info=pinfo;
        mainActivity.addPopup(pInfo);
    }


    private void removeBenifits(){
        if(benifitLists!=null) {
            for (int i = 0; i < benifitLists.size(); ++i) {
                benifitLists.get(i).removeList();

            }
            benifitLists.clear();
            benifitLists=null;
        }

    }
    protected void doRemove() {

        super.doRemove();


        removeBenifits();

        if(ConsumeInfo.getInstence().delegate==this)
        {
            ConsumeInfo.getInstence().delegate = null;
            ConsumeInfo.getInstence().mdDelegate=null;
            ConsumeInfo.getInstence().updateDelegate=null;
        }


    }
    public void onModifyDefaultData(boolean result){

        if(result==false){
            onModifyError();
            return;
        }
        MainActivity.getInstence().removePopup();
        MainActivity.getInstence().viewAlert("",R.string.msg_consume_default_modify_complete,null);
        ConsumeInfo.getInstence().loadDefaultData(true);

    }
    public void onModifyAddedDatas(boolean result){
        if(result==false){
            onModifyError();
            return;
        }
        MainActivity.getInstence().removePopup();
        MainActivity.getInstence().viewAlert("",R.string.msg_consume_added_modify_complete,null);
        ConsumeInfo.getInstence().loadAddedData(true);
    }
    public void onModifyBenifitDatas(boolean result){
        if(result==false){
            onModifyError();
            return;
        }
        MainActivity.getInstence().removePopup();
        MainActivity.getInstence().viewAlert("",R.string.msg_consume_benefit_modify_complete,null);
        ConsumeInfo.getInstence().loadBenifitData(true);
    }

    private void onModifyError(){

        MainActivity.getInstence().viewAlert("",R.string.msg_consume_modify_error,null);
    }
    public void onLoadAutoUpdate(boolean isAutoUpdate){

        btnAutoOk.setSelected( isAutoUpdate);
        if(isAutoUpdate==true){

            btnAutoNo.setSelected(false);
        }else{
            btnAutoNo.setSelected(true);

        }
    }
    public void onModifyAutoUpdate(boolean result){
        if(result==true){

            ConsumeInfo.getInstence().modifyAutoUpdateComplete(btnAutoOk.isSelected());
        }else{

            if(btnAutoOk.isSelected()==true){
                btnAutoOk.setSelected(false);
                btnAutoNo.setSelected(true);
            }else{
                btnAutoOk.setSelected(true);
                btnAutoNo.setSelected(false);

            }
            MainActivity.getInstence().viewAlert("", R.string.msg_consume_update_fail, null);

        }
    }
    public void onLoadDefaultData(DefaultObject defaultObject){
        currentDefaultObj=defaultObject;

        textWhere.setText(currentDefaultObj.sido+" "+currentDefaultObj.gugun);
        textUsedYear.setText(currentDefaultObj.getConsumePerYearOnly());
        textUsedMonth.setText(currentDefaultObj.getConsumePerMonthMin());
        textIncome.setText(currentDefaultObj.getIncomePerYear());
        textSex.setText(currentDefaultObj.getSex());
        textMerried.setText(currentDefaultObj.getMerried());
        textFamilly.setText(currentDefaultObj.getFamilly());
        textBirth.setText(currentDefaultObj.getBirth());






    }
    public void onLoadAddedDatas(ArrayList<AddedGroup> addeds){

    }
    public void onLoadBenifitDatas(ArrayList<BenifitGroup> benifits){
        removeBenifits();

        currentBenifits=benifits;
        benifitLists = new ArrayList<ListBenifit>();
        Log.i("", "benifits : " + benifits.size());

        for(int i=0;i<benifits.size();++i){
            BenifitGroup group=benifits.get(i);
            addBenifitGroups(group);

        }


    }
    public void addBenifitGroups(BenifitGroup group){
        //Log.i("", "group.lists : " + group.lists.size());

        for(int i=0;i<group.lists.size();++i){

            //Log.i("", "group.lists select: " + group.lists.get(i).isSelected);
            if(group.lists.get(i).isSelected==true) {
                ListBenifit list = new ListBenifit(MainActivity.getInstence());
                benifitStack.addView(list);
                list.setData(group.lists.get(i));
                benifitLists.add(list);
            }

        }


    }
    public void onLoadError(ConsumeInfo info,String path){


    }







 /////////////////////////////////////////









}
