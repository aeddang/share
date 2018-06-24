package lib.view;





import lib.ViewUtil;


import android.content.Context;
import android.graphics.Point;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;



public class SlideBox extends FrameLayout implements AnimationListener{
	
	
	
	public SlideBoxDelegate delegate;
	private Point touchStartPoint;
    private FrameLayout currentView;
    private FrameLayout currentFrame;
    private final float maxSpeed=150f;
    private long startTime;
    private Boolean isFrameMove,isTop,isEnd,isSelect;
    private int totalNum,currentIdx,moveAbleID,sizeX,sizeY;
    private Context context;
   
	
	//private Button exitBtn,startBtn;
	public SlideBox(Context _context,SlideBoxDelegate del) {
		
		super(_context); 
		context=_context;
		delegate=del;
		isFrameMove=true; 
		
	}
	public void resetSlide(int idx,int num)
	{
		 totalNum=num;
		 if(idx>=totalNum){
			 idx=totalNum-1;
		 }
		 moveIndex(idx);
	}
	public void setSlide(int idx,int num)
	{
		this.removeAllViews();
	    totalNum=num;
	    currentIdx=idx;
	    moveAbleID=0;
	    sizeX=this.getWidth();
	    sizeY=this.getHeight();
	    
	    currentView=new FrameLayout(context);
	    currentFrame=new FrameLayout(context);
	    LayoutParams layoutV=new LayoutParams( sizeX*3,sizeY ,Gravity.LEFT|Gravity.TOP);
	   
	    layoutV.leftMargin=-sizeX;
	    addView(currentView,layoutV );
	    
	    LayoutParams layoutF=new LayoutParams( sizeX,sizeY,Gravity.LEFT|Gravity.TOP );
	   
	    layoutF.leftMargin=sizeX;
	    currentView.addView(currentFrame, layoutF);
	    
	    if(delegate!=null){
	        
	        delegate.sliderInit(this, currentFrame, currentIdx);
	        changeFrameComplete();
	    }

	}
	public void clearSlide()
	{
		if(currentView==null){
			return;
		}
		int num=currentView.getChildCount();
		for(int i=0;i<num;++i){
			FrameLayout core=(FrameLayout) currentView.getChildAt(i);
			if(delegate!=null){delegate.clearFrame(this, core);}
		}
	}
	public void removeSlide()
	{

		currentView=null;
	    currentFrame=null;
	    this.removeAllViews();
	    delegate=null;
	    ViewUtil.remove(this);
	}
	public Boolean moveLeft()
	{
	    return moveSlide(currentIdx+1);
	}
	public Boolean moveRight(){
	    return  moveSlide(currentIdx-1);
	}

