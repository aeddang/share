package com.aeddang.pagechanger;





import java.util.ArrayList;

import com.aeddang.pagechanger.R;

import lib.CommonUtil;
import lib.GrapicUtil;
import lib.PhotoSelecter;
import lib.PhotoSelecter.PhotoSelecterDelegate;
import lib.core.PageObject;
import lib.core.ViewCore;
import lib.opengl.GLESObject;
import lib.opengl.GLESRenderer;
import lib.opengl.GLESRenderer.GLESRendererDelegate;
import lib.opengl.GLESRendererInfo;
import lib.opengl.GLESSurfaceView;
import lib.opengl.GLESTransform;
import lib.opengl.assets.GLESCube;
import lib.opengl.assets.GLESObj;
import lib.opengl.assets.GLESRect;
import lib.view.Gesture;
import lib.view.Gesture.GestureDelegate;



import android.os.Build;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.FrameLayout;

import android.widget.ImageView;




@SuppressLint("NewApi")
public class Page extends ViewCore implements GestureDelegate, GLESRendererDelegate, OnClickListener, PhotoSelecterDelegate{
	
	
	
	private static final int TYPE_EYE=0;
	private static final int TYPE_LIGHT=1;
	private static final int TYPE_OBJECT=2;
	
	
	private GLESSurfaceView glesSurfaceView;
	private GLESRenderer glesRenderer;
	private FrameLayout body;
	private Gesture gesture;
	private float gestureSpd;
	
	private LinearLayout uiBoxTop,uiBoxBottom;
	private Button lightBtn,eyeBtn,objectBtn,textureBtn,albumBtn,cameraBtn,addBtn,removeBtn;
	
	private int type=TYPE_EYE;
	private int eyePosition;
	private boolean isUiHidden=false;
	
	private GLESTransform startPoint;
	private ArrayList<GLESTransform> startPoints;
	private double eyeDgreeH,eyeDgreeV;
	private PhotoSelecter photoSelecter;
	
	
	
