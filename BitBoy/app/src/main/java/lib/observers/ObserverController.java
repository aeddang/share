
package lib.observers;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObserverController implements Subject {

	private Map<String,ArrayList<Observer>> observers;


	private static ObserverController instence;
	public static ObserverController shareInstence(){

		if(instence == null){

			instence = new ObserverController();
		}

		return instence;
	}

	public ObserverController() {

		Log.i("ObserverController", "ObserverController init");
		this.observers = new HashMap<String,ArrayList<Observer>>();
	}

	@Override
	public void registerObserver(Observer o, String key) {

		ArrayList<Observer> group = this.observers.get(key);
		if(group == null){

			group = new ArrayList<Observer>();
			this.observers.put(key,group);
		}
		group.add(o);

	}
	@Override
	public void removeObserver(Observer o) {

		Set<String> allkeys = this.observers.keySet();
		for (String key : allkeys) this.removeObserver(o, key);

	}
	@Override
	public void removeObserver(Observer o, String key) {
		ArrayList<Observer> group = this.observers.get(key);
		Observer observer;
		if (group != null){
			for (int i = group.size()-1 ; i >= 0; --i) {
				observer = group.get(i);
				if(observer == o) {
					group.remove(o);
				}
			}

		}

	}

	@Override
	public void notifyPost(String key,Object value, Map<String,Object> userData)
	{

		ArrayList<Observer> group = this.observers.get(key);
		if(group == null){

			return;
		}
		Observer observer;
		for (int i = 0; i < group.size(); i++) {


			observer = group.get(i);
			observer.notification(key,value, userData);
		}
	}



}