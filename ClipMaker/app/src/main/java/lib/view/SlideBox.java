package lib.view;





import java.util.ArrayList;

import lib.ViewUtil;

import lib.view.Gesture.GestureDelegate;


import android.content.Context;
import android.graphics.Point;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;



public class SlideBox extends FrameLayout implements AnimationListener,GestureDelegate{
	
	
	
	public SlideBoxDelegate delegate;
	private Gesture gesture;
	
	private FrameLayout body;
    private FrameLayout currentFrame,addFrame;
    
    private final float maxSpeed=150f;
 
    private Boolean isMoveAble,isMoving,isSliding,selfStart,isActive;
    
    private String finalGesture;
    private int totalNum,currentIdx,sizeX,sizeY,currentDr;
    private Context context;
   
	
	//private Button exitBtn,startBtn;
	public SlideBox(Context _context) {
		
		super(_context); 
		context=_context;
	    gesture=new Gesture(this,false,true);
	}
	public SlideBox(Context _context,AttributeSet attrs) {
		
		super(_context,attrs,0);
		context=_context;
		gesture=new Gesture(this,false,true);
	}
	public void initBox(SlideBoxDelegate del)
	{
		delegate=del;
		finalGesture="";
		isMoveAble=false;
		isMoving=false;
		isSliding=false;
		selfStart=false;
        isActive=true;
		currentDr=0;
	}
    public void setActive(boolean ac)
    {
        isActive=ac;
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
	    sizeX=this.getWidth();
	    sizeY=this.getHeight();
	    
	    
	    body=new FrameLayout(context);
	    
	    LayoutParams layoutV=new LayoutParams( sizeX*3,LayoutParams.MATCH_PARENT ,Gravity.LEFT|Gravity.TOP);
	    layoutV.leftMargin=-sizeX;
	    addView(body,layoutV );
	    moveIndex(idx);
	    
	   

	}
	public void moveLeft()
	{
		moveInit(1);
		moveSlide(1);
	}
	public void moveRight(){
		moveInit(-1);
	    moveSlide(-1);
	}
	public void moveIndex(int idx){
		
		
		
		removeCurrentView();
		removeAddView();
		
		
		currentIdx=idx;
		currentFrame=new FrameLayout(context);
        LayoutParams layout=new LayoutParams( sizeX,sizeY,Gravity.LEFT|Gravity.TOP);
	    layout.leftMargin=sizeX;
	    body.addView(currentFrame, layout);
		
	    
	    isMoving=false;
		isMoveAble=false;
		isSliding=false;
		currentDr=0;
	    if(delegate!=null){delegate.moveInit(this,currentFrame, 0 , currentIdx);}
	    if(delegate!=null){delegate.moveComplete(this, currentFrame, currentIdx);}
	    
	    //Log.i("","count: "+body.getChildCount());
		
	}
	public void removeSlide()
	{
        body=null;
		removeAddView();
		removeCurrentView();
	    removeAllViews();
	    delegate=null;
	    ViewUtil.remove(this);
	}
	private void removeCurrentView() 
	{
	    if(currentFrame!=null){
	    	if(delegate!=null){delegate.clearFrame(this, currentFrame);}
	    	ViewUtil.remove(currentFrame);
	    	currentFrame=null;
	    }
	}
	private void removeAddView() 
	{
	    if(addFrame!=null){
	    	if(delegate!=null){delegate.clearFrame(this, addFrame);}
	    	ViewUtil.remove(addFrame);
	    	addFrame=null;
	    }
	}
	private void moveInit(int dr) 
	{
		
		if(isMoveAble==true && currentDr==dr){
		    return;
		}
		currentDr=dr;
		isMoveAble=true;
	    removeAddView();
        
	    int idx=currentIdx+dr;
		if(idx<0 || idx>=totalNum){
		   return;
		}
		
		
		
        addFrame=new FrameLayout(context);
        LayoutParams layout=new LayoutParams( sizeX,sizeY,Gravity.LEFT|Gravity.TOP);
	    layout.leftMargin=(dr+1)*sizeX;
	    
	 
	    body.addView(addFrame, layout);
		delegate.moveInit(this,addFrame, dr , currentIdx);
    }
    private void moveSlide(int dr) 
	{
        
		if(isSliding==true){
		   return;
		}

		if(isMoveAble==false){
		    moveInit(dr);
		}
		
		if(isMoving==true){
		    return;
		}
       
        if(totalNum!=-1){
		
		    int idx=currentIdx+dr;
			if(idx<0 || idx>=totalNum){
			   dr=0;
			}
		}
        
		isMoving=true;
		isSliding=true;
		
		int tx=-(dr+1)*sizeX;
        LayoutParams layoutV=( LayoutParams)body.getLayoutParams();
       
	    int dfX=layoutV.leftMargin;
	    layoutV.leftMargin=tx;
        body.setLayoutParams(layoutV);
        currentIdx=currentIdx+dr;
        TranslateAnimation moveAni=new TranslateAnimation(dfX-tx,0,0,0);
		moveAni.setDuration(200);
		moveAni.setAnimationListener(this);
		body.startAnimation(moveAni);
		

    }
	
