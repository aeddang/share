package lib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.security.MessageDigest;

import lib.core.ActivityCore;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;













public class GrapicUtil
{
	private static final String TAG = "GrapicUtil";
	
	public  static  void setSaturation(ImageView v,float saturation){
	    ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(saturation);                        //0이면 grayscale
	    ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
	    v.setColorFilter(cf);
	}

	
	
	public   static Drawable convertSaturation(Drawable d,float saturation){
	    ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(saturation);                    //0이면 grayscale
	    ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
	    d.setColorFilter(cf);

	    return d;
	}
	
	public static Bitmap getBitmapByDrawable(Drawable drawable) {

	     BitmapDrawable btd = (BitmapDrawable) drawable;
	    return btd.getBitmap();
	}
	
	public static File getFileByBitmap(Context context,Bitmap btm,String filename) {
        File f = new File(context.getCacheDir(),filename);
		try {
			f.createNewFile();
			Bitmap bitmap = btm;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();


			
			byte[] bitmapdata = bos.toByteArray();

		
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
			return f;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}

		
	}
	public static Bitmap getBitmapMaskImage(Bitmap maskedImage,Bitmap mask,Boolean isInvert) {

		
         Canvas c = new Canvas();
		 Bitmap result = Bitmap.createBitmap(maskedImage.getWidth(), maskedImage.getHeight(), Bitmap.Config.ARGB_8888);
         c.setBitmap(result);
         c.drawBitmap(maskedImage, 0, 0, null);
         Paint paint = new Paint();
         paint.setFilterBitmap(false);
  
         if(isInvert){
        	 paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT) );
         }else{
        	 paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN) );
         }
         c.drawBitmap(mask, 0, 0, paint);
         paint.setXfermode(null);
         return result;

	 }

	public static Bitmap getBitmapInvertImage(Bitmap src) {
		// create new bitmap with the same settings as source bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
		// color info
		int A, R, G, B;
		int pixelColor;
		// image size
		int height = src.getHeight();
		int width = src.getWidth();

		// scan through every pixel
	    for (int y = 0; y < height; y++)
	    {
	        for (int x = 0; x < width; x++)
	        {
	        	// get one pixel
	            pixelColor = src.getPixel(x, y);
	            // saving alpha channel
	            A = Color.alpha(pixelColor);
	            // inverting byte for each R/G/B channel
	            R = 255 - Color.red(pixelColor);
	            G = 255 - Color.green(pixelColor);
	            B = 255 - Color.blue(pixelColor);
	            // set newly-inverted pixel to output image
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }

	    // return final bitmap
	    return bmOut;
	}

	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	 
	    int color = 0xff424242;
	    Paint paint = new Paint();
	    Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    RectF rectF = new RectF(rect);
	   
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	 
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	    
	    return output;
	 }

	public static Bitmap getBitmapModifyExifInterface(String imagePath){
		ExifInterface exif;
		Bitmap image = BitmapFactory.decodeFile(imagePath);    
		try {
			exif = new ExifInterface(imagePath);
		} catch (IOException e) {
		    
			return image;
		}    
		int exifOrientation = exif.getAttributeInt(  ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);    
		int exifDegree = GrapicUtil.exifOrientationToDegrees(exifOrientation);    
		image = GrapicUtil.rotateImage(image, exifDegree);
		return image;
		
	}
	public static int getOrientationToDegreesModifyExifInterface(String imagePath){
		ExifInterface exif;
		try {
			exif = new ExifInterface(imagePath);
		} catch (IOException e) {
		    
			return 0;
		}    
		int exifOrientation = exif.getAttributeInt(  ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);    
		int exifDegree = GrapicUtil.exifOrientationToDegrees(exifOrientation);    
		return exifDegree;
		
	}
	public static int exifOrientationToDegrees(int exifOrientation)
	{  
		if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)  {    return 90;  }  
		else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)  {    return 180;  }  
		else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)  {    return 270;  }  
		return 0;
	}
   
	
	public static Bitmap getBitmapFromUri(Activity context, Uri uri,float returnSize) throws FileNotFoundException, IOException{
        InputStream input = context.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > returnSize) ? (originalSize / returnSize) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
    public static  Bitmap resizeImage(Bitmap image, int wid, int hei) 
	{
    	return Bitmap.createScaledBitmap(image, wid ,hei, true);
    	
	}
    public static  Bitmap resizeImageByWidth(Bitmap image, int wid) 
	{
    	
    	float h=(float)wid/(float)image.getWidth()*(float)image.getHeight();
    	int hei=(int)Math.floor(h);
    	return Bitmap.createScaledBitmap(image, wid ,hei, true);
    	
	}
    public static  Bitmap resizeImageByHeight(Bitmap image,int hei) 
	{
    	
    	float w=(float)hei/(float)image.getHeight()*(float)image.getWidth();
    	int wid=(int)Math.floor(w);
    	
    	return Bitmap.createScaledBitmap(image, wid ,hei, true);
    	
	}
    public static  Bitmap resizeImageByScale(Bitmap image, float pct) 
	{
    	int wid=(int)Math.floor((float)image.getWidth()*pct);
    	int hei=(int)Math.floor((float)image.getHeight()*pct);
    	return Bitmap.createScaledBitmap(image, wid ,hei, true);
    	
	}
    public static  Bitmap rotateImage(Bitmap image, int degree) 
	{
		if(degree==0){
			return image;
		}
		Bitmap returnImg;
    	Matrix mat=new Matrix();
    	mat.setRotate(degree, image.getWidth()/2, image.getHeight()/2);
    	
    	try {
    		returnImg=Bitmap.createBitmap(image, 0,  0, image.getWidth(),  image.getHeight(),mat,true);
    	} catch (OutOfMemoryError e)
    	{
    	    returnImg=image;
    	}
    	return returnImg;
	}
	    
    public static  Bitmap cropBitmapImageByRect(Bitmap image, Rect range)
	{
    	try {
    		 image=Bitmap.createBitmap(image, range.left,  range.top, range.right,  range.bottom);
    	} catch ( IllegalArgumentException e)
    	{
    		image=null;
    	}
       
        return image;
	}

	public static  Bitmap cropImageByRect(Drawable image, Rect range) 
	{
		BitmapDrawable imageB =(BitmapDrawable)image;
		Bitmap bitMap = imageB.getBitmap();
		bitMap=Bitmap.createBitmap(bitMap, range.left,  range.top, range.right,  range.bottom);
		return bitMap;
	}
	public static  void saveImageForAlbum(ContentResolver cr,Bitmap image, String imageName) 
	{
		
		MediaStore.Images.Media.insertImage(cr, image, imageName, "");
    }
	
	public static  String saveImage(Bitmap image, String imageName) 
	{
		 String extStorageDirectory = Environment.getExternalStorageDirectory().toString()+"/";
	    OutputStream outStream = null;
	    File file = new File(extStorageDirectory, imageName+".png");
	    try {
	       outStream = new FileOutputStream(file);
	       image.compress(Bitmap.CompressFormat.PNG, 100, outStream);
	       outStream.flush();
	       outStream.close();
	    }
	    catch(Exception e)
	    {
	    }
	    return extStorageDirectory+imageName+".png";
    }
	
	public static  Drawable loadImage(String imageName) 
	{
		
	    String extStorageDirectory = Environment.getExternalStorageDirectory().toString()+"/";
	    Drawable result = null;
        try {
            final File file = new File(extStorageDirectory + "/" + imageName+".png");
            if(file.exists()) {
                result = Drawable.createFromPath(file.toString());
            }
        } catch(Exception e) {}
        return result;
    }
	public static  Bitmap loadBitmap(String imageName) 
	{
		
	    String extStorageDirectory = Environment.getExternalStorageDirectory().toString()+"/";
	    Bitmap result = null;
        try {
            final File file = new File(extStorageDirectory + "/" + imageName+".png");
            if(file.exists()) {
            	BitmapFactory.Options op=new BitmapFactory.Options();
                result = BitmapFactory.decodeFile(file.toString(),op);
            }
        } catch(Exception e) {
        	Log.i("","e :"+e);
        }
        return result;
    }
	public static  Bitmap loadBitmap(int resorce) 
	{
		Bitmap result = null;
		InputStream is =ActivityCore.getInstence().getResources().openRawResource(resorce);
	    try {
	    	
	    	result = BitmapFactory.decodeStream(is);
	    
	    } finally {
	        try {
	            is.close();
	         } catch(IOException e) {
	        	Log.i("","e :"+e);
	        }
	    }
        return result;
    }
	
	
	public static  Drawable loadImageFromHardCache(Context context, String image_path) {
        Drawable result = null;
        try {
            final File file = new File(context.getCacheDir().toString() + "/" + md5(image_path));
            if(file.exists()) {
                result = Drawable.createFromPath(file.toString());
            }
        } catch(Exception e) {}
        return result;
    }
	
	public static File saveImageToHardCache(Context context, String image_path, Bitmap image) {
        
        try {
            final File file = new File(context.getCacheDir().toString() + "/" + md5(image_path)); 
            if(file.exists())
                file.delete();
            
            final FileOutputStream fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
           
            fos.close();
            Log.i(TAG, "Saved to hard cache: " + image_path);
            
            return file;
        } catch(Exception e) {return null;}
        
        
    }
	


    private static String md5(String s) {
        try {
            final MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            final byte messageDigest[] = digest.digest();
            
            final StringBuffer hexString = new StringBuffer(); final 
            int length = messageDigest.length;
            for(int i=0; i<length;++i){                
            	hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } catch(Exception e) { 
        	
        }
        return null;
    }

    
    
	
}








