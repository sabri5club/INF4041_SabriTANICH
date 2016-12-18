package com.sabri.inf4042_sabritanich.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sabri.inf4042_sabritanich.Fragments.ContactFragment;
import com.sabri.inf4042_sabritanich.Fragments.DownloadFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ContactFragment tab1 = new ContactFragment();
                return tab1;
            case 1 :
                DownloadFragment tab2 = new DownloadFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}