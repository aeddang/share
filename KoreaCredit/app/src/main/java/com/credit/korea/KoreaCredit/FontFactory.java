package com.credit.korea.KoreaCredit;


import android.app.Activity;
import android.graphics.Typeface;


public class FontFactory {



     FontFactory instence;


    public Typeface FONT_KR;
    public Typeface FONT_KR_B;
    public Typeface FONT_ENG;

	public static FontFactory getInstence()
    {


        return instence;
	}

    public FontFactory(Activity ac) {
    	instence=this;
        FONT_KR_B=Typeface.createFromAsset(ac.getAssets(), "RixGoEB.ttf");
        FONT_KR=Typeface.createFromAsset(ac.getAssets(), "RixGoM.ttf");
        FONT_ENG=Typeface.createFromAsset(ac.getAssets(), "Roboto-Regular.ttf");
    }





}

