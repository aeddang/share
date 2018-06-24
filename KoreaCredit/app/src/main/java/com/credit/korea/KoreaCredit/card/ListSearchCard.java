package com.credit.korea.KoreaCredit.card;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.FontFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import com.credit.korea.KoreaCredit.card.book.ListBook;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.ViewUtil;
import lib.core.PageObject;
import lib.imagemanager.ImageLoader;
import lib.view.HorizontalListView;


public class ListSearchCard extends FrameLayout implements View.OnClickListener,ImageLoader.ImageLoaderDelegate{

    private TextView textTitle,textIndex,textAdd;

    private ArrayList<TextView> texts;
    private ArrayList<LinearLayout> boxs;

    private Button btn0,btn1,btnCheck,btnSms,btnCounsel;
    private ImageView imageCard,imgBg,imgLinkTitle,imgIcon;
    private ImageLoader loader;
    private CardObject info;

    private LinearLayout addStack,boxAdd,boxBtn;
    private HorizontalListView listBox;
    private ArrayList<ListLinkCard> lists;

    private CustomBaseAdapter adapter;
    public ListSearchCard(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_search_card,this);
        textTitle=(TextView) findViewById(R.id._textTitle);

        textIndex=(TextView) findViewById(R.id._textIndex);

        textAdd=(TextView) findViewById(R.id._textAdd);

        boxAdd=(LinearLayout) findViewById(R.id._boxAdd);
        boxBtn=(LinearLayout) findViewById(R.id._boxBtn);

        imageCard=(ImageView) findViewById(R.id._imageCard);
        imgIcon=(ImageView) findViewById(R.id._imgIcon);
        imgBg=(ImageView) findViewById(R.id._imgBg);
        imgLinkTitle=(ImageView) findViewById(R.id._imgLinkTitle);
        listBox=(HorizontalListView) findViewById(R.id._listBox);
        btn0=(Button) findViewById(R.id._btn0);
        btn1=(Button) findViewById(R.id._btn1);

        btnCheck=(Button) findViewById(R.id._btnCheck);
        btnSms=(Button) findViewById(R.id._btnSms);
        btnCounsel=(Button) findViewById(R.id._btnCounsel);

        lists=new ArrayList<ListLinkCard>();
        addStack=(LinearLayout) findViewById(R.id._addStack);

