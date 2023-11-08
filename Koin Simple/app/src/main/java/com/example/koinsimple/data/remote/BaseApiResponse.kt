package com.example.koinsimple.data.remote

import com.example.koinsimple.utils.NetworkResult
import retrofit2.Response
import android.util.Log

abstract class BaseApiResponse {

    suspend fun <T,R> validateApiCall(apiCall: suspend () -> Response<R>, transform :(R) -> T): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(transform(body))
                }
            }
            Log.e(this.javaClass.name, "Api call not successful: ${response.code()} ${response.message()}")
            return NetworkResult.Error("Ha ocurrido un error al recuperar la información")
        } catch (e: Exception) {
            Log.e(this.javaClass.name, "Api call failed: $e")
            return NetworkResult.Error("Ha ocurrido un error al recuperar la información")
        }
    }

}