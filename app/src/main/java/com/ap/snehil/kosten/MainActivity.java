package com.ap.snehil.kosten;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ap.snehil.kosten.Adapters.MyFragmentPagerAdapter;
import com.ap.snehil.kosten.Permissions.PermissionManager;

public class MainActivity extends AppCompatActivity {
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager =(ViewPager) findViewById(R.id.viewPager);
        myFragmentPagerAdapter =new MyFragmentPagerAdapter(getSupportFragmentManager(),3);
        viewPager.setAdapter(myFragmentPagerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}