	public Boolean moveSlide(int idx)
	{   
	    if(isFrameMove==true){
	        return false;
	    }
	    if(currentIdx==idx){
	        return false;
	    }
	    if(idx<0)
	    {
	        return false;
	    }
	    if(idx>=totalNum)
	    {
	        return false;
	    }
	    int dr;
	    int gep=currentIdx-idx;
	    
	    if(gep==1){
	        dr=-1;
	    }else if(gep==-1){
	        dr=1;
	    }else{
	        moveIndex(idx);
	        return true;
	    }
	    changeFrameStart();
	    changeFrame(dr);
	    return true;
	    //[self changeFrameComplete];
	}
	public void moveIndex(int idx)
	{ 
	    currentFrame=null;
	    currentIdx=idx;
	    changeFrameComplete();
	    currentFrame=new FrameLayout(context);
	    currentView.addView(currentFrame, new LayoutParams( sizeX,sizeY ,Gravity.LEFT|Gravity.TOP));
	    
	    if(delegate!=null){
	        delegate.sliderInit(this,  currentFrame, currentIdx);
	    }
	    changeFrameComplete();
	}
	private void changeFrameStart()
	{
	    if(currentView==null){
	        return;
	    }
	    
	    isFrameMove=true;
	    FrameLayout leftFrame;
	    FrameLayout rightFrame;
	    
	    leftFrame=new FrameLayout(context);
	    rightFrame=new FrameLayout(context);
	    
	    LayoutParams layoutL=new LayoutParams( sizeX,sizeY,Gravity.LEFT|Gravity.TOP);
	    LayoutParams layoutR=new LayoutParams( sizeX,sizeY,Gravity.LEFT|Gravity.TOP);
	 
	    
	    layoutL.leftMargin=0;
	    layoutR.leftMargin=sizeX*2;
	    currentView.addView(leftFrame, layoutL);
	    currentView.addView(rightFrame, layoutR);
	  
	    
	    
	    if(totalNum<=1){
	        moveAbleID=0;
	    }else if(currentIdx==0){
	        moveAbleID=1;
	    }else if(currentIdx==totalNum-1){
	        moveAbleID=-1;
	    }else{
	        moveAbleID=2;
	    }
	    if(delegate!=null){
	       delegate.moveInit(this,leftFrame, rightFrame , currentIdx);
	    }
	}
	private void changeFrame(int dr)
	{
	    
	    if(currentView==null){
	        return;
	    }
	    
	    if(moveAbleID==0){
	        dr=0;
	    }else if(moveAbleID==2){
	        //dr=dr
	    }else if(moveAbleID!=dr){
	        dr=0;
	    }
	    
	  
	    int pos;
	    
	    int num=currentView.getChildCount();
		for(int i=0;i<num;++i){
			FrameLayout core=(FrameLayout) currentView.getChildAt(i);
			LayoutParams layout=( LayoutParams)core.getLayoutParams();
			
			pos=layout.leftMargin-sizeX;
			if(pos<0 && dr==-1){
				    currentFrame=core;
		    }else if(pos>=sizeX && dr==1){
		            currentFrame=core;
		    }
		}
	    currentIdx=currentIdx+dr;
	    
	    
	    int pozL=(sizeX*-dr)-sizeX;
        LayoutParams layoutV=( LayoutParams)currentView.getLayoutParams();
       
	    int dfX=layoutV.leftMargin;
	    layoutV.leftMargin=pozL;
        currentView.setLayoutParams(layoutV);
        
        TranslateAnimation moveAni=new TranslateAnimation(dfX-pozL,0,0,0);
		moveAni.setDuration(200);
		moveAni.setAnimationListener(this);
		currentView.startAnimation(moveAni);
		
	    
	    
	}
	public void onAnimationEnd(Animation arg0) {
		changeFrameComplete();
	}
	private void changeFrameComplete()
	{
	    isFrameMove=false;
	    if(currentView==null){
	        return;
	    }
	   
	    int num=currentView.getChildCount();
	   
		for(int i=num-1;i>=0;--i){
			FrameLayout core=(FrameLayout) currentView.getChildAt(i);
			if(core!=currentFrame){
				 delegate.clearFrame(this, core);
				 core.removeAllViews();
				 ViewUtil.remove(core);
			
	        }else{
	        	LayoutParams layout=( LayoutParams)currentFrame.getLayoutParams();
	        	layout.leftMargin=sizeX;
	        	currentFrame.setLayoutParams(layout);
	        }
		}
	  
	    LayoutParams layoutV=( LayoutParams)currentView.getLayoutParams();
	    layoutV.leftMargin=-sizeX;
        currentView.setLayoutParams(layoutV);
	    if(delegate!=null){
	        if(currentFrame==null){
	            return;
	        }
	        delegate.moveComplete(this, currentFrame, currentIdx);
	    }
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		this.changeSize();
	}
	public void changeSize()
	{
		sizeX=this.getWidth();
		sizeY=this.getHeight();
		
		if(currentView==null || currentFrame==null){
			return;
			
		}
		
		LayoutParams layoutV=(LayoutParams) currentView.getLayoutParams();
		layoutV.width=sizeX*3;
		layoutV.height=sizeY;
		layoutV.leftMargin=-sizeX;
		LayoutParams layoutF=(LayoutParams) currentFrame.getLayoutParams();
		layoutF.width=sizeX;
		layoutF.height=sizeY;
		layoutF.leftMargin=sizeX;
		currentView.setLayoutParams(layoutV);
		currentFrame.setLayoutParams(layoutF);
		delegate.frameSizeChange(this,currentFrame,currentIdx);
	}
	
