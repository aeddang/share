package lib.xmlmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;





public class XmlObject
{
	
	private String tag;
	private String value;
	private Map<String,String> attrs;
	private ArrayList<XmlObject> children;
	
	
	public XmlObject ()
	{
		tag="";
		value="";
		attrs=new HashMap<String,String>();
		children=new ArrayList<XmlObject>();
	}
	public XmlObject (String _tag,Map<String,String> _attrs)
	{
		tag=_tag;
		attrs=_attrs;
		value="";
		children=new ArrayList<XmlObject>();
	}
	
	public void removeObject ()
	{
		if(attrs!=null){
			attrs.clear();
			attrs=null;
		}
		if(children!=null){
			children.clear();
			children=null;
		}
    }
	
	public void setValue (String _value)
	{
		value=_value;
    }
	public void addList (XmlObject list)
	{
		
		children.add(list);
    }
	public String getXmlString ()
	{
		String str= "tag : "+tag+" value : "+value +" attrs :"+attrs.toString();
		for(int i=0;i<children.size();++i){
			str=str+" children"+i+" : "+children.get(i).getString();
		}
		return str;
    }
	public String getValue ()
	{
		return value;
    }
	
	public String getValue (String dfvalue)
	{
		if(value.equals("")){
			return dfvalue;
		}else{
			return value;
		}
		
    }
	public String getTag ()
	{
		return tag;
    }
	public int childNum ()
	{
		return children.size();
    }
	public ArrayList<XmlObject> getChildenByTag (String tag)
	{
		ArrayList<XmlObject> returnArr=new ArrayList<XmlObject>();
		XmlObject obj;
		for(int i=0;i<children.size();++i){
			obj=children.get(i);
			if(obj.getTag().equals(tag)){
				returnArr.add(obj);
			}
			
		}
		return returnArr;
    }
	public String getString ()
	{
		String str= "tag : "+tag+" value : "+value +" attrs :"+attrs.toString();
		for(int i=0;i<children.size();++i){
			str=str+" children"+i+" : "+children.get(i).getString();
		}
		return str;
    }
	public XmlObject getChildByTag (String tag)
	{
		XmlObject obj;
		for(int i=0;i<children.size();++i){
			obj=children.get(i);
			if(obj.getTag().equals(tag)){
				return obj;
			}
			
		}
		return new XmlObject();
    }
	
	public XmlObject getChildByIndex (int idx)
	{
		if(children.size()<=idx){
			return null;
		}
		return children.get(idx);
    }
	public String getAttr (String attr,String df)
	{
		if(attrs.get(attr)==null){
			return df;
		}else{
			if(attrs.get(attr).equals("")){
				return df;
			}
		    return attrs.get(attr);
		}
		
    }
	public String getAttr (String attr)
	{
		return attrs.get(attr);
    }
	
	
	
}//package