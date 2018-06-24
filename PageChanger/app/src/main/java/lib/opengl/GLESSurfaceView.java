package lib.opengl;





import lib.ViewUtil;
import android.content.Context;

import android.graphics.Color;
import android.graphics.PixelFormat;

import android.opengl.GLSurfaceView;


public class GLESSurfaceView extends GLSurfaceView   
{
	
	
	public GLESSurfaceViewDelegate delegate;
	public GLESRenderer mRenderer;
    
	public GLESSurfaceView(Context context,GLESRendererInfo info) {

              super(context);
              
              setBackgroundColor(Color.TRANSPARENT);
              initGL(info);
              

    }
    private void initGL(GLESRendererInfo info) 
    {
    	
    	this.setEGLContextClientVersion(2);
        this.setEGLConfigChooser(8,8,8,8,16,0);
        mRenderer = new GLESRenderer(info);
        setRenderer(mRenderer);
        this.getHolder().setFormat( PixelFormat.RGB_565 );
        this.getHolder().setFormat( PixelFormat.TRANSLUCENT );
        this.setZOrderOnTop(false);   
    }
    
    public void removeSurfaceView() 
    {
    	onPause(); 
    	
    	mRenderer.removeRenderer();
    	mRenderer=null;
    	delegate=null;
    	ViewUtil.remove(this);
    }
    
    public interface GLESSurfaceViewDelegate
	{
		
	}
    
}
