package com.goyals.mercuryscannerapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import com.goyals.mercuryscannerapp.model.schema.AadharResponse
import com.goyals.mercuryscannerapp.ui.edit_form.EditFormActivity
import com.goyals.mercuryscannerapp.ui.scanner.AadharScanActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.app_bar_main.fab
import kotlinx.android.synthetic.main.app_bar_main.fab_other_id
import kotlinx.android.synthetic.main.app_bar_main.fab_scan_id
import kotlinx.android.synthetic.main.app_bar_main.rl_fabs
import kotlinx.android.synthetic.main.app_bar_main.rv_main
import kotlinx.android.synthetic.main.app_bar_main.toolbar
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
    setSupportActionBar(toolbar)
    handleFabs()
  }

  private fun handleFabs() {
    fab.setOnClickListener {
      rl_fabs.visibility = View.VISIBLE
    }
    fab_scan_id.setOnClickListener {
      rl_fabs.visibility = View.GONE
      startActivity(Intent(this, AadharScanActivity::class.java))
    }
    fab_other_id.setOnClickListener {
      rl_fabs.visibility = View.GONE
      startActivity(Intent(this, EditFormActivity::class.java))
    }
  }

  private fun getCustomers() {
    mainViewModel.getCustomers()
      .observe(this, Observer {
        when (it.status) {
          SUCCESS -> {
            progress_bar.visibility = View.GONE
            val response : AadharResponse = it.data!!
            val aadharAdapter = AadharAdapter()
            Log.d("main_act", "list length: ${response.customerList
              .aadharList.size}")
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
}