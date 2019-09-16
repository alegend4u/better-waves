package com.example.better_waves.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.better_waves.Fragment_Albums;
import com.example.better_waves.Fragment_AllSongs;
import com.example.better_waves.Fragment_Artists;
import com.example.better_waves.R;
import com.example.better_waves.Fragment_Recommendations;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_songs,
            R.string.tab_album,
            R.string.tab_artists,
            R.string.tab_recommend
    };
    private static final int tabsNo = TAB_TITLES.length;

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Fragment_AllSongs songs = new Fragment_AllSongs();
                return songs;
            case 1:
                Fragment_Albums albums = new Fragment_Albums();
                return albums;
            case 2:
                Fragment_Artists artists = new Fragment_Artists();
                return artists;
            case 3:
                Fragment_Recommendations recommends = new Fragment_Recommendations();
                return recommends;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return tabsNo;
    }
}