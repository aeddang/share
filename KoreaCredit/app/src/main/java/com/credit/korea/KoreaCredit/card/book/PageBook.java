package com.credit.korea.KoreaCredit.card.book;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.listresult.PopupListResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.core.ViewCore;


public class PageBook extends ViewCore implements BookInfo.BookInfoDelegate,View.OnClickListener
{


    private ArrayList<ListBook> lists;
    private ArrayList<BookObject> datas;
    private CustomBaseAdapter adapter;
    private TextView period;
    private Button btnAdd;
    public ListView listView;

    public PageBook(Context context, PageObject pageInfo)
    {
        super(context, pageInfo);
        View.inflate(context, R.layout.page_card_book,this);


        listView=(ListView) findViewById(R.id._lists);
        btnAdd=(Button) findViewById(R.id._btnAdd);
        period=(TextView) findViewById(R.id._period);

        period.setTypeface(FontFactory.getInstence().FONT_KR);

        lists=new ArrayList<ListBook>();

        int resID = this.getResources().getIdentifier( "transparent", "drawable", MainActivity.getInstence().getPackageName());
        listView.setSelector(resID);

        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);



        resetOnListener();


        long now = System.currentTimeMillis();
        Date dat = new Date(now);
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateMM = new SimpleDateFormat("MM");

        String str="기간 : "+dateYY.format(dat)+". "+Integer.valueOf(dateMM.format(dat))+". 1~";

        period.setText(str);

        btnAdd.setOnClickListener(this);

        datas=BookInfo.getInstence().getBookDatas();
        if(datas!=null){

            onLoadBookDatas(datas);
        }


    }
    protected void doMovedInit() {

        super.doMovedInit();
        BookInfo.getInstence().delegate=this;
        if(datas==null){
            loadDatas(false);
        }

    }
    public void onClick(View v) {

        if(v== btnAdd){
            PageObject pInfo=new PageObject(Config.POPUP_CARD_BOOK_ADDED);
            Map<String,Object> pinfo=new HashMap<String,Object>();
            pinfo.put("data", null);
            pInfo.dr=1;
            pInfo.info=pinfo;
            MainActivity.getInstence().addPopup(pInfo);
        }

    }
    protected void doResetOnListener() {
        BookInfo.getInstence().delegate=this;
    }
    private void loadDatas(boolean isReset){

        datas=new ArrayList<BookObject>();
        BookInfo.getInstence().loadBookDatas(isReset);
    }
    protected void doRemove() {

        super.doRemove();
        if(BookInfo.getInstence().delegate==this){

            BookInfo.getInstence().delegate=null;
        }
        removeLists();
    }

    private void removeLists(){

        if( lists!=null){
            for(int i=0;i< lists.size();++i){
                ListBook list=lists.get(i);
                list.removeList();
            }
            lists=null;

        }

    }
    public void onModifyBookData(){

        loadDatas(true);

    }
    public void onLoadBookDatas(ArrayList<BookObject> books){
        MainActivity.getInstence().loadingStop();
        datas = books;
        adapter.notifyDataSetChanged();

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

        public BookObject getItem(int position)
        {
            return datas.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {
            ListBook list;
            BookObject data=getItem(position);


                if(convertview instanceof ListBook ){
                    list= (ListBook)convertview;
                }else{

                    list=new ListBook(MainActivity.getInstence());
                    lists.add(list);
                }




            list.setData(data);


            return list;
        }

    }

}

