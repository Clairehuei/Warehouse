package com.fabric.warehouse.di;


import com.fabric.warehouse.MainActivity;
import com.fabric.warehouse.WarehouseLifecycleHandler;

import javax.inject.Singleton;

import dagger.Component;

/**
 * An interface as a bridge for <a href="">Dagger 2</a> to connect the module (provider)
 * and consumer.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class
})
public interface ApiComponent {

    void inject(MainActivity activity);

    void inject(WarehouseLifecycleHandler handler);

}
