package lib;




import android.os.Handler;
import android.util.Log;


public class CustomTimer
{
	
	
	private Handler tHandler;
	private Runnable tRunnable;
	private TimerDelegate delegate;
	private int repeat,count,time;
	
	public CustomTimer(int _time,int _repeat,TimerDelegate _delegate) {
		
		count=0; 
		delegate=_delegate;
		repeat=_repeat;
		time=_time;
		
	}
	
	public void timerStart(){
		timerStop();
		tHandler=new Handler();
		final CustomTimer that=this;
		tRunnable=new Runnable(){
	    	 public void run() {   
	    		 count++;
	    		 delegate.onTime(that);
	    		 if(count==repeat){
	    			 delegate.onComplete(that);
	    			 timerStop();
	    		 }else{
	    			 if(tHandler!=null){
	    			    tHandler.postDelayed(tRunnable, time);
	    			 }
	    		 }
            };
	     };
	     
	     tHandler.postDelayed(tRunnable, time);
	}
	public void timerStop(){
		if(tHandler!=null){
			tHandler.removeCallbacks(tRunnable);
		} 
	    tHandler=null;
		tRunnable=null;
	}
	public void resetTimer(){
		count=0;
		timerStop();
	}
	public void removeTimer(){
		timerStop();
		delegate=null;
	
	}
	public interface TimerDelegate
	{
	    void onTime(CustomTimer timer);
	    void onComplete(CustomTimer timer);
	   
	}
}








