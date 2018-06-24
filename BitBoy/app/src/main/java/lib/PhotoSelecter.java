package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;



public class PhotoSelecter {
	
	
	public static final int PICK_FROM_CAMERA = 10000;
	public static final int PICK_FROM_ALBUM = 10001;
	public static final int CROP_FROM_CAMERA = 10002;
	public static final int PICK_FROM_VIDEO = 10003;

	
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
	public void takeVideoAction()
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

		takeVideoAction(getOutputVideoUri(timeStamp,""));
	}
	public void takeVideoAction(String filePath,String fileName)
	{
		if(fileName == null)
		{
			fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		}
		takeVideoAction(getOutputVideoUri(fileName,filePath));
	}
	public void takeVideoAction(Uri videoUri)
	{

		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		mImageCaptureUri = videoUri;  // create a file to save the video in specific folder
		if (mImageCaptureUri != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
		}
		//intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		activity.startActivityForResult(intent,PICK_FROM_VIDEO);
	}
	private static Uri getOutputVideoUri(String fileName ,String filePath) {
		if (Environment.getExternalStorageState() == null) {
			return null;
		}

		String extStorageDirectory = Environment.getExternalStorageDirectory().toString()+"/";

		File mediaFile = null;
		if(filePath.equals("")==false){
			File f = new File(Environment.getExternalStorageDirectory(),filePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			extStorageDirectory = extStorageDirectory+filePath+"/";
			mediaFile = new File(extStorageDirectory, fileName+".mp4");

		}else
		{
			File mediaStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "YOUR_APP_VIDEO");
			if (!mediaStorage.exists() &&
					!mediaStorage.mkdirs()) {
				return null;
			}
			mediaFile = new File(mediaStorage, fileName + ".mp4");
		}

		return Uri.fromFile(mediaFile);
	}

	public void takeAlbumAction()
    {   
    	Intent intent = new Intent(Intent.ACTION_PICK);    
    	intent.setType(MediaStore.Images.Media.CONTENT_TYPE);    
    	activity.startActivityForResult(intent, PICK_FROM_ALBUM);  
    	
    }  
    
	private Uri getTempUri(){
		if (Environment.getExternalStorageState() == null) {
			return null;
		}
		String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		Uri tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
		return tempUri;
	}

	private void photoFromCamera(Intent data)
	{
		if(mImageCaptureUri != null) {
			Bitmap photo = null;
			try {
				photo = FileUtil.getBitmapFromUri(activity, mImageCaptureUri, 640);
				if (photo == null) {
					Log.i("FileNotFound", "FileNotFound");
					return;
				}
				int degrees = GraphicUtil.getOrientationToDegreesModifyExifInterface(mImageCaptureUri.getPath());
				photo = GraphicUtil.rotateImage(photo, degrees);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Log.i("FileNotFoundException", "FileNotFoundException");
				return;
			} catch (IOException e) {
				e.printStackTrace();
				Log.i("IOException", "IOException");
				return;
			}

			if (delegate != null) {

				delegate.getPhotoComplete(photo);
			}

			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}
			mImageCaptureUri = null;
		}else
		{

			Log.i("Permission", "GET Permission");
		}
	}
	private void videoFromCamera(Intent data) {

		if(mImageCaptureUri != null) {
			Log.d("Video", "Video saved to:\n" + mImageCaptureUri);
			Log.d("Video", "Video path:\n" + mImageCaptureUri.getPath());
			if(delegate!=null){

				delegate.getVideoComplete(mImageCaptureUri);
			}
		}else
		{
			Log.i("Permission", "GET Permission");
		}

	}
	public void activityResult(int requestCode, Intent data)  
    {    
    	
    	
    	switch(requestCode)    
    	{      
    	    case PICK_FROM_VIDEO:
				videoFromCamera(data);
    		    break;      
    	    default:

				photoFromCamera(data);
     		    break;      
    	      
    	}  
    }
	
	public interface PhotoSelecterDelegate
	{
		void getPhotoComplete(Bitmap photo);
		void getVideoComplete(Uri video);
	}
}
