package com.hieunv.mvvmarch.base.preferences

import com.hieunv.mvvmarch.base.preferences.auth.AuthSharedPrefs
import com.hieunv.mvvmarch.base.preferences.auth.AuthSharedPrefsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class SharedPrefsModule {

    // Makes Dagger provide AuthSharedPrefsDataSource when a AuthSharedPrefs type is requested
    @Binds
    abstract fun provideAuthSharedPreferences(authSharedPrefsDataSource: AuthSharedPrefsDataSource): AuthSharedPrefs

}
