package com.goyals.mercuryscannerapp.model.schema

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class Login(@Json(name = "responseCode") val responseCode: Int = 0,
  @Json(name = "responseData") val responseData: LoginData = LoginData(),
  @Json(name = "responseMessage") val responseMessage: String = "")

@JsonClass(generateAdapter = true)
data class LoginData(@Json(name = "user_email") val userEmail: String = "",
  @Json(name = "user_id") val userId: Int = 0,
  @Json(name = "user_name") val userName: String = "",
  @Json(name = "user_password") val userPassword: String = "",
  @Json(name = "user_status") val userStatus: Int = 0,
  @Json(name = "user_type") val userType: String = "")