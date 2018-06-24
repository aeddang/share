package lib.xmlmanager;





import java.util.HashMap;
import java.util.Map;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.os.AsyncTask;
import android.util.Log;








public class XmlManager implements XmlLoaderDelegate
{
	
	private static XmlManager instance;
	private static final String TAG = "XmlManager";
   
    
    static public XmlManager getInstance(XmlManagerDelegate context) {
        if(instance == null) {
        	instance = new XmlManager(context);
        }else{
        	instance.delegate=context;
        };
        return instance;
    }
    
    
    
    static public void destoryInstance() {
        instance.destory();
        instance = null;
    }
	
    
    public XmlManagerDelegate delegate;
    private Map<String,XmlInfo>  dataA;
   	private Map<String,XmlLoader> loaderA;
   	public String type;
  
   	
	public XmlManager ( XmlManagerDelegate context)
	{
		 delegate=context;
		 type="POST";
		
		 dataA = new HashMap<String,XmlInfo>();
		 loaderA =new HashMap<String,XmlLoader>();
	}
    public XmlInfo readData (String xmlUrl) { 
    	return dataA.get(xmlUrl);
	} 
    
    public void removeAllData() { 
		
    	dataA.clear();
	} 
    public void removeData (String xmlUrl) { 
		dataA.remove(xmlUrl);
	} 
    public void removeAllLoader() { 
        Object[] allkeys=loaderA.keySet().toArray();;
    	for(int i=0;i<allkeys.length;++i){
    		String key=(String)allkeys[i];
    		loaderA.get(key).cancel(true);
    	}
    	loaderA.clear();
	} 
    
    public void removeLoader (String xmlUrl) { 
    	XmlLoader loader=loaderA.get(xmlUrl);
		if(loader!=null){
			loader.cancel(false);
			loaderA.remove(xmlUrl);
		}
	} 
	public void loadData(String xmlUrl) { 
		
		removeLoader (xmlUrl);
		XmlLoader loader=new XmlLoader();
		loaderA.put(xmlUrl, loader);
		loader.start(this,null,type);
		loader.execute(xmlUrl);
		
	} 
	
    public void loadData(String xmlUrl,Map<String,String> param) { 
		
    	
    	removeLoader (xmlUrl);
		XmlLoader loader=new XmlLoader();
		loaderA.put(xmlUrl, loader);
		loader.start(this,param,type);
		loader.execute(xmlUrl);
		
	}

	
	
	public void destory() { 
		
		removeAllData();
		removeAllLoader();
		delegate=null;
		loaderA=null;
		dataA=null;
	} 
	
	public void onCompleted(String xmlUrl,XmlInfo result) { 
	    if(delegate!=null){
	    	dataA.put(xmlUrl, result);
			delegate.onXMLCompleted(xmlUrl, result);
			removeLoader (xmlUrl);
		};
	} 
	public void onLoadErr(String xmlUrl) { 
		if(delegate!=null){
			delegate.onXMLLoadErr(xmlUrl);
			removeLoader (xmlUrl);
		};
    } 
	public void onParsingErr(String xmlUrl) { 
		if(delegate!=null){
			delegate.onXMLParsingErr(xmlUrl);
			removeLoader (xmlUrl);
		};
		
		
    } 
	
	public  interface XmlManagerDelegate {
	    void onXMLCompleted(String xml_path,XmlInfo result);
	    void onXMLLoadErr(String xml_path);
	    void onXMLParsingErr(String xml_path);
	}
	
}


class XmlLoader extends AsyncTask<String, Integer, XmlInfo> 
{
	
	  private static final String TAG = "XmlLoader";
	  private String xml_path;
	  private XmlLoaderDelegate delegate;
	  private Map<String,String> urlParam;

	  private String type;
	  
	  
	  
	  public void start(XmlLoaderDelegate _delegate,Map<String,String> param,String _type) {
		    xml_path = "";
	        delegate = _delegate;
	        urlParam=param;

	        type=_type;
	        Log.i(TAG, "XmlLoader START");
	  }

	  protected XmlInfo doInBackground(String... params) {
		    Log.i(TAG, "BEGIN ParserThread");
		    XmlInfo result = new XmlInfo();
		    xml_path = params[0];
		    XmlPullParser parser;
		    XmlPullParserFactory parserCreator;
		    int parserEvent;
		    
		    try{
		    	parserCreator = XmlPullParserFactory.newInstance();
        		parser = parserCreator.newPullParser();
        	}catch( Exception e ){
	            Log.e(TAG, "Error in network call", e);
	            return null;
	        }
		    
		    
            try{

            		URL text = new URL(  xml_path  );
                	
                    if(urlParam!=null){
                    	
                    	HttpURLConnection uc = (HttpURLConnection) text.openConnection();
                        uc.setDoInput(true);
                        if(type.equals("POST")){
                       	    uc.setDoOutput(true);
                        }
                        uc.setUseCaches(false);
                
                        uc.setRequestMethod(type);
                        uc.setConnectTimeout(10000);  // Ŀ�ؼ� Ÿ�Ӿƿ�
                        uc.setAllowUserInteraction(true);
                        uc.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                        
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
                    	//write=java.net.URLEncoder.encode(new String(write.getBytes("UTF-8")));
                    	
                    	wr.writeBytes (write);      
                    	wr.flush ();
                    	wr.close ();
                    	parser.setInput(uc.getInputStream() , "utf-8" );
                    	
                    	Log.i(TAG, "xmlUrl="+xml_path+"?"+write);
                    }else{
                    	parser.setInput( text.openStream(), null );
                    	Log.i(TAG, "xmlUrl="+xml_path);
                    	
                    }
                   
    
                    
                    

            	
            	parserEvent=parser.getEventType();
                String tag;
                while (parserEvent != XmlPullParser.END_DOCUMENT){
                	
                	
                	switch(parserEvent){
                       case XmlPullParser.TEXT:
                            tag = parser.getText();
                            result.addValue(tag);
                            break;
                  
                       case XmlPullParser.END_TAG:
                            tag = parser.getName();
                            result.endTag(tag);
                            break;                 
                       case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            Map<String,String> attrs = getAttributes(parser); 
                            result.startTag(tag, attrs);
                            break;
                     }
                     parserEvent = parser.next();
                }
                
            }catch( Exception e ){
            	// if(delegate!=null){delegate.onLoadErr(xml_path);}
            	  Log.e(TAG, "Error in parser run", e);
            	  return null;
	              
	        }
		    return result;
	    }
	  
	  
			  
	    
	    protected void onPostExecute(XmlInfo result) {
	       
	        if(result==null){
	        	if(delegate!=null){delegate.onParsingErr(xml_path);}
	        }else{
	        	if(delegate != null) delegate.onCompleted(xml_path, result);
	        }
	    	super.onPostExecute(result);
	    }
	    
	    private Map<String,String>  getAttributes(XmlPullParser parser){ 
            Map<String,String> attrs=null; 
            int acount=parser.getAttributeCount(); 
            if(acount != -1) { 
               //Log.d(TAG,"Attributes for ["+parser.getName()+"]"); 
                attrs = new HashMap<String,String>(acount); 
                for(int i=0;i<acount;i++) { 
                    attrs.put(parser.getAttributeName(i), parser.getAttributeValue(i)); 
                } 
            } 
            return attrs; 
        } 
	    

}//package

interface XmlLoaderDelegate
{
    void onCompleted(String xml_path,XmlInfo result);
    void onLoadErr(String xml_path);
    void onParsingErr(String xml_path);
}







