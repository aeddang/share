package com.deliverycommerce.idel;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deliverycommerce.Config;
import com.deliverycommerce.LocationInfo;
import com.deliverycommerce.MainActivity;
import com.deliverycommerce.R;
import com.deliverycommerce.UserInfo;
import com.deliverycommerce.delivery.DeliveryObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.core.PageObject;
import lib.imagemanager.ImageLoader;
import lib.imagemanager.ImageLoader.ImageLoaderDelegate;
import lib.jsonmanager.JsonManager;


@SuppressLint("NewApi")
public class IdelList extends FrameLayout implements ImageLoaderDelegate,JsonManager.JsonManagerDelegate {

	private TextView title,dc,desc,price0,price1;
	public IdelObject info;
    private FrameLayout imgBox;
	private ImageView img;
	private ImageLoader imageLoader;
    private JsonManager jsonManager;
	private Bitmap bitmap;

	public IdelList(Context context)
	{
		super(context); 
	    View.inflate(context, R.layout.idel_list,this);
	    title=(TextView) findViewById(R.id._title);
        desc=(TextView) findViewById(R.id._desc);
        price0=(TextView) findViewById(R.id._price0);
        price1=(TextView) findViewById(R.id._price1);
	    dc=(TextView) findViewById(R.id._dc);
	    
	    imgBox=(FrameLayout) findViewById(R.id._imgBox);
	    img=(ImageView) findViewById(R.id._img);

        img.setVisibility(View.GONE);

	   
    }

	public void setData(IdelObject _info)
	{
        info=_info;

        title.setText("");
        desc.setText("loading...");
        price0.setText("");
        price1.setText("");
        dc.setText("");
		removeImage();
        removeJsonLoader();


        if(info.isLoaded==false){
            loadData();
        }else{
            loadImage();
            setList();
        }
    }
    private void setList()
    {
        title.setText(info.storename);
        desc.setText(info.y4comment+info.y4comment+info.y4comment);


        if(info.y4listprice.equals("0")==true || info.y4listprice.equals("")==true){
            price0.setText("");
            price1.setText("");

        }else{
            price0.setText(info.y4listprice+"원");
            price1.setText(info.y4saleprice+"원");
            price0.setPaintFlags(price0.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }




        if(info.y4discountper.equals("")){

        }else{

            dc.setText(info.y4discountper+"%");
        }

    }
    private void loadImage()
    {
        img.setVisibility(View.GONE);
        imageLoader=new ImageLoader(this);
        imageLoader.loadImg(info.img);
    }
    private void loadFail()
    {
        desc.setText("no data...");
    }
    private void loadData()
    {

        jsonManager=new JsonManager(this);
        Map<String,String> paramA=new HashMap<String,String>();
        paramA.put("apuid", UserInfo.getInstence().userID);

        paramA.put("lot", LocationInfo.getInstence().getLongitude()+"");
        paramA.put("lat", LocationInfo.getInstence().getLatitude()+"");
        paramA.put("bid", info.code);
        jsonManager.loadData(Config.API_LOAD_BEACON_INFO,paramA);


    }
    public void onJsonCompleted(JsonManager manager,String xml_path,JSONObject result)
    {
        MainActivity.getInstence().loadingStop();
        JSONArray results = null;

        try {
            results = result.getJSONArray("datalist");
        } catch (JSONException e) {
            loadFail();
        }
        if( results.length()<1){
            loadFail();
            return;
        }
        JSONObject data=null;
        try {
            data = results.getJSONObject(0);
            info.setData(data);
            loadImage();
            setList();
        } catch (JSONException e) {
            loadFail();
            return;
        }


    }


    public void onJsonLoadErr(JsonManager manager,String xml_path) {

        loadFail();
    }
	public void  removeList()
	{
		removeImage();
        removeJsonLoader();
	}

    private void  removeJsonLoader()
    {
        if(jsonManager!=null){
            jsonManager.destory();
            jsonManager=null;

        }

    }
	private void  removeImage()
	{
		 img.setImageBitmap(null);
    	 if(imageLoader!=null){
    		 imageLoader.removeLoader();
    		 imageLoader=null;
    	 }
    	 
    	 if(bitmap!=null){
    		 bitmap.recycle();
    		 bitmap=null;
    		 
    	 }
		
	}
	public void onImageLoadCompleted(ImageLoader loader,Bitmap image ){
   		
    	if( image==null){
    		Drawable drawable=getResources().getDrawable( R.drawable.transparent);
    		img.setImageDrawable(drawable);
    		return;
		}
    	bitmap=image;
        img.setVisibility(View.VISIBLE);
		img.setImageBitmap(image);

   	}
    
}
