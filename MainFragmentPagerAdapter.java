package com.example.joananton.battery;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    //

    public static final int NUM_ITEMS = 2;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data members
    //

    private String mTabTitles[] = new String[]{"Main", "Chart"};
    private Page1Fragment mPage1Fragment;
    private Page2Fragment mPage2Fragment;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    public Page1Fragment getPage1Fragment() {

        if (mPage1Fragment == null)
            mPage1Fragment = new Page1Fragment();

        return mPage1Fragment;
    }

    public Page2Fragment getPage2Fragment() {

        if (mPage2Fragment == null)
            mPage2Fragment = new Page2Fragment();

        return mPage2Fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getPage1Fragment();
            case 1:
                return getPage2Fragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

}
