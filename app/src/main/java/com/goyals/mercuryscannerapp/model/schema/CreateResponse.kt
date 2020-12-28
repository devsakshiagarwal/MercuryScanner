package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class CreateResponse(@Json(
  name = "responseCode") val responseCode: Int = 0,
  @Json(name = "responseData") val responseData: ResponseData = ResponseData(),
  @Json(name = "responseMessage") val responseMessage: String = "")

@JsonClass(generateAdapter = true)
data class ResponseData(@Json(name = "customerID") val customerID: Int = 0)