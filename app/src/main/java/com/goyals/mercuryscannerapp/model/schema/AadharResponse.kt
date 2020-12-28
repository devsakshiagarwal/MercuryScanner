package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class AadharResponse(@Json(
  name = "responseCode") val responseCode: Int = 0,
  @Json(name = "responseData") val customerList: CustomerList = CustomerList(),
  @Json(name = "responseMessage") val responseMessage: String = "")

@JsonClass(generateAdapter = true)
data class CustomerList(@Json(
  name = "customerList") val aadharList: List<AadharInfo> = listOf())