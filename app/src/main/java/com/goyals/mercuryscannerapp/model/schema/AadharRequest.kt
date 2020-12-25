package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class AadharRequest(@Json(name = "address") val address: String = "",
  @Json(name = "district") val district: String = "",
  @Json(name = "dob") val dob: String = "",
  @Json(name = "gender") val gender: String = "",
  @Json(name = "martial") val martial: String = "",
  @Json(name = "name") val name: String = "",
  @Json(name = "phone") val phone: String = "",
  @Json(name = "pincode") val pincode: String = "",
  @Json(name = "state") val state: String = "",
  @Json(name = "uid") val uid: String = "")