	public Page(Context context, PageObject  pageInfo) 
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page,this);  
	    body=(FrameLayout) findViewById(R.id._body);
	    
	    uiBoxTop=(LinearLayout) findViewById(R.id._uiBoxTop);
	    uiBoxBottom=(LinearLayout) findViewById(R.id._uiBoxBottom);
	    
	    lightBtn=(Button) findViewById(R.id._lightBtn);
	    eyeBtn=(Button) findViewById(R.id._eyeBtn);
	    objectBtn=(Button) findViewById(R.id._objectBtn);
	    textureBtn=(Button) findViewById(R.id._textureBtn);
	    albumBtn=(Button) findViewById(R.id._albumBtn);
	    cameraBtn =(Button) findViewById(R.id._cameraBtn);
	    addBtn =(Button) findViewById(R.id._addBtn);
	    removeBtn =(Button) findViewById(R.id._removeBtn);
	    
	    lightBtn.setOnClickListener(this);
	    eyeBtn.setOnClickListener(this);
	   
	    objectBtn.setOnClickListener(this);
	    textureBtn.setOnClickListener(this);
	    albumBtn.setOnClickListener(this);
	    cameraBtn.setOnClickListener(this);
	    addBtn.setOnClickListener(this);
	    removeBtn.setOnClickListener(this);
	    startPoint=new GLESTransform();
	    gestureSpd=0.1f;
	    gesture=new Gesture(this,true,true);
	     
	    MainActivity ac=(MainActivity)MainActivity.getInstence();
	    photoSelecter=ac.photoSelecter;
	    photoSelecter.delegate=this;
	    
     	glesSurfaceView = new GLESSurfaceView(mainActivity,null);
        glesRenderer=glesSurfaceView.mRenderer;
        glesRenderer.delegate=this;
        glesRenderer.info.lensType=GLESRenderer.PERSPECTIVE;
        glesRenderer.info.near=5.f;
        glesRenderer.info.far=120.f;
      
       
        GLESCube program;
        int unit=2;
        int num=unit*unit*unit;
        float lange=2.5f;
        float offset=(lange*unit/2) -(lange/2);
       
        ArrayList<String> colors=new ArrayList<String>();
        colors.add("#ffffff");
       
        ArrayList<Integer> textures=new ArrayList<Integer>();
        textures.add(com.aeddang.pagechanger.R.drawable.texture);
       
        
        for(int i=0;i<num;++i){
        	int idx=i%colors.size();
        	int idxt=i%textures.size();
        	
            program=new GLESCube(colors.get(idx),textures.get(idxt),null);
           
        	program.transform.x=(float) ((i%unit)*lange)-offset;
        	program.transform.y=(float) (Math.floor((i%(unit*unit))/unit)*lange)-offset;
        	program.transform.z=(float) (Math.floor(i/(unit*unit))*lange)-offset;
        	glesRenderer.addView(program);
        }
         
        
        program=new GLESCube(colors.get(0),textures.get(0),null);
        
    	program.transform.height=0.1f;
        program.transform.width=10;
        program.transform.length=10;
    	program.transform.x=0;
    	program.transform.y=-(lange+program.transform.height)*unit/2;
    	program.transform.z=0;
    	program.selectable=false;
    	glesRenderer.addView(program);
        
        glesRenderer.loadTexture();
	       
    }
	
	
	
	public void onClick(View arg) {
		
		 if(arg==lightBtn){
			 type=TYPE_LIGHT;
			 glesRenderer.removeAllSelect();
		 }else if(arg==eyeBtn){
			 type=TYPE_EYE;
			 glesRenderer.removeAllSelect();
		 }else if(arg==objectBtn){
			 type=TYPE_OBJECT;
			 glesRenderer.allSelect();
		 }else if(arg==textureBtn){
			 
		 }else if(arg==albumBtn){
			 MainActivity ac=(MainActivity)MainActivity.getInstence();
			 ac.photoSelecter.takeAlbumAction();
		 }else if(arg==cameraBtn){
			 MainActivity ac=(MainActivity)MainActivity.getInstence();
			 ac.photoSelecter.takePhotoAction();
		 }else if(arg==addBtn){
			 GLESCube program=new GLESCube("#ffffff",R.drawable.texture,null);
			 program.nTextureID=glesRenderer.textureFactory.getTextureIndexById(Integer.toString(R.drawable.texture));
	         glesRenderer.addView(program);
		 }else if(arg==removeBtn){
			 GLESObject program;
			 int size=glesRenderer.pikPrograms.size();
			 if(size>0){
				 for(int i=size-1;i>=0;--i){
					program=glesRenderer.pikPrograms.get(i);
					glesRenderer.removeView(program);
						
				 }
			 }
			 
			 
		 }
		
	}
	public void getPhotoComplete(Bitmap photo){
		GLESObject program;
		
		GLESRendererInfo saveData=glesSurfaceView.mRenderer.info;
		glesSurfaceView.removeSurfaceView();
		
		glesSurfaceView = new GLESSurfaceView(mainActivity,saveData);
        glesRenderer=glesSurfaceView.mRenderer;
        glesRenderer.delegate=this;
        String imgName=CommonUtil.getCurrentTimeCode();
        Log.i("","a imgName : "+imgName);
        
        
        GrapicUtil.saveImage(photo, imgName);
        for(int i=0;i<glesRenderer.pikPrograms.size();++i){
			program=glesRenderer.pikPrograms.get(i);
			program.nTextureResource=imgName;
		}
        glesRenderer.loadTexture();
	}
	
	public void onTextureLoadProgress(float pct){
		
		
	}
	public void onTextureLoadCompleted(){
		
		this.addView( glesSurfaceView ,0, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
		
	}
	
	protected void doMovedInit() { 
	    super.doMovedInit();
	} 
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) 
  	{       
    	boolean trigger=gesture.adjustEvent(event);
  		return trigger;
  	}
    
    
    public void stateChange(Gesture g,String e){
    	
    	float offset=1.0f;
    	Point d=g.changePosA.get(0);
    	if(e==Gesture.START){
    		//Point s=g.startPosA.get(0);
    		eyePosition=glesRenderer.getViewType();
    		
    		if(glesRenderer.pikPrograms.size()!=0){
    			type=TYPE_OBJECT;
    		}else if(type==TYPE_OBJECT){
    			type=TYPE_EYE;
    			
    		}
    		
    		
    		
    		switch(type)
    		{
    			
    			case TYPE_EYE:
    				startPoint.rotateX=(float)glesRenderer.info.eyeTransform.getRotateX();
    				startPoint.rotateY=(float)glesRenderer.info.eyeTransform.getRotateY();
    				startPoint.rotateZ=(float)glesRenderer.info.eyeTransform.getRotateZ();
    				startPoint.x=glesRenderer.info.eyeTransform.x;
    				startPoint.y=glesRenderer.info.eyeTransform.y;
    				startPoint.z=glesRenderer.info.eyeTransform.z;
    				
    				break;	
    			case TYPE_LIGHT:
    				startPoint.x=glesRenderer.info.lightTransform.x;
    				startPoint.y=glesRenderer.info.lightTransform.y;
    				startPoint.z=glesRenderer.info.lightTransform.z;
        			break;
    			case TYPE_OBJECT:
    				
    				
    				startPoints=new ArrayList<GLESTransform>();
    				GLESTransform tf;
    				GLESTransform pf;
    				for(int i=0;i<glesRenderer.pikPrograms.size();++i){
    					tf=new GLESTransform();
    					pf=glesRenderer.pikPrograms.get(i).transform;
    					startPoints.add(tf);
    					tf.x=pf.x;
    					tf.y=pf.y;
    					tf.z=pf.z;
    					tf.width=pf.width;
    					tf.height=pf.height;
    					tf.length=pf.length;
    					tf.rotate=pf.rotate;
    					tf.rotateX=pf.rotateX;
    					tf.rotateY=pf.rotateY;
    					tf.rotateZ=pf.rotateZ;
    					
    				}
    				break;
    		
    		}
    		
    	}else if(e==Gesture.MOVE_H){
    		
    		switch(type)
    		{	
	    		case TYPE_EYE:
	    			moveEyeHorizontal(d);
	    			break;
				case TYPE_LIGHT:
					break;
				case TYPE_OBJECT:
					break;
			}
    		
    	}else if(e==Gesture.MOVE_V){
    		switch(type)
    		{	
	    		case TYPE_EYE:
	    			moveEyeVertical(d);
	    			break;
				case TYPE_LIGHT:
					break;
				case TYPE_OBJECT:
					break;
			}
    		
    		
    	}else if(e==Gesture.PAN){
    		
    		switch(type)
    		{	
	    		case TYPE_EYE:
	    			break;
				case TYPE_LIGHT:
					moveLight(d);
					break;
				case TYPE_OBJECT:
					moveObject(d);
					break;
			}
    		
    	}else if(e==Gesture.PINCH_MOVE){
    		
    		switch(type)
    		{	
	    		case TYPE_EYE:
	    			break;
				case TYPE_LIGHT:
					break;
				case TYPE_OBJECT:
					rotationObject(d);
					break;
			}
    		
    	}
    	
    	
    	
    	
    }
    
    
    
    public void pinchChange(Gesture g,float dist){
    	
    	float offset=2f;
    	
    	switch(type)
		{	
    		case TYPE_EYE:
			    zoomEye(dist);
				break;	
			case TYPE_LIGHT:
				zoomLight(dist);
				break;
			case TYPE_OBJECT:
			    zoomObject(dist);
				break;
		}
    	
    	
    }
    
    private void moveEyeHorizontal(Point d){
    	double t=startPoint.rotateY+(d.x*gestureSpd);
		glesRenderer.info.eyeTransform.setRotateY(t);
    }
    
    private void moveEyeVertical(Point d){
    	
    	
    	float amount=d.y*gestureSpd;
    	if(eyePosition==GLESRenderer.VIEW_F || eyePosition==GLESRenderer.VIEW_TF || eyePosition==GLESRenderer.VIEW_BF){
			glesRenderer.info.eyeTransform.setRotateX(startPoint.rotateX+(amount));
		}else if(eyePosition==GLESRenderer.VIEW_B || eyePosition==GLESRenderer.VIEW_TB || eyePosition==GLESRenderer.VIEW_BB){
			glesRenderer.info.eyeTransform.setRotateX(startPoint.rotateX-(amount));
		}else if(eyePosition==GLESRenderer.VIEW_L || eyePosition==GLESRenderer.VIEW_TL || eyePosition==GLESRenderer.VIEW_BL){
			glesRenderer.info.eyeTransform.setRotateZ(startPoint.rotateZ+(amount));
		}else{
			glesRenderer.info.eyeTransform.setRotateZ(startPoint.rotateZ-(amount));
		}
    }
    private void zoomEye(float dist){
    	float offset=2f;
    	float amount=dist*gestureSpd*offset;
    	
    	if(eyePosition==GLESRenderer.VIEW_F ){
    		glesRenderer.info.eyeTransform.z=startPoint.z+(amount);
		}else if(eyePosition==GLESRenderer.VIEW_B){
			glesRenderer.info.eyeTransform.z=startPoint.z-(amount);
		}else if(eyePosition==GLESRenderer.VIEW_L){
			glesRenderer.info.eyeTransform.x=startPoint.x-(amount);
		}else if(eyePosition==GLESRenderer.VIEW_R){
			glesRenderer.info.eyeTransform.x=startPoint.x+(amount);
		}else if(eyePosition==GLESRenderer.VIEW_TF|| eyePosition==GLESRenderer.VIEW_TB||eyePosition==GLESRenderer.VIEW_TR||eyePosition==GLESRenderer.VIEW_TL){
			glesRenderer.info.eyeTransform.y=startPoint.y-(amount);
		}else{
			glesRenderer.info.eyeTransform.y=startPoint.y+(amount);
		}
    }
    
    
    private void moveLight(Point d){
    	
    	float offset=0.1f;
    	float mx=d.x*gestureSpd*offset;
    	float my=d.y*gestureSpd*offset;
    	
    	if(eyePosition==GLESRenderer.VIEW_F ){
    		glesRenderer.info.lightTransform.x=startPoint.x-mx;
    		glesRenderer.info.lightTransform.y=startPoint.y-my;
    	}else if(eyePosition==GLESRenderer.VIEW_B){
    		glesRenderer.info.lightTransform.x=startPoint.x+mx;
    		glesRenderer.info.lightTransform.y=startPoint.y-my;
		}else if(eyePosition==GLESRenderer.VIEW_L){
			glesRenderer.info.lightTransform.z=startPoint.z-mx;
    		glesRenderer.info.lightTransform.y=startPoint.y-my;
		}else if(eyePosition==GLESRenderer.VIEW_R){
			glesRenderer.info.lightTransform.z=startPoint.z+mx;
    		glesRenderer.info.lightTransform.y=startPoint.y-my;
		}else if(eyePosition==GLESRenderer.VIEW_TF){
			glesRenderer.info.lightTransform.x=startPoint.x-mx;
    		glesRenderer.info.lightTransform.z=startPoint.z-my;
		}else if(eyePosition==GLESRenderer.VIEW_TB){
			glesRenderer.info.lightTransform.x=startPoint.x+mx;
    		glesRenderer.info.lightTransform.z=startPoint.z+my;
		}else if(eyePosition==GLESRenderer.VIEW_TL){
			glesRenderer.info.lightTransform.z=startPoint.z-mx;
    		glesRenderer.info.lightTransform.x=startPoint.x+my;
		}else if(eyePosition==GLESRenderer.VIEW_TR){
			glesRenderer.info.lightTransform.z=startPoint.z+mx;
    		glesRenderer.info.lightTransform.x=startPoint.x-my;
		}else if(eyePosition==GLESRenderer.VIEW_BF){
			glesRenderer.info.lightTransform.x=startPoint.x-mx;
    		glesRenderer.info.lightTransform.z=startPoint.z+my;
			
		}else if(eyePosition==GLESRenderer.VIEW_BB){
			glesRenderer.info.lightTransform.x=startPoint.x+mx;
    		glesRenderer.info.lightTransform.z=startPoint.z-my;
		}else if(eyePosition==GLESRenderer.VIEW_BL){
			glesRenderer.info.lightTransform.z=startPoint.z-mx;
    		glesRenderer.info.lightTransform.x=startPoint.x-my;
		}else if(eyePosition==GLESRenderer.VIEW_BR){
			glesRenderer.info.lightTransform.z=startPoint.z+mx;
    		glesRenderer.info.lightTransform.x=startPoint.x+my;
		}
    	
    }
    
    private void zoomLight(float dist){
    	float offset=2f;
    	float amount=-dist*gestureSpd*offset;
    	
    	if(eyePosition==GLESRenderer.VIEW_F ){
    		glesRenderer.info.lightTransform.z=startPoint.z+(amount);
		}else if(eyePosition==GLESRenderer.VIEW_B){
			glesRenderer.info.lightTransform.z=startPoint.z-(amount);
		}else if(eyePosition==GLESRenderer.VIEW_L){
			glesRenderer.info.lightTransform.x=startPoint.x-(amount);
		}else if(eyePosition==GLESRenderer.VIEW_R){
			glesRenderer.info.lightTransform.x=startPoint.x+(amount);
		}else if(eyePosition==GLESRenderer.VIEW_TF|| eyePosition==GLESRenderer.VIEW_TB||eyePosition==GLESRenderer.VIEW_TR||eyePosition==GLESRenderer.VIEW_TL){
			glesRenderer.info.lightTransform.y=startPoint.y-(amount);
		}else{
			glesRenderer.info.lightTransform.y=startPoint.y+(amount);
		}
    }
    
    
    private void moveObject(Point d){
    	
    	float offset=0.1f;
    	float mx=d.x*gestureSpd*offset;
    	float my=d.y*gestureSpd*offset;
    	GLESTransform obj;
    	GLESTransform point;
    	for(int i=0;i<startPoints.size();++i){
    		obj=glesRenderer.pikPrograms.get(i).transform;
    		point=startPoints.get(i);
    		if(eyePosition==GLESRenderer.VIEW_F ){
    			obj.x=point.x-mx;
    			obj.y=point.y-my;
        	}else if(eyePosition==GLESRenderer.VIEW_B){
        		obj.x=point.x+mx;
        		obj.y=point.y-my;
    		}else if(eyePosition==GLESRenderer.VIEW_L){
    			obj.z=point.z-mx;
    			obj.y=point.y-my;
    		}else if(eyePosition==GLESRenderer.VIEW_R){
    			obj.z=point.z+mx;
    			obj.y=point.y-my;
    		}else if(eyePosition==GLESRenderer.VIEW_TF){
    			obj.x=point.x-mx;
    			obj.z=point.z-my;
    		}else if(eyePosition==GLESRenderer.VIEW_TB){
    			obj.x=point.x+mx;
    			obj.z=point.z+my;
    		}else if(eyePosition==GLESRenderer.VIEW_TL){
    			obj.z=point.z-mx;
    			obj.x=point.x+my;
    		}else if(eyePosition==GLESRenderer.VIEW_TR){
    			obj.z=point.z+mx;
    			obj.x=point.x-my;
    		}else if(eyePosition==GLESRenderer.VIEW_BF){
    			obj.x=point.x-mx;
    			obj.z=point.z+my;
    		}else if(eyePosition==GLESRenderer.VIEW_BB){
    			obj.x=point.x+mx;
    			obj.z=point.z-my;
    		}else if(eyePosition==GLESRenderer.VIEW_BL){
    			obj.z=point.z-mx;
    			obj.x=point.x-my;
    		}else if(eyePosition==GLESRenderer.VIEW_BR){
    			obj.z=point.z+mx;
    			obj.x=point.x+my;
    		}
    		
    	}
    	
    	
    }
    private void rotationObject(Point d){
    	
    	float offset=1.0f;
    	float mx=d.x*gestureSpd*offset;
    	float my=d.y*gestureSpd*offset;
    	
    	//float dx=mx
    	
    	GLESTransform obj;
    	GLESTransform point;
    	for(int i=0;i<startPoints.size();++i){
    		obj=glesRenderer.pikPrograms.get(i).transform;
    		point=startPoints.get(i);
    		obj.rotate=point.rotate+mx;
    	}
    	
    	
    }
    private void zoomObject(float dist){
    	float offset=0.2f;
    	float amount=dist*gestureSpd*offset;
    	GLESTransform obj;
    	GLESTransform point;
    	for(int i=0;i<startPoints.size();++i){
    		obj=glesRenderer.pikPrograms.get(i).transform;
    		point=startPoints.get(i);
	    	if(eyePosition==GLESRenderer.VIEW_F || eyePosition==GLESRenderer.VIEW_B ){
	    		obj.width=point.width+(amount);
	    		obj.height=point.height+(amount);
			}else if(eyePosition==GLESRenderer.VIEW_L || eyePosition==GLESRenderer.VIEW_R){
				
				obj.length=point.length+(amount);
	    		obj.height=point.height+(amount);
				
			}else{
				obj.length=point.length+(amount);
	    		obj.width=point.width+(amount);
			}
    	}
    }
    
    public void rotateChange(Gesture g,float rotate){
    	//Log.i("GLESSurfaceView","rotateChange : " +rotate);
    }
    public void gestureChange(Gesture g,String e){
    	//Log.i("GLESSurfaceView","gestureChange : " +e);
    }
    public void gestureComplete(Gesture g,String e){
    	
    	
    	if(e==Gesture.TOUCH){
    		Point d=g.startPosA.get(0);
    		glesRenderer.pickObject(d);
    		
    	}else if(e==Gesture.LONG_TOUCH){
    		
    		
    	}
    }
    
    protected void doRemove() { 
    	
    	super.doRemove();
    	
    } 
    protected void doPause() { 
    	glesSurfaceView.onPause();
    } 
    protected void doResume() { 
    	glesSurfaceView.onResume();
    } 
}
