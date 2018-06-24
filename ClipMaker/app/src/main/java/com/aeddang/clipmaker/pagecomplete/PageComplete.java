package com.aeddang.clipmaker.pagecomplete;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aeddang.clipmaker.Config;

import com.aeddang.clipmaker.MainActivity;
import com.aeddang.clipmaker.R;
import com.aeddang.clipmaker.SetupInfo;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.Aes;
import lib.CommonUtil;
import lib.CustomTimer;
import lib.GraphicUtil;

import lib.core.PageObject;
import lib.core.ViewCore;
import lib.gifencoder.AnimatedGifEncoder;
import lib.datamanager.DataManager;

import lib.datamanager.FileObject;

import lib.gifencoder.GifMaker;
import lib.view.SlideBox;
import lib.view.SlideBox.SlideBoxDelegate;




public class PageComplete extends ViewCore implements
        View.OnClickListener,SlideBoxDelegate, CustomTimer.TimerDelegate,
        DataManager.JsonManagerDelegate,SeekBar.OnSeekBarChangeListener,GifMaker.GifMakerDelegate
        {


    private final int fpsProgress=5;

    private final String SHERE_TYPE_ALL="all";
    private final String SHERE_TYPE_NONE="none";
    private final String SHERE_TYPE_KAKAO="com.kakao.talk";
    private final String SHERE_TYPE_TELE="org.telegram.messenger";
    private final String SHERE_TYPE_FB_MSG="com.facebook.orca";
    private final String SHERE_TYPE_MSG="com.android.mms";

    private final String SHERE_TYPE_UPLOAD="upLoad";


    private int pageIdx=-1;
    private int pageNum=0;
    private SlideBox slideBox;

    private FrameLayout viewer;
    private ImageButton btnUpload,retryBtn,gifBtn;

    private Button btnMsg,btnKakao,btnTele,btnFBMsg;

    private SeekBar progress,fpsBar;
    private TextView fpsTxt;
    private ImageView playImg;
    private boolean isPlaying,gifChanged;
    private CustomTimer playTimer;
    private ArrayList<Bitmap> records;

    private int frameRate;
    private String finalSendType;

    private String savePath,gifPath;
    private DataManager upLoader;
    private GifMaker gifMaker;

   // private  byte[] gifbytes;

    public PageComplete(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_complete,this);
        slideBox=(SlideBox) findViewById(R.id._slideBox);
        viewer=(FrameLayout) findViewById(R.id._viewer);
        playImg=(ImageView) findViewById(R.id._playImg);
        btnUpload=(ImageButton) findViewById(R.id._btnUpload);
        gifBtn=(ImageButton) findViewById(R.id._gifBtn);
        retryBtn=(ImageButton) findViewById(R.id._retryBtn);
        btnFBMsg=(Button) findViewById(R.id._btnFBMsg);
        btnMsg=(Button) findViewById(R.id._btnMsg);
        btnKakao=(Button) findViewById(R.id._btnKakao);
        btnTele=(Button) findViewById(R.id._btnTele);


        progress=(SeekBar) findViewById(R.id._progress);
        fpsBar=(SeekBar) findViewById(R.id._fpsBar);
        fpsTxt=(TextView) findViewById(R.id._fpsTxt);
        pageIdx=0;
        slideBox.initBox(this);
        isPlaying=false;
        gifChanged=true;
        if(pageInfo.info!=null){
            records=(ArrayList<Bitmap>)pageInfo.info.get("records");
            Log.i("","pageInfo.info :"+records.size());
        }else{
            records=new ArrayList<Bitmap>();
        }
        Log.i("","records :"+records.size());

        savePath="";
        gifPath="";

        pageNum=records.size();
        playImg.setSelected(false);



        gifBtn.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        retryBtn.setOnClickListener(this);

        btnMsg.setOnClickListener(this);
        btnKakao.setOnClickListener(this);
        btnTele.setOnClickListener(this);
        btnFBMsg.setOnClickListener(this);

        progress.setOnSeekBarChangeListener(this);
        fpsBar.setOnSeekBarChangeListener(this);

        upLoader=new DataManager("POST");
        upLoader.setOnJsonDelegate(this);



        frameRate=SetupInfo.getInstence().getFR();
        fpsBar.setMax(fpsProgress);
        fpsBar.setProgress(getFpsProgress(frameRate));
        setFpsTxt();
        playTimer=new CustomTimer(SetupInfo.getInstence().getScanTime(frameRate),-1,this);

    }
    protected void doMovedInit() {
        super.doMovedInit();
        progress.setMax(pageNum-1);
        progress.setProgress(pageIdx);
        slideBox.setSlide(pageIdx, pageNum);
        MainActivity ac = (MainActivity) mainActivity;

    }
    protected void doRemove() {

        super.doRemove();
        removeUploader();
        removeGifMaker();
        if(slideBox!=null){
            slideBox.removeSlide();
            slideBox=null;
        }
        if(playTimer!=null){
            playTimer.removeTimer();
            playTimer=null;

        }
        if(records!=null){

            Bitmap record;
            for(int i=0;i<records.size();++i){
                record=records.get(i);
                if(record!=null) {
                    record.recycle();
                }
            }
            records.clear();
            records=null;

        }

    }
    private void setFpsTxt(){
        String desc="FPS : "+String.format("%.1f" , getFps(fpsBar.getProgress()));
        fpsTxt.setText(desc);

    }

    private int getFpsProgress(int num){
        num=num-SetupInfo.getInstence().FR_MIN;
        return num;
    }
    private int getFrameRate(int num){
        num=num+SetupInfo.getInstence().FR_MIN;
        return num;
    }
    private double getFps(int num){
        num=getFrameRate(num);

        double fps = ((double)SetupInfo.getInstence().FRAME_UNIT/(double)num);
        return fps;
    }
	protected void doResize() {
    	super.doResize();

    }


    public void onClick(View v) {


        if(v==retryBtn){
            mainActivity.changeViewBack();
        }else if(v==gifBtn){

            saveGif(SHERE_TYPE_NONE);

        }else if(v==btnKakao){

            saveGif(SHERE_TYPE_KAKAO);

        }else if(v==btnMsg){

            saveGif(SHERE_TYPE_MSG);

        }else if(v==btnTele){

            saveGif(SHERE_TYPE_TELE);

        }else if(v==btnFBMsg){

            saveGif(SHERE_TYPE_FB_MSG);

        }else if(v==btnUpload){

            saveGif(SHERE_TYPE_UPLOAD);

        }

    }

    private void removeUploader(){
        if(upLoader!=null){

            upLoader.destory();
            upLoader=null;
        }

    }

    private void upLoadGif() {

        if(CommonUtil.isDeviceOnline(mainActivity)==false) {
            mainActivity.viewAlert("", R.string.msg_network_err, null);
            return;
        }else if(gifPath.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_no_share_data,null);
            return;
        }
        mainActivity.loadingStart(true);
        File file = new File(savePath,"");



        Map<String,String> paramA=new HashMap<String,String>();



        String aesValue=Aes.encodeAesString(Config.AES_KEY);
        String keyValue="";



        try {
            keyValue= Aes.aesEncrypt(Config.AES_KEY, aesValue, Aes.AES_ECB);

        } catch (Exception e) {
            Log.i("", "aes fail");
        }
        Log.i("", "aes check"+Aes.decodeAesString(aesValue,Config.AES_KEY));
        paramA.put("key",keyValue);

        ArrayList<FileObject> files=new ArrayList<FileObject>();
        FileObject entity=new FileObject();
        entity.key="gif";
        entity.name="test";
        entity.file=file;
        files.add(entity);


        upLoader.loadData(Config.DATA_UPLOAD,paramA,files);



    }
    public void onJsonCompleted(DataManager manager,String path,JSONObject result){

        mainActivity.loadingStop();
        String resultStr;
        String value;
        try {
            resultStr=result.getString("result");
        } catch (JSONException e) {
            resultStr="fail";
        }
        try {
            value=result.getString("value");
        } catch (JSONException e) {
            value="";
        }

        Log.i("","upload complete : "+value);

        String gifpath=Config.DATA_PATH+value;

        shareLink(gifpath);

    }
    public void onJsonLoadErr(DataManager manager,String path){
        mainActivity.loadingStop();
        Log.i("","upload error");

    }
    private void removeGifMaker(){


        if( gifMaker!=null){

            gifMaker.removeMaker();
            gifMaker=null;
        }


   }
    private void saveGif(final String sendType){

        imgStop();
        removeGifMaker();
        mainActivity.loadingStart(true);
        finalSendType=sendType;
        savePath="";
        if(gifChanged==false){

            savePath=gifPath;
            saveComplete("image/gif",sendType);
            return;
        }


        gifChanged=false;
        final int delayTime=SetupInfo.getInstence().getScanTime(frameRate);

        gifMaker=new GifMaker(this);
        gifMaker.encode(records,delayTime);




    }
    public void onGifMakingProgress(GifMaker maker, int progress){
        slideBox.moveIndex(progress-1);
    }
    public void onGifMakingCompleted(GifMaker maker,  byte[] gifbytes){
        savePath=GraphicUtil.saveGifImage(gifbytes,"clipgif"+ CommonUtil.getCurrentTimeCode(),Config.SAVE_DIR);
        gifPath=savePath;
        saveComplete("image/gif",finalSendType);
    }

    private void saveComplete(final String type,final String sendType){

        mainActivity.loadingStop();
        if(sendType.equals(SHERE_TYPE_NONE)==true){

            mainActivity.viewAlert("", R.string.msg_save_complete, null);
        }else if(sendType.equals(SHERE_TYPE_UPLOAD)==true){

            upLoadGif();
        }else if(sendType.equals(SHERE_TYPE_ALL)==true){

            CharSequence[] items={"shareImg","complete"};

            mainActivity.viewAlertSelectWithMenu("",R.string.msg_save_complete,items,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    if(which==-1){
                        shareImage(type,SHERE_TYPE_ALL);

                    }else{


                    }

                }
            });
        }else{

            shareImage(type,sendType);
        }



    }
    private void shareLink(String path){

         Intent waIntent = new Intent(Intent.ACTION_SEND);

         if (waIntent != null) {
             waIntent.setType("text/plain");
             waIntent.putExtra(Intent.EXTRA_TEXT, path);
             mainActivity.startActivity(Intent.createChooser(waIntent, "Share with"));
         } else {
             mainActivity.viewAlert("", R.string.msg_share_no_app,null);
         }

    }
    private void shareImage(String type,final String sendType){
        if(savePath.equals("")==true){
            mainActivity.viewAlert("",R.string.msg_no_share_data,null);
            return;
        }




        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType(type);

        Log.i("","sendType : "+sendType);
        if(sendType.equals(SHERE_TYPE_ALL)==true){

        }else{
            try {

                PackageManager pm = mainActivity.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(sendType.trim(), PackageManager.GET_META_DATA);
                waIntent=waIntent.setPackage(sendType);
            }

            catch (PackageManager.NameNotFoundException e)
            {
                waIntent=null;
            }


        }

        if (waIntent != null) {
            File file = new File(savePath,"");
            Uri img=Uri.fromFile(file);

            Log.i("","savePath img : "+img);
            waIntent.putExtra(Intent.EXTRA_STREAM, img);
            mainActivity.startActivity(Intent.createChooser(waIntent, "Share with"));
        } else {
            mainActivity.viewAlertSelect("", R.string.msg_share_no_app, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    if (which == -1) {
                        CommonUtil.goLink(mainActivity, CommonUtil.getMarketUrl(sendType));

                    } else {


                    }

                }
            });
        }

    }




