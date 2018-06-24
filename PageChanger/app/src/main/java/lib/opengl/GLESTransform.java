package lib.opengl;

import android.graphics.Point;
import android.util.FloatMath;
import android.util.Log;



public class GLESTransform {
	
	public GLESTransformDelegate delegate;
	
	public float x,y,z;
	public float width,height,length;
	public float rotate,rotateX,rotateY,rotateZ;
	
	public float hitRange;
	
	
	
	private float ox,oy,oz;
	private float owidth,oheight,olength;
	private float orotate,orotateX,orotateY,orotateZ;
	
	private float tx,ty,tz;
	private float twidth,theight,tlength;
	private float trotate,trotateX,trotateY,trotateZ;
	private float duration,t,delay;
	
	int easy;
	
	public GLESTransform()
	{
		x=0;
		y=0;
		z=0;
		
		hitRange=0;
		width=1;
		height=1;
		length=1;
		rotate=0;
		rotateX=1;
		rotateY=0;
		rotateZ=0;
		delay=0;
		duration=-1;
		tx=-1;
		ty=-1;
		tz=-1;
		twidth=-1;
		theight=-1;
		tlength=-1;
		trotate=-1;
		trotateX=-1;
		trotateY=-1;
		trotateZ=-1;
		
		t=0;
	}
	public void addAnimation(String v,float amount)
	{
		if(v=="x"){
			tx=amount;
			ox=x;
		}else if(v=="y"){
			ty=amount;
			oy=y;
		}else if(v=="z"){
			tz=amount;
			oz=z;
		}else if(v=="width"){
			twidth=amount;
			owidth=width;
		}else if(v=="height"){
			theight=amount;
			oheight=height;
		}else if(v=="length"){
			tlength=amount;
			olength=amount;
		}else if(v=="rotate"){
			trotate=amount;
			orotate=rotate;
		}else if(v=="rotateX"){
			trotateX=amount;
			orotateX=rotateX;
		}else if(v=="rotateY"){
			trotateY=amount;
			orotateY=rotateY;
		}else if(v=="rotateZ"){
			trotateZ=amount;
			orotateZ=rotateZ;
		}
	}
	public void startAnimation(float _duration,int _easy)
	{
		
		duration=_duration;
		easy=_easy;
		
		t=0;
	}
	public void stopAnimation()
	{
		endAnimation();
	}
	public String toString()
	{
		return x+" "+y+" "+z+" "+width+" "+height+" "+length+" "+rotate+" "+rotateX+" "+rotateY+" "+rotateZ;
	}
	
	public void setScale(float sc)
	{
		width=sc;
		height=sc;
		length=sc;
	}
	public float getDistence()
	{
		
		return FloatMath.sqrt((x*x)+(y*y)+(z*z));
		
	}
	
	public double getRotateX()
	{
		double rd=Math.atan2(z, y);
		return rd*180.f/Math.PI;
		
	}
	public double getRotateY()
	{
		double rd=Math.atan2(z, x);
		return rd*180.f/Math.PI;
		
	}
	public double getRotateZ()
	{
		double rd=Math.atan2(y, x);
		return rd*180.f/Math.PI;
		
	}
	public void setRotateX(double rd)
	{
		float[] xp=setRotateValue(y, z, rd);
		y=xp[0];
		z=xp[1];	
	}
	public void setRotateY(double rd)
	{
		float[] yp=setRotateValue(x, z, rd);
		x=yp[0];
		z=yp[1];
	}
	public void setRotateZ(double rd)
	{
		float[] zp=setRotateValue(x, y, rd);
		x=zp[0];
		y=zp[1];	
	}
	private float[] setRotateValue(float p1, float p2, double rd)
	{
		rd=rd/180.f*Math.PI;
	
		float r=FloatMath.sqrt((p1*p1) + (p2*p2));
		
		float[] rp=new float[2];
		rp[0]=(float) (r*Math.cos(rd));
		rp[1]=(float) (r*Math.sin(rd));
		return rp;
	}
	public void moveRotate(double rx,double ry,double rz)
	{
		float[] xp=moveRotateValue(y, z, rx);
		y=xp[0];
		z=xp[1];
		
		
		float[] yp=moveRotateValue(x, z, ry);
		x=yp[0];
		z=yp[1];
		
		float[] zp=moveRotateValue(x, y, rz);
		x=zp[0];
		y=zp[1];
	    	
	}
	
