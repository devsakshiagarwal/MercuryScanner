package com.goyals.mercuryscannerapp.utils

import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object AppUtils {
  fun convertScanDataToAadharData(rawString: String): AadharRequest {
    var dob = ""
    val pinCode: String
    val uid = rawString.substringAfter("uid=\"")
      .substringBefore("\" name")
    val name = rawString.substringAfter("name=\"")
      .substringBefore("\" gender")
    val gender = rawString.substringAfter("gender=\"")
      .substringBefore("\" yob")
    val yob = rawString.substringAfter("yob=\"")
      .substringBefore("\" co")
    val address = getAddressFromString(rawString.substringAfter("co=\"")
      .substringBefore("\" pc"))
    val district = rawString.substringAfter("dist=\"")
      .substringBefore("\"")
    val state = rawString.substringAfter("state=\"")
      .substringBefore("\"")
    if (rawString.contains("dob")) {
      dob = rawString.substringAfter("dob=\"")
        .substringBefore("\"/>")
      pinCode = rawString.substringAfter("pc=\"")
        .substringBefore("\" dob")
    } else {
      pinCode = rawString.substringAfter("pc=\"")
        .substringBefore("\"/>")
    }
    return AadharRequest(address, 0, "updated", district, dob, gender, "", "",
      name, "", pinCode, uid, "", state, 0, yob)
  }

  private fun getAddressFromString(rawAddress: String): String {
    return rawAddress.replace("\"", "")
  }

  fun getAge(year: Int): Int {
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()
    dob[year, 1] = 1
    var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
    if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
      age--
    }
    return age
  }

  fun getFormattedDob(date: Int,
    month: Int,
    year: Int): String {
    return "$date-$month-$year"
  }

  fun changeDateFormat(time: String): String {
    val inputPattern = "yyyy-MM-dd"
    val outputPattern = "dd-MMM-yyyy"
    val inputFormat = SimpleDateFormat(inputPattern)
    val outputFormat = SimpleDateFormat(outputPattern)
    val date: Date
    var str = ""
    try {
      date = inputFormat.parse(time)
      str = outputFormat.format(date)
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return str
  }
}