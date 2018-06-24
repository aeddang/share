package com.ironraft.bitboy.model.bitdata;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BitSet extends Object
{
    public Map<String,BitTickerObject> sets;
    private String regDate;
    public BitSet()
    {
        sets = new HashMap<String,BitTickerObject>();
    }

    public void setData(JSONObject json)
    {


        JSONObject data = null;
        try
        {
            String jsonStr = json.getString("value");
            JSONObject obj = new JSONObject(jsonStr);
            data = obj.getJSONObject("data");
            regDate = json.getString("reg_date");
        }
        catch (JSONException e)
        {
            return;
        }

        ArrayList<BitObject> settings =  BitDataManager.getInstence().settings;
        for(int i =0;i< settings.size();++i )
        {
            BitObject setting = settings.get(i);
            BitTickerObject set = new BitTickerObject(setting.seq,setting.view);
            try
            {
                set.setData(data.getJSONObject(setting.seq));
            }
            catch (JSONException e)
            {
            }
            sets.put(setting.seq,set);
        }

    }

    public BitTickerObject getBitTicker(String seq)
    {
        return this.sets.get(seq);
    }

    public String getBitDate()
    {
        String[] sA = regDate.split(" ");
        if(sA.length != 2) return "0000-00-00";
        return sA[0];
    }

    public String getBitTime()
    {
        String[] sA =    regDate.split(" ");
        if(sA.length != 2) return "00:00";
        String[] tA = sA[1].split(":");
        if(tA.length != 3) return "00:00";
        return tA[0]+ ":" + tA[1];
    }



}






