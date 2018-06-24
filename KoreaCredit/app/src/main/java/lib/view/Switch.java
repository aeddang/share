package lib.view;














import lib.ViewUtil;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;



import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;




public class Switch extends FrameLayout{
	
	
	public SwitchDelegate delegate;
	private ImageView bgOn,bgOff,btn;
	private Boolean isOn;
	private int startPoint;
	
	
	public Switch(Context context,int bgOnId,int bgOffId,int btnId) {
		 super(context);
		 LayoutParams bglayout=new  LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,Gravity.LEFT);
		 LayoutParams btnlayout=new  LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT,Gravity.LEFT);
		 startPoint=0;
		 isOn=false;
		 
		 bgOn=new ImageView(context);
		 bgOn.setScaleType(ScaleType.FIT_XY);
		 bgOn.setImageResource(bgOnId);
		 
		 bgOff=new ImageView(context);
		 bgOff.setScaleType(ScaleType.FIT_XY);
		 bgOff.setImageResource(bgOffId);
			
		 btn=new ImageView(context);
		 btn.setScaleType(ScaleType.FIT_START);
		 btn.setImageResource(btnId);
		 
		 this.addView(bgOn,bglayout);
		 this.addView(bgOff,bglayout);
		 this.addView(btn,btnlayout);
		 setValueFn(isOn);
	}
    public Boolean getValue(){
		
		return isOn;
		
	}
	public void changeValue(){
		
		if(isOn){
			 setValue(false);
		}else{
			 setValue(true);
		}
		
	}
	public void removeSwitch()
	{
	    this.removeAllViews();
	    delegate=null;
	    ViewUtil.remove(this);
	}
    public void setValue(Boolean _isOn){
    	 if(setValueFn(_isOn)){
    		 if(delegate!=null){
     	         delegate.onChangeValue(this);
     	     } 
    	 }
    	 
    	 
	}
	
    public Boolean setValueFn(Boolean _isOn){
    	Boolean ac;
    	if(isOn!=_isOn)
    	{
    		ac=true;
    	}else{
    		ac=false;
    	}	
    	isOn=_isOn;
    	
    	LayoutParams btnlayout=(LayoutParams)btn.getLayoutParams();
    	int dfX=btnlayout.leftMargin;
    	int pozX=0;
    	if(isOn){
    		bgOn.setVisibility(View.VISIBLE); 
    		bgOff.setVisibility(View.GONE); 
    		pozX=this.getWidth()- btn.getHeight();
    		
		}else{
			bgOff.setVisibility(View.VISIBLE); 
    		bgOn.setVisibility(View.GONE); 
    	}
    	btnlayout.leftMargin=pozX;
    	btn.setLayoutParams(btnlayout);
    
        TranslateAnimation moveAni=new TranslateAnimation(dfX-pozX,0,0,0);
		moveAni.setDuration(200);
        btn.startAnimation(moveAni);
    	return ac;
	}
    
    public boolean onTouchEvent(MotionEvent event) 
  	{       
    	if(btn==null){
    		return true;
    	}
  		int action = event.getAction();  
  	    int x = (int)Math.floor(event.getX());  
       
       
  	    switch (action) 
  		{        
  		 
  		     case MotionEvent.ACTION_DOWN :   
  		    	startPoint=x;
  		     case MotionEvent.ACTION_MOVE :
  		    	LayoutParams btnlayout=(LayoutParams)btn.getLayoutParams();
  		    	int posX=x-(int)Math.floor(btn.getWidth()/2);
  		    	Rect rect = new Rect(0,10, this.getWidth(), this.getHeight());
                if(!rect.contains((int)event.getX(), (int)event.getY())){
  		            
                	
  		        }else{
  		        	int max=this.getWidth()- btn.getWidth();
  		        	if(posX<0){
  		        		posX=0;
  		        	}else if(posX>max){
  		        		posX=max;
  		        	}
  		        	btnlayout.leftMargin=posX;
  	  		    	btn.setLayoutParams(btnlayout);
  	  		    	break;  
  		        }

  		    case MotionEvent.ACTION_UP :   
  		    	
  		    	int div=(int)Math.floor(this.getWidth()/2);
  		    	int gep=Math.abs(x-startPoint);
  		    	if(gep>5){
  		    		if(x<startPoint){
  	  		    		setValue(false);
  	  		    	}else{
  	  		    		setValue(true);
  	  		    	}
  		    	}else{
  		    		if(x<div){
  	  		    		setValue(false);
  	  		    	}else{
  	  		    		setValue(true);
  	  		    	}
  		    	}
  		    	break; 
  		
  		}
  		return true;
  	}
    
	
	public interface SwitchDelegate
	{
		void onChangeValue(Switch view);
		
	 
	}
}
