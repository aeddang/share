package lib.core

import android.content.Context
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import com.aeddang.magellan.R
import lib.ViewUtil
import lib.observer.ObserverController
import lib.view.Gesture

class LeftMenuCore(context: Context, wid: Int = 0) : FrameLayout(context), Gesture.GestureDelegate, View.OnClickListener, Animation.AnimationListener {

    private val gesture: Gesture = Gesture(this, false, true)
    var coreDelegate: LeftMenuCoreDelegate? = null
    var isActive = false
    var isInit = false
    var body: FrameLayout?

    private var startPosition = 0f
    private val dimed: ImageView?
    private var finalGesture = ""
    private var trigger = false
    private val menuSize = wid

    init {

        this.dimed = ImageView(this.context)
        this.dimed.scaleType = ImageView.ScaleType.FIT_XY
        this.dimed.setImageResource(R.drawable.transparent70)
        val layoutDimed = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.LEFT or Gravity.TOP)
        layoutDimed.leftMargin = 0
        layoutDimed.topMargin = 0
        this.addView(this.dimed, layoutDimed)

        this.body = FrameLayout(this.context)
        val layoutBody = FrameLayout.LayoutParams(wid, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.LEFT or Gravity.TOP)
        layoutBody.leftMargin = -wid
        this.addView(this.body, layoutBody)
        ViewUtil.setAlpha(this.dimed, 0f)
        this.visibility = View.GONE

    }

    fun initAction()
    {
        this.isInit = true
        coreDelegate!!.initMenu(this)

    }

    override fun onClick(arg: View)
    {
        this.passive()
    }


    fun active() {


        if (this.isActive == true) return
        this.isActive = true
        this.visibility = View.VISIBLE
        this.coreDelegate?.activeMenu(this, this.menuSize.toFloat())
        ViewUtil.setAlpha(this.dimed, 1f)
        val tx = 0
        val layout = this.body?.layoutParams as FrameLayout.LayoutParams
        val dfX = layout.leftMargin
        layout.leftMargin = tx
        this.body?.layoutParams = layout
        val moveAni = TranslateAnimation(dfX.toFloat(), 0f, 0f, 0f)
        moveAni.duration = 200
        moveAni.setAnimationListener(this)
        this.body?.startAnimation(moveAni)
        ObserverController.shareInstence().notifyPost(LeftMenuCore.NotificationLeftMenuOn, null, null)


    }


    fun passive()
    {
        if (isActive == false) return
        if(body == null) return
        isActive = false
        this.coreDelegate?.passiveMenu(this)
        ViewUtil.setAlpha(this.dimed, 0f)
        val tx = - this.body!!.width
        val layout = this.body!!.layoutParams as FrameLayout.LayoutParams
        val dfX = layout.leftMargin
        layout.leftMargin = tx
        this.body!!.layoutParams = layout
        val moveAni = TranslateAnimation((dfX - tx).toFloat(), tx.toFloat(), 0f, 0f)
        moveAni.duration = 200
        moveAni.setAnimationListener(this)
        this.body!!.startAnimation(moveAni)
        ObserverController.shareInstence().notifyPost(LeftMenuCore.NotificationLeftMenuOff, null, null)
    }

    override fun onAnimationEnd(arg0: Animation)
    {
        if (this.isActive == true) return
        this.visibility = View.GONE
    }

    override fun stateChange(g: Gesture, e: String)
    {
        val d = g.changePosA[0]
        when (e)
        {
            Gesture.START -> touchStart()
            Gesture.MOVE_H -> touchMove(d.x)
            Gesture.END,Gesture.CANCLE -> touchEnd()
        }
    }

    private fun touchStart()
    {
        if(this.body == null) return
        this.finalGesture = Gesture.NONE
        this.startPosition = this.body!!.x
    }

    private fun touchMove(point: Int)
    {
        if(this.body == null) return
        var p = point + this.startPosition
        if (p > 0) p = 0f else if (p < -this.body!!.width) p = (-this.body!!.width).toFloat()
        val pct = (this.body!!.width + p) / this.body!!.width
        ViewUtil.setAlpha(this.dimed, pct)
        ViewUtil.setFrame(this.body, Math.floor(p.toDouble()).toInt(), -1, -1, -1)
        this.coreDelegate?.moveMenu(this, p + this.body!!.width)

    }

    private fun touchEnd()
    {
        if(this.body == null) return
        when (finalGesture)
        {
            Gesture.PAN_RIGHT -> active()
            Gesture.PAN_LEFT -> passive()
            Gesture.TOUCH -> {
                val bodyPos = Math.floor(this.body!!.x.toDouble()).toInt() + this.body!!.width
                if (this.gesture.startPosA[0].x > bodyPos) this.passive()
            }
            else ->
            {
                if (isActive)
                {
                    isActive = false
                    active()
                }
                else
                {
                    isActive = true
                    passive()
                }
            }

        }

    }

    override fun gestureComplete(g: Gesture, e: String)
    {
        if (e === Gesture.PAN_RIGHT || e === Gesture.PAN_LEFT || e === Gesture.TOUCH) this.finalGesture = e
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean
    {
        if (trigger == false) return true
        trigger = this.gesture.adjustEvent(event)
        return if (trigger == false) true else false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        trigger = this.gesture.adjustEvent(event)
        return trigger
    }


    override fun rotateChange(g: Gesture, rotate: Float) {}
    override fun pinchChange(g: Gesture, dist: Float) {}
    override fun gestureChange(g: Gesture, e: String) {}


    override fun onAnimationRepeat(arg0: Animation) {
        // TODO Auto-generated method stub

    }

    override fun onAnimationStart(arg0: Animation) {
        // TODO Auto-generated method stub

    }

    interface LeftMenuCoreDelegate {

        fun moveMenu(view: LeftMenuCore, p: Float){}
        fun activeMenu(view: LeftMenuCore, p: Float){}
        fun passiveMenu(view: LeftMenuCore){}
        fun initMenu(view: LeftMenuCore){}
    }

    companion object {
        internal val NotificationLeftMenuOn = "NotificationLeftMenuOn"
        internal val NotificationLeftMenuOff = "NotificationLeftMenuOff"
    }
}