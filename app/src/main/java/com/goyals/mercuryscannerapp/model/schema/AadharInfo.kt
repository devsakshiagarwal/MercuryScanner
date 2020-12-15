package com.goyals.mercuryscannerapp.model.schema

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AadharInfo(@Json(name = "uid") var uid: String = "",
  @Json(name = "name") var name: String = "",
  @Json(name = "gender") var gender: String = "",
  @Json(name = "dob") var dob: String = "",
  @Json(name = "yob") var yob: String = "",
  @Json(name = "address") var address: String = "",
  @Json(name = "pincode") var pincode: String = "") : Parcelable