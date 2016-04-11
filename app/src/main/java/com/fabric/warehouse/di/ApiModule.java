package com.fabric.warehouse.di;


import com.fabric.warehouse.Api.GetAuthToken;
import com.fabric.warehouse.Api.User;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Module which provide the instance of APIs.
 */
@Module
public class ApiModule {

    @Provides @Singleton GetAuthToken authToken(RestAdapter restAdapter) {
        return restAdapter.create(GetAuthToken.class);
    }

    @Provides @Singleton User user(RestAdapter restAdapter) {
        return restAdapter.create(User.class);
    }

}
