package com.credit.korea.KoreaCredit.card.find;


import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.SelectObject;
import com.credit.korea.KoreaCredit.card.search.ListGroupSearch;
import com.credit.korea.KoreaCredit.listresult.PopupListResult;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.mypage.MyPageInfo;
import com.credit.korea.KoreaCredit.mypage.MySearchObject;
import com.credit.korea.KoreaCredit.mypage.consume.AddedGroup;
import com.credit.korea.KoreaCredit.mypage.consume.BenifitGroup;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;
import com.credit.korea.KoreaCredit.mypage.consume.DefaultObject;
import com.credit.korea.KoreaCredit.mypage.consume.IListSelect;
import com.credit.korea.KoreaCredit.mypage.consume.ISelectListener;
import com.credit.korea.KoreaCredit.mypage.consume.ListBtn;
import com.credit.korea.KoreaCredit.mypage.consume.ListCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;


public class GroupFind extends FrameLayout implements View.OnClickListener ,PopupListResult.PopupListResultDelegate,DataFactory.DataFactoryDelegate
                                                    ,AdapterView.OnItemSelectedListener,ISelectListener,TextView.OnEditorActionListener
{


    private LinearLayout stackKinds,stackNations;
    private Button btnSearch;
    private ImageButton btnComplete;
    private EditText inputCompany,inputMin,inputMax,inputKey;
    private  ArrayList<SelectObject> companyDatas;
    private SelectObject selectCompany;
    private Spinner spinnerCompanys;
    private String selectCardCompany;
    private ArrayList<IListSelect> listNations,listKinds;

    private String[] searchCompanys;

    public GroupFindDelegate delegate;

    public interface GroupFindDelegate
    {
        void onFindStart(GroupFind v,Map<String,String> sendParams);


    }


    public GroupFind(Context context)
	{
        super(context);
        View.inflate(context, R.layout.group_cardfind,this);
        stackKinds = (LinearLayout) findViewById(R.id._stackKinds);
        stackNations = (LinearLayout) findViewById(R.id._stackNations);

        btnSearch = (Button) findViewById(R.id._btnSearch);
        btnComplete = (ImageButton) findViewById(R.id._btnComplete);

        inputCompany = (EditText) findViewById(R.id._inputCompany);
        inputMin = (EditText) findViewById(R.id._inputMin);
        inputMax = (EditText) findViewById(R.id._inputMax);
        inputKey = (EditText) findViewById(R.id._inputKey);
        spinnerCompanys = (Spinner) findViewById(R.id._spinnerCompanys);

        listNations = new ArrayList<IListSelect>();
        listKinds = new ArrayList<IListSelect>();

        btnSearch.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
        spinnerCompanys.setOnItemSelectedListener(this);

        searchCompanys = new String[DataFactory.SEARCH_COMPANYS.length+1];
        searchCompanys[0]=DataFactory.NONE_SELECT;

        for(int i=0;i<DataFactory.SEARCH_COMPANYS.length;++i){

            searchCompanys[1+i] = DataFactory.SEARCH_COMPANYS[i];
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.getInstence(), android.R.layout.simple_spinner_item, searchCompanys);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanys.setAdapter(dataAdapter);
        spinnerCompanys.setSelection(0);

        createListBox(listKinds,stackKinds,DataFactory.SEARCH_KINDS_MIN,3,0);
        createListBox(listNations,stackNations,DataFactory.SEARCH_NATIONS,3,1);

        inputCompany.setOnEditorActionListener(this);

    }
    public void initGroup(){

        DataFactory.getInstence().delegate=this;

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

        }else{

            onSearch();
        }


    }
    private void createListBox(ArrayList<IListSelect> arr, LinearLayout box, String[] datas, int row,int type){

        int lineNum= (int)Math.ceil((float)datas.length/(float)row);
        for(int i=0;i<lineNum;++i){
            LinearLayout stack=new LinearLayout(MainActivity.getInstence());
            box.addView(stack, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            stack.setWeightSum((float)row);

            int limit = (i * row) + row;
            if(limit>datas.length){
                limit=datas.length;
            }

            limit=limit - (i * row);
            for(int x=0;x<row;++x){
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout.weight = 1;
                if(x < limit) {

                    int idx = (i * row) + x;
                    if (type == 0) {
                        ListBtn btn = new ListBtn(MainActivity.getInstence());
                        stack.addView(btn, layout);

                        btn.setData(false, datas[idx]);
                        btn.setOnSelectListener(this);
                        arr.add(btn);
                    } else {
                        ListCheck select = new ListCheck(MainActivity.getInstence());
                        stack.addView(select, layout);
                        if(idx==0){
                            select.setData(true, datas[idx]);
                        }else{
                            select.setData(false, datas[idx]);
                        }

                        select.setOnSelectListener(this);
                        arr.add(select);

                    }
                }else{
                    FrameLayout frame= new FrameLayout(MainActivity.getInstence());
                    stack.addView(frame, layout);

                }

            }


        }


    }
    public void onSelectedChange(View v,boolean isSelected){

        if(listKinds.indexOf(v)!=-1){


        }else if(listNations.indexOf(v)!=-1){
            setOnlySelectedState(listNations ,listNations.indexOf(v));

        }

    }
    public void onSelectedChange(View v,int selectedIdx){}

    private void setOnlySelectedState(ArrayList< IListSelect > lists ,int selectID) {


        for(int i=0;i<lists.size();++i){
            if(i==selectID){

            }else{

                lists.get(i).setSelect(false);
            }
        }

    }
    private String getSelectValue(String[] keys, ArrayList< IListSelect > lists) {
        String value="";
        for(int i=0;i<lists.size();++i){
            if(lists.get(i).getSelected()==true){
                if(value.equals("")==true){
                    value= keys[i];
                }else{
                    value +=","+keys[i];

                }
            }
        }
        return value;
    }

    private String getSelectKey() {
        String value="";
        for(int i=0;i<listNations.size();++i){
            if(listNations.get(i).getSelected()==true){
                switch(i)
                {
                    case 0:
                        value="N";
                        break;
                    case 1:
                        value="K";
                        break;
                    case 2:
                        value="E";
                        break;
                }
            }
        }
        return value;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectCardCompany=searchCompanys[position];

    }

    public void onNothingSelected(AdapterView<?> parent) {


    }
    private void onComplete() {

        Map<String, String> params = new HashMap<String, String>();

        MainActivity mainActivity = ( MainActivity)MainActivity.getInstence();

        String key = inputCompany.getText().toString();
        if (key.equals("") == true) {

        }else{

            if(selectCompany!=null){
                params.put("sv_value", DataFactory.getInstence().getUriencode(selectCompany.title));
                params.put("sv_idx", selectCompany.id);
            }else{

                params.put("sv_value", key);
                params.put("sv_idx","");
            }
        }


        key = inputKey.getText().toString();
        if (key.equals("") == true) {

        }else{

            params.put("card_name", DataFactory.getInstence().getUriencode(key));
        }




        key = inputMin.getText().toString();
        if (key.equals("") == true) {

        }else{

            params.put("fee_start", key);
        }



        key = inputMax.getText().toString();
        if (key.equals("") == true) {

        }else{

            params.put("fee_end", key);
        }

        if(selectCardCompany.equals(DataFactory.NONE_SELECT)==true){

        }else{
            params.put("card_company ", DataFactory.getInstence().getUriencode(selectCardCompany));
        }

        key=getSelectValue(DataFactory.SEARCH_KINDS_MIN, listKinds);
        if(key.equals("")==true){
            mainActivity.viewAlert("", R.string.msg_card_find_select_card, null);

            return;
        }

        params.put("card_type",DataFactory.getInstence().getUriencode(key) );
        params.put("nation", getSelectKey());




        if(delegate!=null){
            delegate.onFindStart(this,params);
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
    public  void removeGroup(){
        delegate=null;
        if(DataFactory.getInstence().delegate==this){
            DataFactory.getInstence().delegate=null;
        }

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


    public void onSelectData(int idx){

        if(companyDatas==null){
            return;
        }
        selectCompany = companyDatas.get(idx);
        inputCompany.setText(selectCompany.title);

    }





 /////////////////////////////////////////









}
