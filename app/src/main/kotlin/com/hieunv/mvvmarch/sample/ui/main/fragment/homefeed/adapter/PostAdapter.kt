package com.hieunv.mvvmarch.sample.ui.main.fragment.homefeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hieunv.mvvmarch.base.entity.Post
import com.hieunv.mvvmarch.base.ui.common.LoadMoreSimpleAdapter
import com.hieunv.mvvmarch.sample.databinding.RvItemLoadMoreBinding
import com.hieunv.mvvmarch.sample.databinding.RvItemPostBinding
import com.hieunv.mvvmarch.sample.ui.main.adapter.helper.OnLoadMoreViewModel

class PostAdapter(
        recyclerView: RecyclerView,
        private val viewModel: OnLoadMoreViewModel,
        private val itemClickListener: OnItemClickListener,
        loadMoreListener: OnLoadMoreListener
) : LoadMoreSimpleAdapter<Post, RvItemPostBinding, RvItemLoadMoreBinding>(recyclerView, loadMoreListener) {

    interface OnItemClickListener {
        fun onItemClick(item: Post)
    }

    override fun onCreateItemBinding(parent: ViewGroup): RvItemPostBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RvItemPostBinding.inflate(layoutInflater, parent, false)
    }

    override fun onCreateLoadMoreBinding(parent: ViewGroup): RvItemLoadMoreBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RvItemLoadMoreBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindItem(binding: RvItemPostBinding, item: Post) {
        binding.post = item

        Glide.with(binding.postImage).load(item.image).into(binding.postImage)

        binding.root.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }

    override fun onBindLoadMore(binding: RvItemLoadMoreBinding) {
        binding.viewModel = viewModel
    }

    fun updatePosts(posts: List<Post>) {
        if (items.isNotEmpty()) {
            posts.forEach { newPost ->
                // Remove duplicate post
                items.removeAll { oldPost ->
                    oldPost?.id == newPost.id
                }
            }
        }
        items.addAll(posts)
        notifyDataSetChanged()
    }

    fun updatePost(post: Post) {
        items.firstOrNull {
            it?.id == post.id
        }?.let {
            notifyDataSetChanged()
        }
    }

    fun removePost(post: Post) {
        items.removeAll {
            it?.id == post.id
        }.let {
            if (it) {
                notifyDataSetChanged()
            }
        }
    }

}
