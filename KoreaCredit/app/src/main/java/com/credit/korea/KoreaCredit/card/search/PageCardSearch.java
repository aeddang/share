package com.credit.korea.KoreaCredit.card.search;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.card.CardInfo;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.card.ListSearchCard;
import com.credit.korea.KoreaCredit.card.book.ListBook;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PageCardSearch extends ViewCore implements View.OnClickListener,CardInfo.CardSearchDelegate,AbsListView.OnScrollListener,MyCardInfo.MyCardDelegate,GroupSearch.GroupSearchDelegate
{


    private ArrayList<ListSearchCard> lists;
    private ArrayList<CardObject> datas;
    private CustomBaseAdapter adapter;


    public ListView listView;

    private GroupSearch listHeader;
    private boolean isInit;

    public PageCardSearch(Context context, PageObject pageInfo)
    {
        super(context, pageInfo);
        View.inflate(context, R.layout.page_card_search,this);

        listView=(ListView) findViewById(R.id._lists);

        lists=new ArrayList<ListSearchCard>();
        datas=new ArrayList<CardObject>();
        listHeader=new GroupSearch(MainActivity.getInstence());
        listHeader.delegate=this;
        listView.addHeaderView(listHeader);
        int resID = this.getResources().getIdentifier( "transparent", "drawable", MainActivity.getInstence().getPackageName());
        listView.setSelector(resID);

        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);

        listView.setOnScrollListener(this);





        isInit=true;
        adapter.notifyDataSetChanged();


    }
    protected void doMovedInit() {

        super.doMovedInit();
        CardInfo.getInstence().delegate=this;
        MyCardInfo.getInstence().delegate=this;

        listHeader.initGroup();
    }
    public void onSearchStart(GroupSearch v){

        CardInfo.getInstence().loadSearchCards();

    }
    public void onClick(View v) {



    }

    public void onScrollStateChanged(AbsListView view, int scrollState){

    }
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount){

        /*
        if(firstVisibleItem==0){
            if(btnCompare.getVisibility()==View.VISIBLE){
                btnCompare.setVisibility(View.GONE);
            }

        }else{
            if(btnCompare.getVisibility()==View.GONE){
                btnCompare.setVisibility(View.VISIBLE);
            }

        }*/

    }

    public void onLoadCards(ArrayList<CardObject> cards){

        listHeader.isActiveCheck();
        datas=cards;

        adapter.notifyDataSetChanged();
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

        CardInfo.getInstence().unLoadSearchCards();
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

    public void onModifyMyCards(){

        MyCardInfo.getInstence().loadMyCards(true);
        CardInfo.getInstence().loadSearchCards();
    }

    public void onLoadMyCards(ArrayList<CardObject> myCards){}
    public void onLoadMyCounsels(ArrayList<MyCounselObject> myCounsels){}


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

