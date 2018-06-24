package com.aeddang.clipmaker;





import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lib.GraphicUtil;
import lib.imagemanager.ImageLoader;


public class DataObject extends Object implements ImageLoader.ImageLoaderDelegate {


    public static final String FILTER="FILTER";
    public static final String FIX="FIX";
    public static final String MOVE="MOVE";

    public static final String FACE="FACE";
    public static final String FACE_EYE="FACE_EYE";
    public static final String FACE_NOSE="FACE_NOSE";
    public static final String FACE_MOUSE="FACE_MOUSE";
    public static final String FACE_HEAD="FACE_HEAD";
    public static final String FACE_HEAD_LEFT="FACE_HEAD_LEFT";
    public static final String FACE_HEAD_RIGHT="FACE_HEAD_RIGHT";


    public static final String LAYOUT_TOP="CT";
    public static final String LAYOUT_LEFT_TOP="LT";
    public static final String LAYOUT_RIGHT_TOP="RT";

    public static final String LAYOUT_BOTTOM="CB";
    public static final String LAYOUT_LEFT_BOTTOM="LB";
    public static final String LAYOUT_RIGHT_BOTTOM="RB";

    public static final String LAYOUT_LEFT_CENTER="LC";
    public static final String LAYOUT_RIGHT_CENTERM="RC";

    public static final String LAYOUT_CENTER="CC";


    //public static final String CLIP_DIR="clipmaker";

    public String key,type,layoutType;
    private ArrayList<String> paths;
    private ArrayList<Bitmap> btms;
    public DataObjectDelegate delegate;

    public double scaleRatio,sizeRatio;
    public int width,height,modifyX,modifyY;
    private int duration,frame,progress,frameRate,loofDelay,loofTime;
    private Boolean isLoading,isComplete;
    private ImageLoader imageLoader;
    private Bitmap currentBitmap;
    public DataObject()
	{
        key="";
        paths=new ArrayList<String>();
        btms=new ArrayList<Bitmap>();
        delegate=null;
        frame=0;
        progress=0;
        isLoading=false;
        isComplete=false;
        scaleRatio=1.f;
        sizeRatio=1.f;
        loofDelay=0;
        loofTime=0;
        frameRate=1;
        modifyX=0;
        modifyY=0;
        layoutType=LAYOUT_CENTER;
        type=FIX;
    }

    public void setData(JSONObject obj,String gkey)
    {

        //fix
        try {

            String pathkey=obj.getString("key");
            key=gkey+pathkey;

            String path= obj.getString("path");
            duration= obj.getInt("duration");
            String imgPath;
            type=obj.getString("type");

            int len=duration+1;

            for(int i=0;i<len;++i){
                imgPath=Config.DATA_PATH+path+pathkey+i+".png";
                paths.add(imgPath);
                btms.add(null);

            }


           // Log.i("",key+" btms : "+btms.size());


        } catch (JSONException  e) {
            key="";
            duration=0;
            type="";


        }

        //option
        try {
            frameRate=obj.getInt("frameRate");
        } catch (JSONException  e) {
            //scaleRatio=1.f;

        }
        try {
            loofDelay=obj.getInt("loofDelay");
        } catch (JSONException  e) {
            //scaleRatio=1.f;

        }

        try {
            scaleRatio=obj.getDouble("scaleRatio");
        } catch (JSONException  e) {
            //scaleRatio=1.f;

        }
        try {
            sizeRatio=obj.getDouble("sizeRatio");
        } catch (JSONException  e) {
            //sizeRatio=1.f;

        }
        try {
            layoutType = obj.getString("layoutType");
        } catch (JSONException  e) {
            //layoutType=LAYOUT_CENTER;

        }
        try {
            modifyX = obj.getInt("modifyX");
        } catch (JSONException  e) {
            //layoutType=LAYOUT_CENTER;

        }
        try {
            modifyY = obj.getInt("modifyY");
        } catch (JSONException  e) {
            //layoutType=LAYOUT_CENTER;

        }
        modifyX=(int)Math.round((float)modifyX*MainActivity.getInstence().dpi);
        modifyY=(int)Math.round((float)modifyY*MainActivity.getInstence().dpi);
        loofTime=loofDelay;
        loadImages();

    }
    public void loadImages()
    {
        if(isComplete==true){

            return;
        }

        Bitmap btm;
        isComplete=true;

        //Log.i("",key+" load btms : "+btms.size());
        for(int i=0;i<btms.size();++i){

            btm= GraphicUtil.loadImageFromHardCache(MainActivity.getInstence(),getBtmPath(i));
            btms.set(i,btm);
            if(btm==null){
                if(isComplete==true) {
                    progress = i;
                    isComplete = false;
                }
            }else{


            }
        }
        Log.i("",isComplete+" : "+progress);

    }
    public void clearImages()
    {
        Bitmap btm;
        for(int i=0;i<btms.size();++i){
            btm=btms.get(i);
            if(btm!=null){
                btm.recycle();

            }
            btms.set(i,null);
        }
        //Log.i("",key+" clear btms : "+btms.size());
        isComplete=false;

    }
    public void loadPreviewImage()
    {
        if(btms.size()>0){
            if(delegate!=null){
                Bitmap btm= btms.get(0);
                if(btm!=null){
                    delegate.previewComplete(this,btm);
                    return;
                }
            }
        }
        progress=0;
        downLoadImage();
    }
    public void downLoadStop()
    {
        isLoading=false;

    }
    public boolean getComplete()
    {
        return isComplete;
    }
    public void downLoadImages()
    {
        if(isComplete==true){

            if(delegate!=null){
                delegate.downLoadComplete(this);

            }
        }
        if(isLoading==true){

            return;
        }
        isLoading=true;
        downLoadImage();

    }
    public void resetFrame()
    {
        loofTime=loofDelay;
        frame=0;
    }

