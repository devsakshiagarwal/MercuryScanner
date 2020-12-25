package com.goyals.mercuryscannerapp.ui.edit_form

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.arch.Result
import com.goyals.mercuryscannerapp.model.SharedPref
import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import com.goyals.mercuryscannerapp.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_form.*
import kotlinx.android.synthetic.main.layout_progress.progress_bar
import java.util.Calendar

@AndroidEntryPoint
class EditFormActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
  private val editFormViewModel: EditFormViewModel by viewModels()

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
    if (item.itemId == android.R.id.home) {
      onBackPressed()
      return true
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
      val aadharInfo = intent.getParcelableExtra<AadharRequest>("aadhar_info")!!
      et_id_type.setText("Aadhar Card")
      et_uid.setText(aadharInfo.uid)
      et_name.setText(aadharInfo.name)
      et_dob.setText(aadharInfo.dob)
      et_yob.setText(aadharInfo.yob)
      et_address.setText(aadharInfo.address)
      et_age.setText(aadharInfo.age)
      et_gender.setText(aadharInfo.gender)
    }
  }

  private fun handleDob() {
    val calender = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(this,
      { _: DatePicker?, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
        et_dob.setText(
          AppUtils.getFormattedDob(dayOfMonth, monthOfYear + 1, selectedYear))
      }, calender[Calendar.YEAR], calender[Calendar.MONTH],
      calender[Calendar.DAY_OF_MONTH])
    datePickerDialog.show()
  }

  private fun updateData() {
    if (isIdValid() && isNameValid() && isAddressValid() && isAgeValid() && isGenderValid() && isPhoneValid()) {
      editFormViewModel.createUser(AadharRequest())
        .observe(this, Observer {
          when (it.status) {
            Result.Status.SUCCESS -> {
              progress_bar.visibility = View.GONE
              Toast.makeText(this, "Entry Updated", Toast.LENGTH_SHORT)
                .show()
              finish()
            }
            Result.Status.ERROR -> {
              progress_bar.visibility = View.GONE
              showError(it.message!!)
            }
            Result.Status.LOADING -> progress_bar.visibility = View.VISIBLE
          }
        })
    }
  }

  private fun isIdValid(): Boolean {
    return if (et_uid.text.toString().length >= 7) {
      tv_err_uid.visibility = View.GONE
      true
    } else {
      tv_err_uid.visibility = View.VISIBLE
      false
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
    return if (et_age.text.toString()
        .toInt() > 0) {
      tv_err_age.visibility = View.GONE
      true
    } else {
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
}