	private float[] moveRotateValue(float p1, float p2, double rd)
	{
		
		
		float r=FloatMath.sqrt((p1*p1) + (p2*p2));
        double prd=Math.atan2(p2, p1);
		double d=rd/Math.abs(rd)/180.f*Math.PI;
		
		if(!Double.isNaN(d)){
			prd+=d;
		}
		
		float[] rp=new float[2];
		rp[0]=(float) (r*Math.cos(prd));
		rp[1]=(float) (r*Math.sin(prd));
		
		return rp;
	}
	public void animation()
	{
		if(duration==-1){
			return;
		}
		if(tx!=-1){
			x=animating(ox,tx);
		}
		if(ty!=-1){
			y=animating(oy,ty);
		}
		if(tz!=-1){
			z=animating(oz,tz);
		}
		if(twidth!=-1){
			width=animating(owidth,twidth);
		}
		if(theight!=-1){
			height=animating(oheight,theight);
		}
		if(tlength!=-1){
			length=animating(olength,tlength);
		}
		
		if(trotate!=-1){
			rotate=animating(orotate,trotate);
		}
		if(trotateX!=-1){
			rotateX=animating(orotateX,trotateX);
		}
		if(trotateY!=-1){
			rotateY=animating(orotateY,trotateY);
		}
		if(trotateZ!=-1){
			rotateZ=animating(orotateZ,trotateZ);
		}
	
		if(delegate!=null){
			
			delegate.moveUpdate(this);
		}
		t++;
		if(duration<t){
			if(delegate!=null){
				delegate.moveComplete(this);
			}
			endAnimation();
			
		}
	}
	private float animating(float ov,float tv)
	{
		if(delay>0){
		    delay--;
			return -1;
		}else{
			if(delegate!=null){
				
				delegate.moveStart(this);
			}
			
		}
		
		if(tv==-1){
			return -1;
		}
		float diff=tv-ov;
		int offsetR=0;
		int rangeR=0;
		
		int offsetV=0;
		int mt=1;
		float r=0;
		
		String type="n";
		
		switch(easy){
			case GLESRenderer.EASY_IN:
				offsetR=0;
				rangeR=90;
				offsetV=0;
				mt=1;
				type="s";
				break;
			case GLESRenderer.EASY_OUT:
				offsetR=270;
				rangeR=90;
				offsetV=1;
				mt=1;
				type="s";
				break;
			case GLESRenderer.EASY_IN_OUT:
				offsetR=180;
				rangeR=180;
				offsetV=1;
				mt=2;
				type="c";
				break;
		    default:
		    	
		    	break;
		}
		//Log.i("GLES animation","t  : "+t+" d  : "+duration);
		float pct=t/duration;
		//Log.i("GLES animation","pct  : "+pct);
		if(type=="n"){
			r=diff*pct;
		}else{
			
			int cr=0;
			if(rangeR!=0){
				cr=offsetR+(int)Math.floor(rangeR*pct);
			}
			//Log.i("GLES animation","r : "+ cr);
			float rd=(float) (Math.PI*cr/180);
			if(type=="s"){
				//Log.i("GLES animation","sin : "+ Math.sin(rd));
				r=(float) Math.sin(rd);
			}else if(type=="c"){
				//Log.i("GLES animation","cos : "+ Math.cos(rd));
				r=(float) Math.cos(rd);
			}else{
				
			}
			
			r=offsetV+r;
			//Log.i("GLES animation","r offset : "+r);
			r=r/mt;
			//Log.i("GLES animation","r mt : "+r);
			r=diff*r;
			
			
		}
		
		r=ov+r;
		
		//Log.i("GLES animation","r ov : "+r);
		return r;
	}
	
	public void endAnimation()
	{
		delay=0;
		duration=-1;
		tx=-1;
		ty=-1;
		tz=-1;
		twidth=-1;
		theight=-1;
		tlength=-1;
		trotate=-1;
		trotateX=-1;
		trotateY=-1;
		trotateZ=-1;
	}
	
	public interface GLESTransformDelegate
	{
		void moveStart(GLESTransform transform);
		void moveComplete(GLESTransform transform);
		void moveUpdate(GLESTransform transform);
	}
}
