package com.goyals.mercuryscannerapp.utils

import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object AppUtils {
  fun convertScanDataToAadharData(rawString: String): AadharRequest {
    var dob = ""
    val pinCode: String
    val uid = try {
      rawString.substringAfter("uid=\"")
        .substringBefore("\"")
    } catch (e: Exception) {
      ""
    }
    val name = try {
      rawString.substringAfter("name=\"")
        .substringBefore("\"")
    } catch (e: Exception) {
      ""
    }
    val gender = try {
      rawString.substringAfter("gender=\"")
        .substringBefore("\"")
    } catch (exception: Exception) {
      ""
    }
    val yob = try {
      rawString.substringAfter("yob=\"")
        .substringBefore("\"")
    } catch (e: Exception) {
      ""
    }
    var address = try {
      if (rawString.contains("co=")) {
        getAddressFromString(rawString.substringAfter("co=\"")
          .substringBefore("\" pc"))
      } else {
        ""
      }
    } catch (e: Exception) {
      ""
    }
    if (address.isEmpty()) {
      address = try {
        getAddressFromString(rawString.substringAfter("street=\"")
          .substringBefore("\" pc"))
      } catch (exception: Exception) {
        ""
      }
    }
    val district = try {
      rawString.substringAfter("dist=\"")
        .substringBefore("\"")
    } catch (e: Exception) {
      ""
    }
    val state = try {
      rawString.substringAfter("state=\"")
        .substringBefore("\"")
    } catch (e: Exception) {
      ""
    }
    if (rawString.contains("dob")) {
      dob = try {
        rawString.substringAfter("dob=\"")
          .substringBefore("\"/>")
      } catch (e: Exception) {
        ""
      }
      pinCode = try {
        rawString.substringAfter("pc=\"")
          .substringBefore("\"")
      } catch (e: Exception) {
        ""
      }
    } else {
      pinCode = try {
        rawString.substringAfter("pc=\"")
          .substringBefore("\"/>")
      } catch (e: Exception) {
        ""
      }
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