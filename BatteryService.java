package com.example.joananton.battery;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class BatteryService extends Service {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    //

    public static final String ACTION_EXIT_SERVICE = MainActivity.PACKAGE + ".ACTION_EXIT_SERVICE";
    public static final String ACTION_GET_BATTERY_INFO_ARRAY = MainActivity.PACKAGE + ".ACTION_GET_BATTERY_INFO_ARRAY";
    public static final String ACTION_GET_BATTERY_INFO = MainActivity.PACKAGE + ".ACTION_GET_BATTERY_INFO";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data members
    //

    private static BatteryService mInstance = null;
    private Timer mTimer;
    private Timer mTimerLong;
    private boolean mBatteryArrayInfoState;

    private BatteryInfoArray mBatteryInfoArray;
    private BatteryInfo mBatteryInfo;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    public static boolean isInstanceCreated() {
        return mInstance != null;
    }
    public static BatteryInfoArray getBatteryInfoArray(){
        return mInstance.mBatteryInfoArray;
    }
    public static BatteryInfo getBatteryInfo(){
        return mInstance.mBatteryInfo;
    }

    // Short time receiver
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {

                Bundle bundle = intent.getExtras();

                if (null == bundle)
                    return;

                mBatteryInfo = new BatteryInfo(bundle);
                unregisterReceiver(mBatteryReceiver);

                Intent service_intent = new Intent(BatteryService.ACTION_GET_BATTERY_INFO);
                sendBroadcast(service_intent);

                if(mBatteryArrayInfoState)
                {
                    mBatteryInfoArray.addInfo(bundle);

                    Intent service_intent2 = new Intent(BatteryService.ACTION_GET_BATTERY_INFO_ARRAY);
                    sendBroadcast(service_intent2);
                    mBatteryArrayInfoState = false;
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        mInstance = this;

        mBatteryInfoArray = new BatteryInfoArray();

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                        registerReceiver(mBatteryReceiver, filter);
                    }
                }
                , 0, 1000);

        mTimerLong = new Timer();
        mTimerLong.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        mBatteryArrayInfoState = true;
                    }
                }
                , 0, BatteryInfoArray.SECONDS_PER_OFFSET*1000);

        Toast.makeText(this,"Battery service started",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerLong != null) {
            mTimerLong.cancel();
            mTimerLong = null;
        }

        /*try {
            unregisterReceiver(mBatteryReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }*/

        Intent intent = new Intent(ACTION_EXIT_SERVICE);
        sendBroadcast(intent);

        Toast.makeText(this,"Battery service stopped",Toast.LENGTH_SHORT).show();
        mInstance = null;
    }
}