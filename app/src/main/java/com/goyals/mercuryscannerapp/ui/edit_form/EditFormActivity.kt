package com.goyals.mercuryscannerapp.ui.edit_form

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.arch.Result.Status
import com.goyals.mercuryscannerapp.model.SharedPref
import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import com.goyals.mercuryscannerapp.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_form.*
import kotlinx.android.synthetic.main.layout_progress.progress_bar
import java.lang.Exception
import java.util.Calendar

@AndroidEntryPoint
class EditFormActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
  private val editFormViewModel: EditFormViewModel by viewModels()
  private var aadharInfoFromBundle = AadharRequest()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_form)
    initViews()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_save, menu)
    return true
  }

  override fun onMenuItemClick(item: MenuItem?): Boolean {
    when (item!!.itemId) {
      R.id.item_save -> {
        updateData()
        return true
      }
    }
    return false
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        onBackPressed()
        return true
      }
      R.id.item_save -> {
        updateData()
        return true
      }
    }
    return false
  }

  private fun initViews() {
    actionBar?.setDisplayHomeAsUpEnabled(true)
    handleBundle()
    et_id_type.setOnClickListener {
      IdTypeFragment.newInstance()
        .show(supportFragmentManager, "id_type_fragment")
    }
    editFormViewModel.idTypeLiveData.observe(this, Observer {
      et_id_type.setText(it!!)
    })
    et_gender.setOnClickListener {
      GenderFragment.newInstance()
        .show(supportFragmentManager, "gender_fragment")
    }
    editFormViewModel.genderLiveData.observe(this, Observer {
      et_gender.setText(it!!)
    })
    et_location.setOnClickListener {
      LocationFragment.newInstance()
        .show(supportFragmentManager, "location_fragment")
    }
    editFormViewModel.locationLiveData.observe(this, Observer {
      et_location.setText(it!!)
      SharedPref(this).setLocation(it)
    })
    et_location.setText(SharedPref(this).getLocation())
    et_dob.setOnClickListener { handleDob() }
  }

  private fun handleBundle() {
    if (intent.getParcelableExtra<AadharRequest>("aadhar_info") != null) {
      aadharInfoFromBundle = intent.getParcelableExtra("aadhar_info")!!
      et_uid.setText(aadharInfoFromBundle.proofNumber)
      et_name.setText(aadharInfoFromBundle.name)
      if (aadharInfoFromBundle.dob.isNotEmpty()) {
        et_dob.setText(aadharInfoFromBundle.dob)
      }
      et_yob.setText(aadharInfoFromBundle.yearOfBirth)
      et_address.setText(aadharInfoFromBundle.address)
      et_age.setText(aadharInfoFromBundle.age.toString())
      et_gender.setText(aadharInfoFromBundle.genderToShow)
    }
  }

  private fun handleDob() {
    val calender = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(this,
      { _: DatePicker?, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
        et_yob.setText(selectedYear.toString())
        et_dob.setText(
          AppUtils.getFormattedDob(dayOfMonth, monthOfYear + 1, selectedYear))
      }, calender[Calendar.YEAR], calender[Calendar.MONTH],
      calender[Calendar.DAY_OF_MONTH])
    datePickerDialog.show()
  }

  private fun updateData() {
    if (isIdValid() && isNameValid() && isAddressValid() && isAgeValid() && isGenderValid() && isPhoneValid()) {
      val aadharRequest =
        AadharRequest(et_address.text.toString(), 0, "pending",
          aadharInfoFromBundle.district, et_dob.text.toString(),
          if (et_gender.text.toString() == getString(R.string.other)) {
            "Transgender"
          } else {
            et_gender.text.toString()
          }, et_location.text.toString(), aadharInfoFromBundle.martial,
          et_name.text.toString(), et_phone.text.toString(),
          aadharInfoFromBundle.pincode, et_uid.text.toString(),
          et_id_type.text.toString(), aadharInfoFromBundle.state,
          SharedPref(this).getUserId(), et_yob.text.toString(),
          SharedPref(this).getUserId())
      editFormViewModel.createUser(aadharRequest)
        .observe(this, Observer {
          when (it.status) {
            Status.SUCCESS -> {
              val createResponse = it.data!!
              progress_bar.visibility = View.GONE
              showID(
                "Entry updated. your id is: ${createResponse.responseData.customerID}")
            }
            Status.ERROR -> {
              progress_bar.visibility = View.GONE
              showError(it.message!!)
            }
            Status.LOADING -> progress_bar.visibility = View.VISIBLE
          }
        })
    }
  }

  private fun isIdValid(): Boolean {
    return if (et_id_type.text.toString() == getString(R.string.aadhar_card) && et_uid.text.toString().length != 12) {
      tv_err_uid.visibility = View.VISIBLE
      false
    } else if (et_id_type.text.toString() == getString(R.string.pan_card) && et_uid.text.toString().length != 10) {
      tv_err_uid.visibility = View.VISIBLE
      false
    } else if (et_uid.text.toString().length < 6) {
      tv_err_uid.visibility = View.VISIBLE
      false
    } else {
      tv_err_uid.visibility = View.GONE
      true
    }
  }

  private fun isNameValid(): Boolean {
    return if (et_name.text.toString()
        .isNotEmpty()) {
      tv_err_name.visibility = View.GONE
      true
    } else {
      tv_err_name.visibility = View.VISIBLE
      false
    }
  }

  private fun isAddressValid(): Boolean {
    return if (et_address.text.toString()
        .isNotEmpty()) {
      tv_err_address.visibility = View.GONE
      true
    } else {
      tv_err_address.visibility = View.VISIBLE
      false
    }
  }

  private fun isAgeValid(): Boolean {
    return try {
      if (et_age.text.toString()
          .toInt() > 0) {
        tv_err_age.visibility = View.GONE
        true
      } else {
        tv_err_age.visibility = View.VISIBLE
        false
      }
    } catch (e: Exception) {
      tv_err_age.visibility = View.VISIBLE
      false
    }
  }

  private fun isGenderValid(): Boolean {
    return if (et_gender.text.toString()
        .isNotEmpty()) {
      tv_err_gender.visibility = View.GONE
      true
    } else {
      tv_err_gender.visibility = View.VISIBLE
      false
    }
  }

  private fun isPhoneValid(): Boolean {
    return if (et_phone.text.toString().length >= 10) {
      tv_err_phone.visibility = View.GONE
      true
    } else {
      tv_err_phone.visibility = View.VISIBLE
      false
    }
  }

  private fun showError(message: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Alert")
      .setMessage(message)
    builder.setPositiveButton("Ok") { dialog, _ ->
      dialog.dismiss()
    }
    builder.show()
  }

  private fun showID(message: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Note your ID")
      .setMessage(message)
    builder.setPositiveButton("Ok") { dialog, _ ->
      dialog.dismiss()
      finish()
    }
    builder.show()
  }
}