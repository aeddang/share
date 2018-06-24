package com.ironraft.bitboy.model;


import android.util.Log;
import android.widget.Toast;

import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class APIManager{

    private static APIManager instence;
    public static APIManager getInstence()
    {
        if(instence == null){

            instence = new APIManager();
        }
        return instence;
	}
    public APIManager()
    {

    }
    public String getDefaultData(String value, String defaultValue)
    {
        if(value == null)
        {
            return defaultValue;
        }
        if(value == "")
        {
            return defaultValue;
        }
        if(value == "null")
        {
            return defaultValue;
        }
        return  value;
    }
    public Boolean getResultAble(JSONObject result, String path)
    {
        String resultCode = "";
        String msg = "";
        try {
            resultCode = result.getString("resultCode");
            msg = result.getString("msg");
        } catch (JSONException e) {

        }
        if(resultCode.equals("")==true)
        {
            Log.i("", "error path :" + path);
            int msgID = LanguageFactory.getInstence().getResorceID("msg_server_err");
            Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
            return false;

        }
        if(resultCode.equals("0") == false)
        {
            if(resultCode.equals("1") == true)
            {
                if(msg.equals("")==true){

                }else
                {
                    Toast.makeText(MainActivity.getInstence(),msg,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                int msgID = LanguageFactory.getInstence().getResorceID("msg_server_err");
                Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
            }
            Log.i("", "error path :" + path);
            return false;
        }
        return true;

    }


    public JSONObject getResult(JSONObject result, String path)
    {
        JSONObject data = null;
        try {
            data = result.getJSONObject("value");
        } catch (JSONException e) {
            Log.i("", "error path :" + path);

        }
        if(data == null)
        {
            int msgID = LanguageFactory.getInstence().getResorceID("msg_no_data");
            Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
        }
        return data;

    }
    public JSONArray getResultLists(JSONObject result, String path)
    {
        JSONArray datas = null;
        try {
            datas = result.getJSONArray("value");
        } catch (JSONException e) {
            Log.i("", "error path :" + path);

        }
        if(datas == null)
        {
            int msgID = LanguageFactory.getInstence().getResorceID("msg_no_data");
            Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
        }
        return datas;

    }
}

