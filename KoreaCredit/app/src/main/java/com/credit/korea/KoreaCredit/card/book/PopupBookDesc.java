package com.credit.korea.KoreaCredit.card.book;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lib.CustomTimer;
import lib.core.PageObject;
import lib.core.ViewCore;

import static com.credit.korea.KoreaCredit.MainActivity.*;


public class PopupBookDesc extends ViewCore implements BookInfo.BookDescriptDelegate,View.OnClickListener,CustomTimer.TimerDelegate
{
    private static final float spd=0.1f;
    private static final float maxPoint=100.f;
    private TextView textType,textPoint,textUsed,textConsume,textTitle;
    private FrameLayout consumeBar,usedBar;
    private LinearLayout barBox;

    private CustomTimer timer;
    private FrameLayout cBar;
    private float cPct,tPct;

    private ArrayList<ListBookDesc> lists;
    private BookObject data;
    private ArrayList<BookUnit> datas;
    private CustomBaseAdapter adapter;
    private TextView period;
    private ImageButton btnMore;
    private Button btnAdd;
    private int pageIdx=0;

    public ListView listView;

    public PopupBookDesc(Context context, PageObject pageInfo)
    {
        super(context, pageInfo);
        View.inflate(context, R.layout.popup_card_book_description,this);

        if(pageInfo.info!=null){
            data=(BookObject)pageInfo.info.get("data");

        }else{
            data=new BookObject();

        }
        textTitle=(TextView) findViewById(R.id._textTitle);
        textType=(TextView) findViewById(R.id._textType);

        textPoint=(TextView) findViewById(R.id._textPoint);
        textUsed=(TextView) findViewById(R.id._textUsed);
        textConsume=(TextView) findViewById(R.id._textConsume);
        barBox=(LinearLayout) findViewById(R.id._barBox);
        consumeBar=(FrameLayout) findViewById(R.id._consumeBar);
        usedBar=(FrameLayout) findViewById(R.id._usedBar);

        btnAdd=(Button) findViewById(R.id._btnAdd);


        textTitle.setTypeface(FontFactory.getInstence().FONT_KR);
        textType.setTypeface(FontFactory.getInstence().FONT_KR);

        textPoint.setTypeface(FontFactory.getInstence().FONT_KR);
        textUsed.setTypeface(FontFactory.getInstence().FONT_KR);
        textConsume.setTypeface(FontFactory.getInstence().FONT_KR);


        timer=new CustomTimer(10,-1,this);


        listView=(ListView) findViewById(R.id._lists);
        //btnMore=(ImageButton) findViewById(R.id._btnMore);
        period=(TextView) findViewById(R.id._period);

        period.setTypeface(FontFactory.getInstence().FONT_KR);

        lists=new ArrayList<ListBookDesc>();

        int resID = this.getResources().getIdentifier( "transparent", "drawable", getInstence().getPackageName());
        listView.setSelector(resID);
        datas=new ArrayList<BookUnit>();
        adapter=new CustomBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(null);
        BookInfo.getInstence().descDelegate=this;

        btnMore = new ImageButton(getInstence());
        btnMore.setImageResource(R.drawable.btn_more_txt);
        btnMore.setBackgroundResource(R.drawable.btn_bg2);


        ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,(int)Math.floor(30.f * MainActivity.getInstence().dpi));
        int pd= (int)Math.floor(MainActivity.getInstence().dpi*10);

        btnMore.setPadding(pd,pd,pd,pd);
        btnMore.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        btnMore.setLayoutParams(layout);

        textTitle.setText("카드북 " + data.title + "전체 이용실적");
        textType.setText(data.title);


        long now = System.currentTimeMillis();
        Date dat = new Date(now);
        SimpleDateFormat dateYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateMM = new SimpleDateFormat("MM");
        String str="기간 : "+dateYY.format(dat)+". "+Integer.valueOf(dateMM.format(dat))+". 1~\n(매월 기준으로 실적 집게함)";


        listView.addFooterView(btnMore);

        period.setText(str);

