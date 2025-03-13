package com.example.vkmusicapp

import retrofit2.http.GET
import retrofit2.http.Query

interface VKApiService {
    @GET("photos.get")
    suspend fun getPhotos(
        @Query("owner_id") ownerId: String,
        @Query("album_id") albumId: String,
        @Query("v") version: String,
        @Query("access_token") accessToken: String
    ): VKPhotoResponse
}

data class VKPhotoResponse(
    val response: PhotoResponse?
)

data class PhotoResponse(
    val items: List<PhotoItem>
)

data class PhotoItem(
    val id: Long,
    val owner_id: Long,
    val sizes: List<PhotoSize>
) {
    // Получаем URL из массива sizes
    fun getUrl(): String? {
        // Здесь можно выбрать нужный размер, например, 'z' для среднего
        return sizes.firstOrNull { it.type == "z" }?.url
    }
}

data class PhotoSize(
    val type: String,
    val url: String
)
