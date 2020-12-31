package com.goyals.mercuryscannerapp.model.schema

import android.os.Parcelable
import com.goyals.mercuryscannerapp.utils.AppUtils
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AadharRequest(@Json(name = "address") val address: String = "",
  @Json(name = "covid_result") val covidResult: Int = 0,
  @Json(name = "covid_status") val covidStatus: String = "",
  @Json(name = "district") val district: String = "",
  @Json(name = "dob") val dob: String = "",
  @Json(name = "gender") val gender: String = "",
  @Json(name = "location_name") val locationName: String = "",
  @Json(name = "martial") val martial: String = "",
  @Json(name = "name") val name: String = "",
  @Json(name = "phone") val phone: String = "",
  @Json(name = "pincode") val pincode: String = "000000",
  @Json(name = "proof_number") val proofNumber: String = "",
  @Json(name = "proof_type") val proofType: String = "",
  @Json(name = "state") val state: String = "",
  @Json(name = "user_id") val userId: Int = 0,
  @Json(name = "year_of_birth") val yearOfBirth: String = "",
  @Json(name = "updated_by") val updatedBy: Int = 0) : Parcelable {
  val age: Int = when {
    yearOfBirth.isNotEmpty() -> {
      AppUtils.getAge(yearOfBirth.toInt())
    }
    else -> {
      0
    }
  }
  val genderToShow: String = if (gender.isNotEmpty()) {
    when {
      gender.first() == 'F' -> {
        "Female"
      }
      gender.first() == 'M' -> {
        "Male"
      }
      else -> {
        "Other"
      }
    }
  } else {
    "Other"
  }
}