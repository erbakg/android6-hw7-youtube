package com.example.hw6_youtube_app.data.models

data class YouTubeItemsModel(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String?,
    val pageInfo: PageInfo,
    val prevPageToken: String?
)

data class Item(
    val contentDetails: ContentDetails,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet
)

data class PageInfo(
    val resultsPerPage: Int,
    val totalResults: Int
)

data class ContentDetails(
    val itemCount: Int?,
    val videoId: String?,
    val videoPublishedAt: String?
)

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val localized: Localized?,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String,
    val playlistId: String?,
    val position: Int?,
    val resourceId: ResourceId?,
    val videoOwnerChannelId: String?,
    val videoOwnerChannelTitle: String?
)

data class ResourceId(
    val kind: String,
    val videoId: String
)

data class Localized(
    val description: String,
    val title: String
)

data class Thumbnails(
    val default: Thumbnail?,
    val high: Thumbnail?,
    val maxres: Thumbnail?,
    val medium: Thumbnail?,
    val standard: Thumbnail?
)

data class Thumbnail(
    val height: Int,
    val url: String,
    val width: Int
)
