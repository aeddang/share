package lib.datamanager;





import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;






public class DataManager implements DataLoaderDelegate
{
	

	
	private static final String TAG = "dataManager";

    private static final String RETURN_TYPE_JSON="JSON";
    private static final String RETURN_TYPE_TXT="TXT";
	
	

    public JsonManagerDelegate jsonDelegate;
    public DataManagerDelegate dataDelegate;
    private Map<String,DataLoader> loaderA;

 	public String type,returnType;

    
    public DataManager (String _type)
	{

		 type=_type;

         loaderA =new HashMap<String,DataLoader>();
		
		 
	}
    public void setOnJsonDelegate (JsonManagerDelegate _jsonDelegate)
    {
        jsonDelegate=_jsonDelegate;
        returnType=RETURN_TYPE_JSON;

    }
    public void setOnDataDelegate (DataManagerDelegate _dataDelegate)
    {
        dataDelegate=_dataDelegate;
        returnType=RETURN_TYPE_TXT;

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
		loader.start(this,null,type,false,null,"",null,"application/x-www-form-urlencoded");
		loader.execute(dataUrl);
		
	} 
    public void loadData(String dataUrl,Map<String,String> param) {
		
    	
    	removeLoader (dataUrl);
    	DataLoader loader=new DataLoader();
		loaderA.put(dataUrl, loader);
		loader.start(this,param,type,false,null,"",null,"application/x-www-form-urlencoded");
		loader.execute(dataUrl);
		
	}
    public void loadData(String dataUrl,Map<String,String> param,ArrayList<FileObject> files) {


        removeLoader (dataUrl);
        DataLoader loader=new DataLoader();
        loaderA.put(dataUrl, loader);
        loader.start(this,param,"POST",true,files,"",null,"multipart/form-data");
        loader.execute(dataUrl);

    }
    public void loadData(String dataUrl,Map<String,String> param,ArrayList<FileObject> files,String boundary) {


        removeLoader (dataUrl);
        DataLoader loader=new DataLoader();
        loaderA.put(dataUrl, loader);
        loader.start(this, param, "POST", true, files, boundary,null,"multipart/form-data");
        loader.execute(dataUrl);

    }
    public void loadData(String dataUrl,Map<String,String> param,ArrayList<FileObject> files,String boundary,Map<String,String> headers) {


        removeLoader (dataUrl);
        DataLoader loader=new DataLoader();
        loaderA.put(dataUrl, loader);
        loader.start(this, param, "POST", true, files, boundary, headers,"multipart/form-data");
        loader.execute(dataUrl);

    }
    public void loadData(String dataUrl,Map<String,String> param,ArrayList<FileObject> files,String boundary,Map<String,String> headers,String contentType) {


        removeLoader (dataUrl);
        DataLoader loader=new DataLoader();
        loaderA.put(dataUrl, loader);
        loader.start(this, param, "POST", true, files, boundary, headers,contentType);
        loader.execute(dataUrl);

    }
    public void destory() {
		
		
		removeAllLoader();
        dataDelegate=null;
        jsonDelegate=null;
		loaderA=null;
		
	} 
	
	public void onCompleted(String dataUrl,String result) {
        removeLoader (dataUrl);
        if(dataDelegate!=null){
            dataDelegate.onDataCompleted(this,dataUrl, result);

		};
        if(jsonDelegate!=null){

            try {

                Log.i(TAG, "result : "+result);
                JSONObject responseJSON = new JSONObject(result);
                jsonDelegate.onJsonCompleted(this, dataUrl, responseJSON);
            } catch (JSONException e) {
                Log.e(TAG, "Error in responseJSON parser ", e);
                jsonDelegate.onJsonParseErr(this, dataUrl);

            }


        };

	} 
	public void onLoadErr(String dataUrl) {
        removeLoader (dataUrl);
		if(dataDelegate!=null){
            dataDelegate.onDataLoadErr(this,dataUrl);

		};
        if(jsonDelegate!=null){
            jsonDelegate.onJsonLoadErr(this, dataUrl);

        };

    } 
	
	
	public  interface DataManagerDelegate {
	    void onDataCompleted(DataManager manager,String path,String result);
	    void onDataLoadErr(DataManager manager,String path);

	}
    public  interface JsonManagerDelegate {
        void onJsonCompleted(DataManager manager,String path,JSONObject result);
        void onJsonParseErr(DataManager manager,String path);
        void onJsonLoadErr(DataManager manager,String path);

    }
}


