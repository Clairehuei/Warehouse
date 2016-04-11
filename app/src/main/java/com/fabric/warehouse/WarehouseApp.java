package com.fabric.warehouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.fabric.warehouse.di.ApplicationComponent;
import com.fabric.warehouse.di.ApplicationModule;
import com.fabric.warehouse.di.DaggerApplicationComponent;
import com.squareup.otto.Bus;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by 6193 on 2016/4/11.
 */
public class WarehouseApp extends android.support.multidex.MultiDexApplication {

    private ApplicationComponent applicationComponent;
    private ApplicationModule applicationModule;

    @Inject
    Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        initializeTimber();
        registerActivityLifecycleCallbacks(new WarehouseLifecycleHandler(this));
        JodaTimeAndroid.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeInjector() {
        applicationModule = new ApplicationModule(this);
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build();
        applicationComponent.inject(this);
    }

    public Bus appBus() {
        return this.bus;
    }

    public ApplicationComponent appComponent() {
        return applicationComponent;
    }

    public ApplicationModule appModule() {
        return applicationModule;
    }

    public void restartApp(Activity activity) {
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        activity.finish();
    }

}
