



package lib.view;





import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;

import android.content.Context;

import android.widget.FrameLayout;




public class ScoreList extends FrameLayout{
	
	public ScoreList(Context context,int rayOutID) {
		 super(context);
		 View.inflate(context, rayOutID,this); 
		 
	}
	
	public void movePos(int pos,long delay,int uint){
		
		
		LayoutParams layout=(LayoutParams)this.getLayoutParams();
		int dfX=layout.leftMargin;
		int dfY=layout.topMargin;
		int posT=-uint * pos;
		layout.setMargins(dfX, posT, 0, 0);
		//Log.i("score pos","posT="+posT);
		setLayoutParams(layout);
		
		TranslateAnimation moveAni=new TranslateAnimation(0,0,dfY-posT,0);
		moveAni.setStartOffset(delay);
		moveAni.setDuration(300);
		this.startAnimation(moveAni);
		
	}
	
}
