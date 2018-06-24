package com.credit.korea.KoreaCredit.card.find;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.card.CardInfo;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.card.ListSearchCard;
import com.credit.korea.KoreaCredit.card.book.ListBook;
import com.credit.korea.KoreaCredit.card.recommend.GroupRecommend;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import java.util.ArrayList;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PopupCardFind extends ViewCore implements View.OnClickListener,CardInfo.CardSearchDelegate,GroupFind.GroupFindDelegate,MyCardInfo.MyCardDelegate
{


    private ArrayList<ListSearchCard> lists;
    private ArrayList<CardObject> datas;
    private CustomBaseAdapter adapter;

    public ListView listView;

    private GroupFind listHeader;
    private boolean isInit;
    private Map<String,String> finalParams;
    public PopupCardFind(Context context, PageObject pageInfo)
    {
        super(context, pageInfo);
        View.inflate(context, R.layout.popup_card_find,this);

        listView=(ListView) findViewById(R.id._lists);
        datas=new ArrayList<CardObject>();
        listHeader=new GroupFind(MainActivity.getInstence());
        listHeader.delegate=this;

        listView.addHeaderView(listHeader);
        lists=new ArrayList<ListSearchCard>();
        finalParams=null;
        int resID = this.getResources().getIdentifier( "transparent", "drawable", MainActivity.getInstence().getPackageName());
        listView.setSelector(resID);

        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);




        isInit=true;
        adapter.notifyDataSetChanged();


    }
    protected void doMovedInit() {

        super.doMovedInit();
        CardInfo.getInstence().delegate=this;
        MyCardInfo.getInstence().delegate=this;
        listHeader.initGroup();

    }
    public void onFindStart(GroupFind v,Map<String,String> sendParams){


        loadCards(sendParams);

    }
    public void onClick(View v) {



    }
    public void onModifyMyCards(){
        loadCards(finalParams);
    }

    public void onLoadMyCards(ArrayList<CardObject> myCards){

    }
    public void onLoadMyCounsels(ArrayList<MyCounselObject> myCounsels){

    }

    public void onLoadCards(ArrayList<CardObject> cards){

        Log.i("", "ONLOAD CARD : " + cards.size());
        datas=cards;

        adapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(1);
        if(datas.size()<1){
            Toast.makeText(mainActivity, R.string.msg_no_search_data, Toast.LENGTH_SHORT).show();


        }else{
            if(isInit==false){
                listView.smoothScrollToPosition(1);
            }
        }

        if(isInit==true){
            isInit=false;
        }

    }


    protected void doRemove() {

        super.doRemove();
        if(listHeader!=null){
            listHeader.removeGroup();
            listHeader=null;
        }

        if(MyCardInfo.getInstence().delegate==this){
            MyCardInfo.getInstence().delegate=null;
        }
        CardInfo.getInstence().unLoadFindCards();
        if(CardInfo.getInstence().delegate==this){

            CardInfo.getInstence().delegate=null;
        }
        removeLists();
    }

    private void removeLists()
    {

        if( lists!=null){
            for(int i=0;i< lists.size();++i){
                ListSearchCard list=lists.get(i);
                list.removeList();
            }
            lists=null;

        }

    }
    public void loadCards(Map<String,String> sendParams){
        finalParams=sendParams;
        CardInfo.getInstence().loadFindCards(sendParams);
    }



    private class CustomBaseAdapter extends BaseAdapter
    {



        public CustomBaseAdapter()
        {
            super();
        }
        public int getCount()
        {


            return datas.size();
        }

        public CardObject getItem(int position)
        {

             return datas.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {



                ListSearchCard list;
                CardObject data=getItem(position);


                if(convertview instanceof ListBook ){
                    list= (ListSearchCard)convertview;
                }else{

                    list=new ListSearchCard(MainActivity.getInstence());
                    lists.add(list);
                }
                list.setData(data,position);
                return list;




        }

    }

}

