package com.credit.korea.KoreaCredit.mypage.consume;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.ArrayList;


public class GroupSelect extends FrameLayout implements ISelectListener{

    private ListSelect listSelect;
    private ArrayList<IListSelect>listSelects;


    private TextView textTitle;
    private LinearLayout body;
    private boolean isMultiSelected;

    private AddedGroup addGroup;

    private BenifitGroup benifitGroup;

    public GroupSelect(Context context)
	{
        super(context);
        View.inflate(context, R.layout.group_select,this);
        body = (LinearLayout) findViewById(R.id._body);
        textTitle = (TextView) findViewById(R.id._textTitle);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR_B);
    }

    public void setAddedGroup(AddedGroup group)
    {
        addGroup=group;
        textTitle.setText(group.title);
        isMultiSelected=group.isMultiSelected;
        if(isMultiSelected==true){

            createCheckLists();
        }else{
            createSelectList(group);
        }


    }
    public void setBenifitGroup(BenifitGroup group)
    {
        benifitGroup=group;
        textTitle.setText(group.title);
        isMultiSelected=group.isMultiSelected;
        createCheckLists();


    }

    private void createSelectList(AddedGroup group){
        listSelect=new ListSelect(MainActivity.getInstence());
        LinearLayout.LayoutParams layout=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        body.addView(listSelect,layout);

        int sIdx=0;
        ArrayList<String> datas=new ArrayList<String>();
        datas.add("선택안함");
        for(int i=0;i<group.lists.size();++i){
            AddedObject added=group.lists.get(i);
            if(added.isSelected==true){
                sIdx=i;

            }
            datas.add(added.title);

        }
        listSelect.setData(datas,sIdx);
    }


    private void createCheckLists(){
        listSelects=new ArrayList<IListSelect>();
        LinearLayout cBody=null;
        int cnum = 0;
        if(addGroup!=null){
            cnum = addGroup.lists.size();

        }else if(benifitGroup!=null){
            cnum = benifitGroup.lists.size();

        }



        int num = (int)Math.ceil((float)cnum/3.f)*3;

        for(int i=0;i<num;++i){
            int idx=i%3;
            if(idx==0){
                cBody=new LinearLayout(MainActivity.getInstence());
                body.addView(cBody, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ));
                cBody.setOrientation(LinearLayout.HORIZONTAL);
            }
            if(cBody!=null) {
                LinearLayout.LayoutParams layout=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout.weight=1;
                if(i<cnum) {
                    IListSelect check=null;
                    if(addGroup!=null){
                        check = new ListCheck(MainActivity.getInstence());
                        AddedObject obj = addGroup.lists.get(i);
                        check.setData(obj.isSelected, obj.title);
                        cBody.addView((ListCheck)check, layout);
                    }else if(benifitGroup!=null){

                        check = new ListBtn(MainActivity.getInstence());
                        BenifitObject obj = benifitGroup.lists.get(i);
                        check.setData(obj.isSelected, obj.title);
                        cBody.addView((ListBtn)check, layout);

                    }
                    if(check!=null) {
                        check.setOnSelectListener(this);

                        listSelects.add(check);
                    }
                }else{
                    FrameLayout v=new FrameLayout(MainActivity.getInstence());
                    cBody.addView(v, layout);
                }
            }
        }


    }
    public  void onSelectedChange(View v,boolean isSelected){

        int find=-1;
        if(listSelects!=null){
            find=listSelects.indexOf(v);
            if(find!=-1){
                if(isMultiSelected==true){
                    setDataChange(find,isSelected);

                }else{
                    if(isSelected==false){
                        listSelects.get(find).setSelect(true);
                    }else{

                        for(int i=0;i<listSelects.size();++i){
                            if(i!=find){
                                listSelects.get(i).setSelect(false);

                            }

                        }

                        setDataChange(find,true);
                    }
                }
            }
        }




    }
    public  void onSelectedChange(View v,int selectedIdx){

        setDataChange(selectedIdx-1,true);
    }


    private  void setDataChange(int idx,boolean isSelected){


        if(addGroup!=null){

            if(isMultiSelected==true) {
                AddedObject add=addGroup.lists.get(idx);
                add.isSelected = isSelected;
            }else{
                for(int i=0;i<addGroup.lists.size();++i){
                    AddedObject add=addGroup.lists.get(i);
                    if(i==idx){
                        add.isSelected=true;

                    }else{
                        add.isSelected=false;

                    }

                }

            }
        }
        if(benifitGroup!=null){

            if(isMultiSelected==true) {
                BenifitObject benifit=benifitGroup.lists.get(idx);
                benifit.isSelected=isSelected;
            }else{
                for(int i=0;i<benifitGroup.lists.size();++i){
                    BenifitObject benifit=benifitGroup.lists.get(i);
                    if(i==idx){
                        benifit.isSelected=true;

                    }else{
                        benifit.isSelected=false;

                    }

                }

            }

        }

    }




 /////////////////////////////////////////









}
