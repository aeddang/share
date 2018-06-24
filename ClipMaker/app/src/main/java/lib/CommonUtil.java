package lib;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class CommonUtil 
{

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    public static boolean isDeviceOnline(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public static String readTextFileFromRawResource(final Context context,
			final int resourceId)
	{
		final InputStream inputStream = context.getResources().openRawResource(
				resourceId);
		final InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream);
		final BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);

		String nextLine;
		final StringBuilder body = new StringBuilder();

		try
		{
			while ((nextLine = bufferedReader.readLine()) != null)
			{
				body.append(nextLine);
				body.append('\n');
			}
		}
		catch (IOException e)
		{
			return null;
		}

		return body.toString();
	}
    public static String getMarketUrl (String packageName)
    {
        return "market://details?id="+packageName;
    }
	public static String getMarketUrl (Context context)
	{
    	return "market://details?id="+context.getPackageName();
	}
	public static void goLink (Context context,String link)
	{
		Intent i = new Intent(Intent.ACTION_VIEW); 
		Uri u = Uri.parse(link); 
		i.setData(u); 
		context.startActivity(i);
		
	}
	
	public static InputFilter getRestrictSpecialInput ()
	{
		return new InputFilter() {
			  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				   Pattern ps = Pattern.compile("^[0-9가-힣ㄱ-ㅎㅏ-ㅣ\u318D\u119E\u11A2\u2025a-zA-Z]+$"); 
				   if (!ps.matcher(source).matches()) {
				     return "";
				   }
				   return null;
				   
				   
				  }
		  };
		
	}
	public static InputFilter getOnlyEngInput ()
	{
		return new InputFilter() {
			  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				   Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
				   if (!ps.matcher(source).matches()) {
				     return "";
				   }
				   return null;
				   
				   
				  }
		  };
		
	}
	
	public static String onlyNum(String str) {
		  if ( str == null ) return "";
		  
		  StringBuffer sb = new StringBuffer();
		  for(int i = 0; i < str.length(); i++){
		    if( Character.isDigit( str.charAt(i) ) ) {
		    sb.append( str.charAt(i) );
		    }
		  }
		  return sb.toString();
	} 
		

	
	public static int[] getColorArray (int div)
	{
		 int size=div*4;
		 int[] colorA=new int[size];
		 float dt=1/(float)(div-1);
		 float d=0f;
		 
	     for(int i=0;i<size;++i){
	         int r=0;
		     int g=0;
		     int b=0;
	    	 
	    	 float df=(float)(i%div);
	    	 d=(255f*df*dt);
	    	 
	    	 int line=(int)Math.floor(i/div);
	    	 switch(line){
	    	 case 0:
	    		 r=(int)Math.round(d);
	    		 g=(int)Math.round(d);
	    		 b=(int)Math.round(d);
	    		 break;
	    	 case 1:
	    		 r=255;
	    		 g=0;
	    		 
	    		 b=(int)Math.round(d);
	    		 
	    		 break;
	    	 case 2:
	    		 r=(int)Math.round(d);
	    		 g=255;
	    		 b=0;
	    		 
	    		 break;
	    	 case 3:
	    		 r=0;
	    		 g=(int)Math.round(d);
	    		 b=255;
	    		 break;
	    	 
	    	 }
	    	
	    	 colorA[i]=Color.rgb(r, g, b);
	     }
		 return  colorA;
	}
	
	public static void clearApplicationCache(Context context,java.io.File dir){
        if(dir==null)
            dir = context.getCacheDir();
        else;
        if(dir==null)
            return;
        else;
           java.io.File[] children = dir.listFiles();
        try{
            for(int i=0;i<children.length;i++)
                if(children[i].isDirectory())
                	clearApplicationCache(context,children[i]);
                else children[i].delete();
        }
        catch(Exception e){}
    }

	
	public static String getCurrentTimeCode ()
	{
		long now = System.currentTimeMillis();
		Date dat = new Date(now);
		SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMddHHmmss");
    	return dateNow.format(dat);
	}
	
	public static Map<String,String> getQurry (String qurryString)
	{
		Log.i("","qurryString="+qurryString);
		
		String[] qurryA=qurryString.split("\\?");
		//Log.i("","qurryA");
		
		if(qurryA.length<2){
			return null;
		}
		//Log.i("","qurryA[1]="+qurryA[1]);
		
		qurryA=qurryA[1].split("\\&");
		if(qurryA.length<1){
			return null;
		}
		//Log.i("","qurryA="+qurryA.length);
		Map<String,String> qurry=new HashMap<String,String>();
		for(int i=0;i<qurryA.length;++i){
			String[] q=qurryA[i].split("\\=");
			if(q.length>=2){
				//Log.i("","q="+q[0]+" "+q[1]);
				qurry.put(q[0], q[1]);
			}
			
		}
	    return qurry;
	}
	
	public static String intToText (int n,int len)
	{
		String str = String.valueOf(n);
		int num = str.length();
		if (num >= len)
		{

		}
		else
		{
			for (int i = num; i < len; ++i)
			{
				str = "0" + str;
			}
		}

		return str;
	}
	
	public static String getDateByCode (int yymmdd,String key)
	{
		String ymdStr=String.valueOf(yymmdd);
		if(ymdStr.length()!=8){
			return "";
		}
		
		String str=ymdStr.substring(0,4)+key+ymdStr.substring(4,6)+key+ymdStr.substring(6,8);
		return str;
	}
	
	@SuppressWarnings("deprecation")
	public static int getDays (int year,int mon,int day)
	{
	
		Date someday = new Date(year,mon - 1,day);
		return someday.getDay();
	}
	
	public static int getDaysInMonth (int year,int mon)
	{
		int daysInMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};
		if (isLeapYear(year) == true)
		{
			daysInMonth[1] = 29;
		}
		return daysInMonth[mon - 1];
	}
	
	public static int getRandomInt (int range)
	{
		int idx=(int)Math.floor(Math.random()*range);
		if(idx==range){
			idx=range-1;
		}
		return idx;
	}
	public static int getRandomInt(int range,int except){
	    int r=getRandomInt(range-1);
	    if(r>=except){
	        r++;
	    }
	    return r;
	}
	
	public static int getRandomInt(int range, ArrayList<String> exceptA)
	{

		ArrayList<String> numA=new ArrayList<String>();
		
	    for(int i=0;i<range;++i){
	    	String idx=String.valueOf(i);
	        if(exceptA.indexOf(idx)==-1){
	        	numA.add(idx);
	        }
	    }
	    
	    int r=getRandomInt(numA.size());
	    return Integer.parseInt(numA.get(r));
	}
	
	
	private static boolean isLeapYear (int year)
	{
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public  static float[] getColorByString(String cororCode)
	{
				
		
		String cd=cororCode.replace("#", "");
	    if(cd.length()!=6){
	    	float[] returnData ={0,0,0,0};	
	        return  returnData;
	    }
	    
	    String cr0=cd.substring(0, 1);
	    String cr1=cd.substring(1, 2);
	    
	    String cg0=cd.substring(2, 3);
	    String cg1=cd.substring(3, 4);
	    
	    String cb0=cd.substring(4, 5);
	    String cb1=cd.substring(5, 6);
	    
	    float r0=CommonUtil.getNumberByCode16(cr0);
	    float r1=CommonUtil.getNumberByCode16(cr1);
	    
	    float g0=CommonUtil.getNumberByCode16(cg0);
	    float g1=CommonUtil.getNumberByCode16(cg1);
	    
	    float b0=CommonUtil.getNumberByCode16(cb0);
	    float b1=CommonUtil.getNumberByCode16(cb1);
	    
	    
	    
	    float r=((r0*16.f)+r1)/256.f;
	    float g=((g0*16.f)+g1)/256.f;
	    float b=((b0*16.f)+b1)/256.f;
	    
	   
	    float[] returnData ={r,g,b,1.0f};	
	    return returnData;
	    
	    

	}
	@SuppressLint("DefaultLocale")
	public  static float getNumberByCode16(String code)
	{
	    
	    float n=0;
	    String cd=code.toUpperCase();
	   // Log.i("","code : "+cd);
	    if(cd.equals("A")==true){
	        n=10.f;
	    }else if(cd.equals("A")==true){
	        n=11.f;
	    }else if(cd.equals("B")==true){
	        n=12.f;
	    }else if(cd.equals("C")==true){
	        n=13.f;
	    }else if(cd.equals("D")==true){
	        n=14.f;
	    }else if(cd.equals("E")==true){
	        n=15.f;
	    }else if(cd.equals("F")==true){
	        n=16.f;
	    }else{
	        n=(float)Integer.parseInt(code);
	    }
	    return n;
	}
	
	@SuppressLint("FloatMath")
	public static Rect getEqualRatioRect (Rect mc,int tw,int th,Boolean smallResize,int dfWid,int dfHei)
	{
		
	    int w;
		int h;
		if(dfWid==0){
			w=mc.right;
			dfWid=w;
		}else{
			w=dfWid;
		}
		if(dfHei==0){
			h=mc.bottom;
			dfHei=h;
		}else{
			h=dfHei;
		}
		
		int p;
		if (w > tw || h > th)
		{
			if (w > tw)
			{
				p=w;
				w = tw;
				h = (int)Math.floor(h * ( (float)tw / (float)p));
			}
			if (h > th)
			{
				
				p=h;
				h = th;
				w = (int)Math.floor(w * ((float)th / (float)p));
			}
		}else{
			if(smallResize==true){
				if (w < tw)
				{
					p=w;
					w = tw;
					
					h = (int)Math.floor(h * ((float)tw / (float)p));
				}
				if (h > th)
				{
					p=h;
					h = th;
					w = (int)Math.floor(w * ((float)th / (float)p));
				}
			}
		}
		
		

		mc.left = Math.round((tw - w) / 2);
		mc.top = Math.round((th - h) / 2);
        mc.bottom = mc.top+h;
        mc.right = mc.left+w;

		return mc;
		
	}
} 
