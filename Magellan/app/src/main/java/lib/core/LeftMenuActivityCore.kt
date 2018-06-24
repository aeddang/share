package lib.core

import android.os.Bundle
import android.util.Log
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import lib.ViewUtil

open class LeftMenuActivityCore : ActivityCore(), LeftMenuCore.LeftMenuCoreDelegate {
    open var leftMenuSize = 205
    var leftMenuCore: LeftMenuCore? = null
    private var initSet: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        if (isInit == true) return
        this.leftMenuCore = LeftMenuCore(this.applicationContext, this.dpi.toInt() * leftMenuSize)
        this.leftMenuCore?.coreDelegate = this
        this.body?.addView(this.leftMenuCore, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        this.leftMenuCore?.initAction()


    }

    fun setInit()
    {
        if (initSet == false)
        {
            initSet = true
            ViewUtil.setFrame(this.viewer, -1, -1, this.body!!.getWidth(), this.body!!.getHeight())
        }
    }


    override fun resizeScreen(proposedWidth: Int, proposedheight: Int)
    {
        ViewUtil.setFrame(this.viewer, -1, -1, proposedWidth, proposedheight)
        super.resizeScreen(proposedWidth, proposedheight)

    }

    fun toggleLeftMenu()
    {
        setInit()
        if(leftMenuCore == null) return
        if (leftMenuCore!!.isActive) leftMenuCore!!.passive() else leftMenuCore!!.active()
    }

    fun openLeftMenu()
    {
        setInit()
        leftMenuCore?.active()
    }

    fun closeLeftMenu()
    {
        setInit()
        leftMenuCore?.passive()
    }

    override fun initMenu(view: LeftMenuCore)
    {
        //ViewUtil.setFrame(this.viewer, 0,0,this.body.getWidth(),this.body.getHeight());
    }

    override fun moveMenu(view: LeftMenuCore, p: Float)
    {
        ViewUtil.setFrame(this.viewer, Math.floor(p.toDouble()).toInt(), -1, -1, -1)
    }

    override fun activeMenu(view: LeftMenuCore, p: Float)
    {
        val tx = Math.floor(p.toDouble()).toInt()
        val layout = this.viewer?.getLayoutParams() as FrameLayout.LayoutParams
        val dfX = layout.leftMargin
        layout.leftMargin = tx
        Log.i("activeMenu", "dfX : $dfX  tx : $tx")
        this.viewer?.setLayoutParams(layout)
        val moveAni = TranslateAnimation((dfX - tx).toFloat(), 0f, 0f, 0f)
        moveAni.duration = 200
        this.viewer?.startAnimation(moveAni)


    }

    override fun passiveMenu(view: LeftMenuCore) {

        val layout = this.viewer?.getLayoutParams() as FrameLayout.LayoutParams
        val dfX = layout.leftMargin
        layout.leftMargin = 0
        this.viewer?.setLayoutParams(layout)
        val moveAni = TranslateAnimation(dfX.toFloat(), 0f, 0f, 0f)
        moveAni.duration = 200
        this.viewer?.startAnimation(moveAni)

    }


}
