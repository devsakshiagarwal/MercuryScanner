package com.goyals.mercuryscannerapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.goyals.mercuryscannerapp.R.layout
import com.goyals.mercuryscannerapp.arch.Result.Status.ERROR
import com.goyals.mercuryscannerapp.arch.Result.Status.LOADING
import com.goyals.mercuryscannerapp.arch.Result.Status.SUCCESS
import com.goyals.mercuryscannerapp.model.SharedPref
import com.goyals.mercuryscannerapp.model.schema.AadharResponse
import com.goyals.mercuryscannerapp.model.schema.ListRequest
import com.goyals.mercuryscannerapp.ui.covid_result.CovidResultActivity
import com.goyals.mercuryscannerapp.ui.edit_form.EditFormActivity
import com.goyals.mercuryscannerapp.ui.login.LoginActivity
import com.goyals.mercuryscannerapp.ui.scanner.AadharScanActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.app_bar_main.fab
import kotlinx.android.synthetic.main.app_bar_main.fab_other_id
import kotlinx.android.synthetic.main.app_bar_main.fab_results
import kotlinx.android.synthetic.main.app_bar_main.fab_scan_id
import kotlinx.android.synthetic.main.app_bar_main.iv_logout
import kotlinx.android.synthetic.main.app_bar_main.rl_fabs
import kotlinx.android.synthetic.main.app_bar_main.rv_main
import kotlinx.android.synthetic.main.layout_progress.progress_bar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private val mainViewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    initViews()
  }

  override fun onResume() {
    super.onResume()
    getCustomers()
  }

  private fun initViews() {
    handleFabs()
    iv_logout.setOnClickListener {
      showErrorForLogout()
    }
  }

  private fun handleFabs() {
    fab.setOnClickListener {
      if (rl_fabs.visibility == View.VISIBLE) {
        rl_fabs.visibility = View.GONE
      } else {
        rl_fabs.visibility = View.VISIBLE
      }
    }
    fab_scan_id.setOnClickListener {
      rl_fabs.visibility = View.GONE
      startActivity(Intent(this, AadharScanActivity::class.java))
    }
    fab_other_id.setOnClickListener {
      rl_fabs.visibility = View.GONE
      startActivity(Intent(this, EditFormActivity::class.java))
    }
    fab_results.setOnClickListener {
      rl_fabs.visibility = View.GONE
      startActivity(Intent(this, CovidResultActivity::class.java))
    }
  }

  private fun getCustomers() {
    mainViewModel.getCustomers(
      ListRequest(30, 0, "", SharedPref(this).getUserId(),
        SharedPref(this).getUserType()!!))
      .observe(this, Observer {
        when (it.status) {
          SUCCESS -> {
            progress_bar.visibility = View.GONE
            val response: AadharResponse = it.data!!
            val aadharAdapter = AadharAdapter(null)
            aadharAdapter.setAddress(response.customerList.aadharList)
            rv_main.apply {
              layoutManager = LinearLayoutManager(this@MainActivity)
              adapter = aadharAdapter
            }
          }
          ERROR -> {
            progress_bar.visibility = View.GONE
            showError(it.message!!)
          }
          LOADING -> progress_bar.visibility = View.VISIBLE
        }
      })
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

  private fun showErrorForLogout() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Alert")
      .setMessage("Are you sure you want to logout?")
    builder.setPositiveButton("Yes") { dialog, _ ->
      dialog.dismiss()
      SharedPref(this).clearSharedPref()
      startActivity(Intent(this, LoginActivity::class.java))
      finish()
    }
    builder.setNegativeButton("No") { dialog, _ ->
      dialog.dismiss()
    }
    builder.show()
  }
}