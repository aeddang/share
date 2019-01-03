package com.ironleft.sms.smsmanager.sms
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import lib.core.ActivityCore
import java.sql.Timestamp
import java.time.LocalDateTime

class SMSReader() : ReaderDelegate
{
    private val FINAL_SMS_KEY = "finalSMSKey"
    private val INIT_TIME_KEY = "initTimeKey"
    private val shared: SharedPreferences = ActivityCore.shareInstence()!!.getSharedPreferences( ActivityCore.PREFS_NAME, 0)
    var delegate:SMSReaderDelegate? = null


    private fun getFinalSendSMS(): String
    {
        return shared.getString(FINAL_SMS_KEY, "")
    }

    fun setFinalSendSMSKey(key:String)
    {
        val editor = shared.edit()
        editor.putString(FINAL_SMS_KEY, key)
        editor.commit()
    }

    fun readStart()
    {
        if(ActivityCore.shareInstence() == null) return
        val cursor = ActivityCore.shareInstence()!!.getContentResolver().query(Uri.parse("content://sms/inbox"), arrayOf("_id","date","address", "body"),
                null, null, null)

        val reader = Reader()
        var initTime = shared.getLong(INIT_TIME_KEY ,0)
        if(initTime == 0.toLong())
        {
            initTime = System.currentTimeMillis()
            val editor = shared.edit()
            editor.putLong(INIT_TIME_KEY,initTime)
            editor.commit()
        }
        reader.start(this,getFinalSendSMS(),initTime)
        reader.execute(cursor)
    }

    override fun onCompleted(result: ArrayList<SMSObject>)
    {
        delegate?.onReadCompleted(result)
    }

}


internal class Reader : AsyncTask<Cursor, Int, ArrayList<SMSObject>>()
{
    private var delegate: ReaderDelegate? = null
    private var finalKey:String = ""
    private var initTime:Long = 0
    fun start(_delegate: ReaderDelegate,_finalKey:String,_initTime:Long)
    {
        delegate = _delegate
        finalKey = _finalKey
        initTime = _initTime

    }


    override fun doInBackground(vararg params: Cursor): ArrayList<SMSObject>
    {
        var cursor = params[0]
        val smss = ArrayList<SMSObject>()
        var sms:SMSObject? = null

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                for (idx in 0 until cursor.getColumnCount())
                {
                    val column = cursor.getColumnName(idx)
                    val value = cursor.getString(idx)
                    if(column == "_id")
                    {

                        if(value == finalKey)
                        {
                            if(sms != null) smss.add(sms!!)
                            sms = null
                            cursor.moveToLast()
                        }
                        else
                        {
                            if(sms != null) smss.add(sms!!)
                            sms = SMSObject()
                        }

                    }
                    if(column == "date")
                    {
                        val time =  value.toLong()
                        val date = Timestamp(time)
                        if(time < initTime)
                        {
                            if(sms != null) smss.add(sms!!)
                            sms = null
                            cursor.moveToLast()
                        }
                    }
                    sms?.addValue(column,value)
                }
            } while (cursor.moveToNext())
        }
        if(sms != null) smss.add(sms!!)
        return smss
    }


    override fun onPostExecute(result:ArrayList<SMSObject>)
    {
        if (result != null) delegate?.onCompleted(result)
        super.onPostExecute(result)
    }


}//package

internal interface ReaderDelegate
{
    fun onCompleted(result:ArrayList<SMSObject>)
}


interface SMSReaderDelegate
{
    fun onReadCompleted(result:ArrayList<SMSObject>)
}