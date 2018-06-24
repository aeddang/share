package com.aeddang.clipmaker.pagerecord;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.aeddang.clipmaker.Config;
import com.aeddang.clipmaker.DataObject;
import com.aeddang.clipmaker.MainActivity;
import com.aeddang.clipmaker.R;
import com.aeddang.clipmaker.SetupInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.CommonUtil;
import lib.GraphicUtil;
import lib.ViewUtil;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.view.Gesture;
import lib.view.SlideBox;
import lib.view.SlideBox.SlideBoxDelegate;
import lib.view.VideoScaner;


@SuppressLint("NewApi")
public class PageRecord extends ViewCore implements
        SlideBoxDelegate,DecoLists.DecoListsDelegate,VideoScaner.VideoScanerDelegate,View.OnTouchListener,Gesture.GestureDelegate, View.OnClickListener{





    private SlideBox slideBox;
    private Gesture clipGesture;
    private SetupInfo setupInfo;
    private VideoScaner scaner;
    private FrameLayout viewer;

    private ImageButton settingBtn,recordBtn,changeViewBtn,changeFlashBtn,detectBtn;
    private ImageView dimed,filter;
    private ArrayList<ImageView> clips;
    private Rect clipPosition;

    private FrameLayout recordViewer,cover;
    private ImageView recordProgress;

    private DataObject selectedClip,selectedFilter;

    private PageRecordInfo info;


    private ArrayList<RecordObject> records;


    public static String selectedClipKey="";
    public static String selectedFilterKey="";

    public static DecoList selectedClipList=null;
    public static DecoList selectedFilterList=null;

    private static int scanerHeight =-1;
    private static int recordTop,recordViwerTop;
    private Handler recordHandler=new Handler();


    public PageRecord(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_record,this);
        slideBox=(SlideBox) findViewById(R.id._slideBox);
        viewer=(FrameLayout) findViewById(R.id._viewer);
        recordBtn=(ImageButton) findViewById(R.id._recordBtn);
        changeViewBtn=(ImageButton) findViewById(R.id._changeViewBtn);
        changeFlashBtn=(ImageButton) findViewById(R.id._changeFlashBtn);
        settingBtn=(ImageButton) findViewById(R.id._settingBtn);
        detectBtn=(ImageButton) findViewById(R.id._detectBtn);

        filter=(ImageView) findViewById(R.id._filter);
        dimed=(ImageView) findViewById(R.id._dimed);
        recordViewer=(FrameLayout) findViewById(R.id._recordViewer);
        recordProgress=(ImageView) findViewById(R.id._recordProgress);
        cover=(FrameLayout) findViewById(R.id._cover);

        setupInfo=SetupInfo.getInstence();
        info=new PageRecordInfo();
        clips=new ArrayList<ImageView>();

        slideBox.initBox(this);


        selectedClip=null;
        selectedFilter=null;

        changeFlashBtn.setVisibility(View.GONE);
        detectBtn.setVisibility(View.GONE);
        dimed.setVisibility(View.GONE);
        recordBtn.setOnTouchListener(this);
        viewer.setOnTouchListener(this);
        settingBtn.setOnClickListener(this);

        changeViewBtn.setOnClickListener(this);
        changeFlashBtn.setOnClickListener(this);
        detectBtn.setOnClickListener(this);

        cover.setVisibility(View.VISIBLE);

        selectedClipKey="";
        selectedFilterKey="";

        detectBtn.setSelected(info.isDetectAble);
        if(scanerHeight!=-1){
            resetPos(scanerHeight,recordTop,recordViwerTop);
        }
    }
	private void resetPos(int _scanerHeight,int _recordTop,int _recordViwerTop){

        scanerHeight=_scanerHeight;
        recordTop=_recordTop;
        recordViwerTop=_recordViwerTop;

        ViewGroup.LayoutParams layoutV=(ViewGroup.LayoutParams)viewer.getLayoutParams();
        layoutV.height=scanerHeight;
        viewer.setLayoutParams(layoutV);

        ViewUtil.setFrame(recordBtn,-1,recordTop,-1,-1);
        ViewUtil.setFrame(recordViewer,-1,recordViwerTop,-1,-1);

        cover.setVisibility(View.GONE);

    }
	protected void doResize() {
    	super.doResize();

    }
    public void onClick(View v) {
        //v.getLocalVisibleRect()
       // Log.i("","CLICK"+v);
        if(v==changeViewBtn){
            changeCamera();
        }else if(v==settingBtn){

            goSetting();
        }else if(v==changeFlashBtn){

            if(scaner.getFlashOn()==true){
                scaner.setCameraFlash(false);
                changeFlashBtn.setSelected(false);
            }else{
                scaner.setCameraFlash(true);
                changeFlashBtn.setSelected(true);

            }
        }else if(v==detectBtn){
            if(info.isDetectAble==true){
                info.isDetectAble=false;

            }else{
                info.isDetectAble=true;

            }
            setupInfo.registerDetect(info.isDetectAble);
            detectBtn.setSelected(info.isDetectAble);
            setFaceDetect();

        }

    }
    private void createScaner(boolean _isFront){

        info.isFront=_isFront;
        scaner=new VideoScaner(mainActivity,this,info.isFront);

        scaner.maxResolution=SetupInfo.getInstence().getResoultion();
        scaner.isCompactSize=true;  //정확한사이즈 뷰어
        scaner.setScanTime(SetupInfo.getInstence().getFR());
        //scaner.setAutoFucus(true);
        if(info.isFront==true){
            changeViewBtn.setSelected(true);
            changeFlashBtn.setVisibility(View.GONE);
        }else{
            changeViewBtn.setSelected(false);
            changeFlashBtn.setVisibility(View.VISIBLE);

        }
        changeFlashBtn.setSelected(false);
        viewer.addView(scaner, 0,new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ,Gravity.LEFT|Gravity.TOP));
        scaner.init();
    }
    private void removeScaner(){

        if(scaner!=null){

            scaner.removeScan();
            scaner=null;
        }
    }
    private void changeCamera(){
        if(scaner.isFront()==true){
            removeScaner();
            createScaner(false);
        }else{
            removeScaner();
            createScaner(true);

        }
        SetupInfo.getInstence().registerCameraType(info.isFront);

    }

    public boolean onTouch(View v, MotionEvent event){

        if(v.equals(recordBtn)==true){
            touchRecord(event);

        }else if(v.equals(viewer)==true){

            touchClip(event);

        }
        return true;

    }
    private void touchClip(MotionEvent event){

        if(selectedClip!=null){

                if(selectedClip.type.equals(DataObject.FIX) ==true){
                    return;

                }



        }else{
            return;
        }
        if(clips.size()<1){
            return;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ImageView clip=clips.get(0);
                LayoutParams layout = (LayoutParams) clip.getLayoutParams();
                clipPosition=new Rect();
                clipPosition.left=layout.leftMargin;
                clipPosition.top=layout.topMargin;

                clipPosition.right=clip.getWidth();
                clipPosition.bottom=clip.getHeight();

                break;
            case  MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                clipPosition=null;
                break;

        }
        clipGesture.adjustEvent(event);


    }

    private void touchRecord(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startRecording();
                break;
            case  MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                completeRecording();
                break;
        }

    }
    private void startRecording(){
        recordCanceled();
        dimed.setVisibility(View.VISIBLE);
        records=new ArrayList<RecordObject>();

        if(selectedClip!=null){

            selectedClip.resetFrame();
        }
        if(selectedFilter!=null){

            selectedFilter.resetFrame();
        }

        info.isRecording=true;
        scaner.isCapture=true;

    }
    private void completeRecording(){
        scaner.isCapture=false;
        dimed.setVisibility(View.GONE);
        if(info.isRecording==false){

            return;
        }

        info.isRecording=false;
        if(records.size()>=info.MIN_DRAUTION){
            mainActivity.viewAlertSelect("", R.string.msg_record_complete, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PageObject pInfo;
                    if(which==-1){

                        goNext();


                    }else{
                        recordCanceled();

                    }

                }
            });

        }else{

            recordCanceled();
        }

    }
    private void goSetting(){


        PageObject pInfo=new PageObject(Config.PAGE_SETTING);
        mainActivity.changeView(pInfo);
    }
    private ArrayList<Bitmap> createRecords(){

        if(records==null){
            return null;
        }

        ArrayList<Bitmap> completes=new ArrayList<Bitmap>();
        RecordObject record;


        Bitmap img;
        Bitmap mergeImg;
        double sc;

        for(int i=0;i<records.size();++i){
            record=records.get(i);

            img=scaner.getCaptureImage(record.data);
            if(img!=null) {
                sc = (double) img.getWidth() / (double) viewer.getWidth();
                mergeImg=record.getRecordImage(img,sc);
                img.recycle();
                record.remove();
                completes.add(mergeImg);
            }

        }
        return completes;

    }

    private void goNext(){
        mainActivity.loadingStart(true);
        new Thread(new Runnable() {
            public void run() {
                final ArrayList<Bitmap> recordCompletes=createRecords();

                recordHandler.post(new Runnable() {
                    public void run() {
                        recordComplete(recordCompletes);
                    }

                });
            }
        }).start();

    }
    private void recordComplete(ArrayList<Bitmap> recordCompletes){
        mainActivity.loadingStop();

        records=null;
        PageObject pInfo=new PageObject(Config.PAGE_COMPLETE);
        Map<String,Object> pinfo=new HashMap<String,Object>();
        pinfo.put("records", recordCompletes);
        pInfo.info=pinfo;

        mainActivity.changeView(pInfo);
    }
	protected void doMovedInit() { 
	    super.doMovedInit();
        slideBox.setSlide(PageRecordInfo.pageIdx, info.pageNum);
        MainActivity ac = (MainActivity) mainActivity;

        createScaner(info.isFront);
        clipGesture=new Gesture(this,true,true);

	}

    ///////////GESTURE
    public  void stateChange(Gesture g,String e){
        if(e==Gesture.PAN){


            if(clipPosition!=null){
                if(clips.size()<1){

                    return;
                }
                ImageView clip=clips.get(0);
                Point delta= g.changePosA.get(0);

                ViewUtil.setFrame(clip, clipPosition.left + delta.x, clipPosition.top + delta.y, -1, -1);
            }


        }

    }
    public void rotateChange(Gesture g,float rotate){

    }
    public void pinchChange(Gesture g,float dist){

        //Log.i("","dist "+dist);
        if(clipPosition!=null) {
            //Rect position=new Rect();
            if(clips.size()<1){

                return;
            }
            ImageView clip=clips.get(0);
            int amount=(int)Math.floor(dist)*20;
            ViewUtil.setFrame(clip,-1,-1,clipPosition.right+amount,clipPosition.bottom+amount);
        }
    }
    public void gestureChange(Gesture g,String e){

    }
    public void gestureComplete(Gesture g,String e){

    }
    ///////////GESTURE


    private synchronized void recordCanceled(){


        recordProgress.setImageResource(R.drawable.record_progress_0000);
        info.isRecording=false;
        if(records!=null){
            RecordObject record;
            for(int i=0;i<records.size();++i){
                record=records.get(i);
                if(record!=null) {
                    record.remove();
                }
            }
            records.clear();
            records=null;
        }

    }
    protected void doRemoveInit() {
        removeScaner();
    }
    protected void doRemove() { 
    	
    	super.doRemove();
        recordCanceled();
        selectedClipCanceled();
        selectedFilterCanceled();
        if(clipGesture!=null){
            clipGesture.delegate=null;
            clipGesture=null;

        }

        if(slideBox!=null){
            slideBox.removeSlide();
            slideBox=null;
        }
        removeScaner();

    }


    ////////scanner  delegate
    public void scanReady(VideoScaner scan,Rect layout){
        Log.i("","READY");


        LayoutParams layoutS=(LayoutParams)scaner.getLayoutParams();
        layoutS.width=viewer.getWidth();
        layoutS.height=(int)Math.round((double)viewer.getWidth()*(double)layout.height()/(double)layout.width());
        Log.i("","layoutS : "+layoutS.width+"  : "+layoutS.height);
        scanerHeight=viewer.getWidth();
        scaner.setLayoutParams(layoutS);
        int recordHei=scanerHeight-(int)Math.round((double)recordBtn.getHeight()/2.f);
        int recordHeiV=scanerHeight-(int)Math.round((double)recordViewer.getHeight()/2.f);
        resetPos(scanerHeight,recordHei,recordHeiV);

        setFaceDetect();

    }

    public void detectFace(Camera.Face[] faces){

        if(selectedClip==null){

            return;
        }
        int remainNum=faces.length;
        int clipSize=clips.size();
        ImageView clip;
        if (faces.length > 0){


            for(int i=0;i<remainNum;++i) {
                if(i<clipSize){
                    clip=clips.get(i);
                }else{
                    clip=creatClip(info.getStartPosition(selectedClip,viewer.getWidth(),viewer.getHeight()));
                }
                Face face = faces[i];
                LayoutParams layout = (LayoutParams) clip.getLayoutParams();
                clip.setLayoutParams(info.getFacePosition(face, selectedClip, layout));
            }
        }else{
            remainNum=1;
        }

        if(clipSize>remainNum){
            //info.faceDelayClear++;
            //if(info.faceDelayClear>=info.MAX_FACE_DELAY) {

                for (int i = clipSize - 1; i >= remainNum; --i) {

                    clip = clips.get(i);
                    ViewUtil.remove(clip);
                    clips.remove(clip);

                }
            //}
        }else{
           // info.faceDelayClear=0;

        }
        //Log.i("","clips : "+clips.size());

    }
    private  ImageView creatClip(FrameLayout.LayoutParams layout){
        if(selectedClip==null){
            return null;
        }
        ImageView clip=new ImageView(mainActivity);
        clips.add(clip);
        viewer.addView(clip,1);
        clip.setLayoutParams(layout);
        //sLog.i("","creatClip");

        return clip;
    }
    public synchronized void scanImage(byte[] data){

        Bitmap cimg=null;
        Bitmap fimg=null;
        ImageView clip;
        int i;
        if(selectedClip!=null){
            cimg=selectedClip.getImage();
            if(cimg!=null){

                for(i=0;i<clips.size();++i) {
                    clip=clips.get(i);
                    clip.setImageBitmap(cimg);
                }
            }

        }
        if(selectedFilter!=null){
            fimg=selectedFilter.getImage();
            if(fimg!=null){
                //Log.i("","cimg "+selectedClip.key+" : " +cimg.isRecycled());
                filter.setImageBitmap(fimg);
            }

        }

        if(data==null){

            return;
        }

        if(info.isRecording==false){

            return;

        }
        RecordObject obj=new RecordObject();
        obj.data=data;
        //if(selectedClip!=null) {
            obj.clip = cimg;
            obj.filter = fimg;
            obj.pozs = new ArrayList<Rect>();
            Rect rec;
            for(i=0;i<clips.size();++i) {
                clip=clips.get(i);
                rec=new Rect();
                LayoutParams layout = (LayoutParams)clip.getLayoutParams();
                rec.left = layout.leftMargin;
                rec.top = layout.topMargin;
                rec.right = rec.left+clip.getWidth();
                rec.bottom = rec.top+clip.getHeight();
                obj.pozs.add(rec);
            }


        //}
        recordProgressFn(obj);


    }
    private void recordProgressFn(RecordObject img){

        if(records!=null){

            records.add(img);
            int num=records.size();
            int idx=1+(int)Math.ceil(48.f*(double)num/(double)info.MAX_DRAUTION);
            String idf= "record_progress_00"+CommonUtil.intToText(idx,2);

            int pid = this.getResources().getIdentifier( idf, "drawable", mainActivity.getPackageName());
            recordProgress.setImageResource(pid);
        }

        if(records.size()>=info.MAX_DRAUTION){

            completeRecording();
        }
    }


    private void setFaceDetect(){


        if(selectedClip==null){
            scaner.detectStop();
            detectBtn.setVisibility(View.GONE);
            return;
        }

        if(selectedClip.type.indexOf("FACE")==-1){
            detectBtn.setVisibility(View.GONE);
        }else{
            detectBtn.setVisibility(View.VISIBLE);

        }

        Log.i("","isDetectAble "+info.isDetectAble+" selectedClip.type : "+selectedClip.type);
        if(info.isDetectAble==false){
            scaner.detectStop();
            return;
        }
        if(selectedClip.type.indexOf("FACE")==-1){
            scaner.detectStop();
        }else{
            scaner.detectStart();

        }

    }
    public  void selectList(DataObject obj,DecoList list){
        if(obj.type.equals(DataObject.FILTER)==true){

            selectFilterList(obj,list);
        }else{

            selectClipList(obj,list);
        }
    }
    private void selectedClipCanceled(){
        if(scaner!=null) {
            scaner.detectStop();
        }
        selectedClipKey="";
        if(selectedClipList!=null){
            selectedClipList.unSelectedList();
            selectedClipList=null;
        }
        clipPosition=null;
        clearAllClips();

        selectedClip=null;
    }
    private void clearAllClips(){
        ImageView clip;
        for(int i=0;i<clips.size();++i) {
            clip=clips.get(i);
            ViewUtil.remove(clip);
        }
        clips.clear();
    }
    private void selectedFilterCanceled(){
        selectedFilterKey="";
        if(selectedFilterList!=null){
            selectedFilterList.unSelectedList();
            selectedFilterList=null;
        }
        filter.setImageResource(R.drawable.transparent);
        selectedFilter=null;
    }

    private  void selectFilterList(DataObject obj,DecoList list){
        if(selectedFilter!=null){
            if(selectedFilter.key.equals(obj.key)==true){

                selectedFilterCanceled();
                return;
            }
        }
        if(selectedFilterList!=null){
            selectedFilterList.unSelectedList();
            selectedFilterList=null;
        }
        selectedFilter=obj;
        selectedFilterList=list;
        selectedFilterKey=obj.key;
        list.selectedList();
        filter.setImageBitmap(selectedFilter.getImage());
    }

    private  void selectClipList(DataObject obj,DecoList list){

        if(selectedClip!=null){
            if(selectedClip.key.equals(obj.key)==true){
                list.unSelectedList();
                selectedClipCanceled();
                return;
            }


        }

        clearAllClips();
        selectedClip=obj;
        ImageView clip=creatClip(info.getStartPosition(obj,viewer.getWidth(),viewer.getHeight()));
        if(selectedClipList!=null){
            selectedClipList.unSelectedList();
            selectedClipList=null;
        }
        selectedClipList=list;
        selectedClipKey=obj.key;
        list.selectedList();

        setFaceDetect();

        //clip.setImageBitmap(selectedClip.getImage());


    }
    private void setSlide(FrameLayout frame, int idx){

        if(idx<0 || idx>=info.pageNum){
            return;
        }
        DecoLists currentView;
        PageObject pInfo=new PageObject(Config.PAGE_RECORD);
        Map<String,Object> pinfo=new HashMap<String,Object>();
        pinfo.put("pageIdx", idx);
        pinfo.put("listSize", slideBox.getWidth());
        pInfo.info=pinfo;
        currentView=new DecoLists(mainActivity,pInfo);
        currentView.listView.setParent(slideBox);
        currentView.delegate=this;
        LayoutParams layout=new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT );
        layout.gravity= Gravity.TOP;

        frame.addView(currentView,layout) ;

    }

    public void  moveInit(SlideBox slideView,FrameLayout frame,int dr,int idx){
        //Log.i("","moveInit :"+idx);

        setSlide(frame,idx+dr);


    }
    public void clearFrame(SlideBox slideView,FrameLayout cframe){
        View child=cframe.getChildAt(0);
        if(child!=null){
            if(child instanceof ViewCore==true){
                ViewCore currentView=(ViewCore)child;
                currentView.removeViewCore();
            }
        }
        cframe.removeAllViews();
    }

    public void frameSizeChange(SlideBox slideView,FrameLayout cframe,int idx){
        View child=cframe.getChildAt(0);
        if(child!=null){
            if(child instanceof ViewCore==true){
                ViewCore currentView=(ViewCore)child;
                //currentView.configurationChanged();
            }


        }
    }
    public void moveComplete(SlideBox slideView,FrameLayout cframe,int idx){

        View child=cframe.getChildAt(0);
        if(child!=null){
            if(child instanceof ViewCore==true){
                ViewCore currentView=(ViewCore)child;
                currentView.movedInit();
            }


        }
        int pos=0;
        if(idx!=PageRecordInfo.pageIdx){

            //selectedCanceled();
        }

        PageRecordInfo.pageIdx=idx;


    }
	

	
	public void onTouchStart(SlideBox slideView){};
	public void onTouchEnd(SlideBox slideView){};
	
	public void onTopSlide(SlideBox slideView){};
	public void onEndSlide(SlideBox slideView){};
	public void selectSlide(SlideBox slideView,int idx){};
}
