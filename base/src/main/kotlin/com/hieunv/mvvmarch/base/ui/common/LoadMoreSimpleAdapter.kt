package com.hieunv.mvvmarch.base.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreSimpleAdapter<T, IB : ViewDataBinding, LB : ViewDataBinding>(
        private val recyclerView: RecyclerView,
        private val loadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 2

        private const val VIEW_LOAD_MORE = 0
        private const val VIEW_ITEM = 1
    }

    protected val items: MutableList<T?> = mutableListOf()

    private var isLoadingMore = false
    private var isLoadMoreFail = false

    init {
        val layoutManager = recyclerView.layoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when (layoutManager) {
                    is LinearLayoutManager -> {
                        val totalItemCount = layoutManager.itemCount
                        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                        if (!isLoadingMore && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                            loadMoreListener.onLoadMore()
                        }
                    }
                    is GridLayoutManager -> {
                        if (dy > 0) {
                            val visibleItemCount = layoutManager.childCount
                            val totalItemCount = layoutManager.itemCount
                            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                            if (!isLoadingMore && (visibleItemCount + firstVisibleItem) >= totalItemCount) {
                                loadMoreListener.onLoadMore()
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = when (viewType) {
            VIEW_ITEM -> {
                onCreateItemBinding(parent)
            }
            VIEW_LOAD_MORE -> {
                onCreateLoadMoreBinding(parent)
            }
            else -> throw IllegalArgumentException("viewType is not valid")
        }
        return DataBoundViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DataBoundViewHolder<*>
        val binding = holder.binding
        val item = items[position]
        if (item != null) {
            onBindItem(binding as IB, item)
        } else {
            onBindLoadMore(binding as LB)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) VIEW_ITEM else VIEW_LOAD_MORE
    }

    fun clearAllItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun updateAllItems(list: List<T>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun startLoadMoreItem() {
        // start load more, set loading = true
        isLoadingMore = true

        if (isLoadMoreFail) {
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

    fun loadMoreSuccess() {
        // finish load more with result success, set loading = false and remove item load more
        if (items.isNotEmpty()) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size)
        }
        isLoadingMore = false
        isLoadMoreFail = false
    }

    fun loadMoreFail() {
        // finish load more with result success, only set loading = false and load fail = true
        isLoadingMore = false
        isLoadMoreFail = true
    }

    protected abstract fun onCreateItemBinding(parent: ViewGroup): IB

    protected abstract fun onCreateLoadMoreBinding(parent: ViewGroup): LB

    protected abstract fun onBindItem(binding: IB, item: T)

    protected abstract fun onBindLoadMore(binding: LB)

}
