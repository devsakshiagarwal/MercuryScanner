package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.annotation.Nullable

@JsonClass(generateAdapter = true)
data class AadharInfo(@Json(name = "address") val address: String = "",
  @Json(name = "covid_result") val covidResult: Int = 0,
  @Json(name = "covid_status") val covidStatus: String? = "",
  @Json(name = "dob") val dob: String = "",
  @Json(name = "gender") val gender: String = "",
  @Nullable @Json(
    name = "location_name") val locationName: String? = "",
  @Json(name = "martial") val martial: String = "",
  @Json(name = "name") val name: String = "",
  @Json(name = "phone") val phone: String = "",
  @Json(name = "proof_number") val proofNumber: String = "",
  @Nullable @Json(name = "proof_type") val proofType: String? = "",
  @Json(name = "id") val userId: Int = 0,
  @Nullable @Json(
    name = "year_of_birth") val yearOfBirth: String? = "") {
  val covidResultToShow: String = if (covidResult == 0) {
    "Negative"
  } else {
    "Positive"
  }
}