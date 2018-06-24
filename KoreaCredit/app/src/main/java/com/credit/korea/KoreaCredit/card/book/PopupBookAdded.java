package com.credit.korea.KoreaCredit.card.book;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.SelectObject;
import com.credit.korea.KoreaCredit.listresult.PopupListResult;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lib.CommonUtil;
import lib.calendar.DayData;
import lib.core.PageObject;
import lib.core.ViewCore;


public class PopupBookAdded extends ViewCore implements OnClickListener, AdapterView.OnItemSelectedListener,
                                                        View.OnKeyListener,MyCardInfo.MyCardDelegate,PopupListResult.PopupListResultDelegate,
                                                        DataFactory.DataFactoryDelegate,BookInfo.BookModifyDelegate
{
    private ImageView titleBenefitGroup,titleBenefitKind,titleCompanySort,imgTitle;
    private TextView textDate;
    private EditText inputCompany, inputPrice;
    private Spinner spinnerCardName,spinnerBenefitGroup,spinnerBenefitKind,spinnerCompanySort;
    private ImageButton btnComplete;

    private Button btnSearch,btnDate;

    private ArrayList<CardObject> cards;
    private CardObject selectedCard;

    private SelectObject selectCompany,selectedGroup,selectedKind,selectedSort;

    private  String selectDate;
    private  ArrayList<SelectObject> companyDatas,groups,kinds,sorts;
    private boolean isAddMode,isSelectMode;
    private BookUnit data,parentData;
    private String bookSeq;




    public PopupBookAdded(Context context, PageObject pageInfo) {
        super(context, pageInfo);

        View.inflate(context, R.layout.popup_card_book_added, this);
        imgTitle = (ImageView) findViewById(R.id._imgTitle);
        bookSeq="";
        if(pageInfo.info!=null){
            data=(BookUnit)pageInfo.info.get("data");
            if(data!=null){
                bookSeq=data.seq;
                isAddMode=false;
                imgTitle.setImageResource(R.drawable.book_add_title1);
            }else{
                imgTitle.setImageResource(R.drawable.book_add_title);
                parentData=(BookUnit)pageInfo.info.get("parentData");

                isAddMode=true;

            }
        }else{
            data=null;
            isAddMode=true;

        }
        Log.i("","isAddMode : "+isAddMode);


        titleBenefitGroup = (ImageView) findViewById(R.id._titleBenefitGroup);
        titleBenefitKind = (ImageView) findViewById(R.id._titleBenefitKind);
        titleCompanySort = (ImageView) findViewById(R.id._titleCompanySort);

        spinnerBenefitGroup = (Spinner) findViewById(R.id._spinnerBenefitGroup);
        spinnerBenefitKind = (Spinner) findViewById(R.id._spinnerBenefitKind);
        spinnerCompanySort = (Spinner) findViewById(R.id._spinnerCompanySort);

        inputCompany = (EditText) findViewById(R.id._inputCompany);
        inputPrice = (EditText) findViewById(R.id._inputPrice);
        textDate=(TextView) findViewById(R.id._textDate);
        spinnerCardName = (Spinner) findViewById(R.id._spinnerCardName);

        btnComplete = (ImageButton) findViewById(R.id._btnComplete);

        btnSearch = (Button) findViewById(R.id._btnSearch);
        btnDate = (Button) findViewById(R.id._btnDate);

        btnComplete.setOnClickListener(this);

        spinnerCardName.setOnItemSelectedListener(this);
        spinnerBenefitGroup.setOnItemSelectedListener(this);
        spinnerBenefitKind.setOnItemSelectedListener(this);
        spinnerCompanySort.setOnItemSelectedListener(this);

        inputCompany.setOnKeyListener(this);


        selectDate="";


        btnSearch.setTypeface(FontFactory.getInstence().FONT_KR);
        btnSearch.setOnClickListener(this);

        btnDate.setOnClickListener(this);




        if(isAddMode==true)
        {
            btnComplete.setImageResource(R.drawable.btn_complete_txt2);
            btnSearch.setVisibility(View.VISIBLE);


        }
        else
        {

            inputCompany.setText(data.title);
            inputPrice.setText(String.valueOf((int)Math.round(data.price)));
            btnComplete.setImageResource(R.drawable.btn_complete_txt1);
            //btnSearch.setVisibility(View.GONE);
        }
        inputPrice.setOnFocusChangeListener(new OnFocusChangeListener(){

            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus==true){
                    inputPrice.setText("");
                }

            }
        });

    }

    protected void doDirectBack(){
        super.doDirectBack();



    }
    protected void doMovedInit() {
        super.doMovedInit();




        DataFactory.getInstence().delegate=this;
        MyCardInfo.getInstence().delegate=this;
        MyCardInfo.getInstence().loadMyCards(false);

        BookInfo.getInstence().modifyDelegate=this;
        setDate();



        if(isAddMode==true){

            activeGroupMode();
        }else{
            passiveGroupMode();
        }


    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            onSelectDate(year,monthOfYear,dayOfMonth);

        }
    };
    private void setDate(){

        DayData day = new DayData(0,0,0);
        String dateStr = getNowStr();
        if(isAddMode==true) {


        }else{

            String ds=data.usedate.replace(".","");
            Log.i("setDate",ds);

            dateStr=dateStr.substring(0,4) + ds;
            Log.i("setDate",dateStr);
            if(dateStr.length()!=8){
                dateStr=getNowStr();

            }

        }

        day.setDayData(dateStr);
        selectDate= dateStr;
        textDate.setText(day.getYear()+"-"+day.getMonth()+"-"+day.getDate());

    }
    private String getNowStr(){

        long now = System.currentTimeMillis();
        Date dat = new Date(now);
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMdd");
        return dateNow.format(dat);
    }

    private void activeGroupMode(){
        isSelectMode=true;

        selectCompany=null;
        selectedGroup=null;
        selectedKind=null;
        selectedSort=null;


        titleBenefitGroup.setVisibility(View.GONE);
        titleBenefitKind.setVisibility(View.GONE);
        titleCompanySort.setVisibility(View.GONE);

        spinnerBenefitGroup.setVisibility(View.GONE);
        spinnerBenefitKind.setVisibility(View.GONE);
        spinnerCompanySort.setVisibility(View.GONE);





    }
    private void passiveGroupMode(){

        btnSearch.setVisibility(View.GONE);
        isSelectMode=false;
        selectedGroup=null;
        selectedKind=null;
        selectedSort=null;
        titleBenefitGroup.setVisibility(View.VISIBLE);
        titleBenefitKind.setVisibility(View.VISIBLE);
        titleCompanySort.setVisibility(View.VISIBLE);

        spinnerBenefitGroup.setVisibility(View.VISIBLE);
        spinnerBenefitKind.setVisibility(View.VISIBLE);
        spinnerCompanySort.setVisibility(View.VISIBLE);

        DataFactory.getInstence().loadBenefitGroups();

    }
    protected void doResize() {
        super.doResize();

    }
    public boolean onKey(View v, int keyCode, KeyEvent event){

        if(v==inputCompany && keyCode== KeyEvent.KEYCODE_ENTER){
             onSearch();
             return true;
        }


        return false;
    }
    public void onClick(View v) {

        if (v == btnComplete) {
            onComplete();

        }else if(v== btnSearch) {
            onSearch();
        }else if(v== btnDate) {

            DayData day = new DayData(0,0,0);
            day.setDayData(selectDate);

            new DatePickerDialog(MainActivity.getInstence(), dateSetListener, day.getYear(), day.getMonth()-1, day.getDate()).show();
        }
    }

    public void onModifyBookData(){

        mainActivity.removePopup(this);
    }
    public void onModifyError(){

        MainActivity.getInstence().viewAlert("", R.string.msg_data_added_err, null);

    }


    private void onSelectDate(int year,
                              int monthOfYear, int dayOfMonth){

        String yy= CommonUtil.intToText(year,4);
        String mm= CommonUtil.intToText(monthOfYear+1,2);
        String dd= CommonUtil.intToText(dayOfMonth,2);

        Log.i("","SELECTED");
        selectDate= yy+mm+dd;
        textDate.setText(yy+"-"+mm+"-"+dd);


        //datePicker.setVisibility(View.GONE);



    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spnir = (Spinner)parent;
        if(spnir.equals(spinnerCardName)){
            selectedCard=cards.get(position);

        }else if(spnir.equals(spinnerBenefitGroup)){

            selectedGroup=groups.get(position);
            selectedKind=null;
            selectedSort=null;

            DataFactory.getInstence().loadBenefitKinds(selectedGroup.id);
        }else if(spnir.equals(spinnerBenefitKind)){
            selectedKind=kinds.get(position);
            selectedSort=null;
            DataFactory.getInstence().loadCompanySort(selectedGroup.id,selectedKind.id);

        }else if(spnir.equals(spinnerCompanySort)){
            selectedSort=sorts.get(position);

        }






    }
    public void onNothingSelected(AdapterView<?> parent) {


    }
    public void onModifyMyCards(){}
    public void onLoadMyCounsels(ArrayList<MyCounselObject> myCounsels){}
    public void onLoadError(String path){}
    public void onLoadMyCards(ArrayList<CardObject> myCards){

        cards  = new ArrayList<CardObject>();
        for(int i=0;i<myCards.size();++i){
            if(myCards.get(i).isMine==true){

                cards.add(myCards.get(i));
            }

        }

        cards=myCards;
        if(cards.size()<1){

            MainActivity.getInstence().viewAlert("",R.string.msg_card_added_none,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PageObject pInfo;
                    if(which==-1){
                        goBack();
                        goAddPage();

                    }else{
                        goBack();

                    }

                }
            });

            return;
        }

        ArrayList<String> datas=new ArrayList<String>();
        int idx=0;
        for(int i=0;i<cards.size();++i){
            if(data!=null) {
                if (cards.get(i).idx == data.cardID) {
                    idx = i;
                }
            }


            datas.add(cards.get(i).title);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, datas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCardName.setAdapter(dataAdapter);
        spinnerCardName.setSelection(idx);





    }
    private void goAddPage(){

        PageObject pInfo=new PageObject(Config.PAGE_MYPAGE);
        pInfo.info=new HashMap<String,Object>();
        pInfo.info.put("pageIdx",1);
        mainActivity.changeView(pInfo);
    }
    private void goBack(){

        mainActivity.removePopup(this);
    }



    protected void doRemove() {

        super.doRemove();

        if(BookInfo.getInstence().modifyDelegate==this){

            BookInfo.getInstence().modifyDelegate=null;
        }
        if(MyCardInfo.getInstence().delegate==this){

            MyCardInfo.getInstence().delegate=null;
        }
        if(DataFactory.getInstence().delegate==this){

            DataFactory.getInstence().delegate=null;
        }


    }


    /////////////////////////////////////////
    private void onSearch() {
        String key = inputCompany.getText().toString();


        if (key.equals("") == true) {

            onLoadCompanys(new ArrayList<SelectObject>());
           // mainActivity.viewAlert("", R.string.msg_card_book_input_company, null);
            //inputCompany.requestFocus();
            return;
        }

        mainActivity.hideKeyBoard();
        companyDatas=null;
        DataFactory.getInstence().loadSearchCompanys(key);
    }

    public void onLoadBenefitGroups(ArrayList<SelectObject> datas){

        groups=datas;
        ArrayList<String> items=new ArrayList<String>();
        int idx=0;

        for(int i=0;i<groups.size();++i){
            if(data!=null) {
               // Log.i("",i+" groups.id "+groups.get(i).id+"  data.groupID"+data.groupID);
                if (groups.get(i).id.equals(data.groupID)) {
                    idx = i;
                    //selectedGroup=groups.get(i);
                }
            }else if(parentData!=null){
                if (groups.get(i).id.equals(parentData.groupID)) {
                    idx = i;
                    //selectedGroup=groups.get(i);
                }

            }
            items.add(groups.get(i).title);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBenefitGroup.setAdapter(dataAdapter);


        //if(selectedGroup!=null) {
            spinnerBenefitGroup.setSelection(idx);
            //DataFactory.getInstence().loadBenefitKinds(selectedGroup.id);
        //}

    }
    public void onLoadBenefitKinds(ArrayList<SelectObject> datas){

        kinds=datas;
        ArrayList<String> items=new ArrayList<String>();
        int idx=0;
        for(int i=0;i<kinds.size();++i){
            if(data!=null) {

              //  Log.i("",i+" kinds.id "+kinds.get(i).id+"  data.kinds"+data.kindID);
                if (kinds.get(i).id.equals(data.kindID)) {
                    idx = i;
                    //selectedKind=kinds.get(i);
                }
            }
            items.add(kinds.get(i).title);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBenefitKind.setAdapter(dataAdapter);


        //if(selectedKind!=null) {
            spinnerBenefitKind.setSelection(idx);
            //DataFactory.getInstence().loadCompanySort(selectedGroup.id,selectedKind.id);
        //}


    }
    public void onLoadCompanySort(ArrayList<SelectObject> datas){


        sorts=datas;
        ArrayList<String> items=new ArrayList<String>();
        int idx=0;
        for(int i=0;i<sorts.size();++i){
            if(data!=null) {

               // Log.i("",i+" sorts.id "+sorts.get(i).id+"  data.sortID"+data.sortID);
                if (sorts.get(i).id.equals(data.sortID)) {
                    idx = i;
                    //selectedSort=sorts.get(i);
                }
            }
            items.add(sorts.get(i).title);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanySort.setAdapter(dataAdapter);
        spinnerCompanySort.setSelection(idx);
        data=null;
    }




    public void onLoadCompanys(ArrayList<SelectObject> datas){


        if(datas.size()<1){
            //mainActivity.viewAlert("",R.string.msg_no_data,null);
           // return;
        }
        companyDatas=datas;


        SelectObject noneSelect=new SelectObject();
        noneSelect.setNoneData();
        companyDatas.add(noneSelect);

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

    public void onSelectData(int idx){

        if(companyDatas==null){
            return;
        }
        if(companyDatas.get(idx).id.equals(SelectObject.NONE_ID)==true) {
            passiveGroupMode();
        }else{
            activeGroupMode();
            selectCompany = companyDatas.get(idx);
            inputCompany.setText(selectCompany.title);


        }



    }


    private void onComplete() {

        Map<String, String> params = new HashMap<String, String>();

        if(selectedCard==null){
            mainActivity.viewAlert("", R.string.msg_card_book_selectcard, null);
            return;
        }
        params.put("card_idx", selectedCard.idx);

        String key="";
        if(isSelectMode==false){
            if(selectedGroup!=null){
                params.put("group_idx", selectedGroup.id);

            }else{
                mainActivity.viewAlert("", R.string.msg_card_book_selectgroup, null);
                return;

            }
            if(selectedKind!=null){

                params.put("sub_idx", selectedKind.id);

            }else{
                mainActivity.viewAlert("", R.string.msg_card_book_selectkind, null);
                return;
            }

            if(selectedSort!=null){
                params.put("sv_idx", selectedSort.id);
                params.put("sv_value", DataFactory.getInstence().getUriencode(selectedKind.title));
            }else{
                //mainActivity.viewAlert("", R.string.msg_card_book_selectsort, null);
                //return;

            }
            key = inputCompany.getText().toString();
            if (key.equals("") == true) {
                mainActivity.viewAlert("", R.string.msg_card_book_input_company, null);
                inputCompany.requestFocus();
                return;
            }

            params.put("shop_name", DataFactory.getInstence().getUriencode(key));
        }else{


            if(selectCompany==null){
                mainActivity.viewAlert("", R.string.msg_card_book_select_company, null);
                inputCompany.requestFocus();
                return;
            }else{
                params.put("sv_idx", selectCompany.id);
                params.put("sv_value", DataFactory.getInstence().getUriencode(selectCompany.title));

            }

            //params.put("group_idx", data.groupID);
        }


        key = inputPrice.getText().toString();
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_card_book_input_price, null);
            inputPrice.requestFocus();
            return;
        }
        params.put("price", key);


        key = selectDate;
        if (key.equals("") == true) {
            mainActivity.viewAlert("", R.string.msg_card_book_input_date, null);
            return;
        }

        params.put("usedate",key);


        if(isAddMode==false){
            params.put("bookuse_idx", bookSeq);
            BookInfo.getInstence().modifyBookUnit(params,BookInfo.MODIFY_TYPE_REPLACE);
        }else{

            BookInfo.getInstence().modifyBookUnit(params,BookInfo.MODIFY_TYPE_ADD);
        }



    }
}