class DataLoader extends AsyncTask<String, Integer, String> 
{
	
	  private static final String TAG = "dataLoader";
	  private String path,boundary = "^******^";;
	  private DataLoaderDelegate delegate;
	  private Map<String,String> urlParam,headers;
      private ArrayList<FileObject> files;
	  private String type,contentType;
      private boolean isMultipart;


	  public void start(DataLoaderDelegate _delegate,Map<String,String> param,String _type,
                        boolean _isMultipart,ArrayList<FileObject> _files,String _boundary,
                        Map<String,String> _headers,String _contentType) {
		    path = "";
	        delegate = _delegate;
	        urlParam=param;
            contentType=_contentType;
            headers=_headers;
            if(_boundary.equals("")==false) {
                boundary=_boundary;
            }
            files=_files;
            isMultipart=_isMultipart;
	        type=_type;
	        Log.i(TAG, "DataLoader START "+type+" mp:"+isMultipart);
	  }



	  protected String doInBackground(String... params) {
		    Log.i(TAG, "BEGIN ParserThread");
		    
		    path = params[0];
            HttpURLConnection uc=null;
            InputStream is=null;
		    String responseStr="";
		    
            try {

                    URL text = new URL(path);
                    uc = (HttpURLConnection) text.openConnection();

                    uc.setDoInput(true);
                    if (type.equals("POST")||isMultipart==true) {
                        uc.setDoOutput(true);
                    }
                    uc.setUseCaches(false);
                    uc.setRequestMethod(type);
                    uc.setConnectTimeout(10000);  //
                    uc.setAllowUserInteraction(true);

                    if(headers!=null){
                        Object[] keySet = headers.keySet().toArray();
                        String key;

                        for (int i = 0; i < keySet.length; ++i) {
                            key = (String) keySet[i];
                            uc.setRequestProperty(key, headers.get(key));
                            Log.i(TAG, "header = " + key + ":" + headers.get(key));
                        }

                    }


                    if(isMultipart==true){


                        String delimiter = "\r\n--" + boundary + "\r\n";
                        uc.setRequestProperty("Connection", "Keep-Alive");
                        uc.setRequestProperty("Content-Type", contentType+" ;boundary="
                                + boundary);


                        DataOutputStream wr = new DataOutputStream(new BufferedOutputStream(
                                uc.getOutputStream()));
                        if (urlParam != null) {


                            String write = DataLoaderUtil.getParamsString(urlParam, delimiter);
                            wr.writeUTF(write);

                            Log.i(TAG, "dataUrl=" + path + "?" + write);
                        } else {
                            Log.i(TAG, "dataUrl=" + path);

                        }

                        if(files!=null){
                            for(int i=0;i<files.size();++i){

                                Log.i(TAG, "file =" + files.get(i).key+" : "+files.get(i).name);
                                DataLoaderUtil.setFileBuffer(files.get(i), wr, delimiter);

                            }

                        }
                        wr.writeBytes(delimiter);
                        wr.flush();
                        wr.close();


                    }else{

                        uc.setRequestProperty("Content-type", contentType);

                        if (urlParam != null) {
                            StringBuffer sb = new StringBuffer();
                            Object[] keySet = urlParam.keySet().toArray();
                            String key;

                            for (int i = 0; i < keySet.length; ++i) {
                                key = (String) keySet[i];
                                if (i == (keySet.length - 1)) {
                                    sb.append(key).append("=").append(urlParam.get(key));
                                } else {
                                    sb.append(key).append("=").append(urlParam.get(key)).append("&");
                                }
                            }

                            DataOutputStream wr = new DataOutputStream(uc.getOutputStream());
                            String write = sb.toString();
                            wr.writeBytes(write);
                            wr.flush();
                            wr.close();
                            Log.i(TAG, "dataUrl=" + path + "?" + write);
                        } else {
                            Log.i(TAG, "dataUrl=" + path);

                        }
                    }





                    int responseCode = uc.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        is = uc.getInputStream();
                        //uc.disconnect();

                    }

            }catch(Exception e){

                 Log.e(TAG, "Error in parser run", e);
                 return null;

            }
            if(is!=null){

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                try {
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error in InputStream read ", e);
                    return null;
                }
                byteData = baos.toByteArray();
                responseStr = new String(byteData);
                //Log.i(TAG, "response=" + responseStr);



            }
            if(uc!=null){
                uc.disconnect();
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





