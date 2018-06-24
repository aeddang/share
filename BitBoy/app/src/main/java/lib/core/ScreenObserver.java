package lib.core;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;



public class ScreenObserver extends RelativeLayout {

    private int actualHeight = -1;
    private int actualWidth = -1;

    public ScreenObserver(Context context, AttributeSet attributeSet) {


        super(context, attributeSet);
       // Log.i("ScreenObserver", "ScreenObserver");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(ActivityCore.getInstence()==null){
             return;
        }
        final int proposedheight = View.MeasureSpec.getSize(heightMeasureSpec);
        final int proposedWidth = View.MeasureSpec.getSize(widthMeasureSpec);

       // Log.i("ScreenObserver", "proposedheight:"+proposedheight+" proposedWidth:"+proposedWidth);
        if (actualHeight <= 0)
        {
            actualHeight = proposedheight;
            actualWidth = proposedWidth;
        }
        if(actualWidth == proposedWidth)
        {
            int diff = Math.abs(proposedheight-actualHeight);
           // Log.i("ScreenObserver", "diff :"+diff);
            if(diff>150)
            {
                if (actualHeight > proposedheight){
                    ActivityCore.getInstence().keyBoardShow(diff);

                } else {

                    ActivityCore.getInstence().keyBoardHidden();
                }
            }
        }else
        {
            ActivityCore.getInstence().resizeScreen(proposedWidth,proposedheight);
        }
        actualHeight = proposedheight;
        actualWidth = proposedWidth;




    }
}