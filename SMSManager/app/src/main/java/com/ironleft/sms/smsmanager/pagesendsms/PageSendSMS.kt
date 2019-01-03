package com.ironleft.sms.smsmanager.pagesendsms

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ironleft.sms.smsmanager.Config
import com.ironleft.sms.smsmanager.R
import com.ironleft.sms.smsmanager.UserInfo
import com.ironleft.sms.smsmanager.sms.SMSManager
import com.ironleft.sms.smsmanager.sms.SMSObject
import lib.core.PageObject
import lib.core.ViewCore
import lib.observer.Observer
import lib.observer.ObserverController


class PageSendSMS(context: Context, pageInfo: PageObject) : ViewCore(context, pageInfo), Observer,View.OnClickListener
{
    private var textNodata:TextView? = null
    private var textDate:TextView? = null
    private var btnRetry:Button? = null
    private var btnSetup:Button? = null

    private var lists:ListView? = null
    private var adapter: CustomBaseAdapter = CustomBaseAdapter()
    private var datas:ArrayList<SMSObject> = ArrayList<SMSObject>()

    init
    {
        View.inflate(context, R.layout.page_send_sms, this)

        btnRetry = findViewById<View>(R.id._btnRetry) as Button
        btnSetup = findViewById<View>(R.id._btnSetup) as Button
        lists = findViewById<View>(R.id._lists) as ListView
        textNodata  = findViewById<View>(R.id._textNodata) as TextView
        textDate  = findViewById<View>(R.id._textDate) as TextView


        this.pageInfo.isHome = true
        lists?.adapter = adapter
        textNodata?.visibility = View.GONE

    }

    override fun doMovedInit() {
        super.doMovedInit()
        ObserverController.shareInstence().registerObserver(this,SMSManager.NotificationSMSStart)
        ObserverController.shareInstence().registerObserver(this,SMSManager.NotificationSMSComplete)
        ObserverController.shareInstence().registerObserver(this,SMSManager.NotificationSMSError)
        ObserverController.shareInstence().registerObserver(this,UserInfo.NotificationUserInfoChanged)
        btnRetry?.setOnClickListener(this)
        btnSetup?.setOnClickListener(this)
        requestCerti()

    }

    fun requestCerti()
    {
        UserInfo.shareInstence().requestUserInfo()
        mainActivity?.loadingStart(true)
    }

    override fun doRemove() {
        super.doRemove()
        ObserverController.shareInstence().removeObserver(this)
    }


    override fun notification(notify: String, value: Any?, userData: Map<String, Any>?)
    {
        when(notify)
        {
            SMSManager.NotificationSMSStart -> sendStart()
            SMSManager.NotificationSMSComplete-> sendComplete(value)
            SMSManager.NotificationSMSError-> sendError()
            UserInfo.NotificationUserInfoChanged -> userUpdate(value)
        }
    }

    private  fun userUpdate(value: Any?)
    {
        //mainActivity?.loadingStop()
        var isSucces = value as Boolean
        var items:Array<CharSequence> = arrayOf("재시도","인증번호변경")
        if(isSucces == false) {
            mainActivity?.viewAlertSelectWithMenu("", R.string.msg_error_certify, items,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    when (i) {
                        DialogInterface.BUTTON_POSITIVE -> requestCerti()
                        DialogInterface.BUTTON_NEGATIVE -> mainActivity?.changeView(PageObject(Config.PAGE_CERTIFICATION))
                    }
                }
            )
            return
        }
        textDate?.setText(UserInfo.shareInstence().getServiceDay())
        SMSManager.shareInstence().initManager()
    }

    private fun sendStart()
    {
        if(mainActivity == null) return
        mainActivity!!.loadingStart(true)
        Toast.makeText(mainActivity!!,R.string.msg_sms_send_start,Toast.LENGTH_SHORT).show()
    }
    private fun sendError()
    {
        if(mainActivity == null) return
        mainActivity!!.loadingStop()
        Toast.makeText(mainActivity!!,R.string.msg_sms_send_error,Toast.LENGTH_SHORT).show()
        updateLists()
    }
    private fun sendComplete(value: Any?)
    {
        if(mainActivity == null) return
        mainActivity!!.loadingStop()

        var sendDatas = value as ArrayList<SMSObject>?
        if(sendDatas == null) return
        if(sendDatas.size>0)
        {
            Toast.makeText(mainActivity!!,R.string.msg_sms_send_complete,Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(mainActivity!!,R.string.msg_sms_send_nodata,Toast.LENGTH_SHORT).show()
        }

        datas.addAll(sendDatas)
        updateLists()

    }

    private fun updateLists()
    {
        textNodata?.visibility = if (datas.size<=0) View.VISIBLE else View.GONE
        adapter.notifyDataSetChanged()
    }

    private fun retrySMSReader()
    {
        if(SMSManager.shareInstence().isReadAble())
        {
            SMSManager.shareInstence().readStart()
        }else
        {
            Toast.makeText(mainActivity!!,R.string.msg_sms_send_unable,Toast.LENGTH_SHORT).show()
        }
    }
    private fun openSetupPopup()
    {
        var pobj:PageObject =  PageObject(Config.POPUP_SETUP)
        mainActivity?.addPopup(pobj)
    }
    override fun onClick(arg: View)
    {
        when(arg)
        {
            btnRetry -> retrySMSReader()
            btnSetup -> openSetupPopup()
        }

    }
    private inner class CustomBaseAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return datas.size
        }

        override fun getItem(position: Int): SMSObject {
            return datas.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }


        override fun getView(position: Int, convertview: View?, parent: ViewGroup): View
        {
            val list: ListSendSMS
            if (convertview == null)
            {
                list = ListSendSMS(mainActivity!!)
            }
            else
            {
                list = convertview as ListSendSMS
            }
            val data = getItem(position)
            list.setData(data)
            return list
        }

    }



}