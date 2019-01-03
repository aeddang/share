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


class PageCertification(context: Context, pageInfo: PageObject) : ViewCore(context, pageInfo),
        View.OnClickListener, DataManager.JsonManagerDelegate
{
    companion object
    {
        val REQUEST_PERMISSION_ID = 101
    }
    var btnRequest: Button? = null
    var btnConfirm: Button? = null
    var inputPhone: EditText? = null
    var inputCerti: EditText? = null
    var dataLoader: DataManager = DataManager("GET")

    init {
        View.inflate(context, R.layout.page_certification, this)
        pageInfo.isHome = true

        btnRequest = findViewById<View>(R.id._btnRequest) as Button
        btnConfirm = findViewById<View>(R.id._btnConfirm) as Button
        inputCerti = findViewById<View>(R.id._inputCerti) as EditText
        inputPhone = findViewById<View>(R.id._inputPhone) as EditText

        btnRequest?.setOnClickListener(this)
        btnConfirm?.setOnClickListener(this)
        dataLoader.setOnJsonDelegate(this)
        inputCerti?.isEnabled = false
        btnConfirm?.isClickable = false
        inputPhone?.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                requestCerti()
                true
            } else {
                false
            }
        }

        inputCerti?.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                confirm()
                true
            } else {
                false
            }
        }

    }


    override fun doResize() {
        super.doResize()
        requestPermission()

    }

    override fun doMovedInit() {
        super.doMovedInit()

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

    fun requestCerti()
    {
        if(inputPhone!!.text.isEmpty())
        {
            mainActivity?.viewAlert("",R.string.msg_require_phone_number,
                    DialogInterface.OnClickListener { dialogInterface, i -> inputPhone?.requestFocus() })
            return
        }
        mainActivity?.loadingStart(false)
        dataLoader.loadData(Config.API_HOST+Config.API_CERTIFICATION )

    }

    fun requestCertiComplete(result: JSONObject)
    {
        mainActivity?.viewAlert("",R.string.msg_request_certify,DialogInterface.OnClickListener { dialogInterface, i -> inputCerti?.requestFocus() })

        inputCerti?.isEnabled = true
        btnConfirm?.isClickable = true
    }

    fun confirm()
    {
        if(inputCerti!!.text.isEmpty())
        {
            mainActivity?.viewAlert("",R.string.msg_require_certify_number,
                    DialogInterface.OnClickListener { dialogInterface, i -> inputCerti?.requestFocus() })
            return
        }

        var mc = mainActivity as MainActivity?
        mc?.certificationComplete("key")

    }

    override fun onJsonCompleted(manager: DataManager, path: String?, result: JSONObject)
    {
        mainActivity?.loadingStop()
        when(path)
        {
            Config.API_HOST+Config.API_CERTIFICATION -> requestCertiComplete(result)
        }
    }
    override fun onJsonParseErr(manager: DataManager, path: String?)
    {
        mainActivity?.loadingStop()
        mainActivity?.viewAlert("",R.string.msg_server_err_kor,null)
    }
    override fun onJsonLoadErr(manager: DataManager, path: String?)
    {
        mainActivity?.loadingStop()
        mainActivity?.viewAlert("",R.string.msg_network_err_kor,null)
    }


    override fun onClick(arg: View)
    {
        when(arg)
        {
            btnRequest -> requestCerti()
            btnConfirm -> confirm()
        }

    }



}