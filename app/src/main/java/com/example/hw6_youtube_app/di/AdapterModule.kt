package com.example.hw6_youtube_app.di

import com.example.hw6_youtube_app.ui.adapters.PlaylistsVideosAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { providePlaylistsAdapter() }
}

fun providePlaylistsAdapter() =
    PlaylistsVideosAdapter()
