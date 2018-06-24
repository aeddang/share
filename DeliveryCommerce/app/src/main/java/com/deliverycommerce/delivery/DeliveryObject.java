package com.deliverycommerce.delivery;





import org.json.JSONException;
import org.json.JSONObject;





public class DeliveryObject extends Object{
	
	public String store_idx,storename,tel,deli_time,dist,isfav,img,maddr1,maddr2,maddr3,work_time,deli_comment,discount;
	public float point;
	public DeliveryObject() 
	{
		store_idx="";
		storename="";
		tel="";
		deli_time="";
		dist="";
		isfav="";
		img="";
		maddr1="";
		maddr2="";
		maddr3="";
		point=0;
		
		work_time="" ;
        deli_comment=""; 
		discount=""; 

    }


    public void setData(JSONObject obj) 
	{
    	
    	try {
    		store_idx=obj.getString("store_idx");
    		storename=obj.getString("storename");
    		tel=obj.getString("tel");
    		deli_time=obj.getString("deli_time");
    		dist=obj.getString("dist");
    		isfav=obj.getString("isfav");
    		img=obj.getString("img");
    		maddr1=obj.getString("maddr1");
    		maddr2=obj.getString("maddr2");
    		maddr3=obj.getString("maddr3");
    		work_time=obj.getString("work_time");;
            deli_comment=obj.getString("deli_comment"); 
    		discount=obj.getString("discount");
    		
    		
    	    String star	=obj.getString("star");
    		if(star.equals("")){
    			point=0;
    		}else{
    			point=Float.parseFloat(star);
    			point=point/2.f;
    		}
    		
		} catch (JSONException e) {
			//title="";
		}
		
    }
    
}
