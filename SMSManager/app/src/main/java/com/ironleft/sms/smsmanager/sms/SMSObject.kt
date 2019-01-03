package com.ironleft.sms.smsmanager.sms

import android.util.Log
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class SMSObject()
{
    var date = ""
    var body = ""
    var address = ""
    var id:String = ""
    var status = ""
    var isComplete:Boolean = false

    fun addValue(column:String?, value:String?)
    {
        if(column == null) return
        if(value == null) return
        when(column!!)
        {
            "_id" -> id = value!!
            "date" -> date = value!!
            "address" -> address = value!!
            "body" -> body = value!!
            else -> return
        }
        Log.i("SMS","column : " + column + " " + value)
    }

    fun getViewDate():String
    {
        var dateNum = date.toLongOrNull()
        if(dateNum == null) return "-"
        val stamp = Timestamp(dateNum)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val dat = Date(stamp.getTime())
        return simpleDateFormat.format(dat)
    }

    fun getViewStatus():String
    {
        when(status)
        {
            "receiver_error" -> return "입금, 이체 문자가 아닙니다 "
            "receiver_number_error" -> return "인증되지 않은 사용자입니다"
            else -> return status
        }

    }
}

