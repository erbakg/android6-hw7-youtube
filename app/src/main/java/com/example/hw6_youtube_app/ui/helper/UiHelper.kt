package com.example.hw6_youtube_app.ui.helper

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import coil.load
import com.example.hw6_youtube_app.data.models.Item

object UiHelper {

    fun changeVisibility(isVisible: Boolean, vararg views: View) {
        for (view in views) {
            view.isVisible = isVisible
        }
    }

    fun loadPreview(item: Item, imageView: ImageView) {
        item.snippet.thumbnails.standard?.let {
            imageView.load(it.url)
            return
        }
        item.snippet.thumbnails.default?.let {
            imageView.load(it.url)
            return
        }
        item.snippet.thumbnails.medium?.let {
            imageView.load(it.url)
            return
        }
        item.snippet.thumbnails.high?.let {
            imageView.load(it.url)
            return
        }
        item.snippet.thumbnails.maxres?.let {
            imageView.load(it.url)
            return
        }
    }
}