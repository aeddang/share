package lib.observers;


import java.util.Map;

public interface Observer {
	public void notification(String notify,Object value, Map<String,Object> userData);
}