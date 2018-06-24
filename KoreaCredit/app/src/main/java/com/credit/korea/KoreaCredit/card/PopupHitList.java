package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.card.book.ListBook;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PopupHitList extends ViewCore implements View.OnClickListener,CardInfo.CardSearchDelegate,MyCardInfo.MyCardDelegate
{


    private ArrayList<ListHitCard> lists;
    private ArrayList<CardObject> datas;
    private CustomBaseAdapter adapter;
    private TextView textTitle,textSubTitle;
    public ListView listView;




    public PopupHitList(Context context, PageObject pageInfo)
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
            title="인기카드";
        }
        listView=(ListView) findViewById(R.id._lists);
        textTitle=(TextView) findViewById(R.id._textTitle);
        textSubTitle=(TextView) findViewById(R.id._textSubTitle);
        lists=new ArrayList<ListHitCard>();

        int resID = this.getResources().getIdentifier( "transparent", "drawable", MainActivity.getInstence().getPackageName());
        listView.setSelector(resID);


        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textSubTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textTitle.setText(title);
        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);


        long now = System.currentTimeMillis();
        Date dat = new Date(now);
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyy.MM");
        String str="기간 : "+dateNow.format(dat)+".1~";

        textSubTitle.setText(str);

        if(datas!=null) {
            adapter.notifyDataSetChanged();
        }


    }
    protected void doMovedInit() {

        super.doMovedInit();
        MyCardInfo.getInstence().delegate=this;
        if(datas==null) {
            CardInfo.getInstence().delegate = this;
            CardInfo.getInstence().loadFavoritCards();
        }

    }

    public void onClick(View v) {



    }

    public void onModifyMyCards(){

        CardInfo.getInstence().loadFavoritCards();

    }

    public void onLoadMyCards(ArrayList<CardObject> myCards){}
    public void onLoadMyCounsels(ArrayList<MyCounselObject> myCounsels){}
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
        if(MyCardInfo.getInstence().delegate==this){
            MyCardInfo.getInstence().delegate=null;
        }
        removeLists();
    }

    private void removeLists()
    {

        if( lists!=null){
            for(int i=0;i< lists.size();++i){
                ListHitCard list=lists.get(i);
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


               ListHitCard list;
                CardObject data=getItem(position);


                if(convertview instanceof ListBook ){
                    list= (ListHitCard)convertview;
                }else{

                    list=new ListHitCard(MainActivity.getInstence());
                    lists.add(list);
                }
                list.setData(data);
                return list;







        }

    }

}

