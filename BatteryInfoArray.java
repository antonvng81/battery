package com.example.joananton.battery;

import android.os.BatteryManager;
import android.os.Bundle;

import java.util.Calendar;

public class BatteryInfoArray
{
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    //

    public static final int ARRAY_SIZE = 10;
    public static final int SECONDS_PER_OFFSET = 10;

    private static final String LEVEL_ARRAY = MainActivity.PACKAGE +"." + "level_array";
    private static final String SCALE_ARRAY = MainActivity.PACKAGE +"." + "scale_array";
    private static final String PRESENT_ARRAY = MainActivity.PACKAGE +"." + "present_array";
    private static final String TECHNOLOGY_ARRAY = MainActivity.PACKAGE +"." + "technology_array";
    private static final String HEALTH_ARRAY = MainActivity.PACKAGE +"." + "health_array";
    private static final String TEMPERATURE_ARRAY = MainActivity.PACKAGE +"." + "temperature_array";
    private static final String VOLTAGE_ARRAY = MainActivity.PACKAGE +"." + "voltage_array";
    private static final String PLUGGED_ARRAY = MainActivity.PACKAGE +"." + "plugged_array";

    private static final String OFFSET = MainActivity.PACKAGE +"." + "offset";
    private static final String IS_FULL = MainActivity.PACKAGE +"." + "is_full";

    private static final String TIME_START = MainActivity.PACKAGE +"." + "time_start";
    private static final String TIME_END = MainActivity.PACKAGE +"." + "time_end";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data Members
    //

    private int [] mLevel;
    private int [] mScale;
    private boolean [] mPresent;
    private String [] mTechnology;
    private int [] mHealth;
    private int [] mTemperature;
    private int [] mVoltage;
    private int [] mPlugged;

    private int [] mTimeStart;//s m h d
    private int [] mTimeEnd;//s m h d

