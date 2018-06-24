package lib.opengl.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import lib.CommonUtil;
import lib.opengl.GLESObject;

public class GLESObj extends GLESObject {

	private GLESObjLoader loader;
	
	public GLESObj(Context context,int resorce,String colorCode,int textureID,Bitmap textureBp){
		
		super();
		loader=new GLESObjLoader(context,resorce,GLESObjLoader.GLES_SORCE_OBJ);
		
		float[] color=CommonUtil.getColorByString(colorCode);
		//Log.i("",color[0]+" "+color[1]+" "+color[2]+" "+color[3]);
		int size=(int)Math.ceil(loader.temp.length/3)*4;
		float[] colorData =new float[size];
		float c;
		int idx;
		for(int i=0;i<size;++i){
			idx=i%4;
			c=color[idx];
			colorData[i]=c;
			
		}
        
		nShapePoints=loader.temp;
		nPositions=getBuffer(loader.temp);
		nColors=getBuffer(colorData);
		nNormals=getBuffer(loader.normals_temp);
		nTextures=getBuffer(loader.tex_temp);
		if(textureID!=-1){
			nTextureResource=textureID;
		}
		
	}
	protected void doAnimation()
	{
		loader.drawFrame();
	}
	
}
