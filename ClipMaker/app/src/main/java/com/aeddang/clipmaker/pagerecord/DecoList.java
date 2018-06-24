package com.aeddang.clipmaker.pagerecord;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.aeddang.clipmaker.DataObject;
import com.aeddang.clipmaker.MainActivity;
import com.aeddang.clipmaker.R;

import lib.ViewUtil;


public class DecoList extends FrameLayout implements DataObject.DataObjectDelegate {


	public DataObject info;
	private ImageView img,bg;



    public DecoListDelegate delegate;
    public interface DecoListDelegate
    {
        void selectList(DataObject obj,DecoList list);
    }


	public DecoList(Context context,DecoListDelegate _delegate)
	{
		super(context); 
	    View.inflate(context, R.layout.deco_list,this);
        img=(ImageView) findViewById(R.id._img);
        bg=(ImageView) findViewById(R.id._bg);
        delegate=_delegate;
        unSelectedList();
    }
	

	public void setData(DataObject _info)
	{
        clearList();
        info=_info;
        info.delegate=this;
        info.loadPreviewImage();
        if(PageRecord.selectedClipKey.equals(info.key)==true || PageRecord.selectedFilterKey.equals(info.key)==true){
            selectedList();
        }else{
            unSelectedList();

        }
        setCompleter();
    }
    private void setCompleter()
    {

        if(info.getComplete()==true){

            ViewUtil.setAlpha(img,1.0f);
        }else{
            ViewUtil.setAlpha(img,0.85f);
        }
    }
    private void  clearList()
    {
        if(info==null){

            return;
        }


        img.setImageResource(R.drawable.transparent);
        info.downLoadStop();
        info.delegate = null;

        info=null;

    }
	public void  removeList()
	{
        clearList();

    }
    public void  selectedList()
    {
        ViewUtil.setAlpha(bg,1.0f);

    }
    public void  unSelectedList()
    {
        ViewUtil.setAlpha(bg,0.85f);

    }

    public void  roadImgs()
    {
        if(info!=null){

            info.loadImages();
            if(info.getComplete())
            {

                delegate.selectList(info,this);
            }else{
                MainActivity.getInstence().loadingStart(true);
                info.downLoadImages();
            }

        }

    }

    public  void previewComplete(DataObject obj,Bitmap prevImg){
        if(prevImg!=null) {

           // Bitmap bitmap = Bitmap.createBitmap(prevImg);
            img.setImageBitmap(prevImg);
        }


    }
    public  void downLoadError(DataObject obj){

        MainActivity.getInstence().loadingStop();
        MainActivity.getInstence().viewAlert("",R.string.msg_network_err,null);

    }
    public  void downLoadComplete(DataObject obj){
        MainActivity.getInstence().loadingStop();
        delegate.selectList(info,this);

        setCompleter();
    }

    
}
