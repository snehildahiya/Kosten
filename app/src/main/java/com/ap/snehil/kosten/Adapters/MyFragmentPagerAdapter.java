package com.ap.snehil.kosten.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ap.snehil.kosten.Fragments.CashFragment;
import com.ap.snehil.kosten.Fragments.CashlessFragment;
import com.ap.snehil.kosten.Fragments.LoansFragment;

/**
 * Created by HP on 12-08-2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private int fragcount;
    public static final String TAG="yo";


    public MyFragmentPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.fragcount=count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){

            case 0:
                fragment = new CashlessFragment();
                break;
            case 1:
                fragment= new CashFragment();
                break;
            case 2:
                fragment= new LoansFragment();
                break;
        }

        return fragment;

    }


    @Override
    public int getCount() {
        return fragcount;
    }
}
