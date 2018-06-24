package lib.view;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.CommonUtil;
import lib.GraphicUtil;
import lib.ViewUtil;




import android.os.Handler;
import android.util.FloatMath;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Bitmap;

import android.graphics.Point;
import android.graphics.Rect;




import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;


import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;


@SuppressLint("FloatMath")
public class ImageEditer extends FrameLayout implements AnimationListener
{
	
	private Context context;
	private ImageView viewImg;
	private Bitmap drawable;
	
	private final int NONE = 0;
	private final int DRAG = 1;
	private final int ZOOM = 2;

	private int mode = NONE;
    private int posX1=0, posX2=0, posY1=0, posY2=0,sPosX,sPosY,sWid,sHei;
    private int  minW, minH, maxW, maxH;
	
	private float oldDist = 1f;
	private float newDist = 1f;
	
	private boolean isAni;
	
	public ImageEditer(Context _context, Bitmap _drawable ) {
		super(_context);
		context=_context;
		drawable=_drawable;
		viewImg= new ImageView(context);
		viewImg.setImageBitmap(drawable);
		viewImg.setScaleType(ScaleType.FIT_XY);
		
		LayoutParams layoutV=new LayoutParams( drawable.getWidth(),drawable.getHeight() ,Gravity.LEFT|Gravity.TOP);
		this.addView(viewImg,layoutV);
		
		
		
    	
    	maxW= drawable.getWidth()*2;
    	maxH= drawable.getHeight()*2;
		
		
	     
	    
	     
	}
	public void init(int width, int height)
	{
		reSize( width, height);
		
		
		LayoutParams layoutV=(LayoutParams)viewImg.getLayoutParams();
      
		
		int tx=(int) Math.floor((width-layoutV.width)/2);
    	int ty=(int) Math.floor((height-layoutV.height)/2);
    	
		layoutV.setMargins(tx, ty,0, 0);
	
    	viewImg.setLayoutParams(layoutV);
		
	}
	public void removeImageEditer()
	{

		viewImg=null;
		drawable=null;
	    ViewUtil.remove(this);
	}
	public void reSize(int width, int height)
	{
        if(width==-1){
        	minH= height;
        	minW= (int)Math.floor(((float)height*((float)maxW/(float)maxH)));
        }else{
        	minW= width;
        	minH= (int)Math.floor(((float)width*((float)maxH/(float)maxW)));
        }
		
    	setEditer();
	}
    private void setEditer(){
		
    	int w=viewImg.getWidth();
    	int h=viewImg.getHeight();
    	if(w>maxW){
    	   w=maxW;
    	}
    	if(w<minW){
    		w=minW;
    	}
    	if(h>maxH){
     	   h=maxH;
     	}
     	if(h<minH){
     		h=minH;
     	}
     	
        int minX=this.getWidth()-w;
    	int minY=this.getHeight()-h;
    	int maxX=0;
    	int maxY=0;
    	
    	LayoutParams layout=(LayoutParams)viewImg.getLayoutParams();
      	int dfX=layout.leftMargin;
		int dfY=layout.topMargin;
		
		
		int tx=dfX;
    	int ty=dfY;
    	if(tx>maxX){
    		tx=maxX;
     	}
     	if(tx<minX){
     		tx=minX;
     	}
     	if(ty>maxY){
      	   ty=maxY;
      	}
      	if(ty<minY){
      		ty=minY;
      	}
      	
      	
		layout.setMargins(tx, ty, 0, 0);
		layout.width=w;
		layout.height=h;
    	viewImg.setLayoutParams(layout);
    	
    	
        TranslateAnimation moveAni=new TranslateAnimation(dfX-tx,0,dfY-ty,0);
      	moveAni.setDuration(300);
		if(dfX!=tx || dfY!=ty){
			if(!isAni){
				isAni=true;
				moveAni.setAnimationListener(this);	
			}
		}
		
		viewImg.startAnimation(moveAni);
    }
    
    public boolean onTouchEvent(MotionEvent event) { 

        if(isAni==true){
        	return false;
        }
    	int act = event.getAction();
        LayoutParams layout;
         switch(act & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:    //첫번째 손가락 터치(드래그 용도)

                posX1 = (int) event.getX();
                posY1 = (int) event.getY();
                layout=(LayoutParams)viewImg.getLayoutParams();
                sPosX = layout.leftMargin;
                sPosY =  layout.topMargin;
                mode = DRAG;

                break;

            case MotionEvent.ACTION_MOVE: 

                if(mode == DRAG) {   // 드래그 중

                    posX2 = (int) event.getX();
                    posY2 = (int) event.getY();

                    layout=(LayoutParams) viewImg.getLayoutParams();
                    layout.topMargin=sPosY-(posY1-posY2);
                	layout.leftMargin=sPosX-(posX1-posX2);
              
                	viewImg.setLayoutParams(layout);

                } else if (mode == ZOOM) {    // 핀치 중

                    newDist = spacing(event);
                     
                    layout=(LayoutParams) viewImg.getLayoutParams();
                    int changeW=(int)Math.floor(sWid*newDist/oldDist);
                    int changeH=(int)Math.floor(sHei*newDist/oldDist);
                    if(changeW>=maxW){
                    	changeW=maxW;
                    }
                    if(changeH>=maxH){
                    	changeH=maxH;
                    }
                    layout.topMargin=sPosY-(int)Math.floor((changeH-sHei)/2.0);
                	layout.leftMargin=sPosX-(int)Math.floor((changeW-sWid)/2.0);
                	layout.width=changeW;
                    layout.height=changeH;
                    viewImg.setLayoutParams(layout);
                }

                break;

            case MotionEvent.ACTION_UP:    // 첫번째 손가락을 떼었을 경우
            case MotionEvent.ACTION_POINTER_UP:  // 두번째 손가락을 떼었을 경우
            	 mode = NONE;
            	
            	 setEditer();
                 break;

            case MotionEvent.ACTION_POINTER_DOWN:  

                mode = ZOOM;
                newDist = spacing(event);
                oldDist = spacing(event);
                layout=(LayoutParams) viewImg.getLayoutParams();
                sPosX = layout.leftMargin;
                sPosY =  layout.topMargin;
                
              
                sWid=layout.width;
                sHei=layout.height;
                break;

            case MotionEvent.ACTION_CANCEL:

            default : 

                break;

        }
        return true;

    }

     

     private float spacing(MotionEvent event) {

        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);

    }
   
    public void onAnimationEnd(Animation arg0) {
    	isAni=false;
 	}

 	public void onAnimationRepeat(Animation arg0) {
 		// TODO Auto-generated method stub
 		
 	}

 	public void onAnimationStart(Animation arg0) {
 		isAni=true;
 		
 	}

}












