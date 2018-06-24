package lib.imagemanager;





import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import lib.GraphicUtil;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;



public class ImageLoader extends AsyncTask<String, Integer,byte[]>{
	public static int MAX_CASH_NUM=50;
	
	private static  ArrayList<String> imgPaths=new ArrayList<String>();
	private static  ArrayList<Bitmap> imgCashs=new ArrayList<Bitmap>();
	
	private static final String TAG = "ImageLoader";
	public ImageLoaderDelegate delegate;
	public String image_path;
	public Bitmap image;
	public Point size;
	

	
	public static void removeAllCashs() {
		
		
		Bitmap btm;
		for(int i=0;i<imgCashs.size();++i){
			btm=imgCashs.get(i);
			if(btm!=null){
				btm.recycle();
				
			}
		}
		imgPaths.clear();
		imgCashs.clear();
		
		
	}
	public static void clearMemory(){
		
		
		Bitmap btm;
		int size=imgPaths.size();
		if(size>MAX_CASH_NUM){
			size=size-MAX_CASH_NUM;
			
		}else{
			return;
		}
		
		for(int i=size-1;i>=0;--i){
		
			btm=imgCashs.get(i);
			if(btm!=null){
				btm.recycle();
				
			}
			imgPaths.remove(i);
			imgCashs.remove(i);
		}
		
		
	}
	public ImageLoader(ImageLoaderDelegate _delegate)
	{
		delegate = _delegate;
		
	}
	
    public void removeLoader() {
    	 delegate=null;
    	 image=null;
    	
         this.cancel(true);
         
    }
    public void loadImg(String imgPath) {
    	clearMemory();
    	image_path=imgPath;
    	int imageIdx=imgPaths.indexOf(imgPath);
    	//Log.i("","cash size :"+imgCashs.size());
    	if(imageIdx!=-1){
    		image=imgCashs.get(imageIdx);
    		Bitmap copyBitmap = image.copy(Bitmap.Config.ARGB_8888,true); 
    		if(delegate != null) delegate.onImageLoadCompleted(this,copyBitmap);
    	}else{
    		this.execute(imgPath);
    		
    	}
   }
  
    protected byte[] doInBackground(String... params) {
        
    	image_path=params[0];
    	Log.i(TAG, "image_path = "+image_path);
    	
    	
    	
    	
    	byte[] result = null;
        final HttpURLConnection conn = makeConnection(image_path, "GET");
        if(conn == null){
        	Log.d(TAG, "conn null");
        	return null;
        } 
        try {
            conn.connect();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final InputStream is = conn.getInputStream();
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                
                int size = 0;
                byte[] buff = new byte[2048];
                
                while((size = is.read(buff)) != -1){
                	
                    os.write(buff, 0, size);
                    
                }
                result = os.toByteArray();
                is.close();
                os.close();
            }else{
            	Log.i(TAG, "conn.getResponseCode="+conn.getResponseCode());
            }
            conn.disconnect();
        } catch(Exception e) {
        	Log.e(TAG, "conn err",e);
        }
        if(result==null){
        	Log.d(TAG, "result null");
        }else{
        	Log.d(TAG, "result ok");
        	
        }
        
        return result;
    }
    

    protected void onPostExecute(byte[] result) {
    	
        //Log.i(TAG, "result="+result);
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(result);
            image = BitmapFactory.decodeStream(bais);
            if(size!=null){
            	int w;
            	int h;
            	if(size.x==-1 && size.y!=-1){
            		h=size.y;
            		w=(int)Math.floor((float)size.y*(float)image.getWidth()/(float)image.getHeight());
            		
            	}else if(size.y==-1 && size.x!=-1){
            		w=size.x;
            		h=(int)Math.floor((float)size.x*(float)image.getHeight()/(float)image.getWidth());
            		
            	}else{
            		w=size.x;
            		h=size.y;
            		
            	}	
            	image=GraphicUtil.resizeImage(image, w,h);
            	
            }
            
            bais.close();
            bais=null;
            imgPaths.add(image_path);
            imgCashs.add(image);
            
        } catch(Exception e) {
        	image=null;
        }
       
        
        if(delegate != null) {
        	Bitmap copyBitmap=null;
        	try {
        		copyBitmap = image.copy(Bitmap.Config.ARGB_8888,true); 
        	}catch(Exception e) {
        		copyBitmap=null;
            }
        	delegate.onImageLoadCompleted(this,copyBitmap);
        	
        }
    }
    
    final private HttpURLConnection makeConnection(String uri, String method) {
        try {
            final URL url = new URL(uri);
            HttpURLConnection conn = null;
            if(url.getProtocol().toLowerCase().equals("https")) {
                final HttpsURLConnection https = (HttpsURLConnection)url.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            } else {
                conn = (HttpURLConnection)url.openConnection();
            }
            conn.setAllowUserInteraction(true);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; i-NavFourF; .NET CLR 1.1.4322)");
            conn.setUseCaches(true);
            return conn;
        } catch(Exception e) {
        	Log.e(TAG, "makeConnection err",e);
            return null;
        }
    }
    
    final static private HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() 
    {
        public boolean verify(String hostname, SSLSession session) {
                return true;
        }
    };
    
    public interface ImageLoaderDelegate {
        void onImageLoadCompleted(ImageLoader loader,Bitmap image);
    }
    
}








