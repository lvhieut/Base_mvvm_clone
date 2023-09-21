package com.hieunv.mvvmarch.base.repository.post

import com.hieunv.mvvmarch.base.api.common.rxjava.Result
import com.hieunv.mvvmarch.base.api.post.PostsRemoteDataSource
import com.hieunv.mvvmarch.base.db.post.PostsLocalDataSource
import com.hieunv.mvvmarch.base.entity.PaginatedEntities
import com.hieunv.mvvmarch.base.entity.Post
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
        private val postsRemoteDataSource: PostsRemoteDataSource,
        private val postsLocalDataSource: PostsLocalDataSource
) : PostRepository {

    override fun getPosts(afterId: Long): Observable<Result<PaginatedEntities<Post>>> {
        return postsRemoteDataSource.getPosts(afterId)
    }

}
