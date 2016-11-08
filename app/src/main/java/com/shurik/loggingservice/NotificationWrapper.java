package com.shurik.loggingservice;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class NotificationWrapper {

    private NotificationManager mNotifyManager;
    private int id = 0;
    private String mTitleNotification = null;

    public NotificationWrapper(Context context, int id, String title) {
        this.id = id;
        this.mTitleNotification = title;
        mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public NotificationCompat.Builder createBuilder(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(mTitleNotification);
        builder.setOngoing(true);
        return builder;
    }

    public void setProgress(Context context, int incr) {
        NotificationCompat.Builder builder = createBuilder(context).setProgress(100, incr, false);
        mNotifyManager.notify(id, builder.build());
    }

    public void setContentText(Context context, String string) {
        NotificationCompat.Builder builder = createBuilder(context).setContentText(string);
        mNotifyManager.notify(id, builder.build());
    }

    public void cancel(int id) {
        mNotifyManager.cancel(id);
    }

    public void cancelAll() {
        mNotifyManager.cancelAll();
    }
}
