package com.goyals.mercuryscannerapp.model.schema

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AadharInfo(@Json(name = "address") val address: String = "",
  @Json(name = "covid_result") val covidResult: Int = 0,
  @Json(name = "covid_status") val covidStatus: String = "",
  @Json(name = "district") val district: String = "",
  @Json(name = "dob") val dob: String = "",
  @Json(name = "gender") val gender: String = "",
  @Json(name = "location_name") val locationName: String = "",
  @Json(name = "martial") val martial: String = "",
  @Json(name = "name") val name: String = "",
  @Json(name = "phone") val phone: String = "",
  @Json(name = "proof_number") val proofNumber: String = "",
  @Json(name = "proof_type") val proofType: String = "",
  @Json(name = "user_id") val userId: Int = 0,
  @Json(name = "year_of_birth") val yearOfBirth: String = "") : Parcelable {
  val covidResultToShow: String = if (covidResult == 0) {
    "Negative"
  } else {
    "Positive"
  }
}