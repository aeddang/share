package lib.gifencoder;


import android.graphics.Bitmap;

import android.os.AsyncTask;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.ArrayList;




public class GifMaker extends AsyncTask<ArrayList<Bitmap>, Integer,byte[]>{


	private static final String TAG = "GifMaker";
	public GifMakerDelegate delegate;
	private int delay,totalFrame;





	public GifMaker(GifMakerDelegate _delegate)
	{
		delegate = _delegate;

	}
	
    public void removeMaker() {
    	 delegate=null;
         this.cancel(true);
         
    }
    public void encode(ArrayList<Bitmap> bitmaps,int _delay) {

        delay=_delay;
        totalFrame=bitmaps.size();
        this.execute(bitmaps);
    		

   }
  
    protected byte[] doInBackground(ArrayList<Bitmap>... params) {


        ArrayList<Bitmap> bitmaps = params[0];
    	
    	
    	byte[] result = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.setDelay(delay);
        encoder.start(bos);
        int progress=0;
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
            progress++;
            publishProgress(progress);

        }
        encoder.finish();
        result=bos.toByteArray();
        return result;
    }
    @Override
    protected void onProgressUpdate(Integer... progress) {
        if(delegate != null) {
            delegate.onGifMakingProgress(this,progress[0].intValue());

        }
    }
    @Override
    protected void onPostExecute(byte[] result) {
    	if(delegate != null) {
            delegate.onGifMakingCompleted(this,result);
        	
        }
    }
    

    
    public interface GifMakerDelegate {
        void onGifMakingProgress(GifMaker maker, int progress);
        void onGifMakingCompleted(GifMaker maker,  byte[] gifbytes);
    }
    
}








