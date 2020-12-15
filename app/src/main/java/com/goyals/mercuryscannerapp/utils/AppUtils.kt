package com.goyals.mercuryscannerapp.utils

import android.util.Log
import com.goyals.mercuryscannerapp.model.schema.AadharInfo

object AppUtils {
  fun convertScanDataToAadharData(rawString: String): AadharInfo {
    Log.v("AppUtils", "raw string is: $rawString")
    val uid = rawString.substringAfter("uid=\"")
      .substringBefore("\" name")
    val name = rawString.substringAfter("name=\"")
      .substringBefore("\" gender")
    val gender = rawString.substringAfter("gender=\"")
      .substringBefore("\" yob")
    val yob = rawString.substringAfter("yob=\"")
      .substringBefore("\" co")
    val address = rawString.substringAfter("co=\"")
      .substringBefore("\" pc")
    val dob = rawString.substringAfter("dob=\"")
      .substringBefore("\"/>")
    val pincode = rawString.substringAfter("pc=\"")
      .substringBefore("\" dob")
    Log.v("AppUtils",
      "name is: $name, uid is: $uid, gender is: $gender, yob is: $yob, address is: $address, dob is: $dob, pincode is: $pincode")
    return AadharInfo(uid, name, gender, dob, yob, address, pincode)
  }
}