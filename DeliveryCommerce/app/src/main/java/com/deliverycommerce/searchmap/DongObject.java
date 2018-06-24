package com.deliverycommerce.searchmap;





import org.json.JSONException;
import org.json.JSONObject;









public class DongObject extends Object{
	
	public String addr1,addr2,addr3;
	
	public DongObject() 
	{
        addr1="";
        addr2="";
        addr3="";
    }
	public void setData(JSONObject obj)
	{
		try {
            addr1=obj.getString("addr1");
            addr2=obj.getString("addr2");
            addr3=obj.getString("addr3");
		} catch (JSONException e) {

		}

    }

    
}
