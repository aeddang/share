package com.deliverycommerce.idel;





import org.json.JSONException;
import org.json.JSONObject;


public class IdelObject extends Object{

	public String store_idx,storename,code,y4comment,y4listprice,y4saleprice,tel,y4discountper,dist,img;
    public Boolean isLoaded;
    public IdelObject()
	{
        store_idx="";
        storename="";
        code="";
        y4comment="";

        y4listprice="";
        y4saleprice="";
        tel="";
        y4discountper="";
        dist="";
        img="";
        isLoaded=false;
    }


    public void setData(JSONObject obj) 
	{


    	try {

            store_idx=obj.getString("store_idx");
            storename=obj.getString("storename");
            y4comment=obj.getString("y4comment");
            y4listprice=obj.getString("y4listprice");
            y4saleprice=obj.getString("y4saleprice");
            tel=obj.getString("tel");
            y4discountper=obj.getString("y4discountper");
            dist=obj.getString("dist");
            img=obj.getString("img");
            isLoaded=true;
		} catch (JSONException  e) {

		}
		
    }
    
}
