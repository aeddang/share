package lib.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class ScreenObserver(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {

    private var actualHeight = -1
    private var actualWidth = -1

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (ActivityCore.shareInstence() == null) return
        val proposedheight = View.MeasureSpec.getSize(heightMeasureSpec)
        val proposedWidth = View.MeasureSpec.getSize(widthMeasureSpec)

        // Log.i("ScreenObserver", "proposedheight:"+proposedheight+" proposedWidth:"+proposedWidth);
        if (actualHeight <= 0)
        {
            actualHeight = proposedheight
            actualWidth = proposedWidth
        }

        if (actualWidth == proposedWidth)
        {
            val diff = Math.abs(proposedheight - actualHeight)
            // Log.i("ScreenObserver", "diff :"+diff);
            if (diff > 150)
            {
                if (actualHeight > proposedheight) ActivityCore.shareInstence()?.keyBoardShow(diff)
                else ActivityCore.shareInstence()?.keyBoardHidden()
            }
        }
        else
        {
            ActivityCore.shareInstence()?.resizeScreen(proposedWidth, proposedheight)
        }
        actualHeight = proposedheight
        actualWidth = proposedWidth


    }
}