	public boolean onTouchEvent(MotionEvent event) 
  	{       
    	if(currentView==null){
    		return true;
    	}
  		int action = event.getAction();  
  	
  		
        int x = (int)Math.floor(event.getX());  
        int y = (int)Math.floor(event.getY());
        Point  location=new Point(x,y);
       
  	    switch (action) 
  		{        
  		     case MotionEvent.ACTION_DOWN :   
  		    	isTop=false;
  			    isEnd=false;
  			    isSelect=true;
  			    delegate.onTouchStart(this);
  			    touchStartPoint=new Point(x,y);
  			    if(isFrameMove==true){
  			        return true;
  			    }
  			    startTime = System.currentTimeMillis();
  			    changeFrameStart();
  			     break;   
  		     case MotionEvent.ACTION_MOVE :
  		    	 if(isFrameMove==true){
  			        int pos=location.x-touchStartPoint.x;
  			        LayoutParams layoutV=( LayoutParams)currentView.getLayoutParams();
  			        layoutV.leftMargin=pos-sizeX;
  		            currentView.setLayoutParams(layoutV);
  		    		
  			    }
  			    if(delegate==null){
  			        return true;
  			    }
  			    if(Math.abs(touchStartPoint.x-location.x)>30){
  			        
  			        isSelect=false;
  			        delegate.onTouchEnd(this);
  			    } 
  			    if(Math.abs(touchStartPoint.y-location.y)>30){
  			    	delegate.onTouchEnd(this); 
  			        isSelect=false;
  			    }
  			    //
  			    if(moveAbleID==1 && isTop==false){
  			       
  			        if(currentView.getLeft()>(sizeX/2)){
  			             
  			            isTop=true;
  			        }
  			    }else if(moveAbleID==-1 && isEnd==false){
  			        
  			        if(currentView.getLeft()<-(sizeX/2)){
  			           
  			            isEnd=false;
  			            
  			        }
  			    }
  		    	 break;  
  		     case MotionEvent.ACTION_UP :   
  		    	if(isTop){
  			        delegate.onTopSlide(this);
  			    }
  			    if(isEnd){
  			    	delegate.onEndSlide(this);
  			    }
  			    isTop=false;
  			    isEnd=false;
  			    if(isFrameMove){
  			        long time = System.currentTimeMillis();
  			        int moveRange= touchStartPoint.x - location.x;
  			        float speed = ( moveRange*1000)/(float)(time - startTime);
  	  			   // Log.i("","speed="+speed);
  			        if (speed > maxSpeed ){
  			            changeFrame(1);
  			        }else if (speed < -maxSpeed){
  			            changeFrame(-1);
  			        }else{
  			            changeFrame(0);
  			        }    
  	  			}
  			    if(isSelect){
  			    	delegate.onTouchEnd(this);
  			    	delegate.selectSlide(this, currentIdx);
  			    }
			    break; 
  		
  		}
  		return true;
  	}
    
	
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public interface SlideBoxDelegate
	{
		void sliderInit(SlideBox slideView, FrameLayout cframe,int idx);
		void moveInit(SlideBox slideView,FrameLayout lframe,FrameLayout rframe,int idx);
		void moveComplete(SlideBox slideView,FrameLayout cframe,int idx);
		void clearFrame(SlideBox slideView,FrameLayout cframe);
		void frameSizeChange(SlideBox slideView, FrameLayout cframe,int idx);
		
		void onTopSlide(SlideBox slideView);
		void onEndSlide(SlideBox slideView);
		void selectSlide(SlideBox slideView,int idx);
		
		void onTouchStart(SlideBox slideView);
		void onTouchEnd(SlideBox slideView);
	}


}
