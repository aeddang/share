package lib.core

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import lib.ViewUtil
import lib.view.Gesture

class PopupCore(context: Context, _pageInfo: PageObject) : ViewCore(context, _pageInfo), Gesture.GestureDelegate
{

    var body: FrameLayout? = null
    private var gesture: Gesture? = null
    var popupDelegate: PopupCoreDelegate? = null

    private var startPosition = 0f
    private var finalGesture = Gesture.NONE
    private var popupType = POPUP_TYPE_BOTTOM
    private var trigger: Boolean = false

    init
    {
        gesture = Gesture(this, true, false)
        when (pageInfo.dr)
        {
            -1 -> popupType = POPUP_TYPE_TOP
            1 -> popupType = POPUP_TYPE_BOTTOM
            else -> popupType = POPUP_TYPE_CENTER
        }
    }

    override fun doMovedInit()
    {
        this.setBodySize()
        super.doMovedInit()
    }

    override fun doRemove()
    {
        popupDelegate?.removePopup(this)
        popupDelegate = null
        gesture?.delegate = null
        gesture = null
        super.doRemove()


    }

    override fun setBodySizeChanged(proposedWidth: Int, proposedheight: Int)
    {
        ViewUtil.setFrame(this.body!!, -1, -1, proposedWidth, proposedheight)
        super.setBodySizeChanged(proposedWidth, proposedheight)

    }

    fun setBodySize()
    {
        ViewUtil.setFrame(this.body!!, -1, -1, this.width, this.height)
    }

    private fun onGestureClose()
    {
        this.pageInfo?.dr = 0
        this.removePosition()
        ActivityCore.shareInstence()?.removePopup(this)
    }


    override fun stateChange(g: Gesture, e: String)
    {
        val d = g.changePosA[0]
        when (e)
        {
            Gesture.START -> touchStart()
            Gesture.MOVE_V -> touchMove(d.y)
            Gesture.END,Gesture.CANCLE -> touchEnd()
        }
    }

    private fun touchStart()
    {
        this.finalGesture = Gesture.NONE
        this.startPosition = this.body!!.y
    }

    private fun touchMove(point: Int)
    {

        var p = point + this.startPosition
        var pct = 1f

        if (this.popupType == POPUP_TYPE_TOP)
        {
            if (p > 0) p = 0f else if (p < -this.height) p = (-this.height).toFloat()
            pct = (this.height + p) / this.height

        }
        else
        {
            val topPos = 0f
            if (p > this.height) p = this.height.toFloat() else if (p < topPos) p = topPos
            pct = (this.height + (topPos - p)) / this.height
        }
        ViewUtil.setFrame(this.body, -1, Math.floor(p.toDouble()).toInt(), -1, -1)
        ViewUtil.setAlpha(this, pct)

    }

    private fun touchEnd() {
        if (this.popupType == POPUP_TYPE_TOP)
        {
            if (finalGesture === Gesture.PAN_UP) onGestureClose() else returnPosition()
        }
        else
        {
            if (finalGesture === Gesture.PAN_DOWN) onGestureClose() else returnPosition()
        }

    }

    override fun gestureComplete(g: Gesture, e: String)
    {
        if (e === Gesture.PAN_UP || e === Gesture.PAN_DOWN) this.finalGesture = e
    }

    /*
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if(trigger == false)
        {
            return true;
        }else
        {
            trigger=this.gesture.adjustEvent(event);
            if(trigger == false)
            {
                return true;
            }else
            {
                return false;
            }

        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        if(this.gesture==null)
        {

            return super.onTouchEvent(event);
        }
        if(this.pageInfo.isGestureUsed==true)
        {
            trigger=this.gesture.adjustEvent(event);
            return trigger;
        }
        else
        {
            return super.onTouchEvent(event);
        }

    }
    */
    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        if (this.gesture == null) return super.onTouchEvent(event)
        if (this.pageInfo.isGestureUsed == true)
        {
            trigger = this.gesture!!.adjustEvent(event)
            return trigger
        }
        else
        {
            return super.onTouchEvent(event)
        }

    }

    override fun rotateChange(g: Gesture, rotate: Float) {}
    override fun pinchChange(g: Gesture, dist: Float) {}
    override fun gestureChange(g: Gesture, e: String) {}

    private fun removePosition()
    {
        if(this.body == null) return
        Log.i("move", "removePosition")
        val layout = this.body?.layoutParams as FrameLayout.LayoutParams
        var ty = this.height
        val dfY = Math.floor(this.body!!.y.toDouble()).toInt()
        if (this.popupType == POPUP_TYPE_TOP) ty = -ty
        layout.topMargin = ty
        this.body?.layoutParams = layout
        val moveAni = TranslateAnimation(0f, 0f, (dfY - ty).toFloat(), ty.toFloat())
        moveAni.duration = 200
        //moveAni.setAnimationListener(this);
        this.body?.startAnimation(moveAni)

    }

    private fun returnPosition()
    {
        if(this.body == null) return
         ViewUtil.setAlpha(this, 1f)
        Log.i("move", "returnPosition")
        val layout = this.body?.layoutParams as FrameLayout.LayoutParams
        val dfY = Math.floor(this.body!!.y.toDouble()).toInt()
        layout.topMargin = 0
        this.body?.layoutParams = layout
        val moveAni = TranslateAnimation(0f, 0f, dfY.toFloat(), 0f)
        moveAni.duration = 200
        //moveAni.setAnimationListener(this);
        this.body?.startAnimation(moveAni)

    }

    fun onAnimationRepeat(arg0: Animation) {
        // TODO Auto-generated method stub

    }

    fun onAnimationStart(arg0: Animation) {
        // TODO Auto-generated method stub

    }

    interface PopupCoreDelegate {
        fun removePopup(view: PopupCore){}
    }

    companion object
    {
        val POPUP_TYPE_BOTTOM = 0
        val POPUP_TYPE_TOP = 1
        val POPUP_TYPE_CENTER = 2
    }
}