package com.ironleft.sms.smsmanager


import lib.loader.DataManager
import lib.observer.ObserverController
import org.json.JSONObject

class SetupInfo(): DataManager.JsonManagerDelegate
{
    private var dataLoader: DataManager = DataManager("GET")
    var accountList:ArrayList<String> = ArrayList<String>()

    init
    {
        dataLoader.setOnJsonDelegate(this)
    }
    fun getAccounts()
    {

        if(accountList.size <= 0) requestAccounts() else requestAccountsComplete()
    }
    fun requestAccounts()
    {

        dataLoader.loadData(Config.API_HOST+Config.API_SETUP )
    }

    private fun requestAccountsComplete()
    {
        ObserverController.shareInstence().notifyPost(NotificationSetupChanged,accountList);
    }

    override fun onJsonCompleted(manager: DataManager, path: String?, result: JSONObject)
    {
        accountList.add("47617313")
        accountList.add("0108006")
        accountList.add("1588")
        requestAccountsComplete()
    }
    override fun onJsonParseErr(manager: DataManager, path: String?)
    {
        ObserverController.shareInstence().notifyPost(NotificationSetupError);
    }
    override fun onJsonLoadErr(manager: DataManager, path: String?)
    {

        ObserverController.shareInstence().notifyPost(NotificationSetupError);
    }

    companion object
    {
        val NotificationSetupChanged = "NotificationSetupChanged"
        val NotificationSetupError = "NotificationSetupError"

        private var instence: SetupInfo? = null

        fun shareInstence(): SetupInfo
        {
            if (instence == null) instence = SetupInfo()
            return instence!!
        }
    }

}