package com.example.hw6_youtube_app.app

import android.app.Application
import com.example.hw6_youtube_app.di.adapterModule
import com.example.hw6_youtube_app.di.networkModule
import com.example.hw6_youtube_app.di.pagingSourceModule
import com.example.hw6_youtube_app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                viewModelModule,
                adapterModule,
                pagingSourceModule
            )
        }
    }
}