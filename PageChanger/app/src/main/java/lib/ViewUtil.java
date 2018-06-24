package lib;





import android.graphics.Rect;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
