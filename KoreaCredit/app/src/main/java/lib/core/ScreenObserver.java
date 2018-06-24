package lib.core;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import lib.CustomTimer;
import lib.CustomTimer.TimerDelegate;

public class ScreenObserver extends RelativeLayout {

    public ScreenObserver(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.i("ScreenObserver", "onMeasure : "+widthMeasureSpec+" "+heightMeasureSpec);

        if(ActivityCore.getInstence()==null){
             return;
        }
        final int proposedheight = View.MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();

        if (actualHeight > proposedheight){
            Log.i("ScreenObserver", "Keyboard is shown");
            ActivityCore.getInstence().keyBoardShow();

        } else {
            Log.i("ScreenObserver", "Keyboard is hidden");
            ActivityCore.getInstence().keyBoardHidden();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}