package lib.imagemanager;





import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class ImageLoader extends AsyncTask<String, Integer,byte[]>{
	private static final String TAG = "ImageLoader";
	public ImageLoaderDelegate delegate;
	public String image_path;
	public Bitmap image;
	
	public ImageLoader(ImageLoaderDelegate _delegate)
	{
		delegate = _delegate;
	}
	
    public void removeLoader() {
    	 delegate=null;
         this.cancel(true);
         if(image!=null){
        	 image.recycle();
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
    	Bitmap bmp = null;
        //Log.i(TAG, "result="+result);
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(result);
            image = BitmapFactory.decodeStream(bais);
            bais.close();
            bais=null;
        } catch(Exception e) {
        	image=null;
        }
        if(delegate != null) delegate.onImageLoadCompleted(this,image);
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








