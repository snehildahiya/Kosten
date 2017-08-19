package com.ap.snehil.kosten;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ap.snehil.kosten.Adapters.MyFragmentPagerAdapter;
import com.ap.snehil.kosten.Fragments.CashFragment;
import com.ap.snehil.kosten.Fragments.CashlessFragment;
import com.ap.snehil.kosten.Fragments.LoansFragment;
import com.ap.snehil.kosten.Permissions.PermissionManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.support.v7.app.ActionBar.TabListener {
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
    List<Fragment> fragList = new ArrayList<Fragment>();
    Fragment f = null;   // Thx to Andre Krause for his support!
    CashFragment cashf = null;
    CashlessFragment cashlessf=null;
    LoansFragment loansf = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar bar =  getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int i=1; i <= 3; i++) {
            android.support.v7.app.ActionBar.Tab tab = bar.newTab();
            switch(i){
                case 1:
                    tab.setText("Cashless ");
                    break;
                case 2:
                    tab.setText("Cash ");
                    break;
                case 3:
                    tab.setText("Balance");
                    break;
            }
            tab.setTabListener((android.support.v7.app.ActionBar.TabListener) this);
            bar.addTab(tab);

        }

//        viewPager =(ViewPager) findViewById(R.id.viewPager);
//        myFragmentPagerAdapter =new MyFragmentPagerAdapter(getSupportFragmentManager(),3);
//        viewPager.setAdapter(myFragmentPagerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
        //Fragment f = null;
//        TabFragment tf = null;

        if (fragList.size() > tab.getPosition()) {

            ft.replace(android.R.id.content, fragList.get(tab.getPosition()));

        } else {
            switch (tab.getPosition()) {
                case 0:
                    cashlessf = new CashlessFragment();
                    fragList.add(cashlessf);
                    ft.replace(android.R.id.content, cashlessf);
                    break;
                case 1:
                    cashf = new CashFragment();
                    fragList.add(cashf);
                    ft.replace(android.R.id.content, cashf);
                break;
                case 2:
                    loansf = new LoansFragment();
                    fragList.add(loansf);
                    ft.replace(android.R.id.content, loansf);
                    break;

            }


        }
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragList.size() > tab.getPosition()) {
            ft.remove(fragList.get(tab.getPosition()));
        }

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }


}
