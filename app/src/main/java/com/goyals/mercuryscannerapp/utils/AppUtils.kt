package com.goyals.mercuryscannerapp.utils

import android.util.Log
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object AppUtils {
  fun convertScanDataToAadharData(rawString: String): AadharInfo {
    var dob = ""
    var pinCode = ""
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
    if (rawString.contains("dob")) {
      dob = rawString.substringAfter("dob=\"")
        .substringBefore("\"/>")
      pinCode = rawString.substringAfter("pc=\"")
        .substringBefore("\" dob")
    } else {
      pinCode = rawString.substringAfter("pc=\"")
        .substringBefore("\"/>")
    }
    Log.v("AppUtils",
      "name is: $name, uid is: $uid, gender is: $gender, yob is: $yob, address is: $address, dob is: $dob, pincode is: $pinCode")
    return AadharInfo(uid, name, if (gender.equals("M", true)) {
      "Male"
    } else {
      "Female"
    }, dob, yob, "$address, $pinCode", getAge(getTimeStampInMillis(yob)))
  }

  private fun getAddressFromString(rawAddress: String): String {
    //return rawAddress.replace("\" loc=\"", ", ", true)
    //  .replace("\" vtc=\"", ", ", true)
    //  .replace("\" po=\"", ", ", true)
    //  .replace("\" dist=\"", ", ", true)
    //  .replace("\" state=\"", ", ", true)
    //  .replace("\" pc=\"", ", ", true)
    //  .replace("\" house=\"", ", ", true)
    //  .replace("\" street=\"", ", ", true)
    //  .replace("\" subdist=\"", ", ", true)
    return rawAddress.replace("\"", "")
  }

  private fun getAge(dateOfBirth: Long): String {
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
    val ageInt = age
    return "$ageInt years"
  }

  fun getDateFromMillis(timestamp: Long): String {
    return SimpleDateFormat("yyyy-MM-DD", Locale.getDefault()).format(
      Date(timestamp))
      .toString()
  }

  private fun getTimeStampInMillis(time: String): Long {
    return try {
      val formatter: DateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
      val date: Date = formatter.parse(time) as Date
      date.time
    } catch (exception: Exception) {
      0
    }
  }
}