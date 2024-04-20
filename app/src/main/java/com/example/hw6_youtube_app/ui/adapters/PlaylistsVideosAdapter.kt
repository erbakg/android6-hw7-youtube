package com.example.hw6_youtube_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hw6_youtube_app.databinding.ItemPlaylistBinding
import com.example.hw6_youtube_app.databinding.ItemVideoBinding
import com.example.hw6_youtube_app.data.models.Item
import com.example.hw6_youtube_app.data.utils.Constants
import com.example.hw6_youtube_app.ui.helper.UiHelper.loadPreview

class PlaylistsVideosAdapter : PagingDataAdapter<Item, ViewHolder>(DIFF_UTIL_CALL_BACK) {

    private enum class ItemType { PLAYLIST, VIDEO }

    private companion object {
        val DIFF_UTIL_CALL_BACK: DiffUtil.ItemCallback<Item> =
            object : DiffUtil.ItemCallback<Item>() {
                override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun getItemViewType(position: Int) =
        if (getItem(position)?.contentDetails?.videoId == null)
            ItemType.PLAYLIST.ordinal
        else
            ItemType.VIDEO.ordinal


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemVideoViewHolder -> getItem(position)?.let { holder.onBind(it) }
            is ItemPlaylistViewHolder -> getItem(position)?.let { holder.onBind(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == ItemType.PLAYLIST.ordinal)
            ItemPlaylistViewHolder(
                ItemPlaylistBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        else ItemVideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    fun setOnItemClickListener(onClick: (Item) -> Unit) {
        onItemClick = onClick
    }

    private var onItemClick: ((Item) -> Unit)? = null

    inner class ItemVideoViewHolder(private val binding: ItemVideoBinding) :
        ViewHolder(binding.root) {

        fun onBind(item: Item) = with(binding) {
            root.setOnClickListener {
                onItemClick?.let { onItemClick ->
                    onItemClick(item)
                }
            }
            tvVideoTitle.text = item.snippet.title
            loadPreview(item, ivPreview)
        }
    }

    inner class ItemPlaylistViewHolder(private val binding: ItemPlaylistBinding) :
        ViewHolder(binding.root) {

        fun onBind(item: Item) = with(binding) {
            root.setOnClickListener {
                onItemClick?.let { onItemClick ->
                    onItemClick(item)
                }
            }
            tvPlaylistTitle.text = item.snippet.title
            "${item.contentDetails.itemCount} ${Constants.VIDEO_SERIES}".apply {
                tvVideosCount.text = this
            }
            loadPreview(item, ivPreview)
        }
    }
}
