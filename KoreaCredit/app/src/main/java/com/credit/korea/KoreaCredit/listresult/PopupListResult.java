package com.credit.korea.KoreaCredit.listresult;


import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.ListView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.ArrayList;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PopupListResult extends ViewCore implements AdapterView.OnItemClickListener
{



    private ArrayList<String> datas;
    private CustomBaseAdapter adapter;
    private TextView textTitle;
    public ListView listView;

    public PopupListResultDelegate delegate;

    public interface PopupListResultDelegate
    {

        void onSelectData(int idx);

    }

    public PopupListResult(Context context, PageObject pageInfo)
    {
        super(context, pageInfo);
        View.inflate(context, R.layout.popup_list,this);
        String title="";
        if(pageInfo.info!=null){
            datas=(ArrayList<String>)pageInfo.info.get("datas");
            title=(String)pageInfo.info.get("title");

        }else{
            datas=new ArrayList<String>();


        }
        if(title==null || title.equals("")==true){
            title="검색결과";
        }

        listView=(ListView) findViewById(R.id._lists);
        textTitle=(TextView) findViewById(R.id._textTitle);
        int resID = this.getResources().getIdentifier( "transparent", "drawable", MainActivity.getInstence().getPackageName());
        listView.setSelector(resID);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textTitle.setText(title);
        adapter=new CustomBaseAdapter();
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        adapter.notifyDataSetChanged();

    }
    protected void doMovedInit() {
        super.doMovedInit();




    }



    protected void doRemove() {

        delegate=null;

        super.doRemove();

    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if( delegate!=null){

            delegate.onSelectData(position);
        }

        mainActivity.removePopup(this);

    }


    private class CustomBaseAdapter extends BaseAdapter
    {



        public CustomBaseAdapter()
        {
            super();
        }
        public int getCount()
        {
            if(datas==null){
                return 0;
            }

            return datas.size();
        }

        public String getItem(int position)
        {
            return datas.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {
            ListResult list;
            String data=getItem(position);


                if(convertview instanceof ListResult ){
                    list= (ListResult)convertview;
                }else{

                    list=new ListResult(MainActivity.getInstence());

                }




            list.setData(data);


            return list;
        }

    }

}

