package com.goyals.mercuryscannerapp.utils

import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
    return AadharRequest(address, 0, "updated", district,
      "${getTimeStampInMillis(dob)}", gender, "", "", name, "", pinCode, uid,
      "", state, 0, yob)
  }

  private fun getAddressFromString(rawAddress: String): String {
    return rawAddress.replace("\"", "")
  }

  fun getAge(dateOfBirth: Long): Int {
    val dobLocal = Calendar.getInstance()
    dobLocal.timeInMillis = dateOfBirth
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()
    dob.set(dobLocal.get(Calendar.YEAR), dobLocal.get(Calendar.MONTH),
      dobLocal.get(Calendar.DAY_OF_MONTH))
    var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
    if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
      age--
    }
    return age
  }

  fun getDateFromMillis(timestamp: Long): String {
    return SimpleDateFormat("DD-MM-yyyy", Locale.getDefault()).format(
      Date(timestamp))
      .toString()
  }

  fun getTimeStampInMillis(time: String): Long {
    if (time.contains("/")) {
      if (time.indexOf("/", 0, true) == 2) {
        try {
          val formatter: DateFormat =
            SimpleDateFormat("DD/MM/yyyy", Locale.getDefault())
          val date: Date = formatter.parse(time) as Date
          return date.time
        } catch (exception: Exception) {
          return 0
        }
      } else {
        try {
          val formatter: DateFormat =
            SimpleDateFormat("yyyy/MM/DD", Locale.getDefault())
          val date: Date = formatter.parse(time) as Date
          return date.time
        } catch (exception: Exception) {
          return 0
        }
      }
    } else if (time.contains("-")) {
      if (time.indexOf("-", 0, true) == 2) {
        try {
          val formatter: DateFormat =
            SimpleDateFormat("DD-MM-yyyy", Locale.getDefault())
          val date: Date = formatter.parse(time) as Date
          return date.time
        } catch (exception: Exception) {
          return 0
        }
      }
      else try {
        val formatter: DateFormat =
          SimpleDateFormat("yyyy-MM-DD", Locale.getDefault())
        val date: Date = formatter.parse(time) as Date
        return date.time
      } catch (exception: Exception) {
        return 0
      }
    }
    return 0
  }

  fun getFormattedDob(date: Int,
    month: Int,
    year: Int): String {
    return "$date-$month-$year"
  }
}