package com.goyals.mercuryscannerapp.arch

import org.json.JSONObject
import retrofit2.Response

abstract class BaseDataSource {
  protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
    try {
      val response = call()
      if (response.isSuccessful) {
        val body = response.body()
        if (body != null) return Result.success(body)
      }
      val errorBody = JSONObject(response.errorBody()!!
        .charStream()
        .readText())
      return error(errorBody.getString("responseMessage"))
    } catch (e: Exception) {
      return error(e.message ?: e.toString())
    }
  }

  private fun <T> error(message: String): Result<T> {
    return Result.error(message)
  }
}
