package com.example.joananton.battery;

import android.os.BatteryManager;
import android.os.Bundle;

public class BatteryInfo {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data Members
    //

    private int mLevel;
    private int mScale;
    private boolean mPresent;
    private String mTechnology;
    private int mHealth;
    private int mTemperature;
    private int mVoltage;
    private int mPlugged;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    public BatteryInfo(Bundle bundle)
    {
        mLevel = bundle.getInt(BatteryManager.EXTRA_LEVEL);
        mScale = bundle.getInt(BatteryManager.EXTRA_SCALE);
        mPresent = bundle.getBoolean(BatteryManager.EXTRA_PRESENT);
        mTechnology = bundle.getString(BatteryManager.EXTRA_TECHNOLOGY);
        mHealth = bundle.getInt(BatteryManager.EXTRA_HEALTH);
        mTemperature = bundle.getInt(BatteryManager.EXTRA_TEMPERATURE);
        mVoltage = bundle.getInt(BatteryManager.EXTRA_VOLTAGE);
        mPlugged = bundle.getInt(BatteryManager.EXTRA_PLUGGED);
    }

    public int getLevel()
    {
        return mLevel;
    }

    public int getScale()
    {
        return mScale;
    }

    public int getHealth()
    {
        return mHealth;
    }

    public boolean getPresent()
    {
        return mPresent;
    }

    public String getTechnology()
    {
        return mTechnology;
    }

    public int getTemperature()
    {
        return mTemperature;
    }

    public int getVoltage()
    {
        return mVoltage;
    }

    public int getPlugged()
    {
        return mPlugged;
    }
}
