package com.aeddang.clipmaker.pagecomplete;






import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import lib.core.PageObject;
import lib.core.ViewCore;
import lib.datamanager.DataManager;
import lib.datamanager.FileObject;
import lib.googleapi.GoogleDriveAPI;

import android.content.Intent;
import android.net.Uri;

import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;


import android.content.Context;


import android.widget.Button;

import android.widget.FrameLayout;
import android.widget.ImageButton;


import com.aeddang.clipmaker.Config;
import com.aeddang.clipmaker.R;

public class PopupShare extends ViewCore  implements  OnClickListener,DataManager.DataManagerDelegate{


    private FrameLayout body;

    private ImageButton backBtn;
    private Button kakaoBtn,driveBtn;
    private String filePath;
    private DataManager upLoader;


    public PopupShare(Context context, PageObject  pageInfo)
    {
        super(context, pageInfo);

        if(pageInfo.info!=null){
            filePath=(String)pageInfo.info.get("filePath");


        }else{
            filePath="";

        }

        View.inflate(context, R.layout.popup_share,this);



        body=(FrameLayout) findViewById(R.id._body);
        backBtn=(ImageButton) findViewById(R.id._backBtn);
        kakaoBtn=(Button) findViewById(R.id._kakaoBtn);
        driveBtn=(Button) findViewById(R.id._driveBtn);
        backBtn.setOnClickListener(this);
        kakaoBtn.setOnClickListener(this);
        driveBtn.setOnClickListener(this);

        upLoader=new DataManager("POST");
        upLoader.setOnDataDelegate(this);

    }


    protected void doMovedInit() {
        super.doMovedInit();



    }

    protected void doRemove() {

        super.doRemove();

    }


    public void onClick(View v) {


        if(v==backBtn){
            mainActivity.removePopup(this);
        }else if(v==kakaoBtn){

            shareKakao();
        }else if(v==driveBtn){
            upLoadGif();

        }

    }
    private void shareKakao(){
        if(filePath.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_no_share_data,null);
            return;
        }
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("image/gif");

        Log.i("", "savePath : " + filePath);

        waIntent.setPackage("com.kakao.talk");
        if (waIntent != null) {
            File file = new File(filePath,"");
            Uri img=Uri.fromFile(file);

            Log.i("","savePath img : "+img);
            waIntent.putExtra(Intent.EXTRA_STREAM, img);
            mainActivity.startActivity(Intent.createChooser(waIntent, "Share with"));
        } else {
            mainActivity.viewAlert("",R.string.msg_share_no_app,null);
        }

    }
    private void shareImage(String type){
        if(filePath.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_no_share_data,null);
            return;
        }
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType(type);

        Log.i("","savePath : "+filePath);

        waIntent.setPackage("com.kakao.talk");
        if (waIntent != null) {
            File file = new File(filePath,"");
            Uri img=Uri.fromFile(file);

            Log.i("","savePath img : "+img);
            waIntent.putExtra(Intent.EXTRA_STREAM, img);
            mainActivity.startActivity(Intent.createChooser(waIntent, "Share with"));
        } else {
            mainActivity.viewAlert("",R.string.msg_share_no_app,null);
        }

    }
    private void upLoadGif() {

        if(filePath.equals("")==true){

            return;
        }

        File file = new File(filePath,"");
        Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("test", "test");
        paramA.put("test1", "test1");
        paramA.put("test2", "test2");


        ArrayList<FileObject> files=new ArrayList<FileObject>();
        FileObject entity=new FileObject();
        entity.key="gif";
        entity.name="test";
        entity.file=file;
        files.add(entity);
        upLoader.loadData(Config.DATA_UPLOAD,null,files);



    }
    public void onDataCompleted(DataManager manager,String path,String result){

        Log.i("","upload complete : "+result);
    }
    public void onDataLoadErr(DataManager manager,String path){
        Log.i("","upload error");

    }

}
