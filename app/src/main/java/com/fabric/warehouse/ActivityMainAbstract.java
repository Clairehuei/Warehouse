package com.fabric.warehouse;

import android.os.Bundle;

import com.fabric.warehouse.di.ApplicationComponent;
import com.fabric.warehouse.di.DaggerApplicationComponent;

import butterknife.ButterKnife;

/**
 * Created by 6193 on 2016/4/11.
 */
public abstract class ActivityMainAbstract extends FabricBaseActivity {

    protected abstract void setMainMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.inject(this);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(getAppModule())
                .build();
        applicationComponent.inject(this);

        toolbarTitle.setText(R.string.app_name);
        setMainMenu();
    }
}
