package com.credit.korea.KoreaCredit;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import lib.datamanager.DataManager;

public class SMSObject
{
    public String date;
    public String value;
    public String adress;

    public SMSObject() {


    }

    public String getValue()
    {
        return  DataFactory.getInstence().getUriencode(value)+";"+adress+";"+date;

    }
}

