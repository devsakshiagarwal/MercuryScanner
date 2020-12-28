package com.goyals.mercuryscannerapp.ui.covid_result

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.arch.Result.Status.ERROR
import com.goyals.mercuryscannerapp.arch.Result.Status.LOADING
import com.goyals.mercuryscannerapp.arch.Result.Status.SUCCESS
import com.goyals.mercuryscannerapp.model.SharedPref
import com.goyals.mercuryscannerapp.model.schema.AadharResponse
import com.goyals.mercuryscannerapp.model.schema.ListRequest
import com.goyals.mercuryscannerapp.ui.main.AadharAdapter
import com.goyals.mercuryscannerapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.app_bar_main.rv_main
import kotlinx.android.synthetic.main.covid_result_fragment.rv_search
import kotlinx.android.synthetic.main.covid_result_fragment.search_view
import kotlinx.android.synthetic.main.layout_progress.progress_bar

@AndroidEntryPoint
class CovidResultActivity : AppCompatActivity(),
  Toolbar.OnMenuItemClickListener {
  private val mainViewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.covid_result_fragment)
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
    search_view.setOnQueryTextListener(object : OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        getCustomers(newText!!)
        return false
      }
    })
  }

  private fun getCustomers(query: String) {
    mainViewModel.getCustomers(
      ListRequest(0, 0, query, SharedPref(this).getUserId(),
        SharedPref(this).getUserType()!!))
      .observe(this, Observer {
        when (it.status) {
          SUCCESS -> {
            progress_bar.visibility = View.GONE
            val response: AadharResponse = it.data!!
            val aadharAdapter = AadharAdapter()
            aadharAdapter.setAddress(response.customerList.aadharList)
            rv_search.apply {
              layoutManager = LinearLayoutManager(this@CovidResultActivity)
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

  private fun updateData() {
  }
}