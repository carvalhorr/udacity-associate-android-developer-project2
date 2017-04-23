package com.example.popularmovies.injection;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by carvalhorr on 3/26/17.
 */

public class PopularMoviesApplication extends Application {

    private PopularMoviesApplicationComponent component;

    public static PopularMoviesApplication get(Context context) {
        return (PopularMoviesApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerPopularMoviesApplicationComponent
                .builder()
                .popularMoviesInjectionModule(new PopularMoviesInjectionModule(PopularMoviesApplication.this))
                .build();
    }

    public PopularMoviesApplicationComponent getComponent() {
        return component;
    }

    public void setComponent(PopularMoviesApplicationComponent component) {
        this.component = component;
    }
}