    public Bitmap getCurrentImage()
    {
        if(currentBitmap==null){
            return getImage();
        }
        return currentBitmap;

    }

    public Bitmap getImage()
    {
        if(btms.size()<1){

            return null;
        }

        int mdf=(int)Math.floor((float)frame/(float)frameRate)+1;
        //Log.i("","getImage : "+frame+"  mdf : "+mdf);
        if(btms.size()<=mdf){
            if(loofTime==0) {
                frame = 0;
                loofTime=loofDelay;
            }else{
                loofTime--;
            }
            mdf = 1;
        }

        currentBitmap=btms.get(mdf);

        frame++;
        return currentBitmap;

    }
    private void removeImageLoader(){

        if(imageLoader!=null) {
            imageLoader.removeLoader();
            imageLoader = null;
        }
    }

    public void onImageLoadCompleted(ImageLoader loader,Bitmap image ){

        //Log.i("","loadComplete");
        if( image==null){
            if(delegate!=null){
                removeImageLoader();
                delegate.downLoadError(this);
            }
            return;
        }
        btms.set(progress,image);

        if(loader!=null){
           // GraphicUtil.saveImage(image,getBtmPath(sel_progress),CLIP_DIR);
            GraphicUtil.saveImageToHardCache(MainActivity.getInstence(),getBtmPath(progress),image);
        }
        if(progress==0){

            if(delegate!=null){
                delegate.previewComplete(this,image);
            }
        }
        progress++;
        //Log.i("","sel_progress : "+sel_progress+"  "+isLoading);
        removeImageLoader();
        if(isLoading==true){

            downLoadImage();

        }

    }
    private void downLoadImage()
    {
        if(imageLoader!=null){

            return;
        }
        Log.i("","sel_progress : "+progress+"  "+duration);
        if(progress<=duration) {
            //Bitmap btm= GraphicUtil.loadImage(getBtmPath(sel_progress),CLIP_DIR);
            Bitmap btm= GraphicUtil.loadImageFromHardCache(MainActivity.getInstence(),getBtmPath(progress));
            if(btm==null){
                imageLoader=new ImageLoader(this);
                imageLoader.loadImg(paths.get(progress));
            }else{
                onImageLoadCompleted(null,btm);
            }


        }else{
            isLoading=false;
            isComplete=true;
            if(delegate!=null){
                delegate.downLoadComplete(this);

            }
        }



    }
    private String getBtmPath(int idx)
    {

        return key+String.valueOf(idx);
    }

    public interface DataObjectDelegate
    {
        void previewComplete(DataObject obj,Bitmap prevImg);
        void downLoadError(DataObject obj);
        void downLoadComplete(DataObject obj);

    }
    
}
