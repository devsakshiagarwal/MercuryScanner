package com.goyals.mercuryscannerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import kotlinx.android.synthetic.main.activity_edit_form.*

class EditFormActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_form)
    initViews()
  }

  private fun initViews() {
    val aadharInfo = intent.getParcelableExtra<AadharInfo>("aadhar_info")!!
    tv_random.text =
      "${aadharInfo.uid}, ${aadharInfo.name}, ${aadharInfo.gender}, ${aadharInfo.dob}, ${aadharInfo.yob}, ${aadharInfo.address}, " + "${aadharInfo.pincode}"
  }
}