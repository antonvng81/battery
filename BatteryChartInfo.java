package com.example.joananton.battery;

public class BatteryChartInfo implements ChartView.ChartInfo{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data Members
    //

    private BatteryInfoArray mBatteryInfoArray;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    BatteryChartInfo( BatteryInfoArray batteryInfoArray) {
        mBatteryInfoArray = batteryInfoArray;
    }

    @Override
    public int getSize() {
        return BatteryInfoArray.ARRAY_SIZE;
    }

    @Override
    public float getY(int n) {
        return mBatteryInfoArray.getLevel(n);
    }


}
