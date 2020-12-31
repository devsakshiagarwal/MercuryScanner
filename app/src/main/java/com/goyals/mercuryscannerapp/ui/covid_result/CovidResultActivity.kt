package com.goyals.mercuryscannerapp.ui.covid_result

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import com.goyals.mercuryscannerapp.model.schema.AadharResponse
import com.goyals.mercuryscannerapp.model.schema.ListRequest
import com.goyals.mercuryscannerapp.model.schema.UpdateCustomerSchema
import com.goyals.mercuryscannerapp.ui.main.AadharAdapter
import com.goyals.mercuryscannerapp.ui.main.AadharAdapter.AadharAdapterInterface
import com.goyals.mercuryscannerapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.covid_result_fragment.cl_search
import kotlinx.android.synthetic.main.covid_result_fragment.rb_negative
import kotlinx.android.synthetic.main.covid_result_fragment.rb_positive
import kotlinx.android.synthetic.main.covid_result_fragment.rg_result
import kotlinx.android.synthetic.main.covid_result_fragment.rv_search
import kotlinx.android.synthetic.main.covid_result_fragment.search_view
import kotlinx.android.synthetic.main.covid_result_fragment.tv_address
import kotlinx.android.synthetic.main.covid_result_fragment.tv_gender
import kotlinx.android.synthetic.main.covid_result_fragment.tv_id
import kotlinx.android.synthetic.main.covid_result_fragment.tv_id_type
import kotlinx.android.synthetic.main.covid_result_fragment.tv_name
import kotlinx.android.synthetic.main.covid_result_fragment.tv_phone
import kotlinx.android.synthetic.main.covid_result_fragment.tv_result
import kotlinx.android.synthetic.main.covid_result_fragment.tv_user_id
import kotlinx.android.synthetic.main.layout_progress.progress_bar

@AndroidEntryPoint
class CovidResultActivity : AppCompatActivity(),
  Toolbar.OnMenuItemClickListener,
  AadharAdapterInterface {
  private val mainViewModel: MainViewModel by viewModels()
  private var selectedAadharInfo = AadharInfo()

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

  override fun onClick(aadharInfo: AadharInfo) {
    selectedAadharInfo = aadharInfo
    rv_search.visibility = View.GONE
    rg_result.visibility = View.VISIBLE
    cl_search.visibility = View.VISIBLE
    tv_name.text = selectedAadharInfo.name
    tv_id_type.text = selectedAadharInfo.proofType
    tv_id.text = selectedAadharInfo.proofNumber
    tv_address.text = selectedAadharInfo.address
    tv_phone.text = selectedAadharInfo.phone
    tv_gender.text = selectedAadharInfo.gender
    tv_user_id.text = selectedAadharInfo.userId.toString()
    tv_result.text = selectedAadharInfo.covidResultToShow
  }

  private fun initViews() {
    actionBar?.setDisplayHomeAsUpEnabled(true)
    search_view.setOnQueryTextListener(object : OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        selectedAadharInfo = AadharInfo()
        rv_search.visibility = View.VISIBLE
        rg_result.visibility = View.GONE
        cl_search.visibility = View.GONE
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
            val aadharAdapter = AadharAdapter(this)
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
    if (selectedAadharInfo.userId.toString()
        .isNotEmpty()) {
      var updateCustomerSchema =
        UpdateCustomerSchema(1, SharedPref(this).getUserId(),"updated")
      if (rb_negative.isChecked) {
        updateCustomerSchema =
          UpdateCustomerSchema(0, SharedPref(this).getUserId(), "updated")
      }
      mainViewModel.updateCustomer(updateCustomerSchema,
        selectedAadharInfo.userId)
        .observe(this, Observer {
          when (it.status) {
            SUCCESS -> {
              progress_bar.visibility = View.GONE
              Toast.makeText(this, "Entry Updated", Toast.LENGTH_SHORT)
                .show()
              finish()
            }
            ERROR -> {
              progress_bar.visibility = View.GONE
              showError(it.message!!)
            }
            LOADING -> progress_bar.visibility = View.VISIBLE
          }
        })
    }
  }
}