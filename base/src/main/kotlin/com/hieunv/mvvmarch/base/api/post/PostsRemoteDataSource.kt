package com.hieunv.mvvmarch.base.api.post

import com.hieunv.mvvmarch.base.api.common.rxjava.Result
import com.hieunv.mvvmarch.base.api.common.rxjava.retrofitPaginatedResponseToResult
import com.hieunv.mvvmarch.base.entity.PaginatedEntities
import com.hieunv.mvvmarch.base.entity.Post
import com.hieunv.mvvmarch.base.util.rx.RxSchedulers
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRemoteDataSource @Inject constructor(
        private val postApi: PostApi,
        private val rxSchedulers: RxSchedulers
) {

    companion object {
        private const val REQUEST_POSTS_LIMIT = 10
    }

    fun getPosts(afterId: Long): Observable<Result<PaginatedEntities<Post>>> {
        return postApi.getPosts(REQUEST_POSTS_LIMIT, afterId)
                .retrofitPaginatedResponseToResult()
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidThread())
    }

}
