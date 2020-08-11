package com.example.better_waves.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new Fragment_Albums();
            case 2:
                return new Fragment_Artists();
            case 3:
                return new Fragment_Recommendations();
            default:
                return new Fragment_AllSongs();
        }
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