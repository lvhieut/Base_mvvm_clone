package com.hieunv.mvvmarch.sample.ui.main.fragment.homefeed

import android.util.Log
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hieunv.mvvmarch.base.api.common.ErrorState
import com.hieunv.mvvmarch.base.api.common.rxjava.RetrofitObserver
import com.hieunv.mvvmarch.base.entity.PaginatedEntities
import com.hieunv.mvvmarch.base.entity.PaginatedEntities.Companion.REQUEST_ID_FIRST_TIME
import com.hieunv.mvvmarch.base.entity.Post
import com.hieunv.mvvmarch.base.repository.post.PostRepository
import com.hieunv.mvvmarch.base.ui.SingleLiveData
import com.hieunv.mvvmarch.base.util.extension.plusAssign
import com.hieunv.mvvmarch.sample.ui.main.adapter.helper.OnLoadMoreViewModel
import io.reactivex.disposables.CompositeDisposable

class HomeFeedViewModel @ViewModelInject constructor(
        private val postRepository: PostRepository
) : ViewModel(), OnLoadMoreViewModel {

    companion object {
        private const val TAG = "HomeFeedViewModel"
    }

    private val compositeDisposable = CompositeDisposable()

    private val paginatedPosts = SingleLiveData<PaginatedEntities<Post>>()
    private val errorWithRequestId = SingleLiveData<Long>()

    val isLoadingFirstTime = ObservableField(false)
    val isLoadFirstTimeFail = ObservableField(false)

    private val isLoadMoreFail = ObservableField(false)
    override fun isLoadMoreFail(): ObservableField<Boolean> = isLoadMoreFail

    fun getErrorWithRequestId(): SingleLiveData<Long> {
        return errorWithRequestId
    }

    fun getPaginatedPosts(): SingleLiveData<PaginatedEntities<Post>> {
        return paginatedPosts
    }

    fun fetchPosts(
            afterId: Long,
            isRefresh: Boolean = false
    ) {
        if (afterId == REQUEST_ID_FIRST_TIME) {
            isLoadingFirstTime.set(!isRefresh)
            isLoadFirstTimeFail.set(false)
        } else {
            isLoadMoreFail.set(false)
        }
        compositeDisposable += postRepository
                .getPosts(afterId)
                .subscribeWith(PostsObserver(afterId))
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private inner class PostsObserver(val requestId: Long) : RetrofitObserver<PaginatedEntities<Post>>() {
        override fun onSuccess(t: PaginatedEntities<Post>) {
            Log.d(TAG, "PostsObserver: requestId = $requestId; onSuccess - $t")
            if (requestId == REQUEST_ID_FIRST_TIME) {
                isLoadingFirstTime.set(false)
            }
            t.requestId = requestId
            paginatedPosts.postValue(t)
        }

        override fun onError(error: ErrorState) {
            Log.d(TAG, "PostsObserver: requestId = $requestId; onError - $error")
            if (requestId == REQUEST_ID_FIRST_TIME) {
                isLoadingFirstTime.set(false)
                isLoadFirstTimeFail.set(true)
            } else {
                isLoadMoreFail.set(true)
            }

            errorWithRequestId.postValue(requestId)
        }
    }

}