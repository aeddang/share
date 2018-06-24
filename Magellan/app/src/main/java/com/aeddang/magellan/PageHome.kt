package com.aeddang.magellan

import android.content.Context
import android.view.View
import android.widget.Button
import lib.core.ActivityCore
import lib.core.LeftMenuActivityCore
import lib.core.PageObject
import lib.core.ViewCore


class PageHome(context: Context, pageInfo: PageObject) : ViewCore(context, pageInfo), View.OnClickListener
{
    var btnMenu:Button? = null
    var btnTest:Button? = null

    init
    {
        View.inflate(context, R.layout.page_home, this)

        btnMenu = findViewById<View>(R.id._btnMenu) as Button
        btnTest = findViewById<View>(R.id._btnTest) as Button
        this.pageInfo.isHome = true

        btnMenu?.setOnClickListener(this)
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
        when(arg)
        {
            btnMenu ->
            {
                var la:LeftMenuActivityCore? =  ActivityCore.shareInstence() as? LeftMenuActivityCore
                la?.toggleLeftMenu()
            }

            btnTest ->
            {
                val pobj = PageObject(Config.PAGE_SUB)
                mainActivity!!.changeView(pobj)
            }
        }

    }



}