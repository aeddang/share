package lib.opengl;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.aeddang.pagechanger.MainActivity;
import com.aeddang.pagechanger.R;
import lib.CommonUtil;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.opengl.GLES20;

import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;

import android.util.FloatMath;
import android.util.Log;




@SuppressLint("NewApi")
public class GLESRenderer implements Renderer, TextureFactoryDelegate{

	public static final int EASY_NONE = -1;
	public static final int EASY_IN = 0;
	public static final int EASY_OUT = 1;
	public static final int EASY_IN_OUT = 2;
	
	
	public static final int VIEW_F=0;
	public static final int VIEW_B=1;
	public static final int VIEW_L=2;
	public static final int VIEW_R=3;
	
	public static final int VIEW_TF=4;
	public static final int VIEW_TB=5;
	public static final int VIEW_TL=6;
	public static final int VIEW_TR=7;
	
	public static final int VIEW_BF=8;
	public static final int VIEW_BB=9;
	public static final int VIEW_BL=10;
	public static final int VIEW_BR=11;
	
	public static final int FRUSTUM = 0;
	public static final int PERSPECTIVE = 1;
	
	public static final int FLOAT_SIZE_BYTES = 4;
	
	/** Used to hold a light centered on the origin in model space. We need a 4th coordinate so we can get translations to work when
	 *  we multiply this by our transformation matrices. */
	private final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f,1.0f};
	/** Used to hold the current position of the light in world space (after transformation via model matrix). */
    
	private final float[] mLightPosInWorldSpace = new float[4];
	private final float[] mLightPosInEyeSpace = new float[4];
	
	private int mProgram,mPoint;
	private int maPositionHandle;
	private int maTextureHandle;
	private int maColorHandle;
	private int maNormalHandle;
	private int muLightPosHandle;
	private int muTextureUniformHandle;
	
	
	private int muMVPMatrixHandle,muMVMatrixHandle;    
	
	private int pointMVPMatrixHandle,pointPositionHandle;
   

	private float[] mLightModelMatrix = new float[16];	
	private float[] mMVPMatrix = new float[16];    
	private float[] mMMatrix = new float[16];    
    private float[] mVMatrix = new float[16];    
    private float[] mProjMatrix = new float[16];
    public float viewRatio,viewWidth,viewHeight;
    
    
    private int vertexShader;
    private int fragmentShader;
    private int pointVertexShader;
    private int pointFragmentShader;
    public int eyePosition;
    private Vector3D rayVector;
    private Point rayPoint;
    
    
    
    
	
	public GLESTextureFactory textureFactory;
	public GLESRendererDelegate delegate;
    public ArrayList<GLESObject> pikPrograms;
	
	public  ArrayList<Bitmap> textureResult;
	
	public GLESRendererInfo info;
	
    public GLESRenderer(GLESRendererInfo _info) {
        super();
        delegate=null;
        if(_info!=null){
        	info=_info;
        }else{
        	info=new GLESRendererInfo();
        }
        pikPrograms=new ArrayList<GLESObject>();
        for(int i=0;i<info.programs.size();++i){
        	if(info.programs.get(i).state==GLESObject.STATE_SELECTED){
        		pikPrograms.add(info.programs.get(i));
        	}	
        }
        textureFactory=new GLESTextureFactory(this);
        
    }
    public void removeRenderer(){
    	textureFactory.removeFactory();
    	GLES20.glDeleteProgram(mProgram);
    	GLES20.glDeleteProgram(mPoint);
    	GLES20.glDeleteShader(vertexShader);
    	GLES20.glDeleteShader(fragmentShader);
    	GLES20.glDeleteShader(pointVertexShader);
    	GLES20.glDeleteShader(pointFragmentShader);
    	
    
	}
	
   
	@Override

	public void onDrawFrame(GL10 unused) {

        
        if(info.programs.size()<1){
        	return;
        }
        
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT );
        
        
        drawWorld(); 
        drawModel(); 
        setRayVector();
        
        drawLightPoint();
		
		
		
       
	}
	
	private void drawLightPoint() {
		GLES20.glUseProgram(mPoint);        
	    GLES20.glVertexAttrib3f(pointPositionHandle, mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);
        GLES20.glDisableVertexAttribArray(pointPositionHandle);  
		
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mLightModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
      
        GLES20.glUniformMatrix4fv(pointMVPMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
		
		
	}
	
	private void drawWorld() {
		info.eyeTransform.animation();
		info.lensTransform.animation();
		Matrix.setLookAtM(mVMatrix, 0, info.eyeTransform.x, info.eyeTransform.y, info.eyeTransform.z, 0f, 0f, 0f, 0f, info.eyeTransform.rotateY, 0f);
		if(info.lensType==PERSPECTIVE){
			 Matrix.perspectiveM(mProjMatrix,0, info.lensTransform.rotate ,viewRatio, info.near, info.far);
		}else
		{
			Matrix.frustumM(mProjMatrix, 0, -viewRatio, viewRatio, -info.lensTransform.rotateY, info.lensTransform.rotateY,info.near, info.far);
		}
		info.lightTransform.animation();
		Matrix.setIdentityM(mLightModelMatrix, 0);
		Matrix.scaleM(mLightModelMatrix, 0 , info.lightTransform.width, info.lightTransform.height, info.lightTransform.length);
		 
        Matrix.rotateM(mLightModelMatrix, 0,info.lightTransform.rotate, info.lightTransform.rotateX, info.lightTransform.rotateY, info.lightTransform.rotateZ);
        Matrix.translateM(mLightModelMatrix, 0, info.lightTransform.x, info.lightTransform.y, info.lightTransform.z);     
        
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mVMatrix, 0, mLightPosInWorldSpace, 0);    
        
        
        
        
		

	}
	private void drawModel( ) {
		GLES20.glUseProgram(mProgram);  
        GLESObject program;
       
		int textureID; 
		float pikkigIndex=-1.f;
		float pidx;
	
		FloatBuffer positions,colors,textures,normals;
		
		GLESObject pikProgram=null;
		
        for(int i=0;i<info.programs.size();++i){
			program=info.programs.get(i);
			program.animation();
			
			if(info.usedTexture==true){
				GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
				textureID=textureFactory.getTextureByIdx(program.getTextureID());
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureID);
				GLES20.glUniform1i(muTextureUniformHandle, 0);     
			}
			positions=program.getPositions();
			positions.position(0);
	        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false,
	                0, positions);
	        GLES20.glEnableVertexAttribArray(maPositionHandle);
	        
	        colors=program.getColors();
	        colors.position(0);
	        GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT, false,
	                0, colors);
	        GLES20.glEnableVertexAttribArray(maColorHandle);
	        
	        textures=program.getTextures();
	        textures.position(0);
	        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false,
	                0, textures);
	        GLES20.glEnableVertexAttribArray(maTextureHandle);
	        
	        normals=program.getNormals();
	        normals.position(0);
	        GLES20.glVertexAttribPointer(maNormalHandle, 3, GLES20.GL_FLOAT, false,
	                0, normals);
	        GLES20.glEnableVertexAttribArray(maNormalHandle);
	        Matrix.setIdentityM(mMMatrix, 0);
	        GLESTransform transform= program.transform;
	        
	        Matrix.translateM(mMMatrix, 0, transform.x, transform.y, transform.z);
	        Matrix.rotateM(mMMatrix, 0, transform.rotate, transform.rotateX, transform.rotateY, transform.rotateZ);        
	        Matrix.scaleM(mMMatrix, 0 , transform.width, transform.height, transform.length);
		    Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);    
		    
		    GLES20.glUniformMatrix4fv(muMVMatrixHandle, 1, false, mMVPMatrix, 0); 
		    Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);     
			if(program.selectable==true){
			    pidx=picking(program.getHitRange());
		        if(pidx!=-1){
		        	if(pikkigIndex==-1 || pikkigIndex>pidx){
		        		
		        		pikProgram=program;
			        	pikkigIndex=pidx;
			     
		        	}
		        	
		        }
			}
			GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0); 
		    GLES20.glUniform3f(muLightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, (int) Math.floor(program.getShapePoints().length/3));
	        
	        
	        
		}
        
        
        
        if(rayVector!=null){
        	
    		if(pikProgram!=null){
    			
    			if(info.isMultiPiking==false){
    				if(pikPrograms.size()>0){
    					pikPrograms.get(0).setState( GLESObject.STATE_NORMAL);
    					pikPrograms.remove(0);
    				}
    				pikProgram.setState( GLESObject.STATE_SELECTED);
    				pikPrograms.add(pikProgram);
    			
    			}else{
    				int proidx=pikPrograms.indexOf(pikProgram);
        		    if(proidx!=-1){
        		    	pikProgram.setState( GLESObject.STATE_NORMAL);
        				pikPrograms.remove(proidx);
        			}else{
        				pikProgram.setState( GLESObject.STATE_SELECTED);
        				pikPrograms.add(pikProgram);
        			}
    				
    			}
    			
    			
    			
    			
    		}
        	rayVector=null;
        } 
        
	}
	private void setRayVector() {
		if(rayPoint!=null){
			
			float xp = (rayPoint.x / (float)viewWidth) * 2 - 1;
			float yp = (rayPoint.y / (float)viewHeight) * 2 - 1;
			rayVector = new Vector3D( xp, -yp, info.near );
			
			rayPoint=null;
			
		}
	}
	private float picking(float hitRange) {
		
		if(rayVector!=null){

            float eyeDist=info.eyeTransform.getDistence();

            Log.i("","rayVector.x : "+rayVector.x+"  rayVector.y : "+rayVector.y);
            Log.i("","modelDistence : "+eyeDist);

            Vector3D modelPoint = new Vector3D(mMVPMatrix [12]/eyeDist,mMVPMatrix[13]/eyeDist,mMVPMatrix[14]/eyeDist);


            Log.i("","x : "+modelPoint.x+"  y : "+modelPoint.y+"  z : "+modelPoint.z);

            // float rp=eyeDist/(modelPoint.z+eyeDist);
           //  Log.i("","modelDistence : "+eyeDist);
           // Log.i("","modelPoint : "+(modelPoint.x*rp)+"  "+(modelPoint.y*rp));



            //float rp =((far)-(modelPoint.z)) /(far);
        	float distX = ((modelPoint.x)-rayVector.x) ;
        	float distY = ((modelPoint.y)-rayVector.y) ;
        	float dist = FloatMath.sqrt(distX*distX + distY*distY);


        	float md= hitRange/eyeDist;
            Log.i("","dist : "+dist+"  md : "+md);


        	if(dist<=md){
        		
        		return modelPoint.z;
        	}
        
    		
        }
		return -1.f;
	}
	
	
	
	@Override

	public void onSurfaceChanged(GL10 unused, int width, int height) {

		GLES20.glViewport(0,0,width,height);
		
		viewWidth=width;
		viewHeight=height;
		
		viewRatio = (float) width/height;
		if(info.lensType==PERSPECTIVE){
			 Matrix.perspectiveM(mProjMatrix,0, info.lensTransform.rotate ,viewRatio, info.near, info.far);
			
		}else
		{
			Matrix.frustumM(mProjMatrix, 0, -viewRatio, viewRatio, -info.lensTransform.rotateY, info.lensTransform.rotateY,info.near, info.far);
		}
		
		
	}




	@Override

	public void onSurfaceCreated(GL10 unused, EGLConfig config) {

		GLES20.glClearColor(0,0,0,0);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDisable(GLES20.GL_DITHER);
		
		Matrix.setLookAtM(mVMatrix, 0, info.eyeTransform.x, info.eyeTransform.y, info.eyeTransform.z, 0f, 0f, 0f, 0f, info.eyeTransform.rotateY, 0f);
		
		
		
		vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, 
				                      CommonUtil.readTextFileFromRawResource(MainActivity.getInstence(),R.raw.vertex_shader));        
        fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, 
        		                        CommonUtil.readTextFileFromRawResource(MainActivity.getInstence(),R.raw.fragment_shader));                
        String[] attributes = new String[] {"aPosition",  "aColor", "aNormal", "aTextureCoord"};
		mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program        
		
	    GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program        
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program        
        
        final int size = attributes.length;
	    for (int i = 0; i < size; i++)
	    {
		   GLES20.glBindAttribLocation(mProgram, i, attributes[i]);
	    }						
		
        
        GLES20.glLinkProgram(mProgram);      
         
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        muMVMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVMatrix"); 
        muLightPosHandle= GLES20.glGetUniformLocation(mProgram, "uLightPos");
        muTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "uTexture");
        //muShadowProjMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uShadowProjMatrix");
        
        maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
    	maColorHandle= GLES20.glGetAttribLocation(mProgram, "aColor");
    	maNormalHandle= GLES20.glGetAttribLocation(mProgram, "aNormal");
		
    	
    	
    	
    	pointVertexShader = loadShader(GLES20.GL_VERTEX_SHADER, 
                CommonUtil.readTextFileFromRawResource(MainActivity.getInstence(),R.raw.point_vertex_shader));        
    	pointFragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, 
                  CommonUtil.readTextFileFromRawResource(MainActivity.getInstence(),R.raw.point_fragment_shader));       

    	mPoint = GLES20.glCreateProgram();             // create empty OpenGL Program        

    	GLES20.glAttachShader(mPoint, pointVertexShader);   // add the vertex shader to program        
    	GLES20.glAttachShader(mPoint, pointFragmentShader); // add the fragment shader to program        
    	GLES20.glBindAttribLocation(mPoint, 0, "aPosition");
    	GLES20.glLinkProgram(mPoint);    
    	
    	
    	pointMVPMatrixHandle = GLES20.glGetUniformLocation(mPoint, "uMVPMatrix");
        pointPositionHandle = GLES20.glGetAttribLocation(mPoint, "aPosition");
        
        
        
        textureFactory.textureMapping(textureResult);
		GLESObject program;
		for(int i=0;i<info.programs.size();++i){
			program=info.programs.get(i);
			program.init();
		}
		textureResult=null;
	}
	
	public void loadTexture(){
		GLESObject program;
		
		for(int i=0;i<info.programs.size();++i){
			program=info.programs.get(i);
			if(program.nTextureResource!=null){
				program.nTextureID=textureFactory.addTexture(program.nTextureResource);
			}
			if(program.aTextureResource!=null){
				program.aTextureID=textureFactory.addTexture(program.aTextureResource);
			}
			
			if(program.pTextureResource!=null){
				program.pTextureID=textureFactory.addTexture(program.pTextureResource);
			}
			
			if(program.sTextureResource!=null){
				program.sTextureID=textureFactory.addTexture(program.sTextureResource);
			}
		}
		textureFactory.addComplete();
	}
	public void textureMapping(ArrayList<Bitmap> result){
		textureFactory.textureMapping(result);
	}
	
	public void onProgress(float pct){
		if(delegate!=null){delegate.onTextureLoadProgress(pct);}
	}
	public void onCompleted(ArrayList<Bitmap> result){
		info.usedTexture=true;
		textureResult=result;
		if(delegate!=null){delegate.onTextureLoadCompleted();}
	}
	
	
	
	public int getViewType(){
    	
    	float distH=FloatMath.sqrt((info.eyeTransform.x*info.eyeTransform.x) + (info.eyeTransform.z*info.eyeTransform.z));
    	float distV=FloatMath.sqrt((info.eyeTransform.z*info.eyeTransform.z) + (info.eyeTransform.y*info.eyeTransform.y));
    	float distL=FloatMath.sqrt((info.eyeTransform.y*info.eyeTransform.y) + (info.eyeTransform.x*info.eyeTransform.x));
    	
    	
    	double eyeDgree;
    	int rtype;
    	if(distH>=distV && distH>=distL){
    		rtype=0;
    		eyeDgree=info.eyeTransform.getRotateY();
    	}else if(distV>=distH && distV>=distL){
    		rtype=1;
    		eyeDgree=info.eyeTransform.getRotateX();
    	}else{
    		rtype=2;
    		eyeDgree=info.eyeTransform.getRotateZ();
    		
    	}
    	
    	eyeDgree=getEyeDgree(eyeDgree);
		
		
		Log.i("","eyeDgree : "+eyeDgree+"   rtype : "+rtype);
		
		
		switch(rtype){
			case 0:    
		
				if(eyeDgree<=90){
					eyePosition=VIEW_L;
				}else if(eyeDgree<=180){
					eyePosition=VIEW_B;
				}else if(eyeDgree<=270){
					eyePosition=VIEW_R;
				}else{
					eyePosition=VIEW_F;
		    	
				}
				break;
			case 1:    
				if(eyeDgree<=90){
					eyePosition=999; 
			    }else if(eyeDgree<=180){
			    	eyePosition=VIEW_B;
			    }else if(eyeDgree<=270){
			    	eyePosition=-1;
			    }else{
			    	eyePosition=VIEW_F;
			    }
				break;
			case 2:    
				if(eyeDgree<=90){
					eyePosition=VIEW_L;
			    }else if(eyeDgree<=180){
			    	eyePosition=999;
			    }else if(eyeDgree<=270){
			    	eyePosition=VIEW_R;
			    }else{
			    	eyePosition=-1;
			    }
				break;
			
			
		}
		
		
		if(eyePosition==999 || eyePosition==-1){
			
			eyeDgree=info.eyeTransform.getRotateY();
			eyeDgree=getEyeDgree(eyeDgree);
			Log.i("","BT Dgree : "+eyeDgree);
			
			if(eyeDgree<=90){
				if(eyePosition==999){
					eyePosition=VIEW_TL;
				}else{
					eyePosition=VIEW_BL;
				}
				
			}else if(eyeDgree<=180){
				if(eyePosition==999){
					eyePosition=VIEW_TB;
				}else{
					eyePosition=VIEW_BB;
				}
			}else if(eyeDgree<=270){
				if(eyePosition==999){
					eyePosition=VIEW_TR;
				}else{
					eyePosition=VIEW_BR;
				}
			}else{
				if(eyePosition==999){
					eyePosition=VIEW_TF;
				}else{
					eyePosition=VIEW_BF;
				}
	    	
			}
			
			
		
		
		}
		String v="";
		switch(eyePosition)
		{	
    		case VIEW_F:
    			v="F";
				break;
			case VIEW_B:
				v="B";
				break;	
			case VIEW_L:
				v="L";
				break;	
			case VIEW_R:
				v="R";
				break;	
			case VIEW_TF:
				v="TF";
				break;
			case VIEW_TB:
				v="TB";
				break;	
			case VIEW_TL:
				v="TL";
				break;	
			case VIEW_TR:
				v="TR";
				break;	
			case VIEW_BF:
				v="BF";
				break;
			case VIEW_BB:
				v="BB";
				break;	
			case VIEW_BL:
				v="BL";
				break;	
			case VIEW_BR:
				v="BR";
				break;	
		
		}
		
		
		
		Log.i("","eyePosition : "+v);
		return eyePosition;
    }
	private double getEyeDgree(double d){
		
		d=d+45;
		if(d<0){
			d=360+d;
		}
		return d;
	}
	
	private int loadShader(int type, String shaderCode){            

		int shader = GLES20.glCreateShader(type);                 
        GLES20.glShaderSource(shader, shaderCode);        

		GLES20.glCompileShader(shader);   

		
		int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
		return shader;    

	}
	
	private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("GLESRenderer", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
	
	
	public void pickObject(Point location){            
	    rayPoint=location;
	}
	
	public void programSelect(GLESObject obj){            
		if(info.programs.indexOf(obj)!=-1){
			obj.state=GLESObject.STATE_SELECTED;
        	pikPrograms.add(obj);
		} 
	}
	
	public void allSelect(){            
		GLESObject program;
		pikPrograms=new ArrayList<GLESObject>();
        for(int i=0;i<info.programs.size();++i){
        	program=info.programs.get(i);
        	if(program.selectable==true){
        		program.state=GLESObject.STATE_SELECTED;
        		pikPrograms.add(program);
        	}
        		
        }
	   
	}
	
	public void removeSelect(GLESObject obj){            
		if(info.programs.indexOf(obj)!=-1){
			obj.state=GLESObject.STATE_NORMAL;
        	pikPrograms.remove(obj);
		} 
		   
	}
	public void removeAllSelect(){            
		GLESObject program;
		for(int i=0;i<info.programs.size();++i){
        	program=info.programs.get(i);
        	program.state=GLESObject.STATE_NORMAL;
        }
		pikPrograms=new ArrayList<GLESObject>();
		   
	}
	public void addView(GLESObject obj){            
		
		info.programs.add(obj);
	   
	}
	
	public void removeView(GLESObject obj){            
		
		if(pikPrograms.indexOf(obj)!=-1){
			pikPrograms.remove(obj);
		}
		
		obj.removeObject();
		if(info.programs.indexOf(obj)!=-1){
			info.programs.remove(obj);
		}
	   
	}
	public void removeView(int idx){            
		
		if(idx<0 || idx>=info.programs.size()){
			return;
		}
		GLESObject obj=info.programs.get(idx);
		removeView(obj);
	   
	}
	public interface GLESRendererDelegate
	{
	    void onTextureLoadProgress(float pct);
		void onTextureLoadCompleted();
	  
	}
    
}



