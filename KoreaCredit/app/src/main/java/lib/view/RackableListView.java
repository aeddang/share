package lib.view;



import lib.CustomTimer;
import lib.ViewUtil;
import lib.CustomTimer.TimerDelegate;
import lib.core.ActivityCore;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;



import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ScrollView;




public class RackableListView extends ListView {
	
	
	private boolean mScrollable = true;
	public boolean isHorizontal = true;
	public boolean isVertical  = true;
	public Point currentPoint; 
	private Point prevPoint; 
	private final int diff=2;
	private boolean isFirstEvent;
	private  ViewGroup activityCore;
	
	public RackableListView(Context context) {
		 super(context);
		 init();
	}
	public RackableListView(Context context,AttributeSet attrs) {
		super(context,attrs,0);
		init();
	}
	
	private void init() {
		prevPoint=null;
		isFirstEvent=true;
		this.setCacheColorHint(0);
    }
	public void setParent( ViewGroup parent) {
		activityCore=parent;
	}

	@Override
    public boolean onTouchEvent(MotionEvent ev) {
    	int x = (int)Math.floor(ev.getX());  
        int y = (int)Math.floor(ev.getY());
        currentPoint=new Point(x,y);
    	switch (ev.getAction()) {
    		case MotionEvent.ACTION_DOWN:
    			
    			if (isFirstEvent==true){
    				prevPoint=null;
    		        return super.onTouchEvent(ev);
                }else{
                	prevPoint=null;
                	isFirstEvent=true;
                	return false;
                }
            case MotionEvent.ACTION_MOVE:
            	
            	
            	if(isHorizontal==false || isVertical==false){
	            	
	            	if(prevPoint==null){
	            		prevPoint=new Point(x,y);
	            		mScrollable=true;
	            		
	            	}else{ 
	            	
	            		int dx=Math.abs(prevPoint.x-x);
	            		int dy=Math.abs(prevPoint.y-y);
	            		
	            		if(dx>=diff || dy >=diff){
	            			
	            			if(isHorizontal==true){
		                    	if(dx>=dy){
		                    		mScrollable=true;
		                    	}else{
		                    		mScrollable=false;
		                    	}
		                    	
		                    }else if(isVertical==true){
		                    	if(dy>=dx){
		                    		mScrollable=true;
		                    	}else{
		                    		mScrollable=false;
		                    	}
		                    	
		                    }else{
		                    	mScrollable=false;
		                    }
		            		prevPoint.x=x;
		            		prevPoint.y=y;
	            			
	            		}else{
	            			mScrollable=true;
	            			
	            		}
	            		
	            	}
                    
            	}else{
            		mScrollable=true;
            		
            	}
            	
                if (mScrollable==true){
                	//Log.i("","ACTION_MOVE");
                	return super.onTouchEvent(ev);
                }else{
                	
                	if(activityCore!=null){
	                	//Log.i("","ACTION_MOVE_END");
	                	isFirstEvent=false;
	                	long downTime = SystemClock.uptimeMillis();
	                	long eventTime = SystemClock.uptimeMillis();
	                	MotionEvent down_event = MotionEvent.obtain(downTime, eventTime,   MotionEvent.ACTION_DOWN, prevPoint.x,prevPoint.y, 0);
	                    activityCore.dispatchTouchEvent(down_event);
	                    return false;
                	}else{
                		activityCore=(ViewGroup)this.getParent();
                		return super.onTouchEvent(ev);
                	}
                	
                }
                
             	case MotionEvent.ACTION_UP:
             	isFirstEvent=true;
            	
            default:
            	
            	return super.onTouchEvent(ev);
        }
    }

}
