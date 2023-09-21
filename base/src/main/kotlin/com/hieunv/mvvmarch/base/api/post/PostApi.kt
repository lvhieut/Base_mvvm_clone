package com.hieunv.mvvmarch.base.api.post

import com.hieunv.mvvmarch.base.api.common.PaginatedResponse
import com.hieunv.mvvmarch.base.entity.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("/api/v1/get_posts.seam")
    fun getPosts(
            @Query("limit") limit: Int,
            @Query("after_id") afterId: Long
    ): Observable<PaginatedResponse<Post>>

}
