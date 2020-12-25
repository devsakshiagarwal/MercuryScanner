package com.goyals.mercuryscannerapp.model.schema

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import javax.annotation.Nullable

@Parcelize
@JsonClass(generateAdapter = true)
data class AadharInfo(@Json(name = "customer_address") val address: String = "",
  @Json(name = "customer_covid_result") val covidResult: Int = 0,
  @Json(name = "customer_covid_status") val covidStatus: String = "",
  @Json(name = "customer_dob") val dob: String = "",
  @Json(name = "customer_gender") val gender: String = "",
  @Nullable @Json(
    name = "customer_location_name") val locationName: String? = "",
  @Json(name = "customer_martial") val martial: String = "",
  @Json(name = "customer_name") val name: String = "",
  @Json(name = "customer_phone") val phone: String = "",
  @Json(name = "customer_proof_number") val proofNumber: String = "",
  @Nullable @Json(name = "customer_proof_type") val proofType: String? = "",
  @Json(name = "customer_id") val userId: Int = 0,
  @Nullable @Json(
    name = "customer_year_of_birth") val yearOfBirth: String? = "") : Parcelable {
  val covidResultToShow: String = if (covidResult == 0) {
    "Negative"
  } else {
    "Positive"
  }
}