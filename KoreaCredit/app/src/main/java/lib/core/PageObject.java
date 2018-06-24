package lib.core;


import java.util.Map;




public class PageObject{
	public int pageID;
	public Map<String,Object> info;
	public int dr;
	public Boolean isHistory;
	public Boolean isHome;
	
	
    public PageObject(int _pageID) {
    	pageID=_pageID;
    	dr=2;
    	isHistory=true;
    	isHome=false;
    	
    }

    
}
