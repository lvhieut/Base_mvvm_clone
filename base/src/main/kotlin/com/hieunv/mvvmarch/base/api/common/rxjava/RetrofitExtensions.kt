package com.hieunv.mvvmarch.base.api.common.rxjava

import com.hieunv.mvvmarch.base.api.common.PaginatedResponse
import com.hieunv.mvvmarch.base.api.common.ResultResponse
import com.hieunv.mvvmarch.base.entity.PaginatedEntities
import io.reactivex.Observable
import retrofit2.HttpException
import java.io.IOException

fun <T> Observable<T>.retrofitResponseToResult(): Observable<Result<T>> {
    return this.map { it.asResult() }
            .onErrorReturn {
                if (it is HttpException || it is IOException) {
                    return@onErrorReturn it.asErrorResult<T>()
                } else {
                    throw it
                }
            }
}

private fun <T> T.asResult(): Result<T> {
    return Result(data = this, error = null)
}

private fun <T> Throwable.asErrorResult(): Result<T> {
    return Result(data = null, error = this)
}

fun <T> Observable<ResultResponse<T>>.retrofitResultResponseToResult(): Observable<Result<T>> {
    return this.map { it.asResult() }
            .onErrorReturn {
                if (it is HttpException || it is IOException) {
                    return@onErrorReturn it.asErrorResult<T>()
                } else {
                    throw it
                }
            }
}

private fun <T> ResultResponse<T>.asResult(): Result<T> {
    return Result(data = this.data, error = null)
}

fun <T> Observable<PaginatedResponse<T>>.retrofitPaginatedResponseToResult(): Observable<Result<PaginatedEntities<T>>> {
    return this.map { it.asResult() }
            .onErrorReturn {
                if (it is HttpException || it is IOException) {
                    return@onErrorReturn it.asErrorResult<PaginatedEntities<T>>()
                } else {
                    throw it
                }
            }
}

private fun <T> PaginatedResponse<T>.asResult(): Result<PaginatedEntities<T>> {
    return if (this.data != null) {
        val paginatedEntities = PaginatedEntities(this.data, this.pagination?.afterId)
        Result(data = paginatedEntities, error = null)
    } else {
        Result(data = null, error = null)
    }
}
