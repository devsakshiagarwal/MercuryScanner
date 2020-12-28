package com.goyals.mercuryscannerapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.goyals.mercuryscannerapp.ui.main.MainActivity
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.arch.Result
import com.goyals.mercuryscannerapp.model.SharedPref
import com.goyals.mercuryscannerapp.model.schema.LoginData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_progress.progress_bar

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
  private val loginViewModel: LoginViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    if (SharedPref(this).isLoggedIn()) {
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    } else {
      initViews()
    }
  }

  private fun initViews() {
    button_login.setOnClickListener {
      if (et_username.text!!.toString()
          .isNotEmpty() && et_password.text!!.toString()
          .isNotEmpty()) {
        loginViewModel.loginUser(et_username.text!!.toString(),
          et_password.text!!.toString())
          .observe(this, Observer {
            when (it.status) {
              Result.Status.SUCCESS -> {
                progress_bar.visibility = View.GONE
                val login = it.data!!
                proceedWithLogin(login.responseData)
              }
              Result.Status.ERROR -> {
                progress_bar.visibility = View.GONE
                showError(it.message!!)
              }
              Result.Status.LOADING -> progress_bar.visibility = View.VISIBLE
            }
          })
      } else {
        showError("Username & Password Mandatory!")
      }
    }
  }

  private fun proceedWithLogin(login: LoginData) {
    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT)
      .show()
    val sharedPref = SharedPref(this)
    sharedPref.setLoggedIn(true)
    sharedPref.setUserId(login.userId)
    sharedPref.setUserName(login.userName)
    sharedPref.setUserType(login.userType)
    startActivity(Intent(this, MainActivity::class.java))
    finish()
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