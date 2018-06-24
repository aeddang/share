package com.credit.korea.KoreaCredit.mypage;


import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.mypage.consume.ConsumeInfo;
import com.credit.korea.KoreaCredit.mypage.consume.IListSelect;
import com.credit.korea.KoreaCredit.mypage.consume.ISelectListener;
import com.credit.korea.KoreaCredit.mypage.consume.ListBtn;
import com.credit.korea.KoreaCredit.mypage.consume.ListCheck;
import com.credit.korea.KoreaCredit.mypage.consume.ListSelect;

import java.util.ArrayList;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PageMySearch extends ViewCore implements View.OnClickListener,MyPageInfo.SearchDelegate,ISelectListener{


    private LinearLayout stackStd,stackKinds,stackCompanys,stackNations,stackAirs;
    private FrameLayout closeBox;
    private ArrayList<IListSelect> listStd,listNations,listAirs,listKinds,listCompanys;
    private ListAddedModify listAddedModify;
    private Button btnOpen,btnClose;
    private ImageView titleAir;
    private ImageButton btnComplete;
    private MySearchObject myData;
    private String selectSTD;
    public boolean isModify,isAddedMode;
    public PageMySearch(Context context, PageObject pageInfo)
	{
		super(context, pageInfo);
	    View.inflate(context, R.layout.page_mypage_search,this);
        if(pageInfo.info!=null){
            myData=(MySearchObject)pageInfo.info.get("myObj");
            isModify=true;

        }else{
            myData=null;
            isModify=false;

        }
        closeBox  = (FrameLayout) findViewById(R.id._closeBox);
        btnOpen = (Button) findViewById(R.id._btnOpen);
        btnClose= (Button) findViewById(R.id._btnClose);
        listAddedModify = (ListAddedModify) findViewById(R.id._listAddedModify);
        stackStd = (LinearLayout) findViewById(R.id._stackStd);
        stackKinds = (LinearLayout) findViewById(R.id._stackKinds);
        stackCompanys = (LinearLayout) findViewById(R.id._stackCompanys);
        stackNations = (LinearLayout) findViewById(R.id._stackNations);
        stackAirs = (LinearLayout) findViewById(R.id._stackAirs);

        titleAir = (ImageView) findViewById(R.id._titleAir);
        listStd = new ArrayList<IListSelect>();
        listNations = new ArrayList<IListSelect>();
        listAirs = new ArrayList<IListSelect>();
        listKinds = new ArrayList<IListSelect>();
        listCompanys = new ArrayList<IListSelect>();




        btnComplete =(ImageButton) findViewById(R.id._btnComplete);

        createListBox(listStd,stackStd,DataFactory.SEARCH_STANDARDS,2,1,0);
        createListBox(listKinds,stackKinds,DataFactory.SEARCH_KINDS_MIN,3,1,0);
        createListBox(listCompanys,stackCompanys,DataFactory.SEARCH_COMPANYS,3,0,-1);
        createListBox(listNations,stackNations,DataFactory.SEARCH_NATIONS,3,1,0);
        createListBox(listAirs,stackAirs,DataFactory.SEARCH_AIR,3,0,-1);

        selectSTD="";
        btnOpen.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnComplete.setOnClickListener(this);

        Log.i("", "onModifyMySerch TOP "+isModify);
        if(isModify==false) {

            myData=MyPageInfo.getInstence().getMySearchOption();
            if(myData!=null) {
                onLoadMySerch(myData);
            }

        }else{

            Log.i("", "setMyData "+isModify);
            setMyData(myData);
        }

        titleAir.setVisibility(View.GONE);
        stackAirs.setVisibility(View.GONE);
        setAddMode(false);

    }
    private void setAddMode(boolean isOpen){
        isAddedMode=isOpen;
        listAddedModify.setActive(isOpen);

        if(isOpen==true){
            closeBox.setVisibility(View.VISIBLE);
            btnOpen.setVisibility(View.GONE);
        }else {
            closeBox.setVisibility(View.GONE);
            btnOpen.setVisibility(View.VISIBLE);
        }


    }


    private void createListBox(ArrayList<IListSelect> arr, LinearLayout box, String[] datas, int row,int type,int defaultSelected){

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
                     boolean isSelected=false;
                     if(i==defaultSelected){
                         isSelected=true;
                     }
                     if (type == 0) {
                         ListBtn btn = new ListBtn(MainActivity.getInstence());
                         stack.addView(btn, layout);

                         btn.setData(isSelected, datas[idx]);
                         btn.setOnSelectListener(this);
                         arr.add(btn);
                     } else {
                         ListCheck select = new ListCheck(MainActivity.getInstence());
                         stack.addView(select, layout);
                         select.setData(isSelected, datas[idx]);
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

        if(listStd.indexOf(v)!=-1){
            setOnlySelectedState(listStd ,listStd.indexOf(v));
            if(isSelected==true){
                selectSTD=DataFactory.SEARCH_STANDARDS[listStd.indexOf(v)];

            }else{
                selectSTD="";
            }

            if(selectSTD.equals(DataFactory.SEARCH_STANDARDS[3])==false){
                stackAirs.setVisibility(View.GONE);
                titleAir.setVisibility(View.GONE);
                for(int i=0;i<listAirs.size();++i){
                    listAirs.get(i).setSelect(false);

                }
            }else{
                titleAir.setVisibility(View.VISIBLE);
                stackAirs.setVisibility(View.VISIBLE);
            }


         }else if(listKinds.indexOf(v)!=-1){
            setOnlySelectedState(listKinds ,listKinds.indexOf(v));

         }else if(listCompanys.indexOf(v)!=-1){


         }else if(listNations.indexOf(v)!=-1){
            setOnlySelectedState(listNations ,listNations.indexOf(v));

         }else if(listAirs.indexOf(v)!=-1){


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
    protected void doResize()
    {
    	super.doResize();

    }
    private void setListChanges(){


        setListChange(0, listStd);
        setListChange(1, listKinds);
        setListChange(2, listCompanys);

        setListChange(3, listNations);
        setListChange(4, listAirs);



    }

    private boolean setDataChanges(){


        boolean isSelected=setSelectChange(0,DataFactory.SEARCH_STANDARDS, listStd);

        setSelectChange(1,DataFactory.SEARCH_KINDS_MIN, listKinds);
        setSelectChange(2,DataFactory.SEARCH_COMPANYS,listCompanys);

        setSelectChange(3,DataFactory.SEARCH_NATIONS, listNations);
        setSelectChange(4,DataFactory.SEARCH_AIR, listAirs);

        return isSelected;

    }
    private void setListChange(int idx, ArrayList< IListSelect > lists) {
        String value="";
        for(int i=0;i<lists.size();++i){
            boolean isChecked = myData.isChecked(idx,i);
            lists.get(i).setSelect(isChecked);

        }

    }

    private boolean setSelectChange(int idx,String[] keys, ArrayList< IListSelect > lists) {
        String value="";
        boolean isSelected=false;
        for(int i=0;i<lists.size();++i){
            if(lists.get(i).getSelected()==true){
                if(value.equals("")==true){
                    value= keys[i];
                    isSelected=true;
                }else{
                    value +=","+keys[i];

                }
            }
        }
        myData.setValue(idx,value);
        return isSelected;
    }

    public void onClick(View v) {

        if(v == btnComplete){
            if(setDataChanges()==true){
                MyPageInfo.getInstence().modifyMySearchOption(myData);
            }else{
                MainActivity.getInstence().viewAlert("",R.string.msg_search_select_sdt_fail,null);
            }


        }else if(v == btnOpen){



                setAddMode(true);


        }else if(v == btnClose){

            setAddMode(false);

        }


    }



	protected void doMovedInit() {
	    super.doMovedInit();
        if(isModify==false) {
            MyPageInfo.getInstence().searchDelegate = this;
            backButton.setVisibility(View.GONE);
        }else{
            backButton.setVisibility(View.VISIBLE);

        }
        if(myData==null){
            MyPageInfo.getInstence().loadMySearchOption(false);
        }




	}
    private void setMyData(MySearchObject obj){

        myData=obj;
        setListChanges();

        if(myData.isActive==false && isModify==true){
            MainActivity.getInstence().viewAlert("",R.string.msg_search_default_added,null);

        }
    }
    //override
    public void onModifyMySerch(boolean result){

         if(result==false){

             mainActivity.viewAlert("",R.string.msg_data_modify_err,null);
             return;
         }
         if(isAddedMode==true){
             MyPageInfo.getInstence().loadMySearchOption(true);
             if(listAddedModify.adatas!=null) {
                ConsumeInfo.getInstence().modifyAddedData(listAddedModify.adatas);
            }
         }else{
             mainActivity.viewAlert("",R.string.msg_data_modify_complete,null);

         }


    }

    public void onLoadMySerch(MySearchObject obj){

        setMyData(obj);


    }

    protected void doRemove() {

        super.doRemove();
        listAddedModify.remove();

        if(MyPageInfo.getInstence().searchDelegate==this)
        {
            MyPageInfo.getInstence().searchDelegate = null;
        }


    }





 /////////////////////////////////////////









}
