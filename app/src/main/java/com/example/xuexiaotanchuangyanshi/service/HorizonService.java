package com.example.xuexiaotanchuangyanshi.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;


/**
 * Created by Administrator on 2017/11/21.
 */

public class HorizonService extends Service {
    private  AlarmManager manager;
    private  PendingIntent pi;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent intent1=new Intent("duanxianchonglian");
        sendBroadcast(intent1);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

         manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int five = 20000; // 这是5s
        long triggerAtTime = SystemClock.elapsedRealtime() + five;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        manager.cancel(pi);
        super.onDestroy();
    }
}