package  lib.view;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import android.graphics.Rect;
import android.graphics.YuvImage;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;


import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.widget.FrameLayout;






public class VideoScaner extends FrameLayout  implements  SurfaceHolder.Callback , Camera.PreviewCallback , 
                                                         Camera.AutoFocusCallback,TimerDelegate
{
	
	private Camera camera;
	private SurfaceView viwer;
	private SurfaceHolder holder;
    private VideoScanerDelegate delegate;
    
    private CustomTimer autoFucusTimer;
    private int rotationType;
    public int scanTime;
    private int st;
    public int maxResolution;
    public boolean isCapture;
    
    private Context context;

	public VideoScaner(Context _context,VideoScanerDelegate _delegate) {
		super(_context);
		context=_context;
		maxResolution=1024;
		rotationType=0;
		scanTime=10;
		st=0;
		isCapture=false;
        delegate=_delegate;
		autoFucusTimer=new CustomTimer(1000,1,this);
		autoFucusTimer.timerStart();
		
		
	}
	
	
	
	public void init()
	{
		viwer=new SurfaceView(context);
        holder=viwer.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.addView(viwer, 0,new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ,Gravity.LEFT|Gravity.TOP));
	}
    public void capImage() 
	{
    	isCapture=true;
	}
    public void removeScan()
	{
    	removeTimer();
    	closeCamera();
    	delegate=null;
    	ViewUtil.remove(this);
    	
	}
    private void removeTimer(){
    	if(autoFucusTimer!=null){
    		autoFucusTimer.removeTimer();
    		autoFucusTimer=null;
    	}
    	
    }
	
	public void onTime(CustomTimer timer){
		Log.i("onTime","onAutoFocus START"); 
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


	public void onAutoFocus(boolean success, Camera camera) {
		 
		
		if(success){
			Log.i("onAutoFocus","onAutoFocus SUCCESS"); 
			
			autoFucusTimer.resetTimer();
			autoFucusTimer.timerStart();
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
			
		if(t==0 || isCapture==true){
			Parameters parameters = camera.getParameters();
			Bitmap btm=decodeNV21(data,parameters);
			isCapture=false;
			if(delegate!=null){
			    delegate.scanImage(btm);
			}
		}
		
	}
	private Bitmap decodeNV21(byte[] data, Camera.Parameters ps){

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
		Log.i("","rotationType : "+rotationType);
		Bitmap result=null;
		switch (rotationType) {

			case Surface.ROTATION_0: 
				result=GraphicUtil.rotateImage(retimage, 90);
				retimage.recycle();
				break;
	        case Surface.ROTATION_90:
	        	result=retimage;
	        	break;
	        case Surface.ROTATION_180: 
	        	result=GraphicUtil.rotateImage(retimage, -90);
				retimage.recycle();
	        	break;
	        case Surface.ROTATION_270: 
	        	result=GraphicUtil.rotateImage(retimage, 180);
				retimage.recycle();
	        	break;

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
		        camera = Camera.open(); // attempt to get a Camera instance
		    }
		    catch (Exception e){
		        return;
		    }

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
				setViwerSize(parameters,degrees);
				camera.setParameters(parameters);
				camera.startPreview();
				fucusStart();
				
			} catch (IOException e) {
				closeCamera();
			}
			if(delegate!=null){
			    delegate.scanReady(this);
			}
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
		closeCamera();
		
    }

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

		Log.i("surfaceChanged","surfaceChanged");
		
    }
	private synchronized void setViwerSize(Camera.Parameters parameters,int degrees) 
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
		float sc= (float)sw/(float)sh;
		
		List<Size> pictureSizes = parameters.getSupportedPictureSizes();
		Size pictureSize=null;
		int i=0;
		float gep=1000;
		float psc=0;
		Size size;
		for(i=0;i<pictureSizes.size();++i){
			size=pictureSizes.get(i);
			if(isRt==true){
				psc=(float)size.height/(float)size.width;
			}else{
				psc=(float)size.width/(float)size.height;
				
			}
			psc=Math.abs(sc-psc);
			if(psc<gep){
				gep=psc;
				pictureSize=size;
			}
			
		}
		
		List<Size> previewSizes = parameters.getSupportedPreviewSizes();
		Size previewSize=null;
		gep=1000;
		psc=0;
	
		for(i=0;i<previewSizes.size();++i){
			size=previewSizes.get(i);
			
			if(isRt==true){
				psc=(float)size.height/(float)size.width;
			}else{
				psc=(float)size.width/(float)size.height;
				
			}
			psc=Math.abs(sc-psc);
			if(psc<gep){
				gep=psc;
				previewSize=size;
			}
		}
		
		
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
	    layout.leftMargin=rec.left;
	    layout.topMargin=rec.top;
        layout.width=rec.right;
        layout.height=rec.bottom;
        //viwer.setLayoutParams(layout);

	}

	private synchronized void closeCamera() {
		
		
		
		if(camera!=null){
			
			fucusStop();
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera= null;
		}
	}
   
	
   
    public interface VideoScanerDelegate
	{
	    void scanReady(VideoScaner scan);
	    void scanImage(Bitmap img);
	}
    
  
}
