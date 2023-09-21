package com.hieunv.mvvmarch.base.repository.post

import com.hieunv.mvvmarch.base.api.common.rxjava.Result
import com.hieunv.mvvmarch.base.entity.PaginatedEntities
import com.hieunv.mvvmarch.base.entity.Post
import io.reactivex.Observable

interface PostRepository {

    /**
     *  Fetch Posts from Network when paging.
     *
     *  @param afterId (required)
     **/
    fun getPosts(afterId: Long): Observable<Result<PaginatedEntities<Post>>>

}
