package com.credit.korea.KoreaCredit.card.book;


import android.util.Log;

import com.credit.korea.KoreaCredit.Config;
import com.credit.korea.KoreaCredit.DataFactory;
import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.mypage.MyCounselObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;

public class BookInfo implements DataManager.JsonManagerDelegate
{

    public static final String MODIFY_TYPE_ADD="add";
    public static final String MODIFY_TYPE_REPLACE="edit";
    public static final String MODIFY_TYPE_DELETE="del";


    private ArrayList<BookObject> books;


    private static BookInfo instence;
    private DataManager jsonManager;


    public BookInfoDelegate delegate;
    public BookDescriptDelegate descDelegate;
    public BookModifyDelegate modifyDelegate;
    private BookObject currentBook;



    private String currentModifyType;

    interface BookInfoDelegate
    {

        void onLoadBookDatas(ArrayList<BookObject> books);
        void onModifyBookData();


    }
    interface BookDescriptDelegate
    {
        void onLoadBookDatas(ArrayList<BookObject> books);
        void onModifyBookData(boolean isInit);
        void onLoadBookData(BookObject book,ArrayList<BookUnit> lists);
        void onModifyError(boolean isInit);


    }

    interface BookModifyDelegate
    {
        void onModifyBookData();
        void onModifyError();

    }

    public static BookInfo getInstence(){

        if(instence==null){
            instence=new BookInfo();

        }

        return instence;


    }

    public BookInfo() {

        jsonManager=new DataManager("POST");
        jsonManager.setOnJsonDelegate(this);
    }

    public void resetInfo(){

        books = null;
        currentBook=null;

    }
    public ArrayList<BookObject> getBookDatas(){
        return books;
    }
    public void loadBookDatas(boolean isReset){

        if(isReset==true){
            books = null;
        }else{


        }
        if(books ==null) {
            MainActivity.getInstence().loadingStart(false);
            Map<String,String> sendParams=new HashMap<String,String>();
            sendParams.put("uid", MemberInfo.getInstence().getID());

            jsonManager.loadData(Config.API_LOAD_BOOK_CARD,sendParams);
        }
        if(delegate!=null && books !=null){
            delegate.onLoadBookDatas(books);
        }



    }
    public void onLoadBookDatas(JSONArray datas){


        books=new ArrayList<BookObject>();
        for(int i=0;i<datas.length();++i){
            try {
                BookObject data=new BookObject();
                data.setData(datas.getJSONObject(i));

                books.add(data);
            } catch (JSONException e) {

            }


        }


        if(delegate!=null){
            delegate.onLoadBookDatas(books);
        }
        if(descDelegate!=null && books !=null){
            descDelegate.onLoadBookDatas(books);
        }

    }

    public void loadBookData(BookObject obj,int page){


        currentBook=obj;
         MainActivity.getInstence().loadingStart(false);
         Map<String,String> sendParams=new HashMap<String,String>();
         sendParams.put("uid", MemberInfo.getInstence().getID());
         sendParams.put("page",String.valueOf(page+1));
        sendParams.put("book_idx",obj.seq);

        jsonManager.loadData(Config.API_LOAD_BOOK_DETAIL,sendParams);


    }
    public void onLoadBookData(JSONArray datas){

        ArrayList<BookUnit> lists=new ArrayList<BookUnit>();

        for(int i=0;i<datas.length();++i){
            try {
                BookUnit data=new BookUnit();
                data.setData(datas.getJSONObject(i));

                lists.add(data);
            } catch (JSONException e) {

            }


        }


        if(descDelegate!=null){
            descDelegate.onLoadBookData(currentBook,lists);
        }

    }
    public void modifyBookUnit(Map<String,String> sendParams,String type){

        currentModifyType=type;

        MainActivity.getInstence().loadingStart(false);
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("opt", currentModifyType);
        jsonManager.loadData(Config.API_MODIFY_BOOK_DETAIL,sendParams);



    }
    public void deleteBookUnit(BookUnit obj){

        currentModifyType=MODIFY_TYPE_DELETE;
        Map<String,String> sendParams=new HashMap<String,String>();
        sendParams.put("uid", MemberInfo.getInstence().getID());
        sendParams.put("opt", currentModifyType);
        sendParams.put("bookuse_idx",obj.seq);
        jsonManager.loadData(Config.API_MODIFY_BOOK_DETAIL,sendParams);


    }

    private void onModifyBookData(){

        boolean isInit=true;

        if(modifyDelegate!=null){

            modifyDelegate.onModifyBookData();
            isInit=false;
        }

        if(descDelegate!=null){
            descDelegate.onModifyBookData(isInit);

        }
        if(delegate!=null){
            delegate.onModifyBookData();

        }

    }



    public void onJsonCompleted(DataManager manager,String path,JSONObject result)
    {
        MainActivity.getInstence().loadingStop();
        JSONArray results = null;
        try {
            results = result.getJSONArray("datalist");
        } catch (JSONException e) {
            Log.i("", "error path :" + path);
            MainActivity.getInstence().viewAlert("", R.string.msg_data_load_err, null);
            return;

        }

        if(path.equals(Config.API_LOAD_BOOK_CARD)){
            onLoadBookDatas(results);


        }else if(path.equals(Config.API_LOAD_BOOK_DETAIL)){

            onLoadBookData(results);

        }else if(path.equals(Config.API_MODIFY_BOOK_DETAIL)){
            boolean isSuccess= DataFactory.getInstence().getSuccess(results);
            if(isSuccess==false){

                MainActivity.getInstence().viewAlert("", R.string.msg_data_modify_err, null);
            }else{
                if(currentModifyType==MODIFY_TYPE_DELETE){

                    MainActivity.getInstence().viewAlert("", R.string.msg_data_delete_complete, null);
                }else if(currentModifyType==MODIFY_TYPE_ADD){
                    MainActivity.getInstence().viewAlert("", R.string.msg_data_added_complete, null);
                }else{
                    MainActivity.getInstence().viewAlert("", R.string.msg_data_modify_complete, null);
                }


                onModifyBookData();
            }



        }




    }
    public void onJsonParseErr(DataManager manager,String path) {
        MainActivity.getInstence().viewAlert("",R.string.msg_data_parse_err,null);
    }
    public void onJsonLoadErr(DataManager manager,String path) {
        MainActivity.getInstence().loadingStop();
        MainActivity.getInstence().viewAlert("", R.string.msg_network_err,null);

    }



}






