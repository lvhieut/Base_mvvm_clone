package com.hieunv.mvvmarch.sample.ui.main.fragment.homefeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieunv.mvvmarch.base.entity.PaginatedEntities.Companion.REQUEST_ID_FIRST_TIME
import com.hieunv.mvvmarch.base.entity.Post
import com.hieunv.mvvmarch.base.platform.AppManager
import com.hieunv.mvvmarch.base.ui.common.LoadMoreSimpleAdapter
import com.hieunv.mvvmarch.sample.R
import com.hieunv.mvvmarch.sample.databinding.FragmentHomeFeedBinding
import com.hieunv.mvvmarch.sample.ui.main.MainActivity
import com.hieunv.mvvmarch.sample.ui.main.MainViewModel
import com.hieunv.mvvmarch.sample.ui.main.fragment.homefeed.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFeedFragment : Fragment(),
        PostAdapter.OnItemClickListener, LoadMoreSimpleAdapter.OnLoadMoreListener {

    companion object {
        private const val TAG = "HomeFeedFragment"
    }

    @Inject
    lateinit var appManager: AppManager

    private lateinit var homeFeedBinding: FragmentHomeFeedBinding

    private val mainViewModel: MainViewModel by activityViewModels()
    private val homeFeedViewModel: HomeFeedViewModel by viewModels()

    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_feed, container, false)
        return homeFeedBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).updateToolbar()

        initiateView()
        handleObservable()

        homeFeedViewModel.fetchPosts(REQUEST_ID_FIRST_TIME)
    }

    private fun initiateView() {
        homeFeedBinding.viewModel = homeFeedViewModel
        homeFeedBinding.postRv.setHasFixedSize(true)
        homeFeedBinding.postRv.layoutManager = LinearLayoutManager(context)
        postAdapter = PostAdapter(homeFeedBinding.postRv, homeFeedViewModel, this, this)
        homeFeedBinding.postRv.adapter = postAdapter

        homeFeedBinding.postRefresh.setOnRefreshListener {
            homeFeedViewModel.fetchPosts(REQUEST_ID_FIRST_TIME, true)
        }
    }

    private fun handleObservable() {
        homeFeedViewModel.getPaginatedPosts().observe(
                viewLifecycleOwner,
                Observer {
                    homeFeedBinding.postRefresh.isRefreshing = false
                    it?.let { paginatedPosts ->
                        if (paginatedPosts.requestId == REQUEST_ID_FIRST_TIME) {
                            postAdapter.clearAllItems()
                        } else {
                            postAdapter.loadMoreSuccess()
                        }

                        val posts = paginatedPosts.entities
                        if (posts.isNotEmpty()) {
                            postAdapter.updatePosts(posts)
                        }
                    }
                }
        )

        homeFeedViewModel.getErrorWithRequestId().observe(
                viewLifecycleOwner,
                Observer {
                    homeFeedBinding.postRefresh.isRefreshing = false
                    when (it) {
                        REQUEST_ID_FIRST_TIME -> {
                            postAdapter.clearAllItems()
                        }
                        else -> {
                            postAdapter.loadMoreFail()
                        }
                    }
                }
        )
    }

    override fun onItemClick(item: Post) {
        // preventing double click
        if (appManager.isPreventingDoubleClick) {
            return
        }

    }

    override fun onLoadMore() {
        val requestAfterId = homeFeedViewModel.getPaginatedPosts().value?.afterId ?: REQUEST_ID_FIRST_TIME
        if (requestAfterId == REQUEST_ID_FIRST_TIME) {
            // No more posts, no start load more
            return
        }
        postAdapter.startLoadMoreItem()
        homeFeedViewModel.fetchPosts(requestAfterId)
    }
}
