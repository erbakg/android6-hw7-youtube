package com.example.hw6_youtube_app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hw6_youtube_app.data.api.YouTubeApiService
import com.example.hw6_youtube_app.data.models.Item
import com.example.hw6_youtube_app.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class YouTubeVideosSource(
    private val api: YouTubeApiService,
    private val id: String
) : PagingSource<String, Item>() {

    override fun getRefreshKey(state: PagingState<String, Item>): String? {
        var current: String? = ""

        val anchorPosition = state.anchorPosition


        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            if (anchorPosition != null) {
                current = state.closestPageToPosition(anchorPosition)?.prevKey?.let {
                    api.fetchVideos(
                        maxResults = Constants.MAX_RESULTS,
                        part = Constants.PART,
                        pageToken = it,
                        playlistId = id
                    ).body()?.nextPageToken
                }
            }
        }
        return current
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
        val start = params.key ?: Constants.STARTING_PAGE_TOKEN

        return try {
            val response = api.fetchVideos(
                maxResults = Constants.MAX_RESULTS,
                part = Constants.PART,
                pageToken = start,
                playlistId = id
            )
            if(response.isSuccessful && response.body() != null) {
                val nextKey =
                    if (response.body()?.items?.isEmpty() == true) null else response.body()?.nextPageToken
                val prevKey =
                    if (start == Constants.STARTING_PAGE_TOKEN) null else response.body()?.prevPageToken

                LoadResult.Page(
                    data = response.body()!!.items,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            else {
                throw IOException()
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }
}