package com.ironleft.sms.smsmanager.sms

import android.util.Log
import com.ironleft.sms.smsmanager.Config
import com.ironleft.sms.smsmanager.MainActivity
import lib.core.ActivityCore
import lib.loader.DataManager
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class SMSSender(): DataManager.DataManagerDelegate
{
    private var dataLoader: DataManager = DataManager("GET")
    var delegate:SMSSenderDelegate? = null
    private var sendLists: ArrayList<SMSObject>? = null
    private var sendList:SMSObject? = null
    var isBusy = false
    private var currentCount = 0

    init
    {
        dataLoader.setOnDataDelegate(this)
    }

    fun requestSend(result: ArrayList<SMSObject>):Boolean
    {
        if(isBusy) return false
        sendLists = result
        currentCount = 0
        request()
        return true
    }

    fun retryRequestSend(sms:SMSObject):Boolean
    {
        if(isBusy) return false
        sendList = sms
        requestSMS(sms)
        return true
    }
    private fun retryRequestSendComplete()
    {
        isBusy = false
        delegate?.onSendCompleted(sendLists)
        sendList = null
    }

    private fun request()
    {
        if(sendLists == null) return

        if(sendLists!!.size == currentCount)
        {
            isBusy = false
            delegate?.onSendCompleted(sendLists)
            sendLists = null
            return
        }
        val cList = sendLists!!.get(currentCount)
        requestSMS(cList)
    }

    private fun requestSMS(sms:SMSObject)
    {
        var mc = ActivityCore.shareInstence() as MainActivity?
        if(mc == null) return

        isBusy = true
        dataLoader.loadData(Config.API_HOST
                +Config.API_SEND
                + "?my_number=" + mc.getCertificationKey()
                + "&msg_cont=" + URLEncoder.encode(sms.body, "UTF-8")
                + "&bank_number=" +sms.address)
    }


    override fun onDataCompleted(manager: DataManager, path: String?, result: String)
    {
        if(sendLists == null) return
        if(sendLists!!.size <= currentCount) return
        var sms:SMSObject = sendLists!!.get(currentCount)
        sms.isComplete = true

        Log.i("SENDER",result)
        if(result.indexOf("receiver_error") != -1) sms.status = "입금, 이체 문자가 아닙니다 "
        if(result.indexOf("receiver_number_error") != -1) sms.status = "인증되지 않은 사용자입니다 "
        if(sms.status == "") sms.status = "전송완료"
        currentCount++
        if(sendLists != null) request()
    }
    override fun onDataLoadErr(manager: DataManager, path: String?)
    {
        if(sendLists == null) return
        if(sendLists!!.size <= currentCount) return
        var sms:SMSObject = sendLists!!.get(currentCount)
        sms.isComplete = false
        sms.status = "전송실패"
        currentCount++
        if(sendLists != null) request()
    }


}

interface SMSSenderDelegate
{
    fun onSendCompleted(result: ArrayList<SMSObject>?)
    fun onSendCompleted(result: SMSObject)
}