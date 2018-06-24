package lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class OverlapDictionary
{
	//view
			private Map<String,ArrayList<Object>> parent;
			
			
			public OverlapDictionary()
			{
				parent=new HashMap<String,ArrayList<Object>>();
			}
			public int size()
			{
				return parent.size();
			}
			public void put(String key,Object value)
			{
				
				ArrayList<Object> valueA=parent.get(key);
				if(valueA==null){
					valueA=new ArrayList<Object>();
					valueA.add(value);
					parent.put(key, valueA);
				}else{
					valueA.add(value);
				}
			}
			public void remove(String key)
			{
				parent.remove(key);
			}
			
			public ArrayList<Object> get(String key)
			{
				return parent.get(key);
			}
			public void clear()
			{
				parent.clear();
			}
			
}//package