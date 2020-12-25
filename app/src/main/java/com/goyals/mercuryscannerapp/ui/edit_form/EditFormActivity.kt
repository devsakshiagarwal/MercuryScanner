package com.goyals.mercuryscannerapp.ui.edit_form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_form.*

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
    et_id_type.setOnClickListener {
      IdTypeFragment.newInstance()
        .show(supportFragmentManager, "id_type_fragment")
    }
    editFormViewModel.idTypeLiveData.observe(this, Observer {
      et_id_type.setText(it!!)
    })
    if (intent.getParcelableExtra<AadharInfo>("aadhar_info") != null) {
      val aadharInfo = intent.getParcelableExtra<AadharInfo>("aadhar_info")!!
      et_uid.setText(aadharInfo.customerId)
      et_name.setText(aadharInfo.customerName)
      et_dob.setText(aadharInfo.customerDob)
      //      et_yob.setText(aadharInfo.yob)
      et_address.setText(aadharInfo.customerAddress)
      //      et_age.setText(aadharInfo.a)
      et_gender.setText(aadharInfo.customerGender)
    }
  }
}