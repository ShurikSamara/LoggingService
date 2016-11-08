package com.shurik.loggingservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainService extends Service {

    Writer writer = new Writer();
    private static final int id = 0;
    private static final String LOG_TAG = "MyLOG";
    static boolean wasThread = false;

    public MainService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        writer.writeToFile("\n onCreate \n");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (wasThread == false) {
            wasThread = true;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    int counter = 0;
                    while (true) {
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                            String format = simpleDateFormat.format(new Date());

                            NotificationWrapper notificationWrapper =
                                    new NotificationWrapper(getApplicationContext(), id, "Title");
                            notificationWrapper.setContentText(getApplicationContext(), "Finish");
                            startForeground(id,
                                    notificationWrapper.createBuilder(getApplicationContext()).build());


                            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                            PowerManager.WakeLock wakeLock =
                                    powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
                            wakeLock.acquire();

                            //wakeLock.release();

                            Thread.sleep(1000);
                            writer.writeToFile(".");
                            counter++;
                            if (counter % 60 == 0) {
                                writer.writeToFile("\t\t" +  format + " I'm working " + "\n");
                                counter = 0;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
        writer.writeToFile("\n onDestroy \n");


        stopForeground(true);
    }
}
