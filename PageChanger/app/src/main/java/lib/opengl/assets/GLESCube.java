package lib.opengl.assets;

import java.lang.reflect.Array;

import android.graphics.Bitmap;
import android.util.Log;
import lib.CommonUtil;
import lib.opengl.GLESObject;

public class GLESCube extends GLESObject {

	public GLESCube(String colorCode,int textureID,Bitmap textureBp){
		
		super();
		float[] color=CommonUtil.getColorByString(colorCode);
		final float[] positionData =
			{
					// In OpenGL counter-clockwise winding is default. This means that when we look at a triangle, 
					// if the points are counter-clockwise we are looking at the "front". If not we are looking at
					// the back. OpenGL has an optimization where all back-facing triangles are culled, since they
					// usually represent the backside of an object and aren't visible anyways.
					
					// Front face
					-1.0f, 1.0f, 1.0f,				
					-1.0f, -1.0f, 1.0f,
					1.0f, 1.0f, 1.0f, 
					-1.0f, -1.0f, 1.0f, 				
					1.0f, -1.0f, 1.0f,
					1.0f, 1.0f, 1.0f,
					
					// Right face
					1.0f, 1.0f, 1.0f,				
					1.0f, -1.0f, 1.0f,
					1.0f, 1.0f, -1.0f,
					1.0f, -1.0f, 1.0f,				
					1.0f, -1.0f, -1.0f,
					1.0f, 1.0f, -1.0f,
					
					// Back face
					1.0f, 1.0f, -1.0f,				
					1.0f, -1.0f, -1.0f,
					-1.0f, 1.0f, -1.0f,
					1.0f, -1.0f, -1.0f,				
					-1.0f, -1.0f, -1.0f,
					-1.0f, 1.0f, -1.0f,
					
					// Left face
					-1.0f, 1.0f, -1.0f,				
					-1.0f, -1.0f, -1.0f,
					-1.0f, 1.0f, 1.0f, 
					-1.0f, -1.0f, -1.0f,				
					-1.0f, -1.0f, 1.0f, 
					-1.0f, 1.0f, 1.0f, 
					
					// Top face
					-1.0f, 1.0f, -1.0f,				
					-1.0f, 1.0f, 1.0f, 
					1.0f, 1.0f, -1.0f, 
					-1.0f, 1.0f, 1.0f, 				
					1.0f, 1.0f, 1.0f, 
					1.0f, 1.0f, -1.0f,
					
					// Bottom face
					1.0f, -1.0f, -1.0f,				
					1.0f, -1.0f, 1.0f, 
					-1.0f, -1.0f, -1.0f,
					1.0f, -1.0f, 1.0f, 				
					-1.0f, -1.0f, 1.0f,
					-1.0f, -1.0f, -1.0f,
			};	
			
			// R, G, B, A
			final float[] colorData =
			{				
					// Front face (red)
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					
					// Right face (green)
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					
					// Back face (blue)
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					
					// Left face (yellow)
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					
					// Top face (cyan)
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					
					// Bottom face (magenta)
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3],			
					color[0], color[1], color[2], color[3],	
					color[0], color[1], color[2], color[3]
			};
			
			// X, Y, Z
			// The normal is used in light calculations and is a vector which points
			// orthogonal to the plane of the surface. For a cube model, the normals
			// should be orthogonal to the points of each face.
			final float[] normalData =
			{												
					// Front face
					0.0f, 0.0f, 1.0f,				
					0.0f, 0.0f, 1.0f,
					0.0f, 0.0f, 1.0f,
					0.0f, 0.0f, 1.0f,				
					0.0f, 0.0f, 1.0f,
					0.0f, 0.0f, 1.0f,
					
					// Right face 
					1.0f, 0.0f, 0.0f,				
					1.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,				
					1.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					
					// Back face 
					0.0f, 0.0f, -1.0f,				
					0.0f, 0.0f, -1.0f,
					0.0f, 0.0f, -1.0f,
					0.0f, 0.0f, -1.0f,				
					0.0f, 0.0f, -1.0f,
					0.0f, 0.0f, -1.0f,
					
					// Left face 
					-1.0f, 0.0f, 0.0f,				
					-1.0f, 0.0f, 0.0f,
					-1.0f, 0.0f, 0.0f,
					-1.0f, 0.0f, 0.0f,				
					-1.0f, 0.0f, 0.0f,
					-1.0f, 0.0f, 0.0f,
					
					// Top face 
					0.0f, 1.0f, 0.0f,			
					0.0f, 1.0f, 0.0f,
					0.0f, 1.0f, 0.0f,
					0.0f, 1.0f, 0.0f,				
					0.0f, 1.0f, 0.0f,
					0.0f, 1.0f, 0.0f,
					
					// Bottom face 
					0.0f, -1.0f, 0.0f,			
					0.0f, -1.0f, 0.0f,
					0.0f, -1.0f, 0.0f,
					0.0f, -1.0f, 0.0f,				
					0.0f, -1.0f, 0.0f,
					0.0f, -1.0f, 0.0f
			};
			
			// S, T (or X, Y)
			// Texture coordinate data.
			// Because images have a Y axis pointing downward (values increase as you move down the image) while
			// OpenGL has a Y axis pointing upward, we adjust for that here by flipping the Y axis.
			// What's more is that the texture coordinates are the same for every face.
			final float[] textureCoordinateData =
			{												
					// Front face
					0.0f, 0.0f, 				
					0.0f, 1.0f,
					1.0f, 0.0f,
					0.0f, 1.0f,
					1.0f, 1.0f,
					1.0f, 0.0f,				
					
					// Right face 
					0.0f, 0.0f, 				
					0.0f, 1.0f,
					1.0f, 0.0f,
					0.0f, 1.0f,
					1.0f, 1.0f,
					1.0f, 0.0f,	
					
					// Back face 
					0.0f, 0.0f, 				
					0.0f, 1.0f,
					1.0f, 0.0f,
					0.0f, 1.0f,
					1.0f, 1.0f,
					1.0f, 0.0f,	
					
					// Left face 
					0.0f, 0.0f, 				
					0.0f, 1.0f,
					1.0f, 0.0f,
					0.0f, 1.0f,
					1.0f, 1.0f,
					1.0f, 0.0f,	
					
					// Top face 
					0.0f, 0.0f, 				
					0.0f, 1.0f,
					1.0f, 0.0f,
					0.0f, 1.0f,
					1.0f, 1.0f,
					1.0f, 0.0f,	
					
					// Bottom face 
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
		
		defaultStateSetting(colorData, 0.3f);
	}
	
	
}
