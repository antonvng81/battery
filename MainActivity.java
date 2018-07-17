package com.example.joananton.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    //

    public static final String PACKAGE = "com.example.joananton.battery";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data members
    //

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainFragmentPagerAdapter mMainFragmentPagerAdapter;

    private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (mViewPagerPosition) {
                case 0:
                    mMainFragmentPagerAdapter.getPage1Fragment().onDataReceived(intent);

                case 1:
                    mMainFragmentPagerAdapter.getPage2Fragment().onDataReceived(intent);
            }
        }
    };

    private boolean mIsDataReceiverActive;

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        // This method will be invoked when a new page becomes selected.
        @Override
        public void onPageSelected(int position) {
            mViewPagerPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private int mViewPagerPosition;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    private void init() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(BatteryService.ACTION_GET_BATTERY_INFO_ARRAY);
        filter.addAction(BatteryService.ACTION_GET_BATTERY_INFO);
        filter.addAction(BatteryService.ACTION_EXIT_SERVICE);
        registerReceiver(mDataReceiver, filter);
        mIsDataReceiverActive = true;
    }

    private void close() {
        if(mIsDataReceiverActive) {
            unregisterReceiver(mDataReceiver);
            mIsDataReceiverActive = false;
        }

        mMainFragmentPagerAdapter.getPage2Fragment().onActivityClose();
        mMainFragmentPagerAdapter.getPage1Fragment().onActivityClose();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFragmentPagerAdapter = new MainFragmentPagerAdapter(
                getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.Activity_ViewPager);
        mViewPager.setAdapter(mMainFragmentPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.Activity_TabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onDestroy() {
        close();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        close();
        super.onPause();
    }

    public void Page1_CheckBox1_OnClick(View view) {
        mMainFragmentPagerAdapter.getPage1Fragment().Page1_CheckBox1_OnClick(view);
    }
}



