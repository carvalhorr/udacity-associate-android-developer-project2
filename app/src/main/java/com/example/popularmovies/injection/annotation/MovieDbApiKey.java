package com.example.popularmovies.injection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Named;

/**
 * Annotation to identify the movie DB api key to be injected. Since it is a String, this annotation
 * tells Dagger2 which string to use.
 * Created by carvalhorr on 4/18/17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Named(value = "movieDbApiKey")
public @interface MovieDbApiKey {
}
