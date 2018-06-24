package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import lib.core.ActivityCore;
import lib.opengl.GLESTransform;
import lib.opengl.GLESTransform.GLESTransformDelegate;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;



public class PhotoSelecter {
	
	
	public static final int PICK_FROM_CAMERA = 0;  
	public static final int PICK_FROM_ALBUM = 1;  
	public static final int CROP_FROM_CAMERA = 2;
	
	private Uri mImageCaptureUri;
	private Activity activity;
	
	public PhotoSelecterDelegate delegate=null;
	
	public PhotoSelecter(Activity _activity) 
	{
		activity=_activity;
		
	}
	
	
	
	public void takePhotoAction()  
    {   
    	  
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);       
    	mImageCaptureUri = getTempUri();
    	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
    	activity.startActivityForResult(intent, PICK_FROM_CAMERA);  
     }   
    
	public void takeAlbumAction()
    {   
    	Intent intent = new Intent(Intent.ACTION_PICK);    
    	intent.setType(MediaStore.Images.Media.CONTENT_TYPE);    
    	activity.startActivityForResult(intent, PICK_FROM_ALBUM);  
    	
    }  
    
	private Uri getTempUri(){
		 String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";    
    	 Uri tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));  
		 return tempUri; 
	}
	
	public void activityResult(int requestCode, Intent data)  
    {    
    	
    	
    	switch(requestCode)    
    	{      
    	    case CROP_FROM_CAMERA:      

    		    break;      
    	    default: 
    	    	Log.i("CAMERA","CAMERA_INIT requestCode="+requestCode);
    	    	if(requestCode==PICK_FROM_ALBUM){
    	    		mImageCaptureUri = data.getData();  
    	    	}else{
    	    		Log.i("CAMERA","mImageCaptureUri="+mImageCaptureUri);
    	    	    
    	    	}
    	    	Bitmap photo=null;
    	    	try {
    	    		photo=GrapicUtil.getBitmapFromUri( activity, mImageCaptureUri, 350);
    	    		int degrees=GrapicUtil.getOrientationToDegreesModifyExifInterface(mImageCaptureUri.getPath());
    	    		photo=GrapicUtil.rotateImage(photo, degrees);
    		      } catch (FileNotFoundException e) 
    		      {
    		          e.printStackTrace();
    		          Log.i("FileNotFoundException","FileNotFoundException");
    		          return;
    		      } catch (IOException e) 
    		      {
    	              e.printStackTrace();
    	              Log.i("IOException","IOException");
    	              return;
    		      }
    	    	
    	    	if(delegate!=null){
    				
    				delegate.getPhotoComplete(photo);
    			}
    	    	
     		    File f = new File(mImageCaptureUri.getPath());        
     		    if(f.exists())        
     		    {          
     			    f.delete();        
     		    } 
     		    mImageCaptureUri=null;
    	    	
     		    break;      
    	      
    	}  
    }
	
	public interface PhotoSelecterDelegate
	{
		void getPhotoComplete(Bitmap photo);
		
	}
}
