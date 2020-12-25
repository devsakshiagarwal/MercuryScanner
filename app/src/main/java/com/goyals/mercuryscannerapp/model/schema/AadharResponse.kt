package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class AadharResponse(@Json(
  name = "responseCode") val responseCode: Int = 0,
  @Json(name = "responseData") val aadharList: List<AadharInfo> = listOf(),
  @Json(name = "responseMessage") val responseMessage: String = "")