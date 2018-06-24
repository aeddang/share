package lib.jsonmanager;





import java.util.HashMap;
import java.util.Map;

import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;






public class DataManager implements DataLoaderDelegate
{
	
	private static DataManager instance;
	
	private static final String TAG = "dataManager";

    
	
	
	
	 static public DataManager getInstance(DataManagerDelegate context) {
	        if(instance == null) {
	        	instance = new DataManager(context);
	        }else{
	        	instance.delegate=context;
	        };
	        return instance;
	    }
	    
	    
	    
	    static public void destoryInstance() {
	        instance.destory();
	        instance = null;
	    }
	
    
    public DataManagerDelegate delegate;
    private Map<String,DataLoader> loaderA;
   
 	public String type;
    
    public DataManager ( DataManagerDelegate context)
	{
		 delegate=context;
		 type="POST";
		 loaderA =new HashMap<String,DataLoader>();
		
		 
	}
   
   
    public void removeAllLoader() { 
		
    	loaderA.clear();
	} 
    
    public void removeLoader (String dataUrl) { 
    	DataLoader loader=loaderA.get(dataUrl);
		if(loader!=null){
			loader.cancel(false);
			loaderA.remove(dataUrl);
		}
	} 
	public void loadData(String dataUrl) { 
		
		removeLoader (dataUrl);
		DataLoader loader=new DataLoader();
		loaderA.put(dataUrl, loader);
		loader.start(this,null,type);
		loader.execute(dataUrl);
		
	} 
    public void loadData(String dataUrl,Map<String,String> param) { 
		
    	
    	removeLoader (dataUrl);
    	DataLoader loader=new DataLoader();
		loaderA.put(dataUrl, loader);
		loader.start(this,param,type);
		loader.execute(dataUrl);
		
	} 
	
	public void destory() { 
		
		
		removeAllLoader();
		delegate=null;
		loaderA=null;
		
	} 
	
	public void onCompleted(String dataUrl,String result) { 
	    if(delegate!=null){
			delegate.onDataCompleted(dataUrl, result);
			removeLoader (dataUrl);
		};
	} 
	public void onLoadErr(String dataUrl) { 
		if(delegate!=null){
			delegate.onDataLoadErr(dataUrl);
			removeLoader (dataUrl);
		};
    } 
	
	
	public  interface DataManagerDelegate {
	    void onDataCompleted(String path,String result);
	    void onDataLoadErr(String path);
	 
	}
	
}


class DataLoader extends AsyncTask<String, Integer, String> 
{
	
	  private static final String TAG = "dataLoader";
	  private String path;
	  private DataLoaderDelegate delegate;
	  private Map<String,String> urlParam;
	  private String type;
	  
	  public void start(DataLoaderDelegate _delegate,Map<String,String> param,String _type) {
		    path = "";
	        delegate = _delegate;
	        urlParam=param;
	        type=_type;
	        Log.i(TAG, "XmlLoader START");
	  }
	
	  protected String doInBackground(String... params) {
		    Log.i(TAG, "BEGIN ParserThread");
		    
		    path = params[0];
		    
		    String responseStr="";
		    
            try{
            	URL text = new URL(  path  );
            	 HttpURLConnection uc = (HttpURLConnection) text.openConnection();
                 
                 uc.setDoInput(true);
                 if(type.equals("POST")){
                	 uc.setDoOutput(true);
                 }
                 uc.setUseCaches(false);
                 uc.setRequestMethod(type);
                 uc.setConnectTimeout(10000);  // д©Ёь╪г е╦юс╬ф©Т
                 uc.setAllowUserInteraction(true);
                
                // String myCookies = "userId=igbrown; sessionId=SID77689211949; isAuthenticated=true";  
                // uc.setRequestProperty("Cookie", myCookies);  
                 // Http Header Setting
                 uc.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                
                
                if(urlParam!=null){
                	StringBuffer sb = new StringBuffer();
                	Object[] keySet= urlParam.keySet().toArray();
                    String key;
                   
                	for(int i=0;i<keySet.length;++i){
                    	 key=(String)keySet[i];
                    	 if(i==(keySet.length-1)){
                    		 sb.append(key).append("=").append(urlParam.get(key));
                    	 }else{
                    		 sb.append(key).append("=").append(urlParam.get(key)).append("&");
                    	 }
                    }
                	
                	DataOutputStream wr = new DataOutputStream( uc.getOutputStream ());     
                	String write=sb.toString();
                	wr.writeBytes (write);      
                	wr.flush ();
                	wr.close ();
                	Log.i(TAG, "dataUrl="+path+"?"+write);
                }else{
                	Log.i(TAG, "dataUrl="+path);
                	
                }
                
                int responseCode = uc.getResponseCode(); 
                if(responseCode == HttpURLConnection.HTTP_OK) {     
                	InputStream  is = uc.getInputStream();    
                	ByteArrayOutputStream baos = new ByteArrayOutputStream();    
                	byte[] byteBuffer = new byte[1024];    
                	byte[] byteData = null;    
                	int nLength = 0;    
                	while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {        
                		baos.write(byteBuffer, 0, nLength);    
                	}    
                	byteData = baos.toByteArray();         
                	responseStr = new String(byteData);         
               
                	
                }
            }catch( Exception e ){
           	    
           	    Log.e(TAG, "Error in parser run", e);
           	    return null;
	              
	        }
            return responseStr;
	    }
	  
	  
			  
	    
	    protected void onPostExecute(String result) {
	       
	        if(result==null){
	        	if(delegate != null) delegate.onLoadErr(path);
	        }else{
	        	if(delegate != null) delegate.onCompleted(path, result);
	        }
	    	super.onPostExecute(result);
	    }
	    
	   

}//package

interface DataLoaderDelegate
{
    void onCompleted(String path,String result);
    void onLoadErr(String path);
 
}





