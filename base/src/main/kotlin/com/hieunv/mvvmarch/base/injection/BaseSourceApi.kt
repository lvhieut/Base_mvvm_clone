package com.hieunv.mvvmarch.base.injection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseSourceApi(val value: String)
