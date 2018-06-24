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
	public static void setFrame(View view,int left,int top, int width,int height)
	{
		LayoutParams layout=(LayoutParams)view.getLayoutParams();

		if(left==-1){
			left=layout.leftMargin;
		}
		

		if(top==-1){
			top=layout.topMargin;
		}

        int mfX=0;
        int mfY=0;



		if(width==-1 || width==0){
            width=layout.width;
		}else{
            mfX= (int)Math.floor((double)(width-view.getWidth())/2.f);
        }
		

		if(height==-1|| height==0){
            height=layout.height;
		}else{
            mfY= (int)Math.floor((double)(height-view.getHeight())/2.f);
        }
		
		layout.setMargins(left-mfX, top-mfY, layout.rightMargin, layout.bottomMargin);
        layout.width=width;
        layout.height=height;
		view.setLayoutParams(layout);
	}
    public static void moveFrame(View view,int left,int top)
    {
        LayoutParams layout=(LayoutParams)view.getLayoutParams();

        if(left==-1){
            left=layout.leftMargin;
        }else{
            left=layout.leftMargin+left;
        }


        if(top==-1){
            top=layout.topMargin;
        }else{
            top=layout.topMargin+top;
        }
        layout.setMargins(left, top, layout.rightMargin, layout.bottomMargin);
        view.setLayoutParams(layout);
    }
	
} 