    private int mOffset;
    private boolean mIsFull;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    public BatteryInfoArray()
    {
        mOffset = 0;
        mIsFull = false;

        mLevel = new int [ARRAY_SIZE];
        mScale = new int [ARRAY_SIZE];
        mPresent = new boolean [ARRAY_SIZE];
        mTechnology = new String [ARRAY_SIZE];
        mHealth = new int [ARRAY_SIZE];
        mTemperature = new int [ARRAY_SIZE];
        mVoltage = new int [ARRAY_SIZE];
        mPlugged = new int [ARRAY_SIZE];

        mTimeStart= new int [4];
        mTimeEnd= new int [4];

        Calendar calendar = Calendar.getInstance();

        mTimeStart[0] = calendar.get(Calendar.SECOND);
        mTimeStart[1] = calendar.get(Calendar.MINUTE);
        mTimeStart[2] = calendar.get(Calendar.HOUR);
        mTimeStart[3] = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public BatteryInfoArray(Bundle bundle)
    {

        mLevel = bundle.getIntArray(LEVEL_ARRAY);
        mScale = bundle.getIntArray(SCALE_ARRAY);
        mPresent = bundle.getBooleanArray(PRESENT_ARRAY);
        mTechnology = bundle.getStringArray(TECHNOLOGY_ARRAY);
        mHealth = bundle.getIntArray(HEALTH_ARRAY);
        mTemperature = bundle.getIntArray(TEMPERATURE_ARRAY);
        mVoltage = bundle.getIntArray(VOLTAGE_ARRAY);
        mPlugged = bundle.getIntArray(PLUGGED_ARRAY);

        mOffset = bundle.getInt(OFFSET);
        mIsFull = bundle.getBoolean(IS_FULL);

        mTimeStart = bundle.getIntArray(TIME_START);
        mTimeEnd = bundle.getIntArray(TIME_END);

    }

    public void addInfo(Bundle bundle)
    {
        mLevel[mOffset] = bundle.getInt(BatteryManager.EXTRA_LEVEL);
        mScale[mOffset] = bundle.getInt(BatteryManager.EXTRA_SCALE);
        mPresent[mOffset] = bundle.getBoolean(BatteryManager.EXTRA_PRESENT);
        mTechnology[mOffset] = bundle.getString(BatteryManager.EXTRA_TECHNOLOGY);
        mHealth[mOffset] = bundle.getInt(BatteryManager.EXTRA_HEALTH);
        mTemperature[mOffset] = bundle.getInt(BatteryManager.EXTRA_TEMPERATURE);
        mVoltage[mOffset] = bundle.getInt(BatteryManager.EXTRA_VOLTAGE);
        mPlugged[mOffset] = bundle.getInt(BatteryManager.EXTRA_PLUGGED);

        Calendar calendar = Calendar.getInstance();

        mTimeEnd[0] = calendar.get(Calendar.SECOND);
        mTimeEnd[1] = calendar.get(Calendar.MINUTE);
        mTimeEnd[2] = calendar.get(Calendar.HOUR);
        mTimeEnd[3] = calendar.get(Calendar.DAY_OF_WEEK);

        if( mIsFull )
        {
            int x = SECONDS_PER_OFFSET * ARRAY_SIZE;
            int y;

            y = (mTimeEnd[0] - x);
            mTimeStart[0] = ((y<0)?(y+60):y)%60;

            y = (mTimeEnd[1] - x/60);
            mTimeStart[1] = ((y<0)?(y+60):y)%60;

            y = (mTimeEnd[2] - x/3600);
            mTimeStart[2] = ((y<0)?(y+24):y)%24;

            y = (mTimeEnd[3] - x/(24*3600));
            mTimeStart[3] = ((y<0)?(y+7):y)%7;
        }

        mOffset = (mOffset + 1) % ARRAY_SIZE;

        if(mOffset == 0)
            mIsFull = true;
    }

    public Bundle getBundle()
    {
        Bundle bundle = new Bundle();

        bundle.putIntArray(LEVEL_ARRAY,mLevel);
        bundle.putIntArray(SCALE_ARRAY,mScale);
        bundle.putBooleanArray(PRESENT_ARRAY,mPresent);
        bundle.putStringArray(TECHNOLOGY_ARRAY,mTechnology);
        bundle.putIntArray(HEALTH_ARRAY,mHealth);
        bundle.putIntArray(TEMPERATURE_ARRAY,mTemperature);
        bundle.putIntArray(VOLTAGE_ARRAY, mVoltage);
        bundle.putIntArray(PLUGGED_ARRAY, mPlugged);

        bundle.putInt(OFFSET, mOffset);
        bundle.putBoolean(IS_FULL, mIsFull);

        bundle.putIntArray(TIME_START, mTimeStart);
        bundle.putIntArray(TIME_END, mTimeEnd);

        return bundle;
    }

    public int getOffset()
    {
        return mOffset;
    }

    public int getLevel(int n)
    {
        return mLevel[(mOffset+n)%ARRAY_SIZE];
    }

    public int getScale(int n)
    {
        return mScale[(mOffset+n)%ARRAY_SIZE];
    }

    public boolean getPresent(int n)
    {
        return mPresent[(mOffset+n)%ARRAY_SIZE];
    }

    public String getTechnology(int n)
    {
        return mTechnology[(mOffset+n)%ARRAY_SIZE];
    }

    public int getTemperature(int n)
    {
        return mTemperature[(mOffset+n)%ARRAY_SIZE];
    }

    public int getVoltage(int n)
    {
        return mVoltage[(mOffset+n)%ARRAY_SIZE];
    }

    public int getPlugged(int n)
    {
        return mPlugged[(mOffset+n)%ARRAY_SIZE];
    }

    public int getHealth(int n)
    {
        return mHealth[(mOffset+n)%ARRAY_SIZE];
    }

    public int [] getTimeStart()
    {
        return mTimeStart;
    }

    public int [] getTimeEnd()
    {
        return mTimeEnd;
    }

    public boolean getIsFull()
    {
        return mIsFull;
    }

}
