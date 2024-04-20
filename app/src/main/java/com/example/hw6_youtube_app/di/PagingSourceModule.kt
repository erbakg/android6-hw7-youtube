package com.example.hw6_youtube_app.di

import com.example.hw6_youtube_app.data.api.YouTubeApiService
import com.example.hw6_youtube_app.data.paging.YouTubePlaylistsSource
import org.koin.dsl.module

val pagingSourceModule = module {
    factory { provideYouTubePagingSource(get()) }
}

fun provideYouTubePagingSource(apiService: YouTubeApiService) =
    YouTubePlaylistsSource(apiService)