package com.ironleft.sms.smsmanager.sms

import android.Manifest
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import com.ironleft.sms.smsmanager.R
import com.ironleft.sms.smsmanager.SetupInfo
import lib.core.ActivityCore
import lib.observer.Observer
import lib.observer.ObserverController
import java.util.*

class SMSManager(): SMSReaderDelegate,SMSSenderDelegate, Observer
{
    private val reader:SMSReader =  SMSReader()
    private val sender:SMSSender =  SMSSender()
    private var needUpdate = false
    init
    {
        ObserverController.shareInstence().registerObserver(this,SetupInfo.NotificationSetupChanged)
        ObserverController.shareInstence().registerObserver(this,SetupInfo.NotificationSetupError)
        reader.delegate = this
        sender.delegate = this
    }

    fun initManager()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermission() else readStart()
    }

    fun checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCore.shareInstence()?.checkSelfPermission(Manifest.permission.READ_SMS) !== PackageManager.PERMISSION_GRANTED)
            {
                ActivityCore.shareInstence()?.viewAlertSelect("", R.string.msg_require_permission,
                        DialogInterface.OnClickListener { dialogInterface, i -> requestPermission()})
            } else {
                readStart()
            }
        }
    }

    fun isReadAble():Boolean
    {
        if(sender.isBusy) return false
        return true
    }

    fun readStart()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ActivityCore.shareInstence()?.checkSelfPermission(Manifest.permission.READ_SMS) !== PackageManager.PERMISSION_GRANTED) return;
        }
        if(sender.isBusy)
        {
            needUpdate = true
            return
        }
        needUpdate = false
        ObserverController.shareInstence().notifyPost(NotificationSMSStart)
        reader.readStart()
        //SetupInfo.shareInstence().getAccounts()

    }

    fun retrySend(sms:SMSObject):Boolean
    {
        if(sender.isBusy) return false
        needUpdate = false
        sender.retryRequestSend(sms)
        return true

    }

    override fun notification(notify: String, value: Any?, userData: Map<String, Any>?)
    {
        when(notify)
        {
            SetupInfo.NotificationSetupChanged -> reader.readStart()
            SetupInfo.NotificationSetupError-> onReadError()
        }
    }

    private fun onReadError()
    {

        ObserverController.shareInstence().notifyPost(NotificationReadError)
        ObserverController.shareInstence().notifyPost(NotificationSMSError)
    }

    override fun onReadCompleted(result:ArrayList<SMSObject>)
    {
        ObserverController.shareInstence().notifyPost(NotificationReadComplete)
        sender.requestSend(result)

    }

    override fun onSendCompleted(result: ArrayList<SMSObject>?)
    {
        if(result != null && result.size > 0) reader.setFinalSendSMSKey(result.get(0).id)
        ObserverController.shareInstence().notifyPost(NotificationSendComplete)
        ObserverController.shareInstence().notifyPost(NotificationSMSComplete,result)
        if(needUpdate) readStart()
    }

    override fun onSendCompleted(result:SMSObject)
    {
        ObserverController.shareInstence().notifyPost(NotificationRetryComplete,result)
    }



    fun requestPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCore.shareInstence()?.checkSelfPermission(Manifest.permission.READ_SMS) !== PackageManager.PERMISSION_GRANTED)
            {
                ActivityCore.shareInstence()?.requestPermissions(
                        arrayOf(Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS),
                        REQUEST_PERMISSION_ID)
            } else {
                readStart()
            }
        }
    }

    companion object
    {
        val REQUEST_PERMISSION_ID = 100
        val NotificationSMSStart = "NotificationSMSStart"
        val NotificationReadComplete = "NotificationReadComplete"
        val NotificationReadError = "NotificationReadError"
        val NotificationSendComplete = "NotificationSendComplete"
        val NotificationRetryComplete = "NotificationRetryComplete"
        val NotificationSMSError = "NotificationSMSError"
        val NotificationSMSComplete = "NotificationSMSComplete"

        var instence: SMSManager? = null


        fun shareInstence(): SMSManager
        {
            if (instence == null) instence = SMSManager()
            return instence!!
        }
    }

}

