package lib.opengl;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.GrapicUtil;
import lib.core.ActivityCore;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.AsyncTask;
import android.util.Log;

@SuppressLint("UseSparseArrays")
public class GLESTextureFactory implements TextureLoaderDelegate
{
	public TextureFactoryDelegate delegate; 
	private TextureLoader loader;
    private ArrayList<Object> textureSorceA;
    private ArrayList<int[]>textureIdA;
    private int finalIndex;
    
    private Map<String,Integer> textureIdxA;
	public GLESTextureFactory(TextureFactoryDelegate _delegate){
		delegate=_delegate;
		finalIndex=0;
		
		textureSorceA=new ArrayList<Object>();
		textureIdA=new ArrayList<int[]>();
		textureIdxA=new HashMap<String,Integer>();
		
		
	}
	public void removeFactory(){
		
		for(int i=0;i<textureIdA.size();++i){
			GLES20.glDeleteTextures(1, textureIdA.get(i),0);
		}
		
		
		textureSorceA=null;
		textureIdA=null;
		textureIdxA=null;
	}
	public int getTextureIndexById(String id){
		Integer idx=textureIdxA.get(id);
		if(idx==null){
			return -1;
		}
		
		return idx.intValue();
	
	}
	public int getTextureByIdx(int idx){
	    return textureIdA.get(idx)[0];
	
	}
	
	public int addTexture(Object resource){
		
		String imgID="";
		if(resource instanceof Bitmap){
			Bitmap bitmap=(Bitmap)resource;
			int idx=finalIndex+textureSorceA.size();
	        textureSorceA.add(bitmap);
	        return idx;
    	
		}else if(resource instanceof String){
    		
			imgID=(String)resource;
    		
    	}else{
    		imgID=resource+"";
    		
    	}
		Integer idx=textureIdxA.get(imgID);
		if(idx!=null){
			return idx;
		}
		return addTexture(resource,imgID);
		
		
	}
   
	
    public int addTexture(Object bitmap,String id){
		
    	
        int idx=finalIndex+textureSorceA.size();
        textureIdxA.put(id, idx);
        textureSorceA.add(bitmap);
        
        return idx;
		
	}
    public void textureMapping(ArrayList<Bitmap> result){
    	
    	
    	for(int i=0;i<result.size();++i){
    		Bitmap bitmap=result.get(i);
    		if(bitmap!=null){
    		
	    		int[] textures = new int[1];
	    		GLES20.glGenTextures(1, textures, 0);
	    		checkGlError("glGenTextures");
	
	    		int mTextureID = textures[0];
	            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);
	           
	            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
	                    GLES20.GL_NEAREST);
	            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
	                    GLES20.GL_TEXTURE_MAG_FILTER,
	                    GLES20.GL_LINEAR);
	
	            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
	                    GLES20.GL_REPEAT);
	            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
	                    GLES20.GL_REPEAT);
	            
	            
	            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
	            bitmap.recycle();
	            textureIdA.add(textures);
    		}else{
    			Log.i("","image null : "+i);
    			textureIdA.add(new int[1]);
    		}
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
	public void addComplete(){
    	Log.i("","addComplete excute");
    	
    	loader=new TextureLoader();
		loader.start(this);
    	loader.execute(textureSorceA);
    	
    }
    
    
    public void onProgress(float pct){
        
    	if(delegate!=null){delegate.onProgress(pct);}
        
    }
    public void onCompleted(ArrayList<Bitmap> result){
    	Log.i("","onCompleted");
    	
    	finalIndex=finalIndex+loader.index;
    	textureSorceA=new ArrayList<Object>();
    	loader=null;
    	if(delegate!=null){delegate.onCompleted(result);}
    }
    
    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("GLESRenderer", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
}

interface TextureFactoryDelegate
{
    void onProgress(float pct);
	void onCompleted(ArrayList<Bitmap> result);
  
}

class TextureLoader extends AsyncTask<ArrayList<Object>, Integer, ArrayList<Bitmap>> 
{
	
	private TextureLoaderDelegate delegate; 
	public int index;
	
	
	public void start(TextureLoaderDelegate _delegate) {
	    
        delegate = _delegate;
        index=0;
    }
	protected ArrayList<Bitmap> doInBackground(ArrayList<Object>... params) {
		    
		ArrayList<Object> bitmaps=params[0];    
		ArrayList<Bitmap> bitmapDatas=new ArrayList<Bitmap>();
	    int i=0;
	    int num=bitmaps.size();
        while(i<num){
        
	        
	        Object obj;
	        Bitmap bitmap=null;
	        obj=bitmaps.get(i);
        	if(obj instanceof Bitmap){
        		bitmap=(Bitmap)obj;
        		Log.i("","bitmap : "+bitmap);
        	}else if(obj instanceof String){
        		String imgPath=(String)obj;
        		
        		bitmap=GrapicUtil.loadBitmap(imgPath);
        		Log.i("","bitmap : "+bitmap);
        		
        		
        	}else{
        		bitmap=GrapicUtil.loadBitmap((Integer)obj);
        		
        	}
        	i++;
            index++;      
        	publishProgress(i/num);
        	bitmapDatas.add(bitmap);
	       
        	
        }
	        
	    return bitmapDatas;
  }

  protected void onProgressUpdate(Integer... progress) {
	   super.onProgressUpdate(progress);  
	   
	   if(delegate!=null){delegate.onProgress(progress[0]);}
	   
  }
  protected void onPostExecute(ArrayList<Bitmap> result) {
	   super.onPostExecute(result);
	   if(delegate!=null){delegate.onCompleted(result);}
  }
 

}//package
interface TextureLoaderDelegate
{
    void onProgress(float pct);
	void onCompleted(ArrayList<Bitmap> result);
  
}


