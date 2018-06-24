package lib.opengl;

import java.util.ArrayList;

public class GLESRendererInfo 
{

	public GLESTransform lightTransform;
    public GLESTransform eyeTransform;
    public GLESTransform lensTransform;
    public int lensType;
    public float near,far;
    
    public boolean usedTexture;
    public boolean isMultiPiking;
    public ArrayList<GLESObject> programs;
	
	public GLESRendererInfo() {
        
        isMultiPiking=true;
        usedTexture=false;
        programs=new ArrayList<GLESObject>();
        lightTransform=new GLESTransform();
        lightTransform.z=-5;
        lightTransform.y=0;
        lightTransform.rotateX=0;
        lightTransform.rotateY=1;
        lightTransform.rotateZ=0;
        
        eyeTransform=new GLESTransform();
        eyeTransform.z=-10;
        eyeTransform.rotateY=1;
        
        lensTransform=new GLESTransform();
        lensTransform.rotate=45;
        lensTransform.rotateY=1;
        near=3;
        far=15;
        lensType=GLESRenderer.PERSPECTIVE;
        
        
    }
	
}
