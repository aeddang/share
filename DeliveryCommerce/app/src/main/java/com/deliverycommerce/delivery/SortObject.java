package com.deliverycommerce.delivery;





import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;








public class SortObject extends Object{
	
	public String code,name;
	
	public SortObject() 
	{
		name="";
		code="";
    }
	public void setOrderData(JSONObject obj) 
	{
		try {
			name=obj.getString("ordername");
		} catch (JSONException e) {
			name="";
		}
		try {
			code=obj.getString("ordercode");
		} catch (JSONException e) {
			code="";
		}
		
		
		
		try {
			name=URLDecoder.decode(name,"euc-kr");
		} catch (UnsupportedEncodingException e) {
			name="";
		}
		if(name.equals("")){
			
			name="";
		}
    }
    public void setData(JSONObject obj) 
	{
		try {
			name=obj.getString("typename");
		} catch (JSONException e) {
			name="";
		}
		try {
			code=obj.getString("typecode");
		} catch (JSONException e) {
			code="";
		}
		
		
		
		try {
			name=URLDecoder.decode(name,"euc-kr");
		} catch (UnsupportedEncodingException e) {
			name="";
		}
		if(name.equals("")){
			
			name="ALL";
		}
    }
    
}
