package lib.opengl.assets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


import edu.union.graphics.FloatMesh;
import edu.union.graphics.MD2Loader;
import edu.union.graphics.Mesh;
import edu.union.graphics.Model;
import edu.union.graphics.ModelLoader;
import edu.union.graphics.ObjLoader;

public class GLESShaderLoader {
     
	
	public static final int GLES_SORCE_MD2=0;
	public static final int GLES_SORCE_OBJ=1;
	
	private int currentFrame, nextFrame;
	private int ix = 0;
	private int frameCount;
	
	private float[][] vertices;
	public float[] drawVertices;
	private float[][] normals;
	public float[] drawNormals;
	
	private int vertexCount;
	private int sorceType;
	public float[] temp;
	public float[] normals_temp;
	public float[] tex_temp;
	
	private float[] textureCoordinateData=
	{												
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f				
    };
	
	public GLESShaderLoader(Context context,int rid,int _sorceType){
    	 
		 sorceType=_sorceType;
		 ModelLoader loader;
		 switch(sorceType)
		 {
			 case  GLES_SORCE_MD2:
				 loader = new MD2Loader();
				 break;
			 default:
				 loader = new ObjLoader();
			     break;
			  
		 }
		 
         
		 loader.setFactory(FloatMesh.factory());
         try {
         	Model m = loader.load(context.getResources().openRawResource(rid));
         	Log.i("GLESObjLoader", "Frames: " + m.getFrameCount());
         	
         	for (int i = 0; i < m.getFrameCount(); ++i) {
         		Mesh mesh = m.getFrame(i).getMesh();
         		mesh.scale(0.05f);
         	}
         	
         	objRenderer(m);
         	
              
         } catch (IOException ex) {
         	 Log.e("GLESObjLoader", "Loading obj", ex);
         }
         
    }
	public void drawFrame(){
		
		float fraction = ((float)ix) / 10;
		interpolate(fraction, vertices[currentFrame], vertices[nextFrame], drawVertices);
		interpolate(fraction, normals[currentFrame], normals[nextFrame], drawNormals);
		
		ix++;
		if (ix % 10 == 0) {
			currentFrame = (currentFrame + 1) % frameCount;
			nextFrame = (nextFrame + 1) % frameCount;
		}
	}  
	
	private void interpolate(float fraction, float[] a, float[] b, float[] out) {
		for (int i = 0; i < a.length; ++i) {
			out[i] = (a[i] * (1 - fraction)) + b[i] * fraction;
		}
	}
	private void objRenderer(Model model) {
		
		frameCount = model.getAnimation(0).getEndFrame();
		//this.frameCount = 16;
		vertices = new float[frameCount][];
		normals = new float[frameCount][];
		for (int i = 0; i < frameCount; ++i) {
			loadMesh(model.getFrame(i).getMesh(), i);
		}
		drawNormals = new float[vertexCount * 3];
		drawVertices = new float[vertexCount * 3];
		
	}
	
	private void loadMesh(Mesh mesh, int frame_ix) {
		vertexCount = mesh.getFaceCount() * 3;
		vertices[frame_ix] = new float[vertexCount * 3];
		normals[frame_ix] = new float[vertexCount * 3];
		temp = vertices[frame_ix];
		normals_temp = normals[frame_ix];
		tex_temp = new float[vertexCount *2];
    	int ix2 = 0, ix3 = 0;
    	int texture_ix=0;
    	int texture_ix_len=textureCoordinateData.length;
    	for (int i = 0; i < mesh.getFaceCount(); ++i) {
    		int[] face_ix = mesh.getFace(i);
    		int[] normal_ix = mesh.getFaceNormals(i);
    		int[] txt_ix={0};
    		if(sorceType==GLES_SORCE_MD2){
    			txt_ix = mesh.getFaceTextures(i);
   			}
   			for (int j = 0; j < 3; ++j) {
    			float[] vertex = mesh.getVertexf(face_ix[j]);
    			float[] normal = mesh.getNormalf(normal_ix[j]);
    			if(sorceType==GLES_SORCE_MD2){
    				float[] tex_coord = mesh.getTextureCoordinatef(txt_ix[j]);
    				tex_temp[ix2++] = tex_coord[0];
        			tex_temp[ix2++] = tex_coord[1];
    			}else{
    				tex_temp[ix2++] = textureCoordinateData[texture_ix%texture_ix_len];
        			texture_ix++;
        			tex_temp[ix2++] = textureCoordinateData[texture_ix%texture_ix_len];
        			texture_ix++;
    				
    			}
    			
    			normals_temp[ix3] = normal[0];
    			temp[ix3++] = vertex[0];
    			normals_temp[ix3] = normal[1];
    			temp[ix3++] = vertex[1];
    			normals_temp[ix3] = normal[2];
    			temp[ix3++] = vertex[2];
    		}
		}
		
	}
	
	
}
