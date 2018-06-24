package com.deliverycommerce;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;




public class CNotification {
 
	@SuppressWarnings("deprecation")
	public static void addNotification(Context context,Intent intent,int noticeId,int iconId,String ticker,
			                            String title,String msg)
	{
		long now = System.currentTimeMillis();
		Notification noti=new Notification(iconId,ticker,now);
		
		PendingIntent pintent=PendingIntent.getActivity(context, 0, intent, 0);
		RemoteViews contentView=new RemoteViews(context.getPackageName(),R.layout.noti);
		
		
		
		
    	Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
    	String strNow = sdfNow.format(date);
		
    	contentView.setTextViewText(R.id._date, strNow);
		contentView.setTextViewText(R.id._title, title);
		contentView.setTextViewText(R.id._text, msg);
		contentView.setImageViewResource(R.id._image, R.drawable.ic_launcher);
		
		noti.flags = Notification.FLAG_AUTO_CANCEL;
		noti.contentView=contentView;
		noti.contentIntent=pintent;
		//noti.sound=Uri.parse("android.resource://com.aeddang.puzzlemon/"+sndID);
       


		NotificationManager nm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(noticeId);
		
		nm.notify(noticeId, noti);
		
	}
	
    public static void removeNotification(Context context,int noticeId)
    {
		
    	
    	NotificationManager nm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancelAll();
    	//Config.NOTICE_ID=0;
	}
	
}

