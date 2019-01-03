package com.ironleft.sms.smsmanager.pagesendsms

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import com.ironleft.sms.smsmanager.MainActivity
import com.ironleft.sms.smsmanager.PageCertification
import com.ironleft.sms.smsmanager.R
import lib.core.ActivityCore
import lib.core.PageObject
import lib.core.PopupCore

class PopupAddAccount(context: Context, pageInfo: PageObject) : PopupCore(context,pageInfo), View.OnClickListener
{
    var btnConfirm: Button? = null
    var inputAccount: EditText? = null

    init {
        View.inflate(context, R.layout.popup_add_account, this)
        pageInfo.isHome = true
        body = findViewById<View>(R.id._body) as FrameLayout
        btnConfirm = findViewById<View>(R.id._btnConfirm) as Button
        inputAccount = findViewById<View>(R.id._inputAccount) as EditText

        btnConfirm?.setOnClickListener(this)

    }

    override fun doMovedInit() {
        super.doMovedInit()
    }



    override fun doRemove() {
        super.doRemove()
    }



    fun confirm()
    {
        if(inputAccount!!.text.isEmpty())
        {
            mainActivity?.viewAlert("", R.string.msg_require_account_number,
                    DialogInterface.OnClickListener { dialogInterface, i -> inputAccount?.requestFocus() })
            return
        }

    }


    override fun onClick(arg: View)
    {
        when(arg)
        {
            btnConfirm -> confirm()
        }

    }



}