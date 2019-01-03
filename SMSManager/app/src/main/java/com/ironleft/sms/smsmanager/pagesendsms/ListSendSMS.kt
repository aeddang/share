package com.ironleft.sms.smsmanager.pagesendsms

import android.content.Context
import android.content.DialogInterface
import android.widget.FrameLayout
import android.widget.TextView
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ironleft.sms.smsmanager.R
import com.ironleft.sms.smsmanager.sms.SMSManager
import com.ironleft.sms.smsmanager.sms.SMSObject
import lib.core.ActivityCore
import lib.core.ViewCore
import lib.observer.Observer
import lib.observer.ObserverController

class ListSendSMS(context: Context) : FrameLayout(context) , View.OnClickListener, Observer
{
    private var textDate:TextView? = null
    private var textDesc:TextView? = null
    private var textStatus:TextView? = null
    private var btnRetry:Button? = null
    private var data:SMSObject? = null

    init
    {
        View.inflate(context, R.layout.list_send_sms, this)
        textDate = findViewById<View>(R.id._textDate) as TextView
        textDesc = findViewById<View>(R.id._textDesc) as TextView
        textStatus = findViewById<View>(R.id._textStatus) as TextView
        btnRetry = findViewById<View>(R.id._btnRetry) as Button
        btnRetry?.visibility = View.GONE
        btnRetry?.setOnClickListener(this)

        ObserverController.shareInstence().registerObserver(this, SMSManager.NotificationRetryComplete)
    }

    fun setData(_data:SMSObject)
    {
        data = _data
        textDate?.text = data?.getViewDate()
        textDesc?.text = data?.body
        update()
    }

    private fun update()
    {
        if(data == null) return
        textStatus?.text = data!!.getViewStatus()
        if(data!!.isComplete) btnRetry?.visibility = View.GONE else btnRetry?.visibility = View.VISIBLE
    }

    override fun notification(notify: String, value: Any?, userData: Map<String, Any>?)
    {
        val newData:SMSObject? = value as SMSObject?
        if(newData == null) return
        if(newData.id == data?.id) update()
    }

    override fun onClick(arg: View)
    {
        if(data == null) return

        if(SMSManager.shareInstence().isReadAble())
        {
            SMSManager.shareInstence().retrySend(data!!)
        }else
        {
            Toast.makeText(ActivityCore.shareInstence(),R.string.msg_sms_send_unable, Toast.LENGTH_SHORT).show()
        }

    }


}