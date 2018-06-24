package com.aeddang.magellan

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import lib.core.ActivityCore
import lib.core.LeftMenuActivityCore
import lib.core.PageObject


class MainActivity : LeftMenuActivityCore() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pobj = PageObject(Config.PAGE_HOME)
        pobj.dr = 0
        changeView(pobj)
        removeIntroStart(2000)
    }

    override fun creatView()
    {
        super.creatView()
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        when (currentPageObj!!.pageID)
        {

            Config.PAGE_HOME -> {currentView = PageHome(this, currentPageObj!!)}
            Config.PAGE_SUB -> {currentView = PageSub(this, currentPageObj!!)}
        }
        Log.i("CV","currentView :" + currentView!!.toString())


    }
}
