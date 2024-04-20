package com.example.hw6_youtube_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hw6_youtube_app.data.api.YouTubeApiService
import com.example.hw6_youtube_app.data.models.Item
import com.example.hw6_youtube_app.data.paging.YouTubeVideosSource
import com.example.hw6_youtube_app.data.utils.Constants
import kotlinx.coroutines.flow.Flow

class PlaylistItemViewModel(
    private val api: YouTubeApiService
): ViewModel() {

    private var itemsFlow: Flow<PagingData<Item>>? = null

    fun setupPlaylistId(id: String) {
        itemsFlow = Pager(
            config = PagingConfig(pageSize = Constants.MAX_RESULTS),
            pagingSourceFactory = { YouTubeVideosSource(api, id) }
        ).flow.cachedIn(viewModelScope)
    }

    fun getVideos(): Flow<PagingData<Item>>? = itemsFlow

}