package lib.opengl.assets;

import android.graphics.Bitmap;
import android.util.Log;
import lib.CommonUtil;
import lib.opengl.GLESObject;

public class GLESRect extends GLESObject {

	public GLESRect(String colorCode,int textureID,Bitmap textureBp){
		
		super();
		
		float[] color=CommonUtil.getColorByString(colorCode);
		//Log.i("",color[0]+" "+color[1]+" "+color[2]+" "+color[3]);
		float[] positionData =
				{
						-1.0f, 1.0f, 0.0f,				
						-1.0f, -1.0f, 0.0f,
						1.0f, 1.0f, 0.0f, 
						-1.0f, -1.0f, 0.0f, 				
						1.0f, -1.0f, 0.0f,
						1.0f, 1.0f, 0.0f
						
						
				};	
				
		
		float[] colorData =
				{				
						
						color[0], color[1], color[2], color[3],				
						color[0], color[1], color[2], color[3],	
						color[0], color[1], color[2], color[3],	
						color[0], color[1], color[2], color[3],			
						color[0], color[1], color[2], color[3],	
						color[0], color[1], color[2], color[3]	
						
				};
			
				
		float[] normalData =
				{												
						0.0f, 0.0f, 0.0f,				
						0.0f, 0.0f, 0.0f,
						0.0f, 0.0f, 0.0f,
						0.0f, 0.0f, 0.0f,				
						0.0f, 0.0f, 0.0f,
						0.0f, 0.0f, 0.0f
				};
				
				
		float[] textureCoordinateData =
				{												
						
						0.0f, 0.0f, 				
						0.0f, 1.0f,
						1.0f, 0.0f,
						0.0f, 1.0f,
						1.0f, 1.0f,
						1.0f, 0.0f				
				};
		
		
		nShapePoints=positionData;
		nPositions=getBuffer(positionData);
		nColors=getBuffer(colorData);
		nNormals=getBuffer(normalData);
		nTextures=getBuffer(textureCoordinateData);
		if(textureID!=-1){
			nTextureResource=textureID;
		}
		
	}
	
	
}
