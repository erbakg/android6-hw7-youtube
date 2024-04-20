package com.example.hw6_youtube_app.data.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.hw6_youtube_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

abstract class BaseRepository {

    protected fun <T> doRequest(apiCall: suspend () -> Response<T>): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val response = apiCall()
                if (response.isSuccessful && response.body() != null && response.code() in 200..300) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    emit(Resource.Error("Unsuccessful response: ${response.code()}"))
                }
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Unknown error!"
                emit(Resource.Error(errorMessage))
            }
        }
}