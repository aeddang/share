package com.ironraft.bitboy.model.bitdata;



import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import com.ironraft.bitboy.Config;
import com.ironraft.bitboy.LanguageFactory;
import com.ironraft.bitboy.MainActivity;
import com.ironraft.bitboy.R;
import com.ironraft.bitboy.model.APIManager;
import com.ironraft.bitboy.model.TopicManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lib.CustomTimer;
import lib.datamanager.DataManager;
import lib.observers.ObserverController;


public class BitDataManager extends Object implements DataManager.JsonManagerDelegate,  CustomTimer.TimerDelegate
{

    public static final String NotificatioBitDataSetupComplete = " NotificatioBitDataSetupComplete";
    public static final String NotificatioBitDataLoadComplete = "NotificatioBitDataLoadComplete";
    public static final String NotificatioBitDataLoadError = "NotificatioBitDataLoadError";
    public static final String NotificatioBitDataSetupError = "NotificatioBitDataSetupError";

    private final String SELECTED_KEY="SELECTED_KEY";
    private final String AUTO_LOAD_KEY="AUTO_LOAD_KEY";
    private final int AUTO_LOAD_TIME = 1000 * 10;

    private static BitDataManager instence;
    public static BitDataManager getInstence()
    {
        return instence;
    }

    private DataManager jsonManager;
    public ArrayList<BitObject> settings;
    private ArrayList<BitSet> sets;

    private SharedPreferences shared;
    private boolean isAutoLoad;
    private CustomTimer timer;

    public BitDataManager() {

        instence=this;
        jsonManager=new DataManager("GET");
        jsonManager.setOnJsonDelegate(this);
        shared = MainActivity.getInstence().getSharedPreferences(Config.PREFS_NAME, 0);
        isAutoLoad = getAutoLoad();
        autoLoadInit();
    }
    public  void removeInfo()
    {
        removeTimer();
        if(jsonManager != null)
        {
            jsonManager.destory();
            jsonManager=null;
        }
    }


    public boolean getAutoLoad() {

        return shared.getBoolean(AUTO_LOAD_KEY,true);
    }
    public void setAutoLoad(boolean _isAutoLoad)
    {
        isAutoLoad = _isAutoLoad;
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(AUTO_LOAD_KEY,isAutoLoad);
        editor.commit();
        autoLoadInit();
    }
    private void autoLoadInit()
    {
        if(isAutoLoad == true)
        {
            autoLoadStart();
        }
        else
        {
            autoLoadStop();
        }
    }
    private void autoLoadStart()
    {
        removeTimer();
        timer = new CustomTimer(AUTO_LOAD_TIME, -1, this);
        timer.timerStart();
    }

    private void autoLoadStop()
    {
        removeTimer();
    }


    public boolean getSelected(String key) {

        return shared.getBoolean(SELECTED_KEY+key,false);
    }
    public void setSelect(String key,boolean isSelected)
    {
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(SELECTED_KEY+key,isSelected);
        editor.commit();
        TopicManager.getInstence().updateCoin(key,isSelected);
    }

    public ArrayList<BitSet> getAllBitSets()
    {
        return this.sets;
    }
    public BitSet getCurrentBitSet()
    {
        if(this.sets == null) return null;
        if(this.sets.size()<=0) return null;
        return this.sets.get(0);
    }

    public ArrayList<BitTickerObject> getCurrentBitLists(boolean isSelected)
    {
        ArrayList<BitTickerObject> lists = new ArrayList<BitTickerObject>();
        BitSet set = this.getCurrentBitSet();
        if(set == null) return lists;
        for(int i =0; i<settings.size();++i)
        {
            BitTickerObject data = set.getBitTicker(settings.get(i).seq);
            if(isSelected== true && data.isSelected==true)
            {
                lists.add(data);
            }
            else if(isSelected==false)
            {
                lists.add(data);
            }

        }
        return lists;
    }

    public ArrayList<BitGraphInfo> getBitTickerGraphs(String seq)
    {
        ArrayList<BitGraphInfo> graphs = new ArrayList<BitGraphInfo>();
        if(this.settings == null) return graphs;

        for(int i=0;i<settings.size();++i) {
            BitObject set = settings.get(i);
            if(set.seq == seq)
            {
                BitGraphInfo ginfo =  getBitTickerGraph(set.seq);
                ginfo.setting = set;
                graphs.add(ginfo);
            }
        }
        return graphs;
    }

    public ArrayList<BitGraphInfo> getBitTickerGraphs(boolean isOnlySelected)
    {
        ArrayList<BitGraphInfo> graphs = new ArrayList<BitGraphInfo>();
        if(this.settings == null) return graphs;

        for(int i=0;i<settings.size();++i) {
            BitObject set = settings.get(i);
            if((isOnlySelected == true && getSelected(set.seq)) || isOnlySelected == false)
            {
                BitGraphInfo ginfo =  getBitTickerGraph(set.seq);
                ginfo.setting = set;
                graphs.add(ginfo);
            }
        }
        return graphs;
    }

