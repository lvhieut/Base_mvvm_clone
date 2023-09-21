package com.hieunv.mvvmarch.base.api.common.rxjava

data class Result<out T>(
        val data: T?,
        val error: Throwable?
)