        btnMore.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        loadDatas();


    }
    protected void doMovedInit() {
        super.doMovedInit();




    }
    public void onClick(View v) {

        if(v== btnMore){
            loadDatas();
        }else if(v== btnAdd){
            PageObject pInfo=new PageObject(Config.POPUP_CARD_BOOK_ADDED);
            Map<String,Object> pinfo=new HashMap<String,Object>();
            pinfo.put("data", null);

            BookUnit parentData=new BookUnit();
            //parentData.seq=data.seq;
            parentData.groupID=data.seq;
            pinfo.put("parentData", parentData);

            pInfo.dr=1;
            pInfo.info=pinfo;
            getInstence().addPopup(pInfo);
        }

    }
    private void loadDatas(){

        BookInfo.getInstence().loadBookData(data,pageIdx);
    }


    protected void doRemove() {

        super.doRemove();
        if(BookInfo.getInstence().delegate==this){

            BookInfo.getInstence().delegate=null;
        }
        removeLists();
    }

    private void removeLists(){

        if(timer!=null){
            timer.removeTimer();
            timer=null;
        }
        if( lists!=null){
            for(int i=0;i< lists.size();++i){
                ListBookDesc list=lists.get(i);
                list.removeList();
            }
            lists=null;

        }

    }

    public void setData()
    {

        Log.i("","setdata : "+data.usedPoint);

        textPoint.setText(String.valueOf((int)Math.round(data.adjustPoint))+"%");
        textConsume.setText(String.valueOf((int)Math.round(data.consumePoint)));
        textUsed.setText(String.valueOf((int)Math.round(data.usedPoint)));

        setBar(0,consumeBar);
        setBar(0,usedBar);
        consumeBar.setBackgroundColor(Color.parseColor("#cccccc"));

        if(data.usedPoint>=data.consumePoint){
            tPct = 1.0f;
            usedBar.setBackgroundColor(Color.parseColor("#22b4ed"));

        }else{

            usedBar.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        cPct=0;


        tPct=(float)data.consumePoint/maxPoint;

        if(tPct>=1.0f){
            tPct = 1.0f;

        }else{


        }
        cBar=consumeBar;
        if(timer!=null) {
            timer.resetTimer();
            timer.timerStart();
        }

    }
    public void onLoadBookDatas(ArrayList<BookObject> books){
        Log.i("","onLoadBookDatas");
        for(int i=0;i<books.size();++i){

            Log.i("","books.get(i).seq : "+books.get(i).seq+" "+data.seq);
            if(data.seq.equals(books.get(i).seq)==true ){

                Log.i("","onLoadBookDatas update data");
                data=books.get(i);
                break;
            }

        }
        pageIdx=0;
        datas=new ArrayList<BookUnit>();
        loadDatas();
    }

    public void onModifyBookData(boolean isInit){

        Log.i("","onModifyBookData");



    }
    public void onLoadBookData(BookObject book,ArrayList<BookUnit> lists){



        pageIdx++;
        data=book;

        setData();
        datas.addAll(lists);

        adapter.notifyDataSetChanged();

    }
    public void onModifyError(boolean isInit){

        if(isInit==true) {
            getInstence().viewAlert("", R.string.msg_data_modify_err, null);
        }
    }
    public void onLoadError(){

        getInstence().viewAlert("", R.string.msg_data_load_err,null);
    }

    private void setBar(float pct,FrameLayout bar)
    {
       // Log.i("", "pct :" + pct);

        int w= barBox.getWidth()-textConsume.getWidth();
       // Log.i("","barBox w :"+barBox.getWidth()+" W:"+w);

        w = (int)Math.round((float)w * pct);
        LinearLayout.LayoutParams layout= (LinearLayout.LayoutParams)bar.getLayoutParams();

        layout.width=w;
        bar.setLayoutParams(layout);

    }


    public void onTime(CustomTimer timer){

        cPct+=spd;
        if(cPct>tPct){
            cPct=tPct;
            if(cBar==consumeBar)
            {
                setBar(cPct,cBar);
                cPct=0;
                tPct=(float)data.usedPoint/maxPoint;

                if(tPct>=1.0f){
                    tPct = 1.0f;

                }else{


                }
                cBar=usedBar;
            }else{
                setBar(cPct,cBar);
                timer.timerStop();
            }


        }else{
            setBar(cPct,cBar);

        }


    }
    public void onComplete(CustomTimer timer){

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

        public BookUnit getItem(int position)
        {
            return datas.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {
            ListBookDesc list;
            BookUnit data=getItem(position);


                if(convertview instanceof ListBook ){
                    list= (ListBookDesc)convertview;
                }else{

                    list=new ListBookDesc(getInstence());
                    lists.add(list);
                }




            list.setData(data);


            return list;
        }

    }

}

