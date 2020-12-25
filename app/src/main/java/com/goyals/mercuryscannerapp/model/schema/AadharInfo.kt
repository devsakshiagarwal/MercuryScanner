package com.goyals.mercuryscannerapp.model.schema

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AadharInfo(@Json(
  name = "customer_address") val customerAddress: String = "",
  @Json(name = "customer_district") val customerDistrict: String = "",
  @Json(name = "customer_dob") val customerDob: String = "",
  @Json(name = "customer_gender") val customerGender: String = "",
  @Json(name = "customer_id") val customerId: Int = 0,
  @Json(name = "customer_martial") val customerMartial: String = "",
  @Json(name = "customer_name") val customerName: String = "",
  @Json(name = "customer_phone") val customerPhone: String = "",
  @Json(name = "customer_pincode") val customerPincode: Int = 0,
  @Json(name = "customer_state") val customerState: String = "",
  @Json(name = "customer_uid") val customerUid: String = "") : Parcelable