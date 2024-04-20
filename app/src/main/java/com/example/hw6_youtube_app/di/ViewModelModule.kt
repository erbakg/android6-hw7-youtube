package com.example.hw6_youtube_app.di

import com.example.hw6_youtube_app.data.api.YouTubeApiService
import com.example.hw6_youtube_app.data.paging.YouTubePlaylistsSource
import com.example.hw6_youtube_app.ui.viewmodels.PlaylistItemViewModel
import com.example.hw6_youtube_app.ui.viewmodels.PlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<PlaylistsViewModel> { providePlaylistsViewModel(get()) }
    viewModel<PlaylistItemViewModel> { providePlaylistItemViewModel(get()) }
}

fun providePlaylistsViewModel(youTubePagingSource: YouTubePlaylistsSource) =
    PlaylistsViewModel(youTubePagingSource)

fun providePlaylistItemViewModel(apiService: YouTubeApiService) =
    PlaylistItemViewModel(apiService)