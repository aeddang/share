package lib

import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout

object ViewUtil
{

    fun remove(view: View?)
    {
        if(view == null) return
        val parent = view!!.parent as ViewGroup
        parent?.removeView(view)
    }

    fun addView(addView: ViewGroup, view: View)
    {
        val parent = view.parent as ViewGroup
        if (parent == null)
        {
            addView.addView(view, view.layoutParams)
        }
        else if (parent !== addView)
        {
            parent.removeView(view)
            addView.addView(view, view.layoutParams)
        }
    }

    fun setAlpha(view: View?, a: Float)
    {
        if(view == null) return
        if (Build.VERSION.SDK_INT < 11)
        {
            val animation = AlphaAnimation(a, a)
            animation.duration = 0
            animation.fillAfter = true
            view!!.startAnimation(animation)
        }
        else
        {
            view!!.alpha = a
        }
    }

    fun setFrame(view: View?, left: Int, top: Int, width: Int, height: Int)
    {
        if(view == null) return
        var left = left
        var top = top
        var width = width
        var height = height
        val layout = view!!.layoutParams as FrameLayout.LayoutParams

        if (left == -1) left = layout.leftMargin
        if (top == -1) top = layout.topMargin
        val mfX = 0
        val mfY = 0

        if (width == -1) width = layout.width
        if (height == -1) height = layout.height

        layout.setMargins(left, top, layout.rightMargin, layout.bottomMargin)
        layout.width = width
        layout.height = height
        view!!.layoutParams = layout
    }

    fun setFrameCenter(view: View?, left: Int, top: Int, width: Int, height: Int)
    {
        if(view == null) return
        var left = left
        var top = top
        var width = width
        var height = height
        val layout = view!!.layoutParams as FrameLayout.LayoutParams
        if (left == -1) left = layout.leftMargin
        if (top == -1) top = layout.topMargin

        var mfX = 0
        var mfY = 0

        if (width == -1) width = layout.width else mfX = Math.floor((width - view.width).toDouble() / 2f).toInt()
        if (height == -1) height = layout.height else mfY = Math.floor((height - view.height).toDouble() / 2f).toInt()

        layout.setMargins(left - mfX, top - mfY, layout.rightMargin, layout.bottomMargin)
        layout.width = width
        layout.height = height
        view!!.layoutParams = layout
    }

    fun moveFrame(view: View?, left: Int, top: Int)
    {
        if(view == null) return
        var left = left
        var top = top
        val layout = view!!.layoutParams as FrameLayout.LayoutParams

        if (left == -1) left = layout.leftMargin else left = layout.leftMargin + left
        if (top == -1) top = layout.topMargin else top = layout.topMargin + top
        layout.setMargins(left, top, layout.rightMargin, layout.bottomMargin)
        view!!.layoutParams = layout
    }

    fun hitTestPoint(pos: Point, target: Rect): Boolean
    {
        return if (pos.x > target.left && pos.x < target.right)
        {
            if (pos.y > target.top && pos.y < target.bottom) true else false
        }
        else
        {
            false
        }

    }

    fun hitTest(view: View?, target: View): Boolean
    {
        if(view == null) return false
        val layout = view!!.layoutParams as FrameLayout.LayoutParams
        val targetLayout = target.layoutParams as FrameLayout.LayoutParams
        val rec = Rect(layout.leftMargin,
                layout.topMargin,
                layout.leftMargin + layout.width,
                layout.topMargin + layout.height
        )

        val recTarget = Rect(targetLayout.leftMargin,
                targetLayout.topMargin,
                targetLayout.leftMargin + targetLayout.width,
                targetLayout.topMargin + targetLayout.height
        )
        return ViewUtil.hitTest(rec, recTarget)


    }

    fun hitTest(rec: Rect, target: Rect): Boolean {


        var isHit = false

        val p0 = Point(rec.left, rec.top)
        val p1 = Point(rec.right, rec.top)
        val p2 = Point(rec.left, rec.bottom)
        val p3 = Point(rec.right, rec.bottom)
        val p4 = Point(Math.floor(((rec.left + rec.right) / 2).toDouble()).toInt(),
                Math.floor(((rec.bottom + rec.top) / 2).toDouble()).toInt())

        isHit = ViewUtil.hitTestPoint(p0, target)
        if (isHit == true) return true
        isHit = ViewUtil.hitTestPoint(p1, target)
        if (isHit == true) return true
        isHit = ViewUtil.hitTestPoint(p2, target)
        if (isHit == true) return true
        isHit = ViewUtil.hitTestPoint(p3, target)
        if (isHit == true) return true
        isHit = ViewUtil.hitTestPoint(p4, target)
        return if (isHit == true) true else false

    }


}