////////////////
    public void onTime(CustomTimer timer){
        int idx=pageIdx+1;
        if(idx>=pageNum){
            idx=0;
        }

        //Log.i("","moveInit :"+idx);
        slideBox.moveIndex(idx);
    }
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){}
    public void onStartTrackingTouch(SeekBar seekBar){}
    public void onStopTrackingTouch(SeekBar seekBar){
        if(seekBar==fpsBar){
            int crate=getFrameRate(fpsBar.getProgress());
            if(crate!=frameRate){

                gifChanged=true;
            }


            frameRate=crate;
            setFpsTxt();
            if(slideBox!=null){
                slideBox.moveIndex(0);
            }
            if(playTimer!=null){

                playTimer.resetTimer(SetupInfo.getInstence().getScanTime(frameRate));
                if(isPlaying){
                    imgPlay();
                }else{
                    imgStop();
                }
            }





        }else{
            slideBox.moveIndex(seekBar.getProgress());

        }


    }


    public  void onComplete(CustomTimer timer){}



    private void togglePlay(){

        playTimer.resetTimer();
        if(isPlaying==true){
            slideBox.setActive(true);
            imgStop();
        }else{
            slideBox.setActive(false);
            imgPlay();
        }



    }
    private void imgPlay(){
        isPlaying=true;
        playTimer.timerStart();
        playImg.setSelected(isPlaying);
        playImg.setVisibility(View.GONE);
    }
    private void imgStop(){
        isPlaying=false;
        playTimer.timerStop();
        playImg.setSelected(isPlaying);
        playImg.setVisibility(View.GONE);
    }
    private void setSlide(FrameLayout frame, int idx){

        if(idx<0 || idx>=pageNum){
            return;
        }
        ImageView currentView;

        currentView=new ImageView(mainActivity);

        frame.addView(currentView, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));



        currentView.setImageBitmap(records.get(idx));
       // currentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public void  moveInit(SlideBox slideView,FrameLayout frame,int dr,int idx){
        //Log.i("","moveInit :"+idx);

        setSlide(frame,idx+dr);


    }
    public void clearFrame(SlideBox slideView,FrameLayout cframe){
        View child=cframe.getChildAt(0);
        if(child!=null){
            if(child instanceof ImageView==true){
                ImageView currentView=(ImageView)child;
                currentView.setImageResource(R.drawable.transparent);
            }


        }
        cframe.removeAllViews();
    }

    public void frameSizeChange(SlideBox slideView,FrameLayout cframe,int idx){

    }
    public void moveComplete(SlideBox slideView,FrameLayout cframe,int idx){

        pageIdx=idx;
        progress.setProgress(pageIdx);
    }
	

	
	public void onTouchStart(SlideBox slideView){};
	public void onTouchEnd(SlideBox slideView){};
	
	public void onTopSlide(SlideBox slideView){};
	public void onEndSlide(SlideBox slideView){};
	public void selectSlide(SlideBox slideView,int idx){
        if(playImg.getVisibility()==View.GONE){

            playImg.setVisibility(View.VISIBLE);
        }
        else{
            togglePlay();

        }




    };
}
