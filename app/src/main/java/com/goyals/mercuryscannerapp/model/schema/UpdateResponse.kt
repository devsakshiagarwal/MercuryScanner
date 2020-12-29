package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class UpdateResponse(@Json(
  name = "responseCode") val responseCode: Int = 0,
  @Json(
    name = "responseData") val responseData: UpdateResponseData = UpdateResponseData(),
  @Json(name = "responseMessage") val responseMessage: String = "")

@JsonClass(generateAdapter = true)
class UpdateResponseData()