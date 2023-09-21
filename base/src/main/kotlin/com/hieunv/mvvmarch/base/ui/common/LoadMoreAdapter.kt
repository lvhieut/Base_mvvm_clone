package com.hieunv.mvvmarch.base.ui.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

abstract class LoadMoreAdapter<T, LB : ViewDataBinding, LVH : LoadMoreViewHolder<LB>>(
        private val recyclerView: RecyclerView,
        private val loadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnLoadMoreListener {
        fun onLoadMoreBefore()

        fun onLoadMoreAfter()
    }

    companion object {

        /**
         * This type represents a load more position
         */
        private const val VIEW_TYPE_LOAD_MORE = 0

        private const val VISIBLE_THRESHOLD = 2
    }

    protected var items: MutableList<T?> = mutableListOf()

    private var firstVisibleItem = 0
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    private var isLoadingMoreBefore = false
    private var isLoadMoreBeforeFail = false

    private var isLoadingMoreAfter = false
    private var isLoadMoreAfterFail = false

    init {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    totalItemCount = layoutManager.itemCount

                    if (!isLoadingMoreBefore && (VISIBLE_THRESHOLD - firstVisibleItem) >= 0) {
                        loadMoreListener.onLoadMoreBefore()
                    }

                    if (!isLoadingMoreAfter && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                        loadMoreListener.onLoadMoreAfter()
                    }
                }
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item == null) {
            VIEW_TYPE_LOAD_MORE
        } else {
            getDataItemViewType(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOAD_MORE) {
            onCreateLoadMoreBinding(parent, viewType)
        } else {
            onCreateDataViewBinding(parent, viewType)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (item == null) {
            onBindLoadMoreViewHolder(holder as LVH, position)
        } else {
            onBindDataViewHolder(holder, item, position)
        }
    }

    override fun getItemCount(): Int = items.size

    protected abstract fun getDataItemViewType(item: T): Int

    protected abstract fun onCreateDataViewBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    protected abstract fun onCreateLoadMoreBinding(parent: ViewGroup, viewType: Int): LVH

    protected abstract fun onBindDataViewHolder(holder: RecyclerView.ViewHolder, item: T, position: Int)

    protected abstract fun onBindLoadMoreViewHolder(holder: LVH, position: Int)

    fun startLoadMoreBeforeItem() {
        // start load more, set loading = true
        isLoadingMoreBefore = true

        if (isLoadMoreBeforeFail) {
            // if load more is failed, no add item load more
            return
        }
        items.add(0, null)
        recyclerView.post {
            Runnable {
                notifyItemInserted(0)
            }
        }
    }

    fun loadMoreBeforeSuccess() {
        // finish load more before with result success, set loading = false and remove item load more
        items.removeAt(0)
        notifyItemRemoved(0)
        isLoadingMoreBefore = false
        isLoadMoreBeforeFail = false
    }

    fun loadMoreBeforeFail() {
        // finish load more before with result fail, only set loading = false and load fail = true
        isLoadingMoreBefore = false
        isLoadMoreBeforeFail = true
    }

    fun startLoadMoreAfterItem() {
        // start load more, set loading = true
        isLoadingMoreAfter = true

        if (isLoadMoreAfterFail) {
            // if load more is failed, no add item load more
            return
        }
        items.add(null)
        recyclerView.post {
            Runnable {
                notifyItemInserted(items.size - 1)
            }
        }
    }

    fun loadMoreAfterSuccess() {
        // finish load more before with result success, set loading = false and remove item load more
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
        isLoadingMoreAfter = false
        isLoadMoreAfterFail = false
    }

    fun loadMoreAfterFail() {
        // finish load more before with result fail, only set loading = false and load fail = true
        isLoadingMoreAfter = false
        isLoadMoreAfterFail = true
    }

}