	public void onAnimationEnd(Animation arg0) {
		
		LayoutParams layoutV=( LayoutParams)body.getLayoutParams();
		
		
		if(layoutV.leftMargin==-sizeX){
			    removeAddView();
        }else{
            if(delegate!=null){delegate.clearFrame(this, currentFrame);}
            ViewUtil.remove(currentFrame);
            currentFrame=addFrame;
            layoutV.leftMargin=-sizeX;

            LayoutParams layout=new LayoutParams( sizeX,sizeY,Gravity.LEFT|Gravity.TOP);
            layout.leftMargin=sizeX;
            currentFrame.setLayoutParams(layout);
            body.setLayoutParams(layoutV);
            if(selfStart==true){

            }
            selfStart=false;
            if(delegate!=null){delegate.moveComplete(this, currentFrame, currentIdx);}
        }
        addFrame=null;
        isMoving=false;
        isMoveAble=false;
        isSliding=false;
        currentDr=0;
		
	}
	
	public void stateChange(Gesture g,String e){

        Log.i("","SLIDEBOX Gesture : "+e);
    	Point d=g.changePosA.get(0);
    	if(e==Gesture.START){
    		touchStart();
    	}else if(e==Gesture.MOVE_H){
    		touchMove(g.changePosA.get(0).x);
    		
    	}else if(e==Gesture.END){
    		touchEnd();
    	}
    }
	public void gestureComplete(Gesture g,String e){

		if(e.equals(Gesture.PAN_RIGHT) || e.equals(Gesture.PAN_LEFT)){
			finalGesture=e;
		}else if(e.equals(Gesture.TOUCH)){
            if(delegate!=null){delegate.selectSlide(this, currentIdx);}
        }
		
	}	
	private void touchStart()
	{
	    
		if(isSliding==true){
		   return;
		}
		if(isMoving==true){
		   return;
		}
		selfStart=true;
		isMoving=true;
       
		
    }
    private void touchMove(int point)
	{
        if(isActive==false){

            return;
        }
    	if(isSliding==true){
		   return;
		}
	    
		if(point<0){
		    moveInit(1);
		}else if(point>0){
		    moveInit(-1);
		}
		
		if(isMoveAble==false){
		    return;
		}
		int tx=-sizeX+point;
	    LayoutParams layoutV=( LayoutParams)body.getLayoutParams();
	    layoutV.leftMargin=tx;
        body.setLayoutParams(layoutV);        
		
	}
	
	private void touchEnd()
	{
		if(isSliding==true){
		   return;
		}
		if(isMoveAble==false){
		    return;
		}
        
        int dr;
        if(finalGesture.equals(Gesture.PAN_RIGHT)){
			dr=-1;							
		}else if(finalGesture.equals(Gesture.PAN_LEFT)){
			dr=1;							   
		}else{
			dr=0;
			
		}
        finalGesture="";
		isMoving=false;
		moveSlide(dr);

	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		super.onSizeChanged(w, h, oldw, oldh);
		this.changeSize();
	}
	public void changeSize()
	{
		sizeX=this.getWidth();
		sizeY=this.getHeight();
		
		if(body==null || currentFrame==null){
			return;
			
		}
		LayoutParams layoutV=(LayoutParams) body.getLayoutParams();
		layoutV.width=sizeX*3;
		layoutV.height=LayoutParams.MATCH_PARENT;
		layoutV.leftMargin=-sizeX;
		LayoutParams layoutF=(LayoutParams) currentFrame.getLayoutParams();
		layoutF.width=sizeX;
		layoutF.height=LayoutParams.MATCH_PARENT;
		layoutF.leftMargin=sizeX;
		body.setLayoutParams(layoutV);
		currentFrame.setLayoutParams(layoutF);
		View child=currentFrame.getChildAt(0);
		if(child!=null){
			LayoutParams layoutC=(LayoutParams) child.getLayoutParams();
			layoutC.width=sizeX;
			layoutC.height=LayoutParams.MATCH_PARENT;
			child.setLayoutParams(layoutC);
		}
		delegate.frameSizeChange(this,currentFrame,currentIdx);
	}
	
	public boolean onTouchEvent(MotionEvent event) 
  	{       
		Log.i("","SLIDEBOX THOUGTH"+event.toString());
		boolean trigger=gesture.adjustEvent(event);
  		return trigger;
		
  	}
	
	
	public void rotateChange(Gesture g,float rotate){}
	public void pinchChange(Gesture g,float dist){}
	public void gestureChange(Gesture g,String e){}
	
	
	
	
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	public interface SlideBoxDelegate
	{
		
		void moveInit(SlideBox slideView,FrameLayout frame,int dr,int idx);
		void moveComplete(SlideBox slideView,FrameLayout cframe,int idx);
		void clearFrame(SlideBox slideView,FrameLayout cframe);
		void frameSizeChange(SlideBox slideView, FrameLayout cframe,int idx);
        void selectSlide(SlideBox slideView,int idx);
		
	
	}

	
}
