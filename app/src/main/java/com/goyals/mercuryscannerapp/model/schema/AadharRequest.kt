package com.goyals.mercuryscannerapp.model.schema

import android.os.Parcelable
import com.goyals.mercuryscannerapp.utils.AppUtils
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AadharRequest(@Json(name = "address") val address: String = "",
  @Json(name = "district") val district: String = "",
  @Json(name = "dob") val dob: String = "",
  @Json(name = "yob") val yob: String = "",
  @Json(name = "gender") val gender: String = "",
  @Json(name = "martial") val martial: String = "",
  @Json(name = "name") val name: String = "",
  @Json(name = "phone") val phone: String = "",
  @Json(name = "pincode") val pincode: String = "",
  @Json(name = "state") val state: String = "",
  @Json(name = "uid") val uid: String = "") : Parcelable {
    val age = AppUtils.getAge(dob.toLong())
  }