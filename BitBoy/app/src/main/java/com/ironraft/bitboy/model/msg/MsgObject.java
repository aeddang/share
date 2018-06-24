package com.ironraft.bitboy.model.msg;


import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.model.bitdata.BitDataManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MsgObject extends Object
{
    public Map<String,String> title,notification,msg;
    public String reg_date;

    public MsgObject() {

        title = new HashMap<String, String>();
        notification = new HashMap<String, String>();
        msg = new HashMap<String, String>();
        reg_date = "";
    }

    public String getTitle()
    {
        return title.get(LanguageFactory.getInstence().getLanguageType());
    }
    public String getNotification()
    {
        return notification.get(LanguageFactory.getInstence().getLanguageType());
    }
    public String getMsg()
    {
        return msg.get(LanguageFactory.getInstence().getLanguageType());
    }

    public void setData(JSONObject obj)
    {
        try {

            String jsonStr = obj.getString("value");
            JSONObject value = new JSONObject(jsonStr);
            String title_kor = value.getString("title_kor");
            String notification_kor = value.getString("notification_kor");
            String msg_kor = value.getString("msg_kor");
            String title_eng = value.getString("title_eng");
            String notification_eng = value.getString("notification_eng");
            String msg_eng = value.getString("msg_eng");
            title.put(LanguageFactory.ENG, title_eng);
            title.put(LanguageFactory.KOR, title_kor);
            notification.put(LanguageFactory.ENG, notification_eng);
            notification.put(LanguageFactory.KOR, notification_kor);
            msg.put(LanguageFactory.ENG, msg_eng);
            msg.put(LanguageFactory.KOR, msg_kor);
            reg_date = obj.getString("reg_date");

        }
        catch (JSONException e)
        {

        }
    }
}






