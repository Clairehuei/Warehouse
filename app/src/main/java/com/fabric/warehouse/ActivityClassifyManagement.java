package com.fabric.warehouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 6193 on 2016/4/12.
 */
public class ActivityClassifyManagement extends FabricBaseActivity {

    @InjectView(R.id.pager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_management);
        ButterKnife.inject(this);

        setTitle(getString(R.string.commodity_management));
        showBackButton();

    }



}
