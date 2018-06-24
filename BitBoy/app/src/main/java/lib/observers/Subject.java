package lib.observers;

import java.util.Map;

public interface Subject {
	public void registerObserver(Observer o, String key);

	public void removeObserver(Observer o);
	public void removeObserver(Observer o, String key);
	public void notifyPost(String key,Object value, Map<String,Object> userData);
}