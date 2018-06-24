package  lib.view;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import lib.CommonUtil;
import lib.CustomTimer;
import lib.GraphicUtil;
import lib.ViewUtil;
import lib.CustomTimer.TimerDelegate;
import lib.core.ActivityCore;


import android.app.Activity;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.ImageFormat;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;


import android.media.FaceDetector;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import android.hardware.Camera.*;

import com.aeddang.clipmaker.MainActivity;


public class VideoScaner extends FrameLayout  implements  SurfaceHolder.Callback , PreviewCallback ,
                                                         AutoFocusCallback,FaceDetectionListener,TimerDelegate
{
	
	private Camera camera;
	private SurfaceView viwer;
	private SurfaceHolder holder;
    private VideoScanerDelegate delegate;
    
    private CustomTimer autoFucusTimer;
    private int rotationType;
    private int scanTime;
    private int st;
    public int maxResolution;
    public boolean isCapture,isCompactSize,isMaintenanceRatio;
    private boolean isAutoFucus,isFront,isFaceDetectAble,isFlashOn,isFrontReverse;
    private Context context;
    private float dpi;
	public VideoScaner(Context _context,VideoScanerDelegate _delegate,boolean _isFront) {
		super(_context);
		context=_context;
		maxResolution=1024;
		rotationType=0;
		scanTime=10;
		st=0;
		isCapture=false;
        isCompactSize=false;
        isMaintenanceRatio=false;

        isFlashOn=false;
        isFront=_isFront;
        isAutoFucus=false;
        delegate=_delegate;
		autoFucusTimer=new CustomTimer(1000,1,this);
        dpi= MainActivity.getInstence().dpi;


        isFrontReverse=true;

	}

    public void setScanTime(int _scanTime)
    {
        this.scanTime = _scanTime;
    }

    public void init()
	{
		viwer=new SurfaceView(context);
        holder=viwer.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


		this.addView(viwer, 0,new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ,Gravity.LEFT|Gravity.TOP));
	}

    public boolean isFront() {
        return isFront;
    }



    public void setAutoFucus(boolean ac)
    {
        isAutoFucus=ac;
        if(ac==true){
            autoFucusTimer.timerStart();
        }else{
            autoFucusTimer.timerStop();

        }

    }
    public void removeScan()
	{
    	removeTimer();
    	closeCamera();
    	delegate=null;

        ViewUtil.remove(viwer);
    	ViewUtil.remove(this);
    	
	}
    private void removeTimer(){
    	if(autoFucusTimer!=null){
    		autoFucusTimer.removeTimer();
    		autoFucusTimer=null;
    	}
    	
    }
	
	public void onTime(CustomTimer timer){
		//Log.i("onTime","onAutoFocus START");
		fucusStop();
		fucusStart();
    }
	public void onComplete(CustomTimer timer){
		
	}
	private void fucusStop() {
		
		try {
	        camera.cancelAutoFocus();
	    } catch (RuntimeException re) {
	       
	    }
	}
	private void fucusStart() {
		
		try {
	        camera.autoFocus(this);
	    } catch (RuntimeException re) {
	       
	    }
	}
    public boolean getFaceDetectAble(){
       return isFaceDetectAble;
    }
    public boolean getFlashOn(){
        return isFlashOn;
    }
    public void setCameraFlash(boolean ac) {
        if(isFront==true){
            return;
        }
        if(camera==null){
            return;
        }
        if(isFlashOn==ac){

            return;
        }

        isFlashOn=ac;
        Camera.Parameters cmp = camera.getParameters();
        if(ac==true) {
            cmp.setFlashMode(Parameters.FLASH_MODE_TORCH);
        }else{
            cmp.setFlashMode(Parameters.FLASH_MODE_OFF);
        }
        camera.setParameters(cmp);
    }
    public void detectStart() {
        if(camera==null){
            return;
        }
        if(isFaceDetectAble==false){
            return;

        }
        Log.i("detectStart","detectStart");
        try {
            camera.startFaceDetection();

        } catch (RuntimeException re) {

        }


    }
    public void detectStop() {
        if(camera==null){
            return;
        }
        if(isFaceDetectAble==false){
            return;

        }
        Log.i("detectStop","detectStop");
        try {
            camera.stopFaceDetection();
        } catch (RuntimeException re) {

        }


    }

	public void onAutoFocus(boolean success, Camera camera) {
		 
		
		if(success){
			//Log.i("onAutoFocus","onAutoFocus SUCCESS");
			if(isAutoFucus==true) {
                autoFucusTimer.resetTimer();
                autoFucusTimer.timerStart();
            }
    	}

    }

    public void onFaceDetection(Face[] faces, Camera camera) {


        //Log.i("FaceDetection", "face detected: "+ faces.length);
        if(delegate!=null) {
            if (faces.length > 0) {
                int md = 1000;
                double sc = 2000.f;
                double vw=(double)viwer.getWidth();
                double vh=(double)viwer.getHeight();
                Face face;
                Rect rect;
                Point point;

                //Log.i("viwer", "viwer vw "+ vw+" vh"+vh);
                for (int i = 0; i < faces.length; ++i) {
                    face = faces[i];
                    //face.rect.left=face.rect.left*2;
                    //face.rect.top=face.rect.top*2;
                   // face.rect.right=face.rect.right*2;
                   // face.rect.bottom=face.rect.bottom*2;
                    Log.i("","org : "+face.rect.toString());
                    rect = new Rect();
                    if (isFront == false) {
                        switch (rotationType) {

                            case Surface.ROTATION_0:
                                rect.left = -face.rect.bottom;
                                rect.top = face.rect.left;
                                rect.right= -face.rect.top;
                                rect.bottom= face.rect.right;
                                face.rect=rect;
                                if(face.leftEye!=null){



                                    point=new Point();
                                    point.x=-face.leftEye.y;
                                    point.y=face.leftEye.x;
                                    face.leftEye=point;
                                }
                                if(face.rightEye!=null){

                                    point=new Point();
                                    point.x=-face.rightEye.y;
                                    point.y=face.rightEye.x;
                                    face.rightEye=point;
                                }
                                if(face.mouth!=null){
                                    point=new Point();
                                    point.x=-face.mouth.y;
                                    point.y=face.mouth.x;
                                    face.mouth=point;

                                }
                                break;
                            case Surface.ROTATION_90:

                                break;
                            case Surface.ROTATION_180:
                                //result = GraphicUtil.rotateImage(retimage, -90);

                                break;
                            case Surface.ROTATION_270:
                                //result = GraphicUtil.rotateImage(retimage, 180);

                                break;

                        }
                    } else {

                        switch (rotationType) {

                            case Surface.ROTATION_0:


                                rect.left = -face.rect.bottom;
                                rect.right= -face.rect.top;
                                rect.top = -face.rect.left;
                                rect.bottom= -face.rect.right;

                                face.rect=rect;
                                if(face.leftEye!=null){
                                    Log.i("","leftEye : "+face.leftEye.toString());
                                    point=new Point();
                                    point.x=-face.leftEye.y;
                                    point.y=-face.leftEye.x;
                                    face.leftEye=point;
                                }
                                if(face.rightEye!=null){
                                    Log.i("","rightEye : "+face.rightEye.toString());
                                    point=new Point();
                                    point.x=-face.rightEye.y;
                                    point.y=-face.rightEye.x;
                                    face.rightEye=point;
                                }
                                if(face.mouth!=null){
                                    point=new Point();
                                    point.x=-face.mouth.y;
                                    point.y=-face.mouth.x;
                                    face.mouth=point;

                                }
                                break;
                            case Surface.ROTATION_90:
                                // frontimage = GraphicUtil.rotateImage(retimage, 180);


                                break;
                            case Surface.ROTATION_180:
                                // frontimage = GraphicUtil.rotateImage(retimage, 90);

                                break;
                            case Surface.ROTATION_270:

                                break;

                        }


                    }


                    face.rect.left=face.rect.left+md;
                    face.rect.top=face.rect.top+md;
                    face.rect.right=face.rect.right+md;
                    face.rect.bottom=face.rect.bottom+md;
                    int memory;
                    if(face.rect.left>face.rect.right){
                        memory=face.rect.left;
                        face.rect.left=face.rect.right;
                        face.rect.right=memory;

                    }
                    if(face.rect.top>face.rect.bottom){
                        memory=face.rect.top;
                        face.rect.top=face.rect.bottom;
                        face.rect.bottom=memory;

                    }

                    face.rect.left=(int)Math.round(vw*(double)face.rect.left/sc);
                    face.rect.top=(int)Math.round(vh*(double)face.rect.top/sc);
                    face.rect.right=(int)Math.round(vw*(double)face.rect.right/sc);
                    face.rect.bottom=(int)Math.round(vh*(double)face.rect.bottom/sc);
                    Log.i("","mdf : "+face.rect.toString());
                }

                delegate.detectFace(faces);
            }
        }

    }

	public void onPreviewFrame(byte[] data, Camera _camera) 
	{
		if(camera==null){
			return;
		}
		int t=-1;
		if(scanTime!=-1){
			t=st%scanTime;
		}
        st++;
		if(t==0 ){

            if(isCapture==true) {
                if (delegate != null) {
                    delegate.scanImage(data);
                }

            }else{
                if (delegate != null) {
                    delegate.scanImage(null);
                }

            }


		}else{


        }
		
	}
    public Bitmap getCaptureImage(byte[] data)
    {
        if(camera==null){

            return null;
        }
        Parameters parameters = camera.getParameters();
        return decodeNV21(data,parameters);
    }

	private synchronized Bitmap decodeNV21(byte[] data, Camera.Parameters ps){

		Bitmap retimage = null;

		if(ps.getPreviewFormat() == ImageFormat.NV21 /* || YUV2, NV16 */){
	        int w = ps.getPreviewSize().width;
			int h = ps.getPreviewSize().height;
			YuvImage yuv_image = new YuvImage(data, ps.getPreviewFormat(), w, h, null);
			
			Rect rect = new Rect(0, 0, w, h);
			ByteArrayOutputStream out_stream = new ByteArrayOutputStream();
			yuv_image.compressToJpeg(rect, 100, out_stream); 
			retimage = BitmapFactory.decodeByteArray(out_stream.toByteArray(), 0, out_stream.size());
        }else if(ps.getPreviewFormat() == ImageFormat.JPEG || ps.getPreviewFormat() == ImageFormat.RGB_565){

			retimage = BitmapFactory.decodeByteArray(data, 0, data.length);

		}
		//Log.i("","rotationType : "+rotationType);
		Bitmap result=null;

        if(isFront==false) {
            switch (rotationType) {

                case Surface.ROTATION_0:
                    result = GraphicUtil.rotateImage(retimage, 90);
                    retimage.recycle();
                    break;
                case Surface.ROTATION_90:
                    result = retimage;
                    break;
                case Surface.ROTATION_180:
                    result = GraphicUtil.rotateImage(retimage, -90);
                    retimage.recycle();
                    break;
                case Surface.ROTATION_270:
                    result = GraphicUtil.rotateImage(retimage, 180);
                    retimage.recycle();
                    break;

            }
        }else{
           // Log.i("","rotationType: "+rotationType);
            Bitmap frontimage = null;
            switch (rotationType) {

                case Surface.ROTATION_0:
                    frontimage = GraphicUtil.rotateImage(retimage, -90);
                    retimage.recycle();
                    break;
                case Surface.ROTATION_90:
                    frontimage = GraphicUtil.rotateImage(retimage, 180);
                    retimage.recycle();

                    break;
                case Surface.ROTATION_180:
                    frontimage = GraphicUtil.rotateImage(retimage, 90);
                    retimage.recycle();
                    break;
                case Surface.ROTATION_270:
                    frontimage = retimage;
                    break;

            }
            result=GraphicUtil.flipImage(frontimage);
            frontimage.recycle();

        }
		Bitmap resizeImg=null;
		int rw=result.getWidth();
		int rh=result.getHeight();
		boolean isResize=false;
		if(rw>maxResolution && rw>=rh){
			rh=Math.round(rh*maxResolution/rw);
			rw=maxResolution;
			isResize=true;
		}else if(rh>maxResolution && rh>=rw){
			rw=Math.round(rw*maxResolution/rh);
			rh=maxResolution;
			isResize=true;
		}  
		if(isResize==true){
			resizeImg=GraphicUtil.resizeImage(result, rw,rh);
			result.recycle();
		}else{
			resizeImg=result;
		}
		
		return resizeImg;

	}
  	
	
	
	
	public void surfaceCreated(SurfaceHolder holder) {
		
		if(camera==null){
			
			try {
		        if(isFront==true){
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                }else{
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);

                }
               // attempt to get a Camera instance
		    }
		    catch (Exception e){
		        return;
		    }
            Rect viewerRect=null;
			try {


				camera.setPreviewCallback(this);

				camera.setPreviewDisplay(holder);
				
				ActivityCore ac=ActivityCore.getInstence();
				rotationType =  ac.getScreenOrientation();
				Log.i("","rotationType : "+rotationType);
		        int degrees = 0;
		        

		        
		        switch (rotationType) {

					case Surface.ROTATION_0: degrees = 0; break;
		            case Surface.ROTATION_90: degrees = 90; break;
		            case Surface.ROTATION_180: degrees = 180; break;
		            case Surface.ROTATION_270: degrees = 270; break;

				}

				int result  = (90 - degrees + 360) % 360;
		        camera.setDisplayOrientation(result);
			    Camera.Parameters parameters = camera.getParameters();
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO))
                {
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                }

                viewerRect=setViwerSize(parameters,degrees);
				camera.setParameters(parameters);
				camera.startPreview();

                if (parameters.getMaxNumDetectedFaces() > 0){
                    camera.setFaceDetectionListener(this);
                    isFaceDetectAble=true;

                    Log.i("isFaceDetectAble","isFaceDetect able");
                }else{
                    isFaceDetectAble=false;
                    Log.i("isFaceDetectAble","isFaceDetect disable");
                }
				fucusStart();
				

			} catch (IOException e) {
				closeCamera();
			}
			if(delegate!=null){
			    delegate.scanReady(this, viewerRect);
			}
            fucusStart();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
		closeCamera();
		
    }

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

		Log.i("surfaceChanged", "surfaceChanged");
		
    }
    private Size selectSize(List<Size> sizes,Boolean isRt,float sc,String type)
    {

        Size selectSize=null;
        int i=0;
        float gep=1000;
        float psc=0;
        float rtGep=10000;

        Size size;
        for(i=0;i<sizes.size();++i){
            size=sizes.get(i);
            Log.i("",type+" size :"+size.width+" "+size.height);

            if(sc==0){

                if(isRt==true){
                    psc=(float)maxResolution-(float)size.width;
                }else{
                    psc=(float)maxResolution-(float)size.height;

                }
                psc=Math.abs(psc);

            }else{
                if(isRt==true){
                    psc=(float)size.height/(float)size.width;
                }else{
                    psc=(float)size.width/(float)size.height;

                }
                psc=Math.abs(sc-psc);
            }
            //Log.i("",type+" psc :"+psc);

            //Log.i("",type+" pscget :"+psc);
            if(isMaintenanceRatio==true){
                if(size.height==size.width){
                    selectSize=size;
                    break;
                }

            }

            if(psc<=gep){
                float crt= 0;
                if(isRt==true){
                    crt=(float)maxResolution-(float)size.width;
                }else{
                    crt=(float)maxResolution-(float)size.height;

                }
                crt=Math.abs(crt);
                if(rtGep>crt) {
                    rtGep=crt;
                    gep = psc;
                    selectSize = size;
                }
            }


        }
        return selectSize;
    }
    private List<Size> getPictureSizes(Camera.Parameters parameters)
    {

        List <Size> ablePictures=new ArrayList<Size>();
        List<Size> pictureSizes = parameters.getSupportedPictureSizes();
        List<Size> previewSizes = parameters.getSupportedPreviewSizes();
        List <Size> able;
        float ratio;
        Size size;
        for(int i=0;i<pictureSizes.size();++i){
            size=pictureSizes.get(i);
            ratio=(float)size.height/(float)size.width;
            able=getAbleSize(previewSizes,ratio);
            if(able.size()>0){
                ablePictures.add(size);
            }

        }
        return ablePictures;



    }
    private List<Size> getAbleSize( List<Size> sizes,float ratio)
    {
        List <Size> able=new ArrayList<Size>();
        float crt;
        Size size;
        for(int i=0;i<sizes.size();++i){
            size=sizes.get(i);
            crt=(float)size.height/(float)size.width;
            if(crt==ratio){
                able.add(size);
            }

        }
        return able;
    }



	private synchronized Rect setViwerSize(Camera.Parameters parameters,int degrees)
	{
		
		boolean isRt;
		if(degrees==90 || degrees == 270){
			isRt=false;
			
		}else{
			isRt=true;
			
		}
		
		int sw= this.getWidth();
		while(sw==-1){
			sw= this.getWidth();
		}


		int sh=this.getHeight();

		
		List<Size> pictureSizes = getPictureSizes(parameters);
        List<Size> previewSizes = parameters.getSupportedPreviewSizes();

        Size pictureSize=selectSize(pictureSizes,isRt,0,"pitcture");
        float sc=0;
        if(isRt==true){
            sc= (float)pictureSize.height/(float)pictureSize.width;
        }else{
            sc= (float)pictureSize.width/(float)pictureSize.height;
        }
		Size previewSize=selectSize(previewSizes,isRt,sc,"preview");

        Log.i("","picture size select :"+pictureSize.width+" "+pictureSize.height);
        Log.i("","picture size ratio :"+sc);
        Log.i("","preview size select :"+previewSize.width+" "+previewSize.height);
		parameters.setPictureSize(pictureSize.width, pictureSize.height);
		parameters.setPreviewSize(previewSize.width, previewSize.height);
		
		Rect rec=new Rect();
		if(isRt==true){
			rec.bottom=pictureSize.width;
			rec.right=pictureSize.height;
		}else{
			rec.right=pictureSize.width;
			rec.bottom=pictureSize.height;
		}
		
		rec=CommonUtil.getEqualRatioRect(rec, sw, sh, true,0,0);
	   
		
	    LayoutParams layout=(LayoutParams) viwer.getLayoutParams();
	    if(isCompactSize==true){

            layout.leftMargin=0;
            layout.topMargin=0;
            layout.width= ViewGroup.LayoutParams.MATCH_PARENT;
            layout.height=ViewGroup.LayoutParams.MATCH_PARENT;
        }else{
            layout.leftMargin=rec.left;
            layout.topMargin=rec.top;
            layout.width=rec.right;
            layout.height=rec.bottom;

        }


        viwer.setLayoutParams(layout);
        return rec;
	}

	private synchronized void closeCamera() {
		
		
		
		if(camera!=null){
			
			fucusStop();
            camera.setFaceDetectionListener(null);
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera= null;
		}
	}
   
	
   
    public interface VideoScanerDelegate
	{
	    void scanReady(VideoScaner scan, Rect layout);
	    void scanImage(byte[] data);

        void detectFace(Face[] faces);
	}
    
  
}
