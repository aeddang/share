package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.card.CardInfo;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.card.ListSearchCard;
import com.credit.korea.KoreaCredit.card.book.ListBook;
import com.credit.korea.KoreaCredit.card.thiscard.GroupThis;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import java.util.ArrayList;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PopupCardList extends ViewCore implements View.OnClickListener,CardInfo.CardSearchDelegate
{


    private ArrayList<ListSearchCard> lists;
    private ArrayList<CardObject> datas;
    private CustomBaseAdapter adapter;
    private TextView textTitle;
    public ListView listView;



    private Map<String,String> finalParams;
    public PopupCardList(Context context, PageObject pageInfo)
    {
        super(context, pageInfo);
        View.inflate(context, R.layout.popup_list,this);

        String title="";
        if(pageInfo.info!=null){
            datas=(ArrayList<CardObject>)pageInfo.info.get("datas");
            title=(String)pageInfo.info.get("title");
        }else{

        }
        if(title==null || title.equals("")==true){
            title="검색결과";
        }
        listView=(ListView) findViewById(R.id._lists);
        textTitle=(TextView) findViewById(R.id._textTitle);
        lists=new ArrayList<ListSearchCard>();
        finalParams=null;
        int resID = this.getResources().getIdentifier( "transparent", "drawable", MainActivity.getInstence().getPackageName());
        listView.setSelector(resID);


        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textTitle.setText(title);
        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);


        if(datas!=null) {
            adapter.notifyDataSetChanged();
        }


    }
    protected void doMovedInit() {

        super.doMovedInit();

        if(datas==null) {
            CardInfo.getInstence().delegate = this;
            CardInfo.getInstence().loadFavoritCards();
        }

    }

    public void onClick(View v) {



    }


    public void onLoadCards(ArrayList<CardObject> cards){


        datas=cards;
        if(datas.size()<1){
            mainActivity.viewAlert("",R.string.msg_no_search_data,null);
        }
        adapter.notifyDataSetChanged();

    }


    protected void doRemove() {

        super.doRemove();

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

