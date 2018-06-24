package com.aeddang.clipmaker.pagerecord;





import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import lib.GraphicUtil;


public class RecordObject extends Object {



    public ArrayList<Rect> pozs;
    public byte[] data;
    public Bitmap clip,filter;
    public int clipNum;
    public RecordObject()
	{
        clipNum=0;
    }

    public Bitmap getRecordImage(Bitmap img,double sc)
    {
        ArrayList<Object> imgs=new ArrayList<Object>();
        ArrayList<Rect> recs=new ArrayList<Rect>();

        Log.i("", "img " + img.getWidth() + "  : " + img.getHeight());
        if (img.getWidth() != img.getHeight()) {

            Bitmap newImg = GraphicUtil.cropBitmapImageByRect(img, new Rect(0, 0, img.getWidth(), img.getWidth()));
            img.recycle();
            img = newImg;
            Log.i("", "newImg " + newImg.getWidth() + "  : " + newImg.getHeight());
            recs.add(null);
        }
        imgs.add(img);
        if (clip != null) {
            Rect pos;
            for(int i=0;i<pozs.size();++i){
                pos=pozs.get(i);
                imgs.add(clip);
                pos.left = (int) Math.round((double) pos.left * sc);
                pos.top = (int) Math.round((double) pos.top * sc);
                pos.right = (int) Math.round((double) pos.right * sc);
                pos.bottom = (int) Math.round((double) pos.bottom * sc);

                recs.add(pos);
            }

        }
        if(filter!=null){
            imgs.add(filter);
            recs.add(null);
        }

        return GraphicUtil.mergeImages(imgs, img.getWidth(), img.getHeight(), 1.0, recs);


    }
    public void remove()
    {

        removeRecycle();
    }

    private void removeRecycle()
    {
        pozs=null;

        data=null;
        clip=null;
        filter=null;

    }
    
}
