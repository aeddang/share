package com.ironraft.bitboy.model.bitdata;


import org.json.JSONException;
import org.json.JSONObject;


public class BitTickerObject extends Object
{
    public String seq,view;
    public long volume_7day,average_price,opening_price,sell_price,
                    max_price,min_price,units_traded,buy_price,closing_price,volume_1day;
    public boolean isSelected;

    public BitTickerObject(String _seq,String _view)
    {
        seq = _seq;
        view = _view;


        buy_price = 0;
        sell_price = 0;

        opening_price = 0;
        closing_price = 0;

        max_price = 0;
        min_price = 0;

        average_price = 0;
        units_traded = 0;

        volume_1day = 0;
        volume_7day = 0;
        isSelected = BitDataManager.getInstence().getSelected(seq);
    }

    public long getViewPrice()
    {
        return buy_price;
    }


    public String getTitle()
    {

        return seq +"("+view+")";
    }

    public String getDesc()
    {
        String desc = "";
        desc += "buy_price : " + buy_price+"\n";
        desc += "sell_price : " + sell_price+"\n";
        desc += "opening_price : " + opening_price+"\n";
        desc += "closing_price : " + closing_price+"\n";
        desc += "max_price : " + max_price+"\n";
        desc += "min_price : " + min_price+"\n";
        desc += "average_price : " + average_price+"\n";
        desc += "units_traded : " + units_traded+"\n";
        desc += "volume_1day : " + volume_1day+"\n";
        desc += "volume_7day : " + volume_7day+"\n";
        return desc;

    }

    public void setData(JSONObject obj){

        try {

            volume_7day = obj.getLong("volume_7day");
            average_price = obj.getLong("average_price");
            opening_price = obj.getLong("opening_price");
            sell_price = obj.getLong("sell_price");
            max_price = obj.getLong("max_price");
            min_price = obj.getLong("min_price");
            units_traded = obj.getLong("units_traded");
            buy_price = obj.getLong("buy_price");
            closing_price = obj.getLong("closing_price");
            volume_1day = obj.getLong("volume_1day");
        }
        catch (JSONException e)
        {
        }
    }

    public void updateData(BitTickerObject update)
    {
        volume_7day = update.volume_7day;
        average_price = update.average_price;
        opening_price = update.opening_price;
        sell_price = update.sell_price;
        max_price = update.max_price;
        min_price = update.min_price;
        units_traded = update.units_traded;
        buy_price = update.buy_price;
        closing_price = update.closing_price;
        volume_1day = update.volume_1day;

    }



}






