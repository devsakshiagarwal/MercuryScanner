package com.goyals.mercuryscannerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import kotlinx.android.synthetic.main.activity_edit_form.*
import kotlinx.android.synthetic.main.app_bar_main.toolbar

class EditFormActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_form)
    initViews()
  }

  override fun onMenuItemClick(item: MenuItem?): Boolean {
    when (item!!.itemId) {
      R.id.item_save -> {
        // updateData()
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
    //toolbar.inflateMenu(R.menu.menu_save)
    val aadharInfo = intent.getParcelableExtra<AadharInfo>("aadhar_info")!!
    et_uid.setText(aadharInfo.uid)
    et_name.setText(aadharInfo.name)
    et_dob.setText(aadharInfo.dob)
    et_yob.setText(aadharInfo.yob)
    et_address.setText(aadharInfo.address)
    et_age.setText(aadharInfo.age)
    et_gender.setText(aadharInfo.gender)
  }
}