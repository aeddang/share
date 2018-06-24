package lib.core;


import android.graphics.Rect;

import java.util.Map;




public class PageObject{
	public int pageID;
	public Map<String,Object> info;
	public int dr;
	public Boolean isHistory;
	public Boolean isHome;

	public Boolean isGestureUsed = true;
	
    public PageObject(int _pageID) {
    	pageID=_pageID;
    	dr=2;
    	isHistory=true;
    	isHome=false;
    	
    }

    
}
