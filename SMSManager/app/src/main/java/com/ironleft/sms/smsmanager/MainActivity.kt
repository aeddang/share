package com.ironleft.sms.smsmanager


import android.content.pm.ActivityInfo
import android.os.Bundle
import com.ironleft.sms.smsmanager.pagesendsms.PageSendSMS
import com.ironleft.sms.smsmanager.pagesendsms.PageSendSMSList
import com.ironleft.sms.smsmanager.sms.SMSManager
import lib.core.LeftMenuActivityCore
import lib.core.PageObject
import lib.core.ViewCore

class MainActivity : LeftMenuActivityCore()
{
    private val IS_CERTIFICATION_COMPLETE = "IS_CERTIFICATION_COMPLETE"
    private val CERTIFICATION_KEY = "CERTIFICATION_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shared = getSharedPreferences( PREFS_NAME, 0)
        if(shared.getBoolean(IS_CERTIFICATION_COMPLETE, false)) pageStart() else certificationStart()
        removeIntroStart(3000)
    }

    fun certificationStart()
    {
        var pobj:PageObject =  PageObject(Config.PAGE_CERTIFICATION)
        pobj.dr = 0
        changeView(pobj)
    }

    fun certificationComplete(key:String)
    {
        val editor = getSharedPreferences( PREFS_NAME, 0).edit()
        editor.putBoolean(IS_CERTIFICATION_COMPLETE, true)
        editor.putString(CERTIFICATION_KEY,key)
        editor.commit()
        pageStart()
    }

    fun getCertificationKey():String
    {
        val shared = getSharedPreferences( PREFS_NAME, 0)
        return shared.getString(CERTIFICATION_KEY, "")
    }

    fun pageStart()
    {
        var pobj:PageObject =  PageObject(Config.PAGE_SEND_SMS)
        pobj.dr = 0
        changeView(pobj)
    }

    override fun creatView()
    {
        super.creatView()
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        when (currentPageObj!!.pageID)
        {

            Config.PAGE_CERTIFICATION -> {currentView = PagePhoneNumber(this, currentPageObj!!)}
            Config.PAGE_SEND_SMS -> {currentView = PageSendSMSList(this, currentPageObj!!)}
        }

    }

    override fun creatPopup(pinfo: PageObject): ViewCore?
    {
    	var pop:ViewCore? = null
        when (pinfo.pageID)
        {
    		Config.POPUP_SETUP -> { pop = PopupWebView(this, pinfo)
            }
    	}
    	return pop;

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == SMSManager.REQUEST_PERMISSION_ID) SMSManager.shareInstence().checkPermission()
        if(requestCode == PageCertification.REQUEST_PERMISSION_ID) currentView?.onUpdate()
    }
}
