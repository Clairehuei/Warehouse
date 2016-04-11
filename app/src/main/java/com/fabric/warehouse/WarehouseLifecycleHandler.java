package com.fabric.warehouse;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.fabric.warehouse.Helper.PreferenceHelper;
import com.fabric.warehouse.di.ApiComponent;
import com.fabric.warehouse.di.ApiModule;
import com.fabric.warehouse.di.DaggerApiComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Reference: http://stackoverflow.com/a/13809991/928123
 *
 * @author Allen
 */
public class WarehouseLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private int resumed;
    private int paused;
    private int started;
    private int stopped;

    @Inject
    PreferenceHelper preferenceHelper;

    public WarehouseLifecycleHandler(Application application) {
        ApiComponent apiComponent = DaggerApiComponent.builder()
                .applicationModule(((WarehouseApp) application).appModule())
                .apiModule(new ApiModule())
                .build();
        apiComponent.inject(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        if (isAppInForeground()) {
            if (preferenceHelper.hasStoppedTimestamp()) {
                if (overOperateDurationLimitation()) {
                    ((WarehouseApp) activity.getApplication()).restartApp(activity);
                }
                preferenceHelper.clearStoppedTimestamp();
            }
        }
    }

    private boolean overOperateDurationLimitation() {
        long stoppedTimestamp = preferenceHelper.getStoppedTimestamp();
        long now = System.currentTimeMillis();
        boolean flag = Math.abs(now - stoppedTimestamp) >= BuildConfig.DURATION_LIMIT;
        Timber.i("Over Operate Duration Limit: %s", flag);
        return flag;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        if (isAppInBackground()) {
            preferenceHelper.setStoppedTimestamp();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private boolean isAppInBackground() {
        Timber.i("In Background? %s", started <= stopped);
        return started <= stopped;
    }

    private boolean isAppInForeground() {
        Timber.i("In Foreground? %s", resumed > paused);
        return resumed > paused;
    }
}
