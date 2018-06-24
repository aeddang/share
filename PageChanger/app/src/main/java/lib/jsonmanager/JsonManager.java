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



import org.json.JSONObject;




public class JsonManager implements JsonLoaderDelegate
{
	
	private static JsonManager instance;
	
	private static final String TAG = "JsonManager";

    
	
	
	
	 static public JsonManager getInstance(JsonManagerDelegate context) {
	        if(instance == null) {
	        	instance = new JsonManager(context);
	        }else{
	        	instance.delegate=context;
	        };
	        return instance;
	    }
	    
	    
	    
	    static public void destoryInstance() {
	        instance.destory();
	        instance = null;
	    }
	
    
	public JsonManagerDelegate delegate;
    private Map<String,JsonLoader> loaderA;
 	public String type;
    
    public JsonManager ( JsonManagerDelegate context)
	{
		 delegate=context;
		 type="POST";
		 loaderA =new HashMap<String,JsonLoader>();
		 
		 
	}
   
    
    
    
    public void removeAllLoader() { 
		
    	loaderA.clear();
	} 
    
    public void removeLoader (String jsonUrl) { 
    	JsonLoader loader=loaderA.get(jsonUrl);
		if(loader!=null){
			loader.cancel(false);
			loaderA.remove(jsonUrl);
		}
	} 
	public void loadData(String jsonUrl) { 
		
		removeLoader (jsonUrl);
		JsonLoader loader=new JsonLoader();
		loaderA.put(jsonUrl, loader);
		loader.start(this,null,type);
		loader.execute(jsonUrl);
		
	} 
    public void loadData(String jsonUrl,Map<String,String> param) { 
		
    	
    	removeLoader (jsonUrl);
		JsonLoader loader=new JsonLoader();
		loaderA.put(jsonUrl, loader);
		loader.start(this,param,type);
		loader.execute(jsonUrl);
		
	} 
	
	public void destory() { 
		
		
		removeAllLoader();
		delegate=null;
		loaderA=null;
		
	} 
	
	public void onCompleted(String jsonUrl,JSONObject result) { 
	    if(delegate!=null){
			delegate.onJsonCompleted(jsonUrl, result);
			removeLoader (jsonUrl);
		};
	} 
	public void onLoadErr(String jsonUrl) { 
		if(delegate!=null){
			delegate.onJsonLoadErr(jsonUrl);
			removeLoader (jsonUrl);
		};
    } 
	
	
	public  interface JsonManagerDelegate {
	    void onJsonCompleted(String xml_path,JSONObject result);
	    void onJsonLoadErr(String xml_path);
	 
	}
	
}


class JsonLoader extends AsyncTask<String, Integer, JSONObject> 
{
	
	  private static final String TAG = "JsonLoader";
	  private String json_path;
	  private JsonLoaderDelegate delegate;
	  private Map<String,String> urlParam;
	  private String type;
	  
	  public void start(JsonLoaderDelegate _delegate,Map<String,String> param,String _type) {
		    json_path = "";
	        delegate = _delegate;
	        urlParam=param;
	        type=_type;
	        Log.i(TAG, "JsonLoader START");
	  }
	
	  protected JSONObject doInBackground(String... params) {
		    Log.i(TAG, "BEGIN ParserThread");
		    
		    json_path = params[0];
		    
		    JSONObject responseJSON=null;
		    
            try{
            	URL text = new URL(  json_path  );
                
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
                	Log.i(TAG, "jsonUrl="+json_path+"?"+write);
                }else{
                	Log.i(TAG, "jsonUrl="+json_path);
                	
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
                	String response = new String(byteData);         
                	responseJSON = new JSONObject(response);   
                	
                }
            }catch( Exception e ){
           	    //if(delegate!=null){delegate.onLoadErr(json_path);}
           	    Log.e(TAG, "Error in parser run", e);
           	    return null;
	              
	        }
            return responseJSON;
	    }
	  
	  
			  
	    
	    protected void onPostExecute(JSONObject result) {
	       
	        if(result==null){
	        	if(delegate!=null){delegate.onLoadErr(json_path);}
	        }else{
	        	if(delegate != null) delegate.onCompleted(json_path, result);
	        }
	    	super.onPostExecute(result);
	    }
	    
	   

}//package

interface JsonLoaderDelegate
{
    void onCompleted(String json_path,JSONObject result);
    void onLoadErr(String json_path);
 
}





