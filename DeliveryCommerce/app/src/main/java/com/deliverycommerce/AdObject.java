package com.deliverycommerce;





import org.json.JSONException;
import org.json.JSONObject;





public class AdObject extends Object{
	
	public String url,img;
	public float point;
	public AdObject() 
	{
		img="";
		url="";
	
    }


    public void setData(JSONObject obj) 
	{
    	
    	try {
    		img=obj.getString("mimg");
    		url=obj.getString("murl");
    		
    		
		} catch (JSONException e) {
			//title="";
		}
		
    }
    
}
