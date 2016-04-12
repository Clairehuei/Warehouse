package com.fabric.warehouse;

import android.content.Intent;

import butterknife.OnClick;

/**
 * Created by 6193 on 2016/4/11.
 */
public class ActivityMainAdmin extends ActivityMainAbstract {

    @Override
    protected void setMainMenu() {

    }

    @OnClick(R.id.classify)
    void commodityManagement() {
        Intent intent = new Intent(context, ActivityClassifyManagement.class);
        startActivity(intent);
    }
}
