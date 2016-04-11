package com.fabric.warehouse.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;

import com.fabric.warehouse.BuildConfig;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * The module which provides the instances of objects.
 *
 * @author Allen
 */
@Module
public class ApplicationModule {

    private final Application application;
    private final SharedPreferences sharedPreferences;
    private final LayoutInflater layoutInflater;
    private final RestAdapter restAdapter;
    private final Bus bus;
    private final TelephonyManager telephonyManager;

    public ApplicationModule(Application application) {
        this.application = application;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        layoutInflater = LayoutInflater.from(application);
        restAdapter = new RestAdapter.Builder().setEndpoint(BuildConfig.HOST).build();
        bus = new Bus(ThreadEnforcer.MAIN);
        telephonyManager = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return this.restAdapter;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference() {
        return this.sharedPreferences;
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return this.layoutInflater;
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return this.bus;
    }

    @Provides
    @Singleton
    TelephonyManager provideTelephonyManager() {
        return this.telephonyManager;
    }
}
