package com.ironleft.sms.smsmanager.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.ArrayList

class SMSReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {



        Log.i("SMSReceiver","Received")

        val myBundle = intent.extras
        val format = myBundle.getString("format")
        val messages = ArrayList<SmsMessage>()
        var strMessage = ""

        if (myBundle != null) {
            val pdus = myBundle.get("pdus") as Array<ByteArray>

            for (i in pdus.indices)
            {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    messages.add(SmsMessage.createFromPdu(pdus[i],format))
                }
                else
                {
                    messages.add( SmsMessage.createFromPdu(pdus[i]))
                }
                val address = messages[i].originatingAddress
                strMessage += "SMS From: " + address;
                strMessage += " : ";
                strMessage += messages[i].getMessageBody();
                strMessage += "\n";
                Log.i("SMSReceiver", strMessage)
                if (address != null) {

                    var value = messages[i].messageBody
                    try {
                        value = URLEncoder.encode(value, "UTF-8")
                    } catch (e: UnsupportedEncodingException) {
                        value = ""
                    }
                }

            }
            SMSManager.instence?.checkPermission()


        }

    }
}