    public BitGraphInfo getBitTickerGraph(String seq)
    {
        BitGraphInfo ginfo = new BitGraphInfo();
        if(this.sets == null) return ginfo;
        int len = this.sets.size()-1;
        for(int i=this.sets.size()-1 ;i>=0;--i)
        {
            BitSet set = this.sets.get(i);
            BitGraphObject graph = new BitGraphObject();
            BitTickerObject ticker = set.getBitTicker(seq);

            graph.point = (ticker != null) ? ticker.getViewPrice() : 0;
            ginfo.graphs.add(graph);
            if(graph.point >= ginfo.top)
            {
                ginfo.top = graph.point;
                ginfo.topIdx = len - i;
            }
            if(graph.point <= ginfo.bottom)
            {
                ginfo.bottom = graph.point;
                ginfo.bottomIdx = len - i;
            }
        }
        return ginfo;
    }

    public void loadBitSetup()
    {
        if(this.sets != null)
        {
            ObserverController.shareInstence().notifyPost(BitDataManager.NotificatioBitDataSetupComplete,this.settings,null);
        }
        jsonManager.loadData(Config.API_LOAD_BIT_SETUP);

    }
    private void onLoadBitSetup(JSONArray datas)
    {
        settings = new ArrayList<BitObject>();
        ArrayList<BitObject> defaultSettings = new ArrayList<BitObject>();
        for(int i=0;i<datas.length();++i) {
            BitObject bit = new BitObject();
            try {
                bit.setData(datas.getJSONObject(i));
                if( bit.seq.equals("")==false)
                {
                    settings.add(bit);
                }
                if(bit.defaultSelected==true)
                {
                    defaultSettings.add(bit);
                }
            } catch (JSONException e)
            {
            }
        }
        if(getCurrentBitLists(true).size()<1)
        {
            for(int i=0;i<defaultSettings.size();++i) {
                setSelect(defaultSettings.get(i).seq, true);
            }
        }

        ObserverController.shareInstence().notifyPost(BitDataManager.NotificatioBitDataSetupComplete,this.settings,null);
    }
    public void loadBitDatas()
    {
       jsonManager.loadData(Config.API_LOAD_BIT_DATAS);
    }
    private void onLoadBitDatas(JSONArray datas)
    {
        sets = new ArrayList<BitSet>();

        for(int i=0;i<datas.length();++i) {
            BitSet set = new BitSet();
            try {
                set.setData(datas.getJSONObject(i));
                sets.add(set);
            } catch (JSONException e)
            {
                Log.i("onLoadBitDatas",e.toString());
            }
        }
        ObserverController.shareInstence().notifyPost(BitDataManager.NotificatioBitDataLoadComplete,this.settings,null);
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
            case Config.API_LOAD_BIT_DATAS:
            case Config.API_LOAD_BIT_SETUP:
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
            case Config.API_LOAD_BIT_DATAS:
                this.onLoadBitDatas(datas);
                break;
            case Config.API_LOAD_BIT_SETUP:
                this.onLoadBitSetup(datas);
                break;
            default:
                break;
        }
    }

    private void resultObj(String path,JSONObject result)
    {
        JSONObject data = APIManager.getInstence().getResult(result,path);
        if(data == null){
            this.resultError(path);
        }
    }
    public void onJsonParseErr(DataManager manager, String path)
    {
        this.resultError(path);
        int msgID = LanguageFactory.getInstence().getResorceID("msg_no_data");
        Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
    }
    public void onJsonLoadErr(DataManager manager, String path)
    {
        this.resultError(path);
        int msgID = LanguageFactory.getInstence().getResorceID("msg_network_err");
        Toast.makeText(MainActivity.getInstence(),msgID,Toast.LENGTH_LONG).show();
    }

    private void resultError(String path)
    {
        switch(path)
        {
            case Config.API_LOAD_BIT_DATAS:
                ObserverController.shareInstence().notifyPost(BitDataManager.NotificatioBitDataLoadError,null,null);
                break;
            case Config.API_LOAD_BIT_SETUP:
                ObserverController.shareInstence().notifyPost(BitDataManager.NotificatioBitDataSetupError,null,null);
                break;
        }
    }

    private void removeTimer()
    {
        if(timer != null){
            timer.removeTimer();
            timer = null;
        }

    }


    public void onTime(CustomTimer timer)
    {
        if(settings == null) return;
        if(settings.size()<=0) return;
        loadBitDatas();
    }
    public void onComplete(CustomTimer timer){}
    public void pause()
    {
        autoLoadStop();
    }
    public void resume()
    {
        autoLoadInit();
    }
}






