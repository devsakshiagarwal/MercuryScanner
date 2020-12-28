package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class ListRequest(@Json(name = "page_limit") val pageLimit: Int = 0,
  @Json(name = "page_number") val pageNumber: Int = 0,
  @Json(name = "search_term") val searchTerm: String = "",
  @Json(name = "user_id") val userId: Int = 0,
  @Json(name = "user_type") val userType: String = "")