package com.aeddang.clipmaker;





import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lib.GraphicUtil;
import lib.imagemanager.ImageLoader;


public class GroupObject extends Object {

	public String title;
    public ArrayList<DataObject> dataA;

    public GroupObject()
	{
        title="";
        dataA=new ArrayList<DataObject>();

    }

    public void setData(JSONObject obj,String gkey)
    {
        try {
            JSONArray datas = obj.getJSONArray("datas");
            title = obj.getString("title");
            JSONObject data=null;
            DataObject dataObj=null;
            for(int i=0;i<datas.length();++i){
                data = datas.getJSONObject(i);
                dataObj = new DataObject();
                dataObj.setData(data,gkey);
                if(dataObj.key.equals("")==false) {
                    dataA.add(dataObj);
                }
            }




        } catch (JSONException e) {


        }

    }

    
}
