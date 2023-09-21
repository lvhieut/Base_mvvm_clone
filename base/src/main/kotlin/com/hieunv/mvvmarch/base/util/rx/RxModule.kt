package com.hieunv.mvvmarch.base.util.rx

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RxModule {

    // Makes Dagger provide AppRxSchedulers when a RxSchedulers type is requested
    @Binds
    abstract fun provideRxSchedulers(appRxSchedulers: AppRxSchedulers): RxSchedulers

}
