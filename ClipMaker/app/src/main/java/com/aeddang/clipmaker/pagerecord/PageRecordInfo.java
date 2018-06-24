package com.aeddang.clipmaker.pagerecord;





import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.util.Log;

import android.view.Gravity;
import android.widget.FrameLayout;

import com.aeddang.clipmaker.DataObject;
import com.aeddang.clipmaker.MainActivity;
import com.aeddang.clipmaker.SetupInfo;


public class PageRecordInfo extends Object {

    public final int MAX_DRAUTION=48;
    public final int MIN_DRAUTION=3;
    public final int CLIP_SIZE=160;

    public final int MAX_FACE_DELAY=30;

    public boolean isRecording,isDetectAble,isFront;;
    public static int pageIdx=0;
    public int pageNum=0;

    public int faceDelayClear=0;

    public PageRecordInfo()
	{

        pageNum=SetupInfo.getInstence().groupA.size();
        isRecording=false;
        isDetectAble=SetupInfo.getInstence().getDetect();
        isFront= SetupInfo.getInstence().getCameraType();
    }

    public FrameLayout.LayoutParams getStartPosition(DataObject obj,int width,int height){

        FrameLayout.LayoutParams layout;
        if(obj.type.equals(DataObject.FIX)==true){
            layout=new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT , Gravity.LEFT|Gravity.TOP);

        }else{

            double w = (double)width;
            double h = (double)height;
            int sizeX=(int)Math.round((double)CLIP_SIZE * MainActivity.getInstence().dpi * obj.scaleRatio);
            int sizeY=(int)Math.round((double)sizeX * obj.sizeRatio);
            layout=new FrameLayout.LayoutParams( sizeX, sizeY ,Gravity.LEFT|Gravity.TOP);
            switch (obj.layoutType){
                case DataObject.LAYOUT_CENTER:
                    layout.leftMargin = (int)Math.floor((w-(double)sizeX)/2.f);
                    layout.topMargin = (int)Math.floor((h-(double)sizeY)/2.f);
                    break;
                case DataObject.LAYOUT_TOP:
                    layout.leftMargin = (int)Math.floor((w-(double)sizeX)/2.f);
                    layout.topMargin = 0;
                    break;
                case DataObject.LAYOUT_BOTTOM:
                    layout.leftMargin = (int)Math.floor((w-(double)sizeX)/2.f);
                    layout.topMargin = (int)h-sizeY;
                    break;
                case DataObject.LAYOUT_LEFT_TOP:
                    layout.leftMargin =0;
                    layout.topMargin =0;
                    break;
                case DataObject.LAYOUT_LEFT_CENTER:
                    layout.leftMargin =0;
                    layout.topMargin = (int)Math.floor((h-(double)sizeY)/2.f);
                    break;
                case DataObject.LAYOUT_LEFT_BOTTOM:
                    layout.leftMargin =0;
                    layout.topMargin = (int)h-sizeY;
                    break;
                case DataObject.LAYOUT_RIGHT_TOP:
                    layout.leftMargin =(int)w-sizeX;
                    layout.topMargin =0;

                    break;
                case DataObject.LAYOUT_RIGHT_CENTERM:
                    layout.leftMargin =(int)w-sizeX;
                    layout.topMargin = (int)Math.floor((h-(double)sizeY)/2.f);
                    break;
                case DataObject.LAYOUT_RIGHT_BOTTOM:
                    layout.leftMargin =(int)w-sizeX;
                    layout.topMargin = (int)h-sizeY;
                    break;


            }

        }
        layout.leftMargin=layout.leftMargin+obj.modifyX;
        layout.topMargin=layout.topMargin+obj.modifyY;
        return layout;
    }
    public FrameLayout.LayoutParams getFacePosition(Camera.Face face,DataObject clip,FrameLayout.LayoutParams layout){

        double left=(double)face.rect.left;
        double top=(double)face.rect.top;
        double right=(double)face.rect.right;

        double bottom=(double)face.rect.bottom;
        double width=Math.abs((double) face.rect.width());
        double height=Math.abs((double) face.rect.height());

        int w=layout.width;
        int h=layout.height;

        int hw=(int)Math.round((double)layout.width/2.f);
        int hh=(int)Math.round((double)layout.height/2.f);

        int cX=layout.leftMargin;
            int cY=layout.topMargin;
            int cW=layout.width;
            int cH=layout.height;

            switch (clip.type){
                case DataObject.FACE:
                    cW=(int)Math.round(width*clip.scaleRatio);
                    cH=(int)Math.round((double)cW*clip.sizeRatio);
                   // Log.i("","FACE");
                    cX=(int)left-(int)Math.round(((double)cW-width)/2);
                    cY=(int)top-(int)Math.round(((double)cH-height)/2);
                    break;
                case DataObject.FACE_HEAD:

                    cX=(int)Math.round(left+(width/2.f));
                    cY=(int)top;

                    cX=cX-hw;
                    cY=cY-h;

                  //  Log.i("","FACE_HEAD");
                    break;
                case DataObject.FACE_HEAD_LEFT:

                    cX=(int)left;
                    cY=(int)top;
                    cX=cX-hw;
                    cY=cY-h;

                  //  Log.i("","FACE_HEAD_LEFT");
                    break;
                case DataObject.FACE_HEAD_RIGHT:

                    cX=(int)right;
                    cY=(int)top;
                    cX=cX-hw;
                    cY=cY-h;

                  //  Log.i("","FACE_HEAD_RIGHT");
                    break;
                case DataObject.FACE_NOSE:

                  //  Log.i("","FACE_NOSE");
                    cX=(int)Math.round(left+(width/2.f));
                    cY=(int)Math.round(top+(height/2.5f));
                    cX=cX-hw;
                    cY=cY-hh;

                    break;
                case DataObject.FACE_EYE:

                    cX=(int)Math.round(left+(width/2.f));
                    cY=(int)Math.round(top+(height/3.f));


                    cX=cX-hw;
                    cY=cY-hh;

                 //   Log.i("","FACE_EYE");
                    break;
                case DataObject.FACE_MOUSE:


                    cX=(int)Math.round(left+(width/2.f));
                    cY=(int)Math.round(top+(height/5.f*3.f));

                    cX=cX-hw;
                    cY=cY-hh;
                  //  Log.i("", "FACE_MOUSE");
                    break;

            }
            layout.leftMargin=cX+clip.modifyX;
            layout.topMargin=cY+clip.modifyY;
            layout.width=cW;
            layout.height=cH;
            //Log.i("","detect cX:"+cX+" cY:"+cY +" cW:"+cW+" cH:"+cH);
            return  layout;




    }
    
}
