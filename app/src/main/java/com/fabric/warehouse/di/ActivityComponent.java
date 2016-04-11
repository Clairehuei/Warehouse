package com.fabric.warehouse.di;

import android.app.Activity;

import dagger.Component;

/**
 * An interface as a bridge for <a href="">Dagger 2</a> to connect the module (provider) and consumer.
 */
@ActivityScope
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
