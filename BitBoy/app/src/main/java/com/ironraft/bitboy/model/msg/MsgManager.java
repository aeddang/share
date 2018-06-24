package com.ironraft.bitboy.model.msg;


import android.widget.Toast;

import com.ironraft.bitboy.Config;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.APIManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import lib.datamanager.DataManager;


public class MsgManager extends Object implements DataManager.JsonManagerDelegate
{
    private static MsgManager instence;
    public static MsgManager getInstence()
    {
        if(instence == null){

            instence = new MsgManager();
        }
        return instence;
    }

    private DataManager jsonManager;
    private MsgDataDelegate msgDelegate;

    public MsgManager() {

        instence=this;
        jsonManager=new DataManager("GET");
        jsonManager.setOnJsonDelegate(this);

    }
    public  void removeInfo()
    {
        if(jsonManager != null)
        {
            jsonManager.destory();
            jsonManager=null;
        }
    }
    public void unloadMsg()
    {
        msgDelegate = null;
        jsonManager.removeLoader(Config.API_LOAD_MSG);

    }
    public void loadMsgs(MsgDataDelegate _msgDelegate)
    {
        msgDelegate = _msgDelegate;
        jsonManager.loadData(Config.API_LOAD_MSG);

    }
    private void onLoadMsgs(JSONArray datas)
    {
        ArrayList<MsgObject> msgs = new ArrayList<MsgObject>();
        for(int i=0;i<datas.length();++i) {
            MsgObject msg = new MsgObject();
            try {
                msg.setData(datas.getJSONObject(i));
                msgs.add(msg);
            } catch (JSONException e)
            {
            }
        }
        if(msgDelegate != null) msgDelegate.onLoadMsg(msgs);
        msgDelegate = null;
    }


    public void onJsonCompleted(DataManager manager, String path, JSONObject result) {
        Boolean isAble = APIManager.getInstence().getResultAble(result, path);
        if (isAble == false)
        {
            this.resultError(path);
            return;
        }
        switch(path)
        {
            case Config.API_LOAD_MSG:
                resultLists(path,result);
                break;
            default:
                resultObj(path,result);
                break;

        }

    }
    private void resultLists(String path,JSONObject result)
    {
        JSONArray datas = APIManager.getInstence().getResultLists(result,path);
        if(datas == null)
        {
            this.resultError(path);
            return;
        }
        switch(path)
        {
            case Config.API_LOAD_MSG:
                this.onLoadMsgs(datas);
                break;
            default:
                break;
        }
    }

    private void resultObj(String path,JSONObject result)
    {
        JSONObject data = APIManager.getInstence().getResult(result,path);
        if(data == null)
        {
            this.resultError(path);
            return;
        }
    }

    private void resultError(String path)
    {
        switch(path)
        {
            case Config.API_LOAD_MSG:
                if(msgDelegate != null) msgDelegate.onLoadMsgError();
                msgDelegate = null;
                break;

        }
    }
    public void onJsonParseErr(DataManager manager, String path)
    {
        int msgID = LanguageFactory.getInstence().getResorceID("msg_no_data");
        Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
        this.resultError(path);
    }
    public void onJsonLoadErr(DataManager manager, String path)
    {
        int msgID = LanguageFactory.getInstence().getResorceID("msg_network_err");
        Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
        this.resultError(path);
    }


    public interface MsgDataDelegate
    {
        void onLoadMsg(ArrayList<MsgObject> msgs);
        void onLoadMsgError();
    }
}






