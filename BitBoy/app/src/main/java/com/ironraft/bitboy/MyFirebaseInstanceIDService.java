package com.ironraft.bitboy;


import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ironraft.bitboy.model.AppManager;
import lib.datamanager.DataManager;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
	private DataManager jsonManager;
	@Override
	public void onTokenRefresh() {
		// Get updated InstanceID token.
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
		Log.i("MyFirebase", "Refreshed token: " + refreshedToken);
		AppManager.getInstence().registerPushToken(refreshedToken);
	}



}
