package com.aeddang.magellan

import android.content.Context
import android.view.View
import android.widget.Button
import lib.core.ActivityCore
import lib.core.LeftMenuActivityCore
import lib.core.PageObject
import lib.core.ViewCore


class PageSub(context: Context, pageInfo: PageObject) : ViewCore(context, pageInfo), View.OnClickListener
{
    var btnTest:Button? = null

    init
    {
        View.inflate(context, R.layout.page_sub, this)

        btnTest = findViewById<View>(R.id._btnTest) as Button
        this.pageInfo.isHome = true

        btnTest?.setOnClickListener(this)

    }


    override fun doResize() {
        super.doResize()

    }

    override fun doMovedInit() {
        super.doMovedInit()
        //mainActivity!!.loadingStart(false)

    }


    override fun doRemove() {
        super.doRemove()
    }

    override fun onClick(arg: View)
    {
        val pobj = PageObject(Config.PAGE_HOME)
        mainActivity!!.changeView(pobj)
    }



}