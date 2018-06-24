package lib.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import lib.core.ActivityCore;
import lib.view.SlideBox;
import lib.view.SlideBox.SlideBoxDelegate;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.FloatMath;
import android.util.Log;
import android.widget.FrameLayout;

public class GLESObject {

	
	public static final int STATE_NORMAL = 0;
	public static final int STATE_ACTIVE = 1;
	public static final int STATE_PASSIVE = 2;
	public static final int STATE_SELECTED = 3;
	
	
	public GLESTransform transform;
	public Object nTextureResource,aTextureResource,pTextureResource,sTextureResource;
	
	
	protected FloatBuffer nPositions,nColors,nNormals,nTextures;
	
	protected FloatBuffer aPositions,aColors,aNormals,aTextures;
	protected FloatBuffer pPositions,pColors,pNormals,pTextures;
	protected FloatBuffer sPositions,sColors,sNormals,sTextures;
	
	
	public boolean selectable=true;
	public int nTextureID,aTextureID,pTextureID,sTextureID;   
	protected float[] nShapePoints,aShapePoints,pShapePoints,sShapePoints;
	
	protected int state;
	
	public GLESObject(){
		
		transform=new GLESTransform();
		nTextureResource=null;
		aTextureResource=null;
		pTextureResource=null;
		sTextureResource=null;
		
		
		nTextureID=-1;
		aTextureID=-1;
		pTextureID=-1;
		sTextureID=-1;
		
		nPositions=null;
		nColors=null;
		nNormals=null;
		nTextures=null;
		
		aPositions=null;
		aColors=null;
		aNormals=null;
		aTextures=null;
		pPositions=null;
		pColors=null;
		pNormals=null;
		pTextures=null;
		sPositions=null;
		sColors=null;
		sNormals=null;
		sTextures=null;
		
		nShapePoints=null;
		aShapePoints=null;
		pShapePoints=null;
		sShapePoints=null;
		
		state=STATE_NORMAL;
	}
	public void init(){
		
		
			
	}
	public void removeObject(){
		nTextureResource=null;
		aTextureResource=null;
		pTextureResource=null;
		sTextureResource=null;
	}
	public void defaultStateSetting(float[] ncolors,float mdpct){
		
		transform.hitRange=getShapeRange(nShapePoints);
		int size=ncolors.length;
		float[] acolors =new float[size];
		float[] pcolors =new float[size];
		float[] scolors =new float[size];
		
		for(int i=0;i<size;++i){
			acolors[i]=ncolors[i]*(1+mdpct);
			pcolors[i]=ncolors[i]*(1-mdpct);
			scolors[i]=ncolors[i]*(1+(2*mdpct));
			
		}
		aColors=getBuffer(acolors);
		pColors=getBuffer(pcolors);
		sColors=getBuffer(scolors);
			
	}
	public void setState(int _state){
		state=_state;
	}
	public float getHitRange(){
		
		float d=FloatMath.sqrt((transform.width*transform.width)+(transform.height*transform.height)+(transform.length*transform.length));
		return transform.hitRange*d; 
	}
	
	
	public float getShapeRange(float[] shape){
		
		int size=shape.length/3;
		float td=0;
		for(int i=0;i<size;++i){
			float d=FloatMath.sqrt((shape[i]*shape[i])+(shape[i+1]*shape[i+1])+(shape[i+2]*shape[i+2]));
			td+=d;
		}
		return td/size;
		
	}
	
	
	public float[] getShapePoints(){
		
		float[] shape;
		switch(state){
		case STATE_ACTIVE:
			shape= aShapePoints;
			break;
		case STATE_PASSIVE:
			shape= pShapePoints;
			break;
		case STATE_SELECTED:
			shape= sShapePoints;
			break;
		default:
			shape= nShapePoints;
			break;
			
		}
		if(shape==null){
			shape=nShapePoints;
		}
		return shape;
		
	}
	public int getTextureID(){
		
		int tid;
		switch(state){
		case STATE_ACTIVE:
			tid= aTextureID;
			break;
		case STATE_PASSIVE:
			tid= pTextureID;
			break;
		case STATE_SELECTED:
			tid= sTextureID;
			break;
		default:
			tid= nTextureID;
			break;
			
		}
		if(tid==-1){
			tid=nTextureID;
		}
		return tid;
		
	}
	
	public FloatBuffer getPositions(){
		
		FloatBuffer buf;
		switch(state){
		case STATE_ACTIVE:
			buf= aPositions;
			break;
		case STATE_PASSIVE:
			buf= pPositions;
			break;
		case STATE_SELECTED:
			buf= sPositions;
			break;
		default:
			buf= nPositions;
			break;
			
		}
		if(buf==null){
			buf=nPositions;
		}
		return buf;
		
	}
	
	
    public FloatBuffer getColors(){
		
		FloatBuffer buf;
		switch(state){
		case STATE_ACTIVE:
			buf= aColors;
			break;
		case STATE_PASSIVE:
			buf= pColors;
			break;
		case STATE_SELECTED:
			buf= sColors;
			break;
		default:
			buf= nColors;
			break;
			
		}
		if(buf==null){
			buf=nColors;
		}
		return buf;
		
	}
	
    public FloatBuffer getTextures(){
		
		FloatBuffer buf;
		switch(state){
		case STATE_ACTIVE:
			buf= aTextures;
			break;
		case STATE_PASSIVE:
			buf= pTextures;
			break;
		case STATE_SELECTED:
			buf= sTextures;
			break;
		default:
			buf= nTextures;
			break;
			
		}
		if(buf==null){
			buf=nTextures;
		}
		return buf;
		
	}
    
    public FloatBuffer getNormals(){
		
		FloatBuffer buf;
		switch(state){
		case STATE_ACTIVE:
			buf= aNormals;
			break;
		case STATE_PASSIVE:
			buf= pNormals;
			break;
		case STATE_SELECTED:
			buf= sNormals;
			break;
		default:
			buf= nNormals;
			break;
			
		}
		if(buf==null){
			buf=nNormals;
		}
		return buf;
		
	}
	public  void animation()
	{
		doAnimation();
		transform.animation();
	}
	protected void doAnimation()
	{
	    
	}
	protected FloatBuffer getBuffer(float[] points){            
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(points.length * 4);
        vbb.order(ByteOrder.nativeOrder());	// use the device hardware's native byte order
        FloatBuffer vbuf = vbb.asFloatBuffer();	// create a floating point buffer from the ByteBuffer  
        vbuf.put(points);		// add the coordinates to the FloatBuffer    
	    vbuf.position(0);		// set the buffer to read the first coordinate   
	    
	    return vbuf;

	}

	
	
	
	
	
}