        texts=new ArrayList<TextView>();
        boxs=new ArrayList<LinearLayout>();
        for(int i=0;i<5;++i){
            int tid = getResources().getIdentifier("_text" + i, "id", MainActivity.getInstence().getPackageName());
            TextView text= (TextView)findViewById(tid);
            text.setTypeface(FontFactory.getInstence().FONT_KR);
            texts.add(text);

            int bid = getResources().getIdentifier("_box" + i, "id", MainActivity.getInstence().getPackageName());
            LinearLayout box= (LinearLayout)findViewById(bid);
            box.setVisibility(View.GONE);
            boxs.add(box);



        }



        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);

        btnCheck.setOnClickListener(this);
        btnSms.setOnClickListener(this);
        btnCounsel.setOnClickListener(this);

        textTitle.setOnClickListener(this);
        imageCard.setOnClickListener(this);

        btn0.setTypeface(FontFactory.getInstence().FONT_KR);
        btn1.setTypeface(FontFactory.getInstence().FONT_KR);

        textTitle.setTypeface(FontFactory.getInstence().FONT_KR_B);

        textIndex.setTypeface(FontFactory.getInstence().FONT_ENG);
        textAdd.setTypeface(FontFactory.getInstence().FONT_KR);

        imgLinkTitle.setVisibility(View.GONE);
        listBox.setVisibility(View.GONE);

        addStack.setVisibility(View.GONE);

        btnCheck.setVisibility(View.GONE);
        boxAdd.setVisibility(View.GONE);
        boxBtn.setVisibility(View.GONE);
        imgBg.setSelected(false);

    }

    public void setData(CardObject _info,int idx)
    {
        info=_info;
        if(idx==-1){
            textIndex.setVisibility(View.GONE);

        }else{
            textIndex.setText(String.valueOf(idx+1));
            textIndex.setVisibility(View.VISIBLE);

        }


        setIcon();
        textTitle.setText(info.title);

        if(info.cardType==CardObject.TYPE_SEARCH_CARD){
            setText(0,info.name);
            setText(1, info.kind);
            setText(2, info.pricePerYear);
            setText(3, Html.fromHtml("연간혜택금액예상 <font color='#00bff3'>"+info.getBeneGetTotalView()+"</font>"));

            setOpen(true);
            setBtnState();

        }else if(info.cardType==CardObject.TYPE_COMPARE_CARD){
            setText(0, info.name);
            setText(1, info.kind);
            setText(2, info.pricePerYear);

            setText(3, Html.fromHtml("연간혜택금액예상 <font color='#00bff3'>" + info.getBeneGetTotalView() + "</font>"));
            setText(4, Html.fromHtml("연간이용금액추천 <font color='#00bff3'>"+info.getBeneTotalView()+"</font>"));

            setAddStack(info.benefits);
            setBtnState();
        }
         else if(info.cardType==CardObject.TYPE_RECOMMEND_CARD){
            setText(0, info.name);
            setText(1, info.kind);
            setText(2, info.pricePerYear);
            setText(3, Html.fromHtml("연간혜택금액예상 <font color='#00bff3'>" + info.getBeneGetTotalView() + "</font>"));
            setText(4, Html.fromHtml("연간이용금액추천 <font color='#00bff3'>"+info.getBeneTotalView()+"</font>"));
            setAddStack(info.benefits);


        }
        else if(info.cardType==CardObject.TYPE_THIS_CARD){
            if(info.beforeuse_benef.equals("")==true){
                setText(0, Html.fromHtml(info.type_benef + " <font color='#00bff3'>" + info.amt_benef + "</font>"));
            }else{
                setText(0, Html.fromHtml(info.type_benef + " <font color='#00bff3'>" + info.amt_benef + "</font>(" + info.beforeuse_benef + ")"));
            }
            setText(1, info.overallBenef);
            setAddStack(info.benefits);
            //setBtnState();
        }
        else if(info.cardType==CardObject.TYPE_FIND_CARD){

            setText(0, info.name);
            setText(1, info.kind);
            setText(2, info.pricePerYear);
            setBtnState();
        }

        loadImg();

    }
    private void setIcon() {
        if(info.cardType==CardObject.TYPE_COMPARE_CARD){
            imgIcon.setImageResource(R.drawable.card_search_icon1);
        }else{

            if(info.isMine==true){
                imgIcon.setImageResource(R.drawable.card_search_icon2);

            }else if(info.isInterest==true){

                imgIcon.setImageResource(R.drawable.card_search_icon3);
            }else{
                imgIcon.setImageResource(R.drawable.card_search_icon0);
            }

        }



    }

    private void setText(int idx,CharSequence value) {
        if(value.equals("")==true){
            return;
        }

        boxs.get(idx).setVisibility(View.VISIBLE);

        texts.get(idx).setText(value);


    }

    private void setRecommendPrice(){

        ArrayList<CardOption> options=new ArrayList<CardOption>();
        CardOption opt=new CardOption();
        opt.title="이용금액추천";
        opt.subTitle=info.getBeneTotalView();
        options.add(opt);
        setAddStack(options);
    }

    public void loadImg()
    {
        removeLoader();
        loader=new ImageLoader(this);
        loader.loadImg(info.imgPath);
    }
    public void onImageLoadCompleted(ImageLoader loader,Bitmap image){

        imageCard.setImageBitmap(image);


    }
    public void onClick(View v){

        if(info.cardType==CardObject.TYPE_SEARCH_CARD) {
            if(v==btn0){
                if(btn0.isSelected()==false){
                    if(info.isMine==false){

                        MyCardInfo.getInstence().requestCard(info,MyCardInfo.REQUEST_TYPE_INTEREST_CARD);
                    }else{

                        MainActivity.getInstence().viewAlert("",R.string.msg_card_request_interest_anable,null);
                    }


                }

            }else if(v==btn1){
                if(btn1.isSelected()==false){

                    PageObject pInfo=new PageObject(Config.POPUP_CARD_COMPARE);
                    Map<String,Object> pinfo=new HashMap<String,Object>();
                    pinfo.put("cardSeq", info.seq);
                    pInfo.dr=1;
                    pInfo.info=pinfo;
                    MainActivity.getInstence().addPopup(pInfo);

                }

            }
        }else{
            if(v==btn1){
                if(btn1.isSelected()==false){
                    if(info.isMine==false){

                        MyCardInfo.getInstence().requestCard(info,MyCardInfo.REQUEST_TYPE_INTEREST_CARD);
                    }else{

                        MainActivity.getInstence().viewAlert("",R.string.msg_card_request_interest_fail,null);
                    }


                }

            }else if(v==btn0){
                if(btn0.isSelected()==false){

                    PageObject pInfo=new PageObject(Config.POPUP_CARD_ADD);
                    pInfo.info=new HashMap<String,Object>();
                    pInfo.info.put("title","카드등록");
                    pInfo.info.put("cardObj",info);
                    pInfo.dr=1;
                    MainActivity.getInstence().addPopup(pInfo);

                }

            }


        }


        if(v==btnCheck){
            if(info.isSelected==true){
                setSelect(false);
            }else{

                setSelect(true);
            }

        }else if(v==btnSms) {
            MainActivity mc=(MainActivity)MainActivity.getInstence();
            mc.callSms(info);

        }
        else if(v==btnCounsel) {

            MainActivity mc=(MainActivity)MainActivity.getInstence();
            mc.callCounsel(info);
        }else if(v==textTitle || v==imageCard){

            PageObject pInfo=new PageObject(Config.POPUP_CARD_DETAIL);
            pInfo.info=new HashMap<String,Object>();

            pInfo.info.put("cardObj",info);
            pInfo.dr=1;
            MainActivity.getInstence().addPopup(pInfo);

        }


    }
    private void setAddStack(ArrayList<CardOption> options) {
        if(options.size()<1){
            return;
        }
        addStack.removeAllViews();
        addStack.setVisibility(View.VISIBLE);

        for(int i=0;i<options.size();++i){
            ListCardAddBenit list=new ListCardAddBenit(MainActivity.getInstence());
            list.setData(options.get(i));
            addStack.addView(list);

        }



    }
    private void setAddText() {

        if(info.subInfos.size()<1){
            return;
        }

        boxAdd.setVisibility(View.VISIBLE);
        String str="";
        for(int i=0;i<info.subInfos.size();++i){
            if(i==0){

                str=info.subInfos.get(i).title;
            }else{
                str+=(", "+info.subInfos.get(i).title);

            }


        }
        textAdd.setText("부가기능\n"+str);


    }
    private void setBtnState() {

        boxBtn.setVisibility(View.VISIBLE);

        if(info.cardType==CardObject.TYPE_SEARCH_CARD) {
            if(info.isMine==true){
                btn1.setSelected(true);
                btn1.setText("보유카드");
                btn1.setTextColor(Color.parseColor("#ffffff"));

            }else{
                btn1.setSelected(false);
                btn1.setText("보유카드와 비교");
                btn1.setTextColor(Color.parseColor("#666666"));

            }
            if(info.isInterest==true){
                btn0.setSelected(true);
                btn0.setText("관심카드");
                btn0.setTextColor(Color.parseColor("#ffffff"));

            }else{
                if(info.isMine==true){
                    btn0.setText("관심카드");

                }else{

                    btn0.setText("관심카드등록");

                }
                btn0.setSelected(false);
                btn0.setTextColor(Color.parseColor("#666666"));

            }
        }else{

            if(info.isMine==true){
                btn0.setSelected(true);
                btn0.setText("보유카드");
                btn0.setTextColor(Color.parseColor("#ffffff"));

            }else{
                btn0.setSelected(false);
                btn0.setText("보유카드등록");
                btn0.setTextColor(Color.parseColor("#666666"));

            }
            if(info.isInterest==true){
                btn1.setSelected(true);
                btn1.setText("관심카드");
                btn1.setTextColor(Color.parseColor("#ffffff"));

            }else{
                if(info.isMine==true){
                    btn1.setText("관심카드");

                }else{

                    btn1.setText("관심카드등록");

                }
                btn1.setSelected(false);

                btn1.setTextColor(Color.parseColor("#666666"));

            }
        }

    }

    private void setOpen(boolean ac) {


        info.isOpen=ac;


        if(info.isOpen==true) {
            if(info.linkCards.size()<1){
                return;
            }
            imgLinkTitle.setVisibility(View.VISIBLE);
            listBox.setVisibility(View.VISIBLE);
            adapter=new CustomBaseAdapter();
            listBox.setAdapter(adapter);
            listBox.setDivider(null);
            adapter.notifyDataSetChanged();

        }else{

            imgLinkTitle.setVisibility(View.GONE);
            listBox.setVisibility(View.GONE);


        }
    }
    private void setSelect(boolean ac) {
        info.isSelected=ac;
        imgBg.setSelected(info.isSelected);
        btnCheck.setSelected(info.isSelected);
    }
    private void removeLoader()
    {
        if(loader!=null){
            imageCard.setImageResource(R.drawable.transparent);
            loader.removeLoader();
            loader=null;
        }
    }

    public void  removeList()
    {
        removeLoader();
        removeLinkList();
    }

    public void  removeLinkList()
    {
        if(lists!=null){
            for(int i=0;i<lists.size();++i){
                lists.get(i).removeList();
            }

            lists.clear();
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

            return info.linkCards.size();
        }

        public CardUnit getItem(int position)
        {

            return info.linkCards.get(position);
        }
        public long getItemId(int position)
        {
            return position;
        }


        public View getView(int position, View convertview, ViewGroup parent)
        {



            ListLinkCard list;
            CardUnit data=getItem(position);


            if(convertview instanceof ListBook){
                list= (ListLinkCard )convertview;
            }else{

                list=new ListLinkCard (MainActivity.getInstence());
                lists.add(list);
            }
            list.setData(data);
            return list;



        }

    }

}
