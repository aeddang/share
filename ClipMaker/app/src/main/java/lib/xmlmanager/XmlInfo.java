package lib.xmlmanager;

import java.util.ArrayList;
import java.util.Map;

import android.util.Log;




public class XmlInfo
{
		
		private ArrayList<XmlObject> dataList;
		private XmlObject rootData;
		private XmlObject data;
		public XmlInfo ()
		{
			rootData=null;
			data=null;
        	dataList=new ArrayList<XmlObject>();
		}
		public void removeInfo()
		{
			if(rootData!=null){
				rootData.removeObject();
				rootData=null;
			}
			if(data!=null){
				data.removeObject();
				data=null;
			}
			for(int i=0;i<dataList.size();++i){
				dataList.get(i).removeObject();
			}
			dataList.clear();
			dataList=null;
		}
		
		public XmlObject getRootData()
		{
			return rootData;
		}
		public void startTag(String tag, Map<String,String> attrs)
		{
			//Log.d("XMLINFO", "stag="+tag);
			XmlObject cdata=new XmlObject(tag,attrs);
			if(rootData==null){
				rootData=cdata;
			}
			if(data==null){
				if(dataList.size()>0){
					data=dataList.get(dataList.size()-1);
					
				}
			}
			if(data!=null){
			   data.addList(cdata);
			}
		    data=cdata;
		    dataList.add(cdata);
			
		}
		
		
		public void addValue(String value)
		{
			
			if(data==null){
				return;
			}
			data.setValue(value);
		}
		public void endTag(String tag)
		{
			//Log.d("XMLINFO", "endtag="+tag);
			if(dataList.size()<1){
				return;
			}
			dataList.remove(dataList.size()-1);
			data=null;
		}
		
		
}//class
