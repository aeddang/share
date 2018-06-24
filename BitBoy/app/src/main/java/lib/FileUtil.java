package lib;

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
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

import lib.core.ActivityCore;
import lib.gifencoder.AnimatedGifEncoder;


public class FileUtil
{
	private static final String TAG = "FileUtil";




	public static Bitmap getBitmapFromUri(Activity context, Uri uri,float returnSize) throws FileNotFoundException, IOException{
        InputStream input = context.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig= Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > returnSize) ? (originalSize / returnSize) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig= Config.ARGB_8888;//optional
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

	public static  void saveImageForAlbum(Bitmap image, String imageName, String imagePath)
	{
        ContentResolver cr=ActivityCore.getInstence().getContentResolver();
		MediaStore.Images.Media.insertImage(cr, image, imageName, imagePath);

    }
    public static byte[] generateGIF(ArrayList<Bitmap> bitmaps) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(bos);
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }
        encoder.finish();
        return bos.toByteArray();
    }
    public static  String saveGifImage(ArrayList<Bitmap> bitmaps, String imageName,String imagePath)
    {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        if(imagePath.equals("")==false){
            File f = new File(Environment.getExternalStorageDirectory(),imagePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            extStorageDirectory = extStorageDirectory+"/"+imagePath;
        }
        FileOutputStream outStream = null;
        File file = new File(extStorageDirectory, imageName+".gif");
        try{
            outStream = new FileOutputStream(file);
            outStream.write(generateGIF(bitmaps));
            outStream.close();
        }catch(Exception e) {
        }
        return extStorageDirectory+imageName+".gif";
    }
    public static  String saveGifImage(byte[] bitmaps, String imageName,String imagePath)
    {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        if(imagePath.equals("")==false){
            File f = new File(Environment.getExternalStorageDirectory(),imagePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            extStorageDirectory = extStorageDirectory+"/"+imagePath+"/";
        }
        FileOutputStream outStream = null;
        File file = new File(extStorageDirectory, imageName+".gif");
        try{
            outStream = new FileOutputStream(file);
            outStream.write(bitmaps);
            outStream.close();
        }catch(Exception e) {
        }
        return extStorageDirectory+imageName+".gif";
    }
    public static  void saveImageForAlbum(ContentResolver cr,Bitmap image, String imageName)
    {

        MediaStore.Images.Media.insertImage(cr, image, imageName, "");
    }

    public static ArrayList<File> readFiles(String imagePath)
    {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString()+"/";
        File dir = new File(Environment.getExternalStorageDirectory(),imagePath);
        ArrayList<File> findFiles = new ArrayList<File>();
        if (!dir.exists())
        {
            return findFiles;
        }
        File[] files = dir.listFiles();

        if ( files != null ) {
            for ( File file : files ) {
                if ( file != null ) {
                    if (!file.isDirectory() ) {  // it is a folder...

                        findFiles.add(file);
                    }
                }
            }
        }

        return findFiles;
    }

    public static  Boolean deleteFile(String path)
    {
        File file = new File(path);
        boolean deleted = file.delete();
        return deleted;
    }



    public static  String saveImage(Bitmap image, String imageName ,String imagePath,String extension)
    {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString()+"/";
        if(imagePath.equals("")==false){
            File f = new File(Environment.getExternalStorageDirectory(),imagePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            extStorageDirectory = extStorageDirectory+imagePath+"/";
        }

        OutputStream outStream = null;
        File file = new File(extStorageDirectory, imageName+"."+extension);
        Log.i("SAVE","extStorageDirectory : "+extStorageDirectory);
        Log.i("SAVE","imageName : "+imageName);
        try {
            outStream = new FileOutputStream(file);
            if(extension.toUpperCase().equals("PNG")==true)
            {
                image.compress(CompressFormat.PNG, 100, outStream);
            }else
            {
                image.compress(CompressFormat.JPEG, 100, outStream);
            }

            outStream.flush();
            outStream.close();
            Log.i("SAVE","SAVE IMAGE SUCCESS");
        }
        catch(Exception e)
        {
            Log.i("SAVE","SAVE IMAGE FAIL"+e.toString());
        }


        return extStorageDirectory+imageName+"."+extension;
    }


	public static  String saveImage(Bitmap image, String imageName,String extension)
	{
		 return saveImage(image,imageName,"",extension);
    }



    public static  Bitmap loadImage(String imageName,String imagePath)
    {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString()+"/"+imagePath;
        return loadImageByFullPath(imageName,extStorageDirectory);
    }
    public static  Bitmap loadImage(String imageName)
    {
       return loadImage(imageName,"");
    }

    public static  Bitmap loadImageByFullPath(String imageName,String imagePath)
    {

        Bitmap result = null;

        try {
            final File file = new File(imagePath,imageName);
            if(file.exists()) {
                BitmapFactory.Options op=new BitmapFactory.Options();
                result = BitmapFactory.decodeFile(file.toString(),op);
            }
        } catch(Exception e) {
            Log.i("","e :"+e);
        }
        return result;
    }

	public static  Bitmap loadImage(int resorce)
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

	public static  Bitmap loadImageFromHardCache(Context context, String image_path) {
        /*
        Drawable result = null;
        try {
            final File file = new File(context.getCacheDir().toString() + "/" + md5(image_path));
            if(file.exists()) {
                result = Drawable.createFromPath(file.toString());
            }
        } catch(Exception e) {}
        return result;
        */
        return loadImageByFullPath(context.getCacheDir().toString() + "/" + md5(image_path),"");
    }

	public static File saveImageToHardCache(Context context, String image_path, Bitmap image) {

        try {
            final File file = new File(context.getCacheDir().toString() + "/" + md5(image_path));
            if(file.exists())
                file.delete();

            final FileOutputStream fos = new FileOutputStream(file);
            image.compress(CompressFormat.PNG, 100, fos);

            fos.close();
            Log.i(TAG, "Saved to hard cache: " + image_path);

            return file;
        } catch(Exception e) {return null;}


    }



    private static String md5(String s) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
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








