package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class UpdateCustomerSchema(@Json(
  name = "covid_result") val covidResult: Int = 1,
  @Json(name = "updated_by") val updatedBy: Int = 0,
  @Json(name = "covid_status") val covidStatus: String = "updated")