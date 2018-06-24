package com.ironraft.bitboy;


import android.os.Handler;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService
{
	private Handler sendHandler=new Handler();
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		//Log.i("MyFirebase", "From: " + remoteMessage.getFrom());
		if (remoteMessage.getData().size() > 0) {
			final MainActivity ac = (MainActivity)MainActivity.getInstence();
			if(ac ==null) return;
			final Map<String,String> msg = remoteMessage.getData();
			sendHandler.post(new Runnable() {
				public void run() {
					ac.recivePushMsg(msg);
				}
			});

		}
		if (remoteMessage.getNotification() != null) {
			Log.i("MyFirebase", "Message Notification Body: " + remoteMessage.getNotification().getBody());
		}
	}

}
