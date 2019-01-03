package com.ironleft.sms.smsmanager


import lib.core.ActivityCore
import lib.loader.DataManager
import lib.observer.ObserverController
import org.json.JSONObject

class UserInfo(): DataManager.JsonManagerDelegate
{
    private var dataLoader: DataManager = DataManager("GET")
    private var appServiceDay:String = ""
    private var customerIdx:String = ""
    init
    {
        dataLoader.setOnJsonDelegate(this)
    }

    fun requestUserInfo()
    {
        var mc = ActivityCore.shareInstence() as MainActivity?
        if(mc == null) return
        dataLoader.loadData(Config.API_HOST+Config.API_AUTH
                                   + "?auth_number=" + mc.getCertificationKey())
    }

    fun getServiceDay():String
    {
        return appServiceDay
    }
    fun getCustomerIdx():String
    {
        return customerIdx
    }



    override fun onJsonCompleted(manager: DataManager, path: String?, result: JSONObject)
    {
        var isSuccess = false
        try {
            appServiceDay = result.getString("app_service_day")
            //customerIdx = result.getString("customer_idx")
            isSuccess = result.getBoolean("success")
        }
        catch (e: Exception)
        {

        }
        ObserverController.shareInstence().notifyPost(NotificationUserInfoChanged,isSuccess);
    }
    override fun onJsonParseErr(manager: DataManager, path: String?)
    {
        ObserverController.shareInstence().notifyPost(NotificationUserInfoError);
    }
    override fun onJsonLoadErr(manager: DataManager, path: String?)
    {

        ObserverController.shareInstence().notifyPost(NotificationUserInfoError);
    }

    companion object
    {
        val NotificationUserInfoChanged = "NotificationUserInfoChanged"
        val NotificationUserInfoError = "NotificationUserInfoError"

        private var instence: UserInfo? = null

        fun shareInstence(): UserInfo
        {
            if (instence == null) instence = UserInfo()
            return instence!!
        }
    }

}