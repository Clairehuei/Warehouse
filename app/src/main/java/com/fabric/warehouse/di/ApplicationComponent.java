package com.fabric.warehouse.di;

import android.content.Context;

import com.fabric.warehouse.ActivityMainAbstract;
import com.fabric.warehouse.ActivityMainAdmin;
import com.fabric.warehouse.WarehouseApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * An interface as a bridge for <a href="">Dagger 2</a> to connect the module (provider) and consumer.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    void inject(WarehouseApp app);

    void inject(ActivityMainAdmin activity);

    void inject(ActivityMainAbstract activity);

}
