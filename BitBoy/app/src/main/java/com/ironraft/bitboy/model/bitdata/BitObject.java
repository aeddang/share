package com.ironraft.bitboy.model.bitdata;


import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;


public class BitObject extends Object
{
    public String seq,view,color;
    public Boolean defaultSelected;
    public BitObject() {

        seq="";
        view ="";
        defaultSelected = false;
    }

    public void setData(JSONObject obj){

        try {
            seq = obj.getString("seq");
            view = obj.getString("view");
            color = obj.getString("color");
            defaultSelected = obj.getBoolean("defaultSelected");
        }
        catch (JSONException e)
        {

        }
    }




}






