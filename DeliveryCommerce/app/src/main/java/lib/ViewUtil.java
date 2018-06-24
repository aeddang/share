package lib;





import android.annotation.SuppressLint;
import android.graphics.Rect;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout.LayoutParams;




public class ViewUtil 
{ 
	
	
	public static void remove(View view)
	{
	   ViewGroup parent=(ViewGroup)view.getParent();
	   if(parent!=null){
		   parent.removeView(view);
		 
	   }
	}
	public static void addView(ViewGroup addView,View view)
	{
	   ViewGroup parent=(ViewGroup)view.getParent();
	   
	   if(parent==null){
		   addView.addView(view,view.getLayoutParams());
	   }else if(parent!=addView){
		   parent.removeView(view);
		   addView.addView(view,view.getLayoutParams());
	   }
	}
	
	@SuppressLint("NewApi")
	public static void setAlpha(View view,float a)
	{
		if (Build.VERSION.SDK_INT < 11) 
		{
	        final AlphaAnimation animation = new AlphaAnimation(a, a);
	        animation.setDuration(0);
	        animation.setFillAfter(true);
	        view.startAnimation(animation);
	    } else{
	        view.setAlpha(a);
		}
	}
	public static void setFrame(View view,Rect rec)
	{
		LayoutParams layout=(LayoutParams)view.getLayoutParams();
		int left=rec.left;
		if(left==-1){
			left=layout.leftMargin;
		}
		
		int top=rec.top;
		if(top==-1){
			top=layout.topMargin;
		}
		int right=rec.right;
		if(right==-1){
			right=layout.rightMargin;
		}
		
		int bottom=rec.bottom;
		if(bottom==-1){
			bottom=layout.bottomMargin;
		}
		
		layout.setMargins(left, top, right, bottom);
		view.setLayoutParams(layout);
	}
	
	
} 
