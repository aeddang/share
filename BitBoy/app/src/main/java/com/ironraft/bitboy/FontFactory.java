package com.ironraft.bitboy;


import android.app.Activity;
import android.graphics.Typeface;


public class FontFactory {



    private static FontFactory instence;


    public Typeface FontTypeBold;
    public Typeface FontTypeRegula;
    public Typeface FontTypeThin;

	public static FontFactory getInstence()
    {
        return instence;
	}

    public FontFactory(Activity ac) {
    	instence=this;

        this.FontTypeThin = Typeface.createFromAsset(ac.getAssets(), "NotoSansCJKkr-Thin.otf");
        this.FontTypeRegula = Typeface.createFromAsset(ac.getAssets(), "NotoSansCJKkr-Regular.otf");
        this.FontTypeBold= Typeface.createFromAsset(ac.getAssets(), "NotoSansCJKkr-Bold.otf");
    }





}

