package lib.datamanager;







import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class FileObject extends Object {





    //public static final String CLIP_DIR="clipmaker";

    public String key,name;
    public File file;

    public FileObject()
	{
        key="";
        name="";
        file=null;
    }
    public void addUriFile(Uri uri) {

        file = new File(uri.getPath());
    }
    public void addBitmapFile(Bitmap bitmap,Bitmap.CompressFormat format)
    {

        OutputStream outStream = null;

        String fileName = "uploadFile";
        if(format == Bitmap.CompressFormat.JPEG)
        {
            fileName = fileName+".jpg";
        }else if(format == Bitmap.CompressFormat.PNG)
        {
            fileName = fileName+".png";
        }
        file = new File(Environment.getExternalStorageDirectory().toString()+"/", fileName);
        Log.i("SAVEfile",file.getName());
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(format, 100, outStream);
            outStream.flush();
            outStream.close();
            Log.i("SAVE","SAVE IMAGE SUCCESS : " + file.length());
        }
        catch(Exception e)
        {
            Log.i("SAVE","SAVE IMAGE FAIL"+e.toString());
        }

    }


}
