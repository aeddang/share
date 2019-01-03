package com.ironleft.sms.smsmanager

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import com.ironleft.sms.smsmanager.sms.SMSManager
import com.ironleft.sms.smsmanager.sms.SMSReader
import lib.core.ActivityCore
import lib.core.PageObject
import lib.core.ViewCore
import lib.loader.DataManager
import org.json.JSONObject
import android.telephony.TelephonyManager
import java.io.IOException


class PagePhoneNumber(context: Context, pageInfo: PageObject) : ViewCore(context, pageInfo),
        View.OnClickListener
{
    companion object
    {
        val REQUEST_PERMISSION_ID = 101
    }

    var btnConfirm: Button? = null
    var inputPhone: EditText? = null

    init {
        View.inflate(context, R.layout.page_phone_number, this)
        pageInfo.isHome = true

        btnConfirm = findViewById<View>(R.id._btnConfirm) as Button
        inputPhone = findViewById<View>(R.id._inputPhone) as EditText

        btnConfirm?.setOnClickListener(this)



    }


    override fun doResize() {
        super.doResize()


    }

    override fun doMovedInit() {
        super.doMovedInit()
        requestPermission()
    }

    override fun doUpdate()
    {
        setPhoneNumber()
    }


    fun requestPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCore.shareInstence()?.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED)
            {
                ActivityCore.shareInstence()?.requestPermissions(
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        PageCertification.REQUEST_PERMISSION_ID)
            }else
            {
                setPhoneNumber()
            }
        }else
        {
            setPhoneNumber()
        }

    }

    private fun setPhoneNumber()
    {
        val tMgr: TelephonyManager
        tMgr = mainActivity?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var mPhoneNumber:String? = null
        try
        {
            mPhoneNumber = tMgr?.line1Number
        }
        catch (e: SecurityException){}

        if (mPhoneNumber != null) inputPhone?.setText(mPhoneNumber)
    }


    override fun doRemove() {
        super.doRemove()
    }



    fun confirm()
    {
        if(inputPhone!!.text.isEmpty())
        {
            mainActivity?.viewAlert("",R.string.msg_require_phone_number,
                    DialogInterface.OnClickListener { dialogInterface, i -> inputPhone?.requestFocus() })
            return
        }

        var mc = mainActivity as MainActivity?
        mc?.certificationComplete(inputPhone!!.text.toString())

    }


    override fun onClick(arg: View)
    {
        when(arg)
        {
            btnConfirm -> confirm()
        }